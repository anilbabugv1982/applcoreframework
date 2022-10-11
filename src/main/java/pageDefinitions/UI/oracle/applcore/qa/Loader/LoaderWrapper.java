package pageDefinitions.UI.oracle.applcore.qa.Loader;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.ds.DataSecurityWrapper;

import java.io.*;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class LoaderWrapper {

    private WebDriverWait wait;

    ServiceRequestPage sr;
    LoaderObjects loader;
    FileExportImport fileExportImport;
    ManageCommonLookup commonLookup;
    ManageStandardLookup standardLookup;
    ManageValueSet manageValueSet;
    ScheduleProcessObject processObj;
    ManageAdministratorProfileObject manageProfile;
    DFFUtils dffUtils;
    ManaageValueSetsUtil vsUtils = null;

    public LoaderWrapper(WebDriver LoaderDriver) {
        sr = new ServiceRequestPage(LoaderDriver);
        loader = new LoaderObjects(LoaderDriver);
        fileExportImport = new FileExportImport(LoaderDriver);
        commonLookup = new ManageCommonLookup(LoaderDriver);
        standardLookup = new ManageStandardLookup(LoaderDriver);
        wait = new WebDriverWait(LoaderDriver, 250);
        manageValueSet = new ManageValueSet(LoaderDriver);
        processObj = new ScheduleProcessObject(LoaderDriver);
        manageProfile = new ManageAdministratorProfileObject(LoaderDriver);
        dffUtils = new DFFUtils(LoaderDriver);
        vsUtils = new ManaageValueSetsUtil(LoaderDriver);
    }

    public void waitForElementNotVisible(By elementLocator, WebDriver LoaderDriver) {
        CommonUtils.waitForPageLoad(LoaderDriver);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
    }

    public void uploadAttachments(String fileName, final WebDriver driver) {
        try {
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(fileExportImport.uploadAttachment), driver);
            fileExportImport.uploadAttachment.click();
            CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(ServiceRequestPage.browseFile), driver);
            ServiceRequestPage.browseFile.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\LoaderData\\" + fileName);
            Select sel = new Select(fileExportImport.account);
            sel.selectByValue("1");
            CommonUtils.hold(2);
            CommonUtils.explicitWait(ServiceRequestPage.saveAndClose, "Click", "", driver);
            ServiceRequestPage.saveAndClose.click();
            CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.saveAndClose, driver, 20);
            CommonUtils.hold(5);
            fileExportImport.file.clear();
            fileExportImport.file.sendKeys(fileName);
            CommonUtils.explicitWait(ServiceRequestPage.searchAttachmetns, "Click", "", driver);
            fileExportImport.searchAttachments.click();
            fetchFileFromUcmServer(driver);
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(ServiceRequestPage.uploadLink), driver);
            CommonUtils.waitForInvisibilityOfElement(ServiceRequestPage.browseFile, driver, 15);
            System.out.println("INFO : Uploaded Document to UCM Successfully : " + fileName + " " + ServiceRequestPage.uploadLink.isDisplayed());
        } catch (Exception exp) {
            System.out.println("ERROR: Exception While Uploading File To UCM Server : " + fileName + ", " + exp.getMessage());
//            exp.printStackTrace();
        }
    }

    public void fetchFileFromUcmServer(WebDriver driver) {

        boolean buttonStatus = true;
        while (buttonStatus) {
            try {
                CommonUtils.hold(5);
                fileExportImport.searchButtonDisabled.isDisplayed();
            } catch (NoSuchElementException noSuchEle) {
                System.out.println("INFO -> FileUpload : NoSuchElementException : fetchFileFromUcmServer() -> " + noSuchEle.getMessage());
                buttonStatus = false;
            } catch (Exception exp) {
                System.out.println("INFO -> FileUpload : Exception While Waiting : fetchFileFromUcmServer() -> " + exp.getMessage());
                buttonStatus = false;
            }
        }
    }


    public void importLoader(String file1, String file2, final WebDriver driver) throws Exception {

        try {
            //CommonUtils.waitExplicitly(10,6,ExpectedConditions.elementToBeClickable(loader.action),driver);
            dffUtils.waitForElementToAppear(driver, loader.action);
            loader.action.click();
            CommonUtils.waitForInvisibilityOfElement(loader.action, driver, 5);
//		CommonUtils.waitExplicitly(10,8,ExpectedConditions.elementToBeClickable(loader.Import),driver);
            dffUtils.waitForElementToAppear(driver, loader.Import);
            loader.Import.click();
            CommonUtils.waitForInvisibilityOfElement(loader.Import, driver, 4);
//		CommonUtils.waitExplicitly(8,5,ExpectedConditions.elementToBeClickable(loader.account),driver);
            dffUtils.waitForElementToAppear(driver, loader.account);
            Select sel = new Select(loader.account);
            sel.selectByValue("1");
            CommonUtils.hold(3);
            loader.lookupType.clear();
            loader.lookupCode.clear();
            loader.lookupType.sendKeys(file2);
            loader.lookupCode.sendKeys(file1);
            loader.upload.click();

//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(150))	.pollingEvery(Duration.ofSeconds(30)).ignoring(NoSuchElementException.class);
//		try {
//			Boolean element = wait.until(new Function<WebDriver, Boolean>() {
//				public Boolean apply(WebDriver driver1) {
//					boolean ispresent = loader.sucess1.isDisplayed();
//					return ispresent;
//
//				}
//
//			});
//			System.out.println("INFO : Lookups Import Data Is Successful");
//		} catch (Exception e) {
//			System.out.println("ERROR : LookUps Import Process Failed");
//			CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)),driver);
//			driver.findElement(loader.ok).click();
//			CommonUtils.waitForInvisibilityOfElement(driver.findElement(loader.ok),driver,8);
//			CommonUtils.waitExplicitly(6,4,ExpectedConditions.elementToBeClickable(loader.popup),driver);
//			loader.popup.click();
//			CommonUtils.waitForInvisibilityOfElement(loader.popup,driver,6);
//			Assert.fail("LookUps Import Process Failed");
//		}
//		loader.downloadLogFile.click();
//		CommonUtils.hold(5);
//		CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)),driver);
//		driver.findElement(loader.ok).click();
//		CommonUtils.waitForInvisibilityOfElement(driver.findElement(loader.ok),driver,8);
//		CommonUtils.waitExplicitly(15,8,ExpectedConditions.elementToBeClickable(loader.popup),driver);
//		loader.popup.click();
//		CommonUtils.waitForInvisibilityOfElement(loader.popup,driver,10);

            dffUtils.waitForElementToAppear(driver, loader.progressBar);

            if (checkImportProgress(driver, loader.progressBar)) {
                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(loader.downloadLogFile), driver);
                loader.downloadLogFile.click();
                CommonUtils.hold(8);
                CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
                driver.findElement(loader.ok).click();
                waitForElementNotVisible(loader.ok, driver);
                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(loader.popup), driver);
                loader.popup.click();
                dffUtils.waitForElementToDisappear(driver, loader.popup);
            } else {
                CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
                driver.findElement(loader.ok).click();
                waitForElementNotVisible(loader.ok, driver);
                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(loader.popup), driver);
                loader.popup.click();
                CommonUtils.waitForInvisibilityOfElement(loader.popup, driver, 20);
            }
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportProcess " + exp.getMessage());
            throw  exp;
        }

    }

    public void renameFile(String file, String file2) {

        File f1 = new File(GetDriverInstance.fsmExpImpFile + "\\" + file);
        File newfile = new File(GetDriverInstance.fsmExpImpFile + "\\" + file2);

        if (f1.renameTo(newfile)) {
            System.out.println("Rename is succesful\n");
        } else {
            System.out.println("Rename failed\n");
            assertTrue(false, "File Rename failed");
        }

    }

    public String validatePlainLoaderFile(String file, String task, String function) throws IOException {

        String info = "";
        File f1 = new File(GetDriverInstance.fsmExpImpFile + "\\" + file);
        InputStream is = new FileInputStream(f1);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        String lookupData = "";
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }

        String fileAsString = sb.toString();
        String[] sub = fileAsString.split("----------------------------------------------");
        // System.out.println(sub+"lenght is "+sub.length);
        if (function.toLowerCase().contains("lookup") || function.toLowerCase().contains("relatedvalueset")) {
            String types = sub[1];
            String loades = sub[3];
            String typeData = sub[0];
            String loadData = sub[2];

            // System.out.println(types);
            String[] sub1 = types.split("\n");
            if (function.toLowerCase().contains("lookup")) {
                System.out.println("\nFor Lookup Type records details are :");
            } else if (function.toLowerCase().contains("relatedvalueset")) {
                System.out.println("\nFor RelatedValue Data records details are :");
            }

            for (int i = 0; i < sub1.length; i++) {
                if (sub1[i].toLowerCase().contains(task.toLowerCase())) {
                    System.out.println(sub1[i]);

                    if (task.toLowerCase().contains("error")) {
                        String[] splittedTypeData = typeData.split("\n");
                        for (int k = 0; k < splittedTypeData.length; k++) {
                            if (splittedTypeData[k].toLowerCase().contains("error")) {
                                System.out.println(splittedTypeData[k]);
                            }

                        }
                    }
                    lookupData = sub1[i];
                }
            }

            if (function.toLowerCase().contains("lookup")) {
                System.out.println("\nFor Lookup code records details are :");
            } else if (function.toLowerCase().contains("relatedvalueset")) {
                System.out.println("\nFor RelatedValue Data records details are :");
            }
            String[] sub11 = loades.split("\n");
            for (int i1 = 0; i1 < sub11.length; i1++) {
                if (sub11[i1].toLowerCase().contains(task.toLowerCase())) {
                    System.out.println(sub11[i1]);
                    if (task.toLowerCase().contains("error")) {
                        String[] splittedLoadData = loadData.split("\n");
                        for (int k = 0; k < splittedLoadData.length; k++) {
                            if (splittedLoadData[k].toLowerCase().contains("error")) {
                                System.out.println(splittedLoadData[k]);
                            }

                        }
                    }
                    lookupData = lookupData + "\n" + sub11[i1];
                }

            }
        }
        if (function.toLowerCase().equals("valueset") || function.toLowerCase().equals("profile")) {
            if (task.toLowerCase().contains("error: exception")) {
                String data = sub[2];
                String[] sub11 = data.split("\n");
                System.out.println("\nFor " + function + " records for Error condition details are :\n");
                for (int i = 0; i < sub11.length; i++) {
                    if (sub11[i].toLowerCase().contains(task.toLowerCase())) {
                        System.out.println(sub11[i]);

                    }

                }
            } else if (task.toLowerCase().contains("error") && function.toLowerCase().equals("profile")) {
                String data = sub[0];
                String[] sub11 = data.split("\n");
                System.out.println("\nFor " + function + " records for Error condition details are :\n");
                for (int i = 0; i < sub11.length; i++) {
                    if (sub11[i].toLowerCase().contains(task.toLowerCase())) {
                        System.out.println(sub11[i]);

                    }

                }
            }

            String types = sub[1];
            String[] sub11 = types.split("\n");
            System.out.println("\nFor " + function + " records details are :");
            for (int i = 0; i < sub11.length; i++) {
                if (sub11[i].toLowerCase().contains(task.toLowerCase())) {
                    System.out.println(sub11[i]);
                    return sub11[i];
                }

            }
        }
        return lookupData;
    }

    public void validateSoarFile(String newFileName, String loadedfilename, String task) throws IOException {
        File f1 = new File("C:\\EXPIMP\\" + newFileName);
        InputStream is = new FileInputStream(f1);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {
            if (line.contains("Before Processing the Task:[" + loadedfilename)) {
                while (!line.contains("After Processing the Task:[" + loadedfilename)) {
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }
            }

            line = buf.readLine();
        }
        String s = sb.toString();
        // System.out.print(sb);
        String[] sub = s.split("----------------------------------------------");
        String data = sub[1];
        String[] sub1 = data.split("\n");
        for (int i = 0; i < sub1.length; i++) {
            if (sub1[i].toLowerCase().contains(task))
                System.out.println(sub1[i]);

        }

    }

    public void verifyUploadDataOnStandardLookupUI(WebDriver loaderDriver, String search, int numOfRecords, String conditionValue) {

        Boolean isTypePresent = false;
        Boolean isLoadPresent = false;

        try {
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(standardLookup.lookupType), loaderDriver);
            standardLookup.lookupType.clear();
            standardLookup.lookupType.sendKeys(search);
            standardLookup.search.click();
            CommonUtils.hold(4);

            if (conditionValue.equalsIgnoreCase("type")) {
                if (loaderDriver.findElements(By.xpath("//label[text()='Meaning']/preceding-sibling::input[@value='" + search + "']")).size() == numOfRecords)
                    Assert.assertTrue(true, "Lookups Import Operation Successful");
                else
                    Assert.fail("Lookups Import Operation Failed");
            } else {
                if (loaderDriver.findElements(By.xpath("//label[text()='Description']/preceding-sibling::input[@value='" + search + "_Description']")).size() == numOfRecords)
                    Assert.assertTrue(true, "Lookups Import Operation Successful");
                else
                    Assert.fail("Lookups Import Operation Failed");
            }


//			isLoadPresent = loaderDriver.findElement(By.xpath("//label[contains(text(),\'Display Sequence\')]//..//..//..//td[3]//span//span[contains(text(),\'"+ search + "\')]")).isDisplayed();
//			isTypePresent = loaderDriver.findElement(By.xpath("//label[contains(text(),\'Meaning\')]//..//..//..//td[1]//span//span[contains(text(),\'"+ search + "\')]")).isDisplayed();

        } catch (Exception e) {
            System.out.println("ERROR : Unable To Fetch LookUp Data On UI : " + e.getMessage());
            e.printStackTrace();
            Assert.fail();
        }
//
//		if (isTypePresent && isLoadPresent)
//			System.out.println("Data on Ui is present");
//
//		return isTypePresent && isLoadPresent;
        CommonUtils.hold(4);

    }

    @Deprecated
    public boolean verifyUploadDataOnCommonLookupUI(WebDriver loaderDriver, String search) {

        Boolean isTypePresent = false;
        Boolean isLoadPresent = false;

        try {
            commonLookup.lookupType.clear();
            commonLookup.lookupType.sendKeys(search);
            commonLookup.search.click();
            CommonUtils.hold(4);
            isLoadPresent = loaderDriver.findElement(By.xpath(
                    "//label[contains(text(),\'Display Sequence\')]//..//..//..//td[3]//span//span[contains(text(),\'"
                            + search + "\')]"))
                    .isDisplayed();
            isTypePresent = loaderDriver.findElement(
                    By.xpath("//label[contains(text(),\'Meaning\')]//..//..//..//td[1]//span//span[contains(text(),\'"
                            + search + "\')]"))
                    .isDisplayed();

        } catch (Exception e) {
            System.out.println("Error while finding data on UI");
        }

        if (!(isTypePresent && isLoadPresent)) {
            if (!isTypePresent)
                System.out.println("Data in Lookup type table is not present");
            if (!isLoadPresent)
                System.out.println("Data in Lookup code table is not present");
        } else
            System.out.println("Data on Ui is present");

        return isTypePresent && isLoadPresent;

    }

    public boolean verifyUpdateoperation(String lookUpDescription, WebDriver driver) throws Exception {
        boolean verifyStatus = false;
        try {
            CommonUtils.explicitWait(commonLookup.lkpTypeAddBtn, "Visible", "", driver);
            Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + lookUpDescription + "']")).isDisplayed());
            verifyStatus = true;
        } catch (Exception verifyStatus1) {
            Assert.fail("Exception in verifyStatus(). " + verifyStatus1.getMessage());

        }
        return verifyStatus;
    }

    public String commitData() {
        String sqltoDeletdata = " commit ;";
        return sqltoDeletdata;
    }

    public String gatherStats() {
        String statsQuery = "BEGIN\n" +
                "dbms_stats.gather_schema_stats(ownname=>'FUSION_OCSERVER11G',cascade=>TRUE,no_invalidate=>FALSE);\n" +
                "END;";
        return statsQuery;
    }

    public String cleanUpData(String tableName) {
        String sqltoDeletdata = "BEGIN\r\n" +
                "delete from FND_LOOKUP_VALUES where LOOKUP_TYPE like '" + tableName + "';\r\n" +
                "delete from FND_LOOKUP_VALUES_TL where LOOKUP_TYPE like '" + tableName + "';\r\n" +
                "delete from FND_LOOKUP_VALUES_B where LOOKUP_TYPE like '" + tableName + "';\r\n" +
                "delete from FND_LOOKUP_TYPES_TL where LOOKUP_TYPE like '" + tableName + "';\r\n" +
                "delete from FND_LOOKUP_TYPES where LOOKUP_TYPE like '" + tableName + "';\r\n" +
                "commit;\r\n" +
                "END;";
        System.out.println(sqltoDeletdata);
        return sqltoDeletdata;
    }

    public String cleanUpValueSetData1() {
        String sqltoDeletdata = "BEGIN\r\n "
                + "DELETE FROM FND_VS_VALUES_B where VALUE_SET_ID in (select VALUE_SET_ID from FND_VS_VALUE_SETS where VALUE_SET_CODE like 'ValueSet_%' or   VALUE_SET_CODE like 'VV%'); \r\n"
                + "DELETE FROM FND_VS_VALUES_TL where VALUE_ID in (select value_id from FND_VS_VALUES_B where VALUE_SET_ID in (select VALUE_SET_ID from FND_VS_VALUE_SETS where VALUE_SET_CODE like 'ValueSet_%' or   VALUE_SET_CODE like 'VV%')); \r\n"
                + "commit;\n"
                + "END;\r\n";
        return sqltoDeletdata;
    }

    public String cleanUpValueSetData2() {
        String sqltoDeletdata = "BEGIN\r\n "
                + "DELETE FROM FND_VS_VALUES_TL where VALUE_ID in (select value_id from FND_VS_VALUES_B where VALUE_SET_ID in (select VALUE_SET_ID from FND_VS_VALUE_SETS where VALUE_SET_CODE like 'ValueSet_%' or   VALUE_SET_CODE like 'VV%')); \r\n"
                + "END;\r\n";
        return sqltoDeletdata;
    }

    public String cleanUpValueSetAllinOne() {
        String sqltoDeletdata = "BEGIN\n" +
                "    DELETE FROM FND_VS_VALUES_B where VALUE_SET_ID in (select VALUE_SET_ID from FND_VS_VALUE_SETS where VALUE_SET_CODE LIKE 'VSCODE%' OR VALUE_SET_CODE like 'ValueSet_%' or   VALUE_SET_CODE like 'VV%');\n" +
                "    DELETE FROM FND_VS_VALUES_TL where VALUE_ID in (select value_id from FND_VS_VALUES_B where VALUE_SET_ID in (select VALUE_SET_ID from FND_VS_VALUE_SETS where VALUE_SET_CODE LIKE 'VSCODE%' OR VALUE_SET_CODE like 'ValueSet_%' or   VALUE_SET_CODE like 'VV%')); \n" +
                "    DELETE FROM FND_VS_VALUE_SETS where VALUE_SET_CODE LIKE 'VSCODE%' OR VALUE_SET_CODE like 'ValueSet_%' or   VALUE_SET_CODE like 'VV%'; \n" +
                "    commit;\n" +
                "END;";
        return sqltoDeletdata;
    }

    public String cleanUprelatedValueSetData() {
        String sqltoDeletdata = "BEGIN\r\n" +
                "delete from fnd_vs_related_values_bk;\r\n" +
                "delete from fnd_vs_related_values;\r\n" +
                "delete from fnd_vs_related_sets_bk;\r\n" +
                "delete from fnd_vs_related_sets;\r\n" +
                "commit;\r\n" +
                "END;";
        return sqltoDeletdata;
    }

    public void importValueSet(String file1, String file2, final WebDriver driver, String operation) throws Exception {
        try {
//			CommonUtils.waitExplicitly(40,15,ExpectedConditions.elementToBeClickable(loader.action),driver);
            dffUtils.waitForElementToAppear(driver, loader.action);
            loader.action.click();
            CommonUtils.waitForInvisibilityOfElement(loader.action, driver, 8);

            if (operation.toLowerCase().equals("import")) {
//				CommonUtils.waitExplicitly(20,9,ExpectedConditions.elementToBeClickable(loader.Import),driver);
                dffUtils.waitForElementToAppear(driver, loader.Import);
                loader.Import.click();
//				CommonUtils.waitForInvisibilityOfElement(loader.Import,driver,10);
//				CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(loader.account),driver);
                dffUtils.waitForElementToAppear(driver, loader.account);
                Select sel = new Select(loader.account);
                sel.selectByValue("1");
                CommonUtils.hold(2);
                loader.fileName.clear();
                loader.fileName.sendKeys(file1);
                loader.upload1.click();
//				CommonUtils.hold(5);
//				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(250)).pollingEvery(Duration.ofSeconds(50)).ignoring(NoSuchElementException.class);
//
//				boolean element=false;
//
//				try {
//					element = wait.until(new Function<WebDriver, Boolean>() {
//						public Boolean apply(WebDriver driver1) {
//							boolean ispresent = loader.sucess1.isDisplayed();
//							System.out.println("INFO : ValueSet Value Import Process Is Success");
//							return ispresent;
//						}
//
//					});
//
//				} catch (Exception e) {
//					System.out.println("ERROR : : ValueSet Value Import Process Failed : "+e.getMessage());
//					CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)),driver);
//					driver.findElement(loader.ok).click();
//					CommonUtils.waitForInvisibilityOfElement(driver.findElement(loader.ok),driver,10);
//					CommonUtils.waitExplicitly(15,10,ExpectedConditions.elementToBeClickable(loader.popup),driver);
//					loader.popup.click();
//					CommonUtils.waitForInvisibilityOfElement(loader.popup,driver,10);
//					assertTrue(false, "ValueSet Value Import Process Failed");
//				}
                dffUtils.waitForElementToAppear(driver, loader.progressBar);

                if (checkImportProgress(driver, loader.progressBar)) {
                    CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(loader.downloadLogFile), driver);
                    loader.downloadLogFile.click();
                    CommonUtils.hold(8);
                    CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
                    driver.findElement(loader.ok).click();
                    waitForElementNotVisible(loader.ok, driver);
                    CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageValueSet.popup), driver);
                    manageValueSet.popup.click();
                    CommonUtils.waitForInvisibilityOfElement(manageValueSet.popup, driver, 20);
                } else {
                    CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
                    driver.findElement(loader.ok).click();
                    waitForElementNotVisible(loader.ok, driver);
                    CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageValueSet.popup), driver);
                    manageValueSet.popup.click();
                    CommonUtils.waitForInvisibilityOfElement(manageValueSet.popup, driver, 20);
                    Assert.fail("Import Operation Failed for File : " + file1);
                }

            } else if (operation.toLowerCase().equals("importvalueset")) {
//                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(loader.ImportRelatedValues), driver);
                dffUtils.waitForElementToAppear(driver, loader.ImportRelatedValues);
                loader.ImportRelatedValues.click();
                CommonUtils.waitForInvisibilityOfElement(loader.ImportRelatedValues, driver, 10);
//                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.account), driver);
                dffUtils.waitForElementToAppear(driver, manageValueSet.account);
                Select sel = new Select(manageValueSet.account);
                sel.selectByValue("1");
                manageValueSet.relatedValueSetFileName.clear();
                manageValueSet.relatedValueSetFileName.sendKeys(file1);
                manageValueSet.relatedValuesFileName.clear();
                manageValueSet.relatedValuesFileName.sendKeys(file2);
                CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(loader.upload2), driver);
                loader.upload2.click();
                CommonUtils.hold(5);
//                Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(250)).pollingEvery(Duration.ofSeconds(50)).ignoring(NoSuchElementException.class);
//                try {
//                    Boolean element = wait.until(new Function<WebDriver, Boolean>() {
//                        public Boolean apply(WebDriver driver1) {
//                            boolean ispresent = loader.sucess.isDisplayed();
//                            System.out.println("INFO : RelatedValueSet Value Import Process Is Success");
//                            return ispresent;
//
//                        }
//
//                    });
//
//                } catch (Exception e) {
//                    System.out.println("ERROR : RelatedValueSet Value Import Process Failed : " + e.getMessage());
//                    CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
//                    driver.findElement(loader.ok).click();
//                    CommonUtils.waitForInvisibilityOfElement(driver.findElement(loader.ok), driver, 10);
//                    CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(loader.popup), driver);
//                    loader.popup.click();
//                    CommonUtils.waitForInvisibilityOfElement(manageValueSet.popup, driver, 20);
//                    Assert.fail("RelatedValueSet Value Import Process Failed");
//                }
//                CommonUtils.hold(4);
//                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(loader.downloadLogFile1), driver);
//                loader.downloadLogFile1.click();
//                CommonUtils.hold(5);
//                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
//                driver.findElement(loader.ok2).click();
//                CommonUtils.waitForInvisibilityOfElement(driver.findElement(loader.ok2), driver, 15);
//                CommonUtils.waitForElement(manageValueSet.popup2, driver);
//                CommonUtils.explicitWait(manageValueSet.popup2, "Click", "", driver);
//                manageValueSet.popup2.click();
//                CommonUtils.waitForInvisibilityOfElement(manageValueSet.popup, driver, 20);

                dffUtils.waitForElementToAppear(driver, loader.progressBar);

                if (checkImportProgress(driver, loader.progressBar)) {
                    CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(loader.downloadLogFile1), driver);
                    loader.downloadLogFile1.click();
                    CommonUtils.hold(8);
                    CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok)), driver);
                    driver.findElement(loader.ok2).click();
                    waitForElementNotVisible(loader.ok2, driver);
                    CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageValueSet.popup2), driver);
                    manageValueSet.popup2.click();
                    CommonUtils.waitForInvisibilityOfElement(manageValueSet.popup2, driver, 20);
                } else {
                    CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(loader.ok2)), driver);
                    driver.findElement(loader.ok2).click();
                    waitForElementNotVisible(loader.ok2, driver);
                    CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageValueSet.popup2), driver);
                    manageValueSet.popup2.click();
                    CommonUtils.waitForInvisibilityOfElement(manageValueSet.popup2, driver, 20);
                    Assert.fail("Import Operation Failed for File : " + file1);
                }

            }
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Processing ValueSet OR RelatedValueSet Values Import Process : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail("Exception While Processing ValueSet OR RelatedValueSet Values Import Process");
        }


    }

    public int verifyUploadDataOnValueSetUI(WebDriver loaderDriver, String search) throws Exception {

        try {
            int count = 0;
            CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageValueSet.valueSetCode), loaderDriver);
            manageValueSet.valueSetCode.clear();
            manageValueSet.valueSetCode.sendKeys(search);
            manageValueSet.search.click();
            CommonUtils.hold(4);
            try {
                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(loaderDriver.findElement(By.xpath("//span[contains(text(),'" + search + "')]"))), loaderDriver);
//				isDataPresent = loaderDriver.findElement(By.xpath("//span[contains(text(),'" + search + "')]")).isDisplayed();
            } catch (Exception e) {
                System.out.println("ERROR : Exception While Getting ValueSet Code On VS UI : " + e.getMessage());
                e.printStackTrace();
                Assert.fail("Exception While Getting ValueSet Code On VS UI");
            }

            CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValue), loaderDriver);
            manageValueSet.manageValue.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValue, loaderDriver, 20);
//			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(manageValueSet.search),loaderDriver);
            dffUtils.waitForElementToAppear(loaderDriver, manageValueSet.search);
            manageValueSet.search.click();
            CommonUtils.waitExplicitly(30, 15, ExpectedConditions.elementToBeClickable(manageValueSet.valueSetData), loaderDriver);
//			CommonUtils.explicitWait(manageValueSet.valueSetData, "Click", "", loaderDriver);
//			CommonUtils.hold(3);
            count = Integer.parseInt(manageValueSet.valueSetData.getAttribute("_rowcount"));
            // System.out.println("Count is "+count);

//			if (isDataPresent)
//				System.out.println("Data on Ui is present");

            CommonUtils.hold(2);
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValueLabel, loaderDriver, 10);
            dffUtils.waitForElementToAppear(loaderDriver, manageValueSet.manageValue);
//			CommonUtils.waitExplicitly(15,10,ExpectedConditions.elementToBeClickable(manageValueSet.manageValue),loaderDriver);
//			CommonUtils.explicitWait(manageValueSet.manageValue, "Click", "", loaderDriver);
            CommonUtils.hold(3);
            return count;
        } catch (Exception vsExp) {
            System.out.println("ERROR : While Getting ValueSet Value Import Count :" + vsExp.getMessage());
            vsExp.printStackTrace();
            CommonUtils.hold(2);
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValueLabel, loaderDriver, 20);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValue), loaderDriver);
            CommonUtils.hold(3);
            return -1;
        }
    }


    public void searchVSTableCode(String vsCode, String dsName, WebDriver driver, ManaageValueSetsUtil vUtils) {
        try {
            vUtils.navigateToManageValueSettf(driver);
            manageValueSet.valueSetCode.clear();
            manageValueSet.valueSetCode.sendKeys(vsCode);
            manageValueSet.search.click();
            CommonUtils.hold(6);
            driver.findElement(By.xpath("//span[contains(text(),\'" + vsCode + "\')]")).isDisplayed();
            manageValueSet._dseditIcon.click();
            CommonUtils.hold(4);
            CommonUtils.explicitWait(manageValueSet._dseditDSButton, "Visible", "", driver);
            System.out.println("manageValueSet._dseditDSButton.isEnabled() :" + manageValueSet._dseditDSButton.isEnabled());

            if (!manageValueSet._dseditDSButton.isEnabled()) {
                manageValueSet._dsInputField.clear();
                manageValueSet._dsInputField.sendKeys(dsName);
                manageValueSet._dsSaveButton.click();
                CommonUtils.hold(8);
//				WebElement ds_name=driver.findElement(By.xpath("//span[text()='"+dsName+"']"));
                CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + dsName + "']")), "Visible", "", driver);
            }

        } catch (Exception e) {
            System.out.println("ERROR : exception in method searchVSTableCode() : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean verifyUpdateOnValueSetUI(WebDriver loaderDriver, String description, String startdate, String EndDate, String sortOrder) throws Exception {

        boolean isUpdated = false;

        try {
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValue), loaderDriver);
            manageValueSet.manageValue.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValue, loaderDriver, 10);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValueLabel), loaderDriver);
            manageValueSet.search.click();
            CommonUtils.hold(5);
            isUpdated = loaderDriver.findElement(By.xpath("//input[contains(@value,\'" + description + "\') and contains(@id,'AP1:AT1:_ATp:ATt1:0:ot7')]")).isDisplayed();
            assertTrue(isUpdated, "Description is not updated");
            isUpdated = loaderDriver.findElement(By.xpath("//input[contains(@value,\'" + startdate + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:0:ot3\')]")).isDisplayed();
            assertTrue(isUpdated, "Start date is not updated");
            isUpdated = loaderDriver.findElement(By.xpath("//input[contains(@value,\'" + EndDate + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:0:ot2\')]")).isDisplayed();
            assertTrue(isUpdated, "End Date is not updated");
            isUpdated = loaderDriver.findElement(By.xpath("//input[contains(@value,\'" + sortOrder + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:0:ot9\')]")).isDisplayed();
            assertTrue(isUpdated, "Sort Order is not updated");
        } catch (Exception e) {
            System.out.println("ERROR : Data Not Present in ValueSet Value UI Page : " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Data Not Present in ValueSet Value UI Page");
        } finally {
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValueLabel, loaderDriver, 10);
//            manageValueSet.cancel.click();
            CommonUtils.hold(6);
            loaderDriver.navigate().refresh();
            CommonUtils.hold(5);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.visibilityOf(manageValueSet.manageValue), loaderDriver);
            CommonUtils.hold(10);
        }


        return isUpdated;

    }

    public boolean verifyUpdateOnValueSetUISecondRow(WebDriver loaderDriver, String description, String startdate, String EndDate, String sortOrder) throws Exception {

        boolean isUpdated = false;

        try {
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValue), loaderDriver);
            manageValueSet.manageValue.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValue, loaderDriver, 10);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValueLabel), loaderDriver);
            manageValueSet.search.click();
            CommonUtils.hold(5);

            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + description + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:1:ot7\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "Description is not updated");
            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + startdate + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:1:ot3\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "Start date is not updated");
            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + EndDate + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:1:ot2\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "End Date is not updated");
            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + sortOrder + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:1:ot9\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "Sort Order is not updated");
        } catch (Exception e) {
            System.out.println("Data not updated on UI");
            assertTrue(false, "Data Not updated on UI");
        } finally {
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValueLabel, loaderDriver, 10);
            CommonUtils.hold(6);
            loaderDriver.navigate().refresh();
            CommonUtils.hold(5);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.visibilityOf(manageValueSet.manageValue), loaderDriver);
            CommonUtils.hold(10);
        }
//		manageValueSet.cancel.click();
//		CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValueLabel,loaderDriver,10);

        return isUpdated;

    }

    public boolean verifyUpdateOnValueSetUIThirdRow(WebDriver loaderDriver, String description, String startdate, String EndDate, String sortOrder) throws Exception {

        boolean isUpdated = false;

        try {
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValue), loaderDriver);
            manageValueSet.manageValue.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValue, loaderDriver, 10);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.manageValueLabel), loaderDriver);
            manageValueSet.search.click();
            CommonUtils.hold(5);

            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + description + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:2:ot7\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "Description is not updated");
            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + startdate + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:2:ot3\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "Start date is not updated");
            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + EndDate + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:2:ot2\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "End Date is not updated");
            isUpdated = loaderDriver.findElement(By.xpath(
                    "//input[contains(@value,\'" + sortOrder + "\') and contains(@id,\'AP1:AT1:_ATp:ATt1:2:ot9\')]"))
                    .isDisplayed();
            assertTrue(isUpdated, "Sort Order is not updated");
        } catch (Exception e) {
            System.out.println("Data not updated on UI");
            assertTrue(false, "Data Not updated on UI");
        } finally {
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValueLabel, loaderDriver, 10);
            CommonUtils.hold(6);
            loaderDriver.navigate().refresh();
            CommonUtils.hold(5);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.visibilityOf(manageValueSet.manageValue), loaderDriver);
            CommonUtils.hold(10);
        }

        return isUpdated;

    }

    public void validateRelatedValueSetOnUI(WebDriver driver, String valueSet, String valueSet1, String valueSet2, int rownum1, int rownum2) throws Exception {
        int rowCount = 0;
        String checkBoxAtr = "";

        try {
//            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.valueSetCode), driver);
            dffUtils.waitForElementToAppear(driver, manageValueSet.valueSetCode);
            manageValueSet.valueSetCode.clear();
            manageValueSet.valueSetCode.sendKeys(valueSet);
            manageValueSet.search.click();
            CommonUtils.hold(5);
//            CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInVSTable), driver);
            dffUtils.waitForElementToAppear(driver, manageValueSet.firstRowInVSTable);
            manageValueSet.firstRowInVSTable.click();
            CommonUtils.hold(3);
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesSet), driver);
            manageValueSet.relatedValuesSet.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.keyFlexTab, driver, 10);
//            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesTabName), driver);
            dffUtils.waitForElementToAppear(driver, manageValueSet.relatedValuesTabName);

            if (manageValueSet.firstRowCheckBoxInVSTable.isDisplayed()) {
                System.out.println("VV1 Displayed");
                checkBoxAtr = manageValueSet.firstRowCheckBoxInVSTable.getAttribute("title");
                System.out.println("Image Title : " + checkBoxAtr);
            } else
                System.out.println("VV1 Not Displayed");

            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInRVSTable), driver);
            manageValueSet.firstRowInRVSTable.click();
            CommonUtils.hold(3);

            manageValueSet.editRelatedValueSetCode.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.firstRowInVSTable, driver, 8);
//            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.visibilityOf(manageValueSet.editValueSetRelation), driver);
            dffUtils.waitForElementToAppear(driver, manageValueSet.editValueSetRelation);

            rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
            System.out.println("Row Count For " + valueSet + " -> " + valueSet1 + " Is	:" + rowCount);

            if (!checkBoxAtr.isEmpty() && checkBoxAtr.equalsIgnoreCase("checked") && rowCount == rownum1)
                Assert.assertTrue(true, "Importing RelatedValueSet Value Is SuccessFull " + valueSet + " -> " + valueSet1 + " : " + rownum1);
            else
                Assert.fail("Importing RelatedValueSet Value Is Failed " + valueSet + " -> " + valueSet1 + " : " + rownum1);

            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation, driver, 8);
        } catch (Exception firstRow) {
            System.out.println("ERROR : Exception While Importing RelatedValueSet Value" + valueSet + " -> " + valueSet1 + " : " + rownum1);
            firstRow.printStackTrace();
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation, driver, 8);
        }


//		CommonUtils.waitExplicitly(15,10,ExpectedConditions.elementToBeClickable(manageValueSet.valueSetCode),driver);
//		manageValueSet.valueSetCode.clear();
//		manageValueSet.valueSetCode.sendKeys(valueSet);
//		manageValueSet.search.click();
//		CommonUtils.hold(5);
////		CommonUtils.waitExplicitly(20,10,ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'" + valueSet + "')]"))),driver);
////		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text(),\'" + valueSet + "\')]")), "Click","", driver);
////		driver.findElement(By.xpath("//span[contains(text(),'" + valueSet + "')]")).click();
////		CommonUtils.hold(5);
////		CommonUtils.hold(3);
//
//		CommonUtils.waitExplicitly(15,8,ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInVSTable),driver);
//		manageValueSet.firstRowInVSTable.click();
//		CommonUtils.hold(3);
//		CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesSet),driver);
////		CommonUtils.explicitWait(manageValueSet.relatedValuesSet,"Click","",driver);
////		CommonUtils.hold(3);
//		manageValueSet.relatedValuesSet.click();
//		CommonUtils.waitForInvisibilityOfElement(manageValueSet.keyFlexTab,driver,10);
//		CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesTabName),driver);
//		CommonUtils.hold(3);
//
//
//
//		try {
//			if(manageValueSet.firstRowCheckBoxInVSTable.isDisplayed()) {
//				System.out.println("VS1 Displayed");
//				System.out.println("Image Title : " +manageValueSet.firstRowCheckBoxInVSTable.getAttribute("title"));
//			}
//			else
//				System.out.println("VS1 Not Displayed");
//
//
//			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInRVSTable),driver);
//			manageValueSet.firstRowInRVSTable.click();
//			CommonUtils.hold(3);
////			driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet1 + "\')]//..//following-sibling::td[1]"))	.click();
////			CommonUtils.hold(3);
////			String ischceked = driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet1 + "\')]//..//following-sibling::td[2]//span//span//span//img")).getAttribute("title");
////			if (!ischceked.equals("checked")) {
////				System.out.println(valueSet1 + " is not checked");
////				assertTrue(false, valueSet1 + " is not enabled");
////			}
//		} catch (Exception e) {
//
//			System.out.println("child " + valueSet1 + " is not present ");
//			assertTrue(false, "child " + valueSet1 + " is not present ");
//		}
//
//		manageValueSet.editRelatedValueSetCode.click();
//		CommonUtils.waitForInvisibilityOfElement(manageValueSet.firstRowInVSTable,driver,8);
//		CommonUtils.waitExplicitly(8,4,ExpectedConditions.visibilityOf(manageValueSet.editValueSetRelation),driver);
////		CommonUtils.explicitWait(manageValueSet.editValueSetRelation, "Click", "", driver);
////		CommonUtils.hold(4);
//
//		int rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
//		System.out.println("row count for "+valueSet+" "+valueSet1 +" are	:"+rowCount);
//		if (!(rowCount == rownum1)) {
//			manageValueSet.cancel.click();
//			System.out.println("row count not matches : "+valueSet+" "+valueSet1 +" are	:"+rowCount);
//			assertTrue(false,"Row count does not match for value set values between " + valueSet + " and " + valueSet1);
//		}
//		manageValueSet.cancel.click();

//		CommonUtils.explicitWait(manageValueSet.manageValueSet, "Click", "", driver);
//		CommonUtils.hold(4);
//		try {
//			driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet2 + "\')]//..//following-sibling::td[1]"))	.click();
//			CommonUtils.hold(3);
//			String ischceked = driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet2 + "\')]//..//following-sibling::td[2]//span//span//span//img")).getAttribute("title");
//			if (!ischceked.equals("checked")) {
//				System.out.println(valueSet2 + " is not checked");
//				assertTrue(false, valueSet2 + " is not enabled");
//			}
//		} catch (Exception e) {
//
//			System.out.println("child " + valueSet2 + " is not present ");
//			assertTrue(false, "child " + valueSet2 + " is not present ");
//		}
//		manageValueSet.editRelatedValueSetCode.click();
//		CommonUtils.explicitWait(manageValueSet.editValueSetRelation, "Click", "", driver);
//		CommonUtils.hold(4);
//		rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
//		System.out.println("row count for "+valueSet+" "+valueSet2 +" are	:"+rowCount);
//
//		if (!(rowCount == rownum2)) {
//			manageValueSet.cancel.click();
//			System.out.println("row count not matches :"+valueSet+" : "+valueSet2 +" are	:"+rowCount);
//			assertTrue(false,"Row count does not match for value set values between " + valueSet + " and " + valueSet2);
//
//		}
//		manageValueSet.cancel.click();
//		CommonUtils.explicitWait(manageValueSet.manageValueSet, "Click", "", driver);
//		CommonUtils.hold(4);

        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.secondRowInRVSTable), driver);
            manageValueSet.secondRowInRVSTable.click();
            CommonUtils.hold(3);


            if (manageValueSet.secondRowCheckBoxVSTable.isDisplayed()) {
                System.out.println("VV2 Displayed");
                checkBoxAtr = manageValueSet.secondRowCheckBoxVSTable.getAttribute("title");
                System.out.println("Image Title : " + checkBoxAtr);
            } else {
                System.out.println("VV2 Not Displayed");
            }

            manageValueSet.secondRowInRVSTable.click();
            CommonUtils.hold(4);
            manageValueSet.editRelatedValueSetCode.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.secondRowInRVSTable, driver, 8);
            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.editValueSetRelation), driver);
            rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));

            System.out.println("Row Count For " + valueSet + " -> " + valueSet2 + " Is	:" + rowCount);

            if (!checkBoxAtr.isEmpty() && checkBoxAtr.equalsIgnoreCase("checked") && rowCount == rownum2)
                Assert.assertTrue(true, "Importing RelatedValueSet Value Is SuccessFull " + valueSet + " -> " + valueSet1 + " : " + rownum2);
            else
                Assert.fail("Importing RelatedValueSet Value Is Failed " + valueSet + " -> " + valueSet2 + " : " + rownum2);

            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation, driver, 8);
        } catch (Exception firstRow) {
            System.out.println("ERROR : Exception While Importing RelatedValueSet Value " + valueSet + " -> " + valueSet2 + " : " + rownum2);
            firstRow.printStackTrace();
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation, driver, 8);
        }

    }

//	public void validateRelatedValueSetOnUIForUpdate(WebDriver driver, String valueSet, String valueSet1,	String valueSet2, int rownum1, int rownum2, String isChecked1, String isChecked2) throws Exception {
//		int rowCount=0;
//		String checkBoxAtr="";
//
//		try{
//
//				CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.valueSetCode), driver);
//				manageValueSet.valueSetCode.clear();
//				manageValueSet.valueSetCode.sendKeys(valueSet);
//				manageValueSet.search.click();
//				CommonUtils.hold(5);
//				CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInVSTable), driver);
//				manageValueSet.firstRowInVSTable.click();
//				CommonUtils.hold(3);
//				CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesSet), driver);
//				manageValueSet.relatedValuesSet.click();
//				CommonUtils.waitForInvisibilityOfElement(manageValueSet.keyFlexTab, driver, 10);
//				CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesTabName), driver);
//
//				if (manageValueSet.firstRowCheckBoxInVSTable.isDisplayed()) {
//					System.out.println("INFO : First Row In RVS Table : " + valueSet1);
//					checkBoxAtr = manageValueSet.firstRowCheckBoxInVSTable.getAttribute("title");
//					System.out.println("INFO : First Row In RVS Table CheckBox : " + checkBoxAtr);
//				} else
//					System.out.println("ERROR : First Row In RVS Table Is Not Displayed :" + valueSet1);
//
//				CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInRVSTable), driver);
//				manageValueSet.firstRowInRVSTable.click();
//				CommonUtils.hold(3);
//
//				manageValueSet.editRelatedValueSetCode.click();
//				CommonUtils.waitForInvisibilityOfElement(manageValueSet.firstRowInVSTable, driver, 8);
//				CommonUtils.waitExplicitly(8, 4, ExpectedConditions.visibilityOf(manageValueSet.editValueSetRelation), driver);
//
//
//				if ((valueSet.equalsIgnoreCase("vv1") && valueSet1.equalsIgnoreCase("vv2"))|| (valueSet.equalsIgnoreCase("vv2") && valueSet1.equalsIgnoreCase("vv1"))) {
//					manageValueSet.filter.sendKeys("%10");
//					manageValueSet.filter.sendKeys(Keys.ENTER);
//					CommonUtils.hold(5);
//
//					System.out.println("Total Size : " + manageValueSet.toKnowCheckBoxStatus.size());
//					int checkBoxCount = 0;
//
//					for (WebElement element : manageValueSet.toKnowCheckBoxStatus) {
////					System.out.println("Is Checked : "+ element.isSelected());
//						if (element.isSelected()) {
//							checkBoxCount++;
//						} else
//							break;
//					}
//
//					if (!checkBoxAtr.equalsIgnoreCase(isChecked1) && checkBoxCount != rownum1) {
//						System.out.println("ERROR : Total Number Of Unchecked CheckBox Count Is Not Equal To :" + rownum1);
//						Assert.fail();
//					}
//				} else if ((valueSet.equalsIgnoreCase("vv3") && valueSet1.equalsIgnoreCase("vv4"))|| (valueSet.equalsIgnoreCase("vv4") && valueSet1.equalsIgnoreCase("vv3"))) {
//					manageValueSet.clearAll.click();
//					CommonUtils.hold(3);
//					manageValueSet.filter.sendKeys("%1");
//					manageValueSet.filter.sendKeys(Keys.ENTER);
//					CommonUtils.hold(5);
//
//					rowCount = Integer.valueOf(manageValueSet.summaryTable.getAttribute(""));
//					if (checkBoxAtr.equalsIgnoreCase(isChecked2) && Integer.valueOf(manageValueSet.summaryTable.getAttribute("_rowcount")) == rownum2) {
//						System.out.println("INFO : Total Number Of New RVS Values Imported : " + rownum2);
//						Assert.assertTrue(true, "Total Number Of New RVS Values Imported : " + rownum2);
//					} else {
//						System.out.println("ERROR : Total Number Of New RVS Values Imported Is Not Equal : " + rownum2);
//						Assert.fail("Total Number Of New RVS Values Imported Is Not Equal : " + rownum2);
//					}
//				} else if (((valueSet.equalsIgnoreCase("vv1") && valueSet1.equalsIgnoreCase("vv3"))|| (valueSet.equalsIgnoreCase("vv3") && valueSet1.equalsIgnoreCase("vv1")))) {
//
//				} else if (((valueSet.equalsIgnoreCase("vv2") && valueSet1.equalsIgnoreCase("vv4"))|| (valueSet.equalsIgnoreCase("vv4") && valueSet1.equalsIgnoreCase("vv2")))) {
//
//
//				}
//
////
////
////			manageValueSet.cancel.click();
////			CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation,driver,8);
//		}catch(Exception updExp){
//			System.out.println("ERROR : Exception While Updating RVS Values : "+updExp.getMessage());
//			updExp.printStackTrace();
//			Assert.fail("Exception While Updating RVS Values");
//		}finally {
//			manageValueSet.cancel.click();
//			CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation,driver,8);
//		}
//
////		try{
////			CommonUtils.waitExplicitly(10,5,ExpectedConditions.elementToBeClickable(manageValueSet.secondRowInRVSTable),driver);
////			manageValueSet.secondRowInRVSTable.click();
////			CommonUtils.waitForInvisibilityOfElement(manageValueSet.keyFlexTab, driver, 10);
////			CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesTabName), driver);
//////			CommonUtils.hold(3);
////
////
////			if(manageValueSet.secondRowCheckBoxVSTable.isDisplayed()){
////				System.out.println("VV3 Displayed");
////				checkBoxAtr=manageValueSet.secondRowCheckBoxVSTable.getAttribute("title");
////				System.out.println("Image Title : " + checkBoxAtr);
////			}else{
////				System.out.println("VV2 Not Displayed");
////			}
////
////
////
////
////			rowCount=Integer.valueOf(manageValueSet.summaryTable.getAttribute("_rowcount"));
////
////			if(!checkBoxAtr.equalsIgnoreCase(isChecked2) && rowCount !=rownum2){
////				System.out.println("ERROR : Total Number Of Unchecked CheckBox Count Is Not Equal To :" + rownum2);
////				Assert.fail();
////			}
////
////		}catch(Exception updExp1){
////			System.out.println("ERROR : Exception While Updating RVS Values : "+updExp1.getMessage());
////			updExp1.printStackTrace();
////			Assert.fail("Exception While Updating RVS Values");
////		}finally {
////			manageValueSet.cancel.click();
////			CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation,driver,8);
////		}
//
//
////
////			rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
////			System.out.println("UPDATE : Row Count For "+valueSet+" - > "+valueSet1 +" Is	:"+rowCount);
////
////			if (!(rowCount == rownum1)) {
////				System.out.println("ERROR : UPDATE row count not matches "+valueSet+" "+valueSet1 +" are	:"+rowCount);
////				manageValueSet.cancel.click();
////				assertTrue(false,"Row count does not match for value set values between " + valueSet + " and " + valueSet1);
////			}
//
//
//
//
//
//
////			if ((valueSet.equalsIgnoreCase("vv1") && valueSet1.equalsIgnoreCase("vv2"))|| (valueSet.equalsIgnoreCase("vv2") && valueSet1.equalsIgnoreCase("vv1"))) {
////				manageValueSet.filter.sendKeys("%10");
////				manageValueSet.filter.sendKeys(Keys.ENTER);
////				CommonUtils.hold(5);
////				for (int i = 25; i <= 33; i++) {
////					try {
////						WebElement el = driver.findElement(	By.xpath("//label[contains(@id,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:AT2:_ATp:ATt2:"+ i + ":sbc2::Label0')]"));
////						Boolean isSelected = el.isSelected();
////						if (isSelected) {
////							assertTrue(false, "Rows is not unchecked");
////						}
////					} catch (Exception e) {
////						manageValueSet.cancel.click();
////						assertTrue(false, "Rows is not present");
////					}
////				}
////				for (int i = 1; i <= 21; i = i + 10) {
////					try {
////						WebElement el = driver.findElement(	By.xpath("//label[contains(@id,\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:AT2:_ATp:ATt2:"+ i + ":sbc2::Label0\')]"));
////						Boolean isSelected = el.isSelected();
////						if (isSelected) {
////							assertTrue(false, "Rows is not unchecked");
////						}
////					} catch (Exception e) {
////						assertTrue(false, "Rows is not present");
////					}
////				}
////				manageValueSet.clearAll.click();
////				CommonUtils.hold(4);
////			}
////			manageValueSet.cancel.click();
////			CommonUtils.explicitWait(manageValueSet.manageValueSet, "Click", "", driver);
////			CommonUtils.hold(4);
////
////			try {
////				driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet2 + "\')]//..//following-sibling::td[1]"))	.click();
////				CommonUtils.hold(3);
////				String ischceked = driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet2 + "\')]//..//following-sibling::td[2]//span//span//span//img")).getAttribute("title");
////				if (!ischceked.equals(isChecked2)) {
////					System.out.println(valueSet2 + " is not " + isChecked2);
////					assertTrue(false, valueSet2 + " is not " + isChecked2);
////				}
////			} catch (Exception e) {
////
////				System.out.println("child " + valueSet2 + " is not present ");
////				assertTrue(false, "child " + valueSet2 + " is not present ");
////			}
////			manageValueSet.editRelatedValueSetCode.click();
////			CommonUtils.explicitWait(manageValueSet.editValueSetRelation, "Click", "", driver);
////			CommonUtils.hold(4);
////			rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
////			System.out.println("UPDATE : row count for "+valueSet+" "+valueSet2 +" are	:"+rowCount);
////
////			if (!(rowCount == rownum2)) {
////				System.out.println("UPDATE : row count not matches "+valueSet+" "+valueSet2 +" are	:"+rowCount);
////				manageValueSet.cancel.click();
////				assertTrue(false,"Row count does not match for value set values between " + valueSet + " and " + valueSet2);
////
////			}
////			if ((valueSet.equalsIgnoreCase("vv3") && valueSet1.equalsIgnoreCase("vv4"))
////					|| (valueSet.equalsIgnoreCase("vv4") && valueSet2.equalsIgnoreCase("vv3"))) {
////				manageValueSet.filter2.sendKeys("%_1");
////				manageValueSet.filter2.sendKeys(Keys.ENTER);
////				CommonUtils.hold(5);
////				for (int i = 0; i <= 20; i = i + 5) {
////					try {
////						WebElement el = driver.findElement(
////								By.xpath("//label[contains(@id,\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:AT2:_ATp:ATt2:"
////										+ i + ":sbc2::Label0\')]"));
////						Boolean isSelected = el.isSelected();
////						if (isSelected) {
////							assertTrue(false, "Rows is not unchecked");
////						}
////					} catch (Exception e) {
////						assertTrue(false, "Rows is not present");
////					}
////				}
////
////				manageValueSet.clearAll.click();
////				CommonUtils.hold(4);
////			}
////			manageValueSet.cancel.click();
////			CommonUtils.explicitWait(manageValueSet.manageValueSet, "Click", "", driver);
////			CommonUtils.hold(4);
////		}catch(Exception updExp){
////
////		}
//
//
//
//		//		CommonUtils.explicitWait(manageValueSet.valueSetCode,"Click","",driver);
////		manageValueSet.valueSetCode.clear();
////		manageValueSet.valueSetCode.sendKeys(valueSet);
////		manageValueSet.search.click();
////		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text(),\'" + valueSet + "\')]")),"Click","",driver);
////		CommonUtils.hold(3);
//////		driver.findElement(By.xpath("//span[contains(text(),\'" + valueSet + "\')]")).click();
//////		CommonUtils.hold(3);
////		manageValueSet.relatedValuesSet.click();
////		CommonUtils.explicitWait(manageValueSet.relatedValuesTabName,"Click","",driver);
////		CommonUtils.hold(3);
////
////		try {
////			driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet1 + "\')]//..//following-sibling::td[1]"))	.click();
////			CommonUtils.hold(3);
////			String ischceked = driver.findElement(By.xpath(
////					"//a[contains(text(),\'" + valueSet1 + "\')]//..//following-sibling::td[2]//span//span//span//img"))
////					.getAttribute("title");
////			if (!ischceked.equals(isChecked1)) {
////				System.out.println(valueSet1 + " is not " + isChecked1);
////				assertTrue(false, valueSet1 + " is not " + isChecked1);
////			}
////		} catch (Exception e) {
////
////			System.out.println("child " + valueSet1 + " is not present ");
////			assertTrue(false, "child " + valueSet1 + " is not present ");
////		}
////
////		manageValueSet.editRelatedValueSetCode.click();
////		CommonUtils.explicitWait(manageValueSet.editValueSetRelation, "Click", "", driver);
////		CommonUtils.hold(3);
////
////		int rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
////		System.out.println("UPDATE : row count for "+valueSet+" "+valueSet1 +" are	:"+rowCount);
////
////		if (!(rowCount == rownum1)) {
////			System.out.println("ERROR : UPDATE row count not matches "+valueSet+" "+valueSet1 +" are	:"+rowCount);
////			manageValueSet.cancel.click();
////			assertTrue(false,"Row count does not match for value set values between " + valueSet + " and " + valueSet1);
////		}
////		if ((valueSet.equalsIgnoreCase("vv1") && valueSet1.equalsIgnoreCase("vv2"))
////				|| (valueSet.equalsIgnoreCase("vv2") && valueSet1.equalsIgnoreCase("vv1"))) {
////			manageValueSet.filter.sendKeys("%10");
////			manageValueSet.filter.sendKeys(Keys.ENTER);
////			CommonUtils.hold(5);
////			for (int i = 25; i <= 33; i++) {
////				try {
////					WebElement el = driver.findElement(	By.xpath("//label[contains(@id,\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:AT2:_ATp:ATt2:"+ i + ":sbc2::Label0\')]"));
////					Boolean isSelected = el.isSelected();
////					if (isSelected) {
////						assertTrue(false, "Rows is not unchecked");
////					}
////				} catch (Exception e) {
////					manageValueSet.cancel.click();
////					assertTrue(false, "Rows is not present");
////				}
////			}
////			for (int i = 1; i <= 21; i = i + 10) {
////				try {
////					WebElement el = driver.findElement(	By.xpath("//label[contains(@id,\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:AT2:_ATp:ATt2:"+ i + ":sbc2::Label0\')]"));
////					Boolean isSelected = el.isSelected();
////					if (isSelected) {
////						assertTrue(false, "Rows is not unchecked");
////					}
////				} catch (Exception e) {
////					assertTrue(false, "Rows is not present");
////				}
////			}
////			manageValueSet.clearAll.click();
////			CommonUtils.hold(4);
////		}
////		manageValueSet.cancel.click();
////		CommonUtils.explicitWait(manageValueSet.manageValueSet, "Click", "", driver);
////		CommonUtils.hold(4);
////
////		try {
////			driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet2 + "\')]//..//following-sibling::td[1]"))	.click();
////			CommonUtils.hold(3);
////			String ischceked = driver.findElement(By.xpath("//a[contains(text(),\'" + valueSet2 + "\')]//..//following-sibling::td[2]//span//span//span//img")).getAttribute("title");
////			if (!ischceked.equals(isChecked2)) {
////				System.out.println(valueSet2 + " is not " + isChecked2);
////				assertTrue(false, valueSet2 + " is not " + isChecked2);
////			}
////		} catch (Exception e) {
////
////			System.out.println("child " + valueSet2 + " is not present ");
////			assertTrue(false, "child " + valueSet2 + " is not present ");
////		}
////		manageValueSet.editRelatedValueSetCode.click();
////		CommonUtils.explicitWait(manageValueSet.editValueSetRelation, "Click", "", driver);
////		CommonUtils.hold(4);
////		rowCount = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
////		System.out.println("UPDATE : row count for "+valueSet+" "+valueSet2 +" are	:"+rowCount);
////
////		if (!(rowCount == rownum2)) {
////			System.out.println("UPDATE : row count not matches "+valueSet+" "+valueSet2 +" are	:"+rowCount);
////			manageValueSet.cancel.click();
////			assertTrue(false,"Row count does not match for value set values between " + valueSet + " and " + valueSet2);
////
////		}
////		if ((valueSet.equalsIgnoreCase("vv3") && valueSet1.equalsIgnoreCase("vv4"))
////				|| (valueSet.equalsIgnoreCase("vv4") && valueSet2.equalsIgnoreCase("vv3"))) {
////			manageValueSet.filter2.sendKeys("%_1");
////			manageValueSet.filter2.sendKeys(Keys.ENTER);
////			CommonUtils.hold(5);
////			for (int i = 0; i <= 20; i = i + 5) {
////				try {
////					WebElement el = driver.findElement(
////							By.xpath("//label[contains(@id,\'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:AT2:_ATp:ATt2:"
////									+ i + ":sbc2::Label0\')]"));
////					Boolean isSelected = el.isSelected();
////					if (isSelected) {
////						assertTrue(false, "Rows is not unchecked");
////					}
////				} catch (Exception e) {
////					assertTrue(false, "Rows is not present");
////				}
////			}
////
////			manageValueSet.clearAll.click();
////			CommonUtils.hold(4);
////		}
////		manageValueSet.cancel.click();
////		CommonUtils.explicitWait(manageValueSet.manageValueSet, "Click", "", driver);
////		CommonUtils.hold(4);
//	}
//

    public void validateRelatedValueSetOnUIForUpdate(WebDriver driver, Map<String, String> valuSet) throws Exception {
        int rowCountFirst = 0;
        int rowCountSecond = 0;
        String checkBoxAtrFirst = "";
        String checkBoxAtrSecond = "";
        System.out.println("valuSet.size() ---------->" + valuSet.size());
        try {

            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.elementToBeClickable(manageValueSet.valueSetCode), driver);
            manageValueSet.valueSetCode.clear();
            manageValueSet.valueSetCode.sendKeys(valuSet.get("VSCODE"));
            manageValueSet.search.click();
            CommonUtils.hold(5);

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInVSTable), driver);
            manageValueSet.firstRowInVSTable.click();
            CommonUtils.hold(3);

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesSet), driver);
            manageValueSet.relatedValuesSet.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.keyFlexTab, driver, 10);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(manageValueSet.relatedValuesTabName), driver);

            if (manageValueSet.firstRowCheckBoxInVSTable.isDisplayed()) {
                checkBoxAtrFirst = manageValueSet.firstRowCheckBoxInVSTable.getAttribute("title");
                System.out.println("INFO : First Row In RVS Table CheckBox checkBoxAtrFirst : " + checkBoxAtrFirst);
            }

            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(manageValueSet.firstRowInRVSTable), driver);
            manageValueSet.firstRowInRVSTable.click();
            CommonUtils.hold(3);

            manageValueSet.editRelatedValueSetCode.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.firstRowInVSTable, driver, 8);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.visibilityOf(manageValueSet.editValueSetRelation), driver);


            if (valuSet.get("FIRSTROW").equalsIgnoreCase("update")) {
                manageValueSet.filter.sendKeys("%10");
                manageValueSet.filter.sendKeys(Keys.ENTER);
                CommonUtils.hold(5);

                System.out.println("Total Size : " + manageValueSet.toKnowCheckBoxStatus.size());

                for (WebElement element : manageValueSet.toKnowCheckBoxStatus) {
                    System.out.println("Is Checked : " + element.isSelected());
                    if (!element.isSelected()) {
                        rowCountFirst++;
                    }
                }
                manageValueSet.clearAll.click();
                CommonUtils.hold(5);
                System.out.println("rowCountFirst ------> :" + rowCountFirst);
            } else {
                rowCountFirst = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
                System.out.println("rowFirst Else : " + rowCountFirst);
            }


        } catch (Exception updExp) {
            System.out.println("ERROR : Exception While Updating RVS Values : " + updExp.getMessage());
            updExp.printStackTrace();
            Assert.fail("Exception While Updating RVS Values");
        } finally {
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation, driver, 8);
        }


        try {

            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(manageValueSet.secondRowInRVSTable), driver);
            manageValueSet.secondRowInRVSTable.click();
            CommonUtils.hold(3);

            if (manageValueSet.secondRowCheckBoxVSTable.isDisplayed()) {
                checkBoxAtrSecond = manageValueSet.secondRowCheckBoxVSTable.getAttribute("title");
                System.out.println("INFO : Second Row In RVS Table CheckBox checkBoxAtrSecond: " + checkBoxAtrSecond);
            }

            manageValueSet.editRelatedValueSetCode.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.secondRowInRVSTable, driver, 8);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.visibilityOf(manageValueSet.editValueSetRelation), driver);

            if (valuSet.get("SECONDROW").equalsIgnoreCase("update")) {
                manageValueSet.filter2.sendKeys("%1");
                manageValueSet.filter2.sendKeys(Keys.ENTER);
                CommonUtils.hold(5);

                System.out.println("Total Size : " + manageValueSet.toKnowCheckBoxStatus.size());

                for (WebElement element : manageValueSet.toKnowCheckBoxStatus) {
                    System.out.println("Is Checked : " + element.isSelected());
                    if (!element.isSelected()) {
                        rowCountSecond++;
                    }
                }
                manageValueSet.clearAll.click();
                CommonUtils.hold(5);
            } else {
                rowCountSecond = Integer.parseInt(manageValueSet.summaryTable.getAttribute("_rowcount"));
                System.out.println("rowCountSecond Else : " + rowCountSecond);
            }

        } catch (Exception updExp1) {
            System.out.println("ERROR : Exception While Updating RVS Values : " + updExp1.getMessage());
            updExp1.printStackTrace();
            Assert.fail("Exception While Updating RVS Values");
        } finally {
            manageValueSet.cancel.click();
            CommonUtils.waitForInvisibilityOfElement(manageValueSet.editValueSetRelation, driver, 8);
        }

        if (rowCountFirst == Integer.valueOf(valuSet.get("RVS12COUNT")) && rowCountSecond == Integer.valueOf(valuSet.get("RVS13COUNT")) && checkBoxAtrFirst.equalsIgnoreCase(valuSet.get("RVS12XBOX")) && checkBoxAtrSecond.equalsIgnoreCase(valuSet.get("RVS13XBOX"))) {
            System.out.println("INFO : Update Operation Success For RVS Import Process");
            valuSet.clear();
            Assert.assertTrue(true, "All Update Operation Passed");
        } else {
            valuSet.clear();
            Assert.fail("Update Operation Failed For RVS Import Process");
        }


    }

    public File createEssJobForLoadingValueSet(String processname, String fileName, String account, String fileType, WebDriver driver) {

        try {
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(processObj.scheduleprocess), driver);
            processObj.scheduleprocess.click();
            dffUtils.waitForElementToAppear(driver, processObj.searchIcon);
//			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(processObj.searchIcon),driver);
            processObj._nameInput.clear();
            processObj._nameInput.sendKeys(processname);
            processObj._nameInput.sendKeys(Keys.TAB);
            CommonUtils.hold(5);
            dffUtils.waitForElementToAppear(driver, processObj.descriptionText);
//			CommonUtils.waitExplicitly(20,10,ExpectedConditions.visibilityOf(processObj.descriptionText),driver);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(processObj.popupOK), driver);
            processObj.popupOK.click();
            CommonUtils.waitForInvisibilityOfElement(processObj.popupOK, driver, 10);
            CommonUtils.waitExplicitly(8, 2, ExpectedConditions.elementToBeClickable(processObj.fileName), driver);
            processObj.fileName.sendKeys(fileName);
            processObj.account.sendKeys(account);
            Select sl = new Select(processObj.fileType);
            sl.selectByVisibleText(fileType);
            processObj.submit.click();

            String processId = null;

            try {
               CommonUtils.waitExplicitly(15, 3, ExpectedConditions.elementToBeClickable(processObj.confirm), driver);
                boolean isDisplay = processObj.confirm.isDisplayed();
                String processmessage = processObj.processIdLabel.getText();
                System.out.println("INFO : ESS Job Message  ->" + processmessage);
                processId = processmessage.replaceAll("[^0-9]", "");
                System.out.println("INFO : Process ID Is " + processId);
                processObj.jobOk.click();
                waitForElementNotVisible(processObj.jobOk_, driver);
                if (!isDisplay) {
                    System.out.println("ERROR : Ess Job Is Not Submitted Successfully");
                    assertTrue(false, "Job Is Not Submitted Successfully");

                }
            } catch (Exception e) {
                System.out.println("ERROR : Ess Job Is Not Submitted Successfully : " + e.getMessage());
                e.printStackTrace();
                assertTrue(false, "Ess Job Is Not Submitted Successfully");
            }


            CommonUtils.explicitWait(processObj.expCollapseSearch, "Click", "", driver);
            if (processObj.expCollapseSearch.getAttribute("title").equals("Expand Search"))
                processObj.expCollapseSearch.click();

            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(processObj.inputSearchField), driver);
            processObj.inputSearchField.clear();
            processObj.inputSearchField.sendKeys(processId);
            processObj.searchButton.click();
            CommonUtils.hold(4);

            boolean status = false;
            CommonUtils.explicitWait(processObj.refresh, "Click", "", driver);
            processObj.refresh.click();

            WebElement el1 = null;
            String processText = "";

            long startTime = System.currentTimeMillis();
            long endTime = 0;
            long totalTime = 0;

            while (totalTime < 360 * 1000) {
                try {
                    processObj.refresh.click();
                    CommonUtils.hold(3);
                    el1 = driver.findElement(By.xpath("//*[contains(text(),\'" + processId + "\')]//..//following-sibling::td//*[contains(text(),'Succeeded')]"));
                    processText = el1.getText();
                    System.out.println("INFO : Status Text In Try Block -> Loader <PROCESS_ID> : " + processId + " " + processText);
                    status = el1.isDisplayed();

                    if (status)
                        break;

                } catch (Exception e) {
                    CommonUtils.waitExplicitly(4, 2, ExpectedConditions.elementToBeClickable(processObj.refresh), driver);
                    processObj.refresh.click();
                    CommonUtils.hold(2);
                } finally {
                    endTime = System.currentTimeMillis();
                    totalTime = (endTime - startTime);
                    System.out.println("INFO : Checking Process Completion Status -> Loader <PROCESS_ID> : " + processId);
                    System.out.println("INFO : Total Time In Finally Block Loader <PROCESS_ID> : " + processId + " -> " + totalTime);
                    System.out.println("INFO : Status Text In Finally Block Loader <PROCESS_ID> : " + processId + " -> " + processText);
                }
            }

            if (!status) {
                System.out.println("INFO : Total Time Spent for Job : " + processId + " : " + totalTime);
                System.out.println("ERROR : ESS Job Was Not Run Successfully : Job ID - > " + processId);
                Assert.fail("ERROR : ESS Job Was Not Run Successfully : Job ID - > " + processId);
            }

            try {
                CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(processObj.firstRow), driver);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
                js.executeScript("arguments[0].click();", processObj.firstRow);
                CommonUtils.hold(10);
                CommonUtils.waitExplicitly(30, 10, ExpectedConditions.elementToBeClickable(driver.findElement(By.partialLinkText(processId))), driver);
                WebElement logFileLink = driver.findElement(By.partialLinkText(processId));
                logFileLink.click();
                CommonUtils.hold(10);

                File downloadEssLogFile1 = new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + processId + ".log");
                File downloadEssLogFile = new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + processId + ".txt"); // Some times file downloaded is with *.txt extension

                if (downloadEssLogFile.isFile()) {
                    System.out.println("INFO : Downloaded File Successfully : " + downloadEssLogFile);
                    Assert.assertTrue(true, "Ess log file downloaded successfully for job ID : " + processId);
                    return downloadEssLogFile;
                } else if (downloadEssLogFile1.isFile()) {
                    System.out.println("INFO : Downloaded file successfully : " + downloadEssLogFile1);
                    Assert.assertTrue(true, "Ess log file downloaded successfully for job ID : " + processId);
                    return downloadEssLogFile1;
                } else {
                    System.out.println("ERROR : Download log file failed " + processId);
                    Assert.assertTrue(false, "Download ESS log file failed for job ID : " + processId);
                    return null;
                }

            } catch (Exception downloadEssLgFile) {
                System.out.println("ERROR : Unable to download esslog file : " + downloadEssLgFile.getMessage());
                downloadEssLgFile.printStackTrace();
                return null;
            }
        } catch (Exception essJobFailed) {
            System.out.println("Error in method : " + essJobFailed.getMessage());
            essJobFailed.printStackTrace();
            return null;
        }

    }


    public void importProfile(String fileName, WebDriver driver) throws Exception {
        try {
//            CommonUtils.hold(5);
//            CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageProfile.actions), driver);
            dffUtils.waitForElementToAppear(driver, manageProfile.actions);
            manageProfile.actions.click();
            CommonUtils.waitForInvisibilityOfElement(manageProfile.actions, driver, 5);
//            CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(manageProfile.import1), driver);
            manageProfile.import1.click();
//            CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(manageProfile.account), driver);
            dffUtils.waitForElementToAppear(driver, manageProfile.account);
            Select sel = new Select(manageProfile.account);
            sel.selectByValue("1");
            CommonUtils.hold(3);
            manageProfile.profileValue.clear();
            manageProfile.profileValue.sendKeys(fileName);
            manageProfile.upload.click();

//            Boolean element = false;
//            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(150)).pollingEvery(Duration.ofSeconds(30)).ignoring(NoSuchElementException.class);
//            try {
//                element = wait.until(new Function<WebDriver, Boolean>() {
//                    public Boolean apply(WebDriver driver1) {
//                        boolean ispresent = manageProfile.sucess.isDisplayed();
//                        System.out.println("INFO : ProfileOptions Value Import Is Success");
//                        return ispresent;
//                    }
//
//                });
//
//            } catch (Exception e) {
//                System.out.println("ERROR : ProfileOptions Value Import Is Failed");
//                CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(driver.findElement(manageProfile.ok)), driver);
//                driver.findElement(manageProfile.ok).click();
//                CommonUtils.waitForInvisibilityOfElement(driver.findElement(manageProfile.ok), driver, 10);
//                manageProfile.popup.click();
//                CommonUtils.waitForInvisibilityOfElement(manageProfile.popup, driver, 10);
//                Assert.fail("ProfileOptions Value Import Is Failed");
//            }
//
//            CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(manageProfile.downloadLogFile), driver);
//            manageProfile.downloadLogFile.click();
//            CommonUtils.hold(5);
//            CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(driver.findElement(manageProfile.ok)), driver);
//            driver.findElement(manageProfile.ok).click();
//            waitForElementNotVisible(manageProfile.ok, driver);
//            CommonUtils.waitExplicitly(15, 8, ExpectedConditions.elementToBeClickable(manageProfile.popup), driver);
//            manageProfile.popup.click();
//            CommonUtils.waitForInvisibilityOfElement(manageProfile.popup, driver, 20);

            dffUtils.waitForElementToAppear(driver, loader.progressBar);

            if (checkImportProgress(driver, loader.progressBar)) {
                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageProfile.downloadLogFile), driver);
                manageProfile.downloadLogFile.click();
                CommonUtils.hold(8);
                CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(manageProfile.ok)), driver);
                driver.findElement(manageProfile.ok).click();
                waitForElementNotVisible(manageProfile.ok, driver);
                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageProfile.popup), driver);
                manageProfile.popup.click();
                dffUtils.waitForElementToDisappear(driver, manageProfile.popup);
            } else {
                CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(driver.findElement(manageProfile.ok)), driver);
                driver.findElement(manageProfile.ok).click();
                waitForElementNotVisible(manageProfile.ok, driver);
                CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(manageProfile.popup), driver);
                manageProfile.popup.click();
                CommonUtils.waitForInvisibilityOfElement(manageProfile.popup, driver, 20);
            }

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Importing ProfileOption Values : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail("Exception While Importing ProfileOption Values ");
        }
    }

    public void verifyUploadDataOnProfileUI(WebDriver driver, String search, String search2, String search3) throws Exception {

        try {
        	
        	if(search2.equalsIgnoreCase("site"))
        	{
        		 CommonUtils.waitforElementtoClick(30, manageProfile.profileOptionCode, driver);
                 manageProfile.profileOptionCode.clear();
                 manageProfile.profileOptionCode.sendKeys(search);
                 manageProfile.search.click();
                 CommonUtils.hold(8);
                 CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'" + search + "')]"))), driver);
                 JavascriptExecutor js = (JavascriptExecutor) driver;
//     			EventFiringWebDriver ew = new EventFiringWebDriver(driver);
                 js.executeScript("window.scrollBy(0,500)", "");
                 CommonUtils.waitforElementtoClick(30, manageProfile.userNameInput, driver);
                 manageProfile.profileLevelInput.clear();
                 manageProfile.userNameInput.clear();
                 manageProfile.profileLevelInput.sendKeys("Site");
                 manageProfile.profileLevelInput.sendKeys(Keys.ENTER);
                 CommonUtils.hold(5);
                 CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'" + search2 + "')]"))), driver);
                 WebElement el = driver.findElement(By.xpath("//span[contains(text(),'" + search2 + "')]"));
                 try {
                     Assert.assertTrue(el.isEnabled());
                     WebElement el2=driver.findElement(By.xpath("//label[contains(text(),'Profile Value')]/preceding-sibling::input"));
                     System.out.println("INFO : Selected Option Value : " + el2.getText());
                     Assert.assertTrue(el2.getText().equals(search3));
                 } catch (Exception e) {
                     WebElement el2 = driver.findElement(By.xpath("//input[contains(@value,'" + search3 + "')]"));
                     System.out.println("INFO : Selected Option Value : " + search3);
                     Assert.assertTrue(el2.isDisplayed());
                 }
        	}
        	else {
            CommonUtils.waitforElementtoClick(30, manageProfile.profileOptionCode, driver);
            manageProfile.profileOptionCode.clear();
            manageProfile.profileOptionCode.sendKeys(search);
            manageProfile.search.click();
            CommonUtils.hold(8);
            CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'" + search + "')]"))), driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
//			EventFiringWebDriver ew = new EventFiringWebDriver(driver);
            js.executeScript("window.scrollBy(0,500)", "");
            CommonUtils.waitforElementtoClick(30, manageProfile.userNameInput, driver);
            manageProfile.userNameInput.clear();
            manageProfile.userNameInput.sendKeys(search2);
            manageProfile.userNameInput.sendKeys(Keys.ENTER);
            CommonUtils.hold(5);
            CommonUtils.waitExplicitly(20, 10, ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'" + search2 + "')]"))), driver);
            WebElement el = driver.findElement(By.xpath("//span[contains(text(),'" + search2 + "')]"));

            try {
                Assert.assertTrue(el.isEnabled());
                Select sl = new Select(driver.findElement(By.xpath("//label[contains(text(),'Profile Value')]/preceding-sibling::select")));
                System.out.println("INFO : Selected Option Value : " + sl.getFirstSelectedOption().getText());
                Assert.assertTrue(sl.getFirstSelectedOption().getText().equals(search3));
            } catch (Exception e) {
                WebElement el2 = driver.findElement(By.xpath("//input[contains(@value,'" + search3 + "')]"));
                System.out.println("INFO : Selected Option Value : " + search3);
                Assert.assertTrue(el2.isDisplayed());
            }
        	}
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Validating ProfileOptionsValue : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail("Exception While Validating ProfileOptionsValue");
        }


    }

    public String cleanProfileData(String user1) {
    	String user= user1.toLowerCase();
        String sqltoDeletdata = "\r\n" +
                "BEGIN\r\n" +
                "delete FROM FND_PROFILE_OPTION_VALUES where lower(created_by)='" + user + "' or lower(last_updated_by)='" + user + "';\r\n" +
                "commit;\r\n" +
                "END;";
        System.out.println(sqltoDeletdata);
        return sqltoDeletdata;
        
    }

    public String cleanUpDSdata() {
        String dsSqlQuesry = "BEGIN\r\n" +
                "delete from fnd_object_instance_sets_tl where instance_set_id in (select instance_set_id from fnd_object_instance_sets where instance_set_name like 'SOAR%');\r\n" +
                "delete from fnd_object_instance_sets where  instance_set_name like 'SOAR%';\r\n" +
                "delete from fnd_object_instance_sets_tl where instance_set_id in (select instance_set_id from fnd_object_instance_sets where instance_set_name like 'SOAR%');\r\n" +
                "delete from fnd_object_instance_sets where  instance_set_name like 'SOAR%';\r\n" +
                "delete from fnd_grants where name like 'SOAR%';\r\n" +
                "commit;\r\n" +
                "END;";
        System.out.println(dsSqlQuesry);
        return dsSqlQuesry;
    }

    public void cleanUpDocSeq() {

        String docSeq = "BEGIN\r\n" +
                "delete from fnd_doc_sequence_categories where code like 'SOAR%';\r\n" +
                "delete from  fnd_document_sequences where name like 'DPK%';\r\n" +
                "commit;\r\n" +
                "END;";
        try {
            DbResource.sqlStatement.executeUpdate(docSeq);
        } catch (Exception docSeq1) {
            System.out.println("ERROR : Exception while running docSeqSql " + docSeq1.getMessage());
        }
    }


    public int checkDSCount(WebDriver driver, DataSecurityWrapper dsUtils, String validationType, String dsObject, String value) {
        try {
            dsUtils.navigateToDStaskFlow();
            dsUtils.searchDSObject(dsObject);

            if (validationType.equals("CONDITION")) {
                dsUtils.parentEditBtn.click();
                CommonUtils.explicitWait(dsUtils.conditionsTab, "Click", "", driver);
                CommonUtils.hold(2);
                dsUtils.conditionsTab.click();
                CommonUtils.hold(5);
                return driver.findElements(By.xpath("//span[contains(text(),'" + value + "')]")).size();
            } else {
                return driver.findElements(By.xpath("//span[contains(text(),'" + value + "')]")).size();
            }

        } catch (Exception dsExp) {
            System.out.println("Exception while getting counts : " + dsExp.getMessage());
            dsExp.printStackTrace();
            return -1;
        }

    }

    public boolean checkImportProgress(WebDriver driver, WebElement element) {
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long totalTime = 0;
        String percentage = element.getAttribute("_afrprogressbarpercentcompletion");

        while (!percentage.equalsIgnoreCase("100") && totalTime < 90 * 1000) {
            try {
                percentage = element.getAttribute("_afrprogressbarpercentcompletion");
                System.out.println("Import Process Percentage : " + percentage);

            } catch (Exception e) {
                System.out.println("INFO : Exception While Checking ProgressBar Percent Completion Status : " + e.getMessage());
            } finally {
                CommonUtils.hold(5);
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO :  checkImportProgress() " + totalTime);
            }
        }

        if (percentage.equalsIgnoreCase("100")) {
            if (loader.genericProcessTag.getText().equalsIgnoreCase("Error"))
                return false;
            else
                return true;
        }

        return false;
    }

}