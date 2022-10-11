package pageDefinitions.UI.oracle.applcore.qa.Dff;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import org.apache.commons.math3.ml.neuralnet.UpdateAction;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Audit.CommonAuditSetup;

/**
 * @author Ashish Pareek
 *
 */

public class ManagePositions extends ManagePositionsPage{

	private NavigationTaskFlows navFlow;
	private Actions act;
    private JavascriptExecutor js;
    String id;
    private CommonAuditSetup AuditSetupinstance;
    
    //private CommonAuditSetup AuditSetupinstance=new CommonAuditSetup(AuditDrvInstance);
    
    
	public ManagePositions(WebDriver driver) {
		super(driver);
		navFlow = new NavigationTaskFlows(driver);
		AuditSetupinstance = new CommonAuditSetup(driver);
		
	}
	
	public void editPositionAttributes(String position, String att1Value, String pos_glbValue, String posglobal1Value, WebDriver driver) throws Exception {
		id = CommonUtils.uniqueId();
		searchAndClickPosition(position, driver);
		ifsavedtransactionexist();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(editPositionDetails, "Click", "", driver);
		CommonUtils.clickWithJavaScript(editPositionDetails, driver);
		//editPositionDetails.click();
		CommonUtils.explicitWait(updateButton, "Click", "", driver);
		updateButton.click();
		CommonUtils.explicitWait(updateOkButton, "Click", "", driver);
		updateOkButton.click();
		CommonUtils.waitForInvisibilityOfElement(updateOkButton, driver, 20);
		try {
			CommonUtils.hold(2);
			updateWarningOkButton.click();
		} catch(NoSuchElementException e) {
			System.out.println("Warning box not displayed.");
		}
		
		CommonUtils.explicitWait(att1_UK_001_Simple, "Click", "", driver);
		att1_UK_001_Simple.clear();
		att1_UK_001_Simple.sendKeys(att1Value);
		
		att2_UK_001_Simple.clear();
		CommonUtils.explicitWait(att2_UK_001_SimpleCal, "Click", "", driver);
		att2_UK_001_SimpleCal.click();
		try {
			//CommonUtils.explicitWait(att2_UK_001_Simple_CurrentDate, "Click", "", driver);
			CommonUtils.hold(10);
			att2_UK_001_Simple_CurrentDate.click();
		} catch (NoSuchElementException e) {
			CommonUtils.explicitWait(att2_UK_001_SimpleCal, "Click", "", driver);
			att2_UK_001_SimpleCal.click();
			CommonUtils.explicitWait(att2_UK_001_Simple_CurrentDate, "Click", "", driver);
			att2_UK_001_Simple_CurrentDate.click();
		}
		
		POS_GLB.clear();
		POS_GLB.sendKeys(pos_glbValue);
		
		posglobal1.clear();
		posglobal1.sendKeys(posglobal1Value);
		
		saveButton.click();
		CommonUtils.explicitWait(saveOkConfirmationButton, "Click", "", driver);
		saveOkConfirmationButton.click();
		CommonUtils.waitForInvisibilityOfElement(saveOkConfirmationButton, driver, 20);
		
		CommonUtils.explicitWait(reviewButton, "Click", "", driver);
		reviewButton.click();
		//new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOf(busyButton));
		CommonUtils.explicitWait(submitButton, "Click", "", driver);
		CommonUtils.hold(5);
		submitButton.click();
		CommonUtils.explicitWait(yesWarningButton, "Click", "", driver);
		yesWarningButton.click();
		CommonUtils.waitForInvisibilityOfElement(yesWarningButton, driver, 20);
		CommonUtils.explicitWait(okConfirmationButton, "Click", "", driver);
		okConfirmationButton.click();
		CommonUtils.waitForInvisibilityOfElement(okConfirmationButton, driver, 20);
		  
		 
	}
	
	public void searchAndClickPosition(String position, WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.explicitWait(positionName, "Click", "", driver);
		positionName.clear();
		positionName.sendKeys(position);
		CommonUtils.explicitWait(searchButton, "Click", "", driver);
		searchButton.click();
		String posXpath = "//a/span[normalize-space(text())='"+position+"']";
		AuditSetupinstance.waitTillXpathVisible(posXpath, 20, 2, driver);
		//CommonUtils.explicitWait(driver.findElement(By.xpath("//a/span[normalize-space(text())='"+position+"']")), "Click", "", driver);
		driver.findElement(By.xpath(posXpath)).click();
		CommonUtils.waitForInvisibilityOfElement(searchButton, driver, 15);
	}
	
	public void ifsavedtransactionexist() {
		try {
			deleteStartNew.click();
		} catch(NoSuchElementException e) {
			System.out.println("No saved transaction");
		}
	}
	
	

}
