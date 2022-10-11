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
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;

public class ManageAdminstartorProfileValue extends GetDriverInstance{

	private WebDriver adminPrfDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ProfilesUtils pOUtil = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		
		try {
			adminPrfDriver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(adminPrfDriver);
			taskFlowsNavigation = new NavigationTaskFlows(adminPrfDriver);
			pOUtil = new ProfilesUtils(adminPrfDriver);
			js = (JavascriptExecutor) adminPrfDriver;
			docPage = new ManageDocumentSequence(adminPrfDriver);
			appLogin.login(user, passWord, adminPrfDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", adminPrfDriver);
			CommonUtils.hold(4);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, adminPrfDriver);
			}else {
				CommonUtils.hold(3);
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", adminPrfDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}

			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Administrator Profile Values", adminPrfDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(	adminPrfDriver.findElement(By.xpath("//h1[contains(text(),'Manage Administrator Profile Values')]")),	"Click", "", adminPrfDriver);
		} catch (Exception e) {
			System.out.println("failed in BeforeClass in Manage admin profle class :  " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Test(description = "Search AdminProfile Value")
	public void testcase01() throws Exception {
		pOUtil.searchAdminProfile("AFLOG_LEVEL",adminPrfDriver);
		pOUtil.verifyStatus3("AFLOG_LEVEL", "",adminPrfDriver);
	}

	@Test(description = "Create Profile Option Value")
	public void testcase02() throws Exception {
		pOUtil.addProfileOptionValues("AFLOG_LEVEL", "User", "IT_SECURITY_MANAGER", "Severe",adminPrfDriver);
		pOUtil.verifyStatus3("AFLOG_LEVEL", "IT_SECURITY_MANAGER",adminPrfDriver);
	}

	@Test(description = "Delete Profile Option Value", dependsOnMethods = "testcase02")
	public void testcase03() throws Exception {
		pOUtil.deleteAdminProfile("AFLOG_LEVEL", "IT_SECURITY_MANAGER",adminPrfDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		adminPrfDriver.quit();
	}
}
