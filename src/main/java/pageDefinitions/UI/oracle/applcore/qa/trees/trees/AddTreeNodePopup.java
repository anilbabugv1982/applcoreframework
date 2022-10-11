package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import java.util.*;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import UtilClasses.UI.CommonUtils;

public class AddTreeNodePopup {

    public static LinkedList<String> availableNodes = null;
    public static LinkedList<String> selectedNodes = new LinkedList<>();

    public static ArrayList<String> allAvailableNodes = new ArrayList<>();
    public static ArrayList<String> allSelectedNodes = new ArrayList<>();

    public WebDriver addTreeDriver;
    public List<String> parentNodes = null;
    public List<String> childNodes = null;
    public Map<String, List<String>> dataSourceNodeMap = new HashMap<>();

    public AddTreeNodePopup(WebDriver driver) {
        PageFactory.initElements(driver, this);
        addTreeDriver = driver;
    }

    @FindBy(xpath = "//button[contains(@id,'addView:cb1')]")
    public WebElement button_search;

    // @FindBy(xpath="//button[contains(@id,'input_TreeCode')]")
    //@FindBy(xpath = "//button[contains(@id,'input_TreeCode')][contains(text(),'Search')]")
    @FindBy(xpath = "//button[contains(@id,'qryId1::search') and contains(text(),'Search')]")
    public WebElement searchNodePopup_search;

    @FindBy(xpath = "//button[text()='Add Tree Node']")
    public WebElement button_AddTreeNode;

    @FindBy(xpath = "//button[text()='Add Selected Values']")
    public WebElement button_AddSelectedValues;

    @FindBys({@FindBy(xpath = "//div[contains(@id,'addView:r1:0:AT1:_ATp::_ahCt')]/descendant::tr[contains(@class,'af_table_data-row')]/td[2]/descendant::span[1]")})
    public List<WebElement> allNodes;

    @FindBys({@FindBy(xpath = "//tr[@class='af_table_data-row']/descendant::span[1]")})
    public List<WebElement> nodeNames;

    @FindBys({@FindBy(xpath = "//tr[@class='af_table_data-row']/descendant::span[contains(@id,'pt1:AP1:AH1:addView:r1:0:AT1:_ATp:resId1')][1]")})
    public List<WebElement> nodeValues;

    // This field is present in Add node popup for Orgnization tree
    @FindBy(xpath = "//label[text()='Classification Name']/ancestor::tr[1]/descendant::input")
    public WebElement classificationName;

    @FindBys({@FindBy(xpath = "//ul[contains(@id,'addView:selectOrderShuttle1::leadUl')]/descendant::li")})
    public List<WebElement> nodeNames_ShuttleMode;

    @FindBy(xpath = "//a[@title='Move selected items to: Selected Nodes']")
    public WebElement shuttleIcon;

    @FindBy(xpath = "//button[contains(@id,'addView:addDiag::ok')]")
    public WebElement button_OK;

    @FindBy(xpath = "//select[contains(@id,'addView:dataSourceChoice')]")
    public WebElement dataSource_Dropdown;
    
    @FindBy(xpath = "//a[contains(@id,'addView:dataSourceChoice::drop')]")
    public WebElement dataSource_Dropdown2;

    @FindBy(xpath = "//div[contains(@id,'addView:addPop::content')]/descendant::button[text()='Cancel']")
    public WebElement button_Cancel;

    // Cancel button in SearchNode popup
    @FindBy(xpath = "//button[contains(@id,'pt1:AP1:AH1:addView:r1:0:cb2')]")
    public WebElement searchNodePopup_button_Cancel;

    public void selectDataSource(WebDriver driver, String text) {
        try {
            Select dataSource = new Select(
                    driver.findElement(By.xpath("//select[contains(@id,'addView:dataSourceChoice::content')]")));
            dataSource.selectByVisibleText(text);
        } catch (Exception e) {
        	dataSource_Dropdown.click();
            CommonUtils.hold(3);
            //driver.findElement(By.xpath("//li[contains(text(),'" + text + "')]")).click();
            driver.findElement(By.xpath("//option[contains(text(),'" + text + "')]")).click();
        }
        CommonUtils.hold(5);
    }
    
    public void selectDataSource2(WebDriver driver, String text) {
        try {
            Select dataSource = new Select(
                    driver.findElement(By.xpath("//select[contains(@id,'addView:dataSourceChoice::content')]")));
            dataSource.selectByVisibleText(text);
        } catch (Exception e) {
        	dataSource_Dropdown2.click();
            CommonUtils.hold(3);
            driver.findElement(By.xpath("//li[contains(text(),'" + text + "')]")).click();
            //driver.findElement(By.xpath("//option[contains(text(),'" + text + "')]")).click();
        }
        CommonUtils.hold(5);
    }

    public WebElement selectNode(String nodeName) {
        String node = availableNodes.remove(0);
        selectedNodes.add(node);
        //WebElement element1 = addTreeDriver.findElement(By.xpath("//div[contains(@id,'AT1:_ATp::_ahCt')]"));
        WebElement element = addTreeDriver.findElement(
                By.xpath("//tr[@class='af_table_data-row']/descendant::span[contains(text(),'" + nodeName + "')]"));
        JavascriptExecutor jse = (JavascriptExecutor) addTreeDriver;
        // jse.executeScript("arguments[0].scrollTop=arguments[1].offsetTop",
        // element1,element);

        jse.executeScript("arguments[0].scrollIntoView(true)", element);

        return element;
    }

    public WebElement selectTreeNode(String nodeName) {
        selectedNodes.add(nodeName);
        WebElement element = addTreeDriver.findElement(
                By.xpath("//tr[@class='af_table_data-row']/descendant::span[contains(text(),'" + nodeName + "')]"));
        JavascriptExecutor jse = (JavascriptExecutor) addTreeDriver;
        jse.executeScript("arguments[0].scrollIntoView(true)", element);
        return element;
    }

    public WebElement selectNodeWithoutScrolling(String nodeName) {
        String node = availableNodes.remove(0);
        selectedNodes.add(node);
        WebElement element1 = addTreeDriver.findElement(By.xpath("//div[contains(@id,'AT1:_ATp::_ahCt')]"));
        WebElement element = addTreeDriver.findElement(
                By.xpath("//tr[@class='af_table_data-row']/descendant::span[contains(text(),'" + nodeName + "')]"));
        JavascriptExecutor jse = (JavascriptExecutor) addTreeDriver;
        return element;
    }

    public void availableNodes() {
        availableNodes = new LinkedList<>();
      //  selectedNodes = new LinkedList<>();
        for (WebElement node : nodeNames) {
            availableNodes.add(node.getText());
        }
    }

    public void availableNodes_ShuttleMode() {
        availableNodes = new LinkedList<>();
        selectedNodes = new LinkedList<>();
        for (WebElement node : nodeNames_ShuttleMode) {
            availableNodes.add(node.getText());
        }
    }

    public WebElement selectNode_ShuttleMode(String nodeName) {
        //selectedNodes.add(nodeName);
        WebElement element = addTreeDriver.findElement(By.xpath("//label[text()='" + nodeName + "']"));
        return element;
    }

    public void getParentNodeList() {
//		CommonUtils.hold(5);
//    	AddTreeNodePopup.selectDataSource(addTreeDriver, "Geographic Tree Calendar Top Scopes Data Source");
//    	CommonUtils.hold(5);
        parentNodes = new ArrayList<String>();
        for (WebElement element : nodeNames_ShuttleMode) {
            parentNodes.add(element.getText());
        }
    }

    public void getChildNodeList() {
//		CommonUtils.hold(5);
//		AddTreeNodePopup.selectDataSource(addTreeDriver, "Geographic Tree Territory Code Data Source");
//		CommonUtils.hold(5);
        childNodes = new ArrayList<String>();
        for (WebElement element : nodeNames_ShuttleMode) {
            childNodes.add(element.getText());
        }
    }

    public void getAllNodes() {
        List<String> nodeNameList = allNodes.stream().map(element -> element.getText()).collect(Collectors.toList());
        allAvailableNodes.addAll(nodeNameList);
        System.out.println(allAvailableNodes);
    }

    public void selectNewNode(String nodeName) {
        addTreeDriver.findElement(By.xpath("//span[contains(text(),'" + nodeName + "')]")).click();
        CommonUtils.hold(3);
        allAvailableNodes.remove(allAvailableNodes.indexOf(nodeName));
        allSelectedNodes.add(nodeName);
    }


    public List<String> getDataSourceTreeNodeMap(String datasource) {
        selectDataSource(addTreeDriver, datasource);
        List<String> nodeNameList = nodeNames_ShuttleMode.stream().map(element -> element.getText()).collect(Collectors.toList());
        return nodeNameList;
    }

    public List<String> getDataSourceTreeNodeMap() {
        List<String> nodeNameList = nodeValues.stream().map(element -> element.getText()).collect(Collectors.toList());
        nodeNameList.stream().forEach(System.out::println);
        return nodeNameList;
    }


    public void selectMultipleNodes(String startingFromNode,int numnberOfNodes) {
        Actions actions = new Actions(addTreeDriver).moveToElement(selectTreeNode(startingFromNode)).click();
        for (int i = 0; i < numnberOfNodes; i++) {
            actions.sendKeys(Keys.PAGE_DOWN).build().perform();
        }
    }


}
