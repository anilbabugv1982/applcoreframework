package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ScheduleProcessObject;

public class ItemImportESSJobs extends ItemImportESSJobsPage{
	ScheduleProcessObject processObj;

	public ItemImportESSJobs(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		processObj = new ScheduleProcessObject(driver);
	}
	
	public File createLoadInterfaceFileForImportESSJob(String processname, boolean downloadLog, String dataFileName, WebDriver driver) throws Exception{
		
		CommonUtils.hold(5);
		CommonUtils.explicitWait(processObj.scheduleprocess, "Click", "", driver);
		try {
			processObj.scheduleprocess.click();
		} catch (ElementClickInterceptedException e) {
			CommonUtils.hold(10);
			CommonUtils.explicitWait(processObj.scheduleprocess, "Click", "", driver);
			processObj.scheduleprocess.click();
		}
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.waitExplicitly(20,2,ExpectedConditions.elementToBeClickable(processObj.searchIcon),driver);	
		
		processObj.searchIcon.click();
		AttachmentCommonUtils.waitTillLoad(16, driver);
		CommonUtils.explicitWait(processObj.search, "Click", "", driver);
		processObj.search.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		CommonUtils.explicitWait(processObj.nameInput, "Click", "", driver);
		processObj.nameInput.sendKeys(processname);
		jobSearchPopup_SearchBtn.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		WebElement el = driver.findElement(By.xpath("//span[contains(@id,'pt1:selectOneChoice2_afrLovInternalTableId:0:_afrColChild0')]"));
		CommonUtils.explicitWait(el, "Click", "", driver);
		el.click();
		AttachmentCommonUtils.waitTillLoad(4, driver);
		processObj.popupOK2.click();
		
		//processObj._nameInput.clear();
		//processObj._nameInput.sendKeys(processname);
		//processObj._nameInput.sendKeys(Keys.TAB);
		AttachmentCommonUtils.waitTillLoad(12, driver);
		CommonUtils.hold(2);
		//WebElement jobName = driver.findElement(By.xpath("//span[contains(text(),'"+processname+"')]"));
		//WebElement jobName = driver.findElement(By.xpath("//input[normalize-space(@value)='"+processname+"']"));
		CommonUtils.waitExplicitly(10,2,ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[normalize-space(@value)='"+processname+"']"))),driver);
		CommonUtils.waitExplicitly(10,2,ExpectedConditions.elementToBeClickable(processObj.popupOK),driver);
		processObj.popupOK.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		
		if(processname.equalsIgnoreCase("Load Interface File for Import")) {
			fillandUploadImportProcessDataFile("Item Import", dataFileName, driver);
		} else if(processname.equalsIgnoreCase("Item Import")) {
			fillItemImportBatchID("19123030", driver);
		} else {
			Assert.assertTrue(false, "Invalid ESS job name.");
		}
		
		//fillandUploadImportProcessDataFile("Item Import", dataFileName, driver);
		
		CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(processObj.submit),driver);
		processObj.submit.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);

		String processId=null;	
		try {
			CommonUtils.waitExplicitly(8,2,ExpectedConditions.elementToBeClickable(processObj.confirm),driver);
			boolean isDisplay = processObj.confirm.isDisplayed();
			String processmessage = processObj.processIdLabel.getText();
			System.out.println("INFO : ESS Job Message  ->" + processmessage);
			processId = processmessage.replaceAll("[^0-9]", "");
			System.out.println("INFO : Process ID Is " + processId);
			processObj.jobOk.click();
			CommonUtils.waitForInvisibilityOfElement(processObj.jobOk,driver,8);
			AttachmentCommonUtils.waitTillLoad(8, driver);
			if (!isDisplay) {
				System.out.println("ERROR : Ess Job Is Not Submitted Successfully");
				assertTrue(false, "Job Is Not Submitted Successfully");

			}
		} catch (Exception e) {
			System.out.println("ERROR : Ess Job Is Not Submitted Successfully : " + e.getMessage());
			e.printStackTrace();
			assertTrue(false, "Ess Job Is Not Submitted Successfully");
		}
		
		CommonUtils.hold(3);
		CommonUtils.explicitWait(processObj.expCollapseSearch, "Click", "", driver);
		if (processObj.expCollapseSearch.getAttribute("title").equals("Expand Search"))
			processObj.expCollapseSearch.click();
		
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.waitExplicitly(15,2,ExpectedConditions.elementToBeClickable(processObj.inputSearchField),driver);
		processObj.inputSearchField.clear();
		processObj.inputSearchField.sendKeys(processId);
		processObj.searchButton.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);

		boolean status = false;
		CommonUtils.explicitWait(processObj.refresh, "Click", "", driver);
		processObj.refresh.click();

		WebElement el1=null;
		WebElement el2=null;
		String processText="";

		long startTime=System.currentTimeMillis();
		long endTime=0;
		long totalTime=0;

		while (!status && totalTime < 480*1000) {
			try {
				
				el1 = driver.findElement(By.xpath("//span[contains(text(),'"+processId+"')]/following::a[contains(@id,'panel:result')]"));
				processText=el1.getText();
				System.out.println("Audit Job Status : "+processText);
				
				el2 = driver.findElement(By.xpath("//span[contains(text(),'"+processId+"')]/following::a[normalize-space(text())='Succeeded']"));
				status = el2.isDisplayed();

			} catch (Exception e) {
				try {
					CommonUtils.waitExplicitly(4,2,ExpectedConditions.elementToBeClickable(processObj.searchButton),driver);
					processObj.searchButton.click();
				} catch(StaleElementReferenceException e1) {
					driver.findElement(By.xpath("//img[contains(@title,'Refresh')]")).click();
				}
			}finally {
				endTime = System.currentTimeMillis();
				totalTime = (endTime-startTime);
				System.out.println("Finally Block- \"Load Interface File for Import\" Job INFO : Total Time Taken : "+totalTime);
				CommonUtils.hold(20);
			}
		}

		if(!status){
			System.out.println("ERROR : ESS Job Was Not Run Successfully : Job ID - > "+processId);
			Assert.fail("ERROR : ESS Job Was Not Run Successfully : Job ID - > "+processId);
		}
		
		File log = null;
		
		if(downloadLog) {
			log = downloadLog(processname, processId, driver);
		}
		return log;

	}

//*****************************************************************************************************************************************************************************
	
	public File downloadLog(String processname, String processId, WebDriver driver){
		try {
			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[normalize-space(text())='"+processname+"']"))),driver);
			WebElement firstRow = driver.findElement(By.xpath("//span[normalize-space(text())='"+processname+"']"));
			Actions act=new Actions(driver);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try {
				act.moveToElement(firstRow).click().build().perform();
				CommonUtils.hold(2);
			} catch(Exception e) {
				System.out.println("in firstrow catch block");
				firstRow = driver.findElement(By.xpath("//span[normalize-space(text())='"+processname+"']"));
				js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				js.executeScript("arguments[0].click();", firstRow);
			}
			AttachmentCommonUtils.waitTillLoad(10, driver);
			
			CommonUtils.waitExplicitly(12,2,ExpectedConditions.elementToBeClickable(driver.findElement(By.partialLinkText(processId))),driver);
			WebElement logFileLink = driver.findElement(By.partialLinkText(processId));
			logFileLink.click();
			Log.info("Log file download link clicked");
			CommonUtils.hold(10); //wait for download the log file.
			
			File downloadEssLogFile = new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + processId + ".log");
            File downloadEssLogFile1 = new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + processId + ".txt"); // Some times file downloaded is with *.txt extension

            try {
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
            } catch(NullPointerException e) {
				System.out.println("Expected Log file not found.");
				Assert.assertTrue(false, "Expected Log file not found.");
				return null;
			}
            
		
		} catch (Exception downloadEssLgFile) {
			  System.out.println("ERROR : Unable to download esslog file : " + downloadEssLgFile.getMessage()); 
			  downloadEssLgFile.printStackTrace();
			  Assert.fail("ERROR : Unable to download esslog file : " + downloadEssLgFile.getMessage()); 
			  return null; 
		}
		 
	}
	
//*********************************************************************************************************************************************************************************************************************
	
	public void fillandUploadImportProcessDataFile(String importProcessName, String dataFilename, WebDriver driver) throws Exception {
		CommonUtils.hold(3);
		CommonUtils.explicitWait(importProcessDropdown, "Click", "", driver);
		importProcessDropdown.click();
		AttachmentCommonUtils.waitTillLoad(20, driver);
		CommonUtils.explicitWait(importProcessSearchLink, "Click", "", driver);
		importProcessSearchLink.click();
		AttachmentCommonUtils.waitTillLoad(8, driver);
		CommonUtils.explicitWait(importProcessPopup_SearchField, "Visible", "", driver);
		importProcessPopup_SearchField.clear();
		importProcessPopup_SearchField.sendKeys(importProcessName);
		importProcessPopup_SearchButton.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		WebElement searchResult = driver.findElement(By.xpath("//span[contains(@id,'_afrColChild0') and text()='"+importProcessName+"']"));
		searchResult.click();
		AttachmentCommonUtils.waitTillLoad(6, driver);
		CommonUtils.explicitWait(importProcessPopup_OkButton, "Visible", "", driver);
		importProcessPopup_OkButton.click();
		AttachmentCommonUtils.waitTillLoad(12, driver);
		Assert.assertEquals(importProcessSearchField.getAttribute("value"), importProcessName, "Import Process job is not displayed.");
		
		CommonUtils.explicitWait(dataFileDropdown, "Click", "", driver);
		dataFileDropdown.click();
		AttachmentCommonUtils.waitTillLoad(10, driver);
		CommonUtils.explicitWait(dataFileuploadLink, "Click", "", driver);
		dataFileuploadLink.click();
		AttachmentCommonUtils.waitTillLoad(6, driver);
		CommonUtils.explicitWait(dataFileChooseFile, "Visible", "", driver);
		dataFileChooseFile.sendKeys(AttachmentCommonUtils.getAttachmentFilePath(dataFilename));
		dataFileOkButton.click();
		AttachmentCommonUtils.waitTillLoad(14, driver);
		Assert.assertEquals(dataFileSearchField.getAttribute("value"), dataFilename, "Data File is not uploaded.");
		
	}
	
	public void fillItemImportBatchID(String batchID, WebDriver driver) throws Exception {
		CommonUtils.hold(3);
		CommonUtils.explicitWait(itemImportJob_BatchIDField, "Click", "", driver);
		itemImportJob_BatchIDField.clear();
		itemImportJob_BatchIDField.sendKeys(batchID);
	}
	

}
