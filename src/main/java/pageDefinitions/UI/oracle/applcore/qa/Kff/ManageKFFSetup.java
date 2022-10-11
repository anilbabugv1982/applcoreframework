package pageDefinitions.UI.oracle.applcore.qa.Kff;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ManageKFFSetup {
	
	private WebDriver driver;
	
	public  ManageKFFSetup(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1:value00::content")
	public  WebElement keyFlexfieldCode;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1::search")
	public  WebElement searchFlexfieldButton;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:qryId1::search")
	public  WebElement searchStructureButton;
	
	@FindBy(xpath="//button[contains(text(),'Search')]")
	public  WebElement searchStrButtonCAT;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:qryId1::search")
	public  WebElement searchVSButton;
	
	@FindBy(xpath="//a[contains(text(),'Actions')]")
	public  WebElement actionsButton;
	
	@FindBy(xpath="//td[contains(text(),'Manage Segment Labels')]")
	public  WebElement manageSegmentlabels;
	
//	@FindBy(xpath="//h3[contains(text(),'Manage Segment Labels')]")
//	public  WebElement manageSegmentlabelPage;
	
	
	@FindBy(xpath="//a[contains(@id,'deployDetailSH1::_afrDscl')]")
	public  WebElement deploymentLog;
	
	@FindBy(xpath="//input[contains(@id,'afr_c4::content')]")
	public  WebElement columnFilter_SegmentLabelCode;
	
	@FindBy(xpath="//img[contains(@src,'func_add_16_ena.png')]")
	public  WebElement createIcon;
	
	@FindBy(xpath="//label[contains(text(),'Segment Label Code')]/../input")
	public  WebElement segmentLabelCode;
	
	@FindBy(xpath="//label[contains(text(),'Segment Label Code')]/../../../..//td[2]/span/input")
	public  WebElement labelName;
	
	@FindBy(xpath="//label[contains(text(),'Segment Label Code')]/../../../..//label[contains(text(),'Description')]/../input")
	public  WebElement description;
	
	@FindBy(xpath="//label[contains(text(),'Segment Label Code')]/../../../..//label[contains(text(),'BI Object Name')]/../input")
	public  WebElement biObjectName;
	
	@FindBy(xpath="//button[contains(text(),'ave and Close')]")
	public  WebElement saveAndCloseButton;

	@FindBy(xpath="//h1[contains(text(),'Manage Key Flexfields')]")
	public  WebElement manageKeyFlexfieldLabel;
	
	@FindBy(xpath="//div[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT1:_ATp:ctb1']/descendant::a")
	public  WebElement button_ManageStructures;
	
	@FindBy(xpath="//label[contains(text(),'Structure Code')]/ancestor::tr[1]/descendant::input")
	//@FindBy(xpath="//input[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:qryId1:value00::content']")
	public  WebElement structureCodeField;
	
	@FindBy(xpath="//img[contains(@src,'func_pencil_16_ena.png')]")
	public  WebElement editIcon;
	
	@FindBy(xpath="//table[@summary='This table lists the segments of the structure.']")
	public  WebElement countSegments;
	
	@FindBy(xpath="//label[contains(text(),'Segment Code')]/../../td[2]//input")
	public  WebElement segmentCode;
	
	@FindBy(xpath="//label[contains(text(),'Segment Code')]/../../../../tbody[1]/tr[4]/td[2]//input")
	public  WebElement segmentName;
	
	@FindBy(xpath="//label[contains(text(),'Segment Code')]/../../../../tbody[1]/tr[5]/td[2]//input")
	public  WebElement segmentDesc;
	
	@FindBy(xpath="//label[contains(text(),'Sequence Number')]/../../td[2]//input")
	public  WebElement sequenceNumber;
	
	@FindBy(xpath="//label[contains(text(),'Sequence Number')]/../../../tr[7]//input")
	public  WebElement prompt;
	
	@FindBy(xpath="//label[contains(text(),'Short Prompt')]/../../td[2]//input")
	public  WebElement shortPrompt;
	
	@FindBy(xpath="//label[contains(text(),'Display Width')]/../../td[2]//input")
	public  WebElement displayWidth;

	@FindBy(xpath="//a[@title='Search: Column Name']")
	public  WebElement columnNameLovIcon;
	
	@FindBy(xpath="//span[contains(text(),'SEGMENT')]/../../..//tr[2]")
	public  WebElement columnName;
	
	@FindBy(xpath="//label[contains(text(),'Column Name')]/../../td[2]//input")
	public  WebElement columnNameField;
	
	@FindBy(xpath="//span[contains(text(),'SEGMENT')]/../../..//tr[2]/td/span")
	public  WebElement columnNameText;

	@FindBy(xpath="//label[contains(text(),'Default Value Set Code')]/../../td[2]//input")
	public  WebElement valueSetCode;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:3:pt1:AP1:defaultValueSetCodeId_afrLovInternalTableId:6:_afrColChild0")
	public  WebElement firstAccountValue;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:3:pt1:AP1:defaultValueSetCodeId::lovDialogId::ok")
	public  WebElement okButton;
	
	@FindBy(xpath="//a[@title='Search: Column Name']")
	public  WebElement table_ColumnName;
	
	public  WebElement findLabel(String timestamp){
		 
		WebElement element = driver.findElement(By.xpath("//label[@title='labeldesc"+timestamp+"']"));
	    return element;	 
	 }
	
	@FindBy(xpath="//a[@class='af_selectManyShuttle_move-horizontal-icon-style ']")
	public  WebElement moveLabelRight;
	
	@FindBy(xpath="//h1[contains(text(),'Edit Key Flexfield Structure: OPERATIONS_ACCOUNTING_FLEX')]")
	public  WebElement editKFFStructurePage;
	
	@FindBy(xpath="//h1[contains(text(),'Edit Key Flexfield Structure: CATEGORY_FLEXFIELD')]")
	public  WebElement editKFFStructurePageCAT;
	
	@FindBy(xpath="//h1[contains(text(),'Manage Key Flexfield Structures')]")
	public  WebElement manageKFFStructurePage;
	
	public  WebElement findcreatedSegment(String timestamp){
		 
		WebElement element = driver.findElement(By.xpath("//span[contains(text(),'segmentPrompt"+timestamp+"')]"));
	    return element;	 
	 }
	
	@FindAll(@FindBy(xpath="//span[contains(text(),'edited')]"))
	public  List<WebElement> verifyEditedSegmentPresence;
	
	@FindBy(xpath="//img[@title='Edit']")
	public  WebElement editKFFSegment;
	
	@FindBy(xpath="//div[@title='Manage Key Flexfield Structures']/../..//button")
	public  WebElement doneButtonKFFSTR;
	
	
	@FindBy(xpath="//span[contains(text(),'Manage Structure Instances')]/ancestor::a")
	public  WebElement button_ManageStructureInstances;
	
	@FindBy(xpath="//label[contains(text(),'Name')]/ancestor::tr[1]/descendant::input")
	public  WebElement nameKFFSTRINS;
	
	@FindBy(xpath="//button[contains(text(),'Search')]")
	public  WebElement searchKFFSTRINS;
	
	@FindBy(xpath="//label[contains(text(),'Shorthand alias enabled')]/../input")
	public  WebElement shorthandAliasEnabledCheckbox;
	
	@FindBy(xpath="//label[contains(text(),'Shorthand alias enabled')]")
	public  WebElement shorthandAliasEnabledCheckbox2;
	
	@FindBy(xpath="//div[@title='Manage Key Flexfield Structure Instances']/ancestor::div[1]/descendant::button")
	public  WebElement doneButtonKFFSTRINS;
	
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APb')]")
	public  WebElement button_Done;
	
	@FindBy(xpath="//span[contains(text(),'Deploy Flexfield')]/ancestor::a")
	public  WebElement button_DeployFlexField;
	
	@FindBy(xpath="//span[contains(text(),'Deployment in progress.')]")
	public  WebElement VerifyDeploymentInProgress;
	
	@FindBy(xpath="//span[@class='af_progressIndicator_determinate-filled-icon-style']")
	public  WebElement VerifyDeploymentInProgressIndicator;
		
	@FindBy(xpath="//span/button[contains(text(),'OK')]")
	public  WebElement VerifyOKButtonEnable;
	
	@FindBy(xpath="//table[@title='100%']//tr[1]/td[1]/div")
	public  WebElement progressBarCompletion;
	
	@FindBy(xpath="//span[contains(text(),'Deployment completed successfully.')]")
	public  WebElement VerifyDeploymentStatusSuccess;
	
	@FindBy(xpath="//span[contains(text(),'Error in deployment.')]")
	public  WebElement VerifyDeploymentStatusError;
	
	@FindBy(xpath="//label[text()='Name']/ancestor::tr[1]/descendant::input")
	public  WebElement input_structureName;
	
	@FindBy(xpath="//td[@class='af_column_data-cell']/descendant::span[text()='GL#']")
	public  WebElement selectRow_FlexField;
	
	@FindBy(xpath="//td[@class='af_column_data-cell']/descendant::span[text()='CAT#']")
	public  WebElement selectRow_FlexFieldCAT;
	
	@FindBy(xpath="//img[contains(@src,'func_add_16_ena.png')]")
	public  WebElement createStructureIcon;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:sbc1::content")
	public  WebElement structureEnabledCheckbox;
	
	@FindBy(xpath="//label[normalize-space(text())='Enabled']")
	public  WebElement structureEnabledCheckboxLabel;
	
	@FindBy(xpath="//div[@title='Manage Key Flexfield Structures']/ancestor::div[1]/descendant::button")
	public  WebElement doneButtonManageKFFSTRS;
	
	@FindBy(xpath="//div[@title='Manage Key Flexfields']/ancestor::div[1]/descendant::button")
	public  WebElement doneButtonManageKFF;
	
	@FindBy(xpath="//span[normalize-space(text())='MDSP']")
	public  WebElement MDSPFlexfieldCode;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:qryId1::search")
	public  WebElement StructureSearchButton;
	
	@FindBy(xpath="//label[text()='Description']/ancestor::tr[1]/descendant::input")
	public  WebElement input_description;
	
	public  void waitForManageKFFSetupPageLoaded() {
		 WebDriverWait wait = new WebDriverWait(driver,30);
		 wait.until(ExpectedConditions.elementToBeClickable(searchFlexfieldButton));
		 wait.until(ExpectedConditions.visibilityOf(keyFlexfieldCode));
		// Assert.assertTrue(searchFlexfieldButton.isDisplayed() && keyFlexfieldCode.isDisplayed(), "Manage KFF is not loaded properly");
	}
	
	public  void waitForFlexFieldToDisplay() {
		 WebDriverWait wait = new WebDriverWait(driver,30);
		 wait.until(ExpectedConditions.elementToBeClickable(button_ManageStructures));
		 wait.until(ExpectedConditions.elementToBeClickable(button_ManageStructureInstances));
		 wait.until(ExpectedConditions.elementToBeClickable(button_DeployFlexField));
		 wait.until(ExpectedConditions.visibilityOf(selectRow_FlexField));
		 wait.until(ExpectedConditions.elementToBeClickable(actionsButton));
		 Assert.assertTrue(button_ManageStructures.isDisplayed() && button_DeployFlexField.isDisplayed(), "Manage KFF is not loaded properly");
	}
	
	public  void waitForCATFlexFieldToDisplay() {
		 WebDriverWait wait = new WebDriverWait(driver,30);
		 wait.until(ExpectedConditions.elementToBeClickable(button_ManageStructures));
		 wait.until(ExpectedConditions.elementToBeClickable(button_ManageStructureInstances));
		 wait.until(ExpectedConditions.elementToBeClickable(button_DeployFlexField));
		 wait.until(ExpectedConditions.visibilityOf(selectRow_FlexFieldCAT));
		 wait.until(ExpectedConditions.elementToBeClickable(actionsButton));
		 Assert.assertTrue(button_ManageStructures.isDisplayed() && button_DeployFlexField.isDisplayed(), "Manage KFF is not loaded properly");
	}
		
	public  void waitForSegmentPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(segmentCode));
		wait.until(ExpectedConditions.visibilityOf(segmentName));
		//wait.until(ExpectedConditions.elementToBeClickable(moveLabelRight));
		wait.until(ExpectedConditions.elementToBeClickable(table_ColumnName));
	}
	
	public  void waitForEditKeyFlexFieldStructurePage() {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(createIcon));
		wait.until(ExpectedConditions.elementToBeClickable(saveAndCloseButton));
		
	}
}



