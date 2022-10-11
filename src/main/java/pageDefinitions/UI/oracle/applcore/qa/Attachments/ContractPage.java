package pageDefinitions.UI.oracle.applcore.qa.Attachments;

//Author

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

public class ContractPage {
	
	public ContractPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	
	
	
	@FindBy(xpath="//a[contains(@id,'nv_itemNode_contract_management_contracts')]/span")
	public static WebElement contracts;

	
	//@FindBy(xpath = "//a[contains(text() , 'Actions')]")
	//public  static WebElement action;
	
	@FindBy(xpath = "//div[contains(@id , 'applicationsTable1A:_ATp:create')]/a")
	public  static WebElement create;
	
	@FindBy(xpath = "//a[contains(@id , 'contractTypeId::lovIconId')]")
	public  static WebElement typedropdown;
	
	@FindBy(xpath = "//tr[@class ='af_table_data-row p_AFSelected']/descendant::input[contains(@id,'TitleInputText::content')]")
	public  static WebElement attachmentTitle;
//*******************************************************************************************************	
//Ashish Pareek	
	@FindBy(xpath = "//select[contains(@id,'okc_docs_at1:applicationsTable:_ATp:attachmentTable') and contains(@id,'dCode::content')]")
	public  static WebElement contractDocumentsTypeSelect;
	
	@FindBy(xpath = "//input[contains(@id,'okc_docs_at1:applicationsTable:_ATp:attachmentTable') and contains(@id,'desktopFile::content')]")
	public  static WebElement contractDocumentsChooseFile;
	
	@FindBy(xpath = "//div[contains(@id,'HomeApplicationPanel:DocAu1:1:sdh1')]/descendant::button[contains(@id,'desktopFile::upBtn')]")
	public  static WebElement contractDocumentsUpdateBtn;
	
	@FindBy(xpath = "//input[contains(@id,'okc_docs_at1:applicationsTable:_ATp:attachmentTable') and contains(@id,'itnewurl::content')]")
	public  static WebElement contractDocumentsUrlField;
	
	@FindBy(xpath = "//div[contains(@id,'HomeApplicationPanel:DocAu1:1:sdh1')]/descendant::input[contains(@id,'TitleInputText::content')]")
	public  static WebElement contractDocumentsTitle;
	
	@FindBy(xpath = "//button[contains(@id,'desktopFile::dlgCont::ok')]")
	public  static WebElement updateFileOkBtn;
	
	@FindBy(xpath = "//div[contains(@id,'HomeApplicationPanel:DocAu1:1:sdh1')]/descendant::img[contains(@id,'delete::icon')]")
	public  static WebElement contractDocumentDeleteIcon;
	
	@FindBy(xpath = "//button[contains(@id,'confirm')]")
	public  static WebElement warningYesBtn;
	
	@FindBy(xpath = "//select[contains(@id,'okc_docs_at3:applicationsTable:_ATp:attachmentTable') and contains(@id,'dCode::content')]")
	public  static WebElement supportingDocumentsTypeSelect;
	
	@FindBy(xpath = "//input[contains(@id,'okc_docs_at3:applicationsTable:_ATp:attachmentTable') and contains(@id,'desktopFile::content')]")
	public  static WebElement supportingDocumentsChooseFile;
	
	@FindBy(xpath = "//div[contains(@id,'HomeApplicationPanel:DocAu1:1:sdh2')]/descendant::button[contains(@id,'desktopFile::upBtn')]")
	public  static WebElement supportingDocumentsUpdateBtn;
	
	@FindBy(xpath = "//input[contains(@id,'okc_docs_at3:applicationsTable:_ATp:attachmentTable') and contains(@id,'itnewurl::content')]")
	public  static WebElement supportingDocumentsUrlField;
	
	@FindBy(xpath = "//div[contains(@id,'HomeApplicationPanel:DocAu1:1:sdh2')]/descendant::input[contains(@id,'TitleInputText::content')]")
	public  static WebElement supportingDocumentsTitle;
	
	@FindBy(xpath = "//div[contains(@id,'HomeApplicationPanel:DocAu1:1:sdh2')]/descendant::img[contains(@id,'delete::icon')]")
	public  static WebElement supportingDocumentDeleteIcon;
	
	
//******************************************************************************************************	
	@FindBy(xpath = "//a[contains(@id , 'contractTypeId::dropdownPopup::popupsearch')]")
	public  static WebElement searchlink;
	
	@FindBy(xpath = "//a[contains(@id , ':partyNameId::lovIconId')]")
	public  static WebElement Primarypartydropdon;
	
	@FindBy(xpath = "//a[contains(@id , 'partyNameId::dropdownPopup::popupsearch')]")
	public  static WebElement primarypartysearchlink;
	
	
	@FindBy(xpath = "//input[contains(@id , 'contractTypeId::_afrLovInternalQueryId:value00::content')]")
	public  static WebElement name;

	@FindBy(xpath = "//input[contains(@id , 'partyNameId::_afrLovInternalQueryId:value00::content')]")
	public  static WebElement partyname;
	
	@FindBy(xpath = "//button[contains(@id , 'contractTypeId::_afrLovInternalQueryId::search')]")
	public  static WebElement searchbutton;
	
	@FindBy(xpath = "//button[contains(@id , 'partyNameId::_afrLovInternalQueryId::search')]")
	public  static WebElement partysearchbutton;
	
	@FindBy(xpath = "//span[contains(text(),'Items and Services')]")
	public  static WebElement typerow;
	
	@FindBy(xpath = "//span[text() = 'World of Business']")
	public  static WebElement partytyperow;
	
	@FindBy(xpath = "//button[contains(@id , 'contractTypeId::lovDialogId::ok')]")
	public  static WebElement ok;
	
	@FindBy(xpath = "//button[contains(@id , 'partyNameId::lovDialogId::ok')]")
	public  static WebElement partyok;

	
	@FindBy(xpath = "//input[contains(@id , ':inputText1::content')]")
	public  static WebElement number;
	
	@FindBy(xpath = "//input[contains(@id , ':partyNameId::content')]")
	public  static WebElement primaryparty;
	
	@FindBy(xpath = "//button[contains(text() , 'Save and Continue')]")
	public  static WebElement SaveandContinue;
	
	@FindBy(xpath = "//a[contains(text() , 'Documents')]")
	public  static WebElement Documents;
	
	@FindBy(xpath = "//span[contains(text() , 'Save')]")
	public  static WebElement Save;
	
	@FindBy(xpath = "//div[contains(@id, 'okc_docs_at1:applicationsTable:_ATp:create')]//a")
	public  static WebElement createcontractDocuments;
	
	
	@FindBy(xpath = "//div[contains(@id, 'okc_docs_at3:applicationsTable:_ATp:create')]//a")
	public  static WebElement createsupportcontractDocuments;
	
	@FindBy(xpath = "//input[contains(@id, 'TitleInputText::content')]")
	public  static WebElement titlecontract;	
	
	@FindBy(xpath="//h1[text()='Contract Documents']")
	public static WebElement headerContractDoc;
	
	
	
	
		
	
}
