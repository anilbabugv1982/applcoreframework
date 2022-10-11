package pageDefinitions.UI.oracle.applcore.qa.Dff;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageValuesPage {

	public ManageValuesPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	WebElement fieldElement;
	
	@FindBy(xpath="//a[text()='Setup and Maintenance']")
	public WebElement taskListManagerUI;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP2:manvals') and @onclick]")
	public WebElement ManageValuesnavigate;
	
	/* Xpaths to search for existing independant value of the VSCode */
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value00::content')]")
	public WebElement ManageValuesValueSearchEle;
	
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value10::content')]")
	public WebElement ManageValuesDescSearchEle;
	
	/* xpaths to create new value */
	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]")
	public WebElement CharValueEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot15::content')]")
	public WebElement NumberValueEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot25::content')]")
	public WebElement DateValueEle;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:ot11::content')]")
	public WebElement DateValueEle1;
	
	@FindBy(xpath = "//input[contains(@id , 'ot35::content')]")
	public WebElement DateTimeValueEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot1::content')]")
	public WebElement CharTranslationTextEle;
	
/*	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot7::content')]")
	public WebElement DescEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::label[contains(@for, 'sbc1::content')]/descendant::div")
	public WebElement EnabledEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot3::content')]")
	public WebElement StartDateEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot2::content')]")
	public WebElement EndDateEle;
	
	@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot9::content')]")
	public WebElement SortOrderEle; */
	
	/* xpaths for UI elements */
	@FindBy(xpath = "//img[contains(@id , '_ATp:create::icon')]")
	public WebElement CreateValueEle;
	
	@FindBy(xpath = "//img[contains(@id , '_ATp:delete::icon')]")
	public WebElement DeleteValueEle;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:APsv')]")
	public WebElement SaveTransEle;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:APscl')]")
	public WebElement SaveAndCloseTransEle;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:APc')]")
	public WebElement CancelTransEle;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1::search')]")
	public WebElement InitiateSearchValueEle;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1::reset')]")
	public WebElement ResetSearchEle;
	
	/* xptahs for assigning Independant value while creating new value for dependant valueset code */
	//@FindBy(xpath = "//input[contains(@id , 'ot5::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot4::lovIconId')]")
	//public WebElement CreateLovIcon;
	
	@FindBy(xpath = "//a[contains(@class , 'af_inputComboboxListOfValues_search')]")
	public WebElement CreateLovSearchEle;
	
//	@FindBy(xpath = "//input[contains(@id , 'ot4::_afrLovInternalQueryId:value00::content')]") // Kumar
	@FindBy(xpath = "//input[contains(@id , '::_afrLovInternalQueryId:value00::content')]")
	public WebElement CreateIndSetValueEle;
	
//	@FindBy(xpath = "//button[contains(@id , 'ot4::_afrLovInternalQueryId::search')]") //Kumar
	@FindBy(xpath = "//button[contains(@id , '::_afrLovInternalQueryId::search')]") 
	public WebElement CreateInitiateIndValueSearch;
	
//	@FindBy(xpath = "//button[contains(@id , 'ot4::lovDialogId::ok')]") //Kumar
	@FindBy(xpath = "//button[contains(@id , '::lovDialogId::ok')]")
	public WebElement CreateIndValSearchOK;
	
//	@FindBy(xpath = "//div[contains(@id , 'ot4_afrLovInternalTableId::db')]/descendant::table[@_rowcount > 0]/descendant::tr[@_afrrk = '0']/descendant::table") //Kumar
	@FindBy(xpath = "//div[contains(@id , '_afrLovInternalTableId::db')]/descendant::table[@_rowcount > 0]/descendant::tr[@_afrrk = '0']/descendant::table")
	public WebElement CreatSearchResult;
	
//	@FindBy(xpath = "//div[contains(@id , 'ot4_afrLovInternalTableId::db')]/descendant::table[@_rowcount > 0]") //Kumar
	@FindBy(xpath = "//div[contains(@id , '_afrLovInternalTableId::db')]/descendant::table[@_rowcount > 0]")
	public WebElement CreatSearchResultWait;
	
	/* xptahs for Independant value search for dependant valueset code */
	@FindBy(xpath = "//a[contains(@id , 'AP1:qryId1:value20::lovIconId')]")
	public WebElement SearchLovIcon;
	
	@FindBy(xpath = "//a[contains(@id , 'AP1:qryId1:value20::dropdownPopup::popupsearch')]")
	public WebElement LovSearchEle;
	
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value20::_afrLovInternalQueryId:value00::content')]")
	public WebElement IndSetValueEle;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1:value20::_afrLovInternalQueryId::search')]")
	public WebElement InitiateIndValueSearch;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1:value20::lovDialogId::ok')]")
	public WebElement IndValSearchOK;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:qryId1:value20_afrLovInternalTableId::db')]/descendant::table[@_rowcount > 0]/descendant::tr[@_afrrk = '0']/descendant::table")
	public WebElement SearchResult;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:qryId1:value20_afrLovInternalTableId::db')]/descendant::table[@_rowcount > 0]")
	public WebElement SearchResultWait;
	
	
	/*
	 * SubSet values page elements
	 * 
	 */
	@FindBy(xpath="//button[text()='Search']")
	public WebElement searchBtn;	
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:qryId1::reset')]")
	public WebElement resetBtn;
	
	@FindBy(xpath="//img[contains(@id,'pt1:AP1:AT1:_ATp:welctb1::icon')]")
	public WebElement createBtn;
	
	@FindBy(xpath="//button[text()='Save']")
	public WebElement saveBtn;			
	
	@FindBy(xpath="//button[text()='OK']")
	public WebElement okBtn;
	
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:AT1:qryId2::search')]")
	public WebElement searchBtn1;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:AT1:qryId2:value00::content')]")
	public WebElement valueSearchField ;
	
	@FindBy(xpath="//button[contains(text(),'ave and Close')]")
	public WebElement saveAndCloseBtn;
	
	
	
	
	
	public WebElement getCreatedValue(String createdValue,WebDriver driver) {
		
		WebElement createdValueEle = driver.findElement(By.xpath("//span[text() = '"+createdValue+"']"));
		return createdValueEle;
	}
	
	public WebElement getSelectedIndValue(String IndValue,WebDriver driver) {
//		WebElement getIndValueEle = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot4::content') and @value ='"+IndValue+"']"));
		WebElement getIndValueEle = driver.findElement(By.xpath("//input[contains(@id , '::content') and @value ='"+IndValue+"']"));
		return getIndValueEle;
	}
	
	public WebElement getDescEle(String Datatype,WebDriver driver) {
		
		switch (ValueSetPage.valueDataType.valueOf(Datatype)) {
			case Character : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot7::content')]"));
				break;
			case Number : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot15::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot7::content')]"));
				break;
			case Date : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot25::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot7::content')]"));
				break;
			case DateTime :
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot35::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot7::content')]"));
				break;
		default:
			break;
		}
	return fieldElement;
	}
	
	public WebElement getEnabledEle(String Datatype,WebDriver driver) {
		
		switch (ValueSetPage.valueDataType.valueOf(Datatype)) {
		case Character : 
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::label[contains(@for, 'sbc1::content')]/descendant::div"));
			break;
		case Number : 
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot15::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::label[contains(@for, 'sbc1::content')]/descendant::div"));
			break;
		case Date : 
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot25::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::label[contains(@for, 'sbc1::content')]/descendant::div"));
			break;
		case DateTime :
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot35::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::label[contains(@for, 'sbc1::content')]/descendant::div"));
			break;
		default:
			break;
	}
		return fieldElement;
	}
	
	public WebElement getStartDateEle(String Datatype,WebDriver driver) {
		
		switch (ValueSetPage.valueDataType.valueOf(Datatype)) {
		case Character : 
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot3::content')]"));
			break;
		case Number : 
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot15::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot3::content')]"));
			break;
		case Date : 
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot25::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot3::content')]"));
			break;
		case DateTime :
			fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot35::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot3::content')]"));
			break;
		default:
			break;
	}
		return fieldElement;
	}
	
	public WebElement getEndDateEle(String Datatype,WebDriver driver) {
		switch (ValueSetPage.valueDataType.valueOf(Datatype)) {
			case Character : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot2::content')]"));
				break;
			case Number : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot15::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot2::content')]"));
				break;
			case Date : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot25::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot2::content')]"));
				break;
			case DateTime :
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot35::content')]/ancestor::tr[contains(@class , 'p_AFSelected')]/descendant::input[contains(@id , 'ot2::content')]"));
				break;
			default:
			    break;
		}
		return fieldElement;
	}
	
	public WebElement getSortOrderEle(String Datatype,WebDriver driver) {
		switch (ValueSetPage.valueDataType.valueOf(Datatype)) {
			case Character : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot9::content')]"));
				break;
			case Number : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot15::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot9::content')]"));
				break;
			case Date : 
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot25::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot9::content')]"));
				break;
			case DateTime :
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot35::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot9::content')]"));
				break;
		   default:
			    break;
		}
		
	return fieldElement;
	}
	
	public WebElement getIndVSLovEle(String Datatype,WebDriver driver) {
		switch (ValueSetPage.valueDataType.valueOf(Datatype)) {
			case Character : 
//				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot5::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot4::lovIconId')]"));
				System.out.println("Inside Character element");
//				fieldElement = driver.findElement(By.xpath("//a[contains(@id , 'ot4::lovIconId')]"));
				fieldElement=driver.findElement(By.xpath("//input[contains(@id,'ot4::content')]"));
				break;
			case Number : 
//				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot15::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot4::lovIconId')]"));
//				fieldElement = driver.findElement(By.xpath("//a[contains(@id , 'ot10::lovIconId')]"));
				fieldElement=driver.findElement(By.xpath("//input[contains(@id,'ot10::content')]"));
				break;
			case Date : 
//				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot11::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot4::lovIconId')]"));
//				fieldElement = driver.findElement(By.xpath("//a[contains(@id,':ot11::lovIconId')]"));
				fieldElement=driver.findElement(By.xpath("//input[contains(@id,'ot11::content')]"));
				break;
			case DateTime :
				fieldElement = driver.findElement(By.xpath("//input[contains(@id , 'ot35::content')]/ancestor::tr[@class = 'af_table_data-row p_AFSelected']/descendant::a[contains(@id , 'ot4::lovIconId')]"));
				break;
			default:
				break;
		}
		
		return fieldElement;
	}
	
}
