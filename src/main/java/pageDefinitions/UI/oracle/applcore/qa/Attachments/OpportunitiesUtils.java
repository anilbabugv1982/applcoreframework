package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.By;

//Author

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class OpportunitiesUtils extends OpportunitiesPage {
//*****************************************************************************************************************************
	public OpportunitiesUtils(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
//***************************************************************************************************************************
   public void CreateOpportunity(WebDriver driver) throws Exception {
	   CommonUtils.waitForJStoLoad(driver, 150);
	   CommonUtils.hold(10);
	   AttachmentCommonUtils.waitTillUILoad(50, driver);
	   Log.info("Wait for Page to Load Completely");
	   CommonUtils.explicitWait(createOpportunityButton, "Click", "",driver);
	   //CommonUtils.waitForElement(createOpportunityButton, 10, 5, driver);
	   createOpportunityButton.click();
	   Log.info("Clicked on Create Opportunity button");
	   //CommonUtils.waitForPageLoad(driver);
	   AttachmentCommonUtils.waitTillUILoad(100, driver);
	   CommonUtils.explicitWait(NameTextField, "Click", "", driver);
	   CommonUtils.explicitWait(SaveAndContinueButton, "Click", "", driver);
	   //CommonUtils.waitForElement(NameTextField, 10, 1, driver);
	   //CommonUtils.explicitWait(NameTextField, "Visible", "",driver);
	   //CommonUtils.hold(15);
	   Log.info("Fill opportunity name");
	   NameTextField.sendKeys("OPPNAME"+CommonUtils.uniqueId());
	   //CommonUtils.explicitWait(NameTextField, "Visible", "",driver);
	   CommonUtils.explicitWait(SaveAndContinueButton, "Click", "", driver);
	   //CommonUtils.waitForElement(SaveAndContinueButton, 20, 5, driver);
	   //SaveAndContinueButton.click();
	   CommonUtils.clickWithJavaScript(SaveAndContinueButton, driver);
	   Log.info("Clicked on save and continue");
	   AttachmentCommonUtils.waitTillUILoad(80, driver);
	   //CommonUtils.waitForPageLoad(driver);
	   CommonUtils.hold(5);
	
   }
//*****************************************************************************************************************************
   
   public void addOppFileAttachment(String filename, WebDriver driver) throws Exception {
	   clickOnManageAttachmentsButton(driver);
		Log.info("Updating attachment through type File");
		CommonUtils.explicitWait(OpportunitiesPage.browse, "Click", "", driver);
		//String path = System.getProperty("user.dir");
		//System.out.println(path);
		//String FilePath = path + filePath;
		//Opportunities.browse.sendKeys(FilePath);
		OpportunitiesPage.browse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		CommonUtils.hold(10);
		CommonUtils.explicitWait(OpportunitiesPage.updateFileBtn, "Click", "", driver);
		clickOnAttachmentOkButton(driver);
		//CommonUtils.hold(5);
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File not Uploaded Successfully");
		
	}
   
   public void addOppURLAttachment(String URL, WebDriver driver) throws Exception {
	   
	   clickOnManageAttachmentsButton(driver);
		Log.info("Adding attachment through Type URL");
		CommonUtils.explicitWait(OpportunitiesPage.AttachDrpDown, "Click", "", driver);
		OpportunitiesPage.AttachDrpDown.click();
		Log.info("Click on the Attachment Type Drop Down");
		CommonUtils.explicitWait(OpportunitiesPage.typeSelectURL, "Click", "", driver);
		OpportunitiesPage.typeSelectURL.click();
		Log.info("Select URL form dropdown");
		CommonUtils.waitForElement(OpportunitiesPage.URLTextField, driver);
		OpportunitiesPage.URLTextField.clear();
		OpportunitiesPage.URLTextField.sendKeys(URL);
		Log.info("Filled URL");
		//CommonUtils.hold(10);
		clickOnAttachmentOkButton(driver);
		
		String urlInnerText = URL.substring(0, Math.min(URL.length(), 29));
		System.out.println(urlInnerText);
		if(URL.startsWith("file:/") && !(URL.startsWith("file:///"))) {
			URL = URL.replace("file:/", "file:///");
		}
		System.out.println("Added href: "+driver.findElement(By.xpath("//a[contains(text(),'"+urlInnerText+"')]")).getAttribute("href"));
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+urlInnerText+"')]")).getAttribute("href").contains(URL), "Added URL mismatch.");	
		
	}
	
   
   public void updateOppFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		
		clickOnMoreAttachment(driver);
		
		CommonUtils.explicitWait(OpportunitiesPage.updateFileBtn, "Click", "", driver);
		WebElement updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'fifPopup::upBtn')]"));
		AttachmentCommonUtils.clickOnUpdateButton(updateButton, driver);
		
		//CommonUtils.clickWithJavaScript(updateButton, driver);
		//CommonUtils.waitForPageLoad(driver);
		//CommonUtils.hold(8);
		
		CommonUtils.explicitWait(OpportunitiesPage.updateBrowse, "Click", "", driver);
		OpportunitiesPage.updateBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		CommonUtils.hold(5);
		CommonUtils.explicitWait(OpportunitiesPage.UpdatePopupOkBtn, "Click", "", driver);
		OpportunitiesPage.UpdatePopupOkBtn.click();
		CommonUtils.waitForInvisibilityOfElement(OpportunitiesPage.UpdatePopupOkBtn, driver, 10);
		CommonUtils.explicitWait(OpportunitiesPage.updateFileBtn, "Click", "", driver);
		//CommonUtils.hold(6);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		clickOnAttachmentOkButton(driver);
		clickOnMoreAttachment(driver);
		
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'"+newFilename+"')]"))
					.isDisplayed(), "File updated Successfully");
		} finally {
			clickOnAttachmentOkButton(driver);
		}
		
	}
   
   public void updateOppURLAttachment(String URL, String newURL, WebDriver driver) throws Exception {
		
		clickOnMoreAttachment(driver);
		
	    WebElement existingURLfield = driver.findElement(By.xpath("//input[@value='"+URL+"']"));
	    existingURLfield.clear();
	    existingURLfield.sendKeys(newURL);
		Log.info("Updated URL");
		//CommonUtils.hold(10);
		clickOnAttachmentOkButton(driver);
		//CommonUtils.hold(5);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+newURL+"')]")).getAttribute("href").contains(newURL), "Updated URL mismatch.");
			
	}
   
   public void downloadOppFileAttachment(String filename, WebDriver driver) throws Exception {
	   
	   //hoverOverMoreAttachment(driver);
	   AttachmentCommonUtils.waitTillUILoad(15, driver);
	   CommonUtils.explicitWait(OppSaveIcon, "Click", "", driver);
	   OppSaveIcon.click();
	   CommonUtils.explicitWait(OppSaveIcon, "Click", "", driver);
	   clickOnMoreAttachment(driver);
	   CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveFile') and contains(text(),'"+filename+"')]")), "Click", "", driver);
	   WebElement fileLink = driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveFile') and contains(text(),'"+filename+"')]"));
		//Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]")).getAttribute("href").contains("download"), "Download File link not present");
		//Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]")).getAttribute("href").contains("&Id="), "Download File link ID not present");
		//Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]")).getAttribute("href").startsWith("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content File link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(10);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
   }
   
   public void downloadOppURLAttachment(String URL, WebDriver driver) {
	   WebElement urlLink = driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveURL') and contains(text(),'"+URL+"')]"));
	   AttachmentCommonUtils.validateURL(urlLink, URL, driver);
		
				
	}
   
   public void deleteOppFileAttachment(String filename, WebDriver driver) throws Exception {
		
	   //clickOnMoreAttachment(driver);
	   WebElement file = driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveFile') and contains(text(),'"+filename+"')]"));
		driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveFile') and contains(text(),'"+filename+"')]/following::img[contains(@id,'fatdDel::icon')]")).click();
		//CommonUtils.hold(10);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveFile') and contains(text(),'"+filename+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File is deleted successfully.");
		}
	}
	
	public void deleteOppURLAttachment(String URL, WebDriver driver) throws Exception {
		
		driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveURL') and contains(text(),'"+URL+"')]/following::img[contains(@id,'fatdDel::icon')]")).click();
		CommonUtils.hold(7);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//a[contains(@id,'fglPopFileLiveURL') and contains(text(),'"+URL+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("URL is deleted successfully.");
		}finally {
			clickOnAttachmentOkButton(driver);
		}
		
	}
	
	
	public void clickOnMoreAttachment(WebDriver driver ) throws Exception {
		try {
			CommonUtils.explicitWait(OpportunitiesPage.moreAttachmentLink, "Click", "", driver);
			//Actions actions = new Actions(driver);
			//actions.moveToElement(Opportunities.moreAttachmentLink).click().build().perform();
			OpportunitiesPage.moreAttachmentLink.click();
			CommonUtils.explicitWait(attachmentDiagBox, "Click", "", driver);
			CommonUtils.hold(5); //holding if blocking attachment pane becomes visible.
			//attachmentDiagBox.click();
			try {
				System.out.println("in try1");
				if(driver.findElement(By.xpath("//td[@class='AFPopupSelectorContent']")).isDisplayed()) {
					System.out.println("in if.");
					attachmentDiagBox.click();
					CommonUtils.hold(3);
				}
			} catch(Exception e) {
				System.out.println("No blocking element.");
			}
			
		} catch (NoSuchElementException e1) {
			CommonUtils.explicitWait(OpportunitiesPage.manageAttachmentsButton, "Click", "", driver);
			OpportunitiesPage.manageAttachmentsButton.click();
			CommonUtils.explicitWait(attachmentDiagBox, "Click", "", driver);
			attachmentDiagBox.click();
			driver.findElement(By.xpath("//img[contains(@id,'fatdDel::icon')]")).click();
			CommonUtils.hold(6);
		}
		AttachmentCommonUtils.waitTillUILoad(15, driver);
	}
	
	public void hoverOverMoreAttachment(WebDriver driver ) throws Exception {
		
			CommonUtils.explicitWait(OpportunitiesPage.moreAttachmentLink, "Click", "", driver);
			Actions actions = new Actions(driver);
			actions.moveToElement(OpportunitiesPage.moreAttachmentLink).perform();
			
	}
	
	public void clickOnAttachmentOkButton(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(OpportunitiesPage.okButton, "Click", "", driver);
		OpportunitiesPage.okButton.click();
		Log.info("Clicked on OK button");
		CommonUtils.waitForInvisibilityOfElement(OpportunitiesPage.okButton, driver, 10);
		AttachmentCommonUtils.waitTillUILoad(10, driver);
		CommonUtils.hold(2);
	}
	
	public void clickOnManageAttachmentsButton(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(OpportunitiesPage.manageAttachmentsButton, "Click", "", driver);
		OpportunitiesPage.manageAttachmentsButton.click();
		Log.info("Clicked on manage attachments");
		CommonUtils.explicitWait(OpportunitiesPage.attachmentDiagBox, "Visible", "", driver);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(2);
	}
	
	public void saveAndCloseOpportunities(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(OpportunitiesPage.saveAndCloseOpp, "Click", "", driver);
		OpportunitiesPage.saveAndCloseOpp.click();
		Log.info("Clicked on Save and Close button");
		CommonUtils.waitForInvisibilityOfElement(OpportunitiesPage.saveAndCloseOpp, driver, 10);
		AttachmentCommonUtils.waitTillUILoad(15, driver);
	}
	
	
	
}
