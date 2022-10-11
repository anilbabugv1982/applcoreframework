package pageDefinitions.UI.oracle.applcore.qa.Attachments;

//Author

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ServiceRequestPage {

	public ServiceRequestPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	WebElement ele;

	@FindBy(xpath = "//a[contains(@id,'nv_itemNode_sales_service_requests')]/span")
	public static WebElement serviceRequests;

	@FindBy(xpath="//button[contains(@id,'pt1:ls1:cb1')]")
	public static WebElement createServiceRequestsBtn;

	@FindBy(xpath = "//input[contains(@id,'pt1:it4')]")
	public static WebElement serviceRequestTitleField;

	//button[@title='Save and Continue']
	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:cb4')]")
	public static WebElement savandContinue;
	
	//a[@title='Manage Attachments']
	@FindBy(xpath = "//a[contains(@id,'addAttach2:cilAlFuAdd')]")
	public static WebElement addAttachements;

	@FindBy(xpath = "//button[contains(@id,'addAttach2:cbConfirm')]")
	public static WebElement okButton;

	@FindBy(xpath = "//input[contains(@id,'addAttach2:AlMgmtAVw:dciAdd:sfadd:ifAddFile::if')]")
	public static WebElement browse;

	@FindBy(xpath = "//a[contains(@id,'socAddType::drop')]")
	public static WebElement typeSelectDropdown;

	@FindBy(xpath = "//li[contains(text(),'URL')]")
	public static WebElement typeSelectURL;

	@FindBy(xpath = "//input[contains(@id,'itAddLink::content')]")
	public static WebElement URLTextFieldLink;
	
	@FindBy(xpath = "//input[contains(@id,'itAddTitle::content')]")
	public static WebElement URLTitleField;
	
	@FindBy(xpath = "//button[contains(text(),'Add')]")
	public static WebElement SRUrlAdd;

	@FindBy(xpath = "//button[contains(@id,'pt1:object-subtitle:cb5')]")
	public static WebElement saveandcloseSR;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_scheduled_processes_fuse_plus')]/span")
	public static WebElement scheduleProcess;
	
	@FindBy(xpath = "//input[contains(@id,'addAttach2:AlMgmtAVw:dciDtl:sfdtl:ifUpd::content')]")
	public static WebElement updateFile_browse;
	
	@FindBy(xpath = "//input[contains(@id,'addAttach2:AlMgmtAVw:dciDtl:sfdtl:itTitle::content')]")
	public static WebElement attachmentTitle;
	
	@FindBy(xpath = "//textarea[contains(@id,'addAttach2:AlMgmtAVw:dciDtl:sfdtl:itDesc::content')]")
	public static WebElement attachmentDescription;
	
	@FindBy(xpath = "//button[contains(@id,'addAttach2:AlMgmtAVw:dciDtl:sfdtl:cbDtlApp')]")
	public static WebElement updateDetailsBtn;
	
	@FindBy(xpath = "//button[contains(@id,'addAttach2:AlMgmtAVw:dciDtl:sfdtl:ifUpd::upBtn')]")
	public static WebElement updateFileBtn;
	
	@FindBy(xpath = "//input[contains(@id,'addAttach2:AlMgmtAVw:dciDtl:sfdtl:otUrl::content')]")
	public static WebElement updateURLField;
	
	@FindBy(xpath = "//img[contains(@id,'addAttach2:cilAlFuMan::icon')]")
	public static WebElement attachmentFolder;
	
	@FindBy(xpath = "//div[contains(@id,'addAttach2:dAlMgmt::_ccntr')]")
	public static WebElement attachmentDiagBox;
	
	@FindBy(xpath = "//div[contains(@id,'addAttach2:dAlMgmt::_ttxt')]")
	public static WebElement attachmentDiagBoxHeading;
	
	@FindBy(xpath = "//button[contains(@id,'cnlBtn')]")
	public static WebElement uploadCancelBtn;
	
	@FindBy(xpath = "//span[@class='af_progressIndicator_determinate-filled-icon-style']")
	public static WebElement fileUploadProcessBar;

//*******************************************************************************************************************	
//Similar xpath present in pageDefinitions.UI.oracle.applcore.qa.Attachments.FileImportAndExportUIPage file. Written here because pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper file using these from here.	
	@FindBy(xpath = "//input[contains(@id , 'if1::content')]")
	public static WebElement browseFile;
	
	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:search')]")
	public static WebElement searchAttachmetns;
	
	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:AT1:FAsc1')]")
	public static WebElement saveAndClose;
	
	@FindBy(xpath = "//a[contains(@id,'pt1:AP1:AT1:_ATp:ATt1')]")
	public static WebElement uploadLink;
	
//******************************************************************************************************************	
	
/*	
	@FindBy(xpath = "//a[contains(@id,'itemNode_tools_file_import_and_export')]/span")
	public static WebElement fileImportExport;

	@FindBy(xpath = "//img[@alt='Upload']")
	public static WebElement uploadAttachment;

	@FindBy(xpath = "//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:soc4::content']")
	public static WebElement accountLOV;

	@FindBy(xpath = "//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:soc4::content']")
	public static WebElement accountValue;

	@FindBy(xpath = "//td[@class='p_AFFocused af_column_row-header-cell']")
	public static WebElement rowSelect;

	@FindBy(xpath = "//img[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:AT1:_ATp:delete::icon']")
	public static WebElement deleteRow;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:confirm']")
	public static WebElement yes;

	@FindBy(xpath = "//div[contains(@id,'pt1:AP1:AT1:_ATp:ATt1::db')]/table/tbody/tr/td[2]/div/table/tbody/tr/td")
	public static WebElement uploadLink;
	
	@FindBy(xpath = "//button[@title='Save and Continue']")
	public static WebElement SaveandContinue;
*/
}
