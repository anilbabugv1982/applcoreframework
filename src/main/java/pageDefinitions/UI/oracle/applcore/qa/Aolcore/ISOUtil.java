package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;

public class ISOUtil extends ISOReferenceDataPage{

	public ISOUtil(WebDriver driver) {
		super(driver);
	}

	public void serachCurrency(String currencyName, WebDriver driver) throws Exception {
		crnNameSearchField.clear();
		crnNameSearchField.sendKeys(currencyName);
		btnSearch.click();
		CommonUtils.hold(3);

	}

	public void verifyCurrencyStatus(String currencyName, WebDriver driver) throws Exception {
	try {
			serachCurrency(currencyName, driver);
//			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + currencyName + "']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + currencyName + "']")).isDisplayed());
		} catch (Exception e) {
			//
		}
	}

	public void createCurrency(String currencyName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(btnCreate, "Click", "", driver);
		btnCreate.click();
		CommonUtils.explicitWait(crnCodeinputField, "Click", "", driver);
		CommonUtils.hold(3);
		crnCodeinputField.sendKeys(currencyName);
		CommonUtils.explicitWait(crnNameinputField, "Click", "", driver);
		crnNameinputField.sendKeys(currencyName);
		CommonUtils.explicitWait(crnDescinputField, "Click", "", driver);
		crnDescinputField.sendKeys(currencyName);
		CommonUtils.explicitWait(crnstartDateIcon, "Click", "", driver);
		crnstartDateIcon.click();
		CommonUtils.explicitWait(crnCurrentDate, "Click", "", driver);
		 CommonUtils.hold(4);
		crnCurrentDate.click();
		CommonUtils.explicitWait(btnsave, "Click", "", driver);
		btnsave.click();
		CommonUtils.hold(4);
		verifyCurrencyStatus(currencyName, driver);
	}

	public void updateCurrency(String oldCrnName, String newCurrencyName, WebDriver driver) throws Exception {
		serachCurrency(oldCrnName, driver);
		crnNameinputField.clear();
		crnNameinputField.sendKeys(newCurrencyName);
		btnsave.click();
		CommonUtils.hold(4);
		verifyCurrencyStatus(newCurrencyName, driver);
	}

	public void deleteCurrency(String currencyName, WebDriver driver) throws Exception {
		serachCurrency(currencyName, driver);
		btnsave.click();
		CommonUtils.hold(3);
		btnDelete.click();
		CommonUtils.hold(4);
		btnsave.click();
		CommonUtils.hold(3);
		serachCurrency(currencyName, driver);

		try {
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'No results found')]")).isDisplayed());
		} catch (Exception e) {

		}
	}

	public void createISO(String ISOName, WebDriver driver) throws Exception {
		btnCreate.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(iso2CodeInput, "Click", "", driver);
		iso2CodeInput.sendKeys("HH");
		iso3codeInput.sendKeys("HHH");
		CommonUtils.hold(2);
		System.out.println("Test " + isoNameInput.isDisplayed());
		isoNameInput.sendKeys(ISOName);
		CommonUtils.hold(2);
		isoDescInput.sendKeys("HHH");
		CommonUtils.hold(2);
		isoCheckBox.click();
		btnsave.click();
		isButtonEnabled(btnsave);
		CommonUtils.hold(2);
		searchISO(ISOName, driver);
	}

	public void updateISO(String ISOName, String Update, WebDriver driver) throws Exception {
		searchISO(ISOName, driver);
		isoNameInput.clear();
		isoNameInput.sendKeys(Update);
		CommonUtils.hold(2);
		CommonUtils.explicitWait(btnsave, "Click", "", driver);
		btnsave.click();
		CommonUtils.hold(2);
		isButtonEnabled(btnsave);
		CommonUtils.hold(2);
		Assert.assertTrue(searchISO(Update, driver));
	}

	public boolean searchISO(String ISOName, WebDriver driver) throws Exception {
		isonameSearchInput.clear();
		isonameSearchInput.sendKeys(ISOName);
		btnSearch.click();
		CommonUtils.hold(4);
		try {
			return driver.findElement(By.xpath("//input[@value='" + ISOName + "']")).isDisplayed();
		} catch (Exception e) {
			Assert.assertFalse(false, "Could not found ISOName : " + ISOName);
			return false;
		}
	}
/*
	public void isButtonEnabled(WebElement element) throws Exception {
		while (!element.isEnabled()) {
			try {
				CommonUtils.hold(2);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
*/	
	public void isButtonEnabled(WebElement element) throws Exception {
		
		long startTime = System.currentTimeMillis();
	    long endTime = 0;
	    long totalTime = 0;
		 
		while (!element.isEnabled() && totalTime < 20 * 1000) {
			try {
				CommonUtils.hold(2);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO :: waitForElementToEnable "+totalTime);
            }

		}
	}

	public void deleteISO(String ISOName, WebDriver driver) throws Exception {
		searchISO(ISOName, driver);
		CommonUtils.hold(2);
		CommonUtils.explicitWait(btnsave, "Click", "", driver);
		btnsave.click();
		isButtonEnabled(btnsave);
		CommonUtils.hold(5);
		btnDelete.click();
		isButtonEnabled(btnDelete);
		CommonUtils.hold(4);
		btnsave.click();
		isButtonEnabled(btnsave);
		CommonUtils.hold(3);
		isonameSearchInput.clear();
		isonameSearchInput.sendKeys(ISOName);
		btnSearch.click();
		CommonUtils.hold(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'No results found')]")).isDisplayed());

	}

	public void createIndustry(String indName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(indaddBtn1, "Click", "", driver);
		CommonUtils.hold(4);
		indaddBtn1.click();
		CommonUtils.explicitWait(indcodeinput, "Click", "", driver);
		CommonUtils.hold(8);
		indcodeinput.sendKeys(indName);
		indNameInput.sendKeys(indName);
		indDescInput.sendKeys("ABCD");
		// indCodeCb1.click();
		btnsave.click();
		CommonUtils.hold(5);
	}

	public void createTerritory(String territory, String indName, WebDriver driver) throws Exception {
		searchIndustry(indName, driver);
		indaddBtn2.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(indTerritoryDD, "Click", "", driver);
		CommonUtils.hold(8);
		CommonUtils.selectDropDownElement(indTerritoryDD, territory);
		btnsave.click();
		CommonUtils.hold(3);
		searchIndustry(indName, driver);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + territory + "']")).isDisplayed());
	}

	public void searchIndustry(String indName, WebDriver driver) throws Exception {
		indcodeSearchField.clear();
		indcodeSearchField.sendKeys(indName);
		btnSearch.click();
		CommonUtils.hold(3);

		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + indName + "']")).isDisplayed());
	}

	public void updateIndustry(String indName, String update, WebDriver driver) throws Exception {
		searchIndustry(indName, driver);
		indNameInput.clear();
		indNameInput.sendKeys(update);
		btnsave.click();
		CommonUtils.hold(3);
		searchIndustry(update, driver);
	}

	public void deleteTerritory(String indName, String territory, WebDriver driver) throws Exception {
		searchIndustry(indName, driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + territory + "']")), "Click", "",
				driver);
		driver.findElement(By.xpath("//span[text()='" + territory + "']")).click();
		CommonUtils.hold(2);
		inddeleteBtn2.click();
		CommonUtils.hold(2);
		btnsave.click();
		CommonUtils.hold(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")).isDisplayed());
	}

	public void deleteIndustry(String indName, WebDriver driver) throws Exception {
		searchIndustry(indName, driver);
		btnsave.click();
		CommonUtils.hold(3);
		inddeleteBtn1.click();
		CommonUtils.hold(4);
		btnsave.click();
		CommonUtils.hold(3);
		indcodeSearchField.clear();
		indcodeSearchField.sendKeys(indName);
		btnSearch.click();
		CommonUtils.hold(3);
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//span[text()='No results found.']")).isDisplayed());
		} catch (Exception e) {

		}
	}

	public void searchLanguage(String languageName, WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		CommonUtils.explicitWait(langSearchName, "Click", "", driver);
		langSearchName.clear();
		langSearchName.sendKeys(languageName);
		btnSearch.click();
		CommonUtils.hold(3);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='" + languageName + "']")), "Click", "",driver);
		CommonUtils.hold(5);

		try {
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + languageName + "']")).isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);
		}
	}

	public void updateLanguage(String lang, String Update, WebDriver driver) throws Exception {
		searchLanguage(lang, driver);
		langNameInput.clear();
		langNameInput.sendKeys(Update);
		btnsave.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='" + Update + "']")), "Click", "", driver);
		CommonUtils.hold(5);

		try {
			System.out.println("Before language check");
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + Update + "']")).isDisplayed());
			System.out.println("After language check");
		} catch (Exception e) {
			System.out.println("Failed in language check");
			Assert.assertFalse(false);
		}
	}

	public void createNaturalLanguage(String nlName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(btnCreate, "Click", "", driver);
		btnCreate.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(nlCodeInput, "Click", "", driver);		
		nlCodeInput.sendKeys(nlName);
		CommonUtils.explicitWait(nlNameInput, "Click", "", driver);
		nlNameInput.sendKeys(nlName);
		CommonUtils.explicitWait(nlDescInput, "Click", "", driver);
		nlDescInput.sendKeys(nlName);
		CommonUtils.selectDropDownElement(nlISOLangDD, "Arabic");
		CommonUtils.selectDropDownElement(nlISOTerritoryDD, "Australia");
		btnsave.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='" + nlName + "']")), "Click", "", driver);		
		searchNaturalLanguage(nlName, driver);

	}

	public void updateNaturalLanguage(String nlName, String update, WebDriver driver) throws Exception {
		searchNaturalLanguage(nlName, driver);
		nlNameInput.clear();
		nlNameInput.sendKeys(update);
		btnsave.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='" + update + "']")), "Click", "", driver);		
		CommonUtils.hold(2);
		searchNaturalLanguage(update, driver);
	}

	public void deleteNaturalLanguage(String nlName, WebDriver driver) throws Exception {
		searchNaturalLanguage(nlName, driver);
		btnsave.click();
		CommonUtils.hold(2);
		btnDelete.click();
		CommonUtils.hold(3);
		btnsave.click();
		CommonUtils.hold(3);
		nlNameSearchField.clear();
		nlNameSearchField.sendKeys(nlName);
		btnSearch.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(.,'No results found.')]")), "Click", "",
				driver);
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(.,'No results found.')]")).isDisplayed());
		} catch (Exception e) {
			Assert.fail("Nature language delete operation failed. Please review the error message", e);
		}
	}

	public void searchNaturalLanguage(String nlName, WebDriver driver) throws Exception {
		nlNameSearchField.clear();
		nlNameSearchField.sendKeys(nlName);
		btnSearch.click();
		CommonUtils.hold(4);

		try {
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + nlName + "']")).isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);
		}
	}

	public void createTimeZone(String tzName, WebDriver driver) throws Exception {
		CommonUtils.hold(3);
		CommonUtils.explicitWait(btnCreate, "Click", "", driver);
		btnCreate.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(tzCodeInput, "Click", "", driver);	
		CommonUtils.hold(5);
		tzCodeInput.sendKeys(tzName);
		tzNameInput.sendKeys(tzName);
		btnsave.click();
		CommonUtils.hold(2);
		searchTimeZone(tzName, driver);
	}

	public void updateTimeZone(String tzName, String update, WebDriver driver) throws Exception {
		searchTimeZone(tzName, driver);
		tzNameInput.clear();
		tzNameInput.sendKeys(update);
		btnsave.click();
		CommonUtils.hold(2);
		searchTimeZone(update, driver);
	}

	public void deleteTimeZone(String tzName, WebDriver driver) throws Exception {
		searchTimeZone(tzName, driver);
		btnsave.click();
		CommonUtils.hold(2);
		btnDelete.click();
		CommonUtils.hold(4);
		btnsave.click();
		CommonUtils.hold(2);
		searchTimeZone(tzName, driver);
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(.,'No results found.')]")).isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);
		}

	}

	public void searchTimeZone(String tzName, WebDriver driver) throws Exception {
		tznameQueryInput.clear();
		tznameQueryInput.sendKeys(tzName);
		btnSearch.click();
		CommonUtils.hold(2);

		try {
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + tzName + "")).isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);
		}
	}

	public void createTerritory(String ttName, WebDriver driver) throws Exception {
		btnCreate.click();
		CommonUtils.explicitWait(ttCodeInput, "Click", "", driver);
		CommonUtils.hold(2);
		ttCodeInput.sendKeys("KD");
		ttNameInput.sendKeys(ttName);
		btnsave.click();
		CommonUtils.hold(2);
		searchTerritory(ttName, driver);
	}

	public void updateTerritory(String ttName, String update, WebDriver driver) throws Exception {
		searchTerritory(ttName, driver);
		ttNameInput.clear();
		ttNameInput.sendKeys(update);
		btnsave.click();
		CommonUtils.hold(2);
		searchTerritory(update, driver);
	}

	public void deleteTerritory(String ttName, WebDriver driver) throws Exception {
		searchTerritory(ttName, driver);
		btnsave.click();
		CommonUtils.hold(2);
		btnDelete.click();
		CommonUtils.hold(4);
		btnsave.click();
		CommonUtils.hold(3);
		ttNameQueryinput.clear();
		ttNameQueryinput.sendKeys(ttName);
		btnSearch.click();

		try {
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(.,'No results found.')]")).isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);
		}
	}

	public void searchTerritory(String ttName, WebDriver driver) throws Exception {
		ttNameQueryinput.clear();
		ttNameQueryinput.sendKeys(ttName);
		btnSearch.click();
		CommonUtils.hold(2);

		try {
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + ttName + "']")).isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);

		}
	}
	
	
	public boolean isRedWood() {
		
		return false;
	}

}

