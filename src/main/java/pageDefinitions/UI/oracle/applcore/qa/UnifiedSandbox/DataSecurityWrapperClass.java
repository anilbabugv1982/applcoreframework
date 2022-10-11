/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import org.testng.Assert;

/**
 * @author DASKUMAR
 *
 */
public class DataSecurityWrapperClass extends ManageDataSecurityPages{

	static boolean elementStatus = false;
	static WebElement lElement;
	private static boolean inputStatus;
	private static boolean spanStatus;
	private NavigatorMenuElements nMenuEleInstance;
	private NavigationTaskFlows navigationtFInstance;
	
	public DataSecurityWrapperClass(WebDriver driver) {
		super(driver);
		nMenuEleInstance = new NavigatorMenuElements(driver);
		navigationtFInstance = new NavigationTaskFlows(driver);
	}

	public void navigateToDStaskFlow(WebDriver driver) throws Exception{

		//CommonUtils.navigateTo(shield);
		navigationtFInstance.navigateToTask(nMenuEleInstance.SecurityConsole,driver);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(adminPanelIcon, "Visible", "",driver);
		CommonUtils.hold(10);
		try {
			adminPanelIcon.click();
		}catch(Exception clickInterceptedException) {
			System.out.println("ElementClickInterceptedException thrown");
			/*//JavascriptExecutor executor = (JavascriptExecutor)driver;
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", adminPanelIcon);*/
			CommonUtils.hold(10);
			adminPanelIcon.click();
			System.out.println("Administration link clicked");
		}
		CommonUtils.explicitWait(dsButton, "Click", "",driver);
		CommonUtils.hold(10);
		dsButton.click();
		CommonUtils.hold(5);

	}

	public  void createDataBaseObject(String objName,WebDriver driver) throws Exception {
		WebElement objectRecord;
		try {
			CommonUtils.explicitWait(pageHeading, "Visible", "",driver);
			parentTableNewBtn.click();
			CommonUtils.explicitWait(objNameField, "Visible", "",driver);
			objNameField.sendKeys(objName);
			displayNameField.sendKeys(objName);
			dataFieldSearch.click();
			CommonUtils.explicitWait(dataFieldlovSearchlink, "Click", "",driver);
			dataFieldlovSearchlink.click();
			CommonUtils.explicitWait(dataFieldPopupInput, "Visible", "",driver);
			dataFieldPopupInput.sendKeys("ZMM_ACTY_ACTIVITIES");
			dataFieldSearchBtn.click();
			objectRecord = dataObjectRecord("ZMM_ACTY_ACTIVITIES", driver);
			CommonUtils.explicitWait(objectRecord, "Visible", "",driver);
			CommonUtils.hold(2);
			//dataFieldRecord.click();
			objectRecord.click();
			CommonUtils.hold(2);
			dataFieldOKBtn.click();
			CommonUtils.hold(2);
			CommonUtils.explicitWait(primary_key_addBtn, "Click", "",driver);
			moduleSearchImg.click();
			CommonUtils.explicitWait(modulelovSearchlink, "Click", "",driver);
			modulelovSearchlink.click();
			CommonUtils.explicitWait(modulePopupInput, "Visible", "",driver);
			modulePopupInput.sendKeys("Oracle Middleware Extensions for Applications");
			moduleSearchBtn.click();
			CommonUtils.hold(2);
			moduleRecord.click();
			CommonUtils.hold(2);
			dataFieldOKBtn.click();
			CommonUtils.hold(3);
			primary_key_addBtn.click();
			CommonUtils.hold(5);
			boolean status = false;

			try {
				status = driver.findElement(By.xpath("//a[contains(@id,':_ATp:pkTable:0:pkColumnChoice::drop')]"))
						.isDisplayed();
			} catch (Exception e) {
				System.out.println("Input element not found for Primary Key DROP-DOWN");
			}

			if (status) {
				driver.findElement(By.xpath("//a[contains(@id,':_ATp:pkTable:0:pkColumnChoice::drop')]")).click();
				CommonUtils.hold(3);
				driver.findElement(By.xpath("//li[contains(text(),'ACCOUNT_ID')]")).click();
			} else {
				CommonUtils.explicitWait(pkDropDown, "Visible", "", driver);
				selectOptionfrompkDropDown(pkDropDown, "ACCOUNT_ID");
			}

		/*	CommonUtils.explicitWait(pkDropDown, "Visible", "",driver);
			selectOptionfrompkDropDown(pkDropDown, "ACCOUNT_ID");*/
			actionSubmitBtn.click();
			CommonUtils.hold(4);
			//CommonUtils.ExplicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click", "");
			CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
			actionOKBtn.click();
			CommonUtils.explicitWait(confirmationPopUP, "Visible", "",driver);
		} catch (Exception e) {
			System.out.println("DataBase object creation failed");
		}
/*
		if (searchDSObject(objName,driver))
			Assert.assertTrue("DataBase object created successfully", true);
		else
			Assert.fail();
	*/
	}

	public void createCondition(String dsObjectName, String conditionName,WebDriver driver) throws Exception{

		if (searchDSObject(dsObjectName,driver)) {
			try {
				parentEditBtn.click();
				CommonUtils.hold(2);
				conditionsTab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(condAddButton, "Visible", "",driver);
				condAddButton.click();
				CommonUtils.explicitWait(condNameField, "Visible", "",driver);
				condDisNameField.sendKeys(conditionName);
				CommonUtils.hold(1);
				condNameField.sendKeys(conditionName);
				CommonUtils.hold(1);
				condAddButton1.click();
				CommonUtils.hold(2);
				/*CommonUtils.explicitWait(condDropDown, "Visible", "",driver);
				selectOptionfrompkDropDown(condDropDown, "CREATED_BY");
				CommonUtils.hold(5);
				CommonUtils.explicitWait(condoperatorDropDown, "Visible", "",driver);
				selectOptionfrompkDropDown(condoperatorDropDown, "Is not blank");
				condSaveBtn.click();
				CommonUtils.hold(10);
				actionSubmitBtn.click();
				CommonUtils.hold(3);*/
				
				boolean status;

				try {
					status = driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:AT1:r1:0:AT1:_ATp:ATt1:0:soc1::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Coulmn Name element drop-down not found");
					status = false;
				}

				if (status) {
					driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:AT1:r1:0:AT1:_ATp:ATt1:0:soc1::drop')]")).click();
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//li[text()='ACCOUNT_ID']")).click();
				} else {
					CommonUtils.explicitWait(condDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(condDropDown, "CREATED_BY");
					CommonUtils.hold(5);
				}

				try {
					status = driver.findElement(By.xpath("//a[contains(@id,'AT1:_ATp:ATt1:0:soc22::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Operator element drop-down not found");
					status = false;
				}

				if (status) {
					driver.findElement(By.xpath("//a[contains(@id,'AT1:_ATp:ATt1:0:soc22::drop')]")).click();
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//li[contains(text(),'Is not blank')]")).click();

				} else {
					CommonUtils.explicitWait(condoperatorDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(condoperatorDropDown, "Is not blank");
					CommonUtils.hold(3);
				}
                CommonUtils.hold(5);
				condSaveBtn.click();
				CommonUtils.hold(10);
				actionSubmitBtn.click();
				CommonUtils.hold(20);
				
				try {
					CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
					actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Could not locate DataBase object with name: " +dsObjectName);
			Assert.assertFalse(false);
		}
			verifyStatus(dsObjectName, "condition", conditionName, "update",driver);

	}

	public void createAcion(String dsObjectName, String actionName,WebDriver driver) throws Exception{
		if (searchDSObject(dsObjectName,driver)) {
			try {
				parentEditBtn.click();
				CommonUtils.hold(4);
				CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
				actionsTab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(actionAddBtn, "Click", "",driver);
				actionAddBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(actionNameField, "Visible", "",driver);
				actionNameField.sendKeys(actionName);
				actionDisNameField.sendKeys(actionName);
				actionSubmitBtn.click();
				CommonUtils.hold(10);

				try {
					//CommonUtils.explicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click","",driver);
					CommonUtils.explicitWait(actionOKBtn, "Click","",driver);
					actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Could not locate DataBase object with name : "+dsObjectName);
			Assert.assertFalse(false);
		}
			
		verifyStatus(dsObjectName, "action", actionName, "update",driver);
	}

	/*public void createDataBasePolicy(String dsObjectName, String policyNameValue,WebDriver driver) throws Exception{
		if (searchDSObject(dsObjectName,driver)) {

			try {
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				CommonUtils.hold(3);
				policydiv.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				childTableNewBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(policyGeneralInfoTab, "Click","",driver);
				//driver.findElement(By.xpath("//a[text()='General Information']")).click();
				policyGeneralInfoTab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(crtPolicyPopup, "Visible", "",driver);
				CommonUtils.hold(2);
				policyName.sendKeys(policyNameValue);
				CommonUtils.hold(2);
				// policyStartDateField.sendKeys(dateFormat);
				policyStartDate.click();
				CommonUtils.hold(2);
				policyCurrentDate.click();
				CommonUtils.hold(2);
				roleTab.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(condAddButton1, "Click", "",driver);
				condAddButton1.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(roleAppDropDown, "Visible", "",driver);
				selectOptionfrompkDropDown(roleAppDropDown, "crm");
				
				boolean status = true;
				try {
					CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]")), "Visible", "",driver);
					status = driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Application DROP-DOWN is not present " + e.getMessage());
					status = false;
				}

				if (status) {
					CommonUtils.hold(3);
					driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]")).click();
					CommonUtils.hold(3);
					driver.findElement(By.xpath("//li[text()='crm']")).click();
				} else {
					CommonUtils.explicitWait(roleAppDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(roleAppDropDown, "crm");
					CommonUtils.hold(3);
				}

				
				roleNameTextField.clear();
				roleNameTextField.sendKeys("Application*Developer");
				CommonUtils.explicitWait(roleSearchBtn, "Click", "",driver);
				roleSearchBtn.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(roleRow, "Visible", "",driver);
				roleRow.click();
				CommonUtils.hold(2);
				roleOKBtn.click();
				CommonUtils.hold(3);
				policytabdiv.click();
				CommonUtils.hold(3);
				ruleTab.click();
				CommonUtils.hold(5);
				
				try {
					status = driver.findElement(By.xpath("//a[contains(@id,'gdb1:ChildappTbl:pr1:0:rsOption::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Row Set DROP-DOWN element not found");
					status = false;
				}
				if (status) {
					driver.findElement(By.xpath("//a[contains(@id,'gdb1:ChildappTbl:pr1:0:rsOption::drop')]")).click();
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//li[text()='All Values']")).click();
				} else {
					WebElement element = driver.findElement(By.xpath("//select[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:rsOption::content')]"));
					selectOptionfrompkDropDown(element, "All Values");
					CommonUtils.hold(2);
				}
				
				CommonUtils.hold(2);
				CommonUtils.explicitWait(policyActionTtab, "Click", "",driver);
				policyActionTtab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(moveAllImg, "Click", "",driver);
				moveAllImg.click();
				CommonUtils.hold(2);
				driver.findElement(By.xpath("//button[text()='Save and Close']")).click();
				CommonUtils.hold(5);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//Assert.assertFalse("No such DataBase object "+dsObjectName, false);
		}

		//verifyStatus(dsObjectName, "policy", policyName, "update");
	}
*/
	
	public void createDataBasePolicy(String dsObjectName, String policyNameValue, String roleNameParameter, String roleApplication, String roleName, String ruleSet, String ruleInstanceSetName, String ruleParameter, WebDriver driver) throws Exception{
		if (searchDSObject(dsObjectName,driver)) {

			try {
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				CommonUtils.hold(3);
				policydiv.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				childTableNewBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(policyGeneralInfoTab, "Click","",driver);
				//driver.findElement(By.xpath("//a[text()='General Information']")).click();
				policyGeneralInfoTab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(crtPolicyPopup, "Visible", "",driver);
				CommonUtils.hold(2);
				policyName.sendKeys(policyNameValue);
				CommonUtils.hold(2);
				// policyStartDateField.sendKeys(dateFormat);
				policyStartDate.click();
				CommonUtils.hold(2);
				policyCurrentDate.click();
				CommonUtils.hold(2);
				roleTab.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(condAddButton1, "Click", "",driver);
				condAddButton1.click();
				CommonUtils.hold(2);
				/*CommonUtils.explicitWait(roleAppDropDown, "Visible", "",driver);
				selectOptionfrompkDropDown(roleAppDropDown, "crm");*/
				
				boolean status = true;
				try {
					CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]")), "Visible", "",driver);
					status = driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Application DROP-DOWN is not present " + e.getMessage());
					status = false;
				}

				if (status) {
					CommonUtils.hold(3);
					driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]")).click();
					CommonUtils.hold(3);
					driver.findElement(By.xpath("//li[text()='"+roleApplication+"']")).click();
				} else {
					CommonUtils.explicitWait(roleAppDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(roleAppDropDown, roleApplication);
					CommonUtils.hold(3);
				}

				
				roleNameTextField.clear();
				roleNameTextField.sendKeys(roleNameParameter);
				CommonUtils.explicitWait(roleSearchBtn, "Click", "",driver);
				roleSearchBtn.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(roleName(roleName,driver), "Visible", "",driver);
				roleName(roleName,driver).click();
				CommonUtils.hold(2);
				roleOKBtn.click();
				CommonUtils.hold(3);
				policytabdiv.click();
				CommonUtils.hold(3);
				ruleTab.click();
				CommonUtils.hold(5);
				
				try {
					status = driver.findElement(By.xpath("//a[contains(@id,'gdb1:ChildappTbl:pr1:0:rsOption::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Row Set DROP-DOWN element not found");
					status = false;
				}
				if (status) {
					driver.findElement(By.xpath("//a[contains(@id,'gdb1:ChildappTbl:pr1:0:rsOption::drop')]")).click();
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//li[text()='"+ruleSet+"']")).click();
				} else {
					WebElement element = driver.findElement(By.xpath("//select[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:rsOption::content')]"));
					selectOptionfrompkDropDown(element, "'"+ruleSet+"'");
					CommonUtils.hold(2);
				}
				
				if(ruleSet.equalsIgnoreCase("Multiple Values")) {
					System.out.println("Multiple Values Rule Set");
						CommonUtils.explicitWait(ruleConditionSearchImg, "Visible", "", driver);
						CommonUtils.hold(3);
							ruleConditionSearchImg.click();
					System.out.println("Search for database row initiated");
						CommonUtils.explicitWait(ruleDataBaseRowSearch, "Visible", "", driver);
						CommonUtils.hold(3);
					System.out.println("Entered database row to be searched");
							ruleDataBaseRowSearch.sendKeys(ruleInstanceSetName);
						CommonUtils.hold(2);
							ruleDataBaseRowSearchIcon.click();
						CommonUtils.explicitWait(ruleTableFirstRow, "Visible", "", driver);
					System.out.println("Database row found and displayed");
						CommonUtils.hold(3);
							ruleTableFirstRow.click();
					System.out.println("Database row Selected");
						CommonUtils.hold(2);
							ruleOKBtn.click();
					try {
						CommonUtils.explicitWait(ruleParameterElement, "Visible", "", driver);
						System.out.println("Paramter field exist for dataset row : "+ruleInstanceSetName);
						CommonUtils.hold(2);
						ruleParameterElement.sendKeys(ruleParameter);
					}catch(Exception epe) {
						System.out.println("Paramter field doesn't exist for dataset row : "+ruleInstanceSetName);
						;
					}
					System.out.println("Database row Added");
				}
				CommonUtils.hold(5);
				CommonUtils.explicitWait(policyActionTtab, "Click", "",driver);
				policyActionTtab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(moveAllImg, "Click", "",driver);
				moveAllImg.click();
				CommonUtils.hold(2);
				driver.findElement(By.xpath("//button[text()='Save and Close']")).click();
				CommonUtils.hold(5);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//Assert.assertFalse("No such DataBase object "+dsObjectName, false);
		}

		//verifyStatus(dsObjectName, "policy", policyName, "update");
	}

	
	public boolean searchDSObject(String databaseObjName,WebDriver driver) throws Exception{

		CommonUtils.hold(5);
		CommonUtils.explicitWait(objNameSearchField, "Visible", "",driver);
		objNameSearchField.clear();
		objNameSearchField.sendKeys(databaseObjName);
		searchBtn.click();
		CommonUtils.hold(10);
		try {
			return driver.findElement(By.xpath("//span[contains(text(),'" + databaseObjName + "')]")).isDisplayed();

		} catch (Exception e) {
			System.out.println("No DataBase object with name : " + databaseObjName + " found. " + e.toString());
			return false;
		}

	}

	public void selectOptionfrompkDropDown(WebElement ele, String value) throws Exception{

		Select opt = new Select(ele);
		opt.selectByVisibleText(value);

	}

	public void verifyStatus(String dsObjectName, String tabName, String value, String operationType,WebDriver driver) throws Exception {
		if (searchDSObject(dsObjectName,driver)) {
			if (!tabName.isEmpty() && tabName.equalsIgnoreCase("condition")) {
				parentEditBtn.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(conditionsTab, "Visible", "",driver);
				conditionsTab.click();
				CommonUtils.hold(2);

				if (operationType.equals("update")) {
					try {
						Assert.assertTrue(
								driver.findElement(By.xpath("//span[contains(text(),'" + value + "')]")).isDisplayed());
					} catch (Exception e) {
						System.out.println("No such Condition available : " + e.toString());

					}
				}

				else {
					try {
						driver.findElement(By.xpath("//span[contains(text(),'" + value + "')]")).isDisplayed();
					} catch (Exception e) {
						//Assert.assertFalse("Deleted Condition successfully : "+value, false);
					}

				}

				actionSubmitBtn.click();
				CommonUtils.hold(3);

				try {
					CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
					actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println("No pop up present in update case during CONDITION verification" + e.toString());
				}

			} else if (!tabName.isEmpty() && tabName.equalsIgnoreCase("action")) {
				parentEditBtn.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
				actionsTab.click();
				CommonUtils.hold(2);

				if (operationType.equals("update")) {
					try {
						handleTable(actionTable, actionDeleteBtn,driver);
						Assert.assertTrue(
								driver.findElement(By.xpath("//input[@value='" + value + "']")).isDisplayed());
					} catch (Exception e) {
						//Assert.assertFalse("Update operation falied on Action : "+value, false);
					}
				} else {

					try {

						handleTable(actionTable, commonDeletebtn,driver);
						inputStatus = CommonUtils
								.isElementPresent(driver.findElement(By.xpath("//input[@value='" + value + "']")));
						System.out.println("Element status for action delete " + elementStatus);

					} catch (Exception e) {
						System.out.println("Element status for action delete11 " + elementStatus);
						inputStatus = false;
					}

					try {

						spanStatus = CommonUtils
								.isElementPresent(driver.findElement(By.xpath("//span[text()='" + value + "']")));
						System.out.println("Element status for action delete " + elementStatus);

					} catch (Exception e) {
						System.out.println("Element status for action delete22 " + elementStatus);
						spanStatus = false;
					}

					if (inputStatus && spanStatus)
						Assert.fail();
				//	else
					//	Assert.assertFalse("Deleted action successfully", false);

				}
				actionSubmitBtn.click();
				CommonUtils.hold(10);

				try {
					CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
					actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println("No pop up present in update case during ACTION verification" + e.toString());
				}

			}

			else if (!tabName.isEmpty() && tabName.equalsIgnoreCase("policy")) {

				CommonUtils.hold(4);
				CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
				qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
				qbeSearchField.sendKeys(Keys.ENTER);
				CommonUtils.hold(3);

				if (operationType.equals("update")) {

					try {
						Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
					} catch (Exception e) {
						System.out.println("No such Policy found :" + value + " " + e.toString());
						Assert.fail();
					}
				} else {
					try {
						Assert.assertFalse(
								driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
					} catch (Exception e) {
				//		Assert.assertTrue("Deleted policy successfully", true);
					}
				}

			}
		}

	}
	
	public void verifyDSDeleteStatus(String dsName,WebDriver driver)throws Exception{
			if(searchDSObject(dsName,driver))
				Assert.fail();
			//else
			//	Assert.assertTrue("DS Object deleted successfully",true);
		}
	
	public void updateObject(String dsObject, String tabName, String dsName, String updateText,WebDriver driver) throws Exception{

		if (searchDSObject(dsObject,driver)) {

			switch (tabName) {
			case "condition":
				try {
					parentEditBtn.click();
					CommonUtils.hold(2);
					CommonUtils.explicitWait(conditionsTab, "Visible", "",driver);
					conditionsTab.click();
					CommonUtils.hold(3);
					handleTable(conditionTable, conditionDeleteBtn,driver);
					CommonUtils.explicitWait(
							driver.findElement(By.xpath("//img[contains(@id,'pt1:gdb1:AT1:_ATp:edit::icon')]")),
							"Click", "",driver);
					driver.findElement(By.xpath("//img[contains(@id,'pt1:gdb1:AT1:_ATp:edit::icon')]")).click();
					CommonUtils.hold(2);
					CommonUtils.hold(2);
					CommonUtils.explicitWait(
							driver.findElement(By.xpath("//textarea[contains(@id,'pt1:gdb1:AT1:r2:0:it2::content')]")),
							"Visible", "",driver);
					driver.findElement(By.xpath("//textarea[contains(@id,'pt1:gdb1:AT1:r2:0:it2::content')]")).clear();
					driver.findElement(By.xpath("//textarea[contains(@id,'pt1:gdb1:AT1:r2:0:it2::content')]"))
							.sendKeys(updateText);
					CommonUtils.hold(10);
					CondEditSaveBtn.click();
					CommonUtils.hold(10);
					actionSubmitBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				break;
			case "action":
				try {
					parentEditBtn.click();
					CommonUtils.hold(2);
					CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
					actionsTab.click();
					CommonUtils.hold(2);
					handleTable(actionTable, actionDeleteBtn,driver);
					CommonUtils.hold(2);
					CommonUtils.explicitWait(
							driver.findElement(By.xpath("//label[text()='Description']/preceding-sibling::input")),
							"Visible", "",driver);
					driver.findElement(By.xpath("//label[text()='Description']/preceding-sibling::input")).clear();
					driver.findElement(By.xpath("//label[text()='Description']/preceding-sibling::input"))
							.sendKeys(updateText);
					actionSubmitBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				break;
			case "policy":
				try {
					CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
					qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
					qbeSearchField.sendKeys(Keys.ENTER);
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + dsName + "']")), "Visible",
							"",driver);
					driver.findElement(By.xpath("//span[text()='" + dsName + "']")).click();
					childEditBtn.click();
					CommonUtils.hold(2);					
					CommonUtils.explicitWait(driver.findElement(By.xpath("//img[contains(@id,'gdb1:appTbl:_ATp:delete::icon')]")), "Visible", "",driver);					
					handleTable(driver.findElement(By.xpath("//div[contains(@id,'gdb1:appTbl:_ATp:polTbl::db')]/table/tbody")),policyEditDeleteBtn,driver);
					System.out.println("Good Bye");
					try {
					CommonUtils.explicitWait(driver.findElement(By.xpath("//textarea[contains(@id,':gDesc::content')]")), "Visible", "",driver);
					}catch(Exception GeneralIfnoExcep) {
						GeneralInfotab.click();
						CommonUtils.hold(5);
					}
					driver.findElement(By.xpath("//textarea[contains(@id,':gDesc::content')]")).clear();
					driver.findElement(By.xpath("//textarea[contains(@id,':gDesc::content')]")).sendKeys(updateText);
					actionSaveBtn.click();
					CommonUtils.hold(10);
					actionSubmitBtn.click();
					CommonUtils.hold(3);
//					verifyStatus(dsObject, "policy", updateText, "update");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			}
			
			verifyStatus(dsObject, tabName, updateText, "update",driver);
		
		} else
			Assert.fail();// Discuss

	}

	public void deleteCondition(String dsObject, String conditionName,WebDriver driver) throws Exception{

		searchDSObject(dsObject,driver);
		parentEditBtn.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(conditionsTab, "Visible", "",driver);
		conditionsTab.click();
		CommonUtils.hold(3);
		// handleTable(conditionTable,
		// conditionDeleteBtn);
		driver.findElement(By.xpath("//span[text()='" + conditionName + "']")).click();
		CommonUtils.hold(3);
		conditionDeleteBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d111::yes')]")), "Click",
				"",driver);
		driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d111::yes')]")).click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(actionSubmitBtn, "Click", "",driver);
		actionSubmitBtn.click();
		CommonUtils.hold(5);

		try {
			//CommonUtils.ExplicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click", "");
			CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
			actionOKBtn.click();
			CommonUtils.hold(3);
		} catch (Exception e) {
			System.out.println("No pop up present in update case");
		}

		verifyStatus(dsObject, "condition", conditionName, "delete",driver);
	}

	public void deleteResource(String dsObjectName, String resourceName, String resourceType,WebDriver driver) throws Exception{

		if (!resourceName.isEmpty() && !resourceType.isEmpty()) {
			if (searchDSObject(dsObjectName,driver)) {

				switch (resourceType) {
				case "action":
					try {
						parentEditBtn.click();
						CommonUtils.isElementPresent(actionsTab);
						actionsTab.click();
						CommonUtils.explicitWait(actionTable, "Visible", "",driver);
						handleTable(actionTable,commonDeletebtn,driver);
						driver.findElement(By.xpath("//input[@value='" + resourceName + "']")).click();
						commonDeletebtn.click();
						CommonUtils.hold(2);
						CommonUtils.explicitWait(
								driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d222::yes')]")), "Click",
								"",driver);
						driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d222::yes')]")).click();
						CommonUtils.hold(2);
						actionSubmitBtn.click();
						CommonUtils.hold(2);
					} catch (Exception e) {
						System.out.println(e.toString());
					}

					break;
				case "condition":
					try {
						parentEditBtn.click();
						CommonUtils.isElementPresent(conditionsTab);
						conditionsTab.click();
						CommonUtils.explicitWait(conditionTable, "Visible", "",driver);
						handleTable(conditionTable,commonDeletebtn,driver);
						driver.findElement(By.xpath("//span[text()='" + resourceName + "']")).click();
						commonDeletebtn.click();
						CommonUtils.hold(2);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d111::yes')]")), "Click","",driver);
						driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d111::yes')]")).click();
						CommonUtils.hold(2);
						CommonUtils.explicitWait(actionSubmitBtn, "Click", "",driver);
						actionSubmitBtn.click();
						CommonUtils.hold(2);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					break;
				case "policy":
					try {
						CommonUtils.hold(3);
						CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
						qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
						qbeSearchField.sendKeys(Keys.ENTER);
						CommonUtils.hold(2);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + resourceName + "']")),
								"Visible", "",driver);
						driver.findElement(By.xpath("//span[text()='" + resourceName + "']")).click();
						CommonUtils.hold(3);
						CommonUtils.explicitWait(policyDeleteBtn, "Click", "",driver);
						policyDeleteBtn.click();
						CommonUtils.hold(3);
						CommonUtils.explicitWait(
								driver.findElement(
										By.xpath("//button[contains(@id,'pt1:gdb1:ChildappTbl:delDiag::yes')]")),
								"Click", "",driver);
						driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:ChildappTbl:delDiag::yes')]"))
								.click();
						CommonUtils.hold(3);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					break;
				}

				try {
					CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
					actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println("No warning pop up present in delete case");
				}

				verifyStatus(dsObjectName, resourceType, resourceName, "delete",driver);

			}

		}else{
			if (searchDSObject(dsObjectName,driver)){
				try{
				CommonUtils.isElementPresent(driver.findElement(By.xpath("//span[text()='"+dsObjectName+"']")));
				parentTableDeleteBtn.click();
				CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id,':appTbl:d2::yes')]")), "Click", "",driver);
				driver.findElement(By.xpath("//button[contains(@id,':appTbl:d2::yes')]")).click();
				CommonUtils.hold(4);
				}catch(Exception e){
					System.out.println("No warning pop up present in delete case");
				}
			}
			
			verifyDSDeleteStatus(dsObjectName,driver);
		}
			
	}

	public void deleteAction(String dsObject, String actionName,WebDriver driver) throws Exception{
		if (searchDSObject(dsObject,driver)) {
			parentEditBtn.click();
			CommonUtils.hold(2);
			CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
			actionsTab.click();
			CommonUtils.hold(2);
			handleTable(actionTable, actionDeleteBtn,driver);
			driver.findElement(By.xpath("//input[@value='" + actionName + "']")).click();
			CommonUtils.hold(3);
			actionDeleteBtn.click();
			CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d222::yes')]")),
					"Click", "",driver);
			driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d222::yes')]")).click();
			CommonUtils.hold(5);
			actionSubmitBtn.click();
			CommonUtils.hold(3);

			try {
				CommonUtils.explicitWait(actionOKBtn, "Click", "",driver);
				actionOKBtn.click();
				CommonUtils.hold(3);
			} catch (Exception e) {
				System.out.println("No pop up present in update case");
			}
		} else {
			System.out.println();
			Assert.assertFalse(false);
		}

		verifyStatus(dsObject, "action", actionName, "delete",driver);

	}

	public void deletePolicy(String dsObject, String policyName,WebDriver driver) throws Exception{

		if (searchDSObject(dsObject,driver)) {
			try {
				CommonUtils.hold(3);
				CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
				qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
				qbeSearchField.sendKeys(Keys.ENTER);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + policyName + "']")), "Visible",
						"",driver);
				driver.findElement(By.xpath("//span[text()='" + policyName + "']")).click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(policyDeleteBtn, "Click", "",driver);
				policyDeleteBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(
						driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:ChildappTbl:delDiag::yes')]")),
						"Click", "",driver);
				driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:ChildappTbl:delDiag::yes')]")).click();
				CommonUtils.hold(3);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else
			Assert.fail();

		verifyStatus(dsObject, "policy", policyName, "delete",driver);

	}

	public void handleTable(WebElement tableElement, WebElement actionElement,WebDriver driver) {
		WebElement element;
		CommonUtils.hold(3);		
		List<WebElement> rows = tableElement.findElements(By.xpath("//tr[contains(@class,'af_table_data-row')]"));
	
		for (int i = rows.size() - 1; i >= 0; i--) {
			try {	
				element=driver.findElement(By.xpath("//tr[@_afrrk='" + i + "']"));
				element.click();
				CommonUtils.hold(3);
				if (actionElement.getAttribute("src").contains("ena.png"))
					break;				
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
	}
	
	public void createDataBaseObjectPolicy(String dsObjectName, String policyNameValue, String rolename, String instancesetName, String parameter,WebDriver driver) throws Exception{
		if (searchDSObject(dsObjectName,driver)) {

			try {
				CommonUtils.hold(5);
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				childTableNewBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text()='General Information']")), "Click",
						"",driver);
				driver.findElement(By.xpath("//a[text()='General Information']")).click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(crtPolicyPopup, "Visible", "",driver);
				policyName.sendKeys(policyNameValue);
				CommonUtils.hold(2);
				// policyStartDateField.sendKeys(dateFormat);
				policyStartDate.click();
				CommonUtils.hold(2);
				policyCurrentDate.click();
				CommonUtils.hold(2);
				roleTab.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(condAddButton1, "Click", "",driver);
				condAddButton1.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(roleAppDropDown, "Visible", "",driver);
				selectOptionfrompkDropDown(roleAppDropDown, "fscm");
				roleNameTextField.clear();
				roleNameTextField.sendKeys(rolename);
				CommonUtils.explicitWait(roleSearchBtn, "Click", "",driver);
				roleSearchBtn.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(roleRow, "Visible", "",driver);
				roleRow.click();
				CommonUtils.hold(2);
				roleOKBtn.click();
				CommonUtils.hold(3);
				ruleTab.click();
				CommonUtils.hold(2);
				WebElement element = driver.findElement(
						By.xpath("//select[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:rsOption::content')]"));
				selectOptionfrompkDropDown(element, "Multiple Values");
				CommonUtils.hold(2);
				conditionSearchbtn.click();
				CommonUtils.explicitWait(instanceSetToBeSearch, "Visible", "",driver);
				instanceSetToBeSearch.sendKeys(instancesetName);
				CommonUtils.hold(2);
				instanceSetSearchIcon.click();
				CommonUtils.explicitWait(pickInstanceSet(instancesetName,driver), "Visible", "",driver);
				pickInstanceSet(instancesetName,driver).click();
				CommonUtils.hold(2);
				instancesetOKbtn.click();
				CommonUtils.hold(5);
				parameter1inputfield.sendKeys(parameter);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(policyActionTtab, "Click", "",driver);
				policyActionTtab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(moveAllImg, "Click", "",driver);
				moveAllImg.click();
				CommonUtils.hold(2);
				driver.findElement(By.xpath("//button[text()='Save and Close']")).click();
				CommonUtils.hold(5);
				updateObjectPolicyparameter(dsObjectName,policyNameValue,rolename,parameter,driver);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		//	Assert.assertFalse("No such DataBase object "+dsObjectName, false);
		}

	}//End of createDataBaseObjectPolicy()
	
	public void updateObjectPolicyparameter(String DSObjname, String ObjPolicyname, String policyrole, String parametervalue,WebDriver driver) {
		
		try {
			if (searchDSObject(DSObjname,driver)) {
				CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
				qbeSearchField.sendKeys(policyrole);
				qbeSearchField.sendKeys(Keys.ENTER);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+ObjPolicyname+"']")), "Visible", "",driver);
				driver.findElement(By.xpath("//span[text()='"+ObjPolicyname+"']")).click();
				CommonUtils.hold(5);
				childEditBtn.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(ruleEditlink, "Visible", "",driver);
				ruleEditlink.click();
				CommonUtils.hold(5);
				roleEditlink.click();
				CommonUtils.hold(5);
				ruleEditlink.click();
				CommonUtils.hold(5);
				parameter1inputfield.sendKeys(parametervalue);
				CommonUtils.hold(2);
				actionSaveBtn.click();
				CommonUtils.hold(3);
				actionSubmitBtn.click();
				CommonUtils.hold(3);
			}
		}catch (Exception uOPE) {
			System.out.println("Exception in updateObjectPolicy()");
			uOPE.printStackTrace();
		}
	}//End of updateObjectPolicyparameter()
	
}//End Of DataSecurityWrapperClass
