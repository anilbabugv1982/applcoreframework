package pageDefinitions.UI.oracle.applcore.qa.SessionImpersonation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class SessionImpersonationPage {
	private WebDriver driver;

	public SessionImpersonationPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	@FindBy(linkText="Proxies")
	public static WebElement proxiesLink;
	
	@FindBy(xpath="//a/img[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:_ATp:create::icon']")
	public static WebElement createProxyButton;
	
	@FindBy(xpath="//input[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:it35::content']")
	public static WebElement userName;

	@FindBy(xpath="//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:cb1']")
	public static WebElement searchButton;

	@FindBy(xpath="//div[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:t2::db']/table/tbody/tr/td[3]")
	public static WebElement selectUser2Row;

	@FindBy(xpath="//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:_Scb']")
	public static WebElement applyButton;

	@FindBy(xpath="//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:_Scb1']")
	public static WebElement okButton;

	@FindBy(xpath="//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:APscl']")
	public static WebElement saveButton;
	
	@FindBy(id = "pt1:_UIScmil1u::icon")
	public static WebElement channelAccManagerButton;

	@FindBy(linkText="Switch To")
	public static WebElement switchToButton;

	@FindBy(xpath="//input[@id='password']")
	public static WebElement impersonisationPassword;

	@FindBy(xpath="//button[contains(text(),'Confirm')]")
	public static WebElement confirm;

	@FindBy(xpath="//td/span[contains(text(),'Your session is audited. Some tasks cannot be performed as another user. (FND...')]")
	public static WebElement impersonationMessage;

	@FindBy(xpath="//*[@id='pt1:_UIScmil1u::icon']")
	public static WebElement linkUser;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:AP1:AT2:_Scb2")
	public static WebElement cancelButton;
	
	public void searchUser(String emailAddressUser) throws Exception{
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		Log.info("Click on add button");
		CommonUtils.explicitWait(createProxyButton, "Click", "", driver);
		createProxyButton.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(userName, "Visible", "", driver);
		userName.clear();
		userName.sendKeys(emailAddressUser);
		CommonUtils.hold(2);
		CommonUtils.explicitWait(searchButton, "Click", "", driver);
		searchButton.click();
		CommonUtils.hold(2);
		Log.info("Select the row that appeared");
		CommonUtils.explicitWait(selectUser2Row, "Click", "", driver);
		selectUser2Row.click();
		Log.info("click on apply button");
		CommonUtils.hold(5);
		CommonUtils.explicitWait(applyButton, "Click", "", driver);
		applyButton.click();
		Log.info("click on ok button");
		CommonUtils.hold(5);
		CommonUtils.explicitWait(okButton, "Click", "", driver);
		okButton.click();
		CommonUtils.hold(5);
		saveButton.click();
		CommonUtils.waitForPageLoad(driver);
	}

	public void searchUserAndDelete(String emailAddressUser5) throws Exception{
		String usr = "CHANNEL_OPERATIONS_MANAGER";
		List<WebElement> col = driver.findElements(By.xpath("//div[1]/table[@class='af_column_column-header-table']/tbody/tr[2]/th"));
		System.out.println("col size is"+col.size());
		List<WebElement> row =  driver.findElements(By.xpath("//div[2]/table[@class='af_table_data-table af_table_data-table-VH-lines']/tbody/tr"));
		System.out.println("row size is"+row.size());
		for(int j=0;j<row.size();j++) {
			List<WebElement> row1 =  driver.findElements(By.xpath("//div[2]/table[@class='af_table_data-table af_table_data-table-VH-lines']/tbody/tr"));
			System.out.println("row size is"+row1.size());
			for(int i=0;i<row1.size();i++) {
				String username = driver.findElement(By.xpath("//div[2]/table[@class='af_table_data-table af_table_data-table-VH-lines']/tbody/tr["+(i+1)+"]/td[4]")).getText();
				if(username.equals(usr)) {
					Log.info("username"+username);
					driver.findElement(By.xpath("//div[2]/table[@class='af_table_data-table af_table_data-table-VH-lines']/tbody/tr["+(i+1)+"]/td[1]")).click();
					CommonUtils.hold(5);
					driver.findElement(By.xpath("//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:MAnt2:1:AP1:AT2:_ATp:delete::icon']")).click();
					SessionImpersonationPage.saveButton.click();
					SessionImpersonationPage.proxiesLink.click();
					CommonUtils.waitForPageLoad(driver);
				}
			}
		}
	}

	public void deleteUser(String UserName) throws Exception{
		CommonUtils.hold(10);
		List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'Channel_OPS_Mgr_ENT')]"));
		int length = elements.size();
		Log.info("No of users added"+ length);
		for(int i=0;i<length;i++) {
			Log.info("**********************inside loop***********************");
			List<WebElement> elements1 = driver.findElements(By.xpath("(//tr[td[span[span[contains(text(),'Channel_OPS_Mgr_ENT')]]]]//td/a)[i]"));
			int size_1 = elements1.size();
			Log.info("No of users added"+ size_1);
			driver.findElement(By.xpath("(//tr[td[span[span[contains(text(),'Channel_OPS_Mgr_ENT')]]]]//td/a)[1]")).click();
			CommonUtils.hold(5);
			Log.info("Delete user");
			driver.findElement(By.xpath("//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:MAnt2:1:AP1:AT2:_ATp:delete::icon']")).click();
			Log.info("Save");
			SessionImpersonationPage.saveButton.click();
			Log.info("Click on proxies");
			SessionImpersonationPage.proxiesLink.click();
			CommonUtils.waitForPageLoad(driver);
		}
	}

	public void ImpersoniseUser(String UserName) throws Exception {
		CommonUtils.hold(15);
		CommonUtils.waitForPageLoad(driver);
		Log.info("Inside Impersonise user");
		CommonUtils.explicitWait(SessionImpersonationPage.channelAccManagerButton, "Click", "", driver);
		SessionImpersonationPage.channelAccManagerButton.click();
		CommonUtils.hold(5);
		Log.info("Click on Switch To button");
		CommonUtils.explicitWait(SessionImpersonationPage.switchToButton, "Click", "", driver);
		SessionImpersonationPage.switchToButton.click();
		switchTouser(UserName);
		Log.info("Enter Impersonation Passwrod");
		CommonUtils.explicitWait(SessionImpersonationPage.impersonisationPassword, "Visible", "", driver);
		SessionImpersonationPage.impersonisationPassword.sendKeys("Welcome1");
		CommonUtils.explicitWait(SessionImpersonationPage.confirm, "Click", "", driver);
		SessionImpersonationPage.confirm.click();
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		Log.info("Read the Impersonation Message");
		CommonUtils.explicitWait(SessionImpersonationPage.impersonationMessage, "Visible", "", driver);
		String ImpersonationConfirmMsg = SessionImpersonationPage.impersonationMessage.getText();
		String ImpersonationConfirmMsg_actual = "Your session is audited. Some tasks cannot be performed as another user. (FND...";
		Log.info(ImpersonationConfirmMsg);
		Assert.assertEquals(ImpersonationConfirmMsg, ImpersonationConfirmMsg_actual);
	}

	//This method is to check whether User1 can impersonate User1
	public void searchUserOne(String emailAddressUser2) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		Log.info("Create on add button");
		CommonUtils.explicitWait(SessionImpersonationPage.createProxyButton, "Click", "", driver);
		SessionImpersonationPage.createProxyButton.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(SessionImpersonationPage.userName, "Visible", "", driver);
		SessionImpersonationPage.userName.clear();
		SessionImpersonationPage.userName.sendKeys(emailAddressUser2);
		CommonUtils.hold(2);
		CommonUtils.explicitWait(SessionImpersonationPage.searchButton, "Click", "", driver);
		SessionImpersonationPage.searchButton.click();
		CommonUtils.hold(2);
		System.out.println("Select the user filtered");
		Log.info("Select the user filtered");
		List<WebElement> list = driver.findElements(By.xpath("//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:MAnt2:1:AP1:AT2:t2::db']/table/tbody/tr/td[4]"));
		int size = list.size();
		if(size==0) {
			Log.info("The size of the list is"+size);
			Boolean notPresent = true;
			Assert.assertTrue(notPresent);
			Log.info("Cannot add proxy for User1 as user1 is not found upon searching");
			Log.info("User1 cannot impersonate user1");
			CommonUtils.explicitWait(SessionImpersonationPage.cancelButton, "Click", "", driver);
			SessionImpersonationPage.cancelButton.click();
		}
		else {
			Boolean notPresent = false;
			Assert.assertTrue(notPresent);
			Log.info("User1 can impersonate user1");
		}
	}

	public void switchTouser(String UserName) throws Exception	{
		System.out.println("Username"+UserName);
		List<WebElement> col = driver.findElements(By.xpath("//div[1]/table[@id='pt1:_USSr1:0:_SIme::ScrollContent']/tbody/tr[2]/th"));
		System.out.println("col size is"+col.size());
		List<WebElement> row =  driver.findElements(By.xpath("//div/table[@id='pt1:_USSr1:0:_SIme::ScrollContent']/tbody/tr"));
		System.out.println("row size is"+row.size());
		int index = -1;
		for(int i=0;i<row.size();i++) {
			String username = driver.findElement(By.xpath("//div/table[@id='pt1:_USSr1:0:_SIme::ScrollContent']/tbody/tr["+(i+1)+"]/td[2]")).getText();
			Log.info(username);
			if(username.equals(UserName)) {
				Log.info("username" +username);
				driver.findElement(By.xpath("//div/table[@id='pt1:_USSr1:0:_SIme::ScrollContent']/tbody/tr["+(i+1)+"]/td[2]")).click();
				index = i;
				CommonUtils.hold(5);
				break;
			}

		}
		if(index >= 0) {
			Log.info("The expected user was found to impersonate");
		}else {
			Assert.fail("The expected user was not found to impersonate");
		}
	}

}

