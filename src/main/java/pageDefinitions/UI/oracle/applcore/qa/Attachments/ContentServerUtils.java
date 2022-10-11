package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import java.sql.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;

public class ContentServerUtils extends ContentServerPage{

	public ContentServerUtils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
	}
	
	public void navigateToContentServer(WebDriver driver) {
		String csUrl = GetDriverInstance.EnvironmentName.replace("/fscmUI/faces/FuseWelcome", "/cs");
		System.out.println("Content Server URL: "+csUrl);
		driver.get(csUrl);
	}
	
	public String newCheckIn(String title, String comment, String primaryFilename, String securityGroupName, WebDriver driver) throws Exception {
		
		String content_ID = null;
		
		CommonUtils.explicitWait(newCheckInLink, "Click", "", driver);
		newCheckInLink.click();
		CommonUtils.waitForPageLoad(driver);
		System.out.println("hard wait");
		CommonUtils.hold(8);
		System.out.println("waiting for page heading");
		boolean isPresent = driver.findElements(By.xpath("//textarea[contains(@id,'xComments')]")).size() > 0;
		System.out.println("element present: "+isPresent);
		
		boolean isPresent1 = newCheckInCommentsField.isDisplayed();
		System.out.println("element1 present: "+isPresent1);
		//CommonUtils.explicitWait(newCheckInPageHeading, "Visible", "", driver);
		//CommonUtils.hold(4);
		//CommonUtils.explicitWait(newCheckInTitleField, "Visible", "", driver);
		//newCheckInTitleField.click();
		newCheckInCommentsField.clear();
		newCheckInCommentsField.sendKeys(comment);
		
		newCheckInTitleField.clear();
		newCheckInTitleField.sendKeys(title);
		
		CommonUtils.explicitWait(newCheckInCommentsField, "Click", "", driver);
		
		
		CommonUtils.explicitWait(newCheckInSecurityGroup, "Click", "", driver);
		CommonUtils.selectDropDownElement(newCheckInSecurityGroup, securityGroupName);
		
		CommonUtils.explicitWait(newCheckInBrowse, "Click", "", driver);
		// ----- /Contribution Folders/CRM/ ------
		newCheckInBrowse.sendKeys(System.getProperty("file.separator") + "Contribution Folders" + System.getProperty("file.separator") + "CRM" + System.getProperty("file.separator"));
		
		CommonUtils.explicitWait(newCheckInPrimaryFileBrowse, "Click", "", driver);
		newCheckInPrimaryFileBrowse.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(primaryFilename));
		
		CommonUtils.explicitWait(CheckInButton, "Click", "", driver);
		CheckInButton.click();
		CommonUtils.waitForInvisibilityOfElement(CheckInButton, driver, 10);
		
		CommonUtils.explicitWait(contentID, "Visible", "", driver);
		content_ID = contentID.getText();
		
		Assert.assertTrue(contentID.isDisplayed(), "Content not checked in. Content ID is not displayed.");
		Assert.assertTrue(contentID.getText().startsWith("UCMFA"), "Content not checked in. Content ID Value is not displayed.");
		Assert.assertTrue(contentTitle.isDisplayed(), "Content not checked in. Content Title is not displayed.");
		
		return content_ID;
		
	}
	
	public void searchContentID(String content_ID, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(searchLink, "Click", "", driver);
		searchLink.click();
		
		CommonUtils.explicitWait(searchContentIDField, "Click", "", driver);
		searchContentIDField.sendKeys(content_ID);
		
		WebElement contentInfoIcon = driver.findElement(By.xpath("//a[contains(text(),'"+content_ID+"')]/following::img[@title='Info']"));
		contentInfoIcon.click();
		CommonUtils.explicitWait(contentInformationPageHeading, "Visible", "", driver);
		
	}
	
	public void validateContentInformation(String content_ID , String title, String comments, String filename, WebDriver driver) {
		
		Assert.assertEquals(contentInfo_Title.getText(), title, "Content Info: Title mismatch");
		Assert.assertEquals(contentInfo_Title.getText(), comments, "Content Info: Comments mismatch");
		
		WebElement fileLink = driver.findElement(By.xpath("//td[normalize-space(text())='Native File:']/following::a[normalize-space(text())='"+filename+"']"));
		
		Assert.assertTrue(fileLink.getAttribute("href")
				.matches(".*cs/idcplg?IdcService=GET_FILE&dID=.*&dDocName="+content_ID+".*"), "Download content link not present");
		
		fileLink.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
	}

}
