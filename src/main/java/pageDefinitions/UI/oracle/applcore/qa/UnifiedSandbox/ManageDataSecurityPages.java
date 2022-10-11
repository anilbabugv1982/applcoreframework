/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

/**
 * @author DASKUMAR
 *
 */
public class ManageDataSecurityPages {

	/*
	 * Below are the list of Webelements pertaining 
	 * to the General Information tab in Data Security page
	 * 
	 */
	
	public ManageDataSecurityPages(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//img[@alt='Navigator']")
	public WebElement navigator;
	
	@FindBy(xpath="//a[contains(@id,'nv_ASE_FUSE_SECURITY_CONSOLE')]")
	public WebElement security_console_link;
	
	@FindBy(xpath="//img[@title='Security Console']")
	public WebElement shield;
		
	//@FindBy(xpath="//div[contains(@id,'ASE_ADMIN_WORKAREA::ti')]")
	
	@FindBy(xpath="//div[contains(@id,'ASE_ADMIN_WORKAREA::text')]")
	public WebElement adminPanelIcon;
	
	@FindBy(xpath="//button[text()='Manage Database Resources']")
	public WebElement dsButton;
	
	@FindBy(xpath="//h1[text()='Manage Database Resources and Policies']")
	public WebElement pageHeading;
	
	@FindBy(xpath="//label[contains(text(),'Display Name']/preceding-sibling::input")
	public WebElement displayNameSearchField;
	
	//@FindBy(xpath="//label[contains(text(),'Object Name')]/preceding-sibling::input")
	@FindBy(xpath="//input[contains(@id,'pt1:gdb1:qryId1:value00::content')]")
	public WebElement objNameSearchField;
	
	@FindBy(xpath="//button[text()='Search']")
	public WebElement searchBtn;
	
	@FindBy(xpath="//button[text()='Reset']")
	public WebElement resetBtn;
	
	@FindBy(xpath="//img[contains(@id,'gdb1:appTbl:_ATp:create::icon')]")
	public WebElement parentTableNewBtn;
	
	@FindBy(xpath="//img[contains(@id,'pt1:gdb1:appTbl:_ATp:edit::icon')]")
	public WebElement parentEditBtn;
	
	@FindBy(xpath="//img[contains(@id,'gdb1:ChildappTbl:_ATp:edit::icon')]")
	public WebElement childEditBtn;
	
	@FindBy(xpath="//img[contains(@id,'gdb1:appTbl:_ATp:delete::icon')]")
	public WebElement parentTableDeleteBtn;
	
	@FindBy(xpath="//img[contains(@id,'ChildappTbl:_ATp:delete::icon')]")
	public WebElement childTableDeleteBtn;
	
	@FindBy(xpath="//textarea[contains(@id,'pt1:gdb1:it1::content')]")
	public WebElement descField;
	
	@FindBy(xpath="//img[contains(@id,':ChildappTbl:_ATp:create::icon')]")
	public WebElement childTableNewBtn;
	
	@FindBy(xpath="//button[contains(@id,'gdb1:ChildappTbl:delDiag::yes')]")
	public WebElement childTableWarningOKBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:gdb1:appTbl:d2::yes')]")
	public WebElement parentTableWarningOKBtn;
	
	/*
	 * Below are the list of Webelements pertaining 
	 * to the General Information tab in Data Security page
	 * 
	 */
	
	@FindBy(xpath="//td[contains(@id , 'pt1:gdb1:ph4::_afrTtxt')]")
	public WebElement policydiv;
	
	@FindBy(xpath="//a[text()='General Information']")
	public WebElement generalInfoTab;
	
	@FindBy(xpath="//input[contains(@id,'gdb1:it4::content')]")
	public WebElement objNameField;
	
	@FindBy(xpath="//input[contains(@id,'pt1:gdb1:it2::content')]")
	public WebElement displayNameField;
	
	@FindBy(xpath="//input[contains(@id,'gdb1:databaseObjectId::content')]")
	public WebElement dataObjTextField;
	
	@FindBy(xpath="//a[contains(@id,'gdb1:databaseObjectId::lovIconId')]")
	public WebElement dataFieldSearch;
	
	@FindBy(xpath="//a[contains(@id,'gdb1:databaseObjectId::dropdownPopup::popupsearch')]")
	public WebElement dataFieldlovSearchlink;
	
	@FindBy(linkText="Search...")
	public WebElement dataFieldSearchLink;
	
	@FindBy(xpath="//input[contains(@id,'databaseObjectId::_afrLovInternalQueryId:value00::content')]")
	public WebElement dataFieldPopupInput;
	
	@FindBy(xpath="//button[contains(@id,'databaseObjectId::_afrLovInternalQueryId::search')]")
	public WebElement dataFieldSearchBtn;
	
	//@FindBy(xpath="//div[contains(@id,'gdb1:databaseObjectId_afrLovInternalTableId::db')]/table/tbody/tr[1]")
	@FindBy(xpath="//span[text()='ZMM_ACTY_ACTIVITIES']")
	public WebElement dataFieldRecord;
	
	@FindBy(xpath="//button[text()='OK']")
	public WebElement dataFieldOKBtn;
	
	@FindBy(xpath="//input[contains(@id,'gdb1:moduleIdId::content')]")
	public WebElement moduleField;
	
	@FindBy(xpath="//a[contains(@id,'pt1:gdb1:moduleIdId::lovIconId')]")
	public WebElement moduleSearchImg;
	
	@FindBy(xpath="//a[contains(@id,'pt1:gdb1:moduleIdId::dropdownPopup::popupsearch')]")
	public WebElement modulelovSearchlink;
	
	@FindBy(xpath="//input[contains(@id,'gdb1:moduleIdId::_afrLovInternalQueryId:value00::content')]")
	public WebElement modulePopupInput;
	
	@FindBy(xpath="//button[text()='Search']")
	public WebElement moduleSearchBtn;
	
	
//	@FindBy(xpath="//div[contains(@id,':gdb1:moduleIdId_afrLovInternalTableId::db')]/table/tbody/tr[1]")
	@FindBy(xpath="//span[text()='Oracle Middleware Extensions for Applications']")
	public WebElement moduleRecord;
	
	
	@FindBy(xpath="//img[contains(@id,'AT2:_ATp:create::icon')]")
	public WebElement primary_key_addBtn;
	
	@FindBy(xpath="//select[contains(@id,':pkColumnChoice::content')]")
	public WebElement pkDropDown;
	
	/*
	 * Below are the list of Webelements pertaining 
	 * to the Conditions tab in Data Security page
	 * 
	 */
	
	@FindBy(xpath="//a[text()='Condition']")	
	public WebElement conditionsTab;
	
	@FindBy(xpath="//img[@title='Create']")
	public WebElement condtionActionIcon;
	
	@FindBy(xpath="//div[text()='Create Database Resource Condition']")
	public WebElement conditionPopupWindow;
	
	@FindBy(xpath="//input[contains(@id,':it1::content')]")
	public WebElement condNameField;
	
	@FindBy(xpath="//input[contains(@id,'it4::content')]")
	public WebElement condDisNameField;
	
	@FindBy(xpath="//img[@title='Create']")
	public WebElement condAddButton;
	
	@FindBy(xpath="//img[@title='Add']")
	public WebElement condAddButton1;
	
	@FindBy(xpath="//label[text()='Column Name']/preceding-sibling::select")
	public WebElement condDropDown;
	
//	@FindBy(xpath="//select[contains(@id,'_ATp:ATt1:0:soc32::content')]")
	@FindBy(xpath="//label[contains(text(),'ItemOperatorString')]/preceding-sibling::select")
	public WebElement condoperatorDropDown;
	
	@FindBy(xpath="//span[contains(@id,'pt1:gdb1:AT1:pgl4')]/button[text()='Save']")
	public WebElement condSaveBtn;
	
	@FindBy(xpath="//img[contains(@id,'pt1:gdb1:AT1:_ATp:edit::icon')]")
	public WebElement conditionEditBtn;
	
	@FindBy(xpath="//img[contains(@id,'pt1:gdb1:AT1:_ATp:delete::icon')]")
	public WebElement conditionDeleteBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:gdb1:AT1:cb3')]")
	public WebElement CondEditSaveBtn;
	
	@FindBy(xpath="//div[contains(@id,'pt1:gdb1:AT1:_ATp:condTable::db')]/table/tbody")
	public WebElement conditionTable;
	
	/*
	 * Below are the list of Webelements pertaining 
	 * to the Action tab in Data Security page
	 * 
	 */
	
	@FindBy(xpath="//a[text()='Action']")
	public WebElement actionsTab;
	
	@FindBy(xpath="//img[@alt='Add Row']")
	public WebElement actionAddBtn;
	
	@FindBy(xpath="//img[contains(@id,':pt1:gdb1:AT3:_ATp:delete::icon')]")
	public WebElement actionDeleteBtn;
	
	@FindBy(xpath="//label[text()='Name']/preceding-sibling::input")
	public WebElement actionNameField;
	
	@FindBy(xpath="//label[text()='Display Name']/preceding-sibling::input")
	public WebElement actionDisNameField;
	
	@FindBy(xpath="//button[text()='Save']")
	public WebElement actionSaveBtn;
	
	@FindBy(xpath="//button[@title='Submit']")
	public WebElement actionSubmitBtn;	
	
	@FindBy(xpath="//button[@accesskey='K']")
	public WebElement actionOKBtn;
	
	@FindBy(xpath="//div[text()='Confirmation']")
	public WebElement confirmationPopUP;
	
	@FindBy(xpath="//div[contains(@id,':pt1:gdb1:AT3:_ATp:actionTable::db')]/table/tbody")
	public WebElement actionTable;
	
	/*
	 * Below are the list of Webelements pertaining 
	 * to Create Policy General Information tab 
	 * in Data Security page
	 * 
	 */
	@FindBy(xpath="//div[contains(@id , 'ChildappTbl:newWnd::_ttxt')]")
	public WebElement policytabdiv;
	
	//@FindBy(xpath="//a[@id = 'pt1:USma:0:MAt2:1:pt1:gdb1:ChildappTbl:pr1:0:generalTab::disAcr']")
	
	@FindBy(xpath="//a[contains(@id , 'generalTab::disAcr') and not(contains(@id , 'generalTab::disAcrCnvr'))]")
	public WebElement policyGeneralInfoTab;
	
	@FindBy(xpath="//input[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:gName::content')]")
	public WebElement policyName;
	
	@FindBy(xpath="//div[text()='Create Policy']")
	public WebElement crtPolicyPopup;
	
	@FindBy(xpath="//input[contains(@id,'sDate::content')]")
	public WebElement policyStartDateField;
	
	@FindBy(xpath="//div[contains(@id , 'roleTab::ti')]/ancestor::div[1]/descendant::div[contains(@id , 'generalTab::ti')]")
	public WebElement GeneralInfotab;
	
	/*
	 * Below are the list of Webelements pertaining 
	 * to Create Policy Role tab 
	 * in Data Security page
	 * 
	 */
	
	@FindBy(xpath="//a[contains(@id , 'roleTab::disAcr') and not(contains(@id , 'roleTab::disAcrCnvr'))]")
	public WebElement roleTab;
	
	@FindBy(xpath="//img[contains(@id,'rolePco:roleAdd::icon')]")
	public  WebElement roletCreateBtn;
	
	@FindBy(xpath="//select[contains(@id,'ChildappTbl:pr1:0:appid::content')]")
	public WebElement roleAppDropDown;	
	
	@FindBy(xpath="//button[contains(@id,'db1:ChildappTbl:pr1:0:btn7')]")
	public WebElement roleSearchBtn;
	
	@FindBy(xpath="//input[contains(@id,'gdb1:ChildappTbl:pr1:0:it4::content')]")
	public WebElement roleNameTextField;
	
	//@FindBy(xpath="//div[contains(@id,'gdb1:ChildappTbl:pr1:0:aRoTbl::db')]/table/tbody/tr[1]")
	@FindBy(xpath="//span[text()='Application Developer']")
	public WebElement roleRow;
	
	@FindBy(xpath="//button[contains(@id,'gdb1:ChildappTbl:pr1:0:roleDiag::ok')]")
	public WebElement roleOKBtn;
	
	@FindBy(xpath="//img[contains(@id,'gdb1:appTbl:_ATp:delete::icon')]")
	public WebElement policyEditDeleteBtn;
	
	
	/*
	 * Below are the list of Webelements pertaining 
	 * to Create Policy Rule tab 
	 * in Data Security page
	 * 
	 */
	
	@FindBy(xpath="//a[contains(@id , 'ruleTab::disAcr') and not(contains(@id , 'ruleTab::disAcrCnvr'))]")
	public WebElement ruleTab;
	
	@FindBy(xpath="//img[contains(@id,'gdb1:ChildappTbl:pr1:0:searchLnk::icon')]")
	public WebElement ruleConditionSearchImg;
	
	@FindBy(xpath="//input[contains(@id , 'pt1:gdb1:ChildappTbl:pr1:0:it1::content')]")
	public WebElement ruleDataBaseRowSearch;
	
	@FindBy(xpath="//img[contains(@id , 'pt1:gdb1:ChildappTbl:pr1:0:btn1::icon')]")
	public WebElement ruleDataBaseRowSearchIcon;
	
	@FindBy(xpath="//div[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:setTbl::db')]/table/tbody/tr[1]")
	public WebElement ruleTableFirstRow;
	
	@FindBy(xpath="//button[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:setDiag::ok')]")
	public WebElement ruleOKBtn;
	
	@FindBy(xpath="//input[contains(@id , 'pt1:gdb1:ChildappTbl:pr1:0:j_id196::content')]")
	public WebElement ruleParameterElement;
	
//	@FindBy(xpath="//input[contains(@id,'ChildappTbl:pr1:0:nameTxt::content')]")
//	public static WebElement ruleConditionField;
	
	@FindBy(xpath="//button[text()='Save and Close']")
	public WebElement policySvBtn;
	
	@FindBy(xpath="//button[contains(@id,'::msgDlg::cancel')]")
	public WebElement errorPopup;
	
	
	@FindBy(xpath="//a[contains(@id , 'actTab::disAcr') and not(contains(@id , 'actTab::disAcrCnvr'))]")
	public WebElement policyActionTtab;
	
	@FindBy(xpath="//a[contains(@id,'actShuttle::moveall')]")
	public WebElement moveAllImg;
	
	/*@FindBy(xpath="//input[contains(@id,'afr_pt1_afr_r1_afr_1_afr_pt1_afr_gdb1_afr_ChildappTbl')]")
	public static WebElement qbeSearchField; */
	
	@FindBy(xpath="//span[text() = 'Role']/ancestor::tbody[1]/descendant::input[contains(@id,'afr_pt1_afr_gdb1_afr_ChildappTbl')][1]")
	public WebElement qbeSearchField;
	
	@FindBy(xpath="//div[contains(@id,'ChildappTbl:_ATp:dbResourceChildTbl::db')]/table/tbody")
	public WebElement policyTable;
	
	@FindBy(xpath="//img[contains(@id,'ChildappTbl:_ATp:delete::icon')]")
	public WebElement policyDeleteBtn;
	
	@FindBy(xpath="//a[contains(@id,'sDate::glyph')]")
	public WebElement policyStartDate;
	
	@FindBy(xpath="//td[@tabindex='0']")
	public WebElement policyCurrentDate;
	
	@FindBy(xpath="//img[contains(@id,':delete::icon')]")
	public WebElement commonDeletebtn;
	
	@FindBy(xpath="//img[contains(@id ,'searchLnk::icon')]")
	public WebElement conditionSearchbtn;
	
	@FindBy(xpath="//button[@id = 'pt1:USma:0:MAt2:1:pt1:gdb1:ChildappTbl:pr1:0:setDiag::ok']")
	public WebElement instancesetOKbtn;
	
	@FindBy(xpath="//div[contains(@id , 'ruleTab::ti')]/descendant::a")
	public WebElement ruleEditlink;
	
	@FindBy(xpath="//div[contains(@id , 'roleTab::ti')]/descendant::a")
	public WebElement roleEditlink;
	
	@FindBy(xpath="//label[text() = 'Parameter1']/ancestor::tr[1]/descendant::input")
	public WebElement parameter1inputfield;
	
	@FindBy(xpath="//input[contains(@id , 'it1::content')]")
	public WebElement instanceSetToBeSearch;
	
	@FindBy(xpath="//img[contains(@id , 'btn1::icon')]")
	public WebElement instanceSetSearchIcon;
	
	@FindBy(xpath = "//td[text() = 'Task Data Security is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.']")
	public  WebElement DSPreviewModeMsg;
	
	@FindBy(xpath = "//td[text() = 'Task Data Security is in read-only mode because this tool is not activated in this sandbox. Activate this tool to make changes.']")
	public  WebElement DSToolNotActivatedMsg;
	
	@FindBy(xpath = "//td[text() = 'All customizations in this page are being carried out in the current Sandbox.']")
	public  WebElement DSEditModeMsg;
	
	@FindBy(xpath = "//a[text() = 'Manage Database Security Policies']")
	public  WebElement FndDSTaskflow;
	
	@FindBy(xpath="//img[contains(@id,'gdb1:appTbl:_ATp:create::icon')]/ancestor::a[@aria-disabled]")
	public WebElement previewModeNewBtn;
	
	public WebElement pickInstanceSet(String InstanceSetName,WebDriver driver) {
		
		WebElement instanceset = driver.findElement(By.xpath("//table[@summary = 'Instance Sets']/descendant::span[contains(@id , 'c2ot') and text() = '"+InstanceSetName+"']"));
		return instanceset;
	}
	
	public WebElement dataObjectRecord(String recordName, WebDriver driver) {
		WebElement objectRecordElement = driver.findElement(By.xpath("//span[text()='"+recordName+"']"));
		return objectRecordElement;
	}
	
	public WebElement roleName(String nameValue, WebDriver driver) {
		WebElement roleNameRowElement = driver.findElement(By.xpath("//span[text()='"+nameValue+"']"));
		return roleNameRowElement;
	}
}
