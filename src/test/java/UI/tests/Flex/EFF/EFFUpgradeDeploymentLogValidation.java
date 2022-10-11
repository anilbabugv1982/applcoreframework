package UI.tests.Flex.EFF;

import java.io.File;
import java.io.IOException;

import org.aspectj.weaver.NewFieldTypeMunger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;

import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFFileValidations;

public class EFFUpgradeDeploymentLogValidation {
	public String latestDeployFlexFileName;
	public String availability;
	public EFFFileValidations effFileValidation;
	public File localFile;
	public String getOverallStatus;
	public boolean isPIMFlexPresent;
	@BeforeClass
	public void startUp() throws JSchException, ClassNotFoundException, SQLException {
		latestDeployFlexFileName=null;
		availability=null;
		getOverallStatus=null;
		isPIMFlexPresent=false;
		effFileValidation = new EFFFileValidations();
		
	}
	
	@Test(description="Check whether DeployingFlexfields log file present on target machine or not")
	public void checkDeploylogFilePresent() {
		availability =effFileValidation.checkDeployFlexFileAvailability();
		System.out.println("Value of Availability - "+availability);
		Assert.assertNotNull(availability,"DeployingFlexfields log file is present");
		latestDeployFlexFileName = availability.substring(availability.indexOf("Deploy"));
		System.out.println("Current Deploying Flexfields log file name - "+latestDeployFlexFileName);
		
	}
	
	@Test(dependsOnMethods = {"checkDeploylogFilePresent"},description="Copying Deploying Flexfields to local machine")
	public void copyDeployFlexLogFile() {
		localFile = new File("c:/EXPIMP/"+latestDeployFlexFileName);
		if(localFile.exists()) {
			System.out.println("File - "+localFile.getName()+" exists under given path - C:/EXPIMP");
			localFile.delete();
		}
		else {
			System.out.println("File - "+localFile.getName()+" does not exist under given path - C:/EXPIMP");
		}
		effFileValidation.copyDeployFlexlogFile(availability,"C:/EXPIMP/");
		Assert.assertTrue(localFile.exists(), "Deploying Flexfields log copied");
	}
	
	@Test(dependsOnMethods = {"copyDeployFlexLogFile"},description="Copying DeployingFlexfields log file to local machine")
	public void deployFlexfileValidations() throws IOException {
		getOverallStatus = effFileValidation.searchOverAllStatus(latestDeployFlexFileName);
		Assert.assertEquals(getOverallStatus, "SUCCESS","Assertion for status is completed successfully");
		effFileValidation.getForceDeploymentDetails();
		if(effFileValidation.result.size()>0) {
			int ForceFlexCount=0;
        	System.out.println(" - ");
        	while(ForceFlexCount<effFileValidation.result.size()) {
        		System.out.println("Current data - "+effFileValidation.result.get(ForceFlexCount));
        		if(ForceFlexCount<effFileValidation.result.size()) {
        			System.out.println(", ");
        		}
        		ForceFlexCount++;
        	}
        }
		
	}
	
	@Test(dependsOnMethods = {"copyDeployFlexLogFile"},description="Copying DeployingFlexfields log file to local machine")
	public void checkPIMFlexEntries() {
		isPIMFlexPresent=effFileValidation.checkPIMFlexExists();
		Assert.assertFalse(isPIMFlexPresent, "PIM entries check completed successfully");
	}
	
	private String getForceDeployDetails() {
		String getForceDeployText=null;
		getForceDeployText = Integer.toString(effFileValidation.result.size());
		if(effFileValidation.result.size()>0) {
			int ForceFlexCount=0;
			String currentLineText = null;
			getForceDeployText=getForceDeployText+" - ";
        	System.out.println(" - ");
        	while(ForceFlexCount<effFileValidation.result.size()) {
        		System.out.println("Current data - "+effFileValidation.result.get(ForceFlexCount));
        		currentLineText=effFileValidation.result.get(ForceFlexCount);
        		currentLineText=currentLineText.substring(currentLineText.indexOf("=")+2,currentLineText.indexOf("app")-2);
        		getForceDeployText=getForceDeployText+currentLineText;
        		if(ForceFlexCount<effFileValidation.result.size()-1) {
        			getForceDeployText=getForceDeployText+" ,";
        		}
        		ForceFlexCount++;
        	}
		}	
		return getForceDeployText;
	}
	
	public void generateHtmlReport() throws IOException {
		File input = new File(System.getProperty("user.dir") + "/src/test/resources/UpgradeFlexDeploymentTemplate.html");
        Document document = Jsoup.parse(input, "UTF-8");
        File deploymentReport = new File("upgradeFlexDeployment.html");
		if(deploymentReport.exists() && deploymentReport.isFile()) {
			deploymentReport.delete();
		}
        int ForceFlexCount=0;
        Element elementUpgPath = document.getElementById("upgPath");
        Element elementATGPatch = document.getElementById("atgPatch");
        Element elementFAINTEG = document.getElementById("faIntegLabel");
        Element elementP4FA = document.getElementById("p4faLabel");
        Element elementOverAllStatus = document.getElementById("depStatus");
        Element elementOverAllTimetaken = document.getElementById("timeTaken");
        Element elementforceDepCount = document.getElementById("forceDeployCount");
        Element elementPIMFlexPresent = document.getElementById("pimFlexPresent");
        
        elementUpgPath.append("<td>"+effFileValidation.upgPath+"</td>");
        elementATGPatch.append("<td>"+effFileValidation.atgPatch+"</td>");
        elementFAINTEG.append("<td>"+effFileValidation.faIntegLabel+"</td>");
        elementP4FA.append("<td>"+effFileValidation.p4FALabel+"</td>");
        elementOverAllStatus.append("<td>"+getOverallStatus+"</td>");
        elementOverAllTimetaken.append("<td>"+effFileValidation.timeTaken+"</td>");
        elementforceDepCount.append("<td>"+getForceDeployDetails()+"</td>");
        elementPIMFlexPresent.append("<td>"+isPIMFlexPresent+"</td>");
        
        OutputStream outputStream = new FileOutputStream(deploymentReport);
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(document.toString());
        writer.close();
	}
	
	@AfterClass
	public void close() throws NullPointerException, SQLException {
		try {
			generateHtmlReport();
			/*localFile.delete();*/
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
