package UI.tests.Flex.dff;

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

public class DFFRunTimeJournals extends GetDriverInstance {
	String id="";
	WebDriver journalsDriver = null;
	NavigationTaskFlows navTF = null;
	DFFUtils dfUtils = null;
	ApplicationLogin login = null;
	
	@Parameters({ "user_journals", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {	
		id = CommonUtils.uniqueId();
		journalsDriver = getDriverInstanceObject();
		navTF = new NavigationTaskFlows(journalsDriver);
		dfUtils = new DFFUtils(journalsDriver);
		login = new ApplicationLogin(journalsDriver);
		login.login(user, passWord, journalsDriver);
		CommonUtils.hold(5);
		
	}
	@Parameters({ "user_journals", "pwd" })
	@Test(description="GL_JE_LINES : Deploy Flex")
	public void testcase01(String user,String passWord) throws Exception{
		dfUtils.deployFlex("GL_JE_LINES",journalsDriver);
		dfUtils.deployFlex("PER_PERSONS_DFF", journalsDriver);
		login.logout(journalsDriver);
		login.login(user, passWord, journalsDriver);
		CommonUtils.waitForInvisibilityOfElement(navTF.username,journalsDriver,15);
	}
	
	@Test(description="GL_JE_LINES : Validate case sensitive")
	public void testcase02() throws Exception {
		dfUtils.navigateToJournalsPage("ABCD"+id,journalsDriver);
		dfUtils.validateTableLayOutCaseSensitive(journalsDriver);
	}
	
	@Test(description="GL_JE_LINES : Validate segment length")
	public void testcase03() throws Exception {
		dfUtils.validateTableLayOutSegmentLength(journalsDriver);
	}
	@Test(description="GL_JE_LINES : Validate ContextSwitch")
	public void testcase04() throws Exception {
		dfUtils.validateTableContextSwitch(journalsDriver);
	}	
	
	@Test(description="GL_JE_LINES : Validate segment values")
	public void testcase05() throws Exception{
		String args4[]={
		"NumberofValues","2",
		"ParentValue1","AOL",
		"ChildValue1","AOL1",
		"ChildValue11","AOL2",
		"ParentValue2","FLEX",
		"ChildValue2","FLEX1",
		"ChildValue22","FLEX2",
		"TableValue","FND_TREE_STRUCTURE",
		"SubValue","AOL"
		};
		dfUtils.validateTableSegmentValues(journalsDriver,args4);
	}
	
//	@Test(description="GL_JE_LINES : Validate all segment values")
//	public void testcase12() throws Exception{
//		String args4[]={
//		"journalName","ABCD"+id,
//		"ParentValue","FLEX",
//		"ChildValue","FLEX1",
//		"TableValue","FND_TREE_STRUCTURE",
//		"SubValue","AOL"
//		};
//		dfUtils.validateTableLayOutAllValues(args4);
//	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {		
		journalsDriver.quit();
	}

}
