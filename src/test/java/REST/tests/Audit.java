package REST.tests;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;


import io.restassured.response.Response;
import UtilClasses.REST.RestCommonUtils;



public class Audit {
	
//****************************************************************************************************************************************************************************************************************	
	@Test(description="This testcase is used for Profile Option RestAPI",priority=1,enabled=true)
	public void testcase01() throws FileNotFoundException, IOException, ParseException
	{
		Map<String,List<Object>> postObject;
		Map<String,String> responseObject;
		//String response;
		Response response;
	
		String updatedPayLoad;
		//postObject = RestCommonUtils.parseJson("Audit", ".\\Data\\wsData.json");
		/*response=RestCommonUtils.postRequest(postObject.get("getaudithistory").get(0).toString(),postObject.get("getaudithistory").get(1).toString(),"application/json","internal_auditor_m1","Welcome1");
	   	   
	   System.out.println("Response code -> "+response.getStatusCode());
	   Assert.assertEquals(response.getStatusCode()==200, true , "Request is successful");
	   
	   try {
		   responseObject = RestCommonUtils.responseParser(response,"auditData","userName","description","qualifiedBusinessObject");
		   System.out.println("Number of child objects -> "+(responseObject.size())/3);
		   for(int Cobjindex = 0; Cobjindex < (responseObject.size())/3; Cobjindex++) {
			   System.out.println(responseObject.get("userName"+Cobjindex));
			   System.out.println(responseObject.get("description"+Cobjindex));
			   System.out.println(responseObject.get("qualifiedBusinessObject"+Cobjindex));
		   }
		   
		   updatedPayLoad  = RestCommonUtils.updatePayload(postObject.get("getaudithistory").get(2), "fromDate", "2019-05-24","toDate","2020-03-23").toString();
			System.out.println(updatedPayLoad);
	   } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   //Assert.assertTrue(bodyStringValue.contains("qualifiedBusinessObject"));   //paramterize
	
	//   response.getBody().*/
	    
	}
	
//****************************************************************************************************************************************************************************************************************	
/*
	@Test(description="This testcase is used for Attachment",priority=2,enabled=true)
	public void testcase02() throws FileNotFoundException, IOException, ParseException
	{
		
		String response;
	
		
	    String Req_URL = RestCommonUtils.ReadJSONFile("Request_Post", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String fromDate = RestCommonUtils.ReadJSONFile("fromDate_2", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String toDate = RestCommonUtils.ReadJSONFile("toDate_2", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String product   = RestCommonUtils.ReadJSONFile("product_2", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String businessObjectType = RestCommonUtils.ReadJSONFile("businessObjectType_2", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String includeChildObjects = RestCommonUtils.ReadJSONFile("includeChildObjects_2", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String includeAttributes = RestCommonUtils.ReadJSONFile("includeAttributes_2", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String attributeDetailMode = RestCommonUtils.ReadJSONFile("attributeDetailMode_2", "./src/main/java/ConfigurationResources/REST/wsData.json");

	   System.out.println(Req_URL);
	    //response = _postReq.postRequest(Req_URL,"{\"fromDate\":\"+fromDate+\",\"toDate\":\"+toDate+\",\"product\":\"+product+\",\"businessObjectType\":\"+businessObjectType+\",\"includeChildObjects\":\"+includeChildObjects+\",\"includeAttributes\":\"+includeAttributes+\",\"attributeDetailMode\":\"+attributeDetailMode+\"}");
	   response=RestCommonUtils.postRequest(Req_URL,"{\"fromDate\":"+fromDate+",\"toDate\":"+toDate+",\"product\":"+product+",\"businessObjectType\":"+businessObjectType+",\"includeChildObjects\":"+includeChildObjects+",\"includeAttributes\":"+includeAttributes+",\"attributeDetailMode\":"+attributeDetailMode+"}","application/json","internal_auditor","Welcome1");
	   //String bodyStringValue = response.toString();
	
	   
	   Assert.assertEquals(response.contains("200") , true , "Request is successful");
	   //Assert.assertTrue(bodyStringValue.contains("qualifiedBusinessObject"));   //paramterize
	
	    
	}*/
	
	
//****************************************************************************************************************************************************************************************************************
	@Test(description="This testcase is used for KFF",priority=3,enabled=true)
	public void testcase03() throws FileNotFoundException, IOException, ParseException
	{
		
		String response;
	
		
	    String Req_URL = RestCommonUtils.ReadJSONFile("Request_Post", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String fromDate = RestCommonUtils.ReadJSONFile("fromDate_3", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String toDate = RestCommonUtils.ReadJSONFile("toDate_3", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String product   = RestCommonUtils.ReadJSONFile("product_3", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String businessObjectType = RestCommonUtils.ReadJSONFile("businessObjectType_3", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String includeChildObjects = RestCommonUtils.ReadJSONFile("includeChildObjects_3", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String includeAttributes = RestCommonUtils.ReadJSONFile("includeAttributes_3", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String attributeDetailMode = RestCommonUtils.ReadJSONFile("attributeDetailMode_3", "./src/main/java/ConfigurationResources/REST/wsData.json");

	   System.out.println(Req_URL);
	   //response=RestCommonUtils.postRequest(Req_URL,"{\"fromDate\":"+fromDate+",\"toDate\":"+toDate+",\"product\":"+product+",\"businessObjectType\":"+businessObjectType+",\"includeChildObjects\":"+includeChildObjects+",\"includeAttributes\":"+includeAttributes+",\"attributeDetailMode\":"+attributeDetailMode+"}","application/json","internal_auditor","Welcome1");
	   //String bodyStringValue = response.toString();
	
	   
	  // Assert.assertEquals(response.contains("200") , true , "Request is successful");
	   //Assert.assertTrue(bodyStringValue.contains("qualifiedBusinessObject"));   //paramterize
	
	    
	}
	
	
	
//****************************************************************************************************************************************************************************************************************
	@Test(description="This testcase is used for Validating OPSS Data",priority=4,enabled=true)
	public void testcase04() throws FileNotFoundException, IOException, ParseException
	{
		
		String response;
	
		
	    String Req_URL = RestCommonUtils.ReadJSONFile("Request_Post", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String fromDate = RestCommonUtils.ReadJSONFile("fromDate_4", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String toDate = RestCommonUtils.ReadJSONFile("toDate_4", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String product   = RestCommonUtils.ReadJSONFile("product_4", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String includeAttributes = RestCommonUtils.ReadJSONFile("includeAttributes_4", "./src/main/java/ConfigurationResources/REST/wsData.json");
	    String eventType = RestCommonUtils.ReadJSONFile("eventType_4", "./src/main/java/ConfigurationResources/REST/wsData.json");
	

	   System.out.println(Req_URL);
	    //response = _postReq.postRequest(Req_URL,"{\"fromDate\":\"+fromDate+\",\"toDate\":\"+toDate+\",\"product\":\"+product+\",\"businessObjectType\":\"+businessObjectType+\",\"includeChildObjects\":\"+includeChildObjects+\",\"includeAttributes\":\"+includeAttributes+\",\"attributeDetailMode\":\"+attributeDetailMode+\"}");
	   //response=RestCommonUtils.postRequest(Req_URL,"{\"fromDate\":"+fromDate+",\"toDate\":"+toDate+",\"product\":"+product+",\"includeAttributes\":"+includeAttributes+",\"eventType\":"+eventType+"}","application/json","internal_auditor","Welcome1");
	   //String bodyStringValue = response.toString();
	
	   
	   //Assert.assertEquals(response.contains("200") , true , "Request is successful");
	   //Assert.assertTrue(bodyStringValue.contains("qualifiedBusinessObject"));   //paramterize
	
	 
	}	
	
	
//****************************************************************************************************************************************************************************************************************	

}
