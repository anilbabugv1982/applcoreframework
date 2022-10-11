package UtilClasses.UI;

import java.lang.reflect.Field;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import TestBase.UI.*;

public class ListenersDefinitionClass extends GetDriverInstance implements ITestListener,IInvokedMethodListener, ISuiteListener{
	

	public void onTestStart(ITestResult result) {
//		Class<?> testClassName = result.getTestClass().getRealClass();
//		WebDriver sandBoxDriver = null;
//		System.out.println("In USB check mode ======================================= >");
//		try {
//			Field[] testClassFields = testClassName.getDeclaredFields();
//			for (Field field : testClassFields) {
//				Field driverField = field;
//				if (driverField.toGenericString().toLowerCase().contains("driver")) {
//					driverField.setAccessible(true);
//					sandBoxDriver = (WebDriver) driverField.get(result.getInstance());
//					break;
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		if (sandBoxDriver.findElements(By.xpath("//div[@id='pt1:_UISsbb:0:r1:0:_sbpgl1']")).size() != 0) {
//			if (sandBoxDriver.findElement(By.xpath("//div[@id='pt1:_UISsbb:0:r1:0:_sbpgl1']")).isDisplayed() == true) {
//				sandBoxDriver	.findElement(By.xpath("//div[@id='pt1:_UISsbb:0:r1:0:_sbm2']//div[@class='af_menu_bar-item-open-icon-style']")).click();
//				CommonUtils.hold(3);
//				sandBoxDriver.findElement(By.xpath("//td[text()='Leave Sandbox']")).click();
//				CommonUtils.hold(5);
//				sandBoxDriver.findElement(By.id("pt1:_UISsbb:0:r1:0:_sbd2::ok")).click();
//				CommonUtils.hold(10);
//			}
//		}
		}
	
	
		public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		}
		public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		}
		public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		}
		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		}
		public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		}
		public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		}

		
	 public boolean isConfigurationMethod(ITestNGMethod method, boolean includeGroupConfigs) {
	        boolean flag =  method.isBeforeMethodConfiguration() || method.isBeforeTestConfiguration()  ||
	        						  method.isBeforeClassConfiguration() || method.isBeforeSuiteConfiguration();
	        if (includeGroupConfigs) {
	            flag = flag || method.isBeforeGroupsConfiguration();
	        }
	        return flag;
	    }


//		@Override
		public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void afterInvocation(IInvokedMethod method, ITestResult result) {
			
			if(isConfigurationMethod(method.getTestMethod(), false)){
				Class<?> testClassName = result.getTestClass().getRealClass();
				WebDriver sandBoxDriver = null;
				System.out.println("<================================== In USB check mode ===================================== >");
				System.out.println("    Invoked class name : "+method.getTestMethod().getRealClass().getName()+" : Invoked method name :  "+method.getTestMethod().getMethodName() );
				try {
					Field[] testClassFields = testClassName.getDeclaredFields();
					for (Field field : testClassFields) {
						Field driverField = field;
						if (driverField.toGenericString().toLowerCase().contains("driver")) {
							driverField.setAccessible(true);
							sandBoxDriver = (WebDriver) driverField.get(result.getInstance());
							break;
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (sandBoxDriver.findElements(By.xpath("//div[@id='pt1:_UISsbb:0:r1:0:_sbpgl1']")).size() != 0) {
					if (sandBoxDriver.findElement(By.xpath("//div[@id='pt1:_UISsbb:0:r1:0:_sbpgl1']")).isDisplayed() == true) {
						sandBoxDriver	.findElement(By.xpath("//div[@id='pt1:_UISsbb:0:r1:0:_sbm2']//div[@class='af_menu_bar-item-open-icon-style']")).click();
						CommonUtils.hold(3);
						sandBoxDriver.findElement(By.xpath("//td[text()='Leave Sandbox']")).click();
						CommonUtils.hold(5);
						sandBoxDriver.findElement(By.id("pt1:_UISsbb:0:r1:0:_sbd2::ok")).click();
						CommonUtils.hold(10);
					}
				}

			}
			
			
		}


		@Override
		public void onFinish(ISuite arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onStart(ISuite arg0) {
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

