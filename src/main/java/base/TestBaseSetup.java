package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import util.ExtentTestNGIReporterListener;

public class TestBaseSetup {
    private WebDriver driver;
//    static String driverPath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\";

    public  WebDriver getDriver() {
        return driver;
    }

    private void setDriver(String browserType, String appURL, String driverPath) {
        if (browserType.equals("chrome")) {
            driver = initChromeDriver(appURL, driverPath);

        } else if (browserType.equals("firefox")) {
            driver = initFirefoxDriver(appURL);

        } else {
            System.out.println("browser : " + browserType
                    + " is invalid, Launching Firefox as browser of choice..");
            driver = initFirefoxDriver(appURL);
        }
    }


    private static WebDriver initChromeDriver(String appURL, String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath
                + "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    private static WebDriver initFirefoxDriver(String appURL) {
        System.out.println("Launching Firefox browser..");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }


    @Parameters({ "browserType", "appURL" ,"driverPath"})
    @BeforeClass
    public synchronized  void initializeTestBaseSetup(String browserType, String appURL, String driverPath) {
        try {
            setDriver(browserType, appURL, driverPath);
            ExtentTestNGIReporterListener.driver = driver;
        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }
    }

    @AfterClass
    public synchronized  void tearDown() {
        driver.quit();
    }
}