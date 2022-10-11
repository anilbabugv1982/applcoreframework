package UI.tests.Flex.EFF;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;

public class EFFGroovyTest extends GetDriverInstance {
	String contextName;
	String currentUniqueID;
	String contextCode;
	String segmentName;
	String itemUsageType;
	String childItemClassName;
	String pageName;
	String itemName;
	String currentLoggedInUser;
	WebDriver effGroovyTestDriver;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	//private DbResource dbConnection;	
	private EFFUtils effUtils;
	
	@BeforeClass
	public void startUP() throws Exception {
		effGroovyTestDriver =  getDriverInstanceObject();
		dep = new Deployment(effGroovyTestDriver);
		currentUniqueID=CommonUtils.uniqueId();
		contextName = "GrCon";
		segmentName="grseg"+currentUniqueID;
		itemUsageType="Item Supplier";
		childItemClassName = "1BCNode";
		pageName="GrPage";
		currentLoggedInUser="PIMQA";
		pimRunTime = new RuntimeValidation(effGroovyTestDriver);
		aLoginInstance = new ApplicationLogin(effGroovyTestDriver);
		ntFInstance = new NavigationTaskFlows(effGroovyTestDriver);
		nMEInstance = new NavigatorMenuElements(effGroovyTestDriver);
		effUtils = new EFFUtils(effGroovyTestDriver);
	//	dbConnection = new DbResource(effPIMCrossContextTest);
		deleteSegment();
	}
	
	public void deleteSegment() throws Exception{
		try{
			
			System.out.println("Deleting Contexts from db");
		//	DbResource.getDBConnection();
		//	DbResource.createDbStatement();
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("EGO_ITEM_EFF", childItemClassName, pageName, "GrCon%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("EGO_ITEM_EFF", childItemClassName, "GrCon%"));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DbResource.dbConnectionClose();
		}
	}
	
	@Parameters({"flexfield","flexCode","pwd"})
	@Test(description="Create a source Context with single Row and Segment with Groovy Constant")
	public void createContextSegmentWithGroovyConstant(String flexfield, String flexCode,String pwd) throws Exception {
		aLoginInstance.login("SCMOPERATIONS", pwd, effGroovyTestDriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effGroovyTestDriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effGroovyTestDriver);
		CommonUtils.hold(5);
		dep = new Deployment(effGroovyTestDriver);
		String contextDescription = contextName;
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		Assert.assertTrue(dep.createContext(flexCode, contextName, contextName, "Single Row",itemUsageType), "Context - "+contextName+" created successfully");
		contextCode=dep.getcontextCode(contextName);
		System.out.println("Current Context code - "+contextCode);
		System.out.println("Segment to be created - "+segmentName);
		Assert.assertTrue(dep.createSegment(segmentName, "Character", "120 Characters", "Single Row", "Text Box", false),"Source Segment creation - "+segmentName+" assertion is successful");
		dep.editSegment(segmentName);
		Assert.assertTrue(dep.updateGroovyConstantForSegment(),"Updating Groovy Constant is successful");
		dep.segmentSaveAndClose();
		dep.navigateToCategoriesPage();
		dep.saveAndCloseButtonUnderCategoryPages();
	}
	
	/*@Test(dependsOnMethods = {"createContextSegmentWithGroovyConstant"},description="Add Groovy Validator at Context level")
	public void addValidatorAtContextLevel() {
		Assert.assertTrue(dep.addValidatorToContext(segmentName), "Adding Validator to the Context is successful");
	}*/
	
	@Parameters({"itemClassName","itemclass","flexfield","flexCode"})
	@Test(dependsOnMethods = {"createContextSegmentWithGroovyConstant"},description="Associate Context to Item class, page and deploy the flex")
	public void associateContextAndDeployFlex(String itemClassName,String itemclass,String flexfield, String flexCode) throws Exception {
		boolean bSearchItemExists=false;
		
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effGroovyTestDriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(itemclass, effGroovyTestDriver);
		CommonUtils.hold(10);
		if (!dep.itemClassExists(childItemClassName)) {
			System.out.println("Item Class - "+childItemClassName+" is not present");
			dep.createItemClass(itemClassName,childItemClassName, effGroovyTestDriver);
			CommonUtils.waitForPageLoad(effGroovyTestDriver);
			dep.closeItemClassTaskFlow();
			CommonUtils.hold(3);
			ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effGroovyTestDriver);
			CommonUtils.hold(10);
			ntFInstance.navigateToAOLTaskFlows(itemclass, effGroovyTestDriver);
			CommonUtils.hold(10);
			Assert.assertTrue(dep.itemClassExists(childItemClassName), "Validation of Item Class successful");
		}
		else {
			System.out.println("Item Class exists");
			bSearchItemExists=true;
			Assert.assertTrue(bSearchItemExists,"Item Class - "+childItemClassName+" search validation successful");
		}
		dep.navigateToPageAttributeGroups(childItemClassName);
		CommonUtils.hold(3);
		Assert.assertTrue(dep.associateContextToItemClass(contextCode), "Source Context associated with Category");
		dep.itemClassSaveAndClose();
		dep.navigateToPageAttributeGroups(childItemClassName);
		Assert.assertTrue(dep.navigateToPageLinkUnderItemClass(pageName,itemUsageType, effGroovyTestDriver)," validation of Page search under Item class is successful..");
		System.out.println("Parameters for 'associateContextAndDeployFlex' - flexcode - "+flexCode+", contextName - "+contextCode+", contextUsage - "+itemUsageType+", pageName - "+pageName);
		Assert.assertTrue(dep.associateContextToPage(pageName, contextCode, itemUsageType), "Source Context association to Page assertion is successful");
		dep.itemClassSave();
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effGroovyTestDriver);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows(flexfield, effGroovyTestDriver);
		CommonUtils.hold(10);
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effGroovyTestDriver), "Incremental Deployment is successful");
		aLoginInstance.logout(effGroovyTestDriver);
	}
	
	@Test(dependsOnMethods = {"associateContextAndDeployFlex"},description="Create a new Item and add a supplier to the Organization")
	public void createItemSupplier() throws Exception {
		/*contextCode="GrCon210615095103";
		segmentName="grseg210615094710";*/
		itemName = "v_"+childItemClassName+CommonUtils.uniqueId();
		System.out.println("Item name to be created  is -"+itemName);
        aLoginInstance.login(currentLoggedInUser, "Welcome1",effGroovyTestDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effGroovyTestDriver);
        CommonUtils.hold(15);
        Assert.assertTrue(pimRunTime.createItem(itemName, itemUsageType, childItemClassName, effGroovyTestDriver), "Validation successful for Item creation");
        Assert.assertTrue(pimRunTime.createSupplierUnderItem(),"Supplier Organization is created successfully");
	}
	
	@Test(dependsOnMethods = {"createItemSupplier"},description="Create a new Item and add a supplier to the Organization")
	public void checkDefaultGroovyValue() {
		pimRunTime.navigateToSupplierPage();
		Assert.assertTrue(pimRunTime.pageExistsUnderItemUsage(itemUsageType, pageName),"Page - "+pageName+" displayed under Supplier");
		Assert.assertTrue(pimRunTime.contextExistsUnderPage(pageName, contextCode, "Item Supplier"),"Context - "+contextCode+" is displayed under the page - "+pageName);
		Assert.assertTrue(pimRunTime.checkSegmentDisplayed(segmentName),"Segment - "+segmentName+" is displayed");
		Assert.assertTrue(pimRunTime.checkDefaultGroovySegmentValue(currentLoggedInUser, segmentName));
		pimRunTime.itemSaveAndClose();
	}
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effGroovyTestDriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effGroovyTestDriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effGroovyTestDriver);
		}finally {
			try {
				effGroovyTestDriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
