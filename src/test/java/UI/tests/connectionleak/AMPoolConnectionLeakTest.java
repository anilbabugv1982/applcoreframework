package UI.tests.connectionleak;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.ConnectionLeakPage;

public class AMPoolConnectionLeakTest {
//    private String dmsSpyUrl = "http://slc11pzr.us.oracle.com:11401/dms/loginForm.jsp"; //Enable this and give fahost url while running locally.
//    private String fuseWelcomeUrl = "https://sam42911.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
	private List<ConnectionLeakPage> connectionLeaks = null;
	private ConnectionLeakPage connectionLeakPage = null;
	public WebDriver driverObject = null;
	public WebDriverWait wait;

	private String dmsSpyUrl = "http://" + System.getProperty("FAHost") + ":11401/dms/loginForm.jsp";
	private String fuseWelcomeUrl = System.getProperty("EnvironmentName");
	private String atgPatch = null;
	private String adfPatch = null;

	@Test(priority = 1)
	public void getLabelInfo() throws Exception {
		driverObject.get(fuseWelcomeUrl);
		WebElement username = driverObject.findElement(By.id("userid"));
		WebElement password = driverObject.findElement(By.id("password"));
		WebElement submit = driverObject.findElement(By.id("btnActive"));
		wait.until(ExpectedConditions.visibilityOf(username));
		wait.until(ExpectedConditions.visibilityOf(password));
		wait.until(ExpectedConditions.elementToBeClickable(submit));
		Thread.sleep(4000);
		username.sendKeys("app_impl_consultant");
		password.sendKeys("Welcome1");
		submit.click();
		WebElement navigatorButton = driverObject.findElement(By.xpath("//a[contains(@id,'pt1:_UIScmil3u')]"));
		wait.until(ExpectedConditions.elementToBeClickable(navigatorButton));
		Thread.sleep(5000);
		navigatorButton.click();

//        WebElement userDropDown = driverObject.findElement(By.xpath("//*[contains(@id,'UIScmil1u::icon')]"));
//        wait.until(ExpectedConditions.elementToBeClickable(userDropDown));

		WebElement aboutApplication = driverObject.findElement(By.xpath("//a[@id='pt1:_UIScmi99']"));
		wait.until(ExpectedConditions.elementToBeClickable(aboutApplication));
		Thread.sleep(3000);
		aboutApplication.click();
		Thread.sleep(8000);
		adfPatch = driverObject.findElement(By.xpath("//span[@id='pt1:_UISatpr4:0:i1:0:ott22']")).getText();
		atgPatch = driverObject.findElement(By.xpath("//span[@id='pt1:_UISatpr4:0:i1:1:ott22']")).getText();
	}

	@Test(priority = 2)
	public void getConnectionLeaks() throws Exception {
		driverObject.get(dmsSpyUrl);
		Thread.sleep(5000);
		logonToDmsSpy();
		openAmPoolConnectionLeaks();
		getAllConnectionLeaks();
		printConnectionLeaks();
		generateHtmlReport();
	}

	@BeforeTest
	public void beforeTest() throws Exception {
		driverObject.manage().window().maximize();
		driverObject.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wait = new WebDriverWait(driverObject, 60);
		// driverObject.get(System.getProperty("faHost")); // Disable this while running
		// locally.

	}

	@BeforeSuite
	public void beforeSuite() {
		System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
		driverObject = new FirefoxDriver();
	}

	@AfterSuite
	public void afterSuite() {
		driverObject.quit();
	}

	public void logonToDmsSpy() throws Exception {
		driverObject.findElement(By.id("j_username")).sendKeys("faadmin");
		driverObject.findElement(By.id("j_password")).sendKeys("Fusionapps1");
		driverObject.findElement(By.xpath("//input[@type=\"submit\"]")).click();
		Thread.sleep(5000);
	}

	public void openAmPoolConnectionLeaks() throws Exception {
		driverObject.switchTo().frame("metricTableFrame");
		wait.until(ExpectedConditions.elementToBeClickable(driverObject.findElement(By.xpath("//a[text()='ampool']"))));
		driverObject.findElement(By.xpath("//a[text()='ampool']")).click();
		Thread.sleep(5000);
	}

	public void getAllConnectionLeaks() {
		connectionLeaks = new ArrayList<ConnectionLeakPage>();
		connectionLeaks.clear();
		List<WebElement> allRows = driverObject.findElements(
				By.xpath("//tr[@class='lightRow' or @class='darkRow']/descendant::td[12]/ancestor::tr[1]"));
		System.out.println("All Rows" + allRows.size());
		int index = 0;
		for (WebElement row : allRows) {
			System.out.println("Row Number:" + index++);
			System.out.println(
					"=======================================================================================================");
			List<WebElement> columns = row.findElements(By.tagName("td"));
			columns.stream().forEach(data -> System.out.print(data.getText()));
			System.out.println(" ");
			String amName = columns.get(0).getText();
			String hostName = columns.get(1).getText();
			String processName = columns.get(2).getText();
			int checkedInCount = Integer.parseInt(columns.get(9).getText());
			int checkedOutCount = Integer.parseInt(columns.get(10).getText());
			int failedCount = Integer.parseInt(columns.get(11).getText());
			connectionLeakPage = new ConnectionLeakPage(amName, hostName, processName, checkedInCount, checkedOutCount,
					failedCount);

			if ((checkedOutCount - checkedInCount) > 1 || failedCount > 0) {
				connectionLeaks.add(connectionLeakPage);
			}

		}
	}

	public void printConnectionLeaks() {
		for (ConnectionLeakPage connectionLeak : connectionLeaks) {
			System.out.println(connectionLeak);
		}
	}

	public void generateHtmlReport() throws IOException, URISyntaxException {
		File input = new File(System.getProperty("user.dir") + "/src/test/resources/template.html");
		Document document = Jsoup.parse(input, "UTF-8");

		// Adding Header Elements
		Element element_ATGPFLabel = document.getElementById("atgPatch");
		Element element_dmsURL = document.getElementById("dmsSpyUrl");
		Element element_fuseWelcome = document.getElementById("fuseWelcome");

		element_ATGPFLabel.append("<td>" + atgPatch + "</td>");
		element_dmsURL.append("<td>" + "<a href=" + dmsSpyUrl + ">" + dmsSpyUrl + "</a>" + "</td>");
		element_fuseWelcome.append("<td>" + "<a href=" + fuseWelcomeUrl + ">" + fuseWelcomeUrl + "</a>" + "</td>");

//        document.body().getElementById("atgPatch").append("<div>ATG Patch		:	"+this.atgPatch+"</div>");
//
//        document.body().getElementById("dmsSpyUrl").append("<div>DMSSpy		:	" + "<a href=\"+dmsSpyUrl+\">" + dmsSpyUrl + "</a><div>");
//        document.body().getElementById("fuseWelcome").append("<div>FuseWelcome		:	" + "<a href=\"+fuseWelcomeUrl+\">" + fuseWelcomeUrl + "</a></div>");
		if (connectionLeaks.size() == 0) {
			document.body().getElementById("connectionLeaks").remove();
			document.body().append("<div>No Connection Leaks found</div>");
		} else {
			Element element = document.body().getElementById("connectionLeaks");
			for (ConnectionLeakPage connectionLeak : connectionLeaks) {
				element.append("     <tr> \n" + "      <td>" + connectionLeak.getAmName() + "</td> \n" + "      <td>"
						+ connectionLeak.getCheckinCount() + "</td> \n" + "      <td>"
						+ connectionLeak.getCheckoutCount() + "</td> \n" + "      <td>"
						+ connectionLeak.getFailedCount() + "</td>\n" + "      <td>" + connectionLeak.getHostName()
						+ "</td> \n" + "      <td>" + connectionLeak.getServer() + "</td>  \n" + "     </tr> ");
			}
		}
		OutputStream outputStream = new FileOutputStream(new File("connection-leak.html"));
		Writer writer = new OutputStreamWriter(outputStream);
		writer.write(document.toString());
		writer.close();
	}

}
