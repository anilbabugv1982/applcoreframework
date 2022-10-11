package UtilClasses.UI;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;

public class NavigationTaskFlows extends CommonUtilsDefs{
	
	private GlobalPageTemplate gptInstance_nTF;
	private SetupAndMaintenance sAMInstance_nTF;
	private NavigatorMenuElements nMEInstance_nTF;
	
	public NavigationTaskFlows(WebDriver driver) {
		super(driver);
		gptInstance_nTF = new GlobalPageTemplate(driver);
		sAMInstance_nTF = new SetupAndMaintenance(driver);
		nMEInstance_nTF = new NavigatorMenuElements(driver);
	}
	
	public void navigateToTask(WebElement element, WebDriver driver) throws Exception {
	
		try {
			CommonUtils.explicitWait(gptInstance_nTF.navigatorButton, ExplicitWaitCalls.Click.toString(), "",driver);
			CommonUtils.hold(5);
			System.out.println("Navigator Button Found");
			gptInstance_nTF.navigatorButton.click();
			CommonUtils.explicitWait(nMEInstance_nTF.showMoreOrLessLink, ExplicitWaitCalls.Click.toString(), "",driver);
					
			 /*
			  * Check for newsFeedEnabled
			  */
			
			/*if(ApplicationLogin.newsFeedEnabled) {
				System.out.println("NewsFeed is Enabled, value of newsFeedEnabled : "+ApplicationLogin.newsFeedEnabled);*/
				try {
					System.out.println("<"+nMEInstance_nTF.showMoreOrLessLink.getText()+"> element activated in Navigator Menu");
					CommonUtils.hold(5);
					if(nMEInstance_nTF.showMoreOrLessLink.getText().contains("More")) {
						System.out.println("INFO : <Show More> link activated. Clicking on <Show More> to expand Menu Items");
						nMEInstance_nTF.showMoreOrLessLink.click();
						CommonUtils.explicitWait(element, "Click", "", driver);
						CommonUtils.hold(5);
						System.out.println("INFO : <Show More> link clicked and Menu Items expanded");
						System.out.println("INFO : Clicking on -> "+element+" <- element");
						element.click();
						System.out.println("Navigator element clicked");
					}else if(nMEInstance_nTF.showMoreOrLessLink.getText().contains("Less"))
					{
						System.out.println("INFO : <Show Less> link activated and Menu Items are in expanded State");
						CommonUtils.hold(5);
						System.out.println("INFO : Clicking on -> "+element+" <- element");
						element.click();
						System.out.println("INFO : Navigator element clicked");
					}
				}catch(Exception nee) {
					System.out.println("ERROR : Element "+element+" doesn't exist in navigator menu. Please recheck the element that has been passed. "+nee.getMessage());
				}
		//	}
			/*else {
				System.out.println("INFO : =================== NewsFeed Is Not Enabled In The Env ==============================");
				WebElement moreElement;
				boolean isElementDisplayed = false;

				try {
					CommonUtils.explicitWait(element, ExplicitWaitCalls.Click.toString(), "",driver);
					CommonUtils.hold(5);
					System.out.println("INFO : Searching for an element");
					element.isDisplayed();
					System.out.println("INFO : Element found");
				} catch (WebDriverException e) {
					isElementDisplayed = true;
					System.out.println("ERROR : Element not found");
				}
				
				if (isElementDisplayed) {
					try {
						System.out.println("Before More Element Check");
						WebElement scrollPopUp = driver.findElement(By.xpath("//div[contains(@id ,'pt1:nv_pgl3')]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", scrollPopUp);
						CommonUtils.hold(3);
						moreElement = driver.findElement(By.xpath("//a[text()='More...']"));
						System.out.println("After More Element Check");
						moreElement.click();
						
						CommonUtils.hold(5);
						
						WebElement elementscrollPopUp = driver.findElement(By.xpath("//div[contains(@id , 'pt1:nv_pgl4')]"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", elementscrollPopUp);
						CommonUtils.hold(5);
						element.click();
						System.out.println("Element Visibled and Clicked");
					}catch(Exception e) {
						System.out.println("Element Not found");
						e.printStackTrace();
					}
						CommonUtils.hold(5);
				} else{
					element.click();
					CommonUtils.hold(5);
					}
			}//End Of NewsFeed enabled condition check
	*/
		}catch(Exception nTTE) {
			System.out.println("Exception in navigateToTask()");
			nTTE.printStackTrace();
		}	
	}
	
	/*
	 * verifyMenuItem() will verify presence of webelement passed as a parameter in navigator menu
	 */
	public String verifyMenuItem(String elementToVerify, boolean returnElement, WebDriver driver) throws Exception {

		String eleFound = "NotFound";

		try {
			CommonUtils.explicitWait(gptInstance_nTF.navigatorButton, ExplicitWaitCalls.Click.toString(), "",driver);
			CommonUtils.hold(3);
			System.out.println("Navigator Button Found");
			gptInstance_nTF.navigatorButton.click();
			//CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text()='"+element+"']")), ExplicitWaitCalls.Click.toString(), "",driver);
			CommonUtils.explicitWait(nMEInstance_nTF.showMoreOrLessLink, ExplicitWaitCalls.Click.toString(), "",driver);
			
			 /*
			  * Check for newsFeedEnabled
			  */
		//	if(ApplicationLogin.newsFeedEnabled) {
				System.out.println("NewsFeed is Enabled, value of newsFeedEnabled : "+ApplicationLogin.newsFeedEnabled);
				try {
					System.out.println("<"+nMEInstance_nTF.showMoreOrLessLink.getText()+"> element activated in Navigator Menu");
					CommonUtils.hold(5);
					if(nMEInstance_nTF.showMoreOrLessLink.getText().contains("More")) {
						System.out.println("INFO : <Show More> link activated. Clicking on <Show More> to expand Menu Items");
						nMEInstance_nTF.showMoreOrLessLink.click();
						CommonUtils.hold(10);
						System.out.println("INFO : <Show More> link clicked and Menu Items expanded");
						System.out.println("INFO : Verifying for -> "+elementToVerify+" <- element");
							if(driver.findElement(By.xpath("//span[text()='"+elementToVerify+"']")).isDisplayed()) {
								System.out.println("INFO : element -> "+elementToVerify+" <- found");
								eleFound = "Found";
							}
					}else if(nMEInstance_nTF.showMoreOrLessLink.getText().contains("Less"))
					{
						System.out.println("INFO : <Show Less> link activated and Menu Items are in expanded State");
						CommonUtils.hold(5);
						System.out.println("INFO : Verifying for -> "+elementToVerify+" <- element");
						if(driver.findElement(By.xpath("//span[text()='"+elementToVerify+"']")).isDisplayed()) {
							System.out.println("INFO : element -> "+elementToVerify+" <- found");
							eleFound = "Found";
						}
					}
				}catch(Exception nee) {
					System.out.println("ERROR : Element "+elementToVerify+" doesn't exist in navigator menu. Please recheck the element that has been passed. "+nee.getMessage());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CommonUtils.hold(3);
		nMEInstance_nTF.nMenuClose.click();
		CommonUtils.hold(5);
		/*if(eleFound.equalsIgnoreCase("Found")) {
			driver.findElement(By.xpath("//span[text()='"+elementToVerify+"']")).click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
		}*/
	/*	CommonUtils.hold(5);
	 * 	
		WebElement moreElement;
		boolean isElementDisplayed = false;

		try {
			if(driver.findElement(By.xpath("//a[text()='"+element+"']")).isDisplayed())
				eleFound = "Found";
			if(returnElement)
				eleFound = driver.findElement(By.xpath("//a[text()='"+element+"']")).getText();
		} catch (WebDriverException e) {
			isElementDisplayed = true;
			eleFound = "NotFound";
		}
		
		if (isElementDisplayed) {
			WebElement scrollPopUp = driver.findElement(By.xpath("//div[contains(@id ,'pt1:nv_pgl3')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", scrollPopUp);
			CommonUtils.hold(3);
			moreElement = driver.findElement(By.xpath("//a[text()='More...']"));
			moreElement.click();
			CommonUtils.hold(5);
			try {
				WebElement elementscrollPopUp = driver.findElement(By.xpath("//div[contains(@id , 'pt1:nv_pgl4')]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", elementscrollPopUp);
				CommonUtils.hold(5);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text()='"+element+"']")), ExplicitWaitCalls.Click.toString(), "",driver);
				if(driver.findElement(By.xpath("//a[text()='"+element+"']")).isDisplayed())
					eleFound = "Found";
				if(returnElement)
					eleFound = driver.findElement(By.xpath("//a[text()='"+element+"']")).getText();
			}catch(Exception vMIE){
				CommonUtils.hold(5);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text()='"+element+"']")), ExplicitWaitCalls.Click.toString(), "",driver);
				if(driver.findElement(By.xpath("//a[text()='"+element+"']")).isDisplayed()) {
					if(returnElement)
						eleFound = driver.findElement(By.xpath("//a[text()='"+element+"']")).getText();
					else
						eleFound = "Found";
				}else {
					eleFound = "NotFound";
				}
			}
		} */
		return eleFound;
	}
	
	 /*
     * Navigate to taskflow from "SetupAndMaintainance" "Search Task" page 
     */
	public void navigateToAOLTfSearchPage(String taskFlowName, WebDriver driver) {
		try { 
		searchField.clear();
		searchField.sendKeys(taskFlowName);
		CommonUtils.explicitWait(searchButton, "Click", "",driver);
		searchButton.click();
		CommonUtils.hold(4);
		sAMInstance_nTF.setTaskName(taskFlowName);
		CommonUtils.hold(4);
		sAMInstance_nTF.clickTask(driver);
		CommonUtils.waitForPageLoad(driver);
		
		}catch(Exception ntAFSPE) {
		System.out.println("Exception in navigateToAOLTfSearchPage()");
		ntAFSPE.printStackTrace();
		}
	}
	
	/*
	 * navigateToAOLTaskFlows(String taskFlowName, WebDriver driver) will navigate to AOL taskflow
	 */
	public void navigateToAOLTaskFlows(String taskFlowName, WebDriver driver) {
		try {
			
			CommonUtils.waitForPageLoad(driver);
			sAMInstance_nTF.verifySetupAndMaintenancePage(driver);
			CommonUtils.hold(2);
			CommonUtils.explicitWait(panelDrawer, "Click", "",driver);
			panelDrawer.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(searchLink, "Click", "",driver);
			searchLink.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(searchField, "Visible", "",driver);
			searchField.clear();
			searchField.sendKeys(taskFlowName);
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.explicitWait(searchButton, "Click", "",driver);
			searchButton.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(8);
			sAMInstance_nTF.setTaskName(taskFlowName);
			sAMInstance_nTF.clickTask(driver);
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
 
 
	public void createFSMExpImpZipFolder(String path) {
		File fsmFolder = new File(path);
		if (!fsmFolder.exists())
			fsmFolder.mkdir();
	}
	
	public SetupAndMaintenance getSAMInstance() {
		return sAMInstance_nTF;
	}
	
	public GlobalPageTemplate getGlobalPageInstance() {
		return gptInstance_nTF;
	}
 

}
