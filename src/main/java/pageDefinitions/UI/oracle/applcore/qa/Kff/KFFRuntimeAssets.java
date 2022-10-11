package pageDefinitions.UI.oracle.applcore.qa.Kff;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KFFRuntimeAssets {

	private WebDriver driver;
	
	public KFFRuntimeAssets(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
//	public  WebDriverWait wait = new WebDriverWait(driver, 50);
	@FindBy(id="pt1:_UISmmLink::icon")
	public  WebElement navigatorButton;
		
	@FindBy(id="pt1:_UISnvr:0:nv_itemNode_fixed_assets_additions::icon")
	public  WebElement assetsIcon;

	@FindBy(xpath="//h1[contains(text(),'Assets')]")
	public  WebElement assetsHeader;
	
	@FindBy(xpath="//a[@title='Actions']")
	public  WebElement actionsButton;
	
	@FindBy(xpath="//img[contains(@title,'Tasks')]")
	public  WebElement tasksButton;
	
	
	@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:RAtl1']")
	public  WebElement addAssetLink;	
	
	@FindBy(xpath="//td/div[contains(text(),'Add Asset')]")
	public  WebElement addAssetPopup;

	@FindBy(xpath="//img[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1KBIMG::icon']")
	public  WebElement categoryKFFIcon;	
	
	@FindBy(xpath="//td/div[contains(text(),'Category')]")
	public  WebElement categortyPopup;
	
	
	@FindBy(xpath="//input[contains(@id, 'FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1CS::content')]")
	public  WebElement categoryInputText;
	
	
	
	@FindBy(xpath="//input[contains(@id, '_FONSr2:0:_FOTRaT:0:dynam1:0:kf1SPOP_query:value00::content')]")
	public  WebElement majorInputText;	
	
	@FindBy(xpath="//input[contains(@id, '_FONSr2:0:_FOTRaT:0:dynam1:0:kf1SPOP_query:value10::content')]")
	public  WebElement minorInputText;
	
	@FindBy(xpath="//button[contains(text(),'Next')]")
	public  WebElement nextButton;	
	
	@FindBy(xpath="//button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:cb1')]")
	public  WebElement cancelAddAssetButton;
	
	
	public  WebElement verifyCreatedSegmentPrompt(String timestamp){
		 
		WebElement element = driver.findElement(By.xpath("//span/label[contains(text(),'segmentPrompt"+timestamp+"')]/../input"));
	    return element;	 
	}

	public  WebElement CreatedSegmentPrompt;
	
	@FindBy(xpath="//button[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1CNL')]")
	public  WebElement cancelButton;

	@FindBy(xpath="//input[contains(@id, '_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1SPOP_query:value00::content')]")
	public  WebElement majorCategorySegmentInput;
	
	@FindBy(xpath="//input[contains(@id, '_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1SPOP_query:value10::content')]")
	public  WebElement minorCategorySegmentInput;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1SEl")
	public  WebElement okButton;
	
	@FindBy(xpath="//button[contains(@id, '_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:dynam1:0:kf1QRBT')]")
	public  WebElement searchButton;
	
	
	@FindBy(xpath="//span[contains(text(),'Major Category')]/../../../../../colgroup")
	public  WebElement getSegmentCount;
	
	
	public  void waitForAssetPageTobeLoaded() {
		KFFUtils.waitForElementToBeVisible(assetsHeader,driver);
		//KFFUtils.waitForElementToBeVisible(actionsButton,driver);
		KFFUtils.waitForElementToBeClickable(tasksButton,driver);
	}
	
	public  void waitForAddAssetPopupTobePresent() {
		KFFUtils.waitForElementToBeVisible(nextButton,driver);
		KFFUtils.waitForElementToBeClickable(categoryKFFIcon,driver);
	}
	
}
