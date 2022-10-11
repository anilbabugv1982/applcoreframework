package pageDefinitions.UI.oracle.applcore.qa.Audit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuditReferencePage {
	public AuditReferencePage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	WebElement ele;
	public static String POName = "";
	public static String[] attachCatDetails;
	public static String[] attachEntDetails;
	public static String[] KFFStructureDetails;
	public static String applicationRole = "";
	public static String enterpriseRoles = "";
	public static String ITMCharGlobalSeg1 = "";
	public static String zBIAItemDFF = "";
	public static String RTItemDFFChar15 = "";
	public static String Person_glb = "";
	public static String FBLPG = "";
	public static String empLastName = "";
	public static String att1_UK_001_Simple = "";
	public static String POS_GLB = "";
	public static String posglobal1 = "";
	
	@FindBy(xpath = "//button[normalize-space(text())='Save']")
	public static WebElement Save;
	
	//@FindBy(xpath = "//button[contains(text(),'ave and Close')]")
	@FindBy(xpath = "//button[@accesskey='S']")
	public static WebElement SaveAndCloseBtn;
	
	@FindBy(xpath = "//button[@title='Next']")
	public static WebElement NextButton;
	
	@FindBy(xpath = "//select[contains(@id,'dynamicRegion1:0:AP1:soc1::content')]")
	public static WebElement AuditLevel;
	
	@FindBy(xpath = "//option[text()='Auditing']")
	public static WebElement AuditingOption;
	
	@FindBy(xpath = "//button[text()='Configure Business Object Attributes']")
	public static WebElement AuditingBOAttr;
	
	@FindBy(xpath = "//select[contains(@id,'dynamicRegion1:1:AUpan:soc1::content')]")
	public static WebElement AuditProd;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:AUpan:AUTR:_ATTp:AUtr::ch::t")
	public static WebElement AuditBOAuditing;
	
	@FindBy(xpath = "//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")
	public static WebElement AuditSynchronize;
	
	@FindBy(xpath = "//td[contains(text(),'Scroll to Last')]")
	public static WebElement ScrolltoLast;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:AUpan:cb3")
	public static WebElement AuditCheckbox;
	
	@FindBy(xpath = "//select[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:AP1:soc9::content']")
	public static WebElement OPSSAudit;
	
	@FindBy(xpath = "//div[@class='af_treeTable_node-container']//span[2]")
	public static WebElement PersonBOCount;
	
	
	@FindBy(xpath = "//span[contains(text(),'Person')]")
	public static WebElement PersonAttName;
	
	@FindBy(xpath = "//span[contains(text(),'Person')]//ancestor::tr[2]//descendant::input[@class = 'af_selectBooleanCheckbox_native-input']")
	public static WebElement PersonAttChkBox;
	
	@FindBy(xpath = "//input[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:qryId1:value00::content']")
	public static WebElement auditReportDate;
	
	@FindBy(xpath = "//a[contains(@id,'_FOTsr1:0:qryId1:value00::glyph')]")
	public static WebElement auditReportDateCalendar;
	
	@FindBy(xpath = "//td[@class='af_chooseDate_today p_AFSelected']")
	public static WebElement auditReportCurrentDate;
	
	@FindBy(xpath = "//select[contains(@id,'_FOTsr1:0:qryId1:value20::content')]")
	public static WebElement auditProduct;
	
	@FindBy(xpath = "//select[contains(@id,'_FOTsr1:0:qryId1:value40::content')]")
	public static WebElement auditBusinessObjectType;
	
	@FindBy(xpath = "//button[contains(@id,'qryId1::search')]")
	public static WebElement auditReportSearch;
	
	@FindBy(xpath = "//label[normalize-space(text())='Include child objects']/preceding::input[contains(@id,'_FOTsr1:0:qryId1:value60::content')]")
	public static WebElement includeChildObjectsCheckbox;
	
	@FindBy(xpath = "//label[normalize-space(text())='Include child objects']")
	public static WebElement includeChildObjectsCheckboxLabel;
	
	@FindBy(xpath = "//label[normalize-space(text())='Show Attribute Details']")
	public static WebElement showAttributeDetailsCheckbox;
	
	@FindBy(xpath = "//a[contains(@id,'_FOTsr1:0:AT1:_ATp:viewAttributeId::lovIconId')]")
	public static WebElement showAttributeDetailsDropdown;
	
	//@FindBy(xpath = "//a[normalize-space(text())='All Attributes']")
	@FindBy(xpath = "//a[contains(@id,'_FOTsr1:0:AT1:_ATp:cl_all_att')]")
	public static WebElement allAttributes;
	
	
	
	
	@FindBy(xpath = "//span[normalize-space(text())='ProfileOptionId']")
	public static WebElement profileOptionId;
	
	//@FindBy(xpath = "//span[normalize-space(text())='ApplicationId']")
	//public static WebElement applicationID;
	
	@FindBy(xpath = "//span[normalize-space(text())='Profile Option Code']/following::td[2]/span")
	//@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:AT1:_ATp:ATt1:1:ot7")
	public static WebElement profileOptionCodeValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Profile Option Name']/following::td[2]/span")
	public static WebElement profileOptionNameValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Application']/following::td[2]/span")
	public static WebElement applicationValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Category Name']/following::td[2]/span")
	public static WebElement categoryNameValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='User Name']/following::td[2]/span")
	public static WebElement categoryUserNameValue;
	
	@FindBy(xpath = "(//span[normalize-space(text())='Description'])[2]/following::td[2]/span")
	public static WebElement catDescription;
	
	@FindBy(xpath = "//span[normalize-space(text())='DocumentEntityId']/following::td[2]/span")
	public static WebElement catDocumentEntId;
	
	@FindBy(xpath = "//span[normalize-space(text())='Entity Name']/following::td[2]/span")
	public static WebElement entitiesNameValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Database Resource']/following::td[2]/span")
	public static WebElement entitiesDatabaseResources;
	
	@FindBy(xpath = "//span[normalize-space(text())='Table Name']/following::td[2]/span")
	public static WebElement entitiesTableName;
	
	@FindBy(xpath = "//span[normalize-space(text())='Structure Code']/following::td[2]/span")
	public static WebElement kffStructureCodeValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Name']/following::td[2]/span")
	public static WebElement kffStructureNameValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Delimiter']/following::td[2]/span")
	public static WebElement kffDelimiterValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Application Role']/following::td[2]/span")
	public static WebElement applicationRoleValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Enterprise Roles']/following::td[2]/span")
	public static WebElement enterpriseRolesValue;
	
	@FindBy(xpath = "//img[@title='Create']")
	public static WebElement createAttribute;
	
	@FindBy(xpath = "//label[normalize-space(text())='Flexfields (Additional Attributes)']/preceding-sibling::input[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:AUpan:AUT:pc1:sbc1::content']")
	public static WebElement flexFieldAdditionalAttributeCheckbox;
	
	@FindBy(xpath = "//label[normalize-space(text())='Flexfields (Additional Attributes)']")
	public static WebElement flexFieldAdditionalAttributeCheckboxLabel;
	
	@FindBy(xpath = "//input[contains(@id,'AUpan:AUT:pc1:t1:sbc3::content')]")
	public static WebElement attributeNameCheckbox;
	
	@FindBy(xpath = "//label[contains(@id,'AUpan:AUT:pc1:t1:sbc3::Label0')]")
	public static WebElement attributeNameCheckboxLabel;
	
	@FindBy(xpath = "//button[@title='OK']")
	public static WebElement addAttributeOkButton;
	
	@FindBy(xpath = "//img[@title='Export to Excel']")
	public static WebElement exportToExcelIcon;
	
	@FindBy(xpath = "//img[@title='Export to CSV']")
	public static WebElement exportToCSVIcon;
	
	@FindBy(xpath = "//span[normalize-space(text())='Person_glb']/following::td[2]/span")
	public static WebElement person_glbValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='FBLPG']/following::td[2]/span")
	public static WebElement FBLPGValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='Person ID']/following::td[2]/span")
	public static WebElement PersonIDValue;
	
	@FindBy(xpath = "//div[@class='p_AFWarning af_dialog af_messages_dialog']")
	public static WebElement auditWarningBox;
	
	@FindBy(xpath = "//div[@class='p_AFWarning af_dialog af_messages_dialog']/descendant::button[normalize-space(text())='OK']")
	public static WebElement auditWarningBoxOkButton;
	
	@FindBy(xpath = "//div[contains(@id,':0:MAwd') and @class='af_dialog']")
	public static WebElement auditReportWarningBox;
	
	@FindBy(xpath = "//div[contains(@id,':0:MAwd') and @class='af_dialog']/descendant::button[normalize-space(text())='Yes']")
	public static WebElement auditReportWarningBoxYesButton;
	
	@FindBy(xpath = "//input[contains(@id,'ITM_Glob_Seg_Char_1')]")
	public static WebElement ITMCharGlbSeg;
	
	@FindBy(xpath = "//input[contains(@id,'zBIAItemDFF')]")
	public static WebElement zBIAItem;
	
	@FindBy(xpath = "//input[contains(@id,'itemdff2dchar15')]")
	public static WebElement RTItem;
	
	@FindBy(xpath = "//span[normalize-space(text())='ITM Character Global Segment 1']/following::td[2]/span")
	public static WebElement ITMCharGlobalSeg1_AuditReportValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='zBIAItemDFF']/following::td[2]/span")
	public static WebElement zBIAItemDFF_AuditReportValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='RT-ItemDFF-Char15']/following::td[2]/span")
	public static WebElement RTItemDFFChar15_AuditReportValue;
	
	@FindBy(xpath = "//span[@class='af_column_label-text' and text()='Attribute']")
	public static WebElement auditReportAttributeCoulmn;
	
	@FindBy(xpath = "//span[contains(@class,'p_AFDisabled p_AFBusy')]")
	public static WebElement busyField;
	
	@FindBy(xpath = "//body[@style='cursor: wait;']")
	public static WebElement waitCursor;
	
	@FindBy(xpath = "//span[normalize-space(text())='att1_UK_001_Simple']/following::td[2]/span")
	public static WebElement att1_UK_001_SimpleValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='POS_GLB']/following::td[2]/span")
	public static WebElement POS_GLBValue;
	
	@FindBy(xpath = "//span[normalize-space(text())='posglobal1']/following::td[2]/span")
	public static WebElement posglobal1Value;
	
	

	

	

	
}
