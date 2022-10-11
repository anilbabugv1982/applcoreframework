package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;

public class AttachmentCommonUtils {
	
	public static String getAttachmentFilePath(String fileName) {
		File file = new File("src/test/resources/AttachmentFiles/"+fileName); 
		System.out.println(file.getAbsolutePath());

		return file.getAbsolutePath();
	}
	
	public static void validateURL(WebElement urlLink, String URL, WebDriver driver) {
		Assert.assertTrue(urlLink
				.getAttribute("href").contains(URL), "Download URL link not present");
		
		// Store the current window handle
		String parentWindow = driver.getWindowHandle();
		
		urlLink.click();
		CommonUtils.hold(5);
		
		try {
			// Switch to new window opened
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
			
			System.out.println(driver.getCurrentUrl());
			Assert.assertTrue(driver.getCurrentUrl().contains(URL), "URL link not working.");
			
			// Close the new window, if that window no more required
			driver.close();
			
		} finally {
			// Switch back to original browser (first window)
			System.out.println("Switching back to original window.");
			driver.switchTo().window(parentWindow);
		}
	}
	
	public static void waitTillFileUpload(WebDriver driver) {
		//CommonUtils.waitForInvisibilityOfElement(driver.findElement(By.xpath("//div[@class='AFBlockingGlassPane' and contains(@style,'visibility: visible')]")), driver, 60);
		final int TIMEOUT = 30000;  // 30 seconds
		long targetTime = System.currentTimeMillis() + TIMEOUT;
		while((System.currentTimeMillis() < targetTime)) {
		    if (driver.findElement(By.xpath("//div[@class='AFBlockingGlassPane']")).getAttribute("style").contains("visibility: hidden")) {
		        CommonUtils.hold(1);
		    	break;
		    }
		}
		CommonUtils.hold(2);
	}
	
	public static void isFileDownloadedSuccessfully(String filename) {
		try {
			Assert.assertTrue(AttachmentCommonUtils.isFileDownloaded(GetDriverInstance.fsmExpImpFile, filename));
		}catch (AssertionError e) {
			CommonUtils.hold(10);
			Assert.assertTrue(AttachmentCommonUtils.isFileDownloaded(GetDriverInstance.fsmExpImpFile, filename), "Failed to download the file.");
		}
	}
	
	public static boolean isTextFileDownloaded(String downloadPath, String filename) {
		
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();
		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().startsWith(filename)) {
		        // File has been found, it can now be deleted:
				System.out.println("text file found. now deleting.");
		        dirContents[i].delete();
		        return true;
		     }
		}
		return false;    
	}
	
	public static void isTextFileDownloadedSuccessfully(String filename) {
		try {
			Assert.assertTrue(AttachmentCommonUtils.isTextFileDownloaded(GetDriverInstance.fsmExpImpFile, filename));
		}catch (AssertionError e) {
			CommonUtils.hold(10);
			Assert.assertTrue(AttachmentCommonUtils.isTextFileDownloaded(GetDriverInstance.fsmExpImpFile, filename), "Failed to download the Text file.");
		}
	}
	
	public static void waitTillLoad(int timeInSeconds, WebDriver driver) {
		CommonUtils.hold(1);
		final int TIMEOUT = timeInSeconds*1000;  
		long targetTime = System.currentTimeMillis() + TIMEOUT;
		while((System.currentTimeMillis() < targetTime)) {
			CommonUtils.hold(2);
		    if (driver.findElement(By.xpath("//body[contains(@class,'af_document p_AFMaximized')]")).getAttribute("style").contains("cursor: auto;")) {
		        break;
		    }
		}
		CommonUtils.hold(2);
	}

	public static void waitTillUILoad(int timeInSeconds, WebDriver driver) {
		CommonUtils.hold(1);
		final int TIMEOUT = timeInSeconds*1000;  
		long targetTime = System.currentTimeMillis() + TIMEOUT;
		while((System.currentTimeMillis() < targetTime)) {
			CommonUtils.hold(2);
		    if (!driver.findElement(By.xpath("//body[contains(@class,'af_document p_AFMaximized')]")).getAttribute("style").contains("cursor: wait;")) {
		    	break;
		    }
		}
		CommonUtils.hold(2);
	}
	
	public static void clickOnUpdateButton(WebElement updateButton, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(updateButton).click().build().perform();
		AttachmentCommonUtils.waitTillUILoad(8, driver);
		CommonUtils.hold(8);
		if(!driver.findElement(By.xpath("//td[@class='af_inputFile_label']/label[contains(text(),'File Name')]")).isDisplayed()) {
			//actions.moveToElement(updateButton).click().build().perform();
			driver.findElement(By.xpath("//button[contains(text(),'Update')]")).click();
			AttachmentCommonUtils.waitTillUILoad(8, driver);
			CommonUtils.hold(5);
		}
			
	}
	
	public static String substring(String text, int size) {
		return text.substring(0, Math.min(text.length(), size));
	}
	
//**************************************************************************************************************************

	public static boolean isFileDownloaded(String downloadPath, String fileName) {
		  File dir = new File(downloadPath);
		  File[] dirContents = dir.listFiles();

		  for (int i = 0; i < dirContents.length; i++) {
		      if (dirContents[i].getName().equals(fileName)) {
		          // File has been found, it can now be deleted:
		          dirContents[i].delete();
		          return true;
		      }
		          }
		      return false;
	}
	
//*************************************************************************************************************************
	
	public static WebElement horizontalScrollToElement(WebElement element, WebDriver driver) throws InterruptedException {
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: \"end\", inline: \"nearest\"});", new Object[] { element });
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

		CommonUtils.hold(5);
		driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
		return element;
	}
		
	
}
