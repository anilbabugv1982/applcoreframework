package UI.tests.Flex.dff;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.ApplicationLogin;

public class DFFRunTimePersons extends GetDriverInstance {
	String id = "";
	JavascriptExecutor js;
	WebDriver dffDriver = null;
	NavigationTaskFlows navTF = null;
	DFFUtils dfUtils = null;
	ApplicationLogin login = null;

	@Parameters({ "person_user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		id = CommonUtils.uniqueId();
		dffDriver = getDriverInstanceObject();
		navTF = new NavigationTaskFlows(dffDriver);
		dfUtils = new DFFUtils(dffDriver);
		login = new ApplicationLogin(dffDriver);
		js = (JavascriptExecutor) dffDriver;
		login.login(user, passWord, dffDriver);
		CommonUtils.waitForInvisibilityOfElement(navTF.username,dffDriver,15);
	}

	@Test(description = "PER_PERSONS_DFF : Deploy Flex")
	public void testcase01() throws Exception {
		dfUtils.deployFlex("PER_PERSONS_DFF", dffDriver);
		dffDriver.get(GetDriverInstance.EnvironmentName);
		CommonUtils.hold(4);
	}

	@Test(description = "dff:Validate Character global segment -> Length")
	public void testcase02() throws Exception {
		dfUtils.navigateToHireAnEmployee("Vision Corp - Canada","NAME" + id, dffDriver);
		String args[] = { "SegmentType", "Global", "SegmentDataType", "Character", "ValidationType", "Length", "Value",
				"MORECHARCTERTHANALLOWED IN THE FIELD" };
		dfUtils.validateTableLayoutGlobalSegment(dffDriver, args);

	}

	@Test(description = "dff:Validate Character global segment -> Case sensitive")
	public void testcase03() throws Exception {
		String args1[] = { "SegmentType", "Global", "SegmentDataType", "Character", "ValidationType", "CaseSensitive",
				"Value", "abcd" };
		dfUtils.validateTableLayoutGlobalSegment(dffDriver, args1);
	}

	@Test(description = "dff: Validate Number MinValue")
	public void testcase04() throws Exception {
		String args2[] = { "SegmentType", "Global", "SegmentDataType", "Number", "ValidationType", "MinValue", "Value",
				"0" };
		dfUtils.validateTableLayoutGlobalSegment(dffDriver, args2);
	}

	@Test(description = "dff: Validate Number MaxValue")
	public void testcase05() throws Exception {
		String args3[] = { "SegmentType", "Global", "SegmentDataType", "Number", "ValidationType", "MaxValue", "Value",
				"10" };
		dfUtils.validateTableLayoutGlobalSegment(dffDriver, args3);
	}

	@Test(description = "Change context segment")
	public void testcase06() throws Exception {
		dfUtils.validateContextSwitchInFormLayout(dffDriver);
	}

	@Test(description = "Validate segment values")
	public void testcase07() throws Exception {
		String args4[] = { "NumberofValues", "2", "ParentValue1", "AOL", "ChildValue1", "AOL1", "ChildValue11", "AOL2",
				"ParentValue2", "FLEX", "ChildValue2", "FLEX1", "ChildValue22", "FLEX2" };

		dfUtils.validateCharSegmentValues(dffDriver, args4);
	}

	@Test(description = "Validate all segment values")
	public void testcase08() throws Exception {
		String args4[] = { "CharGlobalValue", "RELEASE", "NumGlobalValue", "5", "ParentValue1", "AOL", "ChildValue1",
				"AOL1", "ParentValue2", "10", "ChildValue2", "100", "TableValue", "FND_TREE_STRUCTURE", "SubValue",
				"AOL",

		};

		dfUtils.validateValidValues(dffDriver, args4);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		dffDriver.quit();
	}

}
