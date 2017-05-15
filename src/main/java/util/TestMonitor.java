package util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TestMonitor implements org.testng.ITestListener {

    public Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void onFinish(ITestContext arg0) {
        log.info("Test " + arg0.getName() + " Ends");
        log.info("---------------------------------------------------------");
    }

    @Override
    public void onStart(ITestContext arg0) {
        PropertyConfigurator.configure("res/log4j.properties");
        log.info("Test " + arg0.getName() + " Starts");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        log.info("Test failed within success Percentage");
    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        log.info("Test run has failed");
        log.error(arg0.getThrowable().getMessage());

        StringWriter sw = new StringWriter();
        arg0.getThrowable().printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        log.error(stacktrace.trim());
    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        log.info("Test skipped");
    }

    @Override
    public void onTestStart(ITestResult arg0) {
        log.info("Test Method " + arg0.getMethod().getMethodName()
                + " executing...");
    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        log.info("Test run is successful");
    }

}