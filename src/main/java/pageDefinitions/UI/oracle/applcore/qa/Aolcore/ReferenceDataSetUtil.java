package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;

public class ReferenceDataSetUtil extends ManageReferenceDataPage{
	
	public ReferenceDataSetUtil(WebDriver driver){
		super(driver);
	}

	public void createReferenceDataSet(String name,WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.explicitWait(addBtn, "Click", "",driver);
		addBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(setCodeInput, "Click", "",driver);
		setCodeInput.sendKeys(name);
		setNameInput.sendKeys(name);
		setDescInput.sendKeys(name);
		saveBtn.click();		
		CommonUtils.hold(5);
		searchReferenceDataSet(name,driver);
	}

	public void searchReferenceDataSet(String name,WebDriver driver) throws Exception {
		CommonUtils.explicitWait(setCodeSearchField, "Click", "", driver);
		setCodeSearchField.clear();
		setCodeSearchField.sendKeys(name);
		searchBtn.click();
		CommonUtils.hold(5);
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value=" + name + "]")), "Click", "", driver);
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value=" + name + "]")).isDisplayed());
		} catch (Exception e) {
//			Assert.fail("Unbale find the specified referencedataset."+name+". "+e.getMessage());
		}
	}

	public void updateReferenceDataSet(String refName, String update,WebDriver driver) throws Exception {
		searchReferenceDataSet(refName,driver);
		CommonUtils.explicitWait(setDescInput, "Click", "", driver);
		setDescInput.clear();
		setDescInput.sendKeys(update);
		saveBtn.click();
		isButtonEnabled(saveBtn);
		CommonUtils.hold(7);

		if (setDescInput.getAttribute("value").equalsIgnoreCase(update)){
			Assert.assertTrue(true,"Successfully updated reference data set value.");
		}else{
			Assert.fail("Unable to update specified reference dataset : "+refName);
		}
		
	}

	public void createSetAssignments(String groupName, String determinantValue, String setName,WebDriver driver)
			throws Exception {
		CommonUtils.hold(3);
		CommonUtils.explicitWait(addBtn, "Click", "",driver);
		addBtn.click();
		CommonUtils.explicitWait(groupNameDD, "Click", "",driver);
		CommonUtils.hold(3);
		CommonUtils.selectDropDownElement(groupNameDD, groupName);
		CommonUtils.selectDropDownElement(determinantNameDD, determinantValue);
		refSetNameInput.sendKeys(setName);
		saveBtn.click();
		isButtonEnabled(saveBtn);
		CommonUtils.hold(10);
		searchAssignments(setName,driver);
		try {
			Assert.assertTrue(driver
					.findElement(By.xpath("//input[@value='" + setName + "']")).isDisplayed());
		} catch (Exception e) {
			Assert.fail("Unbale create the specified setAssignment. "+setName+". "+e.getMessage());
		}
	}
	

	public void searchAssignments(String setName,WebDriver driver) throws Exception {
	    CommonUtils.hold(5);
		refSetNameSearch.clear();
		refSetNameSearch.sendKeys(setName);
		searchBtn.click();
		CommonUtils.hold(8);
	}

	public void updateAssignments(String oldSetName, String newSetName,WebDriver driver) throws Exception {
		searchAssignments(oldSetName,driver);
		refSetNameInput.clear();
		refSetNameInput.sendKeys(newSetName);
		saveBtn.click();
		isButtonEnabled(saveBtn);		
		CommonUtils.hold(10);
		try {
			Assert.assertTrue(driver
					.findElement(By.xpath("//input[@value='" + newSetName + "']")).isDisplayed());
		} catch (Exception e) {
			Assert.fail("Unbale update the specified setAssignment. "+oldSetName);
		}
	}

	public void deleteAssignments(String setName,WebDriver driver) throws Exception {
		searchAssignments(setName,driver);
		deleteBtn.click();
		CommonUtils.hold(5);
		saveBtn.click();
		isButtonEnabled(saveBtn);
		CommonUtils.hold(10);

		try {
			Assert.assertTrue(driver
					.findElement(By.xpath("//div[contains(text(),'No results found')]")).isDisplayed());
		} catch (Exception e) {
			Assert.fail("Unbale delete the specified setAssignment. "+setName);
		}
	}
	
	public void isButtonEnabled(WebElement element) throws Exception {
		
		long startTime = System.currentTimeMillis();
	    long endTime = 0;
	    long totalTime = 0;
		 
		while (!element.isEnabled() && totalTime < 25 * 1000) {
			try {
				CommonUtils.hold(5);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime);
                System.out.println("INFO :: waitForElementToEnable "+totalTime);
            }

		}
	}

}
