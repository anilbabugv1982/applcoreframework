package REST.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.REST.SOAPCommonUtils;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import io.restassured.response.Response;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.AttachCatUtils;
import pageDefinitions.UI.oracle.applcore.qa.Audit.AuditReferencePage;

public class Attachments_CategoryRestAPI extends GetDriverInstance{
	
	public String[] words;
	Map<String, Map<String, Object>> postObject;
	Map<String, Map<String, String>> parsedResponse;
	String parsedResponse1; 
	String time;
	String Ent1, Ent2, Cat1, Cat2;
	
	static String BaseUrl;
	static String uniqueID;
	
	WebDriver EntCatDrvInstance;
	private ApplicationLogin aLoginInstance=null;
	private NavigationTaskFlows ntFInstance=null;
	private NavigatorMenuElements nMEInstance=null;
	private AttachCatUtils AttachCatUtilInstance;

//****************************************************************************************************************************************************************
	@BeforeClass(alwaysRun = true,description="Prequisite: Initialize values")
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
		String env=GetDriverInstance.EnvironmentName;
		Integer index=env.indexOf("/fscmUI/");
		BaseUrl=env.substring(0,index);
		System.out.println(BaseUrl);
        uniqueID = CommonUtils.uniqueId();
        time = CommonUtils.time();
        
        EntCatDrvInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(EntCatDrvInstance);
		ntFInstance = new NavigationTaskFlows(EntCatDrvInstance);
		nMEInstance = new NavigatorMenuElements(EntCatDrvInstance);
		AttachCatUtilInstance=new AttachCatUtils(EntCatDrvInstance);
		
	}
	
//*****************************************************************************************************************************************************************
	@Parameters({ "user", "pwd" })
	@Test(description = "This testcase is used for creating attachment entities(disabled) and categories.", priority = 1, enabled = true)
	public void createDisabledEntAndCatRestAPI(String username, String password) throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String[] attachCat1Details = {};
		String[] attachEnt1Details = {};
		aLoginInstance.login(username, password, EntCatDrvInstance);
		Log.info("Logging into the Application");
		//CommonUtils.hold(3);
		
		EntCatDrvInstance.get(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		CommonUtils.hold(3);
		Log.info("Navigated Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Attachment Entities", EntCatDrvInstance);
		Log.info("Clicked on Manage Attachment Entities");
		
		attachEnt1Details = AttachCatUtilInstance.attachmententity(EntCatDrvInstance, false);
		Log.info("Created Attachment Entity");
		CommonUtils.hold(5);

		
		ntFInstance.navigateToAOLTfSearchPage("Manage Attachment Categories",EntCatDrvInstance);
		Log.info("Click on Manage Attachment Categories");
		attachCat1Details = AttachCatUtilInstance.AttachCateg(EntCatDrvInstance);
		Log.info("Created Attachment Category");
		CommonUtils.hold(5);
		
		Ent1 = attachEnt1Details[0];
		Cat1 = attachCat1Details[0];
		
		System.out.println("Ent1: "+Ent1);
		System.out.println("Cat1: "+Cat1);

	}	
	
//*****************************************************************************************************************************************************************
	@Parameters({ "user", "pwd" })
	@Test(description = "This testcase is used for creating attachment entities(enabled) and categories.", priority = 2, enabled = true, dependsOnMethods={"createDisabledEntAndCatRestAPI"})
	public void createEnabledEntAndCatRestAPI(String username, String password) throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String[] attachCat2Details = {};
		String[] attachEnt2Details = {};
		
		EntCatDrvInstance.get(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		CommonUtils.hold(3);
		Log.info("Navigated Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Attachment Entities", EntCatDrvInstance);
		Log.info("Clicked on Manage Attachment Entities");
		
		attachEnt2Details = AttachCatUtilInstance.attachmententity(EntCatDrvInstance, true);
		Log.info("Created Attachment Entity");
		CommonUtils.hold(5);

		
		ntFInstance.navigateToAOLTfSearchPage("Manage Attachment Categories",EntCatDrvInstance);
		Log.info("Click on Manage Attachment Categories");
		attachCat2Details = AttachCatUtilInstance.AttachCateg(EntCatDrvInstance);
		Log.info("Created Attachment Category");
		CommonUtils.hold(5);
		
		Ent2 = attachEnt2Details[0];
		Cat2 = attachCat2Details[0];
		
		System.out.println("Ent2: "+Ent2);
		System.out.println("Cat2: "+Cat2);

	}		
				
		
//*****************************************************************************************************************************************************************
	@Test(description = "This testcase is used for getting Category Description through RestAPI", priority = 3, enabled = true)
	public void categoryDescriptionRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//{{Env}}:443/fscmRestApi/applcoreApi/v1/attachCategory/describe
		Response catResponse;

		postObject = RestCommonUtils.parseJson("AttachmentsCategoryRestServices",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Category.json");
		Log.info("Reading data from Attachments_Category.json file");

		String url = BaseUrl + postObject.get("CategoryDescription").get("RequestURL");
		System.out.println("Category Description URL: "+ url);
		//Object param1 = postObject.get("ItemDetails").get("Payloads");
		//String parameters = param1.toString();

		catResponse = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),
				"app_impl_consultant", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + catResponse.getStatusCode());
		Log.info("indexcreationresponse:" + catResponse.getBody().asString());
		
		try {

			Assert.assertEquals(catResponse.getStatusCode(), 200, "Get Category Description: REST status code mismtach.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"openapi\": \"3.0.0\""), "Open API3 document is not displayed.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"/attachCategory/e/{entityName}/a/{action}\":"), "CategoryListForEntity path is not displayed in Open API3 document.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"/attachCategory/e/{entityName}/c/{categoryName}\":"), "EntityCategoryDetails path is not displayed in Open API3 document.");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//***************************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for getting Entity-Category details through RestAPI", priority = 4, enabled = true)
	public void getEntCatDetailsRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//{{Env}}:443/fscmRestApi/applcoreApi/v1/attachCategory/e/ENT_111/c/CAT_111
		Response catResponse;

		postObject = RestCommonUtils.parseJson("AttachmentsCategoryRestServices",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Category.json");
		Log.info("Reading data from Attachments_Category.json file");

		String url = BaseUrl + postObject.get("EntityCategoryDetails").get("RequestURL");
		url = url.replace("<entity_name>", Ent1);
		url = url.replace("<category_name>", Cat1);
		System.out.println("Entity-Category Details URL: "+ url);
		//Object param1 = postObject.get("ItemDetails").get("Payloads");
		//String parameters = param1.toString();
		
		catResponse = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),
				"app_impl_consultant", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + catResponse.getStatusCode());
		Log.info("indexcreationresponse:" + catResponse.getBody().asString());
		
		try {

			Assert.assertEquals(catResponse.getStatusCode(), 200, "Get Entity-Category Details: REST status code mismtach.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"EntityName\":\""+Ent1+"\","), "Entity name is not displayed.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"CategoryName\":\""+Cat1+"\","), "Category name is not displayed.");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//***************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for getting Category list through RestAPI for an Entity which has Data security disabled.", priority = 5, enabled = true)
	public void getCatListForDisbledEntityRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//{{Env}}:443/fscmRestApi/applcoreApi/v1/attachCategory/e/ENT_111/a/READ
		Response catResponse;

		postObject = RestCommonUtils.parseJson("AttachmentsCategoryRestServices",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Category.json");
		Log.info("Reading data from Attachments_Category.json file");

		String url = BaseUrl + postObject.get("CategoryListForEntity").get("RequestURL");
		url = url.replace("<entity_name>", Ent1);
		url = url.replace("<action>", "READ");
		System.out.println("Category list for Entity URL: "+ url);
		//Object param1 = postObject.get("ItemDetails").get("Payloads");
		//String parameters = param1.toString();

		catResponse = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),
				"app_impl_consultant", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + catResponse.getStatusCode());
		Log.info("indexcreationresponse:" + catResponse.getBody().asString());
		
		try {

			Assert.assertEquals(catResponse.getStatusCode(), 200, "Get Category List for Entity: REST status code mismtach.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"EntityName\":\""+Ent1+"\","), "Entity name is not displayed.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"CategoryName\":\""+Cat1+"\","), "Category name is not displayed.");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
//***************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for getting Category list through RestAPI for an Entity which has Data security enabled.", priority = 6, enabled = true)
	public void getCatListForEnabledEntityRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//{{Env}}:443/fscmRestApi/applcoreApi/v1/attachCategory/e/ENT_112/a/READ
		Response catResponse;

		postObject = RestCommonUtils.parseJson("AttachmentsCategoryRestServices",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Category.json");
		Log.info("Reading data from Attachments_Category.json file");

		String url = BaseUrl + postObject.get("CategoryListForEntity").get("RequestURL");
		url = url.replace("<entity_name>", Ent2);
		url = url.replace("<action>", "READ");
		System.out.println("Category list for Entity URL: "+ url);
		//Object param1 = postObject.get("ItemDetails").get("Payloads");
		//String parameters = param1.toString();

		catResponse = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),
				"app_impl_consultant", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + catResponse.getStatusCode());
		Log.info("indexcreationresponse:" + catResponse.getBody().asString());
		
		try {

			Assert.assertEquals(catResponse.getStatusCode(), 200, "Get Category List for Entity: REST status code mismtach.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"EntityName\":\""+Ent2+"\","), "Entity name is not displayed.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"CategoryName\":\""+Cat2+"\","), "Category name is not displayed.");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
//****************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for getting Category list through RestAPI for an Entity which has Data security enabled and Authorization user is different.", priority = 7, enabled = true)
	public void getCatListForEnabledEntityWithDifferentAuthorizationRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//{{Env}}:443/fscmRestApi/applcoreApi/v1/attachCategory/e/ENT_112/a/READ
		//Entity with enabled data security was created using app_impl_consultant but here we are using pimqa user.
		Response catResponse;

		postObject = RestCommonUtils.parseJson("AttachmentsCategoryRestServices",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Category.json");
		Log.info("Reading data from Attachments_Category.json file");

		String url = BaseUrl + postObject.get("CategoryListForEntity").get("RequestURL");
		url = url.replace("<entity_name>", Ent2);
		url = url.replace("<action>", "READ");
		System.out.println("Category list for Entity URL: "+ url);
		//Object param1 = postObject.get("ItemDetails").get("Payloads");
		//String parameters = param1.toString();

		catResponse = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),
				"pimqa", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + catResponse.getStatusCode());
		Log.info("indexcreationresponse:" + catResponse.getBody().asString());
		
		try {

			Assert.assertEquals(catResponse.getStatusCode(), 200, "Get Category List for Entity: REST status code mismtach.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"items\":[],"), "Category list displayed which should not be displayed as authorization user is different and data security is enabled for the entity.");
			Assert.assertTrue(catResponse.getBody().asString().contains("\"count\":0,"), "Category list count is not zero for enabled entity when authorization user is different.");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//****************************************************************************************************************************************************************
	
	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {
		EntCatDrvInstance.quit();
	}
//************************************************************************************************************************************	


}
