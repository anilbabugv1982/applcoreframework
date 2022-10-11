package REST.tests;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.REST.SOAPCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.Log;

import TestBase.UI.GetDriverInstance;
import org.testng.annotations.BeforeClass;


public class KFFSOAPTest {

	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	String response;
	static String BaseUrl;
	static String uniqueID;
	static String ExportStatus;
	static Integer count=0;
	static Map<String, Map<String,String>> queryResult;
	
	//Get Json Object and URL
	@BeforeClass(alwaysRun = true,description="Prequisite: Parse KFF REST Json")
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
		String env=GetDriverInstance.EnvironmentName;
		Integer index=env.indexOf("/fscmUI/");
		BaseUrl=env.substring(0,index);
		System.out.println(BaseUrl);
        uniqueID = CommonUtils.uniqueId();
	}
	
	public boolean parseResponse(Response resp) {
		if(resp.getStatusCode()==200) {
			StringBuilder returnstatus=new StringBuilder();
			returnstatus.append(resp.asString());
			return returnstatus.toString().indexOf("status=\"SUCCESS\"") !=-1? true: false; 		
	}
		System.out.println("Status code is not 200, its .."+resp.getStatusCode());
		return false;
	}
	
	//Deploy Flex using SOAP
	@Test(description="Deploy COST",priority=600,enabled=true)
	public void COST_deployFlexUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlex.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "COST", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}	
	
	
	@Test(description="Deploy CAT#",priority=601,enabled=true)
	public void CAT_deployFlexUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlex.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "CAT#", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	
	@Test(description="Deploy LOC#",priority=602,enabled=true)
	public void LOC_deployFlexUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlex.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "LOC#", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	@Test(description="Deploy KEY#",priority=603,enabled=true)
	public void KEY_deployFlexUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlex.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "KEY#", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	
	@Test(description="Deploy MCAT",priority=604,enabled=true)
	public void MCAT_deployFlexUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlex.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "MCAT", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	//Deploy Flex Async
	
	@Test(description="Deploy MTLL",priority=605,enabled=true)
	public void MTLL_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "MTLL", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	
	@Test(description="Deploy VRM",priority=606,enabled=true)
	public void VRM_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "VRM", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	@Test(description="Deploy CONS",priority=607,enabled=true)
	public void CONS_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "CONS", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	@Test(description="Deploy VALU",priority=608,enabled=true)
	public void VALU_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "VALU", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	@Test(description="Deploy PPG",priority=609,enabled=true)
	public void PPG_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "PPG", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	@Test(description="Deploy XCC",priority=610,enabled=true)
	public void XCC_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "XCC", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	//Deploy Patched
	@Test(description="Update the status for some of the KFF",priority=611,enabled=true)
	public void update_statusKFF() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
			Log.info("Update KFF Status");
			System.out.println("Update KFF Status");
			try {
			updateKFFStatus("CONS","READY_INC");
			updateKFFStatus("COST","EDITED");
			updateKFFStatus("VRM","EDITED_READY");
			updateKFFStatus("MDSP","ERROR"); 
			updateKFFStatus("LOC#","SANDBOXED"); 
			updateKFFStatus("MCAT","READY");
			updateKFFStatus("GL#","EDITED");
			}
			catch(Exception e) {
				System.out.println("Exception while updating KFF statuses");
				e.printStackTrace();
			}
			
	}
	
	public void updateKFFStatus(String kffCode, String status) {
		int result_row;
		try {
			result_row = DbResource.updateDB("update FND_KF_FLEXFIELDS_VL set deployment_status = '"+status+"' where key_flexfield_code = '"+kffCode+"'");
			if (result_row>0) {
				Log.info("KFF Status updated successfully");
				System.out.println("Code -"+kffCode+" Updated status is :"+status);
			}
			else
				System.out.println("Code -"+kffCode+" Not Updated as :"+status);
		} catch (SQLException e) {
			System.out.println("Problem with DB connection");
			e.printStackTrace();
		}
	}
	
	
	@Test(description="Deploy All Patched Flex ",priority=612,enabled=true)
	public void deployPatchedFlexUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployPatchedFlex.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
        Response response=SOAPCommonUtils.postPatchedSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	//Deploy Patched
		@Test(description="Update the status for some of the KFF",priority=613,enabled=true)
		public void update_statusMoreKFF() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
				Log.info("Update KFF Status");
				System.out.println("Update KFF Status");
				try {
				//updateKFFStatus("GL#","READY_INC");
				updateKFFStatus("KEY#","EDITED_READY");
				updateKFFStatus("VALU","READY_INC");
				updateKFFStatus("CAT#","ERROR");
				}
				catch(Exception e) {
					System.out.println("Exception while updating KFF statuses");
					e.printStackTrace();
				}
				
		}
	//Deploy Patched Flex Async
	@Test(description="Deploy All Patched Flex Async",priority=614,enabled=true)
	public void deployPatchedFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException, SocketException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployPatchedFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		Response response=SOAPCommonUtils.postPatchedSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	{
        	System.out.println("Response is null");
        	}
	}
	
	//Check the status of KFF after deployment
	@Test(description="Deploy All Patched Flex Async",priority=615,enabled=true)
	public void checkDeploymentStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
        CommonUtils.hold(200);
		try {
			queryResult = DbResource.queryExecute("select * from FND_KF_FLEXFIELDS_VL","KEY_FLEXFIELD_CODE","DEPLOYMENT_STATUS");
			System.out.println("db result");
			for(Entry<String, Map<String,String>> dbresult : queryResult.entrySet()) {
				String depStatus=dbresult.getValue().get("DEPLOYMENT_STATUS").toString();
				String KFFCode=dbresult.getValue().get("KEY_FLEXFIELD_CODE").toString();
				if(KFFCode.matches("COST|MDSP|LOC#|CAT#"))
					Assert.assertTrue(depStatus.matches("SANDBOXED|ERROR|EDITED"));
				else {
					if(!KFFCode.matches("GL#"))
						Assert.assertTrue(depStatus.equalsIgnoreCase("DEPLOYED"));
				}
			}
			
			System.out.println("Deployment status are compared after SOAP call");
		}catch(Exception lSBN) {
			System.out.println("Exception in checkDeploymentStatus()");
			Assert.fail();
		}finally {
			//responseObject.clear();
			queryResult.clear();
		}    
	}
	
	//Only for GL#
	@Test(description="Deploy All Patched Flex Async only for GL#",priority=616,enabled=true)
	public void deployPatchedFlexAsyncGLUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException, SocketException
	{	
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployPatchedFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		
		try {
			   updateKFFStatus("GL#","READY_INC");
			}
			catch(Exception e) {
				System.out.println("Exception while updating KFF statuses");
				e.printStackTrace();
			}
		
		Response response=SOAPCommonUtils.postPatchedSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	{
        	System.out.println("Response is null, GL takes more time");
        	CommonUtils.hold(1200); //Wait for GL to be deployed. Takes about 17 to 20 mins.
        	Assert.assertTrue(true);
        	}
	}
	
	@Test(description="Deploy MDSP",priority=617,enabled=true)
	public void MDSP_deployFlexAsyncUsingSOAP() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		String SOAPWSDL_XMLLocation = "./src/main/java/ConfigurationResources/SOAP/deployFlexAsync.xml";
		String SOAPAction="/fscmService/ApplicationsCoreSetupService";
		try {
			updateKFFStatus("MDSP","EDITED_READY"); 
		}
		catch(Exception e) {
			System.out.println("Exception while updating KFF statuses");
			e.printStackTrace();
		}
        Response response=SOAPCommonUtils.postSOAPRequest(BaseUrl, "app_impl_consultant", "Welcome1", "MDSP", SOAPWSDL_XMLLocation, SOAPAction);
        if (response!=null) 
           Assert.assertEquals(true, parseResponse(response));
        else
        	Assert.fail("Response is null");
	}
	
	@AfterClass(alwaysRun = true)
	public void allDone_updateStatusBack() throws InterruptedException, SQLException {

		Log.info("Update KFF Status");
		System.out.println("Update KFF Status");
		try {
			updateKFFStatus("MDSP","EDITED_READY"); 
			updateKFFStatus("LOC#","EDITED_READY"); 
			updateKFFStatus("CAT#","EDITED_READY"); 
			updateKFFStatus("COST","EDITED_READY"); 
		}
		catch(Exception e) {
			System.out.println("Exception while updating KFF statuses");
			e.printStackTrace();
		}
		try {
			LOC_deployFlexUsingSOAP();
			CAT_deployFlexUsingSOAP();
			COST_deployFlexUsingSOAP();
			MDSP_deployFlexAsyncUsingSOAP();
		}
		catch(Exception e) {
			System.out.println("Exception while deploying Flexfields - LOC|CAT|COST");
		}
		
		
	}
	
	
	
	
	
}