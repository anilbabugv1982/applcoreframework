package pageDefinitions.UI.oracle.applcore.qa.Dff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DescriptiveFlexPage {
	
	
	public DescriptiveFlexPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	/*
	 * Common elements in dff pages.
	 * 
	 */
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:q1:value10::content')]")
	public WebElement dffSearchField;
	
	@FindBy(xpath="//button[contains(@id,'::search')]")
	public WebElement runTimeSearchBtn;
	
	@FindBy(xpath="//img[contains(@id,'edit::icon')]")
	public WebElement edithBtn;
	
	@FindBy(xpath="//div[contains(@id,'dffPnl:gsAT:_ATp:edit')]")
	public WebElement glbSegEdithBtn;
	
	@FindBy(xpath="//img[contains(@id,'dffPnl:cssAT:_ATp:edit::icon')]")
	public WebElement contextSegEdithBtn;
	
	@FindBy(xpath="//img[contains(@id,'create::icon')]")
	public WebElement createhBtn;
	
	@FindBy(xpath="//img[contains(@id,'delete::icon')]")
	public WebElement deletehBtn;
	
	@FindBy(xpath="//button[contains(@id,'APsv')]")
	public WebElement runTimeSaveBtn;
	
	@FindBy(xpath="//button[contains(@id,'APc')]")
	public WebElement cancelBtn;
	
	@FindBy(xpath="//button[contains(@id,'APscl')]")
	public WebElement editSaveCloseBtn;
	
	@FindBy(xpath="//a[contains(@aria-describedby,'pt1:AP1:ctb1_afrdescBy')]")
	public WebElement savecloseBtn1;
	
	@FindBy(xpath="//div[contains(@id , 'AP1:ctb2')]")
	public WebElement glbEditSegSaveCloseBtn;
	
	@FindBy(xpath="//button[contains(@id,'csACTB0')]")
	public WebElement manageContextBtn;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:ffAT:_ATp:depDFFB1')]")
	public WebElement deployFlexBtn;
	
	@FindBy(xpath="//div[contains(text(),'Processing')]")
	public WebElement deploFlexProcessing;
	
	@FindBy(xpath="//div[contains(text(),'Confirmation')]")
	public WebElement deploFlexSuccess;
	
	@FindBy(xpath="//div[contains(text(),'Error')]")
	public WebElement deploFlexError;
	
	@FindBy(xpath="//div[contains(@id,':pt1:AP1:ffAT:d1::_ttxt')]")
	public WebElement deployFlexPopUp;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:ffAT:okdlgp')]")
	public WebElement confirmOKBtn;
	
	@FindBy(xpath="//select[contains(@id,'pt1:dffPnl:socDispTyp::content')]")
	public WebElement contextDisplayTypeDD;

	@FindBy(xpath = "//h1[contains(text(),'Edit Descriptive Flexfield')]")
	public WebElement editFlexFieldPageHeader;
	
	/*
	 * Global segment page elements
	 * 
	 */
	
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it3::content')]")
	public WebElement glbSegNameInput;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it11::content')]")
	public WebElement glbSegCodeInput;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it4::content')]")
	public WebElement glbSegAPIInput;
	
	@FindBy(xpath="//select[contains(@id,'pt1:AP1:dataTypeLov::content')]")
	public WebElement glbDataTypeDD;

	@FindBy(xpath = "//input[contains(@id,'AP1:columnNameId::content')]")
	public WebElement tableColumnField;
	
	@FindBy(xpath = "//a[contains(@id , 'columnNameId::lovIconId')]")
	public WebElement tableColumnLOVField;
	
	@FindBy(xpath = "//div[contains(@id,'columnNameId::dropdownPopup::dropDownContent') and @class = 'af_inputComboboxListOfValues_dropdown-table af_table']")
	public WebElement columnLOVList;
	
	@FindBy(xpath="//input[contains(@id,'valueSetCodeId::content')]")
	public WebElement glbValueSetInput;
	
	@FindBy(xpath="//select[contains(@id,'defaultTypeId::content')]")
	public WebElement glbDefaultTypeDD;
	
	@FindBy(xpath="//select[contains(@id,'displayTypeList::content')]")
	public WebElement glbDisplayTypeDD;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it20::content')]")
	public WebElement glbPromptInput;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it16::content')]")
	public WebElement glbDisplaySize;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:itDefValConstVc2::content')]")
	public WebElement glbDefaultValue;
	
	@FindBy(xpath="//textarea[contains(@id , 'it17::content')]")
	public WebElement glbSegmentDesc;
	
	@FindBy(xpath="//input[contains(@id , 'sbc1::content')]")
	public WebElement glbSegmentEnabled;
	
	@FindBy(xpath="//select[contains(@id , 'soc3::content')] ")
	public WebElement glbValueSetRangeType;
	
	@FindBy(xpath="//input[contains(@id , 'sbc3::content')]")
	public WebElement glbValueSetValidationReq;
	
	@FindBy(xpath="//textarea[contains(@id , 'itDefValSql::content')]")
	public WebElement glbSqlDefaultTypeValue;
	
	@FindBy(xpath="//input[contains(@id , 'it10::content')]")
	public WebElement glbDisplayHeight;
	
	@FindBy(xpath="//textArea[contains(@id , 'itTrmHlpTxt::content')]")
	public WebElement glbDefHelpText;
	
	@FindBy(xpath="//textArea[contains(@id , 'itInFldHlpTxt::content')]")
	public WebElement glbInstructionHelpText;
	
	@FindBy(xpath="//input[contains(@id , 'sbc4::content')]")
	public WebElement glbReadOnlyDisplay;
	
	@FindBy(xpath="//input[contains(@id , 'sbc2::content')]")
	public WebElement glbBIEnabled;
	
	@FindBy(xpath="//select[contains(@id , 'biTag::content')]")
	public WebElement glbBILabel;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:columnNameId::content')]")
	public WebElement glbTableColumn;
	
	

	
	/*
	 * Manage Context page elements
	 * 
	 */

	@FindBy(xpath = "//h1[contains(text(),'Manage Contexts')]")
	public WebElement manageContextPageHeader;

	@FindBy(xpath = "//h1[contains(text(),'Create Context')]")
	public WebElement createContextPageHeader;

	@FindBy(xpath="//input[contains(@id,'pt1:AP1:dctxQRY:value00::content')]")
	public WebElement contextCodeSearchField;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:dctxQRY:value10::content')]")
	public WebElement contxtDescSearchField;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:dctxQRY:value20::content')]")
	public WebElement contextDisplaySearchField;
	
//	@FindBy(xpath="//label[contains(text(),'Display Name')]/parent::td/following-sibling::td/descendant::input")
	@FindBy(xpath="//tr[contains(@id,':pt1:AP1:it4')]/td[last()]/input")
	public WebElement cDisplayNameInput;
	
	@FindBy(xpath="//tr[contains(@id,':pt1:AP1:it1')]/td[last()]/input")
	public WebElement cContextCodeInput;
	
	@FindBy(xpath="//tr[contains(@id,':pt1:AP1:it3')]/td[last()]/input")
	public WebElement cAPINameInput;
	
	@FindBy(xpath="//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:3:pt1:AP1:sbc0::Label0']/div")
	public WebElement cCheckBox;
	
	@FindBy(xpath="//textarea[contains(@id,':pt1:AP1:it2::content')]")
	public WebElement cDescriptionInput;
	
	@FindBy(xpath="//button[contains(@id,':pt1:AP1:cusSV')]")
	public WebElement cContextSaveBtn;

	@FindBy(xpath = "//img[contains(@id,'pt1:AP1:dctxSAT:_ATp:delete::icon')]")
	public WebElement contextDeleteButton;
	
 /*
  * 
  * dff run-time page elements
  * 
  */
	
	@FindBy(xpath="//a[text()='My Client Groups']")
	public WebElement myClientGroup;
	
	@FindBy(xpath="//a[contains(@id,'itemNode_workforce_management_new_person')]")
	public WebElement newPerson;
	
	@FindBy(xpath="//img[@title='Tasks']")
	public WebElement taskBtn;
	
	@FindBy(xpath="//a[text()='Hire an Employee']")
	public WebElement hireEnEmployee;
						
	@FindBy(xpath="//a[contains(@id,'SP1:selectOneChoice3::lovIconId')]") 
	public WebElement hireEnEmployeeSearchLink;
	
	@FindBy(xpath="//a[contains(@id,'SP1:selectOneChoice3::dropdownPopup::popupsearch')]")
	public WebElement searchLink;
	
//	@FindBy(xpath="//input[contains(@id,'SP1:selectOneChoice3::_afrLovInternalQueryId:value00::content')]")
	@FindBy(xpath="//input[contains(@id,'SP1:selectOneChoice3::content')]")
	public WebElement legalEmployeerInputField;

	@FindBy(xpath = "//li[contains(text(),'Vision Corp - Canada')]")
	public WebElement legalEmployeeValue;
	
	@FindBy(xpath="//input[contains(@id,'selectOneChoice3::_afrLovInternalQueryId:value10::content')]")
	public WebElement legislation_Code;
	
	@FindBy(xpath="//button[text()='OK']")
	public WebElement okBtn;
	
	@FindBy(xpath="//div[contains(@id,':tt1:next')]/a[@class='af_commandToolbarButton_link']")
	public WebElement nextBtn;
	
	//div[contains(@id,':tt1:submit')]/a[@class='af_commandToolbarButton_link']
	@FindBy(xpath="//div[contains(@id,':tt1:submit') and @class='af_commandToolbarButton p_AFTextOnly']")
	public WebElement submitBtn;
	
	@FindBy(xpath="//span[text()='Save']")
	public WebElement saveBtn;
		
	@FindBy(xpath="//input[contains(@id,'it20::content')]")
	public WebElement lastNameInputField;	
	
	@FindBy(xpath="//input[contains(@id,'businessUnitId')]")
	public WebElement businessUnitIdInput;
	
	@FindBy(xpath="//a[contains(@id,':businessUnitId::lovIconId')]")
	public WebElement businessUnitLov;
	
	@FindBy(xpath="//button[contains(@id,'okWarningDialog')]")
	public WebElement warningOKBtn;
	
	@FindBy(xpath="//button[contains(@id,'okConfirmationDialog')]")
	public WebElement runTimeConfirmOKBtn;
	
	@FindBy(xpath="//input[contains(@id,':df1_PersonDFFIteratorDFFFCAPI')]")
	public WebElement personCharGlobalSegment;
	
	@FindBy(xpath="//input[contains(@id,':df1_PersonDFFIteratorDFFFNAPI')]")
	public WebElement personNumGlobalSegment;
	
	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFFDAPI')][@class='af_inputDate_content']")
	public WebElement personDateGlobalSegment;
	
	@FindBy(xpath="//a[contains(@id,'df1_PersonDFFIterator__FLEX_Context')]")
	public WebElement personcontextDropDown;
	
	@FindBy(xpath="//li[contains(text(),'DFFCON_NAME')]")
	public WebElement personContextCodesegment;	
	
//	@FindBy(xpath="//a[contains(@id,'df1_PersonDFFIteratorDFFINCHARAPI')]")
//	public WebElement personCharIndSensitiveSegmentDropDown;   // DPK

	@FindBy(xpath="//label[contains(text(),'DFFINCHAR')]/parent::td/following-sibling::td/descendant::a")
	public WebElement personCharIndSensitiveSegmentDropDown;
	
	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFINCHARAPI')]")
	public WebElement personCharIndSensitiveSegmentinput;
	
//	@FindBy(xpath="//a[contains(@id,'df1_PersonDFFIteratorDFFDEPCHARAPI')][contains(@title,'Search: DFFDEPCHAR')]")
//	public WebElement personCharDepSensitiveSegmentSearchBtn;

	@FindBy(xpath="//label[contains(text(),'DFFDEPCHAR')]/parent::td/following-sibling::td/descendant::a")
	public WebElement personCharDepSensitiveSegmentSearchBtn;
	
//	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFDEPCHARAPI')]")
//	public WebElement personCharDepSensitiveSegment;

	@FindBy(xpath="//label[contains(text(),'DFFDEPCHAR')]/parent::td/following-sibling::td/descendant::input")
	public WebElement personCharDepSensitiveSegment;
	
	@FindBy(xpath="//a[contains(@id,'df1_PersonDFFIteratorDFFSUBAPI')][contains(@title,'Search: DFFSUB')]")
	public WebElement personCharSubSensitiveSegmentSearchBtn;
	
//	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFSUBAPI')]") ////label[contains(text(),'DFFSUB')]/parent::td/following-sibling::td/descendant::input
//	public WebElement personCharSubSensitiveSegment;

	@FindBy(xpath="//label[contains(text(),'DFFSUB')]/parent::td/following-sibling::td/descendant::input")
	public WebElement personCharSubSensitiveSegment;
	
//	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFNUMAPI')]")
//	public WebElement personNumIndependentSensitiveSegment;

	@FindBy(xpath="//label[contains(text(),'DFFNUM')]/parent::td/following-sibling::td/descendant::input")
	public WebElement personNumIndependentSensitiveSegment;

//	@FindBy(xpath="//a[contains(@id,'df1_PersonDFFIteratorDFFNUMAPI')]")  ////label[contains(text(),'DFFNUM')]/parent::td/following-sibling::td/descendant::a
//	public WebElement personNumIndependentSensitiveSegmentDropDown;

	@FindBy(xpath="//label[contains(text(),'DFFNUM')]/parent::td/following-sibling::td/descendant::a")
	public WebElement personNumIndependentSensitiveSegmentDropDown;
	
//	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFDEPNUMAPI')]")
//	public WebElement personNumDepSensitiveSegment;

	@FindBy(xpath="//label[contains(text(),'DFFDEPNUM')]/parent::td/following-sibling::td/descendant::input")
	public WebElement personNumDepSensitiveSegment;
	
//	@FindBy(xpath="//a[contains(@id,'df1_PersonDFFIteratorDFFDEPNUMAPI')][contains(@title,'Search: DFFDEPNUM')]")
//	public WebElement personNumDepSensitiveSegmentSearchBtn;

	@FindBy(xpath="//label[contains(text(),'DFFDEPNUM')]/parent::td/following-sibling::td/descendant::a")
	public WebElement personNumDepSensitiveSegmentSearchBtn;
	
//	@FindBy(xpath="//input[contains(@id,'df1_PersonDFFIteratorDFFTABAPI')]")
//	public WebElement personTableSensitiveSegment;	DPK

	@FindBy(xpath="//label[contains(text(),'DFFTAB')]/parent::td/following-sibling::td/descendant::input")
	public WebElement personTableSensitiveSegment;

	@FindBy(xpath="//button[text()='Search']")
	public WebElement searchBtn;
	
    @FindBy(xpath="//button[contains(@id,'okConfirmationDialog')]")
    public WebElement confirmOKBtn1;

    @FindBy(xpath = "//div[contains(text(),'it must not contain more characters than the maximum allowable length')]")
	public WebElement lengthErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'it must contain only uppercase characters.')]")
	public WebElement upperCaseErrorMessage;

    @FindBy(xpath="//div[contains(text(),'it must be a number greater than the minimum value')]")
	public WebElement minValueErrorMessage;

    @FindBy(xpath = "//div[contains(text(),' it must be a number less than the maximum value')]")
	public WebElement maxErrorMessage;

    @FindBy(xpath="//li[contains(text(),'DFFCON_NAME')]")
	public WebElement dffContextSegment1;

    @FindBy(xpath="//li[contains(text(),'CRFL_Context_Test')]")
	public WebElement dffContextSegment2;

	@FindBy(xpath="//label[contains(text(),'Person_Surname')]")
	public WebElement dffContextSegment21;

	@FindBy(xpath = "//a[contains(text(),'Search...')]")
	public WebElement depCharSearchLink;

	@FindBy(xpath = "//li[contains(text(),'AOL')]")
	public WebElement indCharSegmentAolValue;

	@FindBy(xpath = "//li[contains(text(),'FLEX')]")
	public WebElement indCharSegmentFlexValue;

	@FindBy(xpath = "//a[contains(text(),'Search...')]/preceding-sibling::div/descendant::tr[td='AOL1']")
	public WebElement depCharSegmentAol1Value;

	@FindBy(xpath = "//a[contains(text(),'Search...')]/preceding-sibling::div/descendant::tr[td='AOL2']")
	public WebElement depCharSegmentAol2Value;

	@FindBy(xpath = "//span[contains(text(),'AOL1')]")
	public WebElement depCharSegmentAol1ValueSpan;

	@FindBy(xpath = "//span[contains(text(),'AOL2')]")
	public WebElement depCharSegmentAol2ValueSpan;

	@FindBy(xpath = "//a[contains(text(),'Search...')]/preceding-sibling::div/descendant::tr[td='FLEX1']")
	public WebElement depCharSegmentFlex1Value;

	@FindBy(xpath = "//a[contains(text(),'Search...')]/preceding-sibling::div/descendant::tr[td='FLEX2']")
	public WebElement depCharSegmentFlex2Value;

	@FindBy(xpath = "//span[contains(text(),'FLEX1')]")
	public WebElement depCharSegmentFlex1ValueSpan;

	@FindBy(xpath = "//span[contains(text(),'FLEX2')]")
	public WebElement depCharSegmentFlex2ValueSpan;



	@FindBy(xpath = "//li[text()='10']")
	public WebElement indCharSegment10Value;

	@FindBy(xpath = "//li[text()='20']")
	public WebElement indCharSegment20Value;
//
//	@FindBy(xpath = "//td[contains(text(),'AOL1')]")
//	public WebElement depCharSegmentAol1Value;
//
//	@FindBy(xpath = "//td[contains(text(),'AOL2')]")
//	public WebElement depCharSegmentAol2Value;
//
//	@FindBy(xpath = "//td[contains(text(),'FLEX1')]")
//	public WebElement depCharSegmentFlex1Value;
//
//	@FindBy(xpath = "//td[contains(text(),'FLEX2')]")
//	public WebElement depCharSegmentFlex2Value;

	@FindBy(xpath = "//a[contains(@id,'df1_PersonDFFIteratorDFFFDAPI')][@title='Select Date']")
	public WebElement globalDateSegmentIcon;

	@FindBy(xpath = "//td[@tabindex='0']")
	public WebElement currentDate;

	@FindBy(xpath="//h1[contains(text(),'Hire an Employee: Person Information')]")
	public  WebElement pageTitle2;

	@FindBy(xpath = "//h1[contains(text(),'Hire an Employee: Employment Information')]")
	public WebElement pageTitle3;

	@FindBy(xpath = "//h1[contains(text(),'Hire an Employee: Compensation and Other Information')]")
	public WebElement pageTitle4;

	@FindBy(xpath = "//h1[contains(text(),'Hire an Employee: Review')]")
	public WebElement pageTitle5;

	@FindBy(xpath = "//div[contains(@id,'businessUnitId::dropdownPopup::dropDownContent::db')]/table/tbody/tr[2]/td")
	public WebElement businessUnitLovValue;

	@FindBy(xpath = "//li[text()='Vision India']")
	public WebElement bussinessInputValue;
	/*
	 * Page elements for General Accounting and Table Layout dff
	 * 
	 */
	
	@FindBy(xpath="//a[text()='General Accounting']")
	public WebElement generalAccountHomeLink;
	
	@FindBy(xpath="//a[text()='Journals']")
	public WebElement journalsHomeLink;
	
	@FindBy(partialLinkText="Create Journal")
	public WebElement createJournalsLink;
	
	@FindBy(xpath="//input[contains(@id,'pt1:ap1:showLessBatchName::content')]")
	public WebElement jounalBatchInputField;
	
	@FindBy(xpath="//input[contains(@id,'pt1:ap1:showLessJournalName::content')]")
	public WebElement journalInputField;	
	
	@FindBy(xpath="//a[contains(@id,'pt1:ap1:userJeCategoryNameId1::lovIconId')]")
	public WebElement categoryLov;	
	
	@FindBy(xpath="//span[text()='Accrual']")
	public WebElement accrualText;	
	
    @FindBy(xpath="//input[contains(@id,'pt1:ap1:sis3:userJeCategoryNameInputSearch1::content')]")
    public WebElement categoryClientLov;    
	
	@FindBy(xpath="//img[contains(@id,'accountKBIMG::icon')]")
	public WebElement kffLink;
	
	@FindBy(xpath="//button[contains(text(),'Sea')]")
	public WebElement kffsearchBtn;
	
	@FindBy(xpath="//div[contains(@id,'accountSPOP_kffResultTable::db')]/table/tbody/tr[1]/td")
	public WebElement kffFirstRow;
	
	@FindBy(xpath="//button[text()='O']")
	public WebElement kffOKBtn;
	
	@FindBy(xpath="//input[contains(@id,'df3_JournalLineDFF1IteratorGLDFFFCAPI')]")
	public WebElement journalGlobalSegment;
	
//	@FindBy(xpath="//a[contains(@id,'_FOSritemNode_general_accounting_journals')][@title='Expand']")
//	public WebElement expandBtn;

	@FindBy(xpath="//div[contains(@id,'_ATp:t3::db')]/descendant::a[@title='Expand']")
	public WebElement expandBtn;
	
//	@FindBy(xpath="//a[contains(@id,'_FOSritemNode_general_accounting_journals')][@title='Collapse']")
//	public WebElement collapseBtn;

	@FindBy(xpath="//div[contains(@id,'_ATp:t3::db')]/descendant::a[@title='Collapse']")
	public WebElement collapseBtn;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:create::icon')]")
	public WebElement createBtn;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:delete::icon')]")
	public WebElement deleteBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:ap1:yesLineDelete')]")
	public WebElement deleteConfirmBtn1;
	
	@FindBy(xpath="//input[contains(@id,'EnteredDr::content')]")
	public WebElement debitAmountInput;
	
//	@FindBy(xpath="//select[contains(@id,'FLEX_Context::content')]")
//	public WebElement contextSegmentCode;

	@FindBy(xpath="//label[contains(text(),'Account Details')]/parent::td/following-sibling::td/select")
	public WebElement contextSegmentCode;
	
	@FindBy(xpath="//label[text()='Regional Information']")
	public WebElement regionalContextCode;
	
//	@FindBy(xpath="//select[contains(@id,'GLDFFINCHARAPI')]")
//	public WebElement journalIndSensitiveSegment;

	@FindBy(xpath="//label[contains(text(),'GLDFFINCHAR')]/parent::td/following-sibling::td/select")
	public WebElement journalIndSensitiveSegment;
	
	@FindBy(xpath="//a[contains(@id,'GLDFFDEPCHARAPI')]")
	public WebElement journalDepSensitiveSegmentLov;
	
	@FindBy(xpath="//input[contains(@id,'GLDFFDEPCHARAPI')]")
	public WebElement journalDepSensitiveSegmentinput;
	
	@FindBy(xpath="//a[contains(@title,'Search: GLDFFSUB')]")
	public WebElement journalSubSensitiveSegmentLOV;
	
	@FindBy(xpath="//input[contains(@id,'::_afrLovInternalQueryId:value00::content')]")
	public WebElement journalSubLOVSearchUI;
	
	@FindBy(xpath="//button[text()='Search']")
	public WebElement journalSubLOVSearchBtn;
	
	@FindBy(xpath="//button[text()='Cancel']")
	public WebElement journalSubLOVCancelBtn;
	
	@FindBy(xpath="//button[text()='OK']")
	public WebElement journalSubLOVOKBtn;
	
	@FindBy(xpath="//input[contains(@id,'GLDFFSUBAPI')]")
	public WebElement journalSubSegmentInput;
	
	@FindBy(xpath="//input[contains(@id,'GLDFFTABAPI')]")
	public WebElement journalTableInput;
	
	@FindBy(xpath="//a[contains(@id,'GLDFFTABAPI')]")
	public WebElement journalTableLov;
	
	@FindBy(xpath="//button[contains(@id,'userResponsePopupDialogButtonYes')]")
	public WebElement saveConfirmYesBtn;
	
	@FindBy(xpath="//a[text()='Incomplete']")
	public WebElement journalIncompleteTab;
	
	@FindBy(xpath="//img[contains(@id,'AT1:_ATp:_qbeTbr::icon')]")
	public WebElement journalQBEField;
	
	@FindBy(xpath="//input[contains(@id,'afr__ATp_afr_table1_afr_incompletetab_journalBatch::content')]")
	public WebElement journalQBEFieldInput;
	
	@FindBy(xpath="//a[contains(@id,'pt1:ap1:saveBatch::popEl')]")
	public WebElement journasaveAndCloseDropDown;
	
	@FindBy(xpath="//td[text()='ave and Close']")
	public WebElement journalsaveAndCloseBtn;

	@FindBy(xpath = "//label[text()='Account']/preceding-sibling::input[contains(@value,'-')]")
	public WebElement kffInputValue;

	@FindBy(xpath = "//div[contains(text(),'it must contain only uppercase characters.')]")
	public WebElement jUppserCaseErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'it must not contain more characters than the maximum allowable length')]")
	public WebElement jMaxLenthErrorMessage;

	@FindBy(xpath = "//option[contains(text(),'GLDFFCON_NAME')]")
	public WebElement jDffContextSegmentCode;

	@FindBy(xpath = "//option[contains(text(),'Trading Partner')]")
	public WebElement jSedDffContextSegmentCode;

	@FindBy(xpath = "//label[contains(text(),'GLDFFINCHAR')]")
	public WebElement jContextSensitiveSegment;

	@FindBy(xpath="//label[contains(text(),'Trading Partner')]")
	public WebElement jContextSensitiveSegmentSeed;
	
	@FindBy(xpath = "//option[@title='AOL']")
	public WebElement jIndependentAolValue;

	@FindBy(xpath = "//option[@title='FLEX']")
	public WebElement jIndependentFlexValue;

	@FindBy(xpath = "//span[text()='AOL1']")
	public WebElement jDepAol1Value;

	@FindBy(xpath = "//span[text()='AOL2']")
	public WebElement jDepAol2Value;

	@FindBy(xpath = "//span[text()='FLEX1']")
	public WebElement jDepFlex1Value;

	@FindBy(xpath = "//span[text()='FLEX2']")
	public WebElement jDepFlex2Value;

	@FindBy(xpath="//span[text()='AOL']")
	public WebElement jSubAolValue;

	@FindBy(xpath = "//span[text()='FND_TREE_STRUCTURE']")
	public WebElement jTabValue;

	
	
	/*
	 * 
	 * Client LOV page elements
	 * 
	 */	
	
	@FindBy(xpath="//input[contains(@id,'CLOVINCHARAPI')]")
	public WebElement clovgIndCharInput;
	
	@FindBy(xpath="//input[contains(@id,'CLOVDEPCHARAPI')]")
	public WebElement clovgDepCharInput;
	
	@FindBy(xpath="//li[contains(text(),'SCLOVCON_NAME')]")
	public WebElement clovContextCode;	
	
	@FindBy(xpath="//input[contains(@id,'SCLOVINCHARAPI')]")
	public WebElement clovSIndCharInput;
	
	@FindBy(xpath="//input[contains(@id,'SCLOVDEPCHARAPI')]")
	public WebElement clovSDepCharInput;
	
	@FindBy(xpath="//input[contains(@id,'SCLOVNUMAPI')]")
	public WebElement clovSIndNumInput;
	
	@FindBy(xpath="//input[contains(@id,'SCLOVDEPNUMAPI')]")
	public WebElement clovSDepNumInput;
	
	@FindBy(xpath="//input[contains(@id,'SCLOVTABAPI')]")
	public WebElement clovSTableInput;
	
	
	
	/*
	 * 
	 * BIND Client LOV elements
	 * 
	 */
	
	
	@FindBy(xpath="//li[contains(text(),'BINDCON_NAME')]")
	public WebElement bindContextCode;	
	
	@FindBy(xpath="//input[contains(@id,'BIND1API')]")
	public WebElement bind1;
	
	@FindBy(xpath="//input[contains(@id,'BIND2API')]")
	public WebElement bind2;
	
	@FindBy(xpath="//input[contains(@id,'BIND3API')]")
	public WebElement bind3;
	
	@FindBy(xpath="//input[contains(@id,'BIND4API')]")
	public WebElement bind4;
	
	@FindBy(xpath="//input[contains(@id,'BIND5API')]")
	public WebElement bind5;
	
	
	
	/*
	 * Fetch Context code dynamically elements
	 */
	
	@FindBy(xpath="//a[text()='Me']")
	public WebElement  meLink;
	
	@FindBy(xpath="//a[text()='Personal Information']")
	public WebElement  personalInfoLink;
	
	@FindBy(xpath="//a[@title='Document Records']")
	public WebElement  documentRecordsLink;
	
	@FindBy(xpath="//span[text()='Add']")
	public WebElement  addButton;
	
	@FindBy(xpath="//input[contains(@id,'dorDUpl:UPsp1:sh1:lcvSis:typRSrh::content')]")
	public WebElement  searchInputField;	
	
//	@FindBy(xpath="//tr[contains(@id,'typRSrh::item0')]") //div[text()='Legal document']
	@FindBy(xpath="//div[text()='Legal document']")
	public WebElement  birthRecord;
	
	@FindBy(xpath="//div[text()='Licenses and certificates']") //Licenses and certificates
	public WebElement  licenseRecord;
	
	@FindBy(xpath="//input[@value='GLB_BIRTH']")
	public WebElement  contextBirthCode;
	
	@FindBy(xpath="//label[text()='Birth']")
	public WebElement  birthLabel;
	
	@FindBy(xpath="//label[text()='Drivers License']")
	public WebElement  licenseLabel;
		
	@FindBy(xpath="//input[@value='GLB_DRIVERS_LICENSE']")
	public WebElement  contextLicenseCode;
	
	
	/*
	 * DFF segment label page details
	 * 
	 */
	
	
	@FindBy(xpath="//button[contains(@id,'pt1:dffPnl:ctb1')]")
	public WebElement  _labelButton;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APc')]") 
	public WebElement  _labelCancelButton;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT1:_ATp:t1::db')]/table")
	public WebElement  _labelTableSummary;


	@FindBy(xpath = "//input[contains(@id,'pt1:dffPnl:cssICLOV::content')]")
	public WebElement _contextInput;

	@FindBy(xpath="//a[contains(@id,'pt1:dffPnl:cssICLOV::lovIconId')]")
	public WebElement  _contextSearchLov;
	
	@FindBy(xpath="//a[contains(@id,'dffPnl:cssICLOV::dropdownPopup::popupsearch')]")
	public WebElement _contextSearchLink;
	
	@FindBy(xpath="//input[contains(@id,'dffPnl:cssICLOV::_afrLovInternalQueryId:value20::content')]")
	public WebElement  _contextDisplayNameInput;
	
	@FindBy(xpath="//button[contains(@id,'pt1:dffPnl:cssICLOV::_afrLovInternalQueryId::search')]")
	public WebElement _contextDisplayNameSearchBtn;
	
	@FindBy(xpath="//div[contains(@id,'dffPnl:cssICLOV_afrLovInternalTableId::db')]/table/tbody/tr/td[2]/div/table/tbody/tr/td")
	public WebElement _contextDisplayNameSearchResults;
	
	@FindBy(xpath="//button[contains(@id,'pt1:dffPnl:cssICLOV::lovDialogId::ok')]")
	public WebElement _contextOKButton;
	
	@FindBy(xpath="//div[contains(@id,'pt1:dffPnl:gsAT:_ATp:gsT::scroller')]")
	public WebElement _desceningOrder;
	
	
	/*
	 * 
	 * Manage hiring page elements
	 * 
	 */
	
	@FindBy(xpath="//a[@role='button']")
	public WebElement _1addButton;
	
	@FindBy(xpath="//input[contains(@id,'retpSel::content')]")
	public WebElement _2recrutingType;

	@FindBy(xpath = "//li[contains(text(),'Campus')]")
	public WebElement _2recrutingType1;

	@FindBy(xpath = "//span[contains(text(),'GHR_WS_POS_REQ_TEMP01')]")
	public WebElement _2recrutingType2;
	
	@FindBy(xpath="//input[contains(@id,'tmpSis:tmpIs::content')]")
	public WebElement _3recrutingTemplate;

	@FindBy(xpath = "//input[contains(@id,'tmpSis:tmpIs::content') and contains(@disabled,'')]")
	public WebElement _3recrutingTemplateDisable;
	
	@FindBy(xpath="//button[@title='Continue']")
	public WebElement _4continueButton;
	
	@FindBy(xpath="//input[contains(@id,'rneInp::content')]")
	public WebElement _5requestNumber;
	
	@FindBy(xpath="//input[contains(@id,'reSis:reIs::content')]")
	public WebElement _6recruiter;

	@FindBy(xpath = "//span[contains(text(),'ANC_ZBEN_US_EMPLOYEE01')]")
	public WebElement _6recruiter1;
	
	@FindBy(xpath="//input[contains(@id,'orgSis:orgIs::content')]")
	public WebElement _7organization;

	@FindBy(xpath = "//span[contains(text(),'Automation')]")
	public WebElement _7organization1;
	
	
	@FindBy(xpath="//input[contains(@id,'DPK1')]")
	public WebElement _segment1;

	@FindBy(xpath="//a[contains(@id,'DPK1')]")
	public WebElement _segment1Lov;

	@FindBy(xpath = "//ul[contains(@id,'DPK1')]/li[contains(text(),'US')]")
	public WebElement _segment1LovUSValue;

	@FindBy(xpath = "//ul[contains(@id,'DPK1')]/li[contains(text(),'India')]")
	public WebElement _segment1LovIndiaValue;

	@FindBy(xpath="//input[contains(@id,'DPK2')]")
	public WebElement _segment2;

	@FindBy(xpath = "//div[contains(text(),'You must enter a value.')]")
	public WebElement _segment2ErrorMessage;

	@FindBy(xpath = "//a[contains(@id,'DPK2')]")
	public WebElement _segment2Lov;

	@FindBy(xpath = "//span[contains(text(),'New York')]")
	public WebElement _segment2LovNYValue;

	@FindBy(xpath="//input[contains(@id,'DPK3')]")
	public WebElement _segment3;

	@FindBy(xpath = "//td[contains(text(),'New York')]")
	public WebElement _segment3NYValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK4')]")
	public WebElement _segment4;

	@FindBy(xpath = "//td[contains(text(),'India')]")
	public WebElement _segment4IndValue;

	@FindBy(xpath = "//a[contains(@id,'DPK4')]")
	public WebElement _segment4Lov;

	@FindBy(xpath = "//li[text()='US']")
	public WebElement _segment4USValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK5')]")
	public WebElement _segment5;

	@FindBy(xpath = "//div[contains(text(),'is a required field')]")
	public WebElement _segment5ErrorMessage;

	@FindBy(xpath = "//td[contains(text(),'Bangalore')]")
	public WebElement _segment5BlrValue;
	
	@FindBy(xpath="//a[contains(@id,'DPK5')]")
	public WebElement _segment5Lov;

	@FindBy(xpath = "//span[contains(text(),'New York')]")
	public WebElement _segment5LovNYValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK6')]")
	public WebElement _segment6;

	@FindBy(xpath = "//span[contains(text(),'US')]")
	public WebElement _segment6USValue;

	@FindBy(xpath="//a[contains(@id,'DPK6')]")
	public WebElement _segment6Lov;
	
	@FindBy(xpath="//input[contains(@id,'DPK7')]")
	public WebElement _segment7;

	@FindBy(xpath = "//ul[contains(@id,'DPK7')]/li[contains(text(),'US')]")
	public WebElement _segment7USValue;

	@FindBy(xpath = "//td[contains(text(),'New York')]")
	public WebElement _segment7NYValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK8')]")
	public WebElement _segment8;

	@FindBy(xpath = "//div[contains(text(),'You must enter a value.')]")
	public WebElement _segment8ErrorMessage;

	@FindBy(xpath = "//ul[contains(@id,'DPK8')]/li[last()]")
	public WebElement _segment8InValue;

	@FindBy(xpath="//a[contains(@id,'DPK8')]")
	public WebElement _segment8Lov;

	@FindBy(xpath = "//span[contains(text(),'New York')]")
	public WebElement _segment8LovNYValue;

	@FindBy(xpath = "//span[contains(text(),'San Francisco')]")
	public WebElement _segment8LovSFValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK9')]")
	public WebElement _segment9;

	@FindBy(xpath = "//td[contains(text(),'value is required')]")
	public WebElement _segment9ErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Error')]")
	public WebElement genericError;

	@FindBy(xpath = "//button[contains(@id,'msgDlg::cancel')]")
	public WebElement genericErrorOKButton;
	
	@FindBy(xpath="//a[contains(@id,'DPK9')]")
	public WebElement _segment9Lov;

	@FindBy(xpath = "//span[contains(text(),'New York')]")
	public WebElement _segment9LovNYValue;

	@FindBy(xpath = "//span[contains(text(),'San Francisco')]")
	public WebElement _segment9LovUSValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK10')]")
	public WebElement _segment10;

	@FindBy(xpath = "//td[contains(text(),'New York')]")
	public WebElement _segment10LovNyValue;

	@FindBy(xpath = "//td[contains(text(),'India')]")
	public WebElement _segment10LovIndiaValue;

	@FindBy(xpath = "//td[contains(text(),'US')]")
	public WebElement _segment10LovUSValueIrc;

	@FindBy(xpath = "//td[contains(text(),'San Francisco')]")
	public WebElement _segment10LovSfValue;

	@FindBy(xpath = "//td[contains(text(),'San Francisco')]")
	public WebElement _segment10LovUSValue;

	@FindBy(xpath = "//div[contains(text(),'IRC_DPK11 is a required field.')]")
	public WebElement _segment10ErrorMessage;
	
	@FindBy(xpath="//input[contains(@id,'DPK11')]")
	public WebElement _segment11;

	@FindBy(xpath = "//td[contains(text(),'New York')]")
	public WebElement _segment11LovNYValue;

	@FindBy(xpath = "//td[contains(text(),'San Francisco')]")
	public WebElement _segment11LovSFValue;

	@FindBy(xpath = "//td[contains(text(),'India')]")
	public WebElement _segment11LovIndValue;

	@FindBy(xpath = "//div[contains(text(),'is a required field.')]")
	public WebElement _segment11ErrorMessage;
	
	@FindBy(xpath="//input[contains(@id,'DPK12')]")
	public WebElement _segment12;

	@FindBy(xpath = "//td[contains(text(),'Bangalore')]")
	public WebElement _segment12BlrValue;

	@FindBy(xpath = "//td[contains(text(),'Delhi')]")
	public WebElement _segment12DelValue;
	
	@FindBy(xpath="//a[contains(@id,'DPK12')]")
	public WebElement _segment12Lov;
	
	@FindBy(xpath="//button[contains(@id,'_afrLovInternalQueryId::search')]")
	public WebElement _segment12SearchUI;

	@FindBy(xpath = "//span[contains(text(),'Bangalore')]")
	public WebElement _segment12SearchUIBlrValue;

	@FindBy(xpath = "//span[contains(text(),'Delhi')]")
	public WebElement _segment12SearchUIDelValue;

	@FindBy(xpath = "//span[contains(text(),'New York')]")
	public WebElement _segment12SearchUINYValue;

	@FindBy(xpath = "//span[contains(text(),'San Francisco')]")
	public WebElement _segment12SearchUISFValue;
	
	@FindBy(xpath="//button[contains(@id,'lovDialogId::ok')]")
	public WebElement _segment12OK;	
	
	@FindBy(xpath="//input[contains(@id,'DPK13')]")
	public WebElement _segment13;

	@FindBy(xpath="//a[contains(@id,'DPK13')]")
	public WebElement _segment13Lov;

	@FindBy(xpath = "//ul[contains(@id,'DPK13')]/li[contains(text(),'Bangalore')]")
	public WebElement _segment13LovPhBlrValue;

	@FindBy(xpath = "//ul[contains(@id,'DPK13')]/li[contains(text(),'Delhi')]")
	public WebElement _segment13LovPhDelValue;

	@FindBy(xpath = "//td[contains(text(),'Reporting name')]")
	public WebElement _segment13LovValue;

	@FindBy(xpath = "//td[contains(text(),'State code')]")
	public WebElement _segment13LovValue1;
	
	@FindBy(xpath="//input[contains(@id,'DPK14')]")
	public WebElement _segment14;

	@FindBy(xpath = "//td[contains(text(),'New York')]")
	public WebElement _segment14LovNYValue;

	@FindBy(xpath = "//td[contains(text(),'San Francisco')]")
	public WebElement _segment14LovSFValue;

	@FindBy(xpath = "//td[contains(text(),'Bangalore')]")
	public WebElement _segment14LovBlrValue;

	@FindBy(xpath = "//td[contains(text(),'Delhi')]")
	public WebElement _segment14LovDelValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK15')]")
	public WebElement _segment15;
	
	@FindBy(xpath="//input[contains(@id,'DPK16')]")
	public WebElement _segment16;

	@FindBy(xpath = "//table[contains(@id,'DPK16')]/tr")
	public List<WebElement> _segment16LovAllValues;

	@FindBy(xpath = "//table[contains(@id,'DPK16')]/tr[1]/td[1]")
	public WebElement _segment16LovSingaleValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK17')]")
	public WebElement _segment17;

	@FindBy(xpath = "//table[contains(@id,'DPK17')]/tr[1]/td[1]")
	public WebElement _segment17SingleValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK18')]")
	public WebElement _segment18;

	@FindBy(xpath = "//table[contains(@id,'DPK18')]/tr[1]/td[1]")
	public WebElement _segment18SingleValue;
	
	@FindBy(xpath="//input[contains(@id,'DPK19')]")
	public WebElement _segment19;

	@FindBy(xpath = "//table[contains(@id,'DPK19')]/tr[1]/td[1]")
	public WebElement _segment19SingleValue;
	
	@FindBy(xpath="//a[contains(@id,'DPK15')]")
	public WebElement _segment15Lov;

	@FindBy(xpath = "//span[contains(text(),'New York')]")
	public WebElement _segment15LovNYValue;

	@FindBy(xpath = "//span[contains(text(),'San Francisco')]")
	public WebElement _segment15LovSFValue;
	
	@FindBy(xpath="//input[contains(@id,'FLEX_Context')]")
	public WebElement _contextCode;

	@FindBy(xpath = "//li[contains(text(),'CC_NAME1')]")
	public WebElement _contextSegmentCode;

	@FindBy(xpath = "//li[contains(text(),'CC_NAME2_UPDATE')]")
	public WebElement _contextSegmentCode1;
	
	@FindBy(xpath="//button[contains(@id,'UPsp1:saveBtn')]")
	public WebElement resSaveAndCloseBtn;
	
	@FindBy(xpath = "//button[contains(@id,'cancel')]")
	public WebElement errorOKBtn;

	@FindBy(xpath = "//span[contains(text(),'Confirmation')]")
	public WebElement confirMation;
	
	
	
	/*
	 * 
	 * Natiaonal Identifiers page element
	 * 
	 */
	
	@FindBy(xpath="//img[contains(@id,'AT2:_ATp:create::icon')]")
	public WebElement nat_1AddBtn;
	
	@FindBy(xpath="//a[contains(@id,'soc2::drop')]")
	public WebElement nat_2IdenfierDD;
	
	@FindBy(xpath = "//input[contains(@id,'AT2:_ATp:table2:0:it1::content')]")
	public WebElement nat_3NationalIDInput;
	
	@FindBy(xpath="//a[contains(@id,'AT2:_ATp:table2:0:iclov1::lovIconId')]")
	public WebElement nat_4CountryDD;
	
	@FindBy(xpath="//input[contains(@id,'AT2:_ATp:table2:0:iclov1::content')]")
	public WebElement nat_4CountryDDInput;
	
	@FindBy(xpath="//a[contains(@id,'iclov1::dropdownPopup::popupsearch')]")
	public WebElement nat_5SearchLink;
	
	@FindBy(xpath = "//input[contains(@id,'_afrLovInternalQueryId:value00::content')]")
	public WebElement nat_6SearchInput;
	
	@FindBy(xpath = "//button[contains(@id,'::_afrLovInternalQueryId::search')]")
	public WebElement nat_7SearchBtn;
	
	@FindBy(xpath="//span[text()='United States']")
	public WebElement nat_8Result;
	
	@FindBy(xpath = "//button[contains(@id,'::lovDialogId::ok')]")
	public WebElement nat_9SearchOKBtn;
	
	@FindBy(xpath = "//a[contains(@id,'pt_r2:0:AT2:_ATp:table2:0::di')]")
	public WebElement nat_10ExpandIcon;
	
	@FindBy(xpath = "//a[contains(@id,'df1_NationalIdentifierDFF')]")
	public WebElement nat_11ContextDD;



	@FindBy(xpath = "//span[contains(@id,'r1:0:SP1:outputText1')]")
	public WebElement ph_1outText;

	@FindBy(xpath = "//h1[contains(.,'Phone Details')]")
	public WebElement ph_2PageTitle;

	@FindBy(xpath = "//img[contains(@id,'AT1:_ATp:create::icon')]")
	public WebElement ph_3CreateIcon;

	@FindBy(xpath = "//a[contains(@id,'pt_r1:0:AT1:_ATp:table1:0::di')]")
	public WebElement ph_4expandIcon;

	@FindBy(xpath="//a[contains(@id,'pt1:pt_r1:1:SP1:Perso2:0:ph2::_afrDscl')]")
	public WebElement ph_41PhoneExpanIcon;

	@FindBy(xpath = "//a[contains(@id,'AT1:_ATp:table1:0:soc1::drop')]")
	public WebElement ph_5TyepDD;

	@FindBy(xpath = "//li[contains(text(),'Home Fax')]")
	public WebElement ph_5TypeValue;

	@FindBy(xpath = "//input[contains(@id,'ATp:table1:0:iclov1::content')]")
	public WebElement ph_6CountryCode;

	@FindBy(xpath = "//a[contains(@id,'ATp:table1:0:iclov1::lovIconId')]")
	public WebElement ph_61CountryCodeLov;

	@FindBy(xpath="//a[contains(@id,'ATp:table1:0:iclov1::dropdownPopup::popupsearch')]")
	public WebElement ph_62SearchLink;

	@FindBy(xpath = "//input[contains(@id,'afrLovInternalQueryId:value00::content')]")
	public WebElement ph_63CountryCodeInput;

	@FindBy(xpath = "//button[contains(@id,'afrLovInternalQueryId::search')]")
	public WebElement ph_64SearchBtn;

	@FindBy(xpath = "//li[contains(text(),'India 91')]")
	public WebElement ph_65Result;

	@FindBy(xpath = "//button[contains(@id,'ATp:table1:0:iclov1::lovDialogId::ok')]")
	public WebElement ph_66OKBtn;

	@FindBy(xpath = "//input[contains(@id,'ATp:table1:0:it3::content')]")
	public WebElement ph_7Number;

	@FindBy(xpath = "//li[contains(text(),'India 91')]")
	public WebElement ph_8IndiaCode;

	@FindBy(xpath = "//button[contains(@id,'okConfirmationDialog')]")
	public WebElement ph_9ConfirmationOKBtn;

	@FindBy(xpath = "//li[contains(text(),'PHONE_EXTRAINFO')]")
	public WebElement _phContextSegmentCode;

	@FindBy(xpath = "//li[contains(text(),'PH_NAME1')]")
	public WebElement _phContextSegmentCode1;


	/*
	 * 
	 * Elements added by Ashish Pareek for the full flow of Hire a New Person 
	 * 
	 */
	
	@FindBy(xpath="//a[contains(@id,'SP1:selectOneChoice3::lovIconId')]") 
	public WebElement legalEmployerDropdown;
	
	@FindBy(xpath = "//li[contains(text(),'GBI_GHR_3TSTMA')]")
	public WebElement legalEmployeeUSValue;
	
	@FindBy(xpath="//input[contains(@id,'Person_glb')]") 
	public WebElement personglb;
	
	@FindBy(xpath="//input[contains(@id,'FBLPG')]") 
	public WebElement fblpg;
	
	@FindBy(xpath="//td[contains(@class,'af_dialog_header')]/div[normalize-space(text())='Matching Person Records']") 
	public WebElement matchingPersonRecordPopup;
	
	@FindBy(xpath="//button[contains(@class,'af_dialog_footer') and normalize-space(text())='ancel']") 
	public WebElement matchingPersonRecord_CancelBtn;
	
	@FindBy(xpath="//div[contains(@class,'p_AFDisabled p_AFBusy')]") 
	public WebElement busyButton;
	
	@FindBy(xpath="//h1[contains(text(),'Hire an Employee: Employment Information')]") 
	public WebElement HireAnEmp_Page3;
	
	@FindBy(xpath="//h1[contains(text(),'Hire an Employee: Compensation and Other Information')]") 
	public WebElement HireAnEmp_Page4;
	
	@FindBy(xpath="//h1[contains(text(),'Hire an Employee: Review')]") 
	public WebElement HireAnEmp_Page5;
	

	//*******************Completed - Ashish Pareek*****************//



}


