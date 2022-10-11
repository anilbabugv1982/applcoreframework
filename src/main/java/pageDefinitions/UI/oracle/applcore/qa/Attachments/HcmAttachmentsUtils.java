package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.ManageAdminProfileValues;

public class HcmAttachmentsUtils extends HcmAttachmentsPage{
	private GlobalPageTemplate glbpageInstance=null;
	private ManageAdminProfileValues manageAdminProfileValuesInstance=null;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;

	public HcmAttachmentsUtils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		glbpageInstance=new GlobalPageTemplate(driver);
		manageAdminProfileValuesInstance = new ManageAdminProfileValues(driver);
		ntFInstance = new NavigationTaskFlows(driver);
		nMEInstance = new NavigatorMenuElements(driver);
	}
	
//*****************************************************************************************************************************************************************
	
	public void navigateToAOLTask(String AOLTaskName, WebDriver driver) throws Exception {
		//ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, driver);
		driver.get(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		Log.info("Navigated to Setup and Maintenance");
		CommonUtils.hold(4);
		ntFInstance.navigateToAOLTaskFlows(AOLTaskName, driver);
		Log.info("Clicked on "+AOLTaskName);
		CommonUtils.waitForPageLoad(driver);
	}
	
//*****************************************************************************************************************************************************************	
	public void setHcmAttachmentsProfileValues(WebDriver driver) throws Exception {
		
		navigateToAOLTask("Manage Administrator Profile Values", driver);
		String profileLevel = "Site";
		String profileValue = "Y";
		
		manageAdminProfileValuesInstance.setProfileValue("HCM_RESPONSIVE_PAGES_ENABLED", profileLevel, "", profileValue, driver);
		manageAdminProfileValuesInstance.setProfileValue("PER_EMPLOYMENT_GUIDED_FLOWS_RESPONSIVE_ENABLED", profileLevel, "", profileValue, driver);
		manageAdminProfileValuesInstance.setProfileValue("HCM_TASK_CONFIGURATOR_RESPONSIVE_ENABLED", profileLevel, "", profileValue, driver);
		
	}
	
//*****************************************************************************************************************************************************************	
	
	public void navigateToHCMTaskAttachments(String HCMTaskName, WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		glbpageInstance.homeIcon.click();
		Log.info("Clicked on Home Icon");
		CommonUtils.hold(8); // waiting for body html element to have style attribute
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(myTeamTab, "Visible", "", driver);
		myTeamTab.click();
		CommonUtils.hold(4);
		CommonUtils.scrollToElement(glbpageInstance.redWoodMyTeamShowMoreLink, driver);
		glbpageInstance.redWoodMyTeamShowMoreLink.click();
		CommonUtils.explicitWait(glbpageInstance.redWoodShowLessLink, "Visible", "", driver);
		
		WebElement hcmTask = driver.findElement(By.xpath("//a[contains(@class,'flat-show-less-link')]/following::a[text()='"+HCMTaskName+"']"));
		hcmTask.click();
		CommonUtils.hold(10);
		CommonUtils.waitForJStoLoad(driver, 50);
		CommonUtils.hold(8);
		
		try {
			CommonUtils.explicitWait(directReports_Anthony, "Click", "", driver);
			CommonUtils.hold(5);
			//CommonUtils.clickWithJavaScript(directReports_Anthony, driver);
			directReports_Anthony.click();
		}catch (ElementClickInterceptedException e) {
			CommonUtils.hold(10);
			CommonUtils.clickWithJavaScript(directReports_Anthony, driver);
			//directReports_Anthony.click();
		}
		AttachmentCommonUtils.waitTillLoad(20, driver);
		
		try {
			//CommonUtils.explicitWait(commentsAndAttachments, "Click", "", driver);
			CommonUtils.hold(3);
			commentsAndAttachments.click();
			CommonUtils.explicitWait(continueToolbarButton, "Click", "", driver);
			continueToolbarButton.click();
			AttachmentCommonUtils.waitTillLoad(25, driver);
		} catch(NoSuchElementException e) {
			System.out.println("\"What info do you want to manage?\" page is not there. ");
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(@id,'up1Upl:UPsp1:SPph::_afrTtxt')]/descendant::h1[contains(text(),'"+HCMTaskName+"')]")).isDisplayed(), "Not navigated to "+HCMTaskName+" hcm page.");
		Log.info("Navigated to "+HCMTaskName+" hcm page.");
		
	}
	
//*****************************************************************************************************************************************************************	
		
	public void clickOnContinueSectionButton(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(continueButton, "Click", "", driver);
		continueButton.click();
		AttachmentCommonUtils.waitTillLoad(30, driver);
		CommonUtils.hold(3);
		
	}
	
	public static void waitTillHCMFileUpload(int waitInSeconds, WebDriver driver) {
		CommonUtils.hold(2);
		CommonUtils.waitForInvisibilityOfElement(fileUploadProcessBar, driver, waitInSeconds);
		CommonUtils.waitForInvisibilityOfElement(uploadCancelBtn, driver, waitInSeconds);
		AttachmentCommonUtils.waitTillLoad(10, driver);
		
	}
	
	public void hcmFileAttach(String filename, WebDriver driver) throws Exception {
		//CommonUtils.explicitWait(browse, "Click", "", driver);
		CommonUtils.hold(4);
		driver.findElement(By.xpath("//input[contains(@id,'pglAdfIf::dzHfile')]")).sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		waitTillHCMFileUpload(20, driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'pseAvsdCe:otAvsdCeDiFile') and text()='"+filename+"']")).isDisplayed(), "File is not attached successfully.");
	}
	
	public void hcmURLAttach(String url, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(addAttachmentLink, "Click", "", driver);
		addAttachmentLink.click();
		CommonUtils.explicitWait(addLinkMenu, "Click", "", driver);
		addLinkMenu.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(addLinkDiagBox, "Visible", "", driver);
		CommonUtils.explicitWait(addLinktextField, "Visible", "", driver);
		addLinktextField.clear();
		addLinktextField.sendKeys(url);
		addLinkSaveAndClose.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		
		String urlInnerText = url.substring(0, Math.min(url.length(), 30));
		System.out.println(urlInnerText);
		if(url.startsWith("file:/") && !(url.startsWith("file:///"))) {
			url = url.replace("file:/", "file:///");
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+urlInnerText+"')]")).getAttribute("href").contains(url), "URL is not attached successfully.");
	}
	
	public void hcmEditFileAttach(String filename, String newFileTitle, WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		WebElement editIcon = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following::img[contains(@id,'PSEcil6::icon')]"));
		editIcon.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		
		CommonUtils.explicitWait(editTitleField, "Click", "", driver);
		editTitleField.clear();
		editTitleField.sendKeys(newFileTitle);
		
		String descText = "Desc"+newFileTitle;
		CommonUtils.explicitWait(editDescriptionField, "Click", "", driver);
		editDescriptionField.clear();
		editDescriptionField.sendKeys(descText);
		
		CommonUtils.explicitWait(editSaveBtn, "Click", "", driver);
		editSaveBtn.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'pseAvsdCe:otAvsdCeDiFile') and text()='"+newFileTitle+"']")).isDisplayed(), "File is not edited successfully.");
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'"+descText+"')]")).isDisplayed(), "URL Description is not edited successfully.");
	
	}
	
	public void hcmEditURLAttach(String url, String newUrlTitle, WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		WebElement editIcon = driver.findElement(By.xpath("//a[contains(text(),'"+url+"')]/following::img[contains(@id,'PSEcil6::icon')]"));
		editIcon.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		
		CommonUtils.explicitWait(editTitleField, "Click", "", driver);
		editTitleField.clear();
		editTitleField.sendKeys(newUrlTitle);
		
		String descText = "Desc"+newUrlTitle;
		CommonUtils.explicitWait(editDescriptionField, "Click", "", driver);
		editDescriptionField.clear();
		editDescriptionField.sendKeys(descText);
		
		CommonUtils.explicitWait(editSaveBtn, "Click", "", driver);
		editSaveBtn.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'pseAvsdCe:glAvsdCeUrl') and text()='"+newUrlTitle+"']")).getAttribute("href").contains(url), "URL Title is not edited successfully.");
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'"+descText+"')]")).isDisplayed(), "URL Description is not edited successfully.");
	
	}
	
	
	public void hcmDownloadFileAttach(String filename, String fileTitle, WebDriver driver) throws Exception {
		WebElement downloadIcon = driver.findElement(By.xpath("//span[contains(text(),'"+fileTitle+"')]/following::a[contains(@id,'gilAvsdCeDwn')]"));
		
		
		Assert.assertTrue(downloadIcon.getAttribute("href")
				.matches(".*hcmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		downloadIcon.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
	}
	
	public void hcmDownloadURLAttach(String URL, String urlTitle, WebDriver driver) throws Exception {
		WebElement downloadIcon = driver.findElement(By.xpath("//a[contains(text(),'"+urlTitle+"')]"));
		AttachmentCommonUtils.validateURL(downloadIcon, URL, driver);
		
	}
	
	public void hcmDeleteFileAttach(String title, WebDriver driver) throws Exception {
		WebElement deleteIcon = driver.findElement(By.xpath("//*[contains(text(),'"+title+"')]/following::img[contains(@id,'cilAvsdCeDel::icon')]"));
		deleteIcon.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(warningYesBtn, "Click", "", driver);
		warningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.hold(5);
		
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//*[contains(text(),'"+title+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File/URL is deleted successfully.");
		}
	
	}
	
	public void cancelHCM(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(cancelHCMButton, "Click", "", driver);
		cancelHCMButton.click();
		CommonUtils.waitForInvisibilityOfElement(cancelHCMButton, driver, 15);
	}
	
	
	
	
	
}
