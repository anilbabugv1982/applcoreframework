package UI.tests.AOL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ISOUtil;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;

public class ManageLanguages extends GetDriverInstance {;
	private WebDriver langDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ISOUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			langDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(langDriver);
			taskFlowsNavigation = new NavigationTaskFlows(langDriver);
			isoUtils = new ISOUtil(langDriver);
			js = (JavascriptExecutor) langDriver;
			docPage = new ManageDocumentSequence(langDriver);
			appLogin.login(user, passWord, langDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", langDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, langDriver);
			}else {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(3);
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", langDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Languages", langDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(langDriver.findElement(By.xpath("//h1[contains(text(),'Manage Languages')]")),"Click", "",langDriver);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageISOLanguage class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Search Language")
	public void testcase01() throws Exception {
		isoUtils.searchLanguage("Arabic",langDriver);
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Update Language")
	public void testcase02() throws Exception {
		isoUtils.updateLanguage("Arabic", "UPDATE",langDriver);
		isoUtils.updateLanguage("UPDATE", "Arabic",langDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		langDriver.quit();
	}
}
