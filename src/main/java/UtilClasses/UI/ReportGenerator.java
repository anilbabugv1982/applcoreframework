package UtilClasses.UI;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import TestBase.UI.GetDriverInstance;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentEmailReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.IClassListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.IResultListener;


public class ReportGenerator implements IClassListener, ISuiteListener, IResultListener {

    private ExtentReports reporter;
    private ExtentHtmlReporter htmlReporter;
    private ExtentEmailReporter emailableReport;
//    public static ExtentTest testReporter;
    public static String currentHost;
    String ScriptExecutionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    String Seperator = System.getProperty("file.separator");
    //String reportPath = "C:"+Seperator+"Apache24"+Seperator+"htdocs"+Seperator+"AutomationReports"+Seperator+System.getProperty("atgReleaseVersion")+Seperator+"ApplcoreTestResult_"+ScriptExecutionDate+".html";
    String reportPath = "C:" + Seperator + "Apache24" + Seperator + "htdocs" + Seperator + "AutomationReports" + Seperator + GetDriverInstance.atgReleaseVersion + Seperator + "ApplcoreTestResult_" + ScriptExecutionDate + ".html";
    private ThreadLocal<ExtentTest> testReporter = new ThreadLocal();


    @Override
    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext result) {
        // TODO Auto-generated method stub
        //testReporter = reporter.createTest(result.getCurrentXmlTest().getClasses().GET).info("Test Started");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        try {
            testReporter.get().createNode(result.getMethod().getDescription()).fail(result.getThrowable());
        } catch (Exception e) {
            testReporter.get().createNode(result.getMethod().getMethodName()).fail(result.getThrowable());
            System.out.println(" Please add description to your test case :" + e.getMessage());
        }
        //    testReporter.assignCategory(result.getTestClass().getXmlTest().getName());
        reporter.flush();

    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        try {
            testReporter.get().createNode(result.getMethod().getDescription()).skip(result.getThrowable());
        } catch (Exception e) {
            testReporter.get().createNode(result.getMethod().getMethodName()).skip(result.getThrowable());
            System.out.println(" Please add description to your test case :" + e.getMessage());
        }
        //    testReporter.assignCategory(result.getTestClass().getXmlTest().getName());
        reporter.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        //testReporter.info("Test Started");
//        testReporter.assignCategory(result.getTestClass().getXmlTest().getName()).info("Started");
//        reporter.flush();

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        try {
            testReporter.get().createNode(result.getMethod().getDescription()).pass("PASSED");
        } catch (Exception e) {
            testReporter.get().createNode(result.getMethod().getMethodName()).pass("PASSED");
            System.out.println(" Please add description to your test case :" + e.getMessage());
        }
        //    testReporter.assignCategory(result.getTestClass().getXmlTest().getName());
        reporter.flush();
    }

    @Override
    public void onFinish(ISuite arg0) {


    }

    @Override
    public synchronized void onStart(ISuite arg0) {

        reporter = ExtentManager.createInstance(this.reportPath);
//        htmlReporter = new ExtentHtmlReporter(new File(reportPath));
//        reporter = new ExtentReports();
//        reporter.attachReporter(htmlReporter);
//        reporter.setSystemInfo("User", "Applcore");
//        reporter.setSystemInfo("Env", GetDriverInstance.EnvironmentName);
//        reporter.setSystemInfo("DB Host", GetDriverInstance.DBHost);
//        htmlReporter.config().enableTimeline(false);
//        htmlReporter.config().setDocumentTitle("Applcore Sanity Tests");
//        htmlReporter.config().setReportName("Applcore Sanity Tests");
//        htmlReporter.config().setTheme(Theme.DARK);
//        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
//        try {
//            currentHost = InetAddress.getLocalHost().getHostName();
//            System.out.println("RunningHost -> " + currentHost);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    public synchronized void reportLog(String message) {
       // ReportGenerator rt = new ReportGenerator();
       // rt.testReporter.get().log(Status.INFO,message);
    	testReporter.get().log(Status.INFO,message);
        Log.Log.info("Message: " + message);
        Reporter.log(message);
    }

    @Override
    public synchronized void onConfigurationFailure(ITestResult result) {
        testReporter.get().createNode(result.getMethod().getQualifiedName()).fail(result.getThrowable());
        reporter.flush();

    }

    @Override
    public synchronized void onConfigurationSkip(ITestResult result) {
        //    testReporter = reporter.createTest(result.getMethod().getMethodName());
        testReporter.get().createNode(result.getMethod().getQualifiedName()).skip(result.getThrowable());
        //    testReporter.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + "SKIPPED", ExtentColor.ORANGE));
        //    testReporter.assignCategory(result.getTestClass().getXmlTest().getName());
        reporter.flush();
    }

    @Override
    public synchronized void onConfigurationSuccess(ITestResult result) {
        //testReporter = reporter.createTest(result.getTestClass().getName());
        testReporter.get().createNode(result.getMethod().getMethodName()).pass("PASSED");
        //    testReporter.log(Status.PASS, MarkupHelper.createLabel(result.getName() + "PASSED", ExtentColor.GREEN));
        //    testReporter.assignCategory(result.getTestClass().getXmlTest().getName());
        reporter.flush();
    }

    @Override
    public synchronized void onAfterClass(ITestClass arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void onBeforeClass(ITestClass arg0) {
        System.out.println("========================================");
        System.out.println("Class:" + arg0.getXmlClass().getName());
        System.out.println("========================================");
//
//        testReporter = reporter.createTest(arg0.getXmlClass().getSupportClass().getSimpleName());
//        testReporter.assignCategory(arg0.getXmlTest().getName());



        ExtentTest test = reporter.createTest(arg0.getXmlClass().getSupportClass().getSimpleName());
        test.assignCategory(arg0.getXmlTest().getName());
        testReporter.set(test);
    }


}
