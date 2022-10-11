package UI.tests.Flex.EFF;

import TestBase.UI.GetDriverInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import oracle.net.jdbc.TNSAddress.Description;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;
import TestBase.UI.GetDriverInstance;

public class EFFStatusFlipTests extends GetDriverInstance {
	WebDriver effPIMStatusFlipTestDriver;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	//private DbResource dbConnection;	
	private EFFUtils effUtils;
	private String flexCode = null;
	private String contextCode = null;
	private String contextName = null;
	private String itemUsage = null;
	private String description = null;
	private String segmentName = null;
	private String categoryCode = null;
	private String pageName = null;
	@BeforeClass
	public void startUP() throws Exception {
		effPIMStatusFlipTestDriver =  getDriverInstanceObject();
		dep = new Deployment(effPIMStatusFlipTestDriver);
		pimRunTime = new RuntimeValidation(effPIMStatusFlipTestDriver);
		aLoginInstance = new ApplicationLogin(effPIMStatusFlipTestDriver);
		ntFInstance = new NavigationTaskFlows(effPIMStatusFlipTestDriver);
		nMEInstance = new NavigatorMenuElements(effPIMStatusFlipTestDriver);
		effUtils = new EFFUtils(effPIMStatusFlipTestDriver);
		flexCode="DOO_HEADERS_ADD_INFO";
		categoryCode="Additional Header Information";
		pageName="Compliance Info";
		itemUsage = "Additional Header Information";
		
	//	dbConnection = new DbResource(effPIMCrossContextTest);
		deleteSegment();
	}
	
	public void deleteSegment() throws Exception {
		DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage(flexCode, "DOO_HEADERS_ADD_INFO", "COMPLIANCE_INFO", "OMHeader%"));
		DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory(flexCode, "DOO_HEADERS_ADD_INFO", "OMHeader%"));
	}
	
	public void executeUpdateQuery(String updateStatus) throws SQLException {
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = '"+updateStatus+"' where DESCRIPTIVE_FLEXFIELD_CODE = '"+flexCode+"'");
	}
	
	public String executeSelectQuery() throws SQLException{
		String deployStatus = null;
		ResultSet result = DbResource.sqlStatement.executeQuery("select deployment_status from FND_dF_FLEXFIELDS_B where DESCRIPTIVE_FLEXFIELD_CODE = '"+flexCode+"'");
        while(result.next()) {
        	deployStatus = result.getString("DEPLOYMENT_STATUS");
        }
        System.out.println("Current Deployment Status of flexcode - "+flexCode+" is - "+deployStatus);
        return deployStatus;
	}
	
	@Test(description="To get latest status of a flexfield from db")
	public void getStatusFromDB() throws Exception {
		String deployStatus = null;
		aLoginInstance.login("pimqa", "Welcome1",effPIMStatusFlipTestDriver);
		CommonUtils.hold(10);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMStatusFlipTestDriver);
        ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields", effPIMStatusFlipTestDriver);
        CommonUtils.hold(5);
        Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
        
	}
	
	@Test(dependsOnMethods = {"getStatusFromDB"},description="Check basic Incremental Deployment is successful and redeploy")
	public void deployAndReDeployDOOFlex() {
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMStatusFlipTestDriver), "Incremental Deployment is successful");
		System.out.println("Redeploying the same flex - "+flexCode);
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMStatusFlipTestDriver), "Incremental Deployment is successful");
	}
	
	@Test(dependsOnMethods = {"deployAndReDeployDOOFlex"},description="Check SetupUI is modified from DEPLOYED to EDITED")
	public void modifySetUpUIFromDeployToEdited() throws Exception {
		contextName = "OMHeader";
		segmentName = "numInd"+CommonUtils.uniqueId();
		System.out.println("Flexcode - "+flexCode+" status before modification is : "+executeSelectQuery());
		Assert.assertTrue(dep.createContext(flexCode, contextName, description, "Single Row",itemUsage), "Context - "+contextName+" created successfully");
		contextCode=dep.getcontextCode(contextName);
		Assert.assertTrue(dep.createSegment(segmentName, "Number", "Number_10_Key", "Single Row", "List of Values", false),"Number Independent type VS with LOV Segment creation assertion is successful");
		Assert.assertEquals(executeSelectQuery(), "EDITED","SetupUI status got changed from DEPLOYED to EDITED");	
		dep.navigateToCategoriesPage();
		Assert.assertTrue(dep.associateContextToCategory(flexCode,categoryCode, contextCode), "Context associated with Category");
		dep.navigateToAssociatedPages(pageName, effPIMStatusFlipTestDriver);
		System.out.println("Parameters for 'verifyAssociatePageContext' - flexcode - "+flexCode+", Category - "+categoryCode+", contextName - "+contextCode+", contextUsage - "+itemUsage+", pageName - "+pageName);
		Assert.assertTrue(dep.associateContextToPage(pageName, contextCode, itemUsage), "Context association to Page assertion is successful");
		dep.saveAndCloseButtonUnderCategoryPages();
		dep.deployFlexFieldIncremental(flexCode, effPIMStatusFlipTestDriver);
	}
	
	@Test(dependsOnMethods = {"modifySetUpUIFromDeployToEdited"},description="Check SetupUI is modified from READY to EDITED")
	public void modifySetUpUIFromReadyToEdited() throws Exception {
		executeUpdateQuery("READY");
		System.out.println("Flexcode - "+flexCode+" status before modification is : "+executeSelectQuery());
		dep.modifyContext(contextCode);
		CommonUtils.hold(5);
		dep.editSegment(segmentName);
		CommonUtils.hold(5);
		dep.modifySegmentDisplayType("Pop-up List of Values");
		/*dep.createSegment("depSeg"+CommonUtils.uniqueId(), "Number", "numDepVS", "Single Row", "List of Values", false);*/
		dep.segmentSaveAndClose();
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
		Assert.assertEquals(executeSelectQuery(), "EDITED","SetupUI status got changed from READY to EDITED");
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMStatusFlipTestDriver), "Incremental Deployment is successful");
	}
	
	@Test(dependsOnMethods = {"modifySetUpUIFromReadyToEdited"},description="Check SetupUI is modified from ERROR to EDITED")
	public void modifySetUPFromErrorToEdited() throws Exception {
		executeUpdateQuery("ERROR");
		System.out.println("Flexcode - "+flexCode+" status before modification is : "+executeSelectQuery());
		dep.modifyContext(contextCode);
		CommonUtils.hold(5);
		dep.editSegment(segmentName);
		CommonUtils.hold(5);
		dep.modifySegmentDisplayType("Radio Button Group");
		dep.segmentSaveAndClose();
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
		Assert.assertEquals(executeSelectQuery(), "EDITED","SetupUI status got changed from ERROR to EDITED");
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMStatusFlipTestDriver), "Incremental Deployment is successful");
	}

	@Test(dependsOnMethods = {"modifySetUPFromErrorToEdited"},description="Check SetupUI is modified from SANDBOXED to EDITED")
	public void modifySetUpUIFromSandboxedToEdited() throws Exception {
		executeUpdateQuery("SANDBOXED");
		System.out.println("Flexcode - "+flexCode+" status before modification is : "+executeSelectQuery());
		dep.modifyContext(contextCode);
		CommonUtils.hold(5);
		dep.editSegment(segmentName);
		CommonUtils.hold(5);
		dep.modifySegmentDisplayType("Drop-down List");
		dep.segmentSaveAndClose();
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
		Assert.assertEquals(executeSelectQuery(), "EDITED","SetupUI status got changed from SANDBOXED to EDITED");
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMStatusFlipTestDriver), "Incremental Deployment is successful");
	}
	
	/*@Test(dependsOnMethods = {"modifySetUpUIFromSandboxedToEdited"},description="Check SetupUI remains EDITED_READY when flex is modified")
	public void checkEditedReadyRemainEditedReady() throws Exception {
		executeUpdateQuery("EDITED_READY");
		System.out.println("Flexcode - "+flexCode+" status before modification is : "+executeSelectQuery());
		dep.modifyContext(contextCode);
		CommonUtils.hold(5);
		dep.editSegment(segmentName);
		CommonUtils.hold(5);
		dep.modifySegmentDisplayType("List of Values");
		dep.segmentSaveAndClose();
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
		Assert.assertEquals(executeSelectQuery(), "EDITED","SetupUI status got changed from EDITED_READY remain EDITED_READY");
		Assert.assertEquals(executeSelectQuery(), "EDITED_READY","SetupUI status EDITED_READY");
	}*/
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMStatusFlipTestDriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMStatusFlipTestDriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMStatusFlipTestDriver);
		}finally {
			try {
				effPIMStatusFlipTestDriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
