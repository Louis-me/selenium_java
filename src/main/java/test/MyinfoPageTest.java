package test;

import browser.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobjects.LoginPage;
import pageobjects.MyInfoPage;

import java.io.FileNotFoundException;

//@Listeners(value= JyperionListener.class)

public class MyinfoPageTest {
    private WebDriver driver;
    private TestBaseSetup testBaseSetup = new TestBaseSetup();
    @Parameters({ "browserType", "appURL" ,"driverPath","version", "remoteip"})
    @org.testng.annotations.BeforeClass
    public void setUp(String browserType, String appURL, String driverPath, String version, String remoteip) {
        driver = testBaseSetup.setDriver(browserType, appURL,version, remoteip);
    }
    @Test
    public void testMyInfo() throws YamlException, FileNotFoundException, InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();

        MyInfoPage myInfoPage = new MyInfoPage(this.driver, "/Myinfo.yaml");
        myInfoPage.operate();
        Assert.assertTrue(myInfoPage.checkpoint(), "检查点不通过");
    }
    @AfterClass
    public synchronized  void tearDown() {
        driver.quit();
    }
}
