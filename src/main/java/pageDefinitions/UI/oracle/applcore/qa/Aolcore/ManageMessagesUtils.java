
package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;

/**
 * @author daskumar
 *
 */
public class ManageMessagesUtils extends ManageMessagesPage{
	
	public ManageMessagesUtils(WebDriver driver){
		super(driver);
	}

	public void createMessage(String messageName, String messageText,WebDriver driver) {

		try {
			CommonUtils.explicitWait(createBtn, "Visible", "",driver);
			createBtn.click();
			CommonUtils.hold(10);
			CommonUtils.explicitWait(saveandCloseBtn, "Visible", "",driver);
			MessageName.clear();
			MessageName.sendKeys(messageName);
			MessageApplication.sendKeys("Oracle Middleware Extensions for Applications");
			MessageModule.sendKeys("Oracle Middleware Extensions for Applications");
			MessageShortText.sendKeys(messageText);
			saveandCloseBtn.click();
			CommonUtils.hold(5);
		} catch (Exception e) {
			System.out.println("Create message operation failed. Please review the error message. "+e.getMessage());
		}
		

	}// End of createMessage()

	public void searchMessage(String messageName,WebDriver driver) throws Exception {		
		
		try{
			CommonUtils.explicitWait(msgSearchField, "Visible", "",driver);
			msgSearchField.clear();
			msgSearchField.sendKeys(messageName);
			searchBtn.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + messageName + "']")), "Click", "", driver);
		}catch(Exception e){
			System.out.println("ERROR : While searching for message :"+e.getMessage());
			e.printStackTrace();
		}
		
	}// End of searchMessage()

	public void createMessageToken(String messageName, String tokenName, String tokenDescription,WebDriver driver)	throws Exception {
		
		try {
			searchMessage(messageName,driver);
			editBtn.click();
			CommonUtils.hold(10);
			CommonUtils.explicitWait(createBtn, "Click", "",driver);
			createBtn.click();
			CommonUtils.hold(10);
			CommonUtils.explicitWait(MessageTokenName, "Visible", "",driver);
			MessageTokenName.sendKeys(tokenName);
			Select option = new Select(MessageTokenDataType);
			option.selectByVisibleText("Text");
			MessageTokenDescription.sendKeys(tokenDescription);
			saveandCloseBtn.click();
			CommonUtils.hold(3);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}// End of createMessageToken()

	public void updateMessage(String msgeName, String msgShortText,WebDriver driver) throws Exception {
		searchMessage(msgeName,driver);
		editBtn.click();
		CommonUtils.hold(5);
		MessageShortText.clear();
		MessageShortText.sendKeys(msgShortText);
		CommonUtils.hold(5);
		saveBtn.click();
		CommonUtils.hold(5);
		saveandCloseBtn.click();
		CommonUtils.hold(5);
	}// End of updateMessage()

	public void UpdateToken(String msgeName, String tokenUpdateText,WebDriver driver) throws Exception {
	
		try {
			searchMessage(msgeName,driver);
			editBtn.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(MessageTokenDescription, "Visible", "",driver);
			MessageTokenDescription.clear();
			MessageTokenDescription.sendKeys(tokenUpdateText);
			saveandCloseBtn.click();
			CommonUtils.hold(3);
		} catch (Exception e) {
			System.out.println("Unable to update token. Please review error message. "+e.getMessage());
		}

	}// End of UpdateToken()

	public void deleteToken(String msgName, String msgToken,WebDriver driver) throws Exception {

		try {
			searchMessage(msgName,driver);
			editBtn.click();
			CommonUtils.hold(2);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + msgToken + "']")),"Visible", "",driver);
			driver.findElement(By.xpath("//span[text()='" + msgToken + "']")).click();
			CommonUtils.hold(5);
			deleteBtn.click();
			CommonUtils.hold(2);
			saveandCloseBtn.click();
			CommonUtils.hold(2);
			verifyDeleteToken(msgName, msgToken,driver);
		} catch (Exception e) {
			System.out.println("Unable to delete token. Please review error message. "+e.getMessage());
		}

	}// End of deleteToken()

	public void deleteMessage(String msgName,WebDriver driver) throws Exception {
		searchMessage(msgName,driver);
		deleteBtn.click();
		CommonUtils.hold(2);
		saveBtn.click();
		CommonUtils.hold(2);
		verifyDeleteMessage(msgName,driver);

	}// End of deleteMessage()

	public void verifyDeleteMessage(String msgName,WebDriver driver) throws Exception {
		boolean element_status = false;
		try {
			searchMessage(msgName,driver);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + msgName + "']")), "Click", "", driver);
			driver.findElement(By.xpath("//span[text()='" + msgName + "']")).isDisplayed();
		} catch (Exception e) {
			System.out.println("Deleted Message - " + msgName);
			element_status = true;
			Assert.assertTrue(element_status);
		}
	}// End of verifyDeleteMessage()

	public boolean verifyDeleteToken(String msgName, String msgToken,WebDriver driver) throws Exception {
		boolean VerifyDeleteStatus = false;
		searchMessage(msgName,driver);
		editBtn.click();
		CommonUtils.hold(2);
		boolean element_status = false;
		try {
			driver.findElement(By.xpath("//span[text()='" + msgToken + "']")).isDisplayed();
		} catch (Exception e) {
			System.out.println("Deleted");
			element_status = true;
			Assert.assertTrue(element_status);
			// e.printStackTrace();
		}
		saveandCloseBtn.click();
		CommonUtils.hold(3);
		VerifyDeleteStatus = true;

		return VerifyDeleteStatus;
	}// End of verifyDeleteToken()

	public boolean verifyStatus(String messageName, String messageText, String tokenName,String tokenDescription,WebDriver driver) throws Exception {
		
		boolean VerifyMesssagesAndTokenstatus = false;
		searchMessage(messageName,driver);
		
		if (messageName != null && !messageName.isEmpty()) {
			try {
				System.out.println("messageName " + messageName);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text() ,'" + messageName + "')]")), "Visible", "",driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text() , '" + messageName + "')]")).isDisplayed());
			} catch (Exception e) {
				System.out.println("In VerifyStatus() - Message not found : ");
				e.printStackTrace();
				Assert.fail();
			}

		}
		if (tokenName != null && !tokenName.isEmpty()) {
			System.out.println("tokenName " + tokenName);
			try {
				editBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + tokenName + "']")),"Visible", tokenName,driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + tokenName + "']")).isDisplayed());
				saveandCloseBtn.click();
				CommonUtils.hold(2);

			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}

		}
		if (messageText != null && !messageText.isEmpty()) {
			System.out.println("messageText " + messageText);
			try {
				editBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//textarea[contains(text(),'" + messageText + "')]")),"Visible", messageText,driver);
				saveandCloseBtn.click();
				CommonUtils.hold(2);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}

		}
		if (tokenDescription != null && !tokenDescription.isEmpty()) {
			System.out.println("tokenDescription " + tokenDescription);
			try {
				editBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//input[contains(@value,'" + tokenDescription + "')]")),"Visible", messageText,driver);
				saveandCloseBtn.click();
				CommonUtils.hold(2);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}

		}
		VerifyMesssagesAndTokenstatus = true;

		return VerifyMesssagesAndTokenstatus;

	}// End of VerifyStatus()
	
	
	public void loaderVerifytoltalMessages(String messageName,WebDriver driver) throws Exception {
	
		try {
			CommonUtils.explicitWait(msgSearchField, "Visible", "",driver);
			msgSearchField.clear();
			msgSearchField.sendKeys(messageName);
			searchBtn.click();
			CommonUtils.hold(3);
			Assert.assertTrue(driver.findElements(By.xpath("//span[contains(text(),'"+messageName+"')]")).size()==5,"Total number of messagses : 5");	
		}catch(Exception ex) {
			System.out.println("ERROR : while getting message count : ");
			ex.printStackTrace();
		}	
		
	}

	
	public void loaderVerifyUpdate(String messageName,WebDriver driver,String selectTag, String checkBox,String TokenName,String textAreaTag,String inputTag) {
		try {
			searchMessage(messageName, driver);
			
			editBtn.click();
			CommonUtils.hold(3);
			
			if(TokenName!= null) {
				System.out.println("Inside TokenName .....");
				Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'"+TokenName+"')]")).isDisplayed());
			}

			if(selectTag != null) {
				System.out.println("Inside selectTag .....");
				Assert.assertTrue(driver.findElement(By.xpath("//select[@title='"+selectTag+"']")).isDisplayed());
			}
			if(checkBox !=null) {
				System.out.println("Inside checkbox .....");
				Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@id,'pt1:AP1:sbc1::content')]")).isSelected());
			}
			if(textAreaTag !=null) {
				System.out.println("Inside textAreaTag .....");
				Assert.assertTrue(driver.findElements(By.xpath("//textarea[contains(text(),'"+textAreaTag+"')]")).size()==6);
			}
			if(inputTag !=null) {
				System.out.println("Inside inputTag .....");
				Assert.assertTrue(driver.findElement(By.xpath("//input[@value='"+inputTag+"']")).isDisplayed());
			}
			
			saveandCloseBtn.click();
			CommonUtils.hold(3);
		}catch(Exception ex) {
			System.out.println("ERROR : While validating update case : ");
			ex.printStackTrace();
		}
		
	}

}
