/**
 * 
 */
package UtilClasses.UI;

import java.sql.SQLException;

import org.testng.IClassListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;

/**
 * @author klalam
 *
 */
public class USBModeCheckListener implements IClassListener{

	/* (non-Javadoc)
	 * @see org.testng.IClassListener#onAfterClass(org.testng.ITestClass)
	 */
	@Override
	public void onAfterClass(ITestClass arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.testng.IClassListener#onBeforeClass(org.testng.ITestClass)
	 * Will check for USB mode before triggering testmethods of every class
	 */
	@Override
	public void onBeforeClass(ITestClass arg0) {
		 /*try {
			 System.out.println("Chekcing for USB mode in ->"+arg0.getXmlClass().getSupportClass().getSimpleName()+"<- class");
			if (UsbEnvironmentMode.CheckForUsbMode(UsbEnvironmentMode.RetreiveUsbProfileOption).equalsIgnoreCase("N") && UsbEnvironmentMode.CheckForUsbMode(UsbEnvironmentMode.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
				 throw new SkipException("Skipping test because environment not in USB mode");
			  }
			 System.out.println("USB mode check completed");
		} catch (SQLException e) {
			System.out.println("Exception while checking for USB mode in ->"+arg0.getClass().getName());
			e.printStackTrace();
		}*/
		
	}

	

}
