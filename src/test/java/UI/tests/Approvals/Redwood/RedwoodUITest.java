package UI.tests.Approvals.Redwood;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedwoodApprovalRuleConfigurationUtil;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.ReportGenerator;
import io.restassured.response.Response;
import pageDefinitions.REST.Approvals.ApprovalsRESTUtils;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigPage;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigUtil;




public class RedwoodUITest extends GetDriverInstance{

	Map<String, Map<String, Object>> restInfo;
	Map<String, Map<String, String>> parsedResponse;
	String executableHost = RestCommonUtils.getExecutableHost();
	static String invoiceId;
	String invoiceName,invoiceNumber,invoiceAmount,uniqueid;
	static Map<String,String> header = new HashMap<String, String>();
	String invoiceStatusCheckService;
	String getWorkListTasksIdService;
	static String invoiceApprovalStatus;
	Properties fileProperties;
	FileReader fileRead;
	XSSFWorkbook workbook;
	FileInputStream fileIpStream;
	private FndRedWoodRuleConfigUtil ruleUtil;
	WebDriver approvalDriver;
	private FndRedWoodRuleConfigPage rules;
	private XSSFSheet invoiceDataSheet;
	private FndRedwoodApprovalRuleConfigurationUtil approvalTest;
	static String username,password;

	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void getInfo(String usern,String passwd) {
		System.out.println("Parsing Approvals JSON");
		try {
			username = usern;
			password = passwd;
			restInfo = RestCommonUtils.parseJson("approvalFlow", "./src/main/java/ConfigurationResources/REST/ApprovalsFlow.json");
			System.out.println("Completed JSON parsing");
			invoiceStatusCheckService = executableHost+restInfo.get("getInvoiceStatus").get("RequestURL");
			getWorkListTasksIdService = executableHost+restInfo.get("listAssginedInvoices").get("RequestURL");

			uniqueid = CommonUtils.uniqueId();
			fileIpStream = new FileInputStream(new File(GetDriverInstance.approvalsDataFile));
			if(fileIpStream != null) {
				workbook = new XSSFWorkbook(fileIpStream);
			}else
				System.out.println("File doesn't exist");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Get sheet from Excel
		if(workbook != null) {
			System.out.println("Sheet name -> "+workbook.getSheetName(0));
			invoiceDataSheet = workbook.getSheetAt(0);
		}
		else
		{
			Log.info("workbook is null");
			Assert.assertTrue(false, "workbook is null");
		}
		//Login to config ui
		approvalDriver = getDriverInstanceObject();
		ruleUtil=new FndRedWoodRuleConfigUtil(approvalDriver);
		rules=new FndRedWoodRuleConfigPage(approvalDriver);
		approvalTest=new FndRedwoodApprovalRuleConfigurationUtil(approvalDriver);
		
		try {
		//	rules.login(username,password, approvalDriver);
			CommonUtils.hold(10);
			Random r= new Random();
		} 
		catch (Exception e) {
			Log.info("Login Failed");
			Assert.assertTrue(false, "Login Failed");
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void checkMainPage(Method method) throws Exception {
	if(!method.getName().contains("Flow")) {
		String task="Account Payables Invoice Approvals"; 
		Boolean isPresent=false;
	
		rules.login(username,password, approvalDriver);
		CommonUtils.hold(3);
		CommonUtils.waitForPageLoad(approvalDriver);
				
				try {
					
					isPresent=approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")).isDisplayed();
				}
				catch(Exception e)
				{
					approvalDriver.get(RestCommonUtils.getExecutableHost()+"fscmUI/fnd/vp/approvalsconfiguration");
					CommonUtils.hold(10);
							CommonUtils.explicitWait(approvalDriver.findElement(By.xpath("//div[contains(@title,'"+task+"')]/ancestor::div[contains(@class,'oj-listview-cell-element')]/descendant::a[@class='oj-flex-item oj-sm-6 list-item'][1]")), "Click", "", approvalDriver);
					//CommonUtils.hold(10);
				}
	

		}
	}
		
	@Test(description = "method to create rule for SendApproval to User", priority = 1)
	public void ruletoRouteInvoicesToUser() throws Exception{
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
			Reporter.log("Rule for SendApproval to User creation begins");
			approvalTest.ConfigUI("approveviauser",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to route invoices with approvers as users for Approvals", priority = 2,dependsOnMethods = {"ruletoRouteInvoicesToUser"} )
	public void userapprovalFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(1);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();
						Reporter.log("Reading data from data file");
						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }

						Reporter.log("Reading data from data file completed");

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();

						CommonUtils.hold(10);
						System.out.println("Get InvoiceApproval Status ");
						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
						getInvoiceApprovalsStatus();

						System.out.println("Processing of Invoice Approval Wrokflow ");
						Reporter.log("Processing of Invoice Approval Wrokflow ");
						executeInvoiceAction(action,users);

						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
					}
					
				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
	
	@Test(description = "Method to create rule for routing invoices to users based on Role",priority=3)
	public void ruletoRouteInvoicesToRole() throws Exception {

			Reporter.log("Rule for SendApproval to Role creation begins");
			approvalTest.ConfigUI("approveviarole",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to route invoices for users based on role",priority=4,dependsOnMethods = {"ruletoRouteInvoicesToRole"})
	public void approveFlowRouteToRole() throws Exception {

		try {
			String action="APPROVE";
			List<String>users= new ArrayList<String>();
			users.add("Role");
			users.add("ORA_GTG_APPLICATION_CONTROL_MANAGER_JOB");
			//users.add("APP_CONTROL_MGR");
			Row dataRow = invoiceDataSheet.getRow(2);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();

				Reporter.log("Reading data from data file");

				while (dataRowCell.hasNext()) 
                {
                    Cell rowCell = dataRowCell.next();

                  // System.out.println("rowCellType : "+rowCell.getCellType());

                   if(rowCell.getColumnIndex() == 1)
                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
                   if(rowCell.getColumnIndex() == 2) 
                	   invoiceAmount = formatter.formatCellValue(rowCell);
                }

				Reporter.log("Reading data from data file completed");

				System.out.println("Invoice Number :"+invoiceNumber);
				System.out.println("Invoice Amount :"+invoiceAmount);

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();
				CommonUtils.hold(10);
				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();
//				
				boolean isInvoiceAssignedToAllUser=verifyIfInvoiceAssignedToAllUserInRole("ORA_GTG_APPLICATION_CONTROL_MANAGER_JOB");
				if(!isInvoiceAssignedToAllUser)
				{
					Assert.assertTrue(false, "Invoice Not assigned to all user in expected role");

				}
				List <String>RoleUsers=ApprovalsRESTUtils.getUsersFromRole("ORA_GTG_APPLICATION_CONTROL_MANAGER_JOB");
				users.add(RoleUsers.get(0));
				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);

				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");

			}
			
				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
    
	@Test(description ="Method to create rule to route invoices to approvers Serially",priority=5)
	public void ruleRouteToSerialApprovalGroup() throws Exception { 
	    List<String>users=new ArrayList<String>(); users.add("Terry Green");
	    users.add("Connor Horton");
	  
	        Reporter.log("Rule for SerailGroup Approvers creation begins");
	        approvalTest.ConfigUI("serialnotification",username,password);
	        Reporter.log("Rule created and deployed"); 
	}
	
	@Test(description = "Method will route invoices serially to all Approvers for Approvals",priority=6,dependsOnMethods = {"ruleRouteToSerialApprovalGroup"})
	public void serialApprovalsApprovalFlow() throws Exception {
		List<String> users=new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");	
		try {
						String action="APPROVE";
						Row dataRow = invoiceDataSheet.getRow(3);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						Reporter.log("Reading data from data file");
						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();

							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");
							System.out.println("Invoice Number :"+invoiceNumber);
							System.out.println("Invoice Amount :"+invoiceAmount);

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoice(invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);

							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");

						}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
		}

	@Test(description = "Method with route invoices serially and rejected by one of the Approver",priority=7,dependsOnMethods = {"ruleRouteToSerialApprovalGroup"})
	public void serialApprovalRejectFlow() throws Exception {
		List<String> users=new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");	
		try {
						String action="REJECT";
						Row dataRow = invoiceDataSheet.getRow(10);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();

							Reporter.log("Reading data from data file");

							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");

							System.out.println("Invoice Number :"+invoiceNumber);
							System.out.println("Invoice Amount :"+invoiceAmount);

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoice(invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);

							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

						}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
	
	@Test(description = "Method to create rule based on First Responder Wins ",priority=8)
	public void ruleForParallelFirstResponseWins() throws Exception {

			Reporter.log("Rule to route invoices for parellel approvers and first responder wins creation begins");
			approvalTest.ConfigUI("parallefirstresponderwin",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to route invoices to parallel approvers for Approval, WorkFlow is in Approve status if approved by first responder",priority=9,dependsOnMethods = {"ruleForParallelFirstResponseWins"})
	public void parallelFirstResponseWinsApprovalFlow() throws Exception {
		List<String> retrivedusers=new ArrayList<String>();
		List<String> users=new ArrayList<String>();
			users.add("Terry Green");
			users.add("Connor Horton");	
			try {
							String action="APPROVE";

							Row dataRow = invoiceDataSheet.getRow(4);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();

								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());
				                    Reporter.log("Reading data from data file to process invoice workflow");

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Get All Assigned Users list ");								
								Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
								retrivedusers.addAll(listofApprovers.values());

								if(!retrivedusers.containsAll(users))									
								{
									Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
									Reporter.log("Invoice is not assigned to all Expected users");

								}

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}

	@Test(description = "Method to route invoices to parallel approvers for Approval, WorkFlow is rejected if first responder rejects",priority=10,dependsOnMethods = {"ruleForParallelFirstResponseWins"})
	public void parallelFirstResponseWinsRejectFlow() throws Exception {
		List<String> retrivedusers=new ArrayList<String>();
		List<String> users=new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");	
			try {
							String action="REJECT";
							Row dataRow = invoiceDataSheet.getRow(6);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();

								Reporter.log("Reading data from data file to process invoice workflow");

								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Get All Assigned Users list ");								
								Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
								retrivedusers.addAll(listofApprovers.values());

								if(!retrivedusers.containsAll(users))									
								{
									Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
									Reporter.log("Invoice is not assigned to all Expected users");

								}

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
			
	@Test(description = "Method to create rule for routing invoice to parallel approvers",priority=11)
	public void ruleForParallelConsensusApproval() throws Exception {

			Reporter.log("Rule to route invoices for parellel approvers creation begins");
			approvalTest.ConfigUI("parallelconsesus",username,password);
			Reporter.log("Rule created and deployed");

	}

	@Test(description = "Method to route invoices to parallel approvers for Approval, WorkFlow is in Approve status if all approvers approves ",priority=12,dependsOnMethods = {"ruleForParallelConsensusApproval"})
	public void parallelConsensusApprovalFlow() throws Exception {
		List<String> users=new ArrayList<String>();
		List<String> retrivedusers=new ArrayList<String>();

		users.add("Terry Green");
		users.add("Connor Horton");	
					try {
							String action="APPROVE";
							Row dataRow = invoiceDataSheet.getRow(5);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file to process invoice workflow");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
								Reporter.log("Reading data from data file completed");
								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Get All Assigned Users list ");								
								Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
								retrivedusers.addAll(listofApprovers.values());

								if(!retrivedusers.containsAll(users))									
								{
									Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
									Reporter.log("Invoice is not assigned to all Expected users");

								}

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}

	@Test(description = "Method to route invoices to parallel approvers, if an approver reject then invoice will be rejected",priority=13,dependsOnMethods = {"ruleForParallelConsensusApproval"})
	public void parallelConsensusRejectFlow() throws Exception {
		List<String> users=new ArrayList<String>();
		List<String> retrivedusers=new ArrayList<String>();

			users.add("Terry Green");
			users.add("Connor Horton");
				try {
							String action="REJECT";

							Row dataRow = invoiceDataSheet.getRow(7);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();

								Reporter.log("Reading data from data file to process invoice workflow");

								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();


								System.out.println("Get All Assigned Users list ");								
								Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
								retrivedusers.addAll(listofApprovers.values());

								if(!retrivedusers.containsAll(users))									
								{
									Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
									Reporter.log("Invoice is not assigned to all Expected users");

								}

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
			
	@Test(description = "Method will create rule in X currency denomination to process workflow for invoices created in Y denomination ",priority=14)
	public void ruleForCurrencyTypeApproval() throws Exception {

			Reporter.log("Rule to route invoices based on currency denomination conversion creation begins");
			approvalTest.ConfigUI("steplevelcurrency",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to route invoices created in Y currency denomination for approval",priority=15,dependsOnMethods = {"ruleForCurrencyTypeApproval"})
	public void currencyTypeApprovalFlow() throws Exception {
		List<String> users=new ArrayList<String>();
		users.add("Terry Green");
			try {
							String action="APPROVE";			
							Row dataRow = invoiceDataSheet.getRow(8);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();

								Reporter.log("Reading data from data file to process invoice workflow");

								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}
				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
			}
	
	@Test(description = "Method to create rule for auto approve invoices",priority=16)
	public void autoApproveRule() throws Exception {

			Reporter.log("Rule for auto approve invoices creation begins");
			approvalTest.ConfigUI("autoapprove",username,password);
			Reporter.log("Rule created and deployed");
	}
    
	@Test(description = "Method to auto approve invoice based on rule defined",priority=17,dependsOnMethods = {"autoApproveRule"})
	public void autoApproveApprovalFlow() throws Exception {
				try {
							Row dataRow = invoiceDataSheet.getRow(9);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();

								Reporter.log("Reading data from data file to process invoice workflow");

								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
							//	getInvoiceApprovalsStatus();
								CommonUtils.hold(10);
								System.out.println("InvoiceApproval Status ");
								invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}
				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}

	@Test(description = "Method to create rule for assigning invoice to requestor", priority = 18)
	public void ruleApproverAsAttribute()  throws Exception{

			Reporter.log("Rule to route invoice for requestor creation begins");
			approvalTest.ConfigUI("approveviaRequester",username,password);
			Reporter.log("Rule created and deployed");
	}
	
    @Test(description = "Method for routing invoice to requestor for approval", priority = 19,dependsOnMethods = {"ruleApproverAsAttribute"} )
	public void approverAsAttributeApprovalFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Mary Johnson");
		//users.add("attribute");
		try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(11);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();

						Reporter.log("Reading data from data file to process invoice workflow");

						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }
						Reporter.log("Reading data from data file completed");
						System.out.println("Invoice Number :"+invoiceNumber);
						System.out.println("Invoice Amount :"+invoiceAmount);

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();
						System.out.println("Get InvoiceApproval Status ");
						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
						getInvoiceApprovalsStatus();

						CommonUtils.hold(15);

						System.out.println("Processing of Invoice Approval Wrokflow ");
						Reporter.log("Processing of Invoice Approval Wrokflow ");
						executeInvoiceAction(action,users);

						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
					}

			}catch(Exception gEDE) {
				System.out.println("Exception in readExcel: "+gEDE.getMessage());
				gEDE.printStackTrace();
			}
	}

	@Test(description = "Method to create rule for auto rejecting invoices", priority = 20)
	public void autoRejectRule()  throws Exception{

			Reporter.log("Rule for auto rejecting invoices creation begins");
			approvalTest.ConfigUI("autoreject",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to auto reject invoice based on rule defined", priority = 21,dependsOnMethods = {"autoRejectRule"} )
	public void autoRejectWorkFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Mary Johnson");
		//users.add("attribute");
		try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(12);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();

						Reporter.log("Reading data from data file to process invoice workflow");

						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }
						Reporter.log("Reading data from data file completed");

						System.out.println("Invoice Number :"+invoiceNumber);
						System.out.println("Invoice Amount :"+invoiceAmount);

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();
						
//						System.out.println("Get InvoiceApproval Status ");
//						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
//						getInvoiceApprovalsStatus();


						System.out.println("InvoiceApproval Status ");
						invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");					}

		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to create rule for sending FYI notification to User post workflow Approval by group of Approvers", priority = 22)
	public void approvalGroupAndFYINotificationRule() throws Exception {

			Reporter.log("Rule to send FYI notiifcation to user post workflow apporve by group of Approvers creation begins");
			approvalTest.ConfigUI("multiplerules",username,password);
			Reporter.log("Rule created and deployed");
	}

	@Test(description = "Method to sent FYI notification to User post workflow Approval by group of Approvers ", priority = 23,dependsOnMethods = {"approvalGroupAndFYINotificationRule"} )
	public void approvalGroupAndFYINotificationFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
		try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(13);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();

						Reporter.log("Reading data from data file to process invoice workflow");

						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }

						Reporter.log("Reading data from data file completed");

						System.out.println("Invoice Number :"+invoiceNumber);
						System.out.println("Invoice Amount :"+invoiceAmount);

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();
									System.out.println("Get InvoiceApproval Status ");
						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
						getInvoiceApprovalsStatus();

						System.out.println("Processing of Invoice Approval Wrokflow ");
						Reporter.log("Processing of Invoice Approval Wrokflow ");
						executeInvoiceAction(action,users);

						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						//FYI Information need to check here	for Linda Wright
						header.put("Content-Type", "application/json");

						String getWorkListIdServicePayload = restInfo.get("listAssginedInvoices").get("Payloads").toString();
						String workFlowId = ApprovalsRESTUtils.workListInvoiceId(getWorkListTasksIdService, getWorkListIdServicePayload, header, invoiceName, "finuser4");
						header.clear();

						if(workFlowId!= null)
							Reporter.log("Sent FYI notification to <Linda Wright> post worklfow Approval");

						else
									{
										Reporter.log("Sent FYI notification to <Linda Wright> failed post worklfow Approval");
										Assert.assertFalse(true,"FYI not send to Linda Wright");
									}
						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is WorkFlow Approved");					}

			}catch(Exception gEDE) {
				System.out.println("Exception in readExcel: "+gEDE.getMessage());
				gEDE.printStackTrace();
			}
	}
	
	@Test(description = "Method to create rule set to step level as 'Notify to Parallel Consequence' ", priority = 24)
	public void CreateRuleForSteplevelparallelcons()  throws Exception{

			Reporter.log("Rule to chcek functionality of notify in parallel-Consequence at step level");
			approvalTest.ConfigUI("steplevelparallelcons",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check Approve flow with step level as 'Notify to Parallel Consequence' rule",priority=25,dependsOnMethods = {"CreateRuleForSteplevelparallelcons"})
	public void NotifyInparallelConsensusApprovalFlow() throws Exception {
		List<String> retrivedusers=new ArrayList<String>();
			List<String> users=new ArrayList<String>();
			users.add("Terry Green");
			users.add("Connor Horton");	
			users.add("Linda Wright");	
						try {
								String action="APPROVE";
								Row dataRow = invoiceDataSheet.getRow(14);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file to process invoice workflow");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }
									Reporter.log("Reading data from data file completed");
									System.out.println("Invoice Number :"+invoiceNumber);
									System.out.println("Invoice Amount :"+invoiceAmount);

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice(invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();
									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Get All Assigned Users list ");								
									Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									retrivedusers.addAll(listofApprovers.values());

									if(!retrivedusers.containsAll(users))									
									{
										Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
										Reporter.log("Invoice is not assigned to all Expected users");

									}


									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

								}

					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
					}
	}
	
	@Test(description = "Method to check reject flow with step level as 'Notify to Parallel Consequence' rule",priority=26,dependsOnMethods = {"CreateRuleForSteplevelparallelcons"})
	public void NotifyInparallelConsensusRejectFlow() throws Exception {
	List<String> retrivedusers=new ArrayList<String>();
		List<String> users=new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");	
		users.add("Linda Wright");	
					try {
							String action="REJECT";
							Row dataRow = invoiceDataSheet.getRow(15);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file to process invoice workflow");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
								Reporter.log("Reading data from data file completed");
								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
										System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Get All Assigned Users list ");								
								Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
								retrivedusers.addAll(listofApprovers.values());

								if(!retrivedusers.containsAll(users))									
								{
									Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
									Reporter.log("Invoice is not assigned to all Expected users");

								}


								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}

	@Test(description = "Method to create rule for skip remaining rule ", priority = 27)
	public void CreateRuleForSkipRemainingRule()  throws Exception{

			Reporter.log("Rule to check functionality of skip reminaing rule");
			approvalTest.ConfigUI("skipremainingrules",username,password);
			Reporter.log("Rule created and deployed");
	}
	
    @Test(description = "Method to check flow with rule as 'Skip Remaining Rule' ",priority=28,dependsOnMethods = {"CreateRuleForSkipRemainingRule"})
	public void SkipRemainingRuleApprovalFlow() throws Exception {
	    List<String> users=new ArrayList<String>();
		//users.add("Terry Green");
		users.add("Linda Wright");	
		try {
							String action="APPROVE";
							Row dataRow = invoiceDataSheet.getRow(16);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file to process invoice workflow");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
								Reporter.log("Reading data from data file completed");
								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();


								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
	
	@Test(description = "Method to create rule with multiple attributes", priority = 29)
	public void CreateMultipleRule1()  throws Exception{

			Reporter.log("Rule to check functionality of skip reminaing rule");
			approvalTest.ConfigUI("multiplerules1",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to check flow based on multiple attributes",priority=30,dependsOnMethods = {"CreateMultipleRule1"})
	public void MultipleAttributeApprovalFlow1() throws Exception {
	    List<String> users=new ArrayList<String>();
		//users.add("Terry Green");
		users.add("Chris Black");	
		try {
							String action="APPROVE";
							Row dataRow = invoiceDataSheet.getRow(17);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file to process invoice workflow");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
								Reporter.log("Reading data from data file completed");
								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();

								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();


								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}

	@Test(description = "Method to check negetiveflow based on multiple attributes",priority=31,dependsOnMethods = {"CreateMultipleRule1"})
	public void MultipleAttributeApprovalFlow1_1() throws Exception {
	    List<String> users=new ArrayList<String>();
		//users.add("Terry Green");
		users.add("Chris Black");	
		try {
							String action="APPROVE";
							Row dataRow = invoiceDataSheet.getRow(23);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file to process invoice workflow");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
								Reporter.log("Reading data from data file completed");
								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();

								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");

								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

								Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}
	
	@Test(description = "Method to create rule with multiple attributes", priority = 32)
	public void CreateMultipleRule2()  throws Exception{

			Reporter.log("Rule to check functionality of skip reminaing rule");
			approvalTest.ConfigUI("multiplerules2",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to check flow based on multiple attribute Rules with conditions on multiple attributes from parent and child ",priority=33,dependsOnMethods = {"CreateMultipleRule2"})
	public void MultipleAttributeApprovalFlow2() throws Exception {
		List<String> users=new ArrayList<String>();
		users.add("Connor Horton");
		users.add("Chris Black");	
			try {
								String action="APPROVE";
								Row dataRow = invoiceDataSheet.getRow(18);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file to process invoice workflow");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }
									Reporter.log("Reading data from data file completed");
									System.out.println("Invoice Number :"+invoiceNumber);
									System.out.println("Invoice Amount :"+invoiceAmount);

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice(invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();
									CommonUtils.hold(10);
									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();


									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

								}

					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
					}
	} 

	@Test(description = "Method to create rule for multiple attributes", priority = 34)
	public void CreateMultipleRule3()  throws Exception{

			Reporter.log("Rule to check functionality of skip reminaing rule");
			approvalTest.ConfigUI("multiplerules3",username,password);
			Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to check flow based on multiple attribute (Rules with conditions on multiple attributes of parent and child) ",priority=35,dependsOnMethods = {"CreateMultipleRule3"})
	public void MultipleAttributeApprovalFlow3() throws Exception {
		List<String> users=new ArrayList<String>();
		users.add("Chris Black");	
			try {
								String action="APPROVE";
								Row dataRow = invoiceDataSheet.getRow(19);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file to process invoice workflow");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }
									Reporter.log("Reading data from data file completed");
									System.out.println("Invoice Number :"+invoiceNumber);
									System.out.println("Invoice Amount :"+invoiceAmount);

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice(invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();
									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();


									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

								}

					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
					}
				}
	@Test(description = "Method to create rule for invouce less than and greater than some amount ", priority = 36) 
	public void sendFYIRule() throws Exception {

		    Reporter.log("Rule to check functionality of skip reminaing rule");
		    approvalTest.ConfigUI("sendfyi",username,password);
		    Reporter.log("Rule created and deployed");
	}

	@Test(description = "Method to check flow for invoice routing",priority=37,dependsOnMethods = {"sendFYIRule"})
	public void CheckInvoiceRoutingApprovalFlow() throws Exception {
		List<String> users=new ArrayList<String>();
		List<String> retrivedusers=new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");		
			try {
								String action="APPROVE";
								Row dataRow = invoiceDataSheet.getRow(20);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file to process invoice workflow");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }
									Reporter.log("Reading data from data file completed");
									System.out.println("Invoice Number :"+invoiceNumber);
									System.out.println("Invoice Amount :"+invoiceAmount);

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice(invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();
									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Get All Assigned Users list ");								
									Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									retrivedusers.addAll(listofApprovers.values());

									if(!retrivedusers.containsAll(users))									
									{
										Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
										Reporter.log("Invoice is not assigned to all Expected users");

									}

									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+">");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");


									header.put("Content-Type", "application/json");

									String getWorkListIdServicePayload = restInfo.get("listAssginedInvoices").get("Payloads").toString();
									String workFlowId = ApprovalsRESTUtils.workListInvoiceId(getWorkListTasksIdService, getWorkListIdServicePayload, header, invoiceName, "finuser4");
									header.clear();

									if(workFlowId!= null)
										Reporter.log("Sent FYI notification to <Linda Wright> post worklfow Approval");

									else
									{
										Reporter.log("Sent FYI notification to <Linda Wright> failed post worklfow Approval");
										Assert.assertFalse(true,"FYI not send to Linda Wright");
									}
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

								}

					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
					}
	}

	@Test(description = "Method to check flow for invoice routing ",priority=38,dependsOnMethods = {"sendFYIRule"})
	public void CheckInvoiceRoutingApprovalFlow2() throws Exception {
			try {
							String action="APPROVE";
							Row dataRow = invoiceDataSheet.getRow(21);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file to process invoice workflow");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
								Reporter.log("Reading data from data file completed");
								System.out.println("Invoice Number :"+invoiceNumber);
								System.out.println("Invoice Amount :"+invoiceAmount);

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
									System.out.println("InvoiceApproval Status ");
								invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

							}

				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
				}
	}

@Test(description = "Method to edit existing rule ", priority = 39,dependsOnMethods = {"sendFYIRule"}) 
	public void UpdateRule()  throws Exception{

			Reporter.log(" Method to edit exiting rule ");
		    approvalTest.updateRule(approvalDriver); Reporter.log("Rule created and deployed");
	}
		
	@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 40,dependsOnMethods = {"UpdateRule"} )
	public void EditRuleApprovalFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Chris Black");
			try {
						String action="APPROVE";

						Row dataRow = invoiceDataSheet.getRow(22);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();
							Reporter.log("Reading data from data file");
							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoice(invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();

							CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);

							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
						}
				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
			}
	}

	@Test(description = "Method to create rule for Total dist amount (summation attribute) ", priority = 41) 
	public void paymentAttributeRule() throws Exception{

			Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
			approvalTest.ConfigUI("paymentAttributeRule",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 42,dependsOnMethods = {"paymentAttributeRule"} )
	public void paymentAttributeApprovalFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Chris Black");
			try {
						String action="APPROVE";

						Row dataRow = invoiceDataSheet.getRow(24);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();
							Reporter.log("Reading data from data file");
							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoice(invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();

							CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);

							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
						}
				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
			}
		}
		
    @Test(description = "Method to create rule for Total dist amount (summation attribute) ", priority = 43) 
	public void TotalDistAmountRule() throws Exception{

				  Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
				  approvalTest.ConfigUI("TotalDistAmountRule",username,password);
				  Reporter.log("Rule created and deployed");
	}
    
	//Need To implement Approval flow to have distribution amount
	@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 44,dependsOnMethods = {"TotalDistAmountRule"} )
	public void TotalDistAmountRuleApprovalFlow() {
		List<String>users= new ArrayList<String>();
	    users.add("Chris Black");
				try {
							String action="APPROVE";

							Row dataRow = invoiceDataSheet.getRow(25);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
								createInvoice(invoiceNumber,invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();

								CommonUtils.hold(10);
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							}
					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
				}
	}

	@Test(description = "Method to create rule based on no rule match condition ", priority = 45) 
	public void NoRuleMatchApprovRule() throws Exception{
			
			Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
			approvalTest.ConfigUI("noruleapprove",username,password);
			Reporter.log("Rule created and deployed");
	}	

    @Test(description = "Method to route invoices to auto approve if no rule match", priority = 46,dependsOnMethods = {"NoRuleMatchApprovRule"} )
	public void NoRuleMatchApproveFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Chris Black");
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(26);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice(invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									CommonUtils.hold(10);
									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									System.out.println("InvoiceApproval Status ");
									invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

								}
						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}
	}
	
	@Test(description = "Method to create rule based on no rule match condition then auto reject", priority = 47) 
	public void NoRuleMatchRejectRule() throws Exception{
					
			Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
			approvalTest.ConfigUI("noruleReject",username,password);
			Reporter.log("Rule created and deployed");
	}				
				
    @Test(description = "Method to route invoices to auto approve if no rule match", priority = 48,dependsOnMethods = {"NoRuleMatchRejectRule"} )
	public void NoRuleMatchRejectFlow() {
			List<String>users= new ArrayList<String>();
			users.add("Chris Black");
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(27);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice(invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();


									System.out.println("InvoiceApproval Status ");
									invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");					}


						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}
	}
	
	@Test(description = "Method to create rule based on Super visor RP Start With manager User Name", priority = 53) 
	public void alwaysRule() throws Exception{
		
		    Reporter.log(" Method to create always rule");
			approvalTest.ConfigUI("always",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to route invoices according to alway flow", priority = 54,dependsOnMethods = {"alwaysRule"} )
	public void alwaysApprovalFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Chris Black");
		try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(30);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();
						Reporter.log("Reading data from data file");
						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }

						Reporter.log("Reading data from data file completed");

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();

						System.out.println("Get InvoiceApproval Status ");
						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
						getInvoiceApprovalsStatus();

						System.out.println("Processing of Invoice Approval Wrokflow ");
						Reporter.log("Processing of Invoice Approval Wrokflow ");
						executeInvoiceAction(action,users);

						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
					}	


			}catch(Exception gEDE) {
				System.out.println("Exception in readExcel: "+gEDE.getMessage());
				gEDE.printStackTrace();
		}
	}

	@Test(description = "Method to create rule to route invoices according to AA",priority=55)
	public void ApprovalAuthorityLimitRule1() throws Exception {
		  
		    Reporter.log("Rule for SerailGroup Approvers creation begins");
		    approvalTest.ConfigUI("ApprovalAuthorityLimitRule1",username,password);
		    Reporter.log("Rule created and deployed");
	}
	

    @Test(description = "Method to check flow to route invoices according to AA", priority = 56,dependsOnMethods = {"ApprovalAuthorityLimitRule1"} )
	public void ApprovalAuthorityLimitRule1Flow() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		//users.add("Chris Black");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(31);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);
				
				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}

				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to check flow of invoices routing according to AA", priority = 57,dependsOnMethods = {"ApprovalAuthorityLimitRule1"} )
	public void ApprovalAuthorityLimitRule1Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(32);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);
				
				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}

				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to check flow of invoices routing according to AA", priority = 58,dependsOnMethods = {"ApprovalAuthorityLimitRule1"} )
	public void ApprovalAuthorityLimitRule1Flow3() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(33);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);

				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}
				
				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to check flow of invoices routing according to AA", priority = 59,dependsOnMethods = {"ApprovalAuthorityLimitRule1"} )
	public void ApprovalAuthorityLimitRule1Flow4() {

		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(34);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("InvoiceApproval Status ");
				invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");					

			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}

	//test case with null/blank requester
	@Test(description = "Method to check flow of invoices routing according to AA", priority = 60,dependsOnMethods = {"ApprovalAuthorityLimitRule1"} )
	public void ApprovalAuthorityLimitRule1Flow5() 
	{
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(35);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice1("InvoiceNoRequester",invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();
				CommonUtils.hold(10);
				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");

				invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");					

			}
		}
		catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}

	}

	@Test(description = "Method to create rule to route invoices according to AA",priority=61)
	public void ApprovalAuthorityLimitRule2() throws Exception {
		    
			Reporter.log("Rule for SerailGroup Approvers creation begins");
		    approvalTest.ConfigUI("ApprovalAuthorityLimitRule2",username,password);
		    Reporter.log("Rule created and deployed");
	}

    @Test(description = "Method to check flow of invoices routing according to AA", priority = 62,dependsOnMethods = {"ApprovalAuthorityLimitRule2"})
	public void ApprovalAuthorityLimitRule2Flow1() {

		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(36);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);
				
				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}

				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to create rule to route invoices according to AA", priority = 63,dependsOnMethods = {"ApprovalAuthorityLimitRule2"})
	public void ApprovalAuthorityLimitRule2Flow2() {

		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(37);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);

				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}
				
				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to create rule to route invoices according to AA", priority = 64,dependsOnMethods = {"ApprovalAuthorityLimitRule2"})
	public void ApprovalAuthorityLimitRule2Flow3() {

		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(38);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);
				
				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}

				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	//null/blank as requester
	@Test(description = "Method to create rule to route invoices according to AA", priority = 65,dependsOnMethods = {"ApprovalAuthorityLimitRule2"})
	public void ApprovalAuthorityLimitRule2Flow4() {

		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(39);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice1("InvoiceNoRequester",invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();
				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");

				invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");					

			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to create rule with routing policy as approval authority ",priority=66)
	public void ApprovalAuthorityLimitRule5() throws Exception {
		    
			Reporter.log("Rule for SerailGroup Approvers creation begins");
		    approvalTest.ConfigUI("ApprovalAuthorityLimitRule5",username,password);
		    Reporter.log("Rule created and deployed");
	}
	
	//need to change linenumber from 2 to 1
	@Test(description = "Method to check flow of AA", priority = 67,dependsOnMethods = {"ApprovalAuthorityLimitRule5"})
	public void ApprovalAuthorityLimitRule5Flow1() {

		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(40);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice1("createInvoiceLine1",invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				getInvoiceApprovalsStatus();

				System.out.println("Processing of Invoice Approval Wrokflow ");
				Reporter.log("Processing of Invoice Approval Wrokflow ");
				executeInvoiceAction(action,users);

				Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> values = new ArrayList<>(map.values());
				System.out.println("Expected Approver "+users);
				System.out.println("Actual  Approver "+values);
				if(!users.containsAll(values))
				{
					System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
					Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

				}
				
				System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to check flow of AA", priority = 68,dependsOnMethods = {"ApprovalAuthorityLimitRule5"})
	public void ApprovalAuthorityLimitRule5Flow2() {

		try {
			String action="APPROVE";

			Row dataRow = invoiceDataSheet.getRow(41);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice(invoiceNumber,invoiceAmount);

				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				CommonUtils.hold(10);
				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				System.out.println("InvoiceApproval Status ");
				invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

				Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
			
			}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}

	//Need Line Number to 1
	@Test(description = "Method to check flow of AA", priority = 69,dependsOnMethods = {"ApprovalAuthorityLimitRule5"})
	public void ApprovalAuthorityLimitRule5Flow3() {

		try {
			

			Row dataRow = invoiceDataSheet.getRow(42);
			DataFormatter formatter = new DataFormatter();
			System.out.println("data row are "+dataRow.getRowNum());

			if(dataRow.getRowNum() > 0) {
				Iterator<Cell> dataRowCell = dataRow.cellIterator();
				Reporter.log("Reading data from data file");
				while (dataRowCell.hasNext()) 
				{
					Cell rowCell = dataRowCell.next();

					// System.out.println("rowCellType : "+rowCell.getCellType());

					if(rowCell.getColumnIndex() == 1)
						invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					if(rowCell.getColumnIndex() == 2) 
						invoiceAmount = formatter.formatCellValue(rowCell);
				}

				Reporter.log("Reading data from data file completed");

				System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
				Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

				System.out.println("Processing of Invoice Creation ");
				Reporter.log("Processing of Invoice Creation ");
				createInvoice1("createInvoiceLine1",invoiceNumber,invoiceAmount);
				System.out.println("Processing of Workflow Initiation ");
				Reporter.log("Processing of Workflow Initiation ");
				initiateWorkflow();

				CommonUtils.hold(10);
				System.out.println("Get InvoiceApproval Status ");
				Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
				System.out.println("InvoiceApproval Status ");
				invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");

				Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

				Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

				}	


		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
		}
	}
	
	@Test(description = "Method to create rule with routing policy as approval authority ",priority=70)
	public void ApprovalAuthorityLimitRule14() throws Exception {
			
			Reporter.log("Rule for ApprovalAuthorityLimitRule14 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule14",username,password);
			Reporter.log("Rule created and deployed");
    }

	@Test(description = "Method to check flow of AA", priority = 71,dependsOnMethods = {"ApprovalAuthorityLimitRule14"})
	public void ApprovalAuthorityLimitRule14Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Linda Wright");
			try {
						String action="APPROVE";

						Row dataRow = invoiceDataSheet.getRow(43);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();
							Reporter.log("Reading data from data file");
							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoice(invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();

							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);

							Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!users.containsAll(values))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
						}	


				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
			}	
		}
		
	@Test(description = "Method to check flow of AA", priority = 72,dependsOnMethods = {"ApprovalAuthorityLimitRule14"})
	public void ApprovalAuthorityLimitRule14Flow2() {

			try {
				String action="APPROVE";

				Row dataRow = invoiceDataSheet.getRow(44);
				DataFormatter formatter = new DataFormatter();
				System.out.println("data row are "+dataRow.getRowNum());

				if(dataRow.getRowNum() > 0) {
					Iterator<Cell> dataRowCell = dataRow.cellIterator();
					Reporter.log("Reading data from data file");
					while (dataRowCell.hasNext()) 
					{
						Cell rowCell = dataRowCell.next();

						// System.out.println("rowCellType : "+rowCell.getCellType());

						if(rowCell.getColumnIndex() == 1)
							invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
						if(rowCell.getColumnIndex() == 2) 
							invoiceAmount = formatter.formatCellValue(rowCell);
					}

					Reporter.log("Reading data from data file completed");

					System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
					Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

					System.out.println("Processing of Invoice Creation ");
					Reporter.log("Processing of Invoice Creation ");
					createInvoice(invoiceNumber,invoiceAmount);

					System.out.println("Processing of Workflow Initiation ");
					Reporter.log("Processing of Workflow Initiation ");
					initiateWorkflow();

					CommonUtils.hold(10);
					System.out.println("Get InvoiceApproval Status ");
					Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
					System.out.println("InvoiceApproval Status ");
					invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");

					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

					Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");

					}	


			}catch(Exception gEDE) {
				System.out.println("Exception in readExcel: "+gEDE.getMessage());
				gEDE.printStackTrace();
			}
	}
		
	@Test(description = "Method to create rule with routing policy as approval authority ",priority=73)
	public void ApprovalAuthorityLimitRule8() throws Exception {
			
			Reporter.log("Rule for ApprovalAuthorityLimitRule8 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule8",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check flow of AA", priority = 74,dependsOnMethods = {"ApprovalAuthorityLimitRule8"})
	public void ApprovalAuthorityLimitRule8Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Linda Wright");
			
			try {
						String action="APPROVE";

						Row dataRow = invoiceDataSheet.getRow(46);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();
							Reporter.log("Reading data from data file");
							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoice1("createInvoiceLine3",invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();

							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);

							Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!users.containsAll(values))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
						}	


				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
			}	
	}
	
	@Test(description = "Method to check flow of AA", priority = 75,dependsOnMethods = {"ApprovalAuthorityLimitRule8"})
	public void ApprovalAuthorityLimitRule8Flow3() {
		List<String>users= new ArrayList<String>();
		users.add("Linda Wright");
		users.add("Terry Green");
		users.add("Connor Horton");	
		users.add("Chris Black");
			try {
						String action="APPROVE";

						Row dataRow = invoiceDataSheet.getRow(47);
						DataFormatter formatter = new DataFormatter();
						System.out.println("data row are "+dataRow.getRowNum());

						if(dataRow.getRowNum() > 0) {
							Iterator<Cell> dataRowCell = dataRow.cellIterator();
							Reporter.log("Reading data from data file");
							while (dataRowCell.hasNext()) 
			                {
			                    Cell rowCell = dataRowCell.next();

			                  // System.out.println("rowCellType : "+rowCell.getCellType());

			                   if(rowCell.getColumnIndex() == 1)
			                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
			                   if(rowCell.getColumnIndex() == 2) 
			                	   invoiceAmount = formatter.formatCellValue(rowCell);
			                }

							Reporter.log("Reading data from data file completed");

							System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
							Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

							System.out.println("Processing of Invoice Creation ");
							Reporter.log("Processing of Invoice Creation ");
							createInvoicewithoutLineChange("Line3Line2",invoiceNumber,invoiceAmount);

							System.out.println("Processing of Workflow Initiation ");
							Reporter.log("Processing of Workflow Initiation ");
							initiateWorkflow();

							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							getInvoiceApprovalsStatus();

							System.out.println("Processing of Invoice Approval Wrokflow ");
							Reporter.log("Processing of Invoice Approval Wrokflow ");
							executeInvoiceAction(action,users);
							
							Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!users.containsAll(values))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}

							System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
						}	


				}catch(Exception gEDE) {
					System.out.println("Exception in readExcel: "+gEDE.getMessage());
					gEDE.printStackTrace();
			}	
		}
		
	@Test(description = "Method to check flow of AA", priority = 76,dependsOnMethods = {"ApprovalAuthorityLimitRule8"})
	public void ApprovalAuthorityLimitRule8Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Connor Horton");
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(45);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoice1("createInvoiceLine1",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);
									
									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									System.out.println("Expected Approver "+users);
									System.out.println("Actual  Approver "+values);
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}

									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
								}	


						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}	
	}
		
	@Test(description = "Method to create rule with routing policy as approval authority with no limit ",priority=77)
	public void ApprovalAuthorityNoLimitRule6() throws Exception {
	
        	Reporter.log("Rule for ApprovalAuthorityNoLimitRule6 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule6",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	//----No Limit with All matching approver : All distribution line  match to an approver----//
	@Test(description = "Method to check flow of AA", priority = 78,dependsOnMethods = {"ApprovalAuthorityNoLimitRule6"})
	public void ApprovalAuthorityNoLimitRule6Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");	
		users.add("Connor Horton");	
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(129);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoicewithoutLineChange("nolimit6_1",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
								}	


						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}	
					
	}
	
	//----No Limit with All matching approver : Not All distribution line match to an approver----//
	@Test(description = "Method to check flow of AA", priority = 79,dependsOnMethods = {"ApprovalAuthorityNoLimitRule6"})
	public void ApprovalAuthorityNoLimitRule6Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");	
		users.add("Connor Horton");	
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(130);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoicewithoutLineChange("nolimit6_2",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
								}	


						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}				
	}

	//----No Limit with All matching approver : complete line unmatch to an approver----//
	@Test(description = "Method to check flow of AA", priority = 80,dependsOnMethods = {"ApprovalAuthorityNoLimitRule6"})
	public void ApprovalAuthorityNoLimitRule6Flow3() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");	
		users.add("Connor Horton");	
						try {
									String action="APPROVE";

									Row dataRow = invoiceDataSheet.getRow(131);
									DataFormatter formatter = new DataFormatter();
									System.out.println("data row are "+dataRow.getRowNum());

									if(dataRow.getRowNum() > 0) {
										Iterator<Cell> dataRowCell = dataRow.cellIterator();
										Reporter.log("Reading data from data file");
										while (dataRowCell.hasNext()) 
						                {
						                    Cell rowCell = dataRowCell.next();

						                  // System.out.println("rowCellType : "+rowCell.getCellType());

						                   if(rowCell.getColumnIndex() == 1)
						                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
						                   if(rowCell.getColumnIndex() == 2) 
						                	   invoiceAmount = formatter.formatCellValue(rowCell);
						                }

										Reporter.log("Reading data from data file completed");

										System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
										Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

										System.out.println("Processing of Invoice Creation ");
										Reporter.log("Processing of Invoice Creation ");
										createInvoicewithoutLineChange("nolimit6_3",invoiceNumber,invoiceAmount);

										System.out.println("Processing of Workflow Initiation ");
										Reporter.log("Processing of Workflow Initiation ");
										initiateWorkflow();

										System.out.println("Get InvoiceApproval Status ");
										Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
										getInvoiceApprovalsStatus();

										System.out.println("Processing of Invoice Approval Wrokflow ");
										Reporter.log("Processing of Invoice Approval Wrokflow ");
										executeInvoiceAction(action,users);

										Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
										List<String> values = new ArrayList<>(map.values());
										if(!users.containsAll(values))
										{
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

										}
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
									}	


							}catch(Exception gEDE) {
								System.out.println("Exception in readExcel: "+gEDE.getMessage());
								gEDE.printStackTrace();
						}
		}
	
	//----No Limit with All matching approver : complete unmatch so auto approve----//
	@Test(description = "Method to check flow of AA", priority = 81,dependsOnMethods = {"ApprovalAuthorityNoLimitRule6"})
	public void ApprovalAuthorityNoLimitRule6Flow4() {
		List<String>users= new ArrayList<String>();	
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(132);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit6_4",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();
											CommonUtils.hold(10);
											System.out.println("InvoiceApproval Status ");
											invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
			}

	//----No Limit with All matching approver : complete unmatch so auto approve----//
	@Test(description = "Method to check flow of AA", priority = 82,dependsOnMethods = {"ApprovalAuthorityNoLimitRule6"})
	public void ApprovalAuthorityNoLimitRule6Flow5() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");	
						try {
									String action="APPROVE";

									Row dataRow = invoiceDataSheet.getRow(133);
									DataFormatter formatter = new DataFormatter();
									System.out.println("data row are "+dataRow.getRowNum());

									if(dataRow.getRowNum() > 0) {
										Iterator<Cell> dataRowCell = dataRow.cellIterator();
										Reporter.log("Reading data from data file");
										while (dataRowCell.hasNext()) 
						                {
						                    Cell rowCell = dataRowCell.next();

						                  // System.out.println("rowCellType : "+rowCell.getCellType());

						                   if(rowCell.getColumnIndex() == 1)
						                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
						                   if(rowCell.getColumnIndex() == 2) 
						                	   invoiceAmount = formatter.formatCellValue(rowCell);
						                }

										Reporter.log("Reading data from data file completed");

										System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
										Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

										System.out.println("Processing of Invoice Creation ");
										Reporter.log("Processing of Invoice Creation ");
										createInvoicewithoutLineChange("nolimit6_5",invoiceNumber,invoiceAmount);

										System.out.println("Processing of Workflow Initiation ");
										Reporter.log("Processing of Workflow Initiation ");
										initiateWorkflow();

										System.out.println("Get InvoiceApproval Status ");
										Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
										getInvoiceApprovalsStatus();

										System.out.println("Processing of Invoice Approval Wrokflow ");
										Reporter.log("Processing of Invoice Approval Wrokflow ");
										executeInvoiceAction(action,users);

										Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
										List<String> values = new ArrayList<>(map.values());
										if(!users.containsAll(values))
										{
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

										}
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
									}	


							}catch(Exception gEDE) {
								System.out.println("Exception in readExcel: "+gEDE.getMessage());
								gEDE.printStackTrace();
						}
				}
	
    @Test(description = "Method to create rule with routing policy as approval authority with no limit ",priority=83)
	public void ApprovalAuthorityNoLimitRule7() throws Exception {

			Reporter.log("Rule for ApprovalAuthorityNoLimitRule7 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule7",username,password);
			Reporter.log("Rule created and deployed");
	}
	
    //----No Limit with All matching approver : All distribution line  match to an approver----//
	@Test(description = "Method to check flow of AA", priority = 84,dependsOnMethods = {"ApprovalAuthorityNoLimitRule7"})
	public void ApprovalAuthorityNoLimitRule7Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");	
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(134);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoicewithoutLineChange("nolimit7_1",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
								}	


						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}	
					
	}

	//----No Limit with All matching approver : Not All distribution line match to an approver----//
	@Test(description = "Method to check flow of AA", priority = 85,dependsOnMethods = {"ApprovalAuthorityNoLimitRule7"})
	public void ApprovalAuthorityNoLimitRule7Flow2() {
			List<String>users= new ArrayList<String>();
			users.add("Terry Green");	
						try {
									String action="APPROVE";

									Row dataRow = invoiceDataSheet.getRow(135);
									DataFormatter formatter = new DataFormatter();
									System.out.println("data row are "+dataRow.getRowNum());

									if(dataRow.getRowNum() > 0) {
										Iterator<Cell> dataRowCell = dataRow.cellIterator();
										Reporter.log("Reading data from data file");
										while (dataRowCell.hasNext()) 
						                {
						                    Cell rowCell = dataRowCell.next();

						                  // System.out.println("rowCellType : "+rowCell.getCellType());

						                   if(rowCell.getColumnIndex() == 1)
						                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
						                   if(rowCell.getColumnIndex() == 2) 
						                	   invoiceAmount = formatter.formatCellValue(rowCell);
						                }

										Reporter.log("Reading data from data file completed");

										System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
										Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

										System.out.println("Processing of Invoice Creation ");
										Reporter.log("Processing of Invoice Creation ");
										createInvoicewithoutLineChange("nolimit7_2",invoiceNumber,invoiceAmount);

										System.out.println("Processing of Workflow Initiation ");
										Reporter.log("Processing of Workflow Initiation ");
										initiateWorkflow();

										System.out.println("Get InvoiceApproval Status ");
										Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
										getInvoiceApprovalsStatus();

										System.out.println("Processing of Invoice Approval Wrokflow ");
										Reporter.log("Processing of Invoice Approval Wrokflow ");
										executeInvoiceAction(action,users);

										Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
										List<String> values = new ArrayList<>(map.values());
										if(!users.containsAll(values))
										{
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

										}
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
									}	


							}catch(Exception gEDE) {
								System.out.println("Exception in readExcel: "+gEDE.getMessage());
								gEDE.printStackTrace();
						}				
		}

		//----No Limit with All matching approver : complete line unmatch to an approver----//
		@Test(description = "Method to check flow of AA", priority = 86,dependsOnMethods = {"ApprovalAuthorityNoLimitRule7"})
		public void ApprovalAuthorityNoLimitRule7Flow3() {
			List<String>users= new ArrayList<String>();
			users.add("Terry Green");		
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(136);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit7_3",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();

											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);

											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
			}
		
		//----No Limit with All matching approver : complete unmatch so auto approve----//
		@Test(description = "Method to check flow of AA", priority = 87,dependsOnMethods = {"ApprovalAuthorityNoLimitRule7"})
		public void ApprovalAuthorityNoLimitRule7Flow4() {
			List<String>users= new ArrayList<String>();	
								try {
											String action="APPROVE";

											Row dataRow = invoiceDataSheet.getRow(137);
											DataFormatter formatter = new DataFormatter();
											System.out.println("data row are "+dataRow.getRowNum());

											if(dataRow.getRowNum() > 0) {
												Iterator<Cell> dataRowCell = dataRow.cellIterator();
												Reporter.log("Reading data from data file");
												while (dataRowCell.hasNext()) 
								                {
								                    Cell rowCell = dataRowCell.next();

								                  // System.out.println("rowCellType : "+rowCell.getCellType());

								                   if(rowCell.getColumnIndex() == 1)
								                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
								                   if(rowCell.getColumnIndex() == 2) 
								                	   invoiceAmount = formatter.formatCellValue(rowCell);
								                }

												Reporter.log("Reading data from data file completed");

												System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
												Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

												System.out.println("Processing of Invoice Creation ");
												Reporter.log("Processing of Invoice Creation ");
												createInvoicewithoutLineChange("nolimit7_4",invoiceNumber,invoiceAmount);

												System.out.println("Processing of Workflow Initiation ");
												Reporter.log("Processing of Workflow Initiation ");
												initiateWorkflow();
												CommonUtils.hold(10);
												System.out.println("InvoiceApproval Status ");
												invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

												Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

											}	


									}catch(Exception gEDE) {
										System.out.println("Exception in readExcel: "+gEDE.getMessage());
										gEDE.printStackTrace();
								}
				}

		//----All invoice dis line match to an approver but no at least a sequence does not match to any dist line----//
		@Test(description = "Method to check flow of AA", priority = 88,dependsOnMethods = {"ApprovalAuthorityNoLimitRule7"})
		public void ApprovalAuthorityNoLimitRule7Flow5() {
			List<String>users= new ArrayList<String>();
			users.add("Connor Horton");	
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(138);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit7_5",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();

											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);

											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
					}

		@Test(description = "Method to create rule with routing policy as approval authority with no limit ",priority=89)
		public void ApprovalAuthorityNoLimitRule4() throws Exception {
				
				Reporter.log("Rule for ApprovalAuthorityNoLimitRule4 creation begins");
				approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule4",username,password);
				Reporter.log("Rule created and deployed");
		}
		
		@Test(description = "Method to check flow of AA", priority = 90,dependsOnMethods = {"ApprovalAuthorityNoLimitRule4"})
		public void ApprovalAuthorityNoLimitRule4Flow1() {
			List<String>users= new ArrayList<String>();
			users.add("Linda Wright");
			users.add("Pat Stock");
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(139);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit4_1",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();

											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);

											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
					}

		@Test(description = "Method to check flow of AA", priority = 91,dependsOnMethods = {"ApprovalAuthorityNoLimitRule4"})
		public void ApprovalAuthorityNoLimitRule4Flow2() {
			List<String>users= new ArrayList<String>();
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(140);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit4_2",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("InvoiceApproval Status ");
											invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
					}
		
		@Test(description = "Method to check flow of AA", priority = 92,dependsOnMethods = {"ApprovalAuthorityNoLimitRule4"})
		public void ApprovalAuthorityNoLimitRule4Flow3() {
			List<String>users= new ArrayList<String>();
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(141);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit4_3",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("InvoiceApproval Status ");
											invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
					}

		@Test(description = "Method to create rule with routing policy as approval authority with no limit ",priority=93)
		public void ApprovalAuthorityNoLimitRule5() throws Exception {
				
				Reporter.log("Rule for ApprovalAuthorityNoLimitRule5 creation begins");
				approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule5",username,password);
				Reporter.log("Rule created and deployed");
		}
		
		@Test(description = "Method to check flow of AA", priority = 94,dependsOnMethods = {"ApprovalAuthorityNoLimitRule5"})
		public void ApprovalAuthorityNoLimitRule5Flow1() {
			List<String>users= new ArrayList<String>();
			users.add("Terry Green");
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(142);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit5_1",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();

											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);

											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
					}
		
		@Test(description = "Method to check flow of AA", priority = 95,dependsOnMethods = {"ApprovalAuthorityNoLimitRule5"})
		public void ApprovalAuthorityNoLimitRule5Flow2() {
			List<String>users= new ArrayList<String>();
			users.add("Terry Green");
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(143);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("nolimit5_2",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();
											
											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();

											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);

											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										}	


								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
					}
		
		@Test(description = "Method to create rule nolimit aa ", priority = 96) 
		public void ApprovalAuthorityNoLimitRule1() throws Exception{
	
				Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
				approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule1",username,password);
				Reporter.log("Rule created and deployed");
		}
		
		@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 97,dependsOnMethods = {"ApprovalAuthorityNoLimitRule1"} )
		public void ApprovalAuthorityNoLimitRule1Flow1() {
			List<String>users= new ArrayList<String>();
			users.add("Alicia Daniels");
			users.add("Terry Green");
			users.add("Connor Horton");
			users.add("Linda Wright");
				try {
							String action="APPROVE";
	
							Row dataRow = invoiceDataSheet.getRow(144);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());
	
							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();
	
				                  // System.out.println("rowCellType : "+rowCell.getCellType());
	
				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
	
								Reporter.log("Reading data from data file completed");
	
								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	
								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
							createInvoicewithoutLineChange("ApprovalAuthorityNoLimitRule1Flow1", invoiceNumber, invoiceAmount);
	
								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
	
								CommonUtils.hold(10);
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();
	
								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);
	
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							}
					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
						Assert.assertTrue(false);
				}
			}
		@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 98,dependsOnMethods = {"ApprovalAuthorityNoLimitRule1"} )
		public void ApprovalAuthorityNoLimitRule1Flow2() {
			List<String>users= new ArrayList<String>();
					users.add("Connor Horton");
			users.add("Terry Green");
				try {
							String action="APPROVE";
	
							Row dataRow = invoiceDataSheet.getRow(145);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());
	
							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();
	
				                  // System.out.println("rowCellType : "+rowCell.getCellType());
	
				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }
	
								Reporter.log("Reading data from data file completed");
	
								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	
								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
							createInvoicewithoutLineChange("ApprovalAuthorityNoLimitRule1Flow2", invoiceNumber, invoiceAmount);
	
								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();
	
								CommonUtils.hold(10);
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();
	
								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);
	
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							}
					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
						Assert.assertTrue(false);
	
				}
			}
		

		@Test(description = "Method to create rule for AA policy ", priority = 99) 
		public void ApprovalAuthorityNoLimitRule3() throws Exception{

				Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
				approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule3",username,password);
				Reporter.log("Rule created and deployed");
		}
		
	@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 100,dependsOnMethods = {"ApprovalAuthorityNoLimitRule3"} )
		public void ApprovalAuthorityNoLimitRule3Flow1() {
			List<String>users= new ArrayList<String>();
					users.add("Connor Horton");
					try {
							String action="APPROVE";

							Row dataRow = invoiceDataSheet.getRow(146);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
							createInvoicewithoutLineChange("ApprovalAuthorityNoLimitRule3Flow1", invoiceNumber, invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();

								CommonUtils.hold(10);
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							}
					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
						Assert.assertTrue(false);

				}
			}
	@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 101,dependsOnMethods = {"ApprovalAuthorityNoLimitRule3"} )
		public void ApprovalAuthorityNoLimitRule3Flow2() {
					try {
							String action="APPROVE";

							Row dataRow = invoiceDataSheet.getRow(147);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
							createInvoicewithoutLineChange("ApprovalAuthorityNoLimitRule3Flow2", invoiceNumber, invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();

								CommonUtils.hold(10);
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("InvoiceApproval Status ");
								invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");					}

					
					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
						Assert.assertTrue(false);

				}
			}
	@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 102,dependsOnMethods = {"ApprovalAuthorityNoLimitRule3"} )
		public void ApprovalAuthorityNoLimitRule3Flow3() {
			List<String>users= new ArrayList<String>();
					users.add("Robert Jones");
					users.add("John Williams");

					try {
							String action="APPROVE";

							Row dataRow = invoiceDataSheet.getRow(148);
							DataFormatter formatter = new DataFormatter();
							System.out.println("data row are "+dataRow.getRowNum());

							if(dataRow.getRowNum() > 0) {
								Iterator<Cell> dataRowCell = dataRow.cellIterator();
								Reporter.log("Reading data from data file");
								while (dataRowCell.hasNext()) 
				                {
				                    Cell rowCell = dataRowCell.next();

				                  // System.out.println("rowCellType : "+rowCell.getCellType());

				                   if(rowCell.getColumnIndex() == 1)
				                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
				                   if(rowCell.getColumnIndex() == 2) 
				                	   invoiceAmount = formatter.formatCellValue(rowCell);
				                }

								Reporter.log("Reading data from data file completed");

								System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
								Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

								System.out.println("Processing of Invoice Creation ");
								Reporter.log("Processing of Invoice Creation ");
							createInvoicewithoutLineChange("ApprovalAuthorityNoLimitRule3Flow3", invoiceNumber, invoiceAmount);

								System.out.println("Processing of Workflow Initiation ");
								Reporter.log("Processing of Workflow Initiation ");
								initiateWorkflow();

								CommonUtils.hold(10);
								System.out.println("Get InvoiceApproval Status ");
								Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
								getInvoiceApprovalsStatus();

								System.out.println("Processing of Invoice Approval Wrokflow ");
								Reporter.log("Processing of Invoice Approval Wrokflow ");
								executeInvoiceAction(action,users);

								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
								Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							}
					}catch(Exception gEDE) {
						System.out.println("Exception in readExcel: "+gEDE.getMessage());
						gEDE.printStackTrace();
						Assert.assertTrue(false);

				}
			}
		
		
		
		@Test(description = "Method to create rule for no limit AA", priority = 103) 
			public void ApprovalAuthorityNoLimitRule2() throws Exception{

					Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
					approvalTest.ConfigUI("ApprovalAuthorityNoLimitRule2",username,password);
					Reporter.log("Rule created and deployed");
			}
			
			@Test(description = "Method to route invoices to Approvers as Users for Approvals", priority = 104,dependsOnMethods = {"ApprovalAuthorityNoLimitRule2"} )
			public void ApprovalAuthorityNoLimitRule2Flow1() {
				List<String>users= new ArrayList<String>();
				List<String> retrivedusers= new ArrayList<String>();
						users.add("Robert Jones");
						users.add("John Williams");
						users.add("Linda Wright");

						try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(149);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
								createInvoicewithoutLineChange("ApprovalAuthorityNoLimitRule2Flow1", invoiceNumber, invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									CommonUtils.hold(10);
									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();

									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
								}
						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
							Assert.assertTrue(false);

					}
				}
	@Test(description = "Method to create rule with routing policy as approval authority with limit ",priority=113)
	public void ApprovalAuthorityLimitRule7() throws Exception {

			Reporter.log("Rule for ApprovalAuthorityLimitRule7 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule7",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check invoice flow according to AA policy", priority = 114,dependsOnMethods = {"ApprovalAuthorityLimitRule7"} )
	public void ApprovalAuthorityLimitRule7Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
		users.add("Alicia Daniels");
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(73);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoicewithoutLineChange("ApprovalAuthorityLimitRule7Flow1",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();
									
									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);

								

									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									System.out.println("Expected Approver "+users);
									System.out.println("Actual  Approver "+values);
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}
									
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							

								}
						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}
	}
				
	@Test(description = "Method to check invoice flow according to AA policy", priority = 115,dependsOnMethods = {"ApprovalAuthorityLimitRule7"} )
	public void ApprovalAuthorityLimitRule7Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(74);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoicewithoutLineChange("ApprovalAuthorityLimitRule7Flow2",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();
									
									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);


									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									System.out.println("Expected Approver "+users);
									System.out.println("Actual  Approver "+values);
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}
									
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							

								}
						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}
	}
		
	@Test(description = "Method to create rule with routing policy as approval authority with limit ",priority=116)
	public void ApprovalAuthorityLimitRule7_1() throws Exception {

			Reporter.log("Rule for ApprovalAuthorityLimitRule7_1 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule7_1",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check invoice flow according to AA policy", priority = 117,dependsOnMethods = {"ApprovalAuthorityLimitRule7_1"} )
	public void ApprovalAuthorityLimitRule7_1Flow1() {
					
						try {
									String action="APPROVE";

									Row dataRow = invoiceDataSheet.getRow(75);
									DataFormatter formatter = new DataFormatter();
									System.out.println("data row are "+dataRow.getRowNum());

									if(dataRow.getRowNum() > 0) {
										Iterator<Cell> dataRowCell = dataRow.cellIterator();
										Reporter.log("Reading data from data file");
										while (dataRowCell.hasNext()) 
						                {
						                    Cell rowCell = dataRowCell.next();

						                  // System.out.println("rowCellType : "+rowCell.getCellType());

						                   if(rowCell.getColumnIndex() == 1)
						                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
						                   if(rowCell.getColumnIndex() == 2) 
						                	   invoiceAmount = formatter.formatCellValue(rowCell);
						                }

										Reporter.log("Reading data from data file completed");

										System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
										Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

										System.out.println("Processing of Invoice Creation ");
										Reporter.log("Processing of Invoice Creation ");
										createInvoicewithoutLineChange("ApprovalAuthorityLimitRule7Flow1",invoiceNumber,invoiceAmount);

										System.out.println("Processing of Workflow Initiation ");
										Reporter.log("Processing of Workflow Initiation ");
										initiateWorkflow();

										CommonUtils.hold(10);
										System.out.println("Get InvoiceApproval Status ");
										Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
										System.out.println("InvoiceApproval Status ");
										invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");

										
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Rejected");
								

									}
							}catch(Exception gEDE) {
								System.out.println("Exception in readExcel: "+gEDE.getMessage());
								gEDE.printStackTrace();
						}
	}

	@Test(description = "Method to create rule with routing policy as approval authority with limit ",priority=118)
	public void ApprovalAuthorityLimitRule10() throws Exception {

     		Reporter.log("Rule for ApprovalAuthorityLimitRule10 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule10",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	
	@Test(description = "Method to check invoice flow according to AA policy", priority = 119,dependsOnMethods = {"ApprovalAuthorityLimitRule10"} )
	public void ApprovalAuthorityLimitRule10Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Connor Horton");
		users.add("Terry Green");
						try {
									String action="APPROVE";

									Row dataRow = invoiceDataSheet.getRow(76);
									DataFormatter formatter = new DataFormatter();
									System.out.println("data row are "+dataRow.getRowNum());

									if(dataRow.getRowNum() > 0) {
										Iterator<Cell> dataRowCell = dataRow.cellIterator();
										Reporter.log("Reading data from data file");
										while (dataRowCell.hasNext()) 
						                {
						                    Cell rowCell = dataRowCell.next();

						                  // System.out.println("rowCellType : "+rowCell.getCellType());

						                   if(rowCell.getColumnIndex() == 1)
						                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
						                   if(rowCell.getColumnIndex() == 2) 
						                	   invoiceAmount = formatter.formatCellValue(rowCell);
						                }

										Reporter.log("Reading data from data file completed");

										System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
										Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

										System.out.println("Processing of Invoice Creation ");
										Reporter.log("Processing of Invoice Creation ");
										createInvoicewithoutLineChange("ApprovalAuthorityLimitRule10Flow1",invoiceNumber,invoiceAmount);

										System.out.println("Processing of Workflow Initiation ");
										Reporter.log("Processing of Workflow Initiation ");
										initiateWorkflow();

										System.out.println("Get InvoiceApproval Status ");
										Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
										getInvoiceApprovalsStatus();
										
										System.out.println("Processing of Invoice Approval Wrokflow ");
										Reporter.log("Processing of Invoice Approval Wrokflow ");
										executeInvoiceAction(action,users);


										Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
										List<String> values = new ArrayList<>(map.values());
										System.out.println("Expected Approver "+users);
										System.out.println("Actual  Approver "+values);
										if(!users.containsAll(values))
										{
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
											Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

										}
										
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
										Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
								

									}
							}catch(Exception gEDE) {
								System.out.println("Exception in readExcel: "+gEDE.getMessage());
								gEDE.printStackTrace();
						}
	}
				
	@Test(description = "Method to check invoice flow according to AA policy", priority = 120,dependsOnMethods = {"ApprovalAuthorityLimitRule10"} )
	public void ApprovalAuthorityLimitRule10Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Connor Horton");
		users.add("Terry Green");
					try {
								String action="APPROVE";

								Row dataRow = invoiceDataSheet.getRow(77);
								DataFormatter formatter = new DataFormatter();
								System.out.println("data row are "+dataRow.getRowNum());

								if(dataRow.getRowNum() > 0) {
									Iterator<Cell> dataRowCell = dataRow.cellIterator();
									Reporter.log("Reading data from data file");
									while (dataRowCell.hasNext()) 
					                {
					                    Cell rowCell = dataRowCell.next();

					                  // System.out.println("rowCellType : "+rowCell.getCellType());

					                   if(rowCell.getColumnIndex() == 1)
					                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
					                   if(rowCell.getColumnIndex() == 2) 
					                	   invoiceAmount = formatter.formatCellValue(rowCell);
					                }

									Reporter.log("Reading data from data file completed");

									System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
									Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

									System.out.println("Processing of Invoice Creation ");
									Reporter.log("Processing of Invoice Creation ");
									createInvoicewithoutLineChange("ApprovalAuthorityLimitRule10Flow2",invoiceNumber,invoiceAmount);

									System.out.println("Processing of Workflow Initiation ");
									Reporter.log("Processing of Workflow Initiation ");
									initiateWorkflow();

									System.out.println("Get InvoiceApproval Status ");
									Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
									getInvoiceApprovalsStatus();
									
									System.out.println("Processing of Invoice Approval Wrokflow ");
									Reporter.log("Processing of Invoice Approval Wrokflow ");
									executeInvoiceAction(action,users);


									Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
									List<String> values = new ArrayList<>(map.values());
									System.out.println("Expected Approver "+users);
									System.out.println("Actual  Approver "+values);
									if(!users.containsAll(values))
									{
										System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
										Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

									}
									
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
									Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
							

								}
						}catch(Exception gEDE) {
							System.out.println("Exception in readExcel: "+gEDE.getMessage());
							gEDE.printStackTrace();
					}
	}

	@Test(description = "Method to create rule with routing policy as approval authority with limit ",priority=121)
	public void ApprovalAuthorityLimitRule11() throws Exception {

			Reporter.log("Rule for ApprovalAuthorityLimitRule11 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule11",username,password);
			Reporter.log("Rule created and deployed");
	}
		
	@Test(description = "Method to check invoice flow according to AA policy", priority = 122,dependsOnMethods = {"ApprovalAuthorityLimitRule11"} )
	public void ApprovalAuthorityLimitRule11Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Linda Wright");
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(78);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("ApprovalAuthorityLimitRule11Flow1",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();
											
											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);


											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											System.out.println("Expected Approver "+users);
											System.out.println("Actual  Approver "+values);
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
									

										}
								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
	}
						
	@Test(description = "Method to route invoices according to AA", priority = 123,dependsOnMethods = {"ApprovalAuthorityLimitRule11"} )
	public void ApprovalAuthorityLimitRule11Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Chris Black");
								try {
											String action="APPROVE";

											Row dataRow = invoiceDataSheet.getRow(79);
											DataFormatter formatter = new DataFormatter();
											System.out.println("data row are "+dataRow.getRowNum());

											if(dataRow.getRowNum() > 0) {
												Iterator<Cell> dataRowCell = dataRow.cellIterator();
												Reporter.log("Reading data from data file");
												while (dataRowCell.hasNext()) 
								                {
								                    Cell rowCell = dataRowCell.next();

								                  // System.out.println("rowCellType : "+rowCell.getCellType());

								                   if(rowCell.getColumnIndex() == 1)
								                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
								                   if(rowCell.getColumnIndex() == 2) 
								                	   invoiceAmount = formatter.formatCellValue(rowCell);
								                }

												Reporter.log("Reading data from data file completed");

												System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
												Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

												System.out.println("Processing of Invoice Creation ");
												Reporter.log("Processing of Invoice Creation ");
												createInvoicewithoutLineChange("ApprovalAuthorityLimitRule11Flow2",invoiceNumber,invoiceAmount);

												System.out.println("Processing of Workflow Initiation ");
												Reporter.log("Processing of Workflow Initiation ");
												initiateWorkflow();

												System.out.println("Get InvoiceApproval Status ");
												Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
												getInvoiceApprovalsStatus();
												
												System.out.println("Processing of Invoice Approval Wrokflow ");
												Reporter.log("Processing of Invoice Approval Wrokflow ");
												executeInvoiceAction(action,users);


												Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
												List<String> values = new ArrayList<>(map.values());
												System.out.println("Expected Approver "+users);
												System.out.println("Actual  Approver "+values);
												if(!users.containsAll(values))
												{
													System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
													Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
													Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

												}
												
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
												Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										

											}
									}catch(Exception gEDE) {
										System.out.println("Exception in readExcel: "+gEDE.getMessage());
										gEDE.printStackTrace();
								}
	}
	
	@Test(description = "Method to create rule with routing policy as approval authority with limit ",priority=124)
	public void ApprovalAuthorityLimitRule12() throws Exception {
						
			Reporter.log("Rule for ApprovalAuthorityLimitRule12 creation begins");
			approvalTest.ConfigUI("ApprovalAuthorityLimitRule12",username,password);
			Reporter.log("Rule created and deployed");
	}	
	
	@Test(description = "Method to check invoice flow according to AA policy", priority = 125,dependsOnMethods = {"ApprovalAuthorityLimitRule12"} )
	public void ApprovalAuthorityLimitRule12Flow1() {
		List<String>users= new ArrayList<String>();
		users.add("Linda Wright");
		users.add("Chris Black");
							try {
										String action="APPROVE";

										Row dataRow = invoiceDataSheet.getRow(80);
										DataFormatter formatter = new DataFormatter();
										System.out.println("data row are "+dataRow.getRowNum());

										if(dataRow.getRowNum() > 0) {
											Iterator<Cell> dataRowCell = dataRow.cellIterator();
											Reporter.log("Reading data from data file");
											while (dataRowCell.hasNext()) 
							                {
							                    Cell rowCell = dataRowCell.next();

							                  // System.out.println("rowCellType : "+rowCell.getCellType());

							                   if(rowCell.getColumnIndex() == 1)
							                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
							                   if(rowCell.getColumnIndex() == 2) 
							                	   invoiceAmount = formatter.formatCellValue(rowCell);
							                }

											Reporter.log("Reading data from data file completed");

											System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
											Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

											System.out.println("Processing of Invoice Creation ");
											Reporter.log("Processing of Invoice Creation ");
											createInvoicewithoutLineChange("ApprovalAuthorityLimitRule12Flow1",invoiceNumber,invoiceAmount);

											System.out.println("Processing of Workflow Initiation ");
											Reporter.log("Processing of Workflow Initiation ");
											initiateWorkflow();

											System.out.println("Get InvoiceApproval Status ");
											Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
											getInvoiceApprovalsStatus();
											
											System.out.println("Processing of Invoice Approval Wrokflow ");
											Reporter.log("Processing of Invoice Approval Wrokflow ");
											executeInvoiceAction(action,users);


											Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
											List<String> values = new ArrayList<>(map.values());
											System.out.println("Expected Approver "+users);
											System.out.println("Actual  Approver "+values);
											if(!users.containsAll(values))
											{
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
												Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

											}
											
											System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
											Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
									

										}
								}catch(Exception gEDE) {
									System.out.println("Exception in readExcel: "+gEDE.getMessage());
									gEDE.printStackTrace();
							}
	}
						
	@Test(description = "Method to route invoices according to AA", priority = 126,dependsOnMethods = {"ApprovalAuthorityLimitRule12"} )
    public void ApprovalAuthorityLimitRule12Flow2() {
			List<String>users= new ArrayList<String>();
			users.add("Linda Wright");
								try {
											String action="APPROVE";

											Row dataRow = invoiceDataSheet.getRow(81);
											DataFormatter formatter = new DataFormatter();
											System.out.println("data row are "+dataRow.getRowNum());

											if(dataRow.getRowNum() > 0) {
												Iterator<Cell> dataRowCell = dataRow.cellIterator();
												Reporter.log("Reading data from data file");
												while (dataRowCell.hasNext()) 
								                {
								                    Cell rowCell = dataRowCell.next();

								                  // System.out.println("rowCellType : "+rowCell.getCellType());

								                   if(rowCell.getColumnIndex() == 1)
								                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
								                   if(rowCell.getColumnIndex() == 2) 
								                	   invoiceAmount = formatter.formatCellValue(rowCell);
								                }

												Reporter.log("Reading data from data file completed");

												System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
												Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

												System.out.println("Processing of Invoice Creation ");
												Reporter.log("Processing of Invoice Creation ");
												createInvoicewithoutLineChange("ApprovalAuthorityLimitRule12Flow2",invoiceNumber,invoiceAmount);

												System.out.println("Processing of Workflow Initiation ");
												Reporter.log("Processing of Workflow Initiation ");
												initiateWorkflow();

												System.out.println("Get InvoiceApproval Status ");
												Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
												getInvoiceApprovalsStatus();
												
												System.out.println("Processing of Invoice Approval Wrokflow ");
												Reporter.log("Processing of Invoice Approval Wrokflow ");
												executeInvoiceAction(action,users);


												Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
												List<String> values = new ArrayList<>(map.values());
												System.out.println("Expected Approver "+users);
												System.out.println("Actual  Approver "+values);
												if(!users.containsAll(values))
												{
													System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
													Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
													Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

												}
												
												System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
												Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
												Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
										

											}
									}catch(Exception gEDE) {
										System.out.println("Exception in readExcel: "+gEDE.getMessage());
										gEDE.printStackTrace();
								}
	}			

//	@AfterClass(alwaysRun = true)
//	void clearActiveReferences(){
//		try {
//			System.out.println("Closing active workbook and fileIpStream objects");
//			workbook.close();	
//			fileIpStream.close();
//			System.out.println("Active workbook and fileIpStream Objects are Closed");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	
	@Test(description = "Method to create rule based on Super visor RP ,Start with Requester", priority = 49) 
    public void SuperVisorRoutingPolicyRule() throws Exception  {
	      
		    Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
		    approvalTest.ConfigUI("supervisoryhirearchy",username,password);
		    Reporter.log("Rule created and deployed");
    }
	
	@Test(description = "Method to route invoices according to role hierarchy", priority = 50,dependsOnMethods = {"SuperVisorRoutingPolicyRule"} )
    public void SuperVisorRPApprovalFlow() {
		List<String>users= new ArrayList<String>();
		users.add("Mary Johnson");
		users.add("Ted Brown");
		users.add("Alicia Daniels");
		users.add("Linda Wright");
		    try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(28);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();
						Reporter.log("Reading data from data file");
						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }

						Reporter.log("Reading data from data file completed");

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();

						System.out.println("Get InvoiceApproval Status ");
						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
						getInvoiceApprovalsStatus();

						System.out.println("Processing of Invoice Approval Wrokflow ");
						Reporter.log("Processing of Invoice Approval Wrokflow ");
						executeInvoiceAction(action,users);

						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
					}	


			        }catch(Exception gEDE) {
				          System.out.println("Exception in readExcel: "+gEDE.getMessage());
				          gEDE.printStackTrace();
		            }
	}

	@Test(description = "Method to create rule based on Super visor RP Start With manager User Name", priority = 51) 
    public void SuperVisorRoutingPolicyRule2() throws Exception {
	        
			Reporter.log(" Method to create rule for Total dist amount (summation attribute)");
		    approvalTest.ConfigUI("supervisoryhirearchy2",username,password);
		    Reporter.log("Rule created and deployed");
    }
	       
	@Test(description = "Method to route invoices according to role hierarchy", priority = 52,dependsOnMethods = {"SuperVisorRoutingPolicyRule2"} )
	public void SuperVisorRPApprovalFlow2() {
		List<String>users= new ArrayList<String>();
		users.add("Ted Brown");
		users.add("Alicia Daniels");
		    try {
					String action="APPROVE";

					Row dataRow = invoiceDataSheet.getRow(29);
					DataFormatter formatter = new DataFormatter();
					System.out.println("data row are "+dataRow.getRowNum());

					if(dataRow.getRowNum() > 0) {
						Iterator<Cell> dataRowCell = dataRow.cellIterator();
						Reporter.log("Reading data from data file");
						while (dataRowCell.hasNext()) 
		                {
		                    Cell rowCell = dataRowCell.next();

		                  // System.out.println("rowCellType : "+rowCell.getCellType());

		                   if(rowCell.getColumnIndex() == 1)
		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		                   if(rowCell.getColumnIndex() == 2) 
		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		                }

						Reporter.log("Reading data from data file completed");

						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");

						System.out.println("Processing of Invoice Creation ");
						Reporter.log("Processing of Invoice Creation ");
						createInvoice(invoiceNumber,invoiceAmount);

						System.out.println("Processing of Workflow Initiation ");
						Reporter.log("Processing of Workflow Initiation ");
						initiateWorkflow();

						System.out.println("Get InvoiceApproval Status ");
						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
						getInvoiceApprovalsStatus();

						System.out.println("Processing of Invoice Approval Wrokflow ");
						Reporter.log("Processing of Invoice Approval Wrokflow ");
						executeInvoiceAction(action,users);

						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
					}	


			    }catch(Exception gEDE) {
				System.out.println("Exception in readExcel: "+gEDE.getMessage());
				gEDE.printStackTrace();
		        }
	}
	 
	@Test(description = "method to create rule for Sum of Invoice Line Amount", priority = 127)
	public void ruletosumInvoiceLine() throws Exception{
		List<String>users= new ArrayList<String>();
		users.add("Chris Black");
			
			Reporter.log("Rule for Sum of Invoice Line Amount begins");
			approvalTest.ConfigUI("summationAttributeRule1",username,password);
			Reporter.log("Rule created and deployed");
    }
	
	@Test(description = "Method to check two invoice lines are within limit mentioned in RHS attribute", priority = 128,dependsOnMethods = {"ruletosumInvoiceLine"} )
	public void ApprovalFlowtosumInvoiceLine() {
	    List<String>users= new ArrayList<String>();
	    users.add("Chris Black");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(82);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtosumInvoiceLine",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						System.out.println("Get All Assigned Users list ");								
							Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String>retrivedusers=new ArrayList<>();
							retrivedusers.addAll(listofApprovers.values());
							
							if(!retrivedusers.containsAll(users))									
							{
								Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
								Reporter.log("Invoice is not assigned to all Expected users");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	 
	@Test(description = "method to create rule in EURO and use arithmetic operator1", priority = 129)
	public void ruletoUseArithmeticOperator1() throws Exception{
			 
			Reporter.log("Rule for using EURO and Arithmetic Operator 1 begins");
			approvalTest.ConfigUI("arithmeticoperator1",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
	@Test(description = "Method to check currency conversion with arithmetic attribute 1 applied on RHS attribute", priority = 130,dependsOnMethods = {"ruletoUseArithmeticOperator1"} )
	public void ApprovalFlowtoUseArithmeticOperator1() {
	  			  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(83);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoice(invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							System.out.println("InvoiceApproval Status ");
							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
	  					}
	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
    @Test(description = "method to create rule in EURO and use arithmetic operator2", priority = 131)
	public void ruletoUseArithmeticOperator2() throws Exception{
			 
			Reporter.log("Rule for using EURO and Arithmetic Operator 3 begins");
			approvalTest.ConfigUI("arithmeticoperator2",username,password);
			Reporter.log("Rule created and deployed");
 	}
	 
	@Test(description = "Method to check currency conversion with arithmetic attribute 2 applied on RHS attribute", priority = 132,dependsOnMethods = {"ruletoUseArithmeticOperator2"} )
	public void ApprovalFlowtoUseArithmeticOperator2() {
	  			  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(84);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtoUseArithmeticOperator2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
							CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							System.out.println("InvoiceApproval Status ");
							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");

	  						}	
	
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	} 
	
	@Test(description = "method to create rule in EURO and use arithmetic operator3", priority = 133)
	public void ruletoUseArithmeticOperator3() throws Exception{
			 
			Reporter.log("Rule for using EURO and Arithmetic Operator 3 begins");
			approvalTest.ConfigUI("arithmeticoperator3",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check currency conversion with arithmetic attribute 3 applied on RHS attribute", priority = 134,dependsOnMethods = {"ruletoUseArithmeticOperator3"} )
	public void ApprovalFlowtoUseArithmeticOperator3() {
	  			  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(85);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtoUseArithmeticOperator3",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							System.out.println("InvoiceApproval Status ");
							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
							
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	} 
	 
	@Test(description = "method to create rule in EURO and use arithmetic operator4", priority = 135)
	public void ruletoUseArithmeticOperator4() throws Exception{
			 
			Reporter.log("Rule for using EURO and Arithmetic Operator 4 begins");
			approvalTest.ConfigUI("arithmeticoperator4",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check currency conversion with arithmetic attribute 4 applied on RHS attribute", priority = 136,dependsOnMethods = {"ruletoUseArithmeticOperator4"} )
	public void ApprovalFlowtoUseArithmeticOperator4() {
	  			  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(86);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtoUseArithmeticOperator4",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  							  						
	  						CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							System.out.println("InvoiceApproval Status ");
							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	  	}
	 
	
	@Test(description = "method to create rule in EURO and check Sum of Invoice Line Amount is less than Invoice Amount", priority = 137)
	public void ruletocheckSumofInvoiceLineAmountinEURO() throws Exception{
			 
			Reporter.log("Rule for using EURO and check Sum of Invoice Line Amount is less than Invoice Amount begins");
			approvalTest.ConfigUI("SumofLineAmountlessthanInvoiceAmount",username,password);
			Reporter.log("Rule created and deployed");
	}
    
	@Test(description = "Method to check currency conversion with Sum of Invoice Line Amount applied on LHS attribute", priority = 138,dependsOnMethods = {"ruletocheckSumofInvoiceLineAmountinEURO"} )
	public void ApprovalFlowtocheckSumofInvoiceLineAmountinEURO() {
			  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(87);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtocheckSumofInvoiceLineAmountinEURO",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  							  						
	  						CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							System.out.println("InvoiceApproval Status ");
							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}

	@Test(description = "method to create rule in EURO and check Invoice Line Amount is less than Invoice Amount", priority = 139)
	public void ruletocheckInvoiceLineAmountinEURO() throws Exception{
			 
			Reporter.log("Rule for using EURO and check Invoice Line Amount is less than Invoice Amount begins");
			approvalTest.ConfigUI("LineAmountlessthanInvoiceAmount",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check currency conversion with Invoice Line Amount applied on LHS attribute", priority = 140,dependsOnMethods = {"ruletocheckInvoiceLineAmountinEURO"} )
	public void ApprovalFlowtocheckInvoiceLineAmountinEURO() {
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(88);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtocheckInvoiceLineAmountinEURO",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
							System.out.println("Get InvoiceApproval Status ");
							Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
							System.out.println("InvoiceApproval Status ");
							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");

							Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

							Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	
	@Test(description = "method to create rule to check Invoice Line Amount and Sum of Invoice Line Amount", priority = 141)
	public void ruletocheckInvoiceLineAmountiandSumofInvoiceLineAmount() throws Exception{
	  	List<String>users= new ArrayList<String>();
	    users.add("Terry Green");
			 
			Reporter.log("Rule to check Invoice Line Amount and Sum of Invoice Line Amount begins");
			approvalTest.ConfigUI("summationAttributeRule2",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to check one invoice and two invoice lines", priority = 142,dependsOnMethods = {"ruletocheckInvoiceLineAmountiandSumofInvoiceLineAmount"} )
	public void ApprovalFlowtocheckInvoiceLineAmountiandSumofInvoiceLineAmount() {
	  		List<String>users= new ArrayList<String>();
	  		users.add("Terry Green");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(89);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ApprovalFlowtocheckInvoiceLineAmountiandSumofInvoiceLineAmount",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						System.out.println("Get All Assigned Users list ");								
							Map<String,String> listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String>retrivedusers=new ArrayList<>();
							retrivedusers.addAll(listofApprovers.values());
							
							if(!retrivedusers.containsAll(users))									
							{
								Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
								Reporter.log("Invoice is not assigned to all Expected users");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	   
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc1", priority = 143)
	public void ruletocheckAASuperVisorHierarchy1() throws Exception{
			 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc1 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy1",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc1 flow1", priority = 144,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy1"} )
	public void ruletocheckAASuperVisorHierarchy1Flow1() {
	    List<String>users= new ArrayList<String>();
	    users.add("Robert Jackman");
	    users.add("Henry Jones");
	    users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(90);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy1Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc1 flow2", priority = 145,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy1"} )
	public void ruletocheckAASuperVisorHierarchy1Flow2() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Robert Jackman");
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(91);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy1Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc1 flow3", priority = 146,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy1"} )
	public void ruletocheckAASuperVisorHierarchy1Flow3() {
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(92);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy1Flow3",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						
	  						System.out.println("InvoiceApproval Status ");
	  						invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");
	  						
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	 
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc2", priority = 147)
	public void ruletocheckAASuperVisorHierarchy2() throws Exception{
	
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc2 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy2",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc2 flow1", priority = 148,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy2"} )
	public void ruletocheckAASuperVisorHierarchy2Flow1() {
	    List<String>users= new ArrayList<String>();
	    users.add("Barry Rogers");
	  	users.add("Adriana Sanchez");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(93);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy2Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  							  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc2 flow2", priority = 149,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy2"} )
	public void ruletocheckAASuperVisorHierarchy2Flow2() {
	  		List<String>users= new ArrayList<String>();
	  		users.add("Barry Rogers");
	  		users.add("Adriana Sanchez");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(94);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy2Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						System.out.println("InvoiceApproval Status ");
	  						invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");

	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	 
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc3", priority = 150)
	public void ruletocheckAASuperVisorHierarchy3() throws Exception{
			 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc3 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy3",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc3 flow1", priority = 151,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy3"} )
	public void ruletocheckAASuperVisorHierarchy3Flow1() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Mary Johnson");
	  	users.add("Ted Brown");
	  	users.add("Alicia Daniels");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(95);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy3Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	    
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc4", priority = 152)
	public void ruletocheckAASuperVisorHierarchy4() throws Exception{
			 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc4 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy4",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc4 flow1", priority = 153,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy4"} )
	public void ruletocheckAASuperVisorHierarchy4Flow1() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Linda Wright");
	  	users.add("Ted Brown");
	  	users.add("Alicia Daniels");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(96);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy4Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	    
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc5", priority = 154)
	public void ruletocheckAASuperVisorHierarchy5() throws Exception{
			 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc5 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy5",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc5 flow1", priority = 155,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy5"} )
	public void ruletocheckAASuperVisorHierarchy5Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(97);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy5Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  						
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	   
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc6", priority = 156)
	public void ruletocheckAASuperVisorHierarchy6() throws Exception{
			 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc6 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy6",username,password);
			Reporter.log("Rule created and deployed");

	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc6 flow1", priority = 157,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy6"} )
	public void ruletocheckAASuperVisorHierarchy6Flow1() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Michelle Shannon");
	  	users.add("Henry Jones");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(98);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy6Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}   
	  	 	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc6 flow2", priority = 158,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy6"} )
	public void ruletocheckAASuperVisorHierarchy6Flow2() {
	    List<String>users= new ArrayList<String>();
	    users.add("Barry Rogers");
	  	users.add("Adriana Sanchez");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(99);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy6Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc7", priority = 159)
    public void ruletocheckAASuperVisorHierarchy7() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc7 begins");
     		approvalTest.ConfigUI("AASuperVisorHierarchy7",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
    @Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc7 flow1", priority = 160,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy7"} )
	public void ruletocheckAASuperVisorHierarchy7Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Mary Johnson");
	  	users.add("Ted Brown");
	  	users.add("Linda Wright");
	  	users.add("Alicia Daniels");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(100);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						updateInvoice("ruletocheckAASuperVisorHierarchy7Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc7 flow2", priority = 161,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy7"} )
	public void ruletocheckAASuperVisorHierarchy7Flow2() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Mary Johnson");
	  	users.add("Ted Brown");
	  	users.add("Linda Wright");
	  	users.add("Joshua Davis");
	  	users.add("Alicia Daniels");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(101);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy7Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	    

    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc8", priority = 162)
    public void ruletocheckAASuperVisorHierarchy8() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc8 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy8",username,password);
			Reporter.log("Rule created and deployed");
 	}

    @Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc8 flow1", priority = 163,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy8"} )
	public void ruletocheckAASuperVisorHierarchy8Flow1() {
		  	try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(102);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy8Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("InvoiceApproval Status ");
	  						invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.equalsIgnoreCase("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");					
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	  	}
		    
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy tc9", priority = 164)
    public void ruletocheckAASuperVisorHierarchy9() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy tc9 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchy9",username,password);
			Reporter.log("Rule created and deployed");
 	}

    @Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy tc9 flow1", priority = 165,dependsOnMethods = {"ruletocheckAASuperVisorHierarchy9"} )
	public void ruletocheckAASuperVisorHierarchy9Flow1() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Robert Jackman");
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(103);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchy9Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
		    
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc1", priority = 166)
    public void ruletocheckAASuperVisorHierarchyJobLevel1() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc1 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel1",username,password);
			Reporter.log("Rule created and deployed");
 	}
		    
    @Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc1 flow1", priority = 167,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel1"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel1Flow1() {
	  	List<String>users= new ArrayList<String>();
	    users.add("Cody Harken");
	  	users.add("Darryl Moise");
	  	users.add("Julio Manes");
	  	users.add("Carlene Rozell");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(104);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel1Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	 	
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc2", priority = 168)
    public void ruletocheckAASuperVisorHierarchyJobLevel2() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc2 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel2",username,password);
			Reporter.log("Rule created and deployed");
 	}
		     
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc2 flow1", priority = 169,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel2"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel2Flow1() {
	    List<String>users= new ArrayList<String>();
	    users.add("Julio Manes");
	  	users.add("Darryl Moise");
	  	users.add("Carlene Rozell");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(105);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel2Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc2 flow2", priority = 170,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel2"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel2Flow2() {
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(106);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel2Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						
	  						System.out.println("InvoiceApproval Status ");
	  						invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");
	  						
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");

	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}

    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc3", priority = 171)
    public void ruletocheckAASuperVisorHierarchyJobLevel3() throws Exception{
					 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc3 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel3",username,password);
			Reporter.log("Rule created and deployed");
 	}
	 
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc3 flow1", priority = 172,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel3"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel3Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  	users.add("Julio Manes");
	  	users.add("Carlene Rozell");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(107);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel3Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc3 flow2", priority = 173,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel3"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel3Flow2() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  	users.add("Julio Manes");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(108);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel3Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}    

	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc4", priority = 174)
    public void ruletocheckAASuperVisorHierarchyJobLevel4() throws Exception{
						 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc4 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel4",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
    @Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc4 flow1", priority = 175,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel4"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel4Flow1() {
    
    	try {
				String action="APPROVE";
			
				Row dataRow = invoiceDataSheet.getRow(109);
				DataFormatter formatter = new DataFormatter();
				System.out.println("data row are "+dataRow.getRowNum());
				
				if(dataRow.getRowNum() > 0) {
					Iterator<Cell> dataRowCell = dataRow.cellIterator();
					Reporter.log("Reading data from data file");
					while (dataRowCell.hasNext()) 
	                {
	                    Cell rowCell = dataRowCell.next();
	                   
	                  // System.out.println("rowCellType : "+rowCell.getCellType());
	                   
	                   if(rowCell.getColumnIndex() == 1)
	                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	                   if(rowCell.getColumnIndex() == 2) 
	                	   invoiceAmount = formatter.formatCellValue(rowCell);
	                }
					
					Reporter.log("Reading data from data file completed");
					
					System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
					Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
					
					System.out.println("Processing of Invoice Creation ");
					Reporter.log("Processing of Invoice Creation ");
					createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel4Flow1",invoiceNumber,invoiceAmount);
					
					System.out.println("Processing of Workflow Initiation ");
					Reporter.log("Processing of Workflow Initiation ");
					initiateWorkflow();
					
					CommonUtils.hold(10);
					System.out.println("Get InvoiceApproval Status ");
					Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
					System.out.println("InvoiceApproval Status ");
					invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Rejected","", "finuser1");

					Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");

					Assert.assertTrue(invoiceApprovalStatus.equalsIgnoreCase("Rejected"), "Processing of invoice "+invoiceNumber+" workflow ends with status <"+invoiceApprovalStatus+"> And expected status is Rejected");
				}	

				
		}catch(Exception gEDE) {
			System.out.println("Exception in readExcel: "+gEDE.getMessage());
			gEDE.printStackTrace();
	}
	
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc4 flow2", priority = 176,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel4"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel4Flow2() {
		List<String>users= new ArrayList<String>();
		users.add("Darryl Moise");
		  		
		  		try {
		  					String action="APPROVE";
		  				
		  					Row dataRow = invoiceDataSheet.getRow(110);
		  					DataFormatter formatter = new DataFormatter();
		  					System.out.println("data row are "+dataRow.getRowNum());
		  					
		  					if(dataRow.getRowNum() > 0) {
		  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
		  						Reporter.log("Reading data from data file");
		  						while (dataRowCell.hasNext()) 
		  		                {
		  		                    Cell rowCell = dataRowCell.next();
		  		                   
		  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
		  		                   
		  		                   if(rowCell.getColumnIndex() == 1)
		  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
		  		                   if(rowCell.getColumnIndex() == 2) 
		  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
		  		                }
		  						
		  						Reporter.log("Reading data from data file completed");
		  						
		  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
		  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
		  						
		  						System.out.println("Processing of Invoice Creation ");
		  						Reporter.log("Processing of Invoice Creation ");
		  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel4Flow2",invoiceNumber,invoiceAmount);
		  						
		  						System.out.println("Processing of Workflow Initiation ");
		  						Reporter.log("Processing of Workflow Initiation ");
		  						initiateWorkflow();
		  						
		  						System.out.println("Get InvoiceApproval Status ");
		  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
		  						getInvoiceApprovalsStatus();
		  						
		  						System.out.println("Processing of Invoice Approval Wrokflow ");
		  						Reporter.log("Processing of Invoice Approval Wrokflow ");
		  						executeInvoiceAction(action,users);
		  						
		  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
								List<String> values = new ArrayList<>(map.values());
								System.out.println("Expected Approver "+users);
								System.out.println("Actual  Approver "+values);
								if(!values.containsAll(users))
								{
									System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
									Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
									Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

								}
								
		  						
		  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
		  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
		  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
		  					}	

		  					
		  			}catch(Exception gEDE) {
		  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
		  				gEDE.printStackTrace();
		  		}
	}
				    
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc5", priority = 177)
    public void ruletocheckAASuperVisorHierarchyJobLevel5() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc5 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel5",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc5 flow1", priority = 178,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel5"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel5Flow1() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(111);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel5Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
    @Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc5 flow2", priority = 179,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel5"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel5Flow2() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(112);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel5Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc5 flow3", priority = 180,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel5"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel5Flow3() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(113);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel5Flow3",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  					    

    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc6", priority = 181)
    public void ruletocheckAASuperVisorHierarchyJobLevel6() throws Exception{
						 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc6 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel6",username,password);
			Reporter.log("Rule created and deployed");
 	}
	 
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc6 flow1", priority = 182,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel6"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel6Flow1() {
	    List<String>users= new ArrayList<String>();
	    users.add("Darryl Moise");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(114);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel6Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc6 flow2", priority = 183,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel6"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel6Flow2() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(115);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel6Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc6 flow3", priority = 184,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel6"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel6Flow3() {
	    List<String>users= new ArrayList<String>();
	    users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(116);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel6Flow3",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}		    

    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc7", priority = 185)
    public void ruletocheckAASuperVisorHierarchyJobLevel7() throws Exception{
						 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc7 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel7",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc7 flow1", priority = 186,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel7"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel7Flow1() {
	  		List<String>users= new ArrayList<String>();
	  		users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(117);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel7Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc7 flow2", priority = 187,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel7"} )
	  	public void ruletocheckAASuperVisorHierarchyJobLevel7Flow2() {
	  		List<String>users= new ArrayList<String>();
	  		users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(118);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel7Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	   		    
	@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc8", priority = 188)
    public void ruletocheckAASuperVisorHierarchyJobLevel8() throws Exception{
								 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc8 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel8",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc8 flow1", priority = 189,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel8"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel8Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(119);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel8Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc8 flow2", priority = 190,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel8"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel8Flow2() {
	    List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(120);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel8Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc9", priority = 191)
    public void ruletocheckAASuperVisorHierarchyJobLevel9() throws Exception{
								 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc9 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel9",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc9 flow1", priority = 192,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel9"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel9Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(121);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel9Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc10", priority = 193)
    public void ruletocheckAASuperVisorHierarchyJobLevel10() throws Exception{
						 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc10 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel10",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc10 flow1", priority = 194,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel10"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel10Flow1() {
	  		
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(122);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel10Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						CommonUtils.hold(10);
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						
	  						System.out.println("InvoiceApproval Status ");
	  						invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","", "finuser1");
	  						
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	  	}
	  		
    /*@Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc11", priority = 195)
    public void ruletocheckAASuperVisorHierarchyJobLevel11() throws Exception{
								 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc11 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel11",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc11 flow1", priority = 196,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel11"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel11Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Robert Jackman");
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(123);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel11Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	  	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc11 flow2", priority = 197,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel11"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel11Flow2() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Robert Jackman");
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(124);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel11Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc12", priority = 198)
    public void ruletocheckAASuperVisorHierarchyJobLevel12() throws Exception{
				 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc12 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel12",username,password);
			Reporter.log("Rule created and deployed");
 	}
	
 	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc12 flow1", priority = 199,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel12"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel12Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Robert Jackman");
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(125);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel12Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	  	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc12 flow2", priority = 200,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel12"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel12Flow2() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Robert Jackman");
	  	users.add("Henry Jones");
	  	users.add("Michelle Shannon");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(126);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel12Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}*/
	
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc13", priority = 201)
    public void ruletocheckAASuperVisorHierarchyJobLevel13() throws Exception{
							 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc13 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel13",username,password);
			Reporter.log("Rule created and deployed");
 	}

	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc13 flow1", priority = 202,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel13"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel13Flow1() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  	users.add("Julio Manes");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(127);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel13Flow1",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  	
	@Test(description = "method to create invoice rule for Approval Authority SuperVisor Hierarchy Job Level tc13 flow2", priority = 203,dependsOnMethods = {"ruletocheckAASuperVisorHierarchyJobLevel13"} )
	public void ruletocheckAASuperVisorHierarchyJobLevel13Flow2() {
	  	List<String>users= new ArrayList<String>();
	  	users.add("Darryl Moise");
	  	users.add("Julio Manes");
	  	users.add("Carlene Rozell");
	  		try {
	  					String action="APPROVE";
	  				
	  					Row dataRow = invoiceDataSheet.getRow(128);
	  					DataFormatter formatter = new DataFormatter();
	  					System.out.println("data row are "+dataRow.getRowNum());
	  					
	  					if(dataRow.getRowNum() > 0) {
	  						Iterator<Cell> dataRowCell = dataRow.cellIterator();
	  						Reporter.log("Reading data from data file");
	  						while (dataRowCell.hasNext()) 
	  		                {
	  		                    Cell rowCell = dataRowCell.next();
	  		                   
	  		                  // System.out.println("rowCellType : "+rowCell.getCellType());
	  		                   
	  		                   if(rowCell.getColumnIndex() == 1)
	  		                	   invoiceNumber = formatter.formatCellValue(rowCell)+uniqueid;
	  		                   if(rowCell.getColumnIndex() == 2) 
	  		                	   invoiceAmount = formatter.formatCellValue(rowCell);
	  		                }
	  						
	  						Reporter.log("Reading data from data file completed");
	  						
	  						System.out.println("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						Reporter.log("Processing of Invoice "+invoiceNumber+" with Amount "+invoiceAmount+" workflow starts");
	  						
	  						System.out.println("Processing of Invoice Creation ");
	  						Reporter.log("Processing of Invoice Creation ");
	  						createInvoicewithoutLineChange("ruletocheckAASuperVisorHierarchyJobLevel13Flow2",invoiceNumber,invoiceAmount);
	  						
	  						System.out.println("Processing of Workflow Initiation ");
	  						Reporter.log("Processing of Workflow Initiation ");
	  						initiateWorkflow();
	  						
	  						System.out.println("Get InvoiceApproval Status ");
	  						Reporter.log("Retrieving InvoiceApproval Status post wrokflow initiation");
	  						getInvoiceApprovalsStatus();
	  						
	  						System.out.println("Processing of Invoice Approval Wrokflow ");
	  						Reporter.log("Processing of Invoice Approval Wrokflow ");
	  						executeInvoiceAction(action,users);
	  						
	  						Map<String, String> map = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
							List<String> values = new ArrayList<>(map.values());
							System.out.println("Expected Approver "+users);
							System.out.println("Actual  Approver "+values);
							if(!values.containsAll(users))
							{
								System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >  As expected and actual list of assignee does not match");
								Assert.assertTrue(users.containsAll(values), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > As expected and actual list of assignee does not match");

							}
							
	  						
	  						System.out.println("Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Reporter.log("invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" >");
	  						Assert.assertTrue(invoiceApprovalStatus.contains("Workflow approved"), "Processing of invoice "+invoiceNumber+" workflow ends with status < "+invoiceApprovalStatus+" > And expected status is Workflow approved");
	  					}	

	  					
	  			}catch(Exception gEDE) {
	  				System.out.println("Exception in readExcel: "+gEDE.getMessage());
	  				gEDE.printStackTrace();
	  		}
	}
	  						    
    @Test(description = "method to create rule for Approval Authority SuperVisor Hierarchy Job Level tc14", priority = 204)
    public void ruletocheckAASuperVisorHierarchyJobLevel14() throws Exception{
								 
			Reporter.log("Rule for Approval Authority SuperVisor Hierarchy Job Level tc14 begins");
			approvalTest.ConfigUI("AASuperVisorHierarchyJobLevel14",username,password);
     		Reporter.log("Rule created and deployed");					
	}
	

    public void createInvoice1(String payload,String invoiceNumber,String invoiceAmount) {
		try {
			Response serviceResponse;
			String createInvoiceService = executableHost+restInfo.get(payload).get("RequestURL");
			header.put("Content-Type", "application/json");
			String servicePayload = restInfo.get(payload).get("Payloads").toString();

			System.out.println("Create Invoice Service : "+createInvoiceService);
			System.out.println("Create Invoice Payload : "+servicePayload);

			// Updating invoice Payload beings

			String updatedCreateInvoicePayload = RestCommonUtils.updateSerivcePayload(servicePayload, "InvoiceNumber",invoiceNumber,"InvoiceAmount",invoiceAmount,"LineAmount",invoiceAmount,"Quantity",invoiceAmount);
			System.out.println("update Invoice Payload : "+updatedCreateInvoicePayload);
			// Updating invoice Payload end


			System.out.println("Invoice Creation Initiated");
			Reporter.log("Invoice Creation Initiated");
			serviceResponse = RestCommonUtils.postRequest(createInvoiceService, updatedCreateInvoicePayload, header, "finuser1", "Welcome1");

			System.out.println("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			System.out.println("Invoice Creation Service Reponse body : "+serviceResponse.getBody().asString());

			Reporter.log("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			if(serviceResponse.getStatusCode() == 201) {
				parsedResponse = RestCommonUtils.responseParser(serviceResponse, "", "CreatedBy","InvoiceNumber","InvoiceId");
				invoiceId = parsedResponse.get("FINUSER1").get("InvoiceId");
				invoiceName = RestCommonUtils.parseResponsetemp(serviceResponse, "InvoiceNumber");
				System.out.println(invoiceId+" is ID of Invoice Number :"+invoiceName);
				Reporter.log("Invoice created successfully, "+invoiceId+" is ID of Invoice :"+invoiceName);
			}
			else {
				Reporter.log("Invoice creation fails with response code : "+serviceResponse.getStatusCode());
				Assert.fail();
			}


		}catch(Exception cIE) {
			System.out.println("Exception in createInvoice method "+cIE.getMessage());
			cIE.printStackTrace();
			Assert.fail();
		}
		finally{
			System.out.println("Clearing header fields of createInvoice method");
			header.clear();
		}
	}

	public void createInvoicewithoutLineChange(String payload,String invoiceNumber,String invoiceAmount) {
		try {
			Response serviceResponse;
			String createInvoiceService = executableHost+restInfo.get(payload).get("RequestURL");
			header.put("Content-Type", "application/json");
			String servicePayload = restInfo.get(payload).get("Payloads").toString();

			System.out.println("Create Invoice Service : "+createInvoiceService);
			System.out.println("Create Invoice Payload : "+servicePayload);

			// Updating invoice Payload beings

			String updatedCreateInvoicePayload = RestCommonUtils.updateSerivcePayload(servicePayload, "InvoiceNumber",invoiceNumber,"InvoiceAmount",invoiceAmount);
			System.out.println("update Invoice Payload : "+updatedCreateInvoicePayload);
			// Updating invoice Payload end


			System.out.println("Invoice Creation Initiated");
			Reporter.log("Invoice Creation Initiated");
			serviceResponse = RestCommonUtils.postRequest(createInvoiceService, updatedCreateInvoicePayload, header, "finuser1", "Welcome1");

			System.out.println("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			System.out.println("Invoice Creation Service Reponse body : "+serviceResponse.getBody().asString());

			Reporter.log("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			if(serviceResponse.getStatusCode() == 201) {
				parsedResponse = RestCommonUtils.responseParser(serviceResponse, "", "CreatedBy","InvoiceNumber","InvoiceId");
				invoiceId = parsedResponse.get("FINUSER1").get("InvoiceId");
				invoiceName = RestCommonUtils.parseResponsetemp(serviceResponse, "InvoiceNumber");
				System.out.println(invoiceId+" is ID of Invoice Number :"+invoiceName);
				Reporter.log("Invoice created successfully, "+invoiceId+" is ID of Invoice :"+invoiceName);
			}
			else {
				Reporter.log("Invoice creation fails with response code : "+serviceResponse.getStatusCode());
				Assert.fail();
			}


		}catch(Exception cIE) {
			System.out.println("Exception in createInvoice method "+cIE.getMessage());
			cIE.printStackTrace();
			Assert.fail();
		}
		finally{
			System.out.println("Clearing header fields of createInvoice method");
			header.clear();
		}
	}
	public void createInvoice(String invoiceNumber, String invoiceAmount, String... ars) {
		try {
			Response serviceResponse;
			String createInvoiceService = executableHost+restInfo.get("createInvoice").get("RequestURL");
			header.put("Content-Type", "application/json");
			String servicePayload = restInfo.get("createInvoice").get("Payloads").toString();

			System.out.println("Create Invoice Service : "+createInvoiceService);
			System.out.println("Create Invoice Payload : "+servicePayload);

			// Updating invoice Payload beings

			String updatedCreateInvoicePayload = RestCommonUtils.updateSerivcePayload(servicePayload, "InvoiceNumber",invoiceNumber,"InvoiceAmount",invoiceAmount,"LineAmount",invoiceAmount,"Quantity",invoiceAmount);
			System.out.println("update Invoice Payload : "+updatedCreateInvoicePayload);
			// Updating invoice Payload end


			System.out.println("Invoice Creation Initiated");
			Reporter.log("Invoice Creation Initiated");
			serviceResponse = RestCommonUtils.postRequest(createInvoiceService, updatedCreateInvoicePayload, header, "finuser1", "Welcome1");

			System.out.println("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			System.out.println("Invoice Creation Service Reponse body : "+serviceResponse.getBody().asString());

			Reporter.log("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			if(serviceResponse.getStatusCode() == 201) {
				parsedResponse = RestCommonUtils.responseParser(serviceResponse, "", "CreatedBy","InvoiceNumber","InvoiceId");
				invoiceId = parsedResponse.get("FINUSER1").get("InvoiceId");
				invoiceName = RestCommonUtils.parseResponsetemp(serviceResponse, "InvoiceNumber");
				System.out.println(invoiceId+" is ID of Invoice Number :"+invoiceName);
				Reporter.log("Invoice created successfully, "+invoiceId+" is ID of Invoice :"+invoiceName);
			}
			else {
				Reporter.log("Invoice creation fails with response code : "+serviceResponse.getStatusCode());
				Assert.fail();
			}


		}catch(Exception cIE) {
			System.out.println("Exception in createInvoice method "+cIE.getMessage());
			cIE.printStackTrace();
			Assert.fail();
		}
		finally{
			System.out.println("Clearing header fields of createInvoice method");
			header.clear();
		}
	}
	
	public void updateInvoice(String payload,String invoiceNumber, String invoiceAmount) {
		try {
			Response serviceResponse;
			String createInvoiceService = executableHost+restInfo.get(payload).get("RequestURL");
			header.put("Content-Type", "application/json");
			String servicePayload = restInfo.get(payload).get("Payloads").toString();
			
			System.out.println("Create Invoice Service : "+createInvoiceService);
			System.out.println("Create Invoice Payload : "+servicePayload);
			
			// Updating invoice Payload beings
			
			String updatedCreateInvoicePayload = RestCommonUtils.updateSerivcePayload(servicePayload, "InvoiceNumber",invoiceNumber,"InvoiceAmount",invoiceAmount);
			System.out.println("update Invoice Payload : "+updatedCreateInvoicePayload);
			// Updating invoice Payload end
						
			
			System.out.println("Invoice Creation Initiated");
			Reporter.log("Invoice Creation Initiated");
			serviceResponse = RestCommonUtils.postRequest(createInvoiceService, updatedCreateInvoicePayload, header, "finuser1", "Welcome1");
			
			System.out.println("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			System.out.println("Invoice Creation Service Reponse body : "+serviceResponse.getBody().asString());
			
			Reporter.log("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());
			if(serviceResponse.getStatusCode() == 201) {
				parsedResponse = RestCommonUtils.responseParser(serviceResponse, "", "CreatedBy","InvoiceNumber","InvoiceId");
				invoiceId = parsedResponse.get("FINUSER1").get("InvoiceId");
				invoiceName = RestCommonUtils.parseResponsetemp(serviceResponse, "InvoiceNumber");
				System.out.println(invoiceId+" is ID of Invoice Number :"+invoiceName);
				Reporter.log("Invoice created successfully, "+invoiceId+" is ID of Invoice :"+invoiceName);
			}
			else {
				Reporter.log("Invoice creation fails with response code : "+serviceResponse.getStatusCode());
				Assert.fail();
			}
				
			
		}catch(Exception cIE) {
			System.out.println("Exception in createInvoice method "+cIE.getMessage());
			cIE.printStackTrace();
			Assert.fail();
		}
		finally{
			System.out.println("Clearing header fields of createInvoice method");
			header.clear();
		}
	}
	
	public void initiateWorkflow() {
		try {
			Response serviceResponse;
			String workFlowInitiationService = executableHost+restInfo.get("initiateWorkflow").get("RequestURL");
			header.put("Content-Type", "application/json");
			String servicePayload = restInfo.get("initiateWorkflow").get("Payloads").toString();

			System.out.println("WorkFlow initiation Service : "+workFlowInitiationService);
			System.out.println("WorkFlow initiation Payload : "+servicePayload);


			String updatedPayload = RestCommonUtils.updatePayload(servicePayload, "ESSParameters",invoiceId+",,,,204,,,");

			System.out.println("Updated Payload : "+updatedPayload);
			System.out.println("WorkFlow Initiated");

			Reporter.log("Initiating WorkFlow for invoice : "+invoiceId);
			serviceResponse = RestCommonUtils.postRequest(workFlowInitiationService, updatedPayload, header, "finuser1", "Welcome1");

			System.out.println("Invoice Creation Service Reponse Status : "+serviceResponse.getStatusCode());


			if(serviceResponse.getStatusCode() == 201) {
				parsedResponse = RestCommonUtils.responseParser(serviceResponse, "", "OperationName","ReqstId");
				System.out.println("Request ID :"+parsedResponse.get("submitESSJobRequest").get("ReqstId"));
				Reporter.log("WorkFlow for invoice : "+invoiceId+" initiated Successfully");
			}else {
				Reporter.log("WorkFlow for invoice : "+invoiceId+" not initiated with response code :"+serviceResponse.getStatusCode());
				Assert.fail();
			}

			CommonUtils.hold(10);
		}catch(Exception wIE) {
			System.out.println("Exception in initiateWorkflow method : "+wIE.getMessage());
			wIE.printStackTrace();
			Assert.fail();
		}
		finally{
			System.out.println("Clearing header fields of initiateWorkflow method");
			header.clear();
		}
	}

	public void getInvoiceApprovalsStatus() {
		try {
			CommonUtils.hold(5);
			System.out.println("Retreiving status of invoice "+invoiceId);
			invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(),"Initiated","", "finuser1");

			Reporter.log("Approval Status of Invoice post WorkFlow initiation : "+invoiceApprovalStatus);

			Assert.assertTrue(invoiceApprovalStatus.equalsIgnoreCase("Initiated"));
			System.out.println("Invoice Status post workflow Initiate : "+invoiceApprovalStatus);

		}catch(Exception gISE) {
			System.out.println("Exception in getInvoiceStatus method : "+gISE.getMessage());
			Assert.fail();
		}
	}    

	public void executeInvoiceAction(String action ,List<String> users) {
		try {
			String approverName,approverDisplayName,nextapprovalDName;
			Map<String,String> listofApprovers = new HashMap<String,String>();
			Response invoiceActionResponse;
			boolean workFlowProcessed = false;
			String invoiceActionService ="",	invoiceActionServicePayload="";
			List<String> RoleUsers=new ArrayList<String>();
			long assigneeCheckStartTime = System.currentTimeMillis();
			if(!(invoiceApprovalStatus.equalsIgnoreCase("Required"))) {
			while(!(invoiceApprovalStatus.equalsIgnoreCase("Workflow approved") || invoiceApprovalStatus.equalsIgnoreCase("Rejected")) && !(workFlowProcessed)) {
				System.out.println("Get list of Approvers");

				if(users.get(0).equalsIgnoreCase("Role"))
				{
					RoleUsers=ApprovalsRESTUtils.getUsersFromRole(users.get(1));
					System.out.println("Role User :"+RoleUsers.get(0));
					if(!(RoleUsers.isEmpty()))
					{
						listofApprovers.put(RoleUsers.get(0), RoleUsers.get(0));
						Reporter.log("Invoice Assigned to role based Approver : "+RoleUsers.get(0));
						System.out.println("ApproverName :"+listofApprovers.get(RoleUsers.get(0)));
					}
				}else
					listofApprovers = ApprovalsRESTUtils.getInvoiceAssignees(invoiceName,"ASSIGNEES","ASSIGNEESDISPLAYNAME");

				if(!(listofApprovers.isEmpty())) {

					/*
					 *     get InvoiceTaskWorklist id of respective Invoice from the User session  
					 */
					for (Map.Entry<String, String> approverslist : listofApprovers.entrySet()) {
						approverName = approverslist.getKey();
						approverDisplayName = approverslist.getValue();

						Reporter.log("Invoice <"+invoiceName+"> Assigned to Approver : <"+approverDisplayName+">");

						if(!users.contains(approverDisplayName))
						{
							Log.info(approverDisplayName +" is not listed in expected user list");
							Reporter.log("Invoice not Assigned to Expected Approver. Expected Assignee < "+users+" > but Assigned to < "+approverDisplayName+" >");
							Assert.assertFalse(true,approverDisplayName +" is not listed in expected user list");
						}

						System.out.println(invoiceName+" assigned to approver : "+approverDisplayName);

						header.put("Content-Type", "application/json");

						String getWorkListIdServicePayload = restInfo.get("listAssginedInvoices").get("Payloads").toString();

						String workFlowId = ApprovalsRESTUtils.workListInvoiceId(getWorkListTasksIdService, getWorkListIdServicePayload, header, invoiceName, approverName);
						header.clear();

						System.out.println(workFlowId+" is the workFlowId of Invoice "+invoiceName+" for Approver "+approverDisplayName);
						Reporter.log(workFlowId+" is the workFlowId of Invoice "+invoiceName+" for Approver "+approverDisplayName);
						/*
						 *     Perform Invoice Action (APPROVE / REJECT) by Approver for Assigned Invoice 
						 */
						Reporter.log(approverDisplayName+" workflow Action for invoice < "+invoiceName+" > is < "+action+" >");

						if(action.equalsIgnoreCase("APPROVE"))
						{
							invoiceActionService= executableHost+restInfo.get("invoiceAction").get("RequestURL")+workFlowId;
							invoiceActionServicePayload  = restInfo.get("invoiceAction").get("Payloads").toString();
						}
						else if(action.equalsIgnoreCase("REJECT"))
						{
							invoiceActionService = executableHost+restInfo.get("invoiceActionReject").get("RequestURL")+workFlowId;
							invoiceActionServicePayload = restInfo.get("invoiceActionReject").get("Payloads").toString();
						}

						System.out.println("Initiating WorkFlow Action");

						Reporter.log("Initiating WorkFlow Action");

						System.out.println("invoiceAction Service : "+invoiceActionService);
						System.out.println("invoiceAction Payload : "+invoiceActionServicePayload);

						if(workFlowId != null) {

							header.put("Content-Type", "application/json");
							invoiceActionResponse = RestCommonUtils.putRequest(invoiceActionService, invoiceActionServicePayload.substring(1, invoiceActionServicePayload.length()-1), header, approverName, "Welcome1");

							CommonUtils.hold(10);

							Reporter.log("Workflow Action < "+action+" > completed on invoice < "+invoiceName+" > by Approver < "+approverDisplayName+" >");

							System.out.println("WorkFlow Action Completed");

							CommonUtils.hold(10);

							invoiceApprovalStatus = ApprovalsRESTUtils.getInvoiceApprovalStatus(invoiceStatusCheckService, invoiceId, Collections.<String, String>emptyMap(), "Workflow approved","Rejected", "finuser1");

							System.out.println("Invoice status Post APPROVE action by Approver <"+approverDisplayName+"> is <"+invoiceApprovalStatus+">");

							Reporter.log("Approval status of invoice < "+invoiceName+" > post < "+action+" > action by Approver < "+approverDisplayName+" > is < "+invoiceApprovalStatus+" >");

							if(invoiceApprovalStatus.equalsIgnoreCase("Workflow approved") || invoiceApprovalStatus.equalsIgnoreCase("Rejected") ) {
								//if ApprovalStatus is "Workflow approved" then Approval WorkFLow is completed.
								workFlowProcessed = true;
								break; 
							}									
							System.out.println("Final Status of Invoice <"+invoiceName+"> is <"+invoiceApprovalStatus+">");

						}else {
							System.out.println("Couldn't initiate Approve / Reject action on Invoice as Invoice WorkFlow id is NULL for Approver :"+approverDisplayName);
							Reporter.log("Couldn't initiate Approve / Reject action on Invoice as Invoice WorkFlow id is NULL for Approver :"+approverDisplayName);
							workFlowProcessed = true;
							break;
						}
					}
				}
				else
				{
					 System.out.println("Invoice not assinged to expected Approver with in "+((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))+" mins");
					    Reporter.log("Invoice not assinged to expected Approver with in "+((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))+" mins");
					    break;
				}
				listofApprovers.clear();	
			}
			}
			else {
				System.out.println("Can't process invoice as the invoice is in <"+invoiceApprovalStatus+"> status");
				Reporter.log("Can't process invoice as the invoice is in <"+invoiceApprovalStatus+"> status");
				}
		}catch(Exception eIAE) {
			System.out.println("Exception in executeInvoiceAction method : "+eIAE.getMessage());
			Assert.fail();
		}finally {
			header.clear();
		}
	}
	public boolean verifyIfInvoiceAssignedToAllUserInRole(String role) {
		Response workListTaskResponse;
		
		List <String>RoleUsers=ApprovalsRESTUtils.getUsersFromRole(role);
		if(!(RoleUsers.isEmpty()))
		{
			for(int i=0;i<RoleUsers.size();i++)
			{
			

				header.put("Content-Type", "application/json");

				String getWorkListIdServicePayload = restInfo.get("listAssginedInvoices").get("Payloads").toString();

				workListTaskResponse = RestCommonUtils.postRequest(getWorkListTasksIdService, getWorkListIdServicePayload.substring(1, getWorkListIdServicePayload.length()-1), header,  RoleUsers.get(i), "Welcome1");

				
				if(workListTaskResponse.getStatusCode()!=401)
				{
				String workFlowId = ApprovalsRESTUtils.workListInvoiceId(getWorkListTasksIdService, getWorkListIdServicePayload, header, invoiceName, RoleUsers.get(i));
				header.clear();

				System.out.println(workFlowId+" is the workFlowId of Invoice "+invoiceName+" for Approver "+RoleUsers.get(i));
				Reporter.log(workFlowId+" is the workFlowId of Invoice "+invoiceName+" for Approver "+RoleUsers.get(i));
				if (workFlowId==null || workFlowId.isEmpty())
				{
					System.out.println("Invoice not assigned to user "+RoleUsers.get(i));
				//	System.out.println("response is "+workListTaskResponse.getBody().asString());
					Reporter.log("Invoice not assigned to user "+RoleUsers.get(i));
					return false;
				}
				}
					
			}
		}
		return true;
	}
	@AfterMethod(alwaysRun = true)
	void logout(Method method){
		if(!method.getName().contains("Flow")) {
		System.out.println("Logging out from configUI");
		rules.logout(approvalDriver);
		System.out.println("Logged out from configui");
		}
	}

	@AfterClass(alwaysRun = true)
	void clearActiveReferences(){
		try {
			System.out.println("Closing active workbook and fileIpStream objects");
			workbook.close();	
			fileIpStream.close();
			System.out.println("Active workbook and fileIpStream Objects are Closed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
