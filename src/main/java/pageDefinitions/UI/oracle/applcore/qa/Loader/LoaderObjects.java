package pageDefinitions.UI.oracle.applcore.qa.Loader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoaderObjects {
    private WebDriver driver;

    public LoaderObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@id,'AP1:AT1:_ATp:ATm')]//div//table//tbody//tr//td[2]//a[contains(text(),'Actions')]")
    public WebElement action;

    @FindBy(xpath = "//td[text()='Import']")
    public WebElement Import;

    @FindBy(xpath = "//td[text()='Import Related Values']")
    public WebElement ImportRelatedValues;

    @FindBy(xpath = "//select[contains(@id,'AP1:r1:0:soc1::content')]")
    public WebElement account;

    @FindBy(xpath = "//*[contains(text(),'Lookup Type File')]//..//..//td[2]//input")
    public WebElement lookupType;

    @FindBy(xpath = "//*[contains(text(),'Lookup Code File')]//..//..//td[2]//input")
    public WebElement lookupCode;

    @FindBy(xpath = "//span[contains(text(),'Upload')]")
    public WebElement upload;


    @FindBy(xpath = "//div[contains(@id,'AP1:r2:0:cb1')]//ancestor::span[contains(text(),'Upload')]")
    public WebElement upload2;

    @FindBy(xpath = "//div[contains(@id,'pt1:AP1:r1:0:cb1')]//span[contains(text(),'Upload')]")
    public WebElement upload1;

    @FindBy(xpath = "//div[contains(text(),'Success') and contains(@id,'AP1:r2:0:d3::_ttxt') ]") //
    public WebElement sucess;

    @FindBy(xpath = "//div[contains(text(),'Success') ]") //
    public WebElement sucess1;

    @FindBy(xpath = "//a[contains(text(),'Download Log file')]")
    public WebElement downloadLogFile;

    @FindBy(xpath = "//a[contains(@id,':r2:0:cl5')]")
    public WebElement downloadLogFile1;

    By ok2 = By.xpath("//div[contains(@id,'AP1:r2:0:cb3')]");

    By ok = By.xpath("//span[contains(text(),'OK')]");

    @FindBy(xpath = "//*[contains(@id,'popupdialog1::close')]")
    public WebElement popup;

    @FindBy(xpath = "//*[contains(@id,'AP1:r1:0:it1::content')]")
    public WebElement fileName;

    @FindBy(xpath = "//div[contains(text(),'Success')]")
    public WebElement successPopUp;

    @FindBy(xpath = "//div[contains(text(),'Error')]")
    public WebElement failurePopUp;

    @FindBy(xpath = "//div[contains(@id,'_afrProgressBarID')]")
    public WebElement progressBar;

    @FindBy(xpath = "//div[contains(@id,'d3::_ttxt')]")
    public WebElement genericProcessTag;


}
