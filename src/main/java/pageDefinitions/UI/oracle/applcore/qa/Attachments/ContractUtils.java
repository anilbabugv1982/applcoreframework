package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import java.io.File;

//Author

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;

public class ContractUtils extends ContractPage{
	
	public static String filename = "Applying patch.txt";

	public ContractUtils(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

//*****************************************************************************************************************************
	public void createContract(WebDriver driver,String value) throws Exception {
		AttachmentCommonUtils.waitTillUILoad(25, driver);
		CommonUtils.hold(4);
		CommonUtils.explicitWait(create, "Click", "", driver);
		CommonUtils.hold(3);
		create.click();
		Log.info("Click on Create Icon");
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(typedropdown, "Click", "", driver);
		typedropdown.click();
		Log.info("Click on Type DropDown");
		CommonUtils.explicitWait(searchlink, "Click", "", driver);
		searchlink.click();
		Log.info("Click on Search Link");
		AttachmentCommonUtils.waitTillUILoad(10, driver);
		CommonUtils.explicitWait(name, "Click", "", driver);
		name.sendKeys(value);
		Log.info("Enter Type");
		CommonUtils.explicitWait(searchbutton, "Click", "", driver);
		searchbutton.click();
		Log.info("Click on Search Button");
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(typerow, "Click", "", driver);
		typerow.click();
		Log.info("Click on Type row");
		CommonUtils.explicitWait(ok, "Click", "", driver);
		ok.click();
		Log.info("Click on Ok Button");
		CommonUtils.waitForInvisibilityOfElement(ok, driver, 15);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(number, "Click", "", driver);
		//CommonUtils.waitForElement(number,10,5, driver);
		//CommonUtils.hold(5);
		number.sendKeys(time());
		CommonUtils.explicitWait(primaryparty, "Click", "", driver);
		primaryparty.sendKeys("World of Business");
		primaryparty.sendKeys(Keys.TAB);
		AttachmentCommonUtils.waitTillUILoad(12, driver);
		CommonUtils.explicitWait(SaveandContinue, "Click", "", driver);
		SaveandContinue.click();
		CommonUtils.waitForInvisibilityOfElement(SaveandContinue, driver, 30);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.waitForElement(Documents, 30,10, driver);
		CommonUtils.explicitWait(Documents, "Click", "", driver);
        Documents.click();
        AttachmentCommonUtils.waitTillUILoad(15, driver);
		
	}
	
//******************************************************************************************************************************	
	public static String time () {
		
		 Date dNow = new Date();	
		 SimpleDateFormat ft = new SimpleDateFormat("mmss");
	     String datetime = ft.format(dNow);
	     return datetime;
		
		}
	
//****************************************************************************************************************

	public static String filepath() {
		File file = new File("src/test/resources/AttachmentFiles/"+filename); 
		System.out.println(file.getAbsolutePath());

		return file.getAbsolutePath();
	}
	
	
//*****************************************************************************************************************
	//Ashish Pareek

	public void contractDocumentFileAttach(String filename, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(createcontractDocuments, "Click", "", driver);
		createcontractDocuments.click();
		Log.info("Click on Add Contract Documents");
		//CommonUtils.hold(10);
		CommonUtils.explicitWait(contractDocumentsChooseFile, "Click", "", driver);
		CommonUtils.hold(3);
		
		CommonUtils.selectDropDownElement(contractDocumentsTypeSelect, "File");
		CommonUtils.explicitWait(contractDocumentsChooseFile, "Click", "", driver);
		contractDocumentsChooseFile.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		CommonUtils.waitForInvisibilityOfElement(contractDocumentsChooseFile, driver, 15);
		CommonUtils.explicitWait(contractDocumentsUpdateBtn, "Click", "", driver);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::span[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File Uploaded Successfully");
	}
	
	public void updateContractDocumentFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		WebElement updateButton = driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::span[text()='"+filename+"']/following-sibling::button[contains(@id,'desktopFile::upBtn')]"));
		AttachmentCommonUtils.clickOnUpdateButton(updateButton, driver);
		CommonUtils.explicitWait(ContractPage.contractDocumentsChooseFile, "Click", "", driver);
		ContractPage.contractDocumentsChooseFile.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		ContractPage.updateFileOkBtn.click();
		AttachmentCommonUtils.waitTillFileUpload(driver);
		WebElement updateTitle = driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::span[text()='"+newFilename+"']/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newFilename);

		WebElement updateDescription = driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::span[text()='"+newFilename+"']/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys("Desc"+newFilename);
		saveContract(driver);
		CommonUtils.hold(3);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[contains(@id,'glFileLiveFile') and text()='"+newFilename+"']"))
				.isDisplayed(), "File not updated Successfully");
		
	}
	
	public void contractDocumentURLAttach(String URL, String urlTitle, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(createcontractDocuments, "Click", "", driver);
		createcontractDocuments.click();
		Log.info("Click on Add Contract Documents");
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.hold(10);
		
		CommonUtils.selectDropDownElement(contractDocumentsTypeSelect, "URL");
		CommonUtils.explicitWait(contractDocumentsUrlField, "Click", "", driver);
		contractDocumentsUrlField.clear();
		contractDocumentsUrlField.sendKeys(URL);
		//contractDocumentsUrlField.sendKeys(Keys.TAB);
		contractDocumentsTitle.clear();
		contractDocumentsTitle.sendKeys(urlTitle);
		saveContract(driver);
		
		String urlHref = URL;
		if(URL.startsWith("file:/") && !(URL.startsWith("file:///"))) {
			urlHref = URL.replace("file:/", "file:///");
		}
		System.out.println("Added href: "+driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[contains(text(),'"+URL+"')]")).getAttribute("href"));
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[contains(text(),'"+URL+"')]")).getAttribute("href").contains(urlHref), "Added URL mismatch.");
		
	}
	
	public void downloadContractDocumentFileAttachment(String filename, WebDriver driver) {
		CommonUtils.hold(3);
		WebElement fileLink = driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[text()='"+filename+"']"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
	}
	
	public void downloadContractDocumentURLAttachment(String URL, WebDriver driver) {
		WebElement urlLink = driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[contains(text(),'"+URL+"')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deleteContractDocumentAttachment(String name, WebDriver driver) throws Exception {
		
		CommonUtils.hold(3);
		driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[text()='"+name+"']/following::span[contains(@id,'otLstUpdBy')]")).click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.explicitWait(contractDocumentDeleteIcon, "Click", "", driver);
		contractDocumentDeleteIcon.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		//CommonUtils.hold(2);
		CommonUtils.explicitWait(warningYesBtn, "Click", "", driver);
		warningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.hold(3);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//div[contains(@id,'sdh1')]/descendant::a[text()='"+name+"']")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File/URL is deleted successfully.");
		}
		
	}
	
	

//*****************************************************************************************************************************************
//Supporting
//*********************************************************************************************************************************************
	public void supportingDocumentFileAttach(String filename, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(createsupportcontractDocuments, "Click", "", driver);
		createsupportcontractDocuments.click();
		Log.info("Click on Add Supporting Documents");
		//CommonUtils.hold(10);
		CommonUtils.explicitWait(supportingDocumentsChooseFile, "Click", "", driver);
		CommonUtils.hold(3);
		
		CommonUtils.selectDropDownElement(supportingDocumentsTypeSelect, "File");
		CommonUtils.explicitWait(supportingDocumentsChooseFile, "Click", "", driver);
		supportingDocumentsChooseFile.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		CommonUtils.waitForInvisibilityOfElement(supportingDocumentsChooseFile, driver, 15);
		CommonUtils.explicitWait(supportingDocumentsUpdateBtn, "Click", "", driver);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::span[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File Uploaded Successfully");
	}
	
	public void supportingDocumentURLAttach(String URL, String urlTitle, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(createsupportcontractDocuments, "Click", "", driver);
		createsupportcontractDocuments.click();
		Log.info("Click on Add Supporting Documents");
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.hold(10);
		
		CommonUtils.selectDropDownElement(supportingDocumentsTypeSelect, "URL");
		CommonUtils.explicitWait(supportingDocumentsUrlField, "Click", "", driver);
		supportingDocumentsUrlField.sendKeys(URL);
		//contractDocumentsUrlField.sendKeys(Keys.TAB);
		supportingDocumentsTitle.sendKeys(urlTitle);
		saveContract(driver);
		
		String urlHref = URL;
		if(URL.startsWith("file:/") && !(URL.startsWith("file:///"))) {
			urlHref = URL.replace("file:/", "file:///");
		}
		System.out.println("Added href: "+driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[contains(text(),'"+URL+"')]")).getAttribute("href"));
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[contains(text(),'"+URL+"')]")).getAttribute("href").contains(urlHref), "Added URL mismatch.");
		
	}
	
	public void updateSupportingDocumentFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		WebElement updateButton = driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::span[text()='"+filename+"']/following-sibling::button[contains(@id,'desktopFile::upBtn')]"));
		AttachmentCommonUtils.clickOnUpdateButton(updateButton, driver);
		CommonUtils.explicitWait(ContractPage.supportingDocumentsChooseFile, "Click", "", driver);
		ContractPage.supportingDocumentsChooseFile.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		ContractPage.updateFileOkBtn.click();
		AttachmentCommonUtils.waitTillFileUpload(driver);
		WebElement updateTitle = driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::span[text()='"+newFilename+"']/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newFilename);
		
		WebElement updateDescription = driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::span[text()='"+newFilename+"']/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys("Desc"+newFilename);
		saveContract(driver);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[contains(@id,'glFileLiveFile') and text()='"+newFilename+"']"))
				.isDisplayed(), "File not updated Successfully");
		
	}
	
	
	
	public void downloadSupportingDocumentFileAttachment(String filename, WebDriver driver) {
		CommonUtils.hold(3);
		//sdh2
		WebElement fileLink = driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[text()='"+filename+"']"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
	}
	
	public void downloadSupportingDocumentURLAttachment(String URL, WebDriver driver) {
		WebElement urlLink = driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[contains(text(),'"+URL+"')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deleteSupportingDocumentAttachment(String name, WebDriver driver) throws Exception {
		
		CommonUtils.hold(3);
		driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[text()='"+name+"']/following::span[contains(@id,'otLstUpdBy')]")).click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.explicitWait(supportingDocumentDeleteIcon, "Click", "", driver);
		supportingDocumentDeleteIcon.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		//CommonUtils.hold(2);
		CommonUtils.explicitWait(warningYesBtn, "Click", "", driver);
		warningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//div[contains(@id,'sdh2')]/descendant::a[text()='"+name+"']")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File/URL is deleted successfully.");
		}
		
	}
	
//************************************************************************************************************************************************	
	
	public void saveContract(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(ContractPage.Save, "Click", "", driver);
		ContractPage.Save.click();
		CommonUtils.hold(4);
		final int TIMEOUT = 20000;  // 20 seconds
		long targetTime = System.currentTimeMillis() + TIMEOUT;
		while((System.currentTimeMillis() < targetTime)) {
			try {
				if (!(driver.findElement(By.xpath("//div[contains(@id,'HomeApplicationPanel:APsv2')]")).getAttribute("class").contains("p_AFDisabled p_AFBusy"))) {
			        break;
			    }
			} catch(StaleElementReferenceException e) {
				System.out.println("in catch");
				if (!(driver.findElement(By.xpath("//div[contains(@id,'HomeApplicationPanel:APsv2')]")).getAttribute("class").contains("p_AFDisabled p_AFBusy"))) {
			        break;
			    }
			}
		    
		}
		AttachmentCommonUtils.waitTillLoad(20, driver);
		CommonUtils.hold(2);
	}
	
	
	

//*************************************************************************************************************************
	



}
