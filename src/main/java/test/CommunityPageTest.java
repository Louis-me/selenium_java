package test;

import base.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CommunityPage;
import pageobjects.LoginPage;

import java.io.FileNotFoundException;

//@Listeners(value= JyperionListener.class)

public class CommunityPageTest extends TestBaseSetup {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
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

