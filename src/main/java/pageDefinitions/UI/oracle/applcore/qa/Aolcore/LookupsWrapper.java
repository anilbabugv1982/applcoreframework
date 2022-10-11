package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;

/**
 * @author DasKumar
 *
 */
public class LookupsWrapper extends LookupsPage {

	public LookupsWrapper(WebDriver driver) {
		super(driver);
	}

	public void createLookupCode(String lookupTypeName, String lookupCodeText, String meaningText, String descText,
			String taskFlowName, WebDriver driver) {

		try {
			searchLookupType(lookupTypeName, "", driver);
			CommonUtils.hold(3);
			CommonUtils.explicitWait(lkpCodeAddBtn, "Click", "", driver);
			lkpCodeAddBtn.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(lkpCodeInput, "Visible", "", driver);
			CommonUtils.hold(5);
			lkpCodeInput.sendKeys(lookupCodeText);

			if (taskFlowName.equalsIgnoreCase("set")) {
				selectOptionfrompkDropDown(setlkpDataSetSelect);
				setlkpCodeMeaningInput.sendKeys(meaningText);
				setlkpCodeDescInput.sendKeys(descText);
				setdisplaySeqInput.sendKeys("1");
			} else {
				lkpCodeMeaningInput.sendKeys(meaningText);
				lkpCodeDescInput.sendKeys(descText);
				displaySeqInput.sendKeys("1");
			}
			saveBtn.click();
			CommonUtils.hold(5);

		} catch (Exception createLookupCode) {
			 System.out.println("Exception in CreteLookupCode()."+createLookupCode.getMessage());
			
		}

	}// End of createLookupCode()

	public void createLookupType(String lookupTypeText, String meaningText, String descText, String taskFlowName,
			WebDriver driver) {

		try {
			CommonUtils.explicitWait(lkpTypeAddBtn, "Click", "", driver);
			lkpTypeAddBtn.click();
			CommonUtils.explicitWait(lkpTypeinputField, "Visible", "", driver);
			CommonUtils.hold(5);
			lkpTypeinputField.sendKeys(lookupTypeText);

			if (taskFlowName.equalsIgnoreCase("set")) {
				selectOptionfrompkDropDown(setlkpGroupNameSelect);
			}

			lkpTypeMeaningInput.sendKeys(meaningText);
			lkpTypeDescInput.sendKeys(descText);
			moduleInput.sendKeys("Oracle Middleware Extensions for Applications");
			saveBtn.click();
			CommonUtils.hold(5);
		} catch (Exception createLookupType) {
			 System.out.println("Exception in CreateLookupType(). "+createLookupType.getMessage());
		}
	}// End of createLookupType()

	public void WaitAfterCreateLookupType(String meaningText, WebDriver driver) {
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='" + meaningText + "']")), "Visible","", driver);
			CommonUtils.hold(5);
		} catch (Exception e) {			 
			e.printStackTrace();
		}

	}

	public void updateLookupType(String lookupTypeName, String meaning, String description, WebDriver driver) throws Exception {

		searchLookupType(lookupTypeName, "", driver);

		try {
			CommonUtils.explicitWait(lkpCodeMeaningInput, "Visible", "", driver);
		} catch (Exception updateLookupType) {
			System.out.println("Exception in updateLookupType(). " + updateLookupType.getMessage());

		}
		lkpTypeMeaningInput.clear();
		CommonUtils.hold(1);
		lkpTypeMeaningInput.sendKeys(meaning);
		CommonUtils.hold(2);
		lkpTypeDescInput.clear();
		CommonUtils.hold(1);
		lkpTypeDescInput.sendKeys(description);
		saveBtn.click();
		CommonUtils.hold(5);

	}// End of updateLookupType()

	public void updateLookupCode(String lookupTypeName, String lookUpCodeName, String meaning, String description,
			String taskFlowName, WebDriver driver) throws Exception {

		searchLookupType(lookupTypeName, lookUpCodeName, driver);
		try {
			CommonUtils.explicitWait(lkpCodeMeaningInput, "Visible", "", driver);
		} catch (Exception updateLookupCode) {
			System.out.println("Exception in updateLookupCode. "+updateLookupCode.getMessage());
		}

		if (taskFlowName.equalsIgnoreCase("set")) {

			setlkpCodeMeaningInput.clear();
			CommonUtils.hold(1);
			setlkpCodeMeaningInput.sendKeys(meaning);
			CommonUtils.hold(2);
			setlkpCodeDescInput.clear();
			CommonUtils.hold(1);
			setlkpCodeDescInput.sendKeys(description);
			CommonUtils.hold(2);
		} else {

			lkpCodeMeaningInput.clear();
			lkpCodeMeaningInput.sendKeys(meaning);
			CommonUtils.hold(2);
			lkpCodeDescInput.clear();
			lkpCodeDescInput.sendKeys(description);
			CommonUtils.hold(2);
			;
		}

		saveBtn.click();
		CommonUtils.hold(5);

	}// End of updateLookupCode()

	public boolean verifyStatus(String lookupTypeName, String lookupTypeCode, String lookupMeaning,
			String lookUpDescription, WebDriver driver) throws Exception {
		boolean verifyStatus = false;
		searchLookupType(lookupTypeName, lookupTypeCode, driver);
		try {
			CommonUtils.explicitWait(lkpTypeAddBtn, "Visible", "", driver);
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + lookupMeaning + "']")).isDisplayed()
					&& driver.findElement(By.xpath("//input[@value='" + lookUpDescription + "']")).isDisplayed());
			verifyStatus = true;
		} catch (Exception verifyStatus1) {			 
			Assert.fail("Exception in verifyStatus(). "+verifyStatus1.getMessage());
			
		}
		return verifyStatus;
	}

	public boolean verifyDeleteCode(String lookupTypeName, String searchLookupCodename, WebDriver driver)
			throws Exception {
		searchLookupType(lookupTypeName, searchLookupCodename, driver);
		boolean ele = false;
		try {
			Assert.assertTrue(
					driver.findElement(By.xpath("//span[text()='" + searchLookupCodename + "']")).isDisplayed());
		} catch (Exception e) {
			// System.out.println("Deleted LookUp Code");
			ele = true;
		}
		return ele;
	}

	public boolean verifyDeleteType(String lookupTypeName, WebDriver driver) throws Exception {
		searchLookupType(lookupTypeName, "", driver);
		boolean ele = false;
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + lookupTypeName + "']")).isDisplayed());

		} catch (Exception e) {
			// System.out.println("Deleted LookUp Type");
			ele = true;
		}
		return ele;
	}// End of VerifyDeleteType()

	public void deleteLookupType(String lookupTypeName, WebDriver driver) throws Exception {
		searchLookupType(lookupTypeName, "", driver);
		CommonUtils.hold(2);
		CommonUtils.explicitWait(lkpTypeDeleteBtn, "Click", "", driver);
		lkpTypeDeleteBtn.click();
		CommonUtils.hold(4);
		try {
			CommonUtils.explicitWait(lkpTypecondirmBtn, "Click", "", driver);
			CommonUtils.hold(2);
		} catch (Exception deleteLookupType) {
			 System.out.println("Exception in deleteLookupType() "+deleteLookupType.getMessage());			
		}
		lkpTypecondirmBtn.click();
		CommonUtils.waitForInvisibilityOfElement(lkpTypecondirmBtn, driver, 8);
		CommonUtils.explicitWait(saveBtn, "Click", "", driver);
		CommonUtils.hold(2);
		saveBtn.click();
		CommonUtils.hold(5);
		// verifyDeleteType(lookupTypeName);
	}// End of deleteLookupType()

	public void searchLookupType(String searchlookupTypeName, String searchLookupCodename, WebDriver driver)throws Exception {

		lkpTypeSearchField.clear();
		lkpTypeSearchField.sendKeys(searchlookupTypeName);
		searchBtn.click();
		CommonUtils.hold(5);

		if (searchLookupCodename != null && !searchLookupCodename.isEmpty()) {
			qbeSearchField.clear();
			qbeSearchField.sendKeys(searchLookupCodename);
			qbeSearchField.sendKeys(Keys.ENTER);
			CommonUtils.hold(5);
		}

	}

	public void deleteLookupCode(String lookupTypeName, String lookupTypeCode, WebDriver driver) throws Exception {
		searchLookupType(lookupTypeName, lookupTypeCode, driver);

		try {
			CommonUtils.hold(2);
			lkpCodeDeleteBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(lkpCodecondirmBtn, "Click", "", driver);
			lkpCodecondirmBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(saveBtn, "Click", "", driver);
			saveBtn.click();
			CommonUtils.hold(3);
		} catch (Exception deleteLookUpCode) {
			System.out.println("Delete lookup code operation failed. Please check the error message. "
					+ deleteLookUpCode.getMessage());
		}
	}// End of deleteLookupCode()

	public void selectOptionfrompkDropDown(WebElement ele) {
		Select opt = new Select(ele);
		opt.selectByValue("1");
	}

}
