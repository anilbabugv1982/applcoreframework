/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.ReportGenerator;

/**
 * @author klalam
 *
 */
public class ManageMessagesWrapperClass extends ManageMessagesPages{
	
	public ManageMessagesWrapperClass(WebDriver driver) {
		super(driver);
	}
	
	public void createMessage(String messageName, String messageText, WebDriver driver) {
		try {
			CommonUtils.explicitWait(pageHeading, "Visible", "",driver);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		createBtn.click();
		CommonUtils.hold(10);
		try {
			CommonUtils.explicitWait(saveandCloseBtn, "Visible", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageName.clear();
			System.out.println("Message Name < "+messageName+" >");
		MessageName.sendKeys(messageName);
		CommonUtils.hold(5);
		/*MessageApplication_Ddwon.click();
		CommonUtils.hold(2);
		try {
			CommonUtils.scrollToElement(CommonUtils.selectULelements("Oracle Middleware Extensions for Applications"));
			CommonUtils.ExplicitWait(CommonUtils.selectULelements("Oracle Middleware Extensions for Applications"), "Visible", "");
			CommonUtils.selectULelements("Oracle Middleware Extensions for Applications").click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonUtils.hold(5);
		CommonUtils.selectULelements("Oracle Middleware Extensions for Applications").click();
		CommonUtils.hold(5); */
		//MessageApplication.sendKeys("Oracle Middleware Extensions for Applications");
		MessageApplication.click();
		MessageModule.sendKeys("Oracle Middleware Extensions for Applications");
			System.out.println("Message Text <"+messageText+">");
		MessageShortText.sendKeys(messageText);
		saveandCloseBtn.click();
		CommonUtils.hold(4);

		//verifyStatus(messageName, "", "", "");
	}//End of createMessage()

	public void searchMessage(String messageName,WebDriver driver) {
		try {
			CommonUtils.explicitWait(msgSearchField, "Visible", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println("Search for message <"+messageName+"> begins");
		msgSearchField.clear();
		msgSearchField.sendKeys(messageName);
			System.out.println("Initiate Search");
		searchBtn.click();
		CommonUtils.hold(3);
	}//End of searchMessage()

	public void createMessageToken(String messageName, String tokenName, String tokenDescription,WebDriver driver) {
		searchMessage(messageName,driver);
			System.out.println("Message <"+messageName+"> found");
			System.out.println("Initiate message <"+messageName+"> edit");
		editBtn.click();
		CommonUtils.hold(10);
		try {
			CommonUtils.explicitWait(createBtn, "Click", "",driver);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			System.out.println("Initiate message <"+tokenName+"> token creation for the message <"+messageName+">");
		createBtn.click();
		CommonUtils.hold(10);
		try {
			CommonUtils.explicitWait(MessageTokenName, "Visible", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println("Token Name <"+tokenName+">");
		MessageTokenName.sendKeys(tokenName);
		Select option = new Select(MessageTokenDataType);
		option.selectByVisibleText("Text");
		/*MessageTokenDataTypeDdown.click();
		CommonUtils.hold(2);
		CommonUtils.selectULelements("Text").click();
		
		CommonUtils.hold(2);*/
			System.out.println("Token Description <"+tokenDescription+">");
		MessageTokenDescription.sendKeys(tokenDescription);
			System.out.println("Save the transaction");
		saveandCloseBtn.click();
		CommonUtils.hold(3);

		//verifyStatus(messageName, "", tokenName, "");
	}//End of createMessageToken()

	public void updateMessage(String msgeName, String applicationName, String msgShortText,WebDriver driver) throws Exception {
		searchMessage(msgeName,driver);
			System.out.println("Message <"+msgeName+"> found");
			System.out.println("Message <"+msgeName+"> update begins");
			try {
				CommonUtils.explicitWait(getMessageElement(msgeName,applicationName,driver), "Visible", "",driver);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				getMessageElement(msgeName,applicationName,driver).click();
		CommonUtils.hold(5);	
		editBtn.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(MessageShortText, "Visible", "",driver);
		MessageShortText.clear();
			System.out.println("Updated Text <"+msgShortText+">");
		MessageShortText.sendKeys(msgShortText);
		CommonUtils.hold(5);
		saveBtn.click();
		CommonUtils.hold(5);
		saveandCloseBtn.click();
		CommonUtils.hold(5);

		//verifyStatus(msgeName, msgShortText, "", "");
	}//End of updateMessage()

	public void UpdateToken(String msgeName, String applicationName, String tokenUpdateText, WebDriver driver) {
		searchMessage(msgeName,driver);
			System.out.println("Message <"+msgeName+"> found");
			System.out.println("Message token <"+msgeName+"> update begins");
		try {
			CommonUtils.explicitWait(getMessageElement(msgeName,applicationName,driver), "Visible", "",driver);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			getMessageElement(msgeName,applicationName,driver).click();
		CommonUtils.hold(5);
		editBtn.click();
		CommonUtils.hold(3);
		try {
			CommonUtils.explicitWait(MessageTokenDescription, "Visible", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageTokenDescription.clear();
			System.out.println("Updated Text <"+tokenUpdateText+">");	
		MessageTokenDescription.sendKeys(tokenUpdateText);
		saveandCloseBtn.click();
		CommonUtils.hold(3);
		//verifyStatus(msgeName, "", "", tokenUpdateText);
	}//End of UpdateToken()

	public void deleteToken(String msgName, String applicationName, String msgToken, WebDriver driver) {
		searchMessage(msgName,driver);
			System.out.println("Message <"+msgName+"> found");
			try {
				CommonUtils.explicitWait(getMessageElement(msgName,applicationName,driver), "Visible", "",driver);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				getMessageElement(msgName,applicationName,driver).click();
			CommonUtils.hold(5);
			editBtn.click();
		CommonUtils.hold(2);
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + msgToken + "']")), "Visible", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//span[text()='" + msgToken + "']")).click();
		CommonUtils.hold(5);
		deleteBtn.click();
		CommonUtils.hold(2);
		saveandCloseBtn.click();
		CommonUtils.hold(2);

		// searchMessage(msgName);
		// editBtn.click();
		// CommonUtils.hold(2);
		// boolean element_status=false;
		// try{
		// driver.findElement(By.xpath("//span[text()='"+msgToken+"']")).isDisplayed();
		// }catch(Exception e){
		// System.out.println("Deleted");
		// element_status=true;
		// Assert.assertTrue(element_status);
		// e.printStackTrace();
		// }
		// saveandCloseBtn.click();
		// CommonUtils.hold(3);
		verifyDeleteToken(msgName, msgToken,driver);
	}//End of deleteToken()

	public void deleteMessage(String msgName, String applicationName,WebDriver driver) {
		searchMessage(msgName,driver);
			System.out.println("Message <"+msgName+"> found");
			try {
				CommonUtils.explicitWait(getMessageElement(msgName,applicationName,driver), "Visible", "",driver);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				getMessageElement(msgName,applicationName,driver).click();
		deleteBtn.click();
		CommonUtils.hold(2);
		saveBtn.click();
		CommonUtils.hold(2);
		// searchMessage(msgName);
		//
		// boolean element_status=false;
		// try{
		// driver.findElement(By.xpath("//span[text()='"+msgName+"']")).isDisplayed();
		// }catch(Exception e){
		// System.out.println("Deleted123");
		// element_status=true;
		// Assert.assertTrue(element_status);
		// e.printStackTrace();
		// }
		verifyDeleteMessage(msgName,driver);

	}//End of deleteMessage()

	public void verifyDeleteMessage(String msgName,WebDriver driver) {
		searchMessage(msgName,driver);
			System.out.println("Message <"+msgName+"> found");
		boolean element_status = false;
		try {
			driver.findElement(By.xpath("//span[text()='" + msgName + "']")).isDisplayed();
		} catch (Exception e) {
			System.out.println("Deleted Message - "+msgName);
			element_status = true;
			Assert.assertTrue(element_status);
//			e.printStackTrace();
		}
	}//End of verifyDeleteMessage()

	public boolean verifyDeleteToken(String msgName, String msgToken, WebDriver driver) {
		boolean VerifyDeleteStatus = false;
		searchMessage(msgName,driver);
			System.out.println("Message <"+msgName+"> found");
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
	}//End of verifyDeleteToken()

	public boolean verifyStatus(String messageName, String messageText, String tokenName, String tokenDescription, WebDriver driver) {
		boolean VerifyMesssagesAndTokenstatus = false;
		searchMessage(messageName,driver);
			System.out.println("Message <"+messageName+"> found");
		if (messageName != null && !messageName.isEmpty()) {
			try {
				System.out.println("messageName " + messageName);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text() ,'" + messageName + "')]")),"Visible", "",driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text() , '" + messageName + "')]")).isDisplayed());
			} catch (Exception e) {
				System.out.println("In VerifyStatus() - Message not found");
				e.printStackTrace();
				Assert.fail();
			}

		}
		if (tokenName != null && !tokenName.isEmpty()) {
			System.out.println("tokenName " + tokenName);
			try {
				editBtn.click();
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + tokenName + "']")), "Visible",tokenName,driver);
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
				CommonUtils.explicitWait(driver.findElement(By.xpath("//textarea[contains(text(),'" + messageText + "')]")), "Visible",messageText,driver);
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
				CommonUtils.explicitWait(driver.findElement(By.xpath("//input[contains(@value,'" + tokenDescription + "')]")), "Visible",messageText,driver);
				saveandCloseBtn.click();
				CommonUtils.hold(2);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}

		}
		VerifyMesssagesAndTokenstatus = true;
		
	return VerifyMesssagesAndTokenstatus;

	}//End of VerifyStatus()
	
	/*
	 * getMessageElement(String messageName, String applicationName) return message webelement based on message name and application name
	 */
	public WebElement getMessageElement(String messageName, String applicationName, WebDriver driver) {
		WebElement messageElement = driver.findElement(By.xpath("//span[text() = '"+messageName+"']/ancestor::tr[1]/descendant::span[text() = '"+applicationName+"']"));
		
		return messageElement;
	}

	/*
	 * selectedMessage(String messageName, String applicationName) check for message selected or not
	 */
	public WebElement selectedMessage(String messageName, String applicationName, WebDriver driver) {
		WebElement selectedMessageElement = driver.findElement(By.xpath("//span[text() = '"+messageName+"']/ancestor::tr[1]/descendant::span[text() = '"+applicationName+"']/ancestor::tr[contains(@class , 'p_AFSelected')]"));
		
		return selectedMessageElement;
	}
}
