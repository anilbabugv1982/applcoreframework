package UI.tests.Flex.dff;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;

public class DFFInlineSearch extends GetDriverInstance {
	
	String id;
	JavascriptExecutor js;
	String user=null;
	String passWord=null;
	WebElement clovElement=null;
	WebDriver clovDriver=null;
	NavigationTaskFlows navTF = null;
	DFFUtils dfUtils = null;
	ApplicationLogin login = null;
	
	@Parameters({ "person_user", "pwd" })
	@BeforeClass
	public void setUp(String user1, String passWord1) throws Exception {	
		
		try{			
			id = CommonUtils.uniqueId();
			clovDriver=getDriverInstanceObject();
			js=(JavascriptExecutor)clovDriver;
			user=user1;
			passWord=passWord1;
			js=(JavascriptExecutor) clovDriver;
			navTF = new NavigationTaskFlows(clovDriver);
			dfUtils = new DFFUtils(clovDriver);
			login = new ApplicationLogin(clovDriver);
			login.login(user, passWord, clovDriver);
			CommonUtils.hold(5);
//			dfUtils.navigateToHireAnEmployee("NAME" + id, clovDriver);
		}catch(Exception inlineSearch){
			System.out.println("Exception in method setUp() : "+inlineSearch.getMessage());
			inlineSearch.printStackTrace();
		}		
	}
	

	/*
	 * Below test is to validate only Independent values using inline search
	 */	
	
	@Test(description = "Client-LOV:Validate global segments Independent values by clicking inline search")
	public void testcase01() throws Exception {
		dfUtils.navigateToHireAnEmployee("Vision Corp - Canada","NAME" + id, clovDriver);
		
		try{
			CommonUtils.explicitWait(dfUtils.clovgIndCharInput, "Click", "", clovDriver);
			dfUtils.clovgIndCharInput.click();
			CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='AOL']")), "Click", "", clovDriver);
			CommonUtils.hold(3);
			clovElement=clovDriver.findElement(By.xpath("//td[text()='AOL']"));
			CommonUtils.hold(10);
			System.out.println("Waiting for inline search popup to be rendered");
			Assert.assertTrue(clovElement.isDisplayed()&&
										clovDriver.findElement(By.xpath("//td[text()='AOL two']")).isDisplayed() &&
										clovDriver.findElement(By.xpath("//td[text()='Flex one']")).isDisplayed() &&
										clovDriver.findElement(By.xpath("//td[text()='FLEX']")).isDisplayed(),
										"All values displayed on inline search for Independent value set");
			System.out.println("All values visible on clicking inline search...");
		}catch(Exception indValueExc){
			System.out.println("Error while validating inline search in global segment." + indValueExc.getMessage());
			indValueExc.printStackTrace();
		}
		
	}
	
	
	@Test(description = "Client-LOV:Validate global segments Independent values by sending partial value to inline search")
	public void testcase02() throws Exception {

		String []values={"AOL","FLEX"};

		for(int x=0;x<values.length;x++){

		 try{
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(2);
				CommonUtils.explicitWait(dfUtils.clovgIndCharInput, "Click", "", clovDriver);
				dfUtils.clovgIndCharInput.clear();
				dfUtils.clovgIndCharInput.sendKeys(values[x]);
				CommonUtils.hold(10);
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")), "Click", "",clovDriver);
					CommonUtils.hold(5);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")).isDisplayed());
					System.out.println("Value displayed : "+values[x]);

			}catch(Exception indValueExc){
				System.out.println("Error while validating inline search using value : "+values[x]+" : "+indValueExc.getMessage());
				indValueExc.printStackTrace();
			}

	}
	}

	@Test(description = "Client-LOV:Validate global segments dependent values")
	public void testcase03() throws Exception {

		String[] values = { "AOL", "FLEX" };
		String[] values1 = { "AOL1", "FLEX1" };
		String[] values2 = { "AOL2", "FLEX2" };

		for (int x = 0; x < values.length; x++) {

			try {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(2);
				dfUtils.clovgIndCharInput.clear();
				CommonUtils.hold(5);
				dfUtils.clovgIndCharInput.sendKeys(values[x]);
				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")), "Click", "", clovDriver);
				clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")).click();
				CommonUtils.hold(5);
				dfUtils.clovgDepCharInput.click();

				if (dfUtils.clovgDepCharInput.getAttribute("value") == null) {
					Assert.assertTrue(true);
					System.out.println("=======> Found NULL value");
				} else if (dfUtils.clovgDepCharInput.getAttribute("value").equals("")) {
					Assert.assertTrue(true);
					System.out.println("=======> Found Emapty value");
				}


				if(values[x].equals("AOL")){
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					CommonUtils.hold(5);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).isDisplayed() && clovDriver.findElement(By.xpath("//td[text()='"+values2[x]+"']")).isDisplayed(), "Dependent values are filtered properly");
				}else{
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					CommonUtils.hold(5);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).isDisplayed() && clovDriver.findElement(By.xpath("//td[text()='"+values2[x]+"']")).isDisplayed(), "Dependent values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).click();
					CommonUtils.hold(4);
				}

			} catch (Exception e) {
				System.out.println("Error in validating dependent lov values : " + e.getMessage());
				e.printStackTrace();
			}

		}
	}

    @Test(description = "Client-LOV:Validate sensitive segments Independent values")
	public void testcase04() throws Exception {

		String[] values = { "AOL", "FLEX" };
		String[] values1 = { "AOL1", "FLEX1" };
		String[] values2 = { "AOL2", "FLEX2" };

		String[] value = { "10", "20" };
		String[] value1 = { "10", "20" };
		String[] value2 = { "100", "200" };

		for (int x = 0; x < values.length; x++) {

			try {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(2);
				CommonUtils.explicitWait(dfUtils.personcontextDropDown, "Click", "", clovDriver);
				dfUtils.personcontextDropDown.click();
				CommonUtils.explicitWait(dfUtils.clovContextCode, "Click", "", clovDriver);
				dfUtils.clovContextCode.click();
				CommonUtils.explicitWait(dfUtils.clovSIndCharInput, "Click", "", clovDriver);
				CommonUtils.hold(5);
				dfUtils.clovSIndCharInput.clear();
				dfUtils.clovSIndCharInput.sendKeys(values[x]);
				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")), "Click", "", clovDriver);
				clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")).click();
				CommonUtils.hold(5);
				dfUtils.clovSIndNumInput.clear();
				dfUtils.clovSIndNumInput.sendKeys(value[x]);
				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[text()='"+value[x]+"']")), "Click", "", clovDriver);
				clovDriver.findElement(By.xpath("//span[text()='"+value[x]+"']")).click();
				CommonUtils.hold(5);

				if (dfUtils.clovSDepCharInput.getAttribute("value") ==null && dfUtils.clovSDepNumInput.getAttribute("value") == null) {
					Assert.assertTrue(true);
					System.out.println("=======> Found NULL value");
				} else if (dfUtils.clovSDepCharInput.getAttribute("value").equals("") && dfUtils.clovSDepNumInput.getAttribute("value").equals("")) {
					Assert.assertTrue(true);
					System.out.println("=======> Found Emapty value");
				}


				if(values[x].equals("AOL") && value[x].equals("10")){

					dfUtils.clovSDepCharInput.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).isDisplayed() && clovDriver.findElement(By.xpath("//td[text()='"+values2[x]+"']")).isDisplayed(), "Dependent values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.clovSDepNumInput.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+value1[x]+"']")), "Click", "", clovDriver);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//td[text()='"+value1[x]+"']")).isDisplayed() && clovDriver.findElement(By.xpath("//td[text()='"+value2[x]+"']")).isDisplayed(), "Dependent values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+value1[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.saveBtn.click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
					dfUtils.confirmOKBtn1.click();
					CommonUtils.hold(4);

					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//input[@value='AOL1 First AOL']")), "Visible", "", clovDriver);
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//input[@value='10 Ten']")), "Visible", "", clovDriver);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//input[@value='AOL1 First AOL']")).isDisplayed() && clovDriver.findElement(By.xpath("//input[@value='10 Ten']")).isDisplayed() );


				}else{
					dfUtils.clovSDepCharInput.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).isDisplayed() && clovDriver.findElement(By.xpath("//td[text()='"+values2[x]+"']")).isDisplayed(), "Dependent values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.clovSDepNumInput.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+value1[x]+"']")), "Click", "", clovDriver);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//td[text()='"+value1[x]+"']")).isDisplayed() && clovDriver.findElement(By.xpath("//td[text()='"+value2[x]+"']")).isDisplayed(), "Dependent values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+value1[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.saveBtn.click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
					dfUtils.confirmOKBtn1.click();
					CommonUtils.hold(4);

					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//input[@value='FLEX1 First Flex']")), "Visible", "", clovDriver);
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//input[@value='20 Twenty']")), "Visible", "", clovDriver);
					Assert.assertTrue(clovDriver.findElement(By.xpath("//input[@value='FLEX1 First Flex']")).isDisplayed() && clovDriver.findElement(By.xpath("//input[@value='20 Twenty']")).isDisplayed() );

				}

			} catch (Exception e) {
				System.out.println("Error in validating dependent lov values : " + e.getMessage());
				e.printStackTrace();
			}

		}
	}

	@Test(description = "Client-LOV:Validate BIND1 variable")
	public void testcase05() throws Exception {

			try {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(2);
				CommonUtils.explicitWait(dfUtils.personcontextDropDown, "Click", "", clovDriver);
				dfUtils.personcontextDropDown.click();
				CommonUtils.explicitWait(dfUtils.clovContextCode, "Click", "", clovDriver);
				dfUtils.bindContextCode.click();
				CommonUtils.explicitWait(dfUtils.bind1, "Click", "", clovDriver);
				CommonUtils.hold(5);
				dfUtils.bind1.click();
				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'BINDCON_CCODE')]")), "Visible", "", clovDriver);
				CommonUtils.hold(3);
				clovDriver.findElement(By.xpath("//td[contains(text(),'Car Insurance')]"));
				clovDriver.findElement(By.xpath("//td[contains(text(),'CRFL_Context_Test')]"));
				clovDriver.findElement(By.xpath("//td[contains(text(),'DFFCON_CCODE')]"));
				System.out.println("Length lof BIND1 values : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND1API')]/tr")).size());
			    Assert.assertTrue(
							clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND1API')]/tr")).size() == 8
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'Car Insurance')]")).isDisplayed()
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'CRFL_Context_Test')]")).isDisplayed()
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'DFFCON_CCODE')]")).isDisplayed());

			    clovDriver.findElement(By.xpath("//td[contains(text(),'Car Insurance')]")).click();

				dfUtils.saveBtn.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
				dfUtils.confirmOKBtn1.click();
				CommonUtils.hold(4);

				System.out.println("Selected value for BIND1 : "+dfUtils.bind1.getAttribute("value").trim());
				Assert.assertTrue(dfUtils.bind1.getAttribute("value").trim().contains("Car Insurance"));

			} catch (Exception e) {
				System.out.println("Error in validating BIND1 values : " + e.getMessage());
				e.printStackTrace();
			}

	}

	@Test(description = "Client-LOV:Validate BIND2 values")
	public void testcase06() throws Exception {

			try {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//				CommonUtils.hold(2);
//				CommonUtils.explicitWait(dfUtils.personcontextDropDown, "Click", "", clovDriver);
//				dfUtils.personcontextDropDown.click();
//				CommonUtils.explicitWait(dfUtils.clovContextCode, "Click", "", clovDriver);
//				dfUtils.bindContextCode.click();
//				CommonUtils.explicitWait(dfUtils.bind1, "Click", "", clovDriver);
//				CommonUtils.hold(5);
				dfUtils.bind2.click();
				CommonUtils.hold(3);
				clovDriver.findElement(By.xpath("//td[contains(text(),'BIND1_CODE')]"));
				System.out.println("Length of BIND2 values : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND2API')]/tr")).size());
			    Assert.assertTrue(
							clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND2API')]/tr")).size() == 5
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'BIND1_CODE')]")).isDisplayed()
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'BIND2_CODE')]")).isDisplayed()
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'BIND3_CODE')]")).isDisplayed()
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'BIND5_CODE')]")).isDisplayed());
			    clovDriver.findElement(By.xpath("//td[contains(text(),'BIND1_CODE')]")).click();
				CommonUtils.hold(2);

				dfUtils.saveBtn.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
				dfUtils.confirmOKBtn1.click();
				CommonUtils.hold(4);

				System.out.println("Selected value for BIND2 : "+dfUtils.bind2.getAttribute("value").trim());
				Assert.assertTrue(dfUtils.bind2.getAttribute("value").trim().contains("BIND1_CODE"));

			} catch (Exception e) {
				System.out.println("Error in validating BIND2 values : " + e.getMessage());
				e.printStackTrace();
			}

	}

	@Test(description = "Client-LOV:Validate BIND5 values")
	public void testcase07() throws Exception {

			try {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//				CommonUtils.hold(2);
//				CommonUtils.explicitWait(dfUtils.personcontextDropDown, "Click", "", clovDriver);
//				dfUtils.personcontextDropDown.click();
//				CommonUtils.explicitWait(dfUtils.clovContextCode, "Click", "", clovDriver);
//				dfUtils.bindContextCode.click();
//				CommonUtils.explicitWait(dfUtils.bind1, "Click", "", clovDriver);
//				CommonUtils.hold(5);
				dfUtils.bind5.click();
				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'BINDCON_CCODE')]")), "Visible", "", clovDriver);
				CommonUtils.hold(3);
				System.out.println("Length of BIND5 values : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND5API')]/tr")).size());
			    Assert.assertTrue(
							clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND5API')]/tr")).size() == 1
							&& clovDriver.findElement(By.xpath("//td[contains(text(),'BINDCON_CCODE')]")).isDisplayed());

			    clovDriver.findElement(By.xpath("//td[contains(text(),'BINDCON_CCODE')]")).click();
				CommonUtils.hold(2);

				dfUtils.saveBtn.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
				dfUtils.confirmOKBtn1.click();
				CommonUtils.hold(4);

				System.out.println("Selected value for BIND5 : "+dfUtils.bind5.getAttribute("value").trim());
				Assert.assertTrue(dfUtils.bind5.getAttribute("value").trim().contains("BINDCON_CCODE"));

			} catch (Exception e) {
				System.out.println("Error in validating BIND5 values : " + e.getMessage());
				e.printStackTrace();
			}

	}

   @Test(description = "Client-LOV:Validate BIND1/3/4 values")
	public void testcase08() throws Exception {

		String[] values = { "Car Insurance", "HDL_Test3"}; // BIND1 values
		String[] values1 = { "Car Insurance Eligible", "FBLPL" }; //BIND3 values
		String[] values2 = { "Car Insurance Policy Number", "testL" }; //BIND4 values
		String st1="";
		String st2="";
		for(int x=0;x<values.length;x++){

			try {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//				CommonUtils.hold(2);
//				CommonUtils.explicitWait(dfUtils.personcontextDropDown, "Click", "", clovDriver);
//				dfUtils.personcontextDropDown.click();
//				CommonUtils.explicitWait(dfUtils.clovContextCode, "Click", "", clovDriver);
//				dfUtils.bindContextCode.click();
//				CommonUtils.explicitWait(dfUtils.bind1, "Click", "", clovDriver);
//				CommonUtils.hold(5);
				dfUtils.bind1.clear();
				dfUtils.bind1.sendKeys(values[x]);
//				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[text()='"+values[x]+"']")), "Visible", "", clovDriver); //table[contains(@id,'DFFIteratorBIND1API')]/tr
				CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//table[contains(@id,'DFFIteratorBIND1API')]/tr")), "Click", "", clovDriver);
				clovDriver.findElement(By.xpath("//table[contains(@id,'DFFIteratorBIND1API')]/tr")).click(); // Since text value is not efficient, using id to select highlited value from drop down
				CommonUtils.hold(3);

				if (dfUtils.bind3.getAttribute("value") ==null && dfUtils.bind4.getAttribute("value") == null) {
					Assert.assertTrue(true);
					System.out.println("=======> Found NULL value");
				} else if (dfUtils.bind3.getAttribute("value").equals("") && dfUtils.bind4.getAttribute("value").equals("")) {
					Assert.assertTrue(true);
					System.out.println("=======> Found Emapty value");
				}

				if(values[x].equals("Car Insurance")){

					dfUtils.bind3.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					System.out.println(" Total element dispalyed in the BIND3 LOV : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND3API')]/tr")).size());
					Assert.assertTrue(
									clovDriver.findElement(By.xpath("//td[text()='" + values1[x] + "']")).isDisplayed()
									&& clovDriver.findElement(By.xpath("//td[text()='" + values2[x] + "']"))	.isDisplayed()
									&& clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND3API')]/tr")).size() == 3,
									"BIND3 values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.bind4.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					System.out.println(" Total element dispalyed in the BIND4 LOV : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND4API')]/tr")).size());
					Assert.assertTrue(
									clovDriver.findElement(By.xpath("//td[text()='" + values1[x] + "']")).isDisplayed()
									&& clovDriver.findElement(By.xpath("//td[text()='" + values2[x] + "']")).isDisplayed()
									&& clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND4API')]/tr")).size() == 3,
									"BIND4 values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values2[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.saveBtn.click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
					dfUtils.confirmOKBtn1.click();
					CommonUtils.hold(4);

					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
					st1=dfUtils.bind3.getAttribute("value").trim();
					st2=dfUtils.bind4.getAttribute("value").trim();
					System.out.println("Selected values for BIND3/4 are : "+ st1+" ,"+st2);
					Assert.assertTrue(st1.equals(values1[x]) && st2.equals(values2[x]));


				}else{
					dfUtils.bind3.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					System.out.println(" Total element dispalyed in the BIND3 LOV : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND3API')]/tr")).size());
					Assert.assertTrue(
									clovDriver.findElement(By.xpath("//td[text()='" + values1[x] + "']")).isDisplayed()
									&& clovDriver.findElement(By.xpath("//td[text()='" + values2[x] + "']"))	.isDisplayed()
									&& clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND3API')]/tr")).size() == 2,
									"BIND3 values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.bind4.click();
					CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[text()='"+values1[x]+"']")), "Click", "", clovDriver);
					System.out.println(" Total element dispalyed in the BIND4 LOV : "+clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND4API')]/tr")).size());
					Assert.assertTrue(
									clovDriver.findElement(By.xpath("//td[text()='" + values1[x] + "']")).isDisplayed()
									&& clovDriver.findElement(By.xpath("//td[text()='" + values2[x] + "']")).isDisplayed()
									&& clovDriver.findElements(By.xpath("//table[contains(@id,'DFFIteratorBIND4API')]/tr")).size() == 2,
									"BIND4 values are filtered properly");
					clovDriver.findElement(By.xpath("//td[text()='"+values2[x]+"']")).click();
					CommonUtils.hold(4);

					dfUtils.saveBtn.click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(dfUtils.confirmOKBtn1, "Click", "",clovDriver);
					dfUtils.confirmOKBtn1.click();
					CommonUtils.hold(4);

					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
					st1=dfUtils.bind3.getAttribute("value").trim();
					st2=dfUtils.bind4.getAttribute("value").trim();
					System.out.println("Selected values for BIND3/4 are : "+ st1+" , "+st2);
					Assert.assertTrue(st1.equals(values1[x]) && st2.equals(values2[x]));

//					dfUtils.completeFlow(clovDriver);
				}
			} catch (Exception e) {
				System.out.println("Error in validating BIND1/3/4 values : " + e.getMessage());
				e.printStackTrace();
			}
		}


	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {		
		clovDriver.quit();
	}
}

