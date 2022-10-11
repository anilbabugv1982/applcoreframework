package UI.tests.Flex.EFF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import UtilClasses.UI.DbResource;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFFileValidations;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;

public class EFFStatusFlipDeploymentTests {
	public EFFFileValidations effFileValidation;
	public File flexDeployZipFile;
	private EFFUtils effUtils;
	private static String logsFolder;
	private static String srcLogsPath;
	private static String deployedLog;
	private static String patchEditLog;
	private static String patchEditReadyLog;
	private static String patchErrorLog;
	private static String patchReadyLog;
	private static String patchSandBoxLog;
	private static String eff_FlexCode;
	private static String dff_FlexCode;
	private static String kff_FlexCode;
	private static String all_FlexType;
	private static String status_EDITEDDEPLOYED;
	private static String status_ERRORDEPLOYED;
	private static String status_READYDEPLOYED;
	private static String status_SANDBOXDEPLOYED;
	private static String status_DEPLOYED;
	private static String status_EDITEDREADYDEPLOYED;
	private static String status_ERROR;
	private static String status_SANDBOXED;
	private static String status_EDITED;
	private static String resultPassed;
	private static String resultFailed;
	private static String cmdDeployFlex;
	private static String cmdDeployPatchedFlex;
	private File logsDir;
	private HashMap<String,String> effDeployFlexResults;
	private HashMap<String,String> dffDeployFlexResults;
	private HashMap<String,String> kffDeployFlexResults;
	private HashMap<String,String> effDeployPatchedFlexResults;
	private HashMap<String,String> dffDeployPatchedFlexResults;
	private HashMap<String,String> kffDeployPatchedFlexResults;
	
	static {
		logsFolder = "C:/deployStatusChangeTestLogs";
		srcLogsPath = "/u01/APPLTOP/fusionapps/atgpf/lcm/ad/bin";
		deployedLog = srcLogsPath+"/patch_deployed.log";
		patchEditLog = srcLogsPath+"/patch_edit.log";
		patchEditReadyLog = srcLogsPath+"/patch_editready.log";
		patchErrorLog = srcLogsPath+"/patch_error.log";
		patchReadyLog = srcLogsPath+"/patch_ready.log";
		patchSandBoxLog = srcLogsPath+"/patch_sandbox.log";
		eff_FlexCode = "PER_LOCATION_INFORMATION_EFF";
        dff_FlexCode = "INV_GRADES";
        kff_FlexCode = "COST";
	    all_FlexType = "ALL deployPatchedFlex";
        status_EDITEDDEPLOYED = "EDITED - DEPLOYED";
    	status_ERRORDEPLOYED = "ERROR - DEPLOYED";
    	status_READYDEPLOYED = "READY - DEPLOYED";
    	status_SANDBOXDEPLOYED = "SANDBOXED - DEPLOYED";
    	status_DEPLOYED = "DEPLOYED - DEPLOYED";
    	status_EDITEDREADYDEPLOYED = "EDITED_READY - DEPLOYED";
    	status_ERROR = "ERROR - ERROR";
    	status_SANDBOXED = "SANDBOXED  - SANDBOXED";
    	status_EDITED = "EDITED - EDITED";
    	resultPassed = "PASS";
    	resultFailed = "FAIL";
    	cmdDeployFlex = "deployFlex";
    	cmdDeployPatchedFlex = "deployPatchedFlex";
	}
	
	
	
	@BeforeClass
	public void startUp() throws JSchException, ClassNotFoundException, SQLException {
		effFileValidation = new EFFFileValidations();
		
		effDeployFlexResults = new HashMap<String,String>();
		dffDeployFlexResults = new HashMap<String,String>();
		kffDeployFlexResults = new HashMap<String,String>();
		effDeployPatchedFlexResults = new HashMap<String,String>();
		dffDeployPatchedFlexResults = new HashMap<String,String>();
		kffDeployPatchedFlexResults = new HashMap<String,String>();
		logsDir = new File(String.valueOf(logsFolder));
		effFileValidation.connectToDB();
	}
	
	public void getResults(ArrayList<String> completeResult) {
		for(String currentLine:completeResult) {
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_EDITEDDEPLOYED)) {
				effDeployFlexResults.put(status_EDITEDDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_EDITEDDEPLOYED)) {
				dffDeployFlexResults.put(status_EDITEDDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_EDITEDDEPLOYED)) {
				kffDeployFlexResults.put(status_EDITEDDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_ERRORDEPLOYED)) {
				effDeployFlexResults.put(status_ERRORDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_ERRORDEPLOYED)) {
				dffDeployFlexResults.put(status_ERRORDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_ERRORDEPLOYED)) {
				kffDeployFlexResults.put(status_ERRORDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_READYDEPLOYED)) {
				effDeployFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_READYDEPLOYED)) {
				dffDeployFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_READYDEPLOYED)) {
				kffDeployFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_SANDBOXDEPLOYED)) {
				effDeployFlexResults.put(status_SANDBOXDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_SANDBOXDEPLOYED)) {
				dffDeployFlexResults.put(status_SANDBOXDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_SANDBOXDEPLOYED)) {
				kffDeployFlexResults.put(status_SANDBOXDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_DEPLOYED)) {
				effDeployFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_DEPLOYED)) {
				dffDeployFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_DEPLOYED)) {
				kffDeployFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_ERROR)) {
				effDeployPatchedFlexResults.put(status_ERROR, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_ERROR)) {
				dffDeployPatchedFlexResults.put(status_ERROR, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_ERROR)) {
				kffDeployPatchedFlexResults.put(status_ERROR, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(all_FlexType) && currentLine.contains(status_ERROR)) {
				effDeployPatchedFlexResults.put(status_ERROR, resultPassed);
				dffDeployPatchedFlexResults.put(status_ERROR, resultPassed);
				kffDeployPatchedFlexResults.put(status_ERROR, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_READYDEPLOYED)) {
				effDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_READYDEPLOYED)) {
				dffDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_READYDEPLOYED)) {
				kffDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(all_FlexType) && currentLine.contains(status_READYDEPLOYED)) {
				effDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
				dffDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
				kffDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_EDITED)) {
				effDeployPatchedFlexResults.put(status_EDITED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_EDITED)) {
				dffDeployPatchedFlexResults.put(status_EDITED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_EDITED)) {
				kffDeployPatchedFlexResults.put(status_EDITED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(all_FlexType) && currentLine.contains(status_EDITED)) {
				effDeployPatchedFlexResults.put(status_EDITED, resultPassed);
				dffDeployPatchedFlexResults.put(status_EDITED, resultPassed);
				kffDeployPatchedFlexResults.put(status_EDITED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_SANDBOXED)) {
				effDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_SANDBOXED)) {
				dffDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_SANDBOXED)) {
				kffDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(all_FlexType) && currentLine.contains(status_SANDBOXED)) {
				effDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
				dffDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
				kffDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_DEPLOYED)) {
				effDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_DEPLOYED)) {
				dffDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_DEPLOYED)) {
				kffDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(all_FlexType) && currentLine.contains(status_DEPLOYED)) {
				effDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
				dffDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
				kffDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(eff_FlexCode) && currentLine.contains(status_EDITEDREADYDEPLOYED)) {
				effDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(dff_FlexCode) && currentLine.contains(status_EDITEDREADYDEPLOYED)) {
				dffDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(kff_FlexCode) && currentLine.contains(status_EDITEDREADYDEPLOYED)) {
				kffDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
			}
			if(currentLine.contains(cmdDeployPatchedFlex) && currentLine.contains(all_FlexType) && currentLine.contains(status_EDITEDREADYDEPLOYED)) {
				effDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
				dffDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
				kffDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
			}
		}
	}
	
	public void updateStatus(HashMap<String,String> currentFlexResults, String currentStatus) {
		if(!currentFlexResults.get(currentStatus).equals(resultPassed) || currentFlexResults.get(currentStatus).equals(null) ) {
			currentFlexResults.replace(currentStatus, resultFailed);
		}
	}
	
	public String executeSelectQuery(String flexCode,String flexType) throws SQLException{
		String deployStatus = null;
		if(flexType.equals("KFF")) {
			ResultSet result = DbResource.sqlStatement.executeQuery("select deployment_status from FND_kF_FLEXFIELDS_B where KEY_FLEXFIELD_CODE = '"+flexCode+"'");
	        while(result.next()) {
	        	deployStatus = result.getString("DEPLOYMENT_STATUS");
	        }
	        System.out.println("Current Deployment Status of flexcode - "+flexCode+" is - "+deployStatus);
		}
		else {
			ResultSet result = DbResource.sqlStatement.executeQuery("select deployment_status from FND_dF_FLEXFIELDS_B where DESCRIPTIVE_FLEXFIELD_CODE = '"+flexCode+"'");
	        while(result.next()) {
	        	deployStatus = result.getString("DEPLOYMENT_STATUS");
	        }
	        System.out.println("Current Deployment Status of flexcode - "+flexCode+" is - "+deployStatus);
		}
		
        return deployStatus;
	}
	
	@Test(priority = 1,description="Validate READY to DEPLOYED status change test for EFF through wlst command")
	public void validateREADYtoDEPLOYEDForEFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		/*effFileValidation.uploadFileToFAHost("c:/BATS/flex_dep.zip", "/u01/flexdep");*/
		
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "READY"));
		effFileValidation.executeUpdateQueryForIndividualFlexStatusChange("READY", "PER_LOCATION_INFORMATION_EFF");
		effFileValidation.executeCommandDeployFlex(eff_FlexCode, "EFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		effDeployFlexResults.put(status_READYDEPLOYED, resultPassed);
		/*effFileValidation.executeStatusFlipScripts();
		Assert.assertTrue(effFileValidation.bValidateFlexDepComplete, "DeployFlex script execution is successful");
		Assert.assertTrue(effFileValidation.bValidateFlexDepComplete, "DeployPatchedFlex script execution is successful");*/
	}
	
	@Test(priority = 2,description="Validate READY to DEPLOYED status change test for DFF through wlst command")
	public void validateREADYtoDEPLOYEDForDFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "READY"));
		effFileValidation.executeCommandDeployFlex(dff_FlexCode, "DFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		dffDeployFlexResults.put(status_READYDEPLOYED, resultPassed);
	}
	
	@Test(priority = 3,description="Validate READY to DEPLOYED status change test for KFF through wlst command")
	public void validateREADYtoDEPLOYEDForKFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "READY"));
		effFileValidation.executeCommandDeployFlex(kff_FlexCode, "KFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		kffDeployFlexResults.put(status_READYDEPLOYED, resultPassed);
	}
	
	@Test(priority = 4,description="Validate REDEPLOY status change test for EFF through wlst command")
	public void validateREDEPLOYForEFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		effFileValidation.executeCommandDeployFlex(eff_FlexCode, "EFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		effDeployFlexResults.put(status_DEPLOYED, resultPassed);
	}
	
	@Test(priority = 5,description="Validate REDEPLOY status change test for DFF through wlst command")
	public void validateREDEPLOYForDFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		effFileValidation.executeCommandDeployFlex(dff_FlexCode, "DFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		dffDeployFlexResults.put(status_DEPLOYED, resultPassed);
	}
	
	@Test(priority = 6,description="Validate REDEPLOY status change test for KFF through wlst command")
	public void validateREDEPLOYForKFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		effFileValidation.executeCommandDeployFlex(kff_FlexCode, "KFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		kffDeployFlexResults.put(status_DEPLOYED, resultPassed);
	}
	
	@Test(priority = 7,description="Validate EDITED to DEPLOYED status change test for EFF through wlst command")
	public void validateEDITEDtoDEPLOYEDForEFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "EDITED"));
		effFileValidation.executeCommandDeployFlex(eff_FlexCode, "EFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		effDeployFlexResults.put(status_EDITEDDEPLOYED, resultPassed);
	}
	
	@Test(priority = 8,description="Validate EDITED to DEPLOYED status change test for DFF through wlst command")
	public void validateEDITEDtoDEPLOYEDForDFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "EDITED"));
		effFileValidation.executeCommandDeployFlex(dff_FlexCode, "DFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		dffDeployFlexResults.put(status_EDITEDDEPLOYED, resultPassed);
	}
	
	@Test(priority = 9,description="Validate EDITED to DEPLOYED status change test for KFF through wlst command")
	public void validateEDITEDtoDEPLOYEDForKFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "EDITED"));
		effFileValidation.executeCommandDeployFlex(kff_FlexCode, "KFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		kffDeployFlexResults.put(status_EDITEDDEPLOYED, resultPassed);
	}
	
	@Test(priority = 10,description="Validate ERROR to DEPLOYED status change test for EFF through wlst command")
	public void validateERRORtoDEPLOYEDForEFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "ERROR"));
		effFileValidation.executeCommandDeployFlex(eff_FlexCode, "EFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		effDeployFlexResults.put(status_ERRORDEPLOYED, resultPassed);
	}
	
	@Test(priority = 11,description="Validate ERROR to DEPLOYED status change test for DFF through wlst command")
	public void validateERRORtoDEPLOYEDForDFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "ERROR"));
		effFileValidation.executeCommandDeployFlex(dff_FlexCode, "DFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		dffDeployFlexResults.put(status_ERRORDEPLOYED, resultPassed);
	}
	
	@Test(priority = 12,description="Validate ERROR to DEPLOYED status change test for KFF through wlst command")
	public void validateERRORtoDEPLOYEDForKFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "ERROR"));
		effFileValidation.executeCommandDeployFlex(kff_FlexCode, "KFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		kffDeployFlexResults.put(status_ERRORDEPLOYED, resultPassed);
	}
	
	@Test(priority = 13,description="Validate SANDBOXED to DEPLOYED status change test for EFF through wlst command")
	public void validateSANDBOXEDtoDEPLOYEDForEFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "SANDBOXED"));
		effFileValidation.executeCommandDeployFlex(eff_FlexCode, "EFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		effDeployFlexResults.put(status_SANDBOXDEPLOYED, resultPassed);
	}
	
	@Test(priority = 14,description="Validate ERROR to DEPLOYED status change test for DFF through wlst command")
	public void validateSANDBOXEDtoDEPLOYEDForDFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "SANDBOXED"));
		effFileValidation.executeCommandDeployFlex(dff_FlexCode, "DFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		dffDeployFlexResults.put(status_SANDBOXDEPLOYED, resultPassed);
	}
	
	@Test(priority = 15,description="Validate ERROR to DEPLOYED status change test for KFF through wlst command")
	public void validateSANDBOXEDtoDEPLOYEDForKFF() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "SANDBOXED"));
		effFileValidation.executeCommandDeployFlex(kff_FlexCode, "KFF");
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		kffDeployFlexResults.put(status_SANDBOXDEPLOYED, resultPassed);
	}
	
	@Test(priority = 16,description="Validate EDITED_READY to DEPLOYED status change test for all flex types through wlst command")
	public void validateEDITED_READYtoDEPLOYEDForALLFlexTypesUsingDeployPatchedFlex() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "EDITED_READY"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "EDITED_READY"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "EDITED_READY"));
		effFileValidation.executeFlexDeployment();
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		Assert.assertEquals(executeSelectQuery(eff_FlexCode,"EFF"),"DEPLOYED", "EFF deployment script execution is successful");
		effDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
		Assert.assertEquals(executeSelectQuery(dff_FlexCode,"DFF"),"DEPLOYED", "DFF deployment script execution is successful");
		dffDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
		Assert.assertEquals(executeSelectQuery(kff_FlexCode,"KFF"),"DEPLOYED", "KFF deployment script execution is successful");
		kffDeployPatchedFlexResults.put(status_EDITEDREADYDEPLOYED, resultPassed);
	}
	
	@Test(priority = 17,description="Validate REDEPLOY test for all flex types through deployPatchedFlex() wlst command")
	public void validateREDEPLOYForALLFlexTypesUsingDeployPatchedFlex() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));
		effFileValidation.executeFlexDeployment();
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		Assert.assertEquals(executeSelectQuery(eff_FlexCode,"EFF"),"DEPLOYED", "EFF deployment script execution is successful");
		effDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
		Assert.assertEquals(executeSelectQuery(dff_FlexCode,"DFF"),"DEPLOYED", "DFF deployment script execution is successful");
		dffDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
		Assert.assertEquals(executeSelectQuery(kff_FlexCode,"KFF"),"DEPLOYED", "KFF deployment script execution is successful");
		kffDeployPatchedFlexResults.put(status_DEPLOYED, resultPassed);
	}
	
	@Test(priority = 18,description="Validate ERROR as ERROR for deployPatchedFlex() test for all flex types through wlst command")
	public void validateERRORasERRORForALLFlexTypesUsingDeployPatchedFlex() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		/*DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));*/
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "ERROR"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "ERROR"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "ERROR"));
		effFileValidation.executeFlexDeployment();
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		Assert.assertEquals(executeSelectQuery(eff_FlexCode,"EFF"),"ERROR", "EFF deployment script execution is successful");
		effDeployPatchedFlexResults.put(status_ERROR, resultPassed);
		Assert.assertEquals(executeSelectQuery(dff_FlexCode,"DFF"),"ERROR", "DFF deployment script execution is successful");
		dffDeployPatchedFlexResults.put(status_ERROR, resultPassed);
		Assert.assertEquals(executeSelectQuery(kff_FlexCode,"KFF"),"ERROR", "KFF deployment script execution is successful");
		kffDeployPatchedFlexResults.put(status_ERROR, resultPassed);
	}
	
	@Test(priority = 19,description="Validate READY to DEPLOYED for deployPatchedFlex() test for all flex types through wlst command")
	public void validateREADYtoDEPLOYEDForALLFlexTypesUsingDeployPatchedFlex() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "READY"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "READY"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "READY"));
		effFileValidation.executeFlexDeployment();
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		Assert.assertEquals(executeSelectQuery(eff_FlexCode,"EFF"),"DEPLOYED", "EFF deployment script execution is successful");
		effDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
		Assert.assertEquals(executeSelectQuery(dff_FlexCode,"DFF"),"DEPLOYED", "DFF deployment script execution is successful");
		dffDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
		Assert.assertEquals(executeSelectQuery(kff_FlexCode,"KFF"),"DEPLOYED", "KFF deployment script execution is successful");
		kffDeployPatchedFlexResults.put(status_READYDEPLOYED, resultPassed);
	}
	
	@Test(priority = 20,description="Validate SANDBOXED as SANDBOXED for deployPatchedFlex() test for all flex types through wlst command")
	public void validateSANDBOXEDasSANDBOXEDForALLFlexTypesUsingDeployPatchedFlex() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "SANDBOXED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "SANDBOXED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "SANDBOXED"));
		effFileValidation.executeFlexDeployment();
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		Assert.assertEquals(executeSelectQuery(eff_FlexCode,"EFF"),"SANDBOXED", "EFF deployment script execution is successful");
		effDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
		Assert.assertEquals(executeSelectQuery(dff_FlexCode,"DFF"),"SANDBOXED", "DFF deployment script execution is successful");
		dffDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
		Assert.assertEquals(executeSelectQuery(kff_FlexCode,"KFF"),"SANDBOXED", "KFF deployment script execution is successful");
		kffDeployPatchedFlexResults.put(status_SANDBOXED, resultPassed);
	}
	
	@Test(priority = 21,description="Validate EDITED as EDITED for deployPatchedFlex() test for all flex types through wlst command")
	public void validateEDITEDasEDITEDForALLFlexTypesUsingDeployPatchedFlex() throws SQLException, JSchException, IOException, InterruptedException, ClassNotFoundException {
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForAllFlex("DEPLOYED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(eff_FlexCode, "EDITED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualEFFDFFFlex(dff_FlexCode, "EDITED"));
		DbResource.sqlStatement.executeUpdate(effFileValidation.updateStatusForIndividualKFFFlex(kff_FlexCode, "EDITED"));
		effFileValidation.executeFlexDeployment();
		Assert.assertEquals(effFileValidation.FlexDeploymentStatus,"SUCCESS", "DeployFlex script execution is successful");
		Assert.assertEquals(executeSelectQuery(eff_FlexCode,"EFF"),"EDITED", "EFF deployment script execution is successful");
		effDeployPatchedFlexResults.put(status_EDITED, resultPassed);
		Assert.assertEquals(executeSelectQuery(dff_FlexCode,"DFF"),"EDITED", "DFF deployment script execution is successful");
		dffDeployPatchedFlexResults.put(status_EDITED, resultPassed);
		Assert.assertEquals(executeSelectQuery(kff_FlexCode,"KFF"),"EDITED", "KFF deployment script execution is successful");
		kffDeployPatchedFlexResults.put(status_EDITED, resultPassed);
	}
	
	/*@Test(dependsOnMethods = {"uploadFlexDeployZipFile"},description="Copying log files from remote machine to local machine")
	public void downloadLogFiles() {
		if(logsDir.exists()) {
			System.out.println("logs Directory exists. Deleting directory");
			logsDir.delete();
			System.out.println("Recreating logs directory");
			logsDir.mkdir();
		}
		else {
			System.out.println("logs Directory does not exists by default. Creating directory");
			logsDir.mkdir();
		}
		effFileValidation.copyDeployFlexlogFile(deployedLog,logsFolder);
		effFileValidation.copyDeployFlexlogFile(patchEditLog,logsFolder);
		effFileValidation.copyDeployFlexlogFile(patchEditReadyLog,logsFolder);
		effFileValidation.copyDeployFlexlogFile(patchErrorLog,logsFolder);
		effFileValidation.copyDeployFlexlogFile(patchReadyLog,logsFolder);
		effFileValidation.copyDeployFlexlogFile(patchSandBoxLog,logsFolder);
		File depLogFile = new File(logsFolder+"/"+deployedLog);
		File patchEditLogFile = new File(logsFolder+"/"+patchEditLog);
		File patchEditReadyLogFile = new File(logsFolder+"/"+patchEditReadyLog);
		File patchErrorLogFile = new File(logsFolder+"/"+patchErrorLog);
		File patchReadyLogFile = new File(logsFolder+"/"+patchReadyLog);
		File patchSandboxedLogFile = new File(logsFolder+"/"+patchSandBoxLog);
		Assert.assertTrue(depLogFile.exists(), "DeployLog copied successfully");
		Assert.assertTrue(patchEditLogFile.exists(), "PatchEditLog copied successfully");
		Assert.assertTrue(patchEditReadyLogFile.exists(), "PatchEditReadyLog copied successfully");
		Assert.assertTrue(patchErrorLogFile.exists(), "PatchErrorLog copied successfully");
		Assert.assertTrue(patchReadyLogFile.exists(), "PatchReadyLog copied successfully");
		Assert.assertTrue(patchSandboxedLogFile.exists(), "PatchSandBoxLog copied successfully");
		
	}*/
	
	/*@Test(dependsOnMethods = {"uploadFlexDeployZipFilex"},description="Validating results for DeployFlex")
	public void validatedeployFlexResults() {
		getResults(effFileValidation.consoleOutput);
		updateStatus(effDeployFlexResults, status_EDITEDDEPLOYED);
		updateStatus(dffDeployFlexResults, status_EDITEDDEPLOYED);
		updateStatus(kffDeployFlexResults, status_EDITEDDEPLOYED);
		updateStatus(effDeployFlexResults, status_ERRORDEPLOYED);
		updateStatus(dffDeployFlexResults, status_ERRORDEPLOYED);
		updateStatus(kffDeployFlexResults, status_ERRORDEPLOYED);
		updateStatus(effDeployFlexResults, status_READYDEPLOYED);
		updateStatus(dffDeployFlexResults, status_READYDEPLOYED);
		updateStatus(kffDeployFlexResults, status_READYDEPLOYED);
		updateStatus(effDeployFlexResults, status_SANDBOXDEPLOYED);
		updateStatus(dffDeployFlexResults, status_SANDBOXDEPLOYED);
		updateStatus(kffDeployFlexResults, status_SANDBOXDEPLOYED);
		updateStatus(effDeployFlexResults, status_DEPLOYED);
		updateStatus(dffDeployFlexResults, status_DEPLOYED);
		updateStatus(kffDeployFlexResults, status_DEPLOYED);
		updateStatus(effDeployPatchedFlexResults, status_ERROR);
		updateStatus(dffDeployPatchedFlexResults, status_ERROR);
		updateStatus(kffDeployPatchedFlexResults, status_ERROR);
		updateStatus(effDeployPatchedFlexResults, status_READYDEPLOYED);
		updateStatus(dffDeployPatchedFlexResults, status_READYDEPLOYED);
		updateStatus(kffDeployPatchedFlexResults, status_READYDEPLOYED);
		updateStatus(effDeployPatchedFlexResults, status_EDITED);
		updateStatus(dffDeployPatchedFlexResults, status_EDITED);
		updateStatus(kffDeployPatchedFlexResults, status_EDITED);
		updateStatus(effDeployPatchedFlexResults, status_SANDBOXED);
		updateStatus(dffDeployPatchedFlexResults, status_SANDBOXED);
		updateStatus(kffDeployPatchedFlexResults, status_SANDBOXED);
		updateStatus(effDeployPatchedFlexResults, status_DEPLOYED);
		updateStatus(dffDeployPatchedFlexResults, status_DEPLOYED);
		updateStatus(kffDeployPatchedFlexResults, status_DEPLOYED);
		updateStatus(effDeployPatchedFlexResults, status_EDITEDREADYDEPLOYED);
		updateStatus(dffDeployPatchedFlexResults, status_EDITEDREADYDEPLOYED);
		updateStatus(kffDeployPatchedFlexResults, status_EDITEDREADYDEPLOYED);
		Assert.assertEquals(effDeployFlexResults.get(status_EDITEDDEPLOYED), "PASS");
		Assert.assertEquals(dffDeployFlexResults.get(status_EDITEDDEPLOYED), "PASS");
		Assert.assertEquals(kffDeployFlexResults.get(status_EDITEDDEPLOYED), "PASS");
		Assert.assertEquals(effDeployFlexResults.get(status_ERRORDEPLOYED), "PASS");
		Assert.assertEquals(dffDeployFlexResults.get(status_ERRORDEPLOYED), "PASS");
		Assert.assertEquals(kffDeployFlexResults.get(status_ERRORDEPLOYED), "PASS");
		Assert.assertEquals(effDeployFlexResults.get(status_READYDEPLOYED), "PASS");
		Assert.assertEquals(dffDeployFlexResults.get(status_READYDEPLOYED), "PASS");
		Assert.assertEquals(kffDeployFlexResults.get(status_READYDEPLOYED), "PASS");
		Assert.assertEquals(effDeployFlexResults.get(status_SANDBOXDEPLOYED), "PASS");
		Assert.assertEquals(dffDeployFlexResults.get(status_SANDBOXDEPLOYED), "PASS");
		Assert.assertEquals(kffDeployFlexResults.get(status_SANDBOXDEPLOYED), "PASS");
		Assert.assertEquals(effDeployFlexResults.get(status_DEPLOYED), "PASS");
		Assert.assertEquals(dffDeployFlexResults.get(status_DEPLOYED), "PASS");
		Assert.assertEquals(kffDeployFlexResults.get(status_DEPLOYED), "PASS");
	}*/
	
	/*@Test(dependsOnMethods = {"validatedeployFlexResults"},description="Validating results for DeployPatchedFlex")
	public void validateDeployPatchedFlexResults() {
		Assert.assertEquals(effDeployPatchedFlexResults.get(status_ERROR), "PASS");
		Assert.assertEquals(dffDeployPatchedFlexResults.get(status_ERROR), "PASS");
		Assert.assertEquals(kffDeployPatchedFlexResults.get(status_ERROR), "PASS");
		Assert.assertEquals(effDeployPatchedFlexResults.get(status_SANDBOXED), "PASS");
		Assert.assertEquals(dffDeployPatchedFlexResults.get(status_SANDBOXED), "PASS");
		Assert.assertEquals(kffDeployPatchedFlexResults.get(status_SANDBOXED), "PASS");
		Assert.assertEquals(effDeployPatchedFlexResults.get(status_EDITED), "PASS");
		Assert.assertEquals(dffDeployPatchedFlexResults.get(status_EDITED), "PASS");
		Assert.assertEquals(kffDeployPatchedFlexResults.get(status_EDITED), "PASS");
		Assert.assertEquals(effDeployPatchedFlexResults.get(status_READYDEPLOYED), "PASS");
		Assert.assertEquals(dffDeployPatchedFlexResults.get(status_READYDEPLOYED), "PASS");
		Assert.assertEquals(kffDeployPatchedFlexResults.get(status_READYDEPLOYED), "PASS");
		Assert.assertEquals(effDeployPatchedFlexResults.get(status_EDITEDREADYDEPLOYED), "PASS");
		Assert.assertEquals(dffDeployPatchedFlexResults.get(status_EDITEDREADYDEPLOYED), "PASS");
		Assert.assertEquals(kffDeployPatchedFlexResults.get(status_EDITEDREADYDEPLOYED), "PASS");
		Assert.assertEquals(effDeployPatchedFlexResults.get(status_DEPLOYED), "PASS");
		Assert.assertEquals(dffDeployPatchedFlexResults.get(status_DEPLOYED), "PASS");
		Assert.assertEquals(kffDeployPatchedFlexResults.get(status_DEPLOYED), "PASS");
	}*/
	
	public void generateHtmlReport() throws IOException {
		File input = new File(System.getProperty("user.dir") + "/src/test/resources/StatusChangeDeploymentTestTemplate.html");
        Document document = Jsoup.parse(input, "UTF-8");
        //DeployFlex table
        Element editdeploy = document.getElementById("editdeploy");
        Element errordeploy = document.getElementById("errordeploy");
        Element readydeploy = document.getElementById("readydeploy");
        Element sandboxdeploy = document.getElementById("sandboxdeploy");
        Element deployed = document.getElementById("deployed");
        //DeployPatchedFlex table
        Element error = document.getElementById("error");
        Element sandboxed = document.getElementById("sandboxed");
        Element redeploy = document.getElementById("redeploy");
        Element edited = document.getElementById("edited");
        Element readydeploypatchedflex = document.getElementById("readydeploypatchedflex");
        Element editedreadydeploy = document.getElementById("editedreadydeploy");
        
        editdeploy.append("<td>"+effDeployFlexResults.get(status_EDITEDDEPLOYED)+"</td>");
        editdeploy.append("<td>"+dffDeployFlexResults.get(status_EDITEDDEPLOYED)+"</td>");
        editdeploy.append("<td>"+kffDeployFlexResults.get(status_EDITEDDEPLOYED)+"</td>");
        errordeploy.append("<td>"+effDeployFlexResults.get(status_ERRORDEPLOYED)+"</td>");
        errordeploy.append("<td>"+dffDeployFlexResults.get(status_ERRORDEPLOYED)+"</td>");
        errordeploy.append("<td>"+kffDeployFlexResults.get(status_ERRORDEPLOYED)+"</td>");
        readydeploy.append("<td>"+effDeployFlexResults.get(status_READYDEPLOYED)+"</td>");
        readydeploy.append("<td>"+dffDeployFlexResults.get(status_READYDEPLOYED)+"</td>");
        readydeploy.append("<td>"+kffDeployFlexResults.get(status_READYDEPLOYED)+"</td>");
        sandboxdeploy.append("<td>"+effDeployFlexResults.get(status_SANDBOXDEPLOYED)+"</td>");
        sandboxdeploy.append("<td>"+dffDeployFlexResults.get(status_SANDBOXDEPLOYED)+"</td>");
        sandboxdeploy.append("<td>"+kffDeployFlexResults.get(status_SANDBOXDEPLOYED)+"</td>");
        deployed.append("<td>"+effDeployFlexResults.get(status_DEPLOYED)+"</td>");
        deployed.append("<td>"+dffDeployFlexResults.get(status_DEPLOYED)+"</td>");
        deployed.append("<td>"+kffDeployFlexResults.get(status_DEPLOYED)+"</td>");
        
        error.append("<td>"+effDeployPatchedFlexResults.get(status_ERROR)+"</td>");
        error.append("<td>"+dffDeployPatchedFlexResults.get(status_ERROR)+"</td>");
        error.append("<td>"+kffDeployPatchedFlexResults.get(status_ERROR)+"</td>");
        sandboxed.append("<td>"+effDeployPatchedFlexResults.get(status_SANDBOXED)+"</td>");
        sandboxed.append("<td>"+dffDeployPatchedFlexResults.get(status_SANDBOXED)+"</td>");
        sandboxed.append("<td>"+kffDeployPatchedFlexResults.get(status_SANDBOXED)+"</td>");
        redeploy.append("<td>"+effDeployPatchedFlexResults.get(status_DEPLOYED)+"</td>");
        redeploy.append("<td>"+dffDeployPatchedFlexResults.get(status_DEPLOYED)+"</td>");
        redeploy.append("<td>"+kffDeployPatchedFlexResults.get(status_DEPLOYED)+"</td>");
        edited.append("<td>"+effDeployPatchedFlexResults.get(status_EDITED)+"</td>");
        edited.append("<td>"+dffDeployPatchedFlexResults.get(status_EDITED)+"</td>");
        edited.append("<td>"+kffDeployPatchedFlexResults.get(status_EDITED)+"</td>");
        readydeploypatchedflex.append("<td>"+effDeployPatchedFlexResults.get(status_READYDEPLOYED)+"</td>");
        readydeploypatchedflex.append("<td>"+dffDeployPatchedFlexResults.get(status_READYDEPLOYED)+"</td>");
        readydeploypatchedflex.append("<td>"+kffDeployPatchedFlexResults.get(status_READYDEPLOYED)+"</td>");
        editedreadydeploy.append("<td>"+effDeployPatchedFlexResults.get(status_EDITEDREADYDEPLOYED)+"</td>");
        editedreadydeploy.append("<td>"+dffDeployPatchedFlexResults.get(status_EDITEDREADYDEPLOYED)+"</td>");
        editedreadydeploy.append("<td>"+kffDeployPatchedFlexResults.get(status_EDITEDREADYDEPLOYED)+"</td>");
        OutputStream outputStream = new FileOutputStream(new File("StatusChangeDeploymentTest.html"));
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(document.toString());
        writer.close();
	}
	
	@AfterClass
	public void close() throws NullPointerException, SQLException {
		try {
			generateHtmlReport();
			//localFile.delete();
			effFileValidation.closeAll();
		} catch (SftpException | JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
