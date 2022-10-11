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


public class VerticalCSMRestImport { 	
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	String response;
	static String RestUrl;
	static String VerticalCSSetId="0";
	static String importStatus,restoreStatus;
	static String uniqueID;
	static Integer count=0;
	
	public String getURL(String keyDescription) {
		
		return RestUrl+postObject.get(keyDescription).get(1).toString();
	}
	
	public String getParameters(String keyDescription) {
		String parameter=postObject.get(keyDescription).get(2).toString();
		if(parameter.contains("_Date")) {
			String temp=parameter.replace("_Date", uniqueID);
			return temp;
		}  
		if(parameter.contains("RESTORE_ID")) {
			String temp=parameter.replace("RESTORE_ID", VerticalCSSetId);
			return temp;
		}  
		return parameter;
	}
	
	//Get Json Object and URL
	@BeforeClass(alwaysRun = true)
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
			postObject = RestCommonUtils.parseUSBJson("VERTICALSCSMUSB","./src/main/java/ConfigurationResources/REST/VerticalsCSM.json");
			String env=GetDriverInstance.EnvironmentName;
			Integer index=env.indexOf("/fscmUI/");
			RestUrl=env.substring(0,index);
			System.out.println(RestUrl);
	        uniqueID = CommonUtils.uniqueId();
	        //CsSetId = VerticalCSMRestExport.CsSetId;
			
		}
	
	@Test(description="Get Vertical Info",priority=1000,enabled=true)
	public void getVerticalInfo() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
			{		
				Response getusbresponse;
				Log.info("Get Vertical Info");
				String URL=getURL("GetVerticalInfo");
				System.out.println(URL);
				getusbresponse = RestCommonUtils.getUSBRequest(URL);
				Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
				System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
				System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());	
			}
		        
	
	//Import Migration Set
	@Test(description="Import Migration Set",priority=1010,enabled=true)
	public void importMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
			{		
				        
				Response usbresponse;
				Log.info("Import Customization ");
				String URL=getURL("ImportCustomization");
				String parameters=getParameters("ImportCustomization");
				usbresponse = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
				Log.info("USB Creation Response from  Server");
				System.out.println("getStatusCode for usbcreationresponse:  "+usbresponse.getStatusCode());
				System.out.println("usbcreationresponse:"+usbresponse.getBody().asString());
				Assert.assertEquals(usbresponse.getStatusCode() == 201, true, "Request is successful");
				
				//Get Sandbox ID 
				if(usbresponse.getStatusCode() == 201) {
					JsonPath jsonPathEvaluator = usbresponse.jsonPath();
					VerticalCSSetId=jsonPathEvaluator.get("id");
					System.out.println("Import started and its CS Set id is :"+VerticalCSSetId);
				}	
					
				
			}
	
	//Get Import Status
	@Test(description="Get Import Status",priority=1020,enabled=true)
	public void getImportStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
					{		
		//Import Status - LOADED,IMPORT STARTED,IN_PROGRESS,BACKUP_DONE,
				        Response  import_response;
						Log.info("Get Method for getting import status");
						while(count<100) {
							    System.out.println(count++);
								import_response=getCSMVerticalImportStatus(VerticalCSSetId);
								if(import_response.getStatusCode() == 200) {
									JsonPath jsonPathEvaluator = import_response.jsonPath();
									importStatus=jsonPathEvaluator.get("Status");	
									System.out.println("import Status is :"+importStatus);		
									
									if(importStatus.equalsIgnoreCase("ERROR") || importStatus.equalsIgnoreCase("FAIL"))
									{   
										System.out.println("Cannot be imported");
										System.out.println("Getimport Body:" +import_response.getBody().asString());			
										System.out.println("import Status is  "+importStatus);
										Assert.fail("Cannot be imported"+ import_response.getBody().asString());
										return;
									}
									else if(importStatus.equalsIgnoreCase("APPLIED_COMPACTED"))
									{
										System.out.println("imported Successfully");
										System.out.println("import Status is  "+importStatus);
										Assert.assertTrue(true);
										return;
									}
									
									CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
								    
								}
								else 
								{
									Assert.fail("Status code is not 200"+import_response.getStatusCode());
								}
								System.out.println("import Status is  "+importStatus);
								
						}
					}
	
	
		
	

	//Restore Migration Set
		@Test(description="restore Migration Set",priority=1030,enabled=true) //dependsOnMethods="importMigrationSet" to be included
		public void restoreMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
				{				        
					Response usbresponse;
					Response import_response;
					import_response=getCSMVerticalImportStatus(VerticalCSSetId);
					//Status - RESTORED, ERROR 
					if(getStatusCode(import_response) == 200) {
							if(getStatus(import_response).equalsIgnoreCase("APPLIED_COMPACTED")) //If applied then start to Restore the CSM Set
							{ 
									Log.info("Post Method for restoring Migration Set ");
									String URL=getURL("RestoreCustomization");
									String replacedURL=URL.replace("id",VerticalCSSetId);
									String parameters=getParameters("RestoreCustomization");

									System.out.println(replacedURL);
									usbresponse = RestCommonUtils.postRequest(replacedURL,parameters,"app_impl_consultant"); 
									Log.info("restore Response from  Server");
									System.out.println("restoreMigrationSet Status code :  "+usbresponse.getStatusCode());
									System.out.println("restoreMigrationSet Response :"+usbresponse.getBody().asString());
									Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Response code is"+usbresponse.getStatusCode());
									
									//Check the error message
									if(usbresponse.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = usbresponse.jsonPath();
										usbresponse.prettyPrint();
										System.out.println("restore Status  "+jsonPathEvaluator.get("status"));
										System.out.println("Message is ::   "+jsonPathEvaluator.get("Message"));
										if (jsonPathEvaluator.get("Message").toString().contains(("Unexpected error")))
												Assert.fail("Unexpected Error while restoreing Migration set id.."+VerticalCSSetId);
										else if (jsonPathEvaluator.get("status").toString().equals(("restore started")))
					                            assertTrue(true);
									}
							}

					}
							
					
				}
	
		
		@Test(description="Get Restore Status",priority=1040,enabled=true)
		public void getRestoreStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
						{		
			
			 //restore Status - LOADED,restore STARTED,IN_PROGRESS,BACKUP_DONE
					        Response  restore_response;
							Log.info("Get Method for getting restore status");
							while(true) {
								    System.out.println(count++);
									restore_response=getCSMVerticalRestoreStatus(VerticalCSSetId);
									if(restore_response.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = restore_response.jsonPath();
										restoreStatus=jsonPathEvaluator.get("Status");	
										System.out.println("restore Status is :"+restoreStatus);		
										
										if(restoreStatus.equalsIgnoreCase("Error"))
										{   
											System.out.println("Cannot be restoreed");
											System.out.println("Getrestore Body:" +restore_response.getBody().asString());			
											System.out.println("restore Status is  "+restoreStatus);
											Assert.fail("Cannot be restoreed"+ restore_response.getBody().asString());
											return;
										}
										else if(restoreStatus.equalsIgnoreCase("CREATED"))
										{
											System.out.println("restoreed Successfully");
											System.out.println("restore Status is  "+restoreStatus);
											Assert.assertTrue(true);
											return;
										}
										else if(restoreStatus.equalsIgnoreCase("IN_PROGRESS") || restoreStatus.equalsIgnoreCase("BACKUP_DONE"))
										{
											CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
										}
											
									}
									else 
									{
										Assert.fail("Status code is not 200"+restore_response.getStatusCode());
									}
									System.out.println("restore Status is  "+restoreStatus);
									
							}
						}
		
		
		//Import Migration Set
		@Test(description="Import Migration Set",priority=1050,enabled=true)
		public void directImportMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
				{		
					        
					Response usbresponse;
					Log.info("Import Customization ");
					String URL=getURL("ImportCustomization");
					String parameters=getParameters("ImportCustomization");
					usbresponse = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
					Log.info("USB Creation Response from  Server");
					System.out.println("getStatusCode for usbcreationresponse:  "+usbresponse.getStatusCode());
					System.out.println("usbcreationresponse:"+usbresponse.getBody().asString());
					Assert.assertEquals(usbresponse.getStatusCode() == 201, true, "Request is successful");
					
					//Get Sandbox ID 
					if(usbresponse.getStatusCode() == 201) {
						JsonPath jsonPathEvaluator = usbresponse.jsonPath();
						VerticalCSSetId=jsonPathEvaluator.get("id");
						System.out.println("Import started and its CS Set id is :"+VerticalCSSetId);
					}	
						
					
				}
		
		//Get Import Status
		@Test(description="Get Import Status",priority=1060,enabled=true)
		public void getDirectImportStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
						{		
			//Import Status - LOADED,IMPORT STARTED,IN_PROGRESS,BACKUP_DONE,
					        Response  import_response;
							Log.info("Get Method for getting import status");
							while(count<100) {
								    System.out.println(count++);
									import_response=getCSMVerticalImportStatus(VerticalCSSetId);
									if(import_response.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = import_response.jsonPath();
										importStatus=jsonPathEvaluator.get("Status");	
										System.out.println("import Status is :"+importStatus);		
										
										if(importStatus.equalsIgnoreCase("ERROR") || importStatus.equalsIgnoreCase("FAIL"))
										{   
											System.out.println("Cannot be imported");
											System.out.println("Getimport Body:" +import_response.getBody().asString());			
											System.out.println("import Status is  "+importStatus);
											Assert.fail("Cannot be imported"+ import_response.getBody().asString());
											return;
										}
										else if(importStatus.equalsIgnoreCase("APPLIED_COMPACTED"))
										{
											System.out.println("imported Successfully");
											System.out.println("import Status is  "+importStatus);
											Assert.assertTrue(true);
											return;
										}
										
										CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
									    
									}
									else 
									{
										Assert.fail("Status code is not 200"+import_response.getStatusCode());
									}
									System.out.println("import Status is  "+importStatus);
									
							}
						}
		
		
			
		//Restore Direct Import Migration Set
		@Test(description="restore Migration Set",priority=1070,enabled=true) //dependsOnMethods="importMigrationSet" to be included
		public void restoreDirectImportMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
				{				        
					Response usbresponse;
					Response import_response;
					import_response=getCSMVerticalImportStatus(VerticalCSSetId);
					//Status - RESTORED, ERROR 
					if(getStatusCode(import_response) == 200) {
							if(getStatus(import_response).equalsIgnoreCase("APPLIED_COMPACTED")) //If applied then start to Restore the CSM Set
							{ 
									Log.info("Post Method for restoring Migration Set ");
									String URL=getURL("RestoreCustomization");
									String replacedURL=URL.replace("id",VerticalCSSetId);
									String parameters=getParameters("RestoreCustomization");

									System.out.println(replacedURL);
									usbresponse = RestCommonUtils.postRequest(replacedURL,parameters,"app_impl_consultant"); 
									Log.info("restore Response from  Server");
									System.out.println("restoreMigrationSet Status code :  "+usbresponse.getStatusCode());
									System.out.println("restoreMigrationSet Response :"+usbresponse.getBody().asString());
									Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Response code is"+usbresponse.getStatusCode());
									
									//Check the error message
									if(usbresponse.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = usbresponse.jsonPath();
										usbresponse.prettyPrint();
										System.out.println("restore Status  "+jsonPathEvaluator.get("status"));
										System.out.println("Message is ::   "+jsonPathEvaluator.get("Message"));
										if (jsonPathEvaluator.get("Message").toString().contains(("Unexpected error")))
												Assert.fail("Unexpected Error while restoreing Migration set id.."+VerticalCSSetId);
										else if (jsonPathEvaluator.get("status").toString().equals(("restore started")))
					                            assertTrue(true);
									}
							}

					}				
				}
	
		
		private Response getCSMVerticalImportStatus(String CsSetId) {
					Response getusbresponse;
					String URL=getURL("GetImportStatus");
					String replacedURL=URL.replace("CSSETID",CsSetId);
					System.out.println(replacedURL);
					getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
					Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Response code is  "+getusbresponse.getStatusCode());
					System.out.println("Getimport Status Code:" +getusbresponse.getStatusCode());		
					System.out.println("Getimport Body:" +getusbresponse.getBody().asString());			
					return getusbresponse;
				}
		
		private Response getCSMVerticalRestoreStatus(String CsSetId) {
			Response getusbresponse;
			String URL=getURL("GetRestoreStatus");
			String replacedURL=URL.replace("CSSETID",CsSetId);
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
