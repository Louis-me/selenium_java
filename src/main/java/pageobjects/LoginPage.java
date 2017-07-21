package pageobjects;

import com.esotericsoftware.yamlbeans.YamlException;
import model.CheckPoint;
import model.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import util.ExtentTestNGIReporterListener;
import util.OperateElement;
import util.YamlRead;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LoginPage {
    YamlRead yamlRead;
    OperateElement operateElement;
    protected WebDriver driver;
    private boolean isOperate = true;
    /***
     * 默认构造函数
     * @param driver
     * @param path yaml配置参数
     */
    public LoginPage(WebDriver driver, String path) {
        this.driver = driver;
        yamlRead = new YamlRead(path);
        operateElement= new OperateElement(this.driver);
    }

    /***
     * 测试步骤
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public void operate() throws YamlException, FileNotFoundException, InterruptedException {
        List list = (List) yamlRead.getYmal().get("testcase");
//        System.out.println(list);
        for(Object item: list){
            TestCase testCase = new TestCase();
            testCase.setFind_type((String) ((Map)item).get("find_type"));
            testCase.setElement_info((String) ((Map)item).get("element_info"));
            testCase.setText((String) ((Map)item).get("text"));
            testCase.setOperate_type((String) ((Map)item).get("operate_type"));
            if (!operateElement.operate(testCase)) {
                isOperate = false;
                System.out.println("操作失败");
                break;
            }

        }
    }

    /***
     * 检查点
     * @return
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public boolean checkpoint(String browserType) throws YamlException, FileNotFoundException, InterruptedException {
        if (!isOperate) { // 如果操作步骤失败，检查点也就判断失败
            System.out.println("前置条件失败");
            TakesScreenshot(browserType);
            return false;
        }
        List list = (List) yamlRead.getYmal().get("check");
        for(Object item: list){
                CheckPoint checkPoint = new CheckPoint();
                checkPoint.setElement_info((String) ((Map)item).get("element_info"));
                checkPoint.setFind_type((String) ((Map)item).get("find_type"));
                if (!operateElement.checkElement(checkPoint)) {
                    TakesScreenshot(browserType);
                    return false;
                }
            }

        return true;
    }

    public void TakesScreenshot(String browserType) {
        File directory = new File("test-output");
        try {
            String screenPath = directory.getCanonicalPath() + "\\";
            File file = new File(screenPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            System.out.println("------------检查点失败--------");
//            this.driver.switchTo().defaultContent();
            String fileName = screenPath + browserType + "_" + UUID.randomUUID().toString() + ".png";
            driver.switchTo().defaultContent();
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(fileName));

            ExtentTestNGIReporterListener.fileName = fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
