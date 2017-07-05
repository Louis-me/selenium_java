package test;

import base.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

import java.io.FileNotFoundException;

public class LoginTest extends TestBaseSetup {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }
    @Test
    public void testLogin() throws YamlException, FileNotFoundException, InterruptedException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();
        Assert.assertTrue(loginPage.checkpoint(), "检查点不通过");
    }
}
