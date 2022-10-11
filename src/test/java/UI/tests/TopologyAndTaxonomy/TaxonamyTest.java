package UI.tests.TopologyAndTaxonomy;



import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
/*import oracle.applcore.qa.Taxonamy.TaxonamyPage;
import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.common.GlobalPageTemplate;
import oracle.applcore.qa.setup.DriverInstance;*/
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;

import pageDefinitions.UI.oracle.applcore.qa.Taxonamy.TaxonamyPage;

public class TaxonamyTest extends GetDriverInstance{

	static String id;

	private WebDriver TaxonomyTestInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private TaxonamyPage TaxonomyInstance;
	private NavigationTaskFlows taskFlowsNavigation = null;

	@Parameters({ "user1", "pwd1" })
	@BeforeTest
	public void setUp(String user, String passWord) throws Exception {
		TaxonomyTestInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(TaxonomyTestInstance);
		taskFlowsNavigation = new NavigationTaskFlows(TaxonomyTestInstance);
		ntFInstance = new NavigationTaskFlows(TaxonomyTestInstance);
		nMEInstance = new NavigatorMenuElements(TaxonomyTestInstance);
		TaxonomyInstance = new TaxonamyPage(TaxonomyTestInstance);
		id = CommonUtils.uniqueId();
		aLoginInstance.login(user, passWord, TaxonomyTestInstance);
		CommonUtils.hold(3);
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, TaxonomyTestInstance);

		CommonUtils.hold(3);
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Taxonomy Hierarchy", TaxonomyTestInstance);
		//CommonUtils.navigateToAOLTaskFlows("Manage Taxonomy Hierarchy");
		CommonUtils.hold(2);
		/*CommonUtils.explicitWait(
				DriverInstance.getDriverInstance()
						.findElement(By.xpath("//h1[contains(text(),'Manage Taxonomy Hierarchy')]")),
				CommonUtils.ExplicitWaitCalls.Visible.toString(), "");*/

	}


	@Test(priority = 1, enabled = true)
	public void createfamily() throws Exception {

		CommonUtils.hold(5);
		TaxonamyPage.rootnodelink.click();
		CommonUtils.hold(5);
		TaxonamyPage.fusionlink.click();
		CommonUtils.hold(5);
		TaxonamyPage.childnode.click();
		CommonUtils.hold(5);
		TaxonamyPage.selectmodule(TaxonamyPage.module, "Family");
		CommonUtils.hold(5);
		TaxonamyPage.modulename.sendKeys(TaxonamyPage.familyname);
		TaxonamyPage.modulekey.sendKeys(TaxonamyPage.familyname);
		TaxonamyPage.alternativeid.sendKeys(TaxonamyPage.taxid);
		TaxonamyPage.usermodulename.sendKeys(TaxonamyPage.familyname);
		TaxonamyPage.description.sendKeys(TaxonamyPage.familyname);
		TaxonamyPage.selectmodule(TaxonamyPage.seeddata, "NO");
		TaxonamyPage.selectmodule(TaxonamyPage.domain, "FinancialDomain");
		TaxonamyPage.save.click();
		CommonUtils.hold(5);
		TaxonamyPage.ok.click();
	}

	@Test(priority = 2, enabled = true)
	public void createapplication() throws Exception {
		CommonUtils.hold(5);
		TaxonamyPage.fusionexpand.click();
		CommonUtils.hold(5);
		TaxonamyPage.family(TaxonomyTestInstance).click();
		CommonUtils.hold(5);
		TaxonamyPage.childnode.click();
		CommonUtils.hold(5);
		TaxonamyPage.selectmodule(TaxonamyPage.module, "Application");
		CommonUtils.hold(5);
		TaxonamyPage.modulename.sendKeys(TaxonamyPage.applicationname);
		TaxonamyPage.modulekey.sendKeys(TaxonamyPage.applicationname);
		TaxonamyPage.alternativeid.sendKeys(TaxonamyPage.taxid);
		TaxonamyPage.productcode.sendKeys(TaxonamyPage.applicationname);
		TaxonamyPage.usermodulename.sendKeys(TaxonamyPage.applicationname);
		TaxonamyPage.description.sendKeys(TaxonamyPage.applicationname);
		TaxonamyPage.selectmodule(TaxonamyPage.seeddata, "YES");
		TaxonamyPage.selectmodule(TaxonamyPage.application, "GlDrmApp");
		TaxonamyPage.save.click();
		CommonUtils.hold(5);
		TaxonamyPage.ok.click();
	}

	@Test(priority = 3, enabled = true)
	public void createlba() throws Exception {
		CommonUtils.hold(5);
		/*Taxonomy.fusionexpand.click();
		CommonUtils.hold(5);*/
		TaxonamyPage.familyexpand(TaxonomyTestInstance).click();
		CommonUtils.hold(5);
		TaxonamyPage.application(TaxonomyTestInstance).click();
		CommonUtils.hold(5);
		TaxonamyPage.childnode.click();
		CommonUtils.hold(5);
		TaxonamyPage.selectmodule(TaxonamyPage.module, "Logical Business Area");
		CommonUtils.hold(5);
		TaxonamyPage.modulename.sendKeys(TaxonamyPage.lbaname);
		TaxonamyPage.modulekey.sendKeys(TaxonamyPage.lbaname);
		TaxonamyPage.alternativeid.sendKeys(TaxonamyPage.taxid);
		TaxonamyPage.usermodulename.sendKeys(TaxonamyPage.lbaname);
		TaxonamyPage.description.sendKeys(TaxonamyPage.lbaname);
		TaxonamyPage.selectmodule(TaxonamyPage.seeddata, "YES");
		TaxonamyPage.save.click();
		CommonUtils.hold(5);
		TaxonamyPage.ok.click();
	}

	@AfterClass(alwaysRun=true)
	public void logOut() throws InterruptedException {

		CommonUtils.hold(2);
		aLoginInstance.logout(TaxonomyTestInstance);
		CommonUtils.hold(2);
		TaxonomyTestInstance.quit();

	}



}