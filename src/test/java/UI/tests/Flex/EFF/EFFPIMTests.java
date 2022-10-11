package UI.tests.Flex.EFF;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;

import static org.testng.Assert.assertTrue;

import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

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

public class EFFPIMTests extends GetDriverInstance {
	String uniqueID = null;
//	WebDriver driver = null;
	String contextCode=null;
	String contextName=null;
	String independentSegment=null;
	String dependentSegment=null;
	String itemName=null;
	String parentClassItemName=null;
	String childClassItemName=null;
	String tabSegmentName=null;
	String itemPageClassName = null;
	String itemUsage = null;
	String pimPrimaryItemUsage = null;
	String pimPrimaryItemUsageContextName = null;
	String pimPrimaryItemUsageContextCode = null;
	String itemUsagePage=null;
	String pimPrimaryUsageItemName = null;
	String pimPrimaryItemUsagePage = null;
	WebDriver effPIMTestdriver;
	private EFFUtils effUtils;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	//private DbResource dbConnection;	
	/*
	 * Valueset values should be charvs - {a1,a2}.
	 * numvs - {a1-{1001.00,1002.02},a2 - {2001.02,2002.00}}
	 * tabvs - select LOOKUP_CODE from AR_LOOKUPS where LOOKUP_TYPE='ACCOUNT_STATUS';
	 * chardep - {a1 - {a11,a12},a2-{a21,a22}}
	 * numtabvs - select APPLICATION_ID from FND_DF_SEGMENTS_B where SEGMENT_CODE='NH_CB_Character' or SEGMENT_CODE='HRT_CI_GlobalSegment1' or SEGMENT_CODE='VOLUNTARY_SICKNESS_INSURANCE' or SEGMENT_CODE='SFSS_FLAG';
	 * Default value for Number with SQL - select APPLICATION_ID from FND_DF_SEGMENTS_B where SEGMENT_CODE='NH_CB_Character';
	 * CharInd - Department WFMTL  Vision Operations (Account Payables)  Vision Construction	(Cash)
		CharDep - Account WFMTL, 
		Number_10_Key (10/20/30)
	 * 
	 * */
	
	@BeforeClass
	public void startUP() throws Exception {
		
		try{
		       itemUsagePage="IRPage";
		        itemPageClassName="1BBNode";
		        pimPrimaryItemUsagePage="NPage";
				effPIMTestdriver = getDriverInstanceObject();
				dep = new Deployment(effPIMTestdriver);
				pimRunTime = new RuntimeValidation(effPIMTestdriver);
				aLoginInstance = new ApplicationLogin(effPIMTestdriver);
			    ntFInstance = new NavigationTaskFlows(effPIMTestdriver);
			    nMEInstance = new NavigatorMenuElements(effPIMTestdriver);
			    effUtils = new EFFUtils(effPIMTestdriver);
			   /*dbConnection = new DbResource(effPIMTest);*/
			    deleteSegment();
				 
			    
		        CommonUtils.hold(5);
		}catch(Exception ex){
			System.out.println("EFF Error message = ===== : "+ex.getMessage());
			ex.printStackTrace();
		}
 
	}
	
	public void deleteSegment() throws Exception{
		try{
			System.out.println("Deleting Contexts from db");
		
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1BANode", itemUsagePage, "pimMRC%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1BANode", "pimMRC%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1BBNode", pimPrimaryItemUsagePage, "prIUC%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1BBNode", "prIUC%"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Parameters({"pwd","itemclass","Category","itemClassName","flexfield","flexCode"})
	@Test(description = "Check whether basic incremental deployment is successful or not")
	public void checkBasicPIMDeployment(String pwd,String itemclass,String Category, String itemClassName,String flexfield,String flexCode) throws Exception {
		aLoginInstance.login("pimqa", "Welcome1",effPIMTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
		CommonUtils.hold(10);
		dep.itemClassExists(Category);
		dep.navigateToPageAttributeGroups(Category);
		if(dep.searchPageUnderItemClass("Victor TS")) {
			Assert.assertTrue(dep.deleteExistingPage("Victor TS"),"Page delete assertion is successful");
			ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
	        CommonUtils.hold(10);
	        ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMTestdriver);
	        CommonUtils.hold(10);
	        Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
	        Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMTestdriver), "Incremental Deployment is successful");
	        aLoginInstance.logout(effPIMTestdriver);
	        aLoginInstance.login("pimqa", "Welcome1",effPIMTestdriver);
		}
		else {
			System.out.println("Page - 'Victor TS' does not exist under Root Item Class");
		}
		
	}
	
	@Parameters({"pwd","itemclass","Category","itemClassName"})
    @Test(dependsOnMethods = {"checkBasicPIMDeployment"}, description = "Create, Check the new Item Classes")
    public void validateNewItemClassExistence(String pwd,String itemclass,String Category, String itemClassName) throws Exception {
        boolean bSearchItemExists=false;
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(10);
        ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
        CommonUtils.hold(10);
        System.out.println("Inside validateNewItemClassExistence");
        
        if(!dep.itemClassExists(itemClassName)) {
            System.out.println("Item Class - "+itemClassName+" is not present");
            dep.createItemClass(Category,itemClassName, effPIMTestdriver);
            effUtils.scrollToPageTop();
            Assert.assertTrue(dep.itemClassExists(itemClassName), "Validation of Item Class successful");
            //dep.closeItemClassTaskFlow();
        }
        else {
            System.out.println("Item Class - "+itemClassName+" exists");
            bSearchItemExists=true;
            Assert.assertTrue(bSearchItemExists,"Item Class - "+itemClassName+" search validation successful");
        }
    }
 
    @Parameters({"itemclass","itemClassName","childItemClassName1"})
    @Test(dependsOnMethods = {"validateNewItemClassExistence"}, description = "Create , Check first child Item Class")
    public void validatefirstChildItemClassExistence(String itemclass,String itemClassName, String childItemClassName1) throws Exception {
        boolean bSearchItemExists=false;
        itemPageClassName="1BBNode";
        System.out.println("Inside validatefirstChildItemClassExistence");
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(10);
        ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
        
        if(!dep.itemClassExists(childItemClassName1)) {
            System.out.println("Item Class - "+childItemClassName1+" is not present");
            dep.createItemClass(itemClassName,childItemClassName1, effPIMTestdriver);
            effUtils.scrollToPageTop();
            dep.closeItemClassTaskFlow();
            CommonUtils.hold(3);
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
            CommonUtils.hold(5);
            ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
            Assert.assertTrue(dep.itemClassExists(childItemClassName1), "Validation of Item Class successful");
        }
        else {
            System.out.println("Item Class exists");
            bSearchItemExists=true;
            Assert.assertTrue(bSearchItemExists,"Item Class - "+childItemClassName1+" search validation successful");
        }
        if(!dep.itemClassExists(itemPageClassName)) {
            System.out.println("Item Class - "+childItemClassName1+" is not present");
            dep.createItemClass(itemClassName,itemPageClassName, effPIMTestdriver);
            effUtils.scrollToPageTop();
             dep.closeItemClassTaskFlow();
            CommonUtils.hold(3);
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
            CommonUtils.hold(5);
            ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
            Assert.assertTrue(dep.itemClassExists(itemPageClassName), "Validation of Item Class successful");
        }
        else {
            System.out.println("Item Class exists");
            bSearchItemExists=true;
            Assert.assertTrue(bSearchItemExists,"Item Class - "+itemPageClassName+" search validation successful");
        }
    }
 
    @Parameters({"itemclass","childItemClassName1","grandChildItemClassName1"})
    @Test(dependsOnMethods = {"validatefirstChildItemClassExistence"},description = "Create,Check first grand child Item Class")
    public void validatefirstGrandChildItemClassExistence(String itemclass,String childItemClassName1, String grandChildItemClassName1) throws Exception {
        boolean bSearchItemExists=false;
        System.out.println("Inside validatefirstGrandChildItemClassExistence");
        if(!dep.itemClassExists(grandChildItemClassName1)) {
            System.out.println("Item Class - "+grandChildItemClassName1+" is not present");
            dep.createItemClass(childItemClassName1,grandChildItemClassName1, effPIMTestdriver);
            effUtils.scrollToPageTop();
            dep.closeItemClassTaskFlow();
            CommonUtils.hold(3);
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
            CommonUtils.hold(5);
            ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
            Assert.assertTrue(dep.itemClassExists(grandChildItemClassName1), "Validation of Item Class successful");
        }
        else {
            System.out.println("Item Class exists");
            bSearchItemExists=true;
            Assert.assertTrue(bSearchItemExists,"Item Class - "+grandChildItemClassName1+" search validation successful");
        }
        
        dep.closeItemClassTaskFlow();
    }
 
    @Parameters({"flexfield","flexCode"})
    @Test(dependsOnMethods = {"validatefirstGrandChildItemClassExistence"},description = "Create a multi Row Context for PIM")
    public void createPIMItemContextMultipleRowForSecondaryItemUsage(String flexfield, String flexCode) throws Exception {
        contextName = "pimMRC";
        itemUsage="Item Revision";
        String contextDescription = contextName;
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMTestdriver);
        CommonUtils.hold(5);
        Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
        Assert.assertTrue(dep.createContext(flexCode, contextName, contextDescription, "Multiple Rows",itemUsage), "Context - "+contextName+" created successfully");
        contextCode=dep.getcontextCode(contextName);
    }
 
    @Parameters({"displayType_DD"})
    @Test(dependsOnMethods = {"createPIMItemContextMultipleRowForSecondaryItemUsage"},description = "Segment with UniqueKey")
    public void createUniqueSegmentCharacterIndependentValueSet(String displayType_DD) throws Exception {
        independentSegment="CharIndSegment"+CommonUtils.uniqueId();
        System.out.println("Current Context code - "+contextCode);
        System.out.println("Segment to be created - "+independentSegment);
        System.out.println("Verification of Segment Creation with Character type , Independent Value Set");
        Assert.assertTrue(dep.createSegment(independentSegment, "Character", "ADS Job Function", "Multiple Rows", "Drop-down List", true),"Segment creation assertion is successful");
    }
 
    @Parameters({"displayType_DD"})
    @Test(dependsOnMethods = {"createUniqueSegmentCharacterIndependentValueSet"},description = "Segment with dependent valueset")
    public void createNonUniqueSegmentNumberDependentValueSet(String displayType_DD) throws Exception {
        
        dependentSegment="CharDepSegment"+CommonUtils.uniqueId();
        System.out.println("Current Context code - "+contextCode);
        System.out.println("Segment to be created - "+dependentSegment);
        System.out.println("Verification of Segment Creation with Character type , Independent Value Set");
        Assert.assertTrue(dep.createSegment(dependentSegment, "Character", "ADS Job Family", "Multiple Rows", "Drop-down List", false),"Segment creation assertion is successful");
        dep.navigateToCategoriesPage();
    }
 
    @Parameters({"flexfield","flexCode"})
    @Test(dependsOnMethods = {"createNonUniqueSegmentNumberDependentValueSet"},description = "Create a Context, Segment for Primary Item usage under PIM ")
    public void createContextSegmentForPIMItem(String flexfield,String flexCode) throws Exception {
        pimPrimaryItemUsage="Item";
        pimPrimaryItemUsageContextName="prIUC";
        tabSegmentName="tabSeg"+CommonUtils.uniqueId();
        String pimPrimaryItemUsageContextDesc="Creating Context for Item with table type vs";
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMTestdriver);
        CommonUtils.hold(5);
        dep.searchFlexFieldCode(flexCode);
        Assert.assertTrue(dep.createContext(flexCode, pimPrimaryItemUsageContextName,pimPrimaryItemUsageContextDesc , "Multiple Rows",pimPrimaryItemUsage), "Context - "+pimPrimaryItemUsageContextName+" created successfully");
        pimPrimaryItemUsageContextCode=dep.getcontextCode(contextName);
        Assert.assertTrue(dep.createSegment(tabSegmentName, "Character", "tabvs", "Multiple Rows", "List of Values", true),"Segment creation assertion is successful");
    }
 
    @Parameters({"childItemClassName1","itemclass"})
    @Test(dependsOnMethods = {"createContextSegmentForPIMItem"},description = "Associate Context to Child Item Class")
    public void associatePIMContextToChildItemClass(String childItemClassName1,String itemclass) throws Exception {
        
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
        if (dep.itemClassExists(childItemClassName1)) {
            dep.navigateToPageAttributeGroups(childItemClassName1);
            Assert.assertTrue(dep.associateContextToItemClass(contextCode), "Context associated with Category");
        }
    }
 
    @Parameters({"childItemClassName1","itemclass"})
    @Test(dependsOnMethods = {"associatePIMContextToChildItemClass"},description = "Create,Search Page under Child Item Class")
    public void createSearchPIMPageForItemRevision(String childItemClassName1,String itemclass) throws Exception {
        CommonUtils.hold(8);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
        if (dep.itemClassExists(childItemClassName1)) {
            dep.navigateToPageAttributeGroups(childItemClassName1);
            Assert.assertTrue(dep.navigateToPageLinkUnderItemClass(itemUsagePage,itemUsage,effPIMTestdriver)," validation of Page search under Item class is successful..");
        }
 
    }
 
    @Parameters({"childItemClassName1","itemclass"})
    @Test (dependsOnMethods = {"createSearchPIMPageForItemRevision"},description = "Associate Context to Page under Child Item Class")
    public void verifyAssociatePageContext(String childItemClassName1, String itemclass) throws Exception {
        System.out.println("Parameters for 'verifyAssociatePageContext' - flexcode - EGO_ITEM_EFF, contextName - Item Revision, contextUsage - "+itemUsage+", pageName - "+itemUsagePage);
        
        Assert.assertTrue(dep.associateContextToPage(itemUsagePage, contextCode, "Item Revision"), "Context association to Page assertion is successful");
        dep.itemClassSave();
    } 
 
    @Parameters({"itemclass"})
    @Test(dependsOnMethods = {"verifyAssociatePageContext"},description = "Associate Primary Item Usage related Context to its relevant Itemclass and Page")
    public void associateContextToPrimaryItemUsageClassAndPage(String itemclass) throws Exception {
        System.out.println("Item creation for Primary Item Usage : Itemname -"+pimPrimaryUsageItemName+" , Item Classname - "+itemPageClassName+" , Page name - "+pimPrimaryItemUsagePage);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(10);
        ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
        CommonUtils.hold(10);
        if(!dep.itemClassExists(itemPageClassName)) {
            System.out.println("Item Class - "+itemPageClassName+" is not present");
            dep.createItemClass("1ANode",itemPageClassName, effPIMTestdriver);
            dep.closeItemClassTaskFlow();
            CommonUtils.hold(3);
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
            CommonUtils.hold(10);
            ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMTestdriver);
            CommonUtils.hold(10);
        }
        else {
            System.out.println("Item Class exists");
        }
        dep.navigateToPageAttributeGroups(itemPageClassName);
        dep.associateContextToItemClass(pimPrimaryItemUsageContextCode);
        dep.itemClassSaveAndClose();
        dep.navigateToPageAttributeGroups(itemPageClassName);
        dep.navigateToPageLinkUnderItemClass(pimPrimaryItemUsagePage,"Item", effPIMTestdriver);
        Assert.assertTrue(dep.associateContextToPage(pimPrimaryItemUsagePage, pimPrimaryItemUsageContextCode, "Item"), "Context association to Page assertion is successful");
        dep.itemClassSave();
    }
 
    @Parameters({"flexfield","flexCode"})
    @Test(dependsOnMethods = {"associateContextToPrimaryItemUsageClassAndPage"},description = "Incremental Deployment")
    public void verifyIncrementalDeployFlexForPIM(String flexfield,String flexCode) throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMTestdriver);
        CommonUtils.hold(10);
        ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMTestdriver);
        CommonUtils.hold(10);
        Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
        Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMTestdriver), "Incremental Deployment is successful");
        aLoginInstance.logout(effPIMTestdriver);
    }
 
    @Parameters({ "user", "pwd","childItemClassName1"})
    @Test(dependsOnMethods = {"verifyIncrementalDeployFlexForPIM"},description = "Ensure Item creation and saving is successful")
    public void verifyRuntimePIMItemCreation(String user,String pwd,String childItemClassName1) throws Exception {
        itemName = "v_"+childItemClassName1+CommonUtils.uniqueId();
        itemUsage="Item Revision";
        itemUsagePage="IRPage";
        itemPageClassName="1BBNode";
        pimPrimaryItemUsage="Item";
        pimPrimaryItemUsagePage="NPage";
        /*contextCode="pimMRC210618031401";
        independentSegment="CharIndSegment210618031602";
        dependentSegment="CharDepSegment210618031703";
        pimPrimaryItemUsageContextCode="prIUC210618032104";
        tabSegmentName="tabSeg210618032505";*/
        System.out.println("Item name to be created  is -"+itemName);
        aLoginInstance.login("pimqa", pwd,effPIMTestdriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMTestdriver);
        CommonUtils.hold(5);
        Assert.assertTrue(pimRunTime.createItem(itemName, itemUsage, childItemClassName1, effPIMTestdriver), "Validation successful for Item creation");
    }
 
    @Test(dependsOnMethods = {"verifyRuntimePIMItemCreation"},description = "Check page reflected runtime")
    public void verifyRunTimePageExistenceUnderItemCreated() {
        
        Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage(itemUsage,itemUsagePage), "Validation for Page existence under newly created Item");
    }
 
    @Test(dependsOnMethods= {"verifyRunTimePageExistenceUnderItemCreated"},description = "Check Context reflected runtime")
    public void verifyRunTimeContextExistsUnderPage() {
        System.out.println("Current context - "+contextCode);
        Assert.assertTrue(pimRunTime.contextExistsUnderPage(itemUsagePage, contextCode, itemUsage),"Validation of Context under newly created Page is successful");
        dep.scrollToPageBottom();
        pimRunTime.createSegmentValuesForInd_DepVS(independentSegment, dependentSegment);
        dep.scrollToPageTop();
        pimRunTime.itemSave();
    }
 
    @Test(dependsOnMethods= {"verifyRunTimeContextExistsUnderPage"},description = "Check Segment reflected runtime")
    public void validateSegmentValuesSavedUnderItem() {
        dep.scrollToPageBottom();
        Assert.assertTrue(pimRunTime.searchSegmentValuesUnderItem(independentSegment), "Validation of Segment value is successful");
        dep.scrollToPageTop();
        pimRunTime.itemSaveAndClose();
    }
 
    @Parameters({"itemClassName"})
    @Test(dependsOnMethods= {"validateSegmentValuesSavedUnderItem"},description = "Check page under Parent Itemclass at runtime")
    public void validatePageExistenceUnderParentItemClass(String itemClassName) throws Exception {
        
        parentClassItemName="v_"+itemClassName+CommonUtils.uniqueId();
        Assert.assertTrue(pimRunTime.createItem(parentClassItemName, itemUsage, itemClassName, effPIMTestdriver), "Validation successful for Item creation");
        Assert.assertFalse(pimRunTime.pageExistsUnderItemUsage(itemUsage,itemUsagePage), "Validation for Page existence under Parent ItemClass Item completed. Page not displayed ");
    }
 
    @Parameters({"grandChildItemClassName1"})
    @Test(dependsOnMethods= {"validatePageExistenceUnderParentItemClass"},description = "Check page reflected under grand Child item class at runtime")
    public void validatePageAndContextExistenceUnderChildItemClass(String grandChildItemClassName1) throws Exception {
        childClassItemName="v_"+grandChildItemClassName1+CommonUtils.uniqueId();
        Assert.assertTrue(pimRunTime.createItem(childClassItemName, itemUsage, grandChildItemClassName1, effPIMTestdriver), "Validation successful for Item creation");
        dep.scrollToPageBottom();
        Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage(itemUsage,itemUsagePage), "Validation for Page existence under Child ItemClass Item completed. Page -"+itemUsagePage+" is displayed ");
        Assert.assertTrue(pimRunTime.contextExistsUnderPage(itemUsagePage, contextCode, itemUsage),"Validation of Context under newly created Page for Child ItemClass - "+grandChildItemClassName1+" is successful");
        dep.scrollToPageTop();
        pimRunTime.itemSaveAndClose();
    }
 
    @Test(dependsOnMethods= {"validatePageAndContextExistenceUnderChildItemClass"},description = "Check page and Context reflected under Primary Usage related item class at runtime")
    public void validatePageAndContextForPrimaryItemUsage() throws Exception {
        
        pimPrimaryUsageItemName="v_"+pimPrimaryItemUsage+CommonUtils.uniqueId();
        System.out.println("Item creation for Primary Item Usage : Itemname -"+pimPrimaryUsageItemName+" , Item Usage - "+pimPrimaryItemUsage+" , Page name - "+pimPrimaryItemUsagePage);
        Assert.assertTrue(pimRunTime.createItem(pimPrimaryUsageItemName, pimPrimaryItemUsage, itemPageClassName, effPIMTestdriver), "Validation successful for Item creation");
        Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage(pimPrimaryItemUsage,pimPrimaryItemUsagePage), "Validation for Page existence under Parent ItemClass Item completed. Page not displayed ");
        Assert.assertTrue(pimRunTime.contextExistsUnderPage(pimPrimaryItemUsagePage, pimPrimaryItemUsageContextCode, pimPrimaryItemUsage),"Validation of Context under newly created Page is successful");
    }
 
    @Test(dependsOnMethods= {"validatePageAndContextForPrimaryItemUsage"},description = "select table value from LOV under Item, save the values")
    public void updateSaveTableValuesUnderPrimaryItemUsageItem() {
        
        Assert.assertTrue(pimRunTime.updateTableSegmentValues(tabSegmentName,"Preferred",effPIMTestdriver)," Value - Preferred is displayed under LOV");
        pimRunTime.itemSave();
        Assert.assertTrue(pimRunTime.updateTableSegmentValues(tabSegmentName,"INTERNAL",effPIMTestdriver),"Value - INTERNAL is displayed under LOV");
        pimRunTime.itemSave();
    }
 
    @Test(dependsOnMethods= {"updateSaveTableValuesUnderPrimaryItemUsageItem"},description = "Search the values from user saved Table values under Item")
    public void searchTableValuesUnderPrimaryUsageItem() {
        Assert.assertTrue(pimRunTime.validateTableValueRunTime(tabSegmentName, "Preferred",effPIMTestdriver),"Validation for Run time value is successful");
        Assert.assertTrue(pimRunTime.validateTableValueRunTime(tabSegmentName, "INTERNAL",effPIMTestdriver),"Validation for Run time value is successful");
        effUtils.scrollToPageTop();
        pimRunTime.itemSaveAndClose();
    }
 
    @Parameters({ "childItemClassName1"})
    @Test(dependsOnMethods= {"searchTableValuesUnderPrimaryUsageItem"},description = "Search item details at run time")
    public void basicItemsSearch(String childItemClassName1) throws Exception {
    	CommonUtils.hold(5);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMTestdriver);
    	CommonUtils.hold(5);
        pimRunTime.openManageItems();
        Assert.assertTrue(pimRunTime.basicManageItemSearch(childItemClassName1, itemName), "validation of Basic Item search is successful");
    }
 
    @Test(dependsOnMethods = {"basicItemsSearch"},description = "Search items with attribute groups and attributes")
    public void verifyAddAttributesToManageItemsSearch() throws Exception {
        Assert.assertTrue(pimRunTime.addFieldsSearchItems(contextCode, independentSegment), "Validation of adding a Attribute from deployed Context is successful");
    }
		
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMTestdriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMTestdriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMTestdriver);
		}finally {
			try {
				effPIMTestdriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
