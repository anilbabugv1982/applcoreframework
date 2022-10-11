package pageDefinitions.UI.oracle.applcore.qa.Dff;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class DFFUtils extends DescriptiveFlexPage {

    private NavigationTaskFlows navFlow;

    public DFFUtils(WebDriver driver) {
        super(driver);
        navFlow = new NavigationTaskFlows(driver);

    }

    private Map<String, String> dffParameters = new LinkedHashMap<>();
    private Actions act;
    private JavascriptExecutor js;

    public void navigateToDFFtf(WebDriver driver) {
        try {
            driver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseTaskListManagerTop"));
            CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,8);
            js = (JavascriptExecutor) driver;
            navFlow.navigateToAOLTaskFlows("Manage Descriptive Flexfields", driver);
            CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,10);
            CommonUtils.explicitWait(searchBtn, "Click", "", driver);
        } catch (Exception dffSearch) {
            System.out.println("Navigating to dff task-flow failed. Please review the error message : " + dffSearch.getMessage());
            dffSearch.printStackTrace();
        }

    }

    public boolean searchDFFCode(String dffCode, WebDriver driver) {

        try {
            dffSearchField.clear();
            dffSearchField.click();
            dffSearchField.sendKeys(dffCode);
            searchBtn.click();
            CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,10);

            try{
                driver.findElement(By.xpath("//span[contains(text(),'" + dffCode + "')]")).isDisplayed();
                return driver.findElement(By.xpath("//span[contains(text(),'" + dffCode + "')]")).isDisplayed();
            }catch(Exception exp){
                driver.findElement(By.xpath("//td[contains(text(),'" + dffCode + "')]")).isDisplayed();
                return driver.findElement(By.xpath("//td[contains(text(),'" + dffCode + "')]")).isDisplayed();
            }

        } catch (Exception dff) {
            System.out.println("DFF Code Is Not Found In Search Results. Please Review The Error Message. FlexCode : " + dff.getMessage());
            dff.printStackTrace();
            return false;
        }

    }

    public void createSegment(WebDriver driver, String... args) {
        try {
            System.out.println(args.length);
            for (int i = 0; i < args.length; i += 2) {
                dffParameters.put(args[i], args[i + 1]);
                System.out.println("Key -> " + args[i] + " && Value -> " + args[i + 1]);
            }

            if (searchDFFCode(dffParameters.get("dffFlexCode"), driver)) {
                CommonUtils.waitforElementtoClick(5,edithBtn,driver);
                edithBtn.click();
                CommonUtils.waitForInvisibilityOfElement(searchBtn,driver,8);
                switch (dffParameters.get("segmentType")) {
                    case "global":
                        createSegment1(driver);
                        CommonUtils.waitforElementtoClick(10, editSaveCloseBtn,driver);
                        editSaveCloseBtn.click();
                        CommonUtils.waitForInvisibilityOfElement(editSaveCloseBtn,driver,5);
                        CommonUtils.waitforElementtoClick(10,dffSearchField,driver);
                        break;
                    case "sensitive":
                        CommonUtils.waitforElementtoClick(10,manageContextBtn,driver);
                        manageContextBtn.click();
                        CommonUtils.waitForInvisibilityOfElement(manageContextBtn,driver,7);
                        CommonUtils.waitforElementtoClick(6,createhBtn,driver);
                        if (dffParameters.get("createContext").equalsIgnoreCase("Yes")) {
                            createContextCode(driver);
                            createSegment1(driver);
                            CommonUtils.waitforElementtoClick(6,savecloseBtn1,driver);
                            savecloseBtn1.click();
                            CommonUtils.waitForInvisibilityOfElement(createContextPageHeader,driver,8);
                        } else {
                            searchContextCode(driver);
                            CommonUtils.waitforElementtoClick(5,edithBtn,driver);
                            edithBtn.click();
                            CommonUtils.waitForInvisibilityOfElement(manageContextPageHeader,driver,8);
                            createSegment1(driver);
                            CommonUtils.waitforElementtoClick(7,editSaveCloseBtn,driver);
                            editSaveCloseBtn.click();
                            CommonUtils.hold(4);
                        }
                        CommonUtils.waitforElementtoClick(5,editSaveCloseBtn,driver);
                        editSaveCloseBtn.click();
                        CommonUtils.waitForInvisibilityOfElement(manageContextPageHeader,driver,8);
                        CommonUtils.waitforElementtoClick(5,editSaveCloseBtn,driver);
                        editSaveCloseBtn.click();
                        CommonUtils.waitForInvisibilityOfElement(editSaveCloseBtn,driver,5);
                        CommonUtils.waitforElementtoClick(5,dffSearchField,driver);
                        break;
                    default:
                        break;
                }
                // deployFlex();
            }
            dffParameters.clear();
        } catch (Exception e) {
            System.out.println("Create segment failed " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void searchContextCode(WebDriver driver) {
        contextCodeSearchField.clear();
        contextCodeSearchField.sendKeys(dffParameters.get("contextCodeNameSearch"));
        searchBtn.click();

        try {
            CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,5);
            CommonUtils.waitforElementtoVisible(8,driver.findElement(By.xpath("//span[contains(text(),'" + dffParameters.get("contextCodeNameSearch") + "')]")),driver);
//            CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text(),'" + dffParameters.get("contextCodeNameSearch") + "')]")), "Click", "", driver);
        } catch (Exception sContext) {
            System.out.println("Could not find specified context code : " + dffParameters.get("contextCodeNameSearch"));
            Assert.fail();
            sContext.getMessage();
        }
    }


    public void createContextCode(WebDriver driver) {
        try {
            createhBtn.click();
            CommonUtils.waitForInvisibilityOfElement(manageContextPageHeader,driver,10);
            CommonUtils.waitforElementtoClick(10,cDisplayNameInput,driver);
            cDisplayNameInput.sendKeys(dffParameters.get("contextDisplayName"));
            cContextCodeInput.click();
            CommonUtils.hold(7);
            cContextCodeInput.clear();
            cContextCodeInput.sendKeys(dffParameters.get("contextCodeName"));
            CommonUtils.hold(3);
            cAPINameInput.clear();
            cAPINameInput.sendKeys(dffParameters.get("contextAPIName"));
            cContextSaveBtn.click();
            CommonUtils.waitForInvisibilityOfElement(cDisplayNameInput,driver,10);
            CommonUtils.waitforElementtoVisible(5,driver.findElement(By.xpath("//span[contains(text(),'" + dffParameters.get("contextCodeName") + "')]")),driver);
            Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'" + dffParameters.get("contextCodeName") + "')]")).isDisplayed(), "Create contextCode successful : " + dffParameters.get("contextCodeName"));
            System.out.println("Created Context Segment Code Successfully : " + dffParameters.get("contextCodeName"));

        } catch (Exception contextCreate) {
            System.out.println("Create context operation failed. Please review the error message : " + contextCreate.getMessage());
            Assert.assertTrue(false, "Create contextCode operation failed : " + dffParameters.get("contextCodeName"));
            contextCreate.printStackTrace();
        }
    }

    public void createSegment1(WebDriver driver) {

        try {
            CommonUtils.waitForInvisibilityOfElement(edithBtn,driver,10);
            CommonUtils.waitforElementtoClick(10,createhBtn,driver);
//            CommonUtils.explicitWait(createhBtn, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver);
//            CommonUtils.hold(5); // DPK
            createhBtn.click();
            CommonUtils.waitForInvisibilityOfElement(createhBtn,driver,10);
            CommonUtils.waitforElementtoClick(10,glbSegNameInput,driver);
//            CommonUtils.explicitWait(glbSegNameInput, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver); //DPK
//            CommonUtils.hold(8);
            glbSegNameInput.clear();
            glbSegNameInput.sendKeys(dffParameters.get("dffName"));
            glbSegCodeInput.click();
            CommonUtils.hold(3);
            glbSegCodeInput.clear();
            glbSegCodeInput.sendKeys(dffParameters.get("dffCode"));
            glbSegAPIInput.clear();
            glbSegAPIInput.sendKeys(dffParameters.get("dffAPIName"));
            CommonUtils.hold(2);
            glbDataTypeDD.click();
            CommonUtils.waitforElementtoClick(10,driver.findElement(By.xpath("//option[@title='" + dffParameters.get("dataType") + "']")),driver);
//            CommonUtils.explicitWait(driver.findElement(By.xpath("//option[@title='" + dffParameters.get("dataType") + "']")), "Click", "", driver);
//            CommonUtils.hold(4);
            driver.findElement(By.xpath("//option[@title='" + dffParameters.get("dataType") + "']")).click();
//            CommonUtils.waitForInvisibilityOfElement(edithBtn,driver,5);
            if(dffParameters.containsKey("tableColumnValueIndex")) {
            	// To select table column value w.r.t the column index passed through parameter during segment creation. For USB Flex integration
            	CommonUtils.hold(3);
            			tableColumnLOVField.click();
            			CommonUtils.explicitWait(columnLOVList, "Visible", "", driver);
            			WebElement columnAttributeElement = getTableColumnElement(dffParameters.get("tableColumnValueIndex"),driver);
            			 String columnAttributeValue = columnAttributeElement.getText();
            			 columnAttributeElement.click();
            			Assert.assertTrue(isColumnValueSelected(columnAttributeValue,driver));
            	CommonUtils.hold(2);
            }else
            	checkTableColumnValue(driver);  //DPK
            
            glbValueSetInput.sendKeys(dffParameters.get("valueSetCode"));
//            CommonUtils.hold(3); DPK
            glbValueSetInput.sendKeys(Keys.TAB);
            checkDisplayTypeValue(driver);
//            CommonUtils.hold(8); //DPK
            glbPromptInput.clear();
            glbPromptInput.sendKeys(dffParameters.get("promtName"));
            glbDisplayTypeDD.click();
            CommonUtils.waitforElementtoClick(10,driver.findElement(By.xpath("//option[@title='" + dffParameters.get("displayType") + "']")),driver);
//            CommonUtils.explicitWait(driver.findElement(By.xpath("//option[@title='" + dffParameters.get("displayType") + "']")), "Click", "", driver);
//            CommonUtils.hold(3);
            driver.findElement(By.xpath("//option[@title='" + dffParameters.get("displayType") + "']")).click();
            CommonUtils.waitForInvisibilityOfElement(driver.findElement(By.xpath("//option[@title='" + dffParameters.get("displayType") + "']")),driver,10);
            CommonUtils.explicitWait(savecloseBtn1, "Click", "", driver);
            savecloseBtn1.click();
            CommonUtils.waitForInvisibilityOfElement(glbSegNameInput,driver,10);

            if (dffParameters.get("segmentType").equalsIgnoreCase("sensitive")) {
                CommonUtils.waitforElementtoVisible(10,driver.findElement(By.xpath("//input[@value='" + dffParameters.get("promtName") + "']")),driver);
                Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + dffParameters.get("promtName") + "']")).isDisplayed(), "Create sensitive segment operation success. Segment code : " + dffParameters.get("promtName"));
                System.out.println("Created Global Segment Successfully : " + dffParameters.get("promtName"));
            } else {
                CommonUtils.waitforElementtoVisible(10,driver.findElement(By.xpath("//span[contains(text(),'" + dffParameters.get("promtName") + "')]")),driver);
                Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'" + dffParameters.get("promtName") + "')]")).isDisplayed(), "Create global segment operation success. Segment code : " + dffParameters.get("promtName"));
                System.out.println("Created sensitive segment successfully : " + dffParameters.get("promtName"));
            }
            CommonUtils.hold(5);
        } catch (Exception createDff) {
            System.out.println("Create segment operation failed. Please review the error message. : " + createDff.getMessage());
            Assert.assertTrue(false, "Create segment operation failed. Segment code : " + dffParameters.get("promtName"));
            createDff.printStackTrace();
        }

    }


    public void checkTableColumnValue(WebDriver driver) {
        String value=tableColumnField.getAttribute("value");

        while(value.isEmpty()){
            try{
                CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,10);
                value=tableColumnField.getAttribute("value");
            }catch(Exception exp){
                value=tableColumnField.getAttribute("value");
            }
        }
    }

    public void checkDisplayTypeValue(WebDriver driver){
        String value=glbDisplayTypeDD.getAttribute("title");

        System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+value+"  Display Type1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        while(value.isEmpty()){
            try{
                CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,10);
                value=glbDisplayTypeDD.getAttribute("title");
            }catch(Exception exp){
                value=glbDisplayTypeDD.getAttribute("title");
            }
            System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+value+"  Display Type2<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    public void updateContextCode() {

    }

    public void deleteSement() {

    }

    public void updateSegment() {

    }

    public void navigateToHireAnEmployee(String legalEmployer,String empName, WebDriver driver){

        try{
            act = new Actions(driver);
            driver.navigate().to(GetDriverInstance.EnvironmentName.replace("fscmUI/faces/FuseWelcome", "hcmUI/faces/FndOverview?fndGlobalItemNodeId=itemNode_workforce_management_new_person"));
            CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,5);
            CommonUtils.waitforElementtoClick(40,hireEnEmployee,driver);
            hireEnEmployee.click();
            CommonUtils.waitForInvisibilityOfElement(hireEnEmployee,driver,25);
            CommonUtils.waitforElementtoClick(30, nextBtn,driver);
            CommonUtils.waitforElementtoClick(30,hireEnEmployeeSearchLink,driver);
            legalEmployeerInputField.clear();
            legalEmployeerInputField.sendKeys(legalEmployer);
            CommonUtils.hold(3);
            CommonUtils.waitforElementtoClick(30,legalEmployeeValue,driver);
            CommonUtils.hold(5);
            js = (JavascriptExecutor) driver;
            legalEmployeeValue.click();
            CommonUtils.waitForInvisibilityOfElement(legalEmployeeValue,driver,10);
            lastNameInputField.clear();
            lastNameInputField.click();
            js.executeScript("arguments[0].value='" + empName + "';", lastNameInputField);
            lastNameInputField.sendKeys(Keys.TAB);
            CommonUtils.hold(6);
        }catch(Exception exp){
            System.out.println("ERROR : Exception While Navigating to HireAnEmployee Page navigateToHireAnEmployee(): "+exp.getMessage());
            exp.printStackTrace();
        }

    }

    public void validateTableLayoutGlobalSegment(WebDriver driver, String... args) throws Exception {
        System.out.println("Length of the argument : " + args.length);
        for (int i = 0; i < args.length; i += 2) {
            dffParameters.put(args[i], args[i + 1]);
            System.out.println("Key -> " + args[i] + " && Value -> " + args[i + 1]);
        }
        js = (JavascriptExecutor) driver;
        switch (dffParameters.get("SegmentType")) {
            case "Global":
                switch (dffParameters.get("SegmentDataType")) {
                    case "Character":
                        if (dffParameters.get("ValidationType").equals("Length")) {

                            try {
                                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                                System.out.println("Performed page down Length");
                                CommonUtils.waitforElementtoClick(30,personCharGlobalSegment,driver);
                                personCharGlobalSegment.click();
                                personCharGlobalSegment.clear();
                                js.executeScript("arguments[0].value='" + dffParameters.get("Value") + "';",personCharGlobalSegment);
                                saveBtn.click();
                                CommonUtils.waitforElementtoClick(30,lengthErrorMessage,driver);
                                try {
                                    Assert.assertTrue(lengthErrorMessage.isDisplayed());
                                    System.out.println("Clicked OK BTN in Try");
                                } catch (Exception e) {
                                    System.out.println("Could Not Find Error Message For Length Validation");
                                    Assert.fail("Could Not Find Error Message For Length Validation");
                                    System.out.println("Clicked OK BTN in Catch");
                                }
                                okBtn.click();
                                CommonUtils.waitForInvisibilityOfElement(okBtn,driver,15);
                                personCharGlobalSegment.click();
                                personCharGlobalSegment.clear();

                            } catch (Exception e) {
                                System.out.println("Error in validating length validation " + e.getMessage());
                            }

                        } // End of Length validation
                        else if (dffParameters.get("ValidationType").equals("CaseSensitive")) {
                            try {
                                System.out.println("In Case block");
                                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                                System.out.println("Performed page down CaseSensitive");
                                CommonUtils.explicitWait(personCharGlobalSegment, "Click", "", driver);
                                CommonUtils.hold(2);
                                personCharGlobalSegment.click();
                                personCharGlobalSegment.clear();
                                js.executeScript("arguments[0].value='" + dffParameters.get("Value") + "';",personCharGlobalSegment);
                                saveBtn.click();
                                CommonUtils.waitforElementtoClick(30,upperCaseErrorMessage,driver);
                                CommonUtils.hold(4);
                                try {
                                    Assert.assertTrue(upperCaseErrorMessage.isDisplayed());
                                } catch (Exception e) {
                                    System.out.println("Could not find error message for case validation");
                                    Assert.fail("Could not find error message for case validation");

                                }
                                okBtn.click();
                                CommonUtils.waitForInvisibilityOfElement(okBtn,driver,10);
                                personCharGlobalSegment.click();
                                personCharGlobalSegment.clear();
                            } catch (Exception e) {
                                System.out.println("Error in validating case validation " + e.getMessage());
                            }

                        } // End of Case validation

                    case "Number":
                        if (dffParameters.get("ValidationType").equals("MinValue")) {// Start
                            // of
                            // MinVal
                            // validation
                            try {
                                CommonUtils.explicitWait(personNumGlobalSegment, "Click", "", driver);
                                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                                personNumGlobalSegment.click();
                                personNumGlobalSegment.clear();
                                js.executeScript("arguments[0].value='" + dffParameters.get("Value") + "';",personNumGlobalSegment);
                                // personNumGlobalSegment.sendKeys(dffParameters.get("Value"));
                                saveBtn.click();
                                CommonUtils.waitforElementtoClick(30,minValueErrorMessage,driver);
                                CommonUtils.hold(4);
                                try {
                                    Assert.assertTrue(minValueErrorMessage.isDisplayed());

                                } catch (Exception e) {
                                    System.out.println("Could bot find error message for MinValue validaiton");
                                    Assert.fail("Could not find error message for length validaiton");

                                }
                                okBtn.click();
                                CommonUtils.waitForInvisibilityOfElement(okBtn,driver,10);
                                personNumGlobalSegment.click();
                                personNumGlobalSegment.clear();
                            } catch (Exception e) {
                                System.out.println("Error in validating MinValue validation " + e.getMessage());
                            }
                        } // End of MinVal validation
                        else if (dffParameters.get("ValidationType").equals("MaxValue")) {// Start
                            // of
                            // MaxVal
                            // validation
                            try {
                                CommonUtils.explicitWait(personNumGlobalSegment, "Click", "", driver);
                                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                                personNumGlobalSegment.click();
                                personNumGlobalSegment.clear();
                                js.executeScript("arguments[0].value='" + dffParameters.get("Value") + "';",personNumGlobalSegment);
                                saveBtn.click();
                                CommonUtils.waitforElementtoClick(30,maxErrorMessage,driver);
                                CommonUtils.hold(4);
                                try {
                                    Assert.assertTrue(maxErrorMessage.isDisplayed());
                                } catch (Exception e) {
                                    System.out.println("Could bot find error message for MaxValue validation");
                                    Assert.fail("Could not find error message for length validation");
                                }
                                okBtn.click();
                                CommonUtils.waitForInvisibilityOfElement(okBtn,driver,10);
                                personNumGlobalSegment.click();
                                personNumGlobalSegment.clear();
                            } catch (Exception e) {
                                System.out.println("Error in validating MaxValue validation " + e.getMessage());
                            }
                        } // End of MaxVal validation
                    case "Date":
                        try {

                        } catch (Exception e) {
                            System.out.println("Error in validating date format validation " + e.getMessage());
                        }

                }
        }

        dffParameters.clear();

    }

    public void validateContextSwitchInFormLayout(WebDriver driver) throws Exception {
        js = (JavascriptExecutor) driver;
        for (int i = 0; i < 2; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            CommonUtils.hold(3);
            CommonUtils.waitforElementtoClick(30,personcontextDropDown,driver);
            personcontextDropDown.click();
            CommonUtils.waitforElementtoClick(30,dffContextSegment1,driver);
            dffContextSegment1.click();
            CommonUtils.waitForInvisibilityOfElement(dffContextSegment1,driver,30);
            CommonUtils.waitforElementtoClick(30,personCharIndSensitiveSegmentDropDown,driver);
            try {
                Assert.assertTrue(personCharIndSensitiveSegmentDropDown.isDisplayed());
                Assert.assertTrue(personCharDepSensitiveSegmentSearchBtn.isDisplayed());
                Assert.assertTrue(personNumIndependentSensitiveSegment.isDisplayed());
                Assert.assertTrue(personNumDepSensitiveSegment.isDisplayed());
            } catch (Exception e) {
                Assert.fail();
                System.out.println("Context Switch operation failed. Please check the error");
                System.out.println(e.getMessage());
            }

            try {
                personcontextDropDown.click();
                CommonUtils.waitforElementtoClick(30,dffContextSegment2,driver);
                dffContextSegment2.click();
                CommonUtils.waitForInvisibilityOfElement(dffContextSegment2,driver,15);
                CommonUtils.waitforElementtoClick(30,dffContextSegment21,driver);
                Assert.assertTrue(dffContextSegment21.isDisplayed());

            } catch (Exception e) {
                Assert.fail();
                System.out.println("2 context switch operation failed. Please check the error");
                System.out.println(e.getMessage());
            }

        }
        CommonUtils.hold(2);
    }// End of context switch validation

    public void validateCharSegmentValues(WebDriver driver, String... args) throws Exception {
        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        System.out.println("Length of the argument : " + args.length);
        for (int i = 0; i < args.length; i += 2) {
            dffParameters.put(args[i], args[i + 1]);
            System.out.println("Key -> " + args[i] + " && Value -> " + args[i + 1]);
        }

        int totalValues = Integer.valueOf(dffParameters.get("NumberofValues"));
        System.out.println("Total Values " + totalValues);
        for (int x = 1; x <= totalValues; x++) {
            CommonUtils.waitforElementtoClick(30,personcontextDropDown,driver);
            personcontextDropDown.click();
            CommonUtils.waitforElementtoClick(30,dffContextSegment1,driver);
            dffContextSegment1.click();
            CommonUtils.waitForInvisibilityOfElement(dffContextSegment1,driver,10);
            CommonUtils.waitforElementtoClick(30,personCharIndSensitiveSegmentDropDown,driver);
            personCharIndSensitiveSegmentDropDown.click();

            if(dffParameters.get("ParentValue" + x).equalsIgnoreCase("AOL")){
                CommonUtils.waitforElementtoClick(30, indCharSegmentAolValue,driver);
                indCharSegmentAolValue.click();
                CommonUtils.waitForInvisibilityOfElement(indCharSegmentAolValue,driver,10);
            }else{
                CommonUtils.waitforElementtoClick(30, indCharSegmentFlexValue,driver);
                indCharSegmentFlexValue.click();
                CommonUtils.waitForInvisibilityOfElement(indCharSegmentFlexValue,driver,10);
            }

            CommonUtils.waitforElementtoClick(30,personCharDepSensitiveSegmentSearchBtn,driver);
            personCharDepSensitiveSegmentSearchBtn.click();
            CommonUtils.waitforElementtoClick(30,depCharSearchLink,driver);
            CommonUtils.hold(3);

            try {
                if (personCharDepSensitiveSegment.getAttribute("value") == null) {
                    Assert.assertTrue(true);
                    System.out.println("=======> Found NULL Value");
                } else if (personCharDepSensitiveSegment.getAttribute("value").equals("")) {
                    Assert.assertTrue(true);
                    System.out.println("=======> Found Empty Value");
                }
//                Assert.assertTrue(driver
//                        .findElement(By.xpath("//span[text()='" + dffParameters.get("ChildValue" + x) + "']"))
//                        .isDisplayed());
//                Assert.assertTrue(driver
//                        .findElement(By.xpath("//span[text()='" + dffParameters.get("ChildValue" + x + x) + "']"))
//                        .isDisplayed());
//
//                boolean checkElementPresence = false;
//                try {
//                    checkElementPresence = depCharSegmentAol1ValueSpan.isDisplayed();
//                    checkElementPresence = true;
//                } catch (Exception exp) {
//                    System.out.println("Displaying Elements Based on td TAG.");
//                }

//                if (checkElementPresence) {
                if (dffParameters.get("ParentValue" + x).equalsIgnoreCase("AOL")) {
                    Assert.assertTrue(depCharSegmentAol1ValueSpan.isDisplayed());
                    Assert.assertTrue(depCharSegmentAol2ValueSpan.isDisplayed());
                    depCharSegmentAol1ValueSpan.click();
//                       CommonUtils.waitForInvisibilityOfElement(depCharSegmentAol1Value,driver,10);
                } else {
                    Assert.assertTrue(depCharSegmentFlex1ValueSpan.isDisplayed());
                    Assert.assertTrue(depCharSegmentFlex2ValueSpan.isDisplayed());
                    depCharSegmentFlex1ValueSpan.click();
//                    CommonUtils.waitForInvisibilityOfElement(depCharSegmentFlex1Value,driver,10);
                }
//                } else {
//                    if (dffParameters.get("ParentValue" + x).equalsIgnoreCase("AOL")) {
//                        Assert.assertTrue(depCharSegmentAol1Value.isDisplayed());
//                        Assert.assertTrue(depCharSegmentAol2Value.isDisplayed());
//                        depCharSegmentAol1Value.click();
//                       CommonUtils.waitForInvisibilityOfElement(depCharSegmentAol1Value,driver,10);
//                    } else {
//                        Assert.assertTrue(depCharSegmentFlex1Value.isDisplayed());
//                        Assert.assertTrue(depCharSegmentFlex2Value.isDisplayed());
//                        depCharSegmentFlex1Value.click();
////                      CommonUtils.waitForInvisibilityOfElement(depCharSegmentFlex1Value,driver,10);
//                    }
//                }
                CommonUtils.waitForInvisibilityOfElement(depCharSearchLink, driver, 10);

            } catch (Exception e) {
                System.out.println("Context Sensitive Segment Value Validation Failed. Please Check The Error");
                System.out.println(e.getMessage());
            }

            try {
                saveBtn.click();
                CommonUtils.waitforElementtoClick(40,confirmOKBtn1,driver);
                CommonUtils.hold(5);
                confirmOKBtn1.click();
                CommonUtils.waitForInvisibilityOfElement(confirmOKBtn1,driver,15);
                CommonUtils.waitforElementtoClick(30,personcontextDropDown,driver);
                CommonUtils.hold(5);
                Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + dffParameters.get("ChildValue" + x) + "']")).isDisplayed());
            } catch (Exception e) {
                Assert.fail(e.getMessage());
                System.out.println("Exception while validating segment values : " + e.getMessage());
                e.printStackTrace();
            }

        }
        dffParameters.clear();
    }

    public void validateValidValues(WebDriver driver, String... args) throws Exception {

        for (int i = 0; i < args.length; i += 2) {
            dffParameters.put(args[i], args[i + 1]);
            System.out.println("Key -> " + args[i] + " && Value -> " + args[i + 1]);
        }

        try {
            js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            CommonUtils.hold(3);
            personCharGlobalSegment.click();
            personCharGlobalSegment.clear();
            js.executeScript("arguments[0].value='" + dffParameters.get("CharGlobalValue") + "';", personCharGlobalSegment);
            personNumGlobalSegment.click();
            personNumGlobalSegment.clear();
            js.executeScript("arguments[0].value='" + dffParameters.get("NumGlobalValue") + "';", personNumGlobalSegment);
            CommonUtils.waitforElementtoClick(30, globalDateSegmentIcon, driver);
            globalDateSegmentIcon.click();
            CommonUtils.waitforElementtoClick(30, currentDate, driver);
            currentDate.click();
            CommonUtils.waitForInvisibilityOfElement(currentDate, driver, 10);
            CommonUtils.waitforElementtoClick(30, personcontextDropDown, driver);
            personcontextDropDown.click();
            CommonUtils.waitforElementtoClick(30, dffContextSegment1, driver);
            dffContextSegment1.click();
            CommonUtils.waitForInvisibilityOfElement(dffContextSegment1, driver, 10);

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            CommonUtils.hold(2);
            CommonUtils.waitforElementtoClick(30, personCharIndSensitiveSegmentDropDown, driver);
            personCharIndSensitiveSegmentDropDown.click();
            CommonUtils.hold(3);
            CommonUtils.waitforElementtoClick(30, indCharSegmentAolValue, driver);
            indCharSegmentAolValue.click();
            CommonUtils.waitForInvisibilityOfElement(indCharSegmentAolValue, driver, 10);
            personCharDepSensitiveSegment.click();
            personCharDepSensitiveSegment.clear();
            js.executeScript("arguments[0].value='" + dffParameters.get("ChildValue1") + "';", personCharDepSensitiveSegment);
            personCharSubSensitiveSegment.click();
            personCharSubSensitiveSegment.clear();
            js.executeScript("arguments[0].value='" + dffParameters.get("ParentValue1") + "';", personCharSubSensitiveSegment);
            personTableSensitiveSegment.click();
            personTableSensitiveSegment.clear();
            js.executeScript("arguments[0].value='" + dffParameters.get("TableValue") + "';", personTableSensitiveSegment);
            CommonUtils.waitforElementtoClick(30, personNumIndependentSensitiveSegmentDropDown, driver);
            personNumIndependentSensitiveSegmentDropDown.click();
            CommonUtils.waitforElementtoClick(30, indCharSegment10Value, driver);
            indCharSegment10Value.click();
            CommonUtils.waitForInvisibilityOfElement(indCharSegment10Value, driver, 10);
            personNumDepSensitiveSegment.click();
            personNumDepSensitiveSegment.clear();
            js.executeScript("arguments[0].value='" + dffParameters.get("ChildValue2") + "';", personNumDepSensitiveSegment);
            CommonUtils.hold(2);
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(personCharSubSensitiveSegment, driver, 40);
            CommonUtils.waitforElementtoVisible(30, pageTitle2, driver);
            CommonUtils.waitforElementtoClick(30, nextBtn, driver);
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(pageTitle2, driver, 40);
            CommonUtils.waitforElementtoVisible(30, pageTitle3, driver);
            CommonUtils.waitforElementtoVisible(30, businessUnitIdInput, driver);
            businessUnitIdInput.click();
            businessUnitIdInput.clear();
            businessUnitIdInput.sendKeys("Vision India");
            CommonUtils.hold(4);
            CommonUtils.waitforElementtoClick(30, bussinessInputValue, driver);
            bussinessInputValue.click();
            CommonUtils.waitForInvisibilityOfElement(bussinessInputValue, driver, 15);
            CommonUtils.waitforElementtoClick(30, nextBtn, driver);
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(pageTitle3, driver, 40);
            CommonUtils.waitforElementtoVisible(30, pageTitle4, driver);
            CommonUtils.waitforElementtoClick(30, nextBtn, driver);
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(pageTitle4, driver, 50);
            CommonUtils.waitforElementtoClick(30, pageTitle5, driver);

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("CharGlobalValue") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("NumGlobalValue") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("ParentValue1") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("ChildValue1") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("ParentValue2") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("ChildValue2") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("TableValue") + "']")).isDisplayed());

            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + dffParameters.get("SubValue") + "']")).isDisplayed());

            CommonUtils.hold(5);
            CommonUtils.explicitWait(submitBtn, "Click", "", driver);
            submitBtn.click();
            CommonUtils.waitforElementtoClick(30, warningOKBtn, driver);
            CommonUtils.hold(4);
            warningOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(warningOKBtn, driver, 60);
            CommonUtils.waitforElementtoClick(40, confirmOKBtn1, driver);
            confirmOKBtn1.click();
            CommonUtils.waitForInvisibilityOfElement(confirmOKBtn1, driver, 60);
            CommonUtils.waitforElementtoClick(60, hireEnEmployee, driver);
            CommonUtils.hold(5);
        } catch (Exception exp) {
            Assert.fail(exp.getMessage());
            exp.printStackTrace();
        }

    }

    public void completeFlow(WebDriver driver) throws Exception {

        //try {
            WebElement element;
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(lastNameInputField, driver, 40);
            CommonUtils.hold(5);
            element = driver.findElement(By.xpath("//h1[contains(text(),'Hire an Employee: Person Information')]"));
            CommonUtils.explicitWait(element, "Visible", "", driver);
            CommonUtils.explicitWait(nextBtn, "Click", "", driver);
            nextBtn.click();
            //CommonUtils.waitForInvisibilityOfElement(element, driver, 30);
            //CommonUtils.hold(8);
            waitForElementToAppear(driver, HireAnEmp_Page3);
            element = driver.findElement(By.xpath("//h1[contains(text(),'Hire an Employee: Employment Information')]"));
            //CommonUtils.explicitWait(element, "Visible", "", driver);
            CommonUtils.explicitWait(businessUnitIdInput, "Click", "", driver);
            businessUnitIdInput.click();
            businessUnitIdInput.clear();
            businessUnitLov.click();
            //CommonUtils.hold(11);
            waitForElementToAppear(driver, businessUnitLovValue);
           // CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@id,'businessUnitId::dropdownPopup::dropDownContent::db')]/table/tbody/tr[2]/td")), "Click", "", driver);
           // element = driver.findElement(By.xpath("//div[contains(@id,'businessUnitId::dropdownPopup::dropDownContent::db')]/table/tbody/tr[2]/td"));
            businessUnitLovValue.click();
            CommonUtils.hold(5);
            CommonUtils.waitForInvisibilityOfElement(businessUnitLovValue, driver, 25);
            CommonUtils.explicitWait(nextBtn, "Click", "", driver);
            CommonUtils.scrollToElement(nextBtn, driver);
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(HireAnEmp_Page3, driver, 30);
            //CommonUtils.hold(10);
            waitForElementToAppear(driver, HireAnEmp_Page4);
            //element = driver.findElement(By.xpath("//h1[contains(text(),'Hire an Employee: Compensation and Other Information')]"));
            //CommonUtils.explicitWait(element, "Visible", "", driver);
            CommonUtils.explicitWait(nextBtn, "Click", "", driver);
            CommonUtils.hold(4);
            nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(HireAnEmp_Page4, driver, 50);
            //CommonUtils.hold(15);
            //CommonUtils.hold(15);
            waitForElementToAppear(driver, HireAnEmp_Page5);
            //element = driver.findElement(By.xpath("//h1[contains(text(),'Hire an Employee: Review')]"));
            //CommonUtils.explicitWait(element, "Visible", "", driver);
        /*} catch (Exception e) {
            System.out.println("Error while completing the flow in method completeFlow(WebDriver driver) : " + e.getMessage());
            Assert.assertTrue(false, "Error while completing the flow in method completeFlow(WebDriver driver) : " + e.getMessage());
            e.printStackTrace();
        }*/

    }


    public String deleteGlobalSegmentSQL(String flexName, String flexId, String user) {
        String plsqltoDeletGlobalSegment = " declare\r\n" + " l_segment_code FND_DF_SEGMENTS_B.segment_code%type;\r\n"
                + " cursor flex is\r\n" + " select segment_code from FND_DF_SEGMENTS_B\r\n"
                + " where descriptive_flexfield_code = '" + flexName + "'\r\n"
                + " and context_code = 'Global Data Elements'\r\n" + " and created_by = '" + user + "'\r\n" + "or last_UPDATED_by='"+user+"';\r\n"
                + "\r\n" + " begin\r\n" + " open flex;\r\n" + " loop\r\n" + " fetch flex into l_segment_code;\r\n"
                + " exit when flex%notfound;\r\n" + "\r\n" + " FND_FLEX_DF_SETUP_APIS.delete_global_segment(\r\n"
                + " p_application_id 				 => " + flexId + ",\r\n" + " p_descriptive_flexfield_code   => '"
                + flexName + "',\r\n" + " p_segment_code                 => l_segment_code);\r\n"
                + " dbms_output.put_line('Deleted segment ' ||l_segment_code);\r\n" + " end loop;\r\n"
                + " close flex;\r\n" + " commit;\r\n" + " end;";
        return plsqltoDeletGlobalSegment;
    }

    public String deleteContextCode(String flexName, String flexId, String user) {
        String plsqlDeleteContext = "declare\r\n" + "l_segment_code FND_DF_SEGMENTS_B.segment_code%type;\r\n"
                + "cursor flex is\r\n" + "select context_code from fnd_df_contexts_b\r\n"
                + "where descriptive_flexfield_code = '" + flexName + "'\r\n"
                + "and created_by = '" + user + "'\r\n" + "or last_UPDATED_by='"+user+"';\r\n"
                + "begin\r\n" + "open flex;\r\n" + "loop\r\n" + "fetch flex into l_segment_code;\r\n"
                + "exit when flex%notfound;\r\n" + "\r\n" + "FND_FLEX_DF_SETUP_APIS.delete_context(\r\n"
                + "p_application_id               => " + flexId + ",\r\n" + "p_descriptive_flexfield_code   => '"
                + flexName + "',\r\n" + "p_context_code                => l_segment_code);\r\n"
                + "dbms_output.put_line('Deleted contextCode ' ||l_segment_code);\r\n" + "end loop;\r\n"
                + "close flex;\r\n" + "commit;\r\n" + "end;";
        return plsqlDeleteContext;
    }


    public String deleteSegmentLabel(String flexName, String flexId, String user) {
        String plsqlDeleteContext = "declare\r\n" + "l_segment_code FND_DF_SEGMENTS_B.segment_code%type;\r\n"
                + "cursor flex is\r\n" + "select segment_label_code from fnd_df_segment_labels_b\r\n"
                + "where descriptive_flexfield_code = '" + flexName + "'\r\n"
                + "and created_by = '" + user + "';\r\n"
                + "begin\r\n" + "open flex;\r\n" + "loop\r\n" + "fetch flex into l_segment_code;\r\n"
                + "exit when flex%notfound;\r\n" + "\r\n" + "FND_FLEX_DF_SETUP_APIS.delete_segment_label(\r\n"
                + "p_application_id    => " + flexId + ",\r\n" + "p_descriptive_flexfield_code   => '"
                + flexName + "',\r\n" + "p_segment_label_code  => l_segment_code);\r\n"
                + "dbms_output.put_line('Deleted contextCode ' ||l_segment_code);\r\n" + "end loop;\r\n"
                + "close flex;\r\n" + "commit;\r\n" + "end;";
//		System.out.println(plsqlDeleteContext);

        return plsqlDeleteContext;
    }


    public void goToSegmentLabel(String flexCode, WebDriver driver) {

        try {
            navigateToDFFtf(driver);
            searchDFFCode(flexCode, driver);
            CommonUtils.hold(3);
            CommonUtils.explicitWait(edithBtn, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver);
            edithBtn.click();
            CommonUtils.explicitWait(_labelButton, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver);
            CommonUtils.hold(3);
            _labelButton.click();
            CommonUtils.explicitWait(_labelTableSummary, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver);
            CommonUtils.hold(3);
        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while navigating to labels page : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }


    public void getSegmentLabel(String labelName, WebDriver driver, int size) {

        try {
            System.out.println("INFO : Total number of labels : " + driver.findElements(By.xpath("//span[contains(text(),'" + labelName + "')]")).size());
            Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'" + labelName + "')]")).size() == size, "Total labels displayed is :" + driver.findElements(By.xpath("//span[contains(text(),'" + labelName + "')]")).size());
        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while getting label details : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }


    public void goToContextSegmentDetails(String flexCode, String contextCode, WebDriver driver) {
        Actions act = new Actions(driver);
        try {
            navigateToDFFtf(driver);
            searchDFFCode(flexCode, driver);
            CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(edithBtn),driver);
            edithBtn.click();
            CommonUtils.waitForInvisibilityOfElement(edithBtn,driver,8);
            CommonUtils.waitExplicitly(8,4, ExpectedConditions.elementToBeClickable(_contextInput),driver);

            if (_contextInput.getAttribute("value").contains(contextCode)) {
                System.out.println("INFO : Context Pre-Selected With Value : " + contextCode);
            } else {
                _contextInput.clear();
                act.moveToElement(_contextInput).sendKeys(contextCode).sendKeys(Keys.TAB).build().perform();
                CommonUtils.hold(5);
            }
//
//
//
//
//
//			CommonUtils.explicitWait(edithBtn, CommonUtils.ExplicitWaitCalls.Click.toString(),	"",driver);
//			edithBtn.click();
//			CommonUtils.explicitWait(_contextSearchLov, CommonUtils.ExplicitWaitCalls.Click.toString(),	"",driver);
//			CommonUtils.hold(3);
//			_contextSearchLov.click();
//			CommonUtils.explicitWait(_contextSearchLink, CommonUtils.ExplicitWaitCalls.Click.toString(),	"",driver);
//			CommonUtils.hold(2);
//			_contextSearchLink.click();
//			CommonUtils.explicitWait(_contextDisplayNameInput, CommonUtils.ExplicitWaitCalls.Click.toString(),	"",driver);
//			_contextDisplayNameInput.clear();
//			_contextDisplayNameInput.sendKeys(contextCode);
//			_contextDisplayNameSearchBtn.click();
//			CommonUtils.explicitWait(_contextDisplayNameSearchResults, CommonUtils.ExplicitWaitCalls.Click.toString(),	"",driver); // Hard coded value for timebeing
//			CommonUtils.hold(5);
//			_contextDisplayNameSearchResults.click();
//			CommonUtils.hold(2);
//			_contextOKButton.click();
//			CommonUtils.hold(4);


        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while navigating to labels page : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }

    public void enableContextSegmentField(WebDriver driver, String flexCode) {
        try {
            navigateToDFFtf(driver);
            searchDFFCode(flexCode, driver);
            CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(edithBtn),driver);
            edithBtn.click();
            CommonUtils.waitForInvisibilityOfElement(edithBtn,driver,8);
            CommonUtils.waitExplicitly(8,4, ExpectedConditions.elementToBeClickable(contextDisplayTypeDD),driver);
            CommonUtils.selectDropDownElement(contextDisplayTypeDD, "List of Values");
            CommonUtils.waitExplicitly(8,4, ExpectedConditions.elementToBeClickable(editSaveCloseBtn),driver);
            editSaveCloseBtn.click();
            CommonUtils.waitForInvisibilityOfElement(contextDisplayTypeDD,driver,8);

        } catch (Exception exp) {
            System.out.println("ERROR : Error while enabling context code : " + exp.getMessage());
            exp.printStackTrace();
        }
    }

    public void getContextSegmentDetails(String segmentName, WebDriver driver, int size) {

        try {
            System.out.println("INFO : Total number of context segments : " + driver.findElements(By.xpath("//span[contains(text(),'" + segmentName + "')]")).size());
            Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'" + segmentName + "')]")).size() == size, "Total labels displayed is :" + driver.findElements(By.xpath("//span[contains(text(),'" + segmentName + "')]")).size());
        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while getting label details : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }


    public void goToGlobalegmentDetails(String flexCode, String globalSegment, WebDriver driver) {

        try {
            js = (JavascriptExecutor) driver;
            navigateToDFFtf(driver);
            searchDFFCode(flexCode, driver);
            CommonUtils.hold(3);
            CommonUtils.explicitWait(edithBtn, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver);
            edithBtn.click();
            CommonUtils.explicitWait(_desceningOrder, "Click", "", driver);
            CommonUtils.hold(2);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", _desceningOrder);
            CommonUtils.hold(5);
            System.out.println("INFO : Total number of global segments : " + driver.findElements(By.xpath("//span[contains(text(),'" + globalSegment + "')]")).size());
            Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'" + globalSegment + "')]")).size() == 3);

        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while navigating to global segment page : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }


    public void goToContextPage(String flexCode, WebDriver driver) {

        try {
            navigateToDFFtf(driver);
            searchDFFCode(flexCode, driver);
            CommonUtils.waitForElement(edithBtn,10,5,driver);
            edithBtn.click();
            CommonUtils.waitForElement(manageContextBtn,15,7,driver);
            manageContextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(manageContextBtn,driver,10);
            CommonUtils.waitForElement(searchBtn,15,7,driver);
            searchBtn.click();
            CommonUtils.hold(3);
        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while navigating to labels page : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }

    public void getContextCode(String contextName, WebDriver driver, int size) {

        try {
            Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'" + contextName + "')]")).size() == size, "Total labels displayed is :" + driver.findElements(By.xpath("//span[contains(text(),'" + contextName + "')]")).size());
            System.out.println("INFO : Number of CONTEXTS : " + driver.findElements(By.xpath("//span[contains(text(),'" + contextName + "')]")).size());
            Assert.assertTrue(driver.findElements(By.xpath("//span[text()='Yes']")).size() == size, "Total labels displayed is :" + driver.findElements(By.xpath("//span[contains(text(),'" + contextName + "')]")).size());
            System.out.println("INFO : Number of CONTEXT UPDATES : " + driver.findElements(By.xpath("//span[text()='Yes']")).size());
            Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'Updated enabled flag from')]")).size() == size);
            Assert.assertTrue(driver.findElement(By.xpath("//span[text()='CC_NAME2_UPDATE']")).isDisplayed());
        } catch (Exception labelEx) {
            System.out.println("ERROR : Error while getting context details : " + labelEx.getMessage());
            labelEx.printStackTrace();
        }

    }


    public void deployFlex(String flexName, WebDriver driver) throws Exception {
        navigateToDFFtf(driver);
        searchDFFCode(flexName, driver);

        try {
            deployFlexBtn.click();
            CommonUtils.waitforElementtoClick(30,confirmOKBtn,driver);
            while (!confirmOKBtn.isEnabled()) {
                CommonUtils.hold(3);
            }

            String deploymentText[] = deployFlexPopUp.getText().split(":");
            System.out.println("Flex deployment status : " + Arrays.toString(deploymentText));
            confirmOKBtn.click();
            CommonUtils.hold(5);
            CommonUtils.explicitWait(dffSearchField, CommonUtils.ExplicitWaitCalls.Click.toString(),
                    "", driver);

            switch (deploymentText[1].trim()) {
                case "Confirmation":
                    System.out.println("Deployed Flex field successfully : " + dffParameters.get("dffFlexCode"));
                    Assert.assertTrue(true, "FlexField Deployment Successful : " + dffParameters.get("dffFlexCode"));
                    break;
                case "Error":
                    System.out.println("Deploy Flex field failed. Please review the error message " + dffParameters.get("dffFlexCode"));
                    Assert.assertTrue(false, "FlexField deployment failed : " + dffParameters.get("dffFlexCode"));
                    break;
                default:
                    break;

            }

        } catch (Exception e) {
            System.out.println("Deployment of Flex :" + flexName + " failed. Please review the error message." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void navigateToJournalsPage(String batchName, WebDriver driver){

        try {
            driver.get(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FndOverview?fndGlobalItemNodeId=itemNode_general_accounting_journals"));
            CommonUtils.hold(4);
            CommonUtils.waitforElementtoClick(30, taskBtn, driver);
            taskBtn.click();
            CommonUtils.waitforElementtoClick(40, createJournalsLink, driver);
            createJournalsLink.click();
            CommonUtils.waitForInvisibilityOfElement(createJournalsLink, driver, 60);
            CommonUtils.hold(10);
            CommonUtils.waitforElementtoClick(40, deleteBtn, driver);
            deleteBtn.click();
            CommonUtils.waitForInvisibilityOfElement(debitAmountInput, driver, 15);
            CommonUtils.waitforElementtoClick(30, deleteConfirmBtn1, driver);
            deleteConfirmBtn1.click();
            CommonUtils.waitForInvisibilityOfElement(deleteConfirmBtn1, driver, 15);
            CommonUtils.waitforElementtoClick(40, deleteBtn, driver);
            deleteBtn.click();
            CommonUtils.waitForInvisibilityOfElement(debitAmountInput, driver, 15);
            CommonUtils.waitforElementtoClick(30, deleteConfirmBtn1, driver);
            deleteConfirmBtn1.click();
            CommonUtils.waitForInvisibilityOfElement(deleteConfirmBtn1, driver, 15);
            CommonUtils.waitforElementtoClick(30, jounalBatchInputField, driver);
            jounalBatchInputField.clear();
            jounalBatchInputField.sendKeys(batchName);
            journalInputField.clear();
            journalInputField.sendKeys(batchName);
//
//            try {
//                categoryLov.click();
//                CommonUtils.hold(3);
//                CommonUtils.explicitWait(accrualText, "Click", "", driver);
//                accrualText.click();
//                CommonUtils.hold(3);
//            } catch (Exception e) {
////            System.out.println("Category now displaying as Client LOV.");
            categoryClientLov.clear();
            categoryClientLov.sendKeys("Accrual");
            CommonUtils.hold(3);
            CommonUtils.waitforElementtoClick(30, accrualText, driver);
            accrualText.click();
            CommonUtils.waitForInvisibilityOfElement(accrualText, driver, 15);
//            }
            createBtn.click();
            CommonUtils.waitforElementtoClick(30, kffLink, driver);
            debitAmountInput.sendKeys("100");
            kffLink.click();
            CommonUtils.waitforElementtoClick(30, kffsearchBtn, driver);
            kffsearchBtn.click();
            CommonUtils.waitforElementtoClick(30, kffFirstRow, driver);
            kffFirstRow.click();
            CommonUtils.hold(5);
            CommonUtils.waitforElementtoClick(30, kffOKBtn, driver);
            kffOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(kffOKBtn, driver, 15);
            CommonUtils.waitforElementtoClick(30, kffInputValue, driver);
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Navigating To Journals Page. " + exp.getMessage());
            Assert.fail();
            exp.printStackTrace();
        }

    }

    public void validateTableLayOutSegmentLength(WebDriver driver) {

        try {
            journalGlobalSegment.clear();
            journalGlobalSegment.sendKeys("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            saveBtn.click();
            CommonUtils.waitforElementtoClick(30, jMaxLenthErrorMessage, driver);
            Assert.assertTrue(jMaxLenthErrorMessage.isDisplayed());
        } catch (Exception e) {
            System.out.println("Could bot find error message for length validation");
            Assert.fail("Could not find error message for length validation");
        }
        journalGlobalSegment.clear();
    }

    public void validateTableLayOutCaseSensitive(WebDriver driver) {

        try {
            journalGlobalSegment.clear();
            journalGlobalSegment.sendKeys("aasas");
            saveBtn.click();
            CommonUtils.waitforElementtoClick(30, jUppserCaseErrorMessage, driver);
            Assert.assertTrue(jUppserCaseErrorMessage.isDisplayed());
        } catch (Exception e) {
            System.out.println("Could not find error message for case validation");
            Assert.fail("Could not find error message for case validation");

        }
        journalGlobalSegment.clear();
    }

    public void validateTableContextSwitch(WebDriver driver){
        try{
            CommonUtils.waitforElementtoClick(30,expandBtn,driver);
            expandBtn.click();
            CommonUtils.waitForInvisibilityOfElement(expandBtn,driver,15);
            CommonUtils.waitforElementtoClick(30,contextSegmentCode,driver);

            js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            Actions act = new Actions(driver);

            act.moveToElement(contextSegmentCode).keyDown(Keys.ALT).sendKeys(Keys.ARROW_DOWN).build().perform();
            act.perform();
            CommonUtils.waitforElementtoClick(30,jDffContextSegmentCode,driver);
            jDffContextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(jDffContextSegmentCode,driver,10);
            CommonUtils.waitforElementtoClick(30,jContextSensitiveSegment,driver);
            Assert.assertTrue(jContextSensitiveSegment.isDisplayed());

            act.moveToElement(contextSegmentCode).keyDown(Keys.ALT).sendKeys(Keys.ARROW_DOWN).build().perform();
            act.perform();
            CommonUtils.waitforElementtoClick(30,jSedDffContextSegmentCode,driver);
            jSedDffContextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(jSedDffContextSegmentCode,driver,10);
            CommonUtils.waitforElementtoClick(30,jContextSensitiveSegmentSeed,driver);
            Assert.assertTrue(jContextSensitiveSegmentSeed.isDisplayed());
        }catch(Exception exp){
            Assert.fail("ERROR : Exception While Changing ContextSegment Test. "+exp.getMessage());
            exp.printStackTrace();
        }

    }

    public void validateTableSegmentValues(WebDriver driver, String... args) throws Exception {

        for (int i = 0; i < args.length; i += 2) {
            dffParameters.put(args[i], args[i + 1]);
            System.out.println("Key -> " + args[i] + " && Value -> " + args[i + 1]);
        }

        js = (JavascriptExecutor) driver;

        try {
            CommonUtils.waitforElementtoClick(30, contextSegmentCode, driver);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            System.out.println("Scrolled to bottom of the page");

            Actions act = new Actions(driver);

            act.moveToElement(contextSegmentCode).keyDown(Keys.ALT).sendKeys(Keys.ARROW_DOWN).build().perform();
            act.perform();

            CommonUtils.waitforElementtoClick(30, jDffContextSegmentCode, driver);
            jDffContextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(jDffContextSegmentCode, driver, 10);
            CommonUtils.waitforElementtoClick(30, jContextSensitiveSegment, driver);

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            System.out.println("Scrolled to bottom of the page");

            CommonUtils.waitforElementtoClick(30, journalIndSensitiveSegment, driver);
            CommonUtils.waitforElementtoClick(30, journalDepSensitiveSegmentLov, driver);
            CommonUtils.waitforElementtoClick(30, journalDepSensitiveSegmentinput, driver);
            CommonUtils.waitforElementtoClick(30, journalSubSensitiveSegmentLOV, driver);


            int totalValues = Integer.valueOf(dffParameters.get("NumberofValues"));
            System.out.println("Total Values " + totalValues);

            for (int x = 1; x <= totalValues; x++) {
                act.moveToElement(journalIndSensitiveSegment).keyDown(Keys.ALT).sendKeys(Keys.ARROW_DOWN).build().perform();
                act.perform();
                System.out.println("Clicked on ARROW-DOWN");


                if (dffParameters.get("ParentValue" + x).equalsIgnoreCase("AOL")) {
                    CommonUtils.waitforElementtoClick(30, jIndependentAolValue, driver);
                    jIndependentAolValue.click();
                    CommonUtils.waitForInvisibilityOfElement(jIndependentAolValue, driver, 30);
                    CommonUtils.waitforElementtoClick(30, journalDepSensitiveSegmentLov, driver);

                    if (journalDepSensitiveSegmentinput.getAttribute("value") == null) {
                        Assert.assertTrue(true);
                        System.out.println("=======> Found NULL value");
                    } else if (journalDepSensitiveSegmentinput.getAttribute("value").equals("")) {
                        Assert.assertTrue(true);
                        System.out.println("=======> Found Empty value");
                    }
                    journalDepSensitiveSegmentLov.click();
                    CommonUtils.waitforElementtoClick(30, jDepAol1Value, driver);
                    Assert.assertTrue(jDepAol1Value.isDisplayed());
                    CommonUtils.waitforElementtoClick(30, jDepAol2Value, driver);
                    Assert.assertTrue(jDepAol2Value.isDisplayed());
                    jDepAol1Value.click();
                    CommonUtils.waitForInvisibilityOfElement(jDepAol1Value, driver, 40);
                    CommonUtils.hold(6);
                    CommonUtils.waitforElementtoClick(30,journalIndSensitiveSegment,driver);

                } else {
                    CommonUtils.waitforElementtoClick(30, jIndependentFlexValue, driver);
                    jIndependentFlexValue.click();
                    CommonUtils.waitForInvisibilityOfElement(jIndependentFlexValue, driver, 20);
                    CommonUtils.waitforElementtoClick(30, journalDepSensitiveSegmentLov, driver);

                    if (journalDepSensitiveSegmentinput.getAttribute("value") == null) {
                        Assert.assertTrue(true);
                        System.out.println("=======> Found NULL value");
                    } else if (journalDepSensitiveSegmentinput.getAttribute("value").equals("")) {
                        Assert.assertTrue(true);
                        System.out.println("=======> Found Empty value");
                    }
                    journalDepSensitiveSegmentLov.click();
                    CommonUtils.waitforElementtoClick(30, jDepFlex1Value, driver);
                    Assert.assertTrue(jDepFlex1Value.isDisplayed());
                    CommonUtils.waitforElementtoClick(30, jDepFlex2Value, driver);
                    Assert.assertTrue(jDepFlex2Value.isDisplayed());
                    jDepFlex1Value.click();
                    CommonUtils.waitForInvisibilityOfElement(jDepFlex1Value, driver, 40);
                    CommonUtils.hold(6);
                    CommonUtils.waitforElementtoClick(30,journalIndSensitiveSegment,driver);
                }
            }

            CommonUtils.waitforElementtoClick(30, journalSubSensitiveSegmentLOV, driver);
            journalSubSensitiveSegmentLOV.click();
            CommonUtils.waitforElementtoClick(30, journalSubLOVSearchUI, driver);
            CommonUtils.hold(2);
            journalSubLOVSearchUI.clear();
            journalSubLOVSearchUI.sendKeys("AOL");
            journalSubLOVSearchBtn.click();
            CommonUtils.waitforElementtoClick(30, jSubAolValue, driver);
            CommonUtils.hold(3);
            jSubAolValue.click();
            CommonUtils.waitforElementtoClick(30, journalSubLOVOKBtn, driver);
            journalSubLOVOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(journalSubLOVOKBtn, driver, 10);
            CommonUtils.waitforElementtoClick(30, journalTableLov, driver);
            journalTableLov.click();
            CommonUtils.waitforElementtoClick(30, jTabValue, driver);
            jTabValue.click();
            CommonUtils.waitForInvisibilityOfElement(jTabValue, driver, 10);
            CommonUtils.waitforElementtoClick(30, saveBtn, driver);
            saveBtn.click();
            CommonUtils.waitforElementtoClick(30, saveConfirmYesBtn, driver);
            saveConfirmYesBtn.click();
            CommonUtils.waitForInvisibilityOfElement(saveConfirmYesBtn, driver, 10);
            CommonUtils.hold(5);
            Assert.assertTrue(journalIndSensitiveSegment.getAttribute("title").equalsIgnoreCase("FLEX"));

            Assert.assertTrue(journalDepSensitiveSegmentinput.getAttribute("value").equalsIgnoreCase("FLEX1"));

            Assert.assertTrue(journalTableInput.getAttribute("value").equalsIgnoreCase("FND_TREE_STRUCTURE"));

            Assert.assertTrue(journalSubSegmentInput.getAttribute("value").equalsIgnoreCase("AOL"));

        } catch (Exception e) {
            System.out.println("Validate all segment values failed. Please review the error message. " + e.getMessage());
            Assert.fail();
            e.printStackTrace();
        }

        dffParameters.clear();

    }

    public void validateTableLayOutAllValues(WebDriver driver, String... args) throws Exception {
        for (int i = 0; i < args.length; i += 2) {
            dffParameters.put(args[i], args[i + 1]);
            System.out.println("Key -> " + args[i] + " && Value -> " + args[i + 1]);
        }

        CommonUtils.hold(3);
        // CommonUtils.ExplicitWait(generalAccountHomeLink,
        // "Click", "");
        // generalAccountHomeLink.click();
        // CommonUtils.ExplicitWait(journalsHomeLink, "Click",
        // "");
        // journalsHomeLink.click();
        CommonUtils.explicitWait(journalIncompleteTab, "Click", "", driver);
        CommonUtils.hold(5);
        journalIncompleteTab.click();
        CommonUtils.explicitWait(journalQBEField, "Click", "", driver);
        CommonUtils.hold(2);
        journalQBEField.click();
        CommonUtils.explicitWait(journalQBEFieldInput, "Click", "", driver);
        CommonUtils.hold(2);
        journalQBEFieldInput.clear();
        journalQBEFieldInput.clear();
        journalQBEFieldInput.sendKeys(dffParameters.get("journalName"));
        journalQBEFieldInput.sendKeys(Keys.ENTER);
        CommonUtils.hold(2);
        WebElement element = driver
                .findElement(By.xpath("//span[text()='" + dffParameters.get("journalName") + "']"));
        CommonUtils.explicitWait(element, "Click", "", driver);
        element.click();
        CommonUtils.explicitWait(expandBtn, "Click", "", driver);
        CommonUtils.hold(4);

        try {
            element = driver
                    .findElement(By.xpath("//select[@title='" + dffParameters.get("ParentValue") + "']"));
            CommonUtils.explicitWait(element, "Visible", "", driver);
            Assert.assertTrue(element.isDisplayed(),
                    "Context Sensitive Independent Segment value :" + element.getAttribute("title"));

            element = driver
                    .findElement(By.xpath("//input[@value='" + dffParameters.get("ChildValue") + "']"));
            CommonUtils.explicitWait(element, "Visible", "", driver);
            Assert.assertTrue(element.isDisplayed(),
                    "Context Sensitive Dependent Segment value :" + element.getAttribute("value"));

            element = driver
                    .findElement(By.xpath("//input[@value='" + dffParameters.get("SubValue") + "']"));
            CommonUtils.explicitWait(element, "Visible", "", driver);
            Assert.assertTrue(element.isDisplayed(),
                    "Context Sensitive Dependent Segment value :" + element.getAttribute("value"));

            element = driver
                    .findElement(By.xpath("//input[@value='" + dffParameters.get("TableValue") + "']"));
            CommonUtils.explicitWait(element, "Visible", "", driver);
            Assert.assertTrue(element.isDisplayed(),
                    "Context Sensitive Dependent Segment value :" + element.getAttribute("value"));

        } catch (Exception e) {
            System.out.println("Validate all segment values failed. Please Review the error message. " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void navigateToIRCPage(WebDriver driver, String id){
        try {
            driver.get(GetDriverInstance.EnvironmentName.replace("fscmUI/faces/FuseWelcome", "hcmUI/faces/FndOverview?fndGlobalItemNodeId=itemNode_Recruiting_Hiring"));
            CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,4);
            CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(_1addButton),driver);
            _1addButton.click();
            waitForElementToAppear(driver,_2recrutingType);
            _2recrutingType.click();
            CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(_2recrutingType1),driver);
            _2recrutingType1.click();
            waitForElementToDisappear(driver,_3recrutingTemplateDisable);
            _3recrutingTemplate.sendKeys("GHR_WS_POS_REQ_TEMP01");
            waitForElementToAppear(driver,_2recrutingType2);
            _2recrutingType2.click();
            CommonUtils.waitForInvisibilityOfElement(_2recrutingType2,driver,5);
            CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(_4continueButton),driver);
            _4continueButton.click();
            waitForElementToAppear(driver,_5requestNumber);
            _5requestNumber.sendKeys(id);
            CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(_4continueButton),driver);
            _4continueButton.click();
            waitForElementToAppear(driver,_6recruiter);
            _6recruiter.click();
            _6recruiter.sendKeys("ANC_ZBEN_US_EMPLOYEE01");
            waitForElementToAppear(driver,_6recruiter1);
            _6recruiter1.click();
            waitForElementToDisappear(driver,_6recruiter1);
            CommonUtils.waitExplicitly(15,5,ExpectedConditions.elementToBeClickable(_4continueButton),driver);
            _4continueButton.click();
            waitForElementToAppear(driver,_7organization);
            _7organization.click();
            _7organization.sendKeys("AP Automation BU");
            waitForElementToAppear(driver,_7organization1);
            _7organization1.click();
            CommonUtils.waitForInvisibilityOfElement(_7organization1,driver,4);
            CommonUtils.waitExplicitly(15,5,ExpectedConditions.elementToBeClickable(_4continueButton),driver);
            _4continueButton.click();
            waitForElementToAppear(driver,_segment1);
            CommonUtils.hold(3);
        } catch (Exception exp) {
            System.out.println("NavigateToIRCPage Page Failed. Please Review The Error Message. " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    public void waitForElement(WebElement element, WebDriver driver) {
        System.out.println("INFO : Before wait element");
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.elementToBeClickable(element));
        System.out.println("INFO : After wait element");
    }

  //Created by Ashish Pareek
    public void navigateToHireAnEmployee(String empName, String legalEmployer, String Person_glb, String FBLPG, WebDriver driver) throws Exception {
    	act = new Actions(driver);
        driver.get(GetDriverInstance.EnvironmentName.replace("fscmUI/faces/FuseWelcome", "hcmUI/faces/FndOverview?fndGlobalItemNodeId=itemNode_workforce_management_new_person"));
        CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,5);
        CommonUtils.waitforElementtoClick(40,hireEnEmployee,driver);
        hireEnEmployee.click();
        CommonUtils.waitForInvisibilityOfElement(hireEnEmployee,driver,25);
        CommonUtils.waitforElementtoClick(30, nextBtn,driver);
        CommonUtils.waitforElementtoClick(60,hireEnEmployeeSearchLink,driver);
        CommonUtils.hold(5);
        legalEmployeerInputField.clear();
        legalEmployeerInputField.sendKeys(legalEmployer);
        CommonUtils.hold(3);
        CommonUtils.waitforElementtoClick(30,legalEmployeeUSValue,driver);
        CommonUtils.hold(5);
        js = (JavascriptExecutor) driver;
        legalEmployeeUSValue.click();
        CommonUtils.waitForInvisibilityOfElement(legalEmployeeUSValue,driver,10);
        CommonUtils.hold(3);
        lastNameInputField.clear();
        lastNameInputField.click();
        js.executeScript("arguments[0].value='" + empName + "';", lastNameInputField);
        lastNameInputField.sendKeys(Keys.TAB);
        CommonUtils.hold(6);
        Log.info(empName + " Last name provided.");
		//legalEmployerDropdown.click();
		//CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@class='af_inputComboboxListOfValues_dropdown-popup-content']/descendant::span[text()='"+legalEmployer+"']/ancestor::tr[@class='af_table_data-row']")), "Click", "", driver);
		
		personglb.clear();
		personglb.sendKeys(Person_glb);
		
		fblpg.clear();
		fblpg.sendKeys(FBLPG);
		
		/*
		nextBtn.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		
		try {
			if (matchingPersonRecordPopup.isDisplayed()) {
				Log.info("Matching Person Record found. Changing Employee Name.");
				matchingPersonRecord_CancelBtn.click();
				CommonUtils.hold(5);
				
				empName = empName+CommonUtils.uniqueId();
				lastNameInputField.clear();
				lastNameInputField.click();		
				lastNameInputField.sendKeys(empName);
				lastNameInputField.sendKeys(Keys.TAB);
				nextBtn.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(10);
			}
		} catch (Exception e) {
			System.out.println("Employee Name is unique. No matching record found.");
		}
		*/
		completeFlow(driver);
		
		//CommonUtils.hold(5);
		waitForElementToAppear(driver, submitBtn);
		CommonUtils.explicitWait(submitBtn, "Click", "",driver);
		CommonUtils.hold(6); 
		submitBtn.click();
		//CommonUtils.hold(5);
		//CommonUtils.hold(10);
		waitForElementToAppear(driver, warningOKBtn);
		CommonUtils.explicitWait(warningOKBtn, "Click", "",driver);
		warningOKBtn.click();
		CommonUtils.waitForInvisibilityOfElement(warningOKBtn, driver, 40);
		//CommonUtils.hold(10);
		//CommonUtils.hold(30);
		waitForElementToAppear(driver, confirmOKBtn1);
		CommonUtils.explicitWait(confirmOKBtn1, "Click", "",driver);
		confirmOKBtn1.click();
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(confirmOKBtn1, driver, 30);
	}


    public void waitForElementToDisappear(WebDriver driver, WebElement element) {

        boolean disappearStatus = false;
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long totalTime = 0;

        while (!disappearStatus && totalTime < 10 * 1000) {
            try {
                if (element.isDisplayed())
                    disappearStatus = false;

                System.out.println("Element Disappear status "+element.isDisplayed());
            } catch (NoSuchElementException noElement) {
                disappearStatus = true;
                System.out.println("INFO : Element To Disappear Is Success : " + noElement.getMessage());
            } catch (Exception e) {
                disappearStatus = true;
                System.out.println("INFO : Element To Disappear Is Success : " + e.getMessage());
            } finally {
                CommonUtils.hold(3);
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO : waitForElementToDisappear "+totalTime);
            }
        }

    }


    public void waitForElementToAppear(WebDriver driver, WebElement element) {

        boolean visibleStatus = false;
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long totalTime = 0;

        while (!visibleStatus && totalTime < 90 * 1000) {
            try {

                if (element.isDisplayed())
                    visibleStatus = true;
                System.out.println("Element Appear status "+element.isDisplayed());

            } catch (NoSuchElementException noElement) {
                visibleStatus = false;
                System.out.println("INFO : Element To Appear Is Not Success : " + noElement.getMessage());
            } catch (Exception e) {
                visibleStatus = false;
                System.out.println("INFO : Element To Appear Is Not Success : " + e.getMessage());
            } finally {
                CommonUtils.hold(3);
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO : waitForElementToAppear "+totalTime);
            }
        }

    }

    public boolean checkEmptyValue(WebDriver driver, WebElement element) {
        boolean emptyStatus = false;
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long totalTime = 0;
        String empty="";

        while (!emptyStatus && totalTime < 90 * 1000) {
            try {
                empty=element.getAttribute("value");
                System.out.println("Empty value : "+empty);
                emptyStatus=empty.isEmpty();
            } catch (Exception e) {
                System.out.println("INFO : Exception While Checking Empty Status : " + e.getMessage());
                e.printStackTrace();
            } finally {
                CommonUtils.hold(2);
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO :  checkEmptyValue() " + totalTime);
            }
        }

        return emptyStatus;
    }
    
    public WebElement getTableColumnElement(String columnValueIndex,WebDriver driver) {
		WebElement columnValuePath = null;
		columnValuePath = driver.findElement(By.xpath("//div[contains(@id,'columnNameId::dropdownPopup::dropDownContent') and @class = 'af_inputComboboxListOfValues_dropdown-table af_table']/descendant::tr[@_afrrk='"+columnValueIndex+"']/descendant::span"));
		return columnValuePath;
	}
    
    public boolean isColumnValueSelected(String selectColumnValue,WebDriver driver) {
    	boolean isAttributeValSelected = false;
    	int timeout = 0;
    	while(!(isAttributeValSelected) && timeout < 20) {
    		CommonUtils.waitForInvisibilityOfElement(navFlow.username,driver,10);
    		System.out.println("table column value : "+tableColumnField.getAttribute("value"));
    		if(tableColumnField.getAttribute("value").equals(selectColumnValue)) {
    			isAttributeValSelected = true;
    			break;
    		}
    		timeout++;
       	}
    	System.out.println("SelectedValue : "+isAttributeValSelected);
    	return isAttributeValSelected;
    }
    
    
}
