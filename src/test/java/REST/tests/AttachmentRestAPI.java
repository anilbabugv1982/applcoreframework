package REST.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import groovyjarjarasm.asm.commons.Method;
import io.restassured.response.Response;

public class AttachmentRestAPI {
	String pid;

	public static String timestamp = "";
	Map<String, Map<String, Object>> postObject;
	Map<String, String> responseObject;
	String response;
	String time = CommonUtils.time();
	public String RestUrl;
	public String[] words;
	public String[] CreateOpprespo2;
	public String CreateOpprespo1attach;
	public String CreateOpprespo1url;
	public String[] CreateOpprespo1attach1;
	public String[] CreateOpprespo1Url1;
	
	private String OptyNumber = null;
	private String fileAttachKey = null;
	private String urlAttachKey = null;

	
// ****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for Creating Opportunity RestAPI", priority = 1, enabled = true)
	public void CreateOppRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("CreateOpp").get("RequestURL");
		System.out.println(url);
		Object param1 = postObject.get("CreateOpp").get("Payloads");
		String parameters = param1.toString();
		String oppName = "RestOpp_"+time;
		System.out.println("Opportunity Name: "+ oppName);
		parameters = parameters.replace("<RestOpp>", oppName);
		System.out.println("Opportunity Updated Payload: "+ parameters);

		CreateOpprespo = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1),
				"sales_admin");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());
		
		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 201, "Create Opportunity REST status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String CreateOpprespo1 = RestCommonUtils.parseResponsetemp(CreateOpprespo, "href");
		System.out.println(CreateOpprespo1);
		CreateOpprespo2 = CreateOpprespo1.split("/");
		OptyNumber = CreateOpprespo2[7];
		System.out.println("OptyNumber: "+OptyNumber);
			
		

	}

// ****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for Creating file Attachment in Opportunity", priority = 2, enabled = true)
	public void CreateFileAttach() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		
		System.out.println("Attaching File attachment in opportunity through REST.");
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");
		//CreateOpprespo2[7]
		String url = words[0] + "//" + words[2] + postObject.get("PostFileTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment";
		System.out.println(url);
		Object param1 = postObject.get("PostFileTypeAttach").get("Payloads");
		String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1),
				"sales_admin");
		
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());
		
		try {
			// String CreateOpprespo1 = RestCommonUtils.parseResponsetemp(CreateOpprespo,
			// "status");
			
			//System.out.println("status line: "+CreateOpprespo.getStatusLine().replace("HTTP/1.1 ", ""));
			Assert.assertEquals(CreateOpprespo.getStatusCode(), 201, "Opportunity REST - File Attachment status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CreateOpprespo1attach = RestCommonUtils.parseResponsetemp(CreateOpprespo, "href");
		System.out.println("CreateOpprespo1attach: "+CreateOpprespo1attach);
	    CreateOpprespo1attach1=CreateOpprespo1attach.split("/");
	    fileAttachKey = CreateOpprespo1attach1[10];
		System.out.println("fileAttachKey: "+fileAttachKey);
		
	}

// ****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for Creating WebUrl type in Opportunity", priority = 3, enabled = true)
	public void CreateWebUrl() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("PostUrlTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment";
		System.out.println(url);
		Object param1 = postObject.get("PostUrlTypeAttach").get("Payloads");
		String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1),
				"sales_admin");
		
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 201, "Opportunity REST - URL Attachment status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CreateOpprespo1url = RestCommonUtils.parseResponsetemp(CreateOpprespo, "href");
		System.out.println(CreateOpprespo1url);
		CreateOpprespo1Url1=CreateOpprespo1url.split("/");
		urlAttachKey = CreateOpprespo1Url1[10];
		System.out.println("urlAttachKey: "+urlAttachKey);

	}
	
// ****************************************************************************************************************************************************************************************************************

	@Test(description = "This testcase is used for getting all attachments in Opportunity", priority = 4, enabled = true)
	public void GetAllAttachmentsAssociated()
			throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;
		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("PostUrlTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment";
		System.out.println(url);
		//Object param1 = postObject.get("PostUrlTypeAttach").get("Payloads");
		//String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

// ****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for getting file attachments in Opportunity", priority = 5, enabled = true)
	public void GetFileAttachment() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//"+words[2] +
		postObject.get("PostFileTypeAttach").get("RequestURL")+OptyNumber+"/"+"child/Attachment"+"/"+fileAttachKey;
		//String url = CreateOpprespo1attach;
		System.out.println("File url: "+url);
		//Object param1 = postObject.get("PostFileTypeAttach").get("Payloads");
		//String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

// ****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for getting content of file attachment in Opportunity", priority = 6, enabled = true)
	public void GetFileAttachmentContent() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//"+words[2] +
		postObject.get("PostFileTypeAttach").get("RequestURL")+OptyNumber+"/"+"child/Attachment"+"/"+fileAttachKey + "/enclosure/FileContents";
		//String url = CreateOpprespo1attach;
		System.out.println("File url: "+url);
		//Object param1 = postObject.get("PostFileTypeAttach").get("Payloads");
		//String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

// ****************************************************************************************************************************************************************************************************************

	@Test(description = "This testcase is used for getting URL attachment in opportunity", priority = 7, enabled = true)
	public void GetUrlAttachment() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("PostUrlTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment" + "/" + urlAttachKey;
		// String url=CreateOpprespo1attach;
		System.out.println(url);
		//Object param1 = postObject.get("PostUrlTypeAttach").get("Payloads");
		//String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


//****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for updating file attachment in Opportunity", priority = 8, enabled = true)
	public void updateFileAttachment() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//"+words[2] +
		postObject.get("PatchFileTypeAttach").get("RequestURL")+OptyNumber+"/"+"child/Attachment"+"/"+fileAttachKey;
		//String url = CreateOpprespo1attach;
		System.out.println("File url: "+url);
		Object param1 = postObject.get("PatchFileTypeAttach").get("Payloads");
		String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1), "sales_admin", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());
		
		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//****************************************************************************************************************************************************************************************************************

	@Test(description = "This testcase is used for updating url attachment in Opportunityy", priority = 9, enabled = true)
	public void updateUrlAttachment() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("PatchUrlTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment" + "/" + urlAttachKey;
		// String url=CreateOpprespo1attach;
		System.out.println(url);
		Object param1 = postObject.get("PatchUrlTypeAttach").get("Payloads");
		String parameters = param1.toString();

		CreateOpprespo = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1), "sales_admin", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
// ****************************************************************************************************************************************************************************************************************			

	@Test(description = "This testcase is used for Deleting File Type Attachment", priority = 10, enabled = true)
	public void DeleteFileTypeAttach() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;
		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("PostUrlTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment"+"/"+fileAttachKey;
		System.out.println(url);
		//Object param1 = postObject.get("PostUrlTypeAttach").get("Payloads");
		//String parameters = param1.toString();

		// CreateOpprespo = RestCommonUtils.getRequest(url, Collections.<String,
		// String>emptyMap(), "sales_admin","Welcome1");
		CreateOpprespo = RestCommonUtils.deleteRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 204);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for Deleting WebUrl", priority = 11, enabled = true)
	public void DeleteWebUrl() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;
		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");

		String url = words[0] + "//" + words[2] + postObject.get("PostUrlTypeAttach").get("RequestURL")
				+ OptyNumber + "/" + "child/Attachment"+"/"+urlAttachKey;
		System.out.println(url);
		//Object param1 = postObject.get("PostUrlTypeAttach").get("Payloads");
		//String parameters = param1.toString();

		// CreateOpprespo = RestCommonUtils.getRequest(url, Collections.<String,
		// String>emptyMap(), "sales_admin","Welcome1");
		CreateOpprespo = RestCommonUtils.deleteRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 204);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//****************************************************************************************************************************************************************************************************************
	@Test(description = "This testcase is used for Deleting Opportunity", priority = 12, enabled = true)
	public void DeleteOpportunity() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		String str = GetDriverInstance.EnvironmentName;
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;
		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");
		String url = words[0] + "//" + words[2] + postObject.get("DeleteOpportunity").get("RequestURL")
				+ OptyNumber;
		System.out.println(url);
		// https://sam42911.fa.dc1.c9dev2.oraclecorp.com:443/crmRestApi/resources/11.13.18.05/opportunities/CDRM_119505/child/ChildRevenue/300100185589619
		CreateOpprespo = RestCommonUtils.deleteRequest(url, Collections.<String, String>emptyMap(), "sales_admin",
				"Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());

		try {

			Assert.assertEquals(CreateOpprespo.getStatusCode(), 204);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//****************************************************************************************************************************************************************************************************************		
	@Test(description = "This testcase is used for Create Opportunity with multiple Attachments", priority = 13, enabled = true)
	public void CreateOppWithMultipleAttachment() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		
		String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		words = str.split("/");
		System.out.println(words[0] + words[2]);
		Response CreateOpprespo;

		postObject = RestCommonUtils.parseJson("AttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments.json");
		Log.info("Reading data from Attachments.json file");
		
		String url = words[0] + "//" + words[2] + postObject.get("CreateOppWithAttach").get("RequestURL");
				
		System.out.println("Opp URL: "+url);
		Object param1 = postObject.get("CreateOppWithAttach").get("Payloads");
		String parameters = param1.toString();
		String oppName = "RestOppWithAttachment_"+time;
		System.out.println("OpportunityWithAttachment Name: "+ oppName);
		parameters = parameters.replace("<RestOppWithAttachment>", oppName);
		System.out.println("OpportunityWithAttachment Updated Payload: "+ parameters);

		CreateOpprespo = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1),
				"sales_admin");
		
		Log.info("getStatusCode for indexcreationresponse:" + CreateOpprespo.getStatusCode());
		Log.info("indexcreationresponse:" + CreateOpprespo.getBody().asString());
		
		try {
			//System.out.println("status line: "+CreateOpprespo.getStatusLine().replace("HTTP/1.1 ", ""));
			Assert.assertEquals(CreateOpprespo.getStatusCode(), 201, "Opportunity REST - Opportunity with multiple attachments is not created.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

// ****************************************************************************************************************************************************************************************************************
	
}
