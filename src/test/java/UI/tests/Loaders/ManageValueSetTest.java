package UI.tests.Loaders;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.Loader.EssJobUsingCSVLoader;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ManageValueSet;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ManageValueSetTest extends GetDriverInstance {
	static String pid;
	String srName;
	LoaderWrapper loader;
	private ApplicationLogin aLoginInstance;
	WebDriver loaderDriver;
	private NavigationTaskFlows ntFInstance;
	ServiceRequestPage sr;
	ManageValueSet manageValueSet;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	private ManaageValueSetsUtil vsUtils = null;
	private String expImpURL;
	private String taskListURL;
	private List<Integer> vsData;
	private EssJobUsingCSVLoader essLoaderUtil;

	@Parameters({"user", "pwd"})
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			loaderDriver = getDriverInstanceObject();
			setURLS();
			vsData = new ArrayList<Integer>();
			pid = CommonUtils.uniqueId();
			docPage = new ManageDocumentSequence(loaderDriver);
			js = (JavascriptExecutor) loaderDriver;
			vsUtils = new ManaageValueSetsUtil(loaderDriver);
			aLoginInstance = new ApplicationLogin(loaderDriver);
			ntFInstance = new NavigationTaskFlows(loaderDriver);
			loader = new LoaderWrapper(loaderDriver);
			sr = new ServiceRequestPage(loaderDriver);
			manageValueSet = new ManageValueSet(loaderDriver);
			essLoaderUtil = new EssJobUsingCSVLoader(loaderDriver);
			DbResource.sqlStatement.executeUpdate(loader.cleanUpValueSetData1());
			DbResource.sqlStatement.executeUpdate(loader.gatherStats());
			aLoginInstance.login(user, passWord, loaderDriver);
			CommonUtils.waitForInvisibilityOfElement(aLoginInstance.username, loaderDriver, 15);
		} catch (Exception ex) {
			System.out.println("ERROR : While executing method Setup() : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Test(priority = 1, description = "Upload VS files to UCM")
	public void ImportValueSetFiles() throws Exception {

		loaderDriver.get(expImpURL);
		CommonUtils.hold(2);

		String s = "ValueSet";
		for (int i = 1; i <= 8; i++) {
			s = s + i + ".txt";
			loader.uploadAttachments(s, loaderDriver);
			s = "ValueSet";
		}

	}

	@Test(priority = 2, description = "Import file and find required data ")
	public void ImportDataFile1() { // BUG1.txt
		try {
			vsData.clear();
			vsData.add(17);
			vsData.add(17);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(17);
			vsData.add(0);

			String newFileName = "ManageValueSetTest01_";
			vsUtils.navigateToManageValueSettf(loaderDriver);
			CommonUtils.hold(4);
			String valueSetFile = "ValueSet1.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);

			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("VALUESET_VALUES", vsData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TEXT_ML20"), 2);
			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TTEXT_ML20"), 2);
			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_BUG_19826498_WS_NUM_IND"), 3);
		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile1 " + exp.getMessage());
			Assert.fail(exp.getMessage());
		}

	}

	@Test(priority = 3, description = "Import file and find required data ")
	public void ImportDataFile2() { // VS1_UI.txt

		try {
			vsData.clear();
			vsData.add(43);
			vsData.add(37);
			vsData.add(6);
			vsData.add(1);
			vsData.add(0);
			vsData.add(0);
			vsData.add(36);
			vsData.add(0);

			String newFileName = "ManageValueSetTest02_";
			String valueSetFile = "ValueSet3.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);

			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("VALUESET_VALUES", vsData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));

			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_IND_TEXT_ML20"), 4);
			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_DEP_TEXT_ML20"), 6);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_DATETIME_DEP"), 3);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_DATE_DEP"), 3);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_NUM_DEP"), 7);
		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile2 " + exp.getMessage());
			Assert.fail(exp.getMessage());
		}
	}

	@Test(priority = 3, description = "Import file and find required data ")
	public void ImportDataFile3() { // VS7.txt

		try {
			vsData.clear();
			vsData.add(16);
			vsData.add(16);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(15);
			vsData.add(1);

			String newFileName = "ManageValueSetTest03_";
			CommonUtils.hold(2);
			String valueSetFile = "ValueSet5.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);

			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("VALUESET_VALUES", vsData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_IND_NUMERIC"), 2);
			assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_IND_TIME"), 2);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_IND_TIME1"), 2);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_DEP_NUMERIC"), 2);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_DEP_TIME"), 2);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_DEP_TIME1"), 2);
//		assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_CHAR_DEP_TTEXT_ML20"), 2);
		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile3 " + exp.getMessage());
			Assert.fail(exp.getMessage());
		}
	}

	@Test(priority = 4, description = "Import file and find required data ")
	public void ImportDataFile4() { //VS9.txt
		try {
			vsData.clear();
			vsData.add(2);
			vsData.add(2);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(2);
			vsData.add(0);

			boolean isDisplayed1 = false;
			boolean isDisaplyed2 = false;
			String newFileName = "ManageValueSetTest04_";
			CommonUtils.hold(2);
			String valueSetFile = "ValueSet7.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);

			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("VALUESET_VALUES", vsData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));

			vsUtils.searchValueSet("ValueSet_WS_NUM_IND", loaderDriver);
			CommonUtils.waitforElementtoClick(20, manageValueSet.manageValue, loaderDriver);
//            CommonUtils.waitForElementToPresent(manageValueSet.manageValue);
			manageValueSet.manageValue.click();
			CommonUtils.waitForInvisibilityOfElement(manageValueSet.manageValue, loaderDriver, 10);
			CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(manageValueSet.search), loaderDriver);
			manageValueSet.search.click();
			CommonUtils.hold(8);

			CommonUtils.waitforElementtoClick(30, loaderDriver.findElement(By.xpath("//span[contains(text(),'90') and contains(@id,'AP1:AT1:_ATp:ATt1:0:ot15::content')]")), loaderDriver);
			CommonUtils.waitforElementtoClick(30, loaderDriver.findElement(By.xpath("//span[contains(text(),'91') and contains(@id,'AP1:AT1:_ATp:ATt1:1:ot15::content')]")), loaderDriver);


			isDisplayed1 = loaderDriver.findElement(By.xpath("//span[contains(text(),'90') and contains(@id,'AP1:AT1:_ATp:ATt1:0:ot15::content')]")).isDisplayed();
			isDisaplyed2 = loaderDriver.findElement(By.xpath("//span[contains(text(),'91') and contains(@id,'AP1:AT1:_ATp:ATt1:1:ot15::content')]")).isDisplayed();

			if (isDisplayed1 && isDisaplyed2)
				Assert.assertTrue(true, "ValueSet Value 90/91 Are Present In UI");
			else
				Assert.fail("ValueSet Value 90/91 Are Present In UI");

			manageValueSet.cancel.click();
			CommonUtils.hold(6);
			loaderDriver.navigate().refresh();
			CommonUtils.hold(5);
			CommonUtils.waitExplicitly(15, 10, ExpectedConditions.visibilityOf(manageValueSet.manageValue), loaderDriver);
			CommonUtils.hold(10);

		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile4() : " + exp.getMessage());
			exp.printStackTrace();
			manageValueSet.cancel.click();
			CommonUtils.hold(6);
			loaderDriver.navigate().refresh();
			CommonUtils.hold(5);
			CommonUtils.waitExplicitly(15, 10, ExpectedConditions.visibilityOf(manageValueSet.manageValue), loaderDriver);
			CommonUtils.hold(10);
			Assert.fail("Exception While Running Test ImportDataFile4()");
		}
//        finally {
//            CommonUtils.hold(2);
//            manageValueSet.cancel.click();
//            CommonUtils.hold(6);
//            loaderDriver.navigate().refresh();
//            CommonUtils.hold(5);
//            CommonUtils.waitExplicitly(15, 10, ExpectedConditions.visibilityOf(manageValueSet.manageValue), loaderDriver);
//            CommonUtils.hold(10);
//        }

	}

	@Test(priority = 5, description = "Import file and find required data ")
	public void ImportDataFile5() { // ConstraintValidation.txt

		try {
			String newFileName = "ManageValueSetTest05_";
			CommonUtils.hold(2);
			String valueSetFile = "ValueSet8.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);
			System.out.println("Details in " + newFileName + " file are : ");
			String s = loader.validatePlainLoaderFile(newFileName, "ERROR: Exception", "valueset");
		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile5 " + exp.getMessage());
			Assert.fail(exp.getMessage());
		}

	}

	@Test(priority = 6, description = "Import file and find required data ")
	public void ImportDataFile6() {

		try {
			vsData.clear();
			vsData.add(21);
			vsData.add(21);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(0);
			vsData.add(4);
			vsData.add(17);

			String newFileName = "ManageValueSetTest06_";
			CommonUtils.hold(2);
			String valueSetFile = "ValueSet2.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);
//            Assert.assertTrue(essLoaderUtil.ValidateEssLogs("VALUESET_VALUES", vsData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));

//            assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TEXT_ML20"), 2);
			assertTrue(vsUtils.searchValueSet("ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TEXT_ML20", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUI(loaderDriver, "Testing for SAAS E-Business UPD", "1/1/11", "1/1/12", "20"));
			assertTrue(vsUtils.searchValueSet("ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TEXT_ML20", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUISecondRow(loaderDriver, "Testing for SAAS Fusion UPD", "1/1/10", "1/1/11", "10"));


//            assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TTEXT_ML20"), 2);
			assertTrue(vsUtils.searchValueSet("ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TTEXT_ML20", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUI(loaderDriver, "Testing for SAAS Books UPD", "1/1/13", "1/1/14", "10"));
			assertTrue(vsUtils.searchValueSet("ValueSet_BUG_19826498_VS1_WS_CHAR_IND_TTEXT_ML20", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUISecondRow(loaderDriver, "Testing for SAAS Movies UPD", "1/1/14", "1/1/15", "20"));
		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile6 " + exp.getMessage());
			Assert.fail(exp.getMessage());
		}

	}

	@Test(priority = 7, description = "Import file and find required data ")
	public void ImportDataFile7() {
		try {
			vsData.clear();
			vsData.add(39);
			vsData.add(37);
			vsData.add(2);
			vsData.add(1);
			vsData.add(6);
			vsData.add(0);
			vsData.add(0);
			vsData.add(30);

			String newFileName = "ManageValueSetTest07_";
			CommonUtils.hold(2);
			String valueSetFile = "ValueSet4.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importValueSet(valueSetFile, "", loaderDriver, "import");
			loader.renameFile("vsimport_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("VALUESET_VALUES", vsData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
//            assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_DATE_IND"), 2);
			assertTrue(vsUtils.searchValueSet("ValueSet_WS_DATE_IND", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUI(loaderDriver, "Testing for REL7_B9_1 WS WSUPD", "10/15/11", "10/25/12", "10"));
			assertTrue(vsUtils.searchValueSet("ValueSet_WS_DATE_IND", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUISecondRow(loaderDriver, "Testing for REL7_B9_1 WS WSUPD-1", "10/15/11", "10/25/12", ""));
//            assertEquals(loader.verifyUploadDataOnValueSetUI(loaderDriver, "ValueSet_WS_DATETIME_IND"), 3);
			assertTrue(vsUtils.searchValueSet("ValueSet_WS_DATETIME_IND", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUI(loaderDriver, "Testing for REL7_B9_1 WS WSUPD", "1/15/11", "1/25/12", "10"));
			assertTrue(vsUtils.searchValueSet("ValueSet_WS_DATETIME_IND", loaderDriver));
			assertTrue(loader.verifyUpdateOnValueSetUISecondRow(loaderDriver, "Testing for REL7_B9_1 WS WSUPD-1", "10/15/11", "10/25/12", "20"));
//            assertTrue(loader.verifyUpdateOnValueSetUIThirdRow(loaderDriver, "Testing for REL7_B9_1 WS WSUPD-2 updated", "10/15/11", "10/25/12", "30"));
		} catch (Exception exp) {
			System.out.println("ERROR : Exception While Running Test ImportDataFile7 " + exp.getMessage());
			Assert.fail(exp.getMessage());
		}

	}

	public void setURLS() {
		String actualURL = GetDriverInstance.EnvironmentName;
		expImpURL = actualURL.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export");
		taskListURL = actualURL.replaceAll("FuseWelcome", "FuseTaskListManagerTop");
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException, SQLException {
		loaderDriver.quit();
	}
}
