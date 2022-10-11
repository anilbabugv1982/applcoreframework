package pageDefinitions.UI.oracle.applcore.qa.Audit;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import org.testng.Assert;

//import UI.tests.Audit.$missing$;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ISOReferenceDataPage;
import TestBase.UI.GetDriverInstance;


public class CommonAuditSetup extends AuditReferencePage {
	
	public CommonAuditSetup(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		
	}

	public  boolean isChecked;
	
//***********************************************************************************************************************************************************
	public  void applcoreauditsetup(WebDriver driver) throws Exception {
		NavigationTaskFlows nvtskflows=new NavigationTaskFlows(driver);
		//GlobalPageTemplate glbtmp=new GlobalPageTemplate(driver);
		//nvtskflows.navigateToTask(glbtmp.setupandmaintenance,driver);
		CommonUtils.hold(2);
		nvtskflows.navigateToAOLTaskFlows("Manage Audit Policies", driver);
		Log.info("Clicked on Manage Audit Policies in Setup and Maintenance");
		CommonUtils.hold(10);
		
		selectAuditLevel(driver);
		Log.info("Validated whether Auditing is enabled");
		
		clickConfigureBusinessObjectAttributesButton(driver);
		Log.info("Configure Business Object Attributes button has been clicked.");
		//CommonUtils.hold(10);
		
		selectAuditProduct("Oracle Middleware Extensions for Applications",driver);
		Log.info("Selected Audit Product as - Oracle Middleware Extension for Application");
		//CommonUtils.hold(10);
		// objectsauditing();
		
		//AuditingVO(driver);
	}
//***********************************************************************************************************************************************************
	public  void selectAuditLevel(WebDriver driver) throws Exception {
		CommonUtils.explicitWait(driver.findElement(By.xpath("//button[text()='Configure Business Object Attributes']/preceding::td[@class='AFContentCell']/select")), "Click", "", driver);
		WebElement auditlevel = driver
				.findElement(By.xpath("//button[text()='Configure Business Object Attributes']/preceding::td[@class='AFContentCell']/select"));
				
		Select auditLevelDropdown = new Select(auditlevel);
		auditLevelDropdown.selectByVisibleText("Auditing");
				
		CommonUtils.explicitWait(Save, "Click", "", driver);
		AuditReferencePage.Save.click();
				
		boolean ele = driver.findElement(By.xpath("//select[@title='Auditing']")).isDisplayed();
		Assert.assertEquals(true, ele, "Audit Level is not selected as Auditing.");

	}
//***********************************************************************************************************************************************************
	public  void clickConfigureBusinessObjectAttributesButton(WebDriver driver) {
		WebElement auditconfig = driver
				.findElement(By.xpath("//button[text()='Configure Business Object Attributes']"));
		auditconfig.click();
		CommonUtils.waitForInvisibilityOfElement(auditconfig, driver, 15);
	}
//***********************************************************************************************************************************************************	
	public void selectAuditProduct(String str,WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//label[normalize-space(text())='Product']/following::td[@class='AFContentCell']")), "Visible", "", driver);
		Select productDropdown = new Select(driver
				.findElement(By.xpath("//label[normalize-space(text())='Product']/following::td[@class='AFContentCell']/select")));
		productDropdown.selectByVisibleText(str);
		Log.info("Audit Product selected as: "+str);
		String eleXpath1 = "//table[@class='af_selectOneChoice' and contains(@id,'AUpan:soc1')]";
		String eleXpath2 = "//table[@class='p_AFHoverTarget af_selectOneChoice' and contains(@id,'AUpan:soc1')]";
		waitTillElementVisible1(eleXpath1, eleXpath2, 140, 5, driver);
		CommonUtils.hold(3);

	}
//***********************************************************************************************************************************************************	

	public  void objectsauditing(WebDriver driver) {
		int row_count =  driver
				.findElements(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:AUpan:AUTR:_ATTp:AUtr::ch::t")).size();
		System.out.println(row_count);

	}
//***********************************************************************************************************************************************************
	public  void AuditingVO(WebDriver driver) throws Exception {
		List<String> VO = new ArrayList<String>();
		VO.add("FndDocumentCategoriesAdminUIAM");
		VO.add("ProfileServiceAM");
		VO.add("Key Flexfield");
		
		
		for (int i = 0; i < VO.size(); i++) {
			
			System.out.println(VO.get(i));
			CommonUtils.hold(10);
			
			if (VO.get(i).contains("FndDocumentCategoriesAdminUIAM")) {
				
				List<WebElement> docElement =driver.findElements(By.xpath("//span[contains(@id,'AUpan:AUTR') and contains(text(),'Document')]"));
				int iterationCount=1;
				String attribute;
				docElement.remove(0);
				for(WebElement ee:docElement) {
						iterationCount++;
							try {
								System.out.println("iterationCount: "+iterationCount);
								ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Document')])["+iterationCount+"]"));
								attribute = ee.getText().trim();
								System.out.println("element: "+attribute);
							} catch (StaleElementReferenceException e) {
								driver.navigate().refresh();
								ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Document')])["+iterationCount+"]"));
								attribute = ee.getText().trim();
								System.out.println("in catch block- element: "+attribute);
							}
							
							//selectAttributeViewObject(driver, attribute);
					
							WebElement chkbox = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Document')])["+iterationCount+"]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
					        WebElement chkboxLabel = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Document')])["+iterationCount+"]/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
							
							System.out.println("chkbox: "+chkbox.isSelected());
							if (!chkbox.isSelected()) {
								chkboxLabel.click();
								CommonUtils.hold(15);
							}
							CommonUtils.hold(5);
					
				}
				AuditReferencePage.Save.click();
				CommonUtils.hold(5);
				Log.info("Save - Configure Business Object Attributes");
			}
			
			if (VO.get(i).contains("ProfileServiceAM")) {
				
				//clicking on View button dropdown then clicking on Scroll to Last
				CommonUtils.explicitWait(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")), ExplicitWaitCalls.Visible.toString(),"",driver);
				driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")).click();
				driver.findElement(By.xpath("//td[contains(text(),'Scroll to Last')]")).click();
				CommonUtils.hold(10);
				
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Log.info("----after Verticale scroll till bottom----");
				
				List<WebElement> docElement =driver.findElements(By.xpath("//span[contains(@id,'AUpan:AUTR') and contains(@id,'AUot1') and contains(text(),'Profile')]"));
				int iterationCount=0;
				String attribute;
				//docElement.remove(0);
				for(WebElement ee:docElement) {
						iterationCount++;
							try {
								System.out.println("iterationCount: "+iterationCount);
								ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(@id,'AUot1') and contains(text(),'Profile')])["+iterationCount+"]"));
								attribute = ee.getText().trim();
								System.out.println("element: "+attribute);
							} catch (StaleElementReferenceException e) {
								driver.navigate().refresh();
								ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(@id,'AUot1') and contains(text(),'Profile')])["+iterationCount+"]"));
								attribute = ee.getText().trim();
								System.out.println("in catch block- element: "+attribute);
							}
							
							//selectAttributeViewObject(driver, attribute);
					
							WebElement chkbox = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(@id,'AUot1') and contains(text(),'Profile')])["+iterationCount+"]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
					        WebElement chkboxLabel = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(@id,'AUot1') and contains(text(),'Profile')])["+iterationCount+"]/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
							
							
							System.out.println("chkbox: "+chkbox.isSelected());
							if (!chkbox.isSelected()) {
								chkboxLabel.click();
								CommonUtils.hold(15);
							}
							CommonUtils.hold(5);
							
				}
				AuditReferencePage.Save.click();
				CommonUtils.hold(5);
				Log.info("Save - Configure Business Object Attributes");
			}
			
			/*
			else if(VO.get(i).equals("ProfileServiceAM")) {
				//clicking on View button dropdown then clicking on Scroll to Last
				CommonUtils.explicitWait(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")), ExplicitWaitCalls.Visible.toString(),"",driver);
				driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")).click();
				driver.findElement(By.xpath("//td[contains(text(),'Scroll to Last')]")).click();
				CommonUtils.hold(10);
		
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				System.out.println("----after Verticale scroll till bottom----");
				
		        selectAttributeViewObject(driver, VO.get(i));
			}
			*/
			
			else if(VO.get(i).contains("Key Flexfield")) {
				//clicking on View button dropdown then clicking on Scroll to Last
				CommonUtils.explicitWait(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")), ExplicitWaitCalls.Visible.toString(),"",driver);
				driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")).click();
				driver.findElement(By.xpath("//td[contains(text(),'Scroll to Last')]")).click();
				CommonUtils.waitForPageLoad(driver);
		
				selectAttributeViewObject(driver, "Key Flexfield");
				selectAttributeViewObject(driver, "Key Flexfield Structure");
		        selectAttributeViewObject(driver, "Key Flexfield Segment");
		        selectAttributeViewObject(driver, "Key Flexfield Labeled Segment");
		        
		        AuditReferencePage.Save.click();
				CommonUtils.hold(5);
				Log.info("Save - Configure Business Object Attributes");
			}
			
			else {
				System.out.println("in else of AuditingVO");
				selectAttributeViewObject(driver, VO.get(i));
	        
			}
		}

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
		Log.info("----after Verticale scroll till top----");
		
		CommonUtils.explicitWait(SaveAndCloseBtn, "Click", "", driver);
		AuditReferencePage.SaveAndCloseBtn.click();
		CommonUtils.hold(5);
		Log.info("Save and Closed - Configure Business Object Attributes");
		
		CommonUtils.explicitWait(SaveAndCloseBtn, "Click", "", driver);
		AuditReferencePage.SaveAndCloseBtn.click();
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(SaveAndCloseBtn, driver, 8);
		Log.info("Save and Closed - Manage Audit Policies");
	
	}	
	
//***********************************************************************************************************************************************************
	public  void selectAttributeViewObject(WebDriver driver, String attributeName) {
		Log.info("in selectAttributeViewObject method");
		WebElement chkbox = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
        WebElement chkboxLabel = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
		
		System.out.println("chkbox1: "+chkbox.isSelected());
		if (!chkbox.isSelected()) {
			chkboxLabel.click();
			CommonUtils.hold(15);
			
		}
		
		// Below if condition is written as on one click, Key Flexfield is not getting checked by Automation
		if (attributeName.equalsIgnoreCase("Key Flexfield")) {
			chkbox = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
	        chkboxLabel = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
	        System.out.println("chkbox1: "+chkbox.isSelected());
	        if (!chkbox.isSelected()) {
				chkboxLabel.click();
				CommonUtils.hold(15);
				
			}
		}
		CommonUtils.hold(5);

	}

//***********************************************************************************************************************************************************
	public  void opssauditing(WebDriver driver) throws Exception {
		
		CommonUtils.explicitWait(driver.findElement(By.xpath("//select[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:AP1:soc9::content']")), "Click", "", driver);
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:AP1:soc9::content']")));
		//dropdown.selectByIndex(0);
		dropdown.selectByVisibleText("Low - Critical Events Only");
		CommonUtils.hold(4);
				
		boolean ele = driver.findElement(By.xpath("//select[@title='Low - Critical Events Only']")).isDisplayed();
		Assert.assertEquals(true, ele, "Low - Critical Events Only audit level is not selected for OPSS.");
		
		AuditReferencePage.SaveAndCloseBtn.click();
		CommonUtils.waitForInvisibilityOfElement(SaveAndCloseBtn, driver, 8);
		//CommonUtils.hold(5);
		Log.info("Save and Closed - Manage Audit Policies");

	}
//***********************************************************************************************************************************************************
	public  void personDffFlexAuditing(WebDriver driver) throws Exception {
		
		NavigationTaskFlows nvtskflows=new NavigationTaskFlows(driver);
		CommonUtils.hold(5);
		nvtskflows.navigateToAOLTaskFlows("Manage Audit Policies", driver);
		Log.info("Clicked on Manage Audit Policies in Setup and Maintenance");
		CommonUtils.hold(10);
	
		selectAuditLevel(driver);
		Log.info("Validated whether Auditing is enabled");
		
		clickConfigureBusinessObjectAttributesButton(driver);
		Log.info("Configure Business Object Attributes button has been clicked.");
		//CommonUtils.hold(10);
		
		selectAuditProduct("Global Human Resources",driver);
		Log.info("Selected Audit Product as - Global Human Resources");
		//CommonUtils.hold(30);
		
		//CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 80);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[normalize-space(text())='Audit Top Node']")), "Click", "", driver);
		WebElement auditTopNodeText = driver.findElement(By.xpath("//span[normalize-space(text())='Audit Top Node']"));
		System.out.println("----after auditTopNodeText----");
		auditTopNodeText.click();
		CommonUtils.hold(10);
		auditTopNodeText.click();
		CommonUtils.hold(5);
		
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.PAGE_DOWN).perform(); CommonUtils.hold(5);
		actions.sendKeys(Keys.PAGE_DOWN).perform(); CommonUtils.hold(10);  
		 
		
		List<WebElement> personElement =driver.findElements(By.xpath("//span[contains(@id,'AUpan:AUTR') and contains(text(),'Person')]"));
		System.out.println("person element size: "+personElement.size());
		
		if(personElement.size()<20) {
			actions.sendKeys(Keys.PAGE_DOWN).perform(); CommonUtils.hold(10);
			personElement =driver.findElements(By.xpath("//span[contains(@id,'AUpan:AUTR') and contains(text(),'Person')]"));
			System.out.println("in if condition--person element size: "+personElement.size());
		}
		
		if(personElement.size()==0) {
			Assert.assertFalse(true, "Person attribute views not found.");
		}
		
		int iterationCount=0;
		String attribute;
		//personElement.remove(0);
		//WebElement ee;
		for(WebElement ee:personElement) {
		//for(int i=0; i<personElement.size(); i++) {
				iterationCount++;
				if (iterationCount > 25) {
					break;
				}
				
					try {
						System.out.println("iterationCount: "+iterationCount);
						ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Person')])["+iterationCount+"]"));
						CommonUtils.hold(5);
						attribute = ee.getText().trim();
						System.out.println("element: "+attribute);
					} catch (StaleElementReferenceException e) {
						driver.navigate().refresh();
						ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Person')])["+iterationCount+"]"));
						attribute = ee.getText().trim();
						System.out.println("in catch block- element: "+attribute);
					}
					
					//selectAttributeViewObject(driver, attribute);
			
					WebElement chkbox = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Person')])["+iterationCount+"]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
			        WebElement chkboxLabel = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Person')])["+iterationCount+"]/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
					
			        System.out.println("chkbox1: "+chkbox.isSelected());
					try {
						if (!chkbox.isSelected()) {
							System.out.println("--clicking checkbox---");
							chkboxLabel.click();
							CommonUtils.hold(20);
						}
						CommonUtils.hold(5);
					} catch (ElementNotInteractableException e1) {
						actions.sendKeys(Keys.PAGE_DOWN).perform(); 
						CommonUtils.hold(10);
						if (!chkbox.isSelected()) {
							System.out.println("--clicking checkbox---");
							chkboxLabel.click();
							CommonUtils.hold(20);
						}
						CommonUtils.hold(5);
					}
					
		}
		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
		System.out.println("----after Verticale scroll till top----");
		
		AuditReferencePage.SaveAndCloseBtn.click();
		CommonUtils.hold(5);
		Log.info("Save and Closed - Configure Business Object Attributes");
		
		CommonUtils.explicitWait(SaveAndCloseBtn, "Click", "", driver);
		AuditReferencePage.SaveAndCloseBtn.click();
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(SaveAndCloseBtn, driver, 8);
		Log.info("Save and Closed - Manage Audit Policies");
			
	}
//***********************************************************************************************************************************************************
public  void positionDffFlexAuditing(WebDriver driver) throws Exception {
		
		NavigationTaskFlows nvtskflows=new NavigationTaskFlows(driver);
		CommonUtils.hold(5);
		nvtskflows.navigateToAOLTaskFlows("Manage Audit Policies", driver);
		Log.info("Clicked on Manage Audit Policies in Setup and Maintenance");
		CommonUtils.hold(10);
	
		selectAuditLevel(driver);
		Log.info("Validated whether Auditing is enabled");
		
		clickConfigureBusinessObjectAttributesButton(driver);
		Log.info("Configure Business Object Attributes button has been clicked.");
		//CommonUtils.hold(10);
		
		selectAuditProduct("Global Human Resources",driver);
		Log.info("Selected Audit Product as - Global Human Resources");
		//CommonUtils.hold(5);
		
		Actions actions = new Actions(driver);
		
		//CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 80);
		AuditViewObjectScroll(driver, "Last");
		actions.sendKeys(Keys.PAGE_DOWN).perform();
		WebElement auditTopText = driver.findElement(By.xpath("//span[normalize-space(text())='Audit']"));
		auditTopText.click();
		CommonUtils.hold(10);
		auditTopText.click();
		CommonUtils.hold(5);
		
		String position1Xpath = "//span[text()='Position']/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']";
		findPositionAttribute(position1Xpath, 32, 8, driver);
		

		/*
		actions.sendKeys(Keys.PAGE_UP).perform(); 
		CommonUtils.hold(10);
		System.out.println("first page up");
		//actions.sendKeys(Keys.PAGE_UP).perform(); CommonUtils.hold(10);
		//System.out.println("second page up");
		*/
		try {
			selectAttributeViewObject(driver, "Position");
		}catch (Exception e) {
			actions.sendKeys(Keys.PAGE_UP).perform(); CommonUtils.hold(5);
			System.out.println("page up");
			selectAttributeViewObject(driver, "Position");
		}
		System.out.println("first position clicked.");
		
		//to check second position vo
		WebElement chkbox;
		WebElement chkboxLabel;
		String position2ChkboxXpath = "(//span[contains(@id,'AUpan:AUTR') and text()='Position'])[2]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']";
		String position2ChkboxLabelXpath = "(//span[contains(@id,'AUpan:AUTR') and text()='Position'])[2]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']";
		try {
			chkbox = driver.findElement(By.xpath(position2ChkboxXpath));
		    chkboxLabel = driver.findElement(By.xpath(position2ChkboxLabelXpath));
		}catch(Exception e) {
			System.out.println("position2--in catch");
			/*
			 * auditTopText =
			 * driver.findElement(By.xpath("//span[normalize-space(text())='Audit']"));
			 * auditTopText.click(); CommonUtils.hold(2); auditTopText.click();
			 */
			
			
			actions.sendKeys(Keys.PAGE_UP).perform(); CommonUtils.hold(10);
			System.out.println("first page up for second position vo");
			actions.sendKeys(Keys.PAGE_UP).perform(); CommonUtils.hold(10);
			System.out.println("second page up for second position vo");
			
			findPositionAttribute(position2ChkboxXpath, 40, 8, driver);
			try {
				chkbox = driver.findElement(By.xpath(position2ChkboxXpath));
			    chkboxLabel = driver.findElement(By.xpath(position2ChkboxXpath));
			
			}catch (Exception e1) {
				System.out.println("in catch--->catch");
				actions.sendKeys(Keys.PAGE_UP).perform(); CommonUtils.hold(5);
				System.out.println("page up for second position vo");
				chkbox = driver.findElement(By.xpath(position2ChkboxLabelXpath));
			    chkboxLabel = driver.findElement(By.xpath(position2ChkboxLabelXpath));
			
			}
		}
		System.out.println("chkbox: "+chkbox.isSelected());
		try {
			if (!chkbox.isSelected()) {
				//CommonUtils.explicitWait(chkboxLabel, "Click", "", driver);
				chkboxLabel.click();
			}
		}catch(ElementClickInterceptedException e) {
			CommonUtils.hold(8);
			//CommonUtils.explicitWait(chkboxLabel, "Click", "", driver);
			chkboxLabel.click();
		}
		CommonUtils.hold(10);
		
		//CommonUtils.hold(5);
		//clicking on second Position element
		WebElement position2 = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and text()='Position'])[2]"));
		try {
			CommonUtils.explicitWait(position2, "Click", "", driver);
			position2.click();
		}catch (ElementClickInterceptedException e) {
			CommonUtils.hold(5);
			CommonUtils.explicitWait(position2, "Click", "", driver);
			position2.click();
		}catch (Exception e1) {
			actions.sendKeys(Keys.PAGE_UP).perform(); CommonUtils.hold(7);
			CommonUtils.explicitWait(position2, "Click", "", driver);
			position2.click();
		}
		
		System.out.println("clicking on add audit attribute button.");
		selectAuditedAttributes(driver);
					
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
		System.out.println("----after Verticale scroll till top----");
		
		SaveAndCloseBtn.click();
		CommonUtils.hold(5);
		Log.info("Save and Closed - Configure Business Object Attributes");
		
		CommonUtils.explicitWait(SaveAndCloseBtn, "Click", "", driver);
		AuditReferencePage.SaveAndCloseBtn.click();
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(SaveAndCloseBtn, driver, 8);
		Log.info("Save and Closed - Manage Audit Policies");
			
	}
//**********************************************************************************************************

	public  void itemEffFlexAuditing(WebDriver driver) throws Exception {

		NavigationTaskFlows nvtskflows=new NavigationTaskFlows(driver);
		CommonUtils.hold(5);
		nvtskflows.navigateToAOLTaskFlows("Manage Audit Policies", driver);
		Log.info("Clicked on Manage Audit Policies in Setup and Maintenance");
		CommonUtils.hold(10);
	
		selectAuditLevel(driver);
		Log.info("Validated whether Auditing is enabled");
		
		clickConfigureBusinessObjectAttributesButton(driver);
		Log.info("Configure Business Object Attributes button has been clicked.");
		CommonUtils.hold(10);
		
		selectAuditProduct("Product Hub",driver);
		Log.info("Selected Audit Product as - Product Hub");
		CommonUtils.hold(30);
		
		List<WebElement> itemElement =driver.findElements(By.xpath("//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')]"));
		System.out.println("Item elements size: "+itemElement.size());
		
		if(itemElement.size()==0) {
			Assert.assertFalse(true, "Item attribute views not found.");
		}
		
		int iterationCount=0;
		String attribute;
		//itemElement.remove(0);
		for(WebElement ee:itemElement) {
			iterationCount++;
			if (iterationCount > 16) {
				break;
			}
			
			try {
				System.out.println("iterationCount: "+iterationCount);
				ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]"));
				CommonUtils.hold(5);
				attribute = ee.getText().trim();
				System.out.println("element: "+attribute);
			} catch (StaleElementReferenceException e) {
				driver.navigate().refresh();
				ee = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]"));
				attribute = ee.getText().trim();
				System.out.println("in catch block- element: "+attribute);
			}
			
			//selectAttributeViewObject(driver, attribute);
	
			WebElement chkbox = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
	        WebElement chkboxLabel = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
			
			
			System.out.println("chkbox1: "+chkbox.isSelected());
			if (!chkbox.isSelected()) {
				System.out.println("--clicking checkbox---");
				chkboxLabel.click();
				CommonUtils.hold(20);
				
			}
			
			//reassignment because of StaleElementException
			chkbox = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
	        
			if (!chkbox.isSelected()) {
				try {
					if (auditWarningBox.isDisplayed()) {
						System.out.println("in warning box");
						auditWarningBoxOkButton.click();
						//CommonUtils.hold(5);
						selectAuditedAttributes(driver);
					}
				} catch (NoSuchElementException e) {
					Log.info("Warning Alert dialog box is not displayed.");
				}finally {
					CommonUtils.hold(5);
					//reassignment because of StaleElementException
					chkbox = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
			        chkboxLabel = driver.findElement(By.xpath("(//span[contains(@id,'AUpan:AUTR') and contains(text(),'Item')])["+iterationCount+"]/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
			        if (!chkbox.isSelected()) {
						System.out.println("--clicking checkbox again---");
						chkboxLabel.click();
						CommonUtils.hold(20);
					}
				}
			}
			CommonUtils.hold(5);
					
		}
		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
		System.out.println("----after Verticale scroll till top----");
		
		CommonUtils.explicitWait(SaveAndCloseBtn, "Click", "", driver);
		AuditReferencePage.SaveAndCloseBtn.click();
		CommonUtils.hold(5);
		Log.info("Save and Closed - Configure Business Object Attributes");
		
		CommonUtils.explicitWait(SaveAndCloseBtn, "Click", "", driver);
		AuditReferencePage.SaveAndCloseBtn.click();
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(SaveAndCloseBtn, driver, 8);
		Log.info("Save and Closed - Manage Audit Policies");
			
	}

//***********************************************************************************************************************************************************
	public void auditreport(String prdname,String botype1,boolean includeChildObject, WebDriver driver) throws Exception {
		
		fillAuditReportPageDetails(prdname, botype1, includeChildObject, driver);
		
		if (botype1.equalsIgnoreCase("Profile Option")) {
			
			CommonUtils.explicitWait(profileOptionCodeValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(profileOptionCodeValue.getText(), POName, "Profile Option Code value mismatch in audit Report");
			Assert.assertEquals(profileOptionNameValue.getText(), POName, "Profile Option Name value mismatch in audit Report");
			Assert.assertEquals(applicationValue.getText(), "Oracle Middleware Extensions for Applications", "Application value mismatch in audit Report");
			
			Log.info("Profile Option Audit Report validated.");
			
		}

		else if (botype1.equalsIgnoreCase("Document Entities")) {
			
			CommonUtils.explicitWait(entitiesNameValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(entitiesNameValue.getText(), attachEntDetails[0], "Entity Name value mismatch in audit Report");
			Assert.assertEquals(entitiesDatabaseResources.getText(),attachEntDetails[1],  "Entity Database Resources value mismatch in audit Report");
			Assert.assertEquals(entitiesTableName.getText(), attachEntDetails[2], "Entity Table Name value mismatch in audit Report");
			
			Log.info("Document Entities Audit Report validated.");
			
		}
		
		else if (botype1.equalsIgnoreCase("Document Categories")) {
			
			CommonUtils.explicitWait(categoryNameValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(categoryNameValue.getText(), attachCatDetails[0], "Category Name value mismatch in audit Report");
			Assert.assertEquals(categoryUserNameValue.getText(), attachCatDetails[1], "Category User Name value mismatch in audit Report");
			Assert.assertEquals(catDescription.getText(), attachCatDetails[2], "Category Description value mismatch in audit Report");
			/*
			 * if (includeChildObject) { Assert.assertEquals(attachCatDetails[3],
			 * catDocumentEntId.getText(),
			 * "Category Description value mismatch in audit Report"); }
			 */
			
			Log.info("Document Categories Audit Report validated.");
			
		}
		
		else if (botype1.equalsIgnoreCase("Key Flexfield")) {
			
			CommonUtils.explicitWait(kffStructureCodeValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(kffStructureCodeValue.getText(), KFFStructureDetails[0], "KFF Structure Code value mismatch in audit Report");
			Assert.assertEquals(kffStructureNameValue.getText(), KFFStructureDetails[1], "KFF Structure Name value mismatch in audit Report");
			Assert.assertEquals(kffDelimiterValue.getText(), KFFStructureDetails[2], "KFF Delimiter value mismatch in audit Report");
			
			Log.info("Key Flexfield Audit Report validated.");	
			
		}
		
		else if (prdname.equalsIgnoreCase("Oracle Platform Security Services")) {
			
			CommonUtils.explicitWait(applicationRoleValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(applicationRoleValue.getText(), applicationRole, "Application Role value mismatch in audit Report");
			Assert.assertTrue(enterpriseRolesValue.getText().contains(enterpriseRoles), "Enterprise Roles value mismatch in audit Report");
			
			Log.info("Oracle Platform Security Services Audit Report validated.");	
			
		}
		
		else if (prdname.equalsIgnoreCase("Product Hub")) {
			
			CommonUtils.explicitWait(ITMCharGlobalSeg1_AuditReportValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(ITMCharGlobalSeg1_AuditReportValue.getText(), ITMCharGlobalSeg1, "ITM Character Global Segment 1 value mismatch in audit Report");
			Assert.assertEquals(zBIAItemDFF_AuditReportValue.getText(), zBIAItemDFF, "zBIAItemDFF value mismatch in audit Report");
			Assert.assertEquals(RTItemDFFChar15_AuditReportValue.getText(), RTItemDFFChar15, "RT-ItemDFF-Char15 value mismatch in audit Report");
			
			Log.info("Product Hub-Item EFF Audit Report validated.");
			
		}
		
		else if (botype1.equalsIgnoreCase("Person")) {
			
			CommonUtils.explicitWait(person_glbValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(person_glbValue.getText(), Person_glb, "Person_glb value mismatch in audit Report");
			Assert.assertEquals(FBLPGValue.getText(), FBLPG, "FBPLG value mismatch in audit Report");
			Assert.assertEquals(PersonIDValue.getText(), empLastName,  "Person ID value mismatch in audit Report");
			
			Log.info("Global Human Resources - Person Audit Report validated.");
			
		}
		
		else if (botype1.equalsIgnoreCase("Position")) {
			
			CommonUtils.explicitWait(att1_UK_001_SimpleValue, ExplicitWaitCalls.Visible.toString(),"",driver);
			Assert.assertEquals(att1_UK_001_SimpleValue.getText(), att1_UK_001_Simple, "att1_UK_001_Simple value mismatch in audit Report");
			Assert.assertEquals(POS_GLBValue.getText(), POS_GLB, "POS_GLB value mismatch in audit Report");
			Assert.assertEquals(posglobal1Value.getText(), posglobal1,  "posglobal1 value mismatch in audit Report");
			
			Log.info("Global Human Resources - Position Audit Report validated.");
			
		}
		
		else {
			
			Log.info("Invalid Business Object Type/Product Name.");
			Assert.assertTrue(false, "Invalid Business Object Type/Product Name.");
			
		}
		
	}
//***********************************************************************************************************************************************************
	public void validateExporttoExcel(String prdname,String botype1,boolean includeChildObject, WebDriver driver) throws Exception {
	
		fillAuditReportPageDetails(prdname, botype1, includeChildObject, driver);
		CommonUtils.hold(5);
		clickExportToExcelIcon(driver);
		Log.info(prdname+ ":" + botype1 + " - Export to Excel verified.");		
		
	}
//***********************************************************************************************************************************************************

	public void validateExporttoCSV(String prdname,String botype1,boolean includeChildObject, WebDriver driver) throws Exception {
		
		fillAuditReportPageDetails(prdname, botype1, includeChildObject, driver);
		CommonUtils.hold(5);
		clickExportToCSVIcon(driver);
		Log.info(prdname+ ":" + botype1 + " - Export to CSV verified.");		
		
	}
//***********************************************************************************************************************************************************

	public void fillAuditReportPageDetails(String prdname,String botype1,boolean includeChildObject, WebDriver driver) throws Exception {
		
		CommonUtils.waitForPageLoad(driver);
		Log.info("Business Object Type: "+ botype1);
		CommonUtils.explicitWait(auditReportDateCalendar, ExplicitWaitCalls.Click.toString(),"",driver);
		//auditReportDate.sendKeys(CommonUtils.currentDate("MM/dd/yy"));
		CommonUtils.clickWithJavaScript(auditReportDateCalendar, driver);
		//auditReportDateCalendar.click();
		//auditReportCurrentDate.click();
		Actions act=new Actions(driver);
		act.moveToElement(auditReportCurrentDate).click().build().perform();
		
		CommonUtils.hold(4);
		
		Select prd=new Select(auditProduct);
		prd.selectByVisibleText(prdname);
		//CommonUtils.waitForInvisibilityOfElement(busyField, driver, 60);
		//CommonUtils.hold(15);
		
		String eleXpath = "//span[@class='af_selectOneChoice' and contains(@id,'qryId1:value20')]";
		waitTillXpathVisible(eleXpath, 120, 5, driver);
		
		if (!prdname.equalsIgnoreCase("Oracle Platform Security Services")) {
			try {
				Select botype=new Select(auditBusinessObjectType);
			    botype.selectByVisibleText(botype1);  
			    CommonUtils.hold(2);
			} 
			catch (Exception e) {
				CommonUtils.hold(5);
				Select botype=new Select(auditBusinessObjectType);
			    botype.selectByVisibleText(botype1);  
			    CommonUtils.hold(2);
			}
		} 
		
		if (includeChildObject) { 
			if(!includeChildObjectsCheckbox.isSelected()) {
				includeChildObjectsCheckboxLabel.click();
				Log.info("Include Child Objects checkbox clicked.");
				//auditReportSearch.click(); 
				//Log.info("Audit Report Search button clicked.");
				//CommonUtils.waitForPageLoad(driver); 
				//CommonUtils.hold(10);
			}
		}
		else {
			if(includeChildObjectsCheckbox.isSelected()) {
				includeChildObjectsCheckboxLabel.click();
				Log.info("Include Child Objects checkbox unchecked.");
				//auditReportSearch.click(); 
				//Log.info("Audit Report Search button clicked.");
				//CommonUtils.waitForPageLoad(driver); 
				//CommonUtils.hold(10);
			}
		}
		
		CommonUtils.explicitWait(auditReportSearch, "Click", "", driver);
		auditReportSearch.click();
		Log.info("Audit Report Search button clicked.");
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(15);
		
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(showAttributeDetailsCheckbox, ExplicitWaitCalls.Click.toString(),"",driver);
		try {
			if(showAttributeDetailsCheckbox.isEnabled()) {
				showAttributeDetailsCheckbox.click();
			}
		} catch(Exception e) {
			CommonUtils.hold(10);
			CommonUtils.explicitWait(showAttributeDetailsCheckbox, ExplicitWaitCalls.Click.toString(),"",driver);
			if(!showAttributeDetailsCheckbox.isSelected()) {
				showAttributeDetailsCheckbox.click();
			}
		}
		Log.info("Show Attribute Details checkbox clicked.");
		//CommonUtils.hold(10);
		
		CommonUtils.explicitWait(showAttributeDetailsDropdown, ExplicitWaitCalls.Click.toString(),"",driver);
		try {
			showAttributeDetailsDropdown.click();
		} catch (ElementNotInteractableException e) {
			CommonUtils.hold(10);
			CommonUtils.explicitWait(showAttributeDetailsCheckbox, ExplicitWaitCalls.Click.toString(),"",driver);
			if(!showAttributeDetailsCheckbox.isSelected()) {
				showAttributeDetailsCheckbox.click();
			}
			//CommonUtils.hold(10);
			CommonUtils.explicitWait(showAttributeDetailsDropdown, ExplicitWaitCalls.Click.toString(),"",driver);
			showAttributeDetailsDropdown.click();
		}
		
		Log.info("Show Attribute Details dropdown clicked.");
		CommonUtils.explicitWait(allAttributes, ExplicitWaitCalls.Click.toString(),"",driver);
		allAttributes.click();
		Log.info("All Attributes option selected.");
		CommonUtils.explicitWait(auditReportAttributeCoulmn, ExplicitWaitCalls.Visible.toString(),"",driver);
		//CommonUtils.hold(15);
		//pressTab();
	
	}
//***********************************************************************************************************************************************************

	public void selectAuditedAttributes(WebDriver driver) throws Exception {
		
		CommonUtils.hold(5);
		CommonUtils.explicitWait(createAttribute, "Click", "", driver);
		createAttribute.click();
		//CommonUtils.hold(2);
		CommonUtils.explicitWait(attributeNameCheckboxLabel, "Click", "", driver);
		String attributesXpath = "//div[contains(@id,'AUpan:AUT:pc1:t1::db')]";
		waitTillXpathVisible(attributesXpath, 10, 2, driver);
		//CommonUtils.hold(5); //wait for load the attributes
		try {
			if (!flexFieldAdditionalAttributeCheckbox.isSelected()) {
				flexFieldAdditionalAttributeCheckboxLabel.click();
				CommonUtils.hold(3);
				waitTillXpathVisible(attributesXpath, 10, 2, driver);
				//CommonUtils.hold(6); //wait for load the flexfield attributes
			}
		}
		catch (NoSuchElementException e) {
			System.out.println("Flexfield checkbox is not present.");
		}
		finally {
			CommonUtils.hold(3);
			if (!attributeNameCheckbox.isSelected()) {
				attributeNameCheckboxLabel.click();
				CommonUtils.hold(6);
				waitTillXpathVisible(attributesXpath, 10, 2, driver);
			}
			
			CommonUtils.explicitWait(addAttributeOkButton, "Click", "", driver);
			addAttributeOkButton.click();
			CommonUtils.waitForInvisibilityOfElement(addAttributeOkButton, driver, 10);
			//CommonUtils.hold(10);
		}
		
	}
	
//***********************************************************************************************************************************************************	
	
	public void clickExportToExcelIcon(WebDriver driver) throws Exception {
		
		//GetDriverInstance getDriverInstance=new GetDriverInstance();
		CommonUtils.explicitWait(exportToExcelIcon, "Click", "", driver);
		exportToExcelIcon.click();
		Log.info("Export to Excel icon clicked.");
		CommonUtils.hold(30);
		try {
			Assert.assertTrue(isFileDownloaded(GetDriverInstance.fsmExpImpFile, "auditreport.xls"));
		}catch (AssertionError e) {
			CommonUtils.hold(50);
			Assert.assertTrue(isFileDownloaded(GetDriverInstance.fsmExpImpFile, "auditreport.xls"), "Failed to download Expected document file.");
		}
		
	}
	
//***********************************************************************************************************************************************************	
	public void clickExportToCSVIcon(WebDriver driver) throws Exception {
		
		//GetDriverInstance getDriverInstance=new GetDriverInstance();
		CommonUtils.explicitWait(exportToCSVIcon, "Click", "", driver);
		exportToCSVIcon.click();
		Log.info("Export to CSV icon clicked.");
		CommonUtils.hold(30);
		try {
			Assert.assertTrue(isFileDownloaded(GetDriverInstance.fsmExpImpFile, "auditreport.csv"));
		}catch (AssertionError e) {
			CommonUtils.hold(50);
			Assert.assertTrue(isFileDownloaded(GetDriverInstance.fsmExpImpFile, "auditreport.csv"), "Failed to download Expected document file.");
		}
	}
	
//***********************************************************************************************************************************************************	
	
	public boolean isAlertPresents(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	} 
//***********************************************************************************************************************************************************	
	
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		  File dir = new File(downloadPath);
		  File[] dirContents = dir.listFiles();

		  for (int i = 0; i < dirContents.length; i++) {
		      if (dirContents[i].getName().equals(fileName)) {
		          // File has been found, it can now be deleted:
		          dirContents[i].delete();
		          return true;
		      }
		          }
		      return false;
		  }
		
//***********************************************************************************************************************************************************	
	
	public void auditReprotClickOnHomeIcon(WebDriver driver) {
		
		GlobalPageTemplate glbtmp=new GlobalPageTemplate(driver);
		Actions action = new Actions(driver);
		action.moveToElement(glbtmp.homeIcon).click().perform();
		//glbtmp.homeIcon.click();
		Log.info("Clicked on Home Icon");
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(10);
		
		try {
			if (AuditReferencePage.auditReportWarningBox.isDisplayed()) {
				System.out.println("in warning box");
				AuditReferencePage.auditReportWarningBoxYesButton.click();
				CommonUtils.hold(5);
			}
		} catch (NoSuchElementException e) {
			Log.info("Warning Alert dialog box is not displayed.");
		}
		
		//System.out.println("---out warning box----");

		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
	}
	
//***********************************************************************************************************************************************************	
	
	public void enterItemAdditionalAttributesValues(WebDriver driver) throws Exception {
		
		ITMCharGlobalSeg1 = "1";
		zBIAItemDFF = "2";
		RTItemDFFChar15 = "3";
		
		CommonUtils.explicitWait(ITMCharGlbSeg, ExplicitWaitCalls.Visible.toString(), "", driver);
		ITMCharGlbSeg.sendKeys(ITMCharGlobalSeg1);
		zBIAItem.sendKeys(zBIAItemDFF);
		RTItem.sendKeys(RTItemDFFChar15);
		
		
	} 
//***********************************************************************************************************************************************************	

	public  void uncheckAttributeViewObject(WebDriver driver, String attributeName) {
		Log.info("in selectAttributeViewObject method");
		WebElement chkbox = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
        WebElement chkboxLabel = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
		
		System.out.println("chkbox1: "+chkbox.isSelected());
		if (chkbox.isSelected()) {
			chkboxLabel.click();
			CommonUtils.hold(15);
			
		}
		/*
		// Below if condition is written as on one click, Key Flexfield is not getting checked by Automation
		if (attributeName.equalsIgnoreCase("Key Flexfield")) {
			chkbox = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
	        chkboxLabel = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
	        System.out.println("chkbox1: "+chkbox.isSelected());
	        if (chkbox.isSelected()) {
				chkboxLabel.click();
				CommonUtils.hold(15);
				
			}
		}
		*/
		//CommonUtils.hold(5);

	}

//***********************************************************************************************************************************************************	
	
	public boolean isSelectedAttributeViewObject(WebDriver driver, String attributeName) {
		Log.info("in selectAttributeViewObject method");
		WebElement chkbox = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::input[@class = 'af_selectBooleanCheckbox_native-input']"));
        WebElement chkboxLabel = driver.findElement(By.xpath("//span[text()='" + attributeName + "']/ancestor::tr[2]/descendant::label[@class = 'af_selectBooleanCheckbox_item-text']"));
		
		System.out.println("chkbox1: "+chkbox.isSelected());
		boolean flag = chkbox.isSelected();
		
		return flag;

	}

//***********************************************************************************************************************************************************	
	
	public void AuditViewObjectScroll(WebDriver driver, String scrollType) throws Exception {
		CommonUtils.hold(5);
		//clicking on View button dropdown then clicking on Scroll to Last
		CommonUtils.explicitWait(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")), ExplicitWaitCalls.Visible.toString(),"",driver);
		driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Synchronize'])[1]/following::div[6]")).click();
		if(scrollType.equalsIgnoreCase("First")) {
			driver.findElement(By.xpath("//td[contains(text(),'Scroll to First')]")).click();
		}
		else {
			driver.findElement(By.xpath("//td[contains(text(),'Scroll to Last')]")).click();
		}
		CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 10);
		CommonUtils.waitForPageLoad(driver);

	}

//***********************************************************************************************************************************************************	
	
	public void pressTab() throws AWTException {
		Robot robot = new Robot();
		// Simulate key Events
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	
//***********************************************************************************************************************************************************
	
	public void waitTillXpathVisible(String xpath, int totaltime, int interval, WebDriver driver) throws Exception {
		int flag=0;
		int count=0;
		int limit = (int)(totaltime/interval);
		do {
			try {
				System.out.println("product wait count "+count);
				CommonUtils.explicitWait(driver.findElement(By.xpath(xpath)), "Visible", "", driver);
				flag=1;
			}catch(NoSuchElementException e) {
				CommonUtils.hold(interval);
			}
		}while((flag==0) && ((++count)<=limit));
		
		if(flag==1) {
			System.out.println("Element is visible.");
		}
		else {
			System.out.println("Element is not visible.");
			Assert.assertFalse(true, "Element is not visible.");
		}
	}
	
//***********************************************************************************************************************************************************
	
	public void waitTillElementVisible(WebElement element, int totaltime, int interval, WebDriver driver) throws Exception {
		int flag=0;
		int count=0;
		int limit = (int)(totaltime/interval);
		do {
			try {
				System.out.println("product wait count "+count);
				CommonUtils.explicitWait(element, "Visible", "", driver);
				flag=1;
			}catch(NoSuchElementException e) {
				CommonUtils.hold(interval);
			}
		}while((flag==0) && ((++count)<=limit));
		
		if(flag==1) {
			System.out.println("Element is visible.");
		}
		else {
			System.out.println("Element is not visible.");
			Assert.assertFalse(true, "Element is not visible.");
		}
	}	
		
	
//***********************************************************************************************************************************************************	
	
	public void waitTillElementVisible1(String xpath, String xpath1, int totaltime, int interval, WebDriver driver) throws Exception {
		int flag=0;
		int count=0;
		int limit = (int)(totaltime/interval);
		do {
			try {
				System.out.println("product wait count "+count);
				CommonUtils.explicitWait(driver.findElement(By.xpath(xpath)), "Visible", "", driver);
				flag=1;
				System.out.println("Normal element found.");
			}catch(NoSuchElementException e) {
				try {
					CommonUtils.explicitWait(driver.findElement(By.xpath(xpath1)), "Visible", "", driver);
					flag=1;
					System.out.println("Hover Target found.");
				} catch(Exception e1) {
					CommonUtils.hold(interval);
				}
			}
		}while((flag==0) && ((++count)<=limit));
		
		if(flag==1) {
			System.out.println("Element is visible.");
		}
		else {
			System.out.println("Element is not visible.");
			Assert.assertFalse(true, "Element is not visible.");
		}
	}
	
//***********************************************************************************************************************************************************
	public void findPositionAttribute(String xpath, int totaltime, int interval, WebDriver driver) {
		WebElement ele=null;
        int flag=0;
        int count=0;
        int limit = (int)(totaltime/interval);
        Actions actions = new Actions(driver);
        do{
            try{
            	System.out.println("Count: "+count);
                //element to search for while scrolling in grid
                ele = driver.findElement(By.xpath(xpath));
                flag=1;
            } catch(Exception e){
            	actions.sendKeys(Keys.PAGE_UP).perform();
                CommonUtils.hold(interval);
            }
        }while((flag==0) && ((++count)<=limit));

        if(flag==1){
            System.out.println("Position Element is found!!");
        }else{
            System.out.println("Position Element is not found!!");
        }
	}
//***********************************************************************************************************************************************************	
	
	public void isButtonEnabled(WebElement element, int time) throws Exception {
		
		long startTime = System.currentTimeMillis();
	    long endTime = 0;
	    long totalTime = 0;
		 
		while (!element.isEnabled() && totalTime < time * 1000) {
			try {
				CommonUtils.hold(2);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO : waitForElementToEnable "+totalTime);
            }

		}
	}
}


