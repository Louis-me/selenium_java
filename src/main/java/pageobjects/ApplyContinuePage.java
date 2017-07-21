package pageobjects;

import com.esotericsoftware.yamlbeans.YamlException;
import model.CheckPoint;
import model.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import util.ExtentTestNGIReporterListener;
import util.OperateElement;
import util.YamlRead;
import util.WebBrower;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by shikun on 2017/7/3.
 */
public class ApplyContinuePage {
    YamlRead yamlRead;
    OperateElement operateElement;
    protected WebDriver driver;
    private boolean isOperate = true;
    private WebBrower webBrower;
    /***
     * 默认构造函数
     * @param driver
     * @param path yaml配置参数
     */
    public ApplyContinuePage(WebDriver driver, String path) {
        this.driver = driver;
        yamlRead = new YamlRead(path);
        operateElement = new OperateElement(this.driver);
        webBrower = new WebBrower(this.driver);
    }

    /***
     * 前置步骤，打开账号延期申请工作流，切换到iframe
     */
    private void prefix() throws YamlException, FileNotFoundException, InterruptedException {
        List list = (List) yamlRead.getYmal().get("prefix");
        System.out.println(list);
        try {
            for (Object item : list) {
                TestCase testCase = new TestCase();
                testCase.setFind_type((String) ((Map) item).get("find_type"));
                testCase.setElement_info((String) ((Map) item).get("element_info"));
                testCase.setText((String) ((Map) item).get("text"));
                testCase.setOperate_type((String) ((Map) item).get("operate_type"));
                testCase.setFrame((String) ((Map) item).get("frame")); //切到iframe
                if (!operateElement.operate(testCase)) {
                    isOperate = false;
                    System.out.println("前置条件失败");
                    break;
                }
            }
        }
        catch (org.openqa.selenium.NoSuchFrameException e) {
            System.out.println("切换frame失败");
            isOperate = false;
        }

    }

    /***
     * 测试步骤
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public void operate() throws YamlException, FileNotFoundException, InterruptedException {

        prefix();
        if (!isOperate) { // 如果操前置条件失败，操作步骤就不用执行
            System.out.println("前置条件失败");
        } else {
            List list = (List) yamlRead.getYmal().get("testcase");
//        System.out.println(list);
            for (Object item : list) {
                TestCase testCase = new TestCase();
                testCase.setFind_type((String) ((Map) item).get("find_type"));
                testCase.setElement_info((String) ((Map) item).get("element_info"));
                testCase.setText((String) ((Map) item).get("text"));
                testCase.setOperate_type((String) ((Map) item).get("operate_type"));
                testCase.setDefaultContent((String) ((Map) item).get("defaultContent")); // 切回主页面
                if (!operateElement.operate(testCase)) {
                    isOperate = false;
                    System.out.println("操作失败");
                    break;
                }

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
            System.out.println("操作失败");
            TakesScreenshot(browserType);
            return false;
        }
        List list = (List) yamlRead.getYmal().get("check");
        for (Object item : list) {
            CheckPoint checkPoint = new CheckPoint();
            checkPoint.setElement_info((String) ((Map) item).get("element_info"));
            checkPoint.setFind_type((String) ((Map) item).get("find_type"));
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