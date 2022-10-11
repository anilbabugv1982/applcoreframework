package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;

public class AttachCatUtils extends AttachCategoriesReferenceDataPage {
	public static String catname;
	public static String catUserName;
	public static String catDescription;
	public static String entname;
	public static String entDbResource;
	public static String entTblName;
	public static String entUserEnt;
	public AttachCatUtils(WebDriver driver) {
		super(driver);
	}
//************************************************************************************************************************************************************	
	public String[] AttachCateg(WebDriver driver) throws Exception {
		
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(AttachCreateIcon, ExplicitWaitCalls.Click.toString(),"",driver);
		AttachCreateIcon.click(); 
		Log.info("Create on Add Icon");
		CommonUtils.hold(10);
		AttachCatName.sendKeys("CAT"+CommonUtils.uniqueId());
		Log.info("Enter Category Name");
		catname=AttachCatName.getAttribute("value");
		Log.info("Getting Category name in catname");
		Log.info(catname);
		
		AttachCatUsernme.sendKeys("VAGOPU"+CommonUtils.uniqueId());
		Log.info("Enter UserName");
		catUserName=AttachCatUsernme.getAttribute("value");
		
		AttachModName.sendKeys("Oracle Middleware Extensions for Applications");
		Log.info("Enter Module Name");
		AttachDesc.sendKeys("SAMPLE DESC"+CommonUtils.uniqueId());
		Log.info("Enter Description");
		catDescription = AttachDesc.getAttribute("value");
		
		AttachCatOkBtn.click();
		Log.info("Click on ok Button");
		CommonUtils.hold(10);
		AttachCatSaveBtn.click();
		Log.info("Click on Save Icon");
		AttachEntCatAdd.click();
		Log.info("Click on Add Icon to Map Entity");
		CommonUtils.hold(10);
		CommonUtils.explicitWait(AttachEntNameMapp, ExplicitWaitCalls.Click.toString(),"",driver);
		AttachEntNameMapp.click();
		Log.info("Click on Entity Search Box");
		AttachEntNameMapp.clear();
		Log.info("Clear the Entity Search Box");
		AttachEntNameMapp.sendKeys(entname);
		Log.info("Enter the Entity Name");
		CommonUtils.hold(12);
		AttachEntSearch.click();
		Log.info("Search for the Entity");
		CommonUtils.hold(5);
		AttachEntSeltion.click();
		CommonUtils.hold(3);
		AttachEntSelectionOkBtn.click();
		CommonUtils.hold(10);
		AttachEntSaveAndCloseBtn.click();
		return new String[] {catname, catUserName, catDescription, entUserEnt};
	}
//************************************************************************************************************************************************************
public String[] attachmententity(WebDriver driver, boolean enable) throws Exception{
	
	
	AttachEntCreateIcon.click();
	Log.info("Create on Add Icon");
	CommonUtils.hold(10);
	AttachEntName.sendKeys("ENT"+CommonUtils.uniqueId());
	entname=AttachEntName.getAttribute("value");
	
	Log.info("Enter Entity Name");
	AttachEntUserEntName.sendKeys("USERENT"+CommonUtils.uniqueId());
	Log.info("Enter User Entity Name");
	entUserEnt = AttachEntUserEntName.getAttribute("value");
	
	AttachEntModName.sendKeys("Oracle Middleware Extensions for Applications");
	Log.info("Enter Module Name");
	
	AttachEntDbResource.sendKeys("VAGOPU"+CommonUtils.uniqueId());
	Log.info("Enter DB Resource");
	entDbResource = AttachEntDbResource.getAttribute("value");
			
	AttachEntTblName.sendKeys("FND_AUDIT"+CommonUtils.uniqueId());
	Log.info("Enter Table Name");
	entTblName = AttachEntTblName.getAttribute("value");
	
	if(enable) {
		AttachEntEnblSecurity.click();
		Log.info("Enabled Security for Entity");
	}
	
	CommonUtils.hold(7);
	AttachEntOkBtn.click();
	Log.info("Click on Ok Button");
	CommonUtils.hold(10);
	CommonUtils.explicitWait(AttachEntSaveAndCloseBtn, "Click", "", driver);
	AttachEntSaveAndCloseBtn.click();
	Log.info("Click on Save and Close Button");
	return new String[] {entname, entDbResource, entTblName};
	
}
//************************************************************************************************************************************************************



	
}
