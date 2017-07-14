package test;

import browser.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ApplyContinuePage;
import pageobjects.LoginPage;
import util.ExtentTestNGIReporterListener;

import java.io.FileNotFoundException;

/**
 * Created by shikun on 2017/7/4.
 */



public class ApplyContinueTest  {
    private WebDriver driver;
    private TestBaseSetup testBaseSetup = new TestBaseSetup();


    @Parameters({ "browserType", "appURL" ,"driverPath","browserVersion", "remoteIP"})
    @BeforeClass
    public  void setUp(String browserType, String appURL, String driverPath, String browserVersion, String remoteIP) {
        driver = testBaseSetup.setDriver(browserType, appURL, driverPath,browserVersion, remoteIP);

    }


    @Test
    public void testApplyContinue() throws YamlException, FileNotFoundException, InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();

        ApplyContinuePage applyContinuePage = new ApplyContinuePage(this.driver, "/ApplyContinue1.yaml");
        applyContinuePage.operate();
        Assert.assertTrue(applyContinuePage.checkpoint(), "检查点不通过");

    }
    @AfterClass
    public void tearDown() {
//        this.driver.switchTo().defaultContent();
//        ExtentTestNGIReporterListener.driver = this.driver;
        this.driver.quit();
    }
}
