package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExtentTestNGIReporterListener implements  ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance("test-output/extent.html");
    private static ThreadLocal test = new ThreadLocal();
    public static WebDriver driver;

    @Override
    public synchronized void onStart(ITestContext context) {
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        ((ExtentTest)test.get()).pass("Test passed");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        ((ExtentTest)test.get()).fail(result.getThrowable());
        File directory = new File("test-output");
        try {
            String screenPath = directory.getCanonicalPath() + "\\";
            File file = new File(screenPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String fileName = screenPath + result.getMethod().getMethodName() + ".png";
            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(fileName));
            ((ExtentTest)test.get()).addScreenCaptureFromPath(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        ((ExtentTest)test.get()).skip(result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}