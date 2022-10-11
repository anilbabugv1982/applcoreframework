/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

//import com.sun.org.apache.xml.internal.security.Init;

import UtilClasses.UI.ReportGenerator;
import UtilClasses.UI.CommonUtils;

/**
 * @author sdubey
 * @author vivevenk
 */
public class AppComposerMethods extends AppcomposerPage {
	
	public AppComposerMethods(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	public void createCustomObject(String ObjectName,WebDriver driver) throws Exception {
		  try {
		   CommonUtils.explicitWait(appComposerPageTitle, "Visible", "Application Composer",driver);
		   CommonUtils.hold(10);
		   customObject.click();
	       //customObjects.click();
		   System.out.println("***************** Clicked Custom Objects **********************");
	       CommonUtils.explicitWait(customaction, "Click", "",driver);
	       CommonUtils.hold(10);
	       customaction.click();
	       System.out.println("***************** Clicked Action  **********************");
	       CommonUtils.explicitWait(createCustom, "Click", "",driver);
	       CommonUtils.hold(5);
	       createCustom.click();
	       System.out.println("***************** Clicked Create  **********************");
	       CommonUtils.explicitWait(customDisplay, "Click", "",driver);
	       customDisplay.sendKeys(ObjectName);
	       CommonUtils.hold(20);
	       customOK.click();
	       //CommonUtils.explicitWait(WaittillCustomObjectCreate, "ElementVisible", "");
	       CommonUtils.hold(70);
	      // verifyCustomObject(ObjectName);
	       driver.navigate().refresh();
		  }
		  catch(Exception e) {
			  System.out.println("Exception on Create Custom Object");
			  e.printStackTrace();
		  }
	}
	
	public void verifyCustomObject(String objName,WebDriver driver) throws Exception{
		try {
		CustomObjectsExpand.click();
		CommonUtils.hold(5);
		WebElement cobj = Getcustomobject(objName,driver);
		CommonUtils.explicitWait(cobj, "Click", "",driver);
		String editcusttext = cobj.getText();
		Assert.assertEquals(objName, editcusttext, "Custom object created successfully");
		}
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	}

	public void UpdateCustomObject(String objName,String UpdatedText,WebDriver driver) throws Exception {
          try {
	      customObjects.click();
	      WebElement cobj = Getcustomobject(objName,driver);
	      CommonUtils.explicitWait(cobj, "Click", "",driver);
	      cobj.click();
	      CommonUtils.explicitWait(editCustomObject, "Click", "",driver);
	      editCustomObject.click();
	      CommonUtils.explicitWait(editDisplay, "Click", "",driver);
	      editDisplay.clear();
	      editDisplay.sendKeys(UpdatedText);
	      customOK.click();
	      CommonUtils.hold(10);
	      verifyCustomObject(UpdatedText,driver);
	      //DriverInstance.getDriverInstance().navigate().refresh();
          }
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	  }
	  
	/*public static void verifyCustomObjectUpdate(String objName, String updatedText) throws Exception{
		 WebElement cobjupdate = Getupdatedcustomobject(objName, updatedText);
	     CommonUtils.ExplicitWait(cobjupdate, "Click", "");
	     String editcustupdatetext = cobjupdate.getText();
	     Assert.assertEquals(objName+updatedText, editcustupdatetext, "Custom object updated successfully");  
	}*/

	 public void addfieldsinCustObj(String FieldType,String FieldName,String ChoicelistLookupObj,WebDriver driver) throws Exception
	 {
		 addField(FieldType,FieldName,ChoicelistLookupObj,driver);
		 //DriverInstance.getDriverInstance().navigate().refresh();
	 }

	 public void editcustobjfields(String objName,String FieldType,String FieldName,String UpdatedText,WebDriver driver) throws Exception {
		  try {
		 CustomObjectsExpand.click();
		 ExpandObject(objName,driver);
	     CommonUtils.explicitWait(field, "Click", "",driver);
		 field.click();
		 editfields(FieldType,FieldName,UpdatedText,driver);
		 driver.navigate().refresh();
		  }
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	 }

	/* public void addfieldsinStdObj() throws Exception
	  {
		  addfields();
		  DriverInstance.getDriverInstance().navigate().refresh();
		  
	  }*/
	  
	 public void editStandardObjfields(String stdObjname,String FieldType,String FieldName,String UpdatedText,WebDriver driver) throws Exception {
		  try {
		  standardobject.click();
		 /* CommonUtils.ExplicitWait(StadardObjectEle(stdObjname), "Click", "");
		  StadardObjectEle(stdObjname).click();*/
		  ExpandObject(stdObjname,driver);
		  CommonUtils.explicitWait(field, "Click", "",driver);
		  field.click();
		  editfields(FieldType,FieldName,UpdatedText,driver);
		  driver.navigate().refresh();
		  }
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	  }

	   
	public void addfieldsStandardObject(String standardObjName,String FieldType,String FieldName,String ChoicelistLookupObj,WebDriver driver) throws Exception {
		  
		  /*StandardObjectsExpand.click();
		  WebElement sObj = chooseStandardObject(standardObjName);
		  CommonUtils.ExplicitWait(sObj, "Click", "");
		  sObj.click();*/
		System.out.println("Adding Custom Fields to StandardObject..");
		try {
		  standardobject.click();
		  ExpandObject(standardObjName,driver);
		  CommonUtils.explicitWait(field, "Click", "",driver);
		  field.click();
		  CommonUtils.explicitWait(action, "Click", "",driver);
		  addField(FieldType,FieldName,ChoicelistLookupObj,driver);
		  driver.navigate().refresh();
		}
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	  }

	public void addfieldsCustomObject(String objName,String FieldType,String FieldName,String ChoicelistLookupObj,WebDriver driver) throws Exception {
		System.out.println("Adding Custom Field to a Custom Object............");
		  try {
		  CustomObjectsExpand.click();
		  ExpandObject(objName,driver);
		  CommonUtils.explicitWait(field, "Click", "",driver);
		  field.click();
		  CommonUtils.explicitWait(action, "Click", "",driver);
		  addField(FieldType,FieldName,ChoicelistLookupObj,driver);
		  driver.navigate().refresh();
		  }
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
		}
	  
	
	public void addReferenceRelation(String sourceStdObj, String targetStdObj,WebDriver driver) {
		//Launch AppComposer
		//Click Relationships
		//Click Actions
		//Click Create
		//Select Source (Account), Target (Activity)
		
		//Give Name
		//Click Add Search Field
		//Select the first one
		//Starts with "a"
		//Save and close
	}
	  public void addField(String Fieldtype,String FieldName,String ChoicelistLookupObj,WebDriver driver) throws Exception
	  {try {
		 System.out.println(Fieldtype);
		  switch(Fieldtype)
		  {
		  case "Text" : 
		  action.click();
		  CommonUtils.explicitWait(create, "Click", "",driver);
		  System.out.println("Clicked Create");

		  CommonUtils.hold(5);
		  create.click();
		  //CommonUtils.explicitWait(text, "Click", "");
		  CommonUtils.explicitWait(Choosetext, "Click", "",driver);
		  //text.click();
		  Choosetext.click();	  
		  CommonUtils.explicitWait(ok, "Click", "",driver);
		  ok.click();
		  Thread.sleep(2000);
		  CommonUtils.explicitWait(displayname, "Click", "",driver);
		  displayname.click();
		  displayname.sendKeys(FieldName);
		  name.click();
		  name.sendKeys(FieldName);
		  saveandclose.click();
		  CommonUtils.explicitWait(action, "Click", "",driver);
		  verifyCreatedField(FieldName,driver);
		  break;
		  
		  case "Number" : 
		  action.click();
		  CommonUtils.explicitWait(create, "Click", "",driver);
		  create.click();
		  CommonUtils.explicitWait(text, "Click", "",driver);
		  Number_field.click();
		  CommonUtils.explicitWait(ok, "Click", "",driver);
		  ok.click();
		  Thread.sleep(2000);
		  CommonUtils.explicitWait(displayname, "Click", "",driver);
		  displayname.click();
		  displayname.sendKeys(FieldName);
		  name.click();
		  name.sendKeys(FieldName);
		  saveandclose.click();
		  CommonUtils.explicitWait(action, "Click", "",driver);
		  verifyCreatedField(FieldName,driver);
		  break;
		  
		  case "ChoiceListFixed" : 
		  action.click();
		  CommonUtils.explicitWait(create, "Click", "",driver);
		  create.click();
		  CommonUtils.explicitWait(text, "Click", "",driver);
		  FCL_field.click();
		  CommonUtils.explicitWait(ok, "Click", "",driver);
		  ok.click();
		  Thread.sleep(2000);
		  CommonUtils.explicitWait(displayname, "Click", "",driver);
		  displayname.click();
		  displayname.sendKeys(FieldName);
		  name.click();
		  name.sendKeys(FieldName);
		  selectandsearch.click();
		  Thread.sleep(10000);
		  driver.switchTo().frame(1);
		  CommonUtils.explicitWait(LookupValue, "Click", "",driver);
		  LookupValue.click();
		  LookupValue.sendKeys(ChoicelistLookupObj);
		  CommonUtils.explicitWait(search, "Click", "",driver);
		  search.click();
		  Thread.sleep(2000);
		  CommonUtils.explicitWait(editOK, "Click", "",driver);	  
		  editOK.click();
		  Thread.sleep(2000);
		  //DriverInstance.getDriverInstance().switchTo().defaultContent();
		  CommonUtils.PageRefresh(driver);
		  CommonUtils.explicitWait(saveandclose, "Click", "",driver);
		  saveandclose.click();
		  CommonUtils.explicitWait(action, "Click", "",driver);
		  verifyCreatedField(FieldName,driver);
		  }}
		  catch(Exception e) {
			  
			  e.printStackTrace();
			
		  }
		  
		  
	  }
	  
	  public void verifyCreatedField(String fieldName,WebDriver driver) {
		  System.out.println("Verifying created Custom field");
		  try {
		  WebElement textfield = getlinktext(fieldName,driver);
		  String textfieldvalue = textfield.getText();
		  Assert.assertEquals(fieldName, textfieldvalue, "Text field created successfully");
		  System.out.println(fieldName+" has been created successfully");
		  }
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	  }
	  
	  public void editfields(String type,String FieldName,String UpdatedText,WebDriver driver) throws Exception{
		  //CommonUtils.PageRefresh();
		  try {
		  switch(type)
		  {
		  case "Text" :
			 
			 WebElement editcust = getlinktext(FieldName,driver);
			 CommonUtils.explicitWait(editcust, "Click", "",driver);
			 editcust.click();
			 Thread.sleep(2000);
			 CommonUtils.explicitWait(displayname, "Click", "",driver);
			 displayname.clear();
			 displayname.click();
			 displayname.sendKeys(UpdatedText);
			 editSave.click();
			 verifyupdatedField(UpdatedText,driver);
			 break;
		  case "Number" :
		     WebElement editnumcust = getlinknumber(FieldName,driver);
			 CommonUtils.explicitWait(editnumcust, "Click", "",driver);
			 editnumcust.click();
			 Thread.sleep(2000);
			 CommonUtils.explicitWait(displayname, "Click", "",driver);
			 displayname.clear();
			 displayname.click();
			 displayname.sendKeys(UpdatedText);
			 editSave.click();
			 verifyupdatedField(UpdatedText,driver);
			  break;
		  case "choicelist" :	 
			 WebElement editchoicecust = getlinkchoicelist(FieldName,driver);
			 CommonUtils.explicitWait(editchoicecust, "Click", "",driver);
			 editchoicecust.click();
			 Thread.sleep(2000);
			 CommonUtils.explicitWait(displayname, "Click", "",driver);
			 displayname.clear();
			 displayname.click();
			 displayname.sendKeys(UpdatedText);
			 editSave.click();
			 CommonUtils.explicitWait(action, "Click", "",driver);
			 verifyupdatedField(UpdatedText,driver);
			 break;
		  case "standard" :	 
			  	 standard.click();	
			  	CommonUtils.explicitWait(adressline, "Click", "",driver);
			  	 adressline.click();
				 Thread.sleep(2000);
				 displayname.clear();
				 displayname.click();
				 displayname.sendKeys("Address Line 1_Update");
				 editSave.click();
				 CommonUtils.explicitWait(action, "Click", "",driver);
				 WebElement standardfield = standardupdate(driver);
				 String standardfieldvalue = standardfield.getText();
				 Assert.assertEquals(choicelistname+"_Update", standardfieldvalue, "standard field updated successfully");
		  }}
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	  }
	  
	  public void verifyupdatedField(String Updatedfield,WebDriver driver) {
		  try {
		  WebElement choicelistfield = getFieldupdate(Updatedfield,driver);
			 String choicelistfieldvalue = choicelistfield.getText();
			 Assert.assertEquals(Updatedfield, choicelistfieldvalue, "choicelist field updated successfully");
		  }
		  catch(Exception e) {
			  
			  e.printStackTrace();
		  }
	  }

}//End Of AppComposerMethods class
