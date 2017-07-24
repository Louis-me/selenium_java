package test;

import browser.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pageobjects.LoginPage;

import java.io.FileNotFoundException;

public class LoginTest  {
    private WebDriver driver;
    private TestBaseSetup testBaseSetup = new TestBaseSetup();
    private String browserType = "";


    @Parameters({ "browserType", "appURL" ,"browserVersion", "remoteIP"})
    @BeforeClass
    public void setUp(String browserType, String appURL, String browserVersion, String remoteIP,  ITestContext testContext) {
        driver = testBaseSetup.setDriver(browserType, appURL,browserVersion, remoteIP);
        this.browserType = browserType;
    }


//    @Test(priority = 0)
//    public void testLoginFail() throws YamlException, FileNotFoundException, InterruptedException {
//        LoginPage loginPage = new LoginPage(this.driver, "/LoginFail.yaml");
//        loginPage.operate();
//        Assert.assertTrue(loginPage.checkpoint(this.browserType), "检查点不通过");
//
//    }
    @Test(priority = 1)
    public void testLogin() throws YamlException, FileNotFoundException, InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();
        Assert.assertTrue(loginPage.checkpoint(this.browserType), "检查点不通过");

    }

    @AfterClass
    public void tearDown() {

        this.driver.quit();
    }
}
