package UI.tests.ResponsiveFusePlus;

import java.util.concurrent.TimeUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.GuidedLearning;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.GuidedLearningUtil;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.GuidedProcess;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PageTemplateFusePlusUtil;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.ResponsiveFusePlusUtil;

public class GuidedProcessTest extends GetDriverInstance {

	PageTemplateFusePlusUtil pageTempFuseUtil;
	WebDriver guidedProcessDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private ProfilesUtils pOUtil = null;
	private GuidedLearningUtil guidedUtil;
	private GuidedProcess guidedProcess;
	private ResponsiveFusePlusUtil responsiveutil;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void signIn(String username, String password) throws Exception {

		guidedProcessDriver = getDriverInstanceObject();
		responsiveutil = new ResponsiveFusePlusUtil(guidedProcessDriver);
		pageTempFuseUtil=new PageTemplateFusePlusUtil(guidedProcessDriver);
		aLoginInstance = new ApplicationLogin(guidedProcessDriver);
		ntFInstance = new NavigationTaskFlows(guidedProcessDriver);
		nMEInstance = new NavigatorMenuElements(guidedProcessDriver);
		pOUtil = new ProfilesUtils(guidedProcessDriver);
		guidedUtil=new GuidedLearningUtil(guidedProcessDriver);
		guidedProcess=new GuidedProcess(guidedProcessDriver);
		Thread.sleep(1000);
		aLoginInstance.login(username, password,guidedProcessDriver);
		CommonUtils.waitForJStoLoad(guidedProcessDriver);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				guidedProcessDriver);
		ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values", guidedProcessDriver);
		pOUtil.searchAdminProfile("PER_EMPLOYMENT_GUIDED_FLOWS_RESPONSIVE_ENABLED",guidedProcessDriver);
		guidedUtil.setAdminProfileForResponsiveUI("Y", guidedProcessDriver);
		pOUtil.searchAdminProfile("HCM_TASK_CONFIGURATOR_RESPONSIVE_ENABLED",guidedProcessDriver);
		guidedUtil.setAdminProfileForResponsiveUI("Y", guidedProcessDriver);
		guidedProcess.saveCloseBtn.click();
		guidedProcess.iconHome.click();
		CommonUtils.hold(5);
		guidedProcess.iconHome.click();	
		CommonUtils.hold(10);
	}

	@Test(description = "Cancel promote guided process",priority=1)
	public void cancelpromote() throws Exception {
		guidedProcess.iconHome.click();
		CommonUtils.hold(10);
		//guidedProcess.myTeamLink.click();
		responsiveutil.navigateto(guidedProcessDriver, "My Team", "Promote", "yes");
		CommonUtils.hold(10);
		//guidedProcess.promoteLink.click();
		//CommonUtils.hold(10);
		guidedProcess.firstDirectReport.click();
		CommonUtils.hold(10);
		guidedProcess.checkboxDirectReports.click();
		guidedProcess.checkboxCommentsAttachments.click();
		guidedProcess.buttonContinue.click();
		CommonUtils.hold(10);
		guidedProcess.whenWhyContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.whenWhyEdit.isDisplayed();
		guidedProcess.promotionContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.promotionEdit.isDisplayed();
		//guidedProcess.directReportContinueButton.click();
		//CommonUtils.hold(5);
		//guidedProcess.directReportEdit.isDisplayed();
		guidedProcess.buttonCancel.click();
		CommonUtils.hold(10);
		guidedProcess.me.click();
		CommonUtils.hold(10);		
	}

	@Test(description = "Submit promote guided process",priority=2)
	public void submitpromote() throws Exception {
		guidedProcess.iconHome.click();
		CommonUtils.hold(10);
		//guidedProcess.myTeamLink.click();
		responsiveutil.navigateto(guidedProcessDriver, "My Team", "Promote", "yes");
		CommonUtils.hold(10);
		//guidedProcess.promoteLink.click();
		//CommonUtils.hold(10);
		guidedProcess.firstDirectReport.click();
		CommonUtils.hold(10);
		guidedProcess.checkboxDirectReports.click();
		guidedProcess.checkboxCommentsAttachments.click();
		guidedProcess.buttonContinue.click();
		CommonUtils.hold(10);
		guidedProcess.whenWhyContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.whenWhyEdit.isDisplayed();
		guidedProcess.promotionContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.promotionEdit.isDisplayed();
		//guidedProcess.directReportContinueButton.click();
		//CommonUtils.hold(5);
		//guidedProcess.directReportEdit.isDisplayed();
		guidedProcess.buttonSubmit.click();
		CommonUtils.hold(10);
		guidedProcess.me.click();
		CommonUtils.hold(10);		
	}

	@Test(description = "Cancel transfer guided process",priority=3)
	public void canceltransfer() throws Exception {
		guidedProcess.iconHome.click();
		CommonUtils.hold(10);
		//guidedProcess.myTeamLink.click();
		responsiveutil.navigateto(guidedProcessDriver, "My Team", "Transfer", "yes");
		CommonUtils.hold(10);
		//guidedProcess.promoteLink.click();
		//CommonUtils.hold(10);
		guidedProcess.firstDirectReport.click();
		CommonUtils.hold(10);
		guidedProcess.checkboxDirectReports.click();
		guidedProcess.checkboxCommentsAttachments.click();
		guidedProcess.buttonContinue.click();
		CommonUtils.hold(10);
		guidedProcess.whenWhyContinueButtonTransfer.click();
		CommonUtils.hold(5);
		guidedProcess.whenWhyEditTransfer.isDisplayed();
		guidedProcess.transferContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.transferEdit.isDisplayed();
		//guidedProcess.directReportContinueButtonTransfer.click();
		//CommonUtils.hold(5);
		//guidedProcess.directReportEditTransfer.isDisplayed();
		guidedProcess.buttonCancel.click();
		CommonUtils.hold(10);
		guidedProcess.me.click();
		CommonUtils.hold(10);		
	}

	@Test(description = "Submit transfer guided process",priority=4)
	public void submittransfer() throws Exception {
		guidedProcess.iconHome.click();
		CommonUtils.hold(10);
		//guidedProcess.myTeamLink.click();
		responsiveutil.navigateto(guidedProcessDriver, "My Team", "Transfer", "yes");
		CommonUtils.hold(10);
		//guidedProcess.promoteLink.click();
		//CommonUtils.hold(10);
		guidedProcess.firstDirectReport.click();
		CommonUtils.hold(10);
		guidedProcess.checkboxDirectReports.click();
		guidedProcess.checkboxCommentsAttachments.click();
		guidedProcess.buttonContinue.click();
		CommonUtils.hold(10);
		guidedProcess.whenWhyContinueButtonTransfer.click();
		CommonUtils.hold(5);
		guidedProcess.whenWhyEditTransfer.isDisplayed();
		guidedProcess.transferContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.transferEdit.isDisplayed();
		//guidedProcess.directReportContinueButtonTransfer.click();
		//CommonUtils.hold(5);
		//guidedProcess.directReportEditTransfer.isDisplayed();
		guidedProcess.buttonSubmit.click();
		CommonUtils.hold(10);
		guidedProcess.me.click();
		CommonUtils.hold(10);		
	}

	@Test(description = "Cancel termination guided process",priority=5)
	public void canceltermination() throws Exception {
		guidedProcess.iconHome.click();
		CommonUtils.hold(10);
		guidedProcess.myTeamShowMoreLink.click();
		CommonUtils.hold(10);
		guidedProcess.terminationLink.click();
		CommonUtils.hold(10);
		guidedProcess.firstDirectReport.click();
		CommonUtils.hold(10);
		guidedProcess.whenWhyContinueButtonTermination.click();
		CommonUtils.hold(5);
		guidedProcess.whenWhyEditTermination.isDisplayed();
		guidedProcess.terminationContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.terminationEdit.isDisplayed();
		guidedProcess.buttonCancelTermination.click();
		CommonUtils.hold(10);
		guidedProcess.me.click();
		CommonUtils.hold(10);		
	}

	@Test(description = "Submit termination guided process",priority=6)
	public void submittermination() throws Exception {
		guidedProcess.iconHome.click();
		CommonUtils.hold(10);
		guidedProcess.myTeamShowMoreLink.click();
		CommonUtils.hold(10);
		guidedProcess.terminationLink.click();
		CommonUtils.hold(10);
		guidedProcess.firstDirectReport.click();
		CommonUtils.hold(10);
		guidedProcess.whenWhyContinueButtonTermination.click();
		CommonUtils.hold(5);
		guidedProcess.whenWhyEditTermination.isDisplayed();
		guidedProcess.terminationContinueButton.click();
		CommonUtils.hold(5);
		guidedProcess.terminationEdit.isDisplayed();
		guidedProcess.buttonSubmitTermination.isDisplayed();
		guidedProcess.buttonCancelTermination.click();
		CommonUtils.hold(10);
		aLoginInstance.logout(guidedProcessDriver);
		CommonUtils.hold(5);
		guidedProcessDriver.quit();
	}

}
