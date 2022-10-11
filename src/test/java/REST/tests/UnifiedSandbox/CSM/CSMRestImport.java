package REST.tests.UnifiedSandbox.CSM;

import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

import TestBase.UI.GetDriverInstance;
import org.testng.annotations.BeforeClass;


public class CSMRestImport { 	
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	String response;
	static String RestUrl;
	static String SourceRestUrl;
	public static String CsSetId="0";
	static String importStatus,applyStatus,abortStatus,restoreStatus;
	static String uniqueID;
	static Integer count=0;
	static String ExportStatus;
	
	public String getURL(String keyDescription) {
		return RestUrl+postObject.get(keyDescription).get(1).toString();
	}
	
	public String getSourceURL(String keyDescription) {
		return SourceRestUrl+postObject.get(keyDescription).get(1).toString();
	}
	
	public String getParameters(String keyDescription) {
		String parameter=postObject.get(keyDescription).get(2).toString();
		if(parameter.contains("_Date")) {
			String temp=parameter.replace("_Date", uniqueID);
			return temp;
		}  
		return parameter;
	}
	
	//Get Json Object and URL
	@BeforeClass(alwaysRun = true)
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
			postObject = RestCommonUtils.parseUSBJson("CSMUSB","./src/main/java/ConfigurationResources/REST/CSMExpImp.json");
			String env=GetDriverInstance.Environment2Name;
			String sourceenv=GetDriverInstance.EnvironmentName;
			Integer index=env.indexOf("/fscmUI/");
			SourceRestUrl=sourceenv.substring(0,index);
			RestUrl=env.substring(0,index);
			System.out.println(RestUrl);
	        uniqueID = CommonUtils.uniqueId();
	        CsSetId = CSMRestExport.CsSetId;
	        //CsSetId = "2352539623023107";
			
		}
	
	
	//Import Migration Set
	@Test(description="Import Migration Set",priority=410,enabled=true)
	public void importMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
			{		
				Response usbresponse;				
				//Get the status of the Migration Set before importing
			    //Response import_response;
				//import_response=getCSMStatus(CsSetId);
				Response export_response;
				export_response=getCSMExportStatus(CsSetId);
				if(export_response.getStatusCode() == 200) {
					JsonPath jsonPathEvaluator = export_response.jsonPath();
					ExportStatus=jsonPathEvaluator.get("Status");	
					System.out.println("Export Status is :"+ExportStatus);
				}
				CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
				int count=0;
				while(count<5) {
				if(ExportStatus.equalsIgnoreCase("CREATED")) {
					CommonUtils.hold(30);//Sleep for 20 seconds to check the status again
				    count=5; //no need to check again
					//if(getStatusCode(import_response) == 200 && getStatus(import_response).equalsIgnoreCase("LOADED")) {
						// Import the CSM Set
							Log.info("Post Method for Importing Migration Set ");
							String URL=getURL("ImportCSMSet");
							String replacedURL=URL.replace("id",CsSetId);
							System.out.println(replacedURL);
							usbresponse = RestCommonUtils.postRequest(replacedURL,"NULL","app_impl_consultant"); //No Payload, so passing NULL
							Log.info("Import Response from  Server");
							System.out.println("importMigrationSet Status code :  "+usbresponse.getStatusCode());
							System.out.println("importMigrationSet Response :"+usbresponse.getBody().asString());
							Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Response code is"+usbresponse.getStatusCode());
							//Check the error message
							if(usbresponse.getStatusCode() == 200) {
								JsonPath jsonPathEvaluator = usbresponse.jsonPath();
								usbresponse.prettyPrint();
								System.out.println("Import Status  "+jsonPathEvaluator.get("status"));
								System.out.println("Message is ::   "+jsonPathEvaluator.get("Message"));
								if (jsonPathEvaluator.get("Message").toString().contains(("Unexpected error")))
										Assert.fail("Unexpected Error while importing Migration set id.."+CsSetId);
								else if (jsonPathEvaluator.get("status").toString().equals(("Import started")))
			                            assertTrue(true);
							}
				//}	
				}
					else 
						{ 
						System.out.println("Cannot Perform Import as its not loaded in Target Instance though export completed successfully");
						CommonUtils.hold(20);//Lets wait for some more time
						count++;
						if(count>=5) Assert.fail("Migration Set not available to import");
						}
			}
				
			}
	
	private Response getCSMExportStatus(String CsSetId) {
		Response getusbresponse;
		Log.info("Get Method for export status");
		String URL=getSourceURL("GetExportStatus");
		String replacedURL=URL.replace("id",CsSetId);
		
		System.out.println(replacedURL);
		getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("GetExport Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("GetExport Body:" +getusbresponse.getBody().asString());			
		return getusbresponse;
	}
	
	//Get Import Status
	@Test(description="Get Import Status",priority=411,enabled=true)
	public void getImportStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
					{		
		
				        Response  import_response;
						Log.info("Get Method for getting import status");
						CommonUtils.hold(30);//Sleep for 20 seconds to check the status again
						while(true) {
							    System.out.println(count++);
								import_response=getCSMStatus(CsSetId);
								if(import_response.getStatusCode() == 200) {
									JsonPath jsonPathEvaluator = import_response.jsonPath();
									importStatus=jsonPathEvaluator.get("Status");	
									System.out.println("import Status is :"+importStatus);		
									
									if(importStatus.equalsIgnoreCase("BACKUP_ERRORED") || importStatus.equalsIgnoreCase("IMPORT_ERRORED"))
									{   
										System.out.println("Cannot be imported");
										System.out.println("Getimport Body:" +import_response.getBody().asString());			
										System.out.println("import Status is  "+importStatus);
										Assert.fail("Cannot be imported"+ import_response.getBody().asString());
										return;
									}
									else if(importStatus.equalsIgnoreCase("IMPORT_SUCCESS"))
									{
										System.out.println("imported Successfully");
										System.out.println("import Status is  "+importStatus);
										Assert.assertTrue(true);
										return;
									}
									else if(importStatus.equalsIgnoreCase("BACKUP_IN_PROGRESS") || importStatus.equalsIgnoreCase("IMPORT_IN_PROGRESS") || importStatus.equalsIgnoreCase("BACKUP_SUCCESS"))
									{
										CommonUtils.hold(25);//Sleep for 20 seconds to check the status again
										
									}	
								}
								else 
								{
									Assert.fail("Status code is not 200"+import_response.getStatusCode());
									return;
								}
								System.out.println("import Status is  "+importStatus);
								
						}
					}
	
	
	//Apply Migration Set
	@Test(description="apply Migration Set",priority=412,enabled=true,dependsOnMethods="getImportStatus") 
	public void applyMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
			{		
		       
				Response usbresponse;
				Response import_response;
				CommonUtils.hold(30);//Sleep for 20 seconds to check the status again
				import_response=getCSMStatus(CsSetId);		
				if(getStatusCode(import_response) == 200) {
						if(getStatus(import_response).equalsIgnoreCase("IMPORT_SUCCESS")) //If imported then start to Apply the CSM Set
						{   
							    CommonUtils.hold(50); //Just wait for sometime before Apply
							    Log.info("Post Method for applying Migration Set ");
								String URL=getURL("ApplyCSMSet");
								String replacedURL=URL.replace("id",CsSetId);
								System.out.println(replacedURL);
								usbresponse = RestCommonUtils.postRequest(replacedURL,"NULL","app_impl_consultant"); //No Payload, so passing NULL
								Log.info("Apply Response from  Server");
								System.out.println("ApplyMigrationSet Status code :  "+usbresponse.getStatusCode());
								System.out.println("ApplyMigrationSet Response :"+usbresponse.getBody().asString());
								Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Response code is"+usbresponse.getStatusCode());
								
								//Check the error message
								if(usbresponse.getStatusCode() == 200) {
									JsonPath jsonPathEvaluator = usbresponse.jsonPath();
									usbresponse.prettyPrint();
									System.out.println("Apply Status  "+jsonPathEvaluator.get("status"));
									System.out.println("Message is ::   "+jsonPathEvaluator.get("Message"));
									if (jsonPathEvaluator.get("Message").toString().contains(("Unexpected error")))
											Assert.fail("Unexpected Error while applying Migration set id.."+CsSetId);
									else if (jsonPathEvaluator.get("status").toString().equalsIgnoreCase(("Apply started")))
				                            assertTrue(true);
								}
						}

				}
				
			}
	
	
	//Get Apply Status
	@Test(description="Get Apply Status",priority=413,enabled=true)
	public void getApplyStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
					{		
		
		     //apply Status - LOADED,apply STARTED,IN_PROGRESS,BACKUP_DONE,
		     //APPLIED_COMPACTED, ERROR
				        Response  response;
				        CommonUtils.hold(50);//Sleep for 20 seconds to check the status again
						Log.info("Get Method for getting Apply status");
						while(true) {
							    System.out.println(count++);
								response=getCSMStatus(CsSetId);
								if(response.getStatusCode() == 200) {
									JsonPath jsonPathEvaluator = response.jsonPath();
									applyStatus=jsonPathEvaluator.get("Status");	
									System.out.println("apply Status is :"+applyStatus);		
									
									if(applyStatus.equalsIgnoreCase("APPLY_ERRORED"))
									{   
										System.out.println("Cannot be applied");
										System.out.println("Getapply Body:" +response.getBody().asString());			
										System.out.println("apply Status is  "+applyStatus);
										Assert.fail("Cannot be applied"+ response.getBody().asString());
										getRestoreStatus(); //Since Apply failed, it has to restore internally. check the status
										return;
									}
									else if(applyStatus.equalsIgnoreCase("APPLY_SUCCESS"))
									{
										System.out.println("applied Successfully");
										System.out.println("apply Status is  "+applyStatus);
										Assert.assertTrue(true);
										return;
									}
									else if(applyStatus.equalsIgnoreCase("APPLY_IN_PROGRESS"))
									{
										CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
									}
										
								}
								else 
								{
									Assert.fail("Status code is not 200"+response.getStatusCode());
									return;
								}
								System.out.println("apply Status is  "+applyStatus);
								
						}
					}
	
	
	private void getRestoreStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		

        Response  restore_response;
        CommonUtils.hold(30);//Sleep for 30 seconds to check the status again
		Log.info("Get Method for getting restore status");
		while(true) {
			    System.out.println(count++);
				restore_response=getCSMStatus(CsSetId);
				if(restore_response.getStatusCode() == 200) {
					JsonPath jsonPathEvaluator = restore_response.jsonPath();
					restoreStatus=jsonPathEvaluator.get("Status");	
					System.out.println("restore Status is :"+restoreStatus);		
					
					if(restoreStatus.equalsIgnoreCase("RESTORE_ERRORED"))
					{   
						System.out.println("Cannot be restoreed");
						System.out.println("Getrestore Body:" +restore_response.getBody().asString());			
						System.out.println("restore Status is  "+restoreStatus);
						Assert.fail("Cannot be restoreed"+ restore_response.getBody().asString());
						return;
					}
					else if(restoreStatus.equalsIgnoreCase("RESTORE_SUCCESS"))
					{
						System.out.println("restoreed Successfully");
						System.out.println("restore Status is  "+restoreStatus);
						Assert.assertTrue(true);
						return;
					}
					else if(restoreStatus.equalsIgnoreCase("RESTORE_IN_PROGRESS") || restoreStatus.equalsIgnoreCase("APPLY_ERRORED") )
					{
						CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
					}
						
				}
				else 
				{
					Assert.fail("Status code is not 200"+restore_response.getStatusCode());
					return;
				}
				System.out.println("restore Status is  "+restoreStatus);
				
		}
	}


		private Response getCSMStatus(String CsSetId) {
					Response getusbresponse;
					String URL=getURL("GetImportStatus");
					String replacedURL=URL.replace("id",CsSetId);
					System.out.println(replacedURL);
					getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
					Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Response code is  "+getusbresponse.getStatusCode());
					System.out.println("Getimport Status Code:" +getusbresponse.getStatusCode());		
					System.out.println("Getimport Body:" +getusbresponse.getBody().asString());			
					return getusbresponse;
				}
		
				
		private String getStatus(Response response) {
			
			return response.jsonPath().get("Status");
		
		}
		
       private Integer getStatusCode(Response response) {
			
			return response.getStatusCode();
		
		}
		
	}
