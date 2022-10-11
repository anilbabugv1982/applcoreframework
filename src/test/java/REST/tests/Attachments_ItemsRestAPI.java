package REST.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.REST.SOAPCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import io.restassured.response.Response;

public class Attachments_ItemsRestAPI {
	
	public String[] words;
	Map<String, Map<String, Object>> postObject;
	Map<String, Map<String, String>> parsedResponse;
	String parsedResponse1; 
	String time;
	String itemName;
	
	public String itemsResponseFile;
	public String[] itemsResponseFile1;
	public String itemsResponseUrl;
	public String[] itemsResponseUrl1;
	
	
	
	private String itemKey = null;
	private String urlAttachKey = null;
	private String fileAttachKey = null;
	private ArrayList<String> attachKey = new ArrayList<String>();
	
	static String BaseUrl;
	static String uniqueID;
	private static String dID;
	private static String dDocName;

//****************************************************************************************************************************************************************
	@BeforeClass(alwaysRun = true,description="Prequisite: Initialize values")
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
		String env=GetDriverInstance.EnvironmentName;
		Integer index=env.indexOf("/fscmUI/");
		BaseUrl=env.substring(0,index);
		System.out.println(BaseUrl);
        uniqueID = CommonUtils.uniqueId();
        time = CommonUtils.time();
        itemName = "RestItem_"+time;
	}
	
//****************************************************************************************************************************************************************
	
	@Test(description="UploadFileToUCM",priority=1,enabled=true)
	public void uploadFileToUCMUsingSOAP() throws Exception
	{		
		//WSDL Name = https://sam69920.fa.dc1.c9dev2.oraclecorp.com:443/idcws/GenericSoapPort?wsdl
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/UploadFileToUCM.xml";
		String SOAPAction="/idcws/GenericSoapPort";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "pimqa", "Welcome1", "", SOAPWSDL_XMLLocation, SOAPAction);
        
        if (response!=null) {
        	System.out.println("Response Status Code: "+response.getStatusCode());
        	System.out.println("Response Body: "+response.getBody().asString());
        	Assert.assertEquals(response.getStatusCode(), 200);
        }       	
        else {
        	Assert.fail("Response is null");
        }
        	
        
        String responseBody = response.body().asString();
        int index = responseBody.indexOf("\"dID\"");
        //String did = responseBody.substring(dIDIndex);
        dID = responseBody.substring(index+6, index+12);
        dID = dID.replaceAll("[^0-9]", "");
        System.out.println("dID: "+dID); 	// This is DmVersionNumber
        
        index = responseBody.indexOf("\"dDocName\"");
        //String did = responseBody.substring(dIDIndex);
        dDocName = responseBody.substring(index+11, index+24);
        System.out.println("dDocName: "+dDocName); // This is DmDocumentId
        
	}	
	
//*****************************************************************************************************************************************************************
	@Test(description = "This testcase is used for getting Items details through RestAPI", priority = 2, enabled = true)
	public void getItemsDetailsRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		//words = str.split("/");
		//System.out.println(words[0] + words[2]);
		Response itemsResponse;

		postObject = RestCommonUtils.parseJson("ItemAttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Item.json");
		Log.info("Reading data from Attachments_Item.json file");

		String url = BaseUrl + postObject.get("ItemDetails").get("RequestURL");
		System.out.println("Get Items URL: "+ url);
		//Object param1 = postObject.get("ItemDetails").get("Payloads");
		//String parameters = param1.toString();

		itemsResponse = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),
				"pimqa", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + itemsResponse.getStatusCode());
		Log.info("indexcreationresponse:" + itemsResponse.getBody().asString());
		
		try {

			Assert.assertEquals(itemsResponse.getStatusCode(), 200, "Get Items: REST status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
//***************************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for Creating Item with Attachments through RestAPI", priority = 3, enabled = true, dependsOnMethods = {"uploadFileToUCMUsingSOAP"})
	public void createItemWithAttachmentRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		//words = str.split("/");
		//System.out.println(words[0] + words[2]);
		Response itemsResponse;

		postObject = RestCommonUtils.parseJson("ItemAttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Item.json");
		Log.info("Reading data from Attachments_Item.json file");

		String url = BaseUrl + postObject.get("PostItemWithAttachments").get("RequestURL");
		System.out.println("Create Item with Attachments URL: "+ url);
		Object param1 = postObject.get("PostItemWithAttachments").get("Payloads");
		//param1 = RestCommonUtils.updatePayload(param1, "DmDocumentId", dDocName);
		//param1 = RestCommonUtils.updatePayload(param1, "DmVersionNumber", dID);
		String parameters = param1.toString();
		System.out.println("Item Name: "+ itemName);
		parameters = parameters.replace("<RestItem>", itemName);
		parameters = parameters.replace("<UCMId>", dDocName);
		parameters = parameters.replace("<VersionNumber>", dID);
		System.out.println("Items Updated Payload: "+ parameters);

		itemsResponse = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1),
				"pimqa");
		Log.info("getStatusCode for indexcreationresponse:" + itemsResponse.getStatusCode());
		Log.info("indexcreationresponse:" + itemsResponse.getBody().asString());
		
		try {

			Assert.assertEquals(itemsResponse.getStatusCode(), 201, "ItemWithAttachments creation: REST status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Code to get itemKey, urlAttachKey, fileAttachKey 	
		parsedResponse = RestCommonUtils.responseParser(itemsResponse, "ItemAttachment", "links");
		//System.out.println("parsedResponse: "+parsedResponse);
		
		parsedResponse.entrySet().forEach(entry -> {
		    System.out.println("Key: "+entry.getKey());
		    System.out.println("Value: " +entry.getValue());
		    System.out.println("");
		    entry.getValue().entrySet().forEach(entry1 -> {
		    	System.out.println("Key1: "+entry1.getKey());
			    System.out.println("Value1: " +entry1.getValue());
			    int startIndex = entry1.getValue().indexOf("href");
			    //System.out.println("StartIndex: "+startIndex);
			    int endIndex = entry1.getValue().indexOf("\"}");
			    //System.out.println("EndIndex: "+endIndex);
			    attachKey.add(entry1.getValue().substring(startIndex, endIndex));
			    System.out.println("AttachKey: "+attachKey);
			    System.out.println("");
		    });
		    
		});
		
		String urlHref, fileHref, splitUrlHref[], splitFileHref[];
		urlHref = attachKey.get(1);
		fileHref = attachKey.get(0);
		
		splitUrlHref = urlHref.split("/");
		splitFileHref = fileHref.split("/");
		
		itemKey = splitUrlHref[7];
		if(itemKey.endsWith("\\")) {
			itemKey = itemKey.substring(0, itemKey.length()-1);
		}
		urlAttachKey = splitUrlHref[10];
		fileAttachKey = splitFileHref[10];
		
		System.out.println("itemKey: "+itemKey);
		System.out.println("urlAttachKey: "+urlAttachKey);
		System.out.println("fileAttachKey: "+fileAttachKey);
		
		
	}

//***************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for creating/updating Item with Attachments through RestAPI when Upsert Mode is true.", priority = 4, enabled = true)
	public void createUpdateItemWithAttachmentRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		//words = str.split("/");
		//System.out.println(words[0] + words[2]);
		Response itemsResponse;

		postObject = RestCommonUtils.parseJson("ItemAttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Item.json");
		Log.info("Reading data from Attachments_Item.json file");

		String url = BaseUrl + postObject.get("Create/UpdateItemWithAttachments").get("RequestURL");
		System.out.println("Create/Update Item with Attachments URL: "+url);
		Object param1 = postObject.get("Create/UpdateItemWithAttachments").get("Payloads");
		String parameters = param1.toString();
		System.out.println("Item Name: "+ itemName);
		parameters = parameters.replace("<RestItem>", itemName);
		parameters = parameters.replace("<UCMId>", dDocName);
		parameters = parameters.replace("<VersionNumber>", dID);
		System.out.println("Create/Update_Items Updated Payload: "+ parameters);

		
		Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Upsert-Mode", "true");
		itemsResponse = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "pimqa", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + itemsResponse.getStatusCode());
		Log.info("finalindexcreationresponse:" + itemsResponse.getBody().asString());
		
		try {

			Assert.assertEquals(itemsResponse.getStatusCode(), 200, "ItemWithAttachments update: REST status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
//***************************************************************************************************************************************************************
	@Test(description = "This testcase is used for updating url Attachment of the Item through RestAPI", priority = 5, enabled = true, dependsOnMethods = {"createItemWithAttachmentRestAPI"})
	public void updateUrlItemAttachmentRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		//words = str.split("/");
		//System.out.println(words[0] + words[2]);
		Response itemsResponse;

		postObject = RestCommonUtils.parseJson("ItemAttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Item.json");
		Log.info("Reading data from Attachments_Item.json file");

		String url = BaseUrl + postObject.get("PatchUrlTypeAttach").get("RequestURL")
				+ "/" + itemKey + "/child/ItemAttachment/" + urlAttachKey;
		System.out.println("Update url attachment of Item URL: "+url);
		Object param1 = postObject.get("PatchUrlTypeAttach").get("Payloads");
		String parameters = param1.toString();

		itemsResponse = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1),
				"pimqa", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + itemsResponse.getStatusCode());
		Log.info("indexcreationresponse:" + itemsResponse.getBody().asString());
		
		try {

			Assert.assertEquals(itemsResponse.getStatusCode(), 200, "Url attachment update for Items: REST status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//****************************************************************************************************************************************************************
	
	@Test(description = "This testcase is used for updating file Attachment of the Item through RestAPI", priority = 6, enabled = true, dependsOnMethods = {"createItemWithAttachmentRestAPI"})
	public void updateFileItemAttachmentRestAPI() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
		//String str = GetDriverInstance.EnvironmentName;
		// "https://sam33089.fa.dc1.c9dev2.oraclecorp.com/fscmUI/faces/FuseWelcome";
		//words = str.split("/");
		//System.out.println(words[0] + words[2]);
		Response itemsResponse;

		postObject = RestCommonUtils.parseJson("ItemAttachmentTaskflow",
				System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Attachments_Item.json");
		Log.info("Reading data from Attachments_Item.json file");

		String url = BaseUrl + postObject.get("PatchFileTypeAttach").get("RequestURL")
				+  "/" + itemKey + "/child/ItemAttachment/" + fileAttachKey;
		System.out.println("Update file attachment of Item URL: "+url);
		Object param1 = postObject.get("PatchFileTypeAttach").get("Payloads");
		String parameters = param1.toString();
		parameters = parameters.replace("<UCMId>", dDocName);
		parameters = parameters.replace("<VersionNumber>", dID);

		itemsResponse = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1),
				"pimqa", "Welcome1");
		Log.info("getStatusCode for indexcreationresponse:" + itemsResponse.getStatusCode());
		Log.info("indexcreationresponse:" + itemsResponse.getBody().asString());
		
		try {

			Assert.assertEquals(itemsResponse.getStatusCode(), 200, "File attachment update for Items: REST status code mismtach.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//****************************************************************************************************************************************************************
	

}
