package UI.tests.TopologyAndTaxonomy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Topology.TopologyPage;

class TopologyTests extends GetDriverInstance{

static String uniqueID;
	
	private WebDriver TopologywebDriver = null;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private TopologyPage topology;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		TopologywebDriver =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(TopologywebDriver);
		ntFInstance = new NavigationTaskFlows(TopologywebDriver);
		nMEInstance = new NavigatorMenuElements(TopologywebDriver);
		topology= new TopologyPage(TopologywebDriver);
		uniqueID = CommonUtils.uniqueId();
		aLoginInstance.loginem(user, passWord, TopologywebDriver);
		CommonUtils.hold(20);
		TopologyPage.clickcontinue(TopologywebDriver);
		CommonUtils.hold(20);
		TopologyPage.menu.click();
		CommonUtils.hold(5);
		TopologyPage.collapse.click();
		CommonUtils.hold(5);
		// TopologyPage.farmdomainexpand.click();
		CommonUtils.hold(10);
		// TopologyPage.weblogicdomainexpand.click();
		TopologyPage.navigatetotopologymbean(TopologywebDriver);

	}

	@Test(priority = 1)
	public void getAllDeployedAppsInfo() throws Exception {

		TopologyPage.invokeapi("getAllDeployedAppsInfo", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("75300D5AA860A6A5E040578C495D26A5");
		TopologyPage.invokeoperationanvalidatetabular("getAllDeployedAppsInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(5);

	}

	@Test(priority = 2)
	public void getAllEnterpriseAppsInfo() throws Exception {

		TopologyPage.invokeapi("getAllEnterpriseAppsInfo", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("47110F64AC0308E2E040449823C60DB6");
		TopologyPage.inputparam2.sendKeys("Deployed");
		TopologyPage.invokeoperationanvalidatetabular("getAllEnterpriseAppsInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 3)
	public void getAppListFromDeployedDomain() throws Exception {

		TopologyPage.invokeapi("getAppListFromDeployedDomain", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatetabular("getAppListFromDeployedDomain", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 4)
	public void getDependentApps() throws Exception {

		TopologyPage.invokeapi("getDependentApps", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("TopologyManagerServiceApp");
		TopologyPage.invokeoperationanvalidatetabular("getDependentApps", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 5)
	public void getDependentMWComponents() throws Exception {

		TopologyPage.invokeapi("getDependentMWComponents", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("SetupApp");
		TopologyPage.invokeoperationanvalidatetabular("getDependentMWComponents", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 6)
	public void getDeployedAppInfo() throws Exception {

		TopologyPage.invokeapi("getDeployedAppInfo", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidatetabular("getDeployedAppInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 7)
	public void getDeployedAppsFromProductList() throws Exception {

		TopologyPage.invokeapi("getDeployedAppsFromProductList", TopologywebDriver);
		TopologyPage.pencilicon.click();
		CommonUtils.hold(5);
		TopologyPage.addicon.click();
		CommonUtils.hold(5);
		TopologyPage.inputparam3.sendKeys("75300D5AA860A6A5E040578C495D26A5");
		TopologyPage.ok.click();
		CommonUtils.hold(5);
		TopologyPage.invokeoperationanvalidatetabular("getDeployedAppsFromProductList", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 8)
	public void getDeployedDomainInfo() throws Exception {

		TopologyPage.invokeapi("getDeployedDomainInfo",TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatetabular("getDeployedDomainInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 9)
	public void getDeployedDomainsByCompositeName() throws Exception {

		TopologyPage.invokeapi("getDeployedDomainsByCompositeName", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("EgpItemsComposite");
		TopologyPage.invokeoperationanvalidatetabular("getDeployedDomainsByCompositeName", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 10)
	public void getDeployedDomainsByEnvironment() throws Exception {

		TopologyPage.invokeapi("getDeployedDomainsByEnvironment",TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("oracle");
		TopologyPage.invokeoperationanvalidatetabular("getDeployedDomainsByEnvironment", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 11)
	public void getDisabledApplicationsByEnabledOfferings() throws Exception {

		TopologyPage.invokeapi("getDisabledApplicationsByEnabledOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatestring("getDisabledApplicationsByEnabledOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 12)
	public void getDisabledApplicationsByLicensedOfferings() throws Exception {

		TopologyPage.invokeapi("getDisabledApplicationsByLicensedOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatestring("getDisabledApplicationsByLicensedOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 13)
	public void getDisabledClustersByEnabledOfferings() throws Exception {

		TopologyPage.invokeapi("getDisabledClustersByEnabledOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("IDMDomain");
		TopologyPage.invokeoperationanvalidatestring("getDisabledClustersByEnabledOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 14)
	public void getDisabledClustersByLicensedOfferings() throws Exception {

		TopologyPage.invokeapi("getDisabledClustersByLicensedOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("IDMDomain");
		TopologyPage.invokeoperationanvalidatestring("getDisabledClustersByLicensedOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 15)
	public void getDomainNames() throws Exception {

		TopologyPage.invokeapi("getDomainNames", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidatestring("getDomainNames", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 16)
	public void getEnabledApplicationsByEnabledOfferings() throws Exception {

		TopologyPage.invokeapi("getEnabledApplicationsByEnabledOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatestring("getEnabledApplicationsByEnabledOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);
	}

	@Test(priority = 17)
	public void getEnabledApplicationsByLicensedOfferings() throws Exception {

		TopologyPage.invokeapi("getEnabledApplicationsByLicensedOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatestring("getEnabledApplicationsByLicensedOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);
	}

	@Test(priority = 18)
	public void getEnabledClustersByEnabledOfferings() throws Exception {

		TopologyPage.invokeapi("getEnabledClustersByEnabledOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatestring("getEnabledClustersByEnabledOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 19)
	public void getEnabledClustersByLicensedOfferings() throws Exception {

		TopologyPage.invokeapi("getEnabledClustersByLicensedOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidatestring("getEnabledClustersByLicensedOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 20)
	public void getEndPointInfo() throws Exception {

		TopologyPage.invokeapi("getEndPointInfo", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidatetabular("getEndPointInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 21)
	public void getEnterpriseAppInfo() throws Exception {

		TopologyPage.invokeapi("getEnterpriseAppInfo", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidatetabular("getEnterpriseAppInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);
	}

	@Test(priority = 22)
	public void getEnterpriseAppsBySourceFileName() throws Exception {

		TopologyPage.invokeapi("getEnterpriseAppsBySourceFileName", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("EarFinancialsEss.ear");
		TopologyPage.invokeoperationanvalidatetabular("getEnterpriseAppsBySourceFileName", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(5);
		TopologyPage.scrolldown(TopologywebDriver);
		CommonUtils.hold(5);

	}

	@Test(priority = 23)
	public void getEssApplicationInfo() throws Exception {
		try {
			TopologyPage.invokeapi("getEssApplicationInfo", TopologywebDriver);	
		}
		
		catch (Exception e)
		{
			TopologyPage.scrolldown(TopologywebDriver);
			TopologyPage.invokeapi("getEssApplicationInfo", TopologywebDriver);
		}
		
		TopologyPage.inputparam1.sendKeys("47110F64ABC108E2E040449823C60DB6");
		TopologyPage.invokeoperationanvalidatetabular("getEssApplicationInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 24)
	public void getLbasInfo() throws Exception {

		TopologyPage.invokeapi("getLbasInfo", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("75300D5AA860A6A5E040578C495D26A5");
		TopologyPage.invokeoperationanvalidatetabular("getLbasInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 25)
	public void getListOfDeployedApps() throws Exception {

		TopologyPage.invokeapi("getListOfDeployedApps", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("75300D5AA860A6A5E040578C495D26A5");
		TopologyPage.invokeoperationanvalidatestring("getListOfDeployedApps", TopologywebDriver);
		TopologyPage.returnbutton.click();

	}

	@Test(priority = 26)
	public void getListOfDomains() throws Exception {

		TopologyPage.invokeapi("getListOfDomains", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FIN");
		TopologyPage.invokeoperationanvalidatestring("getListOfDomains", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 27)
	public void getListOfEnterpriseApps() throws Exception {

		TopologyPage.invokeapi("getListOfEnterpriseApps", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("75300D5AA860A6A5E040578C495D26A5");
		TopologyPage.inputparam2.sendKeys("Deployed");
		TopologyPage.invokeoperationanvalidatestring("getListOfEnterpriseApps", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 28)
	public void getListOfLbas() throws Exception {

		TopologyPage.invokeapi("getListOfLbas", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("75300D5AA860A6A5E040578C495D26A5");
		TopologyPage.invokeoperationanvalidatestring("getListOfLbas", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 29)
	public void getModuleFromAppShortName() throws Exception {

		TopologyPage.invokeapi("getModuleFromAppShortName", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("LedgerApp");
		TopologyPage.invokeoperationanvalidatetabular("getModuleFromAppShortName", TopologywebDriver);
		TopologyPage.returnbutton.click();

	}

	@Test(priority = 30)
	public void getProductsInfo() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("getProductsInfo", TopologywebDriver);
		TopologyPage.pencilicon.click();
		CommonUtils.hold(5);
		TopologyPage.addicon.click();
		CommonUtils.hold(5);
		TopologyPage.inputparam3.sendKeys("47110F64ABC108E2E040449823C60DB6");
		TopologyPage.ok.click();
		CommonUtils.hold(5);
		TopologyPage.invokeoperationanvalidatetabular("getProductsInfo", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 31)
	public void getSoaEndPoints() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("getSoaEndPoints", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidatetabular("getSoaEndPoints", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 32)
	public void getUsedProducts() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("getUsedProducts", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidatetabular("getUsedProducts", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 33)
	public void isAppInProvisionedOfferings() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("isAppInProvisionedOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidateboolean("isAppInProvisionedOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 34)
	public void isAppProvisioned() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("isAppProvisioned", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("ORA_CRM_SERVICESAPP");
		TopologyPage.invokeoperationanvalidateboolean("isAppProvisioned", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 35)
	public void isCompositeInProvisionedOfferings() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("isCompositeInProvisionedOfferings", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("EgpItemsComposite");
		TopologyPage.invokeoperationanvalidateboolean("isCompositeInProvisionedOfferings", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 36)
	public void isDomainOfType() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("isDomainOfType", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.inputparam2.sendKeys("UI");
		TopologyPage.invokeoperationanvalidateboolean("isDomainOfType", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	@Test(priority = 37)
	public void isReplicationEnabled() throws Exception {

		TopologyPage.scrolldown(TopologywebDriver);
		TopologyPage.invokeapi("isReplicationEnabled", TopologywebDriver);
		TopologyPage.inputparam1.sendKeys("FADomain");
		TopologyPage.invokeoperationanvalidateboolean("isReplicationEnabled", TopologywebDriver);
		TopologyPage.returnbutton.click();
		CommonUtils.hold(3);

	}

	  @AfterClass(alwaysRun=true)
        public void logOut() throws InterruptedException {
 try{
	 aLoginInstance.launchEMURL(TopologywebDriver);    
	    CommonUtils.hold(15);
		CommonUtils.waitForPageLoad(TopologywebDriver);
		TopologywebDriver.findElement(By.xpath("//a[contains(text(),'Log Out')]")).click();
            
            CommonUtils.hold(2);
            TopologywebDriver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
            
            CommonUtils.hold(5);
            TopologywebDriver.switchTo().alert().accept();
            
            CommonUtils.hold(2);
            TopologywebDriver.navigate().to(GetDriverInstance.EnvironmentName);
           
          
            CommonUtils.hold(2);
            TopologywebDriver.manage().deleteAllCookies();
            
            
     
        }
        
        
        catch(Exception e){
          aLoginInstance.launchEMURL(TopologywebDriver);
          CommonUtils.hold(15);
  		CommonUtils.waitForPageLoad(TopologywebDriver);
  		TopologywebDriver.findElement(By.xpath("//a[contains(text(),'Log Out')]")).click();
           
            CommonUtils.hold(2);
            TopologywebDriver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
            
            CommonUtils.hold(5);
            TopologywebDriver.switchTo().alert().accept();
           
            CommonUtils.hold(2);
            TopologywebDriver.navigate().to(GetDriverInstance.EnvironmentName);
           
            CommonUtils.hold(2);
            TopologywebDriver.manage().deleteAllCookies();
        }
}
	  
}
