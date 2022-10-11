package REST.tests.UnifiedSandbox.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.Log;
import io.restassured.response.Response;

public class UsbUiRESTServices {
	
	Map<String, Map<String,Object>> jsonObject;
	static Map<String,Map<String,String>> responseObject;
	static Map<String, Map<String,String>> queryResult;
	Map<String,String> headerInfo = new HashMap<String,String>();
	Response response;
	String executableURL;
	
	@BeforeClass()
	public void restSetup() {
		try {
			System.out.println("Json file Parsing");
			jsonObject = RestCommonUtils.parseJson("USB", "./src/main/java/ConfigurationResources/REST/UsbRest.json");
			System.out.println("Json file Parsing Completed");
			executableURL = RestCommonUtils.getExecutableHost();
			executableURL = executableURL.substring(0, executableURL.lastIndexOf("/"))+":443";
		} catch (Exception e) {
			System.out.println("Exception in parsing json file");
			e.printStackTrace();
		}
	}

	@Test(description="REST API to create Unified Sandboxes",priority=1,enabled=true)
	public void createSandbox() throws FileNotFoundException, IOException, ParseException
	{
		headerInfo.put("Content-Type", "application/json");
		String url = executableURL+jsonObject.get("createSandbox").get("RequestURL").toString();
		int payloadCount = Integer.parseInt(jsonObject.get("createSandbox").get("PayloadsCount").toString());
		System.out.println("Number Of Payloads -> "+payloadCount);
		if(payloadCount > 0) {
			for(int payload = 0; payload < payloadCount; payload++ ) {
				String payloadNameValue = null;
				String requestParameters = jsonObject.get("createSandbox").get("Payload"+payload).toString();
				response = RestCommonUtils.postRequest(url, requestParameters,headerInfo,"app_impl_consultant","Welcome1");
				Log.info("getStatusCode :" + response.getStatusCode());
				Log.info("responsebody:" + response.getBody().asString());
				try {
					if(response.getStatusCode() == 201) {
						responseObject = RestCommonUtils.responseParser(response,"", "name","constraints","features");
						System.out.println("Response -> "+responseObject);
						System.out.println("Parsing json payload to retrieve sandbox name value begins");
						String payLoad = jsonObject.get("createSandbox").get("Payload"+payload).toString();
						System.out.println("Payload Data -> "+payLoad);
						String []payLoadData = payLoad.split(",");
						for(String payLoadAttribute : payLoadData) {
							System.out.println(payLoadAttribute);
							if(payLoadAttribute.contains("name")) {
								String []nameField = payLoadAttribute.split(":");
								payloadNameValue = nameField[1].substring(nameField[1].indexOf("\"")+1, nameField[1].lastIndexOf("\""));
								System.out.println("NameValue -> "+payloadNameValue);
							}
						}
						System.out.println("Parsing json payload to retrieve sandbox name value end");
						System.out.println("Name -> "+responseObject.get(payloadNameValue).get("name"));
						System.out.println("constraints -> "+responseObject.get(payloadNameValue).get("constraints"));
						System.out.println("Features -> "+responseObject.get(payloadNameValue).get("features"));
						Assert.assertTrue(jsonObject.get("createSandbox").get("Payload"+payload).toString().contains(responseObject.get(payloadNameValue).get("name")));
						System.out.println("Sandbox Name Verified");
						Assert.assertTrue(jsonObject.get("createSandbox").get("Payload"+payload).toString().contains(responseObject.get(payloadNameValue).get("features")));
						System.out.println("Sandbox Features Verified");
					}else {
						System.out.println("Servie with Payload"+payload+" not succeeded");
						System.out.println("Post request not succeeded with statuscode -> "+response.getStatusCode());
						Assert.fail();
					}
					
				} catch (Exception e) {
					System.out.println("Exception in createSandbox()");
					e.printStackTrace();
					Assert.fail();
				}finally {
					responseObject.clear();
				}
			}
		}
		headerInfo.clear();
	}
	
	@Test(description="create sandbox with no features",priority=2,enabled=true)
	public void sandboxWoFeatures() {
		headerInfo.put("Content-Type", "application/json");
		String url = executableURL+jsonObject.get("sandboxWOFeatures").get("RequestURL").toString();
		int payloadCount = Integer.parseInt(jsonObject.get("sandboxWOFeatures").get("PayloadsCount").toString());
		if(payloadCount > 0) {
			for(int payload = 0; payload < payloadCount; payload++ ) {
				String payloadNameValue = null;
				String requestParameters = jsonObject.get("sandboxWOFeatures").get("Payload"+payload).toString();
				response = RestCommonUtils.postRequest(url, requestParameters,headerInfo,"app_impl_consultant","Welcome1");
				Log.info("getStatusCode :" + response.getStatusCode());
				Log.info("responsebody:" + response.getBody().asString());
				try {
					if(response.getStatusCode() == 201) {
						responseObject = RestCommonUtils.responseParser(response,"", "name","features");
						System.out.println("Response -> "+responseObject);
						System.out.println("Parsing json payload to retrieve sandbox name value begins");
						String payLoad = jsonObject.get("sandboxWOFeatures").get("Payload"+payload).toString();
						System.out.println("Payload Data -> "+payLoad);
						String []payLoadData = payLoad.split(",");
						for(String payLoadAttribute : payLoadData) {
							System.out.println(payLoadAttribute);
							if(payLoadAttribute.contains("name")) {
								String []nameField = payLoadAttribute.split(":");
								payloadNameValue = nameField[1].substring(nameField[1].indexOf("\"")+1, nameField[1].lastIndexOf("\""));
								System.out.println("NameValue -> "+payloadNameValue);
							}
						}
						System.out.println("Parsing json payload to retrieve sandbox name value end");
						System.out.println("Name -> "+responseObject.get(payloadNameValue).get("name"));
						System.out.println("Features -> "+responseObject.get(payloadNameValue).get("features"));
						Assert.assertTrue(jsonObject.get("sandboxWOFeatures").get("Payload"+payload).toString().contains(responseObject.get(payloadNameValue).get("name")));
						System.out.println("Sandbox Name Verified");
					}else {
						System.out.println("Servie with Payload"+payload+" not succeeded");
						System.out.println("Post request not succeeded with statuscode -> "+response.getStatusCode());
						Assert.fail();
					}
					
				} catch (Exception e) {
					System.out.println("Exception in sandboxWoFeatures()");
					e.printStackTrace();
					Assert.fail();
				}finally {
					responseObject.clear();
					
				}
			}
		}
		headerInfo.clear();
	}//End of sandboxWoFeatures()
	
	@Test(description="Creating existing sandboxes",priority=3,enabled=true)
	public void createExistingSandboxes() {
		headerInfo.put("Content-Type", "application/json");
		String url = executableURL+jsonObject.get("createSandbox").get("RequestURL").toString();
		int payloadCount = Integer.parseInt(jsonObject.get("createSandbox").get("PayloadsCount").toString());
		if(payloadCount > 0) {
			for(int payload = 0; payload < payloadCount; payload++ ) {
				String requestParameters = jsonObject.get("createSandbox").get("Payload"+payload).toString();
				response = RestCommonUtils.postRequest(url, requestParameters,headerInfo,"app_impl_consultant","Welcome1");
				Log.info("getStatusCode for indexcreationresponse:" + response.getStatusCode());
				Log.info("indexcreationresponse:" + response.getBody().asString());
				try {
					 if (response.getStatusCode() == 400) {
						responseObject = RestCommonUtils.responseParser(response,"", "title");
						System.out.println("Response -> "+responseObject);
						System.out.println("Title -> "+responseObject.get("title"));
					}else {
						System.out.println("Servie with Payload"+payload+" not succeeded");
						System.out.println("Post request not succeeded with statuscode -> "+response.getStatusCode());
						Assert.fail();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				responseObject.clear();
				
			}
		}
		headerInfo.clear();
	}//End Of createExistingSandboxes()
	
	@Test(description="Get existing Unified Sandboxes list",priority=4,enabled=true)
	public void listSandboxes() {
		try {
			String url = executableURL+jsonObject.get("listSandboxes").get("RequestURL").toString();
			response = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),"app_impl_consultant","Welcome1");
			System.out.println("Response -> "+response.body().toString());
			responseObject = RestCommonUtils.responseParser(response,"items", "name","state","creationDate");
			System.out.println("ResponseObject -> "+responseObject);
			responseObject.clear();
		} catch (Exception e) {
			System.out.println("Exception in listSandboxes()");
			e.printStackTrace();
			Assert.fail();
		}
	}//End of listSandboxes()
	
	@Test(description="Get existing Unified Sandboxes list fitered by Name",priority=5,enabled=true)
	public void listSandboxesFilterByName() {
		String conditionValue = "Sandbox";
		String filterCondition = "?name="+conditionValue;
		try {
			String url = executableURL+jsonObject.get("listSandboxes").get("RequestURL").toString()+filterCondition;
			response = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),"app_impl_consultant","Welcome1");
			System.out.println("Response -> "+response.body().toString());
			responseObject = RestCommonUtils.responseParser(response,"items", "name","name","id");
			System.out.println("REST response");
			queryResult = DbResource.queryExecute("select * from ADF_SB_SANDBOXES where NAME like '%"+conditionValue+"%'","NAME", "SANDBOX_ID");
			System.out.println("db result");
			for(Entry<String, Map<String,String>> dbresult : queryResult.entrySet()) {
				System.out.println(dbresult.getKey().toString() +"<->"+ responseObject.get(dbresult.getKey()).get("name").toString());
				System.out.println(dbresult.getValue().get("SANDBOX_ID").toString() +"<->"+ responseObject.get(dbresult.getKey()).get("id").toString());
				Assert.assertEquals(dbresult.getKey().toString(), responseObject.get(dbresult.getKey()).get("name").toString());
				Assert.assertEquals(dbresult.getValue().get("SANDBOX_ID").toString(), responseObject.get(dbresult.getKey()).get("id").toString());
			}
			System.out.println("Rest service results are compared and matched with existing data");
		}catch(Exception lSBN) {
			System.out.println("Exception in listSandboxesByName()");
			Assert.fail();
		}finally {
			responseObject.clear();
			queryResult.clear();
		}
	}//End of listSandboxesByName()
	
	@Test(description="Get list of ACTIVE Unified Sandboxes list fitered by condition 'open'",priority=6,enabled=true)
	public void listActiveSandboxes() {
		String conditionValue = "open";
		String filterCondition = "?condition="+conditionValue;
		try {
			String url = executableURL+jsonObject.get("listSandboxes").get("RequestURL").toString()+filterCondition;
			response = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),"app_impl_consultant","Welcome1");
			System.out.println("Response -> "+response.body().toString());
			responseObject = RestCommonUtils.responseParser(response,"items", "name","name","id");
			System.out.println("REST response");
			queryResult = DbResource.queryExecute("select * from ADF_SB_SANDBOXES where NAME = 'ACTIVE' ", "NAME", "SANDBOX_ID");
			System.out.println("db result");
			for(Entry<String, Map<String,String>> dbresult : queryResult.entrySet()) {
				System.out.println(dbresult.getKey().toString() +"<->"+ responseObject.get(dbresult.getKey()).get("name").toString());
				System.out.println(dbresult.getValue().get("SANDBOX_ID").toString() +"<->"+ responseObject.get(dbresult.getKey()).get("id").toString());
				Assert.assertEquals(dbresult.getKey().toString(), responseObject.get(dbresult.getKey()).get("name").toString());
				Assert.assertEquals(dbresult.getValue().get("SANDBOX_ID").toString(), responseObject.get(dbresult.getKey()).get("id").toString());
			}
			System.out.println("Rest service results are compared and matched with existing data");
		}catch(Exception lSBN) {
			System.out.println("Exception in listSandboxesByName()");
			Assert.fail();
		}finally {
			responseObject.clear();
			queryResult.clear();
		}
	}//End of listSandboxesByName()
	
	@Test(description="Get list of COMMITTED Unified Sandboxes list fitered by condition 'published'",priority=7,enabled=true)
	public void listOfPublishedSandboxes() {
		String conditionValue = "published";
		String filterCondition = "?condition="+conditionValue;
		try {
			String url = executableURL+jsonObject.get("listSandboxes").get("RequestURL").toString()+filterCondition;
			response = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),"app_impl_consultant","Welcome1");
			System.out.println("Response -> "+response.body().toString());
			responseObject = RestCommonUtils.responseParser(response,"items", "name","name","id");
			System.out.println("REST response");
			System.out.println(responseObject.toString());
			queryResult = DbResource.queryExecute("select * from ADF_SB_SANDBOXES where sandbox_state = 'COMMITTED' and ARCHIVE_FLAG is null", "NAME", "SANDBOX_ID");
			System.out.println("db result");
			System.out.println("Query result size : "+queryResult.entrySet().size());
			for(Entry<String, Map<String,String>> dbresult : queryResult.entrySet()) {
				System.out.println(dbresult.getKey().toString() +"<->"+ responseObject.get(dbresult.getKey()).get("name").toString());
				System.out.println(dbresult.getValue().get("SANDBOX_ID").toString() +"<->"+ responseObject.get(dbresult.getKey()).get("id").toString());
				Assert.assertEquals(dbresult.getKey().toString(), responseObject.get(dbresult.getKey()).get("name").toString());
				Assert.assertEquals(dbresult.getValue().get("SANDBOX_ID").toString(), responseObject.get(dbresult.getKey()).get("id").toString());
			}
			System.out.println("Rest service results are compared and matched with existing data");
		}catch(Exception lSBN) {
			System.out.println("Exception in listSandboxesByName()");
			Assert.fail();
		}finally {
			responseObject.clear();
			queryResult.clear();
		}
	}//End of listSandboxesByName()
	
	@Test(description="Get existing Unified Sandboxe features",priority=8,enabled=true)
	public void listSandboxFeatures() {
		try {
			String []features = {"oracle.apps.scm.pricing.priceExecution.serviceMappings.publicModel.ServiceMappingSandboxedFeature",
								 "oracle.apps.atk.homePage.fuse.protectedModel.AppearanceFeature",
								 "oracle.apps.atk.homePage.fuse.protectedModel.StructureFeature",
								 "oracle.apps.crmCommon.extensibility.framework.usb.AppComposerFeature",
								 "oracle.apps.hcm.hcmHomePage.protectedUiModel.dataComposer.HcmDataComposerFeature",
								 "oracle.apps.hcm.rulesConfig.publicModel.usb.DesignStudioFeature",
								 "oracle.apps.fnd.applcore.extensibility.feature.StringEditorSandboxedFeature",
								 "oracle.apps.fnd.applcore.extensions.sandbox.PageIntegrationFeature",
								 "oracle.apps.fnd.applcore.extensibility.feature.PageComposerFeature",
								 "oracle.apps.fnd.applcore.extensibility.feature.TemplateComposerFeature",
								 "oracle.apps.fnd.applcore.extensibility.feature.VBCSFeature",
								 "oracle.apps.fnd.applcore.sandbox.unified.feature.DataSecuritySandboxedFeature",
								 "oracle.apps.fnd.applcore.sandbox.unified.feature.FndLookupsSandboxedFeature",
								 "oracle.apps.fnd.applcore.sandbox.unified.feature.FndMessagesSandboxedFeature"};
			String url = executableURL+jsonObject.get("SandboxFeatures").get("RequestURL").toString();
			response = RestCommonUtils.getRequest(url, Collections.<String, String>emptyMap(),"app_impl_consultant","Welcome1");
			String responseString = response.body().asString();
			System.out.println("Response -> "+responseString);
			System.out.println("Response Code -> "+RestCommonUtils.responseStatus(response));
			if(RestCommonUtils.responseStatus(response) == 200) {
				for(String featureElement : features) {
					System.out.println("Feature -> "+featureElement);
					Assert.assertTrue(responseString.contains(featureElement));
					
				}
			}else {
				System.out.println("Response code is -> "+RestCommonUtils.responseStatus(response));
				Assert.fail();
			}
		} catch (Exception e) {
			System.out.println("Exception in listSandboxFeatures()");
			e.printStackTrace();
			Assert.fail();
		}
	}//End of listSandboxFeatures()
	
	@Test(description="Delete Sandbox based on sandbox ID",priority=9,enabled=true)
	public void deleteSandbox() {
		String toBeDeleteSandboxID =null;
		String deleteRequestUrl;
		String deletedSandboxName = null;
		try {
			queryResult = DbResource.queryExecute("select * from ADF_SB_SANDBOXES where sandbox_state = 'ACTIVE' and ARCHIVE_FLAG is null", "NAME", "SANDBOX_ID","sandbox_state");
			System.out.println("db result");
			if(!(queryResult.isEmpty())) {
				System.out.println("Query result size : "+queryResult.entrySet().size());
				Entry<String, Map<String,String>> dbresult = queryResult.entrySet().iterator().next();
				System.out.println("Key -> "+dbresult.getKey().toString());
				System.out.println(dbresult.getValue().get("NAME").toString());
				System.out.println(dbresult.getValue().get("SANDBOX_ID").toString());
				System.out.println(dbresult.getValue().get("sandbox_state").toString());
				
				toBeDeleteSandboxID = dbresult.getValue().get("SANDBOX_ID").toString();
				deletedSandboxName = dbresult.getValue().get("NAME").toString();
			}else {
				System.out.println("Atlease one active sandbox should exist to perform delete");
				Assert.fail();
			}
			queryResult.clear();
			deleteRequestUrl = executableURL+jsonObject.get("DeleteSandbox").get("RequestURL").toString()+toBeDeleteSandboxID+"?purge=false";
			System.out.println("Rquest URL -> "+deleteRequestUrl);
			response = RestCommonUtils.deleteRequest(deleteRequestUrl, Collections.<String, String>emptyMap(),"app_impl_consultant","Welcome1");
			System.out.println("Response -> "+response.body().asString());
			
			if(RestCommonUtils.responseStatus(response)==200) {
				queryResult = DbResource.queryExecute("select * from ADF_SB_SANDBOXES where sandbox_id = '"+toBeDeleteSandboxID+"'", "NAME", "SANDBOX_ID","sandbox_state");
				if(!(queryResult.isEmpty())) {
				Entry<String, Map<String,String>> dbresult = queryResult.entrySet().iterator().next();
				Assert.assertTrue(dbresult.getValue().get("sandbox_state").toString().equalsIgnoreCase("DESTROYED"));
				System.out.println("Sandbox "+deletedSandboxName+" is successfully deleted");
				}
			}else {
				System.out.println("Request failed with status code -> "+RestCommonUtils.responseStatus(response));
				Assert.fail();
			}
		}catch(Exception dSE) {
			System.out.println("Exception in deleting sandbox");
			Assert.fail();
		}finally {
			queryResult.clear();
		}
	}//End of deleteSandbox()
	
}
