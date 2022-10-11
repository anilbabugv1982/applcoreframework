package pageDefinitions.UI.oracle.applcore.qa.Eff;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;
import TestBase.UI.GetDriverInstance;

public class RuntimeValidation extends EFFReferenceDataPage{
	public RuntimeValidation(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		effUtils = new EFFUtils(driver);
	}

	private WebDriverWait wait;
	EFFUtils effUtils;


	public boolean navigateToManageLocations() {
		boolean bNavigate=false;
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(15);
		/*{try

            Boolean isPresent=searchField.isDisplayed();
            if(isPresent)
            {
                searchField.sendKeys("Manage Locations");
                CommonUtils.hold(4);
                searchLocation.click();
                CommonUtils.hold(6);
                manageLocationLink.click();
                CommonUtils.hold(4);
            }


        }
        catch(Exception e)
        {
            System.out.println(" workforce structure page not present");
            wait.until(ExpectedConditions.visibilityOf(manageLocationsLink));
            wait.until(ExpectedConditions.elementToBeClickable(manageLocationsLink));
            manageLocationsLink.click();
        }*/
		searchField.sendKeys("Manage Locations");
		CommonUtils.hold(4);
		searchLocation.click();
		CommonUtils.hold(10);
		manageLocationLink.click();
		CommonUtils.hold(4);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		//EFFUtils.waitConditionForElement(headerTextManageLocations);
		if(headerTextManageLocations.isDisplayed()) {
			System.out.println("Navigated to Manage Locations");
			bNavigate=true;
		}
		else {
			System.out.println("ERROR - Did not navigate to Manage Locations");
		}
		return bNavigate;
	}

	public boolean searchLocation(String location) {
		boolean bSearchLoc=false;
		CommonUtils.waitForPageLoad(driver);
		wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOf(locationsCode));
		wait.until(ExpectedConditions.elementToBeClickable(locationsCode));
		wait.until(ExpectedConditions.elementToBeClickable(locationsSearchButton));
		CommonUtils.hold(2);
		locationsCode.sendKeys(location);
		CommonUtils.hold(2);
		locationsSearchButton.click();
		CommonUtils.waitForElement(searchLocationsResultsTable,driver);
		CommonUtils.hold(2);
		WebElement locationRow = driver.findElement(By.xpath("//table//a/span[text()='"+location+"']"));
		if(locationRow.isDisplayed()) {
			System.out.println("Location - "+location+" is found");
			bSearchLoc=true;
			locationRow.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(2);
		}
		else {
			System.err.println("ERROR : Location - "+location+" is not found");
		}
		return bSearchLoc;
	}

	public void navigateToLocation(String location) {
		CommonUtils.hold(4);
		WebElement locationRow = driver.findElement(By.xpath("//table//a/span[text()='"+location+"']"));
		if(locationRow.isDisplayed()) {
			System.out.println("Location - "+location+" is found");
			locationRow.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(2);
		}
		else {
			System.err.println("ERROR : Location - "+location+" is not found");
		}
	}

	private void editLocation() {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		/*wait.until(ExpectedConditions.visibilityOf(editExpandlink));
		wait.until(ExpectedConditions.elementToBeClickable(editExpandlink));*/
		CommonUtils.waitForElement(editExpandlink,driver);
		if(editExpandlink.isDisplayed()) {
			System.out.println("Edit expand is displayed");
		}
		CommonUtils.hold(10);
		editExpandlink.click();
		CommonUtils.hold(10);
		updateButton.click();
		CommonUtils.hold(5);
		alertDialogBoxClose(okButton_DialogBox);
		CommonUtils.hold(5);
		if(driver.findElements(By.xpath("//button[contains(@id,'AP1:dialog3::ok')]")).size()>0) {
			alertDialogBoxClose(okButton_WarningDialogBox);
		}
		else {
			System.out.println("No Warning dialog box displayed after clicking Update button");
		}
		/*try {
			if(okButton_WarningDialogBox.isDisplayed()) {

			}
		}
		catch (NoSuchElementException noElement) {
			noElement.printStackTrace();
			System.out.println("No Warning dialog box displayed after clicking Update button");
		}*/
	}

	public boolean updateSingleSegmentValues(String context,String segmentname,String displaytype,String value) throws Exception {
		boolean bSegmentUpdateSuccessful=false;
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(6);
		editLocation();
		CommonUtils.hold(6);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2400)", "");
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		WebElement segmentDropDown;
		WebElement selectItem;
		WebElement selectItemDisplayed;
		if (driver.findElement(By.xpath("//label[@class='af_panelLabelAndMessage_label-text'][contains(text(),'"+segmentname+"')]")).isDisplayed()) {
			System.out.println("Context - "+context+" and Segment - "+segmentname+" is displayed");
		}
		selectItemDisplayed = driver.findElement(By.xpath("//input[contains(@id,'"+segmentname.toLowerCase()+"::content') or contains(@id,'"+segmentname.toLowerCase()+"_DisplayId::content') or contains(@id,'"+segmentname.toLowerCase()+"_Display::content')]"));
		if(displaytype.equals("drop-down")) {
			segmentDropDown = driver.findElement(By.xpath("//a[contains(@id,'"+segmentname.toLowerCase()+"::drop')]"));
			segmentDropDown.click();
			CommonUtils.hold(2);
			selectItem = driver.findElement(By.xpath("//li[text()='"+value+"']"));
			selectItem.click();
			CommonUtils.hold(2);
		}
		if(displaytype.equals("Inline Search")) {
			selectItemDisplayed.click();
			selectItemDisplayed.clear();
			selectItemDisplayed.sendKeys("");
			selectItemDisplayed.click();
			CommonUtils.hold(4);
			//WebElement expandSearch = driver.findElement(By.xpath("//a[contains(@id,'basesegment_DisplayId::btn')]"));
			CommonUtils.hold(7);
			WebElement listTable = driver.findElement(By.xpath("//table[contains(@id,'"+segmentname.toLowerCase()+"_DisplayId') or contains(@id,'"+segmentname.toLowerCase()+"_Display') or contains(@id,'"+segmentname.toLowerCase()+"::sgstnBdy')]"));
			CommonUtils.hold(5);
			if(driver.findElement(By.xpath("//td[contains(text(),'"+value+"')]")).isDisplayed()) {
				System.out.println("Option - "+value+" is present");
				driver.findElement(By.xpath("//td[contains(text(),'"+value+"')]")).click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(4);
				bSegmentUpdateSuccessful=true;
			}
		}
		CommonUtils.hold(4);
		if(selectItemDisplayed.isDisplayed()) {
			System.out.println("Value displayed after selection -"+selectItemDisplayed.getAttribute("value"));
			if(selectItemDisplayed.getAttribute("value").equals(value)) {
				System.out.println("Correct value - "+value+" is selected in drop-down");
				bSegmentUpdateSuccessful=true;
			}
			else {
				System.err.println("ERROR - Invalid value is selected");
			}
		}
		else {
			System.err.println("ERROR - could not find the object to display value");
		}

		js.executeScript("window.scrollBy(0,-2500)","");
		CommonUtils.hold(2);
		saveButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		if(okConfirmationDialog.isDisplayed()) {
			alertDialogBoxClose(okConfirmationDialog);
		}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		submitButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(yes_WarningDialogBox, ExplicitWaitCalls.Click.toString(), "",driver);
		CommonUtils.hold(3);

		if(yes_WarningDialogBox.isDisplayed() || no_WarningDialogBox.isDisplayed()) {
			alertDialogBoxClose(yes_WarningDialogBox);
		}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		alertDialogBoxClose(okConfirmationDialog);
		CommonUtils.waitForPageLoad(driver);
		return bSegmentUpdateSuccessful;
	}

	private void alertDialogBoxClose(WebElement okButton) {
		wait = new WebDriverWait(driver, 300);
		CommonUtils.waitForPageLoad(driver);
		wait.until(ExpectedConditions.visibilityOf(okButton));
		wait.until(ExpectedConditions.elementToBeClickable(okButton));
		CommonUtils.waitForElement(okButton,driver);
		CommonUtils.hold(2);
		okButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
	}

	public boolean createItem(String itemName,String itemUsage,String itemClassName, WebDriver driver) throws Exception {
		boolean bItemCreated=false;
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(50);
		pimPanelDrawer.click();
		CommonUtils.explicitWait(createItemLink, "Click", "",driver);
		createItemLink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(orgName, "Click", "",driver);
		orgName.clear();
		orgName.sendKeys("V1");
		CommonUtils.waitForElement(org_V1,driver);
		CommonUtils.hold(4);
		org_V1.click();
		CommonUtils.waitForElement(orgName,driver);
		CommonUtils.hold(30);
		itemClassInput.clear();
		CommonUtils.hold(3);
		itemClassInput.clear();
		CommonUtils.hold(3);
		itemClassInput.sendKeys(itemClassName);
		CommonUtils.waitForElement(itemClassListItem,driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(itemClassListItem, ExplicitWaitCalls.Visible.toString(), itemClassName,driver);
		CommonUtils.hold(4);
		if(itemClassListItem.getText().contains(itemClassName)) {
			System.out.println("Item Class list has the required Item Class - "+itemClassName);
			itemClassListItem.click();
			CommonUtils.hold(8);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@id,'selectManyShuttle1') and @class='af_selectOrderShuttle']/table[contains(@id,'AppTe:0:selectManyShuttle1::content')]")), "Click", "",driver);
			effUtils.waitConditionForElement(driver.findElement(By.xpath("//div[contains(@id,'selectManyShuttle1') and @class='af_selectOrderShuttle']/table[contains(@id,'AppTe:0:selectManyShuttle1::content')]")));
			//CommonUtils.explicitWait(itemCreate_OkButton, "Click", "",driver);
			effUtils.waitConditionForElement(itemCreate_OkButton);
			itemCreate_OkButton.click();

			CommonUtils.hold(5);
			/*try {
				alertDialogBoxClose(YesButton_DialogBox);
			} catch (Exception e) {
				System.out.println("Warning box not displayed.");
			}*/
			if(driver.findElements(By.xpath("//button[contains(normalize-space(text()),'es')]")).size()>0) {
				alertDialogBoxClose(YesButton_DialogBox);
			}
			else {
				System.out.println("Warning box not displayed.");
			}
			CommonUtils.hold(20);
			//CommonUtils.explicitWait(driver.findElement(By.xpath("//h1[contains(@class,'af_panelHeader_title-text0')]")), "Click", "",driver);
			effUtils.waitConditionForElement(driver.findElement(By.xpath("//*[contains(@class,'af_panelHeader_title-text0')]")));
			CommonUtils.hold(10);
			itemNameInputText.clear();
			CommonUtils.hold(4);
			itemNameInputText.sendKeys(itemName);
			CommonUtils.hold(4);
			itemDescriptionTextArea.sendKeys(itemName);
			//CommonUtils.explicitWait(itemSave, "Click", "",driver);
			effUtils.waitConditionForElement(itemSave);
			System.out.println("Saving Item..");
			CommonUtils.hold(2);
			itemSave.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(60);
			effUtils.waitConditionForElement(itemSave);
			if(driver.findElement(By.xpath("//span[contains(@id,'inputText1::content')]")).isDisplayed()) {
				System.out.println("Item saved successfully");
				WebElement itemSavedName = driver.findElement(By.xpath("//span[contains(@id,'inputText1::content')]"));
				if(itemSavedName.getText().equals(itemName)) {
					System.out.println("Item name saved is same as user Input");
					bItemCreated=true;
				}
				else {
					System.err.println("Item name saved is not same as user Input");
				}
			}

		}
		else {
			System.err.println("Current Item Class displayed - "+itemClassListItem.getText()+" does not match with the expected Item Class - "+itemClassName);
		}
		return bItemCreated;
	}

	public boolean pageExistsUnderItemUsage(String itemUsage,String pageName) {
		boolean bPageExists=false;
		CommonUtils.hold(4);
		if(itemUsage.equals("Item") || itemUsage.equals("Item Revision")) {
			System.out.println("Current Item Usage - "+itemUsage);
			specificationsLink.click();
		}
		if(itemUsage.equals("Item Supplier")) {
			System.out.println("Current Item Usage - "+itemUsage);
			/*CommonUtils.hold(4);
			associationsLink.click();
			CommonUtils.hold(4);
			navigateToSupplierPage();*/
		}
		
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)");
		if(driver.findElements(By.xpath("//span[text()='"+pageName+"']")).size()>0) {
			System.out.println("Page Name - "+pageName+"displayed");
			driver.findElement(By.xpath("//span[text()='"+pageName+"']")).click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(2);
			bPageExists=true;
		}
		else {
			System.out.println("ERROR - Page - "+pageName+" not found under Item");
		}
		return bPageExists;
	}

	public boolean contextExistsUnderPage(String pageName,String contextCode, String itemUsage) {
		boolean bContextExists=false;
		String currentPageNameWithUsage=itemUsage+": "+pageName;
		CommonUtils.hold(2);
		driver.findElement(By.xpath("//span[text()='"+pageName+"']/parent::a")).click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForElement(driver.findElement(By.xpath("//*[text()='"+currentPageNameWithUsage+"']")),driver);
		try {
			if(driver.findElement(By.xpath("//*[text()='"+contextCode+"']")).isDisplayed()) {
				System.out.println("Context -"+contextCode+" is displayed under Page -"+pageName);
				bContextExists=true;
			}
		}
		catch(NoSuchElementException noElement) {
			System.out.println("Inside catch block of Context-"+contextCode+" existence under Page -"+pageName);
			System.out.println("Context -"+contextCode+" is not displayed under Page -"+pageName);
		}
		return bContextExists;
	}

	public void itemSaveAndClose() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-400)", "");
		CommonUtils.hold(4);
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", itemSaveExpand);
		CommonUtils.waitForElement(itemSaveExpand,driver);
		//js.executeScript("arguments[0].click()",itemSaveExpand );
		wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOf(itemSaveExpand));
		wait.until(ExpectedConditions.elementToBeClickable(itemSaveExpand));
		itemSaveExpand.click();
		wait.until(ExpectedConditions.visibilityOf(itemSaveAndClose));
		wait.until(ExpectedConditions.elementToBeClickable(itemSaveAndClose));
		CommonUtils.waitForElement(itemSaveAndClose,driver);
		itemSaveAndClose.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(2);
	}

	public void itemSave() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-400)", "");
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(2);
		wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOf(itemSave));
		wait.until(ExpectedConditions.elementToBeClickable(itemSave));
		wait.until(ExpectedConditions.elementToBeClickable(itemSaveExpand));
		CommonUtils.hold(2);
		itemSave.click();
	}

	public boolean createSupplierUnderItem() {
		CommonUtils.hold(4);
		associationsLink.click();
		CommonUtils.hold(4);
		CommonUtils.waitforElementtoClick(14, supplierOrganizations, driver);
		supplierOrganizations.click();
		CommonUtils.hold(15);
		supplierOrgSelectAndAddIcon.click();
		CommonUtils.hold(10);
		effUtils.waitConditionForElement(supplierOrgAssociationsHeader);
		supplierName.clear();
		supplierName.sendKeys("computer services");
		effUtils.waitConditionForElement(supplierSearchButton);
		supplierSearchButton.click();
		CommonUtils.hold(10);
		supplierAddressTableFirstRow.click();
		CommonUtils.hold(5);
		supplierAddressApplyButton.click();
		CommonUtils.hold(6);
		supplierAddressDoneButton.click();
		CommonUtils.hold(10);
		effUtils.scrollToPageTop();
		itemSave();
		effUtils.scrollToPageBottom();
		if(supplierNameAdded.isDisplayed()) {
			System.out.println("Supplier - COMPUTER SERVICES added to the Suppliers list");
			return true;
		}
		else {
			System.err.println("ERROR - Supplier - COMPUTER SERVICES is not added to the Supplier Organization list");
			return false;
		}
	}

	public void navigateToSupplierPage() {
		supplierNameAdded.click();
		CommonUtils.hold(3);
		effUtils.scrollToPageBottom();

	}

	public boolean checkDefaultGroovySegmentValue(String currentUser,String segmentName) {
		CommonUtils.hold(4);
		String defaultText=driver.findElement(By.xpath("//input[contains(@id,'"+segmentName+"::content')]")).getAttribute("value");
		System.out.println("Default Groovy segment text - "+defaultText);
		if(defaultText.equals(currentUser)) {
			System.out.println("Default Groovy segment is same as current user logged in");
			return true;
		}
		else {
			System.err.println("ERROR - Default Groovy segment - '"+defaultText+"' does not match the current user - '"+currentUser+"'");
			return false;
		}
	}

	public void openManageItems() {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(20);
		CommonUtils.waitForElement(pimPanelDrawer,driver);
		pimPanelDrawer.click();
		CommonUtils.hold(10);
		CommonUtils.waitForElement(manageItemsLink,driver);
		manageItemsLink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForElement(searchButton_ManageItems,driver);
		CommonUtils.waitForElement(itemNameSearchInput,driver);
		System.out.println("Manage Items for search is opened");
	}

	public boolean basicManageItemSearch(String itemClass,String itemName) {
		boolean bItemSearch=false;
		CommonUtils.hold(3);
		wait = new WebDriverWait(driver, 300);
		searchItemClassFromManageItems(itemName, itemClass);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(120);
		wait.until(ExpectedConditions.visibilityOf(itemNameSearchInput));
		wait.until(ExpectedConditions.elementToBeClickable(itemNameSearchInput));
		itemNameSearchInput.clear();
		itemNameSearchInput.sendKeys(itemName);
		CommonUtils.hold(3);
		searchButton_ManageItems.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(30);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@id,'table1::db')]/table"))));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[text()='"+itemName+"']/parent::a"))));
		CommonUtils.hold(2);
		if(driver.findElements(By.xpath("//div[contains(@id,'table1::db')]/table")).size()>0) {
			System.out.println("Item Search result table loaded");
			if(driver.findElement(By.xpath("//span[text()='"+itemName+"']/parent::a")).isDisplayed()) {
				System.out.println("Item search for -"+itemName+" is successful");
				bItemSearch=true;
			}
			else {
				System.err.println("ERROR - Item : "+itemName+" not found under search result");
			}
		}
		else {
			System.out.println("Item search table not loaded");
		}
		return bItemSearch;
	}

	private boolean searchItemClassFromManageItems(String itemName,String itemClass) {
		boolean isAvailable=false;
		itemSearchExpandLink.click();
		CommonUtils.waitForElement(searchLink,driver);
		CommonUtils.hold(5);
		searchLink.click();
		CommonUtils.hold(5);
		itemSearchInputText.clear();
		//driver.switchTo().frame(vsPopupSearch);
		itemSearchInputText.sendKeys(itemClass);
		itemSearchButton.click();
		WebElement vsSearchName = driver.findElement(By.xpath("//span[contains(@id,'_afrColChild0') and text()='"+itemClass+"']"));
		vsSearchName.click();
		searchOKBtn.click();
		CommonUtils.waitForElement(itemSearchInputComboBox,driver);
		CommonUtils.hold(5);
		if(itemSearchInputComboBox.getAttribute("value").equals(itemClass)) {
			System.out.println("Correct Item Class - "+itemSearchInputComboBox.getAttribute("value")+" is selected");
			isAvailable=true;
		}
		else {
			System.err.println("Incorrect Item Class - "+itemSearchInputComboBox.getAttribute("value")+" is displayed");
		}
		return isAvailable;
	}

	public boolean addFieldsSearchItems(String context,String segment) throws Exception {
		boolean bAddFields=false;
		wait=new WebDriverWait(driver, 60);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(6);
		if(advancedSearch.getAttribute("aria-expanded").equals("false")) {
			System.out.println("Add Fields button not displayed by default");
			advancedSearch.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(3);
			wait.until(ExpectedConditions.visibilityOf(addFieldsButton));
			wait.until(ExpectedConditions.elementToBeClickable(addFieldsButton));
			CommonUtils.hold(3);
		}
		try {
			addFieldsButton.click();
		}
		catch(Exception noElement) {
			if (driver.findElements(By.xpath("//div[contains(@id,'at1:_ATp:_qbeTbr')]/a")).size()==0) {
				System.out.println("Add Fields button not clicked earlier. Clicking again..");
				addFieldsButton.click();
			}
		}

		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(2);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[contains(@id,'at1:_ATp:_qbeTbr')]/a"))));
		try {
			if(driver.findElement(By.xpath("//div[contains(@id,'at1:_ATp:_qbeTbr')]/a")).getAttribute("aria-pressed").equals("false")) {
				System.out.println("Filters not displayed by default");
				CommonUtils.hold(4);
				driver.findElement(By.xpath("//div[contains(@id,'at1:_ATp:_qbeTbr')]/a")).click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(6);
			}
		}
		catch(ElementNotInteractableException noElement) {
			if(driver.findElements(By.xpath("//div[contains(@id,'at1:_ATp:_qbeTbr')]/a")).size()==0) {
				addFieldsButton.click();
				CommonUtils.waitForPageLoad(driver);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@id,'at1:_ATp:_qbeTbr')]/a")));
				wait.until(ExpectedConditions.visibilityOf(attributeGroupsInputText));
				wait.until(ExpectedConditions.visibilityOf(attributesInputText));
			}
		}
		attributeGroupsInputText.clear();
		attributeGroupsInputText.sendKeys(context);
		CommonUtils.hold(2);
		attributesInputText.clear();
		attributesInputText.sendKeys(segment);
		attributesInputText.sendKeys(Keys.ENTER);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForElement(driver.findElement(By.xpath("//table[@summary='Available Attributes']")),driver);
		CommonUtils.hold(3);
		if(driver.findElement(By.xpath("//span[contains(text(),'"+segment+"')]")).isDisplayed()) {
			System.out.println("Segment name - "+segment+" is displayed");
			driver.findElement(By.xpath("//span[contains(text(),'"+segment+"')]")).click();
			CommonUtils.hold(2);
			addButton_SearchAttributes.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(4);
			if(driver.findElement(By.xpath("//div[contains(@id,'_ATp:t2::db')]/table//span[text()='"+segment+"']")).isDisplayed()) {
				System.out.println("Selected Attribute - "+segment+" is added");
				CommonUtils.waitForElement(driver.findElement(By.xpath("//div[contains(@id,'_ATp:t2::db')]/table//span[text()='"+segment+"']")),driver);
				driver.findElement(By.xpath("//div[contains(@id,'_ATp:t2::db')]/table//span[text()='"+segment+"']")).click();
				CommonUtils.explicitWait(okConfirmationDialog, "Click", "",driver);
				okConfirmationDialog.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(4);
				if(driver.findElement(By.xpath("//label[contains(text(),'"+segment+"') and @class='af_query_criterion::label-text']")).isDisplayed()) {
					System.out.println("Selected Attribute - "+segment+" is successfully added to Manage Items Search");
					bAddFields=true;
				}
				else {
					System.err.println("ERROR - Selected Attribute - "+segment+" is not displayed under Manage Items Search");
				}
			}
			else {
				System.err.println("ERROR - Selected Attribute "+segment+" is not added to table");
			}

		}
		else {
			System.out.println("ERROR - Segment not found");
		}
		CommonUtils.hold(3);

		return bAddFields;
	}

	public void createSegmentValuesForInd_DepVS(String indSegmentName,String depSegmentName) {
		/*
		 * Valueset values should be charvs - {a1,a2}.
		 * numvs - {a1-{1001.00,1002.02},a2 - {2001.02,2002.00}}
		 * */
		CommonUtils.waitForPageLoad(driver);
		wait = new WebDriverWait(driver, 300);
		CommonUtils.hold(3);
		addSegmentRow.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		WebElement currentRowIndValue=driver.findElement(By.xpath("//select[contains(@id,'0:"+indSegmentName.toLowerCase()+"::content')]"));
		CommonUtils.selectDropDownElement(currentRowIndValue, "Operations");
		CommonUtils.hold(6);
		WebElement currentRowDepValue=driver.findElement(By.xpath("//select[contains(@id,'0:"+depSegmentName.toLowerCase()+"::content')]"));
		CommonUtils.selectDropDownElement(currentRowDepValue, "Technical");
		CommonUtils.hold(6);
		addSegmentRow.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		currentRowIndValue=driver.findElement(By.xpath("//select[contains(@id,'1:"+indSegmentName.toLowerCase()+"::content')]"));
		CommonUtils.selectDropDownElement(currentRowIndValue, "Administration");
		CommonUtils.hold(6);
		currentRowDepValue=driver.findElement(By.xpath("//select[contains(@id,'1:"+depSegmentName.toLowerCase()+"::content')]"));
		CommonUtils.selectDropDownElement(currentRowDepValue, "General Admin");
		CommonUtils.hold(6);

	}

	public boolean searchSegmentValuesUnderItem(String indSegmentName) {
		boolean bValueExists=false;
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		if(driver.findElement(By.xpath("//*[contains(@id,'0:"+indSegmentName.toLowerCase()+"::content')]")).getText().equals("Operations")) {
			System.out.println("Independent value for Segment -"+indSegmentName.toLowerCase()+" is displayed properly");
			bValueExists=true;
		}
		else {
			System.err.println("Independent Value for Segment -"+indSegmentName.toLowerCase()+" is not displayed");
		}

		return bValueExists;
	}

	public boolean searchSegmentDefaultValue(String segmentName,String value) {
		boolean bValueExists=false;
		Deployment dep = new Deployment(driver);
		CommonUtils.waitForPageLoad(driver);
		dep.scrollToPageBottom();
		CommonUtils.hold(5);
		if(driver.findElements(By.xpath("//select[contains(@id,'"+segmentName+"::content')]")).size()>0) {
			System.out.println("Source Segment - "+segmentName+" displayed under page");
			if(driver.findElement(By.xpath("//select[contains(@id,'"+segmentName+"::content')]")).getAttribute("title").equals(value)) {
				System.out.println("Default value displayed under Drop-down -"+value+" is correct");
				bValueExists=true;
			}
			else {
				System.err.println("ERROR - Default value displayed under Drop-down : "+driver.findElement(By.xpath("//select[contains(@id,'"+segmentName+"::content')]")).getAttribute("title")+" does not match with expected value - "+value);
			}
		}
		else {
			System.err.println("ERROR - Segment : "+segmentName+" is not displayed");
		}
		return bValueExists;
	}

	public boolean searchTargetContextValue(String segmentName,String value) {
		boolean bTargetValue=false;
		if(driver.findElements(By.xpath("//select[contains(@id,'"+segmentName+"::content')]")).size()>0) {
			System.out.println("Source Segment - "+segmentName+" displayed under page");
			if(driver.findElements(By.xpath("//select[contains(@id,'"+segmentName+"::content')]/option[@title='"+value+"']")).size()>0) {
				System.out.println("Value displayed under Target segment Drop-down -"+value);
				CommonUtils.hold(3);
				CommonUtils.selectDropDownElement(driver.findElement(By.xpath("//select[contains(@id,'"+segmentName+"::content')]")), value);
				bTargetValue=true;
				CommonUtils.hold(2);
			}
			else {
				System.err.println("ERROR - Value :"+value+" does not exists");
			}
		}
		else {
			System.err.println("ERROR - Segment : "+segmentName+" is not displayed");
		}
		return bTargetValue;
	}

	public boolean updateTableSegmentValues(String segmentName,String currentValue,WebDriver driver) {
		CommonUtils.hold(8);
		effUtils.scrollToPageBottom();
		CommonUtils.hold(3);
		effUtils.waitConditionForElement(addSegmentRow);
		addSegmentRow.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(8);
		WebElement expandLOV=driver.findElement(By.xpath("//a[contains(@id,'"+segmentName.toLowerCase()+"::lovIconId')]"));
		expandLOV.click();
		CommonUtils.hold(4);
		effUtils.waitConditionForElement(driver.findElement(By.xpath("//span[contains(@id,'"+segmentName.toLowerCase()+"::dropdownPopup::dropDownContent') and text()='"+currentValue+"']")));
		WebElement requiredValue=driver.findElement(By.xpath("//span[contains(@id,'"+segmentName.toLowerCase()+"::dropdownPopup::dropDownContent') and text()='"+currentValue+"']"));
		requiredValue.click();
		CommonUtils.hold(15);
		WebElement lovText=driver.findElement(By.xpath("//input[contains(@id,':"+segmentName.toLowerCase()+"::content')]"));
		if(lovText.getAttribute("value").equals(currentValue)) {
			System.out.println("Current value - "+currentValue+" is selected under LOV for table");
			CommonUtils.hold(2);
			return true;
		}
		else {
			System.err.println("Current value - "+currentValue+" is not selected under LOV for table");
			CommonUtils.hold(2);
			return false;
		}

	}

	public boolean validateTableValueRunTime(String segmentName,String currentValue,WebDriver driver) {
		boolean bValueExists=false;
		effUtils.scrollToPageBottom();
		CommonUtils.hold(5);
		if(driver.findElement(By.xpath("//span[text()='"+currentValue+"']")).isDisplayed()) {
			System.out.println("Select table value - "+currentValue+" is saved under Item and displayed properly");
			bValueExists=true;
		}
		else {
			System.err.println("Selected table value - "+currentValue+" has some issue and not saved under Item");
		}
		return bValueExists;
	}

	public String getErrorMessageForFormatTypeSegment(String segmentName,String textName,String dataType,WebDriver driver) {
		String errorMessage="";
		CommonUtils.hold(4);
		effUtils.waitConditionForElement(driver.findElement(By.xpath("//input[contains(@id,'"+segmentName.toLowerCase()+"::content')]")));
		WebElement currentFormatSegment = driver.findElement(By.xpath("//input[contains(@id,'"+segmentName.toLowerCase()+"::content')]"));
		currentFormatSegment.clear();
		CommonUtils.hold(2);
		currentFormatSegment.sendKeys(textName);
		CommonUtils.hold(4);
		effUtils.scrollToPageTop();
		itemSave();
		CommonUtils.hold(5);
		if(dataType.equals("Character")) {
			if(driver.findElements(By.xpath("//div[@class='af_message_detail']")).size()>0) {
				errorMessage=driver.findElement(By.xpath("//div[@class='af_message_detail']")).getText();
				System.out.println("Current error message displayed - "+errorMessage);
			}
			if(errorMessage.equals(null)) {
				System.out.println("No error message displayed");
			}
		}
		if(dataType.equals("Number")) {

		}
		return errorMessage;
	}

	public void selectCurrentDate() {
		effUtils.scrollToPageBottom();
		CommonUtils.hold(5);
		if(driver.findElement(By.xpath("//a[contains(@id,'secSegment::glyph')]")).isDisplayed()) {
			System.out.println("Segment - Sec_Segment Date picker is displayed");
			CommonUtils.hold(4);
			WebElement secSegmentDtCtrl = driver.findElement(By.xpath("//a[contains(@id,'secSegment::glyph')]"));
			selectCurrentDate.click();
			CommonUtils.hold(4);
			WebElement dtDisplayed = driver.findElement(By.xpath("//input[contains(@id,'secSegment::content')]"));
			String currentDtDisplayed = dtDisplayed.getAttribute("title");
			System.out.println("Current date displayed - "+currentDtDisplayed);
		}
	}

	public boolean openItem(String itemName) {
		CommonUtils.hold(4);
		if(driver.findElement(By.xpath("//a/span[text()='"+itemName+"']")).isDisplayed()) {
			System.out.println("Item - "+itemName+" found to navigate");
			driver.findElement(By.xpath("//a/span[text()='"+itemName+"']")).click();
			CommonUtils.hold(15);
			CommonUtils.waitForPageLoad(driver);
			return true;
		}
		else {
			System.err.println("ERROR - Item -"+itemName+" not found");
			return false;
		}
	}

	public String getAttributeDisplayType(String segmentName) {
		String currentSegmentDisplay="";
		if(driver.findElements(By.xpath("//span[contains(@id,'"+segmentName.toLowerCase()+"::lovIconId')]")).size()>0) {
			System.out.println("Current Segment - "+segmentName+ " DisplayType - LOV");
			currentSegmentDisplay="LOV";
		}
		if(driver.findElements(By.xpath("//select[contains(@id,'"+segmentName.toLowerCase()+"::content')]")).size()>0) {
			System.out.println("Current Segment - "+segmentName+ " DisplayType - Drop-down");
			currentSegmentDisplay="Drop-down";
		}
		return currentSegmentDisplay;
	}

	public String getAttributeValue(String segmentName) {
		String currentValue = "";
		CommonUtils.hold(2);
		if(driver.findElements(By.xpath("//span[contains(@id,'"+segmentName.toLowerCase()+"::cntnr')]")).size()>0) {
			System.out.println("Current Segment - "+segmentName+" is LOV");
			currentValue = driver.findElement(By.xpath("//input[contains(@id,'"+segmentName+"::content')]")).getAttribute("Value");
		}
		if(driver.findElements(By.xpath("//select[contains(@id,'"+segmentName.toLowerCase()+"::content')]")).size()>0) {
			System.out.println("Current Segment - "+segmentName+" is 'Drop-down List'");
			currentValue = driver.findElement(By.xpath("//select[contains(@id,'"+segmentName.toLowerCase()+"::content')]")).getAttribute("title");
		}
		System.out.println("Current Value for Segment - "+segmentName+" is - "+currentValue);
		return currentValue;
	}

	public boolean selectRunTimeDropDownAttributeValue(String segmentName,String value) {
		CommonUtils.hold(4);
		if(driver.findElements(By.xpath("//select[contains(@id,'"+segmentName.toLowerCase()+"::content')]")).size()>0) {
			System.out.println("Attribute - "+segmentName+"with Display type - Drop-down is displayed");
			CommonUtils.hold(4);
			System.out.println("Select value - "+value);
			CommonUtils.selectDropDownElement(driver.findElement(By.xpath("//select[contains(@id,'"+segmentName.toLowerCase()+"::content')]")), "BLUE");
			CommonUtils.hold(3);
			return true;
		}
		else {
			System.err.println("ERROR - Attribute - "+segmentName+" with Drop-down list display type not found");
			return false;
		}
	}

	public void modifySegmentValueRunTimeForSeedData() {
		CommonUtils.hold(4);
		driver.findElement(By.xpath("//a[contains(@id,'box::lovIconId')]")).click();
		CommonUtils.hold(4);
		driver.findElement(By.xpath("//span[contains(@id,'dropDownContent:2:j_id461')]")).click();
		CommonUtils.hold(4);
		itemSave();
	}

	public boolean checkSegmentDisplayed(String segmentName) {
		CommonUtils.hold(4);
		if(driver.findElements(By.xpath("//label[text()='"+segmentName+"']")).size()>0) {
			System.out.println("Required Segment - "+segmentName+" is present");
			return true;
		}
		else {
			System.out.println("Segment -"+segmentName+" is not displayed");
			return false;
		}
	}

	public boolean navigateToPage(String itemUsage,String pageName) {
		if(pageExistsUnderItemUsage(itemUsage, pageName)) {
			System.out.println("Page - "+pageName+" exists. Clicking to navigate ...");
			CommonUtils.hold(2);
			driver.findElement(By.xpath("//span[text()='"+pageName+"']/parent::a")).click();
			CommonUtils.hold(2);
			return true;
		}
		else {
			System.err.println("ERROR - Page "+pageName+" does not exists. Exiting...");
			return false;
		}
	}

	public void selectLOVSegmentValue(String segmentName,String value) {
		CommonUtils.hold(2);
		effUtils.scrollToPageBottom();
		effUtils.selectLOVValue(segmentName, value);
		CommonUtils.hold(4);
		effUtils.scrollToPageTop();
		CommonUtils.hold(4);
		itemSave.click();
		CommonUtils.hold(8);
		effUtils.waitConditionForElement(itemSave);
	}
}
