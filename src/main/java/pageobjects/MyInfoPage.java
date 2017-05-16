package pageobjects;

import com.esotericsoftware.yamlbeans.YamlException;
import model.CheckPoint;
import model.TestCase;
import org.openqa.selenium.WebDriver;
import util.OperateElement;
import util.YamlRead;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public class MyInfoPage {
    YamlRead yamlRead;
    OperateElement operateElement;
    protected WebDriver driver;
    private boolean isOperate = true;


    public MyInfoPage(WebDriver driver){
        this.driver = driver;
    }

    public MyInfoPage(WebDriver driver, String path){
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
        for(Object item: list){
            TestCase testCase = new TestCase();
            testCase.setFind_type((String) ((Map)item).get("find_type"));
            testCase.setElement_info((String) ((Map)item).get("element_info"));
//            testCase.setText((String) ((Map)item).get("text"));
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
    public boolean checkpoint() throws YamlException, FileNotFoundException, InterruptedException {
        if (!isOperate) { // 如果操作步骤失败，检查点也就判断失败
            System.out.println("操作步骤失败了");
            return false;
        }
        List list = (List) yamlRead.getYmal().get("check");
//        System.out.println(list);
        for(Object item: list){
            CheckPoint checkPoint = new CheckPoint();
            checkPoint.setElement_info((String) ((Map)item).get("element_info"));
            checkPoint.setFind_type((String) ((Map)item).get("find_type"));
            checkPoint.setOperate_type((String) ((Map)item).get("operate_type"));
            checkPoint.setText((String) ((Map)item).get("text"));
            if (!operateElement.checkElement(checkPoint)) {
                return false;
            }
        }
        return true;
    }

}
