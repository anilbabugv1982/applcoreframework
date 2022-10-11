package UI.tests.Flex.EFF;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import pageDefinitions.UI.oracle.applcore.qa.Eff.Context;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.PerPersonEITEFF;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Segment;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerPersonEITEFFSetup extends GetDriverInstance {

	private WebDriver perPersonSetupDriver;
	private EFFUtils effUtils;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private Deployment manageExtensibleFlexFields;
	private PerPersonEITEFF perPersonObj;
	private List<Context> contexts;
	private List<Segment> segments;
	List<String> contextCodes;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void loginToApplication(String user, String pwd) throws Exception {
		perPersonSetupDriver = getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(perPersonSetupDriver);
		ntFInstance = new NavigationTaskFlows(perPersonSetupDriver);
		nMEInstance = new NavigatorMenuElements(perPersonSetupDriver);
		effUtils = new EFFUtils(perPersonSetupDriver);
		aLoginInstance.login(user, pwd, perPersonSetupDriver);
		manageExtensibleFlexFields = new Deployment(perPersonSetupDriver);
		CommonUtils.hold(5);
		ObjectMapper objectMapper = new ObjectMapper();
		perPersonObj = objectMapper.readValue(new File("./src/test/resources/eff/person_eit_eff.json"),
				PerPersonEITEFF.class);
		contexts = Arrays.asList(perPersonObj.getContexts());
	}

	@Test(priority = 1, description = "Delete Existing Data", enabled = true)
	public void testcase001() throws Exception {
		try{
			System.out.println("Deleting Contexts from db");
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromPage(perPersonObj.getFlexCode(), "PER_EIT", perPersonObj.getPage(), "atgC%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContextFromCategory(perPersonObj.getFlexCode(), "PER_EIT", "atgC%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteSegmentsFromContexts(perPersonObj.getFlexCode(), "atgC%"));
			DbResource.sqlStatement.executeUpdate(effUtils.deleteContexts(perPersonObj.getFlexCode(), "atgC%"));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DbResource.dbConnectionClose();
			
		}
	}

	@Test(priority = 2, description = "Navigate to Manage Extensible Flexfields", enabled = true)
	public void testcase002() throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				perPersonSetupDriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields", perPersonSetupDriver);
		CommonUtils.hold(5);
		manageExtensibleFlexFields.searchFlexFieldCode(perPersonObj.getFlexCode());
		System.out.println(perPersonObj);
	}
	
	@Test(priority = 3, description = "Create Single Row Context", enabled = true)
	public void testcase003() throws Exception {
		manageExtensibleFlexFields.createContext(
				perPersonObj.getFlexCode(),
				contexts.get(0).getContextCode(),
				contexts.get(0).getContextCode(),
				contexts.get(0).getBehaviour(),
				contexts.get(0).getContextUsage());
	}

	@Test(priority = 4, description = "Assign 3 Segments", enabled = true)
	public void testcase004() throws Exception {
		List<Segment> segments = Arrays.asList(contexts.get(0).getSegments());
		for (Segment seg : segments) {
			manageExtensibleFlexFields.createSegment(
					seg.getSegmentName(),
					seg.getDataType(),
					seg.getValueSet(),
					contexts.get(0).getBehaviour(),
					seg.getDisplayType(),
					seg.isUnique());
		}
		manageExtensibleFlexFields.navigateToCategoriesPage();
		manageExtensibleFlexFields.saveAndCloseButtonUnderCategoryPages();
	}

	@Test(priority = 5, description = "Create Multiple Row Context", enabled = true)
	public void testcase005() throws Exception {
		manageExtensibleFlexFields.createContext(
				perPersonObj.getFlexCode(),
				contexts.get(1).getContextCode(),
				contexts.get(1).getContextCode(),
				contexts.get(1).getBehaviour(),
				contexts.get(1).getContextUsage());
	}

	@Test(priority = 6, description = "Assign 3 Segments", enabled = true)
	public void testcase006() throws Exception {
		List<Segment> segments2 = Arrays.asList(contexts.get(1).getSegments());
		for (Segment seg : segments2) {
			manageExtensibleFlexFields.createSegment(
					seg.getSegmentName(),
					seg.getDataType(),
					seg.getValueSet(),
					contexts.get(1).getBehaviour(),
					seg.getDisplayType(),
					seg.isUnique());
					}
		manageExtensibleFlexFields.navigateToCategoriesPage();
	}

	@Test(priority = 7, description = "Assign Contexts to Categories", enabled = true)
	public void testcase007() throws Exception {
	//	manageExtensibleFlexFields.navigateFromFlexPageToCategoriesPage();
		contextCodes = contexts.stream().map(context -> context.getContextCode()).collect(Collectors.toList());
		manageExtensibleFlexFields.associateContextToCategory(perPersonObj.getFlexCode(), "Person Extra Information",
				contextCodes);
	}

	@Test(priority = 8, description = "Assign Contexts to Page", enabled = true)
	public void testcase008() throws Exception {
		CommonUtils.hold(3);
		manageExtensibleFlexFields.associatedPagesTabLink.click();
		CommonUtils.hold(3);
		manageExtensibleFlexFields.assignContextsToPage(perPersonObj.getPage(), contextCodes, perPersonSetupDriver,
				"Usage Code for Person");
		manageExtensibleFlexFields.saveAndCloseButtonUnderCategoryPages();
	}

	@Test(priority = 9, description = "Online Deployment")
	public void testcase009() {
		manageExtensibleFlexFields.deployFlexFieldIncremental(perPersonObj.getFlexCode(), perPersonSetupDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logout() {
		perPersonSetupDriver.close();
	}

}
