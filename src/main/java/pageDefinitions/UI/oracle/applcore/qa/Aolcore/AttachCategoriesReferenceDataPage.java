package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AttachCategoriesReferenceDataPage {
	
	public AttachCategoriesReferenceDataPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	WebElement ele;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT9:_ATp:create::icon")
	public WebElement AttachCreateIcon;
	
	@FindBy(name="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT9:pit1")
	public WebElement AttachCatName;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT9:pit2::content")
	public WebElement AttachCatUsernme;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT9:pUserModuleNameId::content")
	public WebElement AttachModName;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT9:pit3::content")
	public WebElement AttachDesc;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT9:FAok1")
	public WebElement AttachCatOkBtn;
	
	@FindBy(xpath = "//button[text()='Save']")
	public WebElement AttachCatSaveBtn;
	
	@FindBy(id ="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT1:_ATp:create::icon")
	public WebElement AttachEntCreateIcon;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT1:pit3::content")
	public WebElement AttachEntName;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT1:pit4::content")
	public WebElement AttachEntUserEntName;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT1:userModuleNameId::content")
	public WebElement AttachEntModName;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT1:pit1::content")
	public WebElement AttachEntDbResource;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT1:pit2::content")
	public WebElement AttachEntTblName;
	
	@FindBy(xpath = "//label[contains(@id,'pt1:ap1:AT1:psbc1::Label1')]")
	public WebElement AttachEntEnblSecurity;
	
	@FindBy(xpath = "//button[contains(@id,'pt1:ap1:AT1:FAok1')]")
	public WebElement AttachEntOkBtn;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT10:_ATp:create::icon")
	public WebElement AttachEntCatAdd;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT10:qryId2:value00::content")
	public WebElement AttachEntNameMapp;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT10:qryId2::search")
	public WebElement AttachEntSearch;
	
	
	@FindBy(xpath = "(.//*[normalize-space(text()) and normalize-space(.)='User Entity Name'])[3]/following::td[3]")
	public WebElement AttachEntSeltion;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:AT10:FAsc4")
	public WebElement AttachEntSelectionOkBtn;
	
	@FindBy(id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:ap1:APscl")
	public WebElement AttachEntSaveAndCloseBtn;

}
