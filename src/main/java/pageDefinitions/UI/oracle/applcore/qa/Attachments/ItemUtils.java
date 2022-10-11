package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class ItemUtils extends ItemPage{

	public ItemUtils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
	}
	
	public void itemFileAttach(String filename, WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		clickOnManageAttachments(driver);
		CommonUtils.explicitWait(attachmentType,"Click" , "", driver);
		CommonUtils.selectDropDownElement(attachmentType, "File");
		CommonUtils.hold(5);	
		CommonUtils.explicitWait(attachmentBrowse, "Click", "", driver);
		CommonUtils.hold(2);
		attachmentBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		//CommonUtils.hold(5);
		AttachmentCommonUtils.waitTillFileUpload(driver);
		clickOnAttachmentOkButton(driver);
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+filename+"')]"))
				.isDisplayed(), "File not Uploaded Successfully");
		CommonUtils.hold(2);
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'cl2')]/img[contains(@title,'"+filename+"')]"))
				.isDisplayed(), "File Image is not displayed.");
		
		
	}
	
		
	public void itemURLAttach(String url, String urlTitle, WebDriver driver) throws Exception {
		clickOnManageAttachments(driver);
		CommonUtils.explicitWait(typeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(typeSelect, "URL");
		CommonUtils.explicitWait(urlField, "Click", "", driver);
		Log.info("Selection of URL Type from first row");
		CommonUtils.hold(5);
		urlField.sendKeys(url);
		attachmentTitle.sendKeys(urlTitle);
		clickOnAttachmentOkButton(driver);
		
		String urlHref = url;
		CommonUtils.explicitWait(lastAttachedURL,"Click","", driver);
		if(url.startsWith("file:/") && !(url.startsWith("file:///"))) {
			urlHref = url.replace("file:/", "file:///");
		}
		Assert.assertTrue(lastAttachedURL.getAttribute("href").contains(urlHref), "Added URL mismatch.");
		CommonUtils.hold(2);
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'cl2')]/img[contains(@src,'"+url+"')]"))
				.isDisplayed(), "URL Image is not displayed.");
		
	}
	
	public void updateItemFileAttachment(String filename, String newFilename, WebDriver driver) throws Exception {
		
	 	clickOnMoreAttachment(driver);
	 	WebElement updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'ifPopup::upBtn')]"));
		updateButton.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		CommonUtils.hold(10);
		System.out.println("clicking on Update second time");
		updateButton = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following-sibling::button[contains(@id,'ifPopup::upBtn')]"));
		updateButton.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(UpdateBrowse, "Click", "", driver);
		UpdateBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
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
		clickOnAttachmentOkButton(driver);
		
	}
 
 	
	public void updateItemURLAttachment(String url, String newURL, String newUrlTitle, WebDriver driver) throws Exception {
		
		clickOnMoreAttachment(driver);
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
	
	//click save Item before	
	public void downloadItemFileAttachment(String filename, WebDriver driver) throws Exception {
		
		clickOnMoreAttachment(driver);
		WebElement fileLink = driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLiveFile')]/a[contains(text(),'"+filename+"')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		clickOnAttachmentOkButton(driver);
	}
	

	public void downloadItemURLAttachment(String URL, WebDriver driver) throws Exception {
		clickOnMoreAttachment(driver);
		WebElement urlLink = driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLiveURL')]/a[contains(text(),'"+URL+"')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
		clickOnAttachmentOkButton(driver);
	}
	
	public void deleteItemAttachment(String name, WebDriver driver) throws Exception {
		
		//CommonUtils.hold(3);
		clickOnMoreAttachment(driver);
		driver.findElement(By.xpath("//span[contains(@id,'pglPopFileLive')]/a[contains(text(),'"+name+"')]/following::input[contains(@id,'TitleInputText::content')]")).click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(attachmentDeleteIcon, "Click", "", driver);
		attachmentDeleteIcon.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		CommonUtils.explicitWait(warningYesBtn, "Click", "", driver);
		warningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//a[text()='"+name+"']")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File/URL is deleted successfully.");
		}
		clickOnAttachmentOkButton(driver);
		
	}
	
	public void clickOnManageAttachments(WebDriver driver) throws Exception {
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(manageAttachments, "Click", "", driver);
		manageAttachments.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(attachmentDiagBox, "Visible", "", driver);
		
	}
	
	
	public void clickOnMoreAttachment(WebDriver driver ) throws Exception {
		try {
			//CommonUtils.explicitWait(moreAttachmentLink, "Click", "", driver);
			//Actions actions = new Actions(driver);
			//actions.moveToElement(Opportunities.moreAttachmentLink).click().build().perform();
			AttachmentCommonUtils.waitTillLoad(15, driver);
			CommonUtils.hold(3);
			moreAttachmentLink.click();
			AttachmentCommonUtils.waitTillLoad(15, driver);
			CommonUtils.explicitWait(attachmentDiagBox, "Click", "", driver);
			CommonUtils.hold(5); //holding if blocking attachment pane becomes visible.
			attachmentDiagBox.click();
			CommonUtils.hold(3);
			/*
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
			*/
		} catch (NoSuchElementException e1) {
			clickOnManageAttachments(driver);
			attachmentDeleteIcon.click();
			AttachmentCommonUtils.waitTillLoad(10, driver);
			warningYesBtn.click();
			AttachmentCommonUtils.waitTillLoad(10, driver);
		}
		
	}
	
	public void clickOnAttachmentOkButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(attachmentOKButton, "Click", "", driver);
		attachmentOKButton.click();
		Log.info("Clicked on OK button");
		AttachmentCommonUtils.waitTillLoad(15, driver);
	}
	
	public void clickOnItemSaveButton(WebDriver driver ) throws Exception {
		
		CommonUtils.explicitWait(itemSaveBtn, "Click", "", driver);
		itemSaveBtn.click();
		Log.info("Clicked on Item Save button");
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.hold(3);
	}
//***************************************************************************************************************************************************************
//***************************************************************************************************************************************************************
	
	public void clickOnAttachmentsTab(WebDriver driver) throws Exception {
		clickOnItemSaveButton(driver);
		CommonUtils.explicitWait(attachmentsTab, "Click", "", driver);
		attachmentsTab.click();
		Log.info("Clicked on Attachments tab.");
		AttachmentCommonUtils.waitTillLoad(15, driver);
	}
	
	public void clickOnItemPanelAddAttachments(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(itemPanelAddAttachments, "Click", "", driver);
		itemPanelAddAttachments.click();
		Log.info("Clicked on Item Panel Add Attachments icon.");
		AttachmentCommonUtils.waitTillLoad(15, driver);
	}
	
	public void clickOnItemPanelTableView(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(tableViewIcon, "Click", "", driver);
		tableViewIcon.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);		
	}
	
	public void clickOnItemPanelListView(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(listViewIcon, "Click", "", driver);
		listViewIcon.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);		
	}
	
	public void clickOnItemPanelGridView(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(gridViewIcon, "Click", "", driver);
		gridViewIcon.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);		
	}
	
	
	public void itemPanelFileAttach(String panel, String filename, WebDriver driver) throws Exception {
		//CommonUtils.waitforElementtoVisible(30, addAttachements, driver);
		clickOnItemPanelAddAttachments(driver);
		//CommonUtils.waitforElementtoVisible(30, browse, driver);
		CommonUtils.explicitWait(ItemPanelbrowse, "Click", "", driver);
		CommonUtils.hold(3);
		Log.info("Adding attachment through type File");
		//browse.sendKeys(System.getProperty("user.dir") + filename);
		ItemPanelbrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(filename));
		CommonUtils.waitForInvisibilityOfElement(fileUploadProcessBar, driver, 40);
		CommonUtils.waitForInvisibilityOfElement(uploadCancelBtn, driver, 40);
		Log.info("FileType Done.. ");
		CommonUtils.waitForPageLoad(driver);
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.hold(5);  // Wait to load the attachment thumbnail in List and Grid view.
		
		panel = panel.toLowerCase();
		if(panel.equals("table")) {
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciTable') and contains(text(),'"+filename+"')]"))
					.isDisplayed(), "File not Uploaded Successfully");
			
		}
		else if (panel.equals("list")) {
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciList') and contains(text(),'"+filename+"')]"))
					.isDisplayed(), "File not Uploaded Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'attThumb:iDefTh') and @title='"+filename+"']"))
					.isDisplayed(), "List view: Attachment thumbnail is not displayed.");
		}
		else if(panel.equals("grid")) {
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciGrid') and contains(text(),'"+filename+"')]"))
					.isDisplayed(), "File not Uploaded Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'avgThumb:iDefTh') and @title='"+filename+"']"))
					.isDisplayed(), "Grid view: Attachment thumbnail is not displayed.");
		}
		else {
			Assert.assertTrue(false, "Invalid Item panel name.");
		}
		
	}
	
	public void itemPanelTextAttach(String panel, String text, String textTitle, WebDriver driver) throws Exception {
		
		CommonUtils.explicitWait(ItemPaneltypeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(ItemPaneltypeSelect, "Text");
		Log.info("Selection of Text Type from first row");
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(ItemPanelTextarea, "Click", "", driver);
		ItemPanelTextarea.clear();
        ItemPanelTextarea.sendKeys(text);
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(ItemPanelTitleField, "Click", "", driver);
		ItemPanelTitleField.clear();
		ItemPanelTitleField.sendKeys(textTitle);
		Log.info("Filled Text Title");
		CommonUtils.explicitWait(ItemPanelUrlAdd, "Click", "", driver);
		ItemPanelUrlAdd.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);  // Wait to load the attachment thumbnail in List and Grid view.
		
		panel = panel.toLowerCase();
		if(panel.equals("table")) {
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciTable') and contains(text(),'"+textTitle+"')]"))
					.isDisplayed(), "Text not attached Successfully");
		}
		else if (panel.equals("list")) {
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciList') and contains(text(),'"+textTitle+"')]"))
					.isDisplayed(), "Text not attached Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'attThumb:iDefTh') and @title='"+textTitle+"']"))
					.isDisplayed(), "List view: Attachment thumbnail is not displayed.");
		}
		else if(panel.equals("grid")) {
			String titleSubstring = AttachmentCommonUtils.substring(textTitle, 17);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'dciGrid') and contains(text(),'"+titleSubstring+"')]"))
					.isDisplayed(), "Text not attached Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'avgThumb:iDefTh') and @title='"+textTitle+"']"))
					.isDisplayed(), "Grid view: Attachment thumbnail is not displayed.");
		}
		else {
			Assert.assertTrue(false, "Invalid Item panel name.");
		}
		
		
	}
	
	public void itemPanelURLAttach(String panel, String URL, String urlTitle, WebDriver driver) throws Exception {
		
		Log.info("Adding attachment through Type URL");
		CommonUtils.explicitWait(ItemPaneltypeSelect, "Click", "", driver);
		CommonUtils.selectDropDownElement(ItemPaneltypeSelect, "URL");
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(ItemPanelURLFieldLink, "Click", "", driver);
		Log.info("Select URL form dropdown");
		CommonUtils.explicitWait(ItemPanelURLFieldLink, "Click", "", driver);
		CommonUtils.hold(3);
		ItemPanelURLFieldLink.clear();
		ItemPanelURLFieldLink.sendKeys(URL);
		Log.info("Filled URL");
		CommonUtils.explicitWait(ItemPanelTitleField, "Click", "", driver);
		ItemPanelTitleField.clear();
		ItemPanelTitleField.sendKeys(urlTitle);
		Log.info("Filled URL Title");
		ItemPanelUrlAdd.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(ItemPanelUrlAdd, "Click", "", driver);
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);  // Wait to load the attachment thumbnail in List and Grid view.
		
		if(URL.startsWith("file:/") && !(URL.startsWith("file:///"))) {
			URL = URL.replace("file:/", "file:///");
		}
		
		panel = panel.toLowerCase();
		if(panel.equals("table")) {
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciTable') and contains(text(),'"+urlTitle+"')]"))
					.getAttribute("href").contains(URL), "Added URL mismatch.");
		}
		else if (panel.equals("list")) {
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciList') and contains(text(),'"+urlTitle+"')]"))
					.getAttribute("href").contains(URL), "Added URL mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'attThumb:iDefTh') and @title='"+urlTitle+"']"))
					.isDisplayed(), "List view: Attachment thumbnail is not displayed.");
		}
		else if(panel.equals("grid")) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'dciGrid') and contains(text(),'"+urlTitle+"')]/parent::a"))
					.getAttribute("href").contains(URL), "Added URL mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'avgThumb:iDefTh') and @title='"+urlTitle+"']"))
					.isDisplayed(), "Grid view: Attachment thumbnail is not displayed.");
		}
		else {
			Assert.assertTrue(false, "Invalid Item panel name.");
		}
		
	}
	
	public void updateItemPanelFileAttachment(String panel, String filename, String newFilename, WebDriver driver) throws Exception {
		
		driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following::img[contains(@id,'cilDtl::icon')]")).click();
		//CommonUtils.hold(6);
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(ItemPanelUpdateBrowse, "Click", "", driver);
		ItemPanelUpdateBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(newFilename));
		CommonUtils.explicitWait(ItemPanelAttachmentTitle, "Click", "", driver);
		ItemPanelAttachmentTitle.clear();
		ItemPanelAttachmentTitle.sendKeys(newFilename);
		CommonUtils.explicitWait(ItemPanelAttachmentDescription, "Click", "", driver);
		ItemPanelAttachmentDescription.clear();
		ItemPanelAttachmentDescription.sendKeys("Desc"+newFilename);
		CommonUtils.explicitWait(ItemPanelUpdateDetailsBtn, "Click", "", driver);
		ItemPanelUpdateDetailsBtn.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.explicitWait(ItemPanelUpdateFileBtn, "Click", "", driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);  // Wait to load the attachment thumbnail in List and Grid view.
		
		panel = panel.toLowerCase();
		if(panel.equals("table")) {
			AttachmentCommonUtils.horizontalScrollToElement(titleColumnHeader, driver);
			System.out.println("After scroll");
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciTable') and contains(text(),'"+newFilename+"')]"))
					.isDisplayed(), "File not Updated Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciTable') and contains(text(),'"+newFilename+"')]/following::span[contains(text(),'"+"Desc"+newFilename+"')]"))
					.isDisplayed(), "File Description not Updated Successfully");
		}
		else if (panel.equals("list")) {
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciList') and contains(text(),'"+newFilename+"')]"))
					.isDisplayed(), "File not Updated Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciList') and contains(text(),'"+newFilename+"')]/following::span[contains(text(),'"+"Desc"+newFilename+"')]"))
					.isDisplayed(), "File Description not Updated Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'attThumb:iDefTh') and @title='"+newFilename+"']"))
					.isDisplayed(), "List view: Attachment thumbnail is not displayed.");
		}
		else if(panel.equals("grid")) {
			//clickOnItemPanelGridView(driver);
			Assert.assertTrue(driver.findElement(By.xpath("//span[contains(@id,'dciGrid') and contains(text(),'"+newFilename+"')]"))
					.isDisplayed(), "File not Updated Successfully");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'avgThumb:iDefTh') and @title='"+newFilename+"']"))
					.isDisplayed(), "Grid view: Attachment thumbnail is not displayed.");
		}
		else {
			Assert.assertTrue(false, "Invalid Item panel name.");
		}
		
		
	}
	
	public void updateItemPanelTextAttachment(String panel, String textTitle, String newText, String newTextTitle, WebDriver driver) throws Exception {
		
		//Using * in xpath because text and URLs are //a in Table and List view but //span in Grid view.
		driver.findElement(By.xpath("//*[contains(text(),'"+textTitle+"')]/following::img[contains(@id,'cilDtl::icon')]")).click();
		//CommonUtils.hold(6);
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(ItemPanelUpdateTextField, "Click", "", driver);
		ItemPanelUpdateTextField.clear();
		ItemPanelUpdateTextField.sendKeys(newText);
		Log.info("Updated Text");
		CommonUtils.explicitWait(ItemPanelAttachmentTitle, "Click", "", driver);
		ItemPanelAttachmentTitle.clear();
		ItemPanelAttachmentTitle.sendKeys(newTextTitle);
		Log.info("Filled URL Title");
		CommonUtils.explicitWait(ItemPanelAttachmentDescription, "Click", "", driver);
		ItemPanelAttachmentDescription.clear();
		ItemPanelAttachmentDescription.sendKeys("Desc"+newTextTitle);
		CommonUtils.explicitWait(ItemPanelUpdateDetailsBtn, "Click", "", driver);
		ItemPanelUpdateDetailsBtn.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.explicitWait(ItemPanelUpdateDetailsBtn, "Click", "", driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);  // Wait to load the attachment thumbnail in List and Grid view.
		
		panel = panel.toLowerCase();
		if(panel.equals("table")) {
			AttachmentCommonUtils.horizontalScrollToElement(titleColumnHeader, driver);
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciTable') and contains(text(),'"+newTextTitle+"')]"))
					.isDisplayed(), "Updated Text mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciTable') and contains(text(),'"+newTextTitle+"')]/following::span[contains(text(),'"+"Desc"+newTextTitle+"')]"))
					.isDisplayed(), "Updated Text Description mismatch.");
		}
		else if (panel.equals("list")) {
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciList') and contains(text(),'"+newTextTitle+"')]"))
					.isDisplayed(), "Updated Text mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciList') and contains(text(),'"+newTextTitle+"')]/following::span[contains(text(),'"+"Desc"+newTextTitle+"')]"))
					.isDisplayed(), "Updated Text Description mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'attThumb:iDefTh') and @title='"+newTextTitle+"']"))
					.isDisplayed(), "List view: Attachment thumbnail is not displayed.");
		}
		else if(panel.equals("grid")) {
			//clickOnItemPanelGridView(driver);
			String titleSubstring = AttachmentCommonUtils.substring(newTextTitle, 17);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'dciGrid') and contains(text(),'"+titleSubstring+"')]"))
					.isDisplayed(), "Updated Text mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'avgThumb:iDefTh') and @title='"+newTextTitle+"']"))
					.isDisplayed(), "Grid view: Attachment thumbnail is not displayed.");
		}
		else {
			Assert.assertTrue(false, "Invalid Item panel name.");
		}
	}
	
	public void updateItemPanelURLAttachment(String panel, String urlTitle, String newURL, String newUrlTitle, WebDriver driver) throws Exception {
		
		//Using * in xpath because text and URLs are //a in Table and Lis view but //span in Grid view.
		driver.findElement(By.xpath("//*[contains(text(),'"+urlTitle+"')]/following::img[contains(@id,'cilDtl::icon')]")).click();
		//CommonUtils.hold(6);
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(ItemPanelUpdateURLField, "Click", "", driver);
		ItemPanelUpdateURLField.clear();
		ItemPanelUpdateURLField.sendKeys(newURL);
		Log.info("Filled URL");
		CommonUtils.explicitWait(ItemPanelAttachmentTitle, "Click", "", driver);
		ItemPanelAttachmentTitle.clear();
		ItemPanelAttachmentTitle.sendKeys(newUrlTitle);
		Log.info("Filled URL Title");
		CommonUtils.explicitWait(ItemPanelAttachmentDescription, "Click", "", driver);
		ItemPanelAttachmentDescription.clear();
		ItemPanelAttachmentDescription.sendKeys("Desc"+newUrlTitle);
		CommonUtils.explicitWait(ItemPanelUpdateDetailsBtn, "Click", "", driver);
		ItemPanelUpdateDetailsBtn.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		CommonUtils.explicitWait(ItemPanelUpdateDetailsBtn, "Click", "", driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);  // Wait to load the attachment thumbnail in List and Grid view.
		
		panel = panel.toLowerCase();
		if(panel.equals("table")) {
			AttachmentCommonUtils.horizontalScrollToElement(titleColumnHeader, driver);
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciTable') and contains(text(),'"+newUrlTitle+"')]"))
					.getAttribute("href").contains(newURL), "Updated URL mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciTable') and contains(text(),'"+newUrlTitle+"')]/following::span[contains(text(),'"+"Desc"+newUrlTitle+"')]"))
					.isDisplayed(), "Updated URL Description mismatch.");
		}
		else if (panel.equals("list")) {
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciList') and contains(text(),'"+newUrlTitle+"')]"))
					.getAttribute("href").contains(newURL), "Updated URL mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@id,'dciList') and contains(text(),'"+newUrlTitle+"')]/following::span[contains(text(),'"+"Desc"+newUrlTitle+"')]"))
					.isDisplayed(), "Updated URL Description mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'attThumb:iDefTh') and @title='"+newUrlTitle+"']"))
					.isDisplayed(), "List view: Attachment thumbnail is not displayed.");
		}
		else if(panel.equals("grid")) {
			//clickOnItemPanelGridView(driver);
			Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'dciGrid') and contains(text(),'"+newUrlTitle+"')]/parent::a"))
					.getAttribute("href").contains(newURL), "Updated URL mismatch.");
			Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@id,'avgThumb:iDefTh') and @title='"+newUrlTitle+"']"))
					.isDisplayed(), "Grid view: Attachment thumbnail is not displayed.");
		}
		else {
			Assert.assertTrue(false, "Invalid Item panel name.");
		}
	}
//save item before	
	public void downloadItemPanelFileAttachment(String filename, WebDriver driver) {
		CommonUtils.hold(3);
		WebElement fileLink = driver.findElement(By.xpath("//span[contains(text(),'"+filename+"')]/following::a[contains(@id,'glDwn')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*/fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
	}
	
	public void downloadItemPanelTextAttachment(String textTitle, WebDriver driver) throws Exception {
		
		//clickOnMoreAttachment(driver);
		String titleSubstring = AttachmentCommonUtils.substring(textTitle, 17);
		WebElement fileLink = driver.findElement(By.xpath("//span[contains(text(),'"+titleSubstring+"')]/following::a[contains(@id,'glDwn')]"));
		//Assert.assertTrue(fileLink.getAttribute("href").endsWith("download"), "Download File link not present");
		//Assert.assertTrue(fileLink.getAttribute("href").contains("/crmUI/content/conn/FusionAppsContentRepository/uuid/dDocID"), "Download content link not present");
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isTextFileDownloadedSuccessfully(textTitle);
	}
	
	public void downloadItemPanelURLAttachment(String URL, String urlTitle, WebDriver driver) {
		//Using * in xpath because text and URLs are //a in Table and List view but //span in Grid view.
		WebElement urlLink = driver.findElement(By.xpath("//*[contains(text(),'"+urlTitle+"')]/following::a[contains(@id,'glDwn')]"));
		AttachmentCommonUtils.validateURL(urlLink, URL, driver);
	}
	
	public void deleteItemPanelFileAttachment(String title, WebDriver driver) throws Exception {
		
		driver.findElement(By.xpath("//span[contains(text(),'"+title+"')]/following::img[contains(@id,'cilDel::icon')]")).click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(ItemPanelWarningYesBtn, "Click", "", driver);
		ItemPanelWarningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//span[contains(text(),'"+title+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("File is deleted successfully.");
		}
	}
	
	public void deleteItemPanelTextAttachment(String title, WebDriver driver) throws Exception {
		//Using * in xpath because text and URLs are //a in Table and List view but //span in Grid view.
		String titleSubstring = AttachmentCommonUtils.substring(title, 17);
		driver.findElement(By.xpath("//*[contains(text(),'"+titleSubstring+"')]/following::img[contains(@id,'cilDel::icon')]")).click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(ItemPanelWarningYesBtn, "Click", "", driver);
		ItemPanelWarningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//span[contains(text(),'"+title+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("Text file is deleted successfully.");
		}
	}
	
	public void deleteItemPanelURLAttachment(String title, WebDriver driver) throws Exception {
		//Using * in xpath because text and URLs are //a in Table and List view but //span in Grid view.
		driver.findElement(By.xpath("//*[contains(text(),'"+title+"')]/following::img[contains(@id,'cilDel::icon')]")).click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(ItemPanelWarningYesBtn, "Click", "", driver);
		ItemPanelWarningYesBtn.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//a[contains(text(),'"+title+"')]")).isDisplayed());
		} catch(NoSuchElementException e) {
			Assert.assertTrue(true);
			Log.info("URL is deleted successfully.");
		}
		
	}

}
