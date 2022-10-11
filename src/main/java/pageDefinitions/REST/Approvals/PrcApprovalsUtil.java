package pageDefinitions.REST.Approvals;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;

import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.Log;
import io.restassured.response.Response;

public class PrcApprovalsUtil {
	
	static String RequisitionApprovalStatus;
    static String Requisition,RequisitionHeaderId;
	Map<String, Map<String, String>> parsedResponse;
	String  RequisitionStatusCheckService,getWorkListTasksIdService,executableHost;
	static Map<String,String> header = new HashMap<String, String>();
	Map<String, Map<String, Object>> restInfo;
	static String getRequisitonAssignmentPart1 = "SELECT W.IDENTIFICATIONKEY, W.CREATEDDATE, W.TASKID, W.TASKDEFINITIONNAME,W.ROOTTASKID,W.VERSIONREASON,\r\n" + 
			"W.ASSIGNEES,W.APPROVERS,W.UPDATEDBY,W.ACQUIREDBY,W.UPDATEDDATE,W.STATE, W.OUTCOME,W.STAGE,W.PARTICIPANTNAME,W.ASSIGNMENTCONTEXT,W.*\r\n" + 
			"FROM FA_FUSION_SOAINFRA.WFTASK W\r\n" + 
			"WHERE W.IDENTIFICATIONKEY like \'%";
	
	static String getRequisitonAssignmentPart2 = "%\'";
		public PrcApprovalsUtil() throws FileNotFoundException, IOException, ParseException{
			restInfo = RestCommonUtils.parseJson("approvalFlow", "./src/main/java/ConfigurationResources/REST/request.json");
	 executableHost = RestCommonUtils.getExecutableHost();
	RequisitionStatusCheckService = executableHost+restInfo.get("getRequisitionStatus").get("RequestURL");
	getWorkListTasksIdService = executableHost+restInfo.get("listAssginedRequisitions").get("RequestURL");
	}
	public String createrequisitionFromPayload(String payload)
	{
		try {
		Response serviceResponse;
		String createRequisitionService = executableHost+restInfo.get(payload).get("RequestURL");
		header.put("Content-Type", "application/json");
		String servicePayload = restInfo.get(payload).get("Payloads").toString();
//		int firstIndex=servicePayload.indexOf("[");
//		int lastIndex= servicePayload.lastIndexOf("]");
//		servicePayload=servicePayload.substring(1);
//		servicePayload=servicePayload.substring(0,lastIndex-1);
		
		System.out.println("Create Requisition Service : "+createRequisitionService);
		System.out.println("Create Requisition Payload : "+servicePayload);

		String updatedCreateInvoicePayload = RestCommonUtils.updateSerivcePayload(servicePayload);
		System.out.println("update Invoice Payload : "+updatedCreateInvoicePayload);
		
		System.out.println("Requisition Creation Initiated");
		Reporter.log("Requisition Creation Initiated");
		serviceResponse = RestCommonUtils.postRequest(createRequisitionService, updatedCreateInvoicePayload, header, "CVRQST01", "Welcome1");

		System.out.println("Requisition Creation Service Reponse Status : "+serviceResponse.getStatusCode());
		System.out.println("Requisition Creation Service Reponse body : "+serviceResponse.getBody().asString());

		Reporter.log("Requisition Creation Service Reponse Status : "+serviceResponse.getStatusCode());
		if(serviceResponse.getStatusCode() == 201) {
			parsedResponse = RestCommonUtils.responseParser(serviceResponse, "", "CreatedBy","RequisitionHeaderId","Requisition");
			Requisition = parsedResponse.get("CVRQST01").get("Requisition");
			RequisitionHeaderId = RestCommonUtils.parseResponsetemp(serviceResponse, "RequisitionHeaderId");
			System.out.println(RequisitionHeaderId+" is Header ID of Requisition :"+Requisition);
			Reporter.log(RequisitionHeaderId+" is Header ID of Requisition :"+Requisition);
		}
		else {
			Reporter.log("Requisition creation fails with response code : "+serviceResponse.getStatusCode());
			Assert.fail();
		}


	}catch(Exception cIE) {
		System.out.println("Exception in createRequisition method "+cIE.getMessage());
		cIE.printStackTrace();
		Assert.fail();
	}
	finally{
		System.out.println("Clearing header fields of createRequisition method");
		header.clear();
	}
		return Requisition+"-"+RequisitionHeaderId;
	}
	public String createReq(String Path , String username , String password) {
		String path = Path;	
		File file = new File(path);
	    String content = null;

	 String header =   
		given()
	    .auth()
        .preemptive()
        .basic(username, password)
        .body(file)
        .with()
        .contentType("application/json")
        .then()
        .expect()
        .statusCode(201)
	    .when()
	    .post(RestCommonUtils.getExecutableHost()+"fscmRestApi/resources/11.13.18.05/purchaseRequisitions").then().extract().response().body().path("RequisitionHeaderId").toString();
	 
	 System.out.println(header);
	 
	 return header;
	 
	 
	    
}

public String GetReq(String Header, String username , String password) {
		    
	    
		String RequistionId =
		  given()
		 .auth()
         .preemptive()
         .basic(username, password)
         .then()
         .expect()         
         .statusCode(200)
		 .when()
		 .get(RestCommonUtils.getExecutableHost()+"fscmRestApi/resources/11.13.18.05/purchaseRequisitions/"+Header).then().extract().response().body().path("Requisition").toString();
		
		return RequistionId;
		 
		  
	}

public void SubmitReq(String Header, String username , String password) {
	try {
 String path = "./src/test/resources/Approvals/SubmitReq.json";	
	File file = new File(path);
	  given()
	 .auth()
     .preemptive()
     .basic(username, password)
     .body(file)
     .with()
     .contentType("application/vnd.oracle.adf.action+json")
     .then()
     .expect()         
     .statusCode(200)
	 .when()
	 .post(RestCommonUtils.getExecutableHost()+"fscmRestApi/resources/11.13.18.05/purchaseRequisitions/"+Header)
	 .getBody().print();
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		Reporter.log(e.getMessage());
	}
}

public String GetStatus(String Header, String username , String password) {
    
    
	String Status =
	  given()
	 .auth()
     .preemptive()
     .basic(username, password)
     .then()
     .expect()         
     .statusCode(200)
	 .when()
	 .get(RestCommonUtils.getExecutableHost()+"fscmRestApi/resources/11.13.18.05/purchaseRequisitions/"+Header).then().extract().response().body().path("Status").toString();
	
	return Status;
	 
	  
}
public boolean verifyIfRequisitionAssignedToAllUserInRole(String role, String requisition) {
	Response workListTaskResponse;
	
	List <String>RoleUsers=ApprovalsRESTUtils.getUsersFromRole(role);
	if(!(RoleUsers.isEmpty()))
	{
		for(int i=0;i<RoleUsers.size();i++)
		{
		

			header.put("Content-Type", "application/json");

			String getWorkListIdServicePayload = restInfo.get("listAssginedRequisitions").get("Payloads").toString();

			workListTaskResponse = RestCommonUtils.postRequest(getWorkListTasksIdService, getWorkListIdServicePayload.substring(1, getWorkListIdServicePayload.length()-1), header,  RoleUsers.get(i), "Welcome1");

			
			if(workListTaskResponse.getStatusCode()!=401)
			{
			String workFlowId = ApprovalsRESTUtils.workListInvoiceId(getWorkListTasksIdService, getWorkListIdServicePayload, header, requisition, RoleUsers.get(i));
			header.clear();

			System.out.println(workFlowId+" is the workFlowId of Requisition "+requisition+" for Approver "+RoleUsers.get(i));
			Reporter.log(workFlowId+" is the workFlowId of Requisition "+requisition+" for Approver "+RoleUsers.get(i));
			if (workFlowId==null || workFlowId.isEmpty())
			{
				System.out.println("Requisition not assigned to user "+RoleUsers.get(i));
			//	System.out.println("response is "+workListTaskResponse.getBody().asString());
				Reporter.log("Requisition not assigned to user "+RoleUsers.get(i));
				return false;
			}
			}
				
		}
	}
	return true;
}
public static String getRequisitionApprovalStatus(String RequisitionStatusService,String requisitionId,Map<String,String> header, String finalStatus1,String finalStatus2, String userName) {
	String RequisitionStatus = null;
	try {
		Response RequisitionStatusResponse;
		RequisitionStatusService = RequisitionStatusService+requisitionId;
		header = Collections.<String, String>emptyMap();
		System.out.println("Retreiving status of Requisition "+requisitionId);
		RequisitionStatusResponse = RestCommonUtils.getRequest(RequisitionStatusService, header, userName, "Welcome1");
		
		if(RequisitionStatusResponse.getStatusCode() == 200) {
			//parsedResponse = RestCommonUtils.responseParser(invoiceStatusResponse, "", "LastUpdatedBy","ApprovalStatus");
			RequisitionStatus = RestCommonUtils.parseResponsetemp(RequisitionStatusResponse, "DocumentStatus");
			System.out.println("Requisition ->"+requisitionId+" ApprovalStatus -> "+RequisitionStatus);
			long invoiceIntiateStart = System.currentTimeMillis();
			System.out.println(" RequisitionIntiateStart "+invoiceIntiateStart);
			CommonUtils.hold(2);
			while(RequisitionStatus.equalsIgnoreCase("Incomplete") && ((System.currentTimeMillis() - invoiceIntiateStart)/(1000*60))<2) {
				RequisitionStatus = RestCommonUtils.parseResponsetemp(RestCommonUtils.getRequest(RequisitionStatusService, header, userName, "Welcome1"), "DocumentStatus");
				System.out.println("Requisition ->"+requisitionId+" ApprovalStatus -> "+RequisitionStatus);
			}
		}
		
		int waitCycle = 0;
		if(!(RequisitionStatus.equalsIgnoreCase("Incomplete"))) {
		while(!(RequisitionStatus.equalsIgnoreCase(finalStatus1) || RequisitionStatus.equalsIgnoreCase(finalStatus2))) {
			CommonUtils.hold(5);
			RequisitionStatusResponse = RestCommonUtils.getRequest(RequisitionStatusService, header, userName, "Welcome1");
			
			 
			  /*
			   *  Loop will break if "ApprovalStatus" of InvoiceID is either FinalStatus1 or FinalStatus2 or FinalStatus3 or after 60second (i.e.) waitCycle*5 = 60
			   */
			  
			 
			if(RequisitionStatus.equalsIgnoreCase(finalStatus1) || RequisitionStatus.equalsIgnoreCase(finalStatus2) || waitCycle==12)
					break;
			
			int responseStatus = RequisitionStatusResponse.getStatusCode();
			/*System.out.println("Invoice Creation Service Reponse Status : "+responseStatus);
			System.out.println("Invoice Creation Service Reponse body : "+invoiceStatusResponse.getBody().asString());*/
			
			
			if(responseStatus == 200) {
				System.out.println("getInvoiceStatus service invocation was successful and response code : "+responseStatus);
				//parsedResponse = RestCommonUtils.responseParser(invoiceStatusResponse, "", "LastUpdatedBy","ApprovalStatus");
				RequisitionStatus = RestCommonUtils.parseResponsetemp(RequisitionStatusResponse, "DocumentStatus");
				System.out.println("Requisition ->"+requisitionId+" ApprovalStatus -> "+RequisitionStatus);
			}else {
				System.out.println("getRequisitionStatus service invocation not successful with response code : "+responseStatus);
				RequisitionStatus = "Requisition Flow Failed";
			}
			
			waitCycle++;
		}
		
	}else {
		System.out.println("Requisition is in <"+RequisitionStatus+"> status after 2mins of workflow intiation");
		Reporter.log("Requisition is in <"+RequisitionStatus+"> status after 2mins of workflow intiation");
	}	
		System.out.println("Requisition is in <"+RequisitionStatus+"> status after 2mins of workflow intiation");
		Reporter.log("Requisition is in <"+RequisitionStatus+"> status after 2mins of workflow intiation");
	}catch(Exception gISE) {
		System.out.println("Exception in getRequisitionStatus method : "+gISE.getMessage());
		gISE.printStackTrace();
	}
	System.out.println(" requisition status is "+RequisitionStatus);
	Reporter.log(" requisition status is "+RequisitionStatus);
return RequisitionStatus;
}
public static Map<String,String> getRequisitionAssignees(String requisitionHeaderId,String assgineeName,String assgineeDisplayName) {
	String assigneeName = null;
	boolean assigneeRetreived = false;
	long assigneeCheckStartTime = System.currentTimeMillis();
	Map<String,String> assignees = new HashMap<String,String>();
	try {
		if (DbResource.sqlStatement != null) {
			String executableQuery = getRequisitonAssignmentPart1+requisitionHeaderId+getRequisitonAssignmentPart2;
			System.out.println(" executableQuery "+executableQuery);
			while(!(assigneeRetreived)) {
				DbResource.queryResult = DbResource.sqlStatement.executeQuery(executableQuery);
				while (DbResource.queryResult.next()) {
					assigneeName = DbResource.queryResult.getNString(assgineeName);
					if(assigneeName != null) {
						System.out.println("Assignee : "+assigneeName);
						assignees.put(assigneeName.split(",")[0], DbResource.queryResult.getNString(assgineeDisplayName));
						assigneeRetreived = true;
					}
						
				}
				//System.out.println("Queryresult : "+DbResource.queryResult.next());
				if(DbResource.queryResult.next() == false && (((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))>10)) {
				    System.out.println("Requisition not assinged to expected Approver with in "+((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))+" mins");
				    Reporter.log("Requisition not assinged to expected Approver with in "+((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))+" mins");
				    break;
				}
				
			}
		}else {
			System.out.println("SqlStatement not established - Check For DBConnection");
		}
		DbResource.queryResult = null;
	}catch(Exception gIAE) {
		System.out.println("Exception in getInvoiceAssignee :"+gIAE.getMessage());
		gIAE.printStackTrace();
		
	}
return assignees;
}
public String executeRequisitionAction(String action ,List<String> users ,String requisition ,String requisitionId) {
	try {
		String approverName,approverDisplayName,nextapprovalDName;
		Map<String,String> listofApprovers = new HashMap<String,String>();
		Response RequisitionActionResponse;
		boolean workFlowProcessed = false;
		String RequisitionActionService ="",	RequisitionActionServicePayload="";
		List<String> RoleUsers=new ArrayList<String>();
		long assigneeCheckStartTime = System.currentTimeMillis();
		RequisitionApprovalStatus = getRequisitionApprovalStatus(RequisitionStatusCheckService, requisitionId, Collections.<String, String>emptyMap(),"Pending approval","", "CVRQST01");
		if(!(RequisitionApprovalStatus.equalsIgnoreCase("Incomplete"))) {
		while(!(RequisitionApprovalStatus.equalsIgnoreCase("approved") || RequisitionApprovalStatus.equalsIgnoreCase("Rejected")) && !(workFlowProcessed)) {
			System.out.println("Get list of Approvers");

			if(users.get(0).equalsIgnoreCase("Role"))
			{
				RoleUsers=ApprovalsRESTUtils.getUsersFromRole(users.get(1));
				System.out.println("Role User :"+RoleUsers.get(0));
				if(!(RoleUsers.isEmpty()))
				{
					listofApprovers.put(RoleUsers.get(0), RoleUsers.get(0));
					Reporter.log("Requisition Assigned to role based Approver : "+RoleUsers.get(0));
					System.out.println("ApproverName :"+listofApprovers.get(RoleUsers.get(0)));
				}
			}else
				listofApprovers = getRequisitionAssignees(requisitionId ,"ASSIGNEES","ASSIGNEESDISPLAYNAME");

			if(!(listofApprovers.isEmpty())) {

				/*
				 *     get RequisitionTaskWorklist id of respective Requisition from the User session  
				 */
				for (Map.Entry<String, String> approverslist : listofApprovers.entrySet()) {
					approverName = approverslist.getKey();
					approverDisplayName = approverslist.getValue();

					Reporter.log("requisition <"+requisition+"> Assigned to Approver : <"+approverDisplayName+">");

					if(!users.contains(approverDisplayName))
					{
						Log.info(approverDisplayName +" is not listed in expected user list");
						Reporter.log("Requisition not Assigned to Expected Approver. Expected Assignee < "+users+" > but Assigned to < "+approverDisplayName+" >");
						Assert.assertFalse(true,approverDisplayName +" is not listed in expected user list");
					}

					System.out.println(requisition+" assigned to approver : "+approverDisplayName);

					header.put("Content-Type", "application/json");

					String getWorkListIdServicePayload = restInfo.get("listAssginedRequisitions").get("Payloads").toString();

					String workFlowId = ApprovalsRESTUtils.workListInvoiceId(getWorkListTasksIdService, getWorkListIdServicePayload, header, requisition, approverName);
					header.clear();

					System.out.println(workFlowId+" is the workFlowId of requisition "+requisition+" for Approver "+approverDisplayName);
					Reporter.log(workFlowId+" is the workFlowId of requisition "+requisition+" for Approver "+approverDisplayName);
					/*
					 *     Perform Invoice Action (APPROVE / REJECT) by Approver for Assigned Invoice 
					 */
					Reporter.log(approverDisplayName+" workflow Action for requisition < "+requisition+" > is < "+action+" >");

					if(action.equalsIgnoreCase("APPROVE"))
					{
						RequisitionActionService= executableHost+restInfo.get("invoiceAction").get("RequestURL")+workFlowId;
						RequisitionActionServicePayload  = restInfo.get("invoiceAction").get("Payloads").toString();
					}
					else if(action.equalsIgnoreCase("REJECT"))
					{
						RequisitionActionService = executableHost+restInfo.get("invoiceActionReject").get("RequestURL")+workFlowId;
						RequisitionActionServicePayload = restInfo.get("invoiceActionReject").get("Payloads").toString();
					}

					System.out.println("Initiating WorkFlow Action");

					Reporter.log("Initiating WorkFlow Action");

					System.out.println("invoiceAction Service : "+RequisitionActionService);
					System.out.println("invoiceAction Payload : "+RequisitionActionServicePayload);

					if(workFlowId != null) {

						header.put("Content-Type", "application/json");
						RequisitionActionResponse = RestCommonUtils.putRequest(RequisitionActionService, RequisitionActionServicePayload.substring(1, RequisitionActionServicePayload.length()-1), header, approverName, "Welcome1");

						CommonUtils.hold(10);

						Reporter.log("Workflow Action < "+action+" > completed on requisition < "+requisition+" > by Approver < "+approverDisplayName+" >");

						System.out.println("WorkFlow Action Completed");

						CommonUtils.hold(10);

						RequisitionApprovalStatus = getRequisitionApprovalStatus(RequisitionStatusCheckService, requisitionId, Collections.<String, String>emptyMap(), "Approved","Rejected", "CVRQST01");

						System.out.println("Invoice status Post APPROVE action by Approver <"+approverDisplayName+"> is <"+RequisitionApprovalStatus+">");

						Reporter.log("Approval status of requisition < "+requisition+" > post < "+action+" > action by Approver < "+approverDisplayName+" > is < "+RequisitionApprovalStatus+" >");

						if(RequisitionApprovalStatus.equalsIgnoreCase("Approved") || RequisitionApprovalStatus.equalsIgnoreCase("Rejected") ) {
							//if ApprovalStatus is "Workflow approved" then Approval WorkFLow is completed.
							workFlowProcessed = true;
							break; 
						}									
						System.out.println("Final Status of requisition <"+requisition+"> is <"+RequisitionApprovalStatus+">");

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
			System.out.println("Can't process invoice as the requisition is in <"+RequisitionApprovalStatus+"> status");
			Reporter.log("Can't process invoice as the requisition is in <"+RequisitionApprovalStatus+"> status");
			}
	}catch(Exception eIAE) {
		System.out.println("Exception in executeInvoiceAction method : "+eIAE.getMessage());
		Assert.fail();
	}finally {
		header.clear();
	}
	return RequisitionApprovalStatus;
}

}
