package UI.tests.Flex.EFF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;

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

public class FlexDeployment {
	public EFFFileValidations effFileValidation;
	private String logsFolder;
	private File logsDir;
	private File localFile;
	private String flexDeploymentStatus;
	private String timeTaken;
	@BeforeClass
	public void startUp() throws JSchException, SQLException, ClassNotFoundException {
		effFileValidation = new EFFFileValidations();
		logsFolder = "C:/FlexDeploymentLogs";
		logsDir = new File(String.valueOf(logsFolder));
		flexDeploymentStatus = null;
		timeTaken = null;
	}
	
	@Test(description="Connect to wlst on FAHost machine and execute FlexDeployment")
	public void executeFlexDeploymentOnFAHost() throws JSchException, IOException, InterruptedException, SQLException, ClassNotFoundException {
		effFileValidation.connectToDB();
		effFileValidation.executeUpdateQuery();
		effFileValidation.executeFlexDeployment();
		Assert.assertTrue(effFileValidation.bWlstExecutionComplete,"WLST execution completed successfully");
	}
	
	@Test(dependsOnMethods = {"executeFlexDeploymentOnFAHost"},description="Copy FlexDeployment.xml file to local machine")
	public void copyAndValidateFlexDeploymentFile() {
		localFile = new File(logsFolder+"/FlexDeployment.xml");
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
		effFileValidation.copyDeployFlexlogFile("/tmp/FlexDeployment.xml",logsFolder);
		Assert.assertTrue(localFile.exists(), "Deploying Flexfields log copied");
	}
	
	@Test(dependsOnMethods = {"copyAndValidateFlexDeploymentFile"},description="Copy FlexDeployment.xml file to local machine")
	public void validateFlexDeploymentDetails() throws IOException {
		flexDeploymentStatus=effFileValidation.getFlexfieldDeploymentReportStatus(localFile);
		timeTaken=effFileValidation.getTotalFlexDeploymentTime();
		Assert.assertEquals(flexDeploymentStatus, "SUCCESS","FlexDeployment is successful");
		Assert.assertNotNull(timeTaken, "Total time taken for FlexDeployment - "+timeTaken);
		
	}
	
	public void generateHtmlReport() throws IOException {
		File input = new File(System.getProperty("user.dir") + "/src/test/resources/FlexDeploymentTemplate.html");
		File deploymentReport = new File("FlexDeployment.html");
		if(deploymentReport.exists() && deploymentReport.isFile()) {
			deploymentReport.delete();
		}
        Document document = Jsoup.parse(input, "UTF-8");
        Element elementATGPatch = document.getElementById("atgPatch");
        Element elementOverAllStatus = document.getElementById("depStatus");
        Element elementOverAllTimetaken = document.getElementById("timeTaken");
        //Deployment Details table
        Element keyFlexfield = document.getElementById("keyFlexfield");
        Element descriptiveFlexfield = document.getElementById("descriptiveFlexfield");
        Element extensibleFlexfield = document.getElementById("extensibleFlexfield");
        //overall status values printing
        elementATGPatch.append("<td>"+effFileValidation.atgPatch+" ("+effFileValidation.getATGPatchLabel()+")</td>");
        elementOverAllStatus.append("<td>"+flexDeploymentStatus+"</td>");
        elementOverAllTimetaken.append("<td>"+timeTaken+"</td>");
        //Deployment details values printing
        keyFlexfield.append("<td>"+effFileValidation.getFlexfieldsProcessedSuccess("KeyFlexfield").get(0)+"</td>");
        keyFlexfield.append("<td>"+effFileValidation.getFlexfieldsProcessedSuccess("KeyFlexfield").get(1)+"</td>");
        descriptiveFlexfield.append("<td>"+effFileValidation.getFlexfieldsProcessedSuccess("DescriptiveFlexfield").get(0)+"</td>");
        descriptiveFlexfield.append("<td>"+effFileValidation.getFlexfieldsProcessedSuccess("DescriptiveFlexfield").get(1)+"</td>");
        extensibleFlexfield.append("<td>"+effFileValidation.getFlexfieldsProcessedSuccess("ExtensibleFlexfield").get(0)+"</td>");
        extensibleFlexfield.append("<td>"+effFileValidation.getFlexfieldsProcessedSuccess("ExtensibleFlexfield").get(1)+"</td>");
        OutputStream outputStream = new FileOutputStream(deploymentReport);
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(document.toString());
        writer.close();
	}
	
	@AfterClass
	public void close() throws IOException, SQLException {
		try {
			generateHtmlReport();
			//localFile.delete();
			effFileValidation.closeAll();
		} catch (SftpException | JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			DbResource.dbConnectionClose();
		}
	}
}
