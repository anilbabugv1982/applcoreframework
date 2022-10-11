package pageDefinitions.UI.oracle.applcore.qa.FusePlus;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;

public class GuidedLearningUtil extends GuidedLearning {

	private NavigationTaskFlows ntFInstance;
	public GuidedLearningUtil(WebDriver driver) {
		super(driver);
		
		ntFInstance = new NavigationTaskFlows(driver);
	}
	
	public boolean verifyErrorMsg()
	{
		boolean isElementPresent =false;
		isElementPresent= errorMsg.isDisplayed();
		System.out.println("isElementPresent "+isElementPresent);
		return isElementPresent;
	}
	
	
	public void setAdminProfile(String value, WebDriver driver) throws Exception {
		
		Select mydrpdwn = new Select(profileValue);
		mydrpdwn.selectByVisibleText(value);
		saveAndClose.click();
		CommonUtils.hold(5);
		home.click();
		//homeIcon.click();
		CommonUtils.hold(20);
	}
	
public void setAdminProfileForResponsiveUI(String value, WebDriver driver) throws Exception {
	profileValue1.clear();
	profileValue1.sendKeys(value);
	save2.click();
		CommonUtils.hold(5);
		
	}
	
	public void createGuidedLearningSandbox(WebDriver GLdriver,String SBname) throws Exception
	{
		CommonUtils.hold(5);
		CommonUtils.explicitWait(createSandBox, "Click", "",GLdriver);
		createSandBox.click();
		CommonUtils.explicitWait(sandboxName, "Click", "",GLdriver);
		boolean isSandboxTypePresent=false;
		isSandboxTypePresent=GLdriver.findElement(By.xpath("//span[contains(text(),'Guided Learning')]")).isDisplayed();
		assertTrue(isSandboxTypePresent,"SandBox type is not present");
		glCheckbox.click();
		CommonUtils.hold(2);
		sandboxName.sendKeys(SBname);
		//sandboxdiscription.sendKeys(SBname);
		createAndEnter.click();
		CommonUtils.hold(25);
	}
	
	public void DeleteGuidedLearningSandbox(WebDriver GLdriver) throws Exception
	{
		CommonUtils.hold(15);
		List<WebElement> el=GLdriver.findElements(By.xpath("//a[contains(text(),\'OGL\')]"));
		int size=el.size();
		Iterator<WebElement> it=el.iterator();
		while(it.hasNext())
		{
			ntFInstance.navigateToTask(sandBox, GLdriver); 
			CommonUtils.hold(10);			
			WebElement ele=GLdriver.findElement(By.xpath("//a[contains(text(),\'OGL\')]"));
			ele.click();
			CommonUtils.hold(10);
			CommonUtils.explicitWait(actions, "Click", "",GLdriver);
			actions.click();
			CommonUtils.explicitWait(delete, "Click", "",GLdriver);
			delete.click();
			CommonUtils.explicitWait(yes, "Click", "",GLdriver);
			yes.click();
			CommonUtils.explicitWait(createSandBox, "Click", "",GLdriver);
			it.next();
		}
		
	}
	

	public void verifySandBox(WebDriver GLdriver,String var, String user) throws Exception {
		CommonUtils.hold(15);
		CommonUtils.explicitWait(createSandBox, "Click", "",GLdriver);
		WebElement sandboxDropDown=GLdriver.findElement(By.xpath("//div[contains(@aria-label,'"+var+"')]"));
		CommonUtils.explicitWait(sandboxDropDown, "Click", "",GLdriver);
		boolean isSandBoxPresent=sandboxDropDown.isDisplayed();
		assertTrue(isSandBoxPresent,"NOT entered into sandbox");
//		tools.click();
//		CommonUtils.hold(5);
//		CommonUtils.explicitWait(glboxtext, "Click", "",GLdriver);
//		boolean isGLPresent=glboxtext.isDisplayed();
//		assertTrue(isGLPresent,"GL is not shown");
		tooldropdown.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(sbDetails, "Click", "",GLdriver);
		try 
		{
			while(sbDetails.isDisplayed())
			{
				break;
								
			}
		}
		catch(Exception e)
		{
			System.out.println("tools drop down not clicked");
			tooldropdown.click();
		}
		sbDetails.click();
		CommonUtils.hold(5);
		boolean isActivateToolHaveGL=gLText.isDisplayed();
		assertTrue(isActivateToolHaveGL,"GL is not shown under activate tool on sandbox detail page");
		boolean isSandBoxNamePresent=GLdriver.findElement(By.xpath("//span[contains(text(),'"+var+"')]")).isDisplayed();
		assertTrue(isSandBoxNamePresent,"Sandbox name is present on sandbox detail page");
		boolean isUserPresent=GLdriver.findElement(By.xpath("//span[contains(text(),'"+user+"')]")).isDisplayed();
		assertTrue(isUserPresent,"User is not present on sandbox detail page");
		done.click();
		CommonUtils.hold(5);
		System.out.println("sandbox verified sucessfully");
		home.click();
		//homeIcon3.click();	
		CommonUtils.hold(10);
	}
	public void ConfigureGuidedLearningwithAPPID(WebDriver GLdriver,String appId,String env) throws Exception
	{
		CommonUtils.explicitWait(applicationId, "Click", "", GLdriver);
		try 
		{
			while(role.isDisplayed())
			{
				deleteRole.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(yesPopup, "Click", "", GLdriver);
				yesPopup.click();
				CommonUtils.hold(5);
				
			}
		}
		catch(Exception e)
		{
			System.out.println("role not present");
		}
		applicationId.clear();
		applicationId.sendKeys(appId);
		if(env.equalsIgnoreCase("dev"))
		{
			dev.click();
		}
		else
		{
			prod.click();
			
		}
		
	}
	
	public void addRoleForGuidedLearningPage(WebDriver GLdriver,String appRole,String glRole) throws Exception
	{
		try 
		{
			while(role.isDisplayed())
			{
				deleteRole.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(yesPopup, "Click", "", GLdriver);
				yesPopup.click();
				CommonUtils.hold(5);
				
			}
		}
		catch(Exception e)
		{
			System.out.println("role not present");
		}
		CommonUtils.hold(5);
		addRole.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(firstAppRole, "Click", "", GLdriver);
		firstAppRole.clear();
		firstAppRole.sendKeys(appRole);
		CommonUtils.hold(3);
		Select sel= new Select(firstGlRole);
		sel.selectByVisibleText(glRole);
		CommonUtils.hold(5);
		save.click();
		CommonUtils.hold(5);
		//boolean isElPresent=GLdriver.findElement(By.xpath("//input[@title='"+appRole+"']")).isDisplayed();
		//assertTrue(isElPresent,"Role Not Saved");
		
				
	}
	
	public void saveandCloseNavigateToHome() throws Exception
	{
		saveAndClose2.click();
		CommonUtils.hold(5);
		home.click();
		//homeIcon.click();
		CommonUtils.hold(20);
		
	}

	public void verifyWidgetOnHomeandPersonalInfoPage(WebElement glWidgets ,WebDriver GLdriver) throws Exception {
		CommonUtils.explicitWait(glWidgets, "Click", "", GLdriver);

		boolean isWidgetPresent=glWidgets.isDisplayed();
			assertTrue(isWidgetPresent,"Widget is not present at home page");
		ntFInstance.navigateToTask(personalInfo, GLdriver); 
		isWidgetPresent=glWidgets.isDisplayed();
		assertTrue(isWidgetPresent,"Widget is not present at personal info page");
		CommonUtils.hold(5);
		CommonUtils.explicitWait(home, "Click", "", GLdriver);
		home.click();
		//homeIcon3.click();
		CommonUtils.hold(10);
		
	}
	
//	public void verifyHelpWidgetOnHomeandPersonalInfoPage(WebDriver GLdriver) throws Exception {
//		CommonUtils.explicitWait(HelpglWidgets, "Click", "", GLdriver);
//
//		boolean isWidgetPresent=HelpglWidgets.isDisplayed();
//		assertTrue(isWidgetPresent,"Widget is not present at home page");
//		HelpglWidgets.click();
//		ntFInstance.navigateToTask(personalInfo, GLdriver); 
//		isWidgetPresent=HelpglWidgets.isDisplayed();
//		HelpglWidgets.click();
//		assertTrue(isWidgetPresent,"Widget is not present at personal info page");
//		CommonUtils.hold(5);
//		CommonUtils.explicitWait(homeIcon3, "Click", "", GLdriver);
//		homeIcon3.click();
//		CommonUtils.hold(10);
//		
//	}
	
	public void verifyLinksOnWidget(WebElement HelpglWidgets , WebDriver GLdriver, String [] links) throws Exception {
		List<String> el=new ArrayList<String>(); 
		CommonUtils.explicitWait(HelpglWidgets, "Click", "", GLdriver);
		int i=1;
		boolean isLinkPresent=false;
		HelpglWidgets.click();
		CommonUtils.hold(2);
		List<WebElement> ls=GLdriver.findElements(By.xpath("//div[contains(text(),'Interactive Guides')]//..//ul//li"));
		System.out.println("lis size "+ls.size());
		Iterator<WebElement> it=ls.iterator();
		while(it.hasNext())
		{
			
			String txt=GLdriver.findElement(By.xpath("//div[contains(text(),'Interactive Guides')]//..//ul//li["+i+"]//button//span")).getText();
			el.add(txt);
			i++;
			it.next();
		}
		System.out.println("links are "+el);
		int len=links.length;
		for(i=0;i<len;i++)
		{

			Assert.isTrue(el.contains(links[i]),links[i]+ "link is not present");
			
		}
		
	}
	
	public void verifyUpdatePhoneLink(WebDriver GLdriver) throws Exception {
		CommonUtils.explicitWait(updatePhone, "Click", "", GLdriver);
		boolean isHelpIcon=false;
		updatePhone.click();
		verifytillContactInfoPage(GLdriver);	
		CommonUtils.explicitWait(updatePhonePopUP, "Click", "", GLdriver);
		isHelpIcon=updatePhonePopUP.isDisplayed();
		assertTrue(isHelpIcon,"update Phone Pop UP is not visible on contact info page");
		nextOnPopUp.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(backOnPopUp, "Click", "", GLdriver);
		isHelpIcon=backOnPopUp.isDisplayed();
		assertTrue(isHelpIcon,"Did not reach at end");
		addPhone.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(select, "Click", "", GLdriver);
		isHelpIcon=select.isDisplayed();
		assertTrue(isHelpIcon,"select phone detail pop up not displayed");
		phoneDetails.click();
		CommonUtils.hold(7);
		CommonUtils.explicitWait(phonePopUp, "Click", "", GLdriver);
		isHelpIcon=phonePopUp.isDisplayed();
		assertTrue(isHelpIcon,"phone detail pop up not displayed");
		nextOnPopUp.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(backOnPopUp, "Click", "", GLdriver);
		isHelpIcon=backOnPopUp.isDisplayed();
		assertTrue(isHelpIcon,"phone detail pop up not displayed");
		cancelPhone.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(nextOnPopUp, "Click", "", GLdriver);
		isHelpIcon=nextOnPopUp.isDisplayed();
		assertTrue(isHelpIcon,"edit msg popup not displayed");
		nextOnPopUp.click();
		CommonUtils.explicitWait(completeGuide, "Click", "", GLdriver);
		isHelpIcon=completeGuide.isDisplayed();
		assertTrue(isHelpIcon,"complete guide popup not displayed");
		doneOnPOpUp.click();
		CommonUtils.explicitWait(home, "Click", "", GLdriver);
		home.click();
		//homeIcon3.click();	
		CommonUtils.hold(10);
	}
	
	public void verifyUpdateAddessLink(WebDriver GLdriver) throws Exception {
	//	CommonUtils.explicitWait(HelpglWidgets, "Click", "", GLdriver);
		boolean isHelpIcon=false;
	//	HelpglWidgets.click();
	//	CommonUtils.hold(2);
		CommonUtils.explicitWait(updateAddress, "Click", "", GLdriver);
		updateAddress.click();
		verifytillContactInfoPage(GLdriver);
		CommonUtils.explicitWait(updateAddressPopUP, "Click", "", GLdriver);
		isHelpIcon=updateAddressPopUP.isDisplayed();
		assertTrue(isHelpIcon,"update Address Pop UP is not visible on contact info page");
		nextOnPopUp.click();
		CommonUtils.explicitWait(clickPopUp, "Click", "", GLdriver);
		isHelpIcon=clickPopUp.isDisplayed();
		assertTrue(isHelpIcon,"pop up not displayed");
		addAddress.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(addressPopUp, "Click", "", GLdriver);
		isHelpIcon=addressPopUp.isDisplayed();
		assertTrue(isHelpIcon,"pop up not displayed");
		nextOnPopUp.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(primaryChcek, "Click", "", GLdriver);
		isHelpIcon=primaryChcek.isDisplayed();
		assertTrue(isHelpIcon,"primary checkup pop up not displayed");
		nextOnPopUp.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(backOnPopUp, "Click", "", GLdriver);
		isHelpIcon=backOnPopUp.isDisplayed();
		assertTrue(isHelpIcon,"pop up not displayed");
		CommonUtils.hold(3);
		CommonUtils.explicitWait(cancelAddress, "Click", "", GLdriver);
		cancelAddress.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(completeGuide, "Click", "", GLdriver);
		isHelpIcon=completeGuide.isDisplayed();
		assertTrue(isHelpIcon,"complete guide popup not displayed");
		doneOnPOpUp.click();
		CommonUtils.explicitWait(home, "Click", "", GLdriver);
		home.click();
	//	homeIcon3.click();	
		CommonUtils.hold(10);
						
	}
	
	public void verifytillContactInfoPage(WebDriver GLdriver) throws Exception
	{
		boolean isHelpIcon=false;
		CommonUtils.hold(10);
		CommonUtils.explicitWait(clickMePopUp, "Click", "", GLdriver);
		isHelpIcon=clickMePopUp.isDisplayed();
		assertTrue(isHelpIcon,"Click me Help Icon is not visible");
		me.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(personalInformationPopUp, "Click", "", GLdriver);
		isHelpIcon=personalInformationPopUp.isDisplayed();
		assertTrue(isHelpIcon,"Personal info Help Icon is not visible");
		personalInformation.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(contactInformationPopUp, "Click", "", GLdriver);
		isHelpIcon=contactInformationPopUp.isDisplayed();
		contactInformation.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(contactInfoPage, "Click", "", GLdriver);
		assertTrue(contactInfoPage.isDisplayed(),"Not landed to contact info page");
	}

	public void verifyViewCompensationInfo(WebDriver guidedLearningDriver) throws Exception {
		boolean isHelpIcon=false;
	//	CommonUtils.explicitWait(HelpglWidgets, "Click", "", guidedLearningDriver);
	//	HelpglWidgets.click();
	//	CommonUtils.hold(2);
		CommonUtils.explicitWait(viewCompensation, "Click", "", guidedLearningDriver);
		viewCompensation.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(personalInformationPopUp, "Click", "", guidedLearningDriver);
		isHelpIcon=personalInformationPopUp.isDisplayed();
		assertTrue(isHelpIcon,"Personal info Help Icon is not visible");
		personalInformation.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(fandEPopUp, "Click", "", guidedLearningDriver);
		isHelpIcon=fandEPopUp.isDisplayed();
		assertTrue(isHelpIcon,"F and E contact Help Icon is not visible");
		fandEContact.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(myContactPopUP, "Click", "", guidedLearningDriver);
		isHelpIcon=myContactPopUP.isDisplayed();
		assertTrue(isHelpIcon,"view mycontact Help Icon is not visible");
		CommonUtils.explicitWait(nextOnPopUp, "Click", "", guidedLearningDriver);
		nextOnPopUp.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(completeGuide, "Click", "", guidedLearningDriver);
		isHelpIcon=completeGuide.isDisplayed();
		assertTrue(isHelpIcon,"complete guide popup not displayed");
		CommonUtils.explicitWait(doneOnPOpUp, "Click", "", guidedLearningDriver);
		doneOnPOpUp.click();
		home.click();
		//homeIcon3.click();	
		CommonUtils.hold(10);
	}

	public void verifyTeamMemberDocument(WebDriver guidedLearningDriver) throws Exception {
		CommonUtils.explicitWait(viewTeamMemberDoc, "Click", "", guidedLearningDriver);
		viewTeamMemberDoc.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(select, "Click", "", guidedLearningDriver);
		boolean isHelpIcon=select.isDisplayed();
		assertTrue(isHelpIcon," Help Icon is not visible");
		myTeam.click();
		isHelpIcon=myTeamPopUp.isDisplayed();
		assertTrue(isHelpIcon," Help Icon is not visible");
		selectMyTeam.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(employee, "Click", "", guidedLearningDriver);
		employee.click();
		CommonUtils.hold(3);
		
	}
	
}
