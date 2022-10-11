package pageDefinitions.UI.oracle.applcore.qa.Kff;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;

/**
 * @author Ashish Pareek
 *
 */

// This class is created by Ashish Pareek to implement some generic KFF methods which can be used in Audit scripting
public class AuditManageKFFUtils extends ManageKFFSetup{

	public AuditManageKFFUtils(WebDriver driver) {
		super(driver); 
	}
	
	public  boolean isChecked;
	public static String flexfieldCode = "GL#";
	public static boolean deployStatus = true;
	public static String structureCode;
	public static String structureName;
	public static String kffDelimiter;
	public static String StructureDescription;
//***********************************************************************************************************************************************************
		public void deployFlexfield(WebDriver driver) {
			System.out.println("Preparing to deploy KFF flexfield");
			button_DeployFlexField.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
			/*
			if (CommonUtils.isElementPresent(AuditManageKFFStructuresPage.VerifyDeploymentInProgress)) {
				System.out.println("Key Flexfield " + flexfieldCode + " Deployment is in Progress!");
			}
			CommonUtils.hold(3);
			*/
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofMinutes(40)).pollingEvery(Duration.ofMillis(35000))
					.ignoring(StaleElementReferenceException.class);

			Boolean element = wait.until(new Function<WebDriver, Boolean>() {

				@Override
				public Boolean apply(WebDriver t) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					deploymentLog.click();
					CommonUtils.hold(3);
					return VerifyOKButtonEnable.isEnabled();
				}

			});

			CommonUtils.waitForPageLoad(driver);
			if (CommonUtils.isElementPresent(VerifyDeploymentStatusSuccess))
				System.out.println("Key Flexfield " + flexfieldCode + " Deployed Successfully!");
			else if (CommonUtils.isElementPresent(VerifyDeploymentStatusError)) {
				System.out.println("Key Flexfield " + flexfieldCode + " failed due to error in Deployment!");
				deployStatus = false;
			}
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(10);
			if (VerifyOKButtonEnable.isDisplayed())
				VerifyOKButtonEnable.click();
			Assert.assertTrue(deployStatus, "Failed due to error in Deployment Flexfield");
			CommonUtils.waitForPageLoad(driver);
		}

//***********************************************************************************************************************************************************
		
		public void clickManageKeyFlexfieldsSearch(WebDriver driver) throws Exception {
			CommonUtils.hold(5);
			CommonUtils.explicitWait(searchFlexfieldButton, ExplicitWaitCalls.Click.toString(),"",driver);
			searchFlexfieldButton.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(10);
		}
		
//***********************************************************************************************************************************************************
		
		public void clickManageStructure(WebDriver driver) throws Exception {
			CommonUtils.explicitWait(button_ManageStructures, ExplicitWaitCalls.Click.toString(), "", driver);
			button_ManageStructures.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
		}

//***********************************************************************************************************************************************************
		
		public String[] createStructure(WebDriver driver, String delimiterValue) throws Exception {
			
			switch (delimiterValue) {
			case ("*"):
				kffDelimiter = "Asterisk";
				break;
			
			case ("."):
				kffDelimiter = "Period";
				break;
			
			default:
				Log.info("invalid dilimiter value");
				Assert.assertTrue(false, "invalid dilimiter value");
			}
			CommonUtils.explicitWait(createStructureIcon, ExplicitWaitCalls.Click.toString(), "", driver);
			createStructureIcon.click();
			Log.info("Clicked on Create Structure Icon.");
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
			
			structureCode = "STR"+CommonUtils.uniqueId();
			structureName = "STRNAME"+CommonUtils.uniqueId();
			StructureDescription = "STRDESC"+CommonUtils.uniqueId();
			CommonUtils.explicitWait(structureCodeField, ExplicitWaitCalls.Visible.toString(),"",driver);
			structureCodeField.sendKeys(structureCode);
			input_structureName.sendKeys(structureName);
			input_description.sendKeys(StructureDescription); 
			
			
			Select select = new Select(driver.findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:soc1::content")));
			select.selectByVisibleText(delimiterValue);
			
			if (structureEnabledCheckbox.isSelected()) {
				structureEnabledCheckboxLabel.click();
				CommonUtils.hold(5);
				
			}
			
			saveAndCloseButton.click();
			Log.info("Clicked on Save and Closed.");
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
			
			return new String[] {structureCode, structureName, kffDelimiter, StructureDescription};
		}

//***********************************************************************************************************************************************************
		
		public void validateCreatedStructure(WebDriver driver) throws Exception {
			CommonUtils.explicitWait(structureCodeField, ExplicitWaitCalls.Visible.toString(),"",driver);
			structureCodeField.sendKeys(structureCode);
			
			StructureSearchButton.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
			
		/*	List<WebElement> structures =driver.findElements(By.xpath("//span[contains(@id,'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:AT2:_ATp:ATt2') and contains(@id,':ot9')]"));
			boolean flag = false;
			for(WebElement ee:structures) {
				if (ee.getText().equalsIgnoreCase(structureCode)) {
					flag = true;
					ee.click();
					Log.info("Clicked on Created Structure.");
					break;
				}
			}
			
			if (!flag) {
				Assert.assertTrue(false, "Created Structure \""+structureCode+"\" not found.");
				Log.info("Created Structure \""+structureCode+"\" not found.");
			}
			*/
			
			WebElement ele = driver.findElement(By.xpath("//span[normalize-space(text())='"+structureCode+"']"));
			if (ele.isDisplayed()) {
				ele.click();
				Log.info("Clicked on Created Structure.");
			}
			else {
				Assert.assertTrue(false, "Created Structure \""+structureCode+"\" not found.");
				Log.info("Created Structure \""+structureCode+"\" not found.");
			}
			
			doneButtonManageKFFSTRS.click();
			Log.info("Clicked on Done button of Manage Key Flex Field Structures page.");
			CommonUtils.waitForPageLoad(driver);
			
			CommonUtils.explicitWait(doneButtonManageKFF, ExplicitWaitCalls.Click.toString(),"",driver);
			doneButtonManageKFF.click();
			Log.info("Clicked on Done button of Manage Key Flex Fields page.");
			CommonUtils.waitForPageLoad(driver);
		}
		
//***********************************************************************************************************************************************************		
		public void clickMDSPFlexfieldCode(WebDriver driver) {
			MDSPFlexfieldCode.click();
			
		}
		
}
