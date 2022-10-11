package TestBase.UI;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class GetDriverInstance {

	public static String browserName;
	public static String weblogicConsole;
	public static String EMName;
	public static String DBHost;
	public static String dbSID;
	public static String dbPort;
	public static String dbUserName;
	public static String DBPWD;
	public static String SIName;
	public static String FndURL = "";
	public static String fsmExpImpFile;
	public static String EnvironmentName;
	public static String Environment2Name;
	public static String executionPlatform;
	public static String atgReleaseVersion;
	public static String approvalsDataFile;
	public static String idcsURL;
	public static String idcsTempPwd;
	static {
		try {
			FileReader propFileRead = new FileReader(
					"./src/main/java/ConfigurationResources/UI/UIConfiguration.properties");
			Properties fileProperties = new Properties();
			if (propFileRead != null) {
				fileProperties.load(propFileRead);

				dbSID = fileProperties.getProperty("sid");
				dbUserName = fileProperties.getProperty("dbUsername");
				fsmExpImpFile = fileProperties.getProperty("fsmziplocation");
				atgReleaseVersion = fileProperties.getProperty("release");
				executionPlatform = fileProperties.getProperty("executionPlatform");
				if (executionPlatform.equalsIgnoreCase("local")) {
					EnvironmentName = fileProperties.getProperty("EnvironmentName");
					EMName = fileProperties.getProperty("EMName");
					DBHost = fileProperties.getProperty("DBHost");
					dbPort = fileProperties.getProperty("port");
					SIName = fileProperties.getProperty("SIName");
					DBPWD = fileProperties.getProperty("DBPWD");
					Environment2Name = fileProperties.getProperty("Environment2Name");
					browserName = fileProperties.getProperty("executableBrowser");
					approvalsDataFile = fileProperties.getProperty("invoiceTestDataLocation");
					idcsURL = fileProperties.getProperty("idcsURL");
					idcsTempPwd = fileProperties.getProperty("idcsTempPwd");
				} else {
					EnvironmentName = System.getProperty("EnvironmentName");
					EMName = System.getProperty("EMName");
					DBHost = System.getProperty("DBHost");
					dbPort = System.getProperty("DBPort");
					SIName = System.getProperty("SIName");
					DBPWD = System.getProperty("DBPWD");
					Environment2Name = System.getProperty("Environment2Name");
					browserName = System.getProperty("ExecutableBrowser");
					approvalsDataFile = System.getProperty("InvoiceInfo");
					Environment2Name = System.getProperty("TargetInstance");
					idcsURL = System.getProperty("idcsURL");
					idcsTempPwd = System.getProperty("idcsTempPwd");
				}
			}
		} catch (Exception rFE) {
			rFE.printStackTrace();
		}
	}

	public WebDriver driverInstanceObject = null;

	/*
	 * Constructor will read parameters from properties file and create instance of
	 * the browser that was provided in the properties file
	 */
	public WebDriver getDriverInstanceObject() throws WebDriverException {

		FirefoxProfile ffprofile;
		FirefoxOptions ffoptions;

		switch (browserName) {
		case "Firefox":
			System.out.println("FireFox driver Instance");
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
			ffprofile = new FirefoxProfile();
			ffprofile.setPreference("browser.download.folderList", 2);
			ffprofile.setPreference("browser.download.manager.showWhenStarting", false);
			ffprofile.setPreference("browser.download.dir", fsmExpImpFile);
			//adding "application/vnd.ms-excel" for saving excel files(.xls) without showing download popup
			ffprofile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/vnd.ms-excel, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/csv, application/ris, text/csv, image/jpeg, image/png, text/xml, charset=utf-8, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
			ffprofile.setPreference("pdfjs.disabled", true); // disable the built-in PDF viewer
			ffprofile.setPreference("browser.helperApps.alwaysAsk.force", false);
			ffprofile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			ffprofile.setPreference("browser.download.manager.focusWhenStarting", false);
			ffprofile.setPreference("browser.download.manager.useWindow", false);
			ffprofile.setPreference("browser.download.manager.showAlertOnComplete", false);
			ffprofile.setPreference("browser.download.manager.closeWhenDone", false);
			ffoptions = new FirefoxOptions();
			ffoptions.setProfile(ffprofile);

			driverInstanceObject = new FirefoxDriver(ffoptions);
			driverInstanceObject.manage().window().maximize();
			break;
		case "Chrome":
			System.out.println("Chrome driver Instance");
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
			ChromeOptions coptions= new ChromeOptions();
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("safebrowsing.enabled", "true");
			chromePrefs.put("download.default_directory", fsmExpImpFile);
			coptions.setExperimentalOption("prefs", chromePrefs);
			coptions.setAcceptInsecureCerts(true);
			coptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			
			driverInstanceObject = ThreadGuard.protect(new ChromeDriver(coptions));
			driverInstanceObject.manage().window().maximize();
			break;
		case "IE":
			driverInstanceObject = new InternetExplorerDriver();
			driverInstanceObject.manage().window().maximize();
			break;
		case "mobile":
			System.out.println("Chrome driver Instance mobile");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("deviceName", "emulator-5544");
			cap.setCapability("platformName", "Android");
			cap.setCapability("platformVersion", "11.0");
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			cap.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("resetKeyboard", true);
			cap.setCapability("chromedriverExecutableDir", "C:\\Users\\SANDDUBE\\Desktop\\Automation\\applcoreqa\\src\\test\\resources");
			URL url = null;
			try {
				url = new URL("http://localhost:4723/wd/hub");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			driverInstanceObject = ThreadGuard.protect(new AndroidDriver<MobileElement>(url, cap));
			System.out.println("entering here");
			break;	
		default:

		}
		
		driverInstanceObject.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driverInstanceObject;
	}

	/*
	 * public static WebDriver getDriverInstance() { if(driverInstanceObject !=
	 * null) return driverInstanceObject; return driverInstanceObject; }
	 * 
	 * public static void getEnvironmentDetails() {
	 * 
	 * try { FileReader propFileRead = new FileReader(
	 * "./src/main/java/ConfigurationResources/UI/UIConfiguration.properties");
	 * Properties fileProperties = new Properties(); if (propFileRead != null) {
	 * fileProperties.load(propFileRead);
	 * 
	 * fuseWelcomePage = fileProperties.getProperty("fuseWelcome"); dbHost =
	 * fileProperties.getProperty("dbHost"); dbSID =
	 * fileProperties.getProperty("sid"); dbPort =
	 * fileProperties.getProperty("port"); dbUserName =
	 * fileProperties.getProperty("dbUsername"); dbPassword =
	 * fileProperties.getProperty("dbPassword"); dbServiceName =
	 * fileProperties.getProperty("dbServiceName"); fsmExpImpFile=
	 * fileProperties.getProperty("fsmziplocation"); browserName =
	 * fileProperties.getProperty("executableBrowser"); }
	 * 
	 * 
	 * }catch(Exception rFE) { rFE.printStackTrace(); } }
	 */
}
