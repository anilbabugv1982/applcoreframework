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
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyNodes;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreeSummaryPage;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesOperations;

public class ManageOrganizationTreeTests extends GetDriverInstance {

    private String treeCode = "Org Tree";
    private String treeVersion1 = "CustomOrgVersion1";
    private String treeVersion2 = "CustomOrgVersion2";
    private String customTreeCode1 = "CustomOrgTree1";
    private String customTreeCode2 = "CustomOrgTree2";
    private String customTreeCode3 = "CustomOrgTree3";
    private String taskFlow = "Manage Organization Trees";

    private WebDriver manageOrgTreeDriver;
    private ApplicationLogin applicationLoginPage;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private TreesUtils treeUtils;
    private TVSpecifyDefinition tvSpecifyDefinitionPage;
    private TVSpecifyNodes tvSpecifyNodesPage;
    private AddTreeNodePopup addTreeNodePopup;
    private TreeSummaryPage treeSummaryPage;
    private TreesOperations treesOperations;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void loginToApplication(String user, String pwd) throws Exception {

        // Initializing required objects
        manageOrgTreeDriver = getDriverInstanceObject();
        applicationLoginPage = new ApplicationLogin(manageOrgTreeDriver);
        ntFInstance = new NavigationTaskFlows(manageOrgTreeDriver);
        nMEInstance = new NavigatorMenuElements(manageOrgTreeDriver);
        treeUtils = new TreesUtils(manageOrgTreeDriver);
        tvSpecifyDefinitionPage = new TVSpecifyDefinition(manageOrgTreeDriver);
        tvSpecifyNodesPage = new TVSpecifyNodes(manageOrgTreeDriver);
        addTreeNodePopup = new AddTreeNodePopup(manageOrgTreeDriver);
        treeSummaryPage = new TreeSummaryPage(manageOrgTreeDriver);
        treesOperations = new TreesOperations(manageOrgTreeDriver);
        applicationLoginPage.login(user, pwd, manageOrgTreeDriver);
        CommonUtils.hold(3);

    }

    @Test(priority = 1, description = "Navigate to Mange Organization trees")
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(applicationLoginPage.getGloablePageTemplateInstance().setupandmaintenance,
                manageOrgTreeDriver);
        CommonUtils.hold(3);
        ntFInstance.navigateToAOLTaskFlows(taskFlow, manageOrgTreeDriver);
        treeSummaryPage.verifyManageOrganizationTreesPage();
    }

    @Test(priority = 2, description = "Delete Existing Custom trees", dependsOnMethods = {"testcase001"})
    public void testcase002() {
        treesOperations.searchGeographyTree("CustomOrgTree");
        treesOperations.deleteAllCustomTrees("CustomOrgTree");
    }

    @Test(priority = 3, description = "Deleting existing tree version", dependsOnMethods = {"testcase001"})
    public void testcase003() {

        treesOperations.searchGeographyTree(treeCode);
        // treesOperations.deleteTreeVersoin(treeVersion);
        treesOperations.deleteAllCustomTreeVersions1("CustomOrg");
    }

    @Test(priority = 4, description = "Create Tree Version", dependsOnMethods = {"testcase001"})
    public void testcase004() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperations.createTreeVersion(treeVersion1);
    }

    @Test(priority = 5, description = "Add Root Node to the Tree Version", dependsOnMethods = {"testcase003"})
    public void testcase005() {

        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treeSummaryPage.selectAction("Edit").click();
        CommonUtils.hold(3);
        tvSpecifyDefinitionPage.verifySpecifyDefinition(manageOrgTreeDriver);
        CommonUtils.hold(3);
        tvSpecifyDefinitionPage.button_Next.click();
        CommonUtils.hold(5);
        TVSpecifyNodes.img_add.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.button_search);
        addTreeNodePopup.button_search.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        CommonUtils.hold(3);
        addTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(5);
        addTreeNodePopup.availableNodes();
        addTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(0)).click();
        CommonUtils.hold(3);
        addTreeNodePopup.button_AddTreeNode.click();
        treeUtils.waitForElementNotVisible("//button[text()='Add Tree Node']");
        CommonUtils.hold(5);
    }

    @Test(priority = 6, description = "Add Child Nodes", dependsOnMethods = {"testcase004"})
    public void testcase006() {
        //String node1 = AddTreeNodePopup.selectedNodes.get(0);
        //tvSpecifyNodesPage.selectNode(node1, manageOrgTreeDriver).click();
    	TVSpecifyNodes.childNode.click();
    	CommonUtils.hold(3);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(3);
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.button_search);
        CommonUtils.hold(3);
        addTreeNodePopup.button_search.click();
        CommonUtils.hold(3);
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        addTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(3);
        // AddTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
        treeUtils.addMoreNodes();
        CommonUtils.hold(3);
        addTreeNodePopup.button_AddTreeNode.click();
        treeUtils.waitForElementNotVisible("//button[text()='Add Tree Node']");
        CommonUtils.hold(3);

    }

    @Test(priority = 7, description = "Add Grand Child Nodes", dependsOnMethods = {"testcase005"})
    public void testcase007() {
        // TreesUtils.waitForElementToBeClickable(tvSpecifyNodesPage.selectNode(AddTreeNodePopup.selectedNodes.get(0)));
        TVSpecifyNodes.button_ExpandOrCollapse.click();
        CommonUtils.hold(5);
        // tvSpecifyNodesPage.selectNode.click();
        //String node = AddTreeNodePopup.selectedNodes.get(1);
        //tvSpecifyNodesPage.selectNode(node, manageOrgTreeDriver).click();
        TVSpecifyNodes.grandchildNode.click();
        CommonUtils.hold(3);
        TVSpecifyNodes.img_add.click();
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.button_search);
        CommonUtils.hold(3);
        addTreeNodePopup.button_search.click();
        CommonUtils.hold(3);
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        addTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(3);
        treeUtils.waitForElementToBeClickable(addTreeNodePopup.button_AddTreeNode);
        // AddTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
        treeUtils.addMoreNodes();
        CommonUtils.hold(4);
        addTreeNodePopup.button_AddTreeNode.click();
        treeUtils.waitForElementNotVisible("//button[text()='Add Tree Node']");
        TVSpecifyNodes.button_Submit.click();
        treeUtils.waitForElementToBeClickable(TVSpecifyDefinition.button_OK1);
        TVSpecifyDefinition.button_OK1.click();

    }

    @Test(priority = 8, description = "Audit Tree Version", dependsOnMethods = {"testcase003"})
    public void testcase008() {

        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.auditTreeVersion(treeVersion1);
    }

    @Test(priority = 9, description = "Column Flattening", dependsOnMethods = {"testcase003"})
    public void testcase009() {

        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("column");
    }

    @Test(priority = 10, description = "Row Flattening", dependsOnMethods = {"testcase003"})
    public void testcase010() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    @Test(priority = 11, description = "Duplicate Tree Version", dependsOnMethods = {"testcase003"})
    public void testcase011() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.duplicateTreeVersion(treeVersion2);
    }

    @Test(priority = 12, description = "Force Flattening", dependsOnMethods = {"testcase010"})
    public void testcase012() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.forceFlattenTreeVersion();
    }

    @Test(priority = 13, description = "Export All Nodes", dependsOnMethods = {"testcase010"})
    public void testcase013() {
        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.navigateToSpecifyNodesPage(treeVersion2);
        treesOperations.exportToExcel();
        tvSpecifyNodesPage.button_Submit.click();
        CommonUtils.hold(2);
//        treeSummaryPage.acceptConfirmation();
    }

    @Test(priority = 14, description = "Export Selected Node", dependsOnMethods = {"testcase010"})
    public void testcase014() {
        treesOperations.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.navigateToSpecifyNodesPage(treeVersion2);
        treesOperations.exportToExcel();
        tvSpecifyNodesPage.button_Submit.click();
        CommonUtils.hold(5);
        treeSummaryPage.acceptConfirmation();
    }

    @Test(priority = 16, description = "Duplicate Tree Without Tree Versions")
    public void testcase016() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        CommonUtils.hold(3);
        treesOperations.duplicateGeographyTree(customTreeCode1);
    }

    @Test(priority = 17, description = "Duplicate Tree With Selected Tree Versions", dependsOnMethods = {
            "testcase016"})
    public void testcase017() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        CommonUtils.hold(3);
        treesOperations.duplicateGeographyTreeWithAllTreeVersions(customTreeCode2);
    }

    @Test(priority = 18, description = "Duplicate Tree With Selected Tree Versions", dependsOnMethods = {
            "testcase016"})
    public void testcase018() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        CommonUtils.hold(3);
        treesOperations.duplicateGeographyTreeWithAllTreeVersions(customTreeCode3);
    }

    @Test(priority = 19, description = "Create Multiple Tree Versions", dependsOnMethods = {"testcase016"})
    public void testcase019() {
        treesOperations.searchGeographyTree(customTreeCode2);

        String[] versionCodes = {"aVersion", "eVersion", "bVersion", "1Version", "5Version", "3Version", "AVersion",
                "version1", "version5"};
        for (String versionCode : versionCodes) {
            treeSummaryPage.selectTreeVersion(treeVersion1);
            CommonUtils.hold(5);
            treesOperations.duplicateTreeVersion(versionCode);
        }
    }

    @Test(priority = 20, description = "Verify Sorting Order", dependsOnMethods = {"testcase019"})
    public void testcase020() {
        treesOperations.searchGeographyTree(customTreeCode1);
        treeSummaryPage.button_ExpandOrCollapse.click();
        CommonUtils.hold(5);
    }

    @AfterClass(alwaysRun = true)
    public void logOut() throws InterruptedException {
        treeUtils.secureLogout();
    }
}
