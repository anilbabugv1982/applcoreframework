package UI.tests.UnifiedSandbox;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.ComposerWrapperfns;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.PageTemplate;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;

public class SandboxUIFlows extends GetDriverInstance {
	static String uniqueID;
	WebDriver pcInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private PageTemplate ptInstance;
	private ComposerWrapperfns cwfInstance;
	private GlobalPageTemplate gptInstance;
	private CreateSandboxUI cSBInstance;
	
//***********************************************************************************************************************************************************
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
		System.out.println("signing to the application with 'application_administrator' user");
		try {
			pcInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(pcInstance);
			ntFInstance = new NavigationTaskFlows(pcInstance);
			nMEInstance = new NavigatorMenuElements(pcInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(pcInstance);
			sbBannerUIInstance = new SandboxBannerUI(pcInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(pcInstance);
			sbUiInstance = new SandboxUI(pcInstance);
			cSBInstance = new CreateSandboxUI(pcInstance);
			ptInstance = new PageTemplate(pcInstance);
			cwfInstance = new ComposerWrapperfns(pcInstance);
			gptInstance= new GlobalPageTemplate(pcInstance);
			
			/*if (uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetreiveUsbProfileOption).equalsIgnoreCase("N") && uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
				 throw new SkipException("Skipping test because environment not in USB mode");
			  }
			 System.out.println("USB mode check completed");*/
			
			aLoginInstance.login("application_administrator", "Welcome1",pcInstance);
			CommonUtils.waitForPageLoad(pcInstance);
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Navigating to Sandboxes UI Begins");
			/*try {
				gptInstance.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				sbBannerUIInstance.SandboxBanner.click();
			}*/
			CommonUtils.hold(5);
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,pcInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",pcInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()
//***********************************************************************************************************************************************************	
	@Test(description="This testcase is for validating HelpIcon in SandboxDetails Page",priority=1)
	public void ValidateSandboxHelpIconinSBDetails() {
		sbUiInstance.clickOnSandboxHelpIcon(pcInstance);
	}
	
//***********************************************************************************************************************************************************

	@Test (description = "method will create sandbox with PageComposer repository ", priority = 2)
	public void createSandbox() {
		try {
			 cSBInstance.CreateSandbox("PageComposer", "Sandbox with PageComposer repository", "Page Composer", "Activate","YES",pcInstance);
			 
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of createSandbox()
	//***********************************************************************************************************************************************************
	@Test(description="This testcase is for validating HelpIcon in SandboxDetails Page",priority=3)
	public void ValidateSandboxHelpIconinSBDetailsForCreateSandbox() {
		sbUiInstance.clickOnSandboxHelpIcon(pcInstance);
	}

}
