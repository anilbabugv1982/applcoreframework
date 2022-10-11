package UI.tests.Flex.EFF;

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
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;
import TestBase.UI.GetDriverInstance;


public class EFFPIMCrossContext extends GetDriverInstance {
	String uniqueID = null;
//	WebDriver driver = null;
	String sourceContextCode=null;
	String sourceContextName=null;
	String sourceSegment=null;
	String targetContextCode=null;
	String targetContextName=null;
	String targetSegment=null;
	String itemName=null;
	String currentUniqueID=null;
	String pageName=null;
	String defaultSourceSegmentValue=null;
	WebDriver effPIMCrossContextTestdriver;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	//private DbResource dbConnection;	
	private EFFUtils effUtils;
	
	/*
	 *value set - MyIndVS - {ACCOUNT_CLASSES, ACCOUNT_SET_TYPES, ACCOUNT_STATUS, APR, ABBR_MONTH} 
	 * */
	
	@BeforeClass
	public void startUP() throws Exception {
		effPIMCrossContextTestdriver =  getDriverInstanceObject();
		dep = new Deployment(effPIMCrossContextTestdriver);
		pimRunTime = new RuntimeValidation(effPIMCrossContextTestdriver);
		aLoginInstance = new ApplicationLogin(effPIMCrossContextTestdriver);
		ntFInstance = new NavigationTaskFlows(effPIMCrossContextTestdriver);
		nMEInstance = new NavigatorMenuElements(effPIMCrossContextTestdriver);
		effUtils = new EFFUtils(effPIMCrossContextTestdriver);
	//	dbConnection = new DbResource(effPIMCrossContextTest);
		deleteSegment();
	}
	
	public void deleteSegment() throws Exception{
		try{
			System.out.println("Deleting Contexts from db");
		//	DbResource.getDBConnection();
		//	DbResource.createDbStatement();
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1CCNode", "CCPage", "scon%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1CCNode", "scon%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1CCNode", "CCPage", "tcon%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1CCNode", "tcon%"));
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DbResource.dbConnectionClose();
		}
	}
	
	@Parameters({"flexfield","flexCode","pwd"})
	@Test(description="Create a source Context with single Row")
	public void createPIMSourceContextSingleRow(String flexfield, String flexCode,String pwd) throws Exception {
		aLoginInstance.login("SCMOPERATIONS", pwd, effPIMCrossContextTestdriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMCrossContextTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMCrossContextTestdriver);
		CommonUtils.hold(10);
		dep = new Deployment(effPIMCrossContextTestdriver);
		currentUniqueID=CommonUtils.uniqueId();
		sourceContextName = "scon";
		String contextDescription = sourceContextName;
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		Assert.assertTrue(dep.createContext(flexCode, sourceContextName, "Source Context", "Single Row","Item"), "Context - "+sourceContextName+" created successfully");
		sourceContextCode=dep.getcontextCode(sourceContextName);
	}
	
	@Parameters({"displayType_DD"})
	@Test(dependsOnMethods = {"createPIMSourceContextSingleRow"},description="Create a Source Segment with default constant value")
	public void createSourceSegmentWithDefaultValue(String displayType_DD) throws Exception {
		sourceSegment="sseg"+currentUniqueID;
		defaultSourceSegmentValue="ACCOUNT_STATUS";
		System.out.println("Current Context code - "+sourceContextCode);
		System.out.println("Segment to be created - "+sourceSegment);
		System.out.println("Verification of Segment Creation with Character type , Independent Value Set");
		Assert.assertTrue(dep.createSegment(sourceSegment, "Character", "MyIndVS", "Single Row", displayType_DD, false),"Source Segment creation - "+sourceSegment+" assertion is successful");
		dep.editSegment(sourceSegment);
		dep.selectDefaultType("Constant", defaultSourceSegmentValue);
		dep.segmentSaveAndClose();
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
	}
	
	@Parameters({"flexfield","flexCode"})
	@Test(dependsOnMethods = {"createSourceSegmentWithDefaultValue"},description="Target Context")
	public void createPIMTargetContextSingleRow(String flexfield, String flexCode) throws Exception {
		targetContextName = "tcon";
		String contextDescription = targetContextName;
		Assert.assertTrue(dep.createContext(flexCode, targetContextName, "Source Context", "Single Row", "Item"), "Target Context - "+targetContextName+" created successfully");
		targetContextCode=dep.getcontextCode(targetContextName);
	}
	
	@Parameters({"displayType_DD"})
	@Test(dependsOnMethods = {"createPIMTargetContextSingleRow"},description="Target Segment")
	public void createTargetSegment(String displayType_DD) throws Exception {
		targetSegment="tseg"+currentUniqueID;
		System.out.println("Current Context code - "+targetContextCode);
		System.out.println("Segment to be created - "+targetSegment);
		System.out.println("Verification of Segment Creation with Character type , Independent Value Set");
		Assert.assertTrue(dep.createSegment(targetSegment, "Character", "2003 SIC", "Single Row", displayType_DD, false),"Target Segment creation - "+targetSegment+" assertion is successful");
		dep.editSegment(targetSegment);
        dep.selectDefaultSQL(sourceContextCode, sourceSegment,effPIMCrossContextTestdriver);
		dep.updateValueSet(sourceContextCode, sourceSegment);
		dep.segmentSaveAndClose();
		
	}
	
	@Parameters({"itemClassName","childItemClassName3","itemclass"})
	@Test(dependsOnMethods = {"createTargetSegment"},description="Map to Item Class and Page")
	public void associateSourceAndTargetContextsToItemClassAndPage(String itemClassName,String childItemClassName3,String itemclass) throws Exception {
		boolean bSearchItemExists=false;
		pageName="CCPage";
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMCrossContextTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMCrossContextTestdriver);
		CommonUtils.hold(10);
		if (!dep.itemClassExists(childItemClassName3)) {
			System.out.println("Item Class - "+childItemClassName3+" is not present");
			dep.createItemClass(itemClassName,childItemClassName3, effPIMCrossContextTestdriver);
			CommonUtils.waitForPageLoad(effPIMCrossContextTestdriver);
			dep.closeItemClassTaskFlow();
			CommonUtils.hold(3);
			ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMCrossContextTestdriver);
			ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMCrossContextTestdriver);
			Assert.assertTrue(dep.itemClassExists(childItemClassName3), "Validation of Item Class successful");
		}
		else {
			System.out.println("Item Class exists");
			bSearchItemExists=true;
			Assert.assertTrue(bSearchItemExists,"Item Class - "+childItemClassName3+" search validation successful");
		}
		dep.navigateToPageAttributeGroups(childItemClassName3);
		Assert.assertTrue(dep.associateContextToItemClass(sourceContextCode), "Source Context associated with Category");
		Assert.assertTrue(dep.associateContextToItemClass(targetContextCode), "Target Context associated with Category");
		dep.itemClassSaveAndClose();
		dep.navigateToPageAttributeGroups(childItemClassName3);
		Assert.assertTrue(dep.navigateToPageLinkUnderItemClass(pageName,"Item", effPIMCrossContextTestdriver)," validation of Page search under Item class is successful..");
		System.out.println("Parameters for 'verifyAssociatePageContext' - flexcode - EGO_ITEM_EFF, contextName - "+sourceContextCode+", contextUsage - Item, pageName - NPage");
		Assert.assertTrue(dep.associateContextToPage(pageName, sourceContextCode, "Item"), "Source Context association to Page assertion is successful");
		dep.itemClassSave();
		Assert.assertTrue(dep.associateContextToPage(pageName, targetContextCode, "Item"), "Target Context association to Page assertion is successful");
		dep.itemClassSave();
	}
	
	@Parameters({"flexfield","flexCode"})
	@Test(dependsOnMethods = {"associateSourceAndTargetContextsToItemClassAndPage"}, description="Deploy Flex")
	public void verifyIncrementalDeployFlexForPIMWithSourceAndTargetContexts(String flexfield,String flexCode) throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMCrossContextTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMCrossContextTestdriver);
		CommonUtils.hold(10);
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMCrossContextTestdriver), "Incremental Deployment is successful");
		aLoginInstance.logout(effPIMCrossContextTestdriver);
	}
	
	@Parameters({ "user", "pwd","childItemClassName3"})
	@Test(dependsOnMethods = {"verifyIncrementalDeployFlexForPIMWithSourceAndTargetContexts"}, description="Create Item for Runtime Cross context test")
	public void verifyRuntimePIMItemCreationWithSourceAndTargetContextsUnderPage(String user,String pwd,String childItemClassName3) throws Exception {
		//driver = DriverInstance.getDriverInstance();
		defaultSourceSegmentValue="ACCOUNT_STATUS";
		itemName = "v_"+childItemClassName3+CommonUtils.uniqueId();
		System.out.println("Item name to be created  is -"+itemName);
		pimRunTime = new RuntimeValidation(effPIMCrossContextTestdriver);
		aLoginInstance.login("pimqa", pwd, effPIMCrossContextTestdriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement, effPIMCrossContextTestdriver);
		Assert.assertTrue(pimRunTime.createItem(itemName, "Item", childItemClassName3, effPIMCrossContextTestdriver), "Item creation for Security completed - PIMQA");
		Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage("Item",pageName), "Validation for Page existence under newly created Item");
		Assert.assertTrue(pimRunTime.contextExistsUnderPage(pageName, sourceContextCode, "Item"),"Source Context is displayed under Item");
		Assert.assertTrue(pimRunTime.contextExistsUnderPage(pageName, targetContextCode, "Item"),"Target Context is displayed under Item");
	}
	
	@Test(dependsOnMethods = {"verifyRuntimePIMItemCreationWithSourceAndTargetContextsUnderPage"}, description="Run Incremental deployment after configuring source and target Context")
	public void verifyDefaultSourceSegmentValueRuntime() {
		Assert.assertTrue(pimRunTime.searchSegmentDefaultValue(sourceSegment, defaultSourceSegmentValue), "Validation of default value under Source Segment is successful");
		pimRunTime.searchSegmentDefaultValue(targetSegment, "Preferred");
	}
	
	@Parameters({"childItemClassName3"})
	@Test(dependsOnMethods = {"verifyDefaultSourceSegmentValueRuntime"}, description="Target Segment run time value select and save")
	public void verifyTargetSegmentValueRuntime(String childItemClassName3) throws Exception {
		String targetValue="Suspended";
		Assert.assertTrue(pimRunTime.searchTargetContextValue(targetSegment, targetValue), "Validation of target segment value is successful");
		pimRunTime.itemSave();
	}
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMCrossContextTestdriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMCrossContextTestdriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMCrossContextTestdriver);
		}finally {
			try {
				effPIMCrossContextTestdriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
