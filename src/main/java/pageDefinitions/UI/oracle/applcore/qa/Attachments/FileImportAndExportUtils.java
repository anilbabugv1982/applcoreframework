package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class FileImportAndExportUtils extends FileImportAndExportUIPage{
	
	public FileImportAndExportUtils(WebDriver driver) {
		super(driver);
	}
	
//***********************************************************************************************************
	public void addFileAttachment(String filename, WebDriver driver) throws Exception {
		
		System.out.println("Adding the file attachment.");
		CommonUtils.explicitWait(FileImportAndExportUIPage.uploadAttachment, "Click", "", driver);
		CommonUtils.hold(5);
		FileImportAndExportUIPage.uploadAttachment.click();
		CommonUtils.hold(5);
		Log.info(AttachmentCommonUtils.getAttachmentFilePath(filename));
		FileImportAndExportUIPage.browseFile.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		
		CommonUtils.hold(3);
		Select sel = new Select(FileImportAndExportUIPage.accountField);
		sel.selectByValue("1");
		CommonUtils.hold(10);
		FileImportAndExportUIPage.saveAndClose.click();
		Log.info("attachment saved");
		
		CommonUtils.waitForPageLoad(driver);
		AttachmentCommonUtils.waitTillLoad(15, driver);
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File Not Uploaded Successfully");
	
	}
	
	public void verifyDownloadFile(String filename, WebDriver driver) {
		WebElement fileLink = driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").contains("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocName"), "UCM Download content File link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href").matches(".*/fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocName.*UCMFA.*download"), "UCM Download content link not present");
		
		
		driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]")).click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
	}
	
	public void searchFile(String filename, WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		FileImportAndExportUIPage.fileField.clear();
		FileImportAndExportUIPage.fileField.sendKeys(filename);
		CommonUtils.hold(5);
		FileImportAndExportUIPage.searchAttachmetns.click();
		CommonUtils.hold(5);
		//CommonUtils.explicitWait(FileImportAndExportUIPage.searchAttachmetns, "Click", "", driver); //wait for search button to be enabled after clicking on search button.
		//System.out.println("Search enabled.");
		AttachmentCommonUtils.waitTillUILoad(60, driver);
		System.out.println("Search button enabled now.");
		CommonUtils.waitForElement(FileImportAndExportUIPage.fileLink, 30, 10, driver);
		Assert.assertTrue(FileImportAndExportUIPage.fileLink.isDisplayed(), "Searched file is not Displayed");
		Assert.assertEquals(FileImportAndExportUIPage.fileLink.getText().trim(), filename, "Searched file mismtach.");
		//CommonUtils.explicitWait(FileImportAndExportUIPage.fileLink, "Click", "", driver);
		//FileImportAndExportUIPage.fileLink.click();
		//Log.info("Click on file Link");
		//CommonUtils.hold(8);
		
		//AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
	}
	
	public void deleteFile(String filename, WebDriver driver) throws Exception {
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(4);
		driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]/following::td[1]")).click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(FileImportAndExportUIPage.deleteFile, "Click", "", driver);
		System.out.println("Clicking on Delete button.");
		FileImportAndExportUIPage.deleteFile.click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(2);
		CommonUtils.explicitWait(FileImportAndExportUIPage.yes, "Click", "", driver);
		FileImportAndExportUIPage.yes.click();
		CommonUtils.waitForInvisibilityOfElement(FileImportAndExportUIPage.yes, driver, 6);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'No results found.')]")).isDisplayed(), "File not deleted successfully.");
		} catch (NoSuchElementException e) {
			Assert.assertFalse(true, "File not deleted successfully.");
		}
		
		
	}

}
