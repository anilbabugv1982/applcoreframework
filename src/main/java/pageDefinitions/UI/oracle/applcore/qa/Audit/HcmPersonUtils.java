package pageDefinitions.UI.oracle.applcore.qa.Audit;

import org.openqa.selenium.WebDriver;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class HcmPersonUtils extends HcmPersonReferenceDetails {

	public HcmPersonUtils(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		
	}
	
//*****************************************************************************************************************************
	public void createperson(WebDriver driver) {
		
		Hireanemp.click();
		Log.info("Click on Hire an Employee");
		CommonUtils.isElementPresent(LegalEmp);
		Log.info("LegalEmployee Is present");
		CommonUtils.hold(10);
		CommonUtils.selectDropDownElement(LegalEmp, "ZHRX-UK-C-LE001");
		LegalEmp.click();
		Log.info("Click on Legal Employee");
		
	}
	
//*****************************************************************************************************************************	
}
