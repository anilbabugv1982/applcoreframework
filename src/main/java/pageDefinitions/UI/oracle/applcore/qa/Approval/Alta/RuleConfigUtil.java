package pageDefinitions.UI.oracle.applcore.qa.Approval.Alta;

import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;

import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import pageDefinitions.UI.oracle.applcore.qa.Approval.Alta.RuleConfigPage;

public class RuleConfigUtil extends RuleConfigPage{
	
	public RuleConfigUtil(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	public void DeleteStepsAndNavigateToRulePage(WebDriver approvalDriver) throws Exception {
		boolean isVisible=true;	
		CommonUtils.hold(12);
		CommonUtils.explicitWait(stepName2, "Visible", "", approvalDriver);
		CommonUtils.explicitWait(stepName2, "Click", "", approvalDriver);
	//	CommonUtils.hold(5);
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		executor.executeScript("arguments[0].click();", stepName2);
		//CommonUtils.hold(30);
		CommonUtils.explicitWait(action, "Click", "", approvalDriver);
		//CommonUtils.hold(5);
		executor.executeScript("arguments[0].click();", action);
		//CommonUtils.hold(2);
		String cls= deleteStep.getAttribute("class");
		System.out.println("class is "+cls);
		isVisible=(!cls.contains("disabled"))&&(!cls.isEmpty());		
			
		
	if(isVisible) {
		CommonUtils.explicitWait(deleteStep, "Click", "", approvalDriver);
		//CommonUtils.hold(2);
		executor.executeScript("arguments[0].click();", deleteStep);
		//CommonUtils.hold(8);
		CommonUtils.explicitWait(yes2, "Click", "", approvalDriver);
		//CommonUtils.hold(5);
		executor.executeScript("arguments[0].click();", yes2);
		CommonUtils.hold(5);
		
	}
		}



	public void createRule(WebDriver driver ,List<String> header, List<String> attributes, List<String> operator, List<String> values, List<String> operations, int rowid) throws Exception {
		waitForPageLoad(driver);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		Dictionary edu = new Hashtable();
		edu.put("lesserthanorequaltosign", 6); 
		edu.put("greaterthanorequaltosign", 4);
		edu.put("greaterthansign", 3);
		edu.put("equaltosign", 7);
		edu.put("notequaltosign", 8);
		edu.put("lesserthansign", 5);
		Dictionary arithmetic = new Hashtable();
		arithmetic.put("plusmark", 2); 
		arithmetic.put("minusmark", 3);
		arithmetic.put("multiplicationmark", 4);
		arithmetic.put("divisionmark", 5);
		try
		{
			//CommonUtils.explicitWait(addNewRule, "Click", "", driver);
			CommonUtils.hold(5);
			addNewRule.click();
			//CommonUtils.hold(4);
			
				}
		catch(Exception e) 
		{

		}

	finally
	{
		int numberOfrules=header.size();
		System.out.println(numberOfrules);
		for(int i=0;i< numberOfrules;i++)
		{	
			System.out.println(i);
			WebElement addRule = driver.findElement(By.xpath("//div[contains(@id, 'condition_r"+rowid+"')]//span[text()='Condition']"));
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'condition_r"+rowid+"')]//span[text()='Condition']")));
			CommonUtils.explicitWait(addRule, "Visible", "", driver);
			executor.executeScript("arguments[0].click();", addRule);
		    CommonUtils.explicitWait(attributeDropDown, "Visible", "", driver);
		    CommonUtils.hold(2);
		    attributeDropDown.click();
		    //CommonUtils.hold(2);
		   // driver.findElement(By.xpath("//span[text() ='"+header.get(i)+"']/ancestor::ul[@class='oj-listview-element oj-component-initnode']//li//div//span[(text() ='"+attributes.get(i)+"')]")).click();
		    driver.findElement(By.xpath("//span[text() ='"+header.get(i)+"']/../../following-sibling::ul//span[(text() ='"+attributes.get(i)+"')]")).click();
		    //CommonUtils.hold(10);
		    CommonUtils.explicitWait(opSelection, "Click", "", driver);
		    //CommonUtils.hold(4);
		    opSelection.click();
		    //CommonUtils.hold(4);
		    if(operator.get(i).contains("sign")) 
		    {
		    	WebElement test = driver.findElement(By.xpath("(//div[@class='oj-listbox-result-label'])["+edu.get(operator.get(i))+"]"));
		    	System.out.println(test);
		    	driver.findElement(By.xpath("(//div[@class='oj-listbox-result-label'])["+edu.get(operator.get(i))+"]")).click();
		    }
		    else
		    {
		    	driver.findElement(By.xpath("//div[contains(@id,'oj-listbox-drop')]//ul//li//div[@aria-label='"+operator.get(i)+"']")).click();	
		    }
		
		    if (operations.get(i).contains("click"))
		    {	
			CommonUtils.explicitWait(value2, "Click", "", driver);
			CommonUtils.hold(4);	
			value2.click();	
			CommonUtils.hold(4);
			driver.findElement(By.xpath("//div[contains(@id,'oj-listbox-drop')]//ul//li//div[@aria-label='"+values.get(i)+"']")).click();
			
			try {
				/*
				 * block to create rules based on arithmetic operations ex : invoice amount > line amount * 100
				 */
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='oj-select-choice-arithmetic_operator_conditionEditor_row_r0']")), "Visible", "", driver);
				driver.findElement(By.xpath("//div[@id='oj-select-choice-arithmetic_operator_conditionEditor_row_r0']")).click();
				CommonUtils.hold(1);
						driver.findElement(By.xpath("(//div[@class='oj-listbox-result-label'])["+arithmetic.get(values.get(i+1))+"]")).click();
				CommonUtils.hold(2);
					driver.findElement(By.xpath("//input[@id='arithmeticValue_conditionEditor_row_r0|input']")).click();
				CommonUtils.hold(1);
					driver.findElement(By.xpath("//input[@id='arithmeticValue_conditionEditor_row_r0|input']")).sendKeys(values.get(i+2));
					
					driver.findElement(By.xpath("//input[@id='arithmeticValue_conditionEditor_row_r0|input']")).sendKeys(Keys.ENTER);
				
					
			}catch (Exception aE) {
				System.out.println("Arithmetic Expressions not found....."+aE.getMessage());
			}
		    }
		    else if(operations.get(i).contains("sendkeys"))
		    {
		    	 if (operator.get(i).contains("contains") || operator.get(i).contains("start") || operator.get(i).contains("equal to") || operator.get(i).contains("one")|| operator.get(i).contains("does not end with")|| operator.get(i).contains("ends with") )		
		    	{				
		    		if (operator.get(i).contains("one")) 
		    		{
					String option = values.get(i);
					String[] options = option.split("oneof");
					int size1 = options.length;
					if (attributes.get(i).contains("Number")) {
						for (int j=0; j< size1; j++)
						{		
						driver.findElement(By.xpath("//input[contains(@class,'oj-combobox-input')]")).sendKeys(options[j]);
						driver.findElement(By.xpath("//input[contains(@class,'oj-combobox-input')]")).sendKeys(Keys.TAB);
						CommonUtils.hold(2);						
						}
						
					}
					else if (attributes.get(i).contains("Requester")) {
						for (int j=0; j< size1; j++)
							{
							CommonUtils.explicitWait(value3, "Click", "", driver);
							CommonUtils.hold(2);
							value3.click();
							CommonUtils.hold(2);
							value3input.sendKeys(options[j]);
							CommonUtils.hold(8);
							CommonUtils.explicitWait(value3table, "Click", "", driver);
							Actions act1 = new Actions(driver);
							CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[@class='oj-listbox-result-sub']//li//div//div[contains(@title,'"+options[j]+"')]")), "Click", "", driver);
							act1.moveToElement(driver.findElement(By.xpath("//ul[@class='oj-listbox-result-sub']//li//div//div[contains(@title,'"+options[j]+"')]"))).click().build().perform();
							CommonUtils.hold(2);
							CommonUtils.explicitWait(value3, "Click", "", driver);
							CommonUtils.hold(4);
							}
						}
					else {
					for (int j=0; j< size1; j++)
						{
						driver.findElement(By.xpath("//input[contains(@id,'value_multi_conditionEditor_row_r')]")).sendKeys(options[j]);
						driver.findElement(By.xpath("//input[contains(@id,'value_multi_conditionEditor_row_r')]")).sendKeys(Keys.TAB);
						}
					}
		    		}
		    		else
		    		{
		    		System.out.println(values.get(i));
		    		if (attributes.get(i).contains("Requester"))
				      {
		    			Actions act1 = new Actions(driver);
		    			CommonUtils.hold(4);
		    			CommonUtils.explicitWait(value2alt, "Visible", "", driver);
		    			CommonUtils.explicitWait(value2alt, "Click", "", driver);
		    			act1.moveToElement(value2alt).click().build().perform();
		    			CommonUtils.hold(4);
				    	act1.moveToElement(value2input).sendKeys(values.get(i)).build().perform();
				    	CommonUtils.hold(25);
				    	CommonUtils.explicitWait(value2alttable, "Click", "", driver);
				    	CommonUtils.explicitWait((driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-group')]//li//div//div//span[(text() ='"+values.get(i)+"')]"))), "Visible", "", driver);
				    	CommonUtils.explicitWait((driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-group')]//li//div//div//span[(text() ='"+values.get(i)+"')]"))), "Click", "", driver);
				    	act1.moveToElement(driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-group')]//li//div//div//span[(text() ='"+values.get(i)+"')]"))).doubleClick().build().perform();
				    	
				      }
		    		else
		    		  {
		    		driver.findElement(By.xpath("//input[contains(@id,'value_1_string_conditionEditor_row_r')]")).sendKeys(values.get(i));
		    		driver.findElement(By.xpath("//input[contains(@id,'value_1_string_conditionEditor_row_r')]")).sendKeys(Keys.TAB);
		    		  }
		    		}
		    	}                         
		
		    	else if (operator.get(i).contains("between"))				
		    	{
				String range = values.get(i);
				String[] ranges = range.split("btwn");
				String min =ranges[0];
				String max =ranges[1];
				
				if (min.contains("range")) {
					min.replace("range", "");
					driver.findElement(By.xpath("//input[contains(@name,'checkbox_1_conditionEditor_row_r')]")).click();
				}
				value2.click();	
				value2.sendKeys(min);
				value2.sendKeys(Keys.TAB);
			  
						
				if (max.contains("range")) {
					max.replace("range", "");
					driver.findElement(By.xpath("//input[contains(@name,'checkbox_2_conditionEditor_row_r')]")).click();
				}
				maxid.click();
				maxid.sendKeys(max);
				maxid.sendKeys(Keys.TAB);
		    	}
		    
		        else 
		        {
				CommonUtils.explicitWait(value2, "Click", "", driver);
				value2.click();		
				value2.sendKeys(values.get(i));
				value2.sendKeys(Keys.TAB);	
		        }
		    }	
		CommonUtils.explicitWait(close, "Click", "", driver);
		CommonUtils.hold(4);
		executor.executeScript("arguments[0].click();", close);
		System.out.println("Clicked close");
		}
		System.out.println("exitingfor");
	}
		
}
	
		
	public void autoapprove(WebDriver driver, String option) throws Exception {
	System.out.println("entered here");
	CommonUtils.explicitWait(addApprover, "Click", "", driver);
	addApprover.click();
	CommonUtils.explicitWait(actionForApprove, "Click", "", driver);
	CommonUtils.hold(2);
	actionForApprove.click();
	CommonUtils.explicitWait(autoApprove, "Click", "", driver);
	CommonUtils.hold(2);
	driver.findElement(By.xpath("//div[text() = '"+option+"']")).click();
	CommonUtils.hold(2);
	CommonUtils.explicitWait(close, "Click", "", driver);
	CommonUtils.hold(20);
	close.click();
	CommonUtils.hold(5);
}																
	

	public void autoReject(WebDriver driver) throws Exception {
		System.out.println("entered here");
		CommonUtils.explicitWait(addApprover, "Click", "", driver);
		addApprover.click();
		CommonUtils.explicitWait(actionForApprove, "Click", "", driver);
		CommonUtils.hold(2);
		actionForApprove.click();
		CommonUtils.explicitWait(autoReject, "Click", "", driver);
		CommonUtils.hold(2);
		autoReject.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(close, "Click", "", driver);
		CommonUtils.hold(20);
		close.click();
		CommonUtils.hold(5);
	}

	public void setActionOnrule(WebDriver driver, String userOrPolicy,List<String> ApproverInfo) throws Exception {
		System.out.println("entered here");
		
		CommonUtils.explicitWait(addApprover, "Click", "", driver);
		addApprover.click();
		CommonUtils.explicitWait(actionForApprove, "Click", "", driver);
		//CommonUtils.hold(2);
		actionForApprove.click();
		CommonUtils.explicitWait(sendForApproval, "Click", "", driver);
		CommonUtils.hold(2);
		if (userOrPolicy.toLowerCase().contains("fyi")) {
			
			driver.findElement(By.xpath("//div[(text()= 'Send FYI Notification')]")).click();
		}
		else
		{
		sendForApproval.click();
		}
		CommonUtils.explicitWait(to, "Click", "", driver);
		CommonUtils.hold(2);
		to.click();
	
		if(userOrPolicy.toLowerCase().contains("user") || userOrPolicy.toLowerCase().contains("role") || userOrPolicy.toLowerCase().contains("fyi")|| userOrPolicy.toLowerCase().contains("attributes"))
		{		
			if(userOrPolicy.toLowerCase().contains("attributes"))
			{
				CommonUtils.explicitWait(AttributeValue, "Click", "", driver);
				CommonUtils.hold(2);
				AttributeValue.click();
			}
			else {
		CommonUtils.explicitWait(userValue, "Click", "", driver);
		CommonUtils.hold(2);
		userValue.click();	
			}
		for(int k=0; k<ApproverInfo.size(); k++)
		{
			System.out.println(k);
			System.out.println(ApproverInfo.size());
			if(userOrPolicy.toLowerCase().contains("attributes"))
			{
				CommonUtils.explicitWait(attributeValue, "Click", "", driver);
				//CommonUtils.hold(2);
				attributeValue.click();
//				System.out.println("ApproverInfo.get(k) "+ ApproverInfo.get(k));
//				attributeValue.sendKeys(Keys.INSERT);
//				CommonUtils.hold(2);
//					attributeValue.sendKeys(ApproverInfo.get(k));
			}
			else {
			CommonUtils.explicitWait(userAndRole, "Click", "", driver);
			//CommonUtils.hold(2);
				userAndRole.click();
		//CommonUtils.hold(2);
				CommonUtils.explicitWait(userValue1, "Click", "", driver);
			//	CommonUtils.hold(2);
				//userValue1.click();
				CommonUtils.hold(1);
				userValue1.sendKeys(Keys.chord("k"));
				
				//CommonUtils.hold(5);
					
					  CommonUtils.explicitWait(userValue2Message, "Visible", "", driver);
					  CommonUtils.hold(2);
					 // userValue2Message.click();
					 // CommonUtils.hold(2);
					  userValue2.click(); 
					  CommonUtils.hold(2); 
					  userValue2.clear();
					  CommonUtils.hold(1); 
					  userValue2.sendKeys(ApproverInfo.get(k));
					 
				//CommonUtils.hold(2);
			}
				if(userOrPolicy.toLowerCase().contains("user") || userOrPolicy.toLowerCase().contains("fyi"))
				{
					CommonUtils.explicitWait(driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")), "Click", "", driver);
					driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")).click();

					//driver.findElement(By.xpath("//span[(text()= 'Users')]/ancestor::div[contains(@id,'oj-listbox-result-label-')]//following-sibling::ul//li//div//div[contains(@title,'"+ApproverInfo.get(k)+"')]")).click();

					//driver.findElement(By.xpath("//div[(text()= 'Users')]/../following-sibling::ul//span[(text() ='"+ApproverInfo.get(k)+"')]")).click();
				}
				if(userOrPolicy.toLowerCase().contains("role"))
				{
					//driver.findElement(By.xpath("//div[(text()= 'Application Roles')]/../following-sibling::ul//span[(text() ='"+ApproverInfo.get(k)+"')]")).click();

					//driver.findElement(By.xpath("//*[(text()= 'Application Roles')]/ancestor::div[contains(@id,'oj-listbox-result-label-')]//following-sibling::ul//li//div//div//span//oj-highlight-text//span[contains(text(),'"+ApproverInfo.get(k)+"')]")).click();
					CommonUtils.explicitWait(driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")), "Click", "", driver);

					driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")).click();

				}
				if(userOrPolicy.toLowerCase().contains("attributes"))
				{
					CommonUtils.explicitWait(driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")), "Click", "", driver);

					driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")).click();
				}
		}}
				
		if(userOrPolicy.toLowerCase().contains("policy"))
		{
			CommonUtils.explicitWait(policyValue, "Click", "", driver);
			//CommonUtils.hold(2);
			policyValue.click();
			CommonUtils.explicitWait(policyField, "Click", "", driver);
			//CommonUtils.hold(2);
			policyField.click();
			//CommonUtils.hold(2);
			//CommonUtils.hold(4);
				driver.findElement(By.xpath("//span[contains(text(),'"+ApproverInfo.get(0)+"')]")).click();
		}
		CommonUtils.explicitWait(close, "Click", "", driver);
		//CommonUtils.hold(20);
		close.click();
		CommonUtils.hold(5);
		System.out.println("exiting set action");
	}
	
	public void DeployRule(WebDriver driver) throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		saveRule.click();
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(save, "Click", "", driver);
		Actions action = new Actions(driver);
		action.moveToElement(save).click().build().perform();
		CommonUtils.hold(5);
		WebDriverWait wait = new WebDriverWait(driver, 45);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Saved']")));
		System.out.println("class is ");
		
			String cls= deploy.getAttribute("class");
			System.out.println("class is "+cls);
			boolean isVisible=cls.contains("disabled");		
			if(!isVisible)
			{
				deploy.click();
				CommonUtils.explicitWait(ok, "Click", "", driver);
				executor.executeScript("arguments[0].click();", ok);
			//CommonUtils.hold(10);
			    CommonUtils.explicitWait(deployComplete, "Click", "", driver);

			boolean isEnabled=deployComplete.isDisplayed();
			if(isEnabled)
			{
				Log.info("Rule Deploy is sucessfully");
			}
			else
			{
				Log.info("Rule Deploy failed");
				Assert.assertTrue(false,"Deployment failed");
			}
			}
			else
			{
				Log.info("Deploy button is not enabled");
				Assert.assertTrue(false,"Deploy button is not enabled");
			}
			CommonUtils.explicitWait(stepClose, "Click", "", driver);
		    CommonUtils.hold(10);
			stepClose.click();
					
		}

	public void EditStepsAndNavigateToRulePage(WebDriver approvalDriver) throws Exception {
	
		CommonUtils.explicitWait(stepName2, "Click", "", approvalDriver);
		//CommonUtils.hold(5);
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		executor.executeScript("arguments[0].click();", stepName2);
			//CommonUtils.hold(3);
		CommonUtils.explicitWait(action, "Click", "", approvalDriver);
		//CommonUtils.hold(5);
		executor.executeScript("arguments[0].click();", action);
		//CommonUtils.hold(2);
		CommonUtils.explicitWait(editStep, "Click", "", approvalDriver);
		//CommonUtils.hold(2);
		executor.executeScript("arguments[0].click();", editStep);
		//CommonUtils.hold(8);
		
	}


	public void createSupervisorRoutingPolicy(WebDriver driver,List<String> Policyname ,String[] SHconditions, String[] SHattributes, String []Values, String noRuleMatchAction ) throws Exception
	{
		
		Actions act=new Actions(driver);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		
		int ValuesLen=Values.length;
		
		Dictionary edu = new Hashtable();
		edu.put("lesserthanorequaltosign", "\u2264");
		edu.put("greaterthanorequaltosign", "\u2265");
		edu.put("greaterthansign", ">");
		edu.put("equaltosign", "=");
		edu.put("notequaltosign", "\u2260");
		edu.put("lesserthansign", "<");
		
		menu.click();
		CommonUtils.explicitWait(createRP, "Click", "", driver);
		createRP.click();
		CommonUtils.explicitWait(supervisorHierarchy, "Click", "", driver);
		act.moveToElement(supervisorHierarchy).click().build().perform();
			
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForJStoLoad(driver);
		CommonUtils.explicitWait(supervisorHierarchyLabel, "Click", "", driver);
		CommonUtils.hold(4);
		policyName.sendKeys(Policyname.get(0));
		CommonUtils.hold(2);
		
		/*/*******************************************************************Adding Start with and choosing Stop at *********************************************** **********/
			
		CommonUtils.explicitWait(startWith, "Click", "", driver);
		startWith.click();
		CommonUtils.hold(4);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[contains(text(),'"+SHconditions[0]+"')]")));
		CommonUtils.hold(4);
		act.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'"+SHconditions[0]+"')]"))).click().build().perform();
		supervisorHierarchyLabel.click();
	
		if(SHconditions[1].contains("After a specific number of levels"))
		{
			CommonUtils.explicitWait(limit, "Click", "", driver);
			limit.click();
			CommonUtils.hold(5);
			limitinput.sendKeys(SHconditions[3]);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[(text() ='"+SHconditions[3]+"')]")), "Click", "", driver);
			driver.findElement(By.xpath("//span[(text() ='"+SHconditions[3]+"')]")).click();
			CommonUtils.hold(2);
			supervisorHierarchyLabel.click();
			CommonUtils.explicitWait(stop, "Click", "", driver);
			stop.click();	
			act.moveToElement(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-stopWhen']//li//div//oj-option[text()='"+SHconditions[1]+"']"))).click().build().perform();
			supervisorHierarchyLabel.click();
			CommonUtils.explicitWait(numberOfLevel, "Click", "", driver);
			numberOfLevel.sendKeys(SHconditions[2]);
		}
		
		else if(SHconditions[1].contains("When approver has sufficient approval authority"))
		{
			CommonUtils.explicitWait(stop, "Click", "", driver);
			CommonUtils.hold(4);
			stop.click();
			act.moveToElement(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-stopWhen']//li//div//oj-option[text()='"+SHconditions[1]+"']"))).click().build().perform();
			supervisorHierarchyLabel.click();
			CommonUtils.hold(2);
			CommonUtils.explicitWait(limitsBasedOn, "Click", "", driver);
			limitsBasedOn.click();
			CommonUtils.hold(1);
			act.moveToElement(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-limitsBasedOn']//div[text()='"+SHconditions[2]+"']"))).click().build().perform();
			CommonUtils.hold(2);
			CommonUtils.explicitWait(limitCurrency, "Click", "", driver);
			CommonUtils.hold(2);
			limitCurrency.click();
			CommonUtils.hold(2);
			limitCurrencyText.sendKeys(SHconditions[3]);
			CommonUtils.hold(2);
			act.moveToElement(limitCurrencyDropdown).click().build().perform();
			CommonUtils.hold(2);
			
			/*/*******************************************************************Adding Attributes****************************************************************************/
			
			for (int i=0;i<SHattributes.length;i++)
			{
				CommonUtils.explicitWait(addAttribute, "Click", "", driver);
	            CommonUtils.hold(2);
	            addAttribute.click();
				CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[contains(text(),'"+SHattributes[i]+"')]")));
				driver.findElement(By.xpath("//*[text()='"+SHattributes[i]+"']")).click();
				addAttributeIcon.click();
			}
			
			/*/*******************************************************************Attributes (with operator and value)********************************************************/       
                        
			for (int i=0; i<ValuesLen-1;i++)
    		{
    		String AllValues = Values[i+1];
    		String [] value = AllValues.split("SH");
    		String [] SHoperators = value[0].split("seperator");
			String [] SHvalues = value[1].split("seperator");
			String SHUsers = value[2];
			String SHlimitlist= value[3];
			            
			if(i>0)
			{
				
				CommonUtils.hold(2);			
				CommonUtils.explicitWait(driver.findElement(By.xpath("*//tr["+i+"]//a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);
				CommonUtils.hold(2);
				act.moveToElement(driver.findElement(By.xpath("*//tr["+i+"]//a[@class='add-icon' and @title='Add Row']"))).click().build().perform();
				CommonUtils.hold(2);
			}
			
			for(int k=1;k<=SHattributes.length;k++)
			{
				String SHop= SHoperators[k-1];
				if(!SHop.contains("donothing")) 
				{
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")), "Click", "", driver);
				driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")).click();
				CommonUtils.hold(2);
				
				Actions Act1 = new Actions(driver);
				Act1.moveToElement(driver.findElement(By.xpath("(//*[@title='Edit'])["+k+"]"))).click().build().perform();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(SHoperator, "Click", "", driver);
				Act1.moveToElement(SHoperator).click().build().perform();;
				
				if(SHop.contains("sign"))
				{
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-operator_conditionEditor_authority-row-editor']//li//div[text()='"+edu.get(SHop)+"']")), "Click", "", driver);
					driver.findElement(By.xpath("//ul[@id='oj-listbox-results-operator_conditionEditor_authority-row-editor']//li//div[text()='"+edu.get(SHop)+"']")).click();

				}
				else if(SHop.contains("between"))
				{
					String SHop2=SHop.replace("inclusive","");
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+SHop2+"']")), "Click", "", driver);

					driver.findElement(By.xpath("//*[text()='"+SHop2+"']")).click();
					CommonUtils.hold(2);
				        int firstIndex=SHop.indexOf("inclusive");
				        int secondIndex=SHop.lastIndexOf("inclusive");
				        if(firstIndex!=-1)
				        {
				        	checkbox1.click();
				        }
				        
				        if(secondIndex!=-1||secondIndex!=firstIndex)
				        {
				        	checkbox2.click();
				        }
				        CommonUtils.hold(2);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+SHop.replace("inclusive", "")+"']")), "Click", "", driver);

						driver.findElement(By.xpath("//*[text()='"+SHop.replace("inclusive", "")+"']")).click();
						CommonUtils.hold(2);
				}
				
				else
				{
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+SHop+"']")), "Click", "", driver);

					driver.findElement(By.xpath("//*[text()='"+SHop+"']")).click();
				}
				if(SHvalues[k-1].contains("donothing")) 
				{
					
					
				}
				else {
				valuesection.click();
				CommonUtils.explicitWait(valuesection, "Click", "", driver);
				if(SHop.contains("is one of") || SHop.contains("is not one of"))
				{
					String SHvalue []= SHvalues[k-1].split("oneof");
					for (int ii=1;ii<=SHvalue.length;ii++)
					{
						
						valuesection.sendKeys(SHvalue[ii-1]);
						CommonUtils.hold(4);
						valuesection.sendKeys(Keys.TAB);CommonUtils.hold(4);
						CommonUtils.explicitWait(valuesection, "Click", "", driver);
						valuesection.click();
						CommonUtils.explicitWait(valuesection, "Click", "", driver);
						CommonUtils.hold(4);						
					}
				}
				else if(SHop.contains("is between") || SHop.contains("is not between"))
				{
					String SHvalue []= SHvalues[k-1].split("isbetween");
					value1.sendKeys(SHvalue[0]);
					value1_1.sendKeys(SHvalue[1].replace("isbetween", ""));
				}
				
				
				else {
				valuesection.sendKeys(SHvalues[k-1]);
				}
				}
				tick.click();
				CommonUtils.hold(2);
				}
			}
            
			/*/*******************************************************************Approver (with usertype and username)********************************************************/

            int SHattribute2= SHattributes.length+1;
			driver.findElement(By.xpath("//*[@id='approver_label_"+i+"_"+SHattribute2+"']")).click();
			CommonUtils.hold(5);
			
			if(SHUsers.contains("User"))
			{
				String [] SHapproverUser = SHUsers.split("-");
				String SHapproverUserType= SHapproverUser[0];
				String SHapproverUserInput= SHapproverUser[1];
				CommonUtils.explicitWait(approvalUserType, "Click", "", driver);
				approvalUserType.click();
				CommonUtils.hold(4);
				driver.findElement(By.xpath("//ul[@aria-label='Approver target type list']//li//span[text()='"+SHapproverUserType+"']")).click();
				CommonUtils.hold(4);
				approvalUserType.sendKeys(Keys.TAB);
				act.moveToElement(SHuser).click().build().perform();
				act.moveToElement(SHuser).sendKeys(SHapproverUserInput).build().perform();
				CommonUtils.hold(25);
				CommonUtils.explicitWait(SHuserTable, "Click", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")), "Visible", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")), "Click", "", driver);
				act.moveToElement(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']"))).doubleClick().build().perform();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(tick2, "Click", "", driver);
				act.moveToElement(tick2).click().build().perform();
				CommonUtils.hold(5);
			}
			
			else if(SHUsers.contains("Job Level"))
			{
				String [] SHapproverJL = SHUsers.split("-");
				String SHapproverJLType= SHapproverJL[0];
				String SHapproverJLInput= SHapproverJL[1];
				CommonUtils.explicitWait(approvalUserType, "Click", "", driver);
				approvalUserType.click();
				CommonUtils.hold(4);
				act.moveToElement(driver.findElement(By.xpath("//ul[@aria-label='Approver target type list']//li//span[text()='"+SHapproverJLType+"']"))).click().build().perform();
				CommonUtils.hold(8);
				JobLevelInput.click();
				JobLevelInput.sendKeys(SHapproverJLInput);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(tick2, "Click", "", driver);
				act.moveToElement(tick2).click().build().perform();
				CommonUtils.hold(5);
			}
			
			/*/*******************************************************************Approver Limit*****************************************************************************/
			
			CommonUtils.explicitWait(approvalLimit, "Click", "", driver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(approvalLimit, "Click", "", driver);
			act.moveToElement(approvalLimit).click().build().perform();
			CommonUtils.hold(4);
			if(SHlimitlist.contains("unlimited"))
			{
				CommonUtils.explicitWait(approvalLimitinput, "Click", "", driver);
				CommonUtils.hold(1);
				approvalLimitinput.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(unlimitedLimit, "Click", "", driver);
				CommonUtils.hold(2);
				act.moveToElement(unlimitedLimit).click().build().perform();
				CommonUtils.hold(2);
			}
			else
			{
			approvalLimitinput.sendKeys(SHlimitlist);
			CommonUtils.hold(2);
			approvalLimitinput.sendKeys(Keys.TAB);
			CommonUtils.hold(2);
    		}
		}
		}
		
		Log.info("no rule action is "+noRuleMatchAction);
		if(("ApproveRejectSkip").contains(noRuleMatchAction))
		{
			CommonUtils.hold(2);
			noauthorityfound.click();
			CommonUtils.explicitWait(noauthorityfoundaction, "Click", "", driver);
			noauthorityfoundaction.click();
			CommonUtils.hold(2);
			driver.findElement(By.xpath("//ul[@id='oj-listbox-results-rpPopupNoMatch']//descendant::oj-option[text()='"+noRuleMatchAction+"']")).click();
		}
		
		
		CommonUtils.explicitWait(savePolicy, "Click", "", driver);
		CommonUtils.hold(5);
		act.moveToElement(savePolicy).click().build().perform();
		CommonUtils.explicitWait(saveandClose, "Click", "", driver);
		CommonUtils.hold(3);
		act.moveToElement(saveandClose).click().build().perform();
		
		try
		{
			boolean ispresent=driver.findElement(By.xpath("//div[text()='"+Policyname.get(0)+"']")).isDisplayed();
			if(ispresent)
			{
				Log.info(" routing policy created");

			}
		}
		catch(Exception e)
		{
			
			Log.info(" routing policy not created");
			boolean ispresent=err.isDisplayed();
			if(ispresent)
			{
				Log.info("Error occured after saving the policy");
				Log.info("Error is "+err.getText());
				assertTrue(false,err.getText());

			}
			assertTrue(false,"Routing policy not created error is"+err.getText());
		}
		CommonUtils.explicitWait(menuClose, "Click", "", driver);
		menuClose.click();
		CommonUtils.hold(3);
	}
	
	
//	public void createApprovalGroup(WebDriver driver,List<String> Policyname , String Notifytype,List<String> users) throws Exception
//	{
//		
//		Actions act=new Actions(driver);
//	    JavascriptExecutor executor = (JavascriptExecutor)driver;
//		menu.click();
//		CommonUtils.hold(4);
//		CommonUtils.explicitWait(createRP, "Click", "", driver);
//		CommonUtils.hold(4);
//		createRP.click();
//		CommonUtils.explicitWait(approvalGroup, "Click", "", driver);
//		CommonUtils.hold(6);
//		act.moveToElement(approvalGroup).click().build().perform();
//		CommonUtils.hold(4);
//		CommonUtils.waitForPageLoad(driver);
//		CommonUtils.waitForJStoLoad(driver);
//		CommonUtils.explicitWait(policyName,"Click", "", driver);
//		policyName.sendKeys(Policyname.get(0));
//		CommonUtils.hold(4);
//		notify.click();
//		driver.findElement(By.xpath("//li//div[text()='"+Notifytype+"']")).click();
//		CommonUtils.hold(4);
//		CommonUtils.explicitWait(EnterName,"Click", "", driver);
//		EnterName.click();
//		CommonUtils.hold(4);
//		act.doubleClick(EnterName).perform();
//		CommonUtils.hold(4);
//		EnterName2.click();
//		//EnterName2.click();
//		CommonUtils.hold(4);
//		driver.findElement(By.xpath("//div[@title='"+users.get(0)+"']")).click();
//		CommonUtils.hold(4);
//		//action1.click();
//		int numberOfUsers=users.size();
//		if(numberOfUsers>1)
//		{
//					
//			for(int i=1;i<numberOfUsers;i++)
//			{
//				CommonUtils.hold(5);
//				executor.executeScript("arguments[0].click();", addMember);
//				CommonUtils.hold(5);	
//				CommonUtils.explicitWait(EnterName2,"Click", "", driver);
//				CommonUtils.hold(5);
//				executor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", EnterName2);
//				EnterName2.click();
//				CommonUtils.hold(5);
//				driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")).click();
//				CommonUtils.hold(6);
//				}
//			
//		}
//		act.moveToElement(savePolicy).click().build().perform();
//		CommonUtils.explicitWait(saveandClose, "Click", "", driver);
//		CommonUtils.hold(5);
//		act.moveToElement(saveandClose).click().build().perform();
//	//	saveandClose.click();
//		CommonUtils.hold(5);
//
//		try
//		{
//			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[text()='"+Policyname.get(0)+"']")), "Click", "", driver);
//			boolean ispresent=driver.findElement(By.xpath("//div[text()='"+Policyname.get(0)+"']")).isDisplayed();
//			if(ispresent)
//			{
//				Log.info(" routing policy created");
//
//			}
//		}
//		catch(Exception e)
//		{
//			
//			Log.info(" routing policy not created");
//			boolean ispresent=err.isDisplayed();
//			if(ispresent)
//			{
//				Log.info("Error occured after saving the policy");
//				Log.info("Error is "+err.getText());
//				assertTrue(false,err.getText());
//
//			}
//			assertTrue(false,"Routing policy not created error is"+err.getText());
//		}
//		CommonUtils.explicitWait(menuClose, "Click", "", driver);
//		CommonUtils.hold(5);
//		menuClose.click();
//				
//		CommonUtils.hold(5);
//	}


public void createApprovalGroup(WebDriver driver,List<String> Policyname , String Notifytype,List<String> users) throws Exception
	{
		
		Actions act=new Actions(driver);
	    JavascriptExecutor executor = (JavascriptExecutor)driver;
		menu.click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(createRP, "Click", "", driver);
		CommonUtils.hold(4);
		createRP.click();
		CommonUtils.explicitWait(approvalGroup, "Click", "", driver);
		CommonUtils.hold(6);
		act.moveToElement(approvalGroup).click().build().perform();
		CommonUtils.hold(4);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForJStoLoad(driver);
		CommonUtils.explicitWait(policyName,"Click", "", driver);
		policyName.sendKeys(Policyname.get(0));
		CommonUtils.hold(4);
		notify.click();
		driver.findElement(By.xpath("//li//div[text()='"+Notifytype+"']")).click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(EnterName,"Click", "", driver);
		EnterName.click();
		CommonUtils.hold(4);
		act.doubleClick(EnterName).perform();
//		CommonUtils.hold(4);
//		EnterName2.click();
//		CommonUtils.hold(4);
//		driver.findElement(By.xpath("//input[@id='oj-searchselect-filter-members|input']")).sendKeys(users.get(0));
//		CommonUtils.hold(4);
//		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='lovDropdown_members']")), "Click", "", driver);
//		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+users.get(0)+"']")), "Visible", "", driver);
//		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+users.get(0)+"']")), "Click", "", driver);
//		CommonUtils.hold(8);
//		act.moveToElement(driver.findElement(By.xpath("//div[@title='"+users.get(0)+"']"))).doubleClick().build().perform();
//		CommonUtils.hold(4);
		//action1.click();
		
		int numberOfUsers=users.size();		
		if(numberOfUsers>1)
		{
				
			int k=1 ;//j=4;	
			for(int i=0;i<numberOfUsers;i++)
			{
				if(i>=1) {
				
				if(i==1)
				act.moveToElement(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]"))).build().perform();
				CommonUtils.explicitWait(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]//tr["+k+"]//td[4]//a[@title='Add Member']")), "Visible", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]//tr["+k+"]//td[4]//a[@title='Add Member']")), "Click", "", driver);
				act.moveToElement(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]//tr["+k+"]//td[4]//a[@title='Add Member']"))).click().build().perform();
				k++;
				}
				//CommonUtils.hold(5);
				//executor.executeScript("arguments[0].click();", addMember);
				CommonUtils.hold(5);
				CommonUtils.explicitWait(EnterName2, "Click", "", driver);
				EnterName2.click();
				CommonUtils.hold(4);
				driver.findElement(By.xpath("//input[@id='oj-searchselect-filter-members|input']")).sendKeys(users.get(i));
				CommonUtils.hold(4);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='lovDropdown_members']")), "Click", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")), "Visible", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")), "Click", "", driver);
				CommonUtils.hold(8);
				act.moveToElement(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).click().build().perform();
				CommonUtils.hold(4);
				act.clickAndHold(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).doubleClick().build().perform();

				//act.moveToElement(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).doubleClick().build().perform();
				//executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")));
				CommonUtils.hold(10);
				//int s=i+1;
//				CommonUtils.explicitWait(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]//tr["+s+"]//td[2]//div[@title='"+users.get(i)+"']")), "Visible", "", driver);
//				
//				CommonUtils.explicitWait(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]//tr["+s+"]//td[2]//div[@title='"+users.get(i)+"']")), "Click", "", driver);
				}
			
		}
		act.moveToElement(savePolicy).click().build().perform();
		CommonUtils.explicitWait(saveandClose, "Click", "", driver);
		CommonUtils.hold(5);
		act.moveToElement(saveandClose).click().build().perform();
	//	saveandClose.click();
		CommonUtils.hold(5);

		try
		{
			boolean ispresent=driver.findElement(By.xpath("//div[text()='"+Policyname.get(0)+"']")).isDisplayed();
			if(ispresent)
			{
				Log.info(" routing policy created");

			}
		}
		catch(Exception e)
		{
			
			Log.info(" routing policy not created");
			boolean ispresent=err.isDisplayed();
			if(ispresent)
			{
				Log.info("Error occured after saving the policy");
				Log.info("Error is "+err.getText());
				assertTrue(false,err.getText());

			}
			assertTrue(false,"Routing policy not created error is"+err.getText());
		}
		CommonUtils.explicitWait(menuClose, "Click", "", driver);
		CommonUtils.hold(5);
		menuClose.click();
				
		CommonUtils.hold(5);
	}
	
	public void deleteApprovalGroup(WebDriver driver) {
		
		
	}
	
	
	public void waitForPageLoad(WebDriver driver) {

	    Wait<WebDriver> wait = new WebDriverWait(driver, 30);
	    wait.until(new Function<WebDriver, Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            System.out.println("Current Window State       : "
	                + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
	            return String
	                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
	                .equals("complete");
	        }
	    });
	}
	
	public String[] loadProperties (String testdata) throws IOException {
	
		FileReader propFileRead = new FileReader(
				"./src/test/resources/Approvals/approvals.properties");
		Properties fileProperties = new Properties();
		fileProperties.load(propFileRead);
		String[] parameters = fileProperties.getProperty(testdata).split(",");
		
		      return parameters; 
		      
		    }
	public void createApprovalAuthorityPolicy(WebDriver driver, List<String> policyName, String assignLimit,
			String returnApproverFrom, String[] AAattributes, String []valueList, String noRuleMatchAction) throws Exception {
		// TODO Auto-generated method stub
		int len=valueList.length;
		System.out.println(len+" len is ");
		System.out.println("assignLimit "+assignLimit);
		System.out.println("returnApproverFrom "+returnApproverFrom);
		System.out.println("aAattributes "+Arrays.toString(AAattributes));

		Dictionary edu = new Hashtable();
		edu.put("lesserthanorequaltosign", 6); 
		edu.put("greaterthansign", 3);
		edu.put("greaterthanorequaltosign", 4); 
		edu.put("equalsign", 7);
		edu.put("lessthansign",5);
		Actions act=new Actions(driver);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		menu.click();
		//CommonUtils.hold(4);
		CommonUtils.explicitWait(createRP, "Click", "", driver);
		//CommonUtils.hold(4);
		createRP.click();
		//CommonUtils.hold(4);
		CommonUtils.explicitWait(approvalAuthority, "Click", "", driver);
		//CommonUtils.hold(6);
		act.moveToElement(approvalAuthority).click().build().perform();
		//CommonUtils.hold(4);

		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForJStoLoad(driver);
		CommonUtils.explicitWait(sequence, "Click", "", driver);
		aaName.sendKeys(policyName.get(0));
		CommonUtils.explicitWait(addAttribute, "Click", "", driver);
		if(assignLimit.contains("no"))
		{
			if(!returnApproverFrom.contains("First"))
			{
				approverFrom.click();CommonUtils.hold(2);
				allautorityrecord.click();CommonUtils.hold(2);
			}
		}	
		if(assignLimit.contains("yes"))
		{
			limit1.click();
			CommonUtils.explicitWait(limitbasedOn, "Click", "", driver);
			String limitdata[]=assignLimit.split("limit");
			System.out.println("Limit data is "+limitdata);
			limitbasedOn.click();
			driver.findElement(By.xpath("//div[contains(text(),'"+limitdata[1]+"')]")).click();
			if(limitdata[2]!="USD")
			{
				System.out.println("Limit based on is "+limitdata[2]);
				limitselection.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(limitselectioninput, "Click", "", driver);

				limitselectioninput.sendKeys(limitdata[2]);		
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-limitCurrency']//descendant::span[contains(text(),'"+limitdata[2]+"')]")), "Click", "", driver);
				driver.findElement(By.xpath("//ul[@id='oj-listbox-results-limitCurrency']//descendant::span[contains(text(),'"+limitdata[2]+"')]")).click();
			}
		}
		for (int i=0;i<AAattributes.length;i++)
		{
			addAttribute.click();
			CommonUtils.hold(2);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[contains(text(),'"+AAattributes[i]+"')]")));
			driver.findElement(By.xpath("//*[text()='"+AAattributes[i]+"']")).click();
			addAttributeIcon.click();
		}
		//			for(int z=1;z<len;z++) 
		//			
		//			{ 
		//				String allvalues=valueList[z];
		//					String [] values= allvalues.split("AA");
		//				String AAopeartors=values[0];
		//				String AAvalues=values[0];
		//				String AAUsers=values[0];
int len2=AAattributes.length;
len2=len2+4;
		//for (int i=0;i<len-1;i++)
			for (int i=0;i<len-1;i++)

		{
			String allvalues=valueList[i+1];
			String [] values= allvalues.split("AA");
			String []AAopeartors=values[0].split("seperator");
			String []AAvalues=values[1].split("seperator");
			String AAUsers=values[2];
			String limitlist=values[3];
			System.out.println("Values in "+i+1+" iteration "+Arrays.toString(values)+" each value "+Arrays.toString(AAopeartors)+"  "+Arrays.toString(AAvalues)+"  "+AAUsers+" "+limitlist);
			if(i>0)
			{
				
				//act.moveToElement(add).click().build().perform();
				//int k=i+2;
				//act.moveToElement(addicon).click().build().perform();
				if(assignLimit.contains("yes"))
				{
					CommonUtils.hold(4);
					System.out.println("*//tr["+i+"]//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']");
					CommonUtils.explicitWait(driver.findElement(By.xpath("*//tr["+i+"]//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);

					act.moveToElement(driver.findElement(By.xpath("*//tr["+i+"]//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']"))).click().build().perform();

				}
				else
				{ int lengh=len2-1;
				System.out.println("//table[contains(@class,'oj-table-element oj-component-initnode oj-focus')]//tbody//tr["+i+"]//td["+lengh+"]//a[@class='add-icon' and @title='Add Row']");
				CommonUtils.explicitWait(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode oj-focus')]//tbody//tr["+i+"]//td["+lengh+"]//a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);

					act.moveToElement(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode oj-focus')]//tbody//tr["+i+"]//td["+lengh+"]//a[@class='add-icon' and @title='Add Row']"))).click().build().perform();

				}CommonUtils.hold(5);
			}
			for(int k=1;k<=AAattributes.length;k++)
			{
				String op=AAopeartors[k-1];
				if(!op.contains("donothing")) {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")), "Click", "", driver);
				driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")).click();
				CommonUtils.hold(2);
				//CommonUtils.explicitWait(editCondition, "ElementVisible", "", driver);

				Actions act1 = new Actions(driver);
				act1.moveToElement(driver.findElement(By.xpath("(//*[@title='Edit'])["+k+"]"))).click().build().perform();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(clickOperator, "Click", "", driver);
				act1.moveToElement(clickOperator).click().build().perform();;
				//String op=AAopeartors[k-1];
				if(op.contains("sign"))
				{
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-operator_conditionEditor_authority-row-editor']//li["+edu.get(op)+"]")), "Click", "", driver);
					driver.findElement(By.xpath("//ul[@id='oj-listbox-results-operator_conditionEditor_authority-row-editor']//li["+edu.get(op)+"]")).click();
				}
				else if(op.contains("between"))
				{
					String op2=op.replace("inclusive","");
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+op2+"']")), "Click", "", driver);

					driver.findElement(By.xpath("//*[text()='"+op2+"']")).click();
					CommonUtils.hold(2);
				        int firstIndex=op.indexOf("inclusive");
				        System.out.println(i);
				         int secondIndex=op.lastIndexOf("inclusive");
				        System.out.println(k);
				        if(firstIndex!=-1)
				        {
				        	checkbox1.click();
				        }
				        
				        if(secondIndex!=-1||secondIndex!=firstIndex)
				        {
				        	checkbox2.click();
				        }
				        CommonUtils.hold(2);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+op.replace("inclusive", "")+"']")), "Click", "", driver);

						driver.findElement(By.xpath("//*[text()='"+op.replace("inclusive", "")+"']")).click();
						CommonUtils.hold(2);
				}
				
				else{
					CommonUtils.hold(2);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+op+"']")), "Click", "", driver);

					driver.findElement(By.xpath("//*[text()='"+op+"']")).click();
				}
				if(AAvalues[k-1].contains("donothing")) 
				{
					
					
				}
				else {
				valuesection.click();
				CommonUtils.explicitWait(valuesection, "Click", "", driver);
				if(op.contains("is one of"))
				{
					String value []=AAvalues[k-1].split("oneof");
					for (int ii=1;ii<=value.length;ii++)
					{
						
						valuesection.sendKeys(value[ii-1]);
						CommonUtils.hold(4);
						valuesection.sendKeys(Keys.TAB);CommonUtils.hold(4);
						CommonUtils.explicitWait(valuesection, "Click", "", driver);
						valuesection.click();
						CommonUtils.explicitWait(valuesection, "Click", "", driver);
						CommonUtils.hold(4);						
					}
				}
				else if(op.contains("is between") || op.contains("is not between"))
				{
					String value []=AAvalues[k-1].split("isbetween");
					value1.sendKeys(value[0]);
					value1_1.sendKeys(value[1].replace("isbetween", ""));
				}
				
				else {
				valuesection.sendKeys(AAvalues[k-1]);
				}
				}
				tick.click();
				CommonUtils.hold(2);
				}
			}
			//CommonUtils.explicitWait(approver, "Click", "", driver);
			//approver.click();
			int len1=AAattributes.length+1;
			driver.findElement(By.xpath("//*[@id='approver_label_"+i+"_"+len1+"']")).click();
			CommonUtils.hold(5);
			//approver.click();CommonUtils.hold(2);
			if(AAUsers.contains("user"))
			{
				String approver=AAUsers.replace("user", "");
				CommonUtils.explicitWait(userinput, "Click", "", driver);
				act.moveToElement(userinput).click().build().perform();
				act.moveToElement(userinput).sendKeys(approver).build().perform();
				CommonUtils.hold(30);
				CommonUtils.explicitWait(usertable, "Click", "", driver);
				//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[contains(text(),'"+approver+"')]")));
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver+"']")), "Click", "", driver);
				act.moveToElement(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver+"']"))).doubleClick().build().perform();
				//userinput.sendKeys(Keys.INSERT);
				CommonUtils.explicitWait(tick2, "Click", "", driver);
				act.moveToElement(tick2).click().build().perform();
				CommonUtils.hold(5);
			}
			if(assignLimit.contains("yes"))
			{int s=AAattributes.length+4;
			int o=i+1;
				act.moveToElement(approvalLimit).doubleClick().build().perform();
				CommonUtils.hold(2);
				if(limitlist.contains("unlimited"))
				{
					
					System.out.println("s is "+s+" i is "+i);
					act.moveToElement(unlimitedLimit).click().build().perform();
					CommonUtils.hold(2);
					
					act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
				}
				else {
				act.moveToElement(approvalLimitinput).sendKeys(limitlist).build().perform();
				CommonUtils.hold(2);
				approvalLimitinput.sendKeys(Keys.TAB);
				act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
				CommonUtils.hold(2);
				
				}
				
				
			}

		}
		//}
			System.out.println("no rule action is "+noRuleMatchAction);
			if(("ApproveRejectSkip").contains(noRuleMatchAction))
			{
				CommonUtils.hold(2);
				//noauthorityfound.click();
				//act.moveToElement(noauthorityfound).click().build().perform();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();",noauthorityfound);
				CommonUtils.explicitWait(noauthorityfoundaction, "Click", "", driver);
				noauthorityfoundaction.click();
				CommonUtils.hold(2);
				driver.findElement(By.xpath("//ul[@id='oj-listbox-results-rpPopupNoMatch']//descendant::oj-option[text()='"+noRuleMatchAction+"']")).click();
			}
		act.moveToElement(savePolicy).click().build().perform();
		CommonUtils.explicitWait(saveandClose, "Click", "", driver);
		CommonUtils.hold(5);
		act.moveToElement(saveandClose).click().build().perform();
		//	saveandClose.click();
		CommonUtils.hold(5);

		try
		{
			boolean ispresent=driver.findElement(By.xpath("//div[text()='"+policyName.get(0)+"']")).isDisplayed();
			if(ispresent)
			{
				Log.info(" routing policy created");

			}
		}
		catch(Exception e)
		{

			Log.info(" routing policy not created");
			boolean ispresent=err.isDisplayed();
			if(ispresent)
			{
				Log.info("Error occured after saving the policy");
				Log.info("Error is "+err.getText());
				assertTrue(false,err.getText());

			}
			assertTrue(false,"Routing policy not created error is"+err.getText());
		}
		CommonUtils.explicitWait(menuClose, "Click", "", driver);
		CommonUtils.hold(5);
		menuClose.click();
		System.out.println("Routing policy created :"+policyName.get(0));
		Log.info("Routing policy created :"+policyName.get(0));
		CommonUtils.hold(5);

	}
		}

	
	
	


