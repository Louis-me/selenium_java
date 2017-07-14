package browser;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import util.ExtentTestNGIReporterListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class TestBaseSetup1 {
    private WebDriver driver;
//    static String driverPath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\";

    public  WebDriver getDriver() {
        return driver;
    }

    private void setDriver(String browserType, String appURL, String driverPath, String version, String remoteip) {
        if (browserType.equals("chrome")) {
            driver = initChromeDriver(appURL, driverPath, version, remoteip);

        } else if (browserType.equals("firefox")) {
            driver = initFirefoxDriver(appURL);

        } else {
            System.out.println("browser : " + browserType
                    + " is invalid, Launching Firefox as browser of choice..");
            driver = initFirefoxDriver(appURL);
        }
    }


    private static WebDriver initChromeDriver(String appURL, String driverPath, String chrome_version, String remoteip) {
        WebDriver driver = null;
        if (chrome_version == null || Objects.equals(chrome_version, "") || remoteip == null || Objects.equals(remoteip, "")) {
            System.setProperty("webdriver.chrome.driver", driverPath
                    + "chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.navigate().to(appURL);
        } else {
            DesiredCapabilities desiredCaps = new DesiredCapabilities("chrome", chrome_version, Platform.WINDOWS);

            try {
                driver = new RemoteWebDriver(new URL("http://" + remoteip + ":4444/wd/hub/"), desiredCaps);
                driver.manage().window().maximize();
                driver.navigate().to(appURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return driver;

    }

    private static WebDriver initFirefoxDriver(String appURL) {
        System.out.println("Launching Firefox browser..");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }


    @Parameters({ "browserType", "appURL" ,"driverPath","version", "remoteip"})
    @BeforeClass
    public synchronized  void initializeTestBaseSetup(String browserType, String appURL, String driverPath, String version, String remoteip) {
        try {
            setDriver(browserType, appURL, driverPath,version, remoteip);
//            ExtentTestNGIReporterListener.driver = driver;
        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }
    }

    @AfterClass
    public synchronized  void tearDown() {
        driver.quit();
    }
}