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
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import TestBase.UI.GetDriverInstance;

public class EFFPIMKeywordsValidation extends GetDriverInstance {
	String uniqueID = null;
//	WebDriver driver = null;
	String contextCode=null;
	String contextName=null;
	String textSegmentName=null;
	String numSegmentName=null;
	String itemName=null;
	String childItemClassName=null;
	String itemclass=null;
	String pageName=null;
	String charVSCode = null;
	String numVSCode = null;
	String charDT = null;
    String numDT = null;
	WebDriver effPIMKeyWordTestdriver;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private EFFUtils effUtils;
	//private DbResource dbConnection;	
	/*
	 * Valueset Details:
	 * char_frm_vs - Charater, 10 Charaters limit
	 * numv_frm_vs - Number, 2-Scale, 6-Precision
	 * 
	 * accClass_tab - select LOOKUP_CODE from AR_LOOKUPS where LOOKUP_TYPE='ACCOUNT_CLASSES';
	 * accSetTypes_tab - select LOOKUP_CODE from AR_LOOKUPS where LOOKUP_TYPE='ACCOUNT_SET_TYPES';
	 * */
	
	public EFFPIMKeywordsValidation() throws WebDriverException {
		
	}
	
	@BeforeClass
	public void startUP() throws Exception {
		effPIMKeyWordTestdriver =  getDriverInstanceObject();
		dep = new Deployment(effPIMKeyWordTestdriver);
		pimRunTime = new RuntimeValidation(effPIMKeyWordTestdriver);
		aLoginInstance = new ApplicationLogin(effPIMKeyWordTestdriver);
		ntFInstance = new NavigationTaskFlows(effPIMKeyWordTestdriver);
		nMEInstance = new NavigatorMenuElements(effPIMKeyWordTestdriver);
		effUtils = new EFFUtils(effPIMKeyWordTestdriver);
		//dbConnection = new DbResource(effPIMKeyWordTest);
		deleteSegment();
		
		
	}
	
	public void deleteSegment() throws Exception{
		try{
			System.out.println("Deleting Contexts from db");
	//		DbResource.getDBConnection();
	//		DbResource.createDbStatement();
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", "1ValNode", "Validations", "DTValues%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", "1ValNode", "DTValues%"));
			/*CommonUtils.SqlStatement.executeUpdate(EFFUtils.deleteSegmentsFromContexts("EGO_ITEM_EFF", "pimMRC%"));
			CommonUtils.SqlStatement.executeUpdate(EFFUtils.deleteContexts("EGO_ITEM_EFF", "pimMRC%"));
			CommonUtils.SqlStatement.executeUpdate(EFFUtils.deletePageFromItemClass("EGO_ITEM_EFF", "NPage"));*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		//	DbResource.dbConnectionClose();
			
		}
	}
	
	@Parameters({"flexfield","flexCode","pwd"})
	@Test(priority = 1,description="Check the Context name with SQL keyword - Update")
	public void validateContextWithSQLKeywordUpdate(String flexfield,String flexCode,String pwd) throws Exception {
		aLoginInstance.login("pimqa", pwd, effPIMKeyWordTestdriver);
		CommonUtils.hold(15);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMKeyWordTestdriver);
		CommonUtils.hold(15);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effPIMKeyWordTestdriver);
		CommonUtils.hold(15);
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		dep.navigateToCreateContext();
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("Update"), "Validation on SQL keyword - 'Update' is successful for Context creation");
		dep.cancelContextSegmentCreation();
	}
	
	@Test(priority = 2 , dependsOnMethods = {"validateContextWithSQLKeywordUpdate"},description="Check the Context name with SQL keyword - Select")
	public void validateContextWithSQLKeywordSelect() {
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("Select"), "Validation on SQL keyword - 'Select' is successful for Context creation");
		dep.cancelContextSegmentCreation();
	}
	
	@Test(priority = 3, dependsOnMethods = {"validateContextWithSQLKeywordSelect"},description="Check the Context name with SQL keyword - Where")
	public void validateContextWithSQLKeywordWhere() {
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("Where"), "Validation on SQL keyword - 'Where' is successful for Context creation");
		dep.cancelContextSegmentCreation();
	}
	
	@Test(priority = 4, dependsOnMethods = {"validateContextWithSQLKeywordWhere"},description="Check the Context name with java keyword - If")
	public void validateContextWithJavaKeywordIf() {
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("If"), "Validation on SQL keyword - 'If' is successful for Context creation");
		dep.cancelContextSegmentCreation();
	}
	
	@Test(priority = 5, dependsOnMethods = {"validateContextWithJavaKeywordIf"},description="Check the Context name with java keyword - For")
	public void validateContextWithJavaKeywordFor() {
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("For"), "Validation on SQL keyword - 'for' is successful for Context creation");
		dep.cancelContextSegmentCreation();
	}
	
	@Test(priority=6, dependsOnMethods = {"validateContextWithJavaKeywordFor"},description="Check the Context name with special characters")
	public void validateContextWithSpecialCharacters() {
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("#Is"), "Validation on SQL keyword - '#ui' is successful for Context creation");
		dep.cancelContextSegmentCreation();
		dep.clickCreateContext();
		Assert.assertTrue(dep.validateContextCreateWithKeywords("#Is"), "Validation on SQL keyword - '$new' is successful for Context creation");
		dep.cancelContextSegmentCreation();
	}
	
	@Test(priority=7,dependsOnMethods = {"validateContextWithSpecialCharacters"},description="Check the segment creation with special characters - #,$")
    public void validateSegmentWithJavaKeyword() throws Exception {
        contextName="DTValues";
        textSegmentName="textfrm"+CommonUtils.uniqueId();
        numSegmentName="numfrm"+CommonUtils.uniqueId();
        itemclass="Manage Item Classes";
        childItemClassName="1ValNode";
        pageName="Validations";
        charVSCode = "1char_frm_vs";
        numVSCode = "num_frm_vs";
        boolean bSearchItemExists=false;
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effPIMKeyWordTestdriver);
        CommonUtils.hold(15);
        ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields",effPIMKeyWordTestdriver);
        CommonUtils.hold(15);
        dep.searchFlexFieldCode("EGO_ITEM_EFF");
        dep.createContext("EGO_ITEM_EFF", "cancelCon", "Values Validation", "Single Row","Item");
        //contextCode=dep.getcontextCode(contextName);
       // dep.createSegment(textSegmentName, "Character", charVSCode, "Single Row", "Text Box", false); //This Segment VS has max 10 characters.
        dep.clickCreateSegmentIcon(effPIMKeyWordTestdriver);
        Assert.assertTrue(dep.validateSegmentWithKeyWord("For"), "Validation on SQL keyword - 'for' is successful for Segment creation");
       /* Assert.assertTrue(dep.validateSegmentWithKeyWord("If"), "Validation on SQL keyword - 'if' is successful for Segment creation");*/
        dep.cancelContextSegmentCreation();
        CommonUtils.hold(4);
        dep.cancelContextSegmentCreation();
        CommonUtils.hold(4);
     }
 
    @Test(priority=8,dependsOnMethods = {"validateSegmentWithJavaKeyword"},description="create setup and deploy the context")
    public void createAndDeployContextWithFormatSegments() throws Exception {
            charDT = "Character";
            numDT = "Number";
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effPIMKeyWordTestdriver);
            CommonUtils.hold(15);
            ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields",effPIMKeyWordTestdriver);
            CommonUtils.hold(15);
            dep.searchFlexFieldCode("EGO_ITEM_EFF");
            dep.createContext("EGO_ITEM_EFF", contextName, "Values Validation", "Single Row","Item");
            contextCode=dep.getcontextCode(contextName);
            dep.createSegment(textSegmentName, charDT, charVSCode, "Single Row", "Text Box", false); //This Segment VS has max 10 characters.
            /*dep.createSegment(numSegmentName, numDT, numVSCode, "Single Row", "Text Box", false);*/ //This Segment VS has max 6 numbers (6-precision, 2-scale).
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effPIMKeyWordTestdriver);
            CommonUtils.hold(15);
            ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMKeyWordTestdriver);
            CommonUtils.hold(15);
            if(!dep.itemClassExists(childItemClassName)) {
                System.out.println("Item Class - "+childItemClassName+" is not present");
                dep.createItemClass("1ANode",childItemClassName, effPIMKeyWordTestdriver);
                dep.closeItemClassTaskFlow();
                CommonUtils.hold(3);
                ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effPIMKeyWordTestdriver);
                CommonUtils.hold(15);
                ntFInstance.navigateToAOLTaskFlows(itemclass, effPIMKeyWordTestdriver);
                CommonUtils.hold(15);
            }
            else {
                System.out.println("Item Class exists");
            }
            dep.navigateToPageAttributeGroups(childItemClassName);
            dep.associateContextToItemClass(contextCode);
            dep.itemClassSaveAndClose();
            dep.navigateToPageAttributeGroups(childItemClassName);
            dep.navigateToPageLinkUnderItemClass(pageName,"Item", effPIMKeyWordTestdriver);
            dep.associateContextToPage(pageName, contextCode, "Item");
            dep.itemClassSave();
            ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effPIMKeyWordTestdriver);
            CommonUtils.hold(15);
            ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields", effPIMKeyWordTestdriver);
            CommonUtils.hold(15);
            dep.searchFlexFieldCode("EGO_ITEM_EFF");
            Assert.assertTrue(dep.deployFlexFieldIncremental("EGO_ITEM_EFF", effPIMKeyWordTestdriver), "Incremental Deployment is successful");
            aLoginInstance.logout(effPIMKeyWordTestdriver);
    }
 
    @Test(priority=9,dependsOnMethods = {"createAndDeployContextWithFormatSegments"},description="Check the segment error messages for invalid data")
    public void validateTextSegmentMessagesForInvalidData() throws Exception {
        childItemClassName="1ValNode";
        pageName="Validations";
        itemName = "v_"+childItemClassName+CommonUtils.uniqueId();
        System.out.println("Item name to be created  is -"+itemName);
        aLoginInstance.login("pimqa", "Welcome1", effPIMKeyWordTestdriver);
        CommonUtils.hold(15);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMKeyWordTestdriver);
        CommonUtils.hold(15);
        Assert.assertTrue(pimRunTime.createItem(itemName, "Item", childItemClassName, effPIMKeyWordTestdriver), "Item creation for Security completed - PIMQA");
        Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage("Item",pageName), "Validation for Page existence under newly created Item");
        Assert.assertTrue(pimRunTime.contextExistsUnderPage(pageName, contextCode, "Item"),"Source Context is displayed under Item");
        String resultMessage = pimRunTime.getErrorMessageForFormatTypeSegment(textSegmentName, "Increasing text length to twenty to check the error message",charDT, effPIMKeyWordTestdriver);
        Assert.assertNotNull(resultMessage, "Error message from RunTime is not null for OOB Text input");
        Assert.assertEquals(resultMessage.contains("maximum allowable length 10"), true, "Proper Error message is displayed in UI");
        pimRunTime.getErrorMessageForFormatTypeSegment(textSegmentName, "validText",charDT, effPIMKeyWordTestdriver);
        pimRunTime.itemSaveAndClose();
    }
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMKeyWordTestdriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMKeyWordTestdriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMKeyWordTestdriver);
		}finally {
			try {
				effPIMKeyWordTestdriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
