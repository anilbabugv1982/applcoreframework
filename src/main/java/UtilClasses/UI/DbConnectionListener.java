package UtilClasses.UI;

import java.sql.SQLException;

import org.testng.ISuite;
import org.testng.ISuiteListener;


public class DbConnectionListener implements ISuiteListener {

	@Override
	public void onFinish(ISuite arg0) {
		try {
			System.out.println("Initiating DB Connection Close");
			DbResource.dbConnectionClose();
			System.out.println("DB Connection Closed");
		}catch(Exception aSE) {
			System.out.println("Exception closing db connection");
			aSE.printStackTrace();
		}
		
	}

	@Override
	public void onStart(ISuite arg0) {
		try {
			System.out.println("Initiating DB Connection Creation");
			DbResource.createDbStatement();
			System.out.println("DB Connection Created");
		} catch (SQLException e) {
			System.out.println("Exception in establishing db connection");
			e.printStackTrace();
		}		
	}

}
