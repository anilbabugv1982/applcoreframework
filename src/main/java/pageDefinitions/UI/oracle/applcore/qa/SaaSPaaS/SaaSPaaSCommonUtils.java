package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import UtilClasses.UI.CommonUtils;

public class SaaSPaaSCommonUtils {

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

}
