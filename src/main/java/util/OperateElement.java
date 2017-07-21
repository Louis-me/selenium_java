package util;
import model.CheckPoint;
import model.TestCase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;
import java.util.Objects;
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
    private static final String CSSSELECTOR  = "cssSelector";
    private static final String ID  = "id";
    WebDriverWait wait;
    WebBrower webBrower;
    public OperateElement(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver,TIMEOUT);
        webBrower = new WebBrower(this.driver);

    }



    /***
     *
     *
     * @param checkPoint 检查点实体类
     * @return
     */
    public Boolean checkElement(CheckPoint checkPoint) throws InterruptedException {
        final By by = getElement(checkPoint.getFind_type(), checkPoint.getElement_info());
        boolean status = waitForElement(by);
        if (checkPoint.getOperate_type() == null) { // Operate_type为空的话，默认就是find查找
            return status;
        } else if ((checkPoint.getOperate_type().equals("getValue") && checkPoint.getText() != null)) { //具体检查点
        status = driver.findElement(by).getAttribute("value").equals(checkPoint.getText());
        System.out.println("查找点为=" + checkPoint.getText());
    } else {
        status = false;
    }
    return status;
    }

    /***
     * 得到元素
     * @param find_type (id,xpath,name,classname)
     * @param element_info 具体元素信息
     * @return By
     */
    public By getElement(String find_type, String element_info){
        By by = null;
        switch (find_type) {
            case ID:
                by = By.id(element_info);

                break;
            case NAME:
                by = By.name(element_info);

                break;
            case XPATH:
                by = By.xpath(element_info);

                break;
            case CSSSELECTOR:
                by = new By.ByCssSelector(element_info);
                break;
            default:
                System.out.println("不支持其他操作方法" + element_info);
                break;
        }
        return by;
    }

    public WebElement setElement(By by){
        WebElement webElement = this.driver.findElement(by);
        return webElement;

    }

    /***
     *  检查元素是否存在
     * @param elementLocator
     */
    private boolean waitForElement(final By elementLocator) {

        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            return true;
        }
        catch (NoSuchElementException | ElementNotVisibleException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    /***
     *  清空文本框
     * webElement

     */
    public void clear(WebElement webElement) {
        webElement.clear();
    }

    /***
     *  执行步骤
     * TestCase 实体类
     * @param testCase
     */
    public boolean operate(TestCase testCase) throws InterruptedException {
        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setFind_type(testCase.getFind_type());
        checkPoint.setElement_info(testCase.getElement_info());

        if (testCase.getDefaultContent()!= null) { //切换到主页面
            Thread.sleep(1000);
            webBrower.defaultContent();
            System.out.println("-------------getDefaultContent--------------------");
            System.out.println("切换到主页面");
        }
        boolean check =  checkElement(checkPoint);
        if (check) {
            Thread.sleep(800);
            By by = getElement(testCase.getFind_type(), testCase.getElement_info());
            WebElement webElement = setElement(by);
            switch (testCase.getOperate_type()) {
                case "click": { // 点击
                    webElement.click();
                    System.out.println("点击了==" + testCase.getElement_info());
                    break;
                }
                case "send_keys": {  //输入内容
                    clear(webElement); // 清空文本框
                    webElement.sendKeys(testCase.getText());
                    System.out.println(testCase.getElement_info() + ":输入内容==" + testCase.getText());
                    break;
                }
                default:
                    System.out.println("不支持操作方法=" + testCase.getElement_info());
                    System.out.println("不支持操作方法=" + testCase.getOperate_type());
                    break;
            }
            if (testCase.getFrame()!= null) { // 切换到frame
                Thread.sleep(1000);
                webBrower.switchToFrame(testCase.getFrame());
                System.out.println("-------------getFrame--------------------");
                System.out.println(testCase.getFrame());
            }


        } else {
            System.out.println(testCase.getElement_info()+"==元素不存在");
        }

        return check;
    }

}
