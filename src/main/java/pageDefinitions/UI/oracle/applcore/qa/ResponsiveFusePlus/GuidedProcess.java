package pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus;

import org.openqa.selenium.By;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GuidedProcess {
	
	public GuidedProcess(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath=".//a[text()='My Team']")
	public static WebElement myTeamLink;
	
	@FindBy(xpath=".//a[contains(text(),'Promote') and contains(@target,'my_team_promote')]")
	public static WebElement promoteLink;
	
	@FindBy(xpath=".//a[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:0:hp01Upl:UPsp1:hp01Pce:hp01Lv:0:hp01Pse:hp02Cl')and contains(text(),'AUTO_GHR_')]")
	public static WebElement firstDirectReport;
	
	@FindBy(xpath=".//span[contains(text(),'Direct Reports')]")
	public static WebElement checkboxDirectReports;
	
	@FindBy(xpath=".//span[contains(text(),'Comments and Attachments')]")
	public static WebElement checkboxCommentsAttachments;
	
	@FindBy(xpath=".//div[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:gp1Upl:UPsp1:SPsb2']/a/span[text()='Contin']")
	public static WebElement buttonContinue;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:r1Rgn:0:GPcb10') and contains(text(),'Contin')]")
	public static WebElement whenWhyContinueButton;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:r1Rgn:0:GPcil10::icon']/../span[text()='Edit']")
	public static WebElement whenWhyEdit;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:r1Rgn:0:GPcb11') and contains(text(),'Contin')]")
	public static WebElement promotionContinueButton;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:r1Rgn:0:GPcil11::icon']/../span[text()='Edit']")
	public static WebElement promotionEdit;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:r1Rgn:0:GPcb12') and contains(text(),'Contin')]")
	public static WebElement directReportContinueButton;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:r1Rgn:0:GPcil12::icon']/../span[text()='Edit']")
	public static WebElement directReportEdit;
	
	@FindBy(xpath=".//div[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:SPsb2']/a/span[text()='Sub']")
	public static WebElement buttonSubmit;
	
	@FindBy(xpath=".//div[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:SPc']/a/span[text()='ancel']")
	public static WebElement buttonCancel;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_UIShome'] | //a[contains(@id,'pt1:_UIShome')]")
	public static WebElement iconHome;
	
	@FindBy(xpath=".//a[contains(text(),'Transfer') and contains(@target,'my_team_transfer')]")
	public static WebElement transferLink;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:gpRgn:0:GPcb10') and contains(text(),'Contin')]")
	public static WebElement whenWhyContinueButtonTransfer;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:gpRgn:0:GPcil10::icon']/../span[text()='Edit']")
	public static WebElement whenWhyEditTransfer;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:gpRgn:0:GPcb11') and contains(text(),'Contin')]")
	public static WebElement transferContinueButton;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:gpRgn:0:GPcil11::icon']/../span[text()='Edit']")
	public static WebElement transferEdit;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:gpRgn:0:GPcb12') and contains(text(),'Contin')]")
	public static WebElement directReportContinueButtonTransfer;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:up1Upl:UPsp1:gpRgn:0:GPcil12::icon']/../span[text()='Edit']")
	public static WebElement directReportEditTransfer;
	
	@FindBy(xpath=".//div/a[(@id='showmore_groupNode_manager_resources') and (text()='Show More')]")
	public static WebElement myTeamShowMoreLink;
	
	@FindBy(xpath=".//div[@class='atk-col-3']/div[@target='my_team_terminate_responsive']/div/a[text()='Termination']")
	public static WebElement terminationLink;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:up1Upl:UPsp1:gpRgn:0:GPcb10') and contains(text(),'Contin')]")
	public static WebElement whenWhyContinueButtonTermination;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:up1Upl:UPsp1:gpRgn:0:GPcil10::icon']/../span[text()='Edit']")
	public static WebElement whenWhyEditTermination;
	
	@FindBy(xpath=".//div/button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:up1Upl:UPsp1:gpRgn:0:GPcb11') and contains(text(),'Contin')]")
	public static WebElement terminationContinueButton;
	
	@FindBy(xpath=".//*[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:up1Upl:UPsp1:gpRgn:0:GPcil11::icon']/../span[text()='Edit']")
	public static WebElement terminationEdit;
	
	@FindBy(xpath=".//div[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:up1Upl:UPsp1:SPsb2']/a/span[text()='Sub']")
	public static WebElement buttonSubmitTermination;
	
	@FindBy(xpath=".//div[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:up1Upl:UPsp1:SPc']/a/span[text()='ancel']")
	public static WebElement buttonCancelTermination;
	
	@FindBy(xpath=".//a[text()='Me']")
	public static WebElement me;
	
	@FindBy(xpath = "//button[text()='ave and Close']")
	public WebElement saveCloseBtn;
}
