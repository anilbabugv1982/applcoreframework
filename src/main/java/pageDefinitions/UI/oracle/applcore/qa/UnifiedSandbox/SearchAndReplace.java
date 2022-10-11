/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.ReportGenerator;

/**
 * @author klalam
 * Xpaths for "Search and Replace Text" popup to perform "Search and Replace" operations
 */
public class SearchAndReplace extends PreviewTextChangesUI {
	
	private ApplicationTextUI atuInstance;
	public SearchAndReplace(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		atuInstance = new ApplicationTextUI(driver);
	}
	
	public enum SearchModules{
		User_Interface_Text,Global_Menu_Label_Text,Multipart_Validation_Messages,Enterprise_Scheduler_Text;
	  }
	
	public static String ExcludedModuleName;
	
	//ArrayList<WebElement> SearchModules = new ArrayList<>[Arrays.asList("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCB']/descendant::input","//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCBj_id_1']/descendant::input","");
	
	//@FindBy(xpath = "//div[text() = 'Search and Replace Text']")
	@FindBy(xpath = "//div[contains(@id , 'id20::_ttxt')]")
	public  WebElement SearchAndReplaceTextWindow;

	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'sngSrch']/descendant::textarea")
	public  WebElement SingularSearch;
	
	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'pluSrch']/descendant::textarea")
	public  WebElement PluralSearch;
	
	@FindBy(xpath = "//div[@id = 'ph3']/descendant::tr[@id = 'sngRepl']/descendant::textarea")
	public  WebElement SingularReplace;
	
	@FindBy(xpath = "//div[@id = 'ph3']/descendant::tr[@id = 'pluRepl']/descendant::textarea")
	public  WebElement PluralReplace;
	
	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'matchCaseBox']/descendant::input[@type = 'checkbox']")
	public  WebElement SearchWithMatchCase;
	
	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'matchCompletePhrase']/descendant::input[@type = 'checkbox']")
	public  WebElement SearchWithMatchword;
	
	/*
	 * Xpaths for Modules to be included in Search and Replace operation
	 */
	
	@FindBy(xpath = "//label[@id = 'pluginCBj_id::Label0']/descendant::div[@class = 'af_selectBooleanCheckbox_virtual-checkbox']")
	public  WebElement UserInterfaceTextModule;
	
	@FindBy(xpath = "//label[@id = 'pluginCBj_id_1::Label0']/descendant::div[@class = 'af_selectBooleanCheckbox_virtual-checkbox']")
	public  WebElement MessagesModule;
	
	@FindBy(xpath = "//label[@id = 'pluginCBj_id_2::Label0']/descendant::div[@class = 'af_selectBooleanCheckbox_virtual-checkbox']")
	public  WebElement GlobalMenuModule;
	
	@FindBy(xpath = "//label[@id = 'pluginCBj_id_3::Label0']/descendant::div[@class = 'af_selectBooleanCheckbox_virtual-checkbox']")
	public  WebElement ESSModule;
	
	/*
	 * Xpaths for Initaite SearchAndReplace Operation or Cancel
	 */
	
	@FindBy(xpath = "//div[@id = 'pgl3']/descendant::button[@id = 'previewButton']")
	public  WebElement PreviewButtonDisabled;
	
	@FindBy(xpath = "//div[@id = 'pgl3']/descendant::button[@id = 'previewButton' and not(@disabled)]")
	public  WebElement PreviewButtonEnabled;
	
	@FindBy(xpath = "//div[@id = 'pgl3']/descendant::button[@id = 'cancelButton']")
	public  WebElement CancelButton;
	
	@FindBy(xpath = "//div[@class = 'AFBlockingGlassPane']")
	public  WebElement AFBlockingGlassPane;
	
	/*
	 * Xpath for retrieving dynamically generated "FrameId" of "Search and Repalce" task frame from main window
	 */
	@FindBy(xpath = "//iframe[contains(@src, 'adf.dialog-request')]")
	public  WebElement SearchAndReplaceFrame;
	
	
	public void SearchAndReplaceStrings(String SingleSearch, String SingleReplace, String PlurSearch, String PlurReplace) {
		System.out.println("SearchAndReplaceStirngs() Begin");
		
		//SearchAndReplaceFrameSwitch();
		
		SingularSearch.click();
		SingularSearch.sendKeys(SingleSearch);
		SingularReplace.sendKeys(SingleReplace);
		
		if(!(PlurSearch.isEmpty())) {
			PluralSearch.sendKeys(PlurSearch);
			PluralReplace.sendKeys(PlurReplace);
		}
		
		System.out.println("SearchAndReplaceStirngs() End");
		CommonUtils.hold(5);
	}//End of SearchAndReplaceStrings()
	
	public void ExcludeModulesSearch(String ModuleName,WebDriver driver) {
		ExcludedModuleName = ModuleName;
		switch(SearchModules.valueOf(ModuleName)) {
			case User_Interface_Text:
				if(UserInterfaceTextModule.isSelected()) {
					CommonUtils.hold(5);
					UserInterfaceTextModule.click();
				}
			case Multipart_Validation_Messages:
				if(MessagesModule.isSelected()) {
					CommonUtils.hold(5);
					MessagesModule.click();
				}
			case Global_Menu_Label_Text:
				if(GlobalMenuModule.isSelected()) {
					CommonUtils.hold(5);
					GlobalMenuModule.click();
				}
			case Enterprise_Scheduler_Text:
				if(ESSModule.isSelected()) {
					//CommonUtils.hold(5);
					try {
						CommonUtils.explicitWait(ESSModule, "Click", "",driver);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ESSModule.click();
				}
				CommonUtils.hold(3);	
		}
	}//End of ExcludeModulesSearch()
	
	public void SearchAndReplaceFrameSwitch(WebDriver driver) {
		System.out.println("SearchAndReplaceFrameSwitch() Begin");
		String FrameId;
		
		FrameId = SearchAndReplaceFrame.getAttribute("id");
		driver.switchTo().frame(FrameId);
		
		SingularSearch.click();
		
		System.out.println("SearchAndReplaceFrameSwitch() End");
	}//End of SearchAndReplaceFrameSwitch()
/*	
	public static void GetModuleElements() {
		String ModuleName,ModuleID;
		for(int loop=1;loop<5;loop++)
		{
			WebElement GetModuleName = DriverInstance.getDriverInstance().findElement(By.xpath("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::input["+loop+"]/ancestor::div[1]/descendant::label"));
			ModuleName = GetModuleName.getText().replaceAll(" ","_");
			WebElement GetModuleId = DriverInstance.getDriverInstance().findElement(By.xpath("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::input[4]/ancestor::div[1]/descendant::label/ancestor::span[@id]"));
			ModuleID = GetModuleId.getAttribute("id");
			
			System.out.println("Module Name - "+ModuleName);
			if(ModuleName.contains("User")) {
				 UserInterfaceTextModule = DriverInstance.getDriverInstance().findElement(By.xpath("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = '"+ModuleID+"']/descendant::input"));
			}
			else if(ModuleName.contains("Messages")) {
				MessagesModule = DriverInstance.getDriverInstance().findElement(By.xpath("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = '"+ModuleID+"']/descendant::input"));
			}
			else if(ModuleName.contains("Menu")) {
				GlobalMenuModule = DriverInstance.getDriverInstance().findElement(By.xpath("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = '"+ModuleID+"']/descendant::input"));
			}
			else
				ESSModule = DriverInstance.getDriverInstance().findElement(By.xpath("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = '"+ModuleID+"']/descendant::input"));
		}//End of Loop
		
	}//End of GetModuleElements()
*/	
	/*
	 * PreformSearchAndReplace() performs SearchAndReplace functionality on the strings that are passed as parameters
	 */
	public void PreformSearchAndReplace(String SingSearch, String SingReplace, String PluSearch, String PluReplace, String ExcludeSearchModule,WebDriver driver) {
		
		boolean isPreviewUIVisible = false;
		
		try {
			
			System.out.println("PreformSearchAndReplace() Begin");
			
			atuInstance.SearchAndReplace_Enabled.click();
			
			CommonUtils.explicitWait(SearchAndReplaceTextWindow, "Visible", "",driver);
			
			SearchAndReplaceFrameSwitch(driver);
			
			System.out.println("SearchAndReplace Text PopUp loaded");
			
			Assert.assertTrue(PreviewButtonDisabled.isDisplayed());
			
		//	GetModuleElements();
			
			SearchAndReplaceStrings(SingSearch,SingReplace,PluSearch,PluReplace);
			
			CommonUtils.hold(10);
			
			/*
			 * condition to exclude list of modules during "Search And Replace" operation
			 */
			if(!(ExcludeSearchModule.isEmpty())) {
				System.out.println("Identifying list of Search Modules to be Excluded during serachAndreplace operation");
				  List<String> excludedModules = getListOfExcludedSearchModules(ExcludeSearchModule);
				  Iterator excludedModulesInterator = excludedModules.iterator();
				  		System.out.println("Identified list of Search Modules to be Excluded during serachAndreplace operation");
				  while(excludedModulesInterator.hasNext()) {
					  ExcludeModulesSearch(excludedModulesInterator.next().toString(),driver);
					  CommonUtils.hold(3);
				  }
			}
			
			PreviewButtonEnabled.click();
			
			driver.switchTo().defaultContent();
						
			//CommonUtils.ExplicitWait(PreviewTextChangesUI.SaveTransaction, "Click", "");
			if(AFBlockingGlassPane.isEnabled())
				AFBlockingGlassPane.click();
			
		//	CommonUtils.PageRefresh();
			while(!isPreviewUIVisible) {
				try {
					CommonUtils.explicitWait(SaveTransaction, "Click", "",driver);
				}catch(Exception iPUVE) {
					System.out.println("Preview Text Changes UI not in visible yet");
				}
				if(SaveTransaction.isDisplayed())
					isPreviewUIVisible = true;
			}
			CommonUtils.hold(5);
			SaveTransaction.click();
			
			//retryingFindClick(PreviewTextChangesUI.SaveTransaction);
			
			CommonUtils.explicitWait(atuInstance.TextReplacementTable, "Visible", "",driver);
			
			System.out.println("PreformSearchAndReplace() End");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}//End of PreformSearchAndReplace()
	
	public boolean retryingFindClick(By by,WebDriver driver) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
	}
	
	/*
	 * getListOfExcludedSearchModules(String toBeExcludeModules) method will return list of modules to be excluded during SearchAndReplace 
	 */
	public List<String> getListOfExcludedSearchModules(String toBeExcludeModules){
		List<String> excludeModuleslist;
		
		if(!(toBeExcludeModules.isEmpty())) {
			excludeModuleslist = new ArrayList<String>();
			String[] RepositoryList = toBeExcludeModules.split(",");
		
			for(String RL : RepositoryList) {
				excludeModuleslist.add(RL);
				System.out.println("Module "+RL+" Added to List");
					System.out.println("Module < "+RL+" > to be exclude during SearchAndReplace");
			}
		}else
			excludeModuleslist = Collections.EMPTY_LIST;
		
		return excludeModuleslist;
	}//End of getListOfExcludedSearchModules()
	
	
	
}//End of SearchAndReplaceText Class
