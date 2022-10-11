/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.openqa.selenium.WebDriver;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;

/**
 * @author klalam
 *
 */
public class MergeLogUI {
	
	static String ImmediateparentObjectName;
	private DbResource dbInstance;
	
/*	public MergeLogUI(WebDriver driver) {
		dbInstance = new DbResource(driver);
	}
	
	
	
	 * getSandboxMergeLogId(String RefreshedSandboxName) will return merge log id of the corresponding sandbox
	 
	public static int getSandboxMergeLogId(String RefreshedSandboxName) {
		int ParentLogId = 0;
		
		System.out.println("\n getSandboxMergeLogId Start");
		  try {
			   if(CommonUtils.SqlStatement != null){
			  			CommonUtils.QueryResult = CommonUtils.SqlStatement.executeQuery("select * from ADF_SB_SANDBOXES where NAME ='"+RefreshedSandboxName+"'");
			  			while(CommonUtils.QueryResult.next())
			  				ParentLogId = Integer.parseInt(CommonUtils.QueryResult.getNString("OP_LOG_ID"));
			  }else
				  System.out.println("getSandboxMergeLogId() - Check For DBConnection");
			 
		           }catch (Exception gsmlie) {
		        	  System.out.println("Exception in getSandboxMergeLogId()"); 
		        	  gsmlie.printStackTrace();
		          }
		  CommonUtils.QueryResult = null;
		  System.out.println("Sandbox Merge Log ID -> "+ParentLogId);
		  System.out.println("\n getSandboxMergeLogId End");
	return ParentLogId;
	}//End of getSandboxMergeLogId()
	
	
	 * processMergeLogs(String RefreshedSandboxName) will process the mergelogs of a sandbox
	 
	public static void processMergeLogs(String RefreshedSandboxName){
		LinkedHashMap<Integer, String> ParentObjects = new LinkedHashMap<Integer, String>();
		LinkedHashMap<String, String> ConflictedObjects = new LinkedHashMap<String, String>();
		Stack<Integer> st = new Stack<Integer>();
		//HashSet<String> ConflictedObjects = new HashSet<String>();
		int parentlogid = 0; 
		
		
		int SandboxLogId = getSandboxMergeLogId(RefreshedSandboxName);
		
		System.out.println("processMergeLogs starts");
		try {
			   if(CommonUtils.SqlStatement != null){
			  			CommonUtils.QueryResult = CommonUtils.SqlStatement.executeQuery("select * from ADF_SB_LOG_RECORDS where log_id = '"+SandboxLogId+"' and parent_log_record_id is NULL");
			  			while(CommonUtils.QueryResult.next()) {
			  				ParentObjects.put(Integer.parseInt(CommonUtils.QueryResult.getNString("LOG_RECORD_ID")), CommonUtils.QueryResult.getNString("OBJECT_NAME"));
			  				st.push(Integer.parseInt(CommonUtils.QueryResult.getNString("LOG_RECORD_ID")));
			  				System.out.println("object id -> "+Integer.parseInt(CommonUtils.QueryResult.getNString("LOG_RECORD_ID"))+" && value -> "+CommonUtils.QueryResult.getNString("OBJECT_NAME"));
			  			}
			  	CommonUtils.QueryResult = null;
			  	while(!(st.empty())) {
			  		parentlogid = st.pop();
			  		System.out.println("parent Object Record Id -> "+parentlogid+" && "+"object Record Value ->"+ParentObjects.get(parentlogid));
			  		CommonUtils.QueryResult = CommonUtils.SqlStatement.executeQuery("select * from ADF_SB_LOG_RECORDS where parent_log_record_id = '"+parentlogid+"'");
			  		while(CommonUtils.QueryResult.next()) {
			  			if(CommonUtils.QueryResult.getNString("MAINLINE_VALUE") == null && CommonUtils.QueryResult.getNString("SANDBOX_VALUE") == null) {
			  				st.push(Integer.parseInt(CommonUtils.QueryResult.getNString("LOG_RECORD_ID")));
			  				ParentObjects.put(Integer.parseInt(CommonUtils.QueryResult.getNString("LOG_RECORD_ID")), CommonUtils.QueryResult.getNString("OBJECT_NAME"));
			  				ImmediateparentObjectName = CommonUtils.QueryResult.getNString("OBJECT_NAME");
			  			}else {
			  				System.out.println("-------------------------");
			  				if(ConflictedObjects.containsKey(CommonUtils.QueryResult.getNString("MAINLINE_VALUE")))
			  					System.out.println("Duplicate entry for the object -> "+ImmediateparentObjectName);
			  				else {
			  					ConflictedObjects.put(CommonUtils.QueryResult.getNString("MAINLINE_VALUE"), ImmediateparentObjectName);
			  					System.out.println("Object Name : "+CommonUtils.QueryResult.getNString("OBJECT_NAME")+" -> "+"SANDBOX_VALUE : "+CommonUtils.QueryResult.getNString("SANDBOX_VALUE")+" -> "+"MAINLINE_VALUE : "+CommonUtils.QueryResult.getNString("MAINLINE_VALUE"));
			  				//	<<<<<<--check for xpath in merge log ui --->>>>
			  				}
			  					
			  				System.out.println("-------------------------");
			  			}
			  		}
			  	}//End of while 
			  		
			   }else
				  System.out.println("processMergeLogs().processing parentlogrecordId - Check For DBConnection");
			 
		      }catch (Exception gsmlie) {
		        	  System.out.println("Exception in processMergeLogs().processing parentlogrecordId"); 
		        	  gsmlie.printStackTrace();
		      }
		System.out.println("processMergeLogs ends");
	}//End of processMergeLogs()
*/	
}//End of MergeLogUI class
