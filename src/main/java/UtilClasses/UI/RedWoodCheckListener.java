package UtilClasses.UI;

import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import TestBase.UI.GetDriverInstance;

public class RedWoodCheckListener extends GetDriverInstance implements ISuiteListener {

	@Override
	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ISuite arg0) {
	 /*
	  * To check RedWood enabled or not
	  */
		WebDriver redWoodCheckInstance = null;
		ApplicationLogin suiteApplicationLoginInstance = null;
		redWoodCheckInstance = getDriverInstanceObject();
		try {
			System.out.println("beforeSuite execution to check for NewsFeed enabled");
			if(redWoodCheckInstance !=null)
				suiteApplicationLoginInstance = new ApplicationLogin(redWoodCheckInstance);
				suiteApplicationLoginInstance.login("app_impl_consultant", "Welcome1", redWoodCheckInstance);
				
				System.out.println("Successfully logged into Application");
				
				/*
				 * 30789269 till fix is available don't use gPTInstance.verifyGlobalPageTemplate(driver);
				 *  use CommonUtils.hold()
				 */
			//	gPTInstance.verifyGlobalPageTemplate(driver);
				CommonUtils.hold(10);
				
				try {
					System.out.println("Verifying for NewsFeed PO");
					if(suiteApplicationLoginInstance.newsFeedEnabled == false && suiteApplicationLoginInstance.newsFeed.isEnabled()) {
						System.out.println("NewsFeed not Enabled "+suiteApplicationLoginInstance.newsFeedEnabled);
						suiteApplicationLoginInstance.newsFeedEnabled = true;
							System.out.println("NewsFeed Enabled");
						}
					System.out.println("Verifying for NewsFeed PO ends");
				}catch(Exception iNFEE) {
					System.out.println("Exception in isNewsFeedEnabled() ");
					iNFEE.printStackTrace();
				}
				
			System.out.println("Logging out from Application");
			
			try {
				suiteApplicationLoginInstance.logout(redWoodCheckInstance);
				

			} catch (Exception ex) {
				suiteApplicationLoginInstance.launchURL(redWoodCheckInstance);
				CommonUtils.hold(2);
				suiteApplicationLoginInstance.logout(redWoodCheckInstance);
			}finally {
				try {
					redWoodCheckInstance.quit();
					System.out.println("driver instance has been closed and quit");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Exception while closing Object Instance ");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Exception in beforeSuite() execution");
			e.printStackTrace();
		}
		
	}//End of beforeSuite()

}
