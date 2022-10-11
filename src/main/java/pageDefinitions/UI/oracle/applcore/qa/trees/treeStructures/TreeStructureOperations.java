package pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures;

import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.WarningDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeStructureOperations {

    private WebDriver pageDriver;
    private TreesUtils treesUtils;
    private TreeStructureSummary treeStructureSummary;
    private WarningDialog warningDialog;
    private TSSpecifyDefinition tsSpecifyDefinition;
    private TSSpecifyDataSource tsSpecifyDataSource;
    private TSSpecifyPerformanceOptions tsSpecifyPerformanceOptions;
    private TSSpecifyAccessRules tsSpecifyAccessRules;

    public TreeStructureOperations(WebDriver pageDriver) {
        this.pageDriver = pageDriver;
        treesUtils = new TreesUtils(pageDriver);
        treeStructureSummary = new TreeStructureSummary(pageDriver);
        tsSpecifyDefinition = new TSSpecifyDefinition(pageDriver);
        tsSpecifyDataSource = new TSSpecifyDataSource(pageDriver);
        warningDialog = new WarningDialog(pageDriver);
        tsSpecifyPerformanceOptions = new TSSpecifyPerformanceOptions(pageDriver);
        tsSpecifyAccessRules  = new TSSpecifyAccessRules(pageDriver);
    }

    public void createTreeStructure(String treeStructureCode) {
        treeStructureSummary.img_Create.click();
        CommonUtils.hold(10);
        tsSpecifyDefinition.verifySpecifyDefinitionPage(pageDriver);
        tsSpecifyDefinition.fillInInformation(treeStructureCode, "Oracle Middleware Extensions for Applications");
        CommonUtils.hold(5);
//        tsSpecifyDataSource.fillInformation("None",);

//        Select labelchoice = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'labelingSchemeChoice')]")));
//        labelchoice.selectByIndex(1);
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//img[@alt='Add']")).click();        //click Add icon for ading data sources
//        treeUtils.waitForElementToBeVisible(manageTreeDriver.findElement(By.xpath("//input[contains(@name,'viewObjectNameInputText')]")));
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'viewObjectNameInputText')]")).sendKeys("oracle.apps.fnd.applcore.flex.vst.model.publicView.CharacterValueSetValuePVO");
//        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'itnm')]")).click();
//        CommonUtils.hold(5);
//        Select depthcontent = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@id,'maxDepth::content')]")));
//        depthcontent.selectByIndex(1);
//        CommonUtils.hold(2);
//        manageTreeDriver.findElement(By.xpath("//label[text()='Label Data Source']/ancestor::td[1]/following-sibling::td[1]//input")).sendKeys("oracle.apps.fnd.applcore.trees.model.view.FndLabelVO");
//        CommonUtils.hold(5);
//
//        //manageTreeDriver.findElement(By.xpath("//img[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:addButton::icon']")).click();                 //Click on Add in Data Source Parameters
//        manageTreeDriver.findElement(By.xpath("//img[@title='Add']")).click();
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:it2']")).sendKeys("VIEW_CRITERIA_NAME");
//
//        CommonUtils.hold(5);
//        Select labelchoice1 = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:soc1')]")));
//        labelchoice1.selectByIndex(3);
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:it3']")).sendKeys("HierarchicalValueSetCriteria");
//        manageTreeDriver.findElement(By.xpath("//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:sbc4::Label0']")).click();   //Select Checkbox
//
//        manageTreeDriver.findElement(By.xpath("//img[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:addButton::icon']")).click();                 //Click on Add in Data Source Parameters
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:it2']")).sendKeys("Bind_ValueSetCode");
//        CommonUtils.hold(5);
//        Select labelchoice2 = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:soc1')]")));
//        labelchoice2.selectByIndex(1);
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:it3']")).sendKeys("09Prod Berigte");
//
//
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:sbc4::Label0']")).click();   //Select Checkbox
//        CommonUtils.hold(5);
//
//        manageTreeDriver.findElement(By.xpath("//img[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:addButton::icon']")).click();                 //Click on Add in Data Source Parameters
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:it2']")).sendKeys("Bind_SummaryFlag");
//
//        CommonUtils.hold(5);
//        Select labelchoice3 = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:soc1')]")));
//        labelchoice3.selectByIndex(1);
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:it3']")).sendKeys("Y");
//        manageTreeDriver.findElement(By.xpath("//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:sbc4::Label0']")).click();   //Select Checkbox
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//button[text()='O']")).click();
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
//        CommonUtils.hold(5);
//        manageTreeDriver.findElement(By.xpath("//button[text()='Sub']")).click();
//        CommonUtils.hold(5);
//
//        manageTreeDriver.findElement(By.xpath("//div[text()='Confirmation']/ancestor::tr[2]/following-sibling::tr[2]//button[text()='OK']")).click();
//        CommonUtils.hold(5);
    }

    public void editTreeStructure(String treeStructureCode) {

    }

    public void duplicateTreeStructure(String treeStructureCode) {

    }

    public void searchTreeStructure(String treeStructureCode) {
        treeStructureSummary.input_TreeStructureCode.clear();
        treeStructureSummary.input_TreeStructureCode.sendKeys(treeStructureCode);
        treeStructureSummary.button_Search.click();
        CommonUtils.hold(3);
    }

    public void activeTreeStructure(String treeStructureCode) {
        treeStructureSummary.selectAction("Set Status").click();
        treeStructureSummary.action_SetStatus_Active.click();
        treesUtils.waitForElementToBeClickable(treeStructureSummary.button_AuditDone);
        CommonUtils.hold(3);
        treeStructureSummary.button_AuditDone.click();
        CommonUtils.hold(5);

    }

    public void enableShuttleMode(String treeStructureCode) {
        treeStructureSummary.selectAction("Quick Edit");
        treesUtils.waitForElementToBeVisible(treeStructureSummary.input_searchViewObject);
        CommonUtils.hold(3);
        treeStructureSummary.input_searchViewObject.clear();
        treeStructureSummary.button_SaveAndClose.click();
        treesUtils.waitForElementNotVisible("//button[text()='Save and Close']");
        CommonUtils.hold(5);
    }

    public void deleteTreeStructures(String treeStructureCode) {
        List<WebElement> treeStructureList = pageDriver.findElements(By.xpath("//span[contains(text(),'" + treeStructureCode + "')]"));
        ArrayList<String> treeStructureList_locators = new ArrayList<>();
        for (int i = 0; i < treeStructureList.size(); i++) {
            treeStructureList_locators.add(treeStructureList.get(i).getAttribute("id"));
        }

        for (int i = 0; i < treeStructureList_locators.size(); i++) {
            pageDriver.findElement(By.xpath("//span[@id='" + treeStructureList_locators.get(i) + "']")).click();
            CommonUtils.hold(3);
            treeStructureSummary.selectAction("Delete").click();
            CommonUtils.hold(5);
            warningDialog.button_Yes.click();
            CommonUtils.hold(5);
        }
    }

    public void deleteTreeStructure() {
        treeStructureSummary.selectAction("Delete").click();
        treeStructureSummary.acceptWarning();

    }
    public void deleteAllCustomTreeStructures(String treeStructureCodePattern){
        String searchResultLocator = "//a[contains(text(),'" + treeStructureCodePattern + "')]";
        List<String> allCustomElements = pageDriver.findElements(By.xpath(searchResultLocator)).stream().map(element -> element.getText()).collect(Collectors.toList());
        for (String treeStructureName : allCustomElements) {
            // String locator = "//a[text()='"+ treeName +"']/ancestor::span[1]";
            String locator = "//span[text()='" + treeStructureName + "']";
            By element = By.xpath(locator);
            if (treesUtils.isElementPresent(element) == true) {
                pageDriver.findElement(element).click();
                CommonUtils.hold(3);
            } else {
                System.out.println("Tree Version Not Present");
            }
           deleteTreeStructure();
        }
    }

    public void createTreeStructure1(String treeStructureCode) throws Exception {
        treeStructureSummary.img_Create.click();
        CommonUtils.hold(8);
        // filling information in TVSpecifyDefinition page
        tsSpecifyDefinition.fillInformation(treeStructureCode,"Oracle Middleware Extensions for Applications","Oracle", pageDriver);
        //filling information in TSSpecifyDataSource page
        tsSpecifyDataSource.fillInformation("Level based","oracle.apps.fnd.applcore.flex.vst.model.publicView.CharacterValueSetValuePVO", "Unlimited" ,"oracle.apps.fnd.applcore.trees.model.view.FndLabelVO", pageDriver);
        //filling information in Specify Performance Options page
        tsSpecifyPerformanceOptions.fillInformation("GL_SEG_VAL_HIER_RF","GL_SEG_VAL_HIER_CF","oracle.apps.fnd.applcore.trees.model.entity.FndTreeNodeCfEO","oracle.apps.financials.generalLedger.shared.flexfield.publicView.GlAcctFlexBICPVO", pageDriver);
        //filling information in tsSpecifyAccessRules page
        tsSpecifyAccessRules.submit.click();
        CommonUtils.hold(5);
//        String sucessmessage=ManageTreeStructures.sucessConfirmation.getText();
//        Assert.assertTrue(sucessmessage.contains("created successfully."));
//        manageTreeStructureUtil.verifyTree(treeStructureCode,driver);
        System.out.println("End of createTreeStructure method");
    }
}
