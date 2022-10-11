package UtilClasses.UI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import TestBase.UI.GetDriverInstance;

public class DbResource {

//	protected WebDriver dbInstance;

//	public DbResource(WebDriver driver) {
//		this.dbInstance = driver;
//	}

	public static Connection sqlConnectionSession = null;
	public static Statement sqlStatement = null;
	public static ResultSet queryResult = null;

	public static Connection getDBConnection() throws SQLException, ClassNotFoundException {
		String jdbcUrl = "jdbc:oracle:thin:@" + GetDriverInstance.DBHost + ":" + GetDriverInstance.dbPort + "/"
				+ GetDriverInstance.SIName + "";
		try {
			System.out.println(jdbcUrl);
			Class.forName("oracle.jdbc.OracleDriver");
			if (sqlConnectionSession == null) {
				sqlConnectionSession = DriverManager.getConnection(jdbcUrl, GetDriverInstance.dbUserName,
						GetDriverInstance.DBPWD);
				System.out.println("DBSession Created");
				createDbStatement();
			} else
				createDbStatement();
		} catch (Exception GDBC) {
			System.out.println("DBConnection Failed to Establish");
			GDBC.printStackTrace();
		}
		return sqlConnectionSession;
	}// End of GetDBConnection()

	public static Statement createDbStatement() throws SQLException {
		try {
			if (sqlConnectionSession != null) {
				sqlStatement = sqlConnectionSession.createStatement();
				System.out.println("SqlStatement Session Created");
			} else
				getDBConnection();
		} catch (Exception CSE) {
			CSE.printStackTrace();
		}
		return sqlStatement;
	}// End of CreateStatement()

		
	/*
	 * ExecuteQuery(String ExecutableQuery,String ConditionOnField,String
	 * ConditionValue,String ColumnField) Executes query with the values passed
	 * in parameters ExecutableQuery -> Query to be executed ex:select * from
	 * ADF_SB_SANDBOXES ConditionOnField -> Column on where clause ex: Name
	 * ConditionValue -> Value on which execute filter ColumnField -> Resultant
	 * ColumnField
	 */
	public static Map<String, Map<String,String>> queryExecute(String executableQuery, String ...dataColumns) throws SQLException {
		//boolean isKeyFetched = false;
		Map<String, Map<String,String>> result = new HashMap<String, Map<String,String>>();
		Map<String,String> valueMap;
		try {
			System.out.println("Executable Query in queryExecute() -> "+executableQuery);
		   	if (sqlStatement != null) {
		   		queryResult = sqlStatement.executeQuery(executableQuery);
					while (queryResult.next()) {
						valueMap = new HashMap<String,String>();
						for(int argslist=0 ; argslist<dataColumns.length; argslist++){
							System.out.println("Value of "+dataColumns[argslist]+" is -> "+queryResult.getNString(dataColumns[argslist]));
							valueMap.put(dataColumns[argslist],queryResult.getNString(dataColumns[argslist]));
						}
							result.put(queryResult.getNString(dataColumns[0]), valueMap);
					}
			}else
				System.out.println("ExecuteQuery() - Check For DBConnection");
		} catch (Exception CFUM) {
			System.out.println("Exception in ExecuteQuery");
			CFUM.printStackTrace();
		}
		queryResult = null;
		System.out.println(" ExecuteQuery block Execution End");
		System.out.println("QueryResult - " + result);
		return result;
	}// End of ExecuteQuery()
	
	//Updates the database
	public static int updateDB(String updateableQuery) throws SQLException {
		int no_of_updated_rows=0;
		try {
			System.out.println("Update Query() -> "+updateableQuery);
		   	if (sqlStatement != null) {
		   		no_of_updated_rows = sqlStatement.executeUpdate(updateableQuery);
				if(no_of_updated_rows>0)
					System.out.println("Successfully updated the rows");
				else
					System.out.println("No rows to update");
			}
		   	else
				System.out.println("ExecuteUpdate() - Check For DBConnection");
		} catch (Exception UQ) {
			System.out.println("Exception in ExecuteUpdate");
			UQ.printStackTrace();
		}
		
		System.out.println(" ExecuteUpdate block Execution End");
		return no_of_updated_rows;
	}// End of ExecuteUpdate()
	

	public static void dbConnectionClose() throws SQLException {
		System.out.println("DBConnectionClose block starts");
		try {
			if (sqlStatement != null) {
				sqlStatement.close();
				System.out.println("SqlStatement Session Closed");
			}
			if (sqlConnectionSession != null) {
				sqlConnectionSession.close();
				System.out.println("SqlConnection Session Closed");
			}
		} catch (Exception DBCC) {
			DBCC.printStackTrace();
		}
		System.out.println("\n DBConectionClose block End");
	} // End of DBConnectionClose()

}
