package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

public class UserIntefaceTextFuseUIPageDef {
	
	public UserIntefaceTextFuseUIPageDef(WebDriver driver){
		
		PageFactory.initElements(driver,this);
		
	}

	static Map<String , Integer> moduleNameIndexMap;

	@FindBy(xpath = "//textarea[contains(@id , 'itSrch::content')]")
	public  WebElement findText;
	
	@FindBy(xpath = "//textarea[contains(@id , 'itRepl::content')]")
	public  WebElement replaceText;
	
	@FindBy(xpath = "//label[contains(@id , 'sbcCase::Label0')]/div")
	public  WebElement optMatchCase;
	
	@FindBy(xpath = "//label[contains(@id , 'sbcWord::Label0')]/div")
	public  WebElement optWordOrPhraseMatch;
	
	@FindBy(xpath = "//div[contains(@id , 'cbSrch')]")
	public  WebElement initiateSearch;
	
	@FindBy(xpath = "//div[contains(@id , 'cancelBtn')]/ancestor::tr[1]/descendant::div[contains(@id , 'saveCloseBtn')]")
	public  WebElement replaceStrings;
	
	@FindBy(xpath = "//div[contains(@id , 'cancelBtn')]")
	public  WebElement newSearch;
	
	@FindBy(xpath = "//img[contains(@id , 'sdiHist::icon')]")
	public  WebElement textReplacementHistoryTab;
	
	@FindBy(xpath = "//img[contains(@id , 'sdiSrch::icon')]")
	public  WebElement searchNreplaceTab;
	
	@FindBy(xpath = "//img[contains(@id , 'sdiExp::icon')]")
	public  WebElement exportTab;
	
	@FindBy(xpath = "//img[contains(@id , 'sdiImp::icon')]")
	public  WebElement importTab;
	
	@FindBy(xpath = "//span[contains(@id , 'pt1:rg1:0:of1')]")
	public  WebElement uitPreviewTextElement;
	
	
	/*
	 * getModuleTypeText(int index) will return name of the module type of the corresponding index passed as a parameter
	 */
	
	public String getModuleTypeText(int index,WebDriver driver) {
		String moduleType = null;
		
			moduleType = driver.findElement(By.xpath("//label[contains(@id , 'itr1:"+index+":pluginCB::Label0')]")).getText().trim();
		
		return moduleType;
	}
	
	/*
	 * isModuleActivated(int index) will check if module type is checked or not.
	 * 
	 *   returns true if module type is activated else returns false
	 */
	public boolean isModuleActivated(int index, WebDriver driver) {
		boolean moduleChecked = false;
		try {
			if(driver.findElement(By.xpath("//label[contains(@id , 'itr1:"+index+":pluginCB::Label0') and @checked]")).isDisplayed())
				moduleChecked = true;
			System.out.println("Module is activated");
		}catch(Exception iMAE) {
			System.out.println("Module is not activated");
		}
	 return moduleChecked;
	}
	
	/*
	 * getModuleTypeElement(int index) return moduleTypeElement of the respective index
	 */
	public WebElement getModuleTypeElement(int index, WebDriver driver) {
		
		return driver.findElement(By.xpath("//label[contains(@id , 'itr1:"+index+":pluginCB::Label0')]/div"));
	}
	
	/*
	 * setModuleNameIndexMap() will create map with "ModuleType Name" as KEY and "Index" as VALUE
	 */
	public void setModuleNameIndexMap(WebDriver driver){
		
		moduleNameIndexMap = new HashMap<String, Integer>();
		
		for(int i=0;i<4;i++) {
			moduleNameIndexMap.put(getModuleTypeText(i,driver), i);
		}

	}
	
	/*
	 * searchAndReplaceStrings() will perform search and replace tasks of the strings from UserInterfaceText ui
	 */
	
	public void stringsSearchAndReplace(String searchableText,String replacableText,String includeModulesList,String excludeModulesList,WebDriver driver) {
		
		boolean isSearchResultUIVisible = false;
		boolean isSearchAndReplaceUIVisible = false;
		
		try {
						
			CommonUtils.hold(3);
			System.out.println("stringsSearchAndReplace() task Begin");
			
			findText.click();
			CommonUtils.hold(2);
			System.out.println("Searchable String");
			findText.sendKeys(searchableText);
						
			CommonUtils.hold(2);
			
			replaceText.click();
			CommonUtils.hold(2);
			System.out.println("Replacable Text");
			replaceText.sendKeys(replacableText);
			
			/*
			 * Condition to include modules during searchAndreplace operation 
			 */
			 if(!(includeModulesList.isEmpty())) {
				 String[] includeModules = includeModulesList.split(",");
				 for(String moduleName : includeModules) {
					 if(!(isModuleActivated(moduleNameIndexMap.get(moduleName),driver))) {
						 System.out.println(moduleNameIndexMap.get(moduleName) +"not activated. Including to participate in Search and Replace task");
						 getModuleTypeElement(moduleNameIndexMap.get(moduleName),driver).click();
						 CommonUtils.hold(10);
						 System.out.println(moduleNameIndexMap.get(moduleName) +"activated to participate in Search and Replace task");
					 }
				 }
			 }
			 
			 /*
			  * Condition to exclude modules during searchAndreplace operation
			  */
			 
			 if(!(excludeModulesList.isEmpty())) {
				 String[] excludeModules = excludeModulesList.split(",");
				 for(String moduleName : excludeModules) {
					 if(isModuleActivated(moduleNameIndexMap.get(moduleName),driver)) {
						 System.out.println(moduleNameIndexMap.get(moduleName) +"is in activated state. Excluding to not participate in Search and Replace task");
						 getModuleTypeElement(moduleNameIndexMap.get(moduleName),driver).click();
						 CommonUtils.hold(10);
						 System.out.println(moduleNameIndexMap.get(moduleName) +"not activated to exclude in Search and Replace task");
					 }
				 }
			 }
			
			 System.out.println("Initiating Seach And Replace Operation");
			 System.out.println("Initiating String Search Process");
			 initiateSearch.click();
			 System.out.println("Search Results UI Navigation Invoked");
			 
			 CommonUtils.hold(5);
			 
			 /*
			  * Loop to wait till "Replace Strings" ui is loaded
			  */
			 for(int searchUIWait = 1; searchUIWait < 50; searchUIWait++){
					if(isSearchResultUIVisible) {
						System.out.println("Search UI is Visibled");
						break;
					}else {
						try {
						if(replaceStrings.isDisplayed()) {
							System.out.println("Search UI is Loaded");
							CommonUtils.hold(2);
							isSearchResultUIVisible = true;
							}
						}catch(Exception wPPE) {
						System.out.println("Search UI not Loaded Yet. Waiting for more time");
						CommonUtils.hold(30);
						}
					}
				}//End Of for
			 
			 System.out.println("Committing Replce Strings transaction");
			 /*
			  * Navigate to "" module in Preview Changes UI to reflect  menu strings replacement in navigator menu
			  * until fix for bug#30907273 available 
			  */
			 CommonUtils.hold(5);
			 getPreviewChangesUIModuleNameElement("Global Menu Label Text",driver).click();
			 CommonUtils.hold(10);
			 
			 replaceStrings.click();
			 CommonUtils.hold(10);
			 
			 /*
			  * Loop to wait till "Search and Replace" ui is loaded after replacing strings
			  */
			 for(int searchAndReplaceUIWait = 1; searchAndReplaceUIWait < 50; searchAndReplaceUIWait++){
					if(isSearchAndReplaceUIVisible) {
						System.out.println("SearchAndReplace UI is Visibled");
						break;
					}else {
							try {
								if(initiateSearch.isDisplayed()) {
									System.out.println("SearchAndReplace UI is Loaded");
								
									CommonUtils.hold(2);
									isSearchAndReplaceUIVisible = true;
								}
							}catch(Exception wPPE) {
								System.out.println("SearchAndReplace UI not Loaded Yet. Waiting for more time");
								CommonUtils.hold(30);
							}
						}
				}//End Of for
		
		System.out.println("PreformSearchAndReplace() End");

		}catch(Exception sSARE) {
			System.out.println("Exception in stringsSearchAndReplace()");
			sSARE.printStackTrace();
		}
	}//End of stringsSearchAndReplace()
	
	/*
	 * getPreviewChangesUIModuleNameElement(String moduleName, WebDriver driver)  returns modulename element of module from "Preview Changes" UI  participated in SearchAndReplace operation
	 */
	public WebElement getPreviewChangesUIModuleNameElement(String moduleName, WebDriver driver) {
		WebElement moudleNameElement = driver.findElement(By.xpath("//a[text() = '"+moduleName+"' and not(contains(@id ,'disAcrCnvr'))]"));
		return moudleNameElement;
	}
}
