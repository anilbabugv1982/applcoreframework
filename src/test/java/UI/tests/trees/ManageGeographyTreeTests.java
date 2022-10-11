package UI.tests.trees;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TRSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyNodes;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreeSummaryPage;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesOperations;
import TestBase.UI.GetDriverInstance;


public class ManageGeographyTreeTests extends GetDriverInstance {

    private String tree = "CustomGeoTree" + CommonUtils.uniqueId();
    private String treeCode = tree;
    private String treeDupCode = treeCode + "Dup";
    private String treeVersion1 = "atg_version1";
    private String treeVersion2 = "atg_version2";

    private WebDriver manageGeographyDriver;
    private ApplicationLogin aLoginInstance;
    private NavigationTaskFlows ntFInstance;
    private TreesOperations treesOperation;
    private TreesUtils treeUtils;
    private TreeSummaryPage treeSummaryPage;
    private TRSpecifyDefinition trSpecifyDefinitionPage;
    private TVSpecifyNodes tvSpecifyNodesPage;
    private TVSpecifyDefinition tvSpecifyDefinition;
    private AddTreeNodePopup addTreeNodePopup;
    private String rootNode;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void logOnToApplication(String user, String pwd) throws Exception {
        manageGeographyDriver = getDriverInstanceObject();
        aLoginInstance = new ApplicationLogin(manageGeographyDriver);
        ntFInstance = new NavigationTaskFlows(manageGeographyDriver);
        treeSummaryPage = new TreeSummaryPage(manageGeographyDriver);
        treesOperation = new TreesOperations(manageGeographyDriver);
        treeUtils = new TreesUtils(manageGeographyDriver);
        trSpecifyDefinitionPage = new TRSpecifyDefinition(manageGeographyDriver);
        tvSpecifyDefinition = new TVSpecifyDefinition(manageGeographyDriver);
        tvSpecifyNodesPage = new TVSpecifyNodes(manageGeographyDriver);
        addTreeNodePopup = new AddTreeNodePopup(manageGeographyDriver);
        aLoginInstance.login(user, pwd, manageGeographyDriver);
        CommonUtils.hold(3);

    }

    /*
     * 1.Login. 2.Navigate to Setup and Maintenance 3.Search for
     * "Manage Geography trees"
     */
    @Test(priority = 1, description = "Navigate to Manage Geography trees")
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
                manageGeographyDriver);
        CommonUtils.hold(2);
        ntFInstance.navigateToAOLTaskFlows("Manage Geography Trees", manageGeographyDriver);
        treeUtils.waitForElementToBeClickable(treeSummaryPage.createTree);
        CommonUtils.hold(3);
    }

    /*
     * 1.Search for tree. 2.Select the tree and click on Delete button.
     */
    @Test(priority = 2, description = "Delete Existing Geography Tree", dependsOnMethods = {"testcase001"})
    public void testcase002() throws Exception {
        treesOperation.deleteAllCustomTrees("CustomGeo");
    }

    /*
     * 1.Click on Create Tree image. 2.Specify Definition Page : Enter all the
     * details 3.Specify Labels Page: Add the labels. 4.Specify Access Rules Page :
     * Submit the tree.
     */
    @Test(priority = 3, description = "Create Geography Tree",dependsOnMethods = {"testcase002"})
    public void testcase003() throws Exception {
        treeSummaryPage.createTree.click();
        trSpecifyDefinitionPage.verifyTRSpecifyDefinitioPage();
        treesOperation.createGeographyTree(treeCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Duplicate
     * 4.Duplicate Tree Popup : Enter the details.
     */
    @Test(priority = 4, description = "Duplicate the tree with out tree versions", dependsOnMethods = {"testcase003"})
    public void testcase004() throws Exception {
        treesOperation.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperation.duplicateGeographyTree(treeDupCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Duplicate
     * 4.Duplicate Tree Popup : Enter the details and select tree versions.
     */
    @Test(priority = 5, description = "Duplicate the tree with tree versions", dependsOnMethods = {"testcase004"})
    public void testcase005() {

    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Duplicate
     * 4.Duplicate Tree Popup : Enter the details.
     */
    @Test(priority = 6, description = "Edit the tree", dependsOnMethods = {"testcase005"})
    public void testcase006() throws Exception {
        treesOperation.searchGeographyTree(treeDupCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperation.editGeographyTree(treeDupCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Delete
     */
    @Test(priority = 7, description = "Delete the tree", dependsOnMethods = {"testcase006"})
    public void testcase007() throws Exception {
        treesOperation.searchGeographyTree(treeDupCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperation.deleteGeographyTree(treeDupCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Create Tree
     * Version 4.Specify Definition Page : Enter all the details.
     */
    @Test(priority = 8, description = "Create Tree Version", dependsOnMethods = {"testcase007"})
    public void testcase008() throws Exception {
       	treesOperation.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperation.createTreeVersion(treeVersion1);
    }

    /*
     * 1.Specify Nodes Page: Click on Add button and add the node.
     */
    @Test(priority = 9, description = "Add Root Node", dependsOnMethods = {"testcase008"})
    public void testcase009() throws Exception {
        treesOperation.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion("atg_version1");
        CommonUtils.hold(5);
        treeSummaryPage.selectAction("Edit").click();
        tvSpecifyDefinition.verifySpecifyDefinition(manageGeographyDriver);
        CommonUtils.hold(3);
        tvSpecifyDefinition.button_Next.click();
        CommonUtils.hold(5);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        addTreeNodePopup.selectDataSource2(manageGeographyDriver, "Geographic Tree Calendar Top Scopes Data Source");
        CommonUtils.hold(5);
        addTreeNodePopup.getParentNodeList();
        rootNode = addTreeNodePopup.parentNodes.get(0);
        addTreeNodePopup.selectNode_ShuttleMode(rootNode).click();
        addTreeNodePopup.shuttleIcon.click();
        CommonUtils.hold(3);
        addTreeNodePopup.button_OK.click();
    }

    /*
     * Specify Nodes Page: Select the root node and add multiple childs
     */
    @Test(priority = 10, description = "Add child nodes", dependsOnMethods = {"testcase009"})
    public void testcase010() throws Exception {
    	tvSpecifyNodesPage.selectNode(rootNode, manageGeographyDriver).click();
        CommonUtils.hold(5);
    	TVSpecifyNodes.img_add.click();
    	CommonUtils.hold(5);
    	addTreeNodePopup.selectNode_ShuttleMode(addTreeNodePopup.parentNodes.get(1)).click();
    	addTreeNodePopup.shuttleIcon.click();
        addTreeNodePopup.selectNode_ShuttleMode(addTreeNodePopup.parentNodes.get(2)).click();
    	addTreeNodePopup.shuttleIcon.click();
    	addTreeNodePopup.button_OK.click();
    	treeUtils.waitForElementNotVisible("//button[contains(@id,'addView:addDiag::ok')]");
    	CommonUtils.hold(3);
        tvSpecifyNodesPage.button_Submit.click();
        treeSummaryPage.acceptConfirmation();
    }

//    /*
//     * Specify Nodes Page: Select the child node and multiple childs
//     */
//    @Test(priority = 11, description = "Add Grand child nodes")
//    public void testcase011() throws Exception {
//
//    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Duplicate
     */
    @Test(priority = 12, description = "Duplicate Tree Version", dependsOnMethods = {"testcase010"})
    public void testcase012() throws Exception {
        treesOperation.searchGeographyTree(treeCode);
        CommonUtils.hold(5);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        treesOperation.duplicateTreeVersion(treeVersion2);

    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Audit 3.Audit Results Page : Click on OnlineAudit Button.
     */
    @Test(priority = 13, description = "Active Tree Version", dependsOnMethods = {"testcase012"})
    public void testcase013() throws Exception {
        treesOperation.searchGeographyTree(treeCode);
        CommonUtils.hold(5);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        treesOperation.activeTreeVersion(treeVersion1);
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Column Flatten
     */
//    @Test(priority = 16, description = "Column Flattening", dependsOnMethods = {"testcase013"})
//    public void testcase016()  {
//        treesOperation.searchGeographyTree(treeCode);
//        treeSummaryPage.selectTreeVersion(treeVersion1);
//        CommonUtils.hold(5);
//        treesOperation.flattenTreeVersion("column");
//    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 17, description = "Row Flattening", dependsOnMethods = {"testcase012"})
    public void testcase017() {
        treesOperation.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperation.flattenTreeVersion("row");
    }

    /*
     * Specify Nodes Page: Select the child node and multiple childs
     */
//    @Test(priority = 18, description = "Add child node")
//    public void testcase018() throws Exception {
//
//    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
//    @Test(priority = 19, description = "Force Flattening")
//    public void testcase019() {
//
//    }

    /*
     * Specify Nodes Page: Select the node and click on remove button.
     */
//    @Test(priority = 20, description = "Remove Node")
//    public void testcase020() throws Exception {
//
//    }

    /*
     * Specify Nodes Page: Select the node and click on remove button.
     */
//    @Test(priority = 21, description = "Drag and drop node")
//    public void testcase021() throws Exception {
//
//    }

    /*
     * Specify Nodes Page: Select the node and click on export button.
     */
//    @Test(priority = 22, description = "Export Nodes")
//    public void testcase022() throws Exception {
//
//    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
//    @Test(priority = 23, description = "Force Flattening")
//    public void testcase023() {
//
//    }

    @AfterClass(alwaysRun = true)
    public void logOut() {

        treeUtils.secureLogout();
    }
}