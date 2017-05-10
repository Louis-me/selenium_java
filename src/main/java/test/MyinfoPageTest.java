package test;

import base.TestBaseSetup;
import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.LoginPage;
import pageobjects.MyInfoPage;

import java.io.FileNotFoundException;

/**
 * Created by sWX458348 on 2017/5/10.
 */
public class MyinfoPageTest extends TestBaseSetup {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }
    @Test
    public void testMyInfo() throws YamlException, FileNotFoundException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();

        MyInfoPage myInfoPage = new MyInfoPage(this.driver, "/Myinfo.yaml");
        myInfoPage.operate();
        Assert.assertTrue(myInfoPage.checkpoint(), "检查点不通过");
    }
}
