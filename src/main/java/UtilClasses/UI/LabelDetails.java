package UtilClasses.UI;

import org.openqa.selenium.WebDriver;

import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;

public class LabelDetails extends CommonUtilsDefs{
	
	public LabelDetails(WebDriver driver) {
		super(driver);
	}
	
	public void openAboutApplication(WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.explicitWait(userLink, "Click", "",driver);
		userLink.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(aboutLink, "Click", "",driver);
		aboutLink.click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(currentBranchName, "Click", "",driver);
	}
	
	public String getcurrentEnvironmentBranch() {
		return currentBranchName.getText();
	}
	
	public String getcurrentADFLabel() {
		return currentADFLabel.getText();
	}
	
	public String getcurrentATGLabel() {
		return currentATGLabel.getText();
	}
	
	public String getCurrentDBPatchLabel() {
		return currentDBPatchLabel.getText();
	}
	
	public void closeAboutApplication(WebDriver driver) throws Exception {
		CommonUtils.hold(3);
		CommonUtils.explicitWait(closeAboutPopUp, "Click", "",driver);
		closeAboutPopUp.click();
		CommonUtils.hold(4);
	}
}
