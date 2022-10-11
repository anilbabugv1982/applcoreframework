package UI.tests.trees;


import org.openqa.selenium.WebDriver;

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
import pageDefinitions.UI.oracle.applcore.qa.trees.ess.ScheduledProcesses;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyNodes;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreeSummaryPage;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesAuditResult;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesOperations;

import java.util.*;


public class ManageAccountHierarchiesTests extends GetDriverInstance {

    private String treeCode = "ALL ACCOUNTS_102";
    private String treeVersion = "atg_version";
    private String treeVersion1 = "atg_version1";
    private String treeVersion2 = "atg_version2";
    private String treeVersion3 = "atg_version3";
    private String treeVersion4 = "atg_version4";

    private WebDriver manageAccountHierarchyDriver;
    private TreesUtils treeUtils;
    private TVSpecifyDefinition tvSpecifyDefinitionPage;
    private TVSpecifyNodes tvSpecifyNodesPage;
    private AddTreeNodePopup addTreeNodePopupPage;
    private ApplicationLogin aLoginInstance;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private ManageTreeStructure managetreesStructure;
    private TreesAuditResult treesAuditResultPage;
    private TreesOperations treesOperations;
    private TreeSummaryPage treeSummaryPage;
    private ScheduledProcesses scheduledProcesses;
//    private Map<String, List<String>> parentDataSourceNodeMap = new LinkedHashMap<>();
//    private Map<String, List<String>> detailDataSourceNodeMap = new LinkedHashMap<>();
    private LinkedList<String> parentDataSourceNodeList = new LinkedList<>();
    private LinkedList<String> detailDataSourceNodeList = new LinkedList<>();
    private ArrayList<String> selectedNodeList = new ArrayList<>();
    private String parentDataSource = "Accounting Flexfield Hierarchy Parent Values";
    private String detailDataSource = "Accounting Flexfield Hierarchy Detail Values";

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void loginToApplication(String user, String pwd) throws Exception {
        manageAccountHierarchyDriver = getDriverInstanceObject();
        aLoginInstance = new ApplicationLogin(manageAccountHierarchyDriver);
        ntFInstance = new NavigationTaskFlows(manageAccountHierarchyDriver);
        nMEInstance = new NavigatorMenuElements(manageAccountHierarchyDriver);
        treeUtils = new TreesUtils(manageAccountHierarchyDriver);
        tvSpecifyDefinitionPage = new TVSpecifyDefinition(manageAccountHierarchyDriver);
        tvSpecifyNodesPage = new TVSpecifyNodes(manageAccountHierarchyDriver);
        addTreeNodePopupPage = new AddTreeNodePopup(manageAccountHierarchyDriver);
        managetreesStructure = new ManageTreeStructure(manageAccountHierarchyDriver);
        treesOperations = new TreesOperations(manageAccountHierarchyDriver);
        treeSummaryPage = new TreeSummaryPage(manageAccountHierarchyDriver);
        aLoginInstance.login(user, pwd, manageAccountHierarchyDriver);
        treesAuditResultPage = new TreesAuditResult(manageAccountHierarchyDriver);
        CommonUtils.hold(5);
    }

    @Test(priority = 1, description = "Disable Shuttle Mode for Node Selection", enabled = true)
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, manageAccountHierarchyDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows("Manage Tree Structures", manageAccountHierarchyDriver);
        CommonUtils.hold(5);
        managetreesStructure.input_TreeStructureCode.sendKeys("GL_ACCT_FLEX");
        managetreesStructure.button_Search.click();
        CommonUtils.hold(3);
        managetreesStructure.action_menu.click();
        treeUtils.waitForElementToBeClickable(managetreesStructure.action_QuickEdit);
        CommonUtils.hold(3);
        managetreesStructure.action_QuickEdit.click();
        treeUtils.waitForElementToBeVisible(managetreesStructure.input_searchViewObject);
        CommonUtils.hold(3);
        managetreesStructure.input_searchViewObject.clear();
        managetreesStructure.input_searchViewObject.sendKeys("oracle.apps.fnd.applcore.flex.vst.model.publicView.HierarchicalValueSearchVO");
        managetreesStructure.button_SaveAndClose.click();
        CommonUtils.hold(3);

    }

    @Test(priority = 2, description = "Navigate to Manage Account Hierarchies", enabled = true, dependsOnMethods = {"testcase001"})
    public void testcase002() throws Exception {

        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, manageAccountHierarchyDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows("Manage Account Hierarchies", manageAccountHierarchyDriver);
        treeSummaryPage.verifyManageAccountHierarchiesPage();
    }


    @Test(priority = 3, description = "Deleting existing tree version", enabled = true, dependsOnMethods = {"testcase002"})
    public void testcase003() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.deleteAllCustomTreeVersions(treeCode);
    }

    @Test(priority = 5, description = "Create Tree Version", enabled = true, dependsOnMethods = {"testcase002"})
    public void testcase005() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperations.createTreeVersion(treeVersion);
    }

    @Test(priority = 6, description = "Get all tree nodes", enabled = true, dependsOnMethods = {"testcase002"})
    public void testcase006() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_search);
        addTreeNodePopupPage.selectDataSource(manageAccountHierarchyDriver, parentDataSource);
        CommonUtils.hold(3);
        addTreeNodePopupPage.button_search.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
        CommonUtils.hold(3);
        //	AddTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopupPage.searchNodePopup_search.click();
        CommonUtils.hold(5);
        parentDataSourceNodeList.addAll(addTreeNodePopupPage.getDataSourceTreeNodeMap());
        CommonUtils.hold(5);
        addTreeNodePopupPage.searchNodePopup_button_Cancel.click();
        CommonUtils.hold(3);
        addTreeNodePopupPage.selectDataSource(manageAccountHierarchyDriver, detailDataSource);
        CommonUtils.hold(3);
        addTreeNodePopupPage.button_search.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
        CommonUtils.hold(3);
        //	AddTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopupPage.searchNodePopup_search.click();
        CommonUtils.hold(5);
        detailDataSourceNodeList.addAll(addTreeNodePopupPage.getDataSourceTreeNodeMap());
        CommonUtils.hold(5);
        addTreeNodePopupPage.searchNodePopup_button_Cancel.click();
        CommonUtils.hold(3);
        addTreeNodePopupPage.button_Cancel.click();
        CommonUtils.hold(3);

    }

    @Test(priority = 7, description = "Add Root node")
    public void testcase007() {
        TVSpecifyNodes.img_add.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_search);
        addTreeNodePopupPage.selectDataSource(manageAccountHierarchyDriver, "Accounting Flexfield Hierarchy Parent Values");
        CommonUtils.hold(3);
        addTreeNodePopupPage.button_search.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
        CommonUtils.hold(3);
        //	AddTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopupPage.searchNodePopup_search.click();
        CommonUtils.hold(5);
        addTreeNodePopupPage.selectTreeNode(parentDataSourceNodeList.getFirst()).click();
        CommonUtils.hold(3);
        selectedNodeList.add(parentDataSourceNodeList.removeFirst());
        addTreeNodePopupPage.button_AddSelectedValues.click();
        treeUtils.waitForElementNotVisible("//button[text()='Add Selected Values']");
        CommonUtils.hold(5);
    }

    @Test(priority = 8, description = "Add Child Nodes")
    public void testcase008() {
        String node = selectedNodeList.get(0);
        tvSpecifyNodesPage.selectNode(node, manageAccountHierarchyDriver).click();
        CommonUtils.hold(3);
        TVSpecifyNodes.img_add.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_search);
        CommonUtils.hold(3);
        addTreeNodePopupPage.button_search.click();
        CommonUtils.hold(5);
        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
        addTreeNodePopupPage.searchNodePopup_search.click();
        CommonUtils.hold(3);
        treesOperations.addMultipleNodes(selectedNodeList.get(0),2);
        CommonUtils.hold(3);
        selectedNodeList.add(parentDataSourceNodeList.removeFirst());
        selectedNodeList.add(parentDataSourceNodeList.removeFirst());
    }

//   @Test(priority = 6, description = "Add Root Node to the Tree Version", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase006() {
//
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treeSummaryPage.selectAction("Edit").click();
//        tvSpecifyDefinitionPage.verifySpecifyDefinition(manageAccountHierarchyDriver);
//        CommonUtils.hold(3);
//        tvSpecifyDefinitionPage.button_Next.click();
//        CommonUtils.hold(5);
//        TVSpecifyNodes.img_add.click();
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_search);
//        addTreeNodePopupPage.selectDataSource(manageAccountHierarchyDriver, "Accounting Flexfield Hierarchy Parent Values");
//        CommonUtils.hold(3);
//        addTreeNodePopupPage.button_search.click();
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
//        CommonUtils.hold(3);
//        //	AddTreeNodePopup.classificationName.sendKeys("Legal Employer");
//        addTreeNodePopupPage.searchNodePopup_search.click();
//        CommonUtils.hold(5);
//        addTreeNodePopupPage.availableNodes();
//        addTreeNodePopupPage.selectNode(AddTreeNodePopup.availableNodes.get(0)).click();
//        CommonUtils.hold(3);
//        addTreeNodePopupPage.button_AddSelectedValues.click();
//        treeUtils.waitForElementNotVisible("//button[text()='Add Selected Values']");
//        CommonUtils.hold(5);
//    }
//
//    @Test(priority = 7, description = "Add Child Nodes", enabled = true, dependsOnMethods = {"testcase006"})
//    public void testcase007() {
//        String node = AddTreeNodePopup.selectedNodes.get(0);
//        tvSpecifyNodesPage.selectNode(node, manageAccountHierarchyDriver).click();
//        CommonUtils.hold(3);
//        TVSpecifyNodes.img_add.click();
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_search);
//        CommonUtils.hold(3);
//        addTreeNodePopupPage.button_search.click();
//        CommonUtils.hold(5);
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
//        //	AddTreeNodePopup.classificationName.sendKeys("Legal Employer");
//        addTreeNodePopupPage.searchNodePopup_search.click();
//        CommonUtils.hold(3);
//        // AddTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
//        treeUtils.addMoreNodesWithoutScrolling();
//        CommonUtils.hold(3);
//        addTreeNodePopupPage.button_AddSelectedValues.click();
//        treeUtils.waitForElementNotVisible("//button[text()='Add Selected Values']");
//        CommonUtils.hold(5);
//
//
//    }
//
//    @Test(priority = 8, description = "Add Grand Child Nodes", enabled = true, dependsOnMethods = {"testcase007"})
//    public void testcase008() {
//        //	TreesUtils.waitForElementToBeClickable(tvSpecifyNodesPage.selectNode(AddTreeNodePopup.selectedNodes.get(0)));
//        TVSpecifyNodes.button_ExpandOrCollapse.click();
//        CommonUtils.hold(5);
//        // tvSpecifyNodesPage.selectNode.click();
//        String node = AddTreeNodePopup.selectedNodes.get(1);
//        tvSpecifyNodesPage.selectNode(node, manageAccountHierarchyDriver).click();
//        CommonUtils.hold(3);
//        TVSpecifyNodes.img_add.click();
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_search);
//        addTreeNodePopupPage.selectDataSource(manageAccountHierarchyDriver, "Accounting Flexfield Hierarchy Detail Values");
//        CommonUtils.hold(3);
//        addTreeNodePopupPage.button_search.click();
//        CommonUtils.hold(3);
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.searchNodePopup_search);
//        //	AddTreeNodePopup.classificationName.sendKeys("Legal Employer");
//        addTreeNodePopupPage.searchNodePopup_search.click();
//        CommonUtils.hold(5);
//        treeUtils.waitForElementToBeClickable(addTreeNodePopupPage.button_AddSelectedValues);
//        // AddTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
//        addTreeNodePopupPage.availableNodes();
//        treeUtils.addMoreNodesWithoutScrolling();
//        CommonUtils.hold(4);
//        addTreeNodePopupPage.button_AddSelectedValues.click();
//        treeUtils.waitForElementNotVisible("//button[text()='Add Selected Values']");
//        CommonUtils.hold(5);
//        TVSpecifyNodes.button_Submit.click();
//        treeUtils.waitForElementToBeClickable(TVSpecifyDefinition.button_OK1);
//        TVSpecifyDefinition.button_OK1.click();
//
//    }
//
//    @Test(priority = 9, description = "Export to Excel", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase009()  {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treeSummaryPage.selectAction("Edit").click();
//        tvSpecifyDefinitionPage.verifySpecifyDefinition(manageAccountHierarchyDriver);
//        CommonUtils.hold(3);
//        tvSpecifyDefinitionPage.button_Next.click();
//        CommonUtils.hold(5);
//        TVSpecifyNodes.img_ExportNodes.click();
//        treeUtils.waitForElementToBeVisible(TVSpecifyNodes.text_ConfirmationText);
//        CommonUtils.hold(3);
//        treeUtils.waitForElementToBeClickable(TVSpecifyNodes.button_Download);
//        TVSpecifyNodes.button_Download.click();
//        treeUtils.waitForElementToBeClickable(TVSpecifyNodes.button_OK);
//        CommonUtils.hold(3);
//        TVSpecifyNodes.button_OK.click();
//        treeUtils.waitForElementNotVisible("//button[contains(@id,'hierCol:downloadokdlgp6')]");
//        CommonUtils.hold(8);
//        TVSpecifyNodes.button_Submit.click();
//        CommonUtils.hold(5);
//        treeSummaryPage.accept_Confirmation_1.click();
//        CommonUtils.hold(5);
//    }
//
//
//    @Test(priority = 10, description = "Audit Tree Version", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase010() {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        treesOperations.auditTreeVersion(treeVersion);
//    }
//
//    @Test(priority = 11, description = "Column Flattening", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase011() {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treesOperations.flattenTreeVersion("column");
//    }
//
//
//    @Test(priority = 12, description = "Row Flattening", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase012()  {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treesOperations.flattenTreeVersion("row");
//    }
//
//    @Test(priority = 13, description = "Delete any Node", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase013() {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treeSummaryPage.selectAction("Edit").click();
//        tvSpecifyDefinitionPage.verifySpecifyDefinition(manageAccountHierarchyDriver);
//        CommonUtils.hold(3);
//        tvSpecifyDefinitionPage.button_Next.click();
//        CommonUtils.hold(5);
//        TVSpecifyNodes.button_ExpandOrCollapse.click();
//        CommonUtils.hold(5);
//        // tvSpecifyNodesPage.selectNode.click();
//        String node = AddTreeNodePopup.selectedNodes.get(1);
//        tvSpecifyNodesPage.selectNode(node, manageAccountHierarchyDriver).click();
//        CommonUtils.hold(3);
//        tvSpecifyNodesPage.img_remove.click();
//        CommonUtils.hold(5);
//        treeUtils.waitForElementToBeClickable(TVSpecifyNodes.deleteWaring_Confirmation);
//        CommonUtils.hold(3);
//        TVSpecifyNodes.deleteWaring_Confirmation.click();
//        CommonUtils.hold(5);
//        TVSpecifyNodes.button_Submit.click();
//        CommonUtils.hold(5);
//        treeSummaryPage.accept_Confirmation.click();
//        CommonUtils.hold(5);
//    }
//
//    @Test(priority = 14, description = "Force Flattening", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase014()  {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treesOperations.forceFlattenTreeVersion();
//    }
//
//    @Test(priority = 15, description = "Duplicate Tree Version", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase015()  {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treesOperations.duplicateTreeVersion(treeVersion1);
//    }
//
//    @Test(priority = 16, description = "Offline Audit", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase016()  {
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
//        CommonUtils.hold(5);
//        treesOperations.offlineAuditTreeVersion(treeVersion1);
//    }
//
//    @Test(priority = 17, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase016"})
//    public void testcase017() throws Exception {
//        new ScheduledProcesses(manageAccountHierarchyDriver).verifyScheduledJobStatus();
//    }
//
//    @Test(priority = 18, description = "Offline Column Flattening", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase018()  {
//        treeUtils.navigateToTreesTaskflow("Manage Account Hierarchies");
//        CommonUtils.hold(3);
//        treeSummaryPage.verifyManageAccountHierarchiesPage();
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
//        CommonUtils.hold(5);
//        treesOperations.offlineFlattenTreeVersion("column");
//    }
//
//    @Test(priority = 19, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase018"})
//    public void testcase019() throws Exception {
//        new ScheduledProcesses(manageAccountHierarchyDriver).verifyScheduledJobStatus();
//    }
//
//    @Test(priority = 20, description = "Offline Row Flattening", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase020()  {
//        treeUtils.navigateToTreesTaskflow("Manage Account Hierarchies");
//        CommonUtils.hold(3);
//        treeSummaryPage.verifyManageAccountHierarchiesPage();
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
//        CommonUtils.hold(5);
//        treesOperations.offlineFlattenTreeVersion("row");
//    }
//
//    @Test(priority = 21, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase020"})
//    public void testcase021() throws Exception {
//        new ScheduledProcesses(manageAccountHierarchyDriver).verifyScheduledJobStatus();
//    }
//
//    @Test(priority = 22, description = "Offline Force Flattening", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase022()  {
//        treeUtils.navigateToTreesTaskflow("Manage Account Hierarchies");
//        CommonUtils.hold(3);
//        treeSummaryPage.verifyManageAccountHierarchiesPage();
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
//        CommonUtils.hold(5);
//        treesOperations.offlineForceFlattenTreeVersion();
//    }
//
//    @Test(priority = 23, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase022"})
//    public void testcase023() throws Exception {
//        new ScheduledProcesses(manageAccountHierarchyDriver).verifyScheduledJobStatus();
//    }
//
//    @Test(priority = 24, description = "Duplicate Tree Version", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase024()  {
//        treeUtils.navigateToTreesTaskflow("Manage Account Hierarchies");
//        CommonUtils.hold(3);
//        treeSummaryPage.verifyManageAccountHierarchiesPage();
//        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion);
//        CommonUtils.hold(5);
//        treesOperations.duplicateTreeVersion(treeVersion2);
//    }
//
//    @Test(priority = 25, description = "Add Hierarchy Nodes", enabled = true, dependsOnMethods = {"testcase005"})
//    public void testcase025()  {
//        treesOperations.searchGeographyTree(treeCode);
//        treesOperations.navigateToSpecifyNodesPage(treeVersion2);
//    }
    @AfterClass(alwaysRun = true)
    public void logout() {
        treeUtils.secureLogout();
    }
}
