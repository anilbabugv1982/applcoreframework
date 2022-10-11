/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

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
public class LookupsWrapperClass extends LookupsPage {
	
	public LookupsWrapperClass(WebDriver driver) {
		super(driver);
	}
	

	public void createLookupCode(String lookupTypeName, String lookupCodeText, String meaningText, String descText,	String taskFlowName, WebDriver driver) {

		try {
			searchLookupType(lookupTypeName, "");
			CommonUtils.hold(3);
			CommonUtils.explicitWait(lkpCodeAddBtn, "Click", "",driver);
			lkpCodeAddBtn.click();	
			CommonUtils.hold(5);
			CommonUtils.explicitWait(lkpCodeInput, "Visible", "",driver);
			lkpCodeInput.sendKeys(lookupCodeText);

			if (taskFlowName.equalsIgnoreCase("set")) {
				selectOptionfrompkDropDown(setlkpDataSetSelect);
				//LookupsPage.setlkpCodeStartDateInput.sendKeys(CommonUtils.currentDate("dd/MM/yy"));
				setlkpCodeMeaningInput.clear();
				CommonUtils.hold(1);
				setlkpCodeMeaningInput.sendKeys(meaningText);
				CommonUtils.hold(1);
				setlkpCodeDescInput.clear();
				CommonUtils.hold(1);
				setlkpCodeDescInput.sendKeys(descText);
				setdisplaySeqInput.sendKeys("1");
			} else {
				lkpCodeMeaningInput.clear();
				CommonUtils.hold(1);
				lkpCodeMeaningInput.sendKeys(meaningText);
				CommonUtils.hold(1);
				lkpCodeDescInput.clear();
				CommonUtils.hold(1);
				lkpCodeDescInput.sendKeys(descText);
				displaySeqInput.sendKeys("1");
				//LookupsPage.lkpCodeStartDateInput.sendKeys(CommonUtils.currentDate("dd/MM/yy"));
			}
			CommonUtils.hold(2);
			saveBtn.click();
		//	CommonUtils.ExplicitWait(DriverInstance.getDriverInstance().findElement(By.xpath("//input[@value='" +meaningText+ "']")), "Visible", "");
			CommonUtils.hold(5);

			//verifyStatus(lookupTypeName, lookupCodeText, meaningText, descText);
			
		}catch(Exception CLCE) {
			System.out.println("Exception in CreteLookupCode()");
			CLCE.printStackTrace();
		}
		
	}//End of createLookupCode()

	public void createLookupType(String lookupTypeText, String meaningText, String descText, String taskFlowName,WebDriver driver) {
		
		try {
			CommonUtils.explicitWait(lkpTypeAddBtn, "Click", "",driver);
			lkpTypeAddBtn.click();
			CommonUtils.explicitWait(lkpTypeinputField, "Visible", "",driver);
			CommonUtils.hold(5);
			lkpTypeinputField.sendKeys(lookupTypeText);

			if (taskFlowName.equalsIgnoreCase("set")) {
				selectOptionfrompkDropDown(setlkpGroupNameSelect);
			}

			lkpTypeMeaningInput.sendKeys(meaningText);
			CommonUtils.hold(1);
			lkpTypeDescInput.sendKeys(descText);
			CommonUtils.hold(1);
			moduleInput.sendKeys("Oracle Middleware Extensions for Applications");
			CommonUtils.hold(2);
			saveBtn.click();
			
			//verifyStatus(lookupTypeText, "", meaningText, descText);
		}catch(Exception CLTE) {
			System.out.println("Exception in CreateLookupType()");
			CLTE.printStackTrace();
		}
	}//End of createLookupType()
	
	public void WaitAfterCreateLookupType(String meaningText, WebDriver driver) {
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='" + meaningText + "']")), "Visible", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonUtils.hold(5);
	}

	public void updateLookupType(String lookupTypeName, String meaning, String description,WebDriver driver) {

		searchLookupType(lookupTypeName, "");

		try {
			CommonUtils.explicitWait(lkpCodeMeaningInput, "Visible", "",driver);
		} catch (Exception ULTE) {
			System.out.println("Exception in updateLookupType()");
			ULTE.printStackTrace();
		}
		lkpTypeMeaningInput.clear();
		lkpTypeMeaningInput.sendKeys(meaning);
		lkpTypeDescInput.clear();
		lkpTypeDescInput.sendKeys(description);
		saveBtn.click();				
		CommonUtils.hold(5);
		
		//verifyStatus(lookupTypeName, "", meaning, description);

	}//End of updateLookupType()

	public void updateLookupCode(String lookupTypeName, String lookUpCodeName, String meaning, String description,
			String taskFlowName,WebDriver driver) {

		searchLookupType(lookupTypeName, lookUpCodeName);
		try {
			CommonUtils.explicitWait(lkpCodeMeaningInput, "Visible", "",driver);
		} catch (Exception ULCE) {
			System.out.println("Exception in updateLookupCode()");
			ULCE.printStackTrace();
		}
		CommonUtils.hold(5);
		if (taskFlowName.equalsIgnoreCase("set")) {

			setlkpCodeMeaningInput.clear();
			setlkpCodeMeaningInput.sendKeys(meaning);
			setlkpCodeDescInput.clear();
			setlkpCodeDescInput.sendKeys(description);
		} else {

			lkpCodeMeaningInput.clear();
			lkpCodeMeaningInput.sendKeys(meaning);
			lkpCodeDescInput.clear();
			lkpCodeDescInput.sendKeys(description);
		}
		CommonUtils.hold(5);
		saveBtn.click();
		CommonUtils.hold(5);

	}//End of updateLookupCode()

	public boolean verifyStatus(String lookupTypeName, String lookupTypeCode, String lookupMeaning, String lookUpDescription,WebDriver driver) {
		boolean verifyStatus = false;
		searchLookupType(lookupTypeName, lookupTypeCode);
		try {
			System.out.println("Verifying Changes");
			CommonUtils.explicitWait(lkpTypeAddBtn, "Visible", "",driver);
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='"+lookupMeaning+"']")).isDisplayed()
					&& driver.findElement(By.xpath("//input[@value='"+lookUpDescription+"']")).isDisplayed());
			verifyStatus = true;
		} catch (Exception VSE) {
			System.out.println("Exception in verifyStatus()");
			Assert.fail();
			VSE.printStackTrace();
		}
		return verifyStatus;
	}

	public void verifyDeleteCode(String lookupTypeName, String searchLookupCodename, WebDriver driver) {
		searchLookupType(lookupTypeName, searchLookupCodename);
		boolean ele = false;
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + searchLookupCodename + "']")).isDisplayed()); 
		} catch (Exception e) {
			Assert.assertTrue(!ele);
		}
	}

	public void verifyDeleteType(String lookupTypeName,WebDriver driver) {
		searchLookupType(lookupTypeName, "");
		boolean ele = false;
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + lookupTypeName + "']")).isDisplayed());
			
		} catch (Exception e) {
			Assert.assertTrue(!ele);
		}
	}//End of VerifyDeleteType()

	public void deleteLookupType(String lookupTypeName,WebDriver driver) {
		searchLookupType(lookupTypeName, "");
		lkpTypeDeleteBtn.click();
		try {
			CommonUtils.explicitWait(lkpTypecondirmBtn, "Click", "",driver);
		} catch (Exception DLTE) {
			System.out.println("Exception in deleteLookupType()");
			DLTE.printStackTrace();
		}
		lkpTypecondirmBtn.click();
		CommonUtils.hold(2);
		saveBtn.click();
		// searchLookupType(lookupTypeName,"");
		// boolean ele = false;
		// try{
		// ele=driver.findElement(By.xpath("//span[text()='"+lookupTypeName+"']")).isDisplayed();
		// }catch(Exception e){
		// Assert.assertTrue(!ele);
		// }
		verifyDeleteType(lookupTypeName,driver);
	}//End of deleteLookupType()

	public void searchLookupType(String searchlookupTypeName, String searchLookupCodename) {
		System.out.println("SearchLookupType()");
		lkpTypeSearchField.clear();
		lkpTypeSearchField.sendKeys(searchlookupTypeName);
		searchBtn.click();
		CommonUtils.hold(4);

		if (searchLookupCodename != null && !searchLookupCodename.isEmpty()) {
			qbeSearchField.clear();
			qbeSearchField.sendKeys(searchLookupCodename);
			qbeSearchField.sendKeys(Keys.ENTER);
			CommonUtils.hold(3);
		}

	}

	public void deleteLookupCode(String lookupTypeName, String lookupTypeCode,WebDriver driver) {
		searchLookupType(lookupTypeName, lookupTypeCode);

		try {
			lkpCodeDeleteBtn.click();
			CommonUtils.explicitWait(lkpCodecondirmBtn, "Click", "",driver);
			lkpCodecondirmBtn.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(saveBtn, "Click", "",driver);
			saveBtn.click();

			CommonUtils.hold(3);
			// searchLookupType(lookupTypeName, lookupTypeCode);
			// boolean ele = false;
			// try{
			// ele=driver.findElement(By.xpath("//span[text()='"+lookupTypeCode+"']")).isDisplayed();
			// }catch(Exception e){
			// Assert.assertTrue(!ele);
			// }
			verifyDeleteCode(lookupTypeName, lookupTypeCode,driver);
		}catch(Exception DLCE) {
			System.out.println("Exception in deleteLookupCode()");
		}
	}//End of deleteLookupCode()

	public void selectOptionfrompkDropDown(WebElement ele) {

		Select opt = new Select(ele);
		opt.selectByValue("1");
		;
	}
	
}
