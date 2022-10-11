package pageDefinitions.REST.Approvals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import io.restassured.response.Response;

public class ApprovalsRESTUtils extends GetDriverInstance{
	
	static String getinvoiceAssignmentPart1 = "SELECT W.IDENTIFICATIONKEY, W.CREATEDDATE, W.TASKID, W.TASKDEFINITIONNAME,W.ROOTTASKID,W.VERSIONREASON,\r\n" + 
			"W.ASSIGNEES,W.APPROVERS,W.UPDATEDBY,W.ACQUIREDBY,W.UPDATEDDATE,W.STATE, W.OUTCOME,W.STAGE,W.PARTICIPANTNAME,W.ASSIGNMENTCONTEXT,W.*\r\n" + 
			"FROM FA_FUSION_SOAINFRA.WFTASK W\r\n" + 
			"WHERE W.IDENTIFICATIONKEY IN (SELECT To_char(INVOICE_ID) FROM fusion.AP_INVOICES_ALL WHERE INVOICE_NUM = '";
	static String getinvoiceAssignmentPart2 = "' ) ORDER BY W.CREATEDDATE DESC";
	
	static String getUserFromRolePart1 = "SELECT USERNAME FROM PER_USERS WHERE USER_ID IN (SELECT USER_ID FROM PER_USER_ROLES WHERE ROLE_ID=(SELECT ROLE_ID FROM PER_ROLES_DN WHERE ROLE_COMMON_NAME='";
	
	static String getUserFromRolePart2 = "'))";

	static String getRequisitonAssignmentPart1 = "SELECT W.IDENTIFICATIONKEY, W.CREATEDDATE, W.TASKID, W.TASKDEFINITIONNAME,W.ROOTTASKID,W.VERSIONREASON,\r\n" + 
			"W.ASSIGNEES,W.APPROVERS,W.UPDATEDBY,W.ACQUIREDBY,W.UPDATEDDATE,W.STATE, W.OUTCOME,W.STAGE,W.PARTICIPANTNAME,W.ASSIGNMENTCONTEXT,W.*\r\n" + 
			"FROM FA_FUSION_SOAINFRA.WFTASK W\r\n" + 
			"WHERE W.IDENTIFICATIONKEY like \'%";
	
	static String getRequisitonAssignmentPart2 = "%\'";
	public static String getInvoiceApprovalStatus(String invoiceStatusService,String invoiceId,Map<String,String> header, String finalStatus1,String finalStatus2, String userName) {
		String invoiceStatus = null;
		try {
			Response invoiceStatusResponse;
			invoiceStatusService = invoiceStatusService+invoiceId;
			header = Collections.<String, String>emptyMap();
			System.out.println("Retreiving status of invoice "+invoiceId);
			invoiceStatusResponse = RestCommonUtils.getRequest(invoiceStatusService, header, userName, "Welcome1");
			
			if(invoiceStatusResponse.getStatusCode() == 200) {
				//parsedResponse = RestCommonUtils.responseParser(invoiceStatusResponse, "", "LastUpdatedBy","ApprovalStatus");
				invoiceStatus = RestCommonUtils.parseResponsetemp(invoiceStatusResponse, "ApprovalStatus");
				System.out.println("Invoice ->"+invoiceId+" ApprovalStatus -> "+invoiceStatus);
				long invoiceIntiateStart = System.currentTimeMillis();
				System.out.println(" invoiceIntiateStart "+invoiceIntiateStart);
				CommonUtils.hold(2);
				while(invoiceStatus.equalsIgnoreCase("Required") && ((System.currentTimeMillis() - invoiceIntiateStart)/(1000*60))<2) {
					invoiceStatus = RestCommonUtils.parseResponsetemp(RestCommonUtils.getRequest(invoiceStatusService, header, userName, "Welcome1"), "ApprovalStatus");
					System.out.println("Invoice ->"+invoiceId+" ApprovalStatus -> "+invoiceStatus);
				}
			}
			
			int waitCycle = 0;
			if(!(invoiceStatus.equalsIgnoreCase("Required"))) {
			while(!(invoiceStatus.equalsIgnoreCase(finalStatus1) || invoiceStatus.equalsIgnoreCase(finalStatus2))) {
				CommonUtils.hold(5);
				invoiceStatusResponse = RestCommonUtils.getRequest(invoiceStatusService, header, userName, "Welcome1");
				
				 
				  /*
				   *  Loop will break if "ApprovalStatus" of InvoiceID is either FinalStatus1 or FinalStatus2 or FinalStatus3 or after 60second (i.e.) waitCycle*5 = 60
				   */
				  
				 
				if(invoiceStatus.equalsIgnoreCase(finalStatus1) || invoiceStatus.equalsIgnoreCase(finalStatus2) || waitCycle==12)
						break;
				
				int responseStatus = invoiceStatusResponse.getStatusCode();
				/*System.out.println("Invoice Creation Service Reponse Status : "+responseStatus);
				System.out.println("Invoice Creation Service Reponse body : "+invoiceStatusResponse.getBody().asString());*/
				
				
				if(responseStatus == 200) {
					System.out.println("getInvoiceStatus service invocation was successful and response code : "+responseStatus);
					//parsedResponse = RestCommonUtils.responseParser(invoiceStatusResponse, "", "LastUpdatedBy","ApprovalStatus");
					invoiceStatus = RestCommonUtils.parseResponsetemp(invoiceStatusResponse, "ApprovalStatus");
					System.out.println("Invoice ->"+invoiceId+" ApprovalStatus -> "+invoiceStatus);
				}else {
					System.out.println("getInvoiceStatus service invocation not successful with response code : "+responseStatus);
					invoiceStatus = "Invoice Flow Failed";
				}
				
				waitCycle++;
			}
			
		}else {
			System.out.println("Invoice is in <"+invoiceStatus+"> status after 2mins of workflow intiation");
			Reporter.log("Invoice is in <"+invoiceStatus+"> status after 2mins of workflow intiation");
		}	
			System.out.println("Invoice is in <"+invoiceStatus+"> status after 2mins of workflow intiation");
			Reporter.log("Invoice is in <"+invoiceStatus+"> status after 2mins of workflow intiation");
		}catch(Exception gISE) {
			System.out.println("Exception in getInvoiceStatus method : "+gISE.getMessage());
			gISE.printStackTrace();
		}
	return invoiceStatus;
	}
	
	public static String workListInvoiceId(String workListTasksService,String workListTasksServicePayload,Map<String,String> header,String invoiceName,String userName) {
		String invoiceWorkListId = null;
		try {
			Response workListTaskResponse;
			long startime = System.currentTimeMillis();
						
			/*System.out.println("WorkListTasks Service : "+workListTasksService);
			System.out.println("WorkListTasks Payload : "+workListTasksServicePayload);*/
			
			workListTaskResponse = RestCommonUtils.postRequest(workListTasksService, workListTasksServicePayload.substring(1, workListTasksServicePayload.length()-1), header, userName, "Welcome1");
			
			
		//	System.out.println("Invoice Creation Service Reponse body : "+workListTaskResponse.getBody().asString());
			/*parsedResponse = RestCommonUtils.responseParser(workListTaskResponse, "results", "id");
			System.out.println("Task Id :"+parsedResponse.get("results").get("id"));*/
			
		//	invoiceWorkListId = RestCommonUtils.parseResponsetemp(workListTaskResponse, "id");
			invoiceWorkListId = getInvoiceworkListId(workListTaskResponse.getBody().asString(),invoiceName);
			
			System.out.println("Invoice Name in workListInvoiceId : "+invoiceName);
			
			//int waitCycle = 0;
			while(invoiceWorkListId == null ) {
				CommonUtils.hold(5);
				workListTaskResponse = RestCommonUtils.postRequest(workListTasksService, workListTasksServicePayload.substring(1, workListTasksServicePayload.length()-1), header, userName, "Welcome1");
				Reporter.log("worklist Response code while fetching worklist id is "+workListTaskResponse.getStatusCode());
				invoiceWorkListId = getInvoiceworkListId(workListTaskResponse.getBody().asString(),invoiceName);
				/*
				 * Loop will break if "invoiceWorkListId" is found or after 180seconds
				 */
				if(invoiceWorkListId != null && ((System.currentTimeMillis()-startime)/(1000*60))<3) {
					System.out.println("WorklistInvoiceId for invoice genereted in "+((System.currentTimeMillis()-startime)/(1000*60)) +" mins");
					break;
				}else if(((System.currentTimeMillis()-startime)/(1000*60))>=3) {
					System.out.println("WorklistInvoiceId for invoice not genereted within "+((System.currentTimeMillis()-startime)/(1000*60)) +" mins");
					break;
				}
						
				//waitCycle++;
			}
			System.out.println(" Final Response of worklist is "+workListTaskResponse.getStatusCode());
			Reporter.log(" Final Response of worklist is "+workListTaskResponse.getStatusCode());
			
			System.out.println("Task Id :"+invoiceWorkListId);
			
			
		}catch(Exception gWLTE) {
			System.out.println("Exception in getWorkListTask method : "+gWLTE.getMessage());
			Assert.fail();
		}
		return invoiceWorkListId;
	}
	
	/*
	 * getInvoiceworkListId() will return "invoiceWorkListId" of current Invoice from Worklist UI
	 */
	static String getInvoiceworkListId(String serviceResponse, String invoiceName) {
		String invoiceworkListIdFinal = null;
		String invoiceworkListIdtemp = null;
		try {
			 JsonFactory jsonFactory = new JsonFactory();

		        JsonParser parser = jsonFactory.createParser(serviceResponse);

		        while (!parser.isClosed()) {
		            JsonToken token = parser.nextToken();

		            if (JsonToken.FIELD_NAME.equals(token)) {
		                String fieldName = parser.getCurrentName();
		             //   System.out.println("FieldName  -> " + fieldName);
		                token = parser.nextToken();
		                
		                if (((JsonToken.VALUE_STRING.equals(token)) || JsonToken.VALUE_NUMBER_INT.equals(token) || JsonToken.VALUE_NUMBER_FLOAT.equals(token))) {
		                  //  System.out.println("Attribute field Value -> " + parser.getValueAsString());
		                      if(fieldName.equalsIgnoreCase("id"))
		                    	  invoiceworkListIdtemp = parser.getValueAsString();
		                      if(parser.getValueAsString().contains(invoiceName)) {
		                    	  invoiceworkListIdFinal = invoiceworkListIdtemp;
		                    	  break;
		                      }
		                 }
		           }
		       }
			
		}catch(Exception gIWLIE) {
			System.out.println("Exception in getInvoiceworkListId method "+gIWLIE.getMessage());
			gIWLIE.printStackTrace();
			
		}
		return invoiceworkListIdFinal;
	}
	
	/*
	 * getAssigneeUser(String wfActionserviceResponse) will return "AssigneesUser" of the Approver post Approve or Reject action on invoice
	 * @return assigneeUser Value 
	 */
	public static String getAssigneeUser(String wfActionserviceResponse) {
		String assigneeUser = null;
		boolean lasttokenToProcess = false;
		boolean assigneeUsersFound = false;
		try {
			 JsonFactory jsonFactoryUser = new JsonFactory();

		        JsonParser userParser = jsonFactoryUser.createParser(wfActionserviceResponse);

		        while (!userParser.isClosed()) {
		            JsonToken userToken = userParser.nextToken();

		            if (JsonToken.FIELD_NAME.equals(userToken)) {
		                String userfieldName = userParser.getCurrentName();
		             //   System.out.println("FieldName  -> " + userfieldName);
		                   userToken = userParser.nextToken();
		                   
		                   /*
		                    * Check to bypass token '[' after "Value" token post attribute with "assigneeUsers" value found
		                    */
		                   if(assigneeUsersFound)
		                	   userToken = userParser.nextToken();
		                   
		                if (((JsonToken.VALUE_STRING.equals(userToken)) || JsonToken.VALUE_NUMBER_INT.equals(userToken) || JsonToken.VALUE_NUMBER_FLOAT.equals(userToken))) {
		                //	System.out.println("Atttribute value  -> " + userParser.getValueAsString());
		                	if(lasttokenToProcess) {
		                		assigneeUser = userParser.getValueAsString();
		                		break;
		                	}
		                		
		                   if(userParser.getValueAsString().equalsIgnoreCase("assigneeUsers")) {
		                	   lasttokenToProcess = true;
		                	   assigneeUsersFound = true;
		                	   System.out.println("LastTokenToProcess Set");
		                    }
		                }
		           }
		       }
			
		}catch(Exception gAUE) {
			System.out.println("Exception in getAssigneeUser method "+gAUE.getMessage());
			gAUE.printStackTrace();
			
		}
		return assigneeUser;
	}
	
	/*
	 * getInvoiceAssignee(String inoviceName) will return Assignee name of the invoice
	 * @return
	 */
	public static String getInvoiceAssignee(String inoviceName,String columnName) {
		String assigneeName = null;
		boolean assigneeRetreived = false;
		try {
			if (DbResource.sqlStatement != null) {
				//System.out.println("Sql statement is not null");
				String executableQuery = getinvoiceAssignmentPart1+inoviceName+getinvoiceAssignmentPart2;
				//System.out.println("Executable Query -> "+executableQuery);
				while(!(assigneeRetreived)) {
					DbResource.queryResult = DbResource.sqlStatement.executeQuery(executableQuery);
					while (DbResource.queryResult.next() && DbResource.queryResult.getNString(columnName)!=null) {
						assigneeName = DbResource.queryResult.getNString(columnName);
					//	System.out.println("AssigneeName -> "+assigneeName);
						if(assigneeName != null) {
							assigneeName = assigneeName.split(",")[0];
					//		System.out.println("Assginee Name Post Split :"+assigneeName);
							assigneeRetreived = true;
						}
							
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
	return assigneeName;
	}
	
	/*
	 * getInvoiceAssignees(String inoviceName,String assgineeName,String assgineeDisplayName) will return List of Assignee names and their displaynames of the invoice
	 * @return
	 */
	public static Map<String,String> getInvoiceAssignees(String inoviceName,String assgineeName,String assgineeDisplayName) {
		String assigneeName = null;
		boolean assigneeRetreived = false;
		long assigneeCheckStartTime = System.currentTimeMillis();
		Map<String,String> assignees = new HashMap<String,String>();
		try {
			if (DbResource.sqlStatement != null) {
				String executableQuery = getinvoiceAssignmentPart1+inoviceName+getinvoiceAssignmentPart2;
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
					    System.out.println("Invoice not assinged to expected Approver with in "+((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))+" mins");
					    Reporter.log("Invoice not assinged to expected Approver with in "+((System.currentTimeMillis() - assigneeCheckStartTime)/(1000*60))+" mins");
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
	
	
	
	/*
	 * getInvoiceAssignees(String inoviceName,String assgineeName,String assgineeDisplayName) will return List of Assignee names and their displaynames of the invoice
	 * @return
	 */
	public static List<String> getUsersFromRole(String Role) {
			List<String> users = new ArrayList<String>();
			String user;
			boolean assigneeRetreived = false;
		try {
			if (DbResource.sqlStatement != null) {
				String executableQuery = getUserFromRolePart1+Role+getUserFromRolePart2;
				System.out.println("query is "+executableQuery);
				while(!(assigneeRetreived)) {
					DbResource.queryResult = DbResource.sqlStatement.executeQuery(executableQuery);
					while (DbResource.queryResult.next()) {
						user = DbResource.queryResult.getNString("USERNAME");
						if(user != null) {
							users.add(user);
							assigneeRetreived = true;
						}
							
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
	return users;
	}
	

}
