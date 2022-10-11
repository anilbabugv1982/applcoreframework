package pageDefinitions.UI.oracle.applcore.qa.Eff;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;
import TestBase.UI.GetDriverInstance;

public class EFFReferenceDataPage {
	WebDriver driver;
	public EFFReferenceDataPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//Test
	@FindBy(xpath="//a[@id='pt1:_UIScmi99']")
	public static WebElement aboutLink;

	@FindBy(xpath="//div[@id='pt1:_dialog::_ttxt']")
	public static WebElement aboutHeader;

	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:ott4_1']")
	public static WebElement currentBranchName;

	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:i1:0:ott22']")
	public static WebElement currentADFLabel;

	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:i1:1:ott22']")
	public static WebElement currentATGLabel;

	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:i1:1:ott23']")
	public static WebElement currentDBPatchLabel;

	@FindBy(xpath="//a[@id='pt1:_dialog::close']")
	public static WebElement closeAboutPopUp;

	@FindBy(xpath = "//*[contains(@id,'UIScmil1u::icon')]")
	public WebElement userLink;
	//Test

	@FindBy(xpath="//a//child::span[text()='Deploy Flexfield']")
	public static WebElement DeployFlex;

	@FindBy(xpath="//a//child::span[text()='Deploy Offline']")
	public static WebElement DeployOffline;

	@FindBy(xpath="//div[contains(@id,':progressLayout2')]")
	public static WebElement offlineDeployInProgressLogs;

	@FindBy(xpath="//span[contains(@id,'progtxtj_id_2')]")
	public static WebElement offLineDeploySuccessMessage;

	@FindBy(xpath="//button[contains(@id,'ffTab:1:rfrshLog')]")
	public static WebElement offlineDeployRefreshButton;

	@FindBy(xpath="//button[contains(@id,'ffTab:1:d134::ok')]")
	public static WebElement offlineDeployOkButton;

	@FindBy(xpath="//img[contains(@id,'_ATp:ffTab:0:i4') and @alt='Deployed']")
	public static WebElement imgDeploymentSuccess;

	@FindBy(xpath="//img[contains(@id,'_ATp:ffTab:1:i1')]")
	public static WebElement imgDeploymentInProgress;

	@FindBy(xpath="//div[contains(@id,'ffAT:d1::_ttxt')]")
	public static WebElement deployFlexHeaderText;

	@FindBy(xpath="//button[contains(@id,'ffAT:okdlgp')]")
	public static WebElement deployFlexOkButton;

	@FindBy(xpath="//span[contains(@id,'AP1:ffAT:pollM1')]")
	public static WebElement deploymentConfirmText;

	@FindBy(xpath="//input[contains(@id,'AP1:q1:value10::content')]")
	public static WebElement flexFieldCode;

	@FindBy(xpath="//button[contains(@id,'::search')]")
	public static WebElement searchButton;

	@FindBy(xpath="//input[contains(@id,'ectxQRY:value20::content')]")
	public static WebElement contextCode_TextBox;

	// workforce structure page
	@FindBy(xpath="//input[contains(@id,'ll01Inp::content')]")
	public static WebElement searchField;

	@FindBy(xpath="//a[text()='Manage Locations']")
	public static WebElement manageLocationLink; //These are added for responsive HCM UI

	@FindBy(xpath="//a[contains(@id,'s1:ll02Cil')]")
	public static WebElement searchLocation;

	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement vsCodeSearchInputText;

	@FindBy(xpath="//td/*[contains(@id,'AP1:ffAT:_ATp:ffTab:0:ot04')]")
	public static WebElement flexCodeColumn;

	@FindBy(xpath="//a[@class='af_commandToolbarButton_link']//child::img[contains(@id,'pt1:AP1:ffAT:_ATp:edit::icon')]")
	public static WebElement flexCodeEditIcon;

	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APc') or contains(@id,'effPnl:APc')]")
	public static WebElement CancelButton;

	@FindBy(xpath="//button[contains(@id,'pt1:effPnl:APscl') or contains(@id,'pt1:AP1:APscl') or contains(@id,'AP2:APscl')]")
	public static WebElement SaveAndCloseButton;

	@FindBy(xpath="//td[@class='af_commandToolbarButton_link-cell']//span[@class='af_commandToolbarButton_text']")
	public static WebElement contextsegmentSaveAndClose;

	@FindBy(xpath="//div[contains(@id,'pt1:AP1:ctb2')]/a")
	public static WebElement modifiedSegmentSaveClose;

	@FindBy(xpath="//div[contains(@id,'AP1:ctb2')]/a")
	public static WebElement editSegmentSaveAndClose;

	@FindBy(xpath="//button[contains(@id,'pt1:effPnl:APsv') or @text='Save']")
	public static WebElement SaveButton;

	@FindBy(xpath="//button[contains(@id,'pt1:effPnl:csACTB0')]")
	public static WebElement ManageContextButton;

	@FindBy(xpath="//div[contains(@id,'pt1:effPnl:eCatCxAT:_ATp:ATm')]")
	public static WebElement AssociateContextActionMenu;

	@FindBy(xpath="//a[contains(@id,'effSDH2::disAcr')]")
	public static WebElement associatedContextsTabLink;

	@FindBy(xpath="//div[contains(@id,'effSDH3::ti')]//a[text()='Pages']")
	public static WebElement associatedPagesTabLink;

	@FindBy(xpath="//a[@class='af_commandToolbarButton_link']//child::img[contains(@id,'create::icon')]")
	public static WebElement createNewPageLink;
	
	@FindBy(xpath="//div[contains(@id,'_ATp:delete') and @title = 'Delete']/a")
	public static WebElement deletePageLink;

	@FindBy(xpath="//input[contains(@id,'eCatPgCrIT0::content')]")
	public static WebElement displayNewPageName;

	@FindBy(xpath="//input[contains(@id,'eCatPgAT:eCatPgCrIT3::content')]")
	public static WebElement displayNewPageDescription;

	@FindBy(xpath="//textarea[contains(@id,'itInsHlpTxtCr::content')]")
	public static WebElement displayNewPageHelpText;

	@FindBy(xpath="//a[contains(@id,'eCatPgCrLOV0::lovIconId')]")
	public static WebElement displayNewPageUsageTableExpandLink;

	@FindBy(xpath="//table[@class='af_table_data-table']")
	public static WebElement displayNewPageUsageTable;

	@FindBy(xpath="//input[contains(@id,'eCatPgCrLOV0::content')]")
	public static WebElement displayNewPageUsageNameTextbox;

	@FindBy(xpath="//table[@class='af_table_data-table af_table_data-table-VH-lines']")
	public static WebElement pageTable;

	@FindBy(xpath="//*[@class='af_panelHeader_title-text2 ']")
	public static WebElement pageNameHeader;

	@FindBy(xpath="//td[@class='af_commandMenuItem_menu-item-text']/*[contains(text(),'Select and ADropdown')]")
	public static WebElement ContextSelectADropdownMenuItem;

	@FindBy(xpath="//a//child::img[(@id,'_ATp:selADropdown::icon')]")
	public static WebElement ContextSelectADropdownImgIcon;

	@FindBy(xpath="//img[contains(@id,'_ATp:selADD::icon') and @title='Select and Add']")
	public static WebElement selectAddContextToCategory;

	@FindBy(xpath="//input[contains(@id,'eCatCxAT:eCatNotCxQRY:value20::content') or contains(@id,'eCatPgNCxQRY:value20::content')  and @class='af_inputText_content']")
	public static WebElement selectContextCode;

	@FindBy(xpath="//td[@class='af_commandMenuItem_menu-item-text'][contains(text(),'Create')]")
	public static WebElement PagesActionCreateMenuItem;

	@FindBy(xpath="//div[contains(@id,'pt1:effPnl:eCatPgCxAT:_ATp:ATm')]//div[@class='af_menu_bar-item-content']//table[@cellspacing='0']//tbody//tr//td[@class='af_menuBar_item-text-cell']//a[contains(text(),'Actions')]")
	public static WebElement PageContextAssociateActionsMenu;

	@FindBy(xpath="//a[@class='af_commandToolbarButton_link']//child::img[contains(@title,'Select and Add')]")
	public static WebElement PageContextAssociateImgIcon;

	@FindBy(xpath="//input[contains(@id,'eCatPgNCxQRY:value00::content')]")
	public static WebElement pageContextAssociateName;

	//		@FindBy(xpath="//input[contains(@id,'value20::content')]")
	//		public static WebElement pageContextAssociateCode;

	@FindBy(xpath="//label[contains(text(),'Context Code')]/ancestor::tr[1]/descendant::input")
	public static WebElement pageContextAssociateCode;

	@FindBy(xpath="//table[@style='table-layout:fixed;width:877px']")
	public static WebElement pageContextSearchTable;

	@FindBy(xpath="//table[@style='table-layout: fixed; width: 1446px']")
	public static WebElement associatedContextsToPageTable;

	@FindBy(xpath="//img[contains(@id,'pt1:AP1:ectxSRAT:_ATp:create::icon')]/parent::a")
	public static WebElement createContextImgIcon;

	@FindBy(xpath="//div[contains(@id,'_ATp:edit')]")
	public static WebElement editContext;

	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it4::content')]")
	public static WebElement createContextDisplayName;

	@FindBy(xpath="//textarea[contains(@id,'pt1:AP1:it2::content')]")
	public static WebElement createContextDescription;

	@FindBy(xpath="//input[contains(@id,'itdbviewname::content')]")
	public static WebElement pimDBViewName;

	@FindBy(xpath="//a//child::img[contains(@id,'AP1:AT2:_ATp:create')]")
	public static WebElement addContextUsage;

	@FindBy(xpath="//img[contains(@id,'pt1:AP1:AT2:_ATp:create::icon')]")
	public static WebElement aDropdownContextUsageImgIcon;

	@FindBy(xpath="//select[contains(@id,'AP1:AT2:_ATp:ATt2:0:soc5::content')]")
	public static WebElement contextUsageDropdown;

	@FindBy(xpath="//select[contains(@id,'pt1:AP1:soc1::content')]")
	public static WebElement contextBehaviorDropdown;

	@FindBy(xpath="//button[contains(@id,'pt1:AP1:cusSV') or contains(@id,'pt1:AP1:APsv')]")
	public static WebElement contextSaveBtn;

	@FindBy(xpath="//div[contains(@id,'pt1:AP1:ctb1')]")
	public static WebElement contextSaveCloseBtn;

	@FindBy(xpath="//*[contains(@id,'AP1:it1::content')]")
	public static WebElement contextCode;

	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it3::content')]")
	public static WebElement contextAPIName;

	@FindBy(xpath="//div[contains(@id,'_ATp:edit')]/a")
	public static WebElement editSegment;

	@FindBy(xpath="//table[contains(@summary,'segment')]")
	public static WebElement segmentTable;

	@FindBy(xpath="//img[contains(@id,'AP1:AT1:_ATp:create::icon')]//parent::a[@class='af_commandToolbarButton_link']")
	public static WebElement addSegmentImgIcon;	//Dropdown

	@FindBy(xpath="//div[contains(@id,'_ATp:delete')]/a")
	public static WebElement deleteSegmentIcon;

	@FindBy(xpath="//button[contains(@id,'AT1:confirm')]")
	public static WebElement yesButton_DeleteSegment;

	@FindBy(xpath="//span[contains(@id,'AT1:ATot1')]")
	public static WebElement warningMessage_DeleteSegment;

	@FindBy(xpath="//*[text()='Create Segment']")
	public static WebElement createSegmentHeader;

	@FindBy(xpath="//*[text()='Edit Segment']")
	public static WebElement editSegmentHeader;

	@FindBy(xpath="//input[contains(@id,'AP1:it3::content')]")
	public static WebElement segmentName;

	@FindBy(xpath="//input[contains(@id,'AP1:it11::content')]")
	public static WebElement segmentCode;

	@FindBy(xpath="//input[contains(@id,'AP1:it4::content')]")
	public static WebElement segmentAPIName;

	@FindBy(xpath="//textarea[contains(@id,'pt1:AP1:it5::content')]")
	public static WebElement segmentDescription;

	@FindBy(xpath="//input[contains(@id,'sbc3::content')]/parent::span[@class='af_selectBooleanCheckbox_content-input']")
	public static WebElement segmentUniqueKey;	//checkbox

	@FindBy(xpath="//select[contains(@id,'AP1:dataTypeLov::content')]")
	public static WebElement segmentDataType;	//Dropdown

	@FindBy(xpath="//input[contains(@id,'AP1:columnNameId::content')]")
	public static WebElement segmentDataTypeColumn;  //Dropdown

	@FindBy(xpath="//a[contains(@id,'AP1:valueSetCodeId::lovIconId')]")
	public static WebElement valuesetSelect;

	@FindBy(xpath="//a[contains(@id,'AP1:valueSetCodeId::dropdownPopup::popupsearch')]")
	public static WebElement vsSearchLink;

	@FindBy(xpath="//input[contains(@id,'::_afrLovInternalQueryId:value00::content')]")
	public static WebElement searchNametextBox;

	@FindBy(xpath="//button[contains(@class,'button p_AFTextOnly') and text()='App']")
	public static WebElement applyButton;


	@FindBy(xpath="//button[contains(@id,'::lovDialogId::ok') or @title='OK']")
	public static WebElement searchOKBtn;

	@FindBy(xpath="//div[contains(@id,'effPnl:eCatPgCxAT:eCatPg')]/descendant::button[@title='OK']")
	public static WebElement searchOKBtn1;

	@FindBy(xpath="//input[contains(@id,'AP1:valueSetCodeId::content')]")
	public static WebElement valueSetName;

	@FindBy(xpath="//span[contains(@id,'AP1:it6::content')]")
	public static WebElement vsDescription;

	@FindBy(xpath="//select[contains(@id,'AP1:defaultTypeId::content')]")
	public static WebElement segmentDefaultType;	//Dropdown

	@FindBy(xpath="//input[contains(@id,'itDefValConstVc2::content')]")
	public static WebElement defaultValueText;

	@FindBy(xpath="//textarea[contains(@id,'itDefValSql::content')]")
	public static WebElement defaultValueSQL;

	@FindBy(xpath="//a[contains(@id,'AP1:cbShowExpressionBuilder')]")
	public static WebElement groovyExpBuilder;


	@FindBy(xpath="//a[contains(@id,'sub1:ces::i')]")
	public static WebElement groovyPanelSplitter;


	@FindBy(xpath="//a[contains(@id,'sub1:t2:0::di') and @title='Expand']")
	public static WebElement groovyFieldObject;


	@FindBy(xpath="//span[contains(@id,'sub1:t2:1:ot2') and contains(text(),'FlexSeg')]")
	public static WebElement groovyFieldChildObject;


	@FindBy(xpath="//a[contains(@id,'sub1:ps1::i')]")
	public static WebElement groovyFieldsVerticalPanelSplit;


	@FindBy(xpath="//div[contains(@id,'sub1:sdi2::ti')]//a[contains(@id,'sub1:sdi2::disAcr') and text()='Fields']")
	public static WebElement groovyExpBuilderFieldsLink;


	@FindBy(xpath="//span[contains(@id,'sub1:pc1:t3:0:ot6')]")
	public static WebElement groovyFieldsDisplayLabelCreatedBy;


	@FindBy(xpath="//button[contains(@id,'sub1:pc1:cb3')]")
	public static WebElement groovyFieldsInsertButton;


	@FindBy(xpath="//div[contains(@id,'sub1:checkSyntaxButton')]/a")
	public static WebElement groovyFieldsValidateLink;


	@FindBy(xpath="//button[contains(@id,'popupdialog1::ok')]")
	public static WebElement groovyExpBuilderOkButton;


	@FindBy(xpath="//div[contains(@id,'AP1:AT3:_ATp:create')]/a")
	public static WebElement groovyAddValidatorIcon;


	@FindBy(xpath="//input[contains(@id,'it5::content')]")
	public static WebElement validatorCode;


	@FindBy(xpath="//a[contains(@id,'ATt3:0:cbShowExpressionBuilder')]")
	public static WebElement validatorExpression;


	@FindBy(xpath="//input[contains(@id,'0:it8::content')]")
	public static WebElement validatorErrorMessage;


	@FindBy(xpath="//input[contains(@id,'0:it7::content')]")
	public static WebElement validatorDescription;


	@FindBy(xpath="//select[contains(@id,'AP1:displayTypeList::content')]")
	public static WebElement segmentdisplayType;	//Dropdown

	@FindBy(xpath="//input[contains(@id,'AP1:it15::content')]")
	public static WebElement segmentdisplaySize;	//TextBox

	@FindBy(xpath="//a[contains(@id,'AP1:valueSetCodeId::lovIconId')]")
	public static WebElement segmentVSselect;

	@FindBy(xpath="//div[contains(@id,'AP1:ctb3')]/a")
	public static WebElement viewValueSet;

	@FindBy(xpath="//*[contains(text(),'Edit Value Set')]")
	public static WebElement editValueSetHeader;

	@FindBy(xpath="//textarea[contains(@id,'it18::content')]")
	public static WebElement vsWhereClauseTextArea;

	@FindBy(xpath="//div[contains(@id,'pt1:AP1:valueSetCodeIdlovPopupId::content')]")
	public static WebElement vsPopupSearch;

	@FindBy(xpath="//input[contains(@id,'AP1:it1::content')]")
	public static WebElement segmentdisplayHeight;	//TextBox

	@FindBy(xpath="//div[contains(@id, 'pt1:AP1:createvset')]")
	public static WebElement createValueSetBtn;

	@FindBy(xpath="//*[contains(@class,'af_panelHeader_title-text0')]")
	public static WebElement pageLabel;

	@FindBy(xpath="//div[contains(@id,'_ATTp:effTT0::db')]")
	public static WebElement categoryHierarchyTable;

	@FindBy(xpath="//a[contains(@id,'ATT2:_ATTp:effTT0:0::di')]")
	public static WebElement expandRootItemParentLink;

	@FindBy(xpath="//div[contains(@id,'::_ttxt')]")
	public static WebElement selectAddContextHeader;

	@FindBy(xpath="//div[contains(@id,'ATTp:effTT0::scroller')]")
	public static WebElement categoryTableScroller;

	@FindBy(xpath="//table[contains(@summary,'contexts associated to selected page')]")
	public static WebElement contextAssociatedPageTable; 

	@FindBy(xpath="//img[contains(@id,'selDL::icon')]/parent::a")
	public static WebElement deleteContextFromPage;
	//--------------- Context Error Messages---------------------------

	@FindBy(xpath ="//div[@id='d1::msgDlg::_ttxt']")
	public static WebElement errorMessageHeader;

	//--------------------PIM Item Class-------------------------------
	@FindBy(xpath="//tbody//span[text()='Root Item Class']")
	public static WebElement description_RootItemClass;

	@FindBy(xpath="//a[@class='af_commandToolbarButton_link']//child::img[contains(@id,'ATTp:create::icon')]")
	public static WebElement createItemClassLinkIcon;

	@FindBy(xpath="//input[contains(@id,'applicationsTreeTable2:it4::content')]")
	public static WebElement itemClassName;

	@FindBy(xpath="//input[contains(@id,'applicationsTreeTable2:it6::content')]")
	public static WebElement itemInternalName;

	@FindBy(xpath="//textarea[contains(@id,'applicationsTreeTable2:it3::content') or contains(@id,'inputText2::content')]")
	public static WebElement itemDescriptionTextArea;

	@FindBy(xpath="//button[contains(@id,'applicationsTreeTable2:saveACBtn1')]")
	public static WebElement itemSaveAndCloseButton;

	@FindBy(xpath="//div[contains(@id,'applicationsTreeTable2:_ATTp::_ahCt')]")
	public static WebElement itemClassTreeView;

	@FindBy(xpath="//button[contains(@id,'cancelBtn1')]")
	public static WebElement itemCancelButton;

	@FindBy(xpath="//div[@class='af_panelTabbed_tab-content']/child::a[contains(@id,'sdi10::disAcr')]")
	public static WebElement  securityLink;

	@FindBy(xpath="//div[contains(@id,'sdi9::ti')]//a[contains(@id,'sdi9::disAcr')]")
	public static WebElement pagesAndAttributeGroupslink;

	@FindBy(xpath="//span[contains(@id,'np1::oc')]/child::a[text()='Attribute Groups']")
	public static WebElement attributeGroupslink;

	@FindBy(xpath="//table[@class='af_table_data-table af_table_data-table-VH-lines' and @summary='Item Class Hierarchy']")
	public static WebElement itemClassTable;

	@FindBy(xpath="//table[@summary='Item Class Hierarchy']//span[text()='Root Item Class']")
	public static WebElement rootItemClassRow;

	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement itemClassSearchNameInputbox;

	@FindBy(xpath="//span[text()='Item Class']//ancestor::table[contains(@id,'d2::t2')]")
	public static WebElement itemClassSearchResultsHeaderTable;

	@FindBy(xpath="//button[contains(@id,'applicationsTreeTable2:cb3')]")
	public static WebElement okButton_itemClassSearch;

	@FindBy(xpath="//div[contains(@id,'ap1:ctb1')]//a[@class='af_commandToolbarButton_link']")
	public static WebElement doneButton;

	@FindBy(xpath="//div[contains(@id,'ap1:cb1')]/a[@role='button']")
	public static WebElement itemClassSaveLink;

	@FindBy(xpath="//div[contains(@id,'ap1:ctb1')]/a")
	public static WebElement itemClassSaveAndCloseLink;

	@FindBy(xpath="//a[contains(@id,'np1:cni2')]")
	public static WebElement itemClassPagesLink;

	@FindBy(xpath="//button[contains(@id,'eCatPgBsv')]")
	public static WebElement pageCreateOkButton;

	@FindBy(xpath="//div[contains(@id,'_ATp:selDL')]/a")
	public static WebElement deleteContextFromPageLink;

	@FindBy(xpath="//div[contains(@id,'_ATp:eCatPgCxT')]")
	public static WebElement pageScroller;

	//--------------------------PIM Security--------------------------------------------------
	@FindBy(xpath="//div[@id='_FOd1::msgDlg']")
	public static WebElement warningMessage;

	@FindBy(xpath="//td[@id='_FOd1::msgDlg::_hse']")
	public static WebElement warningMessageHeader;

	@FindBy(xpath="//button[contains(@id,'::msgDlg::cancel')]")
	public static WebElement warningMessageOkButton;

	@FindBy (xpath="//div[contains(@id,'FOTsdiASE_ADMIN_WORKAREA::text')]")
	public static WebElement administrationLink;

	@FindBy (xpath="//button[contains(@id, 'aSpgt:cb1') and text()='Manage Database Resources']")
	public static WebElement manageDatbaseResources;

	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement objectName;

	@FindBy(xpath="//button[contains(@id,'qryId1::search')]")
	public static WebElement searchButton_ObjectName;

	@FindBy (xpath="//span[contains(@id,'dbResourceTbl:0:ot2')]")
	public static WebElement objectResultcell;

	@FindBy(xpath="//img[contains(@id,':appTbl:_ATp:edit::icon')]//parent::a")
	public static WebElement objectEditIcon;

	@FindBy(xpath="//div[contains(@id,'actionsTab::ti')]//a[contains(@id,'actionsTab::disAcr')]")
	public static WebElement actionLink;

	@FindBy(xpath="//div[contains(@id,'_ATp:create')]/a")
	public static WebElement addActionObjectLink;

	@FindBy(xpath="//div[contains(@id,'_ATp:actionTable::db')]/table[@class='af_table_data-table af_table_data-table-VH-lines']")
	public static WebElement objectsTable;

	@FindBy(xpath="//button[contains(@id,'gdb1:APsv')]")
	public static WebElement saveObjectsButton;

	@FindBy(xpath="//button[contains(@id,'gdb1:APsb2')]")
	public static WebElement submitPolicyObjectsButton;

	@FindBy(xpath="//div[@id='_FOd1::msgDlg::_ttxt' and text()='Confirmation']")
	public static WebElement confirmationDialogText;

	@FindBy(xpath="//button[@id='_FOd1::msgDlg::cancel']")
	public static WebElement okButton_ConfirmationDialog;

	@FindBy(xpath="//button[contains(@id,'pt1:gdb1:APb')]")
	public static WebElement doneButton_SecurityPolicy;

	@FindBy(xpath="//select[contains(@id,'soc2::content')]")
	public static WebElement viewPrivilegesDropDown;

	@FindBy(xpath="//select[contains(@id,'soc3::content')]")
	public static WebElement editPrivilegesDropDown;

	@FindBy(xpath="//div[contains(@id,'_ATp:create')]/a")
	public static WebElement addUserToSecurityLink;

	@FindBy(xpath="//select[contains(@id,'soc1::content')]")
	public static WebElement principalDropDown;

	@FindBy(xpath="//a[contains(@id,'granteeNameId::lovIconId')]")
	public static WebElement usernameLOVExpand;

	@FindBy(xpath="//a[contains(@id,'dropdownPopup::popupsearch')]")
	public static WebElement usernameSearchLink;

	@FindBy(xpath="//input[contains(@id,'_afrLovInternalQueryId:value00::content')]")
	public static WebElement usernameSearchInputText;

	@FindBy(xpath="//div[contains(@id,':_ATp:create')]/a")
	public static WebElement createActions;

	@FindBy(xpath="//div[contains(@id,':_ATp:ctb1')]/a")
	public static WebElement actionsSelctAndAdd;

	@FindBy(xpath="//a[contains(@id,'granteeNameId::lovIconId')]")
	public static WebElement expandUserNameLOV;

	@FindBy(xpath="//a[contains(@id,'organization::lovIconId')]")
	public static WebElement expandOrgLOV;

	@FindBy(xpath="//input[contains(@id,'qryId1:value00::content')]")
	public static WebElement actionsInputText;

	@FindBy(xpath="//span[contains(@id,'Principal PIMQA already exists')]")
	public static WebElement privilegeExistsErrorMessage;

	@FindBy(xpath="//select[contains(@id,'soc1::content')]")
	public static WebElement principalSelect;

	@FindBy(xpath="//div[contains(@id,'_ATp:delete')]/a")
	public static WebElement deleteRole;

	@FindBy(xpath="//input[contains(@id,'_t1_afr_c4::content')]")
	public static WebElement userNameFilter;

	@FindBy(xpath="//input[contains(@id,'_t1_afr_c12::content')]")
	public static WebElement itemClassFilterInputText;

	@FindBy(xpath="//input[contains(@id,'_t1_afr_c1::content')]")
	public static WebElement itemOrgFilter;

	@FindBy(xpath="//div[contains(@id,'_ATp:t1::db')]//span[contains(@id,'t1:0:granteeNameId')]/span[contains(@id,'0:granteeNameId::content')]")
	public static WebElement resultUser;

	@FindBy(xpath="//div[contains(@id,'_ATp:t1::db')]//span[contains(@id,'t1:0:organization')]/span[contains(@id,'0:organization::content')]")
	public static WebElement resultOrg;

	@FindBy(xpath="//div[contains(@id,'_ATp:t1::db')]//span[contains(@id,'_ATp:t1:0:ot5')]")
	public static WebElement resultItemClass;

	@FindBy(xpath="//div[contains(@id,'AT1:d3::_ttxt')]")
	public static WebElement deleteWarningMessage;

	@FindBy(xpath="//button[contains(@id,'d3::yes')]")
	public static WebElement yesButton_WarningMessage;


	//---------------------------------FSM Export Import------------------------------------------//

	@FindBy(xpath="//div[contains(@id,'_ATp:create') and @title='Create']/a")
	public static WebElement createProject;

	@FindBy(xpath="//input[contains(@id,'nameInput::content')]")
	public static WebElement projectNameInput;

	@FindBy(xpath="//label[text()='Status']")
	public static WebElement projectStatus;

	@FindBy(xpath="//a/span[text()='Save and Open Project']")
	public static WebElement saveOpenProject;

	@FindBy(xpath="//div[contains(@id,'_ATTp:create') and @role='presentation']/a")
	public static WebElement addTasks;

	@FindBy(xpath="//select[contains(@id,'value00::content') and @title='Offerings']")
	public static WebElement searchOfferings_DropDown;

	@FindBy(xpath="//input[contains(@id,'value10::content')]")
	public static WebElement taskName_inputBox;

	@FindBy(xpath="//button[contains(@id,'q1::search')]")
	public static WebElement taskName_searchButton;

	@FindBy(xpath="//span[text()='Manage Extensible Flexfields']")
	public static WebElement effTaskName_searchTable;

	@FindBy(xpath="//button[contains(@id,'applicationsTable1:applyBtn')]")
	public static WebElement applyButton_taskNameSearch;

	@FindBy(xpath="//button[contains(@id,'applicationsTable1:doneBtn')]")
	public static WebElement doneButton_taskNameSearch;

	@FindBy(xpath="//a[contains(@id,'cil2')]")
	public static WebElement goToTaskLink;

	@FindBy(xpath="//span[text()='Manage Extensible Flexfields']")
	public static WebElement effTask;

	@FindBy(xpath="//div[contains(@id,'pt1:AP1:APtb1')]//button[contains(@id,'pt1:AP1:APb')]")
	public static WebElement doneButton_effPage;

	@FindBy(xpath="//button[contains(@id,'topAppPanel:APb') or contains(@id,'resultAppPanel:APb') or contains(@id,'AP1:APb')]")
	public static WebElement doneButton_effTask;

	@FindBy(xpath="//div[contains(@id,'_ATp:edit')]/a")
	public static WebElement contextEditIcon;

	@FindBy(xpath="//img[contains(@id,'ffTab:0:i6')]")
	public static WebElement deploymentStatusIcon;

	@FindBy(xpath="//div[contains(@id,'implementationProjectAppTable1:_ATp:delete')]/a")
	public static WebElement deleteProjectLink;


	//--------------------------------------------HCM Run time Objects---------------------------------------//	
	@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_workforce_structures:0:MainAreaTab5::disAcr']")
	public static WebElement manageLocationsLink;

	@FindBy(xpath="//*[text()='Manage Locations']")
	public static WebElement headerTextManageLocations;

	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement locationsName;

	@FindBy(xpath="//input[contains(@id,'value10::content')]")
	public static WebElement locationsCode;

	@FindBy(xpath="//button[contains(@id,'locSearchPanel:q1::search')]")
	public static WebElement locationsSearchButton;

	@FindBy(xpath="//table[@class='af_table_data-table af_table_data-table-VH-lines' and contains(@summary,'Results in Table')]")
	public static WebElement searchLocationsResultsTable;

	@FindBy(xpath="//a[contains(@id,'commandToolbarButton1::popEl')]")
	public static WebElement editExpandlink;

	@FindBy(xpath="//td[contains(text(),'Update')]")
	public static WebElement updateButton;

	@FindBy(xpath="//button[contains(@id,'UpdDlg::ok')]")
	public static WebElement okButton_DialogBox;

	@FindBy(xpath="//button[contains(@id,'AP1:dialog3::ok')]")
	public static WebElement okButton_WarningDialogBox;

	@FindBy(xpath="//span[contains(text(),'Save')]")
	public static WebElement saveButton;

	@FindBy(xpath="//div[contains(@id,'tt1:submit')]//a[@class='af_commandToolbarButton_link']")
	public static WebElement submitButton;

	@FindBy(xpath="//button[contains(@id,'okWarningDialog')]")
	public static WebElement yes_WarningDialogBox;

	@FindBy(xpath="//button[contains(@id,'cancelWarningDialog')]")
	public static WebElement no_WarningDialogBox;

	@FindBy(xpath="//button[contains(@id,'okConfirmationDialog') or contains(@id,'addFieldsDialog::ok')]")
	public static WebElement okConfirmationDialog;

	@FindBy(xpath="//div[contains(@id,'clovInd_DisplayId::loadingIndicator')]")
	public static WebElement clovLoadingDisplayIndicator;

	//PIM Runtime Objects
	@FindBy(xpath="//*[contains(@class,'af_panelHeader_title-text0')]")
	public static WebElement headerPIM;

	@FindBy(xpath="//img[contains(@id,'FOTsdiItemRegionalArea::icon')]")
	public static WebElement expandTasks;

	@FindBy(xpath="//div[contains(@id,'FOTsdiItemRegionalArea::ti')]//a")
	public static WebElement pimPanelDrawer;

	@FindBy(xpath="//a[contains(@id,'0:_FOTRaT:0:RAtl1') and text()='Create Item']")
	public static WebElement createItemLink;

	@FindBy(xpath="//a[contains(@id,'0:_FOTRaT:0:RAtl2') and text()='Manage Items']")
	public static WebElement manageItemsLink;

	@FindBy(xpath="//input[contains(@id,'organizationDispId::content')]")
	public static WebElement orgName;

	@FindBy(xpath="//li[contains(@id,'organizationDispId::su0') and @data-afr-label='V1 Vision Operations']")
	public static WebElement org_V1;

	@FindBy(xpath="//input[contains(@id,'itemClassId::content')]")
	public static WebElement itemClassInput;

	@FindBy(xpath="//li[contains(@id,'itemClassId::su0')]")
	public static WebElement itemClassListItem;

	@FindBy(xpath="//button[contains(@id,'commandButton3')]")
	public static WebElement itemCreate_OkButton;

	@FindBy(xpath="//input[contains(@id,'inputText1::content')]")
	public static WebElement itemNameInputText;

	@FindBy(xpath="//textarea[contains(@id,'inputText2::content')]")
	public static WebElement itemRunTimeDescriptionTextArea;

	@FindBy(xpath="//div[@class='af_panelTabbed_tab-content']/child::a[contains(@id,'sspec::disAcr')]")
	public static WebElement specificationsLink;

	@FindBy(xpath="//*[contains(text(),'Item Revision')]")
	public static WebElement itemRevisionHeader;

	@FindBy(xpath="//div[@title='Item']//*[contains(@class,'af_showDetailHeader_title-text1')][contains(text(),'Item')]")
	public static WebElement itemHeader;

	@FindBy(xpath="//span[@class='af_commandToolbarButton_text'][contains(text(),'Save')]")
	public static WebElement itemSave;

	@FindBy(xpath="//a[contains(@id,'sassoc::disAcr')]")
	public static WebElement associationsLink;


	@FindBy(xpath="//a[contains(@id,'pt1:ap1:np4:cni15')]")
	public static WebElement supplierOrganizations;


	@FindBy(xpath="//div[contains(@id,'AT1asoc:_ATp:create')]/a")
	public static WebElement supplierOrgSelectAndAddIcon;


	@FindBy(xpath="//div[contains(@id,'dynam2:1:d1::_ttxt')]")
	public static WebElement supplierOrgAssociationsHeader;


	@FindBy(xpath="//div[contains(@id,'_ATp:t2assoc::db')]/table[@summary='Supplier Addresses']")
	public static WebElement supplierAddressesTable;


	@FindBy(xpath="//span[contains(@id,'_ATp:t2assoc:0:ot5')]")
	public static WebElement supplierAddressTableFirstRow;


	@FindBy(xpath="//button[contains(@id,'dynam2:1:saveBt1')]")
	public static WebElement supplierAddressApplyButton;


	@FindBy(xpath="//button[contains(@id,'dynam2:1:svACBt1')]")
	public static WebElement supplierAddressDoneButton;


	@FindBy(xpath="//span[contains(@id,'supt1:0:it3::content') and contains(text(),'COMPUTER SERVICES')]")
	public static WebElement supplierNameAdded;


	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement supplierName;


	@FindBy(xpath="//button[contains(@id,'q1::search')]")
	public static WebElement supplierSearchButton;

	@FindBy(xpath="//td[contains(@id,'csavebtn::popArea')]//a")
	public static WebElement itemSaveExpand;

	@FindBy(xpath="//tr[contains(@id,'ap1:saveandClose')]//span")
	public static WebElement itemSaveAndClose;

	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement itemNameSearchInput;

	@FindBy(xpath="//a[contains(@id,'inputComboboxListOfValues1::lovIconId')]")
	public static WebElement itemSearchExpandLink;

	@FindBy(xpath="//a[contains(@id,'dropdownPopup::popupsearch')]")
	public static WebElement searchLink;

	@FindBy(xpath="//input[contains(@id,'_afrLovInternalQueryId:value00::content')]")
	public static WebElement itemSearchInputText;

	@FindBy(xpath="//td[@class='af_query_button-cell']/child::button[contains(@id,'_afrLovInternalQueryId::search')]")
	public static WebElement itemSearchButton;

	/*@FindBy(xpath="//button[contains(@id,'lovDialogId::ok')]")
		public static WebElement searchOKBtn;*/

	@FindBy(xpath="//input[contains(@id,'inputComboboxListOfValues1::content')]")
	public static WebElement itemSearchInputComboBox;

	@FindBy(xpath="//button[contains(@id,'efqrp::search')]")
	public static WebElement searchButton_ManageItems;

	@FindBy(xpath="//button[contains(@id,'efqrp:addFldsCmd')]")
	public static WebElement addFieldsButton;

	@FindBy(xpath="//input[contains(@id,'afr__ATp_afr_t1_afr_c85::content')]")
	public static WebElement attributeGroupsInputText;

	@FindBy(xpath="//input[contains(@id,'afr__ATp_afr_t1_afr_c86::content')]")
	public static WebElement attributesInputText;

	@FindBy(xpath="//button[contains(@id,'simplePanel1:region2') and text()='Add']")
	public static WebElement addButton_SearchAttributes;

	@FindBy(xpath="//a[contains(@id,'efqrp::_afrDscl')]")
	public static WebElement advancedSearch;

	@FindBy(xpath="//div[contains(@id,'_ATp:create')]/a")
	public static WebElement addSegmentRow;

	@FindBy(xpath="//td[@tabindex='0']")
	public static WebElement selectCurrentDate;

	@FindBy(xpath="//button[contains(normalize-space(text()),'es')]")
	public static WebElement YesButton_DialogBox;
	
	//---------------------------------------Manage Administrator Profile Values----------------------------------------//
	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public static WebElement profileOptionCode;
	
	@FindBy(xpath="//button[contains(@id,'qryId1::search')]")
	public static WebElement profileSearchButton;
	
	@FindBy(xpath="//h3[contains(text(),'EGP_UPDATEABLE_ITEM')]")
	public static WebElement updateableItemHeader;
	
	@FindBy(xpath="//span[text()='Site']")
	public static WebElement profileLevelSite;
	
	@FindBy(xpath="//input[contains(@id,'it3::content')]")
	public static WebElement profileValue;
	
	@FindBy(xpath="//button[contains(@id,'AP2:APsv')]")
	public static WebElement profileSave;
	
	@FindBy(xpath="//button[contains(@id,'AP2:APscl')]")
	public static WebElement profileSaveAndClose;
}
