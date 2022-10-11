package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author BIRSWAIN
 *
 */
public class ProfilePages {

	public ProfilePages(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	/*
	 * Below are the list of Web elements pertaining to the informations in Manage
	 * Profile Option Page
	 * 
	 */

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:it3::content\"]")
	public WebElement profileOptCode;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT1:_ATp:create::icon\"]")
	public WebElement profileOptAddBtn;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:it4::content\"]")
	public WebElement profileDspName;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:soc2::content\"]")
	public WebElement profileApplication;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:userModuleNameId::content\"]")
	public WebElement profileModule;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:it6::content\"]")
	public WebElement profileDescription;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:id1::content\"]")
	public WebElement profileStartDate;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:id1::glyph\"]")
	public WebElement startDateIcon;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:id1::pop::cd::ys::increment\"]")
	public WebElement startDateYearIncr;

	@FindBy(xpath = "//table[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:id1::pop::cd::cg\"]/tbody/tr[3]/td[3]")
	public WebElement startDateNumber;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:APscl\"]")
	public WebElement saveAndCloseBtn;

	@FindBy(xpath = "//label[@for='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT3:_ATp:ATt3:0:sbc3::content']")
	public WebElement siteEnableCheckBox;

	@FindBy(xpath = "//label[@for='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT3:_ATp:ATt3:0:sbc4::content']")
	public WebElement siteUpdatableCheckBox;

	@FindBy(xpath = "//label[@for='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT3:_ATp:ATt3:2:sbc3::content']")
	public WebElement userEnabledCheckBox;

	@FindBy(xpath = "//label[@for='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT3:_ATp:ATt3:2:sbc4::content']")
	public WebElement userUpdatableCheckBox;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:APsv\"]")
	public WebElement saveProfileOption;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:APscl\"]")
	public WebElement manageSaveAndCloseProfileOpt;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1::search\"]")
	public WebElement profileOptSearch;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1:value00::content\"]")
	public WebElement profileOptionField;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT1:_ATp:ATt1:0:it3::content\"]")
	public WebElement profileOptionFieldSavedValue;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT1:_ATp:ATt1:0:it4::content\"]")
	public WebElement profileDispNameSavedValue;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT1:_ATp:edit::icon\"]")
	public WebElement editProfileOption;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:APsv\"]")
	public WebElement saveEditProfileOption;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:APscl\"]")
	public WebElement saveAndCloseEditProfileOption;

	/*
	 * Below are the list of Web elements pertaining to the informations in Manage
	 * Profile Category Page
	 * 
	 */

	@FindBy(xpath = "//*[@id=\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:it1::content\']")
	public WebElement categoryCodeTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:it4::content']")
	public WebElement categoryNameTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:userModuleNameId::content']")
	public WebElement moduleTextField;

	@FindBy(xpath = "//*[@id=\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:it2::content\']")
	public WebElement descriptionTextField;

	@FindBy(xpath = "//*[@id=\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT5:_ATp:create::icon\']")
	public WebElement addButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:soc1::content']")
	public WebElement applicationTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:APsv']")
	public WebElement saveButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId2::search']")
	public WebElement searchButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:create']")
	public WebElement addProfileOptionButton;

	@FindBy(xpath = "//input[contains(@id,'qryId2:value00::content')]")
	public WebElement categoryTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:it3::content']")
	public WebElement displaySequenceTextField;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:inputComboboxListOfValues1::content\"]")
	public WebElement profileNameDropdown;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:inputComboboxListOfValues1::lovIconId']")
	public WebElement profileName;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:inputComboboxListOfValues1::dropdownPopup::popupsearch']")
	public WebElement profileSearch;

	@FindBy(xpath = "//*[contains(@id,'inputComboboxListOfValues1::_afrLovInternalQueryId:value10::content')]")
	public WebElement profileDisplayNameTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:inputComboboxListOfValues1::_afrLovInternalQueryId::search']")
	public WebElement searchOptoinButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:inputComboboxListOfValues1_afrLovInternalTableId::db']/table/tbody/tr/td[2]/div/table/tbody/tr/td")
	public WebElement profileDisplayName;

	@FindBy(xpath = "//*[@id=\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:inputComboboxListOfValues1::lovDialogId::ok\']")
	public WebElement okButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:APsv']")
	public WebElement saveprofileButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT5:_ATp:edit']")
	public WebElement editCategoryButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:APsv']")
	public WebElement updatedSaveButton;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT5:_ATp:ATt5:0:ot1']")
	public WebElement profileCategoryTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:ATt4:0:it3::content']")
	public WebElement profileDisplaySequenceTextField;

	@FindBy(xpath = "//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT4:_ATp:delete']")
	public WebElement profileOptionDeleteButton;

	@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP3:APscl\"]")
	public WebElement updatedSaveAndCloseButton;

	//@FindBy(xpath = "//*[@id=\"pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1:value00::content\"]")
	@FindBy(xpath = "//input[contains(@id,'pt1:AP1:qryId1:value00::content')]")
	public WebElement createdProfileOptionValue;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APscl')]")
	public WebElement saveAndCloseBtn1;
	
	@FindBy(xpath="//input[contains(@id,'qryId2:value10::content')]")
	public WebElement categoryNameSearchField;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:edit::icon')]")
	public WebElement categoryEditBtn;
	
	@FindBy(xpath="//input[contains(@name,'pt1:AP1:it2')]")
	public WebElement categoryDescField;
	
	@FindBy(xpath="//input[contains(@id,'inputComboboxListOfValues1::content')]")
	public WebElement catProfileNameInputField;
	
	// Manage admin profile web elements
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP2:qryId1:value00::content')]")
	public WebElement profileoptioncode;
	
	@FindBy(xpath = "//button[contains(@id , 'search')]")
	public  WebElement search;
	
	@FindBy(xpath = "//img[contains(@id , 'create')]")
	public  WebElement create;
	
	@FindBy(xpath = "//select[contains(@id , ':soc3::content')]")
	public  WebElement profilelevel;
	
	@FindBy(xpath = "//input[contains(@id , 'pt1:AP2:AT4:_ATp:ATt4:1:it3::content')]")
	public  WebElement profilevalue;
	
	@FindBy(xpath="//input[contains(@id,'userLevelValueId::content')]")
	public  WebElement adminUserNameInputField;
	
	@FindBy(xpath="//select[contains(@id,'soc2::content')]")
	public  WebElement adminUserNameInputFieldDD;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP2:APscl')]")
	public  WebElement saveandclose;
	
	@FindBy(xpath = "//a[text()= 'Manage Administrator Profile Values']")
	public  WebElement tasklink;
	
	@FindBy(xpath = "//img[contains(@id , 'delete')]")
	public  WebElement delete;
	
	@FindBy(xpath="//input[contains(@id,'ATp_afr_ATt4_afr_c5::content')]")
	public  WebElement qbeSearch;
	
	@FindBy(xpath="//input[contains(@id,'c2::content')]")
	public  WebElement qbeSearch1;
	
	@FindBy(xpath="//a[@title='Clear All']")
	public  WebElement clearAll;
	
	@FindBy(xpath="//button[contains(@id,'APsv')]")
	public  WebElement saveAdminBtn;
	
	
}
