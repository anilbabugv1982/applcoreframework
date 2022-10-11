package UtilClasses.UI;

import TestBase.UI.GetDriverInstance;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentEmailReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports reporter;
    private static ExtentHtmlReporter htmlReporter;
    private static ExtentEmailReporter emailableReport;
    public static ExtentTest testReporter;
    public static String currentHost;
    String ScriptExecutionDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    String Seperator = System.getProperty("file.separator");
    //String reportPath = "C:"+Seperator+"Apache24"+Seperator+"htdocs"+Seperator+"AutomationReports"+Seperator+System.getProperty("atgReleaseVersion")+Seperator+"ApplcoreTestResult_"+ScriptExecutionDate+".html";
    String reportPath = "C:" + Seperator + "Apache24" + Seperator + "htdocs" + Seperator + "AutomationReports" + Seperator + GetDriverInstance.atgReleaseVersion + Seperator + "ApplcoreTestResult_" + ScriptExecutionDate + ".html";


    public static ExtentReports getInstance() {
        if (reporter == null)
            createInstance("test-output/extent.html");

        return reporter;
    }

    public static ExtentReports createInstance(String reportPath) {
        htmlReporter = new ExtentHtmlReporter(new File(reportPath));
        reporter = new ExtentReports();
        reporter.attachReporter(htmlReporter);
        reporter.setSystemInfo("User", "Applcore");
        reporter.setSystemInfo("Env", GetDriverInstance.EnvironmentName);
        reporter.setSystemInfo("DB Host", GetDriverInstance.DBHost);
        htmlReporter.config().enableTimeline(false);
        htmlReporter.config().setDocumentTitle("Applcore Sanity Tests");
        htmlReporter.config().setReportName("Applcore Sanity Tests");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        try {
            currentHost = InetAddress.getLocalHost().getHostName();
            System.out.println("RunningHost -> " + currentHost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reporter;
    }
}
