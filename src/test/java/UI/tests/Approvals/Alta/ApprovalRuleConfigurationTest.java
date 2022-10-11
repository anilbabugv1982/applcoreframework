package UI.tests.Approvals.Alta;


import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import pageDefinitions.UI.oracle.applcore.qa.Approval.Alta.RuleConfigPage;
import pageDefinitions.UI.oracle.applcore.qa.Approval.Alta.RuleConfigUtil;
public class ApprovalRuleConfigurationTest  extends GetDriverInstance {
	
	WebDriver approvalDriver;
	private RuleConfigUtil ruleUtil;
	private RuleConfigPage rules;
	Random r;
	@FindBy(xpath ="//oj-input-text[contains(@class,'oj-fnd-approvals-step-node-title')]")
	public static WebElement waitstep;
	static String PolicyNameNew="RoutingPolicy"+CommonUtils.uniqueId();

	@Parameters({ "user", "pwd" })
	@BeforeMethod
	public void signIn(String username, String password) throws Exception {
			
			approvalDriver = getDriverInstanceObject();
			ruleUtil=new RuleConfigUtil(approvalDriver);
			rules=new RuleConfigPage(approvalDriver);
			rules.login(username,password, approvalDriver);
			r= new Random();
		}
	@AfterMethod
	public void logout() throws Exception {
			
			approvalDriver.quit();
		}	
	
	
	
	public ApprovalRuleConfigurationTest(WebDriver driver)
	{
		approvalDriver=driver;
		ruleUtil=new RuleConfigUtil(driver);
		rules=new RuleConfigPage(driver);
		r= new Random();
	}
	

    @SuppressWarnings("static-access")
	@Test(description = "This Method will create and deploy rule line amount less than invoice amount and approver as Terry Green",priority=1)
	public void ConfigUI(String testname) throws Exception {
    	WebDriverWait wait = new WebDriverWait(approvalDriver, 15);
    	JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
    	//String testname = "steplevelparallelcons";
    	 List<String> PolicyName =new ArrayList<String>();
    	 PolicyName.add("RoutingPolicy"+CommonUtils.uniqueId());
    	 String AAstartwith="",assignLimit="",ReturnApproverFrom="";
    	 String[] AAattributes= {} ;
    	 String[] SHattributes= {} ;
    	 String []noRuleMatch= {};
    	 String noRuleMatchAction="";
    	 String[] SHconditions= {};
    	 String []limitlist= {};
    	 List<String>policylist= new ArrayList<String>();
    	 String[] valuesList1 = {};
    	 String[] Values1= {};
    	 List<String> AAusers=new ArrayList<String>();
    	String[] params = ruleUtil.loadProperties(testname);
    	
		String task="Account Payables Invoice Approvals"; 
		String[] headermultiple = params[0].split("multiple");
		for (int i=0;i<headermultiple.length;i++)
		{
		
		List<String> header=new ArrayList<String>();
		String[] heardermultiples = params[0].split("multiple"); 
		String[] headerlists = heardermultiples[i].split("delimand");
			for (int j=0; j< headerlists.length; j++)
			{
			header.add(headerlists[j]);
			}
		
		List<String> attributes=new ArrayList<String>();
		String[] attributemultiples = params[1].split("multiple"); 
		String[] attributeslists = attributemultiples[i].split("delimand");
			for (int j=0; j< attributeslists.length; j++)
			{
			attributes.add(attributeslists[j]);
			}
		
		List<String> operator=new ArrayList<String>();
		String[] operatormultiples = params[2].split("multiple"); 
		String[] operatorlists = operatormultiples[i].split("delimand");
		  for (int j=0; j < operatorlists.length; j++)
			{
			operator.add(operatorlists[j]);
			}
	    
		List<String> values=new ArrayList<String>();
		String[] valuesmultiples = params[3].split("multiple"); 
		String[] valueslists = valuesmultiples[i].split("delimand");
		for (int j=0; j< valueslists.length; j++)
			{
			values.add(valueslists[j]);
			System.out.println(valueslists[j]);
			}
		
		
		List<String> operations=new ArrayList<String>();
		String[] operationsmultiples = params[4].split("multiple"); 
		String[] operationslists = operationsmultiples[i].split("delimand");
		    for (int j=0; j< operationslists.length; j++)
			{
			operations.add(operationslists[j]);
			}
		
		String[] actionmultiples = params[5].split("multiple"); 
		
		List<String> policyusers=new ArrayList<String>();
		if(params[5].contains("policy")) {
			if(params[5].equalsIgnoreCase("AApolicy"))
			{
				String[] valuesList = params[6].split("AA"); 
				System.out.println("valuesList is "+valuesList);
				assignLimit=valuesList[0];
				if(valuesList[1].contains("FirstMatch"))
				{
					ReturnApproverFrom="First matched authority record";
				}
				else
				{
					ReturnApproverFrom="All matching authority records";
				}
				AAattributes=valuesList[2].split("attribute");
//				AAopeartors=valuesList[3].split("operator");
//				AAvalues=valuesList[4].split("value");
//				AAUsers=valuesList[5].split("approver");
				if(assignLimit.contains("yes"))
				{
					limitlist=valuesList[6].split("limitamount");
					
				}
				
				valuesList1 = params[6].split("Rows"); 
				int len=valuesList1.length;
				System.out.println("valuesList1 are "+Arrays.toString(valuesList1));
				System.out.println("no rule match text: "+valuesList1[len-2]);
				if(valuesList1[len-2].contains("norulematchthen")); 
				{
					noRuleMatchAction=valuesList1[len-1];
					valuesList1=Arrays.copyOfRange(valuesList1, 0, len-2);
				}
				System.out.println("rows are :"+valuesList1.length);
				System.out.println("value list is "+Arrays.toString(valuesList1));
				for(int i1=1;i1<valuesList1.length;i1++)
				{
					AAusers.clear();
					List<String> PolicyName1=new ArrayList<String>();
					PolicyName1.clear();
					PolicyName1.add("RoutingPolicy"+CommonUtils.uniqueId());
					String[] data=valuesList1[i1].split("AA");
					//System.out.println("value list is "+Arrays.toString(data));
					String approver=data[data.length-2];
					//System.out.println("Approver is "+approver);
					if(approver.contains("policy"))
					{
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")));
						approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
						CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
						ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
						CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
						String []data1=approver.split("group");
						System.out.println("policy type is "+data1[data1.length-1]);
						for(int k1=1;k1<data1.length-1;k1++)
						{
							System.out.println("users are "+data1[k1]);
							AAusers.add(data1[k1]);
							
						}
						if(data1[data1.length-1].contains("serially"))
						{
							ruleUtil.createApprovalGroup(approvalDriver, PolicyName1,"Serially", AAusers);
							policylist.add(PolicyName1.get(0));
							RuleConfigPage.stepClose.click();
							
						}
						else if(data1[data1.length-1].contains("parallel"))
						{
							ruleUtil.createApprovalGroup(approvalDriver, PolicyName1,"In Parallel - Consensus", AAusers);
							policylist.add(PolicyName1.get(0));
							RuleConfigPage.stepClose.click();
						}
						else
						{
							ruleUtil.createApprovalGroup(approvalDriver, PolicyName1,"In Parallel - First Responder Wins", AAusers);
							policylist.add(PolicyName1.get(0));
							RuleConfigPage.stepClose.click();
						}
					}
				}
				
			 System.out.println("all policy list is "+policylist);
			}
			else if(params[5].equalsIgnoreCase("SHpolicy"))
			{
				String[] Values = params[6].split("SH"); 
				SHconditions = Values[0].split("delimand");
				SHattributes=Values[1].split("attribute");
				
                Values1 = params[6].split("Rows"); 
		        int ValuesLen=Values1.length; 
			    //System.out.println("Values1 are "+Arrays.toString(Values1));
				//System.out.println("no rule match text: "+Values1[ValuesLen-2]);
			    if(Values1[ValuesLen-2].contains("norulematchthen")); 
				{
				noRuleMatchAction=Values1[ValuesLen-1];
					Values1=Arrays.copyOfRange(Values1, 0, ValuesLen-2);
				}
				//System.out.println("rows are :"+Values1.length);
			    //System.out.println("value list is "+Arrays.toString(Values1));
			}
			else {
			String [] policycheck = params[5].split("multiple");
			String [] policyuser = params[6].split("multiple");
			for (int j=0; j< policycheck.length; j++) {
				if(policycheck[j].contains("policy")) {
					String[] policyuserlists = policyuser[j].split("delimand");
					System.out.println(policyuserlists.length);
				   for (int k=0; k< policyuserlists.length; k++)
					{
					  policyusers.add(policyuserlists[k]);
					  System.out.println(policyuserlists[k]);
					}
				}
				
			}
			}
		}

		List<String> users=new ArrayList<String>();
		String[] usermultiples = params[6].split("multiple"); 
		String[] userlists = usermultiples[i].split("delimand");
			for (int j=0; j< userlists.length; j++)
			{
			users.add(userlists[j]);
			}
	    
		if (i==0) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")));
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		CommonUtils.hold(4);
		ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		if(params[5].contains("policy")) { 
			if (params[5].contains("AApolicy")) {
				
				ruleUtil.createApprovalAuthorityPolicy(approvalDriver, PolicyName, assignLimit,ReturnApproverFrom,AAattributes,valuesList1,noRuleMatchAction);
			}
			else if (params[5].contains("SHpolicy")) {
				
				ruleUtil.createSupervisorRoutingPolicy(approvalDriver, PolicyName, SHconditions, SHattributes, Values1, noRuleMatchAction);
			}
			else {
				
				ruleUtil.createApprovalGroup(approvalDriver, PolicyName, params[7], policyusers);
			}
		
		}
		
		if(params[8].contains("ErrorinRP")) { 
            RuleConfigPage.menu.click();
			
			CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[@class='routing-policy-list-scroll']")), "Click", "", approvalDriver);
			approvalDriver.findElement(By.xpath("//div[@class='routing-policy-list-scroll']")).click();
			CommonUtils.hold(5);
			
			StringBuilder PolicyName2 = new StringBuilder();
			String delim = "-";
			
			int i1 = 0;
	        while (i1 < PolicyName.size() - 1) {
	        	PolicyName2.append(PolicyName.get(i1));
	        	PolicyName2.append(delim);
	            i1++;
	        }
	        PolicyName2.append(PolicyName.get(i1));
	        
	        System.out.print(PolicyName);
	        System.out.print(PolicyName2);
			
			boolean PolicyExist= approvalDriver.findElement(By.xpath("//div[@title='"+PolicyName2+"' and @aria-label='Routing Policies']")).isDisplayed();
			if(PolicyExist)
			{
				Log.info("Routing Policy shouln't exist");
				Assert.assertTrue(false,"Routing Policy created succesfully, but it shouldn't have been created");
			}
			else
			{
				Log.info("Routing Policy not created");
			}
			RuleConfigPage.stepClose.click();		
			}
		
		
		
		CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		
		if(params[8].contains("currency")) { 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'condition_r')]//span[text()='Condition']")));
			approvalDriver.findElement(By.xpath("//div[contains(@id,'oj-select-choice-currency')]")).click();
			CommonUtils.hold(1);		
			approvalDriver.findElement(By.xpath("//div[@class='oj-listbox-result-label'][contains(.,'"+params[10]+"')]")).click();			
			}
		
	
		if(params[8].contains("action")) { 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'condition_r')]//span[text()='Condition']")));	
			approvalDriver.findElement(By.xpath("//div[contains(@id,'oj-select-choice-notificationMethod')]")).click();
			CommonUtils.hold(1);		
			approvalDriver.findElement(By.xpath("//div[(text() ='Notify in Parallel - Consensus')]")).click();
		}
		
		
		}
		ruleUtil.createRule(approvalDriver,header, attributes, operator, values, operations, i);
		Reporter.log("Rule condition created");
		if(actionmultiples[i].contains("user") || actionmultiples[i].contains("role") || actionmultiples[i].contains("attribute") || actionmultiples[i].contains("FYI")) { 
		ruleUtil.setActionOnrule(approvalDriver,actionmultiples[i], users);
		}
		else if (actionmultiples[i].contains("policy"))
		{
		ruleUtil.setActionOnrule(approvalDriver,actionmultiples[i],PolicyName);
		}
		else
		{
		ruleUtil.autoapprove(approvalDriver, actionmultiples[i]);
		}	
		
		
		
		if(operations.get(0).contains("always")) { 
			
			approvalDriver.findElement(By.xpath("//input[contains(@name,'always_checkbox_r')]")).click();
			CommonUtils.hold(1);
			rules.alwaysok.click();
			CommonUtils.hold(1);
			
		}
		
		
		
		if (i==headermultiple.length-1) {
		Reporter.log("SendForApproval to <Terry Green>");
		if(params[8].contains("norule")) { 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'noRuleMatch_text')]")));	
			approvalDriver.findElement(By.xpath("//div[contains(@id, 'noRuleMatch_text')]")).click();
			CommonUtils.hold(1);
			approvalDriver.findElement(By.xpath("//div[contains(@id,'oj-select-choice-noRuleMatch')]")).click();
			CommonUtils.hold(1);		
			approvalDriver.findElement(By.xpath("//div[(text() ='"+params[9]+"')]")).click();
			CommonUtils.hold(1);
			
		}
		ruleUtil.DeployRule(approvalDriver);
		Reporter.log("Rule Deployed");
		CommonUtils.hold(5);
		}
		}
	
	}
	
	@Test(description = "This Method will approve the rule In Parallel - First Responder Win  via routig policy to approve",priority=12)
	public void updateRule() throws Exception {
		String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);	
		approvalDriver.findElement(By.xpath("//div[@id='RULE_1_action0']")).click();
		RuleConfigPage.actionForApprove.click();
		CommonUtils.explicitWait(RuleConfigPage.sendForApproval, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		RuleConfigPage.sendForApproval.click();
		
		CommonUtils.explicitWait(RuleConfigPage.to, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		RuleConfigPage.to.click();
		CommonUtils.explicitWait(RuleConfigPage.userValue, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		RuleConfigPage.userValue.click();	
			CommonUtils.explicitWait(RuleConfigPage.userAndRole, "Click", "", approvalDriver);
				CommonUtils.hold(2);
				RuleConfigPage.userAndRole.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(RuleConfigPage.userValue1, "Click", "", approvalDriver);
				
				//RuleConfigPage.userValue1.click();
				CommonUtils.hold(1);
				RuleConfigPage.userValue1.sendKeys(Keys.chord("k"));
				
//				CommonUtils.explicitWait(RuleConfigPage.userValue2, "Click", "", approvalDriver);
//				RuleConfigPage.userValue2.clear();
//				CommonUtils.hold(1);

				  CommonUtils.explicitWait(RuleConfigPage.userValue2Message, "Visible", "", approvalDriver);
				  CommonUtils.hold(2);
				 // userValue2Message.click();
				 // CommonUtils.hold(2);
				  RuleConfigPage.userValue2.click(); 
				  CommonUtils.hold(2); 
				  RuleConfigPage.userValue2.clear();
				  CommonUtils.hold(1); 
				RuleConfigPage.userValue2.sendKeys("Chris Black");
				CommonUtils.hold(4);
				approvalDriver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'Chris Black')]")).click();
				CommonUtils.explicitWait(RuleConfigPage.close, "Click", "", approvalDriver);
				CommonUtils.hold(20);
				RuleConfigPage.close.click();
				CommonUtils.hold(5);
				ruleUtil.DeployRule(approvalDriver);

	}
	
	public void deleteRule() throws Exception {
		String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);
		Actions act=new Actions(approvalDriver);
		act.moveToElement(RuleConfigPage.rule).build().perform();
		RuleConfigPage.deleteRule.click();
		RuleConfigPage.deleteRuleOK.click();
		CommonUtils.hold(5);
		ruleUtil.DeployRule(approvalDriver);
	}
	public void deleteStep() throws Exception {
		String task="Account Payables Invoice Approvals"; 
		boolean isElementPresent=false;
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		//CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		
		try 
		{
			//Actions act=new Actions(approvalDriver);
		//	act.moveToElement(RuleConfigPage.rule).build().perform();
			isElementPresent=RuleConfigPage.attributeInRule.isDisplayed();
			if(isElementPresent)
			{
				Assert.assertTrue(false,"rule should not be present");
			}
		}
		catch(Exception e)
		{
			if(isElementPresent)
			{
				Assert.assertTrue(false,"rule should not be present");
			}
			
		}
		CommonUtils.explicitWait(RuleConfigPage.stepClose, "Click", "", approvalDriver);
	   // CommonUtils.hold(10);
		RuleConfigPage.stepClose.click();
	}
	public void createPolicy() throws Exception  {
		
		//PolicyName="RoutingPolicy210107093825";
		List<String> PolicyName1=new ArrayList<String>();
		PolicyName1.add(PolicyNameNew);
		
		 List<String> AAusers=new ArrayList<String>();
		 AAusers.add("Terry Green");
		 AAusers.add("Connor Horton");
		 String task="Account Payables Invoice Approvals"; 
			approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
			CommonUtils.hold(15);
//		try {
			ruleUtil.createApprovalGroup(approvalDriver, PolicyName1,"Serially", AAusers);
//		} catch (Exception e) {
//			assertTrue(false,"Policy Not created");
//		}
			
//			
//			RuleConfigPage.menuClose.click();
//			
//			CommonUtils.hold(5);
//			CommonUtils.explicitWait(RuleConfigPage.stepClose, "Click", "", approvalDriver);
//			CommonUtils.hold(4);
//			RuleConfigPage.stepClose.click();
//			System.out.println("Clicked close");
			
	}
	public void UpdatePolicy() throws Exception {
		//PolicyNameNew="RoutingPolicy201217121119";
		System.out.println("Policy to update is :"+PolicyNameNew);
		Log.info("Policy to update is :"+PolicyNameNew);
		 String task="Account Payables Invoice Approvals"; 
	    	WebDriverWait wait = new WebDriverWait(approvalDriver, 15);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")));
			approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
			CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
			CommonUtils.hold(4);
			ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
			CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
					
			Actions act=new Actions(approvalDriver);
		    JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		    RuleConfigPage.menu.click();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(RuleConfigPage.createRP, "Click", "", approvalDriver);
			CommonUtils.hold(4);
			
	//Actions act=new Actions(approvalDriver);
	((JavascriptExecutor) approvalDriver).executeScript("arguments[0].scrollIntoView(true);", approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']")));
	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']"))).click().build().perform();
	CommonUtils.hold(2);
	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']//parent::td//following-sibling::td//img//parent::a[@title='Edit']"))).click().build().perform();

	CommonUtils.hold(4);
	CommonUtils.waitForPageLoad(approvalDriver);
	CommonUtils.waitForJStoLoad(approvalDriver);
	CommonUtils.explicitWait(RuleConfigPage.policyName,"Click", "", approvalDriver);

	RuleConfigPage.notify.click();
	approvalDriver.findElement(By.xpath("//li//div[text()='In Parallel - Consensus']")).click();
	CommonUtils.hold(4);
	

	act.moveToElement(RuleConfigPage.savePolicy).click().build().perform();
	CommonUtils.explicitWait(RuleConfigPage.saveandClose, "Click", "", approvalDriver);
	CommonUtils.hold(5);
	act.moveToElement(RuleConfigPage.saveandClose).click().build().perform();
	CommonUtils.hold(5);

	try
	{
		boolean ispresent=approvalDriver.findElement(By.xpath("//div[text()='"+PolicyNameNew+"']")).isDisplayed();
		if(ispresent)
		{
			Log.info(" routing policy edited");

		}
	}
	catch(Exception e)
	{
		
		Log.info(" routing policy not edited");
		boolean ispresent=RuleConfigPage.err.isDisplayed();
		if(ispresent)
		{
			Log.info("Error occured after saving the policy");
			Log.info("Error is "+RuleConfigPage.err.getText());
			assertTrue(false,RuleConfigPage.err.getText());

		}
		assertTrue(false,"Routing policy not edited and error is"+RuleConfigPage.err.getText());
	}
	
	((JavascriptExecutor) approvalDriver).executeScript("arguments[0].scrollIntoView(true);", approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']")));
	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']"))).click().build().perform();
	CommonUtils.hold(2);
	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']//parent::td//following-sibling::td//img//parent::a[@title='Edit']"))).click().build().perform();

	CommonUtils.hold(4);
	CommonUtils.waitForPageLoad(approvalDriver);
	CommonUtils.waitForJStoLoad(approvalDriver);
	CommonUtils.explicitWait(RuleConfigPage.policyName,"Click", "", approvalDriver);
	String notifyType=approvalDriver.findElement(By.xpath("//span[@id='routingTypeDropdown_selected']")).getText();
	if(notifyType.equalsIgnoreCase("In Parallel - Consensus"))
	{
		
	}
		
	else
	{
		assertTrue(false,"Policy is not edited");
	}
	}
	public void DeletePolicy() {
		try {
			//PolicyNameNew="RoutingPolicy210107010124";
			System.out.println("Policy to update is :"+PolicyNameNew);
			Log.info("Policy to delete is :"+PolicyNameNew);
			 String task="Account Payables Invoice Approvals"; 
				approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
				CommonUtils.hold(15);		
				RuleConfigPage.menu.click();
				CommonUtils.hold(10);
				CommonUtils.explicitWait(RuleConfigPage.createRP, "Click", "", approvalDriver);
				
		Actions act=new Actions(approvalDriver);
		((JavascriptExecutor) approvalDriver).executeScript("arguments[0].scrollIntoView(true);", approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']")));
		//((JavascriptExecutor) approvalDriver).executeScript("arguments[0].scrollIntoView(true);", approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']")));
			act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']"))).click().build().perform();
			CommonUtils.hold(2);
			approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']//parent::td//following-sibling::td//img//parent::a[@title='Delete Routing Policy']")).click();
			CommonUtils.explicitWait(RuleConfigPage.deleteRP, "Click", "", approvalDriver);
			//CommonUtils.hold(10);
			RuleConfigPage.deleteRP.click();
			CommonUtils.explicitWait(RuleConfigPage.deleteRPOK, "Click", "", approvalDriver);

			RuleConfigPage.deleteRPOK.click();CommonUtils.hold(2);
			boolean ispresent=false;
			try
			{
				 ispresent=approvalDriver.findElement(By.xpath("//div[text()='"+PolicyNameNew+"']")).isDisplayed();
				if(ispresent)
				{
					Log.info(" routing policy exit");
					Assert.assertTrue(false,"Routing policy not deleted");
				}
			}
			catch(Exception e)
			{
				Log.info(" routing policy sucessfully deleted");

			}
		}
		catch (Exception e) {
			assertTrue(false,"Policy Not deleted");
		}
		
	}
	public void CheckTaskVisibility(String username, String Password,String task) throws Exception {
		rules.launchURL(approvalDriver);
		CommonUtils.explicitWait(RuleConfigPage.home, "Click", "", approvalDriver);
		RuleConfigPage.home.click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(username, "Welcome1", approvalDriver);
		boolean isElementPresent=false;
		try
		{
			isElementPresent=	approvalDriver.findElement(By.xpath("//div[@title='"+task+"']")).isDisplayed();
			if(isElementPresent)
			{
				Log.info("Task is visible");
				Assert.assertTrue(false,"Task is visible");
			}
		}
		catch(Exception e)
		{
			Log.info("Task Not visible");

		}
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login("finuser1", "Welcome1", approvalDriver);
	}
	public void CreateRuleByViewAccessUser(String user) throws Exception {
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(user, "Welcome1", approvalDriver);
		String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);	

		int k=0;
		List<String> header=new ArrayList<String>();
		header.add("Invoice Header");
		List<String> attribute=new ArrayList<String>();
		attribute.add("Invoice Amount");
		List<String> op=new ArrayList<String>();
		op.add("lesserthanorequaltosign");
		List<String> value=new ArrayList<String>();
		value.add("Line Amount");
		List<String> operation=new ArrayList<String>();
		operation.add("click");
		ruleUtil.createRule(approvalDriver,header,attribute,op,value, operation, k);
		Reporter.log("Rule condition created");
		ruleUtil.autoapprove(approvalDriver, "Approve");
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		RuleConfigPage.saveRule.click();
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(RuleConfigPage.save, "Click", "", approvalDriver);
		Actions action = new Actions(approvalDriver);
		action.moveToElement(RuleConfigPage.save).click().build().perform();
		try
		{
			CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
			//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
			String errmsg=RuleConfigPage.networkErrorMsg.getText();
			if(!errmsg.equals("User with View Access Do Not have permission to save rule"))
			{
				Log.info("Expected Error message is not displayed");
				Assert.assertTrue(false,"Expected Error message is not displayed");
			}
		}
		catch(Exception e)
		{
			Log.info("Rule should not save");
			Assert.assertTrue(false,"Rule should not save . Error should be thrown");

		}
	}
	public void UpdateRuleByViewAccessUser(String user) throws Exception {
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(user, "Welcome1", approvalDriver);
		String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);	
		approvalDriver.findElement(By.xpath("//div[@id='RULE_1_action0']")).click();
		RuleConfigPage.actionForApprove.click();
		CommonUtils.explicitWait(RuleConfigPage.sendForApproval, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		RuleConfigPage.sendForApproval.click();
		
		CommonUtils.explicitWait(RuleConfigPage.to, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		RuleConfigPage.to.click();
		CommonUtils.explicitWait(RuleConfigPage.userValue, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		RuleConfigPage.userValue.click();	
			CommonUtils.explicitWait(RuleConfigPage.userAndRole, "Click", "", approvalDriver);
				CommonUtils.hold(2);
				RuleConfigPage.userAndRole.click();
				CommonUtils.hold(2);
				CommonUtils.explicitWait(RuleConfigPage.userValue1, "Click", "", approvalDriver);
				
				//RuleConfigPage.userValue1.click();
				CommonUtils.hold(1);
				RuleConfigPage.userValue1.sendKeys(Keys.chord("k"));
				
//				CommonUtils.explicitWait(RuleConfigPage.userValue2, "Click", "", approvalDriver);
//				RuleConfigPage.userValue2.clear();
//				CommonUtils.hold(1);

				  CommonUtils.explicitWait(RuleConfigPage.userValue2Message, "Visible", "", approvalDriver);
				  CommonUtils.hold(2);
				 // userValue2Message.click();
				 // CommonUtils.hold(2);
				  RuleConfigPage.userValue2.click(); 
				  CommonUtils.hold(2); 
				  RuleConfigPage.userValue2.clear();
				  CommonUtils.hold(1); 
				RuleConfigPage.userValue2.sendKeys("Chris Black");
				CommonUtils.hold(4);
				approvalDriver.findElement(By.xpath("//ul//li//descendant::*[contains(text(),'Chris Black')]")).click();
				CommonUtils.explicitWait(RuleConfigPage.close, "Click", "", approvalDriver);
				CommonUtils.hold(20);
				RuleConfigPage.close.click();
				CommonUtils.hold(5);
				
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		RuleConfigPage.saveRule.click();
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(RuleConfigPage.save, "Click", "", approvalDriver);
		Actions action = new Actions(approvalDriver);
		action.moveToElement(RuleConfigPage.save).click().build().perform();
		try
		{
			CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
			//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
			String errmsg=RuleConfigPage.networkErrorMsg.getText();
			if(!errmsg.equals("User with View Access Do Not have permission to save rule"))
			{
				Log.info("Expected Error message is not displayed");
				Assert.assertTrue(false,"Expected Error message is not displayed");
			}
		}
		catch(Exception e)
		{
			Log.info("Rule should not save");
			Assert.assertTrue(false,"Rule should not save . Error should be thrown");

		}
	}
	public void CreateDraftFlow() throws Exception {
		String task="Account Payables Invoice Approvals"; 
		ConfigUI("securityTestingRule9");
		approvalDriver.get(RestCommonUtils.getExecutableHost()+"fscmUI/fnd/vp/approvalsconfiguration");
		//ruleUtil.launchURL(approvalDriver);
		CommonUtils.hold(10);
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);
		int k=0;
		List<String> header=new ArrayList<String>();
		header.add("Invoice Header");
		List<String> attribute=new ArrayList<String>();
		attribute.add("Invoice Amount");
		List<String> op=new ArrayList<String>();
		op.add("lesserthanorequaltosign");
		List<String> value=new ArrayList<String>();
		value.add("Line Amount");
		List<String> operation=new ArrayList<String>();
		operation.add("clickalways");
		ruleUtil.createRule(approvalDriver,header,attribute,op,value, operation, k);
		Reporter.log("Rule condition created");
		ruleUtil.autoapprove(approvalDriver, "Approve");
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		RuleConfigPage.saveRule.click();
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(RuleConfigPage.save, "Click", "", approvalDriver);
		Actions action = new Actions(approvalDriver);
		action.moveToElement(RuleConfigPage.save).click().build().perform();
		CommonUtils.hold(5);
		WebDriverWait wait = new WebDriverWait(approvalDriver, 45);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Saved']")));
		CommonUtils.hold(5);
		CommonUtils.explicitWait(RuleConfigPage.stepClose, "Click", "", approvalDriver);
	    RuleConfigPage.stepClose.click();
	    CommonUtils.hold(10);
	
	}
	public void DeleteDraftFlow() throws Exception {
		String task="Account Payables Invoice Approvals"; 

		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]")).click();
		Actions act=new Actions(approvalDriver);
		act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+task+"']/ancestor::div[contains(@class,'oj-listview-cell-element')]//descendant::div[@class='oj-flex-item oj-sm-0 list-item']"))).build().perform();
		act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+task+"']/ancestor::div[contains(@class,'oj-listview-cell-element')]//descendant::div[@class='oj-flex-item oj-sm-0 list-item']//div//a"))).build().perform();
		RuleConfigPage.deleteDraftimg.click();
		CommonUtils.explicitWait(RuleConfigPage.deleteDraft, "Click", "", approvalDriver);
		RuleConfigPage.deleteDraft.click();
		CommonUtils.explicitWait(RuleConfigPage.deleteDraftOK, "Click", "", approvalDriver);
		RuleConfigPage.deleteDraftOK.click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")), "Click", "", approvalDriver);
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);
		boolean ispresent=false;
		try
		{
			 ispresent=approvalDriver.findElement(By.xpath("//span[text()='Terry Green']")).isDisplayed();
			if(!ispresent)
			{
				Log.info("Deployed rule not visible");
				Assert.assertTrue(false,"Deployed rule not visible");
			}
		}
		catch(Exception e)
		{
			Log.info("Deployed rule not visible");
			Assert.assertTrue(false,"Deployed rule not visible");

		}
		RuleConfigPage.stepClose.click();
		CommonUtils.hold(10);
		
	}
	public void DeleteRuleByViewAccessUser(String user) throws Exception {
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(user, "Welcome1", approvalDriver);
		String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);	
		Actions act=new Actions(approvalDriver);
		act.moveToElement(RuleConfigPage.rule).build().perform();
		RuleConfigPage.deleteRule.click();
		RuleConfigPage.deleteRuleOK.click();
		CommonUtils.hold(5);
		JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		RuleConfigPage.saveRule.click();
		//CommonUtils.hold(5);
		CommonUtils.explicitWait(RuleConfigPage.save, "Click", "", approvalDriver);
		Actions action = new Actions(approvalDriver);
		action.moveToElement(RuleConfigPage.save).click().build().perform();
		try
		{
			CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
			//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
			String errmsg=RuleConfigPage.networkErrorMsg.getText();
			if(!errmsg.equals("User with View Access Do Not have permission to save rule"))
			{
				Log.info("Expected Error message is not displayed");
				Assert.assertTrue(false,"Expected Error message is not displayed");
			}
		}
		catch(Exception e)
		{
			Log.info("Rule should not save");
			Assert.assertTrue(false,"Rule should not save . Error should be thrown");

		}
	}
	public void DeleteStepByViewAccessUser(String user) throws Exception {
		String task="Account Payables Invoice Approvals"; 
		//ConfigUI("securityTestingRule9");
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(user, "Welcome1", approvalDriver);
		//String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);

		ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
		try
		{
			CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
			//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
			String errmsg=RuleConfigPage.networkErrorMsg.getText();
			if(!errmsg.equals("User with View Access Do Not have permission to Create Policy"))
			{
				Log.info("Expected Error message is not displayed");
				Assert.assertTrue(false,"Expected Error message is not displayed");
			}
		}
		catch(Exception e)
		{
			Log.info("Rule should not save");
			Assert.assertTrue(false,"Rule should not save . Error should be thrown");

		}
	
	}
	public void CreatePolicyByViewAccessUser(String user) throws Exception {
		
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(user, "Welcome1", approvalDriver);
		
		PolicyNameNew="RoutingPolicy"+CommonUtils.uniqueId();
		List<String> PolicyName1=new ArrayList<String>();
		PolicyName1.add(PolicyNameNew);
		
		 List<String> users=new ArrayList<String>();
		 users.add("Terry Green");
		 //AAusers.add("Connor Horton");
		 String task="Account Payables Invoice Approvals"; 
			approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
			CommonUtils.hold(15);

			Actions act=new Actions(approvalDriver);
		    JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		    RuleConfigPage.menu.click();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(RuleConfigPage.createRP, "Click", "", approvalDriver);
			CommonUtils.hold(4);
			RuleConfigPage.createRP.click();
			CommonUtils.explicitWait(RuleConfigPage.approvalGroup, "Click", "", approvalDriver);
			CommonUtils.hold(6);
			act.moveToElement(RuleConfigPage.approvalGroup).click().build().perform();
			CommonUtils.hold(4);
			CommonUtils.waitForPageLoad(approvalDriver);
			CommonUtils.waitForJStoLoad(approvalDriver);
			CommonUtils.explicitWait(RuleConfigPage.policyName,"Click", "", approvalDriver);
			RuleConfigPage.policyName.sendKeys(PolicyName1.get(0));
			CommonUtils.hold(4);
			RuleConfigPage.notify.click();
			approvalDriver.findElement(By.xpath("//li//div[text()='In Parallel - Consensus']")).click();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(RuleConfigPage.EnterName,"Click", "", approvalDriver);
			RuleConfigPage.EnterName.click();
			CommonUtils.hold(4);
			act.doubleClick(RuleConfigPage.EnterName).perform();
			act.moveToElement(approvalDriver.findElement(By.xpath("//table[contains(@class,'oj-table-element oj-component-initnode')]"))).build().perform();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(RuleConfigPage.EnterName2, "Click", "", approvalDriver);
			RuleConfigPage.EnterName2.click();
			CommonUtils.hold(4);
			approvalDriver.findElement(By.xpath("//input[@id='oj-searchselect-filter-members|input']")).sendKeys(users.get(0));
			CommonUtils.hold(7);
			CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[@id='lovDropdown_members']")), "Click", "", approvalDriver);
			CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[@title='"+users.get(0)+"']")), "Visible", "", approvalDriver);
			CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[@title='"+users.get(0)+"']")), "Click", "", approvalDriver);
			CommonUtils.hold(8);
			act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+users.get(0)+"']"))).click().build().perform();
			act.clickAndHold(approvalDriver.findElement(By.xpath("//div[@title='"+users.get(0)+"']"))).doubleClick().build().perform();

			//act.moveToElement(driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']"))).doubleClick().build().perform();
			//executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@title='"+users.get(i)+"']")));
			CommonUtils.hold(10);
			act.moveToElement(RuleConfigPage.savePolicy).click().build().perform();
			CommonUtils.explicitWait(RuleConfigPage.saveandClose, "Click", "", approvalDriver);
			CommonUtils.hold(5);
			act.moveToElement(RuleConfigPage.saveandClose).click().build().perform();
		//	saveandClose.click();
			CommonUtils.hold(7);
			try
			{
				CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
				//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
				String errmsg=RuleConfigPage.networkErrorMsg.getText();
				if(!errmsg.equals("User with View Access Do Not have permission to Create Policy"))
				{
					Log.info("Expected Error message is not displayed");
					Assert.assertTrue(false,"Expected Error message is not displayed");
				}
			}
			catch(Exception e)
			{
				Log.info("Rule should not save");
				Assert.assertTrue(false,"Rule should not save . Error should be thrown");

			}
			approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
			
			rules.logout(approvalDriver);
			CommonUtils.hold(10);
			rules.login("finuser1", "Welcome1", approvalDriver);
	}
	public void UpdatePolicyByViewAccessUser(String user) throws Exception {
		approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
		CommonUtils.hold(10);
		rules.logout(approvalDriver);
		rules.login(user, "Welcome1", approvalDriver);
		
		System.out.println("Policy to update is :"+PolicyNameNew);
		Log.info("Policy to update is :"+PolicyNameNew);
		 String task="Account Payables Invoice Approvals"; 
	    	WebDriverWait wait = new WebDriverWait(approvalDriver, 15);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")));
			approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
			CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
			CommonUtils.hold(4);
			ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
			CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
					
			Actions act=new Actions(approvalDriver);
		    JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		    RuleConfigPage.menu.click();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(RuleConfigPage.createRP, "Click", "", approvalDriver);
	//((JavascriptExecutor) approvalDriver).executeScript("arguments[0].scrollIntoView(true);", RuleConfigPage.RPFirstRow)));
//	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+PolicyNameNew+"']"))).click().build().perform();
	//CommonUtils.hold(2);
		
		
			CommonUtils.explicitWait(RuleConfigPage.RPFirstRow, "Click", "", approvalDriver);
			//RuleConfigPage.RPFirstRow.click();
			act.moveToElement(RuleConfigPage.RPFirstRow).click().build().perform();CommonUtils.hold(2);
			act.moveToElement(RuleConfigPage.RPFirstRowEdit).click().build().perform();
			CommonUtils.hold(15);
			CommonUtils.waitForPageLoad(approvalDriver);
			CommonUtils.waitForJStoLoad(approvalDriver);
			CommonUtils.explicitWait(RuleConfigPage.policyName,"Click", "", approvalDriver);
			//RuleConfigPage.policyName.clear();
			RuleConfigPage.policyName.sendKeys("_UpdatePolicy");CommonUtils.hold(4);
			act.moveToElement(RuleConfigPage.savePolicy).click().build().perform();
			CommonUtils.explicitWait(RuleConfigPage.saveandClose, "Click", "", approvalDriver);
			CommonUtils.hold(5);
			act.moveToElement(RuleConfigPage.saveandClose).click().build().perform();
			CommonUtils.hold(5);
	try
	{
		CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
		//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
		String errmsg=RuleConfigPage.networkErrorMsg.getText();
		if(!errmsg.equals("User with View Access Do Not have permission to Edit Policy"))
		{
			Log.info("Expected Error message is not displayed");
			Assert.assertTrue(false,"Expected Error message is not displayed");
		}
	}
	catch(Exception e)
	{
		Log.info("Rule should not save");
		Assert.assertTrue(false,"Rule should not save . Error should be thrown");

	}	
	}
public void DeletePolicyByViewAccessUser(String user) throws Exception {
	approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
	CommonUtils.hold(10);
	rules.logout(approvalDriver);
	rules.login(user, "Welcome1", approvalDriver);
		System.out.println("Policy to update is :"+PolicyNameNew);
		Log.info("Policy to update is :"+PolicyNameNew);
		 String task="Account Payables Invoice Approvals"; 
	    	WebDriverWait wait = new WebDriverWait(approvalDriver, 15);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")));
			approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
			CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
			CommonUtils.hold(4);
			ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
			CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
					
			Actions act=new Actions(approvalDriver);
		    JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
		    RuleConfigPage.menu.click();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(RuleConfigPage.createRP, "Click", "", approvalDriver);
			CommonUtils.explicitWait(RuleConfigPage.RPFirstRow, "Click", "", approvalDriver);
			act.moveToElement(RuleConfigPage.RPFirstRow).click().build().perform();CommonUtils.hold(2);
			act.moveToElement(RuleConfigPage.RPFirstRowDelete).click().build().perform();
			CommonUtils.hold(4);
			CommonUtils.explicitWait(RuleConfigPage.deleteRP, "Click", "", approvalDriver);
			//CommonUtils.hold(10);
			RuleConfigPage.deleteRP.click();
			CommonUtils.explicitWait(RuleConfigPage.deleteRPOK, "Click", "", approvalDriver);

			RuleConfigPage.deleteRPOK.click();CommonUtils.hold(2);
			
	try
	{
		CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
		//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
		String errmsg=RuleConfigPage.networkErrorMsg.getText();
		if(!errmsg.equals("User with View Access Do Not have permission to Edit Policy"))
		{
			Log.info("Expected Error message is not displayed");
			Assert.assertTrue(false,"Expected Error message is not displayed");
		}
	}
	catch(Exception e)
	{
		Log.info("Rule should not save");
		Assert.assertTrue(false,"Rule should not save . Error should be thrown");

	}	
	}
public void DeleteDraftFlowByViewAccessUser(String user) throws Exception {
	String task="Account Payables Invoice Approvals"; 
	approvalDriver.findElement(By.xpath("//a[@id='_UIShome_HOa1']")).click();
	CommonUtils.hold(10);
	rules.logout(approvalDriver);
	rules.login(user, "Welcome1", approvalDriver);
	approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]")).click();
	Actions act=new Actions(approvalDriver);
	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+task+"']/ancestor::div[contains(@class,'oj-listview-cell-element')]//descendant::div[@class='oj-flex-item oj-sm-0 list-item']"))).build().perform();
	act.moveToElement(approvalDriver.findElement(By.xpath("//div[@title='"+task+"']/ancestor::div[contains(@class,'oj-listview-cell-element')]//descendant::div[@class='oj-flex-item oj-sm-0 list-item']//div//a"))).build().perform();
	RuleConfigPage.deleteDraftimg.click();
	CommonUtils.explicitWait(RuleConfigPage.deleteDraft, "Click", "", approvalDriver);
	RuleConfigPage.deleteDraft.click();
	CommonUtils.explicitWait(RuleConfigPage.deleteDraftOK, "Click", "", approvalDriver);
	RuleConfigPage.deleteDraftOK.click();
	CommonUtils.hold(10);
	
	try
	{
		CommonUtils.explicitWait(RuleConfigPage.networkErrorMsg, "Click", "", approvalDriver);
		//boolean isElementPresent= RuleConfigPage.networkErrorMsg.isDisplayed();
		String errmsg=RuleConfigPage.networkErrorMsg.getText();
		if(!errmsg.equals("User with View Access Do Not have permission to Edit Policy"))
		{
			Log.info("Expected Error message is not displayed");
			Assert.assertTrue(false,"Expected Error message is not displayed");
		}
	}
	catch(Exception e)
	{
		Log.info("Rule should not save");
		Assert.assertTrue(false,"Rule should not save . Error should be thrown");

	}	
	
}
    }
