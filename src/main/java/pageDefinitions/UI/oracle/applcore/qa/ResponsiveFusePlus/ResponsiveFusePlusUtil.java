package pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;

public class ResponsiveFusePlusUtil extends ResponsiveFusePlusPage {

	private NavigationTaskFlows taskFlowsNavigation;
	private ApplicationLogin applogin;
	private ProfilesUtils pOUtil;
	private ResponsiveFusePlusPage responsivepages;

	public ResponsiveFusePlusUtil(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void navigateto(WebDriver driver, String mainselection, String task, String quickaction) throws Exception {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		driver.findElement(By.xpath("//a[text() = '" + mainselection + "']")).click();
		CommonUtils.hold(10);

		if (quickaction.contains("yes")) {
			// String divid = driver.findElement(By.xpath("//div[@class =
			// 'atk-col-quickactions']/div[2]")).getAttribute("id");
			// System.out.print(divid);
			// showmore.click();
			WebElement tasksubselection = driver.findElement(By.xpath("//a[text() = '" + task + "']"));
			CommonUtils.explicitWait(tasksubselection, "Click", "", driver);

			executor.executeScript("arguments[0].click();", tasksubselection);

		}

		else {
			driver.findElement(By.xpath("//div[@class = 'atk-col-apps']//a[text() = '" + task + "']")).click();

		}
		CommonUtils.hold(40);

	}

	public void checkprofileset(WebDriver driver, String profile) throws Exception {

		taskFlowsNavigation = new NavigationTaskFlows(driver);
		applogin = new ApplicationLogin(driver);
		pOUtil = new ProfilesUtils(driver);
		CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", driver);
		taskFlowsNavigation.navigateToTask(applogin.getGloablePageTemplateInstance().setupandmaintenance, driver);
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Administrator Profile Values", driver);
		pOUtil.searchAdminProfile(profile, driver);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(profileValue, "Click", "", driver);
		String profilevalue = profileValue.getText();
		if (profilevalue.contains("N")) {

			profileValue.clear();
			profileValue.sendKeys("Y");

		}

		savendclosebtn.click();
		CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().homeIcon, "Click", "", driver);
		taskFlowsNavigation.getGlobalPageInstance().homeIcon.click();

	}

	public void createresponsivelayout(WebDriver driver) throws Exception {
		String layoutname = "Payroll Responsive Layout" + CommonUtils.uniqueId();
		String profilename = "Payroll Time Entry Responsive Profile" + CommonUtils.uniqueId();
		navigateto(driver, "My Client Groups", "Time Management", "No");
		CommonUtils.explicitWait(Tasks, "Click", "", driver);
		Tasks.click();
		CommonUtils.explicitWait(layoutSets, "Click", "", driver);
		layoutSets.click();
		CommonUtils.explicitWait(layoutSetInputName, "Click", "", driver);
		layoutSetInputName.sendKeys("Payroll Layout Set");
		CommonUtils.explicitWait(layoutSetSearch, "Click", "", driver);
		layoutSetSearch.click();
		CommonUtils.explicitWait(payrollLayoutSetRow, "Click", "", driver);
		payrollLayoutSetRow.click();
		duplicate.click();
		CommonUtils.explicitWait(labelResposiveUI, "Click", "", driver);
		labelResposiveUI.click();
		CommonUtils.explicitWait(generate, "Click", "", driver);
		generate.click();
		CommonUtils.explicitWait(payrollLayouSetName, "Click", "", driver);
		payrollLayouSetName.sendKeys(layoutname);
		payrollLayoutSetSaveandClose.click();
		CommonUtils.explicitWait(payrollLayoutSetOk, "Click", "", driver);
		payrollLayoutSetOk.click();
		CommonUtils.explicitWait(backButton, "Click", "", driver);
		;
		backButton.click();
		CommonUtils.explicitWait(Tasks, "Click", "", driver);
		Tasks.click();
		CommonUtils.explicitWait(workerTimeEntryProfile, "Click", "", driver);
		workerTimeEntryProfile.click();
		CommonUtils.explicitWait(workerTimeEntryProfileName, "Click", "", driver);
		workerTimeEntryProfileName.sendKeys("Payroll Time Entry Profile");
		CommonUtils.explicitWait(workerTimeEntryProfileSearch, "Click", "", driver);
		workerTimeEntryProfileSearch.click();
		CommonUtils.explicitWait(payrollTimeEntryProfileRow, "Click", "", driver);
		payrollTimeEntryProfileRow.click();
		CommonUtils.explicitWait(duplicate, "Click", "", driver);
		duplicate.click();
		CommonUtils.explicitWait(payrollTimeEntryProfileName, "Click", "", driver);
		payrollTimeEntryProfileName.sendKeys(profilename);
		layoutSetDropDown.click();
		CommonUtils.explicitWait(popupSearch, "Click", "", driver);
		;
		popupSearch.click();
		CommonUtils.explicitWait(popUpLayoutSetName, "Click", "", driver);
		popUpLayoutSetName.clear();
		popUpLayoutSetName.sendKeys(layoutname);
		CommonUtils.explicitWait(popLayoutSearch, "Click", "", driver);
		popLayoutSearch.click();
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//span[text() = '" + layoutname + "']")).click();
		popupOk.click();
		CommonUtils.explicitWait(next, "Click", "", driver);
		next.click();
		CommonUtils.explicitWait(next, "Click", "", driver);
		next.click();
		CommonUtils.explicitWait(next, "Click", "", driver);
		next.click();
		CommonUtils.explicitWait(payrollTimeEntryProfileSaveandClose, "Click", "", driver);
		;
		payrollTimeEntryProfileSaveandClose.click();
		CommonUtils.explicitWait(payrollLayoutSetOk, "Click", "", driver);
		payrollLayoutSetOk.click();
		CommonUtils.explicitWait(trobleshoot, "Click", "", driver);
		trobleshoot.click();
		CommonUtils.explicitWait(employeeName, "Click", "", driver);
		employeeName.sendKeys("payemp1,nvn");
		CommonUtils.explicitWait(layoutSetSearch, "Click", "", driver);
		layoutSetSearch.click();
		CommonUtils.explicitWait(payRollTimeEntryRow, "Click", "", driver);
		payRollTimeEntryRow.click();
		CommonUtils.explicitWait(deleteOverride, "Click", "", driver);
		deleteOverride.click();
		CommonUtils.explicitWait(deleteOverrideYes, "Click", "", driver);
		deleteOverrideYes.click();
		CommonUtils.explicitWait(assignProfileToPerson, "Click", "", driver);
		assignProfileToPerson.click();
		CommonUtils.explicitWait(profileName, "Click", "", driver);
		profileName.sendKeys(profilename);
		CommonUtils.explicitWait(profileName, "Click", "", driver);
		profileName.sendKeys(Keys.ENTER);
		CommonUtils.explicitWait(profileOk, "Click", "", driver);
		profileOk.click();
	}

	public void clickedit(WebDriver driver, String label, int labelindex) throws Exception {

		try {
			String panelsecid = driver
					.findElement(By.xpath("(//label[text() ='" + label + "'])[" + labelindex + "]/..//parent::div"))
					.getAttribute("id");
			System.out.print(panelsecid);
			int index = panelsecid.lastIndexOf(":");
			System.out.println(index);
			panelsecid = panelsecid.substring(0, index);
			System.out.print(panelsecid);
			panelsecid = panelsecid.concat(":PSEcil6");
			System.out.print(panelsecid);
			driver.findElement(By.xpath("//a[@id = '" + panelsecid + "']")).click();
		}

		catch (Exception e)

		{
			String panelsecid = driver.findElement(By.xpath("(//label[text() ='" + label + "'])/..//parent::div"))
					.getAttribute("id");
			System.out.print(panelsecid);
			int index = panelsecid.lastIndexOf(":");
			System.out.println(index);
			panelsecid = panelsecid.substring(0, index);
			System.out.print(panelsecid);
			panelsecid = panelsecid.concat(":PSEcil6");
			System.out.print(panelsecid);
			driver.findElement(By.xpath("//a[@id = '" + panelsecid + "']")).click();

		}

	}

	public void clickadd(WebDriver driver, String label) throws Exception {

		String panelsecid = driver.findElement(By.xpath("(//h2[text() ='" + label + "'])/..//parent::td"))
				.getAttribute("id");
		System.out.print(panelsecid);
		int index = panelsecid.lastIndexOf("::");
		System.out.println(index);
		panelsecid = panelsecid.substring(0, index);
		System.out.print(panelsecid);
		index = panelsecid.lastIndexOf(":");
		System.out.println(index);
		panelsecid = panelsecid.substring(0, index);
		System.out.print(panelsecid);
		panelsecid = panelsecid.concat(":PCEcil1::popEl");
		System.out.print(panelsecid);
		driver.findElement(By.xpath("//a[@id = '" + panelsecid + "']")).click();

	}

	public void clickexpandcollapse(WebDriver driver, String label) throws Exception {

		String panelsecid = driver.findElement(By.xpath("(//h2[text() ='" + label + "'])/..//parent::td"))
				.getAttribute("id");
		System.out.print(panelsecid);
		int index = panelsecid.lastIndexOf("::");
		System.out.println(index);
		panelsecid = panelsecid.substring(0, index);
		System.out.print(panelsecid);
		index = panelsecid.lastIndexOf(":");
		System.out.println(index);
		panelsecid = panelsecid.substring(0, index);
		System.out.print(panelsecid);
		panelsecid = panelsecid.concat(":PCEcil2::icon");
		System.out.print(panelsecid);
		String temp = driver.findElement(By.xpath("//img[@id = '" + panelsecid + "']")).getAttribute("title");
		System.out.print(temp);
		driver.findElement(By.xpath("//img[@id = '" + panelsecid + "']//parent::a")).click();

	}

	public void choosemanagesection(WebDriver driver, String label) throws Exception {

		driver.findElement(By.xpath("(//label[text() ='" + label + "'])//parent::a")).click();

	}

	public void clickcontinue(WebDriver driver) throws Exception {

		driver.findElement(By.xpath("(//button[@title ='Continue'])")).click();

	}

}
