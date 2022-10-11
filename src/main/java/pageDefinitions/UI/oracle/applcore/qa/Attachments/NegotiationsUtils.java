package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class NegotiationsUtils extends Negotiation {

	public NegotiationsUtils(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
//*********************************************************************************************************************************
	public void ManageNego(WebDriver driver) throws Exception {
		//String responseLayout = "Response Layout";
		CommonUtils.hold(4);
		System.out.println("in manageNego method");
		CommonUtils.waitForJStoLoad(driver, 150);
		CommonUtils.hold(5);
		
		try {
			AttachmentCommonUtils.waitTillUILoad(40, driver);
		} catch(Exception e) {
			System.out.println("Wait style is not present.");
		}
		//CommonUtils.waitForPageLoad(driver);
		Log.info("About to click tasks icon");
		CommonUtils.explicitWait(Negotiation.negotiationsTasksPanelDrawerIcon, "Click", "",driver);
		CommonUtils.hold(3);
		Negotiation.negotiationsTasksPanelDrawerIcon.click();
		AttachmentCommonUtils.waitTillUILoad(10, driver);
		//CommonUtils.hold(3);
		//CommonUtils.waitForElement(Negotiation.tasksManageNegotiation, driver);
		CommonUtils.explicitWait(Negotiation.tasksManageNegotiation, "Click", "", driver);
		Negotiation.tasksManageNegotiation.click();
		AttachmentCommonUtils.waitTillUILoad(25, driver);
		//CommonUtils.hold(10);
		CommonUtils.explicitWait(searchArrowIcon, "Click", "", driver);
		if(searchArrowIcon.getAttribute("class").contains("undisclosed-icon-style")) {
			searchArrowIcon.click();
			AttachmentCommonUtils.waitTillLoad(15, driver);
		}
		CommonUtils.waitForElement(Negotiation.negotiationSearchInputText, driver);
		Negotiation.negotiationSearchInputText.click();
		Negotiation.negotiationSearchInputText.clear();
		Negotiation.negotiationSearchInputText.sendKeys("35061");
		CommonUtils.hold(2);
		Negotiation.negotiationSearchButton.click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.hold(5);			
		CommonUtils.waitForElement(Negotiation.searchResultTableNegotiatonType, driver);
		Negotiation.searchResultTableNegotiatonType.click();
	/*	Assert.assertEquals(Negotiation.searchResultTableNegotiaton.getAttribute("title"), 35061, "Negotiation Number : "+Negotiation.searchResultTableNegotiaton.getAttribute("title"));
		Assert.assertEquals(Negotiation.searchResultTableNegotiatonTitle.getAttribute("title"), "PRCBAT_NEG_ACTIVE", "Negotiation Title : "+Negotiation.searchResultTableNegotiatonTitle.getAttribute("title"));
		Assert.assertEquals(Negotiation.searchResultTableNegotiatonType.getAttribute("title"),"Auction","Negotioation Type : "+Negotiation.searchResultTableNegotiatonType.getAttribute("title"));*/
					
		CommonUtils.hold(3);
		CommonUtils.explicitWait(negotiationDuplicateIcon, "", "Click", driver);
		negotiationDuplicateIcon.click();
		AttachmentCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.waitForElement(duplicateNegotiationNegotioationTypeSOC, driver);
		CommonUtils.selectDropDownElement(duplicateNegotiationNegotioationTypeSOC, "Auction");
		//CommonUtils.hold(2);
		AttachmentCommonUtils.waitTillUILoad(8, driver);
		CommonUtils.explicitWait(duplicateNegotiationCreateButton, "", "Click", driver);
		Negotiation.duplicateNegotiationCreateButton.click();
		Log.info("Create negotiation duplicate##############################");
		//CommonUtils.hold(10);
		AttachmentCommonUtils.waitTillUILoad(30, driver);
		CommonUtils.waitForElement(editNegotiationCoverPageNextTrainBarButton, driver);
		CommonUtils.hold(3);
		Log.info("Edit negotiation###################################");
		editNegotiationCoverPageNextTrainBarButton.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		
		//Start of Attachment //
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForElement(negotiationAttachmentAddIcon, driver);
		
		CommonUtils.scrollToElement(closeDateDaysAfterRadioButton, driver);
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", closeDateDaysAfterRadioButton);
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(closeDateDaysAfterRadioButton, "Click", "", driver);
		closeDateDaysAfterRadioButton.click();
		AttachmentCommonUtils.waitTillLoad(5, driver);
		CommonUtils.explicitWait(closeDateDaysAfterTextField, "Click", "", driver);
		closeDateDaysAfterTextField.clear();
		closeDateDaysAfterTextField.sendKeys("3");
		CommonUtils.scrollToElement(negoSaveBtn, driver);
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", negoSaveBtn);
		clickOnNegoSaveButton(driver);
	
		
	}
	
	
	

//***************************************************************************************************************************
//Ashish Pareek
	
	public void NegotiationFileAttach(String filename, WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		//CommonUtils.waitForElement(Negotiation.negotiationAttachmentAddIcon, driver);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentAddIcon,"Click" , "", driver);
		Negotiation.negotiationAttachmentAddIcon.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentType,"Click" , "", driver);
		CommonUtils.selectDropDownElement(Negotiation.negotiationAttachmentType, "File");
		CommonUtils.selectDropDownElement(Negotiation.negotiationAttachmentCategory, "To Supplier");
		CommonUtils.hold(5);	
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentBrowse, "Click", "", driver);
		CommonUtils.hold(2);
		//String path = System.getProperty("user.dir");
		//System.out.println(path);
		//String FilePath = path + filePath;
		Negotiation.negotiationAttachmentBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		//CommonUtils.hold(5);
		AttachmentCommonUtils.waitTillFileUpload(driver);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentOKButton,"Click","", driver);			
		//CommonUtils.hold(15);			
		Negotiation.negotiationAttachmentOKButton.click();
		//CommonUtils.hold(15);
		AttachmentCommonUtils.waitTillLoad(15, driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File Uploaded Successfully");
	}
	
	public void NegotiationTextAttach(String text, String textTitle, WebDriver driver) throws Exception {
		//CommonUtils.waitForElement(Negotiation.negotiationAttachmentAddIcon, driver);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentAddIcon,"Click" , "", driver);
		Negotiation.negotiationAttachmentAddIcon.click();
		//attachtype(1, driver,"Text");
		
		CommonUtils.explicitWait(typeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(typeSelect, "Text");
		CommonUtils.explicitWait(Negotiation.textarea, "Click", "", driver);
		Log.info("Selection of Text Type from first row");
		CommonUtils.hold(5);
        Negotiation.textarea.sendKeys(text);
		//CommonUtils.hold(5);
		attachmentTitle.sendKeys(textTitle);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentOKButton,"Click","", driver);			
		//CommonUtils.hold(15);			
		Negotiation.negotiationAttachmentOKButton.click();
		CommonUtils.waitForInvisibilityOfElement(Negotiation.negotiationAttachmentOKButton, driver, 15);
		//CommonUtils.hold(15);
		CommonUtils.explicitWait(Negotiation.lastAttachedText,"Visible","", driver);
		Assert.assertEquals(Negotiation.lastAttachedText.getText().trim(), textTitle, "Added text title mismatch.");
		
		Actions action = new Actions(driver);
		action.moveToElement(Negotiation.lastAttachedText).click().build().perform();
		CommonUtils.hold(4);
		Negotiation.lastAttachedText.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(Negotiation.ReviewText,"Click","", driver);
		Assert.assertEquals(ReviewText.getText().trim(), text, "Added text mismatch.");
		
		
	}
	
	public void NegotiationURLAttach(String url, String urlTitle, WebDriver driver) throws Exception {
		//CommonUtils.waitForElement(Negotiation.negotiationAttachmentAddIcon, driver);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentAddIcon,"Click" , "", driver);
		Negotiation.negotiationAttachmentAddIcon.click();
		//attachtype(2, driver,"URL");
		CommonUtils.explicitWait(typeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(typeSelect, "URL");
		CommonUtils.explicitWait(Negotiation.urlField, "Click", "", driver);
		Log.info("Selection of URL Type from first row");
		CommonUtils.hold(5);
		//enterattachtext(2, driver);
		//CommonUtils.explicitWait(Negotiation.urlField, "Click", "", driver);
		urlField.sendKeys(url);
		//CommonUtils.hold(5);
		attachmentTitle.sendKeys(urlTitle);
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentOKButton,"Click","", driver);			
		//CommonUtils.hold(15);			
		Negotiation.negotiationAttachmentOKButton.click();
		CommonUtils.waitForInvisibilityOfElement(Negotiation.negotiationAttachmentOKButton, driver, 15);
		//CommonUtils.hold(15);
		
		CommonUtils.explicitWait(Negotiation.lastAttachedURL,"Click","", driver);
		if(url.startsWith("file:/") && !(url.startsWith("file:///"))) {
			url = url.replace("file:/", "file:///");
		}
		Assert.assertTrue(Negotiation.lastAttachedURL.getAttribute("href").contains(url), "Added URL mismatch.");
		
	}


 public void updateNegoFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		
	 	clickOnMoreAttachment(driver);
	 	WebElement updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'ifPopup::upBtn')]"));
		AttachmentCommonUtils.clickOnUpdateButton(updateButton, driver);
		
		CommonUtils.explicitWait(negotiationUpdateBrowse, "Click", "", driver);
		negotiationUpdateBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
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
 
 	public void updateNegoTextAttachment(String text, String newText, String newTextTitle, WebDriver driver) throws Exception {
		
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

	
	public void updateNegoURLAttachment(String url, String newURL, String newUrlTitle, WebDriver driver) throws Exception {
		
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
	
	//click save nego before	
	public void downloadNegoFileAttachment(String filename, WebDriver driver) throws Exception {
		
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
	

	public void downloadNegoTextAttachment(String textname, String textTitle, WebDriver driver) throws Exception {
		
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
	
	public void downloadNegoURLAttachment(String URL, WebDriver driver) {
		WebElement urlLink = driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLiveURL')]/a[contains(text(),'"+URL+"')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deleteNegoAttachment(String name, WebDriver driver) throws Exception {
		
		//CommonUtils.hold(3);
		driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLive')]/a[contains(text(),'"+name+"')]/following::span[contains(@id,'otPopLastBy')]")).click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(NegoAttachmentDeleteIcon, "Click", "", driver);
		NegoAttachmentDeleteIcon.click();
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

	
	
	public void clickOnMoreAttachment(WebDriver driver ) throws Exception {
		try {
			CommonUtils.explicitWait(moreAttachmentLink, "Click", "", driver);
			CommonUtils.hold(2);
			//Actions actions = new Actions(driver);
			//actions.moveToElement(Opportunities.moreAttachmentLink).click().build().perform();
			moreAttachmentLink.click();
			AttachmentCommonUtils.waitTillLoad(12, driver);
			//CommonUtils.explicitWait(attachmentDiagBox, "Click", "", driver);
			CommonUtils.hold(6); //holding if blocking attachment pane becomes visible.
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
			CommonUtils.explicitWait(negotiationAttachmentAddIcon, "Click", "", driver);
			negotiationAttachmentAddIcon.click();
			CommonUtils.explicitWait(attachmentDiagBox, "Click", "", driver);
			attachmentDiagBox.click();
			AttachmentCommonUtils.waitTillLoad(10, driver);
			NegoAttachmentDeleteIcon.click();
			AttachmentCommonUtils.waitTillLoad(8, driver);
			warningYesBtn.click();
			AttachmentCommonUtils.waitTillLoad(8, driver);
		}
		
	}
	
	public void clickOnAttachmentOkButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(Negotiation.negotiationAttachmentOKButton, "Click", "", driver);
		Negotiation.negotiationAttachmentOKButton.click();
		Log.info("Clicked on OK button");
		AttachmentCommonUtils.waitTillLoad(10, driver);
	}
	
	public void clickOnNegoSaveButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(Negotiation.negoSaveBtn, "Click", "", driver);
		Negotiation.negoSaveBtn.click();
		Log.info("Clicked on Negotiation Save button");
		AttachmentCommonUtils.waitTillLoad(10, driver);
	}

//***************************************************************************************************************************************************
	/*
	 * Manager Negotiation Attachments
	 */
	public void manageNegotiation(String negotiationSearchNumber, String negotiationType, String filePath, WebDriver driver) throws Exception {
			
			String responseLayout = "Response Layout";
			
			CommonUtils.hold(10);
			CommonUtils.waitForPageLoad(driver);
			Log.info("About to click tasks icon");
			CommonUtils.explicitWait(Negotiation.negotiationsTasksPanelDrawerIcon, "Click", "",driver);
			Negotiation.negotiationsTasksPanelDrawerIcon.click();
			CommonUtils.hold(5);
			
			CommonUtils.waitForElement(Negotiation.tasksManageNegotiation, driver);
			Negotiation.tasksManageNegotiation.click();
			
			CommonUtils.hold(10);
			CommonUtils.waitForElement(Negotiation.negotiationSearchInputText, driver);
			Negotiation.negotiationSearchInputText.click();
			Negotiation.negotiationSearchInputText.sendKeys(negotiationSearchNumber);
			CommonUtils.hold(2);
			Negotiation.negotiationSearchButton.click();			
			CommonUtils.hold(5);			
			CommonUtils.waitForElement(Negotiation.searchResultTableNegotiatonType, driver);
			Negotiation.searchResultTableNegotiatonType.click();
			
			Assert.assertEquals(Negotiation.searchResultTableNegotiaton.getAttribute("title"), negotiationSearchNumber, "Negotiation Number : "+Negotiation.searchResultTableNegotiaton.getAttribute("title"));
			Assert.assertEquals(Negotiation.searchResultTableNegotiatonTitle.getAttribute("title"), "PRCBAT_NEG_ACTIVE", "Negotiation Title : "+Negotiation.searchResultTableNegotiatonTitle.getAttribute("title"));
			Assert.assertEquals(Negotiation.searchResultTableNegotiatonType.getAttribute("title"),negotiationType,"Negotioation Type : "+Negotiation.searchResultTableNegotiatonType.getAttribute("title"));
						
			CommonUtils.hold(3);
			
			Negotiation.negotiationDuplicateIcon.click();
			
			CommonUtils.waitForElement(Negotiation.duplicateNegotiationNegotioationTypeSOC, driver);
			CommonUtils.selectDropDownElement(Negotiation.duplicateNegotiationNegotioationTypeSOC, negotiationType);
			CommonUtils.hold(5);
			Negotiation.duplicateNegotiationCreateButton.click();
			Log.info("Create negotiation duplicate##############################");
			CommonUtils.hold(10);
			CommonUtils.waitForElement(Negotiation.editNegotiationCoverPageNextTrainBarButton, driver);
			CommonUtils.hold(10);
			Log.info("Edit negotiation###################################");
			Negotiation.editNegotiationCoverPageNextTrainBarButton.click();
			
			//Start of Attachment //
			CommonUtils.hold(5);
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.waitForElement(Negotiation.negotiationAttachmentAddIcon, driver);
			
			Negotiation.negotiationAttachmentAddIcon.click();
			CommonUtils.hold(5);
			
			CommonUtils.explicitWait(Negotiation.negotiationAttachmentType,"Click" , "", driver);
			CommonUtils.selectDropDownElement(Negotiation.negotiationAttachmentType, "File");
			CommonUtils.selectDropDownElement(Negotiation.negotiationAttachmentCategory, "To Supplier");
			CommonUtils.hold(5);	
			
			CommonUtils.explicitWait(Negotiation.negotiationAttachmentBrowse, "Click", "", driver);
			CommonUtils.hold(2);
			String path = System.getProperty("user.dir");
			System.out.println(path);
			String FilePath = path + filePath;
			Negotiation.negotiationAttachmentBrowse.sendKeys(FilePath);
			CommonUtils.hold(5);			
			CommonUtils.explicitWait(negotiationAttachmentOKButton,"Click","", driver);			
			CommonUtils.hold(15);			
			Negotiation.negotiationAttachmentOKButton.click();
			CommonUtils.hold(15);
			
			//End of Attachment//
			
			CommonUtils.waitForElement(EditNegotiationCloseDate, driver);
			CommonUtils.scrollToElement(EditNegotiationCloseDate, driver);
			EditNegotiationCloseDate.sendKeys(CommonUtils.futureDate("MM/dd/yy h:mm a",10));
			
			CommonUtils.hold(5);
			/*CommonUtils.waitForElement(responseLayoutInputText, driver);			
			responseLayoutInputText.clear();
			CommonUtils.hold(5);
			CommonUtils.waitForElement(responseLayoutLOVIcon, driver);
			responseLayoutLOVIcon.click();
			CommonUtils.hold(10);
			CommonUtils.waitForElement(responseLayoutDropDownSearch, driver);
			responseLayoutDropDownSearch.click();

			CommonUtils.hold(5);
			CommonUtils.waitForElement(responseLayoutSearchNameInputText, driver);
			responseLayoutSearchNameInputText.clear();
			responseLayoutSearchNameInputText.sendKeys(responseLayout);
			CommonUtils.hold(5);
			responseLayoutSearchButton.click();
			CommonUtils.hold(5);
			responseLayoutSearchResultTableRow.click();
			CommonUtils.hold(2);
			ResponseLayoutSearchOK.click();	*/
			
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(10);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-250)");
			CommonUtils.explicitWait(negotiationOverviewSaveDropDown, "Click", "",driver);
			CommonUtils.hold(5);
			negotiationOverviewSaveDropDown.click();
			CommonUtils.hold(5);
			negotiationOverviewSaveandClose.click();
			
			CommonUtils.waitForElement(confirmationPopupOK, driver);
			CommonUtils.explicitWait(confirmationPopupOK,"Click","", driver);
			CommonUtils.hold(5);
			confirmationPopupOK.click();
			CommonUtils.hold(3);
			
		
	}
	
//************************************************************************************************************************	
	
}
