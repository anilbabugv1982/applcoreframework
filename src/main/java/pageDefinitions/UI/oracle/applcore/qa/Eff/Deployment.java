package pageDefinitions.UI.oracle.applcore.qa.Eff;

import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;

public class Deployment extends EFFReferenceDataPage {
	public Deployment(WebDriver driver) {
		super(driver);
		effUtils = new EFFUtils(driver);
		js = (JavascriptExecutor) driver;
		date = new Date();
	}

	private WebDriverWait ExplicitWait;
	public static String sContextCodeText;
	JavascriptExecutor js;
	WebDriverWait wait;
	EFFUtils effUtils;
	private Date date;

	public boolean searchFlexFieldCode(String sFlexCode) {
		boolean isFlexCodeExists = false;
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(flexFieldCode);
		flexFieldCode.sendKeys(sFlexCode);
		CommonUtils.hold(10);
		searchButton.click();
		CommonUtils.hold(10);
		WebElement flexColumn = driver.findElement(By.xpath("//tbody//*[contains(text(),'" + sFlexCode + "')]"));
		System.out.println("Flex Column text - " + flexColumn.getText());
		if (flexColumn.isDisplayed()) {
			isFlexCodeExists = true;
			System.out.println("Flex Code in the table - " + sFlexCode + " exists");
		} else {
			System.err.println(
					"ERROR - " + flexCodeColumn.getText() + "does not match with expected flex code - " + sFlexCode);
		}
		return isFlexCodeExists;
	}

	public void navigateFromFlexPageToCategoriesPage() {
		waitConditionForElement(flexCodeEditIcon);
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
				flexCodeEditIcon);
		CommonUtils.hold(4);
		js.executeScript("arguments[0].click()", flexCodeEditIcon);
		// flexCodeEditIcon.click();
	}

	public boolean createContext(String sFlexCode, String sContextName, String sDescription, String sBehaviorOption,
			String itemUsage) {
		String cUsage = null;
		Actions action = new Actions(driver);
		boolean isCreated = false;
		navigateFromFlexPageToCategoriesPage();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait = new WebDriverWait(driver, 120);
		ExplicitWait.until(ExpectedConditions.visibilityOf(ManageContextButton));
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(ManageContextButton));
		js = (JavascriptExecutor) driver;
		action.moveToElement(ManageContextButton).click().perform();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		createContextImgIcon.click();
		if (createContextDisplayName.isEnabled()) {
			System.out.println("New Context page is loaded");
			CommonUtils.waitForElement(createContextDisplayName, driver);
			CommonUtils.hold(4);
			createContextDisplayName.sendKeys(sContextName + CommonUtils.uniqueId());
			CommonUtils.hold(2);
			createContextDescription.sendKeys(sDescription);
			CommonUtils.hold(2);
			CommonUtils.selectDropDownElement(contextBehaviorDropdown, sBehaviorOption);
			addContextUsage.click();
			CommonUtils.hold(2);
			System.out.println("Current Flex Code - " + sFlexCode);
			if (sFlexCode.equals("EGO_ITEM_EFF")) {
				cUsage = itemUsage;
				if (driver.findElements(By.xpath("//input[contains(@id,'itdbviewname::content')]")).size() > 0) {
					String currentDBViewName = pimDBViewName.getAttribute("value");
					System.out.println("Current DB ViewName - " + currentDBViewName);
					pimDBViewName.clear();
					CommonUtils.hold(3);
					pimDBViewName.sendKeys(CommonUtils.uniqueId());
					CommonUtils.hold(3);
					System.out.println("After modifying DB View Name - " + pimDBViewName.getAttribute("value"));
					CommonUtils.hold(3);
				} else {
					System.out.println("Current Patch does not have DBView");
				}
			}
			if (sFlexCode.equals("PER_PERSON_EIT_EFF")) {
				cUsage = "Usage Code for Person";
			}
			if (sFlexCode.equals("PER_LOCATION_INFORMATION_EFF")) {
				cUsage = "Location";
			}
			else {
				cUsage = itemUsage;
			}
			System.out.println("Current Usage - " + cUsage);
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					contextUsageDropdown);
			CommonUtils.selectDropDownElement(contextUsageDropdown, cUsage);
			contextSaveBtn.click();
			CommonUtils.waitForPageLoad(driver);
			try {
				CommonUtils.explicitWait(addSegmentImgIcon, "Click", "", driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonUtils.waitForElement(addSegmentImgIcon, driver);
			CommonUtils.hold(5);
			System.out.println("Context - " + sContextName + " saved successfully");
			isCreated = true;
		} else {
			System.err.println("ERROR - Create Context Display name is not yet loaded");
		}

		return isCreated;
	}

	public void navigateToCreateContext() {
		Actions action = new Actions(driver);
		navigateFromFlexPageToCategoriesPage();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait = new WebDriverWait(driver, 120);
		ExplicitWait.until(ExpectedConditions.visibilityOf(ManageContextButton));
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(ManageContextButton));
		js = (JavascriptExecutor) driver;
		action.moveToElement(ManageContextButton).click().perform();
		CommonUtils.waitForPageLoad(driver);

	}

	public void createContextIcon() {
		createContextImgIcon.click();
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(contextsegmentSaveAndClose);
		waitConditionForElement(createContextDisplayName);
		waitConditionForElement(createContextDescription);
	}

	public void cancelContextSegmentCreation() {
		waitConditionForElement(CancelButton);
		CancelButton.click();
		CommonUtils.waitForPageLoad(driver);
	}

	public void clickCreateContext() {
		waitConditionForElement(createContextImgIcon);
		createContextImgIcon.click();
		CommonUtils.waitForPageLoad(driver);
	}

	public void clickCreateSegment() {
		waitConditionForElement(addSegmentImgIcon);
		addSegmentImgIcon.click();
		waitConditionForElement(createSegmentHeader);
		waitConditionForElement(segmentName);
		waitConditionForElement(segmentDescription);
		waitConditionForElement(segmentDataType);
		waitConditionForElement(contextsegmentSaveAndClose);
	}

	public void cancelSegmentCreation() {
		waitConditionForElement(CancelButton);
		CancelButton.click();
		waitConditionForElement(contextCode);
	}

	public boolean validateContextCreateWithKeywords(String keyName) {
		boolean bErrorDisplayed = false;
		System.out.println("New Context page is loaded");
		waitConditionForElement(createContextDisplayName);
		waitConditionForElement(createContextDescription);
		waitConditionForElement(CancelButton);
		CommonUtils.hold(4);
		createContextDisplayName.sendKeys(keyName);
		// CommonUtils.hold(2);
		createContextDescription.click();
		CommonUtils.hold(4);
		if (driver.findElements(By.xpath("//div[@id='d1::msgDlg::_ttxt']")).size() > 0) {
			if (driver.findElements(By.xpath("//span[contains(text(),'" + keyName + " is not valid')]")).size() > 0
					|| driver.findElements(By.xpath("//span[contains(text(),'is not valid')]")).size() > 0) {
				System.out.println("Error Message displayed properly for Keyword validation - " + keyName
						+ " for context creaton...");
				bErrorDisplayed = true;
				waitConditionForElement(warningMessageOkButton);
				warningMessageOkButton.click();
			}
		} else {
			System.out.println("Error message yet to be displayed");
			contextSaveBtn.click();
			waitConditionForElement(errorMessageHeader);
			if (driver.findElements(By.xpath("//span[contains(text(),'" + keyName + " is not valid')]")).size() > 0
					|| driver.findElements(By.xpath("//span[contains(text(),'is not valid')]")).size() > 0) {
				System.out.println("Error Message displayed properly for Keyword validation - " + keyName
						+ " for context creaton...");
				bErrorDisplayed = true;
				waitConditionForElement(warningMessageOkButton);
				warningMessageOkButton.click();
			} else {
				System.err.println("Error Message not displayed properly..");
			}
		}
		return bErrorDisplayed;
	}

	public String getcontextCode(String sContextName) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(contextCode, ExplicitWaitCalls.TextPresent.toString(), sContextName, driver);
		System.out.println("Curent Context's Code - " + contextCode.getText());
		sContextCodeText = contextCode.getText();
		return sContextCodeText;
	}

	public void clickCreateSegmentIcon(WebDriver driver) {
		CommonUtils.hold(5);
		effUtils.waitConditionForElement(addSegmentImgIcon);
		addSegmentImgIcon.click();
		effUtils.waitConditionForElement(createSegmentHeader);
		effUtils.waitConditionForElement(segmentName);
		effUtils.waitConditionForElement(segmentDescription);
		System.out.println("New Segment is loaded");
	}

	public boolean validateSegmentWithKeyWord(String segName) {
		boolean bErrorDisplayed = false;
		segmentName.clear();
		CommonUtils.hold(2);
		segmentName.sendKeys(segName);
		CommonUtils.hold(2);
		segmentDescription.clear();
		CommonUtils.hold(2);
		segmentDescription.click();
		CommonUtils.hold(4);
		if (driver.findElements(By.xpath("//div[@id='d1::msgDlg::_ttxt']")).size() > 0) {
			if (driver.findElements(By.xpath("//span[contains(text(),'" + segName + " is not valid')]")).size() > 0
					|| driver.findElements(By.xpath("//span[contains(text(),'is not valid')]")).size() > 0) {
				System.out.println("Error Message displayed properly for Keyword validation - " + segName
						+ " for context creaton...");
				bErrorDisplayed = true;
				waitConditionForElement(warningMessageOkButton);
				warningMessageOkButton.click();
			}
		} else {
			System.out.println("Error message yet to be displayed");
			editSegmentSaveAndClose.click();
			waitConditionForElement(errorMessageHeader);
			if (driver.findElements(By.xpath("//span[contains(text(),'" + segName + " is not valid')]")).size() > 0
					|| driver.findElements(By.xpath("//span[contains(text(),'is not valid')]")).size() > 0) {
				System.out.println("Error Message displayed properly for Keyword validation - " + segName
						+ " for context creaton...");
				bErrorDisplayed = true;
				waitConditionForElement(warningMessageOkButton);
				warningMessageOkButton.click();
			} else {
				System.err.println("Error Message not displayed properly..");
			}
		}
		return bErrorDisplayed;
	}

	public boolean createSegment(String segName, String segDataType, String valueSetCode, String behavior,
			String displayType, boolean bIsUnique) throws Exception {
		boolean bSegmentCreated = false;
		System.out.println("Segment Name - " + segName + ", Data type - " + segDataType + ", Value Set Code - "
				+ valueSetCode + ", Behavior - " + behavior + ", Display - " + displayType + ", Unique Attribue - "
				+ bIsUnique);
		CommonUtils.hold(3);
		addSegmentImgIcon.click();
		CommonUtils.hold(10);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		CommonUtils.waitForElement(createSegmentHeader, driver);
		CommonUtils.waitForElement(segmentName, driver);
		segmentName.sendKeys(segName);
		CommonUtils.hold(5);
		segmentDescription.sendKeys(segName);
		CommonUtils.hold(5);
		if (behavior.equals("Single Row")) {
			System.out.println("Behavior of current Context is single row");
		} else if (behavior.equals("Multiple Rows")) {
			System.out.println("Behavior of current Context is multiple rows");
			if (bIsUnique) {
				System.out.println("Current Segment " + segName + " is marked as Unique Key for the current context");
				segmentUniqueKey.click();
			} else {
				System.out.println("Current Segment " + segName + " is not marked as Unique Key");
			}

		}
		CommonUtils.selectDropDownElement(segmentDataType, segDataType);
		CommonUtils.hold(6);
		valueSetName.sendKeys(valueSetCode);
		CommonUtils.hold(6);
		valueSetName.sendKeys(Keys.TAB);
		CommonUtils.hold(6);
		/*searchValueSet(valueSetCode);*/
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)");
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(segmentdisplayType, displayType);
		CommonUtils.hold(5);
		segmentdisplaySize.sendKeys("50");
		segmentdisplayHeight.sendKeys("5");
		CommonUtils.hold(2);
		js.executeScript("window.scrollBy(0,-250)");
		CommonUtils.hold(2);
		contextsegmentSaveAndClose.click();
		CommonUtils.hold(8);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(segmentTable, "Visible", "", driver);
		WebElement segmentRow = driver.findElement(
				By.xpath("//table[contains(@summary,'segment')]//span[contains(@id,'it39') and contains(text(),'"
						+ segName + "')]"));
		if (segmentRow.isDisplayed()) {
			System.out.println("New Segment - " + segName + " is created under Context");
			bSegmentCreated = true;
		} else {
			System.err.println("ERROR - Segment " + segName + " is not displayed under Context");
		}
		return bSegmentCreated;
	}

	public void segmentSaveAndClose() throws Exception {
		CommonUtils.hold(4);
		effUtils.scrollToPageTop();
		CommonUtils.hold(4);
		System.out.println("About to save modified Segment");
		CommonUtils.hold(4);
		CommonUtils.explicitWait(editSegmentSaveAndClose, ExplicitWaitCalls.Click.toString(), "", driver);
		editSegmentSaveAndClose.click();
		CommonUtils.hold(4);
		CommonUtils.waitForPageLoad(driver);
		System.out.println("Moving to context from modified segment");
	}

	private boolean searchValueSet(String VSCode) {
		boolean isAvailable = false;
		segmentVSselect.click();
		CommonUtils.waitForElement(vsSearchLink, driver);
		CommonUtils.hold(10);
		vsSearchLink.click();
		CommonUtils.hold(10);
		// driver.switchTo().frame(vsPopupSearch);
		searchNametextBox.sendKeys(VSCode);
		CommonUtils.hold(3);
		searchButton.click();
		CommonUtils.hold(6);
		/*
		 * effUtils.waitConditionForElement(driver.findElement(By.xpath(
		 * "//tbody//*[contains(text(),'"+VSCode+"')]")));
		 */
		WebElement vsSearchName = driver.findElement(By.xpath("//tbody//*[contains(text(),'" + VSCode + "')]"));
		CommonUtils.hold(10);
		vsSearchName.click();
		vsSearchName.click();
		CommonUtils.hold(5);
		searchOKBtn.click();
		CommonUtils.hold(5);
		CommonUtils.waitForElement(valueSetName, driver);
		if (valueSetName.getAttribute("value").equals(VSCode)) {
			System.out.println("Correct Value Set - " + valueSetName.getAttribute("value") + " is selected");
			isAvailable = true;
		} else {
			System.err.println("Incorrect Value Set - " + valueSetName.getAttribute("value") + " is displayed");
		}
		return isAvailable;
	}

	public void updateValueSet(String sourcecontext, String sourcesegment) {
		waitConditionForElement(viewValueSet);
		CommonUtils.hold(4);
		viewValueSet.click();
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(editValueSetHeader);
		scrollToPageBottom();
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(vsWhereClauseTextArea);
		CommonUtils.hold(2);
		vsWhereClauseTextArea.clear();
		vsWhereClauseTextArea.sendKeys("LOOKUP_TYPE = :{CONTEXT." + sourcecontext + ";SEGMENT." + sourcesegment
				+ "} or :{CONTEXT." + sourcecontext + ";SEGMENT." + sourcesegment + "} is null");
		CommonUtils.hold(2);
		scrollToPageTop();
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(SaveAndCloseButton);
		SaveAndCloseButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
	}

	public void selectDefaultType(String valuetype, String value) {
		CommonUtils.waitForPageLoad(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(segmentDefaultType);
		CommonUtils.hold(3);
		CommonUtils.selectDropDownElement(segmentDefaultType, valuetype);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		defaultValueText.clear();
		defaultValueText.sendKeys(value);
		CommonUtils.hold(3);
		scrollToPageTop();
	}

	public void selectDefaultSQL(String cName, String sName, WebDriver driver) {
		CommonUtils.waitForPageLoad(driver);
		effUtils.scrollToPageBottom();
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(segmentDefaultType);
		CommonUtils.hold(3);
		CommonUtils.selectDropDownElement(segmentDefaultType, "SQL");
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		defaultValueSQL.clear();
		defaultValueSQL.sendKeys("select lookup_code from AR_LOOKUPS where LOOKUP_TYPE=:{CONTEXT." + cName + ";SEGMENT."
				+ sName + "} and LOOKUP_CODE='Preferred'");
		CommonUtils.hold(3);
		scrollToPageTop();
		CommonUtils.hold(3);
	}

	public void editSegment(String segmentName) {
		CommonUtils.waitForPageLoad(driver);
		waitConditionForElement(editSegment);
		waitConditionForElement(segmentTable);
		CommonUtils.hold(5);
		if (driver.findElement(By.xpath("//span[contains(@id,':it39') and contains(text(),'" + segmentName + "')]"))
				.isDisplayed()) {
			System.out.println("Segment - " + segmentName + " is displayed to edit");
			editSegment.click();
			CommonUtils.waitForPageLoad(driver);
			waitConditionForElement(editSegmentHeader);
		}
	}
	
  public boolean updateGroovyConstantForSegment() {
        CommonUtils.hold(4);
        scrollToPageBottom();
        CommonUtils.selectDropDownElement(segmentDefaultType, "Groovy Expression");
        CommonUtils.hold(8);
        if(driver.findElements(By.xpath("//a[contains(@id,'AP1:cbShowExpressionBuilder')]")).size()>0) {
            System.out.println("Groovy Expression builder is displayed");
            groovyExpBuilder.click();
            CommonUtils.hold(20);
            groovyPanelSplitter.click();
            CommonUtils.hold(5);
            groovyExpBuilderFieldsLink.click();
            CommonUtils.hold(5);
            groovyFieldsVerticalPanelSplit.click(); //expanding bottom table under fields
            if(driver.findElements(By.xpath("//span[contains(@id,'sub1:pc1:t3:0:ot6')]")).size()>0) {
                System.out.println("'CreatedBy' - Display label under Fields table is present");
                groovyFieldsDisplayLabelCreatedBy.click();
                CommonUtils.hold(2);
                groovyFieldsInsertButton.click();
                CommonUtils.hold(8);
                if(driver.findElements(By.xpath("//div[contains(@id,'sub1:expression::cc')]//span[text()='CreatedBy']")).size()>0) {
                    System.out.println("'CreatedBy' - Display Label from Fields table is added to the Script successfully.");
                    CommonUtils.hold(4);
                    groovyFieldsValidateLink.click();
                    CommonUtils.hold(10);
                    if(driver.findElements(By.xpath("//td[text()='Information: Success']")).size()>0) {
                        System.out.println("Groovy validation for Field - 'CreatedBy' is successful.");
                        groovyExpBuilderOkButton.click();
                        CommonUtils.hold(10);
                        if(driver.findElements(By.xpath("//*[contains(@id,'pt1:AP1:it7::content') and text()='CreatedBy']")).size()>0) {
                            System.out.println("'CreatedBy' field successfully added to the Groovy constant");
                            return true;
                        }
                        else {
                            System.err.println("ERROR- 'CreatedBy' field is not added properly to the Groovy Constant");
                            return false;
                        }
 
                    }
                    else {
                        System.err.println("ERROR - Groovy validation for Field - 'CreatedBy' is not successful");
                        return false;
                    }
                }
                else {
                    System.err.println("ERROR - 'CreatedBy' Display label is not added properly");
                    return false;
                }
            }
            else {
                System.err.println("ERROR - 'CreatedBy' Display label is not present under Fields table");
                return false;
            }
        }
        else {
            System.err.println("ERROR - Something went wrong. Groovy Expression Builder is not displayed");
            return false;
        }
    }
 
    public boolean addValidatorToContext(String segmentName) {
        CommonUtils.hold(4);
        scrollToPageBottom();
        CommonUtils.hold(3);
        groovyAddValidatorIcon.click();
        CommonUtils.hold(5);
        validatorCode.sendKeys("Groovy User name Test");
        CommonUtils.hold(4);
        validatorExpression.click();
        CommonUtils.hold(15);
        driver.findElement(By.xpath("//div[contains(@id,'0:sub1:expression::cc')]//pre/span")).click();
        CommonUtils.hold(3);
        driver.findElement(By.xpath("//div[contains(@id,'0:sub1:expression::cc')]//pre/span")).sendKeys("FlexSeg?."+segmentName+".equals('SCMOPERATIONS')");
        CommonUtils.hold(4);
        groovyFieldsValidateLink.click();
        CommonUtils.hold(10);
        if(driver.findElements(By.xpath("//td[text()='Information: Success']")).size()>0) {
            System.out.println("Groovy validation for Field - 'CreatedBy' is successful.");
            groovyExpBuilderOkButton.click();
            CommonUtils.hold(10);
            if(driver.findElements(By.xpath("//span[contains(@id,'pt1:AP1:it7::content') and text()='CreatedBy']")).size()>0) {
                System.out.println("'"+segmentName+"' field successfully added to the Groovy constant");
                return true;
            }
            else {
                System.err.println("ERROR- '"+segmentName+"' field is not added properly to the Groovy Constant");
                return false;
            }
 
        }
        else {
            System.err.println("ERROR - Groovy validation for Field - '"+segmentName+"' is not successful");
            return false;
        }
    /*    groovyPanelSplitter.click();
        CommonUtils.hold(5);
        groovyExpBuilderFieldsLink.click();
        CommonUtils.hold(5);
        groovyFieldObject.click();
        CommonUtils.hold(4);
        groovyFieldChildObject.click();
        CommonUtils.hold(4);
        groovyFieldsVerticalPanelSplit.click(); //expanding bottom table under fields
        if(driver.findElements(By.xpath("//span[contains(@id,'8:ot6') and text()='"+segmentName+"']")).size()>0) {
            System.out.println("Display label for Segment name - "+segmentName+" is displayed");
            driver.findElement(By.xpath("//span[contains(@id,'8:ot6') and text()='"+segmentName+"']")).click();
            CommonUtils.hold(4);
            groovyFieldsInsertButton.click();
            CommonUtils.hold(8);
            if(driver.findElements(By.xpath("//div[contains(@id,'sub1:expression::cc')]//span[text()='FlexSeg?."+segmentName+"']")).size()>0) {
                System.out.println("'"+segmentName+"' - Display Label from Fields table is added to the Script successfully.");
                CommonUtils.hold(4);
                driver.findElement(By.xpath("//pre/span[text()='"+segmentName+"']")).sendKeys(".equals('SCMOPERATIONS')");
                CommonUtils.hold(4);
                groovyFieldsValidateLink.click();
                CommonUtils.hold(10);
                if(driver.findElements(By.xpath("//td[text()='Information: Success']")).size()>0) {
                    System.out.println("Groovy validation for Field - 'CreatedBy' is successful.");
                    groovyExpBuilderOkButton.click();
                    CommonUtils.hold(10);
                    if(driver.findElements(By.xpath("//span[contains(@id,'pt1:AP1:it7::content') and text()='CreatedBy']")).size()>0) {
                        System.out.println("'CreatedBy' field successfully added to the Groovy constant");
                        return true;
                    }
                    else {
                        System.err.println("ERROR- 'CreatedBy' field is not added properly to the Groovy Constant");
                        return false;
                    }
 
                }
                else {
                    System.err.println("ERROR - Groovy validation for Field - '"+segmentName+"' is not successful");
                    return false;
                }
            }
            else {
                System.err.println("ERROR - '"+segmentName+"' Display label is not added properly");
                return false;
            }
        }
        else {
            System.err.println("ERROR - Fields link is not clicked and details are not displayed properly");
            return false;
        }*/
    }

	public void navigateToCategoriesPage() {
		// String currentPageLabel = pageLabel.getText();
		/*
		 * if(currentPageLabel.contains("Edit Extensible Flexfield")) {
		 * System.out.println("Navigated to Categories page"); }
		 * if(currentPageLabel.contains("Manage Contexts") ||
		 * currentPageLabel.contains("Edit Context") ||
		 * currentPageLabel.contains("Edit Segment")) { System.out.
		 * println("Currently Page is not yet present at 'Edit Extensible Flexfield Page'. Current Page - "
		 * +currentPageLabel);
		 * while(!currentPageLabel.contains("Edit Extensible Flexfield")) {
		 * CommonUtils.waitForPageLoad(driver); if(driver.findElements(By.xpath(
		 * "//td[@class='af_commandToolbarButton_link-cell']//span[@class='af_commandToolbarButton_text']"
		 * )).size()>0) { System.out.println("Save & Close link displayed");
		 * contextsegmentSaveAndClose.click(); CommonUtils.waitForPageLoad(driver); }
		 * if(driver.findElements(By.
		 * xpath("//button[contains(@id,'pt1:effPnl:APscl') or contains(@id,'pt1:AP1:APscl') or contains(@id,'AP2:APscl')]"
		 * )).size()>0) { System.out.println("Save & Close button displayed");
		 * SaveAndCloseButton.click(); CommonUtils.waitForPageLoad(driver); }
		 * CommonUtils.hold(5); break; } }
		 */
		System.out.println("Save & Close link displayed");
		CommonUtils.hold(5);
		if(driver.findElements(By.xpath("//td[@class='af_commandToolbarButton_link-cell']//span[@class='af_commandToolbarButton_text']")).size()>0) {
			System.out.println("New Context Save and Close Button displayed");
			effUtils.waitConditionForElement(contextsegmentSaveAndClose);
			contextsegmentSaveAndClose.click();
		}
		else {
			if(driver.findElements(By.xpath("//button[contains(@id,'pt1:effPnl:APscl') or contains(@id,'pt1:AP1:APscl') or contains(@id,'AP2:APscl')]")).size()>0) {
				System.out.println("Existing Context Save and Close Button displayed");
				effUtils.waitConditionForElement(SaveAndCloseButton);
				SaveAndCloseButton.click();
			}
		}
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		System.out.println("Save & Close button displayed");
		CommonUtils.hold(5);
		effUtils.waitConditionForElement(SaveAndCloseButton);
		SaveAndCloseButton.click();
		CommonUtils.waitForPageLoad(driver);
	}

	private boolean searchCategoryFromTable(String sFlexCode, String category) {
		boolean bCategoryExists = false;
		List tableRows = categoryHierarchyTable.findElements(By.tagName("tr"));
		int rowCount = tableRows.size();
		int currentId = rowCount - 1;
		WebElement currentCategory = driver.findElement(
				By.xpath("//tbody//span[contains(@id,'effTTOT0') and contains(text(),'" + category + "')]"));
		if (currentCategory.isDisplayed()) {
			currentCategory.click();
			bCategoryExists = true;
		} else if (rowCount <= 1 && !currentCategory.isDisplayed()) {
			expandRootItemParentLink.click();
			CommonUtils.waitForElement(categoryHierarchyTable, driver);
			// WebElement categoryColumn =
			// driver.findElement(By.xpath("//tbody//*[contains(text(),'"+category+"')]"));
			tableRows = categoryHierarchyTable.findElements(By.tagName("tr"));
			rowCount = tableRows.size();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,250)", "");
			driver.findElement(By.xpath("//div[contains(@id,'ATTp:effTT0::scroller')]")).sendKeys(Keys.PAGE_DOWN);
			currentCategory.click();
			bCategoryExists = true;
		}

		return bCategoryExists;
	}

	public boolean associateContextToCategory(String sFlexCode, String category, String contextCode) throws Exception {
		boolean bAssociatedContext = false;
		//navigateToCategoriesPage();
		if(searchCategoryFromTable(sFlexCode,category)) {
			if(driver.findElement(By.xpath("//*[contains(@class,'af_showDetailHeader_title-text1') and contains(text(),'"+category+"')]")).isDisplayed()) {
				System.out.println("Category - "+category+" is selected in hierarchy");
				selectAddContextToCategory.click();
				CommonUtils.waitForElement(selectContextCode, driver);
				// CommonUtils.hold(5);
				searchAssociateToContext(contextCode);
				CommonUtils.waitForPageLoad(driver);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,-250)");
				CommonUtils.waitForElement(SaveButton, driver);
				CommonUtils.hold(5);
				SaveButton.click();
				CommonUtils.waitForPageLoad(driver);
				WebElement associatedContext = driver.findElement(By.xpath(
						"//table[@class='af_table_data-table af_table_data-table-VH-lines']//span[contains(@id,'eCatCxOT0') and text()='"
								+ contextCode + "']"));
				if (associatedContext.isDisplayed()) {
					System.out.println("Context - " + contextCode + " is associated to Category");
					bAssociatedContext = true;
				} else {
					System.err.println("ERROR - Context - " + contextCode + " is not associated to Category");
				}
			} else {
				System.err.println("ERROR - Incorrect Category selected");
			}
		}
		return bAssociatedContext;
	}

	public boolean associateContextToCategory(String sFlexCode, String category, List<String> contextCodes)
			throws Exception {
		System.out.println(contextCodes);
		boolean bAssociatedContext = false;
		// navigateToCategoriesPage();
		if (searchCategoryFromTable(sFlexCode, category)) {
			if (driver.findElement(By.xpath(
					"//h2[contains(@class,'af_showDetailHeader_title-text1') and contains(text(),'" + category + "')]"))
					.isDisplayed()) {
				System.out.println("Category - " + category + " is selected in hierarchy");
				for (String context : contextCodes) {
					selectAddContextToCategory.click();
					effUtils.waitConditionForElement(selectContextCode);
					CommonUtils.waitForElement(selectContextCode, driver);
					// CommonUtils.hold(5);
					searchAssociateToContext(context);
				}
				CommonUtils.waitForPageLoad(driver);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,-250)");
				CommonUtils.waitForElement(SaveButton, driver);
				CommonUtils.hold(5);
				SaveButton.click();
				CommonUtils.waitForPageLoad(driver);
//				WebElement associatedContext = driver.findElement(By.xpath(
//						"//table[@class='af_table_data-table af_table_data-table-VH-lines']//span[contains(@id,'eCatCxOT0') and text()='"
//								+ contextCode + "']"));
//				if (associatedContext.isDisplayed()) {
//					System.out.println("Context - " + contextCode + " is associated to Category");
//					bAssociatedContext = true;
//				} else {
//					System.err.println("ERROR - Context - " + contextCode + " is not associated to Category");
//				}
			} else {
				System.err.println("ERROR - Incorrect Category selected");
			}
		}
		return true;
	}

	private void searchAssociateToContext(String contextCode) throws Exception {
		// CommonUtils.waitForElement(selectContextCode);
				try {
					CommonUtils.hold(3);
					effUtils.waitConditionForElement(selectContextCode);
					selectContextCode.clear();
					CommonUtils.hold(4);
					selectContextCode.sendKeys(contextCode);
				} catch (NoSuchElementException noElement) {
					CommonUtils.hold(3);
					effUtils.waitConditionForElement(pageContextAssociateCode);
					pageContextAssociateCode.clear();
					CommonUtils.hold(4);
					pageContextAssociateCode.sendKeys(contextCode);
				}
				CommonUtils.hold(2);
				searchButton.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(5);
				WebElement vsSearchName = driver.findElement(By.xpath(
						"//tbody//span[contains(@id,'eCatNotCxOT1') or contains(@id,'eCatPgNCxOT0') and contains(text(),'"
								+ contextCode + "')]"));
				CommonUtils.hold(5);
				CommonUtils.waitForPageLoad(driver);
				try {
					CommonUtils.explicitWait(vsSearchName, "Click", "", driver);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(
							"Element vsSearchName with test - " + vsSearchName.toString() + " is having issue in display");
					e.printStackTrace();
				}
				;
				if (vsSearchName.isDisplayed()) {
					System.out.println("Context code - " + contextCode + "is displayed to associate");
				} else {
					System.err.println("Context code - " + contextCode + " not displayed");
				}
				CommonUtils.hold(3);
				CommonUtils.waitForPageLoad(driver);
				/*
				 * CommonUtils.waitForElement(selectAddContextHeader); CommonUtils.hold(2);
				 * selectAddContextHeader.click();
				 */
				CommonUtils.waitForElement(vsSearchName, driver);
				CommonUtils.hold(3);
				vsSearchName.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.waitForElement(applyButton, driver);
				CommonUtils.hold(3);
				applyButton.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.waitforElementtoClick(40, searchOKBtn, driver);
				CommonUtils.waitForElement(searchOKBtn, driver);
				CommonUtils.hold(5);
				searchOKBtn.click();
				CommonUtils.waitforElementtoClick(40, PageContextAssociateImgIcon, driver);
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(5);
	}
	
	private void associateContextToPage(List<String> contextCodes) throws Exception {
		// CommonUtils.waitForElement(selectContextCode);
		
		for(String contextCode:contextCodes) {
			pageContextAssociateCode.clear();
			pageContextAssociateCode.sendKeys(contextCode);
			CommonUtils.hold(2);
			searchButton.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
			WebElement vsSearchName = driver.findElement(By.xpath(
					"//tbody//span[contains(@id,'eCatNotCxOT1') or contains(@id,'eCatPgNCxOT0') and contains(text(),'"
							+ contextCode + "')]"));
			CommonUtils.waitForPageLoad(driver);
			try {
				CommonUtils.explicitWait(vsSearchName, "Click", "", driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(
						"Element vsSearchName with test - " + vsSearchName.toString() + " is having issue in display");
				e.printStackTrace();
			}
			;
			if (vsSearchName.isDisplayed()) {
				System.out.println("Context code - " + contextCode + "is displayed to associate");
			} else {
				System.err.println("Context code - " + contextCode + " not displayed");
			}
			CommonUtils.waitForPageLoad(driver);
			/*
			 * CommonUtils.waitForElement(selectAddContextHeader); CommonUtils.hold(2);
			 * selectAddContextHeader.click();
			 */
			CommonUtils.waitForElement(vsSearchName, driver);
			CommonUtils.hold(3);
			vsSearchName.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.waitForElement(applyButton, driver);
			CommonUtils.hold(3);
			applyButton.click();
		}
		
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForElement(searchOKBtn1, driver);
		CommonUtils.hold(5);
		searchOKBtn1.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
	}

	public boolean associateContextToItemClass(String contextCode) throws Exception {
		boolean bIsAssociated = false;
		ExplicitWait = new WebDriverWait(driver, 120);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(20);
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(selectAddContextToCategory));
		selectAddContextToCategory.click();
		CommonUtils.waitForElement(selectContextCode, driver);
		// CommonUtils.hold(5);
		searchAssociateToContext(contextCode);
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(itemClassSaveLink));
		CommonUtils.waitForElement(itemClassSaveLink, driver);
		// CommonUtils.hold(5);
		itemClassSaveLink.click();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
				"//table[@class='af_table_data-table af_table_data-table-VH-lines']//span[contains(@id,'eCatCxOT0') and text()='"
						+ contextCode + "']"))));
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(
				"//table[@class='af_table_data-table af_table_data-table-VH-lines']//span[contains(@id,'eCatCxOT0') and text()='"
						+ contextCode + "']"))));
		WebElement associatedContext = driver.findElement(By.xpath(
				"//table[@class='af_table_data-table af_table_data-table-VH-lines']//span[contains(@id,'eCatCxOT0') and text()='"
						+ contextCode + "']"));
		if (associatedContext.isDisplayed()) {
			System.out.println("Context - " + contextCode + " is associated to Category");
			bIsAssociated = true;
		} else {
			System.err.println("ERROR - Context - " + contextCode + " is not associated to Category");
		}
		return bIsAssociated;
	}

	public boolean createNewPage(String pageName, String usageItemName) throws Exception {
		boolean bPageCreated = false;
		ExplicitWait = new WebDriverWait(driver, 120);
		Actions action = new Actions(driver);
		js = (JavascriptExecutor) driver;
		// associatedPagesTabLink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(createNewPageLink, "Click", "", driver);
		createNewPageLink.click();
		CommonUtils.waitForPageLoad(driver);
		// ExplicitWait.until(ExpectedConditions.visibilityOf(displayNewPageName));
		CommonUtils.explicitWait(displayNewPageName, ExplicitWaitCalls.Visible.toString(), "", driver);
		CommonUtils.hold(2);
		displayNewPageName.clear();
		displayNewPageName.sendKeys(pageName);
		CommonUtils.explicitWait(displayNewPageDescription, "Visible", "", driver);
		CommonUtils.hold(2);
		displayNewPageDescription.clear();
		displayNewPageDescription.sendKeys(pageName);
		CommonUtils.hold(2);
		displayNewPageHelpText.clear();
		displayNewPageHelpText.sendKeys(pageName);
		CommonUtils.hold(2);
		displayNewPageUsageTableExpandLink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(displayNewPageUsageTable, "Visible", usageItemName, driver);
		CommonUtils.hold(3);
		try {
			WebElement itemSelect = driver.findElement(By.xpath(
					"//div[contains(@id,'dropdownPopup::dropDownContent::db')]//span[contains(@id,'dropdownPopup::dropDownContent') and text()='"
							+ usageItemName + "']"));
			if (itemSelect.isDisplayed()) {
				System.out.println("Detected item usage - " + usageItemName);

				ExplicitWait.until(ExpectedConditions.elementToBeClickable(itemSelect));
				CommonUtils.scrollToElement(itemSelect, driver);
				action.moveToElement(itemSelect).click().perform();
				// itemSelect.click();
				//CommonUtils.waitForElement(displayNewPageUsageNameTextbox, driver);
				CommonUtils.hold(10);
				pageCreateOkButton.click();
				System.out.println("Usage Item is selected properly");
				CommonUtils.hold(15);
				System.out.println("Created new page - " + pageName);
				CommonUtils.hold(4);
				js.executeScript("window.scrollBy(0,-250)");
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.explicitWait(itemClassSaveLink, ExplicitWaitCalls.Click.toString(), "", driver);
				itemClassSaveLink.click();
				if (pageRowSelect(pageName)) {
					System.out.println("Page row selection successful");
					bPageCreated = true;
				} else {
					System.err.println("ERROR - Page either not created or selected");
				}

			}
		} catch (NoSuchElementException noElement) {
			System.err.println("ERROR - Item usage : " + usageItemName + " not found");

		}
		// CommonUtils.selectDropDownElement(displayNewPageUsageTable, usageItemName);

		return bPageCreated;
	}
	
	public boolean deleteExistingPage(String currentPage) {
		boolean isPageDelete = false;
		WebElement selectPage=driver.findElement(By.xpath("//span[contains(@id,'eCatPgOT0') and text()='"+currentPage+"']"));
		selectPage.click();
		System.out.println("Page - "+selectPage.getText()+" is selected to delete");
		CommonUtils.hold(10);
		CommonUtils.waitforElementtoClick(60, deletePageLink, driver);
		deletePageLink.click();
		CommonUtils.waitForInvisibilityOfElement(selectPage, driver, 60);
		CommonUtils.hold(10);
		effUtils.scrollToPageTop();
		itemClassSaveLink.click();
		CommonUtils.hold(8);
		effUtils.scrollToPageBottom();
		if(driver.findElements(By.xpath("//span[contains(@id,'eCatPgOT0') and text()='"+currentPage+"']")).size()==0) {
			System.out.println("Page deleted successfully");
			isPageDelete = true;
		}
		else {
			System.out.println("Page still displayed even after trying to delete");
		}
		effUtils.scrollToPageTop();
		itemClassSaveAndCloseLink.click();
		CommonUtils.hold(15);
		return isPageDelete;
	}

	public void navigateToAssociatedPages(String pageName, WebDriver driver) throws Exception {
		associatedPagesTabLink.click();
		effUtils.waitConditionForElement(pageNameHeader);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)");
		if (pageNameHeader.getText().contains(pageName)) {
			System.out.println("Correct Page - " + pageName + " is selected");
		} else {
			System.out.println("Current Page - " + pageNameHeader.getText() + " is not same as " + pageName);
			if (pageRowSelect(pageName)) {
				System.out.println("Page row selection successful");
			}
		}
	}

	public void assignContextsToPage(String pageName, List<String> contexts, WebDriver driver, String usageCode)
			throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)");
		CommonUtils.hold(3);
		createNewPage(pageName, usageCode);
		CommonUtils.hold(5);
		PageContextAssociateImgIcon.click();
		CommonUtils.explicitWait(pageContextAssociateCode, "Visible", "", driver);
		CommonUtils.hold(2);
		associateContextToPage(contexts);
	}

	public boolean associateContextToPage(String pageName, String contextCode, String usageItemName) throws Exception {
		boolean bAssociateContextPage = false;
		CommonUtils.hold(5);
		PageContextAssociateImgIcon.click();
		CommonUtils.explicitWait(pageContextAssociateCode, "Visible", "", driver);
		CommonUtils.hold(5);
		searchAssociateToContext(contextCode);
		CommonUtils.hold(3);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-450)");
		try {
			SaveButton.click();
			CommonUtils.hold(4);
			System.out.println("Clicking save button after context associated to page");
			bAssociateContextPage = true;
		} catch (NoSuchElementException noElement) {
			if (itemClassSaveLink.isDisplayed()) {
				System.out.println("Associated Item Class after adding context to page and saving for search");
				itemClassSaveLink.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(4);
				bAssociateContextPage = true;
			}
		}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		return bAssociateContextPage;
	}

	public void saveAndCloseButtonUnderCategoryPages() {
		System.out.println("Saving and closing after Pages associated with contexts");
		CommonUtils.hold(10);
		SaveAndCloseButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
	}

	public void itemClassSave() {
		if (itemClassSaveLink.isDisplayed()) {
			System.out.println("Saving Item Class after adding context to page");
			scrollToPageTop();
			itemClassSaveLink.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(6);
		}
	}

	public void itemClassSaveAndClose() {
		if (itemClassSaveAndCloseLink.isDisplayed()) {
			System.out.println("Saving and Closing Item Class after modifying ...");
			scrollToPageTop();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
			itemClassSaveAndCloseLink.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(6);
		}
	}

	private boolean pageRowSelect(String pageName) throws Exception {
		boolean bPageRowPresent = false;
		WebDriverWait wait = new WebDriverWait(driver, 3000);
		System.out.println("Inside method - pageRowSelect");
		CommonUtils.waitForPageLoad(driver);
		scrollToPageBottom();
		CommonUtils.waitForPageLoad(driver);
		wait.until(ExpectedConditions.visibilityOf(driver
				.findElement(By.xpath("//span[contains(@id,'eCatPgOT0') and contains(text(),'" + pageName + "')]"))));
		wait.until(ExpectedConditions.elementToBeClickable(driver
				.findElement(By.xpath("//span[contains(@id,'eCatPgOT0') and contains(text(),'" + pageName + "')]"))));
		try {
			CommonUtils.hold(10);
			WebElement newPageRow = driver.findElement(By.xpath(
					"//table[@class='af_table_data-table af_table_data-table-VH-lines']//span[contains(@id,'eCatPgOT0') and contains(text(),'"
							+ pageName + "')]"));
			if (newPageRow.isDisplayed()) {
				CommonUtils.hold(5);
				newPageRow.click();
				System.out.println("New Page - " + pageName + " is created and present in table");
				bPageRowPresent = true;
			}
		} catch (StaleElementReferenceException noElement) {
			System.err.println("ERROR - New Page -" + pageName + " is not created");
		}
		return bPageRowPresent;
	}

	public boolean deployFlexFieldIncremental(String flexCode, WebDriver driver) {
		boolean bIncrDeployed = false;
		int timerCount = 0;
		WebDriverWait wait = new WebDriverWait(driver, 6000);
		CommonUtils.hold(10);
		effUtils.waitConditionForElement(DeployFlex);
		DeployFlex.click();
		if (flexCode.equals("EGO_ITEM_EFF")) {
			CommonUtils.hold(240);
		}
		/*
		 * while(deployFlexOkButton.getAttribute("aria-disabled").equals("true") ||
		 * deploymentConfirmText.getText().contains("in progress")) {
		 * System.out.println("Incremental Deployment in progress");
		 * CommonUtils.hold(20); }
		 */
		try {
			CommonUtils.hold(120);
			wait.until(ExpectedConditions.elementToBeClickable(deployFlexOkButton));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (deploymentConfirmText.getText().contains("in progress")) {
				System.out.println("Incremental Deployment taking more time to complete....");
				CommonUtils.hold(60);
				wait.until(ExpectedConditions.elementToBeClickable(deployFlexOkButton));
			}
			e.printStackTrace();
		}
		CommonUtils.hold(4);
		if (deploymentConfirmText.getText().contains("completed successfully")) {
			System.out.println("Incremental Deployment successful");
			bIncrDeployed = true;
		} else {
			System.err.println("ERROR - Deployment is not successful");
		}
		deployFlexOkButton.click();
		CommonUtils.hold(4);
		return bIncrDeployed;
	}

	public boolean deployOfflineFlexField(String flexCode) {
		boolean bSuccess = true;
		WebElement confirmOK = null;
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		System.out.println("Offline Deployment is about to start on flex - " + flexCode);
		DeployOffline.click();
		CommonUtils.hold(4);
		if (driver
				.findElement(By.xpath(
						"//div[contains(@id,'asyncdialogConf::_ttxt') and text()='Offline Deployment Confirmation']"))
				.isDisplayed()) {
			System.out.println("Offline Deployment for " + flexCode + " is started");
			CommonUtils.hold(3);
			confirmOK = driver.findElement(By.xpath("//button[contains(@id,'asyncdialogConf::ok') and text()='OK']"));
			confirmOK.click();
		}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		while (driver.findElement(By.xpath("//img[contains(@id,'_ATp:ffTab:0')]")).getAttribute("title")
				.equals("Edited")) {
			System.out.println("Offline deployment in progress");
			CommonUtils.hold(10);
			searchButton.click();
		}
		if (driver.findElement(By.xpath("//img[contains(@id,'_ATp:ffTab:0')]")).getAttribute("title")
				.equals("Deployed")) {
			System.out.println("Offline deployment completed succesfully");
			bSuccess = true;
		} else {
			System.err.println("ERROR - Some issue with Offline deployment");
		}
		/*
		 * searchButton.click(); try { if(imgDeploymentSuccess.isDisplayed()) {
		 * System.out.println("Offline deployment completed succesfully");
		 * bSuccess=true; } } catch(Exception e){
		 * System.out.println("Offline deployment in progress");
		 * driver.findElement(By.xpath(
		 * "//button[contains(@id,'_ATp:ffTab:1:asyncDeploymentStatusDeploying')]")).
		 * click(); CommonUtils.waitForPageLoad(driver);
		 * CommonUtils.waitForElement(offlineDeployInProgressLogs);
		 * ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
		 * offlineDeployInProgressLogs); CommonUtils.hold(5);
		 * offlineDeployOkButton.click(); CommonUtils.hold(4); }
		 */
		return bSuccess;
	}

	public void saveAndCloseContexts() {
		CommonUtils.hold(8);
		SaveAndCloseButton.click();
		CommonUtils.hold(8);
	}
	
	public boolean itemClassExists(String itemClassName) throws Exception {
		boolean bIsItemClassExists = false;
		CommonUtils.hold(5);
		waitConditionForElement(itemClassTreeView);
		CommonUtils.hold(3);
		try {
			if (itemClassSearchByName(itemClassName)) {
				System.out.println("Item Class - " + itemClassName + " search completed successfully by name search");
				bIsItemClassExists = true;
			} else {
				System.out.println("Item Class name search could not find the Item Class - " + itemClassName
						+ ". Need to create a new Item Class");
			}
		} catch (NoSuchElementException exception) {
			exception.printStackTrace();
			System.out.println("ALERT - Moved to catch block of ItemClassExists");
		}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(8);
		return bIsItemClassExists;
	}

	public boolean itemClassSearchByName(String itemClassName) throws Exception {
		boolean isItemClassExistsInSearch = false;
		CommonUtils.hold(2);
		waitConditionForElement(itemClassSearchNameInputbox);
		itemClassSearchNameInputbox.clear();
		itemClassSearchNameInputbox.sendKeys(itemClassName);
		waitConditionForElement(searchButton);
		CommonUtils.hold(5);
		searchButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		waitConditionForElement(itemClassSearchResultsHeaderTable);
		// CommonUtils.waitForElement(itemClassSearchResultsHeaderTable);
		if (driver.findElements(By.xpath("//table//span[contains(@id,':ot6') and text()='" + itemClassName + "']"))
				.size()>0) {
			System.out.println("Item Class - " + itemClassName + " is displayed in Item Classes Table");
			WebElement itemClassPath = driver
					.findElement(By.xpath("//table//span[contains(@id,':ot6') and text()='" + itemClassName
							+ "']/parent::td/following-sibling::td/child::span[contains(@id,'otHierarchyPath')]"));
			System.out.println("Path for the Item Class - " + itemClassPath.getText());
			isItemClassExistsInSearch = true;
		}
		else {
			System.out.println("Could not find Item class - " + itemClassName);
		}
		/*try {
			if (driver.findElement(By.xpath("//table//span[contains(@id,':ot6') and text()='" + itemClassName + "']"))
					.isDisplayed()) {
				System.out.println("Item Class - " + itemClassName + " is displayed in Item Classes Table");
				WebElement itemClassPath = driver
						.findElement(By.xpath("//table//span[contains(@id,':ot6') and text()='" + itemClassName
								+ "']/parent::td/following-sibling::td/child::span[contains(@id,'otHierarchyPath')]"));
				System.out.println("Path for the Item Class - " + itemClassPath.getText());
				isItemClassExistsInSearch = true;
			}
		} catch (NoSuchElementException exception) {
			System.out.println("Could not find Item class - " + itemClassName);
			exception.printStackTrace();
		}*/
		CommonUtils.hold(3);
		okButton_itemClassSearch.click();
		CommonUtils.hold(3);
		return isItemClassExistsInSearch;
	}

	public void closeItemClassTaskFlow() throws Exception {
		effUtils.scrollToPageTop();
		wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOf(doneButton));
		wait.until(ExpectedConditions.elementToBeClickable(doneButton));
		CommonUtils.hold(4);
		doneButton.click();
	}

	public void createItemClass(String parentItemClass, String itemClassText, WebDriver driver) throws Exception {
		// String itemClassText = "1ANode";
		System.out.println("Starting new Itemclass creation - " + itemClassText);
		CommonUtils.waitForPageLoad(driver);
		/*
		 * WebElement parentItemClassLink =
		 * driver.findElement(By.xpath("//a[text()='"+parentItemClass+"']")); WebElement
		 * parentItemClassRow =
		 * driver.findElement(By.xpath("//span[text()='"+parentItemClass+"']"));
		 * CommonUtils.explicitWait(parentItemClassLink, "Click", "",driver);
		 */
		/*
		 * if
		 * (driver.findElements(By.xpath("//a[text()='"+parentItemClass+"']")).size()>0)
		 * { CommonUtils.hold(3);
		 * System.out.println("Parent Item Class - "+parentItemClass+" is displayed");
		 * 
		 * } else { js.executeScript("arguments[0].scrollIntoView(true);",
		 * driver.findElement(By.xpath("//a[text()='"+parentItemClass+"']")));
		 * EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		 * eventDriver.
		 * executeScript("document.querySelector('//div[contains(@id,'applicationsTreeTable2:_ATTp:tt1') and @class='AFStretchWidth af_treeTable AFStretchWidth']').scrollTop=-50"
		 * ); }
		 */
		itemClassSearchByName(parentItemClass);
		WebElement parentItemClassLink = driver.findElement(By.xpath("//a[text()='" + parentItemClass + "']"));
		WebElement parentItemClassRow = driver.findElement(By.xpath("//span[contains(@id,':ot8') and text()='" + parentItemClass + "']"));
		/*CommonUtils.hold(15);
		CommonUtils.explicitWait(parentItemClassLink, "Click", "", driver);
		effUtils.scrollToPageTop();
		CommonUtils.waitforElementtoClick(300, parentItemClassRow, driver);
		parentItemClassRow.click();*/
		CommonUtils.hold(8);
		createItemClassLinkIcon.click();
		CommonUtils.hold(8);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(itemSaveAndCloseButton, "Click", "", driver);
		itemClassName.sendKeys(itemClassText);
		CommonUtils.hold(3);
		itemInternalName.sendKeys(itemClassText);
		CommonUtils.hold(3);
		itemDescriptionTextArea.sendKeys(itemClassText);
		CommonUtils.hold(3);
		waitConditionForElement(itemSaveAndCloseButton);
		CommonUtils.hold(8);
		itemSaveAndCloseButton.click();
		CommonUtils.hold(15);
		waitConditionForElement(searchButton);
		System.out.println("End of Item creation");
	}

	public void navigateToPageAttributeGroups(String itemClassName) throws Exception {
		wait = new WebDriverWait(driver, 3000);
		CommonUtils.hold(20);
		wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='" + itemClassName + "']"))));
		wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.xpath("//a[text()='" + itemClassName + "']"))));
		WebElement itemClassLink = driver.findElement(By.xpath("//a[text()='" + itemClassName + "']"));
		try {
			if (itemClassLink.isDisplayed()) {
				System.out.println("Item Class link - " + itemClassName + " found");
				CommonUtils.hold(10);
				itemClassLink.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(20);
				wait.until(ExpectedConditions.visibilityOf(pagesAndAttributeGroupslink));
				/*wait.until(ExpectedConditions.elementToBeClickable(pagesAndAttributeGroupslink));*/
				System.out.println("Item Class page is loaded");
				pagesAndAttributeGroupslink.click();
				System.out.println("Pages and Attribute Groups link clicked");
				CommonUtils.waitForPageLoad(driver);
				wait.until(ExpectedConditions.visibilityOf(attributeGroupslink));
				wait.until(ExpectedConditions.elementToBeClickable(attributeGroupslink));
				wait.until(ExpectedConditions.elementToBeClickable(selectAddContextToCategory));
			}
		} catch (NoSuchElementException noElement) {
			noElement.printStackTrace();
			System.err.println("No Item Class Element with name - " + itemClassName + " is found");
		}
	}

	public void attributeGroupsLinkClick() {
		CommonUtils.waitForPageLoad(driver);
		scrollToPageBottom();
		CommonUtils.hold(6);
		waitConditionForElement(attributeGroupslink);
		CommonUtils.hold(6);
		attributeGroupslink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(6);
	}

	public boolean deleteCurrentContextFromPage(String contextCode, WebDriver driver) throws Exception {
		boolean bIsDeleted = false;
		wait = new WebDriverWait(driver, 300);
		CommonUtils.waitForPageLoad(driver);
		scrollToPageBottom();
		CommonUtils.hold(6);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(6);
		try {
			CommonUtils.explicitWait(
					driver.findElement(By.xpath("//span[contains(@id,'eCatPgCxOT0') or contains(@id,'eCatCxOT0')]")),
					ExplicitWaitCalls.Visible.toString(), "", driver);
			System.out.println("Inside Delete context method");
			if (driver.findElements(By.xpath("//span[contains(@id,'eCatPgCxOT0')or contains(@id,'eCatCxOT0')]"))
					.size() > 0) {
				CommonUtils.hold(5);
				System.out.println("Context table loaded");
				if (driver.findElement(By.xpath("//span[text()='" + contextCode + "']")).isDisplayed()) {
					wait.until(ExpectedConditions.elementToBeClickable(
							driver.findElement(By.xpath("//span[text()='" + contextCode + "']"))));
					driver.findElement(By.xpath("//span[text()='" + contextCode + "']")).click();
					CommonUtils.waitForPageLoad(driver);
					waitConditionForElement(deleteContextFromPageLink);
					CommonUtils.hold(4);
					deleteContextFromPageLink.click();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(6);
					scrollToPageTop();
					CommonUtils.waitForPageLoad(driver);
					waitConditionForElement(itemClassSaveLink);
					itemClassSaveLink.click();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(6);
					scrollToPageBottom();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(6);
					if (driver.findElements(By.xpath("//span[text()='" + contextCode + "']")).size() == 0) {
						bIsDeleted = true;
						System.out.println("Context - " + contextCode + " is deleted");
					} else {
						System.err.println("Context - " + contextCode + " is still present");
					}
				} else {
					System.err.println("ERROR - Context code not found");
				}
			}
		} catch (NoSuchElementException noElement) {
			System.err.println("Context Table under Page is empty");
		}
		return bIsDeleted;
	}

	public boolean deleteContextFromCategory(String contextCode, WebDriver driver) throws Exception {
		boolean bIsDeleted = true;
		wait = new WebDriverWait(driver, 300);
		CommonUtils.waitForPageLoad(driver);
		scrollToPageBottom();
		CommonUtils.hold(6);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(6);
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(@id,'eCatCxOT0')]")),
					ExplicitWaitCalls.Visible.toString(), "", driver);
			System.out.println("Inside Delete context method");
			if (driver.findElements(By.xpath("//span[contains(@id,'eCatCxOT0')]")).size() > 0) {
				CommonUtils.hold(5);
				System.out.println("Context table loaded");
				if (driver.findElement(By.xpath("//span[text()='" + contextCode + "']")).isDisplayed()) {
					wait.until(ExpectedConditions.elementToBeClickable(
							driver.findElement(By.xpath("//span[text()='" + contextCode + "']"))));
					driver.findElement(By.xpath("//span[text()='" + contextCode
							+ "']/parent::a/parent::td/following-sibling::td//span[contains(@id,'eCatCxOT3')]"))
							.click();
					CommonUtils.waitForPageLoad(driver);
					waitConditionForElement(deleteContextFromPageLink);
					CommonUtils.hold(4);
					deleteContextFromPageLink.click();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(6);
					scrollToPageTop();
					CommonUtils.waitForPageLoad(driver);
					waitConditionForElement(itemClassSaveLink);
					itemClassSaveLink.click();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(6);
					scrollToPageBottom();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(6);
					if (driver.findElements(By.xpath("//span[text()='" + contextCode + "']")).size() == 0) {
						bIsDeleted = true;
						System.out.println("Context - " + contextCode + " is deleted");
					} else {
						System.err.println("Context - " + contextCode + " is still present");
					}
				} else {
					System.err.println("ERROR - Context code not found");
				}
			}
		} catch (NoSuchElementException noElement) {
			System.err.println("Context Table under ItemClass/Category is empty");
		}
		return bIsDeleted;
	}

	public void navigateToItemClassSecurity(String itemClassName) throws Exception {
		WebElement itemClassLink = driver.findElement(By.xpath("//a[text()='" + itemClassName + "']"));
		try {
			if (itemClassLink.isDisplayed()) {
				System.out.println("Item Class link - " + itemClassName + " found");
				waitConditionForElement(itemClassLink);
				CommonUtils.hold(10);
				itemClassLink.click();
				CommonUtils.hold(30);
				waitConditionForElement(securityLink);
				CommonUtils.explicitWait(securityLink, "Click", "", driver);
				System.out.println("Item Class page is loaded");
				securityLink.click();
				System.out.println("Security link under ItemClass clicked");
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(20);
			}
		} catch (NoSuchElementException noElement) {
			noElement.printStackTrace();
			System.err.println("No Item Class Element with name - " + itemClassName + " is found");
		}
	}
	
	public boolean searchPageUnderItemClass(String pageName) throws Exception {
		boolean bPageExists = false;
		waitConditionForElement(itemClassPagesLink);
		itemClassPagesLink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		scrollToPageBottom();
		CommonUtils.hold(4);
		
			if (driver
					.findElements(By.xpath("//span[text()='" + pageName
							+ "']//ancestor::table[contains(@summary,'pages associated to selected category')]"))
					.size()>0) {
				System.out.println("Page - " + pageName + " is displayed");
				bPageExists = true;
				CommonUtils.hold(3);
				pageRowSelect(pageName);
			}
		
			else {
				System.out.println("Page - "+pageName+" does not exist under current Item class");
			}
		
		return bPageExists;
	}
	
	public boolean navigateToPageLinkUnderItemClass(String pageName, String itemUsage, WebDriver driver)
			throws Exception {
		boolean bPageExists = false;
		waitConditionForElement(itemClassPagesLink);
		itemClassPagesLink.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		scrollToPageBottom();
		CommonUtils.hold(4);
		try {
			if (driver
					.findElement(By.xpath("//span[text()='" + pageName
							+ "']//ancestor::table[contains(@summary,'pages associated to selected category')]"))
					.isDisplayed()) {
				System.out.println("Page - " + pageName + " is displayed");
				bPageExists = true;
				CommonUtils.hold(3);
				pageRowSelect(pageName);
			}
		} catch (NoSuchElementException noElement) {
			// TODO: handle exception
			System.out.println("Page - " + pageName + " is not displayed");
			if (createNewPage(pageName, itemUsage)) {
				System.out.println("Page creation of - " + pageName + " is successful");
				bPageExists = true;
				/*
				 * js.executeScript("window.scrollBy(0,-200)"); itemClassSaveLink.click();
				 * CommonUtils.hold(3); pageRowSelect(pageName);
				 */
			} else {
				System.err.println("ERROR - Page creation for " + pageName + " failed");
			}
		}
		return bPageExists;
	}

	public void navigateToPIMManageDataSecurityPolicies(WebDriver driver) {
		boolean bPolicySet = false;
		CommonUtils.hold(10);
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait = new WebDriverWait(driver, 120);
		if(driver.findElements(By.xpath("//div[@id='_FOd1::msgDlg']")).size()>0) {
			System.out.println("Warning Message displayed for Security Console");
			ExplicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='_FOd1::msgDlg']")));
			ExplicitWait.until(ExpectedConditions.elementToBeClickable(warningMessageOkButton));
			CommonUtils.hold(3);
			warningMessageOkButton.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(3);
		}
		else {
			System.out.println("WARNING message dialog not displayed");
		}
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(administrationLink));
		administrationLink.click();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(manageDatbaseResources));
		CommonUtils.hold(10);
		manageDatbaseResources.click();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(objectName));
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(searchButton_ObjectName));
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		objectName.clear();
		objectName.sendKeys("EGO_ITEM_EFF_B");
		CommonUtils.hold(3);
		searchButton_ObjectName.click();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//div[contains(@id,'dbResourceTbl::db')]"))));
		if (driver.findElements(By.xpath("//div[contains(@id,'dbResourceTbl::db')]//span[text()='EGO_ITEM_EFF_B']"))
				.size() > 0) {
			System.out.println("Result of Object search - " + driver
					.findElement(By.xpath("//div[contains(@id,'dbResourceTbl::db')]//span[text()='EGO_ITEM_EFF_B']"))
					.getText() + " is valid");
			driver.findElement(By.xpath("//div[contains(@id,'dbResourceTbl::db')]//span[text()='EGO_ITEM_EFF_B']"))
					.click();
			CommonUtils.hold(4);
			objectEditIcon.click();
			CommonUtils.waitForPageLoad(driver);
			ExplicitWait.until(ExpectedConditions.elementToBeClickable(actionLink));
			CommonUtils.hold(3);
			actionLink.click();
			CommonUtils.waitForPageLoad(driver);
			ExplicitWait.until(ExpectedConditions.visibilityOf(objectsTable));
			ExplicitWait.until(ExpectedConditions.elementToBeClickable(addActionObjectLink));
			CommonUtils.hold(3);
		} else {
			System.out.println("Could not find EGO_ITEM_EFF_B");
		}
	}

	public boolean selectSecurityPrivilege(String privilegeType, String privilegeName) throws Exception {
		boolean bprivilegeTypeSelected = false;
		wait = new WebDriverWait(driver, 300);
		CommonUtils.waitForPageLoad(driver);
		scrollToPageBottom();
		CommonUtils.hold(5);
		if (privilegeType.equals("View Privilege")) {
			CommonUtils.selectDropDownElement(viewPrivilegesDropDown, privilegeName);
			CommonUtils.hold(4);
			scrollToPageTop();
			CommonUtils.waitForPageLoad(driver);
			contextSaveBtn.click();
			CommonUtils.hold(6);
			if (confirmContextSecurityPrivilegeSelected(privilegeType, privilegeName, driver)) {
				System.out.println("View Privilege type - " + privilegeName + " is selected");
				bprivilegeTypeSelected = true;
			} else {
				System.err.println("ERROR - View Privileges not applied");
			}
		}
		if (privilegeType.equals("Edit Privilege")) {
			CommonUtils.selectDropDownElement(editPrivilegesDropDown, privilegeName);
			CommonUtils.hold(4);
			scrollToPageTop();
			CommonUtils.waitForPageLoad(driver);
			contextSaveBtn.click();
			CommonUtils.hold(6);
			if (confirmContextSecurityPrivilegeSelected(privilegeType, privilegeName, driver)) {
				System.out.println("Edit Privilege type - " + privilegeName + " is selected");
				bprivilegeTypeSelected = true;
			} else {
				System.err.println("ERROR - Edit Privileges not applied");
			}
		}
		return bprivilegeTypeSelected;
	}

	private boolean confirmContextSecurityPrivilegeSelected(String privilegeType, String privilegeName,
			WebDriver driver) throws Exception {
		boolean bSelected = false;
		scrollToPageBottom();
		CommonUtils.hold(4);
		if (privilegeType.equals("View Privilege")) {
			CommonUtils.explicitWait(viewPrivilegesDropDown, ExplicitWaitCalls.ElementVisible.toString(), "", driver);
			if (viewPrivilegesDropDown.getAttribute("title").equals(privilegeName)) {
				System.out.println("View Privileges are applied properly");
				bSelected = true;
			} else {
				System.err.println("View Privileges are not applied properly. Current value - "
						+ viewPrivilegesDropDown.getAttribute("title"));
			}
		}
		if (privilegeType.equals("Edit Privilege")) {
			CommonUtils.explicitWait(editPrivilegesDropDown, ExplicitWaitCalls.ElementVisible.toString(), "", driver);
			if (editPrivilegesDropDown.getAttribute("title").equals(privilegeName)) {
				System.out.println("Edit Privileges are applied properly");
				bSelected = true;
			} else {
				System.err.println("Edit Privileges are not applied properly. Current value - "
						+ editPrivilegesDropDown.getAttribute("title"));
			}
		}
		return bSelected;
	}

	public void saveSecurityPolicy() {
		saveObjectsButton.click();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(submitPolicyObjectsButton));
		CommonUtils.hold(3);
		submitPolicyObjectsButton.click();
		CommonUtils.hold(3);
		ExplicitWait.until(ExpectedConditions.visibilityOf(confirmationDialogText));
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(okButton_ConfirmationDialog));
		CommonUtils.hold(3);
		okButton_ConfirmationDialog.click();
		CommonUtils.waitForPageLoad(driver);
		ExplicitWait.until(ExpectedConditions.elementToBeClickable(doneButton_SecurityPolicy));
		CommonUtils.hold(3);
	}

	public boolean findSecurityPolicyUnderActions(String policy) {
		boolean bPolicySet = false;
		System.out.println("Current size of Objects table -" + objectsTable.getAttribute("_rowcount"));
		int tableRowSize = Integer.valueOf(objectsTable.getAttribute("_rowcount"));
		/*
		 * for(int cRow=0;cRow<tableRowSize;cRow++) {
		 * if(driver.findElement(By.xpath("//input[contains(@id,'actionTable:"+cRow+
		 * ":it3::content')]")).getAttribute("value").equals("VIEW_EFF_BI")) {
		 * System.out.println("Current row has - VIEW_EFF_BI"); break; }
		 * 
		 * }
		 */
		if (driver.findElements(By.xpath("//*[@value='" + policy + "' or text()='" + policy + "']")).size() > 0) {
			System.out.println("Policy - " + policy + " is present under Data security");
			bPolicySet = true;
		} else {
			addActionObjectLink.click();
			CommonUtils.waitForPageLoad(driver);
			ExplicitWait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it3::content')]")));
			driver.findElement(By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it3::content')]"))
					.clear();
			driver.findElement(By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it3::content')]"))
					.sendKeys(policy);
			CommonUtils.hold(3);
			ExplicitWait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it5::content')]")));
			driver.findElement(By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it5::content')]"))
					.clear();
			driver.findElement(By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it5::content')]"))
					.sendKeys(policy);
			CommonUtils.hold(3);
			ExplicitWait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it4::content')]")));
			driver.findElement(By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it4::content')]"))
					.clear();
			driver.findElement(By.xpath("//input[contains(@id,'actionTable:" + tableRowSize + ":it4::content')]"))
					.sendKeys(policy);
			CommonUtils.hold(3);

			bPolicySet = true;
		}
		return bPolicySet;
	}

	public boolean searchUserPrivilege(String childItemClassName2, String userName) throws Exception {
		boolean bUserExists = false;
		CommonUtils.waitForPageLoad(driver);
		// CommonUtils.ExplicitWait(actionLink,"Click","");
		CommonUtils.hold(10);
		try {
			if (driver.findElement(By.xpath("//div[contains(@id,'_ATp:_qbeTbr')]/a")).getAttribute("aria-pressed")
					.equals("false")) {
				System.out.println("Filters is hidden by default.Expanding filters");
				driver.findElement(By.xpath("//div[contains(@id,'_ATp:_qbeTbr')]/a[@aria-pressed='false']")).click();
				waitConditionForElement(
						driver.findElement(By.xpath("//div[contains(@id,'_ATp:_qbeTbr')]/a[@aria-pressed='true']")));
				waitConditionForElement(itemClassFilterInputText);
				CommonUtils.hold(3);
			}
			if (driver.findElement(By.xpath("//div[contains(@id,'_ATp:_qbeTbr')]/a")).getAttribute("aria-pressed")
					.equals("true")) {
				System.out.println("Filters is displayed by default.Expanding filters");
				itemClassFilterInputText.clear();
				CommonUtils.hold(2);
				userNameFilter.sendKeys(userName);
				CommonUtils.hold(2);
				itemOrgFilter.sendKeys("V1");
				CommonUtils.hold(2);
				itemClassFilterInputText.sendKeys(childItemClassName2);
				CommonUtils.hold(2);
				itemClassFilterInputText.sendKeys(Keys.ENTER);
				CommonUtils.hold(15);
				int totalRows = driver.findElements(By.xpath("//span[contains(@id,'ot') and text()='1SecNode2']"))
						.size();
				if (totalRows == 1) {
					System.out.println("No. of rows with ItemClass -" + childItemClassName2 + " is : " + driver
							.findElements(By.xpath("//span[contains(@id,'ot') and text()='1SecNode2']")).size());
					driver.findElement(By.xpath("//span[contains(@id,'ot') and text()='1SecNode2']")).click();
					CommonUtils.waitForPageLoad(driver);
					CommonUtils.hold(2);
					bUserExists = true;
				}
			} else {
				System.err.println("ERROR - " + userName + " with V1 not displayed");
			}
		} catch (NoSuchElementException noElement) {
			System.out.println("Current Item - " + childItemClassName2 + " class does not have any security roles");
		}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		return bUserExists;
	}

	public void createUserNamePrivilege(String userName, WebDriver driver) throws Exception {
		waitConditionForElement(createActions);
		CommonUtils.hold(8);
		createActions.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		CommonUtils.selectDropDownElement(principalSelect, "Person");
		CommonUtils.hold(4);
		expandUserNameLOV.click();
		CommonUtils.hold(8);
		usernameSearchLink.click();
		CommonUtils.hold(5);
		if (searchText("userName", userName, driver)) {
			System.out.println("Role - " + userName + " is applied");
			CommonUtils.hold(10);
			waitConditionForElement(expandOrgLOV);
			expandOrgLOV.click();
			CommonUtils.hold(4);
			usernameSearchLink.click();
			CommonUtils.hold(3);
			if (searchText("Org", "V1", driver)) {
				System.out.println("Org - V1 is selected");
				scrollToPageTop();
				CommonUtils.hold(3);
				waitConditionForElement(itemClassSaveLink);
				CommonUtils.hold(3);
				itemClassSaveLink.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(5);
			} else {
				System.err.println("Org - V1 is not properly applied");
			}
		} else {
			System.err.println("Role - PIMQA is not properly applied");
		}
	}

	public boolean searchText(String textType, String textName, WebDriver driver) {
		boolean isAvailable = false;
		searchNametextBox.sendKeys(textName);
		searchButton.click();
		CommonUtils.hold(5);
		if (textType.equals("userName")) {
			if (driver.findElement(By.xpath("//span[contains(@id,'0:_afrColChild0') and text()='" + textName + "']"))
					.isDisplayed()) {
				System.out.println("Correct Role - PIM is selected");
				CommonUtils.hold(4);
				driver.findElement(By.xpath("//span[contains(@id,'0:_afrColChild0') and text()='" + textName + "']"))
						.click();
				CommonUtils.hold(4);
				applyAndCloseSearch();
				isAvailable = true;
			} else {
				System.err.println("Incorrect Role");
			}
		}
		if (textType.equals("Org")) {
			if (driver.findElement(By.xpath("//span[contains(@id,'0:_afrColChild0') and text()='" + textName + "']"))
					.isDisplayed()) {
				System.out.println("Correct Org - V1 is selected");
				driver.findElement(By.xpath("//span[contains(@id,'0:_afrColChild0') and text()='" + textName + "']"))
						.click();
				applyAndCloseSearch();
				isAvailable = true;
			} else {
				System.err.println("Incorrect Org");
			}
		}

		return isAvailable;
	}

	private void applyAndCloseSearch() {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		searchOKBtn.click();
		CommonUtils.hold(10);
		CommonUtils.waitForPageLoad(driver);
	}

	public void addPrivilegetoRole(String privilegeName) {
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		scrollToPageBottom();
		CommonUtils.hold(2);
		actionsSelctAndAdd.click();
		CommonUtils.waitForPageLoad(driver);
		if (driver.findElements(By.xpath("//input[contains(@id,'qryId1:value00::content')]")).size() == 0) {
			driver.findElement(By.xpath("//a[contains(@id,'qryId1::_afrDscl')]")).click();
			CommonUtils.waitForPageLoad(driver);
			System.out.println("Expanding search");
		}
		CommonUtils.hold(4);
		actionsInputText.clear();
		actionsInputText.sendKeys(privilegeName);
		CommonUtils.hold(4);
		searchButton.click();
		CommonUtils.hold(4);
		/*
		 * if(privilegeName.equals("VIEW_EFF_BI")) {
		 * driver.findElement(By.xpath("//a[contains(@id,'qryId1::_afrDscl')]")).click()
		 * ; waitConditionForElement(driver.findElement(By.xpath(
		 * "//input[contains(@id,'qryId1:value00::content')]"))); }
		 */
		CommonUtils.hold(4);
		driver.findElement(By.xpath("//span[contains(@id,'soc4::content')]")).click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(2);
		applyButton.click();
		CommonUtils.waitForPageLoad(driver);
		applyAndCloseSearch();
		/*
		 * try { CommonUtils.explicitWait(driver.findElement(By.
		 * xpath("//span[contains(@id,'resId1:0:ot10') and text()='"+privilegeName+"']")
		 * ), ExplicitWaitCalls.Click.toString(), privilegeName); } catch
		 * (NoSuchElementException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); waitConditionForElement(driver.findElement(By.xpath(
		 * "//span[contains(@id,'resId1:0:ot10')]"))); } catch(Exception e) {
		 * waitConditionForElement(driver.findElement(By.
		 * xpath("//span[contains(@id,'resId1:0:ot10') and text()='"+privilegeName+"']")
		 * )); } if(driver.findElement(By.
		 * xpath("//span[contains(@id,'resId1:0:ot10') and text()='"+privilegeName+"']")
		 * ).isDisplayed()) { CommonUtils.waitForPageLoad(driver); CommonUtils.hold(4);
		 * System.out.println("Privilege - "+privilegeName+" is displayed");
		 * driver.findElement(By.
		 * xpath("//span[contains(@id,'resId1:0:ot10') and text()='"+privilegeName+"']")
		 * ).click(); CommonUtils.waitForPageLoad(driver); CommonUtils.hold(2);
		 * applyButton.click(); CommonUtils.waitForPageLoad(driver);
		 * applyAndCloseSearch(); }
		 */
	}

	public boolean searchPrivilege(String privilegeName) {
		boolean bPrivilegeExists = false;
		scrollToPageBottom();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		if (driver.findElements(By.xpath("//span[contains(@id,':ot1') and text()='" + privilegeName + "']"))
				.size() > 0) {
			System.out.println("Privilege - " + privilegeName + " is already added to PIMQA");
			bPrivilegeExists = true;
			CommonUtils.hold(4);
			scrollToPageTop();
			CommonUtils.hold(3);
			itemClassSave();
		}
		return bPrivilegeExists;
	}

	public boolean deleteRole() throws Exception {
		boolean bDelete = false;

		CommonUtils.hold(4);
		waitConditionForElement(deleteRole);
		deleteRole.click();
		CommonUtils.hold(5);
		waitConditionForElement(deleteWarningMessage);
		CommonUtils.explicitWait(deleteWarningMessage, "TextPresent", "Warning", driver);
		CommonUtils.explicitWait(yesButton_WarningMessage, "Click", "", driver);
		yesButton_WarningMessage.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(15);
		waitConditionForElement(itemClassSaveLink);
		itemClassSaveLink.click();
		CommonUtils.waitForPageLoad(driver);
		if (driver.findElements(By.xpath("//span[contains(@id,'0:ot5') and text()='1SecNode2']")).size() == 0) {
			System.out.println("Deletion of role successful");
			bDelete = true;
		} else {
			System.out.println("Either role PIMQA not present or Deletion of role unsuccessful.Try again..");
		}
		return bDelete;
	}

	public void scrollToPageTop() {
		System.out.println("Scrolling to top of page");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-450)");
	}

	public void scrollToPageBottom() {
		System.out.println("Scrolling to Bottom of page");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,450)");
	}

	private void waitConditionForElement(WebElement element) {
		wait = new WebDriverWait(driver, 300);
		CommonUtils.waitForPageLoad(driver);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public boolean searchVSCode(String vsCode) {
		boolean bVSCode = false;
		CommonUtils.hold(4);
		waitConditionForElement(vsCodeSearchInputText);
		vsCodeSearchInputText.clear();
		System.out.println("Searching for " + vsCode + " ............");
		CommonUtils.hold(2);
		vsCodeSearchInputText.sendKeys(vsCode);
		CommonUtils.hold(4);
		System.out.println("Text displayed in VSCode text box - " + vsCodeSearchInputText.getAttribute("value"));
		waitConditionForElement(searchButton);
		searchButton.click();
		CommonUtils.hold(10);
		/*
		 * try { CommonUtils.hold(4);
		 * waitConditionForElement(driver.findElement(By.xpath(
		 * "//span[contains(@id,'ATp:ATt1:0:ot4')]")));
		 * System.out.println("Result valueset displayed - "+driver.findElement(By.xpath
		 * ("//span[contains(@id,'ATp:ATt1:0:ot4')]")).getText()); }
		 * catch(NoSuchElementException noElement) {
		 * System.out.println("Current Value set seems to be not present "); }
		 */
		System.out.println("size of table - " + driver
				.findElement(By.xpath("//div[contains(@id,'_ATp:ATt1::db')]//table[contains(@summary,'above search')]"))
				.getAttribute("_rowcount"));
		int rowCount = Integer.parseInt(driver
				.findElement(By.xpath("//div[contains(@id,'_ATp:ATt1::db')]//table[contains(@summary,'above search')]"))
				.getAttribute("_rowcount"));
		if (rowCount > 0) {
			System.out.println("Value Set - " + vsCode + " is present");
			bVSCode = true;
			/*
			 * CancelButton.click(); CommonUtils.hold(4);
			 * waitConditionForElement(driver.findElement(By.
			 * xpath("//a[text()='Manage Value Sets']")));
			 * driver.findElement(By.xpath("//a[text()='Manage Value Sets']")).click();
			 */
		} else {
			System.out.println("ERROR - Value Set - " + vsCode + " is not present");
		}
		return bVSCode;
	}

	public boolean createImplProject(String projectName) {
		CommonUtils.hold(5);
		effUtils.waitConditionForElement(createProject);
		createProject.click();
		CommonUtils.hold(5);
		projectNameInput.clear();
		CommonUtils.hold(2);
		projectNameInput.sendKeys(projectName);
		CommonUtils.hold(3);
		projectStatus.click();
		CommonUtils.hold(5);
		saveOpenProject.click();
		CommonUtils.hold(5);
		effUtils.waitConditionForElement(addTasks);
		addTasks.click();
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(searchOfferings_DropDown, "Tasks");
		CommonUtils.hold(5);
		taskName_inputBox.sendKeys("Manage Extensible Flexfields");
		CommonUtils.hold(5);
		taskName_searchButton.click();
		CommonUtils.hold(5);
		effTaskName_searchTable.click();
		CommonUtils.hold(5);
		applyButton_taskNameSearch.click();
		CommonUtils.hold(10);
		doneButton_taskNameSearch.click();
		CommonUtils.hold(10);
		if (goToTaskLink.isDisplayed() || effTask.isDisplayed()) {
			System.out.println("EFF Implementation Project is created successfully");
			return true;
		} else {
			System.err.println("ERROR - EFF Implementation Project is not displayed");
			return false;
		}

	}

	public boolean modifyImplProject(String prjName) {
		CommonUtils.hold(10);
		effUtils.waitConditionForElement(driver.findElement(By.xpath("//a[contains(text(),'" + prjName + "')]")));
		if (driver.findElement(By.xpath("//a[contains(text(),'" + prjName + "')]")).isDisplayed()) {
			System.out.println("Found the project link - " + prjName);
			driver.findElement(By.xpath("//a[contains(text(),'" + prjName + "')]")).click();
			return true;
		} else {
			System.err.println(
					"ERROR - Looks like Manage Implementation Project is not loaded or Project not displayed in UI");
			return false;
		}
	}

	public boolean modifyContext(String contextCode) {
		navigateToCreateContext();
		CommonUtils.hold(4);
		effUtils.waitConditionForElement(contextCode_TextBox);
		contextCode_TextBox.clear();
		contextCode_TextBox.sendKeys(contextCode);
		CommonUtils.hold(5);
		effUtils.waitConditionForElement(searchButton);
		searchButton.click();
		CommonUtils.hold(5);
		if (driver.findElement(By.xpath("//span[contains(@id,'ectxOT2') and text()='" + contextCode + "']"))
				.isDisplayed()) {
			System.out.println("Context - " + contextCode + " found under Search Context");
			CommonUtils.hold(3);
			driver.findElement(By.xpath("//span[contains(@id,'ectxOT2') and text()='" + contextCode + "']")).click();
			effUtils.waitConditionForElement(contextEditIcon);
			contextEditIcon.click();
			return true;
		} else {
			System.err.println("ERROR - Context Code not displayed");
			return false;
		}
	}

	public String updateContextDescription() {
		String updDesc = null;
		String currentText = createContextDescription.getText();
		System.out.println("Current text displayed under Context Description - " + currentText);
		CommonUtils.hold(4);
		createContextDescription.sendKeys(" Modified v2 as part of context modification");
		CommonUtils.hold(4);
		contextSaveBtn.click();
		CommonUtils.hold(4);
		updDesc = createContextDescription.getText();
		System.out.println("Context Description after modifying - " + updDesc);
		CommonUtils.hold(4);
		SaveAndCloseButton.click();
		CommonUtils.hold(10);
		SaveAndCloseButton.click();
		CommonUtils.hold(5);
		return updDesc;
	}

	public void navigateToEFFTask() {
		CommonUtils.hold(3);
		goToTaskLink.click();
		CommonUtils.hold(15);
	}

	public void closeProjectTasks() {
		CommonUtils.hold(10);
		doneButton_effPage.click();
		CommonUtils.hold(8);
		effUtils.waitConditionForElement(goToTaskLink);
		if(driver.findElements(By.xpath("//div[contains(@id,'AppPanel:SPb')]/a")).size()>0) {
			driver.findElement(By.xpath("//div[contains(@id,'AppPanel:SPb')]/a")).click();
			CommonUtils.hold(8);
		}
		else {
			doneButton_effTask.click();
			CommonUtils.hold(8);
		}
		if(driver.findElements(By.xpath("//div[contains(@id,'AppPanel:SPb')]/a")).size()>0) {
			driver.findElement(By.xpath("//div[contains(@id,'AppPanel:SPb')]/a")).click();
			CommonUtils.hold(8);
		}
		else {
			doneButton_effTask.click();
			CommonUtils.hold(8);
		}
	}

	public String getFlexCodeStatus() {
		String flexStatus = "";
		System.out.println("Current flexcode's status - " + deploymentStatusIcon.getAttribute("title"));
		flexStatus = deploymentStatusIcon.getAttribute("title");
		return flexStatus;
	}

	public String getContextDescription() {
		String currentContextDesc = "";
		System.out.println("Current Context Description - " + createContextDescription.getText());
		currentContextDesc = createContextDescription.getText();
		return currentContextDesc;
	}

	public void deleteEFFProject(String projectName) {
		CommonUtils.hold(4);
		if (driver.findElement(By.xpath("//a[text()='" + projectName + "']")).isDisplayed()) {
			System.out.println("Project - " + projectName + " is displayed to delete");
			CommonUtils.hold(2);
			driver.findElement(By.xpath("//a[text()='" + projectName + "']/parent::td")).click();
			CommonUtils.hold(2);
			deleteProjectLink.click();
			CommonUtils.hold(2);
		}
	}

	public void modifySegmentDisplayType(String newDisplayType) {
		CommonUtils.hold(5);
		effUtils.scrollToPageBottom();
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(segmentdisplayType, newDisplayType);
		CommonUtils.hold(6);
		effUtils.scrollToPageTop();
		CommonUtils.hold(5);
		modifiedSegmentSaveClose.click();
	}

	public boolean searchContextCode(String contextCode) {
		navigateToCreateContext();
		CommonUtils.hold(4);
		contextCode_TextBox.sendKeys(contextCode);
		CommonUtils.hold(3);
		searchButton.click();
		CommonUtils.hold(6);
		if (driver.findElements(By.xpath("//span[contains(@id,'ectxOT2') and text()='" + contextCode + "']"))
				.size() > 0) {
			System.out.println("Context Code - " + contextCode + " search successful and displayed");
			CommonUtils.hold(3);
			editContext.click();
			return true;
		} else {
			System.err.println("ERROR - Search Context - " + contextCode + " not successful");
			return false;
		}
	}

	public void deleteSegment(String segmentName) {
		CommonUtils.hold(4);
		if (driver.findElements(By.xpath("//span[contains(@id,'4:it39') and text()='" + segmentName + "']"))
				.size() > 0) {
			driver.findElement(By.xpath("//span[contains(@id,'4:it39') and text()='" + segmentName + "']")).click();
			CommonUtils.hold(3);
			deleteSegmentIcon.click();
			CommonUtils.hold(4);
			effUtils.waitConditionForElement(warningMessage_DeleteSegment);
			effUtils.waitConditionForElement(yesButton_DeleteSegment);
			System.out.println("Delete Segment Warning Message displayed");
			yesButton_DeleteSegment.click();
			CommonUtils.hold(3);
			effUtils.waitConditionForElement(contextSaveBtn);
		} else {
			System.err.println("ERROR - Segment - " + segmentName + " not displayed");
		}
	}

	public boolean searchSegment(String segmentName) {
		CommonUtils.hold(4);
		if (driver.findElements(By.xpath("//span[contains(@id,'4:it39') and text()='" + segmentName + "']"))
				.size() > 0) {
			System.out.println("Segment - " + segmentName + " is displayed");
			return true;
		} else {
			System.out.println("Segment - " + segmentName + " is displayed");
			return false;

		}
	}
	
	private boolean searchUpdateableItemProfile() {
		boolean bProfilePresent=false;
		CommonUtils.waitForPageLoad(20, driver);
		effUtils.waitConditionForElement(profileOptionCode);
		effUtils.waitConditionForElement(profileSearchButton);
		CommonUtils.waitforElementtoClick(20, profileOptionCode, driver);
		CommonUtils.waitforElementtoVisible(20, profileSearchButton, driver);
		CommonUtils.waitforElementtoClick(20, profileSearchButton, driver);
		profileOptionCode.clear();
		CommonUtils.hold(4);
		System.out.println("Entering Profile Option Code as 'EGP_UPDATEABLE_ITEM'");
		profileOptionCode.sendKeys("EGP_UPDATEABLE_ITEM");
		CommonUtils.hold(5);
		profileSearchButton.click();
		CommonUtils.hold(15);
		if(driver.findElements(By.xpath("//h3[contains(text(),'EGP_UPDATEABLE_ITEM')]")).size()>0 && driver.findElements(By.xpath("//span[text()='Site']")).size()>0) {
			System.out.println("Updateable Header displayed");
			System.out.println("Updateable Item Profile level - Site option is displayed");
			System.out.println("Search successful");
			bProfilePresent=true;
		}
		else {
			System.err.println("ERROR - Either Updateable Item Header or Site level profile are missing from the results");
		}
		return bProfilePresent;
	}
	
	public void setItemProfileValue() {
		if(searchUpdateableItemProfile()) {
			if(profileValue.getAttribute("value").equals("YES")) {
				System.out.println("Current Profile value is "+profileValue.getAttribute("value"));
				System.out.println("Resetting Item Profile value at Site level to 'NO'");
				CommonUtils.hold(5);
				profileValue.clear();
				CommonUtils.hold(5);
				profileValue.sendKeys("NO");
				CommonUtils.hold(5);
				profileSave.click();
				CommonUtils.hold(5);
			}
			if(profileValue.getAttribute("value").equals("NO")) {
				System.out.println("Current Profile value is "+profileValue.getAttribute("value"));
				System.out.println("Item Profile value at Site level is as expected. No need to modify the value");
				CommonUtils.hold(5);
				profileSave.click();
			}
			else {
				System.out.println("Current Profile value is "+profileValue.getAttribute("value"));
				System.out.println("ERROR - Invalid Value for Profile at site level");
			}
		}
		
	}
	
	public boolean getItemProfileValue() {
		boolean bProfileValueNO=false;
		if(searchUpdateableItemProfile()) {
			if(profileValue.getAttribute("value").equals("NO")) {
				System.out.println("Current Profile value is "+profileValue.getAttribute("value")+"\nValue is as expected. No need to change");
				profileSaveAndClose.click();
				CommonUtils.hold(10);
				bProfileValueNO=true;
			}
			else {
				System.err.println("ERROR - Updateable Item Profile value - "+profileValue.getAttribute("value")+" does not match");
			}
		}
		return bProfileValueNO;
	}

	/*
	 * public void openAboutApplication() throws Exception { CommonUtils.hold(5);
	 * CommonUtils.explicitWait(userLink, "Click", "",driver); userLink.click();
	 * CommonUtils.hold(4); effUtils.waitConditionForElement(aboutLink);
	 * aboutLink.click(); CommonUtils.hold(4);
	 * effUtils.waitConditionForElement(currentBranchName); }
	 * 
	 * public String getcurrentEnvironmentBranch() { return
	 * currentBranchName.getText(); }
	 * 
	 * public String getcurrentADFLabel() { return currentADFLabel.getText(); }
	 * 
	 * public String getcurrentATGLabel() { return currentATGLabel.getText(); }
	 * 
	 * public String getCurrentDBPatchLabel() { return
	 * currentDBPatchLabel.getText(); }
	 * 
	 * public void closeAboutApplication() throws Exception { CommonUtils.hold(3);
	 * CommonUtils.explicitWait(closeAboutPopUp, "Click", "",driver);
	 * closeAboutPopUp.click(); CommonUtils.hold(8); }
	 */
}
