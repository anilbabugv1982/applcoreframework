package pageDefinitions.UI.oracle.applcore.qa.Eff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import UtilClasses.UI.CommonUtils;
import TestBase.UI.GetDriverInstance;

public class EFFUtils extends EFFReferenceDataPage {
	
	public EFFUtils(WebDriver driver) {
		super(driver);
	}
	
	
	JavascriptExecutor js;
	static WebDriverWait wait = null;
	
	public String deleteContextFromPage(String flexCode,String categoryCode,String pageName,String contextCode) {
		String sqlDeleteContext = "BEGIN\r\n"+
								  "DELETE FROM FND_EF_UI_PAGE_TASK_FLOWS where DESCRIPTIVE_FLEXFIELD_CODE ='"+flexCode+"' AND CATEGORY_CODE ='"+categoryCode+"' AND PAGE_CODE='"+pageName+"' AND CONTEXT_CODE LIKE '"+contextCode+"';\r\n"+
								  "END;\r\n";
		return sqlDeleteContext;
	}
	
	public String deleteContextFromCategory(String flexCode,String categoryCode,String contextCode) {
		String sqlDeleteContext="BEGIN\r\n"+
								"DELETE FROM fnd_ef_category_contexts where DESCRIPTIVE_FLEXFIELD_CODE ='"+flexCode+"' AND CATEGORY_CODE ='"+categoryCode+"' AND CONTEXT_CODE LIKE '"+contextCode+"';\r\n"+
								"END;\r\n";
		return sqlDeleteContext;
	}
	
	public String deleteSegmentsFromContexts(String flexCode,String contextCode) {
		String sqlDeleteSegment = "BEGIN\r\n"+
								  "DELETE FROM fnd_df_segments_TL where descriptive_flexfield_code = '"+flexCode+"' AND CONTEXT_CODE LIKE "+contextCode+";\r\n"+
								  "DELETE FROM fnd_df_segments_B where descriptive_flexfield_code = '"+flexCode+"' AND CONTEXT_CODE LIKE "+contextCode+";\r\n"+
								  "END;\r\n";
		return sqlDeleteSegment;
	}
	
	public String deleteContexts(String flexCode,String contextCode) {
		String sqlDeleteContext="BEGIN\r\n"+
								 "DELETE FROM FND_EF_CONTEXT_USAGES WHERE descriptive_flexfield_code = '"+flexCode+"' AND CONTEXT_CODE LIKE "+contextCode+";\r\n"+
								 "DELETE FROM FND_DF_CONTEXTS_TL WHERE descriptive_flexfield_code = '"+flexCode+"' AND CONTEXT_CODE LIKE "+contextCode+";\r\n"+
								 "DELETE FROM FND_DF_CONTEXTS_B WHERE descriptive_flexfield_code = '"+flexCode+"' AND CONTEXT_CODE LIKE "+contextCode+";\r\n"+
								 "END;\r\n";
		return sqlDeleteContext;
	}
	
	public String deletePageFromItemClass(String flexCode,String pageName) {
		String sqlDeletePage = "BEGIN\r\n"+
							   "DELETE FROM fnd_ef_ui_pages_tl WHERE descriptive_flexfield_code = '"+flexCode+"' AND PAGE_CODE='"+pageName+"';\r\n"+
							   "DELETE FROM fnd_ef_ui_pages_b WHERE descriptive_flexfield_code = '"+flexCode+"' AND PAGE_CODE='"+pageName+"';\r\n"+
							   "END;\r\n";
		return sqlDeletePage;
	}
	
	
	public void scrollToPageTop() {
		System.out.println("Scrolling to top of page");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-450)");
		CommonUtils.hold(4);
	}

	public void scrollToPageBottom() {
		System.out.println("Scrolling to Bottom of page");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,450)");
		CommonUtils.hold(4);
	}
	
	public void waitConditionForElement(WebElement element) {
		wait = new WebDriverWait(driver, 300);
		CommonUtils.waitForPageLoad(driver);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void selectLOVValue(String segmentName,String value) {
		CommonUtils.hold(4);
		WebElement currentLOVExpandLink = driver.findElement(By.xpath("//a[contains(@id,'"+segmentName+"::lovIconId') or contains(@id,'"+segmentName+"_Display::lovIconId')]"));
		CommonUtils.hold(6);
		currentLOVExpandLink.click();
		CommonUtils.hold(6);
		if(driver.findElements(By.xpath("//span[contains(@id,'"+segmentName+"::dropdownPopup::dropDownContent') and text()='"+value+"']")).size()>0) {
			System.out.println("Value -"+value+" present under segment - "+segmentName);
			driver.findElement(By.xpath("//span[contains(@id,'"+segmentName+"::dropdownPopup::dropDownContent') and text()='"+value+"']")).click();
			CommonUtils.hold(8);
		}
	}
	
	public String getLOVValue(String segmentName) {
		String currentLOVValue = null;
		CommonUtils.hold(5);
		scrollToPageBottom();
		CommonUtils.hold(5);
		WebElement currentLOVText = driver.findElement(By.xpath("//input[contains(@id,'"+segmentName+"::content') or contains(@id,'"+segmentName+"_Display::content')]"));
		currentLOVValue=currentLOVText.getAttribute("value");
		return currentLOVValue;
	}
	
	
}
