package pageDefinitions.UI.oracle.applcore.qa.FusePlus;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GuidedLearning {

	public GuidedLearning(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath ="//span[contains(text(),'To add or edit Guided Learning configuration, you must be in a sandbox that supports Guided Learning.')]")
	public static WebElement errorMsg;
	
	@FindBy(xpath ="//span[contains(text(),'Sandboxes')]")
	public static WebElement sandBox;
	
	@FindBy(xpath ="//button[contains(text(),'Create Sandbox')]")
	public static WebElement createSandBox;
	
	@FindBy(xpath ="//input[contains(@id,'r1:0:_csit1::content')]")
	public static WebElement sandboxName;
	
	@FindBy(xpath ="//*[contains(@id,'r1:0:_csit2::content')]")
	public static WebElement sandboxdiscription;
	
	@FindBy(xpath ="//span[text()='Guided Learning']//..//..//..//..//..//..//td[1]//span//span//span//label")
	public static WebElement glCheckbox;
	
	@FindBy(xpath ="//button[contains(text(),'Create and Enter')]")
	public static WebElement createAndEnter;
	
	@FindBy(xpath ="//a[contains(text(),'Tools')]//..//..//td[3]//div")
	public static WebElement tools;
	
	@FindBy(xpath ="//td[contains(text(),'Guided Learning')]")
	public static WebElement glboxtext;
	
	@FindBy(xpath ="//td[contains(text(),'Sandbox Detail')]")
	public static WebElement sbDetails;
	
	@FindBy(xpath ="//button[contains(text(),'Done')]")
	public static WebElement done;
	
	@FindBy(xpath ="//span[text()='Guided Learning']")
	public static WebElement gLText;
	
	@FindBy(xpath ="//span[text()='Save']")
	public static WebElement save;
	
	@FindBy(xpath ="//select[contains(@id,':AP2:AT4:_ATp:ATt4')]")
	public static WebElement profileValue;
	
	@FindBy(xpath ="//input[contains(@id,':AP2:AT4:_ATp:ATt4')]")
	public static WebElement profileValue1;
	
	@FindBy(xpath ="//button[text()='Save']")
	public static WebElement save2;
	
	@FindBy(xpath ="//button[contains(@id,'AP2:APscl')]")
	public static WebElement saveAndClose;
	
	@FindBy(xpath ="//input[contains(@id,'SP1:it3::content')]")
	public static WebElement applicationId;
	
	@FindBy(xpath ="//button[contains(@id,'AP2:APscl')]")
	public static WebElement env;
	
	@FindBy(xpath ="//label[contains(text(),'Development')]")
	public static WebElement dev;
	
	@FindBy(xpath ="//label[contains(text(),'Production')]")
	public static WebElement prod;
	
	@FindBy(xpath ="//div[contains(@id,'SP1:APscl2')]")
	public static WebElement saveAndClose2;
	
	 @FindBy(xpath = "//a[contains(@id,'pt1:_UIShome')]")
     public static WebElement homeIcon;
	 
	 @FindBy(xpath = "//div[contains(text(),'G u i d e d')]")
     public static WebElement glWidgets;
	 
	 @FindBy(xpath = "//div[contains(text(),'G u i d e d')]//..")
     public static WebElement glWidgets2;
	 
	 @FindBy(xpath = "//div[contains(text(),'G u i d e d')]//..//..//..")
     public static WebElement glWidgets3;
	 
	 @FindBy(xpath = "//div[contains(text(),'H E L P')]")
     public static WebElement HelpglWidgets;
	 
	 @FindBy(xpath = "//span[contains(text(),'Personal Information')]")
     public static WebElement personalInfo;
	 
	 @FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:lp1Upl:UPsp1:SPpgl21']")
     public static WebElement back;
	 
	 @FindBy(xpath = "//*[@id='_FOpt1:_UIShome']")
     public static WebElement homeIcon3;
	 
	 @FindBy(xpath = "//*[@id='_FOpt1:_UIShome'] | //a[contains(@id,'pt1:_UIShome')]")
     public static WebElement home;
	 
	 @FindBy(xpath = "//span[contains(text(),'Update Phone Number (Employee)')]")
     public static WebElement updatePhone;
	 
	 @FindBy(xpath = "//span[contains(text(),'Update Address (Employee)')]")
     public static WebElement updateAddress;
	 
	 @FindBy(xpath = "//span[contains(text(),'View Compensation Information')]")
     public static WebElement viewCompensation;
	 
	 @FindBy(xpath = "//span[contains(text(),'View a Team Member's Documents')]")
     public static WebElement viewTeamMemberDoc;
	 
	 @FindBy(xpath = "//strong[text()='Me']")
     public static WebElement clickMePopUp;
	 
	 @FindBy(xpath = "//a[text()='Me']")
     public static WebElement me;
	 
	 @FindBy(xpath = "//a[text()='My Team']")
     public static WebElement myTeam;
	 
	 @FindBy(xpath = "//strong[text()='My Team.']")
     public static WebElement myTeamPopUp;
	 
	 @FindBy(xpath = "//div[@id='PER_HCMPEOPLETOP_FUSE_MY_TEAM']")
     public static WebElement selectMyTeam;
	 
	 @FindBy(xpath = "//strong[contains(text(),'Personal Information')]")
     public static WebElement personalInformationPopUp;
	 
	 @FindBy(xpath = "//a[text()='Personal Information']")
     public static WebElement personalInformation;
	 
	 @FindBy(xpath = "//strong[text()='Contact Information']")
     public static WebElement contactInformationPopUp;
	 
	 @FindBy(xpath = "//span[text()='Contact Info']")
     public static WebElement contactInformation;
	 
	 @FindBy(xpath = "//h2[text()='Communication']")
     public static WebElement contactInfoPage;
	 
	 @FindBy(xpath = "//button[contains(text(),'Back')]")
     public static WebElement backOnPopUp;
	 
	 @FindBy(xpath = "//span[contains(text(),'Click')]")
	 public static WebElement clickPopUp;
	 
	 @FindBy(xpath = "//p[contains(text(),'Use this area to review and update your Phone number.')]")
	 public static WebElement updatePhonePopUP;
	 
	 @FindBy(xpath = "//p[contains(text(),'Use this area to review and update your Address information.')]")
	 public static WebElement updateAddressPopUP;
	 
	 @FindBy(xpath = "//a[contains(text(),'Next')]")
	 public static WebElement nextOnPopUp;
	 
	 @FindBy(xpath = "//*[contains(@id,'commRgn:0:commPce:PCEcil1::icon')]")
	 public static WebElement addPhone;
	 
	 @FindBy(xpath = "//p[contains(text(),'Select')]")
	 public static WebElement select;
	 
	 @FindBy(xpath = "//td[contains(text(),'Phone Details')]")
	 public static WebElement phoneDetails;
	 
	 @FindBy(xpath = "//p[contains(text(),'Use this area to enter a new Phone number.')]")
	 public static WebElement phonePopUp;
	 
	 @FindBy(xpath = "//div[contains(@id,'commRgn:0:commPce:phonPce:phoneLv:3:phnePse:PSEcb3')]")
	 public static WebElement cancelPhone;
	 
	 @FindBy(xpath = "//p[contains(text(),'This completes the guide')]")
	 public static WebElement completeGuide;
	 
	 @FindBy(xpath = "//a[contains(text(),'Done')]")
	 public static WebElement doneOnPOpUp;
	 
	 @FindBy(xpath = "//div[contains(@id,'addrRgn:0:addrPce:PCEcil1')]")
	 public static WebElement addAddress;
	 
	 @FindBy(xpath = "//p[contains(text(),'Use this area to enter a new address')]")
	 public static WebElement addressPopUp;
	 
	 
	 @FindBy(xpath = "//span[contains(text(),'Ensure that one address is selected as your primary address.')]")
	 public static WebElement primaryChcek;
	 
	 @FindBy(xpath = "//span[text()='C']")
	 public static WebElement cancelAddress;
	 
	 @FindBy(xpath = "//strong[contains(text(),'Family and Emergency')]")
     public static WebElement fandEPopUp;
	 
	 @FindBy(xpath = "//span[contains(text(),'Family and Emergency Contacts')]")
     public static WebElement fandEContact;
	 
	 @FindBy(xpath = "//strong[contains(text(),'My Contacts.')]")
     public static WebElement myContactPopUP;
	 
	 @FindBy(xpath = "//div[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:r1:0:_sdctb1')]")
     public static WebElement actions;
	 
	 @FindBy(xpath = "//td[(text()='Delete')]")
     public static WebElement delete;
	 
	 @FindBy(xpath = "//button[contains(@id,'_sdd2::yes')]")
     public static WebElement yes;
	 
	 @FindBy(xpath = "//div[contains(@id,'UISsbb:0:r1:0:_sbm2')]")
     public static WebElement tooldropdown;
	 
	 @FindBy(xpath = "//img[@title='New'] | //div[contains(@id,'SP1:applicationsTable1:_ATp:create')]")
     public static WebElement addRole;
	 
	 @FindBy(xpath = "//input[contains(@id,'applicationsTable1:_ATp:ATt2')]")
     public static WebElement firstAppRole;
	 
	 @FindBy(xpath = "//select[contains(@id,'applicationsTable1:_ATp:ATt2')]")
     public static WebElement firstGlRole;
	 
	 @FindBy(xpath = "//div[contains(text(),'Interactive Guides')]//..//ul//li")
     public static WebElement Guidelist;
	 
	 @FindBy(xpath = "//span[contains(text(),'AUTO_GHR_EMP2_2TMA')]")
	 public static WebElement employee;
	 
	 @FindBy(xpath = "//div[contains(@id,'applicationsTable1:_ATp:delete')]")
	 public static WebElement deleteRole;
	 
	 @FindBy(xpath = "//button[contains(@id,'applicationsTable1:confirm')]")
	 public static WebElement yesPopup;
	 
	 @FindBy(xpath = "//input[contains(@id,'applicationsTable1:_ATp:ATt2:0:it1::content')]")
	 public static WebElement role;
	 
	 @FindBy(xpath = "//td[contains(text(),'Leave Sandbox')]")
	 public static WebElement leaveSandbox;
	 
	 @FindBy(xpath = "//tr[contains(@id,'pt1:_UISsbb:0:r1:0:_sbcmi4')]//td[2]")
	 public static WebElement publish;
	 
	 @FindBy(xpath = "//td//button[contains(@title,'Publish')]")
	 public static WebElement publish2;
	 
	 @FindBy(xpath = "//button[contains(text(),'Continue to Publish')]")
	 public static WebElement continueToPublish;
	 
	 @FindBy(xpath = "//button[contains(@id,'_sbd2::ok')]")
	 public static WebElement ok;
	 
	 @FindBy(xpath = "//button[contains(@id,'r1:0:_sbcb1')]")
	 public static WebElement ok2;
}
