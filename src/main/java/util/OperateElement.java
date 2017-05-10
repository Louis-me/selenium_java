package util;
import model.CheckPoint;
import model.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * 常用元素操作类
 */
public class OperateElement {
    WebDriver driver;
    private final int TIMEOUT = 10;
    private static final String NAME  = "name";
    private static final String XPATH  = "xpath";
    private static final String CLASSNAME  = "className";
    private static final String ID  = "id";


    public OperateElement(WebDriver driver) {
        this.driver = driver;
    }



    /***
     *
     *
     * @param checkPoint 检查点实体类
     * @return
     */
    public boolean checkElement(CheckPoint checkPoint) {
        By by = getElement(checkPoint.getFind_type(),checkPoint.getElement_info());
        boolean status = false;
        try {
            if (checkPoint.getOperate_type() == null) { // Operate_type为空的话，默认就是find查找
                driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
                driver.findElement(by);
                status = true;
            } else if((checkPoint.getOperate_type().equals("getValue") && checkPoint.getText() != null)) { //具体检查点
                status = driver.findElement(by).getAttribute("value").equals(checkPoint.getText());
            } else {
                status = false;
            }
            return status;
        } catch (TimeoutException e) {
            System.out.println("查找元素超时");
            return false;

        } catch (NoSuchElementException e) {
            System.out.println("元素不存在");
            return false;
        }
    }

    /***
     * 得到元素
     * @param find_type (id,xpath,name,classname)
     * @param element_info 具体元素信息
     * @return By
     */
    public By getElement(String find_type, String element_info){
        By by = null;
        if (find_type.equals(ID)) {
            by = By.id(element_info);

        } else if (find_type.equals(NAME)) {
            by = By.name(element_info);

        } else if (find_type.equals(XPATH)) {
            by = By.xpath(element_info);

        } else {
            System.out.println("不支持其他操作方法");
        }
        System.out.println(by);
        return by;
    }

    public WebElement setElement(By by){
        WebElement webElement = this.driver.findElement(by);
        return webElement;

    }

    /***
     * 实体类
     * @param testCase
     */
    public void operate(TestCase testCase) {

        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setFind_type(testCase.getFind_type());
        checkPoint.setElement_info(testCase.getElement_info());
        boolean check =  checkElement(checkPoint);
        if (check) {
            By by = getElement(testCase.getFind_type(),testCase.getElement_info());
            WebElement webElement = setElement(by);
            if (testCase.getOperate_type().equals("click")) {webElement.click();}
            else if(testCase.getOperate_type().equals("send_keys")) {webElement.sendKeys(testCase.getText());}
            else { System.out.println("不支持其他操作方法");}
        } else {
            System.out.println("元素不存在");
        }
    }

}

