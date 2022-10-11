package pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class ManageTreeStructure {
    private static String TestTS = "AutoTreeTS03" + CommonUtils.uniqueId();
    private static String TestTSDup = "AutoTreeTSDup" + CommonUtils.uniqueId();
    private static String treestruct_code = null;
    private ApplicationLogin aLoginInstance;
    private WebDriver manageTreeDriver;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private TreesUtils treeUtils;

    @FindBy(xpath = "//img[@alt='Create']")
    public WebElement img_Create;

    @FindBy(xpath = "//input[contains(@id,'fndTreeStructureSummaryVCQueryId:value00::content')]")
    public WebElement input_TreeStructureCode;

    @FindBy(xpath = "//button[contains(@id,'fndTreeStructureSummaryVCQueryId::search')]")
    public WebElement button_Search;

    @FindBy(xpath = "//img[contains(@id,'btnDelete::icon')]/ancestor::a")
    public WebElement image_delete;

    @FindBys({@FindBy(xpath = "//span[contains(text(),'AutoTree')]")})
    public List<WebElement> matchedTreeStructures;

    @FindBy(xpath = "//button[contains(@id,'delDiag::yes')]")
    public WebElement accept_Confirmation;

    @FindBy(xpath = "//a[text()='Actions']")
    public WebElement action_menu;

    @FindBy(xpath = "//td[text()='Quick Edit']")
    public WebElement action_QuickEdit;

    @FindBy(xpath = "//input[contains(@id,'qNodeSrch::content')]")
    public WebElement input_searchViewObject;

    @FindBy(xpath = "//button[text()='Save and Close']")
    public WebElement button_SaveAndClose;

    public ManageTreeStructure(WebDriver driver) {
        manageTreeDriver = driver;
        PageFactory.initElements(manageTreeDriver, this);
        aLoginInstance = new ApplicationLogin(manageTreeDriver);
        ntFInstance = new NavigationTaskFlows(manageTreeDriver);
        nMEInstance = new NavigatorMenuElements(manageTreeDriver);
        treeUtils  = new TreesUtils(manageTreeDriver);
    }

    public void createTreeStructure() throws Exception {
        img_Create.click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//input[contains(@id,'DefNameCreate')]")).sendKeys(TestTS);
        manageTreeDriver.findElement(By.xpath("//input[contains(@id,'DefCodeCreate')]")).sendKeys(TestTS);
        treestruct_code = manageTreeDriver.findElement(By.xpath("//input[contains(@id,'DefCodeCreate')]")).getAttribute("value");
        manageTreeDriver.findElement(By.xpath("//input[contains(@id,'DefAppCreate')]")).sendKeys("Oracle Middleware Extensions for Applications");
        manageTreeDriver.findElement(By.xpath("//textarea[contains(@id,'DefDescriptionCreate')]")).sendKeys("Automated Tree Structure -- Do not Touch");
        manageTreeDriver.findElement(By.xpath("//label[contains(@id,'DefAllowMulTVCreate')]")).click();
        manageTreeDriver.findElement(By.xpath("//label[contains(@id,'DefCustomizableCreate')]")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
        CommonUtils.hold(5);
        Select labelchoice = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'labelingSchemeChoice')]")));
        labelchoice.selectByIndex(1);
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//img[@alt='Add']")).click();        //click Add icon for ading data sources
        treeUtils.waitForElementToBeVisible(manageTreeDriver.findElement(By.xpath("//input[contains(@name,'viewObjectNameInputText')]")));
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'viewObjectNameInputText')]")).sendKeys("oracle.apps.fnd.applcore.flex.vst.model.publicView.CharacterValueSetValuePVO");
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'itnm')]")).click();
        CommonUtils.hold(5);
        Select depthcontent = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@id,'maxDepth::content')]")));
        depthcontent.selectByIndex(1);
        CommonUtils.hold(2);
        manageTreeDriver.findElement(By.xpath("//label[text()='Label Data Source']/ancestor::td[1]/following-sibling::td[1]//input")).sendKeys("oracle.apps.fnd.applcore.trees.model.view.FndLabelVO");
        CommonUtils.hold(5);

        manageTreeDriver.findElement(By.xpath("//img[contains(@id,'paramAT:_ATp:addButton')]")).click();                 //Click on Add in Data Source Parameters
        //    manageTreeDriver.findElement(By.xpath("//img[@title='Add']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:it2']")).sendKeys("VIEW_CRITERIA_NAME");

        CommonUtils.hold(5);
        Select labelchoice1 = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:soc1')]")));
        labelchoice1.selectByIndex(3);
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:it3']")).sendKeys("HierarchicalValueSetCriteria");
        manageTreeDriver.findElement(By.xpath("//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:sbc4::Label0']")).click();   //Select Checkbox

        manageTreeDriver.findElement(By.xpath("//img[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:addButton::icon']")).click();                 //Click on Add in Data Source Parameters
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:it2']")).sendKeys("Bind_ValueSetCode");
        CommonUtils.hold(5);
        Select labelchoice2 = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:soc1')]")));
        labelchoice2.selectByIndex(1);
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:it3']")).sendKeys("09Prod Berigte");


        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:sbc4::Label0']")).click();   //Select Checkbox
        CommonUtils.hold(5);

        manageTreeDriver.findElement(By.xpath("//img[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:addButton::icon']")).click();                 //Click on Add in Data Source Parameters
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:it2']")).sendKeys("Bind_SummaryFlag");

        CommonUtils.hold(5);
        Select labelchoice3 = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:soc1')]")));
        labelchoice3.selectByIndex(1);
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[@name='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:it3']")).sendKeys("Y");
        manageTreeDriver.findElement(By.xpath("//label[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:sbc4::Label0']")).click();   //Select Checkbox
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//button[text()='O']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//button[text()='Sub']")).click();
        CommonUtils.hold(5);

        manageTreeDriver.findElement(By.xpath("//div[text()='Confirmation']/ancestor::tr[2]/following-sibling::tr[2]//button[text()='OK']")).click();
        CommonUtils.hold(5);

    }


    public void searchTreeStructure() {
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(treestruct_code);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(5);
    }


    public void duplicateTreeStructure() {
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(treestruct_code);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//img[@alt='Duplicate']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'dupCode')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'dupCode')]")).sendKeys(TestTSDup);
        manageTreeDriver.findElement(By.xpath("//button[(text()='Save and Close')]")).click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(TestTSDup);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(10);
    }


    public void editTreeStructure() {
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(TestTSDup);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//img[@alt='Edit']")).click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'DefNameUpdate')]")).sendKeys("Dup");
        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
        CommonUtils.hold(10);
        Select labelchoice = new Select(manageTreeDriver.findElement(By.xpath("//select[contains(@name,'labelingSchemeChoice')]")));
        labelchoice.selectByIndex(0);
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//button[contains(text(),'N')]")).click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//button[text()='Sub']")).click();
        CommonUtils.hold(10);
        //  assertEquals("Tree Structure is Not Edited","Tree structure "+TestTSDup+" updated successfully.", getText("//div[text()='Tree structure "+TestTSDup+" updated successfully.']"));
        manageTreeDriver.findElement(By.xpath("//div[text()='Confirmation']/ancestor::tr[2]/following-sibling::tr[2]//button[text()='OK']")).click();
        CommonUtils.hold(10);
        //skipBrowserRestart();
    }


    public void auditTreeStructure() {

        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(TestTSDup);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//span[text()='" + TestTSDup + "']")).click();
        manageTreeDriver.findElement(By.xpath("//a[text()='Actions']")).click();
        manageTreeDriver.findElement(By.xpath("//td[text()='Audit']")).click();
        CommonUtils.hold(5);
        //manageTreeDriver.findElement(By.xpath("//button[contains(text(),'D')]")).click();

        manageTreeDriver.findElement(By.xpath("//button[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:5:pt1:AP1:APb']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//span[text()='" + TestTSDup + "']"));
        manageTreeDriver.findElement(By.xpath("//a[text()='Actions']")).click();
        manageTreeDriver.findElement(By.xpath("//td[text()='Set Status']")).click();
        manageTreeDriver.findElement(By.xpath("//td[text()='Active']")).click();
        CommonUtils.hold(10);
        //assertEquals("Tree Structure Audit Failed","Audit passed for selected tree structure.Tree structure status changed to active.", getText("//span[text()='Audit passed for selected tree structure.Tree structure status changed to active.']"));
        manageTreeDriver.findElement(By.xpath("//button[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:5:pt1:AP1:APb']")).click();
        CommonUtils.hold(10);
    }


    public void treeStructureSetStatus() {
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(TestTS);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//span[text()='" + TestTS + "']")).click();
        manageTreeDriver.findElement(By.xpath("//a[text()='Actions']")).click();
        manageTreeDriver.findElement(By.xpath("//td[text()='Set Status']")).click();
        manageTreeDriver.findElement(By.xpath("//td[text()='Active']")).click();
        //assertEquals("Tree Structure Audit Failed","Audit passed for selected tree structure.Tree structure status changed to active.", getText("//span[text()='Audit passed for selected tree structure.Tree structure status changed to active.']"));
        manageTreeDriver.findElement(By.xpath("//button[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:5:pt1:AP1:APb']")).click();
        CommonUtils.hold(5);
    }


    public void deleteTreeStructure() {

        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(TestTSDup);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(5);
        manageTreeDriver.findElement(By.xpath("//img[@alt='Delete']")).click();
        CommonUtils.hold(10);
        manageTreeDriver.findElement(By.xpath("//button[contains(@id,'delDiag::yes')]")).click();
        CommonUtils.hold(5);
        //manageTreeDriver.findElement(By.xpath("//button[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:5:pt1:AP1:APb']")).click();
        //CommonUtils.hold(5);
    }

    public void deleteExistingTreeStructures(String treeStructureCode){
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).clear();
        manageTreeDriver.findElement(By.xpath("//input[contains(@name,'fndTreeStructureSummaryVCQueryId:value00')]")).sendKeys(treeStructureCode);
        manageTreeDriver.findElement(By.xpath("//button[text()='Search']")).click();
        CommonUtils.hold(5);
        List<WebElement> treeList = manageTreeDriver.findElements(By.xpath("//span[contains(text(),'"+treeStructureCode+"')]"));
        ArrayList<String> treeList_locators = new ArrayList<>();
        for(int i =0;i<treeList.size();i++)
        {
            treeList_locators.add(treeList.get(i).getAttribute("id"));
        }

        for(int i=0;i<treeList_locators.size();i++)
        {
            //  driver.findElement(By.xpath("//div[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:ATT2:_ATTp:fndTreeSearchVCQueryResultId::db']/table/tbody/tr/td")).click();
            manageTreeDriver.findElement(By.xpath("//span[@id='"+treeList_locators.get(i)+"']")).click();
            CommonUtils.hold(3);
            //  driver.findElement(By.xpath("//a[text()  ='"+treeCode+"']/ancestor::tr[1]")).click();
            manageTreeDriver.findElement(By.xpath("//a[text()='Actions']")).click();
            CommonUtils.hold(5);
            manageTreeDriver.findElement(By.xpath("//td[text()='Delete']")).click();
            //driver.findElement(By.xpath("//img[contains(@alt,'Delete')]")).click();
            CommonUtils.hold(5);
            manageTreeDriver.findElement(By.xpath("//button[contains(@id,'delDiag::yes')]")).click();
            CommonUtils.hold(5);
        }

    }


}
