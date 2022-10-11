package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;


public class DocSeqUtil extends ManageDocumentSequence {
	
	public DocSeqUtil(WebDriver docUtilDriver) throws Exception {
		super(docUtilDriver);
	}

	public void createDocSeqCategory(String categoryName,WebDriver driver) throws Exception{
		createBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(applicationDD, CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
		CommonUtils.hold(6);
		CommonUtils.selectDropDownElement(applicationDD, "Oracle Middleware Extensions for Applications");
		categoryCodeField.sendKeys(categoryName);
		categoryNameField.sendKeys(categoryName);
		moduleField.sendKeys("Oracle Middleware Extensions for Applications");
		tableNameField.sendKeys(categoryName);
		descField.sendKeys(categoryName);
		saveBtn.click();
		CommonUtils.hold(8);		
		
	}
	
	public  void verifystatus2(String categoryName,WebDriver driver){
		try{
			searchDocCategory(categoryName,driver);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//input[@value='"+categoryName+"']")), CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
			CommonUtils.hold(5);
			Assert.assertTrue(driver.findElement(By.xpath("//input[@value='"+categoryName+"']")).isDisplayed());
		}catch(Exception e){
			e.getMessage();
			Assert.assertFalse(false);
		}
	}
	
	public  void searchDocCategory(String categoryName,WebDriver driver) throws Exception{
		CommonUtils.explicitWait(categorySearch, "Click", "", driver);
		CommonUtils.hold(4);
		categorySearch.clear();
		categorySearch.sendKeys(categoryName);
		searchBtn.click();
		CommonUtils.hold(7);	
		
	}
	
	public void updateDocSeqCategory(String categoryName,String update,WebDriver driver) throws Exception{
		searchDocCategory(categoryName,driver);	
		categoryNameField.clear();
		categoryNameField.sendKeys(update);
		saveBtn.click();
		CommonUtils.hold(8);		
	}
	
	public void deleteDocSeqCategory(String categoryName,WebDriver driver) throws Exception{
		searchDocCategory(categoryName,driver);
		deleteBtn.click();
		saveBtn.click();
		CommonUtils.hold(7);
		
		try{
			searchDocCategory(categoryName,driver);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(text(),'No results found.')]")), CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
			CommonUtils.hold(5);
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'No results found.')]")).isDisplayed(), "Deleted DocSeqCategory "+categoryName);
		}catch(Exception e){
			e.getMessage();
			Assert.assertFalse(false, "DocSeqCategory delete failed "+categoryName);
			
		}
	}
	
	
	
	public void createDocumentSequence(String docSeqName,WebDriver driver) throws Exception{
		createBtn.click();
		CommonUtils.explicitWait(docSeqNameField, "Click", "",driver);
		CommonUtils.hold(8);
		docSeqNameField.click();
		docSeqNameField.clear();
		docSeqNameField.sendKeys(docSeqName);
		CommonUtils.explicitWait(applicationDD, CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
		CommonUtils.selectDropDownElement(applicationDD, "Oracle Middleware Extensions for Applications");
		moduleField.sendKeys("Oracle Middleware Extensions for Applications");
		CommonUtils.selectDropDownElement(typeDD, "Automatic");
		startDate.click();
		CommonUtils.hold(2);
		currentDate.click();
		saveBtn.click();
		CommonUtils.hold(8);		
	}
	
	public void searchDocSeq(String docSeqName) throws Exception{
		docNameSearch.clear();
		docNameSearch.sendKeys(docSeqName);
		searchBtn.click();
		CommonUtils.hold(5);	
	
	}
	
	public void verifyStatus1(String docSeqName,String assignmentName,WebDriver driver) throws Exception{
		searchDocSeq(docSeqName);
		if(!assignmentName.isEmpty()){
			try{
				System.out.println("Not empty. ......");
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+assignmentName+"']")), CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='"+assignmentName+"']")).isDisplayed());			
			}catch(Exception e){
				Assert.fail();
			}
		}else{
			try{
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+docSeqName+"']")), CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='"+docSeqName+"']")).isDisplayed());			
			}catch(Exception e){
				Assert.fail();
			}
		}
		
		
	}
	
	public  void createDocSeqAssignment(String docSeqName, String assignmentName,WebDriver driver) throws Exception{
		searchDocSeq(docSeqName);
		assignCreateBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(assignmentNameField, CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
		CommonUtils.hold(8);
		assignmentNameField.sendKeys(assignmentName);
		assignstartDate1.click();
		currentDate.click();
		saveBtn.click();
		CommonUtils.hold(8);	
		
	}
	
	public void deleteDocSeqAssignment(String docSeqName,String assignmentName,WebDriver driver) throws Exception{
		searchDocSeq(docSeqName);
		driver.findElement(By.xpath("//span[text()='"+assignmentName+"']"));
		driver.findElement(By.xpath("//span[text()='"+assignmentName+"']")).click();
		CommonUtils.hold(4);
		assignDeleteBtn.click();
		CommonUtils.hold(4);
		saveBtn.click();
		CommonUtils.hold(5);
		searchDocSeq(docSeqName);
		
		try{
			
			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")), CommonUtils.ExplicitWaitCalls.Visible.toString(), "",driver);
			Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")).isDisplayed(), "Deleted assignemnt "+assignmentName);
		}catch(Exception e){
			e.getMessage();
			Assert.assertFalse(false, "Assignment delete faield "+assignmentName);
			
		}
		
	}	
	
}
