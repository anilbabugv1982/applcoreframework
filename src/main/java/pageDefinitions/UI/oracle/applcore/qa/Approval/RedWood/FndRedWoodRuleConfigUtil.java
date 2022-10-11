package pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood;

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
import org.testng.Reporter;

import com.gargoylesoftware.htmlunit.WebConsole.Logger;
import com.google.common.base.Function;

import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigPage;

public class FndRedWoodRuleConfigUtil extends FndRedWoodRuleConfigPage{
	
	public FndRedWoodRuleConfigUtil(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	public void DeleteStepsAndNavigateToRulePage(WebDriver approvalDriver) throws Exception {
		boolean isVisible=true;	
		CommonUtils.hold(12);
		
		
		boolean isPresent = false;
		try {
			isPresent=FndRedWoodRuleConfigPage.NewStep.isDisplayed();
			Log.info("Creating New Step");
			if(isPresent)
			{
			FndRedWoodRuleConfigPage.NewStep.sendKeys(Keys.ENTER);
			CommonUtils.hold(2);
			}
		} catch (Exception e) {
			Log.info("no need to press enter");
			
		}
		
		CommonUtils.explicitWait(stepName2, "Visible", "", approvalDriver);
		CommonUtils.explicitWait(stepName2, "Click", "", approvalDriver);
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		executor.executeScript("arguments[0].click();", stepName2);
		CommonUtils.explicitWait(action, "Click", "", approvalDriver);
		executor.executeScript("arguments[0].click();", action);
		String cls= deleteStep.getAttribute("class");
		System.out.println("class is "+cls);
		isVisible=(!cls.contains("disabled"))&&(!cls.isEmpty());		
			
		
	if(isVisible) {
		CommonUtils.explicitWait(deleteStep, "Click", "", approvalDriver);
		executor.executeScript("arguments[0].click();", deleteStep);
		CommonUtils.explicitWait(yes2, "Click", "", approvalDriver);
		executor.executeScript("arguments[0].click();", yes2);
		CommonUtils.hold(5);
		
	}
	CommonUtils.waitForPageLoad(approvalDriver);

	
	
		}



	public void createRule(WebDriver driver ,List<String> header, List<String> attributes, List<String> operator, List<String> values, List<String> operations, int rowid) throws Exception {
		waitForPageLoad(driver);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		Dictionary edu = new Hashtable();
		edu.put("lesserthanorequaltosign", "\u2264");
		edu.put("greaterthanorequaltosign", "\u2265");
		edu.put("greaterthansign", ">");
		edu.put("equaltosign", "=");
		edu.put("notequaltosign", "\u2260");
		edu.put("lesserthansign", "<");
		Dictionary arithmetic = new Hashtable();
		arithmetic.put("additionmark", 1); 
		arithmetic.put("substractionmark", 2);
		arithmetic.put("multiplicationmark", 3);
		arithmetic.put("divisionmark", 4);
		try
		{
			CommonUtils.hold(5);
			addNewRule.click();
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
		   // driver.findElement(By.xpath("//span[text() ='"+header.get(i)+"']/../../following-sibling::ul//span[(text() ='"+attributes.get(i)+"')]")).click();
		    driver.findElement(By.xpath("//span[text() ='"+header.get(i)+"']//ancestor::li//ul//span[(text() ='"+attributes.get(i)+"')]")).click();
		    CommonUtils.explicitWait(opSelection, "Click", "", driver);
		    opSelection.click();
		    if(operator.get(i).contains("sign")) 
		    {
		    	CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[contains(@aria-labelledby,'operator_conditionEditor_row')]//descendant::span[text()='"+edu.get(operator.get(i))+"']")), "Click", "", driver);
		    	driver.findElement(By.xpath("//ul[contains(@aria-labelledby,'operator_conditionEditor_row')]//descendant::span[text()='"+edu.get(operator.get(i))+"']")).click();
		    }
		    else
		    {
		    	//driver.findElement(By.xpath("//div[@class='oj-listview-cell-element']//span[text()='"+operator.get(i)+"']")).click();
		    	driver.findElement(By.xpath("//ul[contains(@aria-labelledby,'operator_conditionEditor_row')]//descendant::span[text()='"+operator.get(i)+"']")).click();
		    	
		    }
		
		    if (operations.get(i).contains("click"))
		    {	
		    CommonUtils.explicitWait(typeofValue, "Click", "", driver);
			CommonUtils.hold(4);	
			typeofValue.click();	
			CommonUtils.explicitWait(typeofValueAttribute, "Click", "", driver);
			CommonUtils.hold(4);	
			typeofValueAttribute.click();	
		    CommonUtils.explicitWait(value2, "Click", "", driver);
			CommonUtils.hold(4);	
			value2.click();	
			CommonUtils.hold(4);	
			driver.findElement(By.xpath("//div[text()='"+values.get(i)+"']")).click();
			try {
			if(values.get(i+2)!=null)
			{
				/*
				 * block to create rules based on arithmetic operations ex : invoice amount > line amount * 100
				 */
				CommonUtils.explicitWait(opSelection2, "Visible", "", driver);
				opSelection2.click();
				CommonUtils.hold(1);
				driver.findElement(By.xpath("//ul[contains(@aria-labelledby,'arithmetic_operator_conditionEditor_row_r')]//li["+arithmetic.get(values.get(i+1))+"]")).click();
				CommonUtils.hold(2);
				arithmeticValue.click();
				CommonUtils.hold(1);
				arithmeticValue.sendKeys(values.get(i+2));
				arithmeticValue.sendKeys(Keys.ENTER);
			}
					
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
						driver.findElement(By.xpath("//input[@class='oj-combobox-input']")).sendKeys(options[j]);
						driver.findElement(By.xpath("//input[@class='oj-combobox-input']")).sendKeys(Keys.TAB);
						CommonUtils.hold(2);						
						}
						
					}
					else if (attributes.get(i).contains("Requester")) {
						for (int j=0; j< size1; j++)
							{
							CommonUtils.hold(2);
							value3.click();
							CommonUtils.hold(4);
							value3input.sendKeys(options[j]);
							CommonUtils.hold(4);
							//CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[@class='oj-listbox-results']//li//div//div//span//oj-highlight-text//span[contains(text(),'"+options[j]+"')]")), "Click", "", driver);
							//driver.findElement(By.xpath("//ul[@class='oj-listbox-results']//li//div//div//span//oj-highlight-text//span[contains(text(),'"+options[j]+"')]")).click();
							CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+options[j]+"']")), "Click", "", driver);
							driver.findElement(By.xpath("//span[text()='"+options[j]+"']")).click();
													
							Actions act1 = new Actions(driver);
							act1.moveToElement(value3).doubleClick();
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
		    			//act1.moveToElement(value2alt).click().build().perform();
		    			value2alt.click();
		    			CommonUtils.hold(4);
		    			value2input.click();
				    	act1.moveToElement(value2input).sendKeys(values.get(i)).build().perform();
				    	CommonUtils.hold(10);
				    	CommonUtils.explicitWait(value2alttable, "Click", "", driver);
				    	CommonUtils.explicitWait((driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-element')]//li//div//div//span[(text() ='"+values.get(i)+"')]"))), "Visible", "", driver);
				    	CommonUtils.explicitWait((driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-element')]//li//div//div//span[(text() ='"+values.get(i)+"')]"))), "Click", "", driver);
				    	act1.moveToElement(driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-element')]//li//div//div//span[(text() ='"+values.get(i)+"')]"))).doubleClick().build().perform();
				    	
				      }
		    		else
		    		  {
		    			try {
							
							driver.findElement(By.xpath("//input[contains(@id,'value_1_string_conditionEditor_row_r')]")).sendKeys(values.get(i));
							driver.findElement(By.xpath("//input[contains(@id,'value_1_string_conditionEditor_row_r')]")).sendKeys(Keys.TAB);
						}
						catch (Exception e) {

							WebElement arrow = driver.findElement(By.xpath("//oj-select-single[contains(@id,'value_1_lov_conditionEditor_row_r')]//a"));
							WebElement value = driver.findElement(By.xpath("//input[contains(@id,'searchselect-filter-value_1_lov_conditionEditor_row')]"));
							arrow.click();									
							value.sendKeys(values.get(i));
							driver.findElement(By.xpath("//*[text()='"+values.get(i)+"']")).click();
							
							
							
						}
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
					min=min.replace("range", "");
					driver.findElement(By.xpath("//input[contains(@name,'checkbox_1_conditionEditor_row_r')]")).click();
				}
				value2.click();	
				value2.sendKeys(min);
				value2.sendKeys(Keys.TAB);
						
				if (max.contains("range")) {
					max=max.replace("range", "");
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
		CommonUtils.waitForPageLoad(driver);
	
}
	
		
	public void autoapprove(WebDriver driver, String option) throws Exception {
	System.out.println("entered here");
	JavascriptExecutor executor = (JavascriptExecutor)driver;
//	CommonUtils.explicitWait(addApprover, "Click", "", driver);
//	addApprover.click();
//	CommonUtils.explicitWait(actionForApprove, "Click", "", driver);
//	CommonUtils.hold(2);
//	executor.executeScript("arguments[0].click();", actionForApprove);
//	//actionForApprove.click();
	
	Actions act=new Actions(driver);
	CommonUtils.explicitWait(addApprover, "Click", "", driver);
	addApprover.click();
	CommonUtils.explicitWait(actionForApprove, "Click", "", driver);
	//CommonUtils.hold(2);
	act.moveToElement(actionForApprove).click().build().perform();
	
	CommonUtils.explicitWait(autoApprove, "Click", "", driver);
	CommonUtils.hold(2);
	driver.findElement(By.xpath("//span[text() = '"+option+"']")).click();
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
		CommonUtils.waitForPageLoad(driver);
		Actions act=new Actions(driver);
		try {
			CommonUtils.explicitWait(addApprover, "Click", "", driver);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",addApprover);

		}
		catch(Exception e)
		{
			
		}
		addApprover.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(actionForApprove, "Click", "", driver);
		CommonUtils.hold(2);
		act.moveToElement(actionForApprove).click().build().perform();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(sendForApproval, "Click", "", driver);
		CommonUtils.hold(2);
		if (userOrPolicy.toLowerCase().contains("fyi")) {
		driver.findElement(By.xpath("//span[(text()= 'Send FYI Notification')]")).click();
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
				CommonUtils.hold(2);
			}
			else if(userOrPolicy.toLowerCase().contains("role"))
			{
				CommonUtils.explicitWait(roleValue, "Click", "", driver);
				CommonUtils.hold(2);
				roleValue.click();
				CommonUtils.hold(2);
			}
			else {
		        CommonUtils.explicitWait(userValue, "Click", "", driver);
		        CommonUtils.hold(2);
		        userValue.click();
		        CommonUtils.hold(2);
			}
		for(int k=0; k<ApproverInfo.size(); k++)
		{
			System.out.println(k);
			System.out.println(ApproverInfo.size());
			if(userOrPolicy.toLowerCase().contains("attributes"))
			{
				CommonUtils.explicitWait(attributeValue, "Click", "", driver);
				attributeValue.click();
			}
			else if(userOrPolicy.toLowerCase().contains("role"))
			{
				CommonUtils.explicitWait(Roles, "Click", "", driver);
			    Roles.click();CommonUtils.hold(5);
			    CommonUtils.explicitWait(UsersRolesInput, "Click", "", driver);
			    UsersRolesInput.sendKeys(ApproverInfo.get(k));
			}
			else {
			    CommonUtils.explicitWait(Users, "Click", "", driver);
			    Users.click();CommonUtils.hold(2);
			    UsersRolesInput.sendKeys(ApproverInfo.get(k));
			}
				if(userOrPolicy.toLowerCase().contains("user") || userOrPolicy.toLowerCase().contains("fyi"))
				{
					//CommonUtils.explicitWait(driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")), "Click", "", driver);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//li[@class = 'oj-listbox-results-depth-0 oj-listbox-result oj-listbox-result-selectable']")), "Click", "", driver);
					CommonUtils.hold(5);
					
					CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[text() = '"+ApproverInfo.get(k)+"']"), 20, driver);
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//span[text() = '"+ApproverInfo.get(k)+"']")).click();
					CommonUtils.hold(2);
									
				}
				if(userOrPolicy.toLowerCase().contains("role"))
				{
					CommonUtils.explicitWait(driver.findElement(By.xpath("//ul//li//descendant::span[contains(text(),'"+ApproverInfo.get(k)+"')]")), "Click", "", driver);
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//ul//li//descendant::span[contains(text(),'"+ApproverInfo.get(k)+"')]")).click();
					CommonUtils.hold(2);
				}
				if(userOrPolicy.toLowerCase().contains("attributes"))
				{
					/*CommonUtils.explicitWait(driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")), "Click", "", driver);
					driver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'"+ApproverInfo.get(k)+"')]")).click();*/
					CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//div[text()='"+ApproverInfo.get(k)+"']"), 30, driver);
					CommonUtils.hold(2);
					driver.findElement(By.xpath("//div[text()='"+ApproverInfo.get(k)+"']")).click();
					CommonUtils.hold(2);
				}
		}}
				
		if(userOrPolicy.toLowerCase().contains("policy"))
		{
			CommonUtils.explicitWait(policyValue, "Click", "", driver);
			policyValue.click();
			CommonUtils.explicitWait(policyField, "Click", "", driver);
			act.moveToElement(policyField).click().build().perform();
			CommonUtils.hold(2);
			policyFieldinput.click();
			CommonUtils.hold(2);
			policyFieldinput.sendKeys(ApproverInfo.get(0));
			CommonUtils.explicitWait(policyFieldTable, "Click", "", driver);
			//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[text()='"+ApproverInfo.get(0)+"']")));
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+ApproverInfo.get(0)+"']")), "Click", "", driver);
			driver.findElement(By.xpath("//span[text()='"+ApproverInfo.get(0)+"']")).click();
		}
		CommonUtils.explicitWait(close, "Click", "", driver);
		close.click();
		CommonUtils.hold(5);
		System.out.println("exiting set action");
		CommonUtils.waitForPageLoad(driver);

	}
	
	public void DeployRule(WebDriver driver) throws Exception {
		CommonUtils.waitForPageLoad(driver);
		try {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",saveRule);
		}
		catch(Exception e)
		{
			
		}
		
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		saveRule.click();
		CommonUtils.explicitWait(save, "Click", "", driver);
		Actions action = new Actions(driver);
		action.moveToElement(save).click().build().perform();
		CommonUtils.hold(5);
		WebDriverWait wait = new WebDriverWait(driver, 45);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Saved']")));
		CommonUtils.waitForPageLoad(driver);
		System.out.println("class is ");
		CommonUtils.hold(5);
			String cls= deploy.getAttribute("class");
			System.out.println("deploy button class is "+cls);
			boolean isVisible=cls.contains("disabled");		
			if(!isVisible)
			{
				deploy.click();
				CommonUtils.explicitWait(ok, "Click", "", driver);
				executor.executeScript("arguments[0].click();", ok);
			    CommonUtils.explicitWait(deployComplete, "Click", "", driver);
			    

			boolean isEnabled=deployComplete.isDisplayed();
			if(isEnabled)
			{
				Log.info("Rule Deploy is successful");
				deployComplete.click();
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
			CommonUtils.waitForPageLoad(driver);		
		}

	public void EditStepsAndNavigateToRulePage(WebDriver approvalDriver) throws Exception {
		CommonUtils.waitForPageLoad(approvalDriver);
        CommonUtils.explicitWait(stepName2, "Click", "", approvalDriver);
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		executor.executeScript("arguments[0].click();", stepName2);
		CommonUtils.explicitWait(action, "Click", "", approvalDriver);
		executor.executeScript("arguments[0].click();", action);
		CommonUtils.explicitWait(editStep, "Click", "", approvalDriver);
		executor.executeScript("arguments[0].click();", editStep);
		CommonUtils.waitForPageLoad(approvalDriver);
     
		
	}


	public void createSupervisorRoutingPolicy(WebDriver driver,List<String> Policyname ,String[] SHconditions, String[] SHattributes, String []Values, String noRuleMatchAction ,String isError) throws Exception
    {
        Log.info(" creating policy "+Policyname.get(0));
        Reporter.log(" creating policy "+Policyname.get(0));
        CommonUtils.waitForPageLoad(driver);
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
 
        CommonUtils.explicitWait(menu, "Click", "", driver);
        menu.click();
        CommonUtils.hold(4);
        CommonUtils.explicitWait(createRP, "Click", "", driver);
        createRP.click();
        CommonUtils.explicitWait(supervisorHierarchy, "Click", "", driver);
        act.moveToElement(supervisorHierarchy).click().build().perform();
 
        CommonUtils.waitForPageLoad(driver);
        CommonUtils.waitForJStoLoad(driver);
        //CommonUtils.waitforElementtoClick(15, sequence, driver);
		CommonUtils.hold(2);
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
        	CommonUtils.explicitWait(stop, "Click", "", driver);
            stop.click();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[contains(text(),'"+SHconditions[1]+"')]")));
            act.moveToElement(driver.findElement(By.xpath("//span[text()='"+SHconditions[1]+"']"))).click().build().perform();
            supervisorHierarchyLabel.click();
            CommonUtils.explicitWait(numberOfLevel, "Click", "", driver);
            numberOfLevel.sendKeys(SHconditions[2]);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",limit);
            CommonUtils.explicitWait(limit, "Click", "", driver);
            limit.click();
            CommonUtils.hold(5);
            limitinput.sendKeys(SHconditions[3]);
            CommonUtils.hold(4);
            CommonUtils.explicitWait(driver.findElement(By.xpath("//span[(text() ='"+SHconditions[3]+"')]")), "Click", "", driver);
            driver.findElement(By.xpath("//span[(text() ='"+SHconditions[3]+"')]")).click();
            CommonUtils.hold(2);
            //supervisorHierarchyLabel.click();
        }
 
        else if(SHconditions[1].contains("When approver has sufficient approval authority"))
        {
            CommonUtils.explicitWait(stop, "Click", "", driver);
            CommonUtils.hold(4);
            stop.click();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[contains(text(),'"+SHconditions[1]+"')]")));
            CommonUtils.hold(2);
            act.moveToElement(driver.findElement(By.xpath("//span[text()='"+SHconditions[1]+"']"))).click().build().perform();
            CommonUtils.hold(2);
            CommonUtils.waitForPageLoad(driver);
            CommonUtils.waitForJStoLoad(driver);
            //CommonUtils.waitforElementtoClick(15, sequence, driver);
    		CommonUtils.hold(2);
            supervisorHierarchyLabel.click();
            CommonUtils.hold(2);
            CommonUtils.explicitWait(limitsBasedOn, "Click", "", driver);
            limitsBasedOn.click();
            CommonUtils.hold(2);
            System.out.println("Limit based on is "+SHconditions[3]);
            if(SHconditions[2].contains("Sum of"))
            {
                String SHsumconditions = SHconditions[2].substring(7);
                CommonUtils.hold(2);            
                act.moveToElement(driver.findElement(By.xpath("//div[@id='lovDropdown_limitsBasedOn']//span[text()='"+SHsumconditions+"']"))).click().build().perform();
                CommonUtils.hold(4);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",sumswitch);
                CommonUtils.hold(4);
                act.moveToElement(sumswitch).click().build().perform();
                CommonUtils.hold(2);
            }
            else
            {
            act.moveToElement(driver.findElement(By.xpath("//div[@id='lovDropdown_limitsBasedOn']//span[text()='"+SHconditions[2]+"']"))).click().build().perform();
            CommonUtils.hold(2);
            }
            limitCurrency.click();
            CommonUtils.hold(2);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-limitCurrency']//ul//li//child::span[text()='"+SHconditions[3]+"']")));
            driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-limitCurrency']//ul//li//child::span[text()='"+SHconditions[3]+"']"));
            CommonUtils.hold(2);
            driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-limitCurrency']//ul//li//child::span[text()='"+SHconditions[3]+"']")).click();
            CommonUtils.hold(2);
 
            /*/*******************************************************************Adding Attributes****************************************************************************/
 
            for (int i=0;i<SHattributes.length;i++)
            {
                CommonUtils.explicitWait(addAttribute, "Click", "", driver);
                CommonUtils.hold(2);
                addAttribute.click();
                CommonUtils.hold(2);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//oj-option[text()='"+SHattributes[i]+"']")));
                driver.findElement(By.xpath("//oj-option[text()='"+SHattributes[i]+"']")).click();
                CommonUtils.hold(2);
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
            int i1 = 0;
            int len2 = SHattributes.length+4;
 
            if(i>0)
            {
 
            	WebElement tb=driver.findElement(By.xpath("//tbody[@class='oj-table-body']"));
                WebElement th;
                th=driver.findElement(By.xpath("//th[contains(@abbr,'Approval Limit')]//following-sibling::th"));
                try {
                //tb.click();
                CommonUtils.hold(2);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
                CommonUtils.hold(2);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
                /*i1 = 0;
                while (i1 < 6)
                {
                    tb.sendKeys(Keys.LEFT);
                    ++i1;
                } */ 
                CommonUtils.hold(2);
                System.out.println(" after scroll ");
                }
                catch(Exception e)
                {
                    System.out.println(" scroll bar not displayed");
                    Reporter.log(" scroll bar not displayed");
                }
                Log.info(" Before Clicking on Add icon");
				Reporter.log("Before Clicking on Add icon");
            	CommonUtils.hold(2);            
                //CommonUtils.explicitWait(driver.findElement(By.xpath("//tr["+i+"]//a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);
                //CommonUtils.hold(5);
                //act.moveToElement(driver.findElement(By.xpath("//tr["+i+"]//a[@class='add-icon' and @title='Add Row']"))).click().build().perform();
                //CommonUtils.hold(5);
				act.moveToElement(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+len2+"]"))).build().perform();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);
				act.moveToElement(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']"))).click().build().perform();
                Log.info(" After Clicking on Add icon");
				Reporter.log("After Clicking on Add icon");
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
                CommonUtils.hold(4);
                //commenting out explicitWait
                //CommonUtils.explicitWait(SHoperator, "Click", "", driver);
                Act1.moveToElement(SHoperator).click().build().perform();;
 
                if(SHop.contains("sign"))
                {
                    CommonUtils.hold(2);
                    //CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[@id='oj-listbox-results-operator_conditionEditor_authority-row-editor']//li//div[text()='"+edu.get(SHop)+"']")), "Click", "", driver);
                    //driver.findElement(By.xpath("//ul[@id='oj-listbox-results-operator_conditionEditor_authority-row-editor']//li//div[text()='"+edu.get(SHop)+"']")).click();
                    CommonUtils.explicitWait(driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-element')]//descendant::span[text()='"+edu.get(SHop)+"']")), "Click", "", driver);
                    driver.findElement(By.xpath("//ul[contains(@class,'oj-listview-element')]//descendant::span[text()='"+edu.get(SHop)+"']")).click();
 
                }
                else if(SHop.contains("between"))
                {
                    String SHop2=SHop.replace("inclusive","");
                    CommonUtils.hold(2);
                    CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+SHop2+"']")), "Click", "", driver);
                    driver.findElement(By.xpath("//span[text()='"+SHop2+"']")).click();
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
                        //CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+SHop.replace("inclusive", "")+"']")), "Click", "", driver);
                        //driver.findElement(By.xpath("//*[text()='"+SHop.replace("inclusive", "")+"']")).click();
                        //CommonUtils.hold(2);
                }
 
                else
                {
                    CommonUtils.hold(2);
                    CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+SHop+"']")), "Click", "", driver);
                    driver.findElement(By.xpath("//span[text()='"+SHop+"']")).click();
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
                        valuesection.sendKeys(Keys.TAB);
                        CommonUtils.hold(4);
                        //CommonUtils.explicitWait(valuesection, "Click", "", driver);
                        valuesection.click();
                        //CommonUtils.explicitWait(valuesection, "Click", "", driver);
                        //CommonUtils.hold(4);                        
                    }
                }
                else if(SHop.contains("is between") || SHop.contains("is not between"))
                {
                    String SHvalue []= SHvalues[k-1].split("isbetween");
                    value1.sendKeys(SHvalue[0]);
                    value1_1.sendKeys(SHvalue[1].replace("isbetween", ""));
                }
 
 
                else {
                valuesection.click();
                CommonUtils.hold(2);
                valuesection.sendKeys(SHvalues[k-1]);
                CommonUtils.hold(3);
                valuesection.click();
                }
                }
                tick.click();
                CommonUtils.hold(2);
                }
            }
 
            /*/*******************************************************************Approver (with usertype and username)********************************************************/
 
            /*int pk=i+1;
            WebElement seq=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+pk+"]//td[1]"));
            act.moveToElement(seq).click().build().perform();*/
            //((JavascriptExecutor) driver).executeScript("arguments[0].click();",seq);
            /*WebElement seq=driver.findElement(By.xpath("//th[@title='Approver']"));
            //act.moveToElement(seq).click().build().perform();
            seq.click();*/
            //CommonUtils.hold(4);
            CommonUtils.hold(2);
            WebElement th;
            WebElement tb=driver.findElement(By.xpath("//tbody[@class='oj-table-body']"));
            th=driver.findElement(By.xpath("//th[contains(@abbr,'Approval Limit')]//following-sibling::th"));
            try {
            //tb.click();CommonUtils.hold(2);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
            CommonUtils.hold(2);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
             i1 = 0;
            /*while (i1 < 6)
            {
                tb.sendKeys(Keys.RIGHT);
                ++i1;
            }*/  
            CommonUtils.hold(2);
            System.out.println(" after scroll ");
            }
            catch(Exception e)
            {
                System.out.println(" scroll bar not displayed");
                Reporter.log(" scroll bar not displayed");
            }
            
            //int SHattribute2= SHattributes.length+1;
            /*//searchAuthority.click();
            int op=i+1;
            WebElement el22=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+op+"]//td[1]"));
			//act.moveToElement(el22).click().build().perform();
			el22.click();
            CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+SHattribute2+"']")), "Visible", "", driver);
            //CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+SHattribute2+"']")), "ElementVisible", "", driver);
            //CommonUtils.explicitWait(driver.findElement(By.xpath("//*[@id='approver_label_"+i+"_"+SHattribute2+"']")), "Click", "", driver);
            driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+SHattribute2+"']")).click();
            CommonUtils.hold(5);*/
            //int len1=SHattributes.length+1;
            int rowIndex = i+1;
            try {
			//	CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"), 15, driver);
                CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]"), 15, driver);
 
            }catch(Exception approverDivExcep) {
                System.out.println("Approver div not loaded, retrying again");
                approverDivExcep.printStackTrace();
                CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]"), 30, driver);
				//CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"), 15, driver);

            }
                CommonUtils.hold(2);
                driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']")).click();
                CommonUtils.hold(2);
 
                try {
                    //tb.click();CommonUtils.hold(2);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
                    CommonUtils.hold(2);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
                     /*i1 = 0;
                    while (i1 < 6)
                    {
                        tb.sendKeys(Keys.RIGHT);
                        ++i1;
                    } */ 
                    CommonUtils.hold(2);
                    System.out.println(" after scroll ");
                    }
                    catch(Exception e)
                    {
                        System.out.println(" scroll bar not displayed");
                        Reporter.log(" scroll bar not displayed");
                    }
            
            /*Reporter.log("Before clicking sequence column");
            act.moveToElement(driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+rowIndex+"]//td[1]"))).click();
            Reporter.log("After clicking sequence column");*/
            //act.moveToElement(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"))).build().perform();
            driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]")).click();
            CommonUtils.hold(5);
            /*CommonUtils.hold(5);
            int pk=i+1;
            WebElement seq=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+pk+"]//td[1]"));
            act.moveToElement(seq).click().build().perform();
            CommonUtils.hold(5);
            driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]")).click();
            //act.moveToElement(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"))).click().build().perform();
            CommonUtils.hold(5);*/
 
            if(SHUsers.contains("User"))
            {
                String [] SHapproverUser = SHUsers.split("-");
                //String SHapproverUserType= SHapproverUser[0];
                String SHapproverUserInput= SHapproverUser[1];
                try {
                    //CommonUtils.waitforElementtoClick(15, userinput, driver);
                    CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//input[@id='approver_target_userauthority-row-editor|input']"), 15, driver);
                }catch(Exception approverLOVExcepSH) {
                    System.out.println("Approver LOV not visible, retrying again");
                    approverLOVExcepSH.printStackTrace();
                    driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]")).click();
                    CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//input[@id='approver_target_userauthority-row-editor|input']"), 30, driver);
                }
                //CommonUtils.explicitWait(SHuser, "Click", "", driver);
                //SHuser.sendKeys(Keys.TAB);
                CommonUtils.hold(2);
                SHuser.click();
                System.out.println("After User dp click");
                //act.moveToElement(SHuser).click().build().perform();
                //((JavascriptExecutor) driver).executeScript("arguments[0].click();",SHuser);
                //CommonUtils.explicitWait(SHuser, "Click", "", driver);
                CommonUtils.hold(5);
                //System.out.println("Before ESCAPE");
                //act.sendKeys(Keys.ESCAPE).perform();
                //SHuser.sendKeys(Keys.ESCAPE);
                
                
                //act.moveToElement(SHuser).sendKeys(SHapproverUserInput).build().perform();
                //SHuser.click();
                //CommonUtils.hold(2);
                //SHuser.sendKeys(SHapproverUserInput);
             
                /*CommonUtils.explicitWait(driver.findElement(By.xpath("//oj-select-single[@id='approver_target_userauthority-row-editor']/div["+rowIndex1+"]/span/a")), "Click", "", driver);
                driver.findElement(By.xpath("//oj-select-single[@id='approver_target_userauthority-row-editor']/div["+rowIndex1+"]/span/a")).click();
                CommonUtils.hold(5);*/
                //act.moveToElement(SHuser).sendKeys(Keys.BACK_SPACE);
                //((JavascriptExecutor) driver).executeScript("arguments[0].value='"+SHapproverUserInput+"';", SHuser);
                CommonUtils.hold(2);
                System.out.println("Before sending keys to user");
                act.moveToElement(SHuser).sendKeys(SHapproverUserInput).build().perform();
                System.out.println("After sending keys to user");
                //((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value','"+SHapproverUserInput+"')", SHuser);
                //SHuser.sendKeys(SHapproverUserInput);
                CommonUtils.hold(8);
                
                /*try 
                {
                    CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//input[@id='approver_target_userauthority-row-editor|input']"), 15,driver);
                }
                catch(Exception userVisiblityException) {
                    System.out.println("User popup disappeared, reopening to pass the user again");
                    userVisiblityException.printStackTrace();
                    driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+rowIndex+"]//td[1]")).click();
                    CommonUtils.hold(2);
                    CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+SHattribute2+"']")), "Visible", "", driver);
                    driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+SHattribute2+"']")).click();
                    CommonUtils.hold(5);
                    SHuser.click();
                    CommonUtils.hold(5);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value='"+SHapproverUserInput+"';", SHuser);
                    act.moveToElement(SHuser).sendKeys(SHapproverUserInput).build().perform();
                    CommonUtils.hold(8);
                }*/
 
                try 
                {
                    CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']"), 30,driver);
                }
                catch(Exception userVisiblityException) {
                    System.out.println("User not visible within 30secs, rescanning with 60secs timeout");
                    userVisiblityException.printStackTrace();
                    act.moveToElement(tick2).click().build().perform();
                    CommonUtils.hold(5);
                    SHuser.click();
                    CommonUtils.hold(2);
                    int len11=SHapproverUserInput.length();
                    while(len11!=0)
                    {
 
                          act.moveToElement(userinput).sendKeys(Keys.BACK_SPACE).build().perform();
                          len11--;
                    }
                    //SHuser.clear();
                    CommonUtils.hold(2);
                    act.moveToElement(SHuser).sendKeys(SHapproverUserInput).build().perform();
                    CommonUtils.hold(2);
                    try 
                    {
                        CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']"), 60,driver);
                    }
                    catch(Exception e) 
                    {
                            System.out.println("User not visible within 30secs");
                            Log.info("User not visible within 30secs");
                    }
                    //CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']"), 60,driver);
                }
 
                CommonUtils.hold(2);
                WebElement selectedUser = driver.findElement(By.xpath("//input[@id = 'approver_target_userauthority-row-editor|input']"));
                //act.moveToElement(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']"))).doubleClick().build().perform();
                //CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")), "ElementVisible", "", driver);
                CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")), "Visible", "", driver);
                //CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")), "Click", "", driver);
                driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")).click();
                //CommonUtils.hold(2);
                //driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+SHapproverUserInput+"']")).click();
                CommonUtils.hold(5);
 
                System.out.println("Selected User value: "+selectedUser.getAttribute("value"));
 
                if(selectedUser.getAttribute("value").equalsIgnoreCase(SHapproverUserInput)) {
                    act.moveToElement(tick2).click().build().perform();
                    CommonUtils.hold(5);
                }
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
            try {
				//tb.click();CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
				CommonUtils.hold(2);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
		         /*i1 = 0;
		        while (i1 < 6)
		        {
		            tb.sendKeys(Keys.RIGHT);
		            ++i1;
		        } */ 
		        CommonUtils.hold(2);
		        System.out.println(" after scroll ");
				}
				catch(Exception e)
				{
					System.out.println(" scroll bar not displayed");
					Reporter.log(" scroll bar not displayed");
				}
            
            CommonUtils.explicitWait(approvalLimit, "Click", "", driver);
            CommonUtils.hold(4);
            CommonUtils.explicitWait(approvalLimit, "Click", "", driver);
            act.moveToElement(approvalLimit).click().build().perform();
            CommonUtils.hold(4);
            
            
            int s = SHattributes.length+4;
            int o = i+1;
            System.out.println(" s is "+s+" o is "+o);
            int k = s-2;
            int gh = k+1;
            if(SHlimitlist.contains("unlimited"))
            {
                //policyName.click();
                //WebElement el2=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+o+"]//td[1]"));
                //act.moveToElement(el2).click().build().perform();
                //CommonUtils.hold(4);
                //WebElement el=driver.findElement(By.xpath("(//tr["+o+"]//td//div[contains(@class,'readonlyContent oj-flex-item')])["+k+"]"));
                //WebElement el=driver.findElement(By.xpath("//tr["+o+"]//td["+gh+"]//div[contains(@class,'oj-flex-item')]"));
                WebElement el=driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+gh+"]//*[contains(@class,'oj-flex-item')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",el);
                CommonUtils.hold(5);
                executor.executeScript("arguments[0].click();", el);
                /*CommonUtils.explicitWait(approvalLimitinput, "Click", "", driver);
                CommonUtils.hold(2);
                approvalLimitinput.click();*/
                //act.moveToElement(driver.findElement(By.xpath("(//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@class,'inlineLimitCellContent limit invalid')]//div)[1]"))).click().build().perform();
                act.moveToElement(driver.findElement(By.xpath("(//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@class,'inlineLimitCellContent limit invalid')]//div)["+o+"]"))).click().build().perform();
              //  act.moveToElement(driver.findElement(By.xpath("(//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@class,'inlineLimitCellContent limit invalid')]//div)["+o+"]"))).doubleClick().build().perform();
                CommonUtils.hold(2);
                CommonUtils.explicitWait(unlimitedLimit, "Click", "", driver);
                CommonUtils.hold(2);
                act.moveToElement(unlimitedLimit).click().build().perform();
                CommonUtils.hold(2);
                //act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
                act.moveToElement(driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+s+"]"))).build().perform();
                CommonUtils.hold(2);
            }
            else
            {
                //policyName.click();
                //WebElement el2=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+o+"]//td[1]"));
                //act.moveToElement(el2).click().build().perform();
                //CommonUtils.hold(4);
                //WebElement el=driver.findElement(By.xpath("(//tr["+o+"]//td//div[contains(@class,'readonlyContent oj-flex-item')])["+k+"]"));
                //WebElement el=driver.findElement(By.xpath("//tr["+o+"]//td["+gh+"]//div[contains(@class,'oj-flex-item')]"));
                WebElement el=driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+gh+"]//*[contains(@class,'oj-flex-item')]"));
                //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",el);
                CommonUtils.hold(4);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",el);
                CommonUtils.hold(4);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",approvalLimitinput);
                CommonUtils.hold(4);
                /*act.moveToElement(el).click();
                CommonUtils.hold(4);
                act.moveToElement(approvalLimitinput).click();
                CommonUtils.hold(4);*/               
            approvalLimitinput.sendKeys(SHlimitlist);
            CommonUtils.hold(2);
            approvalLimitinput.sendKeys(Keys.TAB);
            CommonUtils.hold(4);
            //act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
            act.moveToElement(driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+s+"]"))).build().perform();
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
            //driver.findElement(By.xpath("//ul[@id='oj-listbox-results-rpPopupNoMatch']//descendant::oj-option[text()='"+noRuleMatchAction+"']")).click();
            driver.findElement(By.xpath("//span[text()='"+noRuleMatchAction+"']")).click();
        }
 
 
        CommonUtils.explicitWait(savePolicy, "Click", "", driver);
        CommonUtils.hold(5);
        act.moveToElement(savePolicy).click().build().perform();
        CommonUtils.explicitWait(saveandClose, "Click", "", driver);
        CommonUtils.hold(3);
        act.moveToElement(saveandClose).click().build().perform();
        CommonUtils.hold(10);
 
        if(isError.contains("ErrorinRP"))
        {
            try {
            boolean iselementpresent= unableToSave.isDisplayed();
            if(iselementpresent)
            {
                Reporter.log("Error popup appear while saving policy with L2 value");
                CommonUtils.hold(4);
                errorClose.click();
                CommonUtils.hold(4);
                discardChanges.click();
                CommonUtils.hold(4);
                closePolicy.click();
                CommonUtils.hold(4);
                return;
            }
            }
            catch(Exception e)
            {
                Reporter.log("Error popup does not appear while saving policy with L2 value");
                assertTrue(false,"Error popup does not appear while saving policy with L2 value");
            }
 
        }
 
        CommonUtils.explicitWait(NameSortRP, "Click", "", driver);
        CommonUtils.hold(4);
        act.contextClick(NameSortRP).perform();
        CommonUtils.hold(4);
        act.moveToElement(SortButton).build().perform();
        CommonUtils.hold(4);
        act.moveToElement(SortDescending).build().perform();
        CommonUtils.explicitWait(SortDescending, "Visible", "", driver);
        CommonUtils.explicitWait(SortDescending, "Click", "", driver);
        act.moveToElement(SortDescending).perform();
        SortDescending.click();
        CommonUtils.hold(10);
 
        try
        {
            boolean ispresent=driver.findElement(By.xpath("//div[text()='"+Policyname.get(0)+"']")).isDisplayed();
            if(ispresent)
            {
                Log.info("Routing policy created");
 
            }
        }
        catch(Exception e)
        {
 
            Log.info("Routing policy not created");
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
	CommonUtils.waitForPageLoad(driver);
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
		System.out.println(" approver group is "+Policyname.get(0));
		Reporter.log(" approver group is "+Policyname.get(0));
		policyName.sendKeys(Policyname.get(0));
		CommonUtils.hold(4);
		notify.click();
		driver.findElement(By.xpath("//span[text()='"+Notifytype+"']")).click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(EnterName,"Click", "", driver);
		EnterName.click();
		CommonUtils.hold(4);
		act.doubleClick(EnterName).perform();
		CommonUtils.hold(4);
	
		int numberOfUsers=users.size();		
		if(numberOfUsers>1)
		{
				
			int k=1 ;	
			for(int i=0;i<numberOfUsers;i++)
			{
				if(i>=1) {
				
				if(i==1)
				CommonUtils.hold(4);
				act.moveToElement(EnterName).build().perform();
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+k+"']//ancestor::tr//td[4]//a[@title='Add Member']")), "Visible", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+k+"']//ancestor::tr//td[4]//a[@title='Add Member']")), "Click", "", driver);
				act.moveToElement(driver.findElement(By.xpath("//div[@title='"+k+"']//ancestor::tr//td[4]//a[@title='Add Member']"))).click().build().perform();
				k++;
				}
				CommonUtils.hold(5);
				CommonUtils.explicitWait(EnterName2, "Click", "", driver);
				CommonUtils.hold(2);
				EnterName2.click();
				CommonUtils.hold(3);
				/*CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='lovDropdown_members']")), "Click", "", driver);
				CommonUtils.hold(2);*/
				EnterName3.sendKeys(users.get(i));
				CommonUtils.hold(5);
				/*CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")), "Visible", "", driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")), "Click", "", driver);*/
				CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//div[@title='"+users.get(i)+"']"), 30, driver);
				CommonUtils.hold(3);
				//act.moveToElement(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).click().build().perform();
				//act.clickAndHold(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).doubleClick().build().perform();
				act.moveToElement(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).doubleClick().build().perform();
				}
			
		}
		CommonUtils.hold(3);
		act.moveToElement(savePolicy).click().build().perform();
		CommonUtils.explicitWait(saveandClose, "Click", "", driver);
		CommonUtils.hold(5);
		act.moveToElement(saveandClose).click().build().perform();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(NameSortRP, "Click", "", driver);
		CommonUtils.hold(2);
		act.contextClick(NameSortRP).perform();
		CommonUtils.hold(2);
		act.moveToElement(SortButton).build().perform();
		CommonUtils.hold(2);
		act.moveToElement(SortDescending).build().perform();
		CommonUtils.explicitWait(SortDescending, "Visible", "", driver);
		CommonUtils.explicitWait(SortDescending, "Click", "", driver);
		act.moveToElement(SortDescending).perform();
		SortDescending.click();
		CommonUtils.hold(10);

		try
		{
			boolean ispresent=driver.findElement(By.xpath("//div[text()='"+Policyname.get(0)+"']")).isDisplayed();
			if(ispresent)
			{
				Log.info("Routing policy created");

			}
		}
		catch(Exception e)
		{
			
			Log.info("Routing policy not created");
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
			String returnApproverFrom, String[] AAattributes, String []valueList, String noRuleMatchAction, List<String> policylist) throws Exception {
		// TODO Auto-generated method stub
		CommonUtils.waitForPageLoad(driver);
		Log.info(" creating policy "+policyName.get(0));
		Reporter.log(" creating policy "+policyName.get(0));
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
		
			
			CommonUtils.waitforElementtoClick(15, menu, driver);
			CommonUtils.hold(2);
		
		act.moveToElement(menu).click().build().perform();
		
//		try
//		{
//			CommonUtils.hold(2);
//			act.moveToElement(menu2).click().build().perform();
//		}
//		catch(Exception e)
//		{
//			
//		}
		CommonUtils.waitforElementtoClick(15, createRP, driver);
		CommonUtils.hold(2);
		createRP.click();
		
		CommonUtils.waitforElementtoClick(15, approvalAuthority, driver);
		CommonUtils.hold(2);
		act.moveToElement(approvalAuthority).click().build().perform();
		//CommonUtils.hold(4);

		CommonUtils.waitForPageLoad(driver);
		CommonUtils.waitForJStoLoad(driver);
		/*try {
			CommonUtils.explicitWait(sequence, "Click", "", driver);
		} catch (Exception caarExcep4) {
			System.out.println("sequence not found : "+caarExcep4.getMessage());
			caarExcep4.printStackTrace();
		}*/
		
		CommonUtils.waitforElementtoClick(15, sequence, driver);
		CommonUtils.hold(2);
		aaName.sendKeys(policyName.get(0));
		/*try {
			CommonUtils.explicitWait(addAttribute, "Click", "", driver);
		} catch (Exception caarExcep5) {
			System.out.println("sequence not found : "+caarExcep5.getMessage());
			caarExcep5.printStackTrace();
		}*/
		
		CommonUtils.waitforElementtoClick(15, addAttribute, driver);
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
			try {
				CommonUtils.explicitWait(limitbasedOn, "Click", "", driver);
			} catch (Exception caarExcep6) {
				System.out.println("limitbasedon not found : "+caarExcep6.getMessage());
				caarExcep6.printStackTrace();
			}
			String limitdata[]=assignLimit.split("limit");
			System.out.println("Limit data is "+limitdata);
			limitbasedOn.click();
			if(limitdata[1].contains("Sum of"))
			{
				String AAsumconditions = limitdata[1].substring(7);
				CommonUtils.hold(2);			
				act.moveToElement(driver.findElement(By.xpath("//div[@id='lovDropdown_limitsBasedOn']//span[text()='"+AAsumconditions+"']"))).click().build().perform();
				CommonUtils.hold(4);
				act.moveToElement(sumswitch).click().build().perform();
				CommonUtils.hold(2);
			}
			else
			{
			act.moveToElement(driver.findElement(By.xpath("//div[@id='lovDropdown_limitsBasedOn']//span[text()='"+limitdata[1]+"']"))).click().build().perform();
			CommonUtils.hold(2);
			}
			//driver.findElement(By.xpath("//span[contains(text(),'"+limitdata[1]+"')]")).click();
			if(!limitdata[2].equals("USD"))
			{
				System.out.println("Limit based on is "+limitdata[2]);
				limitselection.click();
				CommonUtils.hold(2);
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-limitCurrency']//ul//li//child::span[text()='"+limitdata[2]+"']")));
driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-limitCurrency']//ul//li//child::span[text()='"+limitdata[2]+"']")).click();
}
		}
		for (int i=0;i<AAattributes.length;i++)
		{
			addAttribute.click();
			CommonUtils.hold(5);
//			addAttribute2.sendKeys("ABC");
//			CommonUtils.hold(2);
//			addAttributeinput.clear();
//			addAttributeinput.sendKeys(AAattributes[i]);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//oj-option[text()='"+AAattributes[i]+"']")));
			driver.findElement(By.xpath("//oj-option[text()='"+AAattributes[i]+"']")).click();
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
			String limitlist=values[3]; int i1 = 0;
			System.out.println("Values in "+i+1+" iteration "+Arrays.toString(values)+" each value "+Arrays.toString(AAopeartors)+"  "+Arrays.toString(AAvalues)+"  "+AAUsers+" "+limitlist);
			if(i>0)
			{
				
				WebElement tb=driver.findElement(By.xpath("//tbody[@class='oj-table-body']"));
				WebElement th;
				if(assignLimit.contains("yes"))
				{
				 th=driver.findElement(By.xpath("//th[contains(@abbr,'Approval Limit')]//following-sibling::th"));

				}
				else
				{
					 th=driver.findElement(By.xpath("//th[contains(@abbr,'Approver')]//following-sibling::th"));

				}
				try {
				//tb.click();CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
				CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );

//		         i1 = 0;
//		        while (i1 < 6)
//		        {
//		            tb.sendKeys(Keys.LEFT);
//		            ++i1;
//		        }  
		        CommonUtils.hold(2);
		        System.out.println(" after scroll ");
				}
				catch(Exception e)
				{
					System.out.println(" scroll bar not displayed");
					Reporter.log(" scroll bar not displayed");
				}
				
				
				
				Log.info(" Before Clicking on Add icon");
				Reporter.log("Before Clicking on Add icon");
				  if(assignLimit.contains("yes"))
					{
						CommonUtils.hold(4);
						//System.out.println("//tr["+i+"]//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']");
						act.moveToElement(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+len2+"]"))).build().perform();
						CommonUtils.hold(2);
						CommonUtils.explicitWait(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);
						act.moveToElement(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+len2+"]//div[contains(@id,'icon_authority-row-editor')]//a[@class='add-icon' and @title='Add Row']"))).click().build().perform();

					}
					else
					{ int lengh=len2-1;
					System.out.println("//td[text()='"+i+"']//ancestor::tr//td["+lengh+"]//descendant::a[@class='add-icon' and @title='Add Row']");
					//act.moveToElement(driver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]//tbody//tr["+i+"]//td["+lengh+"]//a[@class='add-icon' and @title='Add Row']"))).build().perform();
					act.moveToElement(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+lengh+"]"))).build().perform();
					CommonUtils.hold(3);
					CommonUtils.explicitWait(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+lengh+"]//descendant::a[@class='add-icon' and @title='Add Row']")), "Click", "", driver);
					act.moveToElement(driver.findElement(By.xpath("//td[text()='"+i+"']//ancestor::tr//td["+lengh+"]//descendant::a[@class='add-icon' and @title='Add Row']"))).click().build().perform();

					}
				  Log.info(" after Clicking on Add icon");
					Reporter.log(" after Clicking on Add icon");
//			  }catch(Exception caarExcep7) {
//				  caarExcep7.printStackTrace();
//			  }
				CommonUtils.hold(5);
//				int pk=i+1;
//				WebElement seq=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+pk+"]//td[1]"));
//				
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",seq);
//				CommonUtils.hold(2);
//				act.moveToElement(seq).click().build().perform();
			}
		
			for(int k=1;k<=AAattributes.length;k++)
			{
				
				CommonUtils.hold(2);
				String op=AAopeartors[k-1];
				if(!op.contains("donothing")) {
try
{ ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")));
}
catch(Exception e)
{
	

}CommonUtils.waitforElementtoClick(30, driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")), driver);
				driver.findElement(By.xpath("//div[@id='inlineConditionContent_"+i+"_"+k+"']")).click();
				CommonUtils.hold(3);
				//CommonUtils.explicitWait(editCondition, "ElementVisible", "", driver);

				Actions act1 = new Actions(driver);
				act1.moveToElement(driver.findElement(By.xpath("(//*[@title='Edit'])["+k+"]"))).click().build().perform();
				CommonUtils.hold(3);
			//	CommonUtils.explicitWait(clickOperator, "Click", "", driver);
				act1.moveToElement(clickOperator).click().build().perform();
				CommonUtils.hold(2);
				//String op=AAopeartors[k-1];
				if(op.contains("sign"))
				{
					CommonUtils.hold(2);
					//CommonUtils.explicitWait(driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-operator_conditionEditor_authority-row-editor']//ul//li["+edu.get(op)+"]")), "Click", "", driver);
					CommonUtils.waitforElementtoClick(15, driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-operator_conditionEditor_authority-row-editor']//ul//li["+edu.get(op)+"]")), driver);
					driver.findElement(By.xpath("//oj-list-view[@id='oj-searchselect-results-operator_conditionEditor_authority-row-editor']//ul//li["+edu.get(op)+"]")).click();
				}
				else if(op.contains("between"))
				{
					String op2=op.replace("inclusive","");
					CommonUtils.hold(2);
					//CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+op2+"']")), "Click", "", driver);
					CommonUtils.waitforElementtoClick(15, driver.findElement(By.xpath("//span[text()='"+op2+"']")), driver);
					driver.findElement(By.xpath("//span[text()='"+op2+"']")).click();
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
//				        CommonUtils.hold(2);
//						CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+op.replace("inclusive", "")+"']")), "Click", "", driver);
//
//						driver.findElement(By.xpath("//*[text()='"+op.replace("inclusive", "")+"']")).click();
//						CommonUtils.hold(2);
				}
				
				else{
					CommonUtils.hold(2);
					//CommonUtils.explicitWait(driver.findElement(By.xpath("//*[text()='"+op+"']")), "Click", "", driver);
					CommonUtils.waitforElementtoClick(15, driver.findElement(By.xpath("//span[text()='"+op+"']")), driver);
					driver.findElement(By.xpath("//span[text()='"+op+"']")).click();
				}
				/*if(AAvalues[k-1].contains("donothing")) 
				{
					System.out.println("**********do nothing********");
					
				}
				else*/ 
				if(!(AAvalues[k-1].contains("donothing"))){
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",valuesection2);
					valuesection2.click();
                    //CommonUtils.explicitWait(valuesection, "Click", "", driver);
				if(op.contains("is one of")|| op.contains("is not one of"))
				{
					String value []=AAvalues[k-1].split("oneof");
					for (int ii=1;ii<=value.length;ii++)
					{
						
						valuesection2.sendKeys(value[ii-1]);
						CommonUtils.hold(4);
						valuesection2.sendKeys(Keys.TAB);CommonUtils.hold(4);
						//CommonUtils.explicitWait(valuesection2, "Click", "", driver);
						CommonUtils.waitforElementtoClick(30, valuesection2, driver);
						valuesection2.click();
						//CommonUtils.explicitWait(valuesection2, "Click", "", driver);
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
					valuesection2.sendKeys(AAvalues[k-1]);
				}
				}
				tick.click();
				CommonUtils.hold(2);
				}
				
				
			}
//			int pk=i+1;
//			WebElement seq=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+pk+"]//td[1]"));
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",seq);			
//			act.moveToElement(seq).click().build().perform(); 
			//CommonUtils.hold(4);
			Reporter.log(" Enterning approver section started");
			CommonUtils.hold(2);
			WebElement th;
			WebElement tb=driver.findElement(By.xpath("//tbody[@class='oj-table-body']"));
			//WebElement tb=driver.findElement(By.xpath("//oj-table[@id='authorityRowEditorTable_authority-row-editor']"));
			if(assignLimit.contains("yes"))
			{
			 th=driver.findElement(By.xpath("//th[contains(@abbr,'Approval Limit')]//following-sibling::th"));

			}
			else
			{
				 th=driver.findElement(By.xpath("//th[contains(@abbr,'Approver')]//following-sibling::th"));

			}
			try {
				//tb.click();CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
				CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
							Reporter.log(" after performed scroll left");

//		         i1 = 0;
//		        while (i1 < 6)
//		        {
//		            tb.sendKeys(Keys.RIGHT);
//		            ++i1;
//		        }  
		        CommonUtils.hold(2);
		        System.out.println(" after scroll ");
				}
				catch(Exception e)
				{
					System.out.println(" scroll bar not displayed");
					Reporter.log(" scroll bar not displayed");
				}
			
			int len1=AAattributes.length+1;
			
			int rowIndex = i+1;
			//CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']")), "Click", "", driver);
			//CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']")), "Visible", "", driver);
			//CommonUtils.waitforElementtoClick(15, driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']")), driver);
			try {
				CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]"), 15, driver);
				//CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"), 15, driver);
			}catch(Exception approverDivExcep) {
				System.out.println("Approver div not loaded, retrying again");
				approverDivExcep.printStackTrace();
				CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]"), 30, driver);
				//CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"), 30, driver);

			}
				CommonUtils.hold(2);
	//			((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']")));
			//	driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']")).click();
						Reporter.log("before click on sequence");

				driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']")).click();
				CommonUtils.hold(2);
				Reporter.log("after click on sequence");
				
				try {
					//tb.click();CommonUtils.hold(2);
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
					CommonUtils.hold(2);
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
						Reporter.log(" after performed scroll left again");
//			         i1 = 0;
//			        while (i1 < 6)
//			        {
//			            tb.sendKeys(Keys.RIGHT);
//			            ++i1;
//			        }  
			        CommonUtils.hold(2);
			        System.out.println(" after scroll ");
					}
					catch(Exception e)
					{
						System.out.println(" scroll bar not displayed");
						Reporter.log(" scroll bar not displayed");
					}
					
						Reporter.log(" before click on label ");
				//act.moveToElement(driver.findElement(By.xpath("//div[@id='approver_label_"+i+"_"+len1+"']"))).build().perform();
	driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']//ancestor::tr[1]//descendant::div[contains(@id, 'approver_label')]")).click();
	
			CommonUtils.hold(5);
Reporter.log(" after click on label ");
			//String approver1=AAUsers.replace("user", "");
			String approver1=policylist.get(i);
			
			if(!(approver1.contains("RoutingPolicy")))
			{
			    Reporter.log(" if block for user");

				try {
					CommonUtils.waitforElementtoClick(15, userinput, driver);
					CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//input[@id='approver_target_userauthority-row-editor|input']"), 15, driver);
				}catch(Exception approverLOVExcep) {
					System.out.println("Approver LOV not visible, retrying again");
					approverLOVExcep.printStackTrace();
					driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]")).click();
					CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//input[@id='approver_target_userauthority-row-editor|input']"), 30, driver);
				}
				Reporter.log(" before moving to user input");
				act.moveToElement(userinput).click().build().perform();
			
				CommonUtils.hold(2);
	Reporter.log(" after moving to user input");
	
	Reporter.log(" before enterning user input");
				act.moveToElement(userinput).sendKeys(approver1).build().perform();
			Reporter.log(" after enterning user input");

				CommonUtils.hold(15);
				Reporter.log(" after 15 sec of  enterning user input");			
								
				try {
				//	CommonUtils.waitforElementtoClick(15, driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")), driver);
				Reporter.log(" before waiting of visibility of user in usertable");
					CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']"), 30,driver);
									Reporter.log(" after waiting of visibility of user in usertable");

				}
				catch(Exception userVisiblityException) 
				{
				    Reporter.log(" inside catch block User not visible within 30secs, rescanning with 60secs timeout");
					System.out.println("User not visible within 30secs, rescanning with 60secs timeout");
					Log.info("User not visible within 30secs, rescanning with 60secs timeout");
					userVisiblityException.printStackTrace();
					act.moveToElement(tick2).click().build().perform();
					CommonUtils.hold(5);
					userinput.click();
					CommonUtils.hold(2);
					//userinput.clear();
					int len11=approver1.length();	
					String del = Keys.chord(Keys.BACK_SPACE);
					while(len11!=0)
					{
	
						  act.moveToElement(userinput).sendKeys(del).build().perform();
						  len11--;
					}
					CommonUtils.hold(2);
					userinput.sendKeys(approver1);
//					CommonUtils.waitforElementtoClick(15, driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")), driver);
					CommonUtils.hold(8);
					try {
					CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']"), 60,driver);
					}
					catch(Exception e) 
					{
						System.out.println("User not visible within 30secs");
						Log.info("User not visible within 30secs");
					}
				}
				
				CommonUtils.hold(2);
				
				
			//	((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")));
				if(assignLimit.contains("yes"))
				{
				    
			act.moveToElement(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']"))).click().build().perform();
			  CommonUtils.hold(2);
		//		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")));
	//	act.moveToElement(driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']"))).click().build().perform();
		
			  
			  
		//	  CommonUtils.hold(5);

				
				act.moveToElement(tick2).click().build().perform();
				CommonUtils.hold(5);
			}
				else
				{
				    				    Reporter.log("before first click on approver from list");

					//driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")).click();
((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")));
					CommonUtils.hold(2);
//					  Reporter.log("after first click on approver from list");
//					    Reporter.log("before second click on approver from list");
					//driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")).click();
//((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//span[@class='oj-listbox-highlighter' and text()='"+approver1+"']")));
//				
//					 CommonUtils.hold(5);
					   Reporter.log("after second click on approver from list");
					 act.moveToElement(tick2).click().build().perform();
						CommonUtils.hold(5);
						  Reporter.log("after  click on approver tick");
				}
		
			}
			else
			{
				
				 Reporter.log(" else block for approvar group");

					try {
						CommonUtils.waitforElementtoClick(15, approverSelection, driver);
					}catch(Exception approverLOVExcep) {
						System.out.println("Approver LOV not visible, retrying again");
						approverLOVExcep.printStackTrace();
						driver.findElement(By.xpath("//td[contains(@class , 'oj-table-data-cell oj-form-control-inherit sequence-column') and text() = '"+rowIndex+"']/ancestor::tr[1]/descendant::div[contains(@id, 'approver_label')]")).click();
						CommonUtils.waitforElementtoClick(15, approverSelection, driver);
					}
					Reporter.log(" before moving to user input");
					act.moveToElement(approverSelection).click().build().perform();
				
					CommonUtils.hold(2);
		
					act.moveToElement(approverGroup).click().build().perform();
					CommonUtils.hold(5);
					
					act.moveToElement(approverGroupInput).click().build().perform();
					
					CommonUtils.hold(2);
		
					act.moveToElement(approverGroupInput).sendKeys(approver1).build().perform();
					CommonUtils.hold(5);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath("//oj-highlight-text[@text='"+approver1+"']")));
				//	act.moveToElement(driver.findElement(By.xpath("//oj-highlight-text[@text='"+approver1+"']"))).click().build().perform();
					  CommonUtils.hold(3);

						
						act.moveToElement(tick2).click().build().perform();
						CommonUtils.hold(5);
					
			}
			
			
	        
			if(assignLimit.contains("yes"))
			{
				try {
									
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",th);
					CommonUtils.hold(2);
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", tb );
//			         i1 = 0;
//			        while (i1 < 6)
//			        {
//			            tb.sendKeys(Keys.RIGHT);
//			            ++i1;
//			        }  
			        CommonUtils.hold(2);
			        System.out.println(" after scroll");
					}
					catch(Exception e)
					{
						System.out.println(" scroll bar not displayed");
						Reporter.log(" scroll bar not displayed");
					}
				
				
		        
				int s=AAattributes.length+4;
			int o=i+1;
			System.out.println(" s is "+s+" o is "+o);
			int k=s-2;
			 int gh=k+1;
				if(limitlist.contains("unlimited"))
				{
//					WebElement el2=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+o+"]//td[1]"));
//					act.moveToElement(el2).click().build().perform();
//					CommonUtils.hold(4);
					//WebElement el=driver.findElement(By.xpath("(//tr["+o+"]//td//*[contains(@class,'readonlyContent oj-flex-item')])["+k+"]"));
				//	WebElement el=driver.findElement(By.xpath("//tr["+o+"]//td["+gh+"]//*[contains(@class,'oj-flex-item')]"));
					WebElement el=driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+gh+"]//*[contains(@class,'oj-flex-item')]"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",el);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();",el);
					CommonUtils.hold(4);
					act.moveToElement(driver.findElement(By.xpath("(//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@class,'inlineLimitCellContent limit invalid')]//div)[1]"))).click().build().perform();
					CommonUtils.hold(2);
					System.out.println("s is "+s+" i is "+i);
					act.moveToElement(unlimitedLimit).click().build().perform();
					CommonUtils.hold(2);
					System.out.println("//td[text()='"+o+"']//ancestor::tr//td["+s+"]");
				//	act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
					act.moveToElement(driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+s+"]"))).build().perform();

					CommonUtils.hold(2);
				}
				else {
//					WebElement el2=driver.findElement(By.xpath("//tr[contains(@class,'oj-table-body-row')]["+o+"]//td[1]"));
//					act.moveToElement(el2).click().build().perform(); 
//
//					CommonUtils.hold(4);
						       
					//WebElement el=driver.findElement(By.xpath("(//tr["+o+"]//td//*[contains(@class,'readonlyContent oj-flex-item')])["+k+"]"));
					WebElement el=driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+gh+"]//*[contains(@class,'oj-flex-item')]"));

				//	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",el);
					CommonUtils.hold(4);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();",el);
					CommonUtils.hold(4);
					
					//act.moveToElement(driver.findElement(By.xpath("(//td[contains(@id,'authorityRowEditorTable_authority-row-editor')]//div[contains(@class,'inlineLimitCellContent limit invalid')]//div)[1]"))).doubleClick().build().perform();

					
					((JavascriptExecutor) driver).executeScript("arguments[0].click();",approvalLimitinput);
					CommonUtils.hold(4);
					approvalLimitinput.sendKeys(limitlist);
				CommonUtils.hold(2);
				approvalLimitinput.sendKeys(Keys.TAB);
				CommonUtils.hold(4);
			//	act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
				act.moveToElement(driver.findElement(By.xpath("//td[text()='"+o+"']//ancestor::tr//td["+s+"]"))).build().perform();
				CommonUtils.hold(2);
				
				}
				
				
			}
			else
			{
				int s=AAattributes.length+3;
				int o=i+1;
				act.moveToElement(driver.findElement(By.xpath("//tbody[@class='oj-table-body']//tr["+o+"]//td["+s+"]"))).build().perform();
				CommonUtils.hold(2);
			}

		}
		//}
			System.out.println("no rule action is "+noRuleMatchAction);
			if(("ApproveRejectSkip").contains(noRuleMatchAction))
			{
				CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",noauthorityfound);
				CommonUtils.hold(2);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();",noauthorityfound);
				//try {
					CommonUtils.explicitWait(noauthorityfoundaction, "Click", "", driver);
					noauthorityfoundaction.click();
					CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@id='lovDropdown_rpPopupNoMatch']//descendant::span[text()='"+noRuleMatchAction+"']")), "Click", "", driver);
					driver.findElement(By.xpath("//div[@id='lovDropdown_rpPopupNoMatch']//descendant::span[text()='"+noRuleMatchAction+"']")).click();
					CommonUtils.hold(4);
					try {
						
					driver.findElement(By.xpath("//a[@title='Close']//img")).click();

				} catch (Exception e) {
					System.out.println(" try to close footer popup in AA policy");
					Reporter.log(" try to close footer popup in AA policy");
					e.printStackTrace();
				}
				
				//CommonUtils.explicitWait(noauthorityfoundTick, "Click", "", driver);
				//noauthorityfoundTick.click();
			}
			CommonUtils.hold(3);
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",savePolicy);

			}	
			catch(Exception e)
			{
				
			}
			act.moveToElement(savePolicy).click().build().perform();
			//CommonUtils.explicitWait(saveandClose, "Click", "", driver);
			CommonUtils.waitforElementtoClick(15, saveandClose, driver);
			CommonUtils.hold(5);
			act.moveToElement(saveandClose).click().build().perform();
			CommonUtils.hold(12);
			//CommonUtils.explicitWait(NameSortRP, "Click", "", driver);
			//CommonUtils.waitforElementtoClick(15, NameSortRP, driver);
			//CommonUtils.hold(15);
			act.contextClick(NameSortRP).perform();
			CommonUtils.hold(4);
			act.moveToElement(SortButton).click().build().perform();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(SortDescending, "Click", "", driver);
			act.moveToElement(SortDescending).build().perform();
			CommonUtils.hold(4);
			CommonUtils.waitforElementtoClick(15, SortDescending, driver);
			/*CommonUtils.explicitWait(SortDescending, "Visible", "", driver);
			CommonUtils.explicitWait(SortDescending, "Click", "", driver);*/
			act.moveToElement(SortDescending).perform();
			SortDescending.click();
			CommonUtils.hold(10);

			try
			{
				boolean ispresent=driver.findElement(By.xpath("//div[text()='"+policyName.get(0)+"']")).isDisplayed();
				if(ispresent)
				{
					Log.info("Routing policy created");

				}
			}
			catch(Exception e)
			{
				
				Log.info("Routing policy not created");
				boolean ispresent=err.isDisplayed();
				if(ispresent)
				{
					Log.info("Error occured after saving the policy");
					Log.info("Error is "+err.getText());
					assertTrue(false,err.getText());

				}
				assertTrue(false,"Routing policy not created error is"+err.getText());
			}
			//CommonUtils.explicitWait(menuClose, "Click", "", driver);
			CommonUtils.waitforElementtoClick(15, menuClose, driver);
			CommonUtils.hold(5);
			menuClose.click();		
			CommonUtils.hold(5);

		/*act.moveToElement(savePolicy).click().build().perform();
		CommonUtils.explicitWait(saveandClose, "Click", "", driver);
		CommonUtils.hold(5);
		act.moveToElement(saveandClose).click().build().perform();
		//	saveandClose.click();
		CommonUtils.hold(8);
		
		System.out.println("Routing policy created :"+policyName.get(0));
		Log.info("Routing policy created :"+policyName.get(0));	
		try
		{
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@title='"+policyName.get(0)+"']")));
			boolean ispresent=false;			
			
			ispresent=driver.findElement(By.xpath("//div[@title='"+policyName.get(0)+"']")).isDisplayed();
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
		CommonUtils.hold(5);*/
	}//End of createApprovalAuthorityroutingpolicy class
}//End of redwoodRuleConfigUtil class
