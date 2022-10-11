package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;

public class ManageTradingPartnerItemsUtils extends ManageTradingPartnerItemsPage{

	public ManageTradingPartnerItemsUtils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
	}
	
	public void manageTradingPartnerItems(String title, String desc, String filename, WebDriver driver) throws Exception {
		
		CommonUtils.explicitWait(pimPanelDrawer, "Click", "", driver);
		pimPanelDrawer.click();
		CommonUtils.explicitWait(manageTradingPartnerItemsLink, "Click", "", driver);
		manageTradingPartnerItemsLink.click();
		AttachmentCommonUtils.waitTillLoad(15, driver);
		
		CommonUtils.explicitWait(showFiltersLink, "Click", "", driver);
		showFiltersLink.click();
		CommonUtils.explicitWait(hideFiltersLink, "Click", "", driver);
		CommonUtils.explicitWait(tradingPartnerField, "Visible", "", driver);
		tradingPartnerField.clear();
		tradingPartnerField.sendKeys("TCP Manufacturer");
		CommonUtils.explicitWait(filterSearchButton, "Click", "", driver);
		filterSearchButton.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(listViewIcon, "Click", "", driver);
		listViewIcon.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.scrollToElement(ICTech1Column, driver);
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.explicitWait(ICTech1Column, "Click", "", driver);
		ICTech1Column.click();
		CommonUtils.explicitWait(attachmentsArrowIcon, "Click", "", driver);
		if(attachmentsArrowIcon.getAttribute("title").equalsIgnoreCase("Expand Attachments")) {
			attachmentsArrowIcon.click();
			AttachmentCommonUtils.waitTillLoad(15, driver);
		}
		
		Assert.assertEquals(attachmentsTitle.getText(), title, "ICtech1: Attachment Title mismatch"); // Title Validation
		Assert.assertEquals(attachmentsDescription.getText(), desc, "ICtech1: Attachment Description mismatch"); // Description Validation
		Assert.assertTrue(attachmentsDownloadIcon.isDisplayed(), "ICtech1: Attachment Download icon is not displayed."); // Download Validation
		
		Assert.assertTrue(attachmentsDownloadIcon.getAttribute("href")
				.matches(".*fscmUI/content/conn/FusionAppsContentRepository/uuid/dDocID.*&Id=.*download"), "ICtech1: Download content link not present");
		attachmentsDownloadIcon.click();
		CommonUtils.hold(8);
		
		AttachmentCommonUtils.isFileDownloadedSuccessfully(filename);
		
	}
	
	

}
