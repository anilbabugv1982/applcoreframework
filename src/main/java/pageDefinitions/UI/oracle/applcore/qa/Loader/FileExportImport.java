package pageDefinitions.UI.oracle.applcore.qa.Loader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FileExportImport {
	
	private WebDriver driver;
	public FileExportImport(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//select[contains(@id,'pt1:AP1:AT1:soc4::content')]")
	public  WebElement account;
		
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:it5::content')]")
	public  WebElement file;
	
	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:search')]")
	public WebElement searchAttachments;

	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:search') and contains(@class,'p_AFDisabled p_AFBusy af')]")
	public WebElement searchButtonDisabled;
	
	By saveandClose= By.xpath("//button[contains(@id,'itemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:FAsc1')]");
	
	@FindBy(xpath = "//img[contains(@id,'AP1:AT1:_ATp:create::icon')]")
	public WebElement uploadAttachment;
}
