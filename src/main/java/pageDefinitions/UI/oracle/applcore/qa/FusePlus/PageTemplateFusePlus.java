package pageDefinitions.UI.oracle.applcore.qa.FusePlus;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageTemplateFusePlus {

	public PageTemplateFusePlus(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	@FindBy(id ="pt1:_UISmmLink::icon")
	public static WebElement navigatorButton;

	@FindBy(id="pt1:nvi_itemNode_tools_setup_and_maintenance::icon")
	public static WebElement setupandmaintenance;

	@FindBy(id ="pt1:_UIShome")
	public static WebElement homeIcon;
	
	@FindBy(id = "pt1:_UISmmLink::icon")
	public static WebElement navigatorFusePlusButton;
	
	@FindBy(id = "pt1:_UISnvr:0:nv_itemNode_my_information_purchase_requisitions")
	public static WebElement purchaseRequisitions;
	
	
	//pt1:nvi_itemNode_my_information_purchase_requisitions
	
	@FindBy(id = "pt1:_UISnvr:0:nv_itemNode_procurement_negotiations")
	public static WebElement negotiations;
		
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsdiNegotiationWorkarea_FndTasksList::ti")
	public static WebElement rightTasksMenu;
		
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:RAtl4")  
	public static WebElement managePrograms;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:AT1:_ATp:create")
	public static WebElement createMP;
	
	//_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt2:0:AP1:ls1:AT1:_ATp:create
	
//	
//	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt4:0:AP1:it1::content")
//	public static WebElement programTitleMP;
	
//	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:0:AP1:it1::content")
//	public static WebElement programTitleMP;
	
//	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:0:AP1:id1::content")
//	public static WebElement dateMP;
			
//	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:0:AP1:APscl2")
//	public static WebElement saveAndCloseMP;		
//	
//	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_my_information_purchase_requisitions:0:_FOTsr1:0:SP4:r1:0:pt1:sf4:it3::content")
	//public static WebElement searchInputField;
	
	//@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:SP4:r1:0:pt1:sf4:it3::content.af_inputText_content")
	//public static WebElement searchInputField;
	
	@FindBy(xpath = "//input[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:SP4:r1:0:pt1:sf4:it3::content']")
	public static WebElement searchInputField;
	
	//_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:SP4:r1:0:pt1:sf4:it3::content.af_inputText_content
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:SP4:r1:0:pt1:sf4:pb2::content")
	public static WebElement searchButton;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:CLL_ot1")
	public static WebElement f1;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:CLL_ot3")
	public static WebElement f2;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:CLL_cil2")
	public static WebElement fNext;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:CLL_cil1")
	public static WebElement fPrevious;
		
	//@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_my_information_purchase_requisitions:0:_FOTsr1:1:SP1:call2:CLL_ctb2")
	//public static WebElement listViewButton;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:CLL_ctb2")
	public static WebElement listViewButton;
	
	@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:AT1:_ATp:t1::db']/table")
	public static WebElement listViewTable;
	
	//@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_my_information_purchase_requisitions:0:_FOTsr1:1:SP1:call2:CLL_ctb1")
	//public static WebElement cardViewButton;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:SP1:call2:CLL_ctb1")
	public static WebElement cardViewButton;
	
	@FindBy(id = "_FOpt1:_UIShome")
	public static WebElement homeButton;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:0:AP1:id1::glyph")
	public static WebElement conversionDate;
	
	//@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt3:0:AP1:it1::content'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt4:0:AP1:it1::content'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt5:0:AP1:it1::content'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt6:0:AP1:it1::content']")
	//public static WebElement programTitleMP;
	
	@ FindBy(xpath ="//*[contains(@id, 'AP1:it1::content')]")
	public static WebElement programTitleMP;
	
	//@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt3:0:AP1:id1::content'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt4:0:AP1:id1::content'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt5:0:AP1:id1::content'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt6:0:AP1:id1::content']")
	//public static WebElement dateMP;
	
	@ FindBy(xpath ="//*[contains(@id, 'AP1:id1::content')]")
	public static WebElement dateMP;
	
//	@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt3:0:AP1:APscl2'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt4:0:AP1:APscl2'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt5:0:AP1:APscl2'] | //*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt6:0:AP1:APscl2']")
//	public static WebElement saveAndCloseMP;
	
	@ FindBy(xpath ="//*[contains(@id, 'AP1:APscl2')]")
	public static WebElement saveAndCloseMP;
	
	@FindBy(id = "pt1:nvi_itemNode_tools_user_interface_text")
	public static WebElement userInterfaceText;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSSFB")
	public static WebElement showFilters;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQry:value00::content")
	public static WebElement title;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSFiltersSave")
	public static WebElement save;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQry::SSDlg_name::content")
	public static WebElement savedSearchName;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQry::SSDlg::ok")
	public static WebElement ok;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQrySS::content")
	public static WebElement savedSeachDropDown;	
	
	@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:AT1:_ATp:ATt1::db']/table")
	public static WebElement resultTable;
	
	@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSPersTab::db']/table")
	public static WebElement managedSavedSearchTable;
	
	@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSPersTab:0:_LSPersTabDel']")
	public static WebElement deleteSavedSearchButton;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSPersBtnAply")
	public static WebElement managedSavedSeachApplyButton;	
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSPersBtnOk")
	public static WebElement managedSavedSeachOkButton;
	
	@FindBy(xpath = "//input[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSSF::content']")
	public static WebElement searchSavedProgram;
	
	@FindBy(id = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSSB")
	public static WebElement manageProgramsSearchButton;
	
//	@FindBy(id =pt1:_UISnvr:0:nv_itemNode_my_information_purchase_requisitions
			
			@FindBy(id="pt1:_UISnvr:0:nvgpgl1_groupNode_procurement")
	public static WebElement expandProcureButton;

}
