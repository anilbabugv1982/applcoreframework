package pageDefinitions.UI.oracle.applcore.qa.Kff;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;


public class KFFUtils {
	
	private static WebDriverWait wait;

	public static String deleteSegments(String segmentCode) {
		String sqlDeleteSegment="BEGIN\r\n"+
								 "DELETE FROM FND_KF_SEGMENTS_TL WHERE SEGMENT_CODE like '"+segmentCode+"' ;\r\n"+
								 "DELETE FROM FND_KF_SEGMENTS_B WHERE SEGMENT_CODE like '"+segmentCode+"';\r\n"+
								 "DELETE FROM fnd_kf_segment_instances where SEGMENT_CODE like '"+segmentCode+"';\r\n"+
								 "END;\r\n";
		return sqlDeleteSegment;
	}

	public static String deleteLabel(String labelName) {
		String sqlDeleteLabel = "BEGIN\r\n"+
								"DELETE  FROM fnd_kf_segment_labels_b WHERE  SEGMENT_LABEL_CODE like'"+labelName+"';\r\n" +
								"DELETE  FROM FND_KF_SEGMENT_LABELS_TL WHERE  SEGMENT_LABEL_CODE like'"+labelName+"';\r\n"+
								"END;\r\n";
				return sqlDeleteLabel;
	}
	
	
//	public static void deleteExistingDataFromDB() throws Exception{
//		try{
//			System.out.println("Deleting Existing Segments and Labels from db");
//			DbResource.
//		//	CommonUtils.CreateStatement();
//			CommonUtils.SqlStatement.executeUpdate(KFFUtils.deleteSegments("segmentCode%"));
//			System.out.println("Segments deleted from db");
//			CommonUtils.SqlStatement.executeUpdate(KFFUtils.deleteLabel("labelcode%"));
//			System.out.println("Labels deleted from db");
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			CommonUtils.DBConnectionClose();
//		}
//	}
//	
//	public static void deleteLabelsFromDB() throws Exception{
//		try{
//			System.out.println("Deleting Labels from db");
//			CommonUtils.GetDBConnection();
//			CommonUtils.CreateStatement();
//			CommonUtils.SqlStatement.executeUpdate(KFFUtils.deleteLabel("labelcode%"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			CommonUtils.DBConnectionClose();
//		}
//	}
	
	public static void waitForElementToBeClickable(WebElement element,WebDriver driver) {
		CommonUtils.waitForPageLoad(driver);
		wait = new WebDriverWait(driver,250);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void waitForElementToBeVisible(WebElement element,WebDriver driver) {
		CommonUtils.waitForPageLoad(driver);
		wait = new WebDriverWait(driver,250);
	   wait.until(ExpectedConditions.visibilityOf(element));
	}
	public static void waitForElementNotVisible(String elementLocator,WebDriver driver) {
		CommonUtils.waitForPageLoad(driver);
		wait = new WebDriverWait(driver,250);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementLocator)));
	}
	
	
}
