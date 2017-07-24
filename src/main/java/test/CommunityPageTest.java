package test;

import browser.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import pageobjects.CommunityPage;
import pageobjects.LoginPage;

import java.io.FileNotFoundException;

//@Listeners(value= JyperionListener.class)

public class CommunityPageTest  {
    private WebDriver driver;
    private TestBaseSetup testBaseSetup = new TestBaseSetup();
    @Parameters({ "browserType", "appURL" ,"version", "remoteip"})
    @org.testng.annotations.BeforeClass
    public void setUp(String browserType, String appURL, String driverPath, String version, String remoteip) {
        driver = testBaseSetup.setDriver(browserType, appURL,version, remoteip);
    }
    @Test
    public void testHighQuality() throws YamlException, FileNotFoundException, InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();

        CommunityPage communityPage = new CommunityPage(this.driver, "/Community.yaml");
        communityPage.operate();
        Assert.assertTrue(communityPage.checkpoint(), "检查点不通过");
    }
}

