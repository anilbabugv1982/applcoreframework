package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import static org.testng.Assert.assertTrue;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ScheduleProcessObject;

public class SaaSPaaS_ScheduleProcess {
	ScheduleProcessObject processObj;

	public SaaSPaaS_ScheduleProcess(WebDriver driver) {
		processObj = new ScheduleProcessObject(driver);
		
	}
	
	public File createEssJobForSaaSPaaSMigration(String processname, int timeout, int interval, boolean downloadLog, WebDriver driver) throws Exception{

		//try {
			CommonUtils.hold(5);
			CommonUtils.waitExplicitly(50,2,ExpectedConditions.elementToBeClickable(processObj.scheduleprocess),driver);
			try {
				processObj.scheduleprocess.click();
			} catch (ElementClickInterceptedException e) {
				CommonUtils.hold(10);
				CommonUtils.explicitWait(processObj.scheduleprocess, "Click", "", driver);
				processObj.scheduleprocess.click();
			}
			CommonUtils.waitExplicitly(20,2,ExpectedConditions.elementToBeClickable(processObj.searchIcon),driver);
			SaaSPaaSCommonUtils.waitTillUILoad(10, driver);
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
			//CommonUtils.hold(4);
			SaaSPaaSCommonUtils.waitTillUILoad(20, driver);
			//WebElement jobName = driver.findElement(By.xpath("//span[contains(text(),'"+processname+"')]"));
			//WebElement jobName = driver.findElement(By.xpath("//input[normalize-space(@value)='"+processname+"']"));
			CommonUtils.waitExplicitly(10,2,ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[normalize-space(@value)='"+processname+"']"))),driver);
			CommonUtils.waitExplicitly(10,2,ExpectedConditions.elementToBeClickable(processObj.popupOK),driver);
			CommonUtils.hold(3);
			processObj.popupOK.click();
			//CommonUtils.hold(4);
			CommonUtils.waitForInvisibilityOfElement(processObj.popupOK,driver,8);
			SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
			CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(processObj.submit),driver);
			processObj.submit.click();
			SaaSPaaSCommonUtils.waitTillUILoad(15, driver);

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


			CommonUtils.explicitWait(processObj.expCollapseSearch, "Click", "", driver);
			if (processObj.expCollapseSearch.getAttribute("title").equals("Expand Search"))
				processObj.expCollapseSearch.click();
			
			CommonUtils.waitExplicitly(15,2,ExpectedConditions.elementToBeClickable(processObj.inputSearchField),driver);
			SaaSPaaSCommonUtils.waitTillUILoad(12, driver);
			processObj.inputSearchField.clear();
			processObj.inputSearchField.sendKeys(processId);
			processObj.searchButton.click();
			//CommonUtils.hold(4);
			SaaSPaaSCommonUtils.waitTillUILoad(20, driver);

			boolean status = false;
			CommonUtils.explicitWait(processObj.refresh, "Click", "", driver);
			processObj.refresh.click();
			SaaSPaaSCommonUtils.waitTillUILoad(15, driver);

			WebElement el1=null;
			WebElement el2=null;
			String processText="";

			long startTime=System.currentTimeMillis();
			long endTime=0;
			long totalTime=0;

			while (!status && totalTime < timeout*1000) {
				try {
					try {
						CommonUtils.waitExplicitly(4,2,ExpectedConditions.elementToBeClickable(processObj.searchButton),driver);
						processObj.searchButton.click();
					} catch(StaleElementReferenceException e1) {
						driver.findElement(By.xpath("//img[contains(@title,'Refresh')]")).click();
					}
					SaaSPaaSCommonUtils.waitTillUILoad(10, driver);
					CommonUtils.hold(2);
					el2 = driver.findElement(By.xpath("//*[contains(text(),'"+processId+"')]/following::*[normalize-space(text())='Succeeded']"));
					status = el2.isDisplayed();
					System.out.println("User Role Migration Job Status : Succeeded");

				} catch (Exception e) {
					el1 = driver.findElement(By.xpath("//*[contains(text(),'"+processId+"')]/following::*[contains(@id,'panel:result')][1]"));
					processText=el1.getText();
					
					if (processText.trim().equalsIgnoreCase("Retrying") || processText.toLowerCase().contains("error")) {
						System.out.println("User Role Migration Job Status : "+processText);
						Log.info("User Role Migration Job Status : "+processText);
						break;
					}
					else {
						System.out.println("User Role Migration Job Status : "+processText);
						CommonUtils.hold(interval);
					}
					
				}finally {
					endTime = System.currentTimeMillis();
					totalTime = (endTime-startTime);
					System.out.println("Finally Block- User Role Migration Job INFO : Total Time Taken : "+totalTime);
					
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


		/*} catch (Exception essJobFailed) {
			System.out.println("Error in method : " + essJobFailed.getMessage());
			essJobFailed.printStackTrace();
			return null;
		}*/
		

	}
	
	public File downloadLog(String processname, String processId, WebDriver driver) {
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
			
			CommonUtils.hold(10);
			CommonUtils.waitExplicitly(10,4,ExpectedConditions.elementToBeClickable(driver.findElement(By.partialLinkText(processId))),driver);
			WebElement logFileLink = driver.findElement(By.partialLinkText(processId));
//			CommonUtils.explicitWait(logFileLink, "Click", "", driver);
			logFileLink.click();
			CommonUtils.hold(10);
			
			File downloadEssLogFile1=new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator")+ processId + ".log");
			File downloadEssLogFile=new File(GetDriverInstance.fsmExpImpFile+ System.getProperty("file.separator") + processId + ".txt"); // Some times filew downloaded are with *.txt extesnsion
			
			

			if (downloadEssLogFile.isFile()) {
				System.out.println("INFO : Downloaded File Successfully : " + downloadEssLogFile);
				Assert.assertTrue(true, "Ess log file downloaded successfully for job ID : " + processId);
				return downloadEssLogFile;
			} else if (downloadEssLogFile1.isFile()) {
				System.out.println("INFO : Downloaded file succesfully : " + downloadEssLogFile1);
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
			Assert.fail("ERROR : Unable to download esslog file : " + downloadEssLgFile.getMessage());
			return null;
		} 
	}
	

}
