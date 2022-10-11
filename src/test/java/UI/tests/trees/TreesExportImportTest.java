package UI.tests.trees;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;
import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.treeLabels.TreeLabelOperations;
import pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures.*;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TRSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyNodes;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreeSummaryPage;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesOperations;

public class TreesExportImportTest extends GetDriverInstance {

    private String timestamp;
    private String treeStructure, searchOption, treeName, implProject;
    private String treeVersion;
    private String id;
    private List<String> taskFlowName;
    private String customTreeStructure = "CustomATGTS" + CommonUtils.uniqueId();
    private String treeStructureCode = customTreeStructure;

    private String customTreeLabel = "CustomATGTL" + CommonUtils.uniqueId();
    private String treeLabelCode = customTreeLabel;

    private String customTreeCode = "CustomATGTR" + CommonUtils.uniqueId();
    private String treeCode = customTreeCode;
    private String valueSetCode = "VF_Product 17859";
    private String treeVersion1 = "atg_version1";

    @FindBy(xpath = "//input[contains(@id,'AP1:fndTreeStructureSummaryVCQueryId:value00::content')]")
    public static WebElement code;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public static WebElement search;

    private WebDriver treesExportImportDriver;
    private FSMExpImpWrapper fsmExpImWrapperObject;
    private TreeStructureOperations treeStructureOperations;
    private ApplicationLogin aLoginInstance;
    private NavigationTaskFlows ntFInstance;
    private TreeLabelOperations treeLabelOperations;
    private TreeSummaryPage treeSummaryPage;
    private TRSpecifyDefinition trSpecifyDefinitionPage;
    private TreesOperations treesOperations;
    private AddTreeNodePopup addTreeNodePopup;
    private TVSpecifyNodes tvSpecifyNodesPage;
    private TreesUtils treeUtils;

    public TreesExportImportTest() throws WebDriverException {

    }

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void setUp(String user, String password) throws Exception {
        treesExportImportDriver = getDriverInstanceObject();
        treeUtils = new TreesUtils(treesExportImportDriver);
        fsmExpImWrapperObject = new FSMExpImpWrapper(treesExportImportDriver);
        treeStructureOperations = new TreeStructureOperations(treesExportImportDriver);
        treeLabelOperations = new TreeLabelOperations(treesExportImportDriver);
        treeSummaryPage = new TreeSummaryPage(treesExportImportDriver);
        trSpecifyDefinitionPage = new TRSpecifyDefinition(treesExportImportDriver);
        treesOperations = new TreesOperations(treesExportImportDriver);
        addTreeNodePopup = new AddTreeNodePopup(treesExportImportDriver);
        tvSpecifyNodesPage = new TVSpecifyNodes(treesExportImportDriver);
        aLoginInstance = new ApplicationLogin(treesExportImportDriver);
        ntFInstance = new NavigationTaskFlows(treesExportImportDriver);
        id = CommonUtils.uniqueId();
        aLoginInstance.login(user, password, treesExportImportDriver);
        CommonUtils.hold(5);

    }

    @Test(priority = 1, description = "Navigate to Manage Tree Structure")
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, treesExportImportDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows("Manage Tree Structures", treesExportImportDriver);
    }

    @Test(priority = 2, description = "delete Existing Tree Structure")
    public void testcase002() {
        treeStructureOperations.searchTreeStructure("CustomATGTS");
        treeStructureOperations.deleteAllCustomTreeStructures("CustomATGTS");
    }

    @Test(priority = 3, description = "Create Tree Structure")
    public void testcase003() throws Exception {
        treeStructureOperations.createTreeStructure1(treeStructureCode);
        treeStructureOperations.searchTreeStructure(treeStructureCode);
        treeStructureOperations.activeTreeStructure(treeStructureCode);
    }

    @Test(priority = 4, description = "Navigate to Manage Tree Labels")
    public void testcase004() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, treesExportImportDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows("Manage Tree Labels", treesExportImportDriver);
    }

//    @Test(priority = 5,description = "Delete Existing Custom Tree Labels")
//    public void testcase005() throws Exception{
//        treeLabelOperations.createTreeLabel(treeStructureCode,treeLabelCode);
//    }

    @Test(priority = 6, description = "Create Tree Label")
    public void testcase006()  {
        treeLabelOperations.createTreeLabel(treeLabelCode, treeStructureCode);
    }

    @Test(priority = 7, description = "Navigate to Manage trees and Tree Versions")
    public void testcase007() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, treesExportImportDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows("Manage Trees and Tree Versions", treesExportImportDriver);
        CommonUtils.hold(3);
    }

    @Test(priority = 8, description = "Delete Existing Custom trees")
    public void testcase008()  {
        treesOperations.searchGeographyTree("CustomATGTR");
        treesOperations.deleteAllCustomTrees("CustomATGTR");
    }

    @Test(priority = 9, description = "Create Custom Tree")
    public void testcase009()  {
        treeSummaryPage.createTree.click();
        trSpecifyDefinitionPage.verifyTRSpecifyDefinitioPage();
        treesOperations.createTree(treeCode, treeStructureCode, valueSetCode);
    }


    @Test(priority = 10, description = "Create Tree Version")
    public void testcase010() throws Exception {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treeUtils.waitForElementToBeVisible(treeSummaryPage.treeSelected);
        CommonUtils.hold(3);
        treesOperations.createTreeVersion(treeVersion1);

    }

    @Test(priority = 11, description = "Add Nodes", dependsOnMethods = {"testcase009"})
    public void testcase011() throws Exception {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.navigateToSpecifyNodesPage(treeVersion1);
        TVSpecifyNodes.img_add.click();
        CommonUtils.hold(5);
        addTreeNodePopup.button_search.click();
        CommonUtils.hold(5);
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(5);
        addTreeNodePopup.availableNodes();
        addTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(0)).click();
        CommonUtils.hold(3);
        addTreeNodePopup.button_AddSelectedValues.click();
        treeUtils.waitForElementNotVisible("//button[text()='Add Selected Values']");
        CommonUtils.hold(5);
        tvSpecifyNodesPage.button_Submit.click();
        CommonUtils.hold(3);
        treeSummaryPage.acceptConfirmation();
        treesOperations.activeTreeVersion(treeVersion1);

    }

    @Test(priority = 12, description = "Navigate to Implementation project")
    public void testcase012() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, treesExportImportDriver);
        treeUtils.waitForElementToBeClickable(ntFInstance.panelDrawer);
        CommonUtils.hold(5);
        ntFInstance.panelDrawer.click();
        treeUtils.waitForElementToBeClickable(ntFInstance.implementationProject);
        CommonUtils.hold(3);
        ntFInstance.implementationProject.click();
        CommonUtils.hold(3);
    }

    @Test(priority = 13, description = "Delete Existing Impl projects.")
    public void testcase013() throws Exception {

    }

    @Test(priority = 14, description = "Create Impl Project")
    public void testcase014() throws Exception {
        CommonUtils.hold(8);
        System.out.println("Starting of Creation Of ManageImplementionProject testcase02");
        taskFlowName = new ArrayList<String>();
        searchOption = "Tasks";
        implProject = "TreesDemo" + id;
        taskFlowName.add("Manage Tree Labels");
        taskFlowName.add("Manage Tree Structures");
        taskFlowName.add("Manage Trees and Tree Versions");
        fsmExpImWrapperObject.createImplementationProject(implProject, taskFlowName, searchOption,
                treesExportImportDriver);
    }

    @Test(priority = 15, description = "Create Configuration Package")
    public void testcase015() throws Exception {
        System.out.println("Starting Export process and downloading configuration package");
        fsmExpImWrapperObject.createConfigurationProject(implProject, treesExportImportDriver);
        fsmExpImWrapperObject.downloadPackage(treesExportImportDriver);
        CommonUtils.hold(3);
    }

    @Test(priority = 16, description = "Edit the tree version")
    public void testcase016() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, treesExportImportDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToAOLTaskFlows("Manage Trees and Tree Versions", treesExportImportDriver);
        CommonUtils.hold(3);
        treesOperations.searchGeographyTree(treeCode);
        CommonUtils.hold(3);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treeSummaryPage.selectAction("Edit").click();
        CommonUtils.hold(5);
        treeUtils.waitForElementToBeClickable(TreeSummaryPage.editOk);
        TreeSummaryPage.editOk.click();
        CommonUtils.hold(3);
    }

    @Test(priority = 17, description = "Upload Package and Import")
    public void testcase017() throws Exception {
        System.out.println("Start of Uploading Configuration Set And Starting Import process");
        fsmExpImWrapperObject.uploadConfigurationPackage(implProject, treesExportImportDriver);
        fsmExpImWrapperObject.importSetupData(implProject, treesExportImportDriver);
    }


    @AfterClass(alwaysRun = true)
    public void logOut() throws Exception {
        treeUtils.secureLogout();
    }
}
