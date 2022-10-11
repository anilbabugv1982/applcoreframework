package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.SetupAndMaintenance;

public class ManageSetupContent extends ManageSetupContentPage{

	public ManageSetupContent(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	private SetupAndMaintenance sAMInstance_nTF=null;
	
	public void navigateToManageSetupContentDefinition(String definition, WebDriver driver) {
		sAMInstance_nTF = new SetupAndMaintenance(driver);
		try {
			
			CommonUtils.waitForPageLoad(driver);
			sAMInstance_nTF.verifySetupAndMaintenancePage(driver);
			CommonUtils.hold(2);
			CommonUtils.explicitWait(panelDrawer, "Click", "",driver);
			panelDrawer.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(manageSetupContentLink, "Click", "",driver);
			manageSetupContentLink.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//a[normalize-space(text())='"+definition+"']")), "Click", "",driver);
			driver.findElement(By.xpath("//a[normalize-space(text())='"+definition+"']")).click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(3);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//h1[normalize-space(text())='"+definition+"']")), "Click", "",driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void integrateIDCSApplication(String application, String fullURL, String userName, String password, WebDriver driver) throws Exception {
		String partnerName = "IDCS";
		String securityPolicy = "oracle/wss_username_token_over_ssl_client_policy";
		
		try {
			CommonUtils.hold(3);
			if(driver.findElement(By.xpath("//a[normalize-space(text())='"+application+"']")).isDisplayed()) {
				System.out.println(application+" is already present.");
			}
		} catch(NoSuchElementException e) {
			CommonUtils.explicitWait(createAppIntegrationIcon, "Click", "",driver);
			createAppIntegrationIcon.click();
			CommonUtils.hold(6);
			CommonUtils.explicitWait(applicationNameField, "Visible", "",driver);
			applicationNameField.clear();
			applicationNameField.sendKeys(application);
			
			fullURLField.clear();
			fullURLField.sendKeys(fullURL);
			
			partnerNameField.clear();
			partnerNameField.sendKeys(partnerName);
			
			CommonUtils.selectDropDownElement(securityPolicyField, securityPolicy);
			CommonUtils.hold(3);
			
			if(securityPolicy.equalsIgnoreCase("oracle/wss_username_token_over_ssl_client_policy")) {
				CommonUtils.explicitWait(userNameField, "Click", "", driver);
				userNameField.clear();
				userNameField.sendKeys(userName);
				
				passwordField.clear();
				passwordField.sendKeys(password);
			}
			
			applyBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(applyBtn, "Click", "",driver);
			Assert.assertEquals(contextRootDetails.getText(), "admin/v1", "IDCS application Integration failed.");
			
			saveAndCloseBtn.click();
			CommonUtils.hold(6);
			
		}
	}
	
	public void integrateSIMApplication(String application, WebDriver driver) throws Exception {
		String fullURL = "http://slc06xcd.us.oracle.com:9001/cloudIdentity/msgqa4reza15jun15/.cloudPortal/api/v1";
		String partnerName = "SIM";
		String securityPolicy = "None";
		
		try {
			if(driver.findElement(By.xpath("//a[normalize-space(text())='"+application+"']")).isDisplayed()) {
				System.out.println(application+" is already present.");
			}
		} catch(NoSuchElementException e) {
			createAppIntegrationIcon.click();
			CommonUtils.explicitWait(applicationNameField, "Visible", "",driver);
			applicationNameField.clear();
			applicationNameField.sendKeys(application);
			
			fullURLField.clear();
			fullURLField.sendKeys(fullURL);
			
			partnerNameField.clear();
			partnerNameField.sendKeys(partnerName);
			
			CommonUtils.selectDropDownElement(securityPolicyField, securityPolicy);
			
			applyBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(applyBtn, "Click", "",driver);
			
			saveAndCloseBtn.click();
			CommonUtils.hold(6);
			
		}
	}


}
