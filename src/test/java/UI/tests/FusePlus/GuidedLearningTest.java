package UI.tests.FusePlus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.GuidedLearning;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.GuidedLearningUtil;

import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PageTemplateFusePlusUtil;

public class GuidedLearningTest extends GetDriverInstance {
	

	PageTemplateFusePlusUtil pageTempFuseUtil;
	WebDriver guidedLearningDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private ProfilesUtils pOUtil = null;
	private GuidedLearningUtil guidedUtil;
	private GuidedLearning guidedLearning;
	int var;
	String SbName;
	String appId;
	String roleInApplication;
	String roleInGL;
	String env;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void signIn(String username, String password) throws Exception {
			
			guidedLearningDriver = getDriverInstanceObject();
			pageTempFuseUtil=new PageTemplateFusePlusUtil(guidedLearningDriver);
			aLoginInstance = new ApplicationLogin(guidedLearningDriver);
			ntFInstance = new NavigationTaskFlows(guidedLearningDriver);
			nMEInstance = new NavigatorMenuElements(guidedLearningDriver);
			pOUtil = new ProfilesUtils(guidedLearningDriver);
			guidedUtil=new GuidedLearningUtil(guidedLearningDriver);
			guidedLearning=new GuidedLearning(guidedLearningDriver);
			Thread.sleep(1000);
			aLoginInstance.login(username, password,guidedLearningDriver);
			ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
					guidedLearningDriver);
			ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values", guidedLearningDriver);
			pOUtil.searchAdminProfile("HCM_RESPONSIVE_PAGES_ENABLED",guidedLearningDriver);
			guidedUtil.setAdminProfileForResponsiveUI("Y", guidedLearningDriver);
			pOUtil.searchAdminProfile("PER_PERSONAL_INFORMATION_RESPONSIVE_ENABLED",guidedLearningDriver);
			guidedUtil.setAdminProfileForResponsiveUI("Y", guidedLearningDriver);
			
			pOUtil.searchAdminProfile("FND_GUIDED_LEARNING_ENABLED",guidedLearningDriver);
			guidedUtil.setAdminProfile("Yes", guidedLearningDriver);	
			
		}
	
//	@Test(description = "Search and set AdminProfile Value" ,priority=1)
//	public void testcase01() throws Exception {
//	
//	}
	
	@Test(description = "Verify Error Message on Guided learning Page",priority=2)
	public void testcase02() throws Exception {
		GuidedLearning.home.click();
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				guidedLearningDriver);
		ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning", guidedLearningDriver);
		Thread.sleep(2000);
		assertTrue(guidedUtil.verifyErrorMsg(),"Error message is not present on configure Guided page");
	
	}
	@Parameters({ "user" })
	@Test(description = "Create Sandbox" , priority=3)
	public void testcase03(String user) throws Exception {
		GuidedLearning.home.click();
		var=(int)Math.floor(Math.random()*(999-100+1)+100);
		SbName="OGL"+var;
		ntFInstance.navigateToTask(guidedLearning.sandBox, guidedLearningDriver); 
		guidedUtil.DeleteGuidedLearningSandbox(guidedLearningDriver);
		guidedUtil.createGuidedLearningSandbox(guidedLearningDriver,SbName);	
		guidedUtil.verifySandBox(guidedLearningDriver,SbName,user);
	
	}
	
		
	@Test(description = "verify Guided widget" ,priority=4,dependsOnMethods="testcase03" )   
	public void testcase04() throws Exception {
	
		String []links= {"Update Phone Number (Employee)","Update Address (Employee)","View Compensation Information"};
		 appId="rMLeyBCcQoyJih9iOdHnwQ";
		 boolean islinkpresent;
		env="dev";GuidedLearning.home.click();
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				guidedLearningDriver);
		Thread.sleep(5000);
	ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning", guidedLearningDriver);
		Thread.sleep(2000);
		guidedUtil.ConfigureGuidedLearningwithAPPID(guidedLearningDriver,appId,env);
		guidedUtil.saveandCloseNavigateToHome();
		guidedUtil.verifyWidgetOnHomeandPersonalInfoPage(GuidedLearning.glWidgets,guidedLearningDriver);
		String hexcolorCode =GuidedLearning.glWidgets2.getCssValue("background-color");
		String buttonColorhex1 = Color.fromString(hexcolorCode).asHex();
		assertTrue(buttonColorhex1.equalsIgnoreCase("#006BA6"),"widget is not in royal blue color");
		String leftOrRight=GuidedLearning.glWidgets3.getAttribute("class");
		System.out.println(leftOrRight);
		assertTrue(leftOrRight.contains("left"),"widget is not at left side");
		guidedUtil.verifyLinksOnWidget(GuidedLearning.glWidgets,guidedLearningDriver,links);
		WebElement sandboxDropDown=guidedLearningDriver.findElement(By.xpath("//div[contains(@aria-label,'"+var+"')]"));
		CommonUtils.explicitWait(sandboxDropDown, "Click", "",guidedLearningDriver);
		sandboxDropDown.click();
		GuidedLearning.leaveSandbox.click();
		CommonUtils.explicitWait(GuidedLearning.ok,"Click", "",guidedLearningDriver);
		GuidedLearning.ok.click();
		CommonUtils.hold(3);
		try
		{
			 islinkpresent=GuidedLearning.glWidgets.isEnabled();
		}
		catch(Exception e)
		{
			islinkpresent=false;
			
		}
		assertFalse(islinkpresent,"widget is present");
	//	guidedUtil.verifyUpdatePhoneLink(guidedLearningDriver);
//		guidedUtil.verifyUpdateAddessLink(guidedLearningDriver);
	//	guidedUtil.verifyViewCompensationInfo(guidedLearningDriver);
		
	}
	@Parameters({ "user" })
	@Test(description = "verify Guided widget" ,priority=5 )   
	public void testcase05(String user) throws Exception {
		GuidedLearning.home.click();
		var=(int)Math.floor(Math.random()*(999-100+1)+100);
		SbName="OGL"+var;
		ntFInstance.navigateToTask(guidedLearning.sandBox, guidedLearningDriver); 
		guidedUtil.DeleteGuidedLearningSandbox(guidedLearningDriver);
		guidedUtil.createGuidedLearningSandbox(guidedLearningDriver,SbName);	
		guidedUtil.verifySandBox(guidedLearningDriver,SbName,user);
		
		String []links= {"Update Phone Number (Employee)","Update Address (Employee)","View Compensation Information"};
		 appId="ALcZRzWyTS+LMYwwCrHzFQ";
		env="Prod";
		
		  ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().
		  setupandmaintenance, guidedLearningDriver); Thread.sleep(5000);
		  ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning",
		  guidedLearningDriver); Thread.sleep(2000);
		  guidedUtil.ConfigureGuidedLearningwithAPPID(guidedLearningDriver,appId,env);
		  guidedUtil.saveandCloseNavigateToHome();
		  guidedUtil.verifyWidgetOnHomeandPersonalInfoPage(GuidedLearning.HelpglWidgets,guidedLearningDriver);
		  guidedUtil.verifyLinksOnWidget(GuidedLearning.HelpglWidgets,guidedLearningDriver,links);
		// guidedUtil.verifyViewCompensationInfo(guidedLearningDriver);
		 
	}
	
	@Test(description = "verify Guided widget with app role" ,priority=6,dependsOnMethods="testcase05" )   
	public void testcase06() throws Exception {
		GuidedLearning.home.click();
		 ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().
				  setupandmaintenance, guidedLearningDriver); Thread.sleep(5000);
					ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning", guidedLearningDriver);
				Thread.sleep(2000);
				guidedUtil.addRoleForGuidedLearningPage(guidedLearningDriver,"ORA_PER_LINE_MANAGER_ABSTRACT","Line Manager");
				guidedUtil.saveandCloseNavigateToHome();
				guidedUtil.verifyWidgetOnHomeandPersonalInfoPage(GuidedLearning.HelpglWidgets,guidedLearningDriver);
				String []links2= {"Update Phone Number (Employee)","Update Address (Employee)","View a Team Member's Documents","Review Employment Information for My Team","View Compensation Information"};
				guidedUtil.verifyLinksOnWidget(GuidedLearning.HelpglWidgets,guidedLearningDriver,links2);
			//	 guidedUtil.verifyTeamMemberDocument(guidedLearningDriver);
	
	}
	
	@Test(description = "verify Guided widget with app role" ,priority=7,dependsOnMethods="testcase05" )   
	public void testcase07() throws Exception {
		GuidedLearning.home.click();
		boolean islinkpresent=true;
		 ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().
				  setupandmaintenance, guidedLearningDriver); Thread.sleep(5000);
					ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning", guidedLearningDriver);
				Thread.sleep(2000);
				guidedUtil.addRoleForGuidedLearningPage(guidedLearningDriver,"ORA_PER_EMPLOYEE_ABSTRACT","Employee");
				guidedUtil.saveandCloseNavigateToHome();
				guidedUtil.verifyWidgetOnHomeandPersonalInfoPage(GuidedLearning.HelpglWidgets,guidedLearningDriver);
				String []links2= {"Update Phone Number (Employee)","Update Address (Employee)","View Compensation Information"};
				guidedUtil.verifyLinksOnWidget(GuidedLearning.HelpglWidgets,guidedLearningDriver,links2);
			//	 guidedUtil.verifyUpdateAddessLink(guidedLearningDriver);
				 ntFInstance.navigateToTask(GuidedLearning.personalInfo, guidedLearningDriver); 
				 CommonUtils.hold(40);
					CommonUtils.explicitWait(GuidedLearning.contactInformation, "Click", "", guidedLearningDriver);
					GuidedLearning.contactInformation.click();
				CommonUtils.hold(15);
				CommonUtils.explicitWait(GuidedLearning.contactInfoPage, "Click", "", guidedLearningDriver);
				assertTrue(GuidedLearning.contactInfoPage.isDisplayed(),"Not landed to contact info page");
				GuidedLearning.HelpglWidgets.click();
				try
				{
					islinkpresent=guidedLearningDriver.findElement(By.xpath("//span[contains(text(),'View Compensation Information')]")).isEnabled();
				}
				catch(Exception e)
				{
					islinkpresent=false;
					
				}
				//GuidedLearning.homeIcon3.click();	
				GuidedLearning.home.click();
				CommonUtils.hold(10);
				assertFalse(islinkpresent,"Link is present");
				
	}
	
	@Test(description = "verify Guided widget" ,priority=8 ,dependsOnMethods="testcase05")   
	public void testcase08() throws Exception {
		GuidedLearning.home.click();
		boolean islinkpresent=true;
		String []links= {"Update Phone Number (Employee)","Update Address (Employee)","View Compensation Information"};
		 appId="SMUY+rpiS_WKLEQEb58gEw";
		env="Dev";
		GuidedLearning.home.click();
		  ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().
		  setupandmaintenance, guidedLearningDriver); Thread.sleep(5000);
		  ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning",guidedLearningDriver); Thread.sleep(2000);
		  guidedUtil.ConfigureGuidedLearningwithAPPID(guidedLearningDriver,appId,env);
		  guidedUtil.saveandCloseNavigateToHome();
		  guidedUtil.verifyWidgetOnHomeandPersonalInfoPage(GuidedLearning.glWidgets,guidedLearningDriver);
		  guidedUtil.verifyLinksOnWidget(GuidedLearning.glWidgets,guidedLearningDriver,links);
		// guidedUtil.verifyViewCompensationInfo(guidedLearningDriver);
		  ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().
				  setupandmaintenance, guidedLearningDriver); Thread.sleep(5000);
					ntFInstance.navigateToAOLTaskFlows("Configure Guided Learning", guidedLearningDriver);
				Thread.sleep(2000);
				guidedUtil.addRoleForGuidedLearningPage(guidedLearningDriver,"ORA_PER_LINE_MANAGER_ABSTRACT","Line Manager");
				guidedUtil.saveandCloseNavigateToHome();
				guidedUtil.verifyWidgetOnHomeandPersonalInfoPage(GuidedLearning.glWidgets,guidedLearningDriver);
				String []links2= {"Update Phone Number (Employee)","Update Address (Employee)","View a Team Member's Documents","Review Employment Information for My Team","View Compensation Information"};
				guidedUtil.verifyLinksOnWidget(GuidedLearning.glWidgets,guidedLearningDriver,links2);
				WebElement sandboxDropDown=guidedLearningDriver.findElement(By.xpath("//div[contains(@aria-label,'"+var+"')]"));
				CommonUtils.explicitWait(sandboxDropDown, "Click", "",guidedLearningDriver);
				sandboxDropDown.click();
				CommonUtils.hold(5);
				GuidedLearning.publish.click();
				
				CommonUtils.explicitWait(GuidedLearning.ok2,"Click", "",guidedLearningDriver);
				GuidedLearning.ok2.click();
				CommonUtils.hold(4);
				GuidedLearning.publish2.click();
				CommonUtils.hold(3);
				GuidedLearning.continueToPublish.click();
				CommonUtils.hold(7);
				GuidedLearning.home.click();
				//homeIcon3.click();	
				CommonUtils.hold(10);
				try
				{
					 islinkpresent=GuidedLearning.glWidgets.isEnabled();
				}
				catch(Exception e)
				{
					islinkpresent=false;
					
				}
				assertTrue(islinkpresent,"widget is not present");
				
		 
	}
}
