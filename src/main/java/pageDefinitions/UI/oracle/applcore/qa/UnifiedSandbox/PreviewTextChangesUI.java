/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author klalam
 *
 */
public class PreviewTextChangesUI {
	
	public PreviewTextChangesUI(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//h2[text() = 'Preview Text Changes']")
	public  WebElement PreviewTextChanges;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetail::ti']/descendant::a")
	public  WebElement FirstModuleTab;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetailj_id_1::ti']/descendant::a")
	public  WebElement SecondModuleTab;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetailj_id_2::ti']/descendant::a")
	public  WebElement ThirdModuleTab;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetailj_id_3::ti']/descendant::a")
	public  WebElement FourthModuleTab;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:r1:0:pcl1:t1::db']/descendant::table[@_rowcount]")
	public  WebElement FirstModuleCount;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetailj_id_1']/descendant::table[@_rowcount]")
	public  WebElement SecondModuleCount;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetailj_id_2']/descendant::table[@_rowcount]")
	public  WebElement ThirdModuleReplacedCount;
	
	@FindBy(xpath = "//div[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:1:pluginDetailj_id_3']/descendant::table[@_rowcount]")
	public  WebElement FourthModuleCount;
	
	@FindBy(xpath = "//div[contains(@id , 'cancelBtn')]/descendant::a/ancestor::tr[1]/descendant::div[contains(@id , 'saveCloseBtn')]/descendant::a")
	public  WebElement SaveTransaction;
	
	@FindBy(xpath = "//div[contains(@id , 'cancelBtn')]/descendant::a")
	public  WebElement DiscardTransaction;
	
	public boolean VerifyDisabledModule() {
		String FirstModuleText,SecondModuleText,ThridModuleText;
		FirstModuleText = FirstModuleTab.getText();
			FirstModuleText = FirstModuleText.replaceAll(" ", "_");
		SecondModuleText = 	SecondModuleTab.getText();
			SecondModuleText = SecondModuleText.replaceAll(" ", "_");
		ThridModuleText	= ThirdModuleTab.getText();
			ThridModuleText= ThridModuleText.replaceAll(" ", "_");
		if(FirstModuleText.equalsIgnoreCase(SearchAndReplace.ExcludedModuleName) && SecondModuleText.equalsIgnoreCase(SearchAndReplace.ExcludedModuleName) && ThridModuleText.equalsIgnoreCase(SearchAndReplace.ExcludedModuleName))
			return false;
		return true;
	}//End of VerifyDisabledModule()
	
		
}//End of PreviewTextChangesUI Class
