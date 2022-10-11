package pageDefinitions.UI.oracle.applcore.qa.GlobalSearch;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;



public class gs {
	public  String fav_text;
	
	public gs(WebDriver driver) {
		
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(id="pt1:_UISGSr:0:_GSFsf::content")
	public  WebElement globalsearch;
	            
	@FindBy(id="pt1:_UISGSr:0:GSSugPers")
	public  WebElement personalizesearch;
	
	@FindBy(id="pt1:_UISGSr:0:GSSugCatsTabSSG::disAcr")
	public  WebElement searchcategory;
	
	@FindBy(id="pt1:_UISGSr:0:GSSugPersTabSSG::disAcr")
	public  WebElement autosuggest;
	                        
	@FindBy(xpath="//a[@id='pt1:_UISGSr:0:_GSsrdc:gsradlg::close']")
	public  WebElement searchclose;
	
	@FindBy(xpath="//span[text()='Opportunities']")
	public  WebElement searchterms;
	
	@FindBy(xpath="//input[@type='checkbox']")
	public  List<WebElement> searchactivegrpscheckbox;

	@FindBy(xpath="//ul[@id='pt1:_UISGSr:0:_GSFsf::_fndSuggestPopup']//li")
	public  List<WebElement> autosuggestpopup;
	
	
	
	@FindBy(xpath="//ul[@id='pt1:_UISGSr:0:_GSFsf::_fndSuggestPopup']//li")
	public  WebElement searchselect; 

		
	@FindBy(xpath="//a[@id='pt1:_UISfpr1:0:itr1_RI:0:cl3_RI']")
	public  WebElement favoritesclick; 	
	
	@FindBy(id="pt1:nv_itemNode_tools_audit_reports")
	public  WebElement recentitems;
	


}
