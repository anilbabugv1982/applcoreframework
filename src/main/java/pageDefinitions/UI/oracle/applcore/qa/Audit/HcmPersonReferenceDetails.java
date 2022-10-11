package pageDefinitions.UI.oracle.applcore.qa.Audit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HcmPersonReferenceDetails {

	public HcmPersonReferenceDetails(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	WebDriver ele;
	
	
	@FindBy(xpath="//a[contains(@id,'FOTsr1:0:cl01Upl:UPsp1:cl01Pce:cl01Lv:1:cl01Pse:cl01Cl')]")
	public WebElement Hireanemp;
	
	@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:selectOneChoice3::lovIconId']")
	public WebElement LegalEmp;
	
	
	@FindBy(xpath="//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:0:it20::content']")
	public WebElement PersonLName;
	
	@FindBy(xpath="//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:0:pt1:SP1:NewPe1:0:pt_r1:0:r1:0:i1:1:it60::content']")
	public WebElement PersonFName;
	
	@FindBy(xpath="//button[contains(text(),'N')]")
	public WebElement NextBtn;
	
	@FindBy(xpath="//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_workforce_management_new_person:0:MAnt2:1:pt1:pt_r1:2:pt1:sP2:NewPe2:0:NewPe1:0:businessUnitId::content']")
	public WebElement BussUnit;
	
}
