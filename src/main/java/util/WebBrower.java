package util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by shikun on 2017/7/3.
 */
public class WebBrower {
    private WebDriver driver;
    public WebBrower(WebDriver driver){
        this.driver = driver;
    }

    /**
     * 切换到iframe
     * frameName: iframe名字
     */
    public void switchToFrame(String frameName) {
            this.driver.switchTo().frame(frameName);
    }

    /**
     * 从iframe切换到默认页
     *
     */
    public void defaultContent() {
        this.driver.switchTo().defaultContent();
    }

    /**
     * 滚动条移动到指定元素位置
     *
     */
    public void executeScript(String elementInfo, WebElement webElement){
        System.out.println("executeScript==");
//        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView();", webElement);
        ((JavascriptExecutor) this.driver).executeScript("arguments[0].style.height='auto';arguments[0].style.width='auto';", webElement);
//        ((JavascriptExecutor) this.driver).executeScript(elementInfo);
    }
}
