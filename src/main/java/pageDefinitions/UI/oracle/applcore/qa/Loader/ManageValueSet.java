package pageDefinitions.UI.oracle.applcore.qa.Loader;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageValueSet {

	public ManageValueSet(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[contains(@id,'AP1:qryId1:value00::content')]")
	public  WebElement valueSetCode;

	@FindBy(xpath="//img[contains(@id,'pt1:AP1:AT1:_ATp:edit::icon')]")
	public  WebElement _dseditIcon;

	@FindBy(xpath="//button[contains(@id,'pt1:AP2:cb1')]")
	public  WebElement _dseditDSButton;

	@FindBy(xpath="//input[contains(@id,'pt1:AP2:it8::content')]")
	public  WebElement _dsInputField;

	@FindBy(xpath="//button[contains(@id,'pt1:AP2:APsv')]")
	public WebElement _dsSaveButton;

	@FindBy(xpath="//button[contains(text(),'Search')]")
	public  WebElement search;

	@FindBy(xpath="//*[contains(@id,'AP1:d1::close')]")
	public  WebElement popup;

	@FindBy(xpath="//*[contains(@id,'AP1:d2::close')]")
	public  WebElement popup2;

	@FindBy(xpath="//span[contains(text(),'Manage Values')]")
	public WebElement manageValue;

	@FindBy(xpath="//label[contains(text(),'Values')]")
	public WebElement value;

	@FindBy(xpath="//*[contains(text(),'Manage Values')]")
	public WebElement manageValueLabel;

	@FindBy(xpath="//div[contains(@id,'AP1:AT1:_ATp:ATt1::db')]//table[@class='af_table_data-table af_table_data-table-VH-lines']")
	public WebElement valueSetData;

	@FindBy(xpath="//span[text()='C']")
	public WebElement cancel;

	@FindBy(xpath="//button[contains(@id,'pt1:AP2:APc')]")
	public WebElement cancel2;

	@FindBy(xpath="//select[contains(@id,'AP1:r2:0:soc1::content')]")
	public  WebElement account;

	@FindBy(xpath="//*[contains(text(),'Related Value Set File Name')]//..//..//td[2]//input")
	public  WebElement relatedValueSetFileName;

	@FindBy(xpath="//*[contains(text(),'Related Values File Name')]//..//..//td[2]//input")
	public  WebElement relatedValuesFileName;

	@FindBy(xpath="//a[contains(text(),'Related Value Sets')]")
	public  WebElement relatedValuesSet;

	@FindBy(xpath = "//span[contains(text(),'Related Value Set Code')]")
	public WebElement relatedValuesTabName;

	@FindBy(xpath = "//span[contains(text(),'Key Flexfield Name')]")
	public WebElement keyFlexTab;

	@FindBy(xpath="//img[contains(@title,'Edit') and contains(@id,'AP1:AT7:_ATp:edit::icon')]")
	public  WebElement editRelatedValueSetCode;

	@FindBy(xpath="//h1[contains(text(),'Edit Value Set Relationship')]")
	public  WebElement editValueSetRelation;

	@FindBy(xpath="//table[contains(@summary,'This table lists the related value set values.')]")
	public  WebElement summaryTable;

	@FindBy(xpath="//h3[contains(text(),'Manage Value Sets')]")
	public  WebElement manageValueSet;

	@FindBy(xpath="//input[contains(@id,'_afr_1_afr_pt1_afr_AP2_afr_AT2_afr__ATp_afr_ATt2_afr_c2::content')]")
	public  WebElement filter;

	@FindBy(xpath="//input[contains(@id,'_afr_1_afr_pt1_afr_AP2_afr_AT2_afr__ATp_afr_ATt2_afr_c6::content')]")
	public  WebElement filter2;

	@FindBy(xpath="//a[contains(@title,'Clear All')]")
	public  WebElement clearAll;

	@FindBy(xpath = "//div[contains(@id,'pt1:AP1:AT7:_ATp:ATt7::db')]/table/tbody/tr[1]/descendant::table/tbody/tr/td[1]")
	public WebElement firstRowInRVSTable;

	@FindBy(xpath = "//div[contains(@id,'pt1:AP1:AT7:_ATp:ATt7::db')]/table/tbody/tr[2]/descendant::table/tbody/tr/td[1]")
	public WebElement secondRowInRVSTable;

	@FindBy(xpath = "//div[contains(@id,'pt1:AP1:AT1:_ATp:ATt1::db')]/table/tbody/tr/descendant::table/tbody/tr/td[1]/span")
	public WebElement firstRowInVSTable;

	@FindBy(xpath = "//div[contains(@id,'pt1:AP1:AT7:_ATp:ATt7::db')]/table/tbody/tr[1]/descendant::table/tbody/tr/td[3]/descendant::img")
	public WebElement firstRowCheckBoxInVSTable;

	@FindBy(xpath = "//div[contains(@id,'pt1:AP1:AT7:_ATp:ATt7::db')]/table/tbody/tr[2]/descendant::table/tbody/tr/td[3]/descendant::img")
	public WebElement secondRowCheckBoxVSTable;

	@FindBy(xpath = "//span[contains(text(),'VV1')]")
	public WebElement vv1Code;

	@FindBy(xpath = "//span[contains(text(),'VV2')]")
	public WebElement vv2Code;

	@FindBy(xpath = "//span[contains(text(),'VV3')]")
	public WebElement vv3Code;

	@FindBy(xpath = "//span[contains(text(),'VV4')]")
	public WebElement vv4Code;

	@FindBy(xpath="//input[contains(@id,'sbc2::content')]")
	public List<WebElement> toKnowCheckBoxStatus;

//	@FindBy(xpath="//input[contains(@id,'sbc2')]")
//	public List<WebElement> toKnowCheckBoxStatus;
}
