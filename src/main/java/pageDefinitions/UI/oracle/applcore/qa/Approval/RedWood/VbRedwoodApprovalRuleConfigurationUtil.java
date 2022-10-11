package pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood;


import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.VbRedWoodRuleConfigPage;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.VbRedWoodRuleConfigUtil;


public class VbRedwoodApprovalRuleConfigurationUtil  extends GetDriverInstance {
	
	WebDriver approvalDriver;
	private VbRedWoodRuleConfigUtil ruleUtil;
	private VbRedWoodRuleConfigPage rules;
	Random r;
	//@FindBy(xpath ="//oj-input-text[contains(@class,'oj-fnd-approvals-step-node-title')]")
	@FindBy(xpath ="//oj-text-area[@id='title_singleStep']//parent::div[@class='oj-fnd-approvals-step-node-title-container']")

	public static WebElement waitstep;
/*	@Parameters({ "user", "pwd" })
	@BeforeMethod
	public void signIn(String username, String password) throws Exception {
			
			approvalDriver = getDriverInstanceObject();
			ruleUtil=new RedWoodRuleConfigUtil(approvalDriver);
			rules=new NewRedWoodRuleConfigPage(approvalDriver);
			rules.login(username,password, approvalDriver);
			r= new Random();
		}
	@AfterMethod
	public void logout() throws Exception {
			
			approvalDriver.quit();
		}*/	
	
	
	
	public VbRedwoodApprovalRuleConfigurationUtil(WebDriver driver)
	{
		approvalDriver=driver;
		ruleUtil=new VbRedWoodRuleConfigUtil(driver);
		rules=new VbRedWoodRuleConfigPage(driver);
		r= new Random();
	}
	

    @SuppressWarnings("static-access")
	//@Test(description = "This Method will create and deploy rule line amount less than invoice amount and approver as Terry Green",priority=1)
	public void ConfigUI(String testname,String username, String password) throws Exception {
    	WebDriverWait wait = new WebDriverWait(approvalDriver, 15);
    	Actions act=new Actions(approvalDriver);
    	JavascriptExecutor executor = (JavascriptExecutor)approvalDriver;
    	try {
    		approvalDriver.manage().window().maximize();
    		CommonUtils.hold(5);
		        Dimension initial_size = approvalDriver.manage().window().getSize();
		        int height =initial_size.getHeight();
		        int width=initial_size.getWidth();
		    	System.out.println("windows's height is "+height+" width is "+width);
		    	Reporter.log(" windows's height is "+height+" width is "+width);
		    	Log.info("windows's height is "+height+" width is "+width);
		    	//Dimension d = new Dimension(1032,776);
				//Resize the current window to the given dimension
		    	//approvalDriver.manage().window().setSize(d);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error in getting windows's height and width ");
        	Reporter.log("Error in getting windows's height and width ");
        	Log.info("Error in getting windows's height and width ");
    	}
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
    	
    	   String task="";
   		if(username.equalsIgnoreCase("finuser1"))
   			task="Account Payables Invoice Approvals"; 
   		else if (username.equalsIgnoreCase("cvrqst01"))
   			task="Requisition";
    	
    	String[] headermultiple = params[0].split("multiple");
		try {
//			rules.login(username,password, approvalDriver);
//			CommonUtils.hold(3);
			
			if(!(approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).isDisplayed()))
			{
				approvalDriver.get(RestCommonUtils.getExecutableHost()+"fscmUI/fnd/vp/approvalsconfiguration");
				//CommonUtils.hold(10);
				CommonUtils.waitForPageLoad(approvalDriver);
			}

		}
		catch(Exception e)
		{
			approvalDriver.get(RestCommonUtils.getExecutableHost()+"fscmUI/fnd/vp/approvalsconfiguration");
			CommonUtils.hold(10);
			CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")), "Click", "", approvalDriver);
			
		}
		
		try {
			System.out.println("Logged into ConfigUI application");
			
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
//					AAopeartors=valuesList[3].split("operator");
//					AAvalues=valuesList[4].split("value");
//					AAUsers=valuesList[5].split("approver");
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
							//CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
							ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
							//CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
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
								VbRedWoodRuleConfigPage.stepClose.click();
								
							}
							else if(data1[data1.length-1].contains("parallel"))
							{
								ruleUtil.createApprovalGroup(approvalDriver, PolicyName1,"In Parallel - Consensus", AAusers);
								policylist.add(PolicyName1.get(0));
								VbRedWoodRuleConfigPage.stepClose.click();
							}
							else
							{
								ruleUtil.createApprovalGroup(approvalDriver, PolicyName1,"In Parallel - First Responder Wins", AAusers);
								policylist.add(PolicyName1.get(0));
								VbRedWoodRuleConfigPage.stepClose.click();
							}
						
						}
						else
						{
							String user=approver.replace("user", "");
							policylist.add(user);
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
				//CommonUtils.hold(5);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")));
			approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
//			boolean isPresent = false;
//			try {
//				isPresent=NewRedWoodRuleConfigPage.NewStep.isDisplayed();
//				Log.info("Creating New Step");
//				if(isPresent)
//				{
//				NewRedWoodRuleConfigPage.NewStep.sendKeys(Keys.ENTER);
//				}
//			} catch (Exception e) {
//				Log.info("no need to press enter");
//				
//			}
			//CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
			ruleUtil.DeleteStepsAndNavigateToRulePage(approvalDriver);
			//CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
			if(params[5].contains("policy")) { 
				if (params[5].contains("AApolicy")) {
					
					ruleUtil.createApprovalAuthorityPolicy(approvalDriver, PolicyName, assignLimit,ReturnApproverFrom,AAattributes,valuesList1,noRuleMatchAction,policylist);
				}
	            else if (params[5].contains("SHpolicy")) {
					
					ruleUtil.createSupervisorRoutingPolicy(approvalDriver, PolicyName, SHconditions, SHattributes, Values1, noRuleMatchAction,params[8]);
					if(params[8].contains("ErrorinRP")) { 
						Log.info("Exiting the ConfigUI method");
						return;
					}
				}
				else {
					ruleUtil.createApprovalGroup(approvalDriver, PolicyName, params[7], policyusers);
				}
			
			}
			
			/*if(params[8].contains("ErrorinRP")) { 
	            NewRedWoodRuleConfigPage.menu.click();
				
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
				NewRedWoodRuleConfigPage.stepClose.click();		
				}*/
			
			//CommonUtils.explicitWait(waitstep, "Click", "", approvalDriver);
			ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
					if(params[8].contains("currency")) { 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'condition_r')]//span[text()='Condition']")));
				VbRedWoodRuleConfigPage.currencychange.click();
				VbRedWoodRuleConfigPage.currencychangeinput.sendKeys(Keys.CONTROL, Keys.chord("a"));
				CommonUtils.hold(2);
				VbRedWoodRuleConfigPage.currencychangeinput.sendKeys(Keys.BACK_SPACE);
				CommonUtils.hold(2);
				VbRedWoodRuleConfigPage.currencychangeinput.sendKeys(params[10]);
				((JavascriptExecutor) approvalDriver).executeScript("arguments[0].scrollIntoView(true);",approvalDriver.findElement(By.xpath("//span[text()='"+params[10]+"']")));
				CommonUtils.hold(2);
				act.moveToElement(approvalDriver.findElement(By.xpath("//span[text()='"+params[10]+"']"))).click().build().perform();
				}
			
		
			if(params[8].contains("action")) { 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id, 'condition_r')]//span[text()='Condition']")));	
				//approvalDriver.findElement(By.xpath("//input[@id='notificationMethod|input']/ancestor::div[@class='oj-text-field-container']//div//input")).click();
				approvalDriver.findElement(By.xpath("//input[@id='notificationMethod|input']")).click();
				CommonUtils.hold(1);		
				approvalDriver.findElement(By.xpath("//span[(text() ='Notify in Parallel - Consensus')]")).click();
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
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='noRuleMatch_text']")));	
				VbRedWoodRuleConfigPage.NoRuleMatch.click();
				CommonUtils.hold(1);
				VbRedWoodRuleConfigPage.IfNoRulesMatch.click();
				CommonUtils.hold(1);		
				approvalDriver.findElement(By.xpath("//span[(text() ='"+params[9]+"')]")).click();
				CommonUtils.hold(1);
				
			}
			ruleUtil.DeployRule(approvalDriver);
			Reporter.log("Rule Deployed");
			CommonUtils.hold(5);
				}
			}
		}catch(Exception configUIException) {
			System.out.println("Exception in ConfigUI : "+configUIException.getMessage());
			configUIException.printStackTrace();
			throw configUIException;
		}
//	finally{
//			CommonUtils.waitForPageLoad(approvalDriver);
//			CommonUtils.hold(3);
//			System.out.println("Logging out from configUI");
//			rules.logout(approvalDriver);
//			System.out.println("Logged out from configui");
//		}
	}
	
	//@Test(description = "This Method will approve the rule In Parallel - First Responder Win via routig policy to approve",priority=12)
	public void updateRule(WebDriver driver) throws Exception {
		Actions act=new Actions(driver);
		String task="Account Payables Invoice Approvals"; 
		approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).click();
		CommonUtils.hold(10);
		ruleUtil.EditStepsAndNavigateToRulePage(approvalDriver);
		CommonUtils.hold(10);	
		approvalDriver.findElement(By.xpath("//div[@id='RULE_1_action0']")).click();
		//NewRedWoodRuleConfigPage.actionForApprove.click();
		act.moveToElement(VbRedWoodRuleConfigPage.actionForApprove).click().build().perform();
		CommonUtils.explicitWait(VbRedWoodRuleConfigPage.sendForApproval, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		VbRedWoodRuleConfigPage.sendForApproval.click();
		
		CommonUtils.explicitWait(VbRedWoodRuleConfigPage.to, "Click", "", approvalDriver);
		CommonUtils.hold(2);
		VbRedWoodRuleConfigPage.to.click();
		CommonUtils.explicitWait(VbRedWoodRuleConfigPage.userValue, "Click", "", approvalDriver);
		VbRedWoodRuleConfigPage.userValue.click();
		CommonUtils.hold(2);
		CommonUtils.explicitWait(VbRedWoodRuleConfigPage.Users2, "Click", "", approvalDriver);
		VbRedWoodRuleConfigPage.Users2.click();CommonUtils.hold(2);
		CommonUtils.explicitWait(VbRedWoodRuleConfigPage.UsersRolesInput, "Click", "", approvalDriver);
		VbRedWoodRuleConfigPage.UsersRolesInput.sendKeys("Chris Black");
		CommonUtils.hold(2);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//li[@class = 'oj-listbox-results-depth-0 oj-listbox-result oj-listbox-result-selectable']")), "Click", "", driver);
		CommonUtils.hold(5);
		
		CommonUtils.waitForVisibilityOfElementLocated(By.xpath("//span[text() = 'Chris Black']"), 20, driver);
		CommonUtils.hold(2);
		driver.findElement(By.xpath("//span[text() = 'Chris Black']")).click();
		CommonUtils.hold(2);
//		NewRedWoodRuleConfigPage.userValue.click();	
//			CommonUtils.explicitWait(NewRedWoodRuleConfigPage.Users2, "Click", "", approvalDriver);
//				CommonUtils.hold(2);
//				NewRedWoodRuleConfigPage.Users2.click();
//				CommonUtils.hold(2);
//				CommonUtils.explicitWait(NewRedWoodRuleConfigPage.userValue1, "Click", "", approvalDriver);
////				NewRedWoodRuleConfigPage.userValue1.click();
////				CommonUtils.hold(4);
////				NewRedWoodRuleConfigPage.userValue1.sendKeys(Keys.chord("k"));
//				NewRedWoodRuleConfigPage.userValueInput.click(); 
//				CommonUtils.hold(2); 
//				NewRedWoodRuleConfigPage.userValueInput.clear();
//				CommonUtils.hold(2); 
//				NewRedWoodRuleConfigPage.userValueInput.sendKeys("Chris Black");
//				approvalDriver.findElement(By.xpath("//oj-highlight-text[@text='Chris Black']")).click();
//				CommonUtils.explicitWait(NewRedWoodRuleConfigPage.close, "Click", "", approvalDriver);
//				CommonUtils.hold(5);
		CommonUtils.explicitWait(VbRedWoodRuleConfigPage.close, "Click", "", driver);
				VbRedWoodRuleConfigPage.close.click();
				CommonUtils.hold(5);
				ruleUtil.DeployRule(approvalDriver);

	}

  }//End of RedwoodApprovalRuleConfigurationTest class
