package UtilClasses.UI;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Field;
import java.net.InetAddress;

public class Screenshot extends TestListenerAdapter {

	String ScriptExecutionDate = new SimpleDateFormat("dd-MM-yy").format(new Date());
	String Seperator = System.getProperty("file.separator");
	String currentHost = "";

	@Override
	public void onConfigurationFailure(ITestResult result) {
		attacheScreenshotToTestReport(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		attacheScreenshotToTestReport(result);
	}
	
    public void attacheScreenshotToTestReport(ITestResult result){
	    	Class<?> testClassName = result.getTestClass().getRealClass();
			WebDriver screenShotDriver = null;

			try {
				currentHost = InetAddress.getLocalHost().getHostName();
				System.out.println("Capturing screen shot on host - > " + currentHost);
				
				Field[] testClassFields = testClassName.getDeclaredFields();
				for (Field field : testClassFields) {
					Field driverField = field;
					if (driverField.toGenericString().toLowerCase().contains("driver")) {
						driverField.setAccessible(true);
						screenShotDriver = (WebDriver) driverField.get(result.getInstance());
						break;
					}
				}
			} catch (Exception ex) {
				System.out.println("Issue while processing method screenshot() :" + ex.getMessage());
				ex.printStackTrace();
			}

			Reporter.setCurrentTestResult(result);
			String sTestClassName = result.getTestClass().getRealClass().getSimpleName();
			String sFileName = result.getName();
			
			System.out.println("Test class name - >" + sTestClassName+" and "+"Test method name - >" + sFileName);	
			
			String imagePathInHost = "C:" + Seperator + "Apache24" + Seperator + "htdocs" + Seperator + "screenshots"
					+ Seperator + ScriptExecutionDate + "TestResult" + Seperator + sTestClassName + Seperator
					+ captureScreenshot(screenShotDriver, sTestClassName, sFileName);
			
			System.out.println("Captured image path in host is :  " + imagePathInHost);

			String imgLinkPath = ScriptExecutionDate + "TestResult" + Seperator + sTestClassName + Seperator + sFileName+ ".png";
			
			Reporter.log("<a href=" + "http://" + currentHost + ".us.oracle.com:8082" + Seperator + "screenshots"
					+ Seperator + imgLinkPath + "> <img width='100' height='100' src=" + "http://" + currentHost
					+ ".us.oracle.com:8082" + Seperator + "screenshots" + Seperator + imgLinkPath + "> </a>");
			Reporter.setCurrentTestResult(null);
	    }


	public String captureScreenshot(WebDriver driver, String sTestClassName, String sFileName) {
		String resultSetName = ScriptExecutionDate + "TestResult";
		sFileName = sFileName + ".png";
		try {
			File file = new File("C:" + Seperator + "Apache24" + Seperator + "htdocs" + Seperator + "screenshots"+ Seperator + resultSetName);
			if (!file.exists()) {
				System.out.println("File created somewhere" + file);
				file.mkdir();
			}

			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File("C:" + Seperator + "Apache24" + Seperator + "htdocs" + Seperator + "screenshots"
					+ Seperator + ScriptExecutionDate + "TestResult" + Seperator + sTestClassName, sFileName);
			if (targetFile.exists()) {
				FileUtils.forceDelete(targetFile);
				FileUtils.copyFile(sourceFile, targetFile);
			} else
				FileUtils.copyFile(sourceFile, targetFile);

			return sFileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
