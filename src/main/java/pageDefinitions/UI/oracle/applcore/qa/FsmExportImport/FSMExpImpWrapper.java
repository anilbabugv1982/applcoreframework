package pageDefinitions.UI.oracle.applcore.qa.FsmExportImport;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.GlobalPageTemplate;

public class FSMExpImpWrapper {

	WebDriver fsmDriver;
	private NavigationTaskFlows ntFInstance;
	private ApplicationLogin aLoginInstance;

	public FSMExpImpWrapper(WebDriver driver) {
		fsmDriver = driver;
		ntFInstance = new NavigationTaskFlows(fsmDriver);
		aLoginInstance = new ApplicationLogin(fsmDriver);
	}

	public void createImplementationProject(String ipName, String taskFlowName, WebDriver driver) throws Exception {
		CommonUtils.hold(2);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, driver);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",driver);
		CommonUtils.hold(7);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,	"Click", "", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipNameField, "Visible", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipNameField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipNameField.sendKeys(ipName);
		CommonUtils.hold(2);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().saveOpenProjectBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskAddBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskAddBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDropDown, "Click", "",	driver);
		CommonUtils.selectDropDownElement(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDropDown,"Tasks");
		// Select options = new
		// Select(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDropDown);
		// options.selectByVisibleText("Tasks");
		CommonUtils.hold(2);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskNameField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskNameField.sendKeys(taskFlowName);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskSearchBtn.click();
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//tr[td='" + taskFlowName + "']"));
		driver.findElement(By.xpath("//tr[td='" + taskFlowName + "']")).click();
		CommonUtils.hold(3);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().applyBtn.click();
		CommonUtils.hold(5);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDoneBtn.click();
		CommonUtils.hold(5);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipDoneBtn.click();
		CommonUtils.hold(5);

		// return searchImplementationProject(ipName);

		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchField.sendKeys(ipName);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
		CommonUtils.hold(5);

		try {
			System.out.println("Create Implementaion Package is success :" + ipName);
			Assert.assertTrue(driver.findElement(By.linkText(ipName)).isDisplayed(),
					"Create Implementaion Package is success :" + ipName);
			// return true;
		} catch (Exception e) {
			System.out.println("Create Implementaion Package failed :" + ipName);
			e.printStackTrace();
			Assert.assertFalse(true, "Create Implementaion Package failed :" + ipName);
			// return false;
		}

	}

	public void createImplementationProject(String ipName, List<String> taskFlowName, String searchOption, WebDriver driver) throws Exception {
		try {
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn, "Click", "",driver);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipNameField, "Visible", "",driver);
			CommonUtils.hold(6);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipNameField.clear();
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipNameField.sendKeys(ipName);
			CommonUtils.hold(2);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().saveOpenProjectBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskAddBtn, "Click", "",driver);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskAddBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDropDown, "Click", "",driver);
			CommonUtils.selectDropDownElement(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDropDown, searchOption);
			CommonUtils.hold(2);

			Iterator<String> it=taskFlowName.iterator();
			String taskname;
			while(it.hasNext())
			{
				taskname=it.next();
				ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskNameField.clear();
				ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskNameField.sendKeys(taskname);
				ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskSearchBtn.click();
				CommonUtils.hold(5);
				String taskList= "//span[contains(text(),'"+taskname+"')]";
				driver.findElement(By.xpath(taskList));
				driver.findElement(By.xpath(taskList)).click();
				CommonUtils.hold(3);
				ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().applyBtn.click();
				CommonUtils.hold(3);
			}
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDoneBtn, "Click", "",driver);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskDoneBtn.click();
			CommonUtils.hold(5);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipDoneBtn.click();
			CommonUtils.hold(5);

			// return searchImplementationProject(ipName);

			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchField.clear();
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchField.sendKeys(ipName);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
			CommonUtils.hold(5);

			try {
				Assert.assertTrue(driver.findElement(By.linkText(ipName)).isDisplayed(),
						"Create Implementaion Package is success :" + ipName);
				System.out.println("Create Implementaion Package is success :" + ipName);

			} catch (Exception e) {
				System.out.println("Create Implementaion Package failed :" + ipName);
				e.printStackTrace();
				Assert.assertFalse(true, "Create Implementaion Package failed :" + ipName);
			}
		}
		catch (Exception e) {
			System.out.println("Create Implementaion Package failed :" + ipName);
			e.printStackTrace();
			Assert.assertFalse(true, "Create Implementaion Package failed :" + ipName);
		}
	}

	public boolean searchImplementationProject(String ipName, WebDriver driver) throws Exception {
		CommonUtils.hold(2);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, driver);
		CommonUtils.hold(4);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,	"Click", "", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		CommonUtils.hold(2);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchField.sendKeys(ipName);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
		CommonUtils.hold(5);

		try {
			driver.findElement(By.linkText(ipName)).isDisplayed();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean searchConfigurationProject(String configName, WebDriver driver) throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, driver);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",	driver);
		CommonUtils.hold(7);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPackageLink, "Click",	"", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPackageLink.click();
		CommonUtils.hold(7);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPkgSearchField, "Click",	"", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPkgSearchField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPkgSearchField.sendKeys(configName);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
		CommonUtils.hold(5);

		try {
			driver.findElement(By.partialLinkText(configName)).isDisplayed();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean exportProcessStatus(WebDriver driver) throws Exception {

		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().typeDropDown, "Click", "",	null);
		Select opt = new Select(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().typeDropDown);
		opt.selectByVisibleText("Export setup data");
		CommonUtils.hold(2);
		return checkProcessCompletion(driver);

	}

	public void downloadPackage(WebDriver driver) throws Exception {
		CommonUtils.hold(2);
		driver.findElement(By.xpath("//img[@alt='Download Latest Version']")).click();
		CommonUtils.hold(20);

	}

	public String getFilePath(final String fileName) throws Exception {
		File file = null;
		File[] files = null;
		String filePath = null;
		try {
			file = new File(GetDriverInstance.fsmExpImpFile);
			FileFilter filter = new FileFilter() {
				public boolean accept(File pathname) {
					if (pathname.getName().startsWith(fileName))
						return true;

					return false;
				}
			};

			files = file.listFiles(filter);
			for (File d : files) {
				System.out.println(d.toString());
				filePath = d.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filePath;

	}

	public boolean importProcessStatus(WebDriver driver) throws Exception {

		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().typeDropDown, "Click", "",driver);
		Select opt = new Select(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().typeDropDown);
		opt.selectByVisibleText("Import setup data");
		CommonUtils.hold(5);

		return checkProcessCompletion(driver);
	}

	public boolean uploadProcessStatus(WebDriver driver) throws Exception {
		CommonUtils.hold(2);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().typeDropDown, "Click", "",	driver);
		Select opt = new Select(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().typeDropDown);
		opt.selectByVisibleText("Upload");
		CommonUtils.hold(5);

		return checkProcessCompletion(driver);
	}

	public boolean checkProcessCompletion(WebDriver driver) throws Exception {

		boolean status = false;
		String statusIconTxt = "";
				
		long startTime=	System.currentTimeMillis();
		long endTime=0;
		long totalTime=0;

		while (!status && totalTime < 1300*1000) {
			try {
				ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
				CommonUtils.hold(5);
				status = ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().expImpSuccessStatusIconText
						.isDisplayed();
				status = true;
			} catch (Exception e) {
				CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().refreshIcon, "Click","", driver);
				// ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().refreshIcon.click();
				ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
				System.out.println("Checking process completion status .......");
				CommonUtils.hold(10);
				
				CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().expImpStatus, "Visible","", driver);
				statusIconTxt = ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().expImpStatus.getText();
				if (statusIconTxt.trim().equalsIgnoreCase("Not started") ||  statusIconTxt.trim().equalsIgnoreCase("In progress") || statusIconTxt.trim().equalsIgnoreCase("Completed successfully")) {
					System.out.println("ExpImp Status is: "+statusIconTxt);
				}
				else {
					System.out.println("ExpImp Status is: "+statusIconTxt);
					Log.info("ExpImp Status is: "+statusIconTxt);
					break;
				}
			}finally{
				endTime=System.currentTimeMillis();
				totalTime=(endTime-startTime);
				System.out.println("INFO : Finally Block Process Completion Status");
				System.out.println("INFO : In Finally Block - Total Time Taken In FSM Exp/Imp : "+totalTime);
				System.out.println("INFO : In Finally Block - FSM Exp/Imp Status Text : "+statusIconTxt);
			}
		}
		
		if(!status){
			System.out.println("INFO : Total Time Spent for Job : "+totalTime);
			System.out.println("INFO: Final Status Text:"+statusIconTxt);
			System.out.println("ERROR : FSM Exp/Imp Process Was Not Run Successfully :");
			Assert.fail("ERROR : FSM Exp/Imp Process Was Not Run Successfully :");
		}

		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
		CommonUtils.hold(5);
		return status;

	}

	public void uploadConfigurationPackage(String cpName, WebDriver driver) throws Exception {
		searchConfigurationProject(cpName, driver);
		driver.findElement(By.xpath("//div[@title='Upload to the current application environment']")).click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//input[contains(@name,'pt1:r1:0:r0:2:uploadPanel:cpFile')]")),"Visible","",	driver);
		driver.findElement(By.xpath("//input[contains(@name,'pt1:r1:0:r0:2:uploadPanel:cpFile')]"))
				.sendKeys(getFilePath(cpName));
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//button[contains(@id,'uploadPanel:uploadButton')]")).click();

		boolean sbtBtnStatus=false;
		long startTime=	System.currentTimeMillis();
		long endTime=0;
		long totalTime=0;

		while (!sbtBtnStatus && totalTime < 150*1000) {
			try {
				CommonUtils.hold(5);
				sbtBtnStatus = driver.findElement(By.xpath("//button[contains(@id,'uploadPanel:submitButton')]")).isEnabled();
				System.out.println("sbt button enable status: "+sbtBtnStatus);
			} catch (Exception e) {
				// sbtBtnStatus=true;
				e.printStackTrace();
			}finally {
				endTime=System.currentTimeMillis();
				totalTime=(endTime-startTime);
			}
		}
		if(!sbtBtnStatus) {
			Assert.assertTrue(false, "Submit button was not enabled while uplodaing the config package");
		}
		
		driver.findElement(By.xpath("//button[contains(@id,'uploadPanel:submitButton')]")).click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configYesBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configYesBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn, "Click", "",	driver);

		// return uploadProcessStatus();

		if (uploadProcessStatus(driver)) {
			System.out.print("Upload configuration is successfull " + cpName);
			Assert.assertTrue(uploadProcessStatus(driver), "Upload configuration is successfull " + cpName);
		} else {
			System.out.print("Upload configuration package failed : " + cpName);
			Assert.assertTrue(false, "Upload configuration package failed : " + cpName);
		}

	}

	public void createConfigurationProject(String ipName, WebDriver driver) throws Exception {

		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, driver);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",driver);
		CommonUtils.hold(7);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPackageLink, "Click","", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPackageLink.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipCreateBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configLOVBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configLOVBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configSearchLink, "Visible",	"", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configSearchLink.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configNameSelectField,	"Visible", "", driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configNameSelectField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configNameSelectField.sendKeys(ipName);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().taskSearchBtn.click();
		CommonUtils.hold(5);
		driver.findElement(	By.xpath("//div[contains(@id,'sourceImplProjectNameId_afrLovInternalTableId::db')]/table/tbody/tr/td"))	.click();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().okBtn.click();
		CommonUtils.hold(3);
		try {
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configWrngYesBtn,	"Click", "", driver);
			ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configWrngYesBtn.click();
		} catch (Exception e) {
			System.out.println("No Such popup present");
		}
		CommonUtils.hold(5);
		// configTrackingName =
		// ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configTrackingName.getAttribute("value");
		// System.out.println("Configuration Name " + configTrackingName);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configsubmitBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configsubmitBtn.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configYesBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configYesBtn.click();
		CommonUtils.hold(10);

		// return searchConfigurationProject(ipName) && exportProcessStatus();

		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPkgSearchField, "Visible", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPkgSearchField.clear();
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configPkgSearchField.sendKeys(ipName);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipSearchBtn.click();
		CommonUtils.hold(5);

		try {
			Assert.assertTrue(
					driver.findElement(By.partialLinkText(ipName)).isDisplayed() && exportProcessStatus(fsmDriver),
					"Created Configuration Package failed : " + ipName);
			System.out.println("Created Configuration Package successfully : " + ipName);

		} catch (Exception e) {
			System.out.println("Create Configuration package failed " + ipName);
			e.printStackTrace();
			Assert.assertFalse(true, "Create Configuration package failed " + ipName);

		}

	}

	public void importSetupData(String configName, WebDriver driver) throws Exception {
		// searchConfigurationProject(configName);
		CommonUtils.hold(3);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@id,'ATp:importBtn')]")), "Click", "",	driver);
		driver.findElement(By.xpath("//div[contains(@id,'ATp:importBtn')]")).click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configsubmitBtn, "Click", "",	driver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configsubmitBtn.click();
		CommonUtils.hold(5);
		// CommonUtils.ExplicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configYesBtn,
		// "Click",
		// "");
		// ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().configYesBtn.click();
		// CommonUtils.hold(3);

		// return importProcessStatus();

		if (importProcessStatus(driver)) {
			System.out.print("Import configuration is successfull " + configName);
			Assert.assertTrue(importProcessStatus(driver), "Import configuration is successfull " + configName);
		} else {
			System.out.print("Import configuration package failed : " + configName);
			Assert.assertTrue(false, "Import configuration package failed : " + configName);
		}

	}

}
