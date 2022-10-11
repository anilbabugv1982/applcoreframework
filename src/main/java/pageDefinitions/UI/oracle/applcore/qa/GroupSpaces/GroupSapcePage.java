package pageDefinitions.UI.oracle.applcore.qa.GroupSpaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GroupSapcePage {
	
	public GroupSapcePage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}

	@FindBy(id="pt1:nv_itemNode_spaces_spaces")
	public static WebElement groupspace;
	
	@FindBy(xpath = "//div[@id='T:MngSpa:CommunityBrowserTable:createGroupSpaceBtn']/a")
	public  static WebElement createlink;
	
	@FindBy(xpath = "//button[@id='T:T1:newportal:it0:0:it1:0:use']")
	public  static WebElement usethis;
	
	@FindBy(xpath = "//button[text() ='Create']")
	public  static WebElement createbutton;
}
