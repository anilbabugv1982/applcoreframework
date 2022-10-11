/**
 * 
 *//*
package oracle.applcore.qa.sandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.setup.DriverInstance;



*//**
 * @author vagopu
 *
 *//*
public class ComposerTestClasses {

	//*****************************************************************************************************************************************************   
		@Test(description="Clicks on the userlink",priority=1)
		public void pagecomposertestcase01(){
			
			
			//WebDriverWait wait = new WebDriverWait(DriverInstance.getDriverInstance(), 30);
			
			//CommonUtils.login("mhoope","Welcome1");
			//Log.info("Log into application with credentials");
			CommonUtils.waitForElement(CommonUtils.userLink);
			//Log.info("Wait for the Home Page to Load");
			CommonUtils.userLink.click();
		                                       //click on the user hyperlink
			//Log.info("Click on the user hyperlink");
			//HomePage.Adminlinks("Global Page Template");
			//Log.info("Select Global Page Template");
		}
		
		//**********************************End of TestCase-01*************************************************************************************************	
		public static void pagecomposertestcase02(){
			    
			    CommonUtils.hold(10);                                           //hold for 10 sec
				Assert.assertEquals(DriverInstance.getDriverInstance().findElement(By.linkText("View")).isEnabled(),true);    //Assertion
				//Log.info("View Link Exists");
				PageTemplate.viewlink();                                  //Click on the View link
			  //  Log.info("Select View link in Global Page Template");
			    PageTemplate.sourcelink();                                //Select Source link
			  //  Log.info("Click on the Source link");
		}
		//**********************************End of TestCase-02*************************************************************************************************
		//   ---> global template
		public static void OracleImageCustomization(){
		
			PageTemplate.imagelogo();                                     //Click on the Image Logo
			//Log.info("Select Image Logo");
			CommonUtils.hold(10);
			PageTemplate.Customizeediticon();                             //click on edit icon
			//Log.info("Select Edit Customized Icon");
			PageTemplate.styletab();
			//Log.info("Click on Style tab");
			PageTemplate.backgroundimage();
			//Log.info("Enter the background image url");
			CommonUtils.hold(10);
			//CommonUtils.popupapply();
			//Log.info("Click on Apply");
			//CommonUtils.popupok();
			//Log.info("Click on Ok");
		}
	//**********************************End of TestCase-03*************************************************************************************************	
		//---> global template
		public static void WatchlistCustomization(String UpdatableText){
		   CommonUtils.hold(10);	
	       //HomePage.watchlisticon.click();                                           //Click on Watchlisticon
	       //Log.info("Click on Watchlist icon");
	       PageTemplate.Customizeediticon();                             //click on edit icon
		   //Log.info("Select Edit Customized Icon");
		   PageTemplate.iconedit();                                      //Click on Icon Edit
		   //Log.info("Click on Icon Edit");
		   PageTemplate.expressionbuilder();                             //Click on Expression Builder
		   //Log.info("Click on Expression Builder");
		   PageTemplate.expressioneditor(UpdatableText);                              //Enter Value in the Expression Editor 
		   //Log.info("Enter Some text in Expression Editor");                   
		   PageTemplate.expressioneditortestbutton();                    //Click on Test Button       
		  // Log.info("Click on Test Button");
		   PageTemplate.expressioneditorokbutton();                      //Click on Ok Button
		  // Log.info("Click on Ok Button");
		   CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
		   //CommonUtils.popupapply();                                           //Click on Popup Apply                                
		  // Log.info("Click on Apply");     
		   //CommonUtils.popupok();                                              //Click on Ok BUtton 
		  // Log.info("Click on Ok");
		   CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
		   PageTemplate.pagecomposerpopupclose();                        //Click on Close option

		}
		
		public static WebElement VerifyCustomizedWatchListIcon(String UpdatedText) {
			WebElement GetIconElement = DriverInstance.getDriverInstance().findElement(By.xpath("//*[local-name() = 'svg' and contains(@id , 'pt1:_UISwlLink::icon')]/ancestor::a[@title = '"+UpdatedText+"']"));
			return GetIconElement;
		}
		//**********************************End of TestCase-04*************************************************************************************************	
		//---> PageComposer
		public static void PageComposer(String ExistingText, String UpdatableText){
			
			CommonUtils.waitForElement(CommonUtils.userLink);
			CommonUtils.hold(7);
			CommonUtils.userLink.click();                                       //Click on the userlink
			//Log.info("Click on the user link");
			//HomePage.Adminlinks("Set Preferences...");                                //Click on the Preferences Page
			//Log.info("Select Preferences link");
			CommonUtils.waitForElement(CommonUtils.userLink);
			CommonUtils.hold(7);
			CommonUtils.userLink.click();                                       //Click on the userlink
			//Log.info("Click on the user link");
			PageTemplate.customizePagesLink().click();                      //Select Customize Pages
			//Log.info("Select Customize Pages link");
			CommonUtils.hold(10);
			PageTemplate.pagecomposerstructure();                  //Click on the Structure
			//Log.info("Click on the Structure"); 
			CommonUtils.hold(7);
			PageTemplate.preferencespage(ExistingText);                    //Click on the My Photo Link
			//Log.info("Click on Myphoto link");
			CommonUtils.hold(7);
			PageTemplate.pagecomposerediticon();                   //Click on the Edit Icon in popup
			//Log.info("Click on the Composer Edit popup icon");
			CommonUtils.hold(10);
			PageTemplate.Customizeediticon();                             //click on edit icon
			//Log.info("Select Edit Customized Icon");
			PageTemplate.customizedropdown("Text",1);
			//Log.info("Click on the text drop dwn label");
			PageTemplate.customizepageexpressionbuilder("Expression Builder");
			//Log.info("Select Expression Builder");
			PageTemplate.pagecomposertextareaeditor(UpdatableText);            //Enter text editor
			//Log.info("Enter Text Area");
			PageTemplate.pagecomposertextareatestbutton();           //Click on Text Button
			//Log.info("Click on Text Button");
			PageTemplate.pagecomposertextareaokbutton();           //Click on Text Button
			//Log.info("Click on OK Button");
			CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
			//CommonUtils.popupapply();                                           //Click on Popup Apply                                
			//Log.info("Click on Apply");     
			//CommonUtils.popupok();                                              //Click on Ok BUtton 
			//Log.info("Click on Ok");
			CommonUtils.hold(10);      
			PageTemplate.pagecomposerpopupclose();                        //Click on Close option
			
		}
		
		public static WebElement VerifyComposerCustomizations(String CustomizedText) {
		
			CommonUtils.waitForElement(CommonUtils.userLink);
			CommonUtils.hold(7);
			CommonUtils.userLink.click();                                       //Click on the userlink
			//Log.info("Click on the user link");
			//HomePage.Adminlinks("Set Preferences...");                                //Click on the Preferences Page
			//Log.info("Select Preferences link");
			CommonUtils.waitForElement(CommonUtils.userLink);
			
			WebElement GetCustomizedElement = DriverInstance.getDriverInstance().findElement(By.xpath("//a[text() = '"+CustomizedText+"']"));
			return GetCustomizedElement;
		}
		//**********************************End of TestCase-04*************************************************************************************************	
		
		//CRM PageComposer testcase on "Quotas" page
		static void QuotasMenu(){
			try {
		//		CommonUtils.NavigationMenuItem("Sales", "Quotas");
				CommonUtils.waitForPageLoad();
			} catch (Exception QME) {
				// TODO Auto-generated catch block
				QME.printStackTrace();
			}
		}
		
		
		static void CustomizePageSitelink(){
			
			CommonUtils.userLink.click();                                       //Click on the userlink
		//Log.info("Click on the user link");
		CommonUtils.hold(3);
		DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(@id,'FOpt1:_UIScmi3')]")).click();
		//HomePage.Adminlinks("Customize Pages");                            //Select Customize Pages
		//Log.info("Select Customize Pages link");
		//PageTemplate.pagecomposercustomerlayer(0);                   //Select Site Layer
		//CommonUtils.hold(10); 
		//Log.info("Select Site Layer");
		//PageTemplate.pagecomposercustomerlayerokbutton();
		//Log.info("Click on Ok Buttton");
		CommonUtils.hold(10); 
		
		}//End Of pagecomposertestcase07()
		
		
		public static void CustomizePageSitelinkcompose(String UpdatableText){
			QuotasMenu();
			CustomizePageSitelink();
			PageTemplate.pagecomposerselect();
		//Log.info("Click on Select button");
		CommonUtils.hold(15);
		PageTemplate.salesglobaldetails();
		//Log.info("Click on sales global details");
		PageTemplate.pagecomposereditcomponent();
		//Log.info("Click on Edit Component Details");
		CommonUtils.hold(10);
		PageTemplate.salescomponenteditcontent(UpdatableText);
		//Log.info("Enter the component detilas in text area");
		//CommonUtils.popupapply();                                           //Click on Popup Apply                                
		//Log.info("Click on Apply");     
	//	CommonUtils.popupok();                                              //Click on Ok BUtton 
		//Log.info("Click on Ok");
		CommonUtils.hold(10);
		
		}//End Of pagecomposertestcase09()
}
*/