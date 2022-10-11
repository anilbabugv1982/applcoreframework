package pageDefinitions.UI.oracle.applcore.qa.Attachments;

/*
 * Author Ashish Pareek
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FileImportAndExportUIPage {

	public FileImportAndExportUIPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	WebElement ele;

	
	@FindBy(xpath = "//a[contains(@id,'itemNode_tools_file_import_and_export')]/span")
	public static WebElement fileImportExport;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_scheduled_processes_fuse_plus')]/span")
	public static WebElement scheduleProcess;

	@FindBy(xpath = "//img[contains(@id,'ATp:create::icon')]")
	public static WebElement uploadAttachment;

	@FindBy(xpath = "//input[contains(@id , 'if1::content')]")
	public static WebElement browseFile;
	/*
	 * @FindBy(xpath =
	 * "//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:soc4::content']")
	 * public static WebElement accountLOV;
	 * 
	 * @FindBy(xpath =
	 * "//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:soc4::content']")
	 * public static WebElement accountValue;
	 */
	
	@FindBy(xpath = "//select[contains(@name,'FOTsr1:0:pt1:AP1:AT1:soc4')]")
	public static WebElement accountField;

	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:AT1:FAsc1')]")
	public static WebElement saveAndClose;

	@FindBy(xpath = "//input[contains(@name,'FOTsr1:0:pt1:AP1:it5')]")
	public static WebElement fileField;
	
	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:search')]")
	public static WebElement searchAttachmetns;
	
	@FindBy(xpath = "//a[contains(@id,'pt1:AP1:AT1:_ATp:ATt1')]")
	public static WebElement fileLink;
	
	@FindBy(xpath = "//img[contains(@id,'pt1:AP1:AT1:_ATp:delete::icon')]")
	public static WebElement deleteFile;
	
	

	//@FindBy(xpath = "//td[@class='p_AFFocused af_column_row-header-cell']")
	//public static WebElement rowSelect;

	//@FindBy(xpath = "//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:_ATp:delete::icon']")
	//public static WebElement deleteRow;

	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:confirm')]")
	public static WebElement yes;
	
	//@FindBy(xpath = "//button[@title='Save and Continue']")
	//public static WebElement SaveandContinue;

}
