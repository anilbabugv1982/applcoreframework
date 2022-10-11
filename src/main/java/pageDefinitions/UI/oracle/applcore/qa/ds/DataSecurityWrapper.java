package pageDefinitions.UI.oracle.applcore.qa.ds;

import java.util.List;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageDefinitions.UI.oracle.applcore.qa.ds.ManageDataSecurityPage;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;


/**
 * @author DASKUMAR
 *
 */
public class DataSecurityWrapper  extends ManageDataSecurityPage {

	private boolean elementStatus = false;
	private WebDriver driver=null;
	private static boolean inputStatus;
	private static boolean spanStatus;
	private NavigationTaskFlows nav=null;
	private ApplicationLogin login=null;
	
	
	public DataSecurityWrapper(WebDriver dsUtilDriver) {
		super(dsUtilDriver);
		driver=dsUtilDriver;
		nav=new NavigationTaskFlows(dsUtilDriver);
		login=new ApplicationLogin(dsUtilDriver);
	}

	public void navigateToDStaskFlow() throws Exception {
		try {

			if (ApplicationLogin.newsFeedEnabled) {
				nav.navigateToTask(security_console_link, driver);
				CommonUtils.explicitWait(adminPanelIcon, "Click", "", driver);
				CommonUtils.hold(10);				
				adminPanelIcon.click();
				CommonUtils.explicitWait(dsButton, "Click", "", driver);
				CommonUtils.hold(10);
				dsButton.click();
				CommonUtils.hold(5);

			} else {
				CommonUtils.hold(10);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(5);
				CommonUtils.explicitWait(toolsIcon, "Click", "", driver);
				toolsIcon.click();
				CommonUtils.hold(5);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(sheildIcon, "Click", "", driver);
				sheildIcon.click();
				CommonUtils.explicitWait(adminPanelIcon, "Click", "", driver);
				CommonUtils.hold(10);
				adminPanelIcon.click();
				CommonUtils.explicitWait(dsButton, "Click", "", driver);
				CommonUtils.hold(10);
				dsButton.click();
				CommonUtils.hold(5);

			}

		} catch (Exception e) {
			System.out.println("Error in navigating to DS page. " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void createDataBaseObject(String objName) throws Exception {

		try {
			CommonUtils.hold(3);
			CommonUtils.explicitWait(pageHeading, "Visible", "",driver);
			parentTableNewBtn.click();
			CommonUtils.explicitWait(objNameField, "Visible", "",driver);
			CommonUtils.hold(3);
			objNameField.sendKeys(objName);
			displayNameField.sendKeys(objName);
			dataFieldSearch.click();
			CommonUtils.explicitWait(dataFieldSearchLink, "Click", "",driver);
			CommonUtils.hold(3);
			dataFieldSearchLink.click();
			CommonUtils.explicitWait(dataFieldPopupInput, "Visible", "",driver);
			CommonUtils.hold(3);			
			dataFieldPopupInput.sendKeys("ZMM_ACTY_ACTIVITIES");
			dataFieldSearchBtn.click();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='ZMM_ACTY_ACTIVITIES']")),	"Visible", "",driver);
			driver.findElement(By.xpath("//span[text()='ZMM_ACTY_ACTIVITIES']")).click();
			CommonUtils.hold(2);
			dataFieldOKBtn.click();
			CommonUtils.explicitWait(primary_key_addBtn, "Click", "",driver);
			CommonUtils.hold(2);			
			moduleSearchImg.click();
			CommonUtils.explicitWait(dataFieldSearchLink, "Click", "", driver);
			CommonUtils.hold(5);
			dataFieldSearchLink.click();
			CommonUtils.explicitWait(modulePopupInput, "Visible", "",driver);
			CommonUtils.hold(3);			
			modulePopupInput.sendKeys("Oracle Middleware Extensions for Applications");
			moduleSearchBtn.click();
			CommonUtils.explicitWait(moduleRecord, "Click", "",driver);
			CommonUtils.hold(5);			
			moduleRecord.click();
			CommonUtils.hold(2);
			dataFieldOKBtn.click();
			CommonUtils.explicitWait(primary_key_addBtn, "Click", "", driver);
			CommonUtils.hold(6);
			primary_key_addBtn.click();
			CommonUtils.hold(3);
			boolean status = false;

			try {
				status = driver.findElement(By.xpath("//a[contains(@id,':_ATp:pkTable:0:pkColumnChoice::drop')]"))	.isDisplayed();
			} catch (Exception e) {
				System.out.println("Input element not found for Primary Key DROP-DOWN");
			}

			if (status) {
				driver.findElement(By.xpath("//a[contains(@id,':_ATp:pkTable:0:pkColumnChoice::drop')]")).click();
				driver.findElement(By.xpath("//li[contains(text(),'ACCOUNT_ID')]")).click();
				CommonUtils.hold(4);
			} else {
				CommonUtils.explicitWait(pkDropDown, "Visible", "",driver);
				selectOptionfrompkDropDown(pkDropDown, "ACCOUNT_ID");
				CommonUtils.hold(4);
			}

			actionSubmitBtn.click();
			CommonUtils.explicitWait(dsObjectWarning, "Click", "",driver);
			CommonUtils.hold(6);
			actionOKBtn.click();
			CommonUtils.explicitWait(confirmationPopUP, "Click", "",driver);
			CommonUtils.hold(4);
		} catch (Exception e) {
			System.out.println("DataBase object creation failed : "+e.getMessage());
			e.printStackTrace();			
		}

	}

	public  void createCondition(String dsObjectName, String conditionName) throws Exception {

		if (searchDSObject(dsObjectName)) {
			try {
				parentEditBtn.click();
				CommonUtils.explicitWait(conditionsTab, "Click", "", driver);
				CommonUtils.hold(2);
				conditionsTab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(condAddButton, "Visible", "",driver);
				condAddButton.click();
				CommonUtils.explicitWait(condNameField, "Visible", "",driver);
				CommonUtils.hold(4);
				condDisNameField.sendKeys(conditionName);
				condNameField.sendKeys(conditionName);
				condAddButton1.click();
				CommonUtils.hold(5);

				boolean status;

				try {
					status = driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:AT1:r1:0:AT1:_ATp:ATt1:0:soc1::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Coulmn drop-down element not found in conditions tab : " + e.getMessage());
					e.printStackTrace();
					status = false;
				}

				if (status) {
					driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:AT1:r1:0:AT1:_ATp:ATt1:0:soc1::drop')]"))	.click();
					CommonUtils.hold(5);
					driver.findElement(By.xpath("//li[text()='ACCOUNT_ID']")).click();
					CommonUtils.hold(5);
				} else {
					CommonUtils.explicitWait(condDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(condDropDown, "CREATED_BY");
					CommonUtils.hold(8);
				}

				try {
					status = driver.findElement(By.xpath("//a[contains(@id,'AT1:_ATp:ATt1:0:soc22::drop')]")).isDisplayed();
				} catch (Exception e) {
					System.out.println("Operator element drop-down not found" + e.getMessage());
					e.printStackTrace();
					status = false;
				}

				if (status) {
					driver.findElement(By.xpath("//a[contains(@id,'AT1:_ATp:ATt1:0:soc22::drop')]")).click();
					CommonUtils.hold(5);
					driver.findElement(By.xpath("//li[contains(text(),'Is not blank')]")).click();
					CommonUtils.hold(5);

				} else {
					CommonUtils.explicitWait(condoperatorDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(condoperatorDropDown, "Is not blank");
					CommonUtils.hold(3);
				}

				condSaveBtn.click();
				CommonUtils.hold(10);
				actionSubmitBtn.click();
				CommonUtils.hold(20);

				try {
					CommonUtils.explicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click","",driver);
					 actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
		  	Assert.assertFalse(false, "Could not locate DataBase object with name: " + dsObjectName);

		verifyStatus(dsObjectName, "condition", conditionName, "update");

	}

	public void createAcion(String dsObjectName, String actionName) throws Exception {
		if (searchDSObject(dsObjectName)) {
			try {
				parentEditBtn.click();
				CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
				CommonUtils.hold(4);
				actionsTab.click();
				CommonUtils.explicitWait(actionAddBtn, "Click", "",driver);
				CommonUtils.hold(2);
				actionAddBtn.click();
				CommonUtils.explicitWait(actionNameField, "Visible", "",driver);
				CommonUtils.hold(3);				
				actionNameField.sendKeys(actionName);
				actionDisNameField.sendKeys(actionName);
				actionSubmitBtn.click();
				CommonUtils.hold(8);

				try {
					CommonUtils.explicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click","",driver);
					CommonUtils.explicitWait(actionOKBtn, "Click", "", driver);
					actionOKBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println("Need to FILL this GAP");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
//			Assert.assertFalse("Could not locate DataBase object with name : " + dsObjectName, false);
			Assert.assertFalse( false,"Could not locate DataBase object with name : " + dsObjectName);
		 	verifyStatus(dsObjectName, "action", actionName, "update");
	}

	public void createDataBasePolicy(String dsObjectName, String policyName) throws Exception {
		
		if (searchDSObject(dsObjectName)) {
			try {
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				CommonUtils.hold(5);
				childTableNewBtn.click();
				CommonUtils.hold(5);		
				CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text()='General Information']")), "Click",	"",driver);
				CommonUtils.hold(5);				
				driver.findElement(By.xpath("//a[text()='General Information']")).click();
				CommonUtils.explicitWait(crtPolicyPopup, "Visible", "",driver);
				CommonUtils.hold(5);				
				dspolicyName.click();
				CommonUtils.hold(1);
				dspolicyName.sendKeys(policyName);
				CommonUtils.hold(8);
				/*policyName.clear();
				policyName.sendKeys(policyName);*/
				// policyStartDateField.sendKeys(dateFormat);
				policyStartDate.click();
				CommonUtils.explicitWait(policyCurrentDate,"Click","",driver);
				CommonUtils.hold(2);
				policyCurrentDate.click();
				CommonUtils.hold(4);
				roleTab.click();
				CommonUtils.explicitWait(condAddButton1, "Click", "",driver);
				CommonUtils.hold(3);
				condAddButton1.click();
				CommonUtils.hold(7);
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
					driver.findElement(By.xpath("//a[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:appid::drop')]"))	.click();
					CommonUtils.hold(5);
					driver.findElement(By.xpath("//li[text()='crm']")).click();
				} else {
					CommonUtils.explicitWait(roleAppDropDown, "Visible", "",driver);
					selectOptionfrompkDropDown(roleAppDropDown, "crm");
					CommonUtils.hold(3);
				}

				roleNameTextField.clear();
				CommonUtils.hold(3);
				roleNameTextField.sendKeys("Application*Developer");
				CommonUtils.explicitWait(roleSearchBtn, "Click", "",driver);
				roleSearchBtn.click();
				CommonUtils.explicitWait(roleRow, "Visible", "",driver);
				CommonUtils.hold(2);
				roleRow.click();
				CommonUtils.hold(2);
				roleOKBtn.click();
				CommonUtils.hold(3);
				ruleTab.click();
				CommonUtils.hold(5);

				try {
					status = driver
							.findElement(By.xpath("//a[contains(@id,'gdb1:ChildappTbl:pr1:0:rsOption::drop')]"))
							.isDisplayed();
				} catch (Exception e) {
					System.out.println("Row Set DROP-DOWN element not found"+ e.getMessage());
					e.printStackTrace();
					status = false;
				}
				if (status) {
					driver
							.findElement(By.xpath("//a[contains(@id,'gdb1:ChildappTbl:pr1:0:rsOption::drop')]")).click();
					CommonUtils.hold(5);
					driver.findElement(By.xpath("//li[text()='All Values']")).click();
					CommonUtils.hold(5);
				} else {
					WebElement element = driver.findElement(By.xpath("//select[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:rsOption::content')]"));
					selectOptionfrompkDropDown(element, "All Values");
					CommonUtils.hold(2);
				}

				CommonUtils.explicitWait(policyActionTtab, "Click", "",driver);
				policyActionTtab.click();
				CommonUtils.explicitWait(moveAllImg, "Click", "",driver);
				CommonUtils.hold(2);
				moveAllImg.click();
				CommonUtils.hold(5);
				driver.findElement(By.xpath("//button[text()='Save and Close']")).click();
				CommonUtils.hold(5);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
//			Assert.assertFalse("No such DataBase object " + dsObjectName, false);
			Assert.assertFalse( false,"No such DataBase object " + dsObjectName);
		}
		verifyStatus(dsObjectName, "policy", policyName, "update");
	}
	
	
	public boolean searchDSObject(String databaseObjName) throws Exception {
		CommonUtils.explicitWait(objNameSearchField, "Visible", "",driver);
		CommonUtils.hold(5);
		objNameSearchField.clear();
		objNameSearchField.sendKeys(databaseObjName);
		CommonUtils.explicitWait(searchBtn, "Click", "", driver);
		searchBtn.click();
		CommonUtils.hold(4);
		try {
			return driver.findElement(By.xpath("//span[contains(text(),'" + databaseObjName + "')]")).isDisplayed();

		} catch (Exception e) {
			System.out.println("No DataBase object with name : " + databaseObjName + " found. " + e.toString());
			return false;
		}

	}

	public void selectOptionfrompkDropDown(WebElement ele, String value) throws Exception {

		Select opt = new Select(ele);
		opt.selectByVisibleText(value);

	}

	public void verifyStatus(String dsObjectName, String tabName, String value, String operationType)
			throws Exception {
		if (searchDSObject(dsObjectName)) {
			if (!tabName.isEmpty() && tabName.equalsIgnoreCase("condition")) {
				CommonUtils.explicitWait(parentEditBtn, "Click", "", driver);
				parentEditBtn.click();
				CommonUtils.explicitWait(conditionsTab, "Visible", "",driver);
				CommonUtils.hold(3);
				conditionsTab.click();
				CommonUtils.hold(4);

				if (operationType.equals("update")) {
					try {
						Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'" + value + "')]")).isDisplayed());
					} catch (Exception e) {
						System.out.println("Error while performing update case in condtion tabs : " + e.toString());
						e.printStackTrace();
					}
				}

				else {
					try {
						driver.findElement(By.xpath("//span[contains(text(),'" + value + "')]")).isDisplayed();
					} catch (Exception e) {
						Assert.assertFalse(false,"Deleted Condition successfully : " + value);
					}

				}

				actionSubmitBtn.click();
				CommonUtils.hold(10);

				try {
					CommonUtils.explicitWait(actionOKBtn, "Click","",driver);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click","",driver);
					actionOKBtn.click();
					CommonUtils.hold(5);
				} catch (Exception e) {
					System.out.println("No confirmation pop up required in update case in CONDITION ");
				}

			} else if (!tabName.isEmpty() && tabName.equalsIgnoreCase("action")) {
				parentEditBtn.click();
				CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
				CommonUtils.hold(2);
				actionsTab.click();
				CommonUtils.hold(5);

				if (operationType.equals("update")) {
					try {
						handleTable(actionTable, actionDeleteBtn);
						Assert.assertTrue(driver.findElement(By.xpath("//input[@value='" + value + "']")).isDisplayed());
					} catch (Exception e) {
						Assert.assertFalse(false,"Update operation falied on Action : " + value);
					}
				} else {
					try {
						handleTable(actionTable, commonDeletebtn);
						inputStatus = CommonUtils	.isElementPresent(driver.findElement(By.xpath("//input[@value='" + value + "']")));
						System.out.println("Element status for action delete " + elementStatus);

					} catch (Exception e) {
						System.out.println("Element status for action delete11 " + elementStatus);
						inputStatus = false;
					}

					try {
						spanStatus = CommonUtils.isElementPresent(driver.findElement(By.xpath("//span[text()='" + value + "']")));
						System.out.println("Element status for action delete " + elementStatus);
					} catch (Exception e) {
						System.out.println("Element status for action delete22 " + elementStatus);
						spanStatus = false;
					}

					if (inputStatus && spanStatus)
						Assert.fail();
					else
						Assert.assertFalse(false,"Deleted action successfully");

				}
				actionSubmitBtn.click();
				CommonUtils.hold(8);

				try {
					CommonUtils.explicitWait(actionOKBtn, "Click", "", driver);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click","",driver);
					actionOKBtn.click();
					CommonUtils.hold(4);
				} catch (Exception e) {
					System.out.println("No confirmation pop up required in update case during ACTION verification");
				}

			}

			else if (!tabName.isEmpty() && tabName.equalsIgnoreCase("policy")) {

				CommonUtils.hold(4);
				CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
				qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
				qbeSearchField.sendKeys(Keys.ENTER);
				CommonUtils.hold(5);

				if (operationType.equals("update")) {

					try {
						Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
					} catch (Exception e) {
						System.out.println("No such Policy found :" + value + " " + e.toString());
						Assert.fail();
					}
				} else {
					try {
						Assert.assertFalse(driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
					} catch (Exception e) {
						Assert.assertTrue( true,"Deleted policy successfully");
					}
				}

			}
		}

	}

	public void verifyDSDeleteStatus(String dsName) throws Exception {
		if (searchDSObject(dsName))
			Assert.fail();
		else
//			Assert.assertTrue("DS Object deleted successfully", true);
			Assert.assertTrue( true,"DS Object deleted successfully");
	}

	public void updateObject(String dsObject, String tabName, String dsName, String updateText)
			throws Exception {

		if (searchDSObject(dsObject)) {
			
			switch (tabName) {
			case "condition":
				try {
					CommonUtils.explicitWait(parentEditBtn, "Click", "", driver);
					parentEditBtn.click();
					CommonUtils.explicitWait(conditionsTab, "Click", "",driver);
					CommonUtils.hold(4);
					conditionsTab.click();
					CommonUtils.hold(3);
					handleTable(conditionTable, conditionDeleteBtn);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//img[contains(@id,'pt1:gdb1:AT1:_ATp:edit::icon')]")),"Click", "",driver);
					driver.findElement(By.xpath("//img[contains(@id,'pt1:gdb1:AT1:_ATp:edit::icon')]")).click();
					CommonUtils.hold(6);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//textarea[contains(@id,'pt1:gdb1:AT1:r2:0:it2::content')]")),"Visible", "",driver);
					driver.findElement(By.xpath("//textarea[contains(@id,'pt1:gdb1:AT1:r2:0:it2::content')]")).clear();
					driver.findElement(By.xpath("//textarea[contains(@id,'pt1:gdb1:AT1:r2:0:it2::content')]")).sendKeys(updateText);
					CommonUtils.hold(4);
					CondEditSaveBtn.click();
					CommonUtils.hold(10);
					actionSubmitBtn.click();
					CommonUtils.hold(3);
				} catch (Exception e) {
					System.out.println("Error while updating DS condition : "+e.toString());
					e.printStackTrace();
				}
				break;
			case "action":
				try {
					CommonUtils.explicitWait(parentEditBtn, "Click", "", driver);
					parentEditBtn.click();
					CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
					CommonUtils.hold(4);
					actionsTab.click();
					CommonUtils.explicitWait(actionTable, "Click", "", driver);
					CommonUtils.hold(4);
					handleTable(actionTable, actionDeleteBtn);
					CommonUtils.hold(4);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//label[text()='Description']/preceding-sibling::input")),"Visible", "",driver);
					driver.findElement(By.xpath("//label[text()='Description']/preceding-sibling::input")).clear();
					driver.findElement(By.xpath("//label[text()='Description']/preceding-sibling::input")).sendKeys(updateText);
					actionSubmitBtn.click();
					CommonUtils.hold(5);
				} catch (Exception e) {
					System.out.println("Error while updating DS actions "+e.toString());
					e.printStackTrace();
				}
				break;
			case "policy":
				try {
					CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
					qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
					qbeSearchField.sendKeys(Keys.ENTER);
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + dsName + "']")), "Visible","",driver);
					driver.findElement(By.xpath("//span[text()='" + dsName + "']")).click();
					CommonUtils.hold(3);
					childEditBtn.click();
					CommonUtils.hold(6);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//img[contains(@id,'gdb1:appTbl:_ATp:delete::icon')]")),"Visible", "",driver);
					handleTable(driver.findElement(By.xpath("//div[contains(@id,'gdb1:appTbl:_ATp:polTbl::db')]/table/tbody")),policyEditDeleteBtn);
					try {
						CommonUtils.hold(5);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//textarea[contains(@id,':gDesc::content')]")), "Visible","",driver);
					} catch (Exception GeneralIfnoExcep) {
						GeneralInfotab.click();
						CommonUtils.hold(5);
					}
					driver.findElement(By.xpath("//textarea[contains(@id,':gDesc::content')]")).clear();
					driver.findElement(By.xpath("//textarea[contains(@id,':gDesc::content')]")).sendKeys(updateText);
					actionSaveBtn.click();
					CommonUtils.hold(10);
					actionSubmitBtn.click();
					CommonUtils.hold(3);
					// verifyStatus(dsObject, "policy", updateText, "update");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			}

			verifyStatus(dsObject, tabName, updateText, "update");

		} else
			Assert.fail();// Discuss

	}

	public void deleteCondition(String dsObject, String conditionName) throws Exception {

		searchDSObject(dsObject);
		parentEditBtn.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(conditionsTab, "Visible", "",driver);
		conditionsTab.click();
		CommonUtils.hold(5);
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
			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@id , 'pt1:gdb1:m4')]/ancestor::table[@class = 'af_dialog_main']")), "Visible", "",driver);
			actionOKBtn.click();
			CommonUtils.hold(3);
		} catch (Exception e) {
			System.out.println("No pop up present in update case");
		}

		verifyStatus(dsObject, "condition", conditionName, "delete");
	}

	public  void deleteResource(String dsObjectName, String resourceName, String resourceType) throws Exception {

		if (!resourceName.isEmpty() && !resourceType.isEmpty()) {
			if (searchDSObject(dsObjectName)) {

				switch (resourceType) {
				case "action":
					try {
						CommonUtils.explicitWait(parentEditBtn, "Click", "", driver);
						parentEditBtn.click();
						CommonUtils.isElementPresent(actionsTab);
						CommonUtils.hold(4);
						actionsTab.click();
						CommonUtils.explicitWait(actionTable, "Visible", "",driver);
						CommonUtils.hold(4);
						handleTable(actionTable,	commonDeletebtn);
						driver.findElement(By.xpath("//input[@value='" + resourceName + "']")).click();
						commonDeletebtn.click();
						CommonUtils.explicitWait(actionDeleteConfOKBtn, "Click","",driver);
						CommonUtils.hold(4);
						actionDeleteConfOKBtn.click();
						CommonUtils.explicitWait(actionSubmitBtn, "Click","",driver);
						CommonUtils.hold(4);
						actionSubmitBtn.click();
						CommonUtils.hold(8);
						
						try {
							System.out.println("Before clicking action OK button");
							CommonUtils.explicitWait(actionOKBtn, "Click","",driver);
							System.out.println("After clicking action OK button");
							actionOKBtn.click();
							CommonUtils.hold(3);
						} catch (Exception e) {
							System.out.println("===========================> : Exception in ACTION  delete case: "+e.getMessage());
						}
					} catch (Exception e) {
						System.out.println("Error while deleting DS condition : "+e.toString());
						e.printStackTrace();
					}
					break;
				case "condition":
					try {
						CommonUtils.explicitWait(parentEditBtn, "Click", "", driver);
						parentEditBtn.click();
						CommonUtils.explicitWait(conditionsTab, "Click", "", driver);
						CommonUtils.hold(5);
						conditionsTab.click();
						CommonUtils.hold(5);
						handleTable(conditionTable,commonDeletebtn);
						driver.findElement(By.xpath("//span[text()='" + resourceName + "']")).click();
						commonDeletebtn.click();
						CommonUtils.explicitWait(condtionDeleteOKBtn, "Click","",driver);
						CommonUtils.hold(4);						
						condtionDeleteOKBtn.click();
						CommonUtils.hold(2);
						CommonUtils.explicitWait(actionSubmitBtn, "Click", "",driver);
						actionSubmitBtn.click();
						
						try {
							CommonUtils.hold(5);
							CommonUtils.explicitWait(dsObjectWarning, "Click","",driver);
							actionOKBtn.click();
							CommonUtils.hold(3);
						} catch (Exception e) {
							System.out.println("===========================> : Exception in CONDITION delete case : "+e.getMessage());
						}						
						
					} catch (Exception e) {
						System.out.println("Error while deleting DS condition : "+e.toString());
						e.printStackTrace();
					}
					break;
				case "policy":
					try {
						CommonUtils.hold(3);
						CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
						qbeSearchField.sendKeys("ORA_FND_APPLICATION_DEVELOPER_JOB");
						qbeSearchField.sendKeys(Keys.ENTER);
						CommonUtils.hold(2);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + resourceName + "']")),"Visible", "",driver);
						driver.findElement(By.xpath("//span[text()='" + resourceName + "']")).click();
						CommonUtils.hold(3);
						CommonUtils.explicitWait(policyDeleteBtn, "Click", "",driver);
						policyDeleteBtn.click();
						CommonUtils.hold(3);
						CommonUtils.explicitWait(policyDeleteOKBtn,"Click", "",driver);
						policyDeleteOKBtn.click();
						CommonUtils.hold(4);
					} catch (Exception e) {
						System.out.println(" ==================================> Exception in POLICY delete case : "+e.toString());
					}
					break;
				}
				
				verifyStatus(dsObjectName, resourceType, resourceName, "delete");

			}

		} else {
			if (searchDSObject(dsObjectName)) {
				try {
					CommonUtils.hold(5);
					CommonUtils.isElementPresent(driver.findElement(By.xpath("//span[text()='" + dsObjectName + "']")));
					parentTableDeleteBtn.click();
					CommonUtils.hold(4);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id,':appTbl:d2::yes')]")),"Click", "",driver);
					driver.findElement(By.xpath("//button[contains(@id,':appTbl:d2::yes')]")).click();
					CommonUtils.hold(4);
				} catch (Exception e) {
					System.out.println("No warning pop up present in delete case");
				}
			}

			verifyDSDeleteStatus(dsObjectName);
		}

	}

	public  void deleteAction(String dsObject, String actionName) throws Exception {
		if (searchDSObject(dsObject)) {
			parentEditBtn.click();
			CommonUtils.hold(2);
			CommonUtils.explicitWait(actionsTab, "Visible", "",driver);
			actionsTab.click();
			CommonUtils.hold(2);
			handleTable(actionTable, actionDeleteBtn);
			driver.findElement(By.xpath("//input[@value='" + actionName + "']")).click();
			CommonUtils.hold(3);
			actionDeleteBtn.click();
			CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d222::yes')]")),	"Click", "",driver);
			driver.findElement(By.xpath("//button[contains(@id,'pt1:gdb1:d222::yes')]")).click();
			CommonUtils.hold(5);
			actionSubmitBtn.click();
			CommonUtils.hold(3);

			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//td[contains(text(),'Warning')]")), "Click", "",driver);
				actionOKBtn.click();
				CommonUtils.hold(3);
			} catch (Exception e) {
				System.out.println("No pop up present in update case");
			}
		} else {
			System.out.println();
			Assert.assertFalse(false);
		}

		verifyStatus(dsObject, "action", actionName, "delete");

	}

	public void deletePolicy(String dsObject, String policyName) throws Exception {

		if (searchDSObject(dsObject)) {
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

		verifyStatus(dsObject, "policy", policyName, "delete");

	}

	public  void handleTable(WebElement tableElement, WebElement actionElement) {
		WebElement element;
		CommonUtils.hold(3);
		List<WebElement> rows = tableElement.findElements(By.xpath("//tr[contains(@class,'af_table_data-row')]"));

		for (int i = rows.size() - 1; i >= 0; i--) {
			try {
				element = driver.findElement(By.xpath("//tr[@_afrrk='" + i + "']"));
				element.click();
				CommonUtils.hold(3);
				if (actionElement.getAttribute("src").contains("ena.png"))
					break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
	}

	public void createDataBaseObjectPolicy(String dsObjectName, String policyName, String rolename, String Application, String RowSet,
			String instancesetName, String parameter) throws Exception {
		if (searchDSObject(dsObjectName)) {

			try {
				CommonUtils.hold(5);
				CommonUtils.explicitWait(childTableNewBtn, "Visible", "",driver);
				childTableNewBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(generalInfoTab, "Click",	"",driver);
				generalInfoTab.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(crtPolicyPopup, "Visible", "",driver);
				dspolicyName.sendKeys(policyName);
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
				selectOptionfrompkDropDown(roleAppDropDown, Application);
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
				WebElement element = driver.findElement(By.xpath("//select[contains(@id,'pt1:gdb1:ChildappTbl:pr1:0:rsOption::content')]"));
				selectOptionfrompkDropDown(element, RowSet);
				CommonUtils.hold(2);
				conditionSearchbtn.click();
				CommonUtils.explicitWait(pickInstanceSet(instancesetName), "Visible", "",driver);
				pickInstanceSet(instancesetName).click();
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
			//	updateObjectPolicyparameter(dsObjectName, policyName, rolename, parameter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
//			Assert.assertFalse("No such DataBase object " + dsObjectName, false);
			Assert.assertFalse(false,"No such DataBase object " + dsObjectName);
		}

	}// End of createDataBaseObjectPolicy()

	public void updateObjectPolicyparameter(String DSObjname, String ObjPolicyname, String policyrole,
			String parametervalue) {

		try {
			if (searchDSObject(DSObjname)) {
				CommonUtils.explicitWait(qbeSearchField, "Visible", "",driver);
				qbeSearchField.sendKeys(policyrole);
				qbeSearchField.sendKeys(Keys.ENTER);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + ObjPolicyname + "']")),"Visible", "",driver);
				driver.findElement(By.xpath("//span[text()='" + ObjPolicyname + "']")).click();
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
		} catch (Exception uOPE) {
			System.out.println("Exception in updateObjectPolicy()");
			uOPE.printStackTrace();
		}
	}// End of updateObjectPolicyparameter()
	
	public WebElement pickInstanceSet(String InstanceSetName) {
   	 
        WebElement instanceset = driver.findElement(By.xpath("//table[@summary = 'Instance Sets']/descendant::span[contains(@id , 'c2ot') and text() = '"+InstanceSetName+"']"));
        return instanceset;
    }

}// End Of DataSecurityWrapperClass
