package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IDCSPage {
	public IDCSPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[normalize-space(text())='Save']")
	public static WebElement Save;
	
	//@FindBy(xpath = "//button[contains(text(),'ave and Close')]")
	@FindBy(xpath = "//button[@accesskey='S']")
	public static WebElement SaveAndCloseBtn;
	
	@FindBy(xpath = "//input[@id='idcs-signin-basic-signin-form-username']")
	public static WebElement loginuserName;
	
	@FindBy(xpath = "//input[contains(@id,'idcs-signin-basic-signin-form-password')]")
	public static WebElement password;
	
	@FindBy(xpath = "//span[normalize-space(text())='Sign In']")
	public static WebElement signinButton;
	
	@FindBy(xpath = "//span[@class='oj-button-icon oj-start opaas-main-menu__icon']")
	public static WebElement navigator;
	
	@FindBy(xpath = "//span[@class='oj-navigationlist-item-label' and text()='Users']")
	public static WebElement usersTab;
	
	@FindBy(xpath = "//span[@class='oj-navigationlist-item-label' and text()='Groups']")
	public static WebElement groupsTab;
	
	@FindBy(xpath = "//h1[@class='idcs-page-header-title']")
	public static WebElement idcsPageHeader;
	
	@FindBy(xpath = "//input[@id='idcs-users-header-search-input|input']")
	public static WebElement usersSearch;
	
	@FindBy(xpath = "//input[@id='idcs-groups-header-search-input|input']")
	public static WebElement groupsSearch;
	
	//@FindBy(xpath = "//input[@id='primaryEmail']")
	//public static WebElement userEmail;
	
	@FindBy(xpath = "//label[text()='Federated']/following::div[@class='oj-switch-thumb']")
	public static WebElement federated;
	
	@FindBy(xpath = "//label[text()='Federated']/following::div[@class='oj-switch-thumb']")
	public static WebElement federatedStatus;
	
	@FindBy(xpath = "//span[@class='oj-tabbar-item-label' and text()='Groups']")
	public static WebElement groupsSection_UsersTab;
	
	@FindBy(xpath = "//input[contains(@id,'oj-inputsearch-input-my-groups-search-input')]")
	public static WebElement searchGroup_UsersTab;
	
	@FindBy(xpath = "//div[@class='idcs-spinning-wheel-background']")
	public static WebElement spinningWheel;
	
	@FindBy(xpath = "//span[@class='oj-tabs-title' and text()='Users']")
	public static WebElement usersSection_GroupsTab;
	
	@FindBy(xpath = "//input[contains(@id,'oj-inputsearch-input-member-search-input')]")
	public static WebElement searchUser_GroupsTab;
	
	@FindBy(xpath = "//a[@title='Go to the Applications page.']/span")
	public static WebElement goToAppsIcon;
	
	@FindBy(xpath = "//div[text()='ATGQAClient']")
	public static WebElement atgQAClientApplication;
	
	@FindBy(xpath = "//span[@class='oj-button-text' and text()='Add']")
	public static WebElement addApplicationBtn;
	
	@FindBy(xpath = "//span[normalize-space(text())='Enterprise Application']")
	public static WebElement enterpriseApplication;
	
	@FindBy(xpath = "//input[@id='idcs-app-management-details-app-name|input']")
	public static WebElement appNameField;
	
	@FindBy(xpath = "//textarea[@id='idcs-app-management-details-app-description|input']")
	public static WebElement appDescField;
	
	@FindBy(xpath = "//input[@id='idcs-app-management-details-landing-page-url|input']")
	public static WebElement appURLField;
	
	@FindBy(xpath = "//button[@id='idcs-app-management-add-app-train-next-button']")
	public static WebElement addAppNextBtn;
	
	@FindBy(xpath = "//span[@id='oj-collapsible-idcs-app-management-oauth-configuration-client-configuration-accordion-header']/a")
	public static WebElement clientConfiguration;
	
	@FindBy(xpath = "//input[@id='idcs-app-management-oauth-configuration-client-configuration-config-radio-configure|rb']")
	public static WebElement configAsClientRadioBtn;
	
	@FindBy(xpath = "//input[@value='client_credentials']")
	public static WebElement clientCredentialsCheckbox;
	
	@FindBy(xpath = "//span[@id='idcs-app-management-oauth-configuration-client-configuration-grant-app-role-add-button-icon']")
	public static WebElement AddGrantClientAccessIcon;
	
	@FindBy(xpath = "//input[@title='Select or deselect Identity Domain Administrator.']")
	public static WebElement IdentityDomainAdminCheckbox;
	
	@FindBy(xpath = "//input[@title='Select or deselect User Administrator.']")
	public static WebElement UserAdminCheckbox;
	
	@FindBy(xpath = "//input[@title='OK']")
	public static WebElement addAppRole_OkBtn;
	
	@FindBy(xpath = "//td[text()='Identity Domain Administrator']")
	public static WebElement IdentityDomainAdminRole;
	
	@FindBy(xpath = "//td[text()='User Administrator']")
	public static WebElement UserAdminRole;
	
	@FindBy(xpath = "//span[text()='Finish']")
	public static WebElement addAppFinishBtn;
	
	@FindBy(xpath = "//span[text()='Activate']")
	public static WebElement ActivateBtn;
	
	@FindBy(xpath = "//button[contains(@class,'oj-button-confirm')]/div/span[text()='OK']")
	public static WebElement activateApp_OkBtn;
	
	@FindBy(xpath = "//span[text()='OAuth Configuration']")
	public static WebElement OAuthConfigurationHeader;
	
	@FindBy(xpath = "//label[@id='idcs-app-management-oauth-configuration-general-information-client-id|label']")
	public static WebElement clientIDValue;
	
	@FindBy(xpath = "//span[text()='Show Secret']")
	public static WebElement showSecretBtn;
	
	@FindBy(xpath = "//div[@id='idcs-app-management-oauth-configuration-general-information-show-client-secret-dialog-dialog']/descendant::span[@class='idcs-font-semibold idcs-text-sm']")
	public static WebElement clientSecretValue;
	
	
	//div[@class='idcs-spinning-wheel-background']
	
	@FindBy(xpath = "//button[@title='Close']")
	public static WebElement clientSecretCloseBtn;
	
	@FindBy(xpath = "//input[@id='idcs-unauthenticated-reset-password-old-password|input']")
	public static WebElement oldPasswordField;
	
	@FindBy(xpath = "//input[@id='idcs-unauthenticated-reset-password-new-password|input']")
	public static WebElement newPasswordField;
	
	@FindBy(xpath = "//input[@id='idcs-unauthenticated-reset-password-confirm-password|input']")
	public static WebElement confirmNewPasswordField;
	
	@FindBy(xpath = "//div[@class='oj-button-label']/span[text()='Reset Password']")
	public static WebElement resetPasswordButton;

}
