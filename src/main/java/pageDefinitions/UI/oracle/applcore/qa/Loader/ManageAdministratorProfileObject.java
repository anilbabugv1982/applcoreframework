package pageDefinitions.UI.oracle.applcore.qa.Loader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageAdministratorProfileObject {

	private WebDriver driver;
	public ManageAdministratorProfileObject(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//div[contains(@id,'AP2:AT4:_ATp:ATm')]//ancestor::a[contains(text(),'Actions')]")
	public  WebElement actions;
	
	
	@FindBy(xpath="//td[contains(text(),'Import')]")
	public  WebElement import1;
	
	@FindBy(xpath="//select[contains(@id,'AP2:r1:0:soc1::content')]")
	public  WebElement account;
	
	@FindBy(xpath="//input[contains(@id,'AP2:r1:0:it2::content')]")
	public  WebElement profileValue;
	
	@FindBy(xpath="//span[contains(text(),'Upload')]")
	public  WebElement upload;
	
	@FindBy(xpath="//div[contains(text(),'Success') ]") // 
	public  WebElement sucess;
	
	By ok=By.xpath("//span[contains(text(),'OK')]");
	
	@FindBy(xpath="//*[contains(@id,'popupdialog1::close')]")
	public  WebElement popup;
	
	
@FindBy(xpath="//a[contains(text(),'Download Log file')]")
public  WebElement downloadLogFile;

@FindBy(xpath="//input[contains(@id,'AP2:qryId1:value00::content')]")
public  WebElement profileOptionCode;

@FindBy(xpath="//button[contains(text(),'Search')]")
public  WebElement search;

@FindBy(xpath="//input[contains(@id,'_AP2_afr_AT4_afr__ATp_afr_ATt4_afr_c5::content')]")
public WebElement userNameInput;

@FindBy(xpath="//input[contains(@id,'AP2_afr_AT4_afr__ATp_afr_ATt4_afr_c2::content')]")
public WebElement profileLevelInput;
}

