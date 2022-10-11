package pageDefinitions.UI.oracle.applcore.qa.Approval.Alta;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;

public class RuleConfigPage extends CommonUtilsDefs  {
	
	public RuleConfigPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath ="//div[contains(@title,'ApplInvoices')]//..//..//a[@class='oj-flex-item oj-sm-6 list-item'][1]")
	public static WebElement draft;
	
	@FindBy(xpath ="//a[@id='_UIShome_HOa1']")
	public static WebElement home;
	
	@FindBy(xpath ="//*[contains(@class,'oj-fnd-approvals-action-icon')]")
	public static List<WebElement> steps;
	
	//@FindBy(xpath ="//*[contains(@class,'oj-fnd-approvals-action-container oj-fnd-approvals-action-show')]//a")
	@FindBy(xpath ="//a[@aria-label='Actions' and @class='oj-fnd-approvals-action-icon']")
	public static WebElement action;
	
	@FindBy(xpath ="//a[@aria-label='Actions' and @class='oj-fnd-approvals-action-icon']")
	public static WebElement editAA;
	
	@FindBy(xpath ="//*[contains(@class,'oj-fnd-approvals-step-node-title')]")
	public static List<WebElement> stepName;
	
	//@FindBy(xpath ="//*[contains(@class,'oj-fnd-approvals-step-node-title')]")
	@FindBy(xpath ="//oj-input-text[contains(@class,'oj-fnd-approvals-step-node-title')]")
	//@FindBy(xpath ="//input[contains(@id,'title_STEP_2')]")	
	public static WebElement stepName2;
	
	@FindBy(xpath ="//span[contains(text(),'Create Draft')]")
	public static WebElement createDraft;
	
	@FindBy(xpath ="//span[contains(@id,'ui-id-54')]")
	public static WebElement yes;
	
	@FindBy(xpath ="//span[contains(@id,'ui-id-133')]")
	public static WebElement deploy1;
	
	@FindBy(xpath ="//oj-option[@id='actionMenuDeleteoj-fnd-step-garph001']")
	//@FindBy(xpath ="//*[@id='actionMenuDeleteoj-fnd-step-garph001']")
	public static WebElement deleteStep;
	
	//@FindBy(xpath ="//*[@id='actionMenuEditRulesoj-fnd-step-garph001']")
	@FindBy(xpath ="//span[text()='Edit']")
	public static WebElement editStep;
	
	@FindBy(xpath ="//*[@id='stepEditorCancel']")
	public static WebElement cancelStep;
	
	@FindBy(xpath ="//oj-button[contains(@id,'deleteStepWarningDialogOkButtonoj-fnd-step-garph001')]")
	//@FindBy(xpath ="//span[@id='ui-id-70']")
	public static WebElement yes2;
	
	@FindBy(xpath ="//oj-button[contains(@id,'alwaysDeleteConditionsOkButton')]")
	public static WebElement alwaysok;
	
	//@FindBy(xpath ="//div[contains(@class,'oj-approvals-rule-condition-node-ghost-container')]")
	//@FindBy(xpath ="//div[contains(@id,'condition_r')]//span[text()='Condition']")
	//@FindBy(xpath ="//div/span[text()='+']/following-sibling::span[text() ='Condition']")
	//public static WebElement addRule;
	
	//@FindBy(xpath ="//*[contains(@id,'oj-searchselect-input-attribute_conditionEditor_row')]")
	@FindBy(xpath ="//input[contains(@id,'oj-searchselect-input-attribute_conditionEditor_row')]")
	public static WebElement attributeDropDown;
	
	@FindBy(xpath ="//div[contains(@id,'oj-select-choice-operator_conditionEditor_row')]")
	public static WebElement opSelection;
	
	//@FindBy(xpath ="//*[contains(@class,'oj-combobox-input')]")
	@FindBy(xpath ="//input[contains(@class,'oj-combobox-input')]")
	//@FindBy(xpath ="//*[contains(@class,'oj-combobox-input')]//..//..//..//..//..[@id='value_1_number_conditionEditor_row_r0']")
	public static WebElement value2;
	
	@FindBy(xpath ="//input[@id='value_1_person_conditionEditor_row_r0|input']")
	public static WebElement value2input;
	
	@FindBy(xpath ="//oj-select-single[@id='value_1_person_conditionEditor_row_r0']")
	public static WebElement value2alt;
	
	@FindBy(xpath ="//oj-list-view[@id='oj-searchselect-results-value_1_person_conditionEditor_row_r0']")
	public static WebElement value2alttable;
	
	@FindBy(xpath ="//ul[@class='oj-select-choices']")
	public static WebElement value3;
	
	@FindBy(xpath ="//div[@id='oj-listbox-drop']//div[contains(@class,'oj-listbox-search-wrapper')]//div[@class='oj-listbox-search']//input[@type='text']")
	public static WebElement value3input;
	
	@FindBy(xpath ="//ul[@class='oj-listbox-result-sub']")
	public static WebElement value3table;
	
	@FindBy(xpath ="//input[contains(@id,'value_2_number_conditionEditor_row_r')]")	
	public static WebElement maxid;
	
	@FindBy(xpath ="//input[contains(@class,'oj-combobox-input')]")
	public static List<WebElement> values;
	
	@FindBy(xpath ="//input[@value='include']")
	public static List<WebElement> ranges;
	
	@FindBy(xpath ="//a[contains(@class,'oj-approvals-condition-action-close-icon')]")
	public static WebElement close;
	
	//@FindBy(xpath ="//oj-button[@title='Edit' and @class='oj-approvals-authority-edit-condition-button oj-button oj-component oj-enabled oj-button-half-chrome oj-button-icon-only oj-complete oj-default']//parent::div[contains(@class,'oj-flex-item oj-sm')]")
	@FindBy(xpath ="//*[@title='Edit']")
	public static WebElement editCondition;
	
	@FindBy(xpath ="(//*[@title='Edit'])[2]")
	public static WebElement editCondition1;
	
	@FindBy(xpath ="//*[@id='approver_targettype_authority-row-editor|input']")
	public static WebElement approver;
	
	@FindBy(xpath ="//div[@class='oj-switch-track']")
	public static WebElement limit1;
	
	@FindBy(xpath ="//*[@id='oj-select-choice-limitsBasedOn']")
	public static WebElement limitbasedOn;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-limitCurrency']")
	public static WebElement limitselection;
	
	@FindBy(xpath ="//ul[@id='oj-listbox-results-limitCurrency']//parent::div[@id='oj-listbox-drop']//div//input")
	public static WebElement limitselectioninput;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-limitsBasedOn']")
	public static WebElement limitsBasedOn;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-limitCurrency']")
	public static WebElement limitCurrency;
	
	@FindBy(xpath ="//input[@aria-controls = 'oj-listbox-results-limitCurrency oj-listbox-live-limitCurrency']")
	public static WebElement limitCurrencyText;
	
	@FindBy(xpath ="//ul[@id='oj-listbox-results-limitCurrency']")
	public static WebElement limitCurrencyDropdown;
	
	@FindBy(xpath ="//div[text()='Sequence']")
	public static WebElement sequence;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-operator_conditionEditor_authority-row-editor']")
	public static WebElement clickOperator;
	
	//@FindBy(xpath ="//*[contains(@id,'conditionEditor_authority-row-editor|input')] | //*[contains(@id,'value_multi_number_conditionEditor_authority-row-editor|input')] | //*[contains(@id,'value_multi_number_conditionEditor_authority-row-editor')] ") 
	@FindBy(xpath ="//*[contains(@id,'conditionEditor_authority-row-editor|input')] | //*[contains(@id,'value_multi_number_conditionEditor_authority-row-editor|input')] | //*[contains(@id,'oj-combobox-input')]")
	public static WebElement valuesection;
	
	@FindBy(xpath ="//div//a[contains(@title,'Close') and @class='oj-approvals-condition-action-close-icon']")
	public static WebElement tick;
	
	@FindBy(xpath ="//span[contains(@id,'approver_label_')]")
	public static WebElement approvertick;
	
	@FindBy(xpath ="//div//a[contains(@title,'Close')]")
	public static WebElement tick2;
	
	@FindBy(xpath ="//ul[@aria-label='Approver target type list']//li[1]")
	public static WebElement user;
	
	@FindBy(xpath ="//input[@id='approver_target_userauthority-row-editor|input']")
	public static WebElement userinput;
	
	@FindBy(xpath ="//*[@class='oj-table-element oj-component-initnode']")
	public static WebElement usertable;
	
	@FindBy(xpath ="//div[text()='Person']")
	public static WebElement person;
	
	@FindBy(xpath ="//ul[@aria-label='Approver target type list']//li[2]")
	public static WebElement aagroup;
	
	@FindBy(xpath ="//span[contains(@class,'oj-flex-item oj-approvals-rule-action-text-ghost oj-approvals-rule-ghost-text-plus')]")
	public static WebElement addApprover;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-operator_conditionEditor_authority-row-editor']")
	public static WebElement SHoperator;
	
	//@FindBy(xpath ="//span[contains(@id,'ojChoiceId_action_type_actionEditor_row_')]")
	@FindBy(xpath ="//span[contains(@id,'action_type_actionEditor_row_')]")
	public static WebElement actionForApprove;
	
	@FindBy(xpath ="//div[contains(text(),'Send for Approval')]")
	public static WebElement sendForApproval;
	
	@FindBy(xpath ="//div[contains(text(),'Approve')]")
	public static WebElement autoApprove;
	
	@FindBy(xpath ="//div[contains(text(),'Reject')]")
	public static WebElement autoReject;
	
//	@FindBy(xpath ="//*[contains(@id,'oj-select-choice-action_targettype_actionEditor_row')]")
	@FindBy(xpath ="//div[contains(@id,'oj-select-choice-action_targettype_actionEditor_row')]")
	public static WebElement to;
	
	//@FindBy(xpath ="//*[contains(@id,'oj-listbox-drop')]//ul//li[1]")
	@FindBy(xpath ="//div[contains(@id,'oj-listbox-drop')]//ul//li[1]")
	public static WebElement userValue;
	
	@FindBy(xpath ="//div[contains(@id,'oj-listbox-drop')]//ul//li[3]")
	public static WebElement AttributeValue;
	
	@FindBy(xpath ="//div[contains(@id,'oj-listbox-drop')]//ul//li[2]")
	public static WebElement policyValue;
	
	@FindBy(xpath ="//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@class,'inlineLimitCellContent limit invalid')]//div")
	public static WebElement approvalLimit;
	
	@FindBy(xpath ="//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@id,'default_icon_authority-row-edito')]")
	public static WebElement add;
	
	@FindBy(xpath ="//a[@class='add-icon' and @title='Add Row']")
	public static WebElement addicon;
	
	@FindBy(xpath ="//input[@id='oj-searchselect-filter-approver_targettype_authority-row-editor|input']")
	public static WebElement approvalUserType;
	
	@FindBy(xpath ="//input[@id='approver_target_joblevelauthority-row-editor|input']")
	public static WebElement JobLevelInput;
	
	@FindBy(xpath ="//input[contains(@id,'approval_limit_authority-row-editor_')]")
	public static WebElement approvalLimitinput;
	
	@FindBy(xpath ="//div[contains(@id,'oj-listbox-result-label')]//*[text()='Unlimited']")
	public static WebElement unlimitedLimit;
	
	@FindBy(xpath ="//div[@class='routing-policy-popup-footer-readonly']")
	public static WebElement noauthorityfound;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-rpPopupNoMatch']")
	public static WebElement noauthorityfoundaction;
	
	@FindBy(xpath ="//*[contains(@id,'action_multi_actionEditor_')]")
	//@FindBy(xpath ="//div[contains(@id,'ojChoiceId_action_multi_actionEditor_row_')]")
	public static WebElement userAndRole;
	
	@FindBy(xpath ="//*[contains(@id,'action_target_actionEditor_')]")
	//@FindBy(xpath ="//div[contains(@id,'ojChoiceId_action_multi_actionEditor_row_')]")
	public static WebElement attributeValue;
	
	@FindBy(xpath ="//ul[contains(@class,'oj-select-choices')]")
	//@FindBy(xpath ="//*[contains(@id,'ojChoiceId_action_multi_actionEditor_row')]")
	//@FindBy(xpath ="//*[contains(text(),'More results available, please filter further.')]//..//..//div[2]//div//input")
	public static WebElement userValue1;
	
	@FindBy(xpath ="//div[contains(text(),'More results available, please filter further.')]/ancestor::div[@class='oj-listbox-drop-layer oj-focus-within']//div[2]//div//input")
	//@FindBy(xpath ="//div[@class = 'oj-listbox-filter-message-box']//ancestor::div[@class = 'oj-listbox-drop-layer oj-focus-within']//input[@class = 'oj-listbox-input']")
	//@FindBy(xpath ="//div[@id='oj-listbox-drop']//descendant::div//input[@class='oj-listbox-input']")
	public static WebElement userValue2;
	
	@FindBy(xpath ="//div[@class = 'oj-listbox-filter-message-box']")
	//@FindBy(xpath ="//div[@id='oj-listbox-drop']//descendant::div//input[@class='oj-listbox-input']")
	public static WebElement userValue2Message;
	
	@FindBy(xpath ="//input[@id='approver_target_userauthority-row-editor|input']")
	public static WebElement SHuser;
	
	@FindBy(xpath ="//*[@class='oj-table-element oj-component-initnode']")
	public static WebElement SHuserTable;
	
	
	@FindBy(xpath ="//input[contains(@id,'action_target_actionEditor_row') and contains(@class,'oj-searchselect-input oj-text-field-input')]")
	public static WebElement policyField;
	
//	@FindBy(xpath ="//*[contains(@id,'btnSaveRule')]//button//div/span[2]")
	@FindBy(xpath ="//oj-menu-button[contains(@id,'btnSaveRule')]")
	public static WebElement saveRule;
	
	@FindBy(xpath ="//oj-dialog[@id='networkFailureErrorMessage']//div[@class='oj-dialog-body']")
	public static WebElement networkErrorMsg;
	
	//@FindBy(xpath ="//*[@id='stepEditorSave']")
	@FindBy(xpath ="//a[text() = 'Save']")
	public static WebElement save;
	
	@FindBy(xpath ="//a[text() = 'Save and Close']")
	public static WebElement saveandClose;
	
	//@FindBy(xpath ="//*[contains(@id,'btnDeploy')]")
	@FindBy(xpath ="//span[text()='Deploy']")
	public static WebElement deploy;
	
	@FindBy(xpath ="//oj-button[contains(@id,'okButtonDepl')]")
	//@FindBy(xpath ="//span[@id='ui-id-40']")
	public static WebElement ok;
	
//	@FindBy(xpath ="//*[@id='deploySuccessMessage']")
	@FindBy(xpath ="//div[text()='The transaction type Account Payables Invoice Approvals was successfully deployed.'] | //div[text()='The transaction type Account Payables Invoice Approvals was deployed.']")
	public static WebElement deployComplete;
	
	
	@FindBy(xpath ="//oj-button[@id='stepFlowClose']")
	public static WebElement stepClose;
	
	@FindBy(xpath ="//img[@class='deploy-img']")
	public static WebElement deleteDraftimg;
	
	@FindBy(xpath ="//oj-option[@id='deleteTT']")
	public static WebElement deleteDraft;
	
	@FindBy(xpath ="//oj-button[@id='okButton']")
	public static WebElement deleteDraftOK;
	
	@FindBy(xpath ="//oj-button[@id='secondaryMenuCloseButton']")
	public static WebElement menuClose;
	
	@FindBy(xpath ="//span[text()='Add New Rule']")
	public static WebElement addNewRule;
	
	@FindBy(xpath ="//oj-button[@id='secondaryMenuOpenButton']")
	public static WebElement menu;
	
	@FindBy(xpath ="(//tr[contains(@class,'routing-policy-table-t')])[1]//td[1]")
	public static WebElement RPFirstRow;
	
	@FindBy(xpath ="//tr[contains(@class,'routing-policy-table-t')][1]//td[3]//img//parent::a[@title='Edit']")
	public static WebElement RPFirstRowEdit;
	
	@FindBy(xpath ="//tr[contains(@class,'routing-policy-table-t')][1]//td[3]//img//parent::a[@title='Delete Routing Policy']")
	public static WebElement RPFirstRowDelete;
	
	@FindBy(xpath ="//div[text()='Create']")
	public static WebElement createRP;
	
	@FindBy(xpath ="//a[text()='Supervisory Hierarchy']")
	//@FindBy(xpath ="//div[@id='emptyRoutingPolicyMenu_layer']//oj-menu//oj-option[3]")
	public static WebElement supervisorHierarchy;
	

	@FindBy(xpath ="//a[text()='Approval Authority']")
	//@FindBy(xpath ="//div[@id='emptyRoutingPolicyMenu_layer']//oj-menu//oj-option[3]")
	public static WebElement approvalAuthority;
	
	@FindBy(xpath ="//a[text()='Approval Group']")
	public static WebElement approvalGroup;
	
	@FindBy(xpath ="//div[text()='Supervisory Hierarchy']")
	public static WebElement supervisorHierarchyLabel;
	
	//@FindBy(xpath ="//input[@id='oj-searchselect-input-startWith']/ancestor::div[@class='oj-text-field-container']//a")
	@FindBy(xpath ="//input[@id='oj-searchselect-input-startWith']/ancestor::div[@class='oj-text-field-container']//div//input")
	public static WebElement startWith;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-stopWhen']")
	public static WebElement stop;
	
	@FindBy(xpath ="//input[@id='numJobLevels|input']")
	public static WebElement numberOfLevel;
	
	//@FindBy(xpath ="//input[@id='oj-searchselect-input-doNotRouteBeyond']")
	@FindBy(xpath ="//oj-select-single[@id='doNotRouteBeyond']")
	public static WebElement limit;
	
	@FindBy(xpath ="//input[@id='oj-searchselect-filter-doNotRouteBeyond|input']")
	public static WebElement limitinput;
	
	@FindBy(xpath ="//input[@id='oj-searchselect-input-doNotRouteBeyond']")
	public static WebElement limit2;
	
	@FindBy(xpath ="//input[@id='name|input']")
	public static WebElement policyName;
	
	@FindBy(xpath ="//div[@id='oj-select-choice-routingTypeDropdown']")
	public static WebElement notify;
	
//	@FindBy(xpath ="//td[contains(@id,'routingPolicyTable:_hdrCo')]//div[@class='invalid']")
	@FindBy(xpath ="//tbody[@class='oj-table-body']//tr[1]//td[2]")
	public static WebElement EnterName;

	@FindBy(xpath ="//oj-select-single[@id='members'] | //input[@id='members|input']")
	public static WebElement EnterName2;
	
	@FindBy(xpath ="//input[@id='oj-searchselect-filter-members|input']")
	public static WebElement EnterName3;
////input[@id='members|input'] |
	@FindBy(xpath ="//div//a[@class='oj-searchselect-arrow oj-searchselect-open-icon oj-searchselect-icon oj-component-icon oj-clickable-icon-nocontext']")
	public static WebElement dropDown;
	
	@FindBy(xpath ="//a[@title='Add Member']")
	public static WebElement addMember;
	
	@FindBy(xpath ="//input[@id='approversFrom|input']")
	public static WebElement approverFrom;
	
	@FindBy(xpath ="//span[text()='All matching authority records']")
	public static WebElement allautorityrecord;
	
	@FindBy(xpath ="//a[@title='Actions']")
	public static WebElement action1;
	
	
	@FindBy(xpath ="//input[@id='value_1_number_conditionEditor_authority-row-editor|input']")
	public static WebElement value1;
	
	@FindBy(xpath ="//input[@id='value_2_number_conditionEditor_authority-row-editor|input']")
	public static WebElement value1_1;
	
	
	@FindBy(xpath ="//*[@id='checkbox_1_conditionEditor_authority-row-editor']")
	public static WebElement checkbox1;
	
	@FindBy(xpath ="//*[@id='checkbox_2_conditionEditor_authority-row-editor']")
	public static WebElement checkbox2;
	@FindBy(xpath ="//oj-menu-button[@id='saveMenuButton']//button//div//span[4]")
	public static WebElement savePolicy;
		
	@FindBy(xpath ="//span[@id='saveButtonText']")
	public static WebElement saveApprovalGroup1;
	
	@FindBy(xpath ="//oj-dialog[@id='rp-error-dialog']")
	public static WebElement err;
	
	@FindBy(xpath ="//oj-button[@id='deleteRPResultDialogYes']")
	public static WebElement deleteRP;
	
	@FindBy(xpath ="//oj-button[@id='deleteRPResultDialogExit']")
	public static WebElement deleteRPOK;

	@FindBy(xpath ="//input[@id='name|input']")
	public static WebElement aaName;
	
	@FindBy(xpath ="//*[@class='oj-select-choices']")
	public static WebElement addAttribute;
	
	@FindBy(xpath ="//a[@title='Add Attribute(s)']")
	public static WebElement addAttributeIcon;
	
	@FindBy(xpath ="//oj-list-view[@id='rulesListView_oj-rule-editor']//ul//li[1]")
	public static WebElement rule;
	
	@FindBy(xpath ="//oj-list-view[@id='rulesListView_oj-rule-editor']//ul//li[1]//descendant::a[@title='Delete Rule']")
	public static WebElement deleteRule;
	
	@FindBy(xpath ="//oj-button[@id='deleteOkButtonRule_oj-rule-editor']")
	public static WebElement deleteRuleOK;
	
	@FindBy(xpath ="//span[@class='oj-approvals-rule-condition-attribute-ro oj-approvals-truncate-with-ellipsis']")
	public static WebElement attributeInRule;
	
	public void login(String loginname, String loginpwd, WebDriver driver) throws Exception {
		driver.manage().deleteAllCookies();
		launchURL(driver);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(username, "Click", "",driver);
		username.sendKeys(loginname);
		CommonUtils.explicitWait(password, "Click", "",driver);
		password.sendKeys(loginpwd);
		CommonUtils.explicitWait(signIn, "Click", "",driver);
		signIn.click();
		CommonUtils.waitForPageLoad(driver);
	}
	
	public void launchURL(WebDriver driver) {

		try {
			driver.get(RestCommonUtils.getExecutableHost()+"fscmUI/fnd/vp/approvalsconfiguration");
			CommonUtils.explicitWait(username, "Visible", "", driver);
		} catch (Exception e) {
			System.out.println("Exception in launchURL() ");
			e.printStackTrace();
		}

	}

	
	 public void logout(WebDriver driver) {
			try {
					CommonUtils.hold(5);
					CommonUtils.explicitWait(userLink, "Click", "",driver);
					userLink.click();
					CommonUtils.explicitWait(logOut, "Click", "",driver);
					CommonUtils.hold(5);
					logOut.click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(confirmButton, "Click", "",driver);
					confirmButton.click();
					CommonUtils.hold(5);
//					
					} catch (Exception e) {
					try{
						System.out.println("Inside catch block to fix log out issue");
						launchURL(driver);
						CommonUtils.hold(5);
						CommonUtils.explicitWait(userLink, "Click", "",driver);
						userLink.click();
						CommonUtils.explicitWait(logOut, "Click", "",driver);
						CommonUtils.hold(5);
						logOut.click();
						CommonUtils.hold(5);
						CommonUtils.explicitWait(confirmButton, "Click", "",driver);
						confirmButton.click();
				
						CommonUtils.hold(5);
					}catch (Exception e1){
						System.out.println("Inside Catch block after refreshing URL");
						e1.printStackTrace();
					}
				}
			}

	
}
