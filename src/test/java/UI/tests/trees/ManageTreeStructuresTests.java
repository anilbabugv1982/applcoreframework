package UI.tests.trees;

import java.util.concurrent.TimeUnit;


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
import pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures.ManageTreeStructure;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures.TreeStructureOperations;

public class ManageTreeStructuresTests extends GetDriverInstance {

    private WebDriver treesTestDriver;
    private ManageTreeStructure managetreesStructure;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private ApplicationLogin aLoginInstance;
    private TreesUtils treesUtils;
    private TreeStructureOperations treeStructureOperations;

    public ManageTreeStructuresTests() throws WebDriverException {

    }

    //************************************************************************************************************************************
    @Parameters({"user", "pwd"})
    @BeforeClass
    public void logOnToApplication(String user, String pwd) throws Exception {
        treesTestDriver = getDriverInstanceObject();
        ntFInstance = new NavigationTaskFlows(treesTestDriver);
        nMEInstance = new NavigatorMenuElements(treesTestDriver);
        aLoginInstance = new ApplicationLogin(treesTestDriver);
        managetreesStructure = new ManageTreeStructure(treesTestDriver);
        treesUtils = new TreesUtils(treesTestDriver);
        treeStructureOperations = new TreeStructureOperations(treesTestDriver);
        aLoginInstance.login(user, pwd, treesTestDriver);
        CommonUtils.hold(3);
        treesTestDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

//************************************************************************************************************************************


    @Test(priority = 1, description = "Navigate to Manage Tree Structure Page")
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
                treesTestDriver);
        CommonUtils.hold(3);
        ntFInstance.navigateToAOLTaskFlows("Manage Tree Structures", treesTestDriver);
        CommonUtils.hold(5);
    }

    @Test(priority = 2, description = "Enable Shuttle Mode", dependsOnMethods = {"testcase001"})
    public void testcase002() throws Exception {
    	CommonUtils.hold(3);
    	managetreesStructure.input_TreeStructureCode.sendKeys("GL_ACCT_FLEX");
        managetreesStructure.button_Search.click();
        CommonUtils.hold(3);
        managetreesStructure.action_menu.click();
        treesUtils.waitForElementToBeClickable(managetreesStructure.action_QuickEdit);
        CommonUtils.hold(3);
        managetreesStructure.action_QuickEdit.click();
        treesUtils.waitForElementToBeVisible(managetreesStructure.input_searchViewObject);
        CommonUtils.hold(3);
        managetreesStructure.input_searchViewObject.clear();
        managetreesStructure.button_SaveAndClose.click();
        treesUtils.waitForElementNotVisible("//button[text()='Save and Close']");
        CommonUtils.hold(5);
    }

    @Test(priority = 3, description = "Search and Delete existing custom tree structures", dependsOnMethods = {"testcase002"})
    public void testcase003() {
        treeStructureOperations.searchTreeStructure("AutoTree");
        CommonUtils.hold(5);
        treeStructureOperations.deleteAllCustomTreeStructures("AutoTree");
    }

    @Test(priority = 4, description = "This testcase is for Creating and Search Tree Structure", enabled = true , dependsOnMethods = {"testcase003"})
    public void testcase004() throws Exception {

        managetreesStructure.createTreeStructure();                            //Create a Tree Structure
        managetreesStructure.searchTreeStructure();                            //Search for a Tree Structure
    }

    //************************************************************************************************************************************
    @Test(priority = 5, description = "This testcase is for Duplicate and Edit Tree Structure", enabled = true, dependsOnMethods = {"testcase004"})
    public void testcase005() throws Exception {
        managetreesStructure.duplicateTreeStructure();                         //DUplicate a Tree Structure
        managetreesStructure.editTreeStructure();                              //Edit a Tree Structure

    }

    //************************************************************************************************************************************
    @Test(priority = 6, description = "This testcase is for Auditing and Setting Status  of Tree Structure", enabled = true, dependsOnMethods = {"testcase005"})
    public void testcase006() throws Exception {
        CommonUtils.hold(10);
        managetreesStructure.auditTreeStructure();                             //Audit Tree Structure
        managetreesStructure.treeStructureSetStatus();                         //Set Status to Tree Structure
        managetreesStructure.deleteTreeStructure();                            //Delete Tree Structure

    }
    //************************************************************************************************************************************
    @AfterClass(alwaysRun = true)
    public void logOut() {

        treesUtils.secureLogout();

    }
//************************************************************************************************************************************
}


