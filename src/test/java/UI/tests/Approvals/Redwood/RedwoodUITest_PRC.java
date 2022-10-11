package UI.tests.Approvals.Redwood;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedwoodApprovalRuleConfigurationUtil;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import io.restassured.response.Response;
import pageDefinitions.REST.Approvals.ApprovalsRESTUtils;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigPage;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigUtil;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedwoodApprovalRuleConfigurationUtil;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigPage;
import pageDefinitions.UI.oracle.applcore.qa.Approval.RedWood.FndRedWoodRuleConfigUtil;
import pageDefinitions.REST.Approvals.*;

public class RedwoodUITest_PRC extends GetDriverInstance{
	
	
	String uniqueid;
	Map<String, Map<String, Object>> restInfo;
    static String Requisition,RequisitionHeaderId;
	Properties fileProperties;
	FileReader fileRead;
	FileInputStream fileIpStream;
	private FndRedWoodRuleConfigUtil ruleUtil;
	WebDriver approvalDriver;
	private FndRedWoodRuleConfigPage rules;
	private FndRedwoodApprovalRuleConfigurationUtil approvalTest;
	static String username,password;
	String RequisitionStatusCheckService,getWorkListTasksIdService;
	private PrcApprovalsUtil prcApprovalsUtil;
	static String RequisitionApprovalStatus;
  String executableHost;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void getInfo(String usern,String passwd) throws FileNotFoundException, IOException, ParseException {
		System.out.println("Parsing Approvals JSON");
	
			username = usern;
			password = passwd;
			 executableHost = RestCommonUtils.getExecutableHost();
				restInfo = RestCommonUtils.parseJson("approvalFlow", "./src/main/java/ConfigurationResources/REST/request.json");
		uniqueid = CommonUtils.uniqueId();
		approvalDriver = getDriverInstanceObject();
		ruleUtil=new FndRedWoodRuleConfigUtil(approvalDriver);
		rules=new FndRedWoodRuleConfigPage(approvalDriver);
		approvalTest=new FndRedwoodApprovalRuleConfigurationUtil(approvalDriver);
		prcApprovalsUtil=new PrcApprovalsUtil();
		RequisitionStatusCheckService = executableHost+restInfo.get("getRequisitionStatus").get("RequestURL");
		getWorkListTasksIdService = executableHost+restInfo.get("listAssginedRequisitions").get("RequestURL");
	}

	@BeforeMethod
	public void checkMainPage(Method method) throws Exception {
	if(!method.getName().contains("Flow")) {
		String task="Requisition"; 
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
	public void ruletoautoapprove() throws Exception{
		List<String>users= new ArrayList<String>();
		users.add("Terry Green");
			Reporter.log("Rule for SendApproval to User creation begins");
			approvalTest.ConfigUI("checkautoapprove",username,password);
			Reporter.log("Rule created and deployed");
	}
	
 @Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 2,dependsOnMethods= {"ruletoautoapprove"})
	public void autoapprovalFlow() {
    	String Header = prcApprovalsUtil.createrequisitionFromPayload("createReq1");
		System.out.println("header id is "+ Header);
		String[] arr=Header.split("-");
		System.out.println("For Requisition "+ arr[0]+" Requisition Header id is "+arr[1]);
		Reporter.log("For Requisition "+ arr[0]+" Requisition Header id is "+arr[1]);
		prcApprovalsUtil.SubmitReq(arr[1],username,password);
		//String status=GetStatus(Header);
		 RequisitionApprovalStatus = prcApprovalsUtil.getRequisitionApprovalStatus(RequisitionStatusCheckService, arr[1], Collections.<String, String>emptyMap(),"Approved","", "CVRQST01");
 
		if(RequisitionApprovalStatus.equalsIgnoreCase("Approved"));
		    
		    else
		    	assertTrue(false);
	}
	
	@Test(description = "method to create rule for SendApproval to User", priority = 3)
	public void RuleForRoleAsApprover() throws Exception{
		Reporter.log("Rule for SendApproval to role creation begins");
			approvalTest.ConfigUI("linetotal",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 4,dependsOnMethods= {"RuleForRoleAsApprover"})
	public void ApprovalFlowForRuleForRoleAsApprover() {
	String action="APPROVE";
	List<String> users=new ArrayList<String>();
	users.add("Role");
	users.add("ORA_FND_APPLICATION_DEVELOPER_JOB");
		 Reporter.log("before calling create req method");
    	String ReqHeaderId = prcApprovalsUtil.createrequisitionFromPayload("createReq2");
    	String parts[]=ReqHeaderId.split("-");
		System.out.println("header id is "+ parts[0]);
		ReqHeaderId=parts[1];
		String requisition=parts[0];
        Reporter.log("For requisition "+requisition+" reqisitionHeaderId is"+ ReqHeaderId);
		 Reporter.log("before submitting create req method");
		prcApprovalsUtil.SubmitReq(ReqHeaderId,username,password);
	 
			RequisitionApprovalStatus = PrcApprovalsUtil.getRequisitionApprovalStatus(RequisitionStatusCheckService, ReqHeaderId, Collections.<String, String>emptyMap(),"Pending approval","", "CVRQST01");
			boolean isInvoiceAssignedToAllUser=prcApprovalsUtil.verifyIfRequisitionAssignedToAllUserInRole(users.get(1),requisition);
			if(!isInvoiceAssignedToAllUser)
			{
				Assert.assertTrue(false, "Invoice Not assigned to all user in expected role");

			}
			List <String>RoleUsers=ApprovalsRESTUtils.getUsersFromRole("ORA_FND_APPLICATION_DEVELOPER_JOB");
			users.add(RoleUsers.get(0));
			
			 Reporter.log("before Initiation of Execute req method");
		
		String finalStatus=prcApprovalsUtil.executeRequisitionAction(action,users,requisition,ReqHeaderId);
		
		System.out.println("Processing of Requisition "+requisition+" workflow ends with status <"+finalStatus+">");
		Reporter.log("requisition "+requisition+" workflow ends with status < "+finalStatus+" >");
		Assert.assertTrue(finalStatus.contains("Approved"), "Processing of requisition "+requisition+" workflow ends with status <"+finalStatus+"> And expected status is Approved");
	
	}
	
	@Test(description = "method to create rule for SendApproval to User", priority = 5)
	public void RuleForParallelResponseWin() throws Exception{
		Reporter.log("Rule for SendApproval to role creation begins");
			approvalTest.ConfigUI("frw",username,password);
			Reporter.log("Rule created and deployed");
	}
	
	@Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 6,dependsOnMethods= {"RuleForParallelResponseWin"})
		public void ApprovalFlowForParallelResponseWin() {
		String action="APPROVE";
		List<String> users=new ArrayList<String>();
			users.add("Terry Green");
			users.add("Maria Smith");
			 Reporter.log("before calling create req method");
	    	String ReqHeaderId = prcApprovalsUtil.createrequisitionFromPayload("createReq2");
	    	String parts[]=ReqHeaderId.split("-");
			System.out.println("header id is "+ parts[0]);
			ReqHeaderId=parts[1];
			String requisition=parts[0];
            Reporter.log("For requisition "+requisition+" reqisitionHeaderId is"+ ReqHeaderId);
			 Reporter.log("before submitting create req method");
			prcApprovalsUtil.SubmitReq(ReqHeaderId,username,password);
			 Reporter.log("before Initiation of Execute req method");
			String finalStatus=prcApprovalsUtil.executeRequisitionAction(action,users,requisition,ReqHeaderId);
			
			System.out.println("Processing of Requisition "+requisition+" workflow ends with status <"+finalStatus+">");
			Reporter.log("requisition "+requisition+" workflow ends with status < "+finalStatus+" >");
			Assert.assertTrue(finalStatus.contains("Approved"), "Processing of requisition "+requisition+" workflow ends with status <"+finalStatus+"> And expected status is Approved");
		
		}
		@Test(description = "method to create rule for SendApproval to User", priority = 7)
		public void RuleForSerialApprovalGroup() throws Exception{
			Reporter.log("Rule for SendApproval to role creation begins");
				approvalTest.ConfigUI("serialapproval",username,password);
				Reporter.log("Rule created and deployed");
		}
		@Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 8,dependsOnMethods= {"RuleForSerialApprovalGroup"})
		public void ApprovalFlowForSerialApprovalGroup() {
		String action="APPROVE";
		List<String> users=new ArrayList<String>();
			users.add("Connor Horton");
			users.add("Chris Black");
			 Reporter.log("before calling create req method");
	    	String ReqHeaderId = prcApprovalsUtil.createrequisitionFromPayload("ApprovalFlowForSerialApprovalGroup");
	    	String parts[]=ReqHeaderId.split("-");
			System.out.println("header id is "+ parts[0]);
			ReqHeaderId=parts[1];
			String requisition=parts[0];
            Reporter.log("For requisition "+requisition+" reqisitionHeaderId is"+ ReqHeaderId);
			 Reporter.log("before submitting create req method");
			prcApprovalsUtil.SubmitReq(ReqHeaderId,username,password);
			 Reporter.log("before Initiation of Execute req method");
			String finalStatus=prcApprovalsUtil.executeRequisitionAction(action,users,requisition,ReqHeaderId);
			
			System.out.println("Processing of Requisition "+requisition+" workflow ends with status <"+finalStatus+">");
			Reporter.log("requisition "+requisition+" workflow ends with status < "+finalStatus+" >");
			Assert.assertTrue(finalStatus.contains("Approved"), "Processing of requisition "+requisition+" workflow ends with status <"+finalStatus+"> And expected status is Approved");
		
		}
		@Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 9,dependsOnMethods= {"RuleForSerialApprovalGroup"})
		public void ApprovalFlowForSerialApprovalGroup2() {
			 Reporter.log("before calling create req method");
	    	String ReqHeaderId = prcApprovalsUtil.createrequisitionFromPayload("ApprovalFlowForSerialApprovalGroup2");
	    	String parts[]=ReqHeaderId.split("-");
			System.out.println("header id is "+ parts[0]);
			ReqHeaderId=parts[1];
			String requisition=parts[0];
            Reporter.log("For requisition "+requisition+" reqisitionHeaderId is"+ ReqHeaderId);
			 Reporter.log("before submitting create req method");
			prcApprovalsUtil.SubmitReq(ReqHeaderId,username,password);
			CommonUtils.hold(10);
			 RequisitionApprovalStatus = prcApprovalsUtil.getRequisitionApprovalStatus(RequisitionStatusCheckService, ReqHeaderId, Collections.<String, String>emptyMap(),"Rejected","", "CVRQST01");
			 
				if(RequisitionApprovalStatus.equalsIgnoreCase("Rejected"));
				    
				    else
				    	assertTrue(false,"Expected final status as Rejected but found "+RequisitionApprovalStatus);
		}
		@Test(description = "method to create rule for SendApproval to User", priority = 10)
		public void RuleForParallelApprovalGroup() throws Exception{
			Reporter.log("Rule for SendApproval to role creation begins");
				approvalTest.ConfigUI("parallelApproval",username,password);
				Reporter.log("Rule created and deployed");
		}
		@Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 11,dependsOnMethods= {"RuleForParallelApprovalGroup"})
			public void ApprovalFlowForParallelApprovalGroup() {
			String action="APPROVE";
			List<String> users=new ArrayList<String>();
				users.add("Terry Green");
				users.add("Ava Davidson");
				 Reporter.log("before calling create req method");
		    	String ReqHeaderId = prcApprovalsUtil.createrequisitionFromPayload("ApprovalFlowForParallelApprovalGroup");
		    	String parts[]=ReqHeaderId.split("-");
				System.out.println("header id is "+ parts[0]);
				ReqHeaderId=parts[1];
				String requisition=parts[0];
	            Reporter.log("For requisition "+requisition+" reqisitionHeaderId is"+ ReqHeaderId);
				 Reporter.log("before submitting create req method");
				prcApprovalsUtil.SubmitReq(ReqHeaderId,username,password);
				
				 RequisitionApprovalStatus = prcApprovalsUtil.getRequisitionApprovalStatus(RequisitionStatusCheckService, ReqHeaderId, Collections.<String, String>emptyMap(),"Rejected","", "CVRQST01");
				 
					if(RequisitionApprovalStatus.equalsIgnoreCase("Rejected"));
					    
					    else
					    	assertTrue(false,"Expected Status is Rejected but actual is "+RequisitionApprovalStatus);
			}
		@Test(description = "Method to route Requisition with approvers as users for Approvals", priority = 12,dependsOnMethods= {"RuleForParallelApprovalGroup"})
		public void ApprovalFlowForParallelApprovalGroup2() {
		String action="APPROVE";
		List<String> users=new ArrayList<String>();
			users.add("Terry Green");
			users.add("Ava Davidson");
			 Reporter.log("before calling create req method");
	    	String ReqHeaderId = prcApprovalsUtil.createrequisitionFromPayload("ApprovalFlowForParallelApprovalGroup2");
	    	String parts[]=ReqHeaderId.split("-");
			System.out.println("header id is "+ parts[0]);
			ReqHeaderId=parts[1];
			String requisition=parts[0];
            Reporter.log("For requisition "+requisition+" reqisitionHeaderId is"+ ReqHeaderId);
			 Reporter.log("before submitting create req method");
			prcApprovalsUtil.SubmitReq(ReqHeaderId,username,password);
			 Reporter.log("before Initiation of Execute req method");
			CommonUtils.hold(10); 
				Map<String, String> mp= PrcApprovalsUtil.getRequisitionAssignees(requisition ,"ASSIGNEES","ASSIGNEESDISPLAYNAME");
				List<String> approvers=new ArrayList<>();
				approvers.addAll(mp.values());

				if(!approvers.containsAll(users))									
				{
					Assert.assertFalse(true,"Invoice is not assigned to all Expected users");
					Reporter.log("Invoice is not assigned to all Expected users");

				}
				
			String finalStatus=prcApprovalsUtil.executeRequisitionAction(action,users,requisition,ReqHeaderId);
			
			System.out.println("Processing of Requisition "+requisition+" workflow ends with status <"+finalStatus+">");
			Reporter.log("requisition "+requisition+" workflow ends with status < "+finalStatus+" >");
			Assert.assertTrue(finalStatus.contains("Approved"), "Processing of requisition "+requisition+" workflow ends with status <"+finalStatus+"> And expected status is Approved");
		
		}
}
