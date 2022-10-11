package pageDefinitions.UI.oracle.applcore.qa.Redwood;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLang;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PageTemplateFusePlus;


public class RedwoodUtil extends RedwoodPage {
	
    private WebDriver driver= null;
    
	public RedwoodUtil(WebDriver driver) {
		super(driver);
		this.driver = driver;
		// TODO Auto-generated constructor stub
	}
	
	public void checkHeader(WebDriver driver, String label, int labelindex) throws Exception {

		

}
	public void navigateToInvoice() throws Exception {
		 CommonUtils.explicitWait(navigatorButton, "Click", "", driver);
         navigatorButton.click();
         RedwoodPage.expandPayablesButton.click();
         RedwoodPage.invoices.click();
         	
	}
	public void checkCommandLinkColour(WebElement elem) throws Exception {
      navigatorButton.click();
      String c = Color.fromString(elem.getCssValue("color")).asHex();
     assertEquals(c,"#00688c");

	}
	public void navigateToReceivable() throws Exception {
		CommonUtils.explicitWait(navigatorButton, "Click", "", driver);
        navigatorButton.click();
        CommonUtils.explicitWait(expandReceivablesButton, "Click", "", driver);
        expandReceivablesButton.click();
        RedwoodPage.accountsReceivable.click();
	}
	public void checkVerBarColour() throws Exception {
		 CommonUtils.explicitWait(VerticalBar, "Click", "", driver);
		String c =  Color.fromString(VerticalBar.getCssValue("background-color")).asHex();
    	assertEquals(c.toUpperCase(),"#E3E1DE");
		//Actions actions = new Actions(driver);
		//actions.moveToElement(VerticalBar).perform();
		//String c2 =  Color.fromString(VerticalBar.getCssValue("background-color")).asHex();
//		VerticalBar.click();
//		String c3 =  Color.fromString(VerticalBar.getCssValue("background-color")).asHex();
//		assertEquals(c3.toUpperCase(),"#5F7D4F");
	}
	
	public void checkHorBarColour() throws Exception {
		 CommonUtils.explicitWait(horizontalBar, "Click", "", driver);
		String c =  Color.fromString(horizontalBar.getCssValue("background-color")).asHex();
		assertEquals(c.toUpperCase(),"#E3E1DE");
		//Actions actions = new Actions(driver);
		//actions.moveToElement(VerticalBar).perform();
		//String c2 =  Color.fromString(VerticalBar.getCssValue("background-color")).asHex();
//		horizontalBar.click();
//		String c3 =  Color.fromString(horizontalBar.getCssValue("background-color")).asHex();
//		assertEquals(c3.toUpperCase(),"#5F7D4F");
		}
	
	public void navigateToPersonManagement() throws Exception {
		CommonUtils.explicitWait(navigatorButton, "Click", "", driver);
        navigatorButton.click();
        CommonUtils.explicitWait(expandMyClientGroup, "Click", "", driver);
        expandMyClientGroup.click();
        RedwoodPage.personManagement.click();
	}
	public void checkDynamicTabBarColour() throws Exception {
		 CommonUtils.explicitWait(dynamicTab, "Click", "", driver);
		String c =  Color.fromString(dynamicTab.getCssValue("border-bottom-color")).asHex();
		assertEquals(c.toUpperCase(),"#5F7D4F");
	}
	
	public void checkRedwoodHeaderFont() throws Exception {
		 CommonUtils.explicitWait(headerFont, "Click", "", driver);
		String c =  headerFont.getCssValue("font-family");
		assertTrue(c.contains("Georgia"));
	}
	public void checkRedwoodHeaderLinkColor() throws Exception {
		 CommonUtils.explicitWait(infoTile, "Click", "", driver);
		infoTile.click();
		CommonUtils.explicitWait(invoiceNumber, "Click", "", driver);
		invoiceNumber.click();
		CommonUtils.explicitWait(headerLink, "Click", "", driver);
		String c =  headerLink.getCssValue("color");
		assertEquals(c.toUpperCase(),"#D5B364");
		
		
	}
	

	

}
