package pageDefinitions.UI.oracle.applcore.qa.Loader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;

public class EssJobUsingCSVLoader extends ScheduleProcessObject {

	public LoaderWrapper csvLoader;
	public List<Integer> logRowCount;
	public LineNumberReader lrader;
	public NavigationTaskFlows nav=null;
	public ServiceRequestPage essPage=null;

	public EssJobUsingCSVLoader(WebDriver essPageObject) {
		super(essPageObject);
		essPage= new ServiceRequestPage(essPageObject);
		nav= new NavigationTaskFlows(essPageObject);

	}

	public void navigateToESSPage(WebDriver driver) {
		driver.get(GetDriverInstance.EnvironmentName);
		CommonUtils.hold(5);

		try {

			if (ApplicationLogin.newsFeedEnabled) {
				nav.navigateToTask(essPage.scheduleProcess, driver);
			} else {
				CommonUtils.hold(4);
				CommonUtils.explicitWait(toolsImageLink, "Click", "", driver);
				toolsImageLink.click();
				CommonUtils.explicitWait(processImageLink, "Click", "", driver);
				processImageLink.click();
				CommonUtils.hold(7);
			}

		} catch (Exception essPage) {
			System.out.println("Exception while navigating to ESS Process page : " + essPage.getMessage());
		}

	}

	public File runCsvESSJob(String essJobName, String accountName, String fileName, String csvLoaderName,WebDriver driver) {

		try {
			csvLoader = new LoaderWrapper(driver);
			return csvLoader.createEssJobForLoadingValueSet(essJobName, fileName, accountName, csvLoaderName, driver);

		} catch (Exception triggerJob) {
			System.out.println("Error while running runCsvESSJob method : " + triggerJob.getMessage());
			triggerJob.printStackTrace();
			return null;
		}
	}

	public void getCounts(File fileName) throws IOException {
		logRowCount = new ArrayList<Integer>();
		try {
			lrader = new LineNumberReader(new FileReader(fileName));
			String _line = null;
			int _lineCount = 0;
			boolean _contiueFlag = false;

			while ((_line = lrader.readLine()) != null) {
				if (_line.contains("---------------")) {
					_contiueFlag = true;
					_lineCount++;
				}
				if (_contiueFlag && _lineCount < 2) {
					if (_line.contains("---------------") || _line.isEmpty());
					else {
						logRowCount.add(Integer.valueOf(_line.replaceAll("[^0-9]", "")));
					}
				}
				if (_lineCount == 2)
					break;
			}

		} catch (Exception e) {
			System.out.println("Exception in method getCounts() : "+e.getMessage());
			e.printStackTrace();
		} finally {
			lrader.close();
		}
	}

	public boolean ValidateEssLogs(String loaderName, List<Integer> testData, File essTestData) throws Exception {
		
		try {
			boolean status = false;

			getCounts(essTestData);

			switch (loaderName) {
			case "USER_PROFILE_VALUES":
				System.out.println("INFO : USER_PROFILE_VALUES : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "VALUE_SET":
				System.out.println("INFO : VALUE_SET : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "VALUESET_VALUES":
				System.out.println("INFO : VALUESET_VALUES : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "PROFILE_OPTIONS":
				System.out.println("INFO : PROFILE_OPTIONS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "PROFILE_CATEGORIES":
				System.out.println("INFO : PROFILE_CATEGORIES : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "PROFILE_CATEGORY_OPTIONS":
				System.out.println("INFO : PROFILE_CATEGORY_OPTIONS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DFF_SEGMENT":
				System.out.println("INFO : DFF_SEGMENT : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DFF_CONTEXT":
				System.out.println("INFO : DFF_CONTEXT : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DFF_LABELED_SEGMENT":
				System.out.println("INFO : DFF_LABELED_SEGMENT : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "COMMON_TYPE":
				System.out.println("INFO : COMMON_TYPE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "COMMON_CODE":
				System.out.println("INFO : COMMON_CODE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "STANDARD_TYPE":
				System.out.println("INFO : STANDARD_TYPE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "STANDARD_CODE":
				System.out.println("INFO : STANDARD_CODE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "SETID_TYPE":
				System.out.println("INFO : SETID_TYPE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "SETID_CODE":
				System.out.println("INFO : SETID_CODE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "MESSAGE_TOKENS":
				System.out.println("INFO : MESSAGE_TOKENS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "MESSAGES":
				System.out.println("INFO : MESSAGES : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DS_CONDITIONS":
				System.out.println("INFO : DS_CONDITIONS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DS_GRANTS":
				System.out.println("INFO : DS_CONDITIONS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DOCUMENT_SEQUENCE_CATEGORY":
				System.out.println("INFO : DOCUMENT_SEQUENCE_CATEGORY : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			case "DOCUMENT_SEQUENCE":
				System.out.println("INFO : DOCUMENT_SEQUENCE : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
				status = Arrays.equals(testData.toArray(), logRowCount.toArray());
				break;
			default:
				break;

			}

			logRowCount.clear();
			return status;
		}catch(Exception logsess) {
			System.out.println("Error while processing ess log : "+logsess.getMessage());
			logsess.printStackTrace();
			logRowCount.clear();
			return false;
		}
		
	}

	public void getCountsTaskLevel(File fileName) throws IOException {
		logRowCount = new ArrayList<Integer>();
		try {
			lrader = new LineNumberReader(new FileReader(fileName));
			String _line = null;
			int _lineCount = 0;
			boolean _contiueFlag = false;

			while ((_line = lrader.readLine()) != null) {
				if (_line.contains("---------------")) {
					_contiueFlag = true;
					_lineCount++;
				}
				if (_contiueFlag && _lineCount < 2) {
					if (_line.contains("---------------") || _line.isEmpty());
					else {
						logRowCount.add(Integer.valueOf(_line.replaceAll("[^0-9]", "")));
						System.out.println(_line);
					}
				}
				if (_lineCount == 2) {
					_contiueFlag=false;
					_lineCount=0;
				}

			}

		} catch (Exception e) {
			System.out.println("Exception in method getCountsTaskLevel() : "+e.getMessage());
			e.printStackTrace();
		} finally {
			lrader.close();
		}

		System.out.println(Arrays.toString(logRowCount.toArray()));
	}

	public boolean validateLoaderLog(String loaderName, List<Integer> testData, File essTestData) throws Exception {

		try {
			boolean status = false;

			getCountsTaskLevel(essTestData);

			switch (loaderName) {
				case "STDLOOKUPS":
					System.out.println("INFO : STDLOOKUPS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
					status = Arrays.equals(testData.toArray(), logRowCount.toArray());
					break;
				case "CMNLOOKUPS":
					System.out.println("INFO : CMNLOOKUPS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
					status = Arrays.equals(testData.toArray(), logRowCount.toArray());
					break;
				case "SETLOOKUPS":
					System.out.println("INFO : SETLOOKUPS : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
					status = Arrays.equals(testData.toArray(), logRowCount.toArray());
					break;
				case "RELATEDVALUESET":
					System.out.println("INFO : RELATEDVALUESET : Expected data : " + Arrays.toString(testData.toArray()) + ", Actual data : "+ Arrays.toString(logRowCount.toArray()));
					status = Arrays.equals(testData.toArray(), logRowCount.toArray());
					break;
				default:
					break;
			}

			logRowCount.clear();
			return status;
		}catch(Exception logEss) {
			System.out.println("ERROR : Error while processing ess log : "+logEss.getMessage());
			logEss.printStackTrace();
			logRowCount.clear();
			return false;
		}

	}

	public String deleteValueSetCode(String valueSetCode) {
		return "delete from fnd_vs_value_sets where value_set_code like '"+valueSetCode+"%'";
	}
	
	public void deleteLookUpData(String type,String code) {
		
		try {
			System.out.println("INFO : Deleting lookup Type and Code before running test ");
			
			String lkpValue= "delete from FND_LOOKUP_VALUES where LOOKUP_TYPE like '"+code+"'";
			String lkpValue_tl= "delete from FND_LOOKUP_VALUES_TL where LOOKUP_TYPE like '"+code+"'";
			String lkpValue_b= "delete from FND_LOOKUP_VALUES_B where LOOKUP_TYPE like '"+code+"'";
			String lkpType_tl= "delete from FND_LOOKUP_TYPES_TL where LOOKUP_TYPE like '"+type+"'";
			String lkpType= "delete from FND_LOOKUP_TYPES where LOOKUP_TYPE like '"+type+"'"; 	
			
			DbResource.sqlStatement.executeUpdate(lkpValue);
			DbResource.sqlStatement.executeUpdate(lkpValue_tl);
			DbResource.sqlStatement.executeUpdate(lkpValue_b);
			DbResource.sqlStatement.executeUpdate(lkpType_tl);
			DbResource.sqlStatement.executeUpdate(lkpType);
			System.out.println("INFO : Deleted lookup Type and Code Successfully ");
		}catch(Exception stdDelete) {
			System.out.println("ERROR : Error while deleting look Type and Code : "+stdDelete.getMessage());
			stdDelete.printStackTrace();
		}
		
	}
	
	public void deleteMessageData(String messageName, String messageToken) {
		
		try {
			String msg_token="delete from FND_MESSAGE_TOKENS where MESSAGE_NAME like '"+messageToken+"'";
			String msg_tl="delete from FND_MESSAGES_TL where MESSAGE_NAME like '"+messageName+"'";
			String msg_b="delete from FND_MESSAGES_B where MESSAGE_NAME like '"+messageName+"'";
			
			DbResource.sqlStatement.executeUpdate(msg_token);
			DbResource.sqlStatement.executeUpdate(msg_tl);
			DbResource.sqlStatement.executeUpdate(msg_b);
			
		}catch(Exception msg) {
			System.out.println("ERROR : Error while deleting messages and tokens : "+msg.getMessage());
			msg.printStackTrace();
		}
		
	}
	
	
	public void deleteProfileOptions(String profileName) {
		
		try {
			String prf_level="delete from FND_PROFILE_OPTION_LEVELS where PROFILE_OPTION_ID in (select PROFILE_OPTION_ID from FND_PROFILE_OPTIONS_B where PROFILE_OPTION_NAME like '"+profileName+"')";
			String prf_tl="delete from FND_PROFILE_OPTIONS_B where PROFILE_OPTION_NAME like '"+profileName+"'";
			String prf_b="delete from FND_PROFILE_OPTIONS_TL where PROFILE_OPTION_NAME like '"+profileName+"'";
			
			DbResource.sqlStatement.executeUpdate(prf_level);
			DbResource.sqlStatement.executeUpdate(prf_tl);
			DbResource.sqlStatement.executeUpdate(prf_b);
			
		}catch(Exception msg) {
			System.out.println("ERROR : Error while deleting PROFILE_OPTIONS and tokens : "+msg.getMessage());
			msg.printStackTrace();
		}
		
	}
	
	
	public void deleteProfileCategory(String categoryName) {
		
		try {
			String prf_level="delete from FND_PROFILE_CAT_OPTIONS_B where CATEGORY_NAME like '"+categoryName+"'";
			String prf_tl="delete from FND_PROFILE_CATS_B where name like '"+categoryName+"'";
			String prf_b="delete from FND_PROFILE_CATS_TL where name like '"+categoryName+"'";
			
			DbResource.sqlStatement.executeUpdate(prf_level);
			DbResource.sqlStatement.executeUpdate(prf_tl);
			DbResource.sqlStatement.executeUpdate(prf_b);
			
		}catch(Exception msg) {
			System.out.println("ERROR : Error while deleting PROFILE_OPTIONS and tokens : "+msg.getMessage());
			msg.printStackTrace();
		}
		
	}
	
	

}
