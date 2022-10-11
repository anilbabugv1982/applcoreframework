package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class FSMExportImportAttachmentUtils extends FSMExportImportAttachmentPage{

	public FSMExportImportAttachmentUtils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
	}
	
	public void editImplementationProject(String ipName, WebDriver driver) throws Exception {
		AttachmentCommonUtils.waitTillLoad(15, driver);
		driver.findElement(By.linkText(ipName)).click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		
	}
	
	public void clickNotes(String taskName, WebDriver driver) throws Exception {
		if(taskName.contains("Entities")) {
			CommonUtils.explicitWait(addEntitiesNotes, "Click", "", driver);
			addEntitiesNotes.click();
		} else if (taskName.contains("Categories")) {
			CommonUtils.explicitWait(addCategoriesNotes, "Click", "", driver);
			addCategoriesNotes.click();
		} else {
			Assert.assertTrue(false, "Invalid Task Name");
		}
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(notesPopupHeading, "Visible", "", driver);
	}
	
	public void navigateToFSMAttachment(String ipName, String taskName, WebDriver driver) throws Exception {
		editImplementationProject(ipName, driver);
		clickNotes(taskName, driver);
	}
	
	public void saveAndCloseAttachmentPopup(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(attachmentSaveAndCloseButton,"Click","", driver);			
		attachmentSaveAndCloseButton.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
	}
	
	public void fsmFileAttach(String taskName, String filename, WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(attachmentAddIcon,"Click" , "", driver);
		attachmentAddIcon.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(attachmentType,"Click" , "", driver);
		CommonUtils.selectDropDownElement(attachmentType, "File");
		CommonUtils.explicitWait(attachmentBrowse, "Click", "", driver);
		CommonUtils.hold(2);
		attachmentBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		AttachmentCommonUtils.waitTillFileUpload(driver);
		CommonUtils.explicitWait(attachmentUpdateBtn,"Click","", driver);
		saveAndCloseAttachmentPopup(driver);
		clickNotes(taskName, driver);
				
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveFile') and contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File attached Successfully");
	}
	
	public void fsmTextAttach(String taskName, String text, String textTitle, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(attachmentAddIcon,"Click" , "", driver);
		attachmentAddIcon.click();
		
		CommonUtils.explicitWait(attachmentType, "Click", "", driver);
		CommonUtils.selectDropDownElement(attachmentType, "Text");
		Log.info("Selection of Text Type from first row");
		AttachmentCommonUtils.waitTillLoad(5, driver);
		
		CommonUtils.explicitWait(textarea, "Click", "", driver);
		textarea.clear();
        textarea.sendKeys(text);
        
        attachmentTitle.clear();
		attachmentTitle.sendKeys(textTitle);
		
		attachmentDescription.clear();
		attachmentDescription.sendKeys("Desc"+textTitle);
		
		saveAndCloseAttachmentPopup(driver);
		clickNotes(taskName, driver);
		
		text = text.substring(0, Math.min(text.length(), 30));
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveText') and contains(text(),'"+text+"')]")).isDisplayed(), "Added text mismatch.");
		
	}
	
	public void fsmURLAttach(String taskName, String url, String urlTitle, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(attachmentAddIcon,"Click" , "", driver);
		attachmentAddIcon.click();
		
		CommonUtils.explicitWait(attachmentType, "Click", "", driver);
		CommonUtils.selectDropDownElement(attachmentType, "URL");
		Log.info("Selection of URL Type from first row");
		AttachmentCommonUtils.waitTillLoad(5, driver);
		
		CommonUtils.explicitWait(urlField, "Click", "", driver);
		urlField.clear();
        urlField.sendKeys(url);
        
        attachmentTitle.clear();
		attachmentTitle.sendKeys(urlTitle);
		
		attachmentDescription.clear();
		attachmentDescription.sendKeys("Desc"+urlTitle);
		
		saveAndCloseAttachmentPopup(driver);
		clickNotes(taskName, driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveURL') and contains(text(),'"+url+"')]")).isDisplayed(), "Added URL mismatch.");
		
	}
	
	
	
	public void updateFSMFileAttachment(String taskName, String filename, String newFilename, WebDriver driver) throws Exception {
		
	 	//Add File attachment
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(attachmentAddIcon,"Click" , "", driver);
		attachmentAddIcon.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		//CommonUtils.hold(2);
		CommonUtils.explicitWait(attachmentType,"Click" , "", driver);
		CommonUtils.selectDropDownElement(attachmentType, "File");
		CommonUtils.explicitWait(attachmentBrowse, "Click", "", driver);
		CommonUtils.hold(2);
		attachmentBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		AttachmentCommonUtils.waitTillFileUpload(driver);
		CommonUtils.explicitWait(attachmentUpdateBtn,"Click","", driver);
		
		
		//Update File attachment
	 	WebElement updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'desktopFile::upBtn')]"));
		updateButton.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		//CommonUtils.hold(10);
		//System.out.println("clicking on Update second time");
		//updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'desktopFile::upBtn')]"));
		//updateButton.click();
		//AttachmentCommonUtils.waitTillLoad(8, driver);
		//CommonUtils.hold(10);
		CommonUtils.explicitWait(attachmentBrowse, "Click", "", driver);
		attachmentBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		updateFileOkBtn.click();
		AttachmentCommonUtils.waitTillFileUpload(driver);
		AttachmentCommonUtils.waitTillLoad(40, driver);
		
		WebElement updateTitle = driver.findElement(By.xpath("//span[text()='"+newFilename+"']/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newFilename);
		
		WebElement updateDescription = driver.findElement(By.xpath("//span[text()='"+newFilename+"']/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys("Desc"+newFilename);
		
		saveAndCloseAttachmentPopup(driver);
		clickNotes(taskName, driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveFile') and contains(text(),'"+newFilename+"')]"))
				.isDisplayed(), "File not updated Successfully");
		
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='"+newFilename+"']/following::input[contains(@id,'TitleInputText::content')]"))
				.getAttribute("value"), newFilename, "File Title is not updated.");
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='"+newFilename+"']/following::input[contains(@id,'DescriptionInputText::content')]"))
				.getAttribute("value"), "Desc"+newFilename, "File Description is not updated.");
		
		
	}
 
 	public void updateFSMTextAttachment(String taskName, String text, String newTitle, String newDesc, WebDriver driver) throws Exception {
		
		WebElement updateTitle = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveText') and contains(text(),'"+text+"')]/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newTitle);
		Log.info("Updated Text Title");
		
		WebElement updateDescription = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveText') and contains(text(),'"+text+"')]/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys(newDesc);
		
		saveAndCloseAttachmentPopup(driver);
		clickNotes(taskName, driver);
		
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveText') and contains(text(),'"+text+"')]/following::input[contains(@id,'TitleInputText::content')]"))
				.getAttribute("value"), newTitle, "Text Title is not updated.");
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveText') and contains(text(),'"+text+"')]/following::input[contains(@id,'DescriptionInputText::content')]"))
				.getAttribute("value"), newDesc, "Text Description is not updated.");
		
	}
 	
 	public void updateFSMURLAttachment(String taskName, String url, String newTitle, String newDesc, WebDriver driver) throws Exception {
		
		WebElement updateTitle = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveURL') and contains(text(),'"+url+"')]/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newTitle);
		Log.info("Updated Text Title");
		
		WebElement updateDescription = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveURL') and contains(text(),'"+url+"')]/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys(newDesc);
		
		saveAndCloseAttachmentPopup(driver);
		clickNotes(taskName, driver);
		
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveURL') and contains(text(),'"+url+"')]/following::input[contains(@id,'TitleInputText::content')]"))
				.getAttribute("value"), newTitle, "URL Title is not updated.");
		Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@id,'glFileLiveURL') and contains(text(),'"+url+"')]/following::input[contains(@id,'DescriptionInputText::content')]"))
				.getAttribute("value"), newDesc, "URL Description is not updated.");
		
	}

	
	public void downloadFSMFileAttachment(String filename, WebDriver driver) throws Exception {
		
		WebElement fileLink = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveFile') and contains(text(),'"+filename+"')]"));
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
	}
	

	public void downloadFSMTextAttachment(String text, String textTitle, WebDriver driver) throws Exception {
		
		//clickOnMoreAttachment(driver);
		
		text = text.substring(0, Math.min(text.length(), 30));
		WebElement fileLink = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveText') and contains(text(),'"+text+"')]"));
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isTextFileDownloadedSuccessfully(textTitle);
	}
	
	public void downloadFSMURLAttachment(String URL, WebDriver driver) {
		WebElement urlLink = driver.findElement(By.xpath("//a[contains(@id,'glFileLiveURL') and contains(text(),'"+URL+"')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deleteFSMAttachment(String name, WebDriver driver) throws Exception {
		
		//CommonUtils.hold(3);
		driver.findElement(By.xpath("//a[contains(@id,'glFileLive') and contains(text(),'"+name+"')]/following::span[contains(@id,'otLstUpdBy')]")).click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(attachmentDeleteIcon, "Click", "", driver);
		attachmentDeleteIcon.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		CommonUtils.explicitWait(warningYesBtn, "Click", "", driver);
		warningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//a[text()='"+name+"']")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File/URL is deleted successfully.");
		}
		
	}

	

}
