package UI.tests.Flex.EFF;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import TestBase.UI.GetDriverInstance;

public class EFFFSMExportImport extends GetDriverInstance {
	String id = null;
	String contextCode=null;
	String contextName=null;
	String independentSegment=null;
	String dependentSegment=null;
	String itemName=null;
	String ipName=null;
	String flexCode=null;
	String contextDescription=null;
	String itemUsage=null;
	String updatedConText=null;
	WebDriver effPIMExpImp;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private FSMExpImpWrapper fsmExpImWrapperObject;
	
	public EFFFSMExportImport() throws WebDriverException {
		
	}
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String pwd) throws Exception {
		try {
			effPIMExpImp =  getDriverInstanceObject();
			dep = new Deployment(effPIMExpImp);
			pimRunTime = new RuntimeValidation(effPIMExpImp);
			aLoginInstance = new ApplicationLogin(effPIMExpImp);
			ntFInstance = new NavigationTaskFlows(effPIMExpImp);
			nMEInstance = new NavigatorMenuElements(effPIMExpImp);
			fsmExpImWrapperObject = new FSMExpImpWrapper(effPIMExpImp);
			id = CommonUtils.uniqueId();
			aLoginInstance.login("APP_IMPL_CONSULTANT", pwd, effPIMExpImp);
			CommonUtils.hold(10);
			ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMExpImp);
			CommonUtils.hold(10);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	@Test(description = "FSMExportImportForEFF")
	public void createProjectFile() throws Exception {
		ipName = "FSMExImEFF" + id;
		System.out.println("Project to be created - "+ipName);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				effPIMExpImp);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",
				effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,
				"Click", "", effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		CommonUtils.hold(8);
		System.out.println("Starting of Creation Of ManageImplementionProject ");
		Assert.assertTrue(dep.createImplProject(ipName), "New Project for EFF is created successfully");
	}
	
	@Test(dependsOnMethods = {"createProjectFile"}, description = "Create Context/Segment,Deploy the flexCode and save the Project for export")
	public void createAndDeployEFFAttributeGroupsUnderProject() throws Exception {
		contextName="exim_";
		flexCode = "EGO_ITEM_EFF";
		itemUsage="Item";
		independentSegment="numindsegment_"+id;
		contextDescription=contextName;
		dep.navigateToEFFTask();
		Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
        Assert.assertTrue(dep.createContext(flexCode, contextName, contextDescription, "Multiple Rows",itemUsage), "Context - "+contextName+" created successfully");
        contextCode=dep.getcontextCode(contextName);
        System.out.println("Current Context code - "+contextCode);
        System.out.println("Segment to be created - "+independentSegment);
        Assert.assertTrue(dep.createSegment(independentSegment, "Number", "Number_10_Key", "Multiple Rows", "Drop-down List", true),"Segment creation assertion is successful");
        dep.navigateToCategoriesPage();
        CommonUtils.hold(5);
        dep.saveAndCloseButtonUnderCategoryPages();
        Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMExpImp), "Incremental Deployment is successful");
        dep.closeProjectTasks();
	}
	
	@Test(dependsOnMethods = {"createAndDeployEFFAttributeGroupsUnderProject"}, description = "Export the EFF Project and download the package")
	public void exportDownloadEFFProjectPackage() throws Exception {
		System.out.println("Starting Export process and downloading configuration package");
		fsmExpImWrapperObject.createConfigurationProject(ipName, effPIMExpImp);
		CommonUtils.hold(5);
		fsmExpImWrapperObject.downloadPackage(effPIMExpImp);
		CommonUtils.hold(3);
	}
	
	@Test(dependsOnMethods= {"exportDownloadEFFProjectPackage"}, description = "Modify the Context/Segment which is part of Implementation Project")
	public void modfiyAttributeGroupAndDeploy() throws Exception {
		/*ipName="FSMExImEFF1910180346";
		contextCode="exim_1910180351";
		independentSegment="numindsegment_1910180346";
		flexCode="EGO_ITEM_EFF";*/
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				effPIMExpImp);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",
				effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,
				"Click", "", effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		CommonUtils.hold(8);
		System.out.println("Modifying current Implementation project");
		Assert.assertTrue(dep.modifyImplProject(ipName), "Project found to modify");
		CommonUtils.hold(8);
		dep.navigateToEFFTask();
		dep.searchFlexFieldCode(flexCode);
		Assert.assertTrue(dep.modifyContext(contextCode), "Navigation to Context successful");
		Assert.assertEquals(dep.getcontextCode(contextCode), contextCode);
		updatedConText=dep.updateContextDescription();
		//dep.navigateToCategoriesPage();
        dep.saveAndCloseButtonUnderCategoryPages();
        Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMExpImp), "Incremental Deployment is successful");
        dep.closeProjectTasks();
	}
	
	@Test(dependsOnMethods= {"modfiyAttributeGroupAndDeploy"}, description = "upload the Project and then Import it uner the current Implementation Project")
	public void uploadDownloadProject() throws Exception {
		System.out.println("Uploading Configuration Set And Starting Import process");
		fsmExpImWrapperObject.uploadConfigurationPackage(ipName, effPIMExpImp);
		fsmExpImWrapperObject.importSetupData(ipName, effPIMExpImp);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				effPIMExpImp);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",
				effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,
				"Click", "", effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		CommonUtils.hold(8);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMExpImp);
		CommonUtils.hold(10);
		ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields", effPIMExpImp);
		CommonUtils.hold(8);
		System.out.println("Checking the current status of flex code - "+flexCode);
		dep.searchFlexFieldCode(flexCode);
		Assert.assertEquals(dep.getFlexCodeStatus(), "Patched","Current Flex code - "+flexCode+" has status - "+dep.getFlexCodeStatus());
	}
	
	@Test(dependsOnMethods= {"uploadDownloadProject"},description = "Check whether the Context is replaced with original values or not")
	public void validateImportedProject() throws Exception {
		Assert.assertTrue(dep.deployFlexFieldIncremental(flexCode, effPIMExpImp), "Incremental Deployment is successful");
		dep.modifyContext(contextCode);
		dep.getcontextCode(contextCode);
		Assert.assertEquals(dep.getContextDescription(), contextDescription);
	}
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException,Exception {
		try {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
				effPIMExpImp);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",
				effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,
				"Click", "", effPIMExpImp);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		dep.deleteEFFProject(ipName);
		dep.deleteEFFProject(ipName+"_1");
		}
		catch (Exception ex) {
			System.err.println("ERROR - Project"+ipName+" not found");
			ex.printStackTrace();
		}
		try {
			aLoginInstance.logout(effPIMExpImp);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMExpImp);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMExpImp);
		}finally {
			try {
				effPIMExpImp.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
