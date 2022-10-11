package UI.tests.Flex.EFF;

import java.util.Set;

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
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import TestBase.UI.GetDriverInstance;

public class EFFPIMSecurityTests extends GetDriverInstance {
	
	String uniqueID = null;
//	WebDriver driver = null;
	String contextCodePIM=null;
	String contextCodeSCM=null;
	String contextNamePIM=null;
	String contextNameSCM=null;
	String independentSegment=null;
	String dependentSegment=null;
	String itemNamePIM=null;
	String itemNameSCM=null;
	private static String indNumberValue="10";
	private static String depNumberValue="113";
	private static String tabNumberValue="801";
	private static String indSegment="numind";
	private static String depSegment="numdep";
	private static String tabSegment="numtab";
	WebDriver effPIMSecurityTestdriver;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	//private DbResource dbConnection;
	private EFFUtils effUtils;
	private ManageDataSecurityPages securityPages;
	
	/*
	 * valueset - FDATE_1903110446, Format type
	 * */
		
	@BeforeClass
	public void startUP() throws Exception {
		effPIMSecurityTestdriver =  getDriverInstanceObject();
		dep = new Deployment(effPIMSecurityTestdriver);
		pimRunTime = new RuntimeValidation(effPIMSecurityTestdriver);
		aLoginInstance = new ApplicationLogin(effPIMSecurityTestdriver);
		ntFInstance = new NavigationTaskFlows(effPIMSecurityTestdriver);
		nMEInstance = new NavigatorMenuElements(effPIMSecurityTestdriver);
		effUtils = new EFFUtils(effPIMSecurityTestdriver);
		securityPages = new ManageDataSecurityPages(effPIMSecurityTestdriver);
		deleteSegment();
		
	}
	
	public void deleteSegment() throws Exception{
		try{
			System.out.println("Deleting Contexts from db");
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1SecNode2", "Security", "sec%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1SecNode2", "sec%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1SecNode2", "Security", "scmOp%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1SecNode2", "scmOp%"));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DbResource.dbConnectionClose();
			
		}
	}
	
	
	@Parameters({ "pwd","itemclass","childItemClassName2"})
	@Test(description="Check the privileges configured for Item usage")
	public void validatePIMDataSecurityPolicy(String pwd,String itemclass,String childItemClassName2) throws Exception {
		boolean bSearchItemExists=false;
		String itemClassName="1ANode";
		
		aLoginInstance.login("SCMOPERATIONS", pwd, effPIMSecurityTestdriver);
		CommonUtils.hold(5);
		//ntFInstance.navigateToTask((securityPages.shield), effPIMSecurityTestdriver);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().securityConsole, effPIMSecurityTestdriver);
		CommonUtils.waitForPageLoad(effPIMSecurityTestdriver);
		dep.navigateToPIMManageDataSecurityPolicies(effPIMSecurityTestdriver);
		Assert.assertTrue(dep.findSecurityPolicyUnderActions("EDIT_EFF_BI"), "validation of EDIT_EFF_BI security policy is successful");
		Assert.assertTrue(dep.findSecurityPolicyUnderActions("VIEW_EFF_BI"), "validation of VIEW_EFF_BI security policy is successful");
		Assert.assertTrue(dep.findSecurityPolicyUnderActions("SCM_EDIT_BI"), "validation of SCM_EDIT_BI security policy is successful");
		Assert.assertTrue(dep.findSecurityPolicyUnderActions("SCM_VIEW_BI"), "validation of SCM_VIEW_BI security policy is successful");
		dep.saveSecurityPolicy();
		aLoginInstance.logout(effPIMSecurityTestdriver);
		
		aLoginInstance.login("pimqa", pwd, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMSecurityTestdriver);
		CommonUtils.waitForPageLoad(effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMSecurityTestdriver);
		CommonUtils.hold(5);
		if(!dep.itemClassExists(childItemClassName2)) {
            System.out.println("Item Class - "+childItemClassName2+" is not present");
            dep.createItemClass(itemClassName,childItemClassName2, effPIMSecurityTestdriver);
            dep.closeItemClassTaskFlow();
            CommonUtils.hold(3);
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMSecurityTestdriver);
            CommonUtils.hold(10);
            ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMSecurityTestdriver);
            CommonUtils.hold(10);
            Assert.assertTrue(dep.itemClassExists(childItemClassName2), "Validation of Item Class successful");
        }
        else {
            System.out.println("Item Class exists");
            
        }
		bSearchItemExists=true;
        Assert.assertTrue(bSearchItemExists,"Item Class - "+childItemClassName2+" search validation successful");
        dep.navigateToItemClassSecurity(childItemClassName2);
        searchAndAddPrivilegesToItemClass(childItemClassName2,"PIMQA","VIEW_EFF_BI","EDIT_EFF_BI");
        dep.itemClassSaveAndClose();
        dep.navigateToItemClassSecurity(childItemClassName2);
        searchAndAddPrivilegesToItemClass(childItemClassName2,"SCMOPERATIONS","SCM_VIEW_BI","SCM_EDIT_BI");
        dep.itemClassSaveAndClose();
		CommonUtils.hold(4);
		aLoginInstance.logout(effPIMSecurityTestdriver);
		
	}
	
	public void searchAndAddPrivilegesToItemClass(String ItemClass,String user,String viewPrivilege,String editPrivilege) throws Exception {
		boolean bEditPrivilegExists=false;
		boolean bViewPrivilegExists=false;
		if(dep.searchUserPrivilege(ItemClass,user)) {
			//dep.deleteRole();
			System.out.println("Privileges exist for - "+user);
			
		}
		else {
			System.out.println("Creating user Privilege");
			dep.createUserNamePrivilege(user, effPIMSecurityTestdriver);
		}
		if(dep.searchPrivilege(editPrivilege)) {
			bEditPrivilegExists=true;
			Assert.assertTrue(bEditPrivilegExists, editPrivilege+" privilege given for "+user+". Validation successful");
		}
		else {
			System.out.println("Adding Edit Privileges to PIMQA");
			dep.addPrivilegetoRole(editPrivilege);
			CommonUtils.hold(4);
			Assert.assertTrue(dep.searchPrivilege(editPrivilege), "Validation of "+editPrivilege+" privilege to "+user+" successful");
		}
		if(dep.searchPrivilege(viewPrivilege)) {
			bViewPrivilegExists=true;
			Assert.assertTrue(bViewPrivilegExists, viewPrivilege+" privilege given for "+user+". Validation successful");
		}
		else {
			System.out.println("Adding Edit Privileges to "+user);
			dep.addPrivilegetoRole(viewPrivilege);
			CommonUtils.hold(4);
			Assert.assertTrue(dep.searchPrivilege(viewPrivilege), "Validation of "+viewPrivilege+" privilege to "+user+" successful");
		}
	}
	@Parameters({"flexfield","flexCode"})
	@Test(dependsOnMethods = {"validatePIMDataSecurityPolicy"},description="Create security setup for PIMQA")
	public void validateSecuritySetupCreationForPIMQA(String flexfield,String flexCode) throws Exception {
		contextNamePIM = "sec";
		aLoginInstance.login("pimqa", "Welcome1", effPIMSecurityTestdriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		Assert.assertTrue(dep.createContext(flexCode, contextNamePIM, "pimSecurity", "Single Row","Item"), "Context - "+contextNamePIM+" created successfully");
		contextCodePIM=dep.getcontextCode(contextNamePIM);
		Assert.assertNotNull(contextCodePIM, "Context code is not null");
		Assert.assertTrue(contextCodePIM.contains(contextNamePIM), "Validation Current ContextCode -"+contextCodePIM);
		Assert.assertTrue(dep.selectSecurityPrivilege("View Privilege","VIEW_EFF_BI"), "View Privilege selection is successful");
		Assert.assertTrue(dep.selectSecurityPrivilege("Edit Privilege","EDIT_EFF_BI"), "Edit Privilege selection is successful");
		Assert.assertTrue(dep.createSegment("sec_Segment", "Date", "FDATE_1903110446", "Single Row", "Date/Time", false), "Security Segment created Successfully");
	}
	
	@Test(dependsOnMethods= {"validateSecuritySetupCreationForPIMQA"},description="Create security setup for SCMOPERATIONS")
	public void validateSecuritySetupCreationForSCM() throws Exception {
		contextNameSCM="scmOp";
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
		Assert.assertTrue(dep.createContext("EGO_ITEM_EFF", contextNameSCM, contextNameSCM, "Single Row","Item"), "Context - "+contextNameSCM+" created successfully");
		contextCodeSCM=dep.getcontextCode(contextNameSCM);
		Assert.assertNotNull(contextCodeSCM, "Context code is not null");
		Assert.assertTrue(contextCodeSCM.contains(contextNameSCM), "Validation Current ContextCode -"+contextCodePIM);
		Assert.assertTrue(dep.selectSecurityPrivilege("View Privilege","SCM_VIEW_BI"), "View Privilege selection is successful");
		Assert.assertTrue(dep.selectSecurityPrivilege("Edit Privilege","SCM_EDIT_BI"), "Edit Privilege selection is successful");
		Assert.assertTrue(dep.createSegment(indSegment, "Number", "Number_10_Key", "Single Row", "List of Values", false), "Number Independent security Segment created Successfully");
		/*Assert.assertTrue(dep.createSegment(depSegment, "Number", "numDepVS", "Single Row", "List of Values", false), "Number Dependent security Segment created Successfully");*/
		Assert.assertTrue(dep.createSegment(tabSegment, "Number", "numtabvs", "Single Row", "List of Values", false), "Number Dependent security Segment created Successfully");
		
	}
	
	@Parameters({"itemclass","itemClassName","childItemClassName2"})
	@Test(dependsOnMethods = {"validateSecuritySetupCreationForSCM"},description="Check the item class with out privileges configured")
	public void validateSecurityClassExistence(String itemclass,String itemClassName, String childItemClassName2) throws Exception {
		boolean bSearchItemExists=false;
		CommonUtils.hold(5);
		System.out.println("Inside validateSecurityClassExistence");
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		if(!dep.itemClassExists(childItemClassName2)) {
			System.out.println("Item Class - "+childItemClassName2+" is not present");
			dep.createItemClass(itemClassName,childItemClassName2, effPIMSecurityTestdriver);
			CommonUtils.waitForPageLoad(effPIMSecurityTestdriver);
			dep.closeItemClassTaskFlow();
			CommonUtils.hold(3);
			ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMSecurityTestdriver);
			CommonUtils.hold(10);
			ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMSecurityTestdriver);
			CommonUtils.hold(10);
			Assert.assertTrue(dep.itemClassExists(childItemClassName2), "Validation of Item Class successful");
		}
		else {
			System.out.println("Item Class exists");
			bSearchItemExists=true;
			Assert.assertTrue(bSearchItemExists,"Item Class - "+childItemClassName2+" search validation successful");
		}
	}
	
	@Parameters({"childItemClassName2","itemclass"})
	@Test(dependsOnMethods = {"validateSecurityClassExistence"},description="Associate security context to Item class")
	public void associatePIMSecurityContextToSecurityItemClass(String childItemClassName2,String itemclass) throws Exception {
		dep.navigateToPageAttributeGroups(childItemClassName2);
		Assert.assertTrue(dep.associateContextToItemClass(contextCodePIM), "ContextPIM associated with Category");
		Assert.assertTrue(dep.associateContextToItemClass(contextCodeSCM), "ContextSCM associated with Category");
		Assert.assertTrue(dep.navigateToPageLinkUnderItemClass("Security","Item", effPIMSecurityTestdriver)," validation of Page search under Item class is successful..");
	}
	
	@Parameters({"childItemClassName2","itemclass"})
	@Test (dependsOnMethods = {"associatePIMSecurityContextToSecurityItemClass"},description="Associate security context to page")
	public void verifyAssociatePagewithSecurityContext(String childItemClassName2, String itemclass) throws Exception {
		System.out.println("Parameters for 'verifyAssociatePageContext' - flexcode - EGO_ITEM_EFF, contextName - "+contextCodePIM+", contextUsage - Item, pageName - Security");
		Assert.assertTrue(dep.associateContextToPage("Security", contextCodePIM, "Item"), "ContextPIM association to Page assertion is successful");
		Assert.assertTrue(dep.associateContextToPage("Security", contextCodeSCM, "Item"), "ContextSCM association to Page assertion is successful");
		dep.itemClassSave();
	} 
	
	@Parameters({"flexfield","flexCode"})
	@Test(dependsOnMethods = {"verifyAssociatePagewithSecurityContext"},description="Incremental deployment")
	public void verifyIncrementalDeployFlexForPIM(String flexfield,String flexCode) throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMSecurityTestdriver), "Incremental Deployment is successful");
		aLoginInstance.logout(effPIMSecurityTestdriver);
	}
	
	@Parameters({ "user", "pwd","childItemClassName2"})
	@Test(dependsOnMethods = {"verifyIncrementalDeployFlexForPIM"},description="Check the item creation and privileges for PIMQA")
	public void verifyRuntimePIMItemCreationForPIMQA(String user,String pwd,String childItemClassName2) throws Exception {
		itemNamePIM = "v_PIM"+childItemClassName2+CommonUtils.uniqueId();
		itemNameSCM = "v_SCM"+childItemClassName2+CommonUtils.uniqueId();
		System.out.println("Item name to be created  is -"+itemNamePIM);
		aLoginInstance.login("pimqa", pwd, effPIMSecurityTestdriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		Assert.assertTrue(pimRunTime.createItem(itemNamePIM, "Item", childItemClassName2, effPIMSecurityTestdriver), "Item creation for Security completed - PIMQA");
		Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage("Item","Security"), "Validation for Page existence under newly created Item");
		Assert.assertFalse(pimRunTime.contextExistsUnderPage("Security", contextCodeSCM, "Item"),"Context "+contextCodeSCM+" not displayed since privileges are not applied for PIMQA");
		Assert.assertTrue(pimRunTime.contextExistsUnderPage("Security", contextCodePIM, "Item"),"Context "+contextCodePIM+" is displayed after security privileges are given to PIMQA");
		pimRunTime.itemSaveAndClose();
		aLoginInstance.logout(effPIMSecurityTestdriver);
		
	}
	
	@Parameters({ "pwd","childItemClassName2"})
	@Test(dependsOnMethods = {"verifyRuntimePIMItemCreationForPIMQA"},description="Check the item creation and privileges for SCMOPERATIONS")
	public void verifyRuntimePIMItemCreationForSCMOPERATIONS(String pwd,String childItemClassName2) throws Exception {
		aLoginInstance.login("scmoperations", pwd, effPIMSecurityTestdriver);
		CommonUtils.hold(5);
		/*scontextCodeSCM="scmOp2004172036";
		contextCodePIM="sec2004172036";*/
		System.out.println("Item name to be created  is -"+itemNameSCM);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement, effPIMSecurityTestdriver);
		CommonUtils.hold(10);
		Assert.assertTrue(pimRunTime.createItem(itemNameSCM, "Item", childItemClassName2, effPIMSecurityTestdriver), "Item creation for Security completed - SCMOPERATIONS");
		Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage("Item","Security"), "Validation for Page existence under newly created Item");
		Assert.assertFalse(pimRunTime.contextExistsUnderPage("Security", contextCodePIM, "Item"),"Context "+contextCodePIM+" not displayed since privileges are not applied for SCMOPERATIONS");
		Assert.assertTrue(pimRunTime.contextExistsUnderPage("Security", contextCodeSCM, "Item"),"Context "+contextCodeSCM+" is displayed after security privileges are given to SCMOPERATIONS");
		pimRunTime.selectLOVSegmentValue(indSegment, indNumberValue);
		/*pimRunTime.selectLOVSegmentValue(depSegment, depNumberValue);*/
		pimRunTime.selectLOVSegmentValue(tabSegment, tabNumberValue);
		pimRunTime.itemSave();
	}
	
	@Test(dependsOnMethods = {"verifyRuntimePIMItemCreationForSCMOPERATIONS"},description="Check the runtime values for SCM secure context with LOV segments")
	public void validateLOVSegmentValuesForSecureContext() {
		Assert.assertEquals(effUtils.getLOVValue(indSegment), indNumberValue,"value selected for Independent Number Segment is correct");
		/*Assert.assertEquals(effUtils.getLOVValue(depSegment), depNumberValue,"value selected for Dependent Number Segment is correct");*/
		Assert.assertEquals(effUtils.getLOVValue(tabSegment), tabNumberValue,"value selected for Table Number Segment is correct");
		pimRunTime.itemSaveAndClose();
	} 
	
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMSecurityTestdriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMSecurityTestdriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMSecurityTestdriver);
		}finally {
			try {
				effPIMSecurityTestdriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
