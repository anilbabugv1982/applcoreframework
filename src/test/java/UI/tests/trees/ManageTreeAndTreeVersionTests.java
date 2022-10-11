package UI.tests.trees;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures.TreeStructureSummary;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TRSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyNodes;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreeSummaryPage;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesOperations;

import java.util.*;

public class ManageTreeAndTreeVersionTests extends GetDriverInstance {

    private String tree = "CustomGLTree" + CommonUtils.uniqueId();
    private String treeCode = tree;
    private String treeDupCode = treeCode + "Dup";
    private String treeVersion1 = "atg_version1";
    private String treeVersion2 = "atg_version2";
    private String taskFlow = "Manage Trees and Tree Versions";
    private String taskflow_TreeStructure = "Manage Tree Structures";

    private TRSpecifyDefinition trSpecifyDefinitionPage;
    private TVSpecifyDefinition tvSpecifyDefinitionsPage;
    private TVSpecifyNodes tvSpecifyNodesPage;

    private WebDriver manageTreeAndTreeVersionDriver = null;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private ApplicationLogin aLoginInstance;
    private TreesUtils treesUtils;
    private TreeSummaryPage treeSummaryPage;
    private TreesOperations treesOperations;
    private AddTreeNodePopup addTreeNodePopup;
    private TreeStructureSummary treeStructureSummary;
    private Map<String, List<String>> parentDataSourceNodeMap = new LinkedHashMap<>();
    private Map<String, List<String>> detailDataSourceNodeMap = new LinkedHashMap<>();
    private LinkedList<String> parentDataSourceNodeList = new LinkedList<>();
    private LinkedList<String> detailDataSourceNodeList = new LinkedList<>();
    private ArrayList<String> selectedNodeList = new ArrayList<>();

    private String parentDataSource = "Accounting Flexfield Hierarchy Parent Values";
    private String detailDataSource = "Accounting Flexfield Hierarchy Detail Values";

    private String rootNode;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void logOnToApplication(String user, String pwd) throws Exception {
        manageTreeAndTreeVersionDriver = getDriverInstanceObject();
        aLoginInstance = new ApplicationLogin(manageTreeAndTreeVersionDriver);
        ntFInstance = new NavigationTaskFlows(manageTreeAndTreeVersionDriver);
        treeSummaryPage = new TreeSummaryPage(manageTreeAndTreeVersionDriver);
        treesOperations = new TreesOperations(manageTreeAndTreeVersionDriver);
        treesUtils = new TreesUtils(manageTreeAndTreeVersionDriver);
        trSpecifyDefinitionPage = new TRSpecifyDefinition(manageTreeAndTreeVersionDriver);
        tvSpecifyDefinitionsPage = new TVSpecifyDefinition(manageTreeAndTreeVersionDriver);
        tvSpecifyNodesPage = new TVSpecifyNodes(manageTreeAndTreeVersionDriver);
        addTreeNodePopup = new AddTreeNodePopup(manageTreeAndTreeVersionDriver);
        treeStructureSummary = new TreeStructureSummary(manageTreeAndTreeVersionDriver);
        aLoginInstance.login(user, pwd, manageTreeAndTreeVersionDriver);
        CommonUtils.hold(3);

    }

    /*
     * 1.Login. 2.Navigate to Setup and Maintenance 3.Search for
     * "Manage Tree Structures"
     */
    @Test(priority = 1, description = "Navigate to Manage Tree Structures")
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
                manageTreeAndTreeVersionDriver);
        CommonUtils.hold(3);
        ntFInstance.navigateToAOLTaskFlows(taskflow_TreeStructure, manageTreeAndTreeVersionDriver);
        treesUtils.waitForElementToBeVisible(treeStructureSummary.input_TreeStructureCode);
        CommonUtils.hold(5);
    }

    /*
     * 1.Login. 2.Navigate to Setup and Maintenance 3.Search for
     * "Manage Tree Structures"
     */
    @Test(priority = 2, description = "Enable Shuttle Mode", dependsOnMethods = {"testcase001"})
    public void testcase002() throws Exception {
        treeStructureSummary.input_TreeStructureCode.sendKeys("GL_ACCT_FLEX");
        treeStructureSummary.button_Search.click();
        CommonUtils.hold(3);
        treeStructureSummary.action_menu.click();
        treesUtils.waitForElementToBeClickable(treeStructureSummary.action_QuickEdit);
        CommonUtils.hold(3);
        treeStructureSummary.action_QuickEdit.click();
        treesUtils.waitForElementToBeVisible(treeStructureSummary.input_searchViewObject);
        CommonUtils.hold(3);
        treeStructureSummary.input_searchViewObject.clear();
        treeStructureSummary.button_SaveAndClose.click();
        CommonUtils.hold(3);
    }


    /*
     * 1.Login. 2.Navigate to Setup and Maintenance 3.Search for
     * "Manage Geography trees"
     */
    @Test(priority = 3, description = "Navigate to Manage trees And Tree Versions",dependsOnMethods = {"testcase002"})
    public void testcase003() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,
                manageTreeAndTreeVersionDriver);
        CommonUtils.hold(3);
        ntFInstance.navigateToAOLTaskFlows(taskFlow, manageTreeAndTreeVersionDriver);
        treesUtils.waitForElementToBeVisible(treeSummaryPage.input_TreeCode);
        CommonUtils.hold(5);
    }

    /*
     * 1.Search for tree. 2.Select the tree and click on Delete button.
     */
    @Test(priority = 4, description = "Delete Existing Tree",dependsOnMethods = {"testcase003"})
    public void testcase004() throws Exception {
        treesOperations.searchGeographyTree("CustomGL");
        treesOperations.deleteAllCustomTrees("CustomGL");
    }

    /*
     * 1.Click on Create Tree image. 2.Specify Definition Page : Enter all the
     * details 3.Specify Labels Page: Add the labels. 4.Specify Access Rules Page :
     * Submit the tree.
     */
    @Test(priority = 5, description = "Create Tree",dependsOnMethods = {"testcase004"})
    public void testcase005()  {
        treeSummaryPage.createTree.click();
        trSpecifyDefinitionPage.verifyTRSpecifyDefinitioPage();
        treesOperations.createTree(treeCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Duplicate
     * 4.Duplicate Tree Popup : Enter the details.
     */
    @Test(priority = 6, description = "Duplicate the tree with out tree versions", dependsOnMethods = {"testcase005"})
    public void testcase006() {
        treesOperations.searchGeographyTree(treeCode);
        CommonUtils.hold(5);
        treeSummaryPage.selectTree.click();
        CommonUtils.hold(5);
        treesOperations.duplicateGeographyTree(treeDupCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Duplicate
     * 4.Duplicate Tree Popup : Enter the details.
     */
    @Test(priority = 7, description = "Edit the tree", dependsOnMethods = {"testcase006"})
    public void testcase007() {
        treesOperations.searchGeographyTree(treeDupCode);
        treeSummaryPage.selectTree.click();
        treesUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        treesOperations.editGeographyTree(treeDupCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Delete
     */
    @Test(priority = 8, description = "Delete the tree", dependsOnMethods = {"testcase007"})
    public void testcase008() {
        treesOperations.searchGeographyTree(treeDupCode);
        treeSummaryPage.selectTree.click();
        treesOperations.deleteGeographyTree(treeDupCode);
    }

    /*
     * 1.Search for the tree. 2.Select the tree. 3.Click Actions -> Create Tree
     * Version 4.Specify Definition Page : Enter all the details.
     */
    @Test(priority = 9, description = "Create Tree Version", dependsOnMethods = {"testcase008"})
    public void testcase009() {
        //  mgUtil.searchGeographyTree("CustomGeoTree1912180433");
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treesOperations.createTreeVersion(treeVersion1);
    }

    /*
     * 1.Specify Nodes Page: Click on Add button and add the node.
     */
    @Test(priority = 10, description = "Get All Nodes", dependsOnMethods = {"testcase009"})
    public void testcase010() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion1);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        parentDataSourceNodeList.addAll(addTreeNodePopup.getDataSourceTreeNodeMap(parentDataSource));
        CommonUtils.hold(5);
        detailDataSourceNodeList.addAll(addTreeNodePopup.getDataSourceTreeNodeMap(detailDataSource));
//        detailDataSourceNodeList = addTreeNodePopup.getDataSourceTreeNodeMap(detailDataSource);
        CommonUtils.hold(5);
        addTreeNodePopup.button_Cancel.click();
        CommonUtils.hold(3);
        TVSpecifyNodes.button_Submit.click();
        CommonUtils.hold(5);
        treeSummaryPage.acceptConfirmation();
    }


    /*
     * 1.Specify Nodes Page: Click on Add button and add the node.
     */
    @Test(priority = 11, description = "Add Root Node", dependsOnMethods = {"testcase010"})
    public void testcase011() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion1);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        addTreeNodePopup.selectDataSource(manageTreeAndTreeVersionDriver, parentDataSource);
        CommonUtils.hold(3);
        treesOperations.addTreeNode_ShuttleMode("", parentDataSourceNodeList.get(0));
        CommonUtils.hold(3);
        selectedNodeList.add(parentDataSourceNodeList.removeFirst());
        CommonUtils.hold(3);
        tvSpecifyNodesPage.button_Submit.click();
        treeSummaryPage.acceptConfirmation();
    }

    /*
     * Specify Nodes Page: Select the root node and add multiple childs
     */
    @Test(priority = 12, description = "Add child nodes", dependsOnMethods = {"testcase011"})
    public void testcase012() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion1);
        tvSpecifyNodesPage.selectTreeNode(selectedNodeList.get(0)).click();
        CommonUtils.hold(3);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        addTreeNodePopup.selectDataSource(manageTreeAndTreeVersionDriver, parentDataSource);
        CommonUtils.hold(3);
        ArrayList<String> childs = new ArrayList<>();
        childs.add(parentDataSourceNodeList.get(0));
        childs.add(parentDataSourceNodeList.get(1));
        treesOperations.addMultipleNodes(childs);
        CommonUtils.hold(3);
        selectedNodeList.add(parentDataSourceNodeList.removeFirst());
        selectedNodeList.add(parentDataSourceNodeList.removeFirst());
        tvSpecifyNodesPage.button_Submit.click();
        treeSummaryPage.acceptConfirmation();
    }

    /*
     * Specify Nodes Page: Select the child node and multiple childs
     */
    @Test(priority = 13, description = "Add Grand child nodes", dependsOnMethods = {"testcase012"})
    public void testcase013() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion1);
        tvSpecifyNodesPage.selectView("Expand All").click();
        CommonUtils.hold(3);
        tvSpecifyNodesPage.selectTreeNode(selectedNodeList.get(1)).click();
        CommonUtils.hold(3);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        addTreeNodePopup.selectDataSource(manageTreeAndTreeVersionDriver, detailDataSource);
        CommonUtils.hold(3);
        ArrayList<String> childs = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            childs.add(detailDataSourceNodeList.get(i));
            selectedNodeList.add(detailDataSourceNodeList.removeFirst());
        }
        treesOperations.addMultipleNodes(childs);
        CommonUtils.hold(3);
        tvSpecifyNodesPage.selectTreeNode(selectedNodeList.get(2)).click();
        CommonUtils.hold(3);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        addTreeNodePopup.selectDataSource(manageTreeAndTreeVersionDriver, detailDataSource);
        CommonUtils.hold(3);
        ArrayList<String> child2 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            child2.add(detailDataSourceNodeList.get(i));
            selectedNodeList.add(detailDataSourceNodeList.removeFirst());
        }
        treesOperations.addMultipleNodes(childs);
        CommonUtils.hold(3);

        tvSpecifyNodesPage.button_Submit.click();
        treeSummaryPage.acceptConfirmation();
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Duplicate
     */
    @Test(priority = 15, description = "Duplicate Tree Version", dependsOnMethods = {"testcase013"})
    public void testcase015() throws Exception {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
       // CommonUtils.hold(5);
        treesOperations.duplicateTreeVersion(treeVersion2);

    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Audit 3.Audit Results Page : Click on OnlineAudit Button.
     */
    @Test(priority = 16, description = "Active Tree Version", dependsOnMethods = {"testcase015"})
    public void testcase016() throws Exception {
        treesOperations.searchGeographyTree(treeCode);
        CommonUtils.hold(3);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        treesOperations.activeTreeVersion(treeVersion1);
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Column Flatten
     */
    @Test(priority = 17, description = "Column Flattening", dependsOnMethods = {"testcase016"})
    public void testcase017() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("column");
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 18, description = "Row Flattening", dependsOnMethods = {"testcase017"})
    public void testcase018() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

     /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 19, description = "Export All Nodes")
    public void testcase019() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion2);
        CommonUtils.hold(5);
        treesOperations.exportToExcel();
        CommonUtils.hold(5);
        tvSpecifyNodesPage.button_Submit.click();    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 20, description = "Row Flatten tree version")
    public void testcase020() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 21, description = "Remove Node")
    public void testcase021() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 22, description = "Drag and Drop from Bottom to top")
    public void testcase022() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 23, description = "Drag and Drop from top to bottom")
    public void testcase023() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 24, description = "Force Flattening")
    public void testcase024() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    /*
     * 1.Search for the tree and select the tree version. 2.Click on Actions ->
     * Flatten - > Row Flatten
     */
    @Test(priority = 25, description = "Export All Nodes")
    public void testcase025() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.flattenTreeVersion("row");
    }

    @AfterClass
    public void logout() {
        treesUtils.secureLogout();
        manageTreeAndTreeVersionDriver.quit();
    }

}
