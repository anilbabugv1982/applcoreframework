package UI.tests.ResponsiveFusePlus;

import java.awt.Panel;
import java.lang.reflect.Method;
import java.sql.Driver;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.ResponsiveFusePlusUtil;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.PanelSectionEditPage;



public class PanelSectionEditTest extends GetDriverInstance{
	static String uniqueID;

	private WebDriver PanelSectionEditTestsInstance;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private PanelSectionEditPage PanelSectionEditPageInstance;
	private ResponsiveFusePlusUtil ResponsiveFusePlusUtilInstance;
	private ProfilesUtils pOUtil = null;
	private ApplicationLogin applogin;

	@Parameters({"user","pwd"})
	@BeforeClass
	public void setUp(String loginname, String loginpwd) throws Exception {
		PanelSectionEditTestsInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(PanelSectionEditTestsInstance);
		ntFInstance = new NavigationTaskFlows(PanelSectionEditTestsInstance);
		nMEInstance = new NavigatorMenuElements(PanelSectionEditTestsInstance);
		ntFInstance = new NavigationTaskFlows(PanelSectionEditTestsInstance);
		nMEInstance = new NavigatorMenuElements(PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance = new PanelSectionEditPage(PanelSectionEditTestsInstance);
		ResponsiveFusePlusUtilInstance = new ResponsiveFusePlusUtil(PanelSectionEditTestsInstance);
		uniqueID = CommonUtils.uniqueId();
		System.out.println("Setup()....");
		Log.info("Setup()....");
		Thread.sleep(2);
		aLoginInstance.login(loginname, loginpwd, PanelSectionEditTestsInstance);
		CommonUtils.hold(2);
		System.out.println("Log In successful...");
		Log.info("Log In successful...");


	}

	@SuppressWarnings("static-access")
	@Test(priority=1,description="Set Profile Options",enabled=true)
	public void setProfileOptions() throws Exception {
		ResponsiveFusePlusUtilInstance.checkprofileset(PanelSectionEditTestsInstance, "HCM_RESPONSIVE_PAGES_ENABLED");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.checkprofileset(PanelSectionEditTestsInstance, "PER_PERSONAL_INFORMATION_RESPONSIVE_ENABLED");
	}
	
	@SuppressWarnings("static-access")
	@Test(priority=2,description="Expand and collapse Communication Section",enabled=true)
	public void ExpandAndCollapseCommunicationSection() throws Exception {
		CommonUtils.hold(15);
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(30);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.contactInfo, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.contactInfo.click();
		CommonUtils.hold(20);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Communication");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Communication");
	}

	@SuppressWarnings("static-access")
	@Test(priority=3,description="Add Home Phone Info",enabled=true)
	public void SaveAndCancelOnHomePhoneInfo() throws Exception {
		CommonUtils.hold(20);
		//ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		//CommonUtils.hold(30);
		//JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		//js.executeScript("window.scrollBy(0,1000)");
		//CommonUtils.explicitWait(PanelSectionEditPageInstance.contactInfo, "Click", "", PanelSectionEditTestsInstance);
		//PanelSectionEditPageInstance.contactInfo.click();
		//CommonUtils.hold(20);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Home Phone", 1);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(PanelSectionEditPageInstance.cancelHomePhone, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.cancelHomePhone.click();
		CommonUtils.hold(15);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Home Phone", 1);
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.inputHomephoneNumer.clear();
		String homePhn = PanelSectionEditPageInstance.setHomePhoneNumber(PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.inputHomephoneNumer.sendKeys(homePhn);
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.submitButton.click();
		Log.info("Home phone info saved successfully");
	}


	@SuppressWarnings("static-access")
	@Test(priority=4,description="Edit Home Address",enabled=true)
	public void expandAndCollapseCommunication() throws Exception {
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(30);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.contactInfo, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.contactInfo.click();
		CommonUtils.hold(20);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Home Address", 2);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(PanelSectionEditPageInstance.homeCanelButton, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.homeCanelButton.click();
		CommonUtils.hold(15);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Home Address", 2);
		CommonUtils.hold(10);
		String addrchng = PanelSectionEditPageInstance.getIdOfAddressChange(PanelSectionEditTestsInstance);
		Log.info("addr chng id is *****************"+addrchng);
		JavascriptExecutor js4 = (JavascriptExecutor) PanelSectionEditTestsInstance;
		String jsCode4 = "document.getElementById('"+addrchng+"').value='2/1/21'";
		js4.executeScript(jsCode4);
		CommonUtils.hold(5);	
		PanelSectionEditPageInstance.addressLine1.clear();
		PanelSectionEditPageInstance.addressLine1.sendKeys("10 Downing Street test1");
		CommonUtils.hold(2);
		PanelSectionEditPageInstance.city.clear();
		String cityID = PanelSectionEditPageInstance.getIdOfCity(PanelSectionEditTestsInstance);
		Log.info("addr chng id is *****************"+cityID);
		JavascriptExecutor js2 = (JavascriptExecutor) PanelSectionEditTestsInstance;
		String jsCode2 = "document.getElementById('"+cityID+"').value='Oxford'";
		js.executeScript(jsCode2);
		CommonUtils.explicitWait(PanelSectionEditPageInstance.AddressSubmit, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.AddressSubmit.click();
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Address");
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Address");
	}

	/*@Test(priority=5,description="Navigate to PersonalIdentification page",enabled=false)
	public void EditHomeAddress() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(30);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.personalDetails, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.indentificationInfo.click();
		CommonUtils.hold(10);

	}*/

/*	@Test(priority=3,description="Navigate to PersonalIdentification page",enabled=false)
	public void addcitizenship() throws Exception {
		PanelSectionEditPageInstance.addCitizenShip.click();
		CommonUtils.hold(10);

	}*/

	@SuppressWarnings("static-access")
	@Test(priority=5,description="Expand/Collapse Name Info and edit Name Info",enabled=true)
	public void expandAndCollapseAddressInfo() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(30);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.personalDetails, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.personalDetails.click();
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Start Date", 1);
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.CancelName.click();
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Start Date", 1);
		CommonUtils.hold(10);
		String namedt = PanelSectionEditPageInstance.getIdOfNameDate(PanelSectionEditTestsInstance);
		Log.info("addr chng id is *****************"+namedt);
		JavascriptExecutor js2 = (JavascriptExecutor) PanelSectionEditTestsInstance;
		String jsCode2 = "document.getElementById('"+namedt+"').value='2/21/21'";
		js.executeScript(jsCode2);
		CommonUtils.hold(5);
		PanelSectionEditPageInstance.lastName.clear();
		PanelSectionEditPageInstance.lastName.sendKeys("Moss1"); 
		PanelSectionEditPageInstance.SubmitName.click();
		Log.info("Inside Expand/collapse method");
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Name");
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Name");
		Log.info("Clicked on Expand and collapse for Name section");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Demographic Info");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Demographic Info");
		CommonUtils.hold(5);

	}

/*	@SuppressWarnings("static-access")
	@Test(priority=3,description="Expand/Collapse Address Info",enabled=false)
	public void navigateToPersonalInfo() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(30);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.personalDetails, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.personalDetails.click();
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickedit(PanelSectionEditTestsInstance, "Start Date", 1);
		CommonUtils.hold(30);
		String calendarID = PanelSectionEditPageInstance.getId(PanelSectionEditTestsInstance);
		JavascriptExecutor js1 = (JavascriptExecutor) PanelSectionEditTestsInstance;
		String jsCode1 = "document.getElementById('"+calendarID+"').value='2/1/21'";
		js1.executeScript(jsCode1);
		PanelSectionEditPageInstance.lastName.clear();
		PanelSectionEditPageInstance.lastName.sendKeys("Moss_1");
		PanelSectionEditPageInstance.firstName.clear();
		PanelSectionEditPageInstance.firstName.sendKeys("Veda_1");
		PanelSectionEditPageInstance.middleName.clear();
		PanelSectionEditPageInstance.middleName.sendKeys("Test2");
		PanelSectionEditPageInstance.submitButton.click();
	}*/

/*	@Test(priority=4,description="Expand/Collapse Name Section",enabled=false)
	public void ExpandAndCollapseNameSection() throws Exception{
		CommonUtils.hold(5);
		CommonUtils.explicitWait(PanelSectionEditPageInstance.collapseNameSection, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.collapseNameSection.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(PanelSectionEditPageInstance.expandNameSection, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.expandNameSection.click();
		PanelSectionEditPageInstance.submitcitizenship.click();
	}*/

	@Test(priority=6,description="Cancel Citizenship",enabled=true)
	public void cancelCitizenship() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(60);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.indentificationInfo, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.idf.click();
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.addCitizenship.click();
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.cancelCitizenship.click();
		CommonUtils.hold(5);
	}
	
	@Test(priority=7,description="expand and collapse Citizenship",enabled=true)
	public void ExpandAndCollapseCitizenship() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(60);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.indentificationInfo, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.idf.click();
		CommonUtils.hold(10);
		CommonUtils.hold(10);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Citizenship");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Citizenship");
		Log.info("Expand and collapse Passports");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Passports");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Passports");
		CommonUtils.hold(5);
		Log.info("Expand and collapse Visa and permits");
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Visas and Permits");
		CommonUtils.hold(5);
		ResponsiveFusePlusUtilInstance.clickexpandcollapse(PanelSectionEditTestsInstance, "Visas and Permits");
		CommonUtils.hold(5);
	}
	@Test(priority=8,description="Cancel Citizenship",enabled=true)
	public void AddCitizenship() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.PersonalInformation, PanelSectionEditTestsInstance);
		CommonUtils.hold(60);
		JavascriptExecutor js = (JavascriptExecutor) PanelSectionEditTestsInstance;  
		js.executeScript("window.scrollBy(0,1000)");
		CommonUtils.explicitWait(PanelSectionEditPageInstance.indentificationInfo, "Click", "", PanelSectionEditTestsInstance);
		PanelSectionEditPageInstance.idf.click();
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.addCitizenship.click();
		CommonUtils.hold(5);
		String citidat = PanelSectionEditPageInstance.getIdOfcitizenshipDate(PanelSectionEditTestsInstance);
		JavascriptExecutor js1 = (JavascriptExecutor) PanelSectionEditTestsInstance;
		String jsCode1 = "document.getElementById('"+citidat+"').value='2/2/21'";
		js1.executeScript(jsCode1);
		CommonUtils.hold(10);
		String citistatus = PanelSectionEditPageInstance.getIdOfcitizenshipStatus(PanelSectionEditTestsInstance);
		JavascriptExecutor js2 = (JavascriptExecutor) PanelSectionEditTestsInstance;
		String jsCode2 = "document.getElementById('"+citistatus+"').value='Active'";
		js1.executeScript(jsCode2);
		CommonUtils.hold(10);
		PanelSectionEditPageInstance.citizenshipSubmit.click();
	}


	@AfterClass
	public void tearDown() throws Exception{
		Log.info("Logout().....");
		aLoginInstance.logout(PanelSectionEditTestsInstance);
		//driverInstanceObject.quit();
		PanelSectionEditTestsInstance.quit();
	}
}
