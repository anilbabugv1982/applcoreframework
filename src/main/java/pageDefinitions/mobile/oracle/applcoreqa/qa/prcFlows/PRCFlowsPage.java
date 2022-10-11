package pageDefinitions.mobile.oracle.applcoreqa.qa.prcFlows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PRCFlowsPage {
	
	public PRCFlowsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//span[(text() ='See More')]")
	public WebElement seeMore;
	
	@FindBy(xpath = "//input[@aria-label = 'Search']")
	public WebElement search;
	
	@FindBy(xpath = "//div[contains(@id, 'ssfs1_numOfResults')]//span")
	public WebElement count;
	
	@FindBy(xpath = "//a[@id = 'oj-tab-bar--527084515-1-tab-1-tab']")
	public WebElement hometab;
	
	@FindBy(xpath = "//button[contains(@aria-labelledby ,'_inFlowBack|text')]")
	public WebElement backbutton;
	
	@FindBy(xpath = "//a[@id = 'oj-tab-bar--527084515-1-tab-2-tab']")
	public WebElement searchtab;
	
	@FindBy(xpath = "//a[@title = 'Remove']")
	public WebElement remove;
	
	@FindBy(xpath = "//button//span[text() = 'Submit']")
	public WebElement submit;
	
	@FindBy(xpath = "//a[@id = 'oj-tab-bar--527084515-1-tab-3-tab']")
	public WebElement carttab;
	
	@FindBy(xpath = "//a[text()= 'Edit']")
	public WebElement edit;
	
	@FindBy(xpath = "//span[@title= 'Select Date.']")
	public WebElement date;
	
	@FindBy(xpath = "//a[text()= 'Enter an address']")
	public WebElement enteraddress;
	
	@FindBy(xpath = "//input[contains(@id, '_address_Address1|input')]")
	public WebElement address1;
	
	@FindBy(xpath = "//input[contains(@id, '_address_geography_City')]")
	public WebElement citydropdown;
	
	@FindBy(xpath = "//input[contains(@id, '_address_geography_PostalCode')]")
	public WebElement postalcode;
	
	@FindBy(xpath = "//a[text()= 'Enter charge account number']")
	public WebElement enterchargeaccount;
	
	@FindBy(xpath = "//input[@id= 'kffInputchargeAccountKFF|input']")
	public WebElement chargeaccount;
	
	@FindBy(xpath = "//span[text()= 'Update']")
	public WebElement update;
	
	@FindBy(xpath = "//li[contains(@id, 'featuredCategoriesList')]")
	public WebElement category;
	
	
	@FindBy(xpath = "//ul/li[@class = 'oj-listview-gridline-placeholder']")
	public WebElement enditem;
	
	@FindBy(xpath = "//div[contains(@id, 'ssfs1_numOfResults')]")
	public WebElement searchresult;
}
