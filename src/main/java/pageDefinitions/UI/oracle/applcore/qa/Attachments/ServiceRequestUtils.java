package pageDefinitions.UI.oracle.applcore.qa.Attachments;

/**
 * @author Ashish Pareek
 *
 */

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class ServiceRequestUtils extends ServiceRequestPage{
//***************************************************************************************************************************************************
	public ServiceRequestUtils(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
//***********************************************************************************************************************************
	public void CreateServiceRequest(WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.waitForJStoLoad(driver, 150);
		System.out.println("wait for UI.");
		AttachmentCommonUtils.waitTillUILoad(60, driver);
		Log.info("Service Requests Page Loaded");
		CommonUtils.explicitWait(createServiceRequestsBtn, "Click", "", driver);
		CommonUtils.hold(5);
		//CommonUtils.waitforElementtoClick(50, createServiceRequestsBtn, driver);
		createServiceRequestsBtn.click();
		Log.info("Clicked on Create Service Request Button.");
		AttachmentCommonUtils.waitTillUILoad(100, driver);
		//CommonUtils.waitForPageLoad(70, driver);
		//CommonUtils.waitforElementtoVisible(30, ServiceRequestPage.serviceRequestTitleField, driver);
		Log.info("Wait for Service Requests Title Field");
		CommonUtils.explicitWait(serviceRequestTitleField, "Click", "", driver);
		CommonUtils.explicitWait(savandContinue, "Click", "", driver);
		//CommonUtils.waitForJStoLoad(driver);
		//CommonUtils.hold(30);
		//CommonUtils.waitforElementtoClick(20, serviceRequestTitleField, driver);
		serviceRequestTitleField.sendKeys("SRNAME" + CommonUtils.uniqueId());
		Log.info("Enter Service Request Name");
		//CommonUtils.waitforElementtoVisible(30, savandContinue, driver);
		CommonUtils.explicitWait(savandContinue, "Click", "", driver);
		//savandContinue.click();
		CommonUtils.clickWithJavaScript(savandContinue, driver);
		Log.info("Click on Save and Continue");
		CommonUtils.waitForInvisibilityOfElement(savandContinue, driver, 15);
		AttachmentCommonUtils.waitTillUILoad(70, driver);
		//CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
	}
	
	public void addSRFileAttachment(String filename, WebDriver driver) throws Exception {
		//CommonUtils.waitforElementtoVisible(30, ServiceRequestPage.addAttachements, driver);
		clickOnAddAttachments(driver);
		//CommonUtils.waitforElementtoVisible(30, ServiceRequestPage.browse, driver);
		CommonUtils.explicitWait(ServiceRequestPage.browse, "Click", "", driver);
		CommonUtils.hold(3);
		Log.info("Adding attachment through type File");
		//ServiceRequestPage.browse.sendKeys(System.getProperty("user.dir") + filename);
		ServiceRequestPage.browse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.fileUploadProcessBar, driver, 40);
		CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.uploadCancelBtn, driver, 40);
		CommonUtils.hold(5);
		clickOnAttachmentOkButton(driver);
		Log.info("FileType Done.. ");

		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File not Uploaded Successfully");
	}
	
	public void addSRURLAttachment(String URL, String urlTitle, WebDriver driver) throws Exception {
		Log.info("Adding attachment through Type URL");
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(ServiceRequestPage.addAttachements, "Click", "", driver);
		CommonUtils.hold(5);
		ServiceRequestPage.addAttachements.click();
		CommonUtils.hold(6);
		CommonUtils.explicitWait(ServiceRequestPage.typeSelectDropdown, "Click", "", driver);
		CommonUtils.hold(3);
		ServiceRequestPage.typeSelectDropdown.click();
		CommonUtils.explicitWait(ServiceRequestPage.typeSelectURL, "Click", "", driver);
		ServiceRequestPage.typeSelectURL.click();
		Log.info("Select URL form dropdown");
		CommonUtils.explicitWait(ServiceRequestPage.URLTextFieldLink, "Click", "", driver);
		CommonUtils.hold(3);
		ServiceRequestPage.URLTextFieldLink.clear();
		ServiceRequestPage.URLTextFieldLink.sendKeys(URL);
		Log.info("Filled URL");
		CommonUtils.explicitWait(ServiceRequestPage.URLTitleField, "Click", "", driver);
		ServiceRequestPage.URLTitleField.clear();
		ServiceRequestPage.URLTitleField.sendKeys(urlTitle);
		Log.info("Filled URL Title");
		ServiceRequestPage.SRUrlAdd.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(ServiceRequestPage.SRUrlAdd, "Click", "", driver);
		CommonUtils.hold(4);
		clickOnAttachmentOkButton(driver);
		System.out.println("URL Done..");
		
		CommonUtils.hold(3);
		if(URL.startsWith("file:/") && !(URL.startsWith("file:///"))) {
			URL = URL.replace("file:/", "file:///");
		}
		System.out.println("Added href: "+driver.findElement(By.xpath("//a[contains(text(),'"+urlTitle+"')]")).getAttribute("href"));
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(text(),'"+urlTitle+"')]")).getAttribute("href"), URL, "Added URL mismatch.");
		
	}
	
	public void updateSRFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		
		CommonUtils.hold(3);
		driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following::img[contains(@id,'cilDtl::icon')]")).click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(ServiceRequestPage.updateFile_browse, "Click", "", driver);
		ServiceRequestPage.updateFile_browse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		CommonUtils.explicitWait(ServiceRequestPage.attachmentTitle, "Click", "", driver);
		ServiceRequestPage.attachmentTitle.clear();
		ServiceRequestPage.attachmentTitle.sendKeys(newFilename);
		CommonUtils.explicitWait(ServiceRequestPage.attachmentDescription, "Click", "", driver);
		ServiceRequestPage.attachmentDescription.clear();
		ServiceRequestPage.attachmentDescription.sendKeys(newFilename);
		CommonUtils.explicitWait(ServiceRequestPage.updateDetailsBtn, "Click", "", driver);
		ServiceRequestPage.updateDetailsBtn.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(ServiceRequestPage.updateFileBtn, "Click", "", driver);
		clickOnAttachmentOkButton(driver);
		clickOnAttachmentFolderIcon(driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'AlMgmtAVw:dciTable:tblAttach') and contains(text(),'"+newFilename+"')]"))
				.isDisplayed(), "File not updated Successfully");
		
		
	}
	
	public void updateSRURLAttachment(String urlTitle, String newURL, String newUrlTitle, WebDriver driver) throws Exception {
		
		driver.findElement(By.xpath("//a[contains(text(),'"+urlTitle+"')]/following::img[contains(@id,'cilDtl::icon')]")).click();
		//CommonUtils.hold(6);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(ServiceRequestPage.updateURLField, "Click", "", driver);
		ServiceRequestPage.updateURLField.clear();
		ServiceRequestPage.updateURLField.sendKeys(newURL);
		Log.info("Filled URL");
		CommonUtils.explicitWait(ServiceRequestPage.attachmentTitle, "Click", "", driver);
		ServiceRequestPage.attachmentTitle.clear();
		ServiceRequestPage.attachmentTitle.sendKeys(newUrlTitle);
		Log.info("Filled URL Title");
		CommonUtils.explicitWait(ServiceRequestPage.attachmentDescription, "Click", "", driver);
		ServiceRequestPage.attachmentDescription.clear();
		ServiceRequestPage.attachmentDescription.sendKeys(newUrlTitle);
		CommonUtils.explicitWait(ServiceRequestPage.updateDetailsBtn, "Click", "", driver);
		ServiceRequestPage.updateDetailsBtn.click();
		CommonUtils.explicitWait(ServiceRequestPage.updateDetailsBtn, "Click", "", driver);
		CommonUtils.hold(5);
		clickOnAttachmentOkButton(driver);
		clickOnAttachmentFolderIcon(driver);
		
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@id,'AlMgmtAVw:dciTable:tblAttach') and contains(text(),'"+newUrlTitle+"')]")).getAttribute("href"), newURL, "Updated URL mismatch.");
		
	}
	
	public void downloadSRFileAttachment(String filename, WebDriver driver) {
		CommonUtils.hold(3);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		WebElement fileLink = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following::a[contains(@id,'glDwn')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
	}
	
	public void downloadSRURLAttachment(String URL, String urlTitle, WebDriver driver) {
		WebElement urlLink = driver.findElement(By.xpath("//a[contains(text(),'"+urlTitle+"')]/following::a[contains(@id,'glDwn')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deleteSRFileAttachment(String title, WebDriver driver) throws Exception {
		
		driver.findElement(By.xpath("//span[contains(text(),'"+title+"')]/following::img[contains(@id,'cilDel::icon')]")).click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.hold(15);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//span[contains(text(),'"+title+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File is deleted successfully.");
		}
		clickOnAttachmentOkButton(driver);
	}
	
	public void deleteSRURLAttachment(String title, WebDriver driver) throws Exception {
		
		driver.findElement(By.xpath("//a[contains(text(),'"+title+"')]/following::img[contains(@id,'cilDel::icon')]")).click();
		CommonUtils.waitForPageLoad(20, driver);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(3);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//a[contains(@id,'dciTable:tblAttach') and contains(text(),'"+title+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("URL is deleted successfully.");
		}finally {
			clickOnAttachmentOkButton(driver);
		}
		
	}
	
	public void clickOnAddAttachments(WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(ServiceRequestPage.addAttachements, "Click", "", driver);
		CommonUtils.hold(5);
		ServiceRequestPage.addAttachements.click();
		CommonUtils.hold(3);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		Log.info("After clicking on add Attachments");
	}
	
	public void clickOnAttachmentFolderIcon(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(ServiceRequestPage.attachmentFolder, "Click", "", driver);
		ServiceRequestPage.attachmentFolder.click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.attachmentFolder, driver, 10);
		CommonUtils.explicitWait(ServiceRequestPage.attachmentDiagBox, "Visible", "", driver);
		CommonUtils.explicitWait(ServiceRequestPage.attachmentDiagBoxHeading, "Visible", "", driver);
		attachmentDiagBoxHeading.click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(4);
	}
	
	public void clickOnAttachmentOkButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(ServiceRequestPage.okButton, "Click", "", driver);
		ServiceRequestPage.okButton.click();
		Log.info("Clicked on OK button");
		CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.okButton, driver, 12);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(3);
	}
	
	public void saveAndCloseServiceRequest(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(ServiceRequestPage.saveandcloseSR, "Click", "", driver);
		ServiceRequestPage.saveandcloseSR.click();
		Log.info("Clicked on Save and Close button");
		CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.saveandcloseSR, driver, 12);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
	}
	
}
