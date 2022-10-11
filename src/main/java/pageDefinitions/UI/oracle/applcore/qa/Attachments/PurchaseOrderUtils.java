package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class PurchaseOrderUtils extends PurchaseOrderPage{

	public PurchaseOrderUtils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
	}
	
	
	public void createOrder(WebDriver driver) throws Exception {
		
		AttachmentCommonUtils.waitTillLoad(30, driver);
		CommonUtils.explicitWait(tasksPanelDrawerIcon, "Click", "", driver);
		tasksPanelDrawerIcon.click();
		CommonUtils.explicitWait(createOrder, "Click", "", driver);
		createOrder.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.explicitWait(createOrderPopupWindowtitle, "Visible", "", driver);
		CommonUtils.explicitWait(PurchaseOrderPage.createOrderCreateButton, "Click", "", driver);
		PurchaseOrderPage.createOrderCreateButton.click();
		AttachmentCommonUtils.waitTillLoad(70, driver);
		clickOnNotesAndAttachments(driver);

	}
	
	public void purchaseOrderFileAttach(String filename, WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		clickOnManageAttachments(driver);
		CommonUtils.explicitWait(attachmentType,"Click" , "", driver);
		CommonUtils.selectDropDownElement(attachmentType, "File");
		CommonUtils.selectDropDownElement(attachmentCategory, "To Supplier");
		CommonUtils.hold(5);	
		CommonUtils.explicitWait(attachmentBrowse, "Click", "", driver);
		CommonUtils.hold(2);
		attachmentBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		//CommonUtils.hold(5);
		AttachmentCommonUtils.waitTillFileUpload(driver);
		clickOnAttachmentOkButton(driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File not Uploaded Successfully");
	}
	
	public void purchaseOrderTextAttach(String text, String textTitle, WebDriver driver) throws Exception {
		clickOnManageAttachments(driver);
		
		CommonUtils.explicitWait(typeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(typeSelect, "Text");
		CommonUtils.explicitWait(textarea, "Click", "", driver);
		Log.info("Selection of Text Type from first row");
		CommonUtils.hold(5);
        textarea.sendKeys(text);
		//CommonUtils.hold(5);
		attachmentTitle.sendKeys(textTitle);
		clickOnAttachmentOkButton(driver);
		CommonUtils.explicitWait(lastAttachedText,"Visible","", driver);
		Assert.assertEquals(lastAttachedText.getText().trim(), textTitle, "Added text title mismatch.");
		
		Actions action = new Actions(driver);
		action.moveToElement(lastAttachedText).click().build().perform();
		CommonUtils.hold(4);
		lastAttachedText.click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(ReviewText,"Click","", driver);
		Assert.assertEquals(ReviewText.getText().trim(), text, "Added text mismatch.");
		
		
	}
	
	public void purchaseOrderURLAttach(String url, String urlTitle, WebDriver driver) throws Exception {
		clickOnManageAttachments(driver);
		CommonUtils.explicitWait(typeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(typeSelect, "URL");
		CommonUtils.explicitWait(urlField, "Click", "", driver);
		Log.info("Selection of URL Type from first row");
		CommonUtils.hold(5);
		urlField.sendKeys(url);
		attachmentTitle.sendKeys(urlTitle);
		clickOnAttachmentOkButton(driver);
		
		CommonUtils.explicitWait(lastAttachedURL,"Click","", driver);
		if(url.startsWith("file:/") && !(url.startsWith("file:///"))) {
			url = url.replace("file:/", "file:///");
		}
		Assert.assertTrue(lastAttachedURL.getAttribute("href").contains(url), "Added URL mismatch.");
		
	}
	
	public void updatePurchaseOrderFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		
	 	clickOnMoreAttachment(driver);
	 	WebElement updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'ifPopup::upBtn')]"));
		AttachmentCommonUtils.clickOnUpdateButton(updateButton, driver);
		CommonUtils.explicitWait(UpdateBrowse, "Click", "", driver);
		UpdateBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		CommonUtils.hold(2);
		updateFileOkBtn.click();
		AttachmentCommonUtils.waitTillFileUpload(driver);
		AttachmentCommonUtils.waitTillLoad(12, driver);
		
		WebElement updateTitle = driver.findElement(By.xpath("//span[text()='"+newFilename+"']/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newFilename);
		
		WebElement updateDescription = driver.findElement(By.xpath("//span[text()='"+newFilename+"']/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys("Desc"+newFilename);
		clickOnAttachmentOkButton(driver);
		clickOnMoreAttachment(driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'ifPopup::upVal') and contains(text(),'"+newFilename+"')]"))
				.isDisplayed(), "File not updated Successfully");
		
		
	}
 
 	public void updatePurchaseOrderTextAttachment(String text, String newText, String newTextTitle, WebDriver driver) throws Exception {
		
		WebElement textInput = driver.findElement(By.xpath("//textarea[contains(text(),'"+text+"')]"));
		textInput.clear();
		textInput.sendKeys(newText);
		Log.info("Updated Text");
		
		WebElement updateTitle = driver.findElement(By.xpath("//textarea[contains(text(),'"+text+"')]/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newTextTitle);
		
		WebElement updateDescription = driver.findElement(By.xpath("//textarea[contains(text(),'"+text+"')]/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys("Desc"+newTextTitle);
		clickOnAttachmentOkButton(driver);
		clickOnMoreAttachment(driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//textarea[contains(text(),'"+newText+"')]")).isDisplayed(), "Updated Text mismatch.");
		
	}

	
	public void updatePurchaseOrderURLAttachment(String url, String newURL, String newUrlTitle, WebDriver driver) throws Exception {
		
		WebElement urlInput = driver.findElement(By.xpath("//input[contains(@value,'"+url+"')]"));
		urlInput.clear();
		urlInput.sendKeys(newURL);
		Log.info("Updated URL");
		
		WebElement updateTitle = driver.findElement(By.xpath("//input[contains(@value,'"+url+"')]/following::input[contains(@id,'TitleInputText::content')]"));
		updateTitle.clear();
		updateTitle.sendKeys(newUrlTitle);
		
		WebElement updateDescription = driver.findElement(By.xpath("//input[contains(@value,'"+url+"')]/following::input[contains(@id,'DescriptionInputText::content')]"));
		updateDescription.clear();
		updateDescription.sendKeys("Desc"+newUrlTitle);
		clickOnAttachmentOkButton(driver);
		clickOnMoreAttachment(driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@value,'"+newURL+"')]")).isDisplayed(), "Updated URL mismatch.");
		clickOnAttachmentOkButton(driver);
		
	}
	
		
	public void downloadPurchaseOrderFileAttachment(String filename, WebDriver driver) throws Exception {
		
		clickOnMoreAttachment(driver);
		WebElement fileLink = driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLiveFile')]/a[contains(text(),'"+filename+"')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
	}
	

	public void downloadPurchaseOrderTextAttachment(String textname, String textTitle, WebDriver driver) throws Exception {
		
		//clickOnMoreAttachment(driver);
		
		textname = textname.substring(0, Math.min(textname.length(), 29));
		WebElement fileLink = driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLiveText')]/a[contains(text(),'"+textname+"')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isTextFileDownloadedSuccessfully(textTitle);
	}
	
	public void downloadPurchaseOrderURLAttachment(String URL, WebDriver driver) {
		WebElement urlLink = driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLiveURL')]/a[contains(text(),'"+URL+"')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deletePurchaseOrderAttachment(String name, WebDriver driver) throws Exception {
		
		//CommonUtils.hold(3);
		driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLive')]/a[contains(text(),'"+name+"')]/following::span[contains(@id,'otPopLastBy')]")).click();
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

	public void clickOnNotesAndAttachments(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(notesAndAttachmentsTab, "Click", "", driver);
		CommonUtils.scrollToElement(notesAndAttachmentsTab, driver);
		notesAndAttachmentsTab.click();
		Log.info("Clicked on Notes and Attachments tab.");
		CommonUtils.explicitWait(manageAttachments, "Click", "", driver);
	}
	
	public void clickOnManageAttachments(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(manageAttachments, "Click", "", driver);
		CommonUtils.scrollToElement(manageAttachments, driver);
		manageAttachments.click();
		CommonUtils.explicitWait(attachmentDiagBox, "Visible", "", driver);
		AttachmentCommonUtils.waitTillLoad(10, driver);
	}
	
	
	public void clickOnMoreAttachment(WebDriver driver ) throws Exception {
		try {
			CommonUtils.explicitWait(moreAttachmentLink, "Click", "", driver);
			//Actions actions = new Actions(driver);
			//actions.moveToElement(Opportunities.moreAttachmentLink).click().build().perform();
			CommonUtils.scrollToElement(moreAttachmentLink, driver);
			moreAttachmentLink.click();
			AttachmentCommonUtils.waitTillLoad(10, driver);
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
			clickOnManageAttachments(driver);
			attachmentDeleteIcon.click();
			AttachmentCommonUtils.waitTillLoad(8, driver);
			warningYesBtn.click();
			AttachmentCommonUtils.waitTillLoad(8, driver);
		}
		
	}
	
	public void clickOnAttachmentOkButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(attachmentOKButton, "Click", "", driver);
		attachmentOKButton.click();
		Log.info("Clicked on OK button");
		AttachmentCommonUtils.waitTillLoad(15, driver);
	}
	
	public void clickOnPurchaseOrderSaveButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(purchaseOrderSaveBtn, "Click", "", driver);
		purchaseOrderSaveBtn.click();
		Log.info("Clicked on Purchase Order Save button");
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.hold(4);
	}

}
