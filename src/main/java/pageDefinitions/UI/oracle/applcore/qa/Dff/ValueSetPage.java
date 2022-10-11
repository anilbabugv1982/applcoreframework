package pageDefinitions.UI.oracle.applcore.qa.Dff;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ValueSetPage {
	
	public ValueSetPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	public Map<String, String> taskflowParamaters;
	public String vsCodeDataType;
	public String typeOfOperation;
	
	@FindBy(xpath = "//a[@id = 'pt1:nv_itemNode_tools_setup_and_maintenance']")
	public WebElement SetupAndMaintenance;

	/* ****** Xpaths for ValuseSet taskflow Page **** */
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value00::content')]")
	public WebElement vspValueSetCode;
	
	@FindBy(xpath="//button[text()='Reset']")
	public WebElement resetBtn;
	
	@FindBy(xpath = "//select[contains(@id , 'AP1:qryId1:value10::content')]")
	public WebElement vspValidationtype;
	
	@FindBy(xpath = "//select[contains(@id , 'AP1:qryId1:value20::content')]")
	public WebElement vspValueDataType;
	
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value30::content')]")
	public WebElement vspModuleType;
	
	@FindBy(xpath = "//a[contains(@id , 'AP1:qryId1:value30::lovIconId')]")
	public WebElement vspModuleTypeLov;
	
	@FindBy(xpath = "//a[contains(@id , 'AP1:qryId1:value30::dropdownPopup::popupsearch')]")
	public WebElement vspModuleTypeLovSearch;
	
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value30::_afrLovInternalQueryId:value00::content')]")
	public WebElement vspModuleTypeLovName;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1:value30::_afrLovInternalQueryId::search')]")
	public WebElement vspModuleTypeLovQuerySearch;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:qryId1:value30_afrLovInternalTableId')]/descendant::table[@_rowcount > 0]")
	public WebElement vspModuleTypeLovQueryResultAssert;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:qryId1:value30_afrLovInternalTableId')]/descendant::table[@_rowcount > 0]/descendant::tr[@_afrrk = '0']/descendant::table")
	public WebElement vspModuleTypeLovQueryResult;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1:value30::lovDialogId::ok')]")
	public WebElement vspModuleTypeLovOK;
	
	@FindBy(xpath = "//input[contains(@id , 'AP1:qryId1:value40::content')]")
	public WebElement vspDesc;
	
	@FindBy(xpath = "//img[contains(@id , 'ATp:create::icon')]")
	public WebElement InitiateObjCreation;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:AT1:_ATp:edit')]/descendant::a[@onclick]/descendant::img")
	public WebElement InitiateObjUpdation;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:AT1:_ATp:delete')]/descendant::a[@onclick]/descendant::img")
	public WebElement InitiateObjdelete;
	
	@FindBy(xpath = "//div[contains(@id , 'AT1:_ATp:ATex')]/descendant::a[@onclick]/descendant::img")
	public WebElement InitiateExportToExcel;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP1:APsv') or contains(@id , 'pt1:AP2:APsv')]")
	public WebElement VsPSaveTransaction;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP1:APscl')]")
	public WebElement VsPSaveandCloseTransaction;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP1:APc')]")
	public WebElement VsPCancelTransaction;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1::search')]")
	public WebElement InitiateObjSearch;
	
	@FindBy(xpath = "//button[contains(@id , 'AP1:qryId1::reset')]")
	public WebElement ResetObjSearch;
	
	@FindBy(xpath = "//div[contains(@id , 'AP1:AT1:_ATp:ATt1::db')]/descendant::table[@_rowcount > 0]")
	public WebElement SearchResultTable;
		
	/* ***** Xpaths for Create Value Set Page ***** */
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it11::content']")
	public WebElement VSCodeEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it6::content']")
	public WebElement VSDescriptionEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId::content']")
	public WebElement VSModuleEle;
	
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc3::drop']")
	public WebElement ValidationTypeEle;
	
	@FindBy(xpath = "//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc3::content']")
	public WebElement ValidationTypeSelectEle;
	
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc2::drop']")
	public WebElement ValueDataTypeEle;

	@FindBy(xpath = "//h2[contains(text(),'Definition')]")
	public WebElement definitionText;
	
	@FindBy(xpath = "//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc2::content']")
	public WebElement ValueDataTypeSelectEle;
	
//	@FindBy(xpath = "//label[contains(@for , 'AP2:sbc2::content')]/descendant::div")
	@FindBy(xpath="//label[@for='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:sbc2::content']/div") //id="pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:sbc2::Label0"
	public WebElement UpperCaseCBEle;
	
	@FindBy(xpath = "//label[contains(@for , 'AP2:sbc3::content')]/descendant::div")
	public WebElement ZeroFillCBEle;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP2:APsv')]")
	public WebElement CvsSaveTransaction;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP2:APscl') or contains(@id,'pt1:AP1:APscl')]")
	public WebElement CvsSaveandCloseTransaction;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP2:APc')]")
	public WebElement CvsCancelTransaction;
	
	/* ****** Xpaths for "Table" validation type definition parameters ***** */
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it20::content']")
	public WebElement FromClauseEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it13::content']")
	public WebElement ValueAttributesTableAliasEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it23::content']")
	public WebElement ValueColumnNameEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it17::content']")
	public WebElement DescriptionColumnNameEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it22::content']")
	public WebElement IDColumnNameEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it19::conten']")
	public WebElement EnabledFlagColumnNameEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it16::content']")
	public WebElement StartDateColumnNameEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it15::content']")
	public WebElement EndDateColumnNameEle;
	
	@FindBy(xpath = "//textarea[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it18::content']")
	public WebElement WHEREClauseEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it21::content']")
	public WebElement ORDERBYClauseEle;
	
	/* ****Xpaths for "Character" data type Definition***** */
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc4::drop']")
	public WebElement ValueSubTypeEle;
	
	@FindBy(xpath = "//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc4::content']")
	public WebElement ValueSubTypeSelectEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it7::content']")
	public WebElement ValueMaxLenghtEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:itMinValVc2::content']")
	public WebElement CharMinValueEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:itMaxValVc2::content']")
	public WebElement CharMaxValueEle;
	
	/* ****Xpaths for "Number" data type Definition***** */
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it12::content']")
	public WebElement PrecisionEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:it3::content']")
	public WebElement ScaleEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:itMinValNum::content']")
	public WebElement NumberMinValueEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:itMaxValNum::content']")
	public WebElement NumberMaxValuesEle;
	
	/* *****Xpaths for "Date" data type Definition***** */ 
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:idMinValDat::content']")
	public WebElement DateMinValueEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:idMaxValDat::content']")
	public WebElement DateMaxValuesEle;
	
	/* *****Xpaths for "DateTime" data type Definition***** */ 
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:idMinValTms::content']")
	public WebElement DateTimeMinValueEle;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:idMaxValTms::content']")
	public WebElement DateTimeMaxValuesEle;
	
	/* *****Xpaths for data type Definition for "Dependant/Subset Value Set"***** */
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId::lovIconId']")
	public WebElement VSCodeanchorEle;
	
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId::dropdownPopup::popupsearch']")
	public WebElement SearchPopup;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId::_afrLovInternalQueryId:value00::content']")
	public WebElement SearchableVSCode;
	
	@FindBy(xpath = "//button[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId::_afrLovInternalQueryId::search']")
	public WebElement InitiateSearch;
	
	@FindBy(xpath = "//div[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId_afrLovInternalTableId::db']/descendant::table[@_rowcount > 0]")
	public WebElement Resulttable;
	
	@FindBy(xpath = "//div[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId_afrLovInternalTableId::db']/descendant::table[@_rowcount > 0]/descendant::tr[@_afrrk = '0']/descendant::table")
	public WebElement Selectresultrow;
	
	@FindBy(xpath = "//button[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:independentValueSetCodeId::lovDialogId::ok']")
	public WebElement OKbutton;
	
	/* **** xpaths for selecting usermoduleid of valueSet **** */
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId::lovIconId']")
	public WebElement UserModLovid;

	@FindBy(xpath = "//input[contains(@id,'pt1:AP2:userModuleNameId::content')]")
	public WebElement userModLovidInput;
	
	@FindBy(xpath = "//a[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId::dropdownPopup::popupsearch']")
	public WebElement UserModSearchPopup;
	
	@FindBy(xpath = "//input[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId::_afrLovInternalQueryId:value00::content']")
	public WebElement SearchableUserModuleId;
	
	@FindBy(xpath = "//button[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId::_afrLovInternalQueryId::search']")
	public WebElement InitiateUserModSearch;
	
	@FindBy(xpath = "//div[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId_afrLovInternalTableId::db']/descendant::table[@_rowcount > 0]")
	public WebElement UserModResulttable;
	
	@FindBy(xpath = "//div[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId_afrLovInternalTableId::db']/descendant::table[@_rowcount > 0]/descendant::tr[@_afrrk = '0']/descendant::table")
	public WebElement UserModresultrowSelect;
	
	@FindBy(xpath = "//button[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:userModuleNameId::lovDialogId::ok']")
	public WebElement UserModOKbutton;
	
	/* **** xpaths for assinging datasecurity resource to valueSet **** */
	@FindBy(xpath = "//label[contains(@for , 'AP2:sbc1::content')]/descendant::div")
	public WebElement EnableDSSecurity;
	
	@FindBy(xpath = "//input[contains(@id , 'AP2:it8::content')]")
	public WebElement DSResourceName;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:AP2:cb1') and @onclick]")
	public WebElement EditSecurityEle;
	

	public enum valueDataType{
		Blank,Character,Number,Date,DateTime;
	  }
	public enum validationDataType{
		Blank,FormatOnly,Independent,Dependent,Subset,Table;
	  }
	public enum charvalueSubType{
		Blank,Text,TranslatedText,Numeric,Time,TimeSeconds;
	  }
	
	/*
	 * setValueSetValidationType() method will create validationType for the valuesetcode
	 */
/*	private void setValueSetValidationType() {
		
		try {
				switch(validationDataType.valueOf(taskflowParamaters.get("ValidationType"))) {
					case FormatOnly: 
								getValidationTypeElement(taskflowParamaters.get("ValidationType")).click();
								CommonUtils.hold(5);
								getValueDataTypeElement(taskflowParamaters.get("ValueType")).click();
								CommonUtils.hold(10);
								setValueType(taskflowParamaters.get("ValueType"));
								break;
					case Independent: 
								getValidationTypeElement(taskflowParamaters.get("ValidationType")).click();
								CommonUtils.hold(5);
								getValueDataTypeElement(taskflowParamaters.get("ValueType")).click();
								CommonUtils.hold(10);
								setValueType(taskflowParamaters.get("ValueType"));
								CommonUtils.hold(5);
								if(taskflowParamaters.containsKey("ManageValues")) {
									System.out.println("inside mv if");
									CvsSaveTransaction.click();
									System.out.println("Save btn clicked");
									if(Integer.parseInt(taskflowParamaters.get("ManageValues")) > 0) {
										System.out.println("Save btn clicked && inside if");
										CommonUtils.ExplicitWait(ManageValuestf.ManageValuesnavigate, "Click", "");
										ManageValuestf.ManageValuesnavigate.click();
										manageValues("Independent",taskflowParamaters.get("ManageValues"),taskflowParamaters.get("ValueType"),taskflowParamaters.get("ValueSubType"));
									}
								}
									
								break;
					case Dependent: 
								getValidationTypeElement(taskflowParamaters.get("ValidationType")).click();
								CommonUtils.hold(5);
								getValueDataTypeElement(taskflowParamaters.get("ValueType")).click();
								CommonUtils.hold(10);
								setValueType(taskflowParamaters.get("ValueType"));
								CommonUtils.ExplicitWait(VSCodeanchorEle, "Visible", "");
								selectIndVSCode(taskflowParamaters.get("IndValueSetCode"));
								CommonUtils.hold(5);
								if(taskflowParamaters.containsKey("ManageValues")) {
										if(Integer.parseInt(taskflowParamaters.get("ManageValues")) > 0) {
										CommonUtils.ExplicitWait(ManageValuestf.ManageValuesnavigate, "Click", "");
										ManageValuestf.ManageValuesnavigate.click();
										manageValues("Dependent",taskflowParamaters.get("ManageValues"),taskflowParamaters.get("ValueType"),taskflowParamaters.get("ValueSubType"));
									}
								}
								break;
					case Subset: 
								getValidationTypeElement(taskflowParamaters.get("ValidationType")).click();
								CommonUtils.hold(5);
								CommonUtils.ExplicitWait(VSCodeanchorEle, "Visible", "");
								selectIndVSCode(taskflowParamaters.get("IndValueSetCode"));
								break;
					case Table: 
								getValidationTypeElement(taskflowParamaters.get("ValidationType")).click();
								CommonUtils.hold(5);
								getValueDataTypeElement(taskflowParamaters.get("ValueType")).click();
								CommonUtils.hold(10);
								tableTypeDef();
								break;
				}
			
		} catch (Exception sVSVTE) {
			// TODO Auto-generated catch block
			sVSVTE.printStackTrace();
		}
		
	}//End of setValueSetValidationType()
	
	/*
	 * setValueType(String ValueDataType) method will create valuesetcode deiniftion of the choosen "value subtype" for the "validationType"
	 */
/*	void setValueType(String ValueDataType) {
		
		try {
			switch(valueDataType.valueOf(ValueDataType)) {
				case Character :
					getCharValueSubTypeElement(taskflowParamaters.get("ValueSubType")).click();
					 switch(charvalueSubType.valueOf(taskflowParamaters.get("ValueSubType"))) {
						 	case Text :
						 	case TranslatedText :
						 	case Numeric :
						 		CommonUtils.ExplicitWait(ValueMaxLenghtEle, "Visible", "");
						 		ValueMaxLenghtEle.sendKeys(taskflowParamaters.get("MaxLength"));
						 		if (taskflowParamaters.containsKey("MinVal"))
						 			CharMinValueEle.sendKeys(taskflowParamaters.get("MinVal"));
						 		if(taskflowParamaters.containsKey("MaxVal"))
						 			CharMaxValueEle.sendKeys(taskflowParamaters.get("MaxVal"));
						 		if (taskflowParamaters.containsKey("UpperCase"))
						 			CharMinValueEle.sendKeys(taskflowParamaters.get("UpperCaseCBEle"));
						 		if(taskflowParamaters.containsKey("Zerofill"))
						 			CharMaxValueEle.sendKeys(taskflowParamaters.get("ZeroFillCBEle"));
						 		break;
						 	case Time :
						 	case TimeSeconds :
						 		CommonUtils.ExplicitWait(CharMaxValueEle, "Visible", "");
						 		if (taskflowParamaters.containsKey("MinVal"))
						 			CharMinValueEle.sendKeys(taskflowParamaters.get("MinVal"));
						 		if(taskflowParamaters.containsKey("MaxVal"))
						 			CharMaxValueEle.sendKeys(taskflowParamaters.get("MaxVal"));
						 		break;
						 }
					break;
				case Number :
					CommonUtils.ExplicitWait(NumberMaxValuesEle, "Visible", "");
					if (taskflowParamaters.containsKey("Precision"))
						PrecisionEle.sendKeys(taskflowParamaters.get("Precision"));
					if (taskflowParamaters.containsKey("Scale"))
						ScaleEle.sendKeys(taskflowParamaters.get("Scale"));
					if (taskflowParamaters.containsKey("MinVal"))
						NumberMinValueEle.sendKeys(taskflowParamaters.get("MinVal"));
					if(taskflowParamaters.containsKey("MaxVal"))
						NumberMaxValuesEle.sendKeys(taskflowParamaters.get("MaxVal"));
					break;
				case Date :
					if(taskflowParamaters.containsKey("MinVal"))
						DateMinValueEle.sendKeys(taskflowParamaters.get("MinVal"));
					if(taskflowParamaters.containsKey("MaxVal"))
						DateMaxValuesEle.sendKeys(taskflowParamaters.get("MaxVal"));
					break;
				case DateTime :
					if(taskflowParamaters.containsKey("MinVal"))
						DateTimeMinValueEle.sendKeys(taskflowParamaters.get("MinVal"));
					if(taskflowParamaters.containsKey("MaxVal"))
						DateTimeMaxValuesEle.sendKeys(taskflowParamaters.get("MaxVal"));
					break;
			
			}
			
		}catch(Exception vSVTD) {
			vSVTD.printStackTrace();
			
		}
		
	}//End of setValueSetValueType()
	
	/*
	 * tableTypeDef() method to create "Table" validationtype definition for the valueset code.
	 */
/*	public void tableTypeDef() {
		try {
			CommonUtils.ExplicitWait(FromClauseEle, "Visible", "");
			FromClauseEle.sendKeys(taskflowParamaters.get("FromClause"));
			if(taskflowParamaters.containsKey("ValueAttributesTableAlias"))
				ValueAttributesTableAliasEle.sendKeys(taskflowParamaters.get("ValueAttributesTableAlias"));
			ValueColumnNameEle.sendKeys(taskflowParamaters.get("ValueColumnName"));
			if(taskflowParamaters.containsKey("DescriptionColumnName"))
				DescriptionColumnNameEle.sendKeys(taskflowParamaters.get("DescriptionColumnName"));
			if(taskflowParamaters.containsKey("IDColumnName"))
				IDColumnNameEle.sendKeys(taskflowParamaters.get("IDColumnName"));
			if(taskflowParamaters.containsKey("EnabledFlagColumnName"))
				EnabledFlagColumnNameEle.sendKeys(taskflowParamaters.get("EnabledFlagColumnName"));
			if(taskflowParamaters.containsKey("StartDateColumnName"))
				StartDateColumnNameEle.sendKeys(taskflowParamaters.get("StartDateColumnName"));
			if(taskflowParamaters.containsKey("EndDateColumnName"))
				EndDateColumnNameEle.sendKeys(taskflowParamaters.get("EndDateColumnName"));
			if(taskflowParamaters.containsKey("WHEREClause"))
				WHEREClauseEle.sendKeys(taskflowParamaters.get("WHEREClause"));
			if(taskflowParamaters.containsKey("ORDERBYClause"))
				ORDERBYClauseEle.sendKeys(taskflowParamaters.get("ORDERBYClause"));
				
		}catch(Exception tTDE) {
			tTDE.printStackTrace();
		}
		
	}//End of TableTypeDef()
	
	private void navigateToManageValueSettf() {
		try {
			CommonUtils.navigateToTask(SetupAndMaintenance);
			CommonUtils.navigateToAOLTaskFlows("Manage Value Sets");
			CommonUtils.ExplicitWait(InitiateObjCreation, "Visible", "");
		}catch (Exception nTMVE) {
			nTMVE.printStackTrace();
		}
		
	}//End of navigateToManageValueSettf()
	
	/*
	 * createValueSet(String ...valueSetParams) will create new valueset code with the user choice parameters
	 * Input -> List parameters with the data to create value set code.
	 */
/*	public void createValueSet(String ...valueSetParams) {
		try {
			System.out.println("Length of valueSetParam argument -> "+valueSetParams.length);
			taskflowParamaters = new LinkedHashMap<String, String>();
			for(int i = 0; i < valueSetParams.length; i += 2) {
				taskflowParamaters.put(valueSetParams[i],valueSetParams[i+1]);
				System.out.println("Key -> "+valueSetParams[i]+" && "+"Value -> "+valueSetParams[i+1]);
			}
			navigateToManageValueSettf();
			InitiateObjCreation.click();
			CommonUtils.ExplicitWait(CvsSaveTransaction, "Visible", "");
			VSCodeEle.click();
			CommonUtils.hold(2);
			VSCodeEle.sendKeys(taskflowParamaters.get("ValueSetCode"));
			if(taskflowParamaters.containsKey("ValueSetDescription"))
				VSDescriptionEle.sendKeys(taskflowParamaters.get("ValueSetDescription"));
			userModuleName(taskflowParamaters.get("ValueSetModule"));
			CommonUtils.hold(5);
			setValueSetValidationType();
			CommonUtils.hold(10);
			CvsSaveandCloseTransaction.click();
			CommonUtils.ExplicitWait(VsPSaveTransaction, "Visible", "");
			Assert.assertTrue(verifyValueset(taskflowParamaters.get("ValueSetCode")));
			taskflowParamaters.clear();
			
		}catch (Exception cVSE) {
			cVSE.printStackTrace();
		}
	}//End of createValueset()
	
	/* 
	 * getValueDataTypeElement(String datatype) method will return webelement of the selected "Validation data type"
	 */
/*	WebElement getValueDataTypeElement(String datatype) {
		WebElement ValDataType;
		try {
		ValueDataTypeEle.click();
		CommonUtils.hold(3);
		ValDataType = DriverInstance.getDriverInstance().findElement(By.xpath("//ul[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc2::pop']/descendant::li[@_adfiv='"+valueDataType.valueOf(datatype).ordinal()+"']"));
		}catch(Exception nseD) {
			ValueDataTypeSelectEle.click();
			CommonUtils.hold(3);
			ValDataType = DriverInstance.getDriverInstance().findElement(By.xpath("//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc2::content']/descendant::option[@value='"+valueDataType.valueOf(datatype).ordinal()+"']"));
		}
		return ValDataType;
	}//End of getValueDataTypeElement()
	
	/*
	 * getValidationTypeElement(String validationTypeval) method will return webelement of the required "value data type" of the corresponding "validation data type"
	 */
/*	WebElement getValidationTypeElement(String validationTypeval) {
		WebElement ValidationType;
		try {
			ValidationTypeEle.click();
			CommonUtils.hold(3);
			ValidationType = DriverInstance.getDriverInstance().findElement(By.xpath("//ul[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc3::pop']/descendant::li[@_adfiv='"+validationDataType.valueOf(validationTypeval).ordinal()+"']"));
		}catch(Exception nsevt) {
			ValidationTypeSelectEle.click();
			CommonUtils.hold(3);
			ValidationType = DriverInstance.getDriverInstance().findElement(By.xpath("//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc3::content']/descendant::option[@value='"+validationDataType.valueOf(validationTypeval).ordinal()+"']"));
		}
		return ValidationType;
	}//End of getValidationTypeElement()
	
	/*
	 * getCharValueSubTypeElement(String valuesubtype)  method will return webelement of the required "value sub type" of char value data type
	 */
/*	WebElement getCharValueSubTypeElement(String valuesubtype) {
		WebElement charValueSubType;
		try {
			ValueSubTypeEle.click();
			CommonUtils.hold(3);
			charValueSubType = DriverInstance.getDriverInstance().findElement(By.xpath("//ul[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc4::pop']/descendant::li[@_adfiv='"+charvalueSubType.valueOf(valuesubtype).ordinal()+"']"));
		}catch(Exception nsevst) {
			ValueSubTypeSelectEle.click();
			CommonUtils.hold(3);
			charValueSubType = DriverInstance.getDriverInstance().findElement(By.xpath("//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc4::content']/descendant::option[@value='"+charvalueSubType.valueOf(valuesubtype).ordinal()+"']"));
		}
		
		return charValueSubType;
	}//End of getValidationTypeElement()
	
	/*
	 * selectIndVSCode(String VSCode) method will select the user preferred "Independant valueset code".
	 */
/*	void selectIndVSCode(String VSCode) {
		try {
			VSCodeanchorEle.click();
			CommonUtils.ExplicitWait(SearchPopup, "Click", "");
			SearchPopup.click();
			CommonUtils.ExplicitWait(SearchableVSCode, "Visible", "");
			SearchableVSCode.sendKeys(VSCode);
			InitiateSearch.click();
			CommonUtils.ExplicitWait(Resulttable, "Visible", "");
			Selectresultrow.click();
			CommonUtils.hold(3);
			OKbutton.click();
			CommonUtils.hold(3);
			CvsSaveTransaction.click();
			CommonUtils.ExplicitWait(ManageValuestf.ManageValuesnavigate, "Click", "");
		}catch(Exception sIVSCE) {
			sIVSCE.printStackTrace();
		}
	}//End of selectIndVSCode()
	
	/*
	 * userModuleName(String UserModuleId) will select "User Module Name id" of the valuesetcode.
	 */
/*	void userModuleName(String UserModuleId) {
		try {
			UserModLovid.click();
			CommonUtils.ExplicitWait(UserModSearchPopup, "Click", "");
			UserModSearchPopup.click();
			CommonUtils.ExplicitWait(SearchableUserModuleId, "Visible", "");
			SearchableUserModuleId.sendKeys(UserModuleId);
			InitiateUserModSearch.click();
			CommonUtils.ExplicitWait(UserModResulttable, "Visible", "");
			UserModresultrowSelect.click();
			CommonUtils.hold(3);
			UserModOKbutton.click();
		}catch(Exception uMNE) {
			uMNE.printStackTrace();
		}
	}//End of userModuleName()
	
	/*
	 * manageValues() method will create values for Independant and Dependant Valueset codes
	 */
/*	void manageValues(String vsCodeType, String noOfValues, String valueType, String valueSubType) {
		try {
			int valuesCount = Integer.parseInt(noOfValues);
			CommonUtils.ExplicitWait(ManageValuestf.CreateValueEle, "Visible", "");
			System.out.println("Values count : "+valuesCount);
			for(int loop=1; loop <= valuesCount; loop++) {
				ManageValuestf.CreateValueEle.click();
				switch (valueDataType.valueOf(valueType)) {
					case Character :
						CommonUtils.ExplicitWait(ManageValuestf.CharValueEle, "Visible", "");
						ManageValuestf.CharValueEle.click();
						CommonUtils.hold(2);
						ManageValuestf.CharValueEle.sendKeys(taskflowParamaters.get("Value"+loop));
						vsCodeDataType = "Character";
							if(valueSubType.equalsIgnoreCase("TranslatedText"))
								ManageValuestf.CharTranslationTextEle.sendKeys(taskflowParamaters.get("TranslatedValue"+loop));
						break;
					case Number :
						CommonUtils.ExplicitWait(ManageValuestf.NumberValueEle, "Visible", "");
						ManageValuestf.NumberValueEle.click();
						CommonUtils.hold(2);
						ManageValuestf.NumberValueEle.sendKeys(taskflowParamaters.get("Value"+loop));
						vsCodeDataType = "Number";
						break;
					case Date :
						CommonUtils.ExplicitWait(ManageValuestf.DateValueEle, "Visible", "");
						ManageValuestf.DateValueEle.click();
						CommonUtils.hold(2);
						ManageValuestf.DateValueEle.sendKeys(taskflowParamaters.get("Value"+loop));
						vsCodeDataType = "Date";
						break;
					case DateTime :
						CommonUtils.ExplicitWait(ManageValuestf.DateTimeValueEle, "Visible", "");
						ManageValuestf.DateTimeValueEle.click();
						CommonUtils.hold(2);
						ManageValuestf.DateTimeValueEle.sendKeys(taskflowParamaters.get("Value"+loop));
						vsCodeDataType = "DateTime";
						break;
				}
				if(taskflowParamaters.containsKey("Desc"+loop))
					ManageValuestf.getDescEle(vsCodeDataType).sendKeys(taskflowParamaters.get("Desc"+loop));
				if(vsCodeType.equalsIgnoreCase("Dependent"))
					selectIndependantValue(taskflowParamaters.get("IndValue"+loop));
				if(taskflowParamaters.containsKey("EFlag"+loop) && taskflowParamaters.get("EFlag"+loop).equalsIgnoreCase("N"))
					ManageValuestf.getEnabledEle(vsCodeDataType).click();
				CommonUtils.hold(2);
				if(taskflowParamaters.containsKey("SDate"+loop))
					ManageValuestf.getStartDateEle(vsCodeDataType).sendKeys(taskflowParamaters.get("SDate"+loop));
				if(taskflowParamaters.containsKey("EDate"+loop))
					ManageValuestf.getEndDateEle(vsCodeDataType).sendKeys(taskflowParamaters.get("EDate"+loop));
				if(taskflowParamaters.containsKey("SOrder"+loop))
					ManageValuestf.getSortOrderEle(vsCodeDataType).sendKeys(taskflowParamaters.get("SOrder"+loop));
			
				ManageValuestf.SaveTransEle.click();
				CommonUtils.ExplicitWait(ManageValuestf.getCreatedValue(taskflowParamaters.get("Value"+loop)), "Visible", "");
			}
			
		ManageValuestf.SaveAndCloseTransEle.click();
		}catch(Exception mVE) {
			mVE.printStackTrace();
		}
		
	}//End of manageValues()
	
	/*
	 * selectIndependantValue(String IndependantValue) will select independant value from the list of existing values for dependant valueset code.
	 * i/p : IndependantValue - Independant value to be assign to dependant value set code.
	 */
/*	void selectIndependantValue(String IndependantValue) {
		try {
			ManageValuestf.getIndVSLovEle(vsCodeDataType).click();
			CommonUtils.ExplicitWait(ManageValuestf.CreateLovSearchEle, "Click", "");
			ManageValuestf.CreateLovSearchEle.click();
			CommonUtils.ExplicitWait(ManageValuestf.CreateIndSetValueEle, "Visible", "");
			ManageValuestf.CreateIndSetValueEle.sendKeys(IndependantValue);
			ManageValuestf.CreateInitiateIndValueSearch.click();
			CommonUtils.ExplicitWait(ManageValuestf.CreatSearchResultWait, "Visible", "");
			ManageValuestf.CreatSearchResult.click();
			CommonUtils.hold(2);
			ManageValuestf.CreateIndValSearchOK.click();
			CommonUtils.ExplicitWait(ManageValuestf.getSelectedIndValue(IndependantValue), "Visible", "");
		}catch(Exception ivE) {
			ivE.printStackTrace();
		}
	}//End of selectIndependantValue()
	
	/*
	 * searchIndependantValue(String IndependantValue) will search ValueSetcode values based on IndependantValueSet values 
	 */
/*	void searchIndependantValue(String IndependantValue) {
		try {
			ManageValuestf.SearchLovIcon.click();
			CommonUtils.ExplicitWait(ManageValuestf.LovSearchEle, "Click", "");
			ManageValuestf.LovSearchEle.click();
			CommonUtils.ExplicitWait(ManageValuestf.IndSetValueEle, "Visible", "");
			ManageValuestf.IndSetValueEle.sendKeys(IndependantValue);
			ManageValuestf.InitiateIndValueSearch.click();
			CommonUtils.ExplicitWait(ManageValuestf.SearchResultWait, "Visible", "");
			ManageValuestf.SearchResult.click();
			CommonUtils.hold(2);
			ManageValuestf.IndValSearchOK.click();
		}catch(Exception sivE) {
			sivE.printStackTrace();
		}
	}//End of searchIndependantValue()
	
	/*
	 * updateValueSet(String valueSetCode, String ...updatablefieds) method updates valueset fiels of the corresponding valueset code
	 * valueSetCode -> Valueset code to be update
	 * updatablefields -> List of valuse set fields to be update
	 */
/*	public void updateValueSet(String valueSetCode, String ...updatablefields) {
		try {
			System.out.println("Length of valueSetParam argument -> "+updatablefields.length);
			taskflowParamaters = new LinkedHashMap<String, String>();
			for(int i = 0; i < updatablefields.length; i += 2) {
				taskflowParamaters.put(updatablefields[i],updatablefields[i+1]);
				System.out.println("Key -> "+updatablefields[i]+" && "+"Value -> "+updatablefields[i+1]);
			}
			navigateToManageValueSettf();
			
		}catch(Exception uVSE) {
			uVSE.printStackTrace();
		}
	}

	/*
	 * isPresenceOfValueSet(String valuesetCode) method will check for the presence of valueset based on valueset code
	 * i/p param : valuesetCode -> Valueset code to be searched for.
	 * o/p	: true 	-> if the valueset found
	 *        false	-> of the valueset doesn't found
	 */
/*	boolean verifyValueset(String valuesetCode) {
		boolean verifiedFlag = false;
		try {
			vspValueSetCode.click();
			CommonUtils.hold(2);
			vspValueSetCode.sendKeys(valuesetCode);
			InitiateObjSearch.click();
			CommonUtils.ExplicitWait(SearchResultTable, "Visible", "");
			Assert.assertTrue(DriverInstance.getDriverInstance().findElement(By.xpath("//div[contains(@id , 'AP1:AT1:_ATp:ATt1::db')]/descendant::table[@_rowcount > 0]/descendant::td[span[text() = '"+valuesetCode+"']]")).isDisplayed());
			verifiedFlag = true;
		}catch(Exception vscE) {
			vscE.printStackTrace();
		}
	return verifiedFlag;
	}
*/	
}//End of ValueSetTaskflow class
