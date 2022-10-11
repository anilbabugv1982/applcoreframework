/*package oracle.applcore.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.common.GlobalPageTemplate;
import oracle.applcore.qa.common.Log;
import oracle.applcore.qa.sandbox.*;
import oracle.applcore.qa.setup.DriverInstance;

public class SandboxTests {
	public static String UpdatableText="Testapplcore";
	public static String ExistingText="Test";
	
	@Parameters({ "uname", "pwd" })
    @BeforeClass
    public void startUP(String username, String password) throws Exception {
		System.out.println(System.getProperty("user.dir")+"\\src\\test\\resources\\MyExtentReport.html");
    CommonUtils.login(username, password);
    CommonUtils.hold(5);
    }*/
//*******************************************START*******************************************************************************************//	

	/*@Test(priority=1,description="This testcase is for activating sandbox",enabled=true)
	public void testcase01() throws Exception {
		ManageSandbox.sandboxcreate();               //Create Sandbox
		ManageSandbox.ActivateSandbox();             //Activate Sandbox
		
	}*/
//*******************************************END OF ACTIVATING SANDBOX************************************************************************//	
	
/*	
	@Test(priority=2,description="This testcase is for Creating Structure related to ATK")
	public void testcase02() throws Exception {
		try {
			
			
			
			CommonUtils.hold(5);
            GlobalPageTemplate.navigatorButton.click();
            CommonUtils.hold(5);
            //CommonUtils.scrollToElement(GlobalPageTemplate.structure);
            //CommonUtils.hold(5);
            WebElement ele=DriverInstance.getDriverInstance().findElement(By.id("pt1:nvi_ATK_HOMEPAGE_FUSE_STRUCTURE::icon"));
            JavascriptExecutor executor = (JavascriptExecutor)DriverInstance.getDriverInstance();
            executor.executeScript("arguments[0].click();", ele);
           // GlobalPageTemplate.structure.click();
			//CommonUtils.navigateToTask(GlobalPageTemplate.structure);
			//CommonUtils.scrollToElement(GlobalPageTemplate.structure);
			StructureTestMethods.createCategory();
			StructureTestMethods.createPageEntryStaticURL();
			StructureTestMethods.createPageEntryDynamicURL();
			StructureTestMethods.createPageEntryUnderExistingCategory();
		    //StructureTestMethods.verifyCategory();
			//StructureTestMethods.verifyStaticPage();
			//StructureTestMethods.verifyDynamicPage();
			//StructureTestMethods.verifyExistingCategoryStaticPage();
			//StructureTestMethods.modifyExistingCategoryPage("welcome");
			//StructureTestMethods.verifyExistingCategoryStaticPage();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	*/
//*******************************************END OF STRUCTURE PANE************************************************************************//
		
		/*@Test(description="Clicks on the userlink",priority=3)
		public void testcase03(){
			
			CommonUtils.hold(10);
			//WebDriverWait wait = new WebDriverWait(DriverInstance.getDriverInstance(), 30);
		     GlobalPageTemplate.homeIcon.click();
		     Log.info("Wait for the Home Page to Load");
			//CommonUtils.login("mhoope","Welcome1");
			//Log.info("Log into application with credentials");
		     CommonUtils.hold(10);
			CommonUtils.waitForElement(CommonUtils.userLink);
			CommonUtils.userLink.click();
			Log.info("Click on the user link");
			CommonUtils.hold(10);
			GlobalPageTemplate.globalpagetemplate.click();
		    Log.info("Select Global Page Template");
		}	*/
		
//*******************************************END OF USERLINK CREATION************************************************************************//		
		/*@Test(priority=4,description="This testcase is for Validating View link")
		public static void testcase04(){
		    
		    CommonUtils.hold(10);                                           //hold for 10 sec
			Assert.assertEquals(DriverInstance.getDriverInstance().findElement(By.linkText("View")).isEnabled(),true);    //Assertion
			Log.info("View Link Exists");
			PageTemplate.viewlink();                                  //Click on the View link
		    Log.info("Select View link in Global Page Template");
		    PageTemplate.sourcelink();                                //Select Source link
		    Log.info("Click on the Source link");
	}	*/
//*******************************************END OF VALIDATING VIEW LINK************************************************************************//		
	
	//   ---> global template
		/*@Test(priority=4,description="This testcase is for Validating Image Customization")
		   public static void testcase05(){
			    CommonUtils.hold(5);
				PageTemplate.imagelogo();                                     //Click on the Image Logo
				Log.info("Select Image Logo");
				CommonUtils.hold(10);
				PageTemplate.Customizeediticon();                             //click on edit icon
				Log.info("Select Edit Customized Icon");
				PageTemplate.styletab();
				Log.info("Click on Style tab");
				PageTemplate.backgroundimage();
				Log.info("Enter the background image url");
				CommonUtils.hold(20);
				CommonUtils.popupapply();
				Log.info("Click on Apply");
				CommonUtils.popupok();
				Log.info("Click on Ok");
			}*/
		
//**********************************End of Validating Image Customization*************************************************************************************************	
			//---> global template
		   /* @Test(priority=5,description="This testcase is for Validating Watchlist Customization")
			public static void WatchlistCustomization(){
			   CommonUtils.hold(10);	
		       GlobalPageTemplate.watchlisticon.click();                     //Click on the watchlisticon
		       Log.info("Click on Watchlist icon");
		       PageTemplate.Customizeediticon();                             //click on edit icon
			   Log.info("Select Edit Customized Icon");
			   PageTemplate.iconedit();                                      //Click on Icon Edit
			   Log.info("Click on Icon Edit");
			   PageTemplate.expressionbuilder();                             //Click on Expression Builder
			   Log.info("Click on Expression Builder");
			   PageTemplate.expressioneditor(UpdatableText);                              //Enter Value in the Expression Editor 
			   Log.info("Enter Some text in Expression Editor");                   
			   PageTemplate.expressioneditortestbutton();                    //Click on Test Button       
			   Log.info("Click on Test Button");
			   PageTemplate.expressioneditorokbutton();                      //Click on Ok Button
			   Log.info("Click on Ok Button");
			   CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
			   CommonUtils.popupapply();                                           //Click on Popup Apply                                
			   Log.info("Click on Apply");     
			   CommonUtils.popupok();                                              //Click on Ok BUtton 
			   Log.info("Click on Ok");
			   CommonUtils.hold(10);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
			   PageTemplate.pagecomposerpopupclose();                        //Click on Close option

			}
			
			public static WebElement VerifyCustomizedWatchListIcon(String UpdatedText) {
				WebElement GetIconElement = DriverInstance.getDriverInstance().findElement(By.xpath("//*[local-name() = 'svg' and contains(@id , 'pt1:_UISwlLink::icon')]/ancestor::a[@title = '"+UpdatedText+"']"));
				return GetIconElement;
			}*/
//**********************************End of Validating Watchlist Customization*************************************************************************************************	
			//---> PageComposer
		/*	@Test(priority=6,description="This testcase is for Validating the Page Composer")
			public static void PageComposer(){
				
				//CommonUtils.waitForElement(CommonUtils.userLink);
				CommonUtils.hold(10);
				CommonUtils.userLink.click();                                       //Click on the userlink
				Log.info("Click on the user link");
				CommonUtils.hold(10);
				GlobalPageTemplate.preferences.click();                             //Click on the Preferences link
				Log.info("Select Preferences link");
				//CommonUtils.waitForElement(CommonUtils.userLink);
				CommonUtils.hold(20);
				DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:_UIScmil1u::icon")).click();                  //Click on User Link
				//CommonUtils.userLink.click();                                       //Click on the userlink
				Log.info("Click on the user link");
				CommonUtils.hold(5);
				GlobalPageTemplate.pagedit.click();
				//PageTemplate.customizePagesLink().click();                      //Select Customize Pages
				Log.info("Select Customize Pages link");
				CommonUtils.hold(10);
				PageTemplate.pagecomposerstructure();                  //Click on the Structure
				Log.info("Click on the Structure"); 
				CommonUtils.hold(7);
				PageTemplate.preferencespage(ExistingText);                    //Click on the My Photo Link
				Log.info("Click on Myphoto link");
				CommonUtils.hold(7);
				PageTemplate.pagecomposerediticon();                   //Click on the Edit Icon in popup
				Log.info("Click on the Composer Edit popup icon");
				CommonUtils.hold(10);
				PageTemplate.Customizeediticon();                             //click on edit icon
				Log.info("Select Edit Customized Icon");
				PageTemplate.customizedropdown("Text",1);
				Log.info("Click on the text drop dwn label");
				PageTemplate.customizepageexpressionbuilder("Expression Builder");
				Log.info("Select Expression Builder");
				PageTemplate.pagecomposertextareaeditor(UpdatableText);            //Enter text editor
				Log.info("Enter Text Area");
				PageTemplate.pagecomposertextareatestbutton();           //Click on Text Button
				Log.info("Click on Text Button");
				PageTemplate.pagecomposertextareaokbutton();           //Click on Text Button
				Log.info("Click on OK Button");
				CommonUtils.hold(20);                                               //Wait for 10 Sec before clicking on Apply and Ok BUtton
				CommonUtils.popupapply();                                           //Click on Popup Apply                                
				Log.info("Click on Apply");
				CommonUtils.hold(10);
				CommonUtils.popupok();                                              //Click on Ok BUtton 
				Log.info("Click on Ok");
				CommonUtils.hold(10);      
				PageTemplate.pagecomposerpopupclose();                        //Click on Close option
				
			}
			
			public static WebElement VerifyComposerCustomizations(String CustomizedText) {
			
				CommonUtils.waitForElement(CommonUtils.userLink);
				CommonUtils.hold(7);
				CommonUtils.userLink.click();                                       //Click on the userlink
				Log.info("Click on the user link");
				GlobalPageTemplate.preferences.click();                             //Click on the Preferences link
				CommonUtils.hold(7);
	            Log.info("Select Preferences link");
				CommonUtils.waitForElement(CommonUtils.userLink);
				CommonUtils.hold(7);
				WebElement GetCustomizedElement = DriverInstance.getDriverInstance().findElement(By.xpath("//a[text() = '"+CustomizedText+"']"));
				return GetCustomizedElement;
			}*/
//**********************************End of Page Composer*************************************************************************************************	
			
		/*	//CRM PageComposer testcase on "Quotas" page
			static void QuotasMenu(){
				try {
					CommonUtils.hold(10);
					GlobalPageTemplate.navigatorButton.click();
					CommonUtils.hold(3);
					GlobalPageTemplate.navigatequotas();
					//CommonUtils.navigateToTask(GlobalPageTemplate.quotas);
			//		CommonUtils.NavigationMenuItem("Sales", "Quotas");
					CommonUtils.hold(20);
					CommonUtils.waitForPageLoad();
				} catch (Exception QME) {
					// TODO Auto-generated catch block
					QME.printStackTrace();
				}
			}*/
			
//**********************************End of Page Site*************************************************************************************************			
		/*	static void CustomizePageSitelink(){
				//DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:_UIScmil1u::icon")).click();                  //Click on User Link
				DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(@id,'FOpt1:_UIScmil1u::icon')]")).click();                                                              
				//CommonUtils.userLink.click();                                       //Click on the userlink
			Log.info("Click on the user link");
			CommonUtils.hold(20);
			//DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(@id,'FOpt1:_UIScmi3')]")).click();
			GlobalPageTemplate.pagedit.click();                            //Select Customize Pages
			Log.info("Select Customize Pages link");
			PageTemplate.pagecomposercustomerlayer(0);                   //Select Site Layer
			CommonUtils.hold(10); 
			Log.info("Select Site Layer");
			PageTemplate.pagecomposercustomerlayerokbutton();
			Log.info("Click on Ok Buttton");
			CommonUtils.hold(10); 
			
			}//End Of pagecomposertestcase07()
*/			
//**********************************End of Custom Page Site*************************************************************************************************	
			/*@Test(priority=7,description="This testcase is for Validating the Page Site link Compose")      
			public static void CustomizePageSitelinkcompose(){
				GlobalPageTemplate.homeIcon.click();           //Click on the Home Icon
				CommonUtils.hold(10);
				QuotasMenu();
				CommonUtils.hold(20);
				CustomizePageSitelink();
				PageTemplate.pagecomposerselect();
			Log.info("Click on Select button");
			CommonUtils.hold(15);
			PageTemplate.salesglobaldetails();
			Log.info("Click on sales global details");
			PageTemplate.pagecomposereditcomponent();
			Log.info("Click on Edit Component Details");
			CommonUtils.hold(10);
			PageTemplate.salescomponenteditcontent(UpdatableText);
			Log.info("Enter the component detilas in text area");
			CommonUtils.popupapply();                                           //Click on Popup Apply                                
			Log.info("Click on Apply");     
		    CommonUtils.popupok();                                              //Click on Ok BUtton 
			Log.info("Click on Ok");
			CommonUtils.hold(10);
			
			}//End Of pagecomposertestcase09()
*/
//**********************************End of CustomizePageSitelink Compose*************************************************************************************************
		/*	@Test(priority=8,description="This testcase is used for publishing the sandbox")
			public static void sandboxpublish() {
				PageTemplateFusePlus.homeButton.click();
				Log.info("Click on Home Button");
				CommonUtils.hold(4);
				ManageSandbox.publishsandbox();
				Log.info("Publish the Sandbox");
			}*/
//**********************************End of Sandbox Publish*************************************************************************************************
		/*	 @AfterClass(alwaysRun = true)*/
    /*public void logOut() throws InterruptedException {
 
        try {
            CommonUtils.logout();
 
        } catch (Exception ex) {
            CommonUtils.launchURL();
            CommonUtils.hold(2);
            CommonUtils.logout();
        }
 
    }
}


*/