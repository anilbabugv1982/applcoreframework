package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.ProgressCheck;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TreesOperations {

    private TreesUtils treeUtil;
    private WebDriver webDriverRef;
    private TRSpecifyDefinition trSpecifyDefinitionPage;
    private TreeSummaryPage treeSummaryPage;
    private TRSpecifyLabels trSpecifyLabelsPage;
    private TRSpecifyAccessRules trSpecifyAccessRulesPage;
    private TVSpecifyDefinition tvSpecifyDefinition;
    private TVSpecifyNodes tvSpecifyNodes;
    private TreeAuditResult treeAuditResult;
    private FlatteningPage flatteningPage;
    private AddTreeNodePopup addTreeNodePopup;
    private Actions actions;
    private ProgressCheck progressCheck;

    public TreesOperations(WebDriver driver) {
        this.webDriverRef = driver;
        treeUtil = new TreesUtils(webDriverRef);
        treeSummaryPage = new TreeSummaryPage(webDriverRef);
        trSpecifyDefinitionPage = new TRSpecifyDefinition(webDriverRef);
        trSpecifyLabelsPage = new TRSpecifyLabels(webDriverRef);
        trSpecifyAccessRulesPage = new TRSpecifyAccessRules(webDriverRef);
        tvSpecifyDefinition = new TVSpecifyDefinition(webDriverRef);
        treeAuditResult = new TreeAuditResult(webDriverRef);
        tvSpecifyNodes = new TVSpecifyNodes(webDriverRef);
        flatteningPage = new FlatteningPage(webDriverRef);
        addTreeNodePopup = new AddTreeNodePopup(webDriverRef);
        actions = new Actions(webDriverRef);
        progressCheck = new ProgressCheck(webDriverRef);
    }

    public void createGeographyTree(String treeName) {
        trSpecifyDefinitionPage.enterDetails(treeName, "create");
        trSpecifyLabelsPage.verifySpecifyLabelsPage();
        trSpecifyLabelsPage.button_Next.click();
        trSpecifyAccessRulesPage.verifyPageLoaded();
        trSpecifyAccessRulesPage.buttonSubmit().click();
        treeSummaryPage.verifyPageLoaded();

    }

    public void createTree(String treeCode)  {
        trSpecifyDefinitionPage.verifyTRSpecifyDefinitioPage();
        CommonUtils.hold(5);
        trSpecifyDefinitionPage.input_Name.sendKeys(treeCode);
        trSpecifyDefinitionPage.input_Code.sendKeys(treeCode);
        trSpecifyDefinitionPage.input_TreeStructure.sendKeys("Accounting Flexfield Hierarchy");
        trSpecifyDefinitionPage.input_Name.click();
        CommonUtils.hold(5);
        trSpecifyDefinitionPage.expand_DetailValueSet.click();
        treeUtil.waitForElementToBeVisible(trSpecifyDefinitionPage.input_detailValueSetCode);
        CommonUtils.hold(3);
        trSpecifyDefinitionPage.input_detailValueSetCode.clear();
        trSpecifyDefinitionPage.input_detailValueSetCode.sendKeys("09Prod Berigte");
        trSpecifyDefinitionPage.expand_ParentValueSet.click();
        treeUtil.waitForElementToBeVisible(trSpecifyDefinitionPage.input_parentValueSetCode);
        CommonUtils.hold(3);
        trSpecifyDefinitionPage.input_parentValueSetCode.clear();
        trSpecifyDefinitionPage.input_parentValueSetCode.sendKeys("09Prod Berigte");
        trSpecifyDefinitionPage.button_Save.click();
        CommonUtils.hold(3);
        trSpecifyDefinitionPage.button_Next.click();
        trSpecifyLabelsPage.verifySpecifyLabelsPage();
        trSpecifyLabelsPage.button_Next.click();
        trSpecifyAccessRulesPage.verifyPageLoaded();
        trSpecifyAccessRulesPage.buttonSubmit().click();
        treeSummaryPage.verifyPageLoaded();
    }

    public void createTree(String treeCode, String treeStructureCode, String valueSetCode) {
        trSpecifyDefinitionPage.verifyTRSpecifyDefinitioPage();
        CommonUtils.hold(5);
        trSpecifyDefinitionPage.input_Name.sendKeys(treeCode);
        trSpecifyDefinitionPage.input_Code.sendKeys(treeCode);
        trSpecifyDefinitionPage.input_TreeStructure.sendKeys(treeStructureCode);
        trSpecifyDefinitionPage.input_Name.click();
        CommonUtils.hold(5);
        trSpecifyDefinitionPage.expand_DetailValueSet.click();
        treeUtil.waitForElementToBeVisible(trSpecifyDefinitionPage.input_detailValueSetCode);
        CommonUtils.hold(3);
//        trSpecifyDefinitionPage.input_detailValueSetCode.clear();
//        trSpecifyDefinitionPage.input_detailValueSetCode.sendKeys(valueSetCode);
        trSpecifyDefinitionPage.input_detailValueSetCode1.clear();
        trSpecifyDefinitionPage.input_detailValueSetCode1.sendKeys(valueSetCode);
        trSpecifyDefinitionPage.button_Save.click();
        CommonUtils.hold(3);
        trSpecifyDefinitionPage.button_Next.click();
        CommonUtils.hold(3);
        trSpecifyLabelsPage.verifySpecifyLabelsPage();
        trSpecifyLabelsPage.img_add.click();
        CommonUtils.hold(3);
        trSpecifyLabelsPage.img_ShuttleAll.click();
        CommonUtils.hold(2);
        trSpecifyLabelsPage.labelPopup_buttonOK.click();
        CommonUtils.hold(3);
        trSpecifyLabelsPage.button_Next.click();
        trSpecifyAccessRulesPage.verifyPageLoaded();
        trSpecifyAccessRulesPage.buttonSubmit().click();
        treeSummaryPage.verifyPageLoaded();
    }

    public void searchGeographyTree(String treeName) {
        treeUtil.waitForElementToBeVisible(treeSummaryPage.input_TreeCode);
        CommonUtils.hold(3);
        treeSummaryPage.input_TreeCode.clear();
        treeSummaryPage.input_TreeCode.sendKeys(treeName);
        treeSummaryPage.button_Search.click();
        CommonUtils.hold(3);
        //      treeUtil.waitForElementToBeVisible(treeSummaryPage.selectTree);
    }

    public void duplicateGeographyTree(String treeDupCode) {
        treeSummaryPage.selectAction("Duplicate").click();
        treeSummaryPage.verifyDuplicateTreePopup();
        treeSummaryPage.duplicateTreeCode.clear();
        treeSummaryPage.duplicateTreeName.clear();
        treeSummaryPage.duplicateTreeCode.sendKeys(treeDupCode);
        treeSummaryPage.duplicateTreeName.sendKeys(treeDupCode);
        treeSummaryPage.saveAndClose.click();
        treeSummaryPage.acceptConfirmation();
    }

    public void duplicateGeographyTreeWithAllTreeVersions(String treeDupCode) {
        treeSummaryPage.selectAction("Duplicate").click();
        treeSummaryPage.verifyDuplicateTreePopup();
        treeSummaryPage.duplicateTreeCode.clear();
        treeSummaryPage.duplicateTreeName.clear();
        treeSummaryPage.duplicateTreeCode.sendKeys(treeDupCode);
        treeSummaryPage.duplicateTreeName.sendKeys(treeDupCode);
        CommonUtils.hold(5);
        List<WebElement> versions = treeSummaryPage.duplicatePopup_treeVersionList;
        for (WebElement version : versions) {
            version.click();
            CommonUtils.hold(2);
        }
        treeSummaryPage.saveAndClose.click();
        treeSummaryPage.tvAcceptConfirmation();
    }

    public void editGeographyTree(String treeCode) {

        treeSummaryPage.selectAction("Edit").click();
        trSpecifyDefinitionPage.enterDetails(treeCode, "edit");
        trSpecifyLabelsPage.verifySpecifyLabelsPage();
        trSpecifyLabelsPage.button_Next.click();
        trSpecifyAccessRulesPage.verifyPageLoaded();
        trSpecifyAccessRulesPage.buttonSubmit().click();
        treeSummaryPage.verifyPageLoaded();
        treeSummaryPage.acceptConfirmation();
    }

    public void deleteGeographyTree(String treeCode) {
        treeSummaryPage.selectAction("Delete").click();
        treeSummaryPage.acceptWarning();
    }

    public void createTreeVersion(String treeVersionCode) {
        treeSummaryPage.selectAction("Create Tree Version").click();
        tvSpecifyDefinition.verifySpecifyDefinition(webDriverRef);
        CommonUtils.hold(3);
        tvSpecifyDefinition.enterDetails(treeVersionCode);
        CommonUtils.hold(3);
        tvSpecifyNodes.acceptConfirmation();
        CommonUtils.hold(3);
        tvSpecifyNodes.button_Submit.click();
        treeSummaryPage.acceptConfirmation();
    }

    public void duplicateTreeVersion(String treeVersionCode) {
        treeSummaryPage.selectAction("Duplicate").click();
        CommonUtils.hold(3);
        treeUtil.waitForElementToBeVisible(treeSummaryPage.duplicateTreeVersionName);
        CommonUtils.hold(3);
        treeSummaryPage.duplicateTreeVersionName.sendKeys(treeVersionCode);
        treeSummaryPage.saveAndClose.click();
        treeUtil.waitForElementNotVisible("//button[text()='Save and Close']");
        progressCheck.waitForProcessCheckCompletion();
    }

    public void deleteTreeVersoin(String treeVersionCode) {
        treeSummaryPage.selectAction("Delete").click();
        treeSummaryPage.acceptWarning();
    }

    public void deleteAllCustomTreeVersions(String treeCode) {

        searchGeographyTree(treeCode);
        treeSummaryPage.expandTree();
        List<String> allCustomElements = webDriverRef.findElements(By.xpath("//a[contains(text(),'atg_version')]")).stream().map(element -> element.getText()).collect(Collectors.toList());
        for (String versionName : allCustomElements) {
            String locator = "//a[text()='" + versionName + "']/ancestor::td[1]/following-sibling::td[1]";
            By element = By.xpath(locator);
            if (treeUtil.isElementPresent(element) == true) {
                webDriverRef.findElement(element).click();
                CommonUtils.hold(3);
            } else {
                System.out.println("Tree Version Not Present");
            }
            deleteTreeVersoin(versionName);
        }
    }

    public void deleteAllCustomTreeVersions1(String treeVersionCodePattern) {
        treeSummaryPage.expandTree();
        List<String> allCustomElements = webDriverRef.findElements(By.xpath("//a[contains(text(),'" + treeVersionCodePattern + "')]")).stream().map(element -> element.getText()).collect(Collectors.toList());
        for (String versionName : allCustomElements) {
            String locator = "//a[text()='" + versionName + "']/ancestor::td[1]/following-sibling::td[1]";
            By element = By.xpath(locator);
            if (treeUtil.isElementPresent(element) == true) {
                webDriverRef.findElement(element).click();
                CommonUtils.hold(3);
            } else {
                System.out.println("Tree Version Not Present");
            }
            deleteTreeVersoin(versionName);
        }
    }

    public void deleteAllCustomTrees(String treeCodePattern) {
        List<String> allCustomElements = webDriverRef.findElements(By.xpath("//a[contains(text(),'" + treeCodePattern + "')]")).stream().map(element -> element.getText()).collect(Collectors.toList());
        for (String treeName : allCustomElements) {
            // String locator = "//a[text()='"+ treeName +"']/ancestor::span[1]";
            String locator = "//span[text()='" + treeName + "']";
            By element = By.xpath(locator);
            if (treeUtil.isElementPresent(element) == true) {
                webDriverRef.findElement(element).click();
                CommonUtils.hold(3);
            } else {
                System.out.println("Tree Version Not Present");
            }
            deleteTreeVersoin(treeName);
        }
    }

    public void activeTreeVersion(String treeVersionCode) {
        treeSummaryPage.selectAction("Set Status").click();
        treeSummaryPage.action_SetStatus_Active.click();
        treeUtil.waitForElementToBeClickable(treeAuditResult.button_OnlineAudit);
        CommonUtils.hold(5);
        treeAuditResult.button_OnlineAudit.click();
        CommonUtils.hold(5);
        treeUtil.waitForElementToBeClickable(treeAuditResult.accept_Confirmation);
        CommonUtils.hold(3);
        treeAuditResult.accept_Confirmation.click();
        treeUtil.waitForElementNotVisible("//button[contains(@id,'okdlgp')]");
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();

    }

    public void auditTreeVersion(String treeVersionCode) {
        treeSummaryPage.selectAction("Audit").click();
        treeUtil.waitForElementToBeClickable(treeAuditResult.button_OnlineAudit);
        CommonUtils.hold(5);
        treeAuditResult.button_OnlineAudit.click();
        treeUtil.waitForElementToBeClickable(treeAuditResult.accept_Confirmation);
        CommonUtils.hold(3);
        treeAuditResult.accept_Confirmation.click();
        treeUtil.waitForElementNotVisible("//button[contains(@id,'okdlgp')]");
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();
    }

    public void offlineAuditTreeVersion(String treeVersionCode) {
        treeSummaryPage.selectAction("Audit").click();
        treeUtil.waitForElementToBeClickable(treeAuditResult.button_OnlineAudit);
        CommonUtils.hold(5);
        treeAuditResult.button_OfflineAudit().click();
        CommonUtils.hold(5);
        treeAuditResult.getProcessID();
        treeAuditResult.offlineAction_Confirmation.click();
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();
    }

    public void scheduleAuditTreeVersion(String treeVersionCode) {
        treeSummaryPage.selectAction("Audit").click();
        treeUtil.waitForElementToBeClickable(treeAuditResult.button_ScheduleAudit);
        CommonUtils.hold(3);
        treeAuditResult.button_ScheduleAudit.click();
        treeUtil.waitForElementToBeClickable(flatteningPage.button_Submit_ScheduleFlattening);
        flatteningPage.button_Submit_ScheduleFlattening.click();
        treeUtil.waitForElementToBeClickable(flatteningPage.confirmationPopup_OK);
        flatteningPage.getProcessID();
        flatteningPage.confirmationPopup_OK.click();
        CommonUtils.hold(5);
    }

    public void flattenTreeVersion(String flattenType) {
        treeSummaryPage.selectAction("Flatten").click();
        if ("column".equalsIgnoreCase(flattenType)) {
            treeSummaryPage.action_Flatten_ColumnFlatterning.click();
        } else {
            treeSummaryPage.action_Flatten_RowFlatterning.click();
        }
        CommonUtils.hold(5);
        flatteningPage.button_OnlineFlattening.click();
        CommonUtils.hold(5);
//        treeUtil.waitForElementToBeClickable(treeAuditResult.accept_Confirmation);
//        CommonUtils.hold(3);
//        treeAuditResult.accept_Confirmation.click();
//        treeUtil.waitForElementNotVisible("//button[contains(@id,'okdlgp')]");
        progressCheck.waitForProcessCheckCompletion();
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();
    }

    public void forceFlattenTreeVersion() {
        treeSummaryPage.selectAction("Flatten").click();
        treeSummaryPage.action_Flatten_ColumnFlatterning.click();
        CommonUtils.hold(5);
        flatteningPage.dropDown_OnlineFlattening.click();
        CommonUtils.hold(5);
        flatteningPage.button_OnlineForceFlattening.click();
        treeUtil.waitForElementToBeClickable(treeAuditResult.accept_Confirmation);
        CommonUtils.hold(3);
        treeAuditResult.accept_Confirmation.click();
        treeUtil.waitForElementNotVisible("//button[contains(@id,'okdlgp')]");
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();
    }

    public void scheduleFlattenTreeVersion(String flattenType) {
        treeSummaryPage.selectAction("Flatten").click();
        if ("column".equalsIgnoreCase(flattenType)) {
            treeSummaryPage.action_Flatten_ColumnFlatterning.click();
        } else {
            treeSummaryPage.action_Flatten_RowFlatterning.click();
        }
        CommonUtils.hold(5);
        treeUtil.waitForElementToBeClickable(flatteningPage.button_ScheduleFlattening);
        flatteningPage.button_ScheduleFlattening.click();
        CommonUtils.hold(3);
        treeUtil.waitForElementToBeClickable(flatteningPage.button_Submit_ScheduleFlattening);
        CommonUtils.hold(3);
        flatteningPage.button_Submit_ScheduleFlattening.click();
        CommonUtils.hold(3);
        treeUtil.waitForElementToBeClickable(flatteningPage.confirmationPopup_OK);
        flatteningPage.getProcessID();
        flatteningPage.confirmationPopup_OK.click();
        CommonUtils.hold(5);
    }

    public void scheduleforceFlattenTreeVersion() {
        treeSummaryPage.selectAction("Flatten").click();
        treeSummaryPage.action_Flatten_ColumnFlatterning.click();
        treeUtil.waitForElementToBeClickable(flatteningPage.button_ScheduleFlattening);
        CommonUtils.hold(5);
        flatteningPage.dropDown_ScheduleFlattening.click();
        flatteningPage.button_ScheduleForceFlattening.click();
        CommonUtils.hold(3);
        treeUtil.waitForElementToBeClickable(flatteningPage.button_Submit_ScheduleFlattening);
        CommonUtils.hold(3);
        flatteningPage.button_Submit_ScheduleFlattening.click();
        treeUtil.waitForElementToBeClickable(flatteningPage.confirmationPopup_OK);
        CommonUtils.hold(3);
        flatteningPage.getProcessID();
        flatteningPage.confirmationPopup_OK.click();
        CommonUtils.hold(5);
    }

    public void offlineFlattenTreeVersion(String flattenType) {
        treeSummaryPage.selectAction("Flatten").click();
        if ("column".equalsIgnoreCase(flattenType)) {
            treeSummaryPage.action_Flatten_ColumnFlatterning.click();
        } else {
            treeSummaryPage.action_Flatten_RowFlatterning.click();
        }
        CommonUtils.hold(5);
        flatteningPage.dropDown_OnlineFlattening.click();
        flatteningPage.button_OfflineFlattening.click();
//        treeUtil.waitForElementToBeClickable(treeAuditResult.button_Yes);
        CommonUtils.hold(3);
        flatteningPage.getOfflineProcessID();
        flatteningPage.offlineAction_acceptConfirmationPopup.click();
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();
    }

    public void offlineForceFlattenTreeVersion() {
        treeSummaryPage.selectAction("Flatten").click();
        treeSummaryPage.action_Flatten_ColumnFlatterning.click();
        CommonUtils.hold(5);
        flatteningPage.dropDown_OnlineFlattening.click();
        flatteningPage.button_OfflineForceFlattening.click();
//        treeUtil.waitForElementToBeClickable(treeAuditResult.button_Yes);
        CommonUtils.hold(3);
        flatteningPage.getOfflineProcessID();
        flatteningPage.offlineAction_acceptConfirmationPopup.click();
        CommonUtils.hold(5);
        treeAuditResult.button_Done.click();
    }

    public void addNode(String parentNode, List<String> childNodes) {
        if (!(parentNode.isEmpty())) {
            tvSpecifyNodes.selectNode(parentNode).click();
            CommonUtils.hold(3);
        }
        TVSpecifyNodes.img_add.click();
        treeUtil.waitForElementToBeClickable(addTreeNodePopup.button_search);
        addTreeNodePopup.button_search.click();
        treeUtil.waitForElementToBeClickable(addTreeNodePopup.searchNodePopup_search);
        CommonUtils.hold(3);
        addTreeNodePopup.classificationName.sendKeys("Legal Employer");
        addTreeNodePopup.searchNodePopup_search.click();
        CommonUtils.hold(5);
        addTreeNodePopup.selectNewNode(childNodes.get(0));
        addTreeNodePopup.button_AddTreeNode.click();
        treeUtil.waitForElementNotVisible("//button[text()='Add Tree Node']");
        CommonUtils.hold(5);
    }

    public void navigateToSpecifyNodesPage(String treeVersionName) {
        treeSummaryPage.selectTreeVersion(treeVersionName);
        CommonUtils.hold(5);
        treeSummaryPage.selectAction("Edit").click();
        CommonUtils.hold(3);
        tvSpecifyDefinition.verifySpecifyDefinition(webDriverRef);
        CommonUtils.hold(3);
        tvSpecifyDefinition.button_Next.click();
        CommonUtils.hold(5);
    }

    public void addTreeNode_ShuttleMode(String parent, String nodeName) {
        addTreeNodePopup.selectNode_ShuttleMode(nodeName).click();
        treeUtil.waitForElementToBeClickable(addTreeNodePopup.shuttleIcon);
        addTreeNodePopup.shuttleIcon.click();
        CommonUtils.hold(3);
        addTreeNodePopup.button_OK.click();
    }

    public void addMultipleNodes(List<String> nodes) {
        Actions action = actions.keyDown(Keys.LEFT_CONTROL);
        for (String node : nodes) {
            action.moveToElement(addTreeNodePopup.selectNode_ShuttleMode(node)).click(addTreeNodePopup.selectNode_ShuttleMode(node));
            CommonUtils.hold(2);
        }
        action.keyUp(Keys.LEFT_CONTROL).build().perform();
        CommonUtils.hold(3);
        addTreeNodePopup.shuttleIcon.click();
        CommonUtils.hold(3);
        addTreeNodePopup.button_OK.click();
    }

    public void addMultipleNodes(String startingNode,int numberOfNodes) {
        WebElement node = addTreeNodePopup.selectTreeNode(startingNode);
        node.click();
        Actions action = new Actions(webDriverRef).moveToElement(node).click();
        for (int i = 0; i < numberOfNodes; i++) {
            action.sendKeys(Keys.PAGE_DOWN).build().perform();
        }
        CommonUtils.hold(3);
        addTreeNodePopup.button_AddSelectedValues.click();
        treeUtil.waitForElementNotVisible("//button[text()='Add Selected Values']");
        CommonUtils.hold(5);
    }

    public void exportToExcel() {
        TVSpecifyNodes.img_ExportNodes.click();
        treeUtil.waitForElementToBeVisible(TVSpecifyNodes.text_ConfirmationText);
        CommonUtils.hold(3);
        treeUtil.waitForElementToBeClickable(TVSpecifyNodes.button_Download);
        TVSpecifyNodes.button_Download.click();
        treeUtil.waitForElementToBeClickable(TVSpecifyNodes.button_OK);
        CommonUtils.hold(3);
        TVSpecifyNodes.button_OK.click();
        treeUtil.waitForElementNotVisible("//button[contains(@id,'hierCol:downloadokdlgp6')]");
        CommonUtils.hold(8);
    }

    public void exportToExcel(String nodeName) {

    }

}
