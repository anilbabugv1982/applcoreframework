package UI.tests.Flex.EFF;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

public class EFFHCMCore extends GetDriverInstance {
	WebDriver effHCMCoreTestDriver;
	private Deployment dep;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private EFFUtils effUtils;
	private String contextCode=null;
	private String tabSegment=null;
	private String segmentName=null;
	private String dataType=null;
	private String behavior=null;
	private String displayType=null;
	String loc_info_eff_flexcode=null;
	String flexfield = null;
	String contextName = null;
	String description = null;
	
	
	@BeforeClass
	public void startUP() throws Exception {
		effHCMCoreTestDriver =  getDriverInstanceObject();
		dep = new Deployment(effHCMCoreTestDriver);
		new RuntimeValidation(effHCMCoreTestDriver);
		aLoginInstance = new ApplicationLogin(effHCMCoreTestDriver);
	    ntFInstance = new NavigationTaskFlows(effHCMCoreTestDriver);
	    new NavigatorMenuElements(effHCMCoreTestDriver);
	    effUtils = new EFFUtils(effHCMCoreTestDriver);
	    deleteSegment();
	}
	
	public void deleteSegment() throws Exception{
		try{
			System.out.println("Deleting Contexts from db");
		//	DbResource.getDBConnection();
		//	DbResource.createDbStatement();
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage("PER_PERSON_EIT_EFF", "PER_EIT", "HRX_IN_MISCELLANEOUS", "HCMCorePerson%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory("PER_PERSON_EIT_EFF", "PER_EIT", "HCMCorePerson%"));
			
			/*CommonUtils.SqlStatement.executeUpdate(EFFUtils.deleteSegmentsFromContexts("PER_LOCATION_INFORMATION_EFF", "HCMCorePerson%"));
			CommonUtils.SqlStatement.executeUpdate(EFFUtils.deleteContexts("PER_LOCATION_INFORMATION_EFF", "HCMCorePerson%"));*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DbResource.dbConnectionClose();
			
		}
	}
	
	@Test (description = "This is to create a simple context ")
	public void verifyCreateSetupForHCMCore() throws Exception {
		loc_info_eff_flexcode="PER_PERSON_EIT_EFF";
		contextName="HCMCorePerson";
		description="HCMCorePerson";
		tabSegment="tabInline";
		dataType="Character";
		displayType="Inline Search";
		aLoginInstance.login("APP_IMPL_CONSULTANT", "Welcome1",effHCMCoreTestDriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effHCMCoreTestDriver);
		ntFInstance.navigateToAOLTaskFlows(flexfield,effHCMCoreTestDriver);
		CommonUtils.hold(5);
		System.out.println("Class - EFF , Code - "+loc_info_eff_flexcode);
		System.out.println("Checking whether Context creation is successful or not");
		System.out.println("Current Flex code to check - "+loc_info_eff_flexcode);
		Assert.assertEquals(dep.searchFlexFieldCode(loc_info_eff_flexcode), true, "Flex Code Result - "+loc_info_eff_flexcode);
		Assert.assertTrue(dep.createContext(loc_info_eff_flexcode, contextName, description, "Single Row","Location"), "Context - "+contextName+" created successfully");
		contextCode=dep.getcontextCode(contextName);
		System.out.println("Current Context code - "+contextCode);
		System.out.println("Verification of Segment Creation");
		Assert.assertTrue(dep.createSegment(segmentName, dataType, "Priority", behavior, displayType, false),"Character Independent type VS with Inline search Segment creation assertion is successful");
		Assert.assertTrue(dep.createSegment(tabSegment, dataType, "tabvs", behavior, displayType, false),"Table type VS with Inline search Segment creation assertion is successful");
		dep.navigateToCategoriesPage();
		Assert.assertTrue(dep.associateContextToCategory(loc_info_eff_flexcode,"Person Extra Information", contextCode), "Context associated with Category");
		dep.navigateToAssociatedPages("India Miscellaneous Information", effHCMCoreTestDriver);
		Assert.assertTrue(dep.deployFlexFieldIncremental(loc_info_eff_flexcode, effHCMCoreTestDriver), "Incremental Deployment is successful");
	}
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effHCMCoreTestDriver);
		} catch (Exception ex) {
			aLoginInstance.launchURL(effHCMCoreTestDriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effHCMCoreTestDriver);
		}finally {
			try {
				effHCMCoreTestDriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
