package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.GlobalPageTemplate;

public class SandboxUtils extends GlobalPageTemplate{

	public SandboxUtils(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	public static boolean labelReadEnabled = false;
	static String retreiveSandboxState = "select * from ADF_SB_SANDBOXES";
	static String retreiveSandboxRepositoryLock = "select * from ADF_SB_REPOSITORY_LOCKS";
	static String labelReadCheck = "select * from FND_PROFILE_OPTION_VALUES where PROFILE_OPTION_ID IN (SELECT PROFILE_OPTION_ID FROM FND_PROFILE_OPTIONS WHERE PROFILE_OPTION_NAME IN ('ORACLE.ADF.CUSTOMIZATION.SHARING'))";
	
	/*
	 * SandboxOperationPoll(String SandboxNamePoll,String FinalStatus,String IntermediateStatus) will check for sandbox operation status in db with polling intervals of hold() method.
	 * SandboxNamePoll  	-> Name of the sandbox for which the status has to be check
	 * FinalStatus			-> Final expecting status of the sandbox operation (ex: For publish operation final status of sandbox is "COMMITTED")
	 * IntermediateStatus	-> Intermediate status during sandbox operation		(ex: For publish operation intermediate status of sandbox during publish is "IN_PROGRESS")
	 * Sandbox Status:
	 * ---------------
	 * Publish Completed		-> 	COMMITTED
	 * Publish Ongoing			-> 	PENDING_PUBLISH
	 * Refresh Completed/Needed	-> 	ACTIVE
	 * Refresh Ongoing			->	PENDING_REFRESH_BEGIN
	 * Conflict Refresh pending -> 	PENDING_REFRESH_RESUME
	 * Refresh Errored			-> 	INACTIVE
	 * 			
	 */
	
	public String GetSandboxStatus(String SandboxName,String FinalStatus,String IntermediateStatus, WebDriver driver) {
		Map<String, Map<String,String>> qryOutput;
		String SandboxOperationStatus = null;
		String SandboxFinalStatus = null;
		try {
			qryOutput = DbResource.queryExecute(retreiveSandboxState+" where NAME = '"+SandboxName+"'", "NAME","SANDBOX_STATE");
			for(Map.Entry<String, Map<String,String>> resultMap : qryOutput.entrySet()) {
				System.out.println("Status -> "+resultMap.getKey());
				SandboxOperationStatus = resultMap.getValue().get("SANDBOX_STATE");
			}
			System.out.println("SandboxOperatonsStatus - "+SandboxOperationStatus);
			for(int holdwait = 1; holdwait < 120; holdwait++) {
				if(SandboxOperationStatus.equalsIgnoreCase(FinalStatus)) {
					SandboxFinalStatus = SandboxOperationStatus;
					break;
					}else if(SandboxOperationStatus.equalsIgnoreCase(IntermediateStatus)) {
						CommonUtils.hold(holdwait);
						qryOutput = DbResource.queryExecute(retreiveSandboxState+" where NAME = '"+SandboxName+"'", "SANDBOX_STATE");
						for(Entry<String, Map<String, String>> resultMap : qryOutput.entrySet()) {
							System.out.println("Status -> "+resultMap.getKey());
							SandboxOperationStatus = resultMap.getKey();
							}
						}else {
							SandboxFinalStatus = SandboxOperationStatus;
						break;
						}
				}//End of For
		} catch (SQLException GSS) {
			System.out.println("Exception in GetSandboxStatus()");
			GSS.printStackTrace();
		}
	return SandboxFinalStatus;
	}//End of GetSandboxStatus()
	
	/*
	 * getSandboxFinalStatus(String sandboxName) will return final status of the sandbox passed as a parameter
	 */
	public static String getSandboxFinalStatus(String sandboxName) {
		Map<String, Map<String, String>> qryOutput;
		String sandboxFinalStatus = null;
		try {
			qryOutput = DbResource.queryExecute(retreiveSandboxState+" where NAME = '"+sandboxName+"'", "SANDBOX_STATE");
			for(Entry<String, Map<String, String>> resultMap : qryOutput.entrySet()) {
				System.out.println("Status -> "+resultMap.getKey());
				sandboxFinalStatus = resultMap.getValue().get("SANDBOX_STATE");
			}
		}catch(SQLException gSFS) {
			System.out.println("Exception in getSandboxFinalStatus()");
			gSFS.printStackTrace();
		}
		return sandboxFinalStatus;
	}//End Of getSandboxFinalStatus()
	
	/*
	 * verifySandboxRepositoryLock() method will return status of sandbox repository lock acquisation
	 */
	public static boolean verifySandboxRepositoryLock() {
		boolean isLockAquired = false;
		System.out.println("sandbox lock aquisation verification begins");
		try {
			if (DbResource.sqlStatement != null) {
				System.out.println("Sql statement is not null");
				System.out.println("Executable Query -> "+retreiveSandboxRepositoryLock);
				DbResource.queryResult = DbResource.sqlStatement.executeQuery(retreiveSandboxRepositoryLock);
				System.out.println("Result set retreived");
				while (DbResource.queryResult.next()) {
					System.out.println("Lock value -> "+DbResource.queryResult.getNString("SANDBOX_ID"));
					if(DbResource.queryResult.getNString("SANDBOX_ID") != null)
						isLockAquired = true;
				}
				System.out.println("Result set processed");
			}else {
				System.out.println("SqlStatement not established - Check For DBConnection");
			}
			
			DbResource.queryResult = null;
		 System.out.println("isSandboxRepositoryLockAquired -> "+isLockAquired);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("sandbox lock aquisation verification end");
		return isLockAquired;
	}//End Of verifySandboxRepositoryLock()
	
	/*
	 * isLabelReadEnabled() will check if 'SITEDEF' enabled or not
	 * if ORACLE.ADF.CUSTOMIZATION.SHARING = OFF then 'SITEDEF' is disabled 
	 * 	else 
	 * 		'SITEDEF' enabled and based on value its PHASE1 or PHASE2
	 */
	
	public static void isLabelReadEnabled() throws SQLException {
		 
		 String lRPOValue = null;
		 try {
			 DbResource.queryResult = DbResource.sqlStatement.executeQuery(labelReadCheck);
			 while (DbResource.queryResult.next()) {
				 lRPOValue = DbResource.queryResult.getNString("PROFILE_OPTION_VALUE");
			 }		
			 if(lRPOValue != null) {
				 if(!(lRPOValue.toString().equalsIgnoreCase("OFF"))) {
					 System.out.println("Label Read PO Value : "+lRPOValue);
					 System.out.println("SiteDef enabled and its Value is : "+lRPOValue.toString());
					 labelReadEnabled = true;
				}
			 }
		 }catch(Exception lrce) {
			 System.out.println("Exception in SITEDEF check method");
			 lrce.printStackTrace();
		 }
	 }//End of isLabelReadEnabled()
	
}
