package UI.tests.trees;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.ess.ScheduledProcesses;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyDefinition;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TVSpecifyNodes;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreeSummaryPage;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesOperations;

public class ManageDepartmentTreeTests extends GetDriverInstance {

    private String treeCode = "FHD";
    private String treeVersion1 = "CustomDeptVersion1";
    private String treeVersion2 = "CustomDeptVersion2";
    private String treeVersion3 = "CustomDeptVersion3";
    private String treeVersion4 = "CustomDeptVersion4";
    private String treeVersion5 = "CustomDeptVersion5";
    private String taskFlow="Manage Department Trees";

    private ApplicationLogin applicationLoginPage;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;

    private WebDriver manageDepartmentTreeDriver;
    private TreesUtils treesUtils;
    private TVSpecifyDefinition tvSpecifyDefinitionPage;
    private TVSpecifyNodes tvSpecifyNodesPage;
    private AddTreeNodePopup addTreeNodePopup;
    private ScheduledProcesses scheduleProcess;
    private TreesOperations treesOperations;
    private TreeSummaryPage treeSummaryPage;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void loginToApplication(String user, String pwd) throws Exception {
        // Initializing required objects
        manageDepartmentTreeDriver = getDriverInstanceObject();
        applicationLoginPage = new ApplicationLogin(manageDepartmentTreeDriver);
        ntFInstance = new NavigationTaskFlows(manageDepartmentTreeDriver);
        nMEInstance = new NavigatorMenuElements(manageDepartmentTreeDriver);
        treesUtils = new TreesUtils(manageDepartmentTreeDriver);
        tvSpecifyDefinitionPage = new TVSpecifyDefinition(manageDepartmentTreeDriver);
        tvSpecifyNodesPage = new TVSpecifyNodes(manageDepartmentTreeDriver);
        addTreeNodePopup = new AddTreeNodePopup(manageDepartmentTreeDriver);
        treeSummaryPage = new TreeSummaryPage(manageDepartmentTreeDriver);
        treesOperations = new TreesOperations(manageDepartmentTreeDriver);
        scheduleProcess = new ScheduledProcesses(manageDepartmentTreeDriver);
        applicationLoginPage.login(user, pwd, manageDepartmentTreeDriver);
        CommonUtils.hold(3);
    }

    @Test(priority = 1, description = "Navigate to Manage Department trees", enabled = true)
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(applicationLoginPage.getGloablePageTemplateInstance().setupandmaintenance,
                manageDepartmentTreeDriver);
        CommonUtils.hold(3);
        ntFInstance.navigateToAOLTaskFlows(taskFlow, manageDepartmentTreeDriver);
        treeSummaryPage.verifyManageDepartmentTreePage();
    }


    @Test(priority = 2, description = "Deleting existing tree version", enabled = true)
    public void testcase002() {
        treesOperations.searchGeographyTree(treeCode);
        treesOperations.deleteAllCustomTreeVersions1("CustomDept");
    }

    @Test(priority = 3, description = "Create Tree Version", enabled = true ,dependsOnMethods = {"testcase002"})
    public void testcase003() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTree.click();
        treesOperations.createTreeVersion(treeVersion1);
    }

    @Test(priority = 4, description = "Add Root Node to the Tree Version", enabled = true , dependsOnMethods = {"testcase002"})
    public void testcase004() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treeSummaryPage.selectAction("Edit").click();
        tvSpecifyDefinitionPage.verifySpecifyDefinition(manageDepartmentTreeDriver);
        CommonUtils.hold(3);
        tvSpecifyDefinitionPage.button_Next.click();
        CommonUtils.hold(5);
        tvSpecifyNodesPage.img_add.click();
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.button_search);
        addTreeNodePopup.button_search.click();
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        CommonUtils.hold(3);
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(5);
        addTreeNodePopup.availableNodes();
        CommonUtils.hold(5);
        //addTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(0)).click();
        addTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
        CommonUtils.hold(3);
        addTreeNodePopup.button_AddTreeNode.click();
        CommonUtils.hold(3);
    }

    @Test(priority = 5, description = "Add Child Nodes", enabled = true , dependsOnMethods = {"testcase004"})
    public void testcase005() {
        treesUtils.waitForElementToBeClickable(tvSpecifyNodesPage.selectNode);
        // tvSpecifyNodesPage.selectNode.click();
        //String node = AddTreeNodePopup.selectedNodes.get(0);
        String node = AddTreeNodePopup.availableNodes.get(0);
        tvSpecifyNodesPage.selectNode(node, manageDepartmentTreeDriver).click();
        CommonUtils.hold(3);
        tvSpecifyNodesPage.img_add.click();
        CommonUtils.hold(3);
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.button_search);
        CommonUtils.hold(3);
        addTreeNodePopup.button_search.click();
        CommonUtils.hold(3);
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(3);
        // AddTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
        treesUtils.addMoreNodes1();
        CommonUtils.hold(3);
        addTreeNodePopup.button_AddTreeNode.click();
        treesUtils.waitForElementNotVisible("//button[text()='Add Tree Node']");
    }

    @Test(priority = 6, description = "Add Grand Child Nodes", enabled = true , dependsOnMethods = {"testcase005"})
    public void testcase006() {
        treesUtils.waitForElementToBeClickable(tvSpecifyNodesPage.selectNode);
        tvSpecifyNodesPage.button_ExpandOrCollapse.click();
        CommonUtils.hold(5);
        // tvSpecifyNodesPage.selectNode.click();
        //String node = AddTreeNodePopup.selectedNodes.get(2);
        String node = AddTreeNodePopup.availableNodes.get(1);
        tvSpecifyNodesPage.selectNode(node, manageDepartmentTreeDriver).click();
        CommonUtils.hold(3);
        tvSpecifyNodesPage.img_add.click();
        CommonUtils.hold(3);
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.button_search);
        CommonUtils.hold(3);
        addTreeNodePopup.button_search.click();
        CommonUtils.hold(3);
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(3);
        treesUtils.waitForElementToBeClickable(addTreeNodePopup.button_AddTreeNode);
        // AddTreeNodePopup.selectNode(AddTreeNodePopup.availableNodes.get(1)).click();
        treesUtils.addMoreNodes2();
        CommonUtils.hold(4);
        addTreeNodePopup.button_AddTreeNode.click();
        treesUtils.waitForElementNotVisible("//button[text()='Add Tree Node']");
        tvSpecifyNodesPage.button_Submit.click();
        treesUtils.waitForElementToBeClickable(TVSpecifyDefinition.button_OK1);
        TVSpecifyDefinition.button_OK1.click();

    }

    @Test(priority = 7, description = "Schedule Column Flattening", enabled = true)
    public void testcase007() {
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.scheduleFlattenTreeVersion("column");
    }

    @Test(priority = 8, description = "Verify Job status", enabled = true, dependsOnMethods = {"testcase007"})
    public void testcase008() throws Exception {
        scheduleProcess.verifyScheduledJobStatus();
    }

    @Test(priority = 9, description = "Schedule Row Flatterning", enabled = true, dependsOnMethods = {"testcase008"})
    public void testcase009()  {
        treesUtils.navigateToTreesTaskflow(taskFlow);
        CommonUtils.hold(3);
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.scheduleFlattenTreeVersion("row");
    }

    @Test(priority = 10, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase009"})
    public void testcase010() throws Exception {
        scheduleProcess.verifyScheduledJobStatus();
    }

    @Test(priority = 11, description = "Schedule Audit Tree Version", enabled = true, dependsOnMethods = {"testcase010"})
    public void testcase011()  {
        treesUtils.navigateToTreesTaskflow(taskFlow);
        CommonUtils.hold(3);
        treesOperations.searchGeographyTree(treeCode);
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.scheduleAuditTreeVersion(treeVersion1);
    }

    @Test(priority = 12, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase011"})
    public void testcase012() throws Exception {
        scheduleProcess.verifyScheduledJobStatus();
    }

    @Test(priority = 13, description = "Schedule Force Flattening", enabled = true, dependsOnMethods = {"testcase012"})
    public void testcase013()  {
        treesUtils.navigateToTreesTaskflow(taskFlow);
        CommonUtils.hold(3);
        treesOperations.searchGeographyTree("FHD");
        treeSummaryPage.selectTreeVersion(treeVersion1);
        CommonUtils.hold(5);
        treesOperations.scheduleforceFlattenTreeVersion();
    }

    @Test(priority = 14, description = "Verify Job Status", enabled = true, dependsOnMethods = {"testcase013"})
    public void testcase014() throws Exception {
        scheduleProcess.verifyScheduledJobStatus();
    }

    @AfterClass(alwaysRun = true)
    public void logout() {
        treesUtils.secureLogout();
    }
}
