package UI.tests.Flex.EFF;
import org.testng.annotations.Test;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;
import TestBase.UI.GetDriverInstance;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import javax.swing.text.Segment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;

public class EFFTests extends GetDriverInstance{
	WebDriver effHCMTestdriver;
	private Deployment dep;
	private RuntimeValidation perLocationInfoRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private String uniqueID;
	//private DbResource dbConnection;
	private EFFUtils effUtils;
	private String contextCode=null;
	private String tabSegment=null;
	private String numIndSegment=null;
	private String numDepSegment=null;
	private String lovContextCode=null;
	private String ddContextCode=null;
	/*String loc_info_eff_flexcode=null;
	String flexfield = null;
	String contextName = null;
	String description = null;
	String behavior = null;
	String displayType = null;
	String vsName = null;
	String segmentName = null;
	String dataType = null;
	String loc_info_usage = null;
	String loc_info_page = null;
	String loc_info_category = null;
	String hcm_user1=null;
	String locationName=null;*/
	
	/*
	 * Value sets numIndVS - {10,100,1000}
	 * numDepVS - {10-{10,11,12},100-{111,112,113},1000-{1001,1002,1003}}
	 * numtabvs - select APPLICATION_ID from FND_DF_SEGMENTS_B where SEGMENT_CODE='NH_CB_Character' or SEGMENT_CODE='HRT_CI_GlobalSegment1' or SEGMENT_CODE='VOLUNTARY_SICKNESS_INSURANCE' or SEGMENT_CODE='SFSS_FLAG'; Result - {396,800,801,10022}
	 * Default value for Number with SQL - select APPLICATION_ID from FND_DF_SEGMENTS_B where SEGMENT_CODE='NH_CB_Character'; Result - 396
	 * chardep - {a1 - {a11,a12},a2-{a21,a22}}
	 * CharInd - Department WFMTL  Vision Operations (Account Payables)  Vision Construction	(Cash)
		CharDep - Account WFMTL, 
		Number_10_Key (10/20/30)
	 * 
	 * */
		
	@BeforeClass
	public void startUP() throws Exception {
		effHCMTestdriver =  getDriverInstanceObject();
		dep = new Deployment(effHCMTestdriver);
		perLocationInfoRunTime = new RuntimeValidation(effHCMTestdriver);
		aLoginInstance = new ApplicationLogin(effHCMTestdriver);
	    ntFInstance = new NavigationTaskFlows(effHCMTestdriver);
	    nMEInstance = new NavigatorMenuElements(effHCMTestdriver);
	    effUtils = new EFFUtils(effHCMTestdriver);
	    deleteSegment();
	}
	
	public void deleteSegment() throws Exception{
		try{
			System.out.println("Deleting Contexts from db");
		//	DbResource.getDBConnection();
		//	DbResource.createDbStatement();
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("PER_LOCATION_INFORMATION_EFF", "HCM_LOC", "HCM_LOC_PAGE", "BaseContext%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("PER_LOCATION_INFORMATION_EFF", "HCM_LOC", "BaseContext%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("PER_LOCATION_INFORMATION_EFF", "HCM_LOC", "HCM_LOC_PAGE", "LOVContext%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("PER_LOCATION_INFORMATION_EFF", "HCM_LOC", "LOVContext%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("PER_LOCATION_INFORMATION_EFF", "HCM_LOC", "HCM_LOC_PAGE", "DDContext%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("PER_LOCATION_INFORMATION_EFF", "HCM_LOC", "DDContext%"));
			/*CommonUtils.SqlStatement.executeUpdate(EFFUtils.deleteSegmentsFromContexts("PER_LOCATION_INFORMATION_EFF", "BaseContext%"));
			CommonUtils.SqlStatement.executeUpdate(EFFUtils.deleteContexts("PER_LOCATION_INFORMATION_EFF", "BaseContext%"));*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DbResource.dbConnectionClose();
			
		}
	}
		
	@Parameters({ "flexfield", "loc_info_eff_flexcode", "contextName", "description", "behavior" })
	@Test (description = "This is to create a simple context ")
	public void verifyCreateSimpleContext(String flexfield,String loc_info_eff_flexcode, String contextName, String description, String behavior) throws Exception {
		
		aLoginInstance.login("PIMQA", "Welcome1",effHCMTestdriver);
		CommonUtils.hold(10);
		loc_info_eff_flexcode="PER_LOCATION_INFORMATION_EFF";
		flexfield = "Manage Extensible Flexfields";
		contextName = "BaseContext";
		behavior="Single Row";
		description=contextName;
		/*displayType="Inline Search";
		vsName="Priority";
		segmentName="BaseSegment";
		loc_info_category = "HcmLocationsCategory";
		loc_info_page ="Extra Information";
		loc_info_usage = "Location";
		hcm_user1="HCM_FUSION_COX_QA6";
		locationName="LocationBenA";
		dataType="Character";
		dep.openAboutApplication();
		System.out.println("Current Branch Name - "+dep.getcurrentEnvironmentBranch());
		System.out.println("Current ADF Label - "+dep.getcurrentADFLabel());
		System.out.println("Current ATG Label - "+dep.getcurrentATGLabel());
		System.out.println("Current DB Patch Label - "+dep.getCurrentDBPatchLabel());
		dep.closeAboutApplication();*/
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effHCMTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(flexfield,effHCMTestdriver);
		CommonUtils.hold(10);
		System.out.println("Class - EFF , Code - "+loc_info_eff_flexcode);
		System.out.println("Checking whether Context creation is successful or not");
		System.out.println("Current Flex code to check - "+loc_info_eff_flexcode);
		Assert.assertEquals(dep.searchFlexFieldCode(loc_info_eff_flexcode), true, "Flex Code Result - "+loc_info_eff_flexcode);
		Assert.assertTrue(dep.createContext(loc_info_eff_flexcode, contextName, description, "Single Row","Location"), "Context - "+contextName+" created successfully");
		contextCode=dep.getcontextCode(contextName);
	}
	
	@Parameters({ "segmentName", "dataType", "vsName", "behavior", "displayType" })
	@Test (dependsOnMethods = {"verifyCreateSimpleContext"},description = "Single Row segment with Character type")
	public void verifyCreateSegment(String segmentName,String dataType,String vsName, String behavior, String displayType) throws Exception {
		tabSegment="tabInline";
		numIndSegment="numIndInline";
		numDepSegment="numDepInline";
		System.out.println("Current Context code - "+contextCode);
		System.out.println("Verification of Segment Creation");
		Assert.assertTrue(dep.createSegment(segmentName, dataType, vsName, behavior, displayType, false),"Character Independent type VS with Inline search Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment(tabSegment, dataType, "tabvs", behavior, displayType, false),"Table type VS with Inline search Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment(numIndSegment, "Number", "Number_10_Key", behavior, displayType, false),"Number Independent type VS with Inline search Segment creation assertion is successful");
		/*Assert.assertTrue(dep.createSegment(numDepSegment, "Number", "numDepVS", behavior, displayType, false),"Number Independent type VS with Inline search Segment creation assertion is successful");*/
		dep.navigateToCategoriesPage();
		/*dep.saveAndCloseButtonUnderCategoryPages();
		dep.deployFlexFieldIncremental("PER_LOCATION_INFORMATION_EFF", effHCMTestdriver);*/
		aLoginInstance.logout(effHCMTestdriver);
	}
	
	@Test (dependsOnMethods = {"verifyCreateSegment"},description = "Single Row segment with Character type and List of Values display type")
	public void createSetupForListOfValues() throws Exception {
		String loc_info_eff_flexcode="PER_LOCATION_INFORMATION_EFF";
		aLoginInstance.login("PIMQA", "Welcome1",effHCMTestdriver);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effHCMTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields",effHCMTestdriver);
		CommonUtils.hold(10);
		System.out.println("Class - EFF , Code - "+loc_info_eff_flexcode);
		System.out.println("Checking whether Context creation is successful or not");
		System.out.println("Current Flex code to check - "+loc_info_eff_flexcode);
		Assert.assertEquals(dep.searchFlexFieldCode(loc_info_eff_flexcode), true, "Flex Code Result - "+loc_info_eff_flexcode);
		Assert.assertTrue(dep.createContext(loc_info_eff_flexcode, "LOVContext", "LOVContext", "Single Row","Location"), "Context - LOVContext created successfully");
		lovContextCode=dep.getcontextCode("LOVContext");
		Assert.assertTrue(dep.createSegment("lovcharindependent", "Character", "Priority", "Single Row", "List of Values", false),"Character Independent type VS with LOV Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment("lovchartable", "Character", "tabvs", "Single Row", "List of Values", false),"Table type VS with Inline search Segment creation assertion is successful");
		dep.navigateToCategoriesPage();
		/*dep.saveAndCloseButtonUnderCategoryPages();*/
		
	}
	
	/*@Test (dependsOnMethods = {"createSetupForListOfValues"},description = "Single Row segment with Character type and List of Values display type")
	public void createSetupForDropDownValues() throws Exception {
		Assert.assertTrue(dep.createContext("PER_LOCATION_INFORMATION_EFF", "DDContext", "DDContext", "Single Row","Location"), "Context - DropdownContext created successfully");
		ddContextCode=dep.getcontextCode("DDContext");
		Assert.assertTrue(dep.createSegment("ddcharindependent", "Character", "Priority", "Single Row", "Drop-down List", false),"Character Independent type VS with Dropdown Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment("ddchartable", "Character", "tabvs", "Single Row", "Drop-down List", false),"Table type VS with Inline search Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment("ddnumindependent", "Number", "numIndVS", "Single Row", "Drop-down List", false),"Number Independent type VS with Inline search Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment("ddnumdependent", "Number", "numDepVS", "Single Row", "Drop-down List", false),"Number Independent type VS with Inline search Segment creation assertion is successful");
		dep.navigateToCategoriesPage();
	}*/
	
	@Parameters({"loc_info_eff_flexcode","loc_info_usage","loc_info_category","loc_info_page"})
	@Test (dependsOnMethods = {"createSetupForListOfValues"},description = "Associate to Category")
	public void verifyAssociatePageContext(String loc_info_eff_flexcode, String loc_info_category, String loc_info_usage, String loc_info_page) throws Exception {
		System.out.println("Parameters for 'verifyAssociatePageContext' - flexcode - "+loc_info_eff_flexcode+", Category - "+loc_info_category+", contextName - "+contextCode+", contextUsage - "+loc_info_usage+", pageName - "+loc_info_page);
		Assert.assertTrue(dep.associateContextToCategory(loc_info_eff_flexcode,loc_info_category, contextCode), "Context associated with Category");
		Assert.assertTrue(dep.associateContextToCategory(loc_info_eff_flexcode,loc_info_category, lovContextCode), "Context associated with Category");
		dep.navigateToAssociatedPages(loc_info_page, effHCMTestdriver);
		Assert.assertTrue(dep.associateContextToPage(loc_info_page, contextCode, loc_info_usage), "Context association to Page assertion is successful");
		Assert.assertTrue(dep.associateContextToPage(loc_info_page, lovContextCode, loc_info_usage), "Context association to Page assertion is successful");
		dep.saveAndCloseButtonUnderCategoryPages();
	} 
		
	@Parameters({"loc_info_eff_flexcode"})
	@Test (dependsOnMethods = {"verifyAssociatePageContext"},description = "Offline Deployment")
	public void verifyOfflineDeployFlex(String loc_info_eff_flexcode) {
		Assert.assertTrue(dep.deployOfflineFlexField(loc_info_eff_flexcode), "Offline Deployment is successful. About to logout");
		aLoginInstance.logout(effHCMTestdriver);
	}
	
	@Parameters({"hcm_user1","pwd","locationName"})
	@Test(dependsOnMethods = {"verifyOfflineDeployFlex"},description = "Update value under Location")
	public void validateSearchLocationRunTime(String hcm_user1,String pwd,String locationName) throws Exception {
		aLoginInstance.login(hcm_user1, "Welcome1",effHCMTestdriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().workForceStructures,effHCMTestdriver);
		CommonUtils.hold(5);
		Assert.assertTrue(perLocationInfoRunTime.navigateToManageLocations(), "Successfully navigated to Manage Locations");
		CommonUtils.hold(5);
		Assert.assertTrue(perLocationInfoRunTime.searchLocation(locationName), "Location validation successful");
	} 
	
	@Parameters({"segmentName","locationName"})
	@Test (dependsOnMethods = {"validateSearchLocationRunTime"})
	public void validateUpdateLocationInlineSearchIndependentVS(String segmentName,String locationName) throws Exception {
		/*contextCode="BaseContext2007241257";
		segmentName="BaseSegment";
		tabSegment = "tabInline";*/
		Assert.assertTrue(perLocationInfoRunTime.updateSingleSegmentValues(contextCode, segmentName, "Inline Search", "Medium"), "Segment update for Inline search with Independent VS validation successful");
	} 
	
	@Parameters({"locationName"})
	@Test(dependsOnMethods = {"validateUpdateLocationInlineSearchIndependentVS"},description = "validateUpdateLocationInlineSearchTableVS")
	public void validateUpdateLocationInlineSearchTableVS(String locationName) throws Exception {
		/*tabSegment = "tabInline";*/
		perLocationInfoRunTime.navigateToLocation(locationName);
		CommonUtils.hold(5);
		Assert.assertTrue(perLocationInfoRunTime.updateSingleSegmentValues(contextCode, tabSegment, "Inline Search", "Collections"), "Segment update for Inline search with Table type VS validation successful");
	}
	
	
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effHCMTestdriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effHCMTestdriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effHCMTestdriver);
		}finally {
			try {
				effHCMTestdriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
