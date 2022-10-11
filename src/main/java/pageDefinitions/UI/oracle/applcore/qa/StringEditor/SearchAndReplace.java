/**
 * 
 *//*
package oracle.applcore.qa.stringeditor;

import java.sql.Driver;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.setup.DriverInstance;



*//**
 * @author klalam
 * Xpaths for "Search and Replace Text" popup to perform "Search and Replace" operations
 *//*
public class SearchAndReplace {
	
	static {
		PageFactory.initElements(DriverInstance.getDriverInstance(), SearchAndReplace.class);
	}
	
	public enum SearchModules{
		User_Interface_Text,Global_Menu_Label_Text,Multipart_Validation_Messages,Enterprise_Scheduler_Text;
	  }
	
	public static String ExcludedModuleName;
	
	//ArrayList<WebElement> SearchModules = new ArrayList<>[Arrays.asList("//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCB']/descendant::input","//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCBj_id_1']/descendant::input","");
	
	@FindBy(xpath = "//div[text() = 'Search and Replace Text']")
	public  static WebElement SearchAndReplaceTextWindow;

	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'sngSrch']/descendant::textarea")
	public  static WebElement SingularSearch;
	
	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'pluSrch']/descendant::textarea")
	public  static WebElement PluralSearch;
	
	@FindBy(xpath = "//div[@id = 'ph3']/descendant::tr[@id = 'sngRepl']/descendant::textarea")
	public  static WebElement SingularReplace;
	
	@FindBy(xpath = "//div[@id = 'ph3']/descendant::tr[@id = 'pluRepl']/descendant::textarea")
	public  static WebElement PluralReplace;
	
	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'matchCaseBox']/descendant::input[@type = 'checkbox']")
	public  static WebElement SearchWithMatchCase;
	
	@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'matchCompletePhrase']/descendant::input[@type = 'checkbox']")
	public  static WebElement SearchWithMatchword;
	
	
	 * Xpaths for Modules to be included in Search and Replace operation
	 
	
	//@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCB']/descendant::input")
	public  static WebElement UserInterfaceTextModule;
	
	//@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCBj_id_1']/descendant::input")
	public  static WebElement MessagesModule;
	
	//@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCBj_id_2']/descendant::input")
	public  static WebElement GlobalMenuModule;
	
	//@FindBy(xpath = "//div[@id = 'ph2']/descendant::tr[@id = 'plam1']/descendant::span[@id = 'pluginCBj_id_3']/descendant::input")
	public  static WebElement ESSModule;
	
	
	 * Xpaths for Initaite SearchAndReplace Operation or Cancel
	 
	
	@FindBy(xpath = "//div[@id = 'pgl3']/descendant::button[@id = 'previewButton']")
	public  static WebElement PreviewButtonDisabled;
	
	@FindBy(xpath = "//div[@id = 'pgl3']/descendant::button[@id = 'previewButton' and not(@disabled)]")
	public  static WebElement PreviewButtonEnabled;
	
	@FindBy(xpath = "//div[@id = 'pgl3']/descendant::button[@id = 'cancelButton']")
	public  static WebElement CancelButton;
	
	@FindBy(xpath = "//div[@class = 'AFBlockingGlassPane']")
	public  static WebElement AFBlockingGlassPane;
	
	
	 * Xpath for retrieving dynamically generated "FrameId" of "Search and Repalce" task frame from main window
	 
	@FindBy(xpath = "//iframe[contains(@src, 'adf.dialog-request')]")
	public  static WebElement SearchAndReplaceFrame;
	
	
	public static void SearchAndReplaceStrings(String SingleSearch, String SingleReplace, String PlurSearch, String PlurReplace) {
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
	}//End of SearchAndReplaceStrings()
	
	public static void ExcludeModulesSearch(String ModuleName) {
		ExcludedModuleName = ModuleName;
		switch(SearchModules.valueOf(ModuleName)) {
			case User_Interface_Text:
				if(UserInterfaceTextModule.isSelected())
					UserInterfaceTextModule.click();
			case Multipart_Validation_Messages:
				if(MessagesModule.isSelected())
					MessagesModule.click();
			case Global_Menu_Label_Text:
				if(GlobalMenuModule.isSelected())
					GlobalMenuModule.click();
			case Enterprise_Scheduler_Text:
				if(ESSModule.isSelected())
					ESSModule.click();
				
		}
	}//End of ExcludeModulesSearch()
	
	public static void SearchAndReplaceFrameSwitch() {
		System.out.println("SearchAndReplaceFrameSwitch() Begin");
		String FrameId;
		
		FrameId = SearchAndReplaceFrame.getAttribute("id");
		DriverInstance.getDriverInstance().switchTo().frame(FrameId);
		
		SingularSearch.click();
		
		System.out.println("SearchAndReplaceFrameSwitch() End");
	}//End of SearchAndReplaceFrameSwitch()
	
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
	
	
	 * PreformSearchAndReplace() performs SearchAndReplace functionality on the strings that are passed as parameters
	 
	public static void PreformSearchAndReplace(String SingSearch, String SingReplace, String PluSearch, String PluReplace, String ExcludeSearchModule) {
		
		try {
			
			System.out.println("PreformSearchAndReplace() Begin");
			
			ApplicationTextUI.SearchAndReplace_Enabled.click();
			
			CommonUtils.explicitWait(SearchAndReplace.SearchAndReplaceTextWindow, "Visible", "");
			
			SearchAndReplace.SearchAndReplaceFrameSwitch();
			
			System.out.println("SearchAndReplace Text PopUp loaded");
			
			Assert.assertTrue(SearchAndReplace.PreviewButtonDisabled.isDisplayed());
			
			GetModuleElements();
			
			SearchAndReplaceStrings(SingSearch,SingReplace,PluSearch,PluReplace);
			
			if(!(ExcludeSearchModule.isEmpty()))
				ExcludeModulesSearch(ExcludeSearchModule);
			
			SearchAndReplace.PreviewButtonEnabled.click();
			
			DriverInstance.getDriverInstance().switchTo().defaultContent();
						
			//CommonUtils.ExplicitWait(PreviewTextChangesUI.SaveTransaction, "Click", "");
			if(AFBlockingGlassPane.isEnabled())
				AFBlockingGlassPane.click();
			
			CommonUtils.PageRefresh();
			
			PreviewTextChangesUI.SaveTransaction.click();
			
			//retryingFindClick(PreviewTextChangesUI.SaveTransaction);
			
			CommonUtils.explicitWait(ApplicationTextUI.TextReplacementTable, "Visible", "");
			
			System.out.println("PreformSearchAndReplace() End");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}//End of PreformSearchAndReplace()
	
	public static boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                DriverInstance.getDriverInstance().findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
}
	
	
}//End of SearchAndReplaceText Class
*/