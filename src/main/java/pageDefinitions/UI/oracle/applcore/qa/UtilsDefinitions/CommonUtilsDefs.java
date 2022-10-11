package pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CommonUtilsDefs {

	public CommonUtilsDefs(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	/*
	 * Xpath definitions of methods in "CommonUtils" util class
	 */
	
	@FindBy(id = "userid")
	public WebElement username;

	@FindBy(name = "password")
	public WebElement password;

	@FindBy(id = "btnActive")
	public WebElement signIn;
	
	/*@FindBy(xpath = "//*[contains(@id,'UIScmil1u::icon')]")*/
	@FindBy(xpath = "//*[contains(@id,'pt1:_UIScmil2u') or contains(@id,'UIScmil1u::icon')]")
	public WebElement userLink;

	@FindBy(xpath = "//span[contains(@class , 'oj-fwk-icon')] | //*[@id='pt1:_UIScmil2u']")
	public WebElement vbUserLink;
	
	@FindBy(xpath = "//a[contains(text(),'Sign Out')]")
	public WebElement logOut;
	
	@FindBy(xpath = "//a[text() = 'Sign Out']")
	public WebElement vbLogOut;
	

	@FindBy(xpath = "//button[@id='Confirm']")
	public WebElement confirmButton;

	@FindBy(id = "_FOpt1:_UIScmil1u::icon")
	public WebElement userLink1;
	
	@FindBy(xpath="//span[contains(@id , '_atkNewsFeed')]")
	public WebElement newsFeed;
	
	/*
	 * Xpath definitions of "SetupAndMaintenance" util class
	 */
	@FindBy(xpath="//input[contains(@id,'it1::content')]")
	public WebElement searchField;

	@FindBy(xpath="//img[contains(@id,'ctb1::icon')]")
	public WebElement searchButton;
	
	@FindBy(xpath="//div[@title='Search']/descendant::img")
	public WebElement img_SearchTasks;
	
	//@FindBy(xpath="//img[@title='Tasks']")
	/*@FindBy(xpath="//img[@id = 'pt1:r1:0:r0:0:r1:0:sdi10::icon']")*/
	@FindBy(xpath="//img[contains(@id,'sdi10::icon')]")
	public WebElement panelDrawer;
	
	@FindBy(xpath="//a[text()='Search']")
	public WebElement searchLink;
	
//	@FindBy(xpath="//li[contains(@value,'1')]")
//	@FindBy(id="pt1:r1:0:r0:0:r1:0:r10:0:i3:0:cl11")
	@FindBy(xpath="//a[contains(@id,'i3:0:cl11')]")
	public WebElement implementationProject;
	
//	@FindBy(xpath="//li[contains(@value,'3')]")
//	@FindBy(id="pt1:r1:0:r0:0:r1:0:r10:0:i3:2:cl11")
	@FindBy(xpath="//a[contains(@id,'i3:2:cl11')]")
	public WebElement configurationProject;
	
	@FindBy(xpath="//input[contains(@id,'resultAppPanel:q1:value00::content')]")
	public WebElement ipSearchField;
	
	@FindBy(xpath="//button[contains(@id,'q1::search')]")
	public WebElement ipSearchBtn;
	
	@FindBy(xpath="//img[contains(@id,'ATp:create::icon')]")
	public WebElement ipCreateBtn;
	
	@FindBy(xpath="//input[contains(@id,'ap1:nameInput::content')]")
	public WebElement ipNameField;
	
	@FindBy(xpath="//textarea[contains(@id,'ap1:it1::content')]")
	public WebElement textAreaInput;
	
	@FindBy(xpath="//div[contains(@id,'ap1:ctb2')]")
	public WebElement saveOpenProjectBtn;
	
	@FindBy(xpath="//img[contains(@id,'applicationsTable1:_ATTp:create::icon')]")
	public WebElement taskAddBtn;
	
	@FindBy(xpath="//input[contains(@id,':applicationsTable1:q1:value00::content')]")
	public WebElement searchTaskinput;
	
	@FindBy(xpath="//select[contains(@id,'topAppPanel:applicationsTable1:q1:value00::content')]")
	public WebElement taskDropDown;
	
	@FindBy(xpath="//input[contains(@id,'topAppPanel:applicationsTable1:q1:value10::content')]")
	public WebElement taskNameField;
	
//	@FindBy(xpath="//button[contains(@id,'applicationsTable1:q1::search')]")
	@FindBy(xpath="//button[contains(@id,'search')]")
	public WebElement taskSearchBtn;
	
	@FindBy(xpath="//button[contains(@id,'applicationsTable1:applyBtn')]")
	public WebElement applyBtn;
	
	@FindBy(xpath="//button[contains(@id,'applicationsTable1:doneBtn')]")
	public WebElement taskDoneBtn;
	

	//@FindBy(xpath="//button[contains(@id,'topAppPanel:APb')]")
	@FindBy(xpath="//div[contains(@id,'topAppPanel:SPb')]")
	public WebElement ipDoneBtn;
	
	@FindBy(xpath="//a[contains(@id,'i3:2:cl11')]")
	public WebElement configPackageLink;
	
	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public WebElement configPkgSearchField;
	
	@FindBy(xpath="//input[contains(@id,':sourceImplProjectNameId::content')]")
	public WebElement configPkgSelectLOV;
	
	@FindBy(xpath="//a[contains(@id,'sourceImplProjectNameId::lovIconId')]")
	public WebElement configLOVBtn;
	
	@FindBy(linkText="Search...")
	public WebElement configSearchLink;
	
	@FindBy(xpath="//input[contains(@id,'_afrLovInternalQueryId:value00::content')]")
	public WebElement configNameSelectField;
	
	@FindBy(xpath="//button[contains(@id,'lovDialogId::ok')]")
	public WebElement okBtn;
	
	@FindBy(xpath="//button[contains(@id,':AP1:d1::yes')]")
	public WebElement configWrngYesBtn;
	
	@FindBy(xpath="//input[contains(@id,'AP1:cpnameText::content')]")
	public WebElement configTrackingName;
	
	
	//@FindBy(xpath="//button[contains(@id,':APsb2')]")
	@FindBy(xpath="//div[contains(@id,':SPsb2')]")
	public WebElement configsubmitBtn;
	
	//@FindBy(xpath="//button[contains(@id,'yes')]")
	@FindBy(xpath="//button[contains(@id,'Dialog::yes')]")
	public WebElement configYesBtn;
	
	@FindBy(xpath="//select[contains(@id,'requestTypeFilter::content')]")
	public WebElement typeDropDown;
	
	@FindBy(xpath="//li[text()='Export setup data']")
	public WebElement exportData;
	
	@FindBy(xpath="//li[text()='Import setup data']")
	public WebElement importData;
	
	@FindBy(xpath="//li[text()='Upload']")
	public WebElement uploadData;
	
	@FindBy(xpath="//*[text()='Completed successfully']")
	public WebElement expImpSuccessStatusIconText;
	
	@FindBy(xpath="//*[text()='Not started']")
	public WebElement notStartedIconText;
	
	@FindBy(xpath="//*[text()='In progress']")
	public WebElement inProgressIconText;
	
	@FindBy(xpath="//img[contains(@id,'refreshBtn::icon')]")
	public WebElement refreshIcon;
	
	//WebElements related to Label read
	@FindBy(xpath="//a[@id='pt1:_UIScmi99']")
	public WebElement aboutLink;
	
	@FindBy(xpath="//div[@id='pt1:_dialog::_ttxt']")
	public WebElement aboutHeader;
	
	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:ott4_1']")
	public WebElement currentBranchName;
	
	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:i1:0:ott22']")
	public WebElement currentADFLabel;
	
	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:i1:1:ott22']")
	public WebElement currentATGLabel;
	
	@FindBy(xpath="//span[@id='pt1:_UISatpr4:0:i1:1:ott23']")
	public WebElement currentDBPatchLabel;
	
	@FindBy(xpath="//a[@id='pt1:_dialog::close']")
	public WebElement closeAboutPopUp;
	
	@FindBy(xpath="//span[contains(@id,'AP1:AT2:_ATp:cppt') and substring(@id,string-length(@id) -string-length('t1') +1) = 't1']")
	public WebElement expImpStatus;
	
}