/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;


/**
 * @author vagopu
 *
 */
public class ComposerWrapperfns extends GlobalPageTemplateClasses {

	private GlobalPageTemplate gptGtcInstance;
	private CommonUtilsDefs cudefInstnace;
	private PageTemplate pTInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	
	public ComposerWrapperfns(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		gptGtcInstance = new GlobalPageTemplate(driver);
		cudefInstnace = new CommonUtilsDefs(driver);
		pTInstance = new PageTemplate(driver);
		ntFInstance = new NavigationTaskFlows(driver);
		nMEInstance = new NavigatorMenuElements(driver);
	}
	
		//   ---> global template
		public void OracleImageCustomization(WebDriver driver){
		
			CommonUtils.hold(5);
			imagelogo(driver);                                     //Click on the Image Logo
			Log.info("Select Image Logo");
			CommonUtils.hold(10);
			Customizeediticon(driver);                             //click on edit icon
			Log.info("Select Edit Customized Icon");
			CommonUtils.hold(10);
			styletab(driver);
			Log.info("Click on Style tab");
			CommonUtils.hold(10);
			backgroundimage(driver);
			Log.info("Enter the background image url");
			CommonUtils.hold(10);
			CommonUtils.popupapply(driver);
			Log.info("Click on Apply");
			CommonUtils.hold(5);
			CommonUtils.popupok(driver);
			Log.info("Click on Ok");
			CommonUtils.hold(3);
		}
	
		//---> global template
		public void WatchlistCustomization(String UpdatableText,WebDriver driver){
		   CommonUtils.hold(10);	
		   gptGtcInstance.watchListIcon.click();
	       //Click on Watchlisticon
	       Log.info("Click on Watchlist icon");
	       CommonUtils.hold(10);
	       Customizeediticon(driver);                             //click on edit icon
		   Log.info("Select Edit Customized Icon");
		   CommonUtils.hold(10);
		   iconedit(driver);                                      //Click on Icon Edit
		   Log.info("Click on Icon Edit");
		   CommonUtils.hold(10);
		   expressionbuilder(driver);                             //Click on Expression Builder
		   Log.info("Click on Expression Builder");
		   CommonUtils.hold(10);
		   expressioneditor(UpdatableText,driver);                              //Enter Value in the Expression Editor 
		   Log.info("Enter Some text in Expression Editor");
		   CommonUtils.hold(10);
		   expressioneditortestbutton(driver);                    //Click on Test Button       
		   Log.info("Click on Test Button");
		   CommonUtils.hold(10);
		   expressioneditorokbutton(driver);                      //Click on Ok Button
		   Log.info("Click on Ok Button");
		   CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
		   CommonUtils.popupapply(driver);                                           //Click on Popup Apply                                
		   Log.info("Click on Apply"); 
		   CommonUtils.hold(5);
		   CommonUtils.popupok(driver);                                              //Click on Ok BUtton 
		   Log.info("Click on Ok");
		   CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
		   pagecomposerpopupclose(driver);                        //Click on Close option
		   CommonUtils.hold(5);
		}
		
		public WebElement VerifyCustomizedWatchListIcon(String UpdatedText,WebDriver driver) {
			WebElement GetIconElement = driver.findElement(By.xpath("//*[local-name() = 'svg' and contains(@id , 'pt1:_UISwlLink::icon')]/ancestor::a[@title = '"+UpdatedText+"']"));
			return GetIconElement;
		}
		//**********************************End of TestCase-04*************************************************************************************************	
		//---> PageComposer
		public void PageComposer(WebElement Existingfield, String UpdatableText,WebDriver driver){
			
			CommonUtils.hold(10);
			cudefInstnace.userLink.click();                                       //Click on the userlink
			Log.info("Click on the user link");
			CommonUtils.hold(10);
			gptGtcInstance.preferenceslink.click();                             //Click on the Preferences link
			Log.info("Select Preferences link");
			//CommonUtils.waitForElement(CommonUtils.userLink);
			CommonUtils.hold(20);
			driver.findElement(By.id("_FOpt1:_UIScmil1u::icon")).click();                  //Click on User Link
			//CommonUtils.userLink.click();                                       //Click on the userlink
			Log.info("Click on the user link");
			CommonUtils.hold(5);
			gptGtcInstance.pagedit.click();
			//PageTemplate.customizePagesLink().click();                      //Select Customize Pages
			Log.info("Select Customize Pages link");
			CommonUtils.hold(10);
			pTInstance.pagecomposerstructure(driver);                  //Click on the Structure
			Log.info("Click on the Structure"); 
			CommonUtils.hold(7);
			//PageTemplate.preferencespage(ExistingText);                    //Click on the My Photo Link
			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id , 'pt1:dsv_src') and contains(@class , 'WCSelected')]")), "Visible", "",driver);
				CommonUtils.waitForElementToPresent(Existingfield);
			} catch (Exception e) {
			Log.info("Structure tab not clicked");
				e.printStackTrace();
			}
			Existingfield.click();
			Log.info("Click on Lanuage link");
			CommonUtils.hold(7);
			pTInstance.pagecomposerediticon(driver);                   //Click on the Edit Icon in popup
			Log.info("Click on the Composer Edit popup icon");
			CommonUtils.hold(10);
			pTInstance.Customizeediticon(driver);                             //click on edit icon
			Log.info("Select Edit Customized Icon");
			pTInstance.customizedropdown("Text",1,driver);
			Log.info("Click on the text drop dwn label");
			pTInstance.customizepageexpressionbuilder("Expression Builder",driver);
			Log.info("Select Expression Builder");
			pTInstance.pagecomposertextareaeditor(UpdatableText,driver);            //Enter text editor
			Log.info("Enter Text Area");
			pTInstance.pagecomposertextareatestbutton(driver);           //Click on Text Button
			Log.info("Click on Text Button");
			pTInstance.pagecomposertextareaokbutton(driver);           //Click on Text Button
			Log.info("Click on OK Button");
			CommonUtils.hold(20);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
			CommonUtils.popupapply(driver);                                           //Click on Popup Apply                                
			Log.info("Click on Apply");
			CommonUtils.hold(10);
			CommonUtils.popupok(driver);                                              //Click on Ok BUtton 
			Log.info("Click on Ok");
			CommonUtils.hold(10);      
			pTInstance.pagecomposerpopupclose(driver);                     //Click on Close option
			
		}
		
		public  WebElement VerifyComposerCustomizations(String CustomizedText,WebDriver driver) {
		
			CommonUtils.waitForElement(cudefInstnace.userLink,driver);
			CommonUtils.hold(7);
			cudefInstnace.userLink.click();        //Click on the userlink
			Log.info("Click on the user link");
			CommonUtils.hold(10);
			gptGtcInstance.preferenceslink.click();                             //Click on the Preferences link
			Log.info("Select Preferences link");
			try {
				CommonUtils.explicitWait(pTInstance.prefrencesLanguageEle, "Visible", "",driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			WebElement GetCustomizedElement = driver.findElement(By.xpath("//a[text() = '"+CustomizedText+"']"));
			return GetCustomizedElement;
		}
		//**********************************End of TestCase-04*************************************************************************************************	
		
		//CRM PageComposer testcase on "Quotas" page
		 void QuotasMenu(WebDriver driver){
			try {
				ntFInstance.navigateToTask(nMEInstance.Quotas,driver);
				CommonUtils.waitForPageLoad(driver);
			} catch (Exception QME) {
				// TODO Auto-generated catch block
				QME.printStackTrace();
			}
		}
		
		
		void CustomizePageSitelink(WebDriver driver){
			
			cudefInstnace.userLink.click();                                       //Click on the userlink
		//Log.info("Click on the user link");
		CommonUtils.hold(3);
		driver.findElement(By.xpath("//a[contains(@id,'FOpt1:_UIScmi3')]")).click();
		//HomePage.Adminlinks("Customize Pages");                            //Select Customize Pages
		//Log.info("Select Customize Pages link");
		//GlobalPageTemplate.pagecomposercustomerlayer(0);                   //Select Site Layer
		//CommonUtils.hold(10); 
		//Log.info("Select Site Layer");
		//GlobalPageTemplate.pagecomposercustomerlayerokbutton();
		//Log.info("Click on Ok Buttton");
		CommonUtils.hold(10); 
		
		}//End Of pagecomposertestcase07()
		
		
		public void CustomizePageSitelinkcompose(String UpdatableText,WebDriver driver){
			QuotasMenu(driver);
			CustomizePageSitelink(driver);
			pagecomposerselect(driver);
		//Log.info("Click on Select button");
		CommonUtils.hold(15);
		salesglobaldetails(driver);
		//Log.info("Click on sales global details");
		pagecomposereditcomponent(driver);
		//Log.info("Click on Edit Component Details");
		CommonUtils.hold(10);
		salescomponenteditcontent(UpdatableText,driver);
		//Log.info("Enter the component details in text area");
		CommonUtils.popupapply(driver);                                           //Click on Popup Apply                                
		//Log.info("Click on Apply");     
		CommonUtils.popupok(driver);                                              //Click on Ok BUtton 
		//Log.info("Click on Ok");
		CommonUtils.hold(10);
		
		}//End Of pagecomposertestcase09()
}
