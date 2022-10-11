package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.sql.Connection;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class UsbEnvironmentMode {

	private DbResource dbResourceInstance;
	public UsbEnvironmentMode(WebDriver driver) {
	//	dbResourceInstance = new DbResource(driver);
	}
	
	ResultSet  QueryResult	 = null;
	int currentSandboxCount;
	public static boolean labelReadEnabled = false;
	static int maxSandboxCount;
	static int availableSandboxCount;
	
	public String RetreiveUsbProfileOption = "select * from FND_PROFILE_OPTION_VALUES where PROFILE_OPTION_ID IN (SELECT PROFILE_OPTION_ID FROM FND_PROFILE_OPTIONS WHERE PROFILE_OPTION_NAME IN ('FND_ENABLE_UNIFIED_SANDBOX'))";
	public String RetrevieADFShareProfileOption = "select * from FND_PROFILE_OPTION_VALUES where PROFILE_OPTION_ID IN (SELECT PROFILE_OPTION_ID FROM FND_PROFILE_OPTIONS WHERE PROFILE_OPTION_NAME IN ('ORACLE.ADF.SHARE.CONFIG.ENABLEADFSESSIONOPTIONS'))";
	public String RetreiveObjMergeProfileOption = "select * from FND_PROFILE_OPTION_VALUES where PROFILE_OPTION_ID IN (SELECT PROFILE_OPTION_ID FROM FND_PROFILE_OPTIONS WHERE PROFILE_OPTION_NAME IN ('ORACLE.ADF.SHARE.SANDBOX.MDS.OBJECTMERGEENABLED'))";
	public String updateSandboxMaxCount = "UPDATE fusion.fnd_profile_option_values SET PROFILE_OPTION_VALUE="+System.getProperty("unifiedsandboxcount")+" WHERE PROFILE_OPTION_ID = \r\n" + 
			"(SELECT PROFILE_OPTION_ID from fusion.fnd_profile_options WHERE PROFILE_OPTION_NAME = 'ORACLE.ADF.SHARE.SANDBOX.MAXIMUMSANDBOXCOUNT')";
	private String getMaxSandboxCount = "select * from FND_PROFILE_OPTION_VALUES where PROFILE_OPTION_ID IN (SELECT PROFILE_OPTION_ID FROM FND_PROFILE_OPTIONS WHERE PROFILE_OPTION_NAME IN ('ORACLE.ADF.SHARE.SANDBOX.MAXIMUMSANDBOXCOUNT'))";
	private static String getAvailableSandboxCount = "select count(*) as sandboxcount from ADF_SB_SANDBOXES where sandbox_state = 'ACTIVE'";
	static String labelReadCheck = "select * from FND_PROFILE_OPTION_VALUES where PROFILE_OPTION_ID IN (SELECT PROFILE_OPTION_ID FROM FND_PROFILE_OPTIONS WHERE PROFILE_OPTION_NAME IN ('ORACLE.ADF.CUSTOMIZATION.SHARING'))";
	
	String EnableUSBMode = "begin\r\n" + 
			" \r\n" + 
			"   fnd_profile_option_values_pkg.delete_value(\r\n" + 
			"    x_application_short_name => 'FND',\r\n" + 
			"    x_profile_option_name => 'FND_ENABLE_UNIFIED_SANDBOX',\r\n" + 
			"    x_level_name => 'SITE',\r\n" + 
			"    x_level_value => 'SITE'); \r\n" + 
			"      \r\n" + 
			"  fnd_profile_option_values_pkg.create_value(\r\n" + 
			"    x_application_short_name => 'FND',\r\n" + 
			"    x_profile_option_name => 'FND_ENABLE_UNIFIED_SANDBOX',\r\n" + 
			"    x_level_name => 'SITE',\r\n" + 
			"    x_level_value => 'SITE',\r\n" + 
			"    x_profile_option_value => 'Y');\r\n" + 
			"    \r\n" + 
			"  fnd_profile_option_values_pkg.delete_value(\r\n" + 
			"    x_application_short_name => 'FND',\r\n" + 
			"    x_profile_option_name => 'ORACLE.ADF.SHARE.CONFIG.ENABLEADFSESSIONOPTIONS',\r\n" + 
			"    x_level_name => 'SITE',\r\n" + 
			"    x_level_value => 'SITE'); \r\n" + 
			"      \r\n" + 
			"  fnd_profile_option_values_pkg.create_value(\r\n" + 
			"    x_application_short_name => 'FND',\r\n" + 
			"    x_profile_option_name => 'ORACLE.ADF.SHARE.CONFIG.ENABLEADFSESSIONOPTIONS',\r\n" + 
			"    x_level_name => 'SITE',\r\n" + 
			"    x_level_value => 'SITE',\r\n" + 
			"    x_profile_option_value => 'TRUE');\r\n" + 
			"    \r\n" + 
			"  fnd_profile_option_values_pkg.delete_value(\r\n" + 
			"    x_application_short_name => 'FND',\r\n" + 
			"    x_profile_option_name => 'ORACLE.ADF.SHARE.SANDBOX.MDS.OBJECTMERGEENABLED',\r\n" + 
			"    x_level_name => 'SITE',\r\n" + 
			"    x_level_value => 'SITE'); \r\n" + 
			"      \r\n" + 
			"  fnd_profile_option_values_pkg.create_value(\r\n" + 
			"    x_application_short_name => 'FND',\r\n" + 
			"    x_profile_option_name => 'ORACLE.ADF.SHARE.SANDBOX.MDS.OBJECTMERGEENABLED',\r\n" + 
			"    x_level_name => 'SITE',\r\n" + 
			"    x_level_value => 'SITE',\r\n" + 
			"    x_profile_option_value => 'Y');\r\n" + 
			"    \r\n" + 
			"end;";
	
	public void EnableUsbMode() throws SQLException, ClassNotFoundException {
	       System.out.println("\n EnableUsbMode block Execution Start");
	  try {
		    if(DbResource.sqlStatement != null)
		    	DbResource.sqlStatement.executeUpdate(EnableUSBMode);
		  else
			  System.out.println("EnableUsbMode() - Check For DBConnection");
		   } catch (Exception EUM) {
	        	  System.out.println("Exception in EnableUSBMode"); 
	              EUM.printStackTrace();
	          }
	 }//End of EnableUsbMode() 
	 
	 public String CheckForUsbMode(String ExecutableQuery) throws SQLException{
		 String ProfileOptionvalue = null;
		 System.out.println("\n CheckForUsbMode block Execution Start");
		  try {
			  DbResource.createDbStatement();
			   if(DbResource.sqlStatement != null){
			  			QueryResult = DbResource.sqlStatement.executeQuery(ExecutableQuery);
			  			while(QueryResult.next())
			  				ProfileOptionvalue = QueryResult.getNString("PROFILE_OPTION_VALUE");
			  			if(getMaxSandboxCount() < Integer.parseInt(System.getProperty("unifiedsandboxcount")))
			  				updateSandboxCount();
			  			System.out.println("Existing Maximum Sandbox count -> "+currentSandboxCount);
			  }else
				  System.out.println("CheckForUsbMode() - Check For DBConnection");
			 
		           }catch (Exception CFUM) {
		        	  System.out.println("Exception in CheckForUSBMode"); 
		        	  CFUM.printStackTrace();
		          }
		return ProfileOptionvalue;
		 
	 } //End of CheckForUsbMode()
	 
	 public void updateSandboxCount() throws SQLException{
		 QueryResult = null;
		 System.out.println("Existing Maximum Sandbox count -> "+currentSandboxCount+" updating Maximum Sandbox Count to -> "+System.getProperty("unifiedsandboxcount"));
		 DbResource.sqlStatement.executeUpdate(updateSandboxMaxCount);
		 System.out.println("Updated Sandbox Maximum count");
	 }
	 
	/* public int getSandboxCount() throws SQLException{
		 QueryResult = DbResource.sqlStatement.executeQuery(getSandboxCountqry);
		 while(QueryResult.next())
		     currentSandboxCount = QueryResult.getInt("PROFILE_OPTION_VALUE");
		 return currentSandboxCount;
	 }*/
	 
	 public int getMaxSandboxCount() throws SQLException{
		 QueryResult = DbResource.sqlStatement.executeQuery(getMaxSandboxCount);
		 while(QueryResult.next())
			 maxSandboxCount = QueryResult.getInt("PROFILE_OPTION_VALUE");
		 System.out.println("Max Sandbox Count -> "+maxSandboxCount);
		 return maxSandboxCount;
	 }
	 
	 public int getAvailableSandboxCount() throws SQLException{
		 QueryResult = DbResource.sqlStatement.executeQuery(getAvailableSandboxCount);
		 while(QueryResult.next())
			 availableSandboxCount = QueryResult.getInt("sandboxcount");
		 System.out.println("Available Sandbox Count -> "+availableSandboxCount);
		 return availableSandboxCount;
	 }
	 
	 public void isLabelReadEnabled() throws SQLException {
		 
		 String lRPOValue = null;
		 QueryResult = null;
		 QueryResult = DbResource.sqlStatement.executeQuery(labelReadCheck);
		 while(QueryResult.next())
			 lRPOValue = QueryResult.getNString("PROFILE_OPTION_VALUE");
		 if(lRPOValue != null) {
			 if(lRPOValue.toString().equalsIgnoreCase("PHASE1")) {
				 System.out.println("Label Read PO Value : "+lRPOValue);
				 System.out.println("Label Read PO Value String: "+lRPOValue.toString());
				 labelReadEnabled = true;
			}
		 }
	 }
	 
	 
	 public DbResource getDbResource() {
		 return dbResourceInstance;
	 }
}//End of EnvironmentSetup Class.
