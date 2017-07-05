package test;

import base.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.ApplyContinuePage;
import pageobjects.LoginPage;

import java.io.FileNotFoundException;

/**
 * Created by shikun on 2017/7/4.
 */
public class ApplyContinueTest extends TestBaseSetup {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void testApplyContinue() throws YamlException, FileNotFoundException, InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();

        ApplyContinuePage applyContinuePage = new ApplyContinuePage(this.driver, "/ApplyContinue1.yaml");
        applyContinuePage.operate();
        Assert.assertTrue(applyContinuePage.checkpoint(), "检查点不通过");
    }
}
