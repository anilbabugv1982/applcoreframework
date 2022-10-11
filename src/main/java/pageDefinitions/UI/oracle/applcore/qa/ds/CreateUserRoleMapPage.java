package pageDefinitions.UI.oracle.applcore.qa.ds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Ashish Pareek
 *
 */

public class CreateUserRoleMapPage {
	
	public CreateUserRoleMapPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[normalize-space(text())='Save']")
	public static WebElement Save;
	
	@FindBy(xpath = "//button[contains(text(),'ave and Close')]")
	public static WebElement SaveAndCloseBtn;
	
	@FindBy(xpath = "//button[@title='Next']")
	public static WebElement NextButton;
	
	@FindBy(xpath = "//div[@class='p_AFWarning af_dialog af_messages_dialog']")
	public static WebElement securityConsoleRolesWarningBox;
	
	@FindBy(xpath = "//div[@class='p_AFWarning af_dialog af_messages_dialog']/descendant::button[normalize-space(text())='OK']")
	public static WebElement securityConsoleRolesOkButton;
	
	@FindBy(xpath = "//div[@class='af_panelTabbed_tab-content']/descendant::div[normalize-space(text())='Users']")
	public static WebElement usersTab;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add User Account']")
	public static WebElement addUserAccountButton;
	
	@FindBy(xpath = "//label[normalize-space(text())='First Name']/following::input[contains(@id,'FirstName')]")
	public static WebElement userFirstName;
	
	@FindBy(xpath = "//label[normalize-space(text())='Last Name']/following::input[contains(@id,'LastName')]")
	public static WebElement userLastName;
	
	@FindBy(xpath = "//label[normalize-space(text())='Email']/following::input[contains(@id,'Email')]")
	public static WebElement userEmail;
	
	@FindBy(xpath = "//label[normalize-space(text())='User Name']/following::input[contains(@id,'UsrLoginAdd')]")
	public static WebElement userUserName;
	
	@FindBy(xpath = "//label[normalize-space(text())='Password']/following::input[contains(@id,'Pass')]")
	public static WebElement userPassword;
	
	@FindBy(xpath = "//label[normalize-space(text())='Confirm Password']/following::input[contains(@id,'ConfPass')]")
	public static WebElement userConfirmPassword;
	
	@FindBy(xpath = "//div[@class='af_panelTabbed_tab-content']/descendant::div[normalize-space(text())='Roles']")
	public static WebElement rolesTab;
	
	@FindBy(xpath = "//button[normalize-space(text())='Create Role']")
	public static WebElement createRoleButton;
	
	@FindBy(xpath = "//label[normalize-space(text())='Role Name']/following::input[contains(@id,'RNam')]")
	public static WebElement roleName;
	
	@FindBy(xpath = "//label[normalize-space(text())='Role Code']/following::input[contains(@id,'RCod')]")
	public static WebElement roleCode;
	
	@FindBy(xpath = "//label[normalize-space(text())='Role Category']/following::input[contains(@id,'RCat')]")
	public static WebElement roleCategory;
	
	@FindBy(xpath = "//span[normalize-space(text())='Add Function Security Policy']")
	public static WebElement addFunctionSecurityPolicyButton;
	
	/*@FindBy(xpath = "//div[@class='af_dialog_title' and normalize-space(text())='Add Function Security Policy']")
	public static WebElement addFunctionSecurityPolicyDialogTitle; */
	
	@FindBy(xpath = "//input[contains(@placeholder,'Enter 3 or more characters to search')]")
	public static WebElement addFunctionSecurityPolicy_Search;
	
	@FindBy(xpath = "//div[@class='FndSearchSuggestItemTextContainer']/descendant::span[@class='FndSearchSuggestItemHilight']")
	public static WebElement addFunctionSecurityPolicy_SearchResult;
	
	@FindBy(xpath = "//label[@class='af_panelLabelAndMessage_label-text' and normalize-space(text())='Name']")
	public static WebElement addFunctionSecurityPolicy_SearchResult_NameContent;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add Selected Privileges']")
	public static WebElement addSelectedPrivilegesButton;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add Selected Privileges']/following-sibling::button[@title='Cancel']")
	public static WebElement addFunctionSecurityPolicy_CancelButton;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add User']")
	public static WebElement addUserButton;
	
	@FindBy(xpath = "//input[@placeholder='Enter 3 or more characters to search']")
	public static WebElement addUser_Search;
	
	@FindBy(xpath = "//div[@class='FndSearchSuggestItemTextContainer']/descendant::div/span[not(text())]/preceding-sibling::span[@class='FndSearchSuggestDescriptionHilight']")
	public static WebElement addUser_SearchResultFull;
	
	@FindBy(xpath = "//div[@class='FndSearchSuggestItemTextContainer']/descendant::span[@class='FndSearchSuggestItemHilight']")
	public static WebElement addUser_SearchResultPart;	
	
	@FindBy(xpath = "//label[@class='af_panelLabelAndMessage_label-text' and normalize-space(text())='User Login']")
	public static WebElement addUser_SearchResult_UserLoginContent;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add User to Role']")
	public static WebElement addUserToRoleButton;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add User to Role']/following-sibling::button[@title='Cancel']")
	public static WebElement addUser_CancelButton;
	
	@FindBy(xpath = "//div[@class='af_dialog_title' and normalize-space(text())='Confirmation']")
	public static WebElement role_ConfirmationPopUp;
	
	@FindBy(xpath = "//div[@class='af_dialog_title' and normalize-space(text())='Confirmation']/following::button[normalize-space(text())='OK']")
	public static WebElement role_ConfirmationPopUpOKButton;
	
	@FindBy(xpath = "//input[contains(@id,'sp1:f1:it1::content')]")
	public static WebElement user_SearchBox;
	
	@FindBy(xpath = "//img[@title='Search']")
	public static WebElement user_SearchButton;
	
	@FindBy(xpath = "//button[normalize-space(text())='Edit']")
	public static WebElement editUserButton;
	
	@FindBy(xpath = "//h1[normalize-space(text())='User Information']")
	public static WebElement userInformation;
	
	@FindBy(xpath = "//label[normalize-space(text())='Active']/preceding-sibling::input")
	public static WebElement activeCheckbox;
	
	@FindBy(xpath = "//label[normalize-space(text())='Active']")
	public static WebElement activeCheckboxLabel;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add Role']")
	public static WebElement addRoleButton;
	
	@FindBy(xpath = "//div[contains(normalize-space(text()),'Add Role Membership')]")
	public static WebElement addRoleMembershipPopUp;
	
	@FindBy(xpath = "//input[contains(@id,'sp1:urSrcBx::content')]")
	public static WebElement Role_SearchBox;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add Role Membership']")
	public static WebElement addRoleMembershipButton;
	
	@FindBy(xpath = "//button[@title='Done']")
	public static WebElement addRoleMembershipDoneButton;
	
	@FindBy(xpath = "//input[contains(@id,'sp1:srchBox::content')]")
	public static WebElement role_SearchBox;
	
	//@FindBy(xpath = "//button[@title='Actions']")
	//public static WebElement roleActionDropdown;
	
	@FindBy(xpath = "//td[normalize-space(text())='Edit Role']")
	public static WebElement editRoleOption;
	
	@FindBy(xpath = "//div[@class='af_train_stop-content']/a[normalize-space(text())='Users']")
	public static WebElement usersStep;
	
	@FindBy(xpath = "//textarea[contains(@id,'Des::content')]")
	public static WebElement roleDescriptionArea;
	
	@FindBy(xpath = "//div[@class='af_train_stop-content']/a[normalize-space(text())='Summary']")
	public static WebElement summaryStep;
	
	@FindBy(xpath = "//button[@accesskey='o']")
	public static WebElement DoneButton;
	
	@FindBy(xpath = "//body[@style='cursor: wait;']")
	public static WebElement waitCursor;
	
	@FindBy(xpath = "//a[contains(@id,'sp1:sDir::drop')]")
	public static WebElement expandTowardDropdown;
	
	@FindBy(xpath = "//ul[contains(@id,':sp1:sDir::pop')]/li[normalize-space(text())='Users']")
	public static WebElement expandTowardAsUsers;
	
	@FindBy(xpath = "//a[contains(@id,'sp1:soc1::drop')]")
	public static WebElement showDropdown;
	
	@FindBy(xpath = "//ul[contains(@id,'sp1:soc1::pop')]/li[normalize-space(text())='Users']")
	public static WebElement showAsUsers;
	
	@FindBy(xpath = "//input[contains(@id,'afr_uncol::content')]")
	public static WebElement userLoginFilter;
	
	@FindBy(xpath = "//div[normalize-space(text())='No data to display.']")
	public static WebElement noDataToDisplay;
	

}
