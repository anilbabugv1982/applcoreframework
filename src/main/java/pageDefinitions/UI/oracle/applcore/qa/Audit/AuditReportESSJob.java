package pageDefinitions.UI.oracle.applcore.qa.Audit;

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

public class AuditReportESSJob extends AuditReportESSJobPage{
	ScheduleProcessObject processObj;

	public AuditReportESSJob(WebDriver driver) {
		super(driver);
		processObj = new ScheduleProcessObject(driver);
	}
	
	public File createEssJobForAuditReport(String processname, boolean downloadLog, String product, String boType, boolean includeChildObjects, String outputFileFormat, WebDriver driver) throws Exception{

		//try {
			CommonUtils.hold(5);
			CommonUtils.waitExplicitly(40,2,ExpectedConditions.elementToBeClickable(processObj.scheduleprocess),driver);
			try {
				processObj.scheduleprocess.click();
			} catch (ElementClickInterceptedException e) {
				CommonUtils.hold(10);
				CommonUtils.explicitWait(processObj.scheduleprocess, "Click", "", driver);
				processObj.scheduleprocess.click();
			}
			CommonUtils.waitExplicitly(20,2,ExpectedConditions.elementToBeClickable(processObj.searchIcon),driver);
//			processObj.searchIcon.click();
//			CommonUtils.explicitWait(processObj.search, "Click", "", driver);
//			CommonUtils.hold(2);
//			processObj.search.click();
//			CommonUtils.explicitWait(processObj.nameInput, "Click", "", driver);
//			CommonUtils.hold(4);
//			processObj.nameInput.sendKeys(processname);
//			processObj.search2.click();
//			CommonUtils.hold(8);
//			WebElement el = driver.findElement(By.xpath("//span[contains(@id,'pt1:selectOneChoice2_afrLovInternalTableId:0:_afrColChild0')]"));
//			CommonUtils.hold(5);
//			CommonUtils.explicitWait(el, "Click", "", driver);
//			el.click();
//			processObj.popupOK2.click();
//			CommonUtils.hold(5);
			processObj._nameInput.clear();
			processObj._nameInput.sendKeys(processname);
			processObj._nameInput.sendKeys(Keys.TAB);
			//CommonUtils.waitForInvisibilityOfElement(processObj.waitCursor, driver, 10);
			CommonUtils.hold(4);
			//WebElement jobName = driver.findElement(By.xpath("//span[contains(text(),'"+processname+"')]"));
			//WebElement jobName = driver.findElement(By.xpath("//input[normalize-space(@value)='"+processname+"']"));
			CommonUtils.waitExplicitly(10,2,ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[normalize-space(@value)='"+processname+"']"))),driver);
			CommonUtils.waitExplicitly(10,2,ExpectedConditions.elementToBeClickable(processObj.popupOK),driver);
			CommonUtils.hold(3);
			processObj.popupOK.click();
			CommonUtils.hold(4);
			CommonUtils.waitForInvisibilityOfElement(processObj.popupOK,driver,8);
			
			fillAuditESSJobData(product, boType, includeChildObjects, outputFileFormat, driver);
			
			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(processObj.submit),driver);
			processObj.submit.click();

			String processId=null;

			try {
				CommonUtils.waitExplicitly(8,3,ExpectedConditions.elementToBeClickable(processObj.confirm),driver);
				boolean isDisplay = processObj.confirm.isDisplayed();
				String processmessage = processObj.processIdLabel.getText();
				System.out.println("INFO : ESS Job Message  ->" + processmessage);
				processId = processmessage.replaceAll("[^0-9]", "");
				System.out.println("INFO : Process ID Is " + processId);
				processObj.jobOk.click();
				CommonUtils.waitForInvisibilityOfElement(processObj.jobOk,driver,8);
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
			
			CommonUtils.hold(5);
			CommonUtils.waitExplicitly(15,2,ExpectedConditions.elementToBeClickable(processObj.inputSearchField),driver);
			processObj.inputSearchField.clear();
			processObj.inputSearchField.sendKeys(processId);
			processObj.searchButton.click();
			CommonUtils.hold(5);

			boolean status = false;
			CommonUtils.explicitWait(processObj.refresh, "Click", "", driver);
			processObj.refresh.click();

			WebElement el1=null;
			WebElement el2=null;
			String processText="";

			long startTime=System.currentTimeMillis();
			long endTime=0;
			long totalTime=0;

			while (!status && totalTime < 240*1000) {
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
					System.out.println("Finally Block- Audit Job INFO : Total Time Taken : "+totalTime);
					CommonUtils.hold(8);
				}
			}

			if(!status){
				System.out.println("ERROR : ESS Job Was Not Run Successfully : Job ID - > "+processId);
				Assert.fail("ERROR : ESS Job Was Not Run Successfully : Job ID - > "+processId);
			}
			
			File log = null;
			
			if(downloadLog) {
				log = downloadLog(processname, processId, outputFileFormat, driver);
			}
			return log;


		/*} catch (Exception essJobFailed) {
			System.out.println("Error in method : " + essJobFailed.getMessage());
			essJobFailed.printStackTrace();
			return null;
		}*/
		

	}

//*****************************************************************************************************************************************************************************
	
	public File downloadLog(String processname, String processId, String outputFileFormat, WebDriver driver){
		try {
			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[normalize-space(text())='"+processname+"']"))),driver);
			WebElement firstRow = driver.findElement(By.xpath("//span[normalize-space(text())='"+processname+"']"));
			//firstRow.click();
			Actions act=new Actions(driver);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try {
				//js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				//js.executeScript("arguments[0].click();", firstRow);
				act.moveToElement(firstRow).click().build().perform();
				CommonUtils.hold(2);
			} catch(Exception e) {
				System.out.println("in firstrow catch block");
				firstRow = driver.findElement(By.xpath("//span[normalize-space(text())='"+processname+"']"));
				js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				js.executeScript("arguments[0].click();", firstRow);
			}
			
			String fileExtension = null;
			if (outputFileFormat.equalsIgnoreCase("Excel")) 
				fileExtension = ".xls";
			else if(outputFileFormat.equalsIgnoreCase("CSV"))
				fileExtension = ".csv";
			else
				Assert.assertTrue(false, "Invalid file format");
			
			CommonUtils.hold(10);
			attachmentMoreLink.click();
			CommonUtils.explicitWait(attachmentDiagBoxHeader, "Visible", "", driver);
			CommonUtils.hold(6);
			attachmentDiagBoxHeader.click();
			CommonUtils.waitExplicitly(12,2,ExpectedConditions.elementToBeClickable(driver.findElement(By.partialLinkText(processId+fileExtension))),driver);
			WebElement logFileLink = driver.findElement(By.partialLinkText(processId+fileExtension));
//			CommonUtils.explicitWait(logFileLink, "Click", "", driver);
			logFileLink.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(attachmentDiagBox_OkBtn, "Click", "", driver);	
			attachmentDiagBox_OkBtn.click();
			CommonUtils.hold(10);
			
			
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			//File downloadEssLogFile1=new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator")+ "auditReport_"+ date + "_" + processId + ".csv");
			//File downloadEssLogFile=new File(GetDriverInstance.fsmExpImpFile+ System.getProperty("file.separator") + "auditReport_"+ date + "_" + processId + ".xls"); 
			
			
			File downloadEssLogFile = getFile(processId+fileExtension);
			
			try {
				if (downloadEssLogFile.isFile()) {
					System.out.println("INFO : Downloaded File Successfully : " + downloadEssLogFile);
					Assert.assertTrue(true, "Ess log file downloaded successfully for job ID : " + processId);
					return downloadEssLogFile;
				} 
				/*
				 * else if (downloadEssLogFile1.isFile()) {
				 * System.out.println("INFO : Downloaded file succesfully : " +
				 * downloadEssLogFile1); Assert.assertTrue(true,
				 * "Ess log file downloaded successfully for job ID : " + processId); return
				 * downloadEssLogFile1; }
				 */
				else {
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
			  System.out.println("ERROR : Unable to download esslog file : " + downloadEssLgFile.getMessage()); downloadEssLgFile.printStackTrace();
			  Assert.fail("ERROR : Unable to download esslog file : " + downloadEssLgFile.getMessage()); 
			  return null; 
		}
		 
	}
	
//*********************************************************************************************************************************************************************************************************************
	
	public void fillAuditESSJobData(String prdname, String botype1, boolean includeChildObjects, String outputFileFormat, WebDriver driver) throws Exception {
		CommonUtils.hold(3);
		CommonUtils.explicitWait(fromDateCalendar, ExplicitWaitCalls.Click.toString(),"",driver);
		fromDateCalendar.click();
		//fromDateCurrentDate.click();
		Actions act=new Actions(driver);
		act.moveToElement(fromDateCurrentDate).click().build().perform();
		
		CommonUtils.hold(3);
		CommonUtils.explicitWait(toDateCalendar, ExplicitWaitCalls.Click.toString(),"",driver);
		toDateCalendar.click();
		//toDateCurrentDate.click();
		act.moveToElement(toDateCurrentDate).click().build().perform();
		
		Select prd=new Select(auditProduct);
		prd.selectByVisibleText(prdname);
		CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 30);
		CommonUtils.hold(5);
		
		if (!prdname.equalsIgnoreCase("Oracle Platform Security Services")) {
				Select botype=new Select(auditBusinessObjectDD);
			    botype.selectByVisibleText(botype1); 
			    CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 10);
			    //CommonUtils.hold(5);
		} 
		
		Select childObj=new Select(includeChildObjectDD);
		if(includeChildObjects) {
			childObj.selectByVisibleText("Yes");
		}
		else {
			childObj.selectByVisibleText("Yes");
		}
		
		Select outputFileObj=new Select(outputFileFormatDD);
		outputFileObj.selectByVisibleText(outputFileFormat);
		
	}
	
//***************************************************************************************************************************************
	public File getFile(final String fileName) throws Exception {
		File file = null;
		File[] files = null;
		//String filePath = null;
		File reqFile = null;
		try {
			file = new File(GetDriverInstance.fsmExpImpFile);
			FileFilter filter = new FileFilter() {
				public boolean accept(File file) {
					if (file.getName().endsWith(fileName))
						return true;

					return false;
				}

			};

			files = file.listFiles(filter);
			for (File d : files) {
				System.out.println("File name is:"+ d.getName());
				System.out.println("File path is:"+ d.toString());
				reqFile = d;
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reqFile;

	}
//*************************************************************************************************************************************
	
	public File getFile1(final String fileName) throws Exception {
		File file = null;
		File[] files = null;
		//String filePath = null;
		File reqFile = null;
		try {
			file = new File(GetDriverInstance.fsmExpImpFile);
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File file, String name) {
					if (name.endsWith(fileName))
						return true;

					return false;
				}

			};

			files = file.listFiles(filter);
			for (File d : files) {
				System.out.println("File name is:"+ d.getName());
				reqFile = d;
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reqFile;

	}
	
	
}
