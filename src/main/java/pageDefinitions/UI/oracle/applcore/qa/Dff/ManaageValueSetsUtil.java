package pageDefinitions.UI.oracle.applcore.qa.Dff;

import java.util.LinkedHashMap;

import org.openqa.selenium.*;

import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ValueSetPage.charvalueSubType;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ValueSetPage.validationDataType;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ValueSetPage.valueDataType;

public class ManaageValueSetsUtil extends ManageValuesPage{

	private ValueSetPage valueSetPage= null;
	private NavigationTaskFlows navPage=null;
	private JavascriptExecutor js=null;
	private NavigationTaskFlows taskFlowsNavigation=null;
	private DFFUtils dffUtils=null;

	public ManaageValueSetsUtil(WebDriver driver) {
		super(driver);
		valueSetPage= new ValueSetPage(driver);
		navPage= new NavigationTaskFlows(driver);
		js = (JavascriptExecutor) driver;
		taskFlowsNavigation= new NavigationTaskFlows(driver);
		dffUtils= new DFFUtils(driver);
//		navigateToManageValueSettf(driver);
	}


	/*
	 * setValueSetValidationType() method will create validationType for the
	 * valuesetcode
	 */


	private void setValueSetValidationType(WebDriver driver) {

		try {
			switch (validationDataType.valueOf(valueSetPage.taskflowParamaters.get("ValidationType"))) {
				case FormatOnly:
					getValidationTypeElement(valueSetPage.taskflowParamaters.get("ValidationType"),driver).click();
//				CommonUtils.waitForInvisibilityOfElement(getValidationTypeElement(valueSetPage.taskflowParamaters.get("ValidationType"),driver),driver,4);
					CommonUtils.waitforElementtoVisible(8,valueSetPage.definitionText,driver);
//				CommonUtils.hold(5);
					getValueDataTypeElement(valueSetPage.taskflowParamaters.get("ValueType"),driver).click();
//				CommonUtils.waitForInvisibilityOfElement(getValueDataTypeElement(valueSetPage.taskflowParamaters.get("ValueType"),driver),driver,4);
					CommonUtils.hold(10);
					setValueType(valueSetPage.taskflowParamaters.get("ValueType"),driver);
					break;
				case Independent:
					getValidationTypeElement(valueSetPage.taskflowParamaters.get("ValidationType"),driver).click();
					CommonUtils.hold(5);
					getValueDataTypeElement(valueSetPage.taskflowParamaters.get("ValueType"),driver).click();
					CommonUtils.hold(10);
					setValueType(valueSetPage.taskflowParamaters.get("ValueType"),driver);
					CommonUtils.hold(5);
					if (valueSetPage.taskflowParamaters.containsKey("ManageValues")) {
						System.out.println("inside mv if");
						valueSetPage.CvsSaveTransaction.click();
						CommonUtils.waitForInvisibilityOfElement(navPage.username,driver,6);
						System.out.println("Save btn clicked");
						if (Integer.parseInt(valueSetPage.taskflowParamaters.get("ManageValues")) > 0) {
							System.out.println("Save btn clicked && inside if");
							CommonUtils.explicitWait(ManageValuesnavigate, "Click", "",driver);
							ManageValuesnavigate.click();
							CommonUtils.waitForInvisibilityOfElement(ManageValuesnavigate,driver,6);
							manageValues("Independent", valueSetPage.taskflowParamaters.get("ManageValues"),
									valueSetPage.taskflowParamaters.get("ValueType"),
									valueSetPage.taskflowParamaters.get("ValueSubType"),driver);
						}
					}

					break;
				case Dependent:
					getValidationTypeElement(valueSetPage.taskflowParamaters.get("ValidationType"),driver).click();
					CommonUtils.hold(5);
					getValueDataTypeElement(valueSetPage.taskflowParamaters.get("ValueType"),driver).click();
					CommonUtils.hold(10);
					setValueType(valueSetPage.taskflowParamaters.get("ValueType"),driver);
					CommonUtils.explicitWait(valueSetPage.VSCodeanchorEle, "Visible", "",driver);
					selectIndVSCode(valueSetPage.taskflowParamaters.get("IndValueSetCode"),driver);
					CommonUtils.hold(5);
					if (valueSetPage.taskflowParamaters.containsKey("ManageValues")) {
						if (Integer.parseInt(valueSetPage.taskflowParamaters.get("ManageValues")) > 0) {
//						CommonUtils.explicitWait(ManageValuesnavigate, "Click", "",driver);
							CommonUtils.waitforElementtoClick(5,ManageValuesnavigate,driver);
							ManageValuesnavigate.click();
							CommonUtils.waitForInvisibilityOfElement(ManageValuesnavigate,driver,6);
							manageValues("Dependent", valueSetPage.taskflowParamaters.get("ManageValues"),
									valueSetPage.taskflowParamaters.get("ValueType"),
									valueSetPage.taskflowParamaters.get("ValueSubType"),driver);
						}
					}
					break;
				case Subset:
					getValidationTypeElement(valueSetPage.taskflowParamaters.get("ValidationType"),driver).click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(valueSetPage.VSCodeanchorEle, "Visible", "",driver);
					selectIndVSCode(valueSetPage.taskflowParamaters.get("IndValueSetCode"),driver);
					createSubsetValueSetValues(driver);
					break;
				case Table:
					getValidationTypeElement(valueSetPage.taskflowParamaters.get("ValidationType"),driver).click();
					CommonUtils.hold(5);
					getValueDataTypeElement(valueSetPage.taskflowParamaters.get("ValueType"),driver).click();
					CommonUtils.hold(10);
					tableTypeDef(driver);
					break;
				default:
					break;
			}

		} catch (Exception sVSVTE) {
			// TODO Auto-generated catch block
			sVSVTE.printStackTrace();
		}

	}// End of setValueSetValidationType()


	public void createSubsetValueSetValues(WebDriver driver) throws Exception {
		System.out.println("Inside Value creation");
		CommonUtils.explicitWait(ManageValuesnavigate, "Click", "",driver);
		ManageValuesnavigate.click();
		CommonUtils.hold(5);

		int count = Integer.parseInt(valueSetPage.taskflowParamaters.get("ManageValues"));
		if (Integer.parseInt(valueSetPage.taskflowParamaters.get("ManageValues")) > 0) {

			for (int loop = 1; loop <= count; loop++) {
				createBtn.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(valueSearchField, CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
				valueSearchField.clear();
				valueSearchField.sendKeys(valueSetPage.taskflowParamaters.get("Value" + loop));
				searchBtn1.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='"+ valueSetPage.taskflowParamaters.get("Value" + loop) + "']")),
						CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
				driver.findElement(By.xpath("//span[text()='" + valueSetPage.taskflowParamaters.get("Value" + loop) + "']")).click();
				CommonUtils.hold(5);
				okBtn.click();
				CommonUtils.hold(5);
				System.out.println("Before clicking Btn");
				CommonUtils.explicitWait(resetBtn,CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
				CommonUtils.explicitWait(saveBtn,CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
				saveBtn.click();
				searchBtn.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(saveBtn,CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
				CommonUtils.explicitWait(driver
								.findElement(By.xpath("//span[text()='"
										+ valueSetPage.taskflowParamaters.get("Value" + loop) + "']")),
						CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
			}

		}

		saveAndCloseBtn.click();
		CommonUtils.hold(3);
		CommonUtils.explicitWait(saveAndCloseBtn,CommonUtils.ExplicitWaitCalls.Click.toString(), "",driver);
//			ManageValuestf.saveAndCloseBtn.click();
//			CommonUtils.hold(3);

	}

	/*
	 * setValueType(String ValueDataType) method will create valuesetcode
	 * deiniftion of the choosen "value subtype" for the "validationType"
	 */
	void setValueType(String ValueDataType,WebDriver driver) {

		try {
			switch (valueDataType.valueOf(ValueDataType)) {
				case Character:
					if (valueSetPage.typeOfOperation.equalsIgnoreCase("Create")) {
						getCharValueSubTypeElement(valueSetPage.taskflowParamaters.get("ValueSubType"),driver).click();
						switch (charvalueSubType.valueOf(valueSetPage.taskflowParamaters.get("ValueSubType"))) {
							case Text:
							case TranslatedText:
							case Numeric:
								CommonUtils.waitForInvisibilityOfElement(navPage.username,driver,4);
								CommonUtils.waitforElementtoVisible(5,valueSetPage.ValueMaxLenghtEle,driver);
//						CommonUtils.explicitWait(valueSetPage.ValueMaxLenghtEle, "Visible", "",driver);
								valueSetPage.ValueMaxLenghtEle.sendKeys(valueSetPage.taskflowParamaters.get("MaxLength"));
								if (valueSetPage.taskflowParamaters.containsKey("MinVal"))
									valueSetPage.CharMinValueEle
											.sendKeys(valueSetPage.taskflowParamaters.get("MinVal"));
								if (valueSetPage.taskflowParamaters.containsKey("MaxVal"))
									valueSetPage.CharMaxValueEle
											.sendKeys(valueSetPage.taskflowParamaters.get("MaxVal"));
								if (valueSetPage.taskflowParamaters.containsKey("UpperCase")) {
									JavascriptExecutor executor = (JavascriptExecutor)driver;
									executor.executeScript("arguments[0].click();", valueSetPage.UpperCaseCBEle);
									System.out.println("Clicked Check box");
								}
								if (valueSetPage.taskflowParamaters.containsKey("Zerofill"))
									valueSetPage.CharMaxValueEle
											.sendKeys(valueSetPage.taskflowParamaters.get("ZeroFillCBEle"));
								break;
							case Time:
							case TimeSeconds:
								CommonUtils.explicitWait(valueSetPage.CharMaxValueEle, "Visible", "",driver);
								if (valueSetPage.taskflowParamaters.containsKey("MinVal"))
									valueSetPage.CharMinValueEle
											.sendKeys(valueSetPage.taskflowParamaters.get("MinVal"));
								if (valueSetPage.taskflowParamaters.containsKey("MaxVal"))
									valueSetPage.CharMaxValueEle
											.sendKeys(valueSetPage.taskflowParamaters.get("MaxVal"));
								break;
							default:
								break;
						}
					} else if (valueSetPage.typeOfOperation.equalsIgnoreCase("Update")) {
						valueSetPage.ValueMaxLenghtEle.sendKeys(valueSetPage.taskflowParamaters.get("MaxLength"));
						if (valueSetPage.taskflowParamaters.containsKey("MinVal"))
							valueSetPage.CharMinValueEle.sendKeys(valueSetPage.taskflowParamaters.get("MinVal"));
						if (valueSetPage.taskflowParamaters.containsKey("MaxVal"))
							valueSetPage.CharMaxValueEle.sendKeys(valueSetPage.taskflowParamaters.get("MaxVal"));
						if (valueSetPage.taskflowParamaters.containsKey("UpperCase"))
							valueSetPage.CharMinValueEle
									.sendKeys(valueSetPage.taskflowParamaters.get("UpperCaseCBEle"));
						if (valueSetPage.taskflowParamaters.containsKey("Zerofill"))
							valueSetPage.CharMaxValueEle
									.sendKeys(valueSetPage.taskflowParamaters.get("ZeroFillCBEle"));
					}

					break;
				case Number:
					CommonUtils.explicitWait(valueSetPage.NumberMaxValuesEle, "Visible", "",driver);
					if (valueSetPage.taskflowParamaters.containsKey("Precision"))
						valueSetPage.PrecisionEle.sendKeys(valueSetPage.taskflowParamaters.get("Precision"));
					if (valueSetPage.taskflowParamaters.containsKey("Scale"))
						valueSetPage.ScaleEle.sendKeys(valueSetPage.taskflowParamaters.get("Scale"));
					if (valueSetPage.taskflowParamaters.containsKey("MinVal"))
						valueSetPage.NumberMinValueEle.sendKeys(valueSetPage.taskflowParamaters.get("MinVal"));
					if (valueSetPage.taskflowParamaters.containsKey("MaxVal"))
						valueSetPage.NumberMaxValuesEle.sendKeys(valueSetPage.taskflowParamaters.get("MaxVal"));
					break;
				case Date:
					if (valueSetPage.taskflowParamaters.containsKey("MinVal"))
						valueSetPage.DateMinValueEle.sendKeys(valueSetPage.taskflowParamaters.get("MinVal"));
					if (valueSetPage.taskflowParamaters.containsKey("MaxVal"))
						valueSetPage.DateMaxValuesEle.sendKeys(valueSetPage.taskflowParamaters.get("MaxVal"));
					break;
				case DateTime:
					if (valueSetPage.taskflowParamaters.containsKey("MinVal"))
						valueSetPage.DateTimeMinValueEle.sendKeys(valueSetPage.taskflowParamaters.get("MinVal"));
					if (valueSetPage.taskflowParamaters.containsKey("MaxVal"))
						valueSetPage.DateTimeMaxValuesEle.sendKeys(valueSetPage.taskflowParamaters.get("MaxVal"));
					break;
				default:
					break;

			}

		} catch (Exception vSVTD) {
			vSVTD.printStackTrace();

		}

	}// End of setValueSetValueType()

	/*
	 * tableTypeDef() method to create "Table" validationtype definition for the
	 * valueset code.
	 */
	public void tableTypeDef(WebDriver driver) {
		try {
			CommonUtils.explicitWait(valueSetPage.FromClauseEle, "Visible", "",driver);
			valueSetPage.FromClauseEle.sendKeys(valueSetPage.taskflowParamaters.get("FromClause"));
			if (valueSetPage.taskflowParamaters.containsKey("ValueAttributesTableAlias"))
				valueSetPage.ValueAttributesTableAliasEle
						.sendKeys(valueSetPage.taskflowParamaters.get("ValueAttributesTableAlias"));
			valueSetPage.ValueColumnNameEle.sendKeys(valueSetPage.taskflowParamaters.get("ValueColumnName"));
			if (valueSetPage.taskflowParamaters.containsKey("DescriptionColumnName"))
				valueSetPage.DescriptionColumnNameEle
						.sendKeys(valueSetPage.taskflowParamaters.get("DescriptionColumnName"));
			if (valueSetPage.taskflowParamaters.containsKey("IDColumnName"))
				valueSetPage.IDColumnNameEle.sendKeys(valueSetPage.taskflowParamaters.get("IDColumnName"));
			if (valueSetPage.taskflowParamaters.containsKey("EnabledFlagColumnName"))
				valueSetPage.EnabledFlagColumnNameEle
						.sendKeys(valueSetPage.taskflowParamaters.get("EnabledFlagColumnName"));
			if (valueSetPage.taskflowParamaters.containsKey("StartDateColumnName"))
				valueSetPage.StartDateColumnNameEle
						.sendKeys(valueSetPage.taskflowParamaters.get("StartDateColumnName"));
			if (valueSetPage.taskflowParamaters.containsKey("EndDateColumnName"))
				valueSetPage.EndDateColumnNameEle
						.sendKeys(valueSetPage.taskflowParamaters.get("EndDateColumnName"));
			if (valueSetPage.taskflowParamaters.containsKey("WHEREClause"))
				valueSetPage.WHEREClauseEle.sendKeys(valueSetPage.taskflowParamaters.get("WHEREClause"));
			if (valueSetPage.taskflowParamaters.containsKey("ORDERBYClause"))
				valueSetPage.ORDERBYClauseEle.sendKeys(valueSetPage.taskflowParamaters.get("ORDERBYClause"));

		} catch (Exception tTDE) {
			tTDE.printStackTrace();
		}

	}// End of TableTypeDef()

	public void navigateToManageValueSettf(WebDriver driver) throws Exception {
		driver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		CommonUtils.waitForInvisibilityOfElement(navPage.username,driver,20);
		CommonUtils.waitforElementtoClick(10,navPage.panelDrawer,driver);
		navPage.navigateToAOLTaskFlows("Manage Value Sets", driver);
		CommonUtils.waitForInvisibilityOfElement(navPage.panelDrawer,driver,20);
		CommonUtils.waitforElementtoClick(10,valueSetPage.InitiateObjCreation,driver);
	}// End of navigateToManageValueSettf()

	/*
	 * createValueSet(String ..valueSetParams) will create new valueset code
	 * with the user choice parameters Input -> List parameters with the data to
	 * create value set code.
	 */
	public void createValueSet(WebDriver driver,String... valueSetParams) {
		try {
			System.out.println("Length of valueSetParam argument -> " + valueSetParams.length);
			valueSetPage.typeOfOperation = "Create";
			valueSetPage.taskflowParamaters = new LinkedHashMap<String, String>();
			for (int i = 0; i < valueSetParams.length; i += 2) {
				valueSetPage.taskflowParamaters.put(valueSetParams[i], valueSetParams[i + 1]);
				System.out.println("Key -> " + valueSetParams[i] + " && " + "Value -> " + valueSetParams[i + 1]);
			}
			navigateToManageValueSettf(driver);
			valueSetPage.InitiateObjCreation.click();
			CommonUtils.waitForInvisibilityOfElement(valueSetPage.InitiateObjCreation,driver,15);
			CommonUtils.waitforElementtoClick(6,valueSetPage.CvsSaveTransaction,driver);
//			CommonUtils.explicitWait(valueSetPage.CvsSaveTransaction, "Visible", "",driver);
			valueSetPage.VSCodeEle.click();
			CommonUtils.hold(2);
			valueSetPage.VSCodeEle.sendKeys(valueSetPage.taskflowParamaters.get("ValueSetCode"));
			if (valueSetPage.taskflowParamaters.containsKey("ValueSetDescription"))
				valueSetPage.VSDescriptionEle
						.sendKeys(valueSetPage.taskflowParamaters.get("ValueSetDescription"));
//			userModuleName(valueSetPage.taskflowParamaters.get("ValueSetModule"),driver);
			valueSetPage.userModLovidInput.clear();
			valueSetPage.userModLovidInput.sendKeys(valueSetPage.taskflowParamaters.get("ValueSetModule"));
			valueSetPage.userModLovidInput.sendKeys(Keys.TAB);
			CommonUtils.hold(2);
			setValueSetValidationType(driver);
			CommonUtils.hold(5);
			valueSetPage.CvsSaveandCloseTransaction.click();
			CommonUtils.hold(5);
			valueSetPage.CvsSaveandCloseTransaction.click();
			CommonUtils.waitForInvisibilityOfElement(valueSetPage.CvsSaveandCloseTransaction,driver,4);
			CommonUtils.waitforElementtoVisible(2,valueSetPage.VsPSaveTransaction,driver);
//			CommonUtils.explicitWait(valueSetPage.VsPSaveTransaction, "Visible", "",driver);
			Assert.assertTrue(searchValueSet(valueSetPage.taskflowParamaters.get("ValueSetCode"),driver));
			valueSetPage.taskflowParamaters.clear();

		} catch (Exception cVSE) {
			cVSE.printStackTrace();
		}
	}// End of createValueset()

	/*
	 * getValueDataTypeElement(String datatype) method will return webelement of
	 * the selected "Validation data type"
	 */
	WebElement getValueDataTypeElement(String datatype,WebDriver driver) {
		WebElement ValDataType;
		try {
			valueSetPage.ValueDataTypeSelectEle.click();
			CommonUtils.hold(3);
			ValDataType = driver
					.findElement(By
							.xpath("//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc2::content']/descendant::option[@value='"
									+ valueDataType.valueOf(datatype).ordinal() + "']"));
		} catch (Exception nseD) {

			valueSetPage.ValueDataTypeEle.click();
			CommonUtils.hold(3);
			ValDataType = driver
					.findElement(By
							.xpath("//ul[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc2::pop']/descendant::li[@_adfiv='"
									+ valueDataType.valueOf(datatype).ordinal() + "']"));
		}
		return ValDataType;
	}// End of getValueDataTypeElement()

	/*
	 * getValidationTypeElement(String validationTypeval) method will return
	 * webelement of the required "value data type" of the corresponding
	 * "validation data type"
	 */
	WebElement getValidationTypeElement(String validationTypeval,WebDriver driver) {
		WebElement ValidationType;
		try {

			valueSetPage.ValidationTypeSelectEle.click();
			CommonUtils.hold(3);
			ValidationType = driver
					.findElement(By
							.xpath("//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc3::content']/descendant::option[@value='"
									+ validationDataType.valueOf(validationTypeval).ordinal() + "']"));

		} catch (Exception nsevt) {
			valueSetPage.ValidationTypeEle.click();
			CommonUtils.hold(3);
			ValidationType = driver
					.findElement(By
							.xpath("//ul[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc3::pop']/descendant::li[@_adfiv='"
									+ validationDataType.valueOf(validationTypeval).ordinal() + "']"));
		}
		return ValidationType;
	}// End of getValidationTypeElement()

	/*
	 * getCharValueSubTypeElement(String valuesubtype) method will return
	 * webelement of the required "value sub type" of char value data type
	 */
	WebElement getCharValueSubTypeElement(String valuesubtype,WebDriver driver) {
		WebElement charValueSubType;
		try {
			valueSetPage.ValueSubTypeSelectEle.click();
			CommonUtils.hold(3);
			charValueSubType = driver
					.findElement(By
							.xpath("//select[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc4::content']/descendant::option[@value='"
									+ charvalueSubType.valueOf(valuesubtype).ordinal() + "']"));

		} catch (Exception nsevst) {

			valueSetPage.ValueSubTypeEle.click();
			CommonUtils.hold(3);
			charValueSubType = driver
					.findElement(By
							.xpath("//ul[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP2:soc4::pop']/descendant::li[@_adfiv='"
									+ charvalueSubType.valueOf(valuesubtype).ordinal() + "']"));
		}

		return charValueSubType;
	}// End of getValidationTypeElement()

	/*
	 * selectIndVSCode(String VSCode) method will select the user preferred
	 * "Independant valueset code".
	 */
	void selectIndVSCode(String VSCode,WebDriver driver) {
		try {
			valueSetPage.VSCodeanchorEle.click();
			CommonUtils.explicitWait(valueSetPage.SearchPopup, "Click", "",driver);
			valueSetPage.SearchPopup.click();
			CommonUtils.explicitWait(valueSetPage.SearchableVSCode, "Visible", "",driver);
			valueSetPage.SearchableVSCode.sendKeys(VSCode);
			valueSetPage.InitiateSearch.click();
			CommonUtils.explicitWait(valueSetPage.Resulttable, "Visible", "",driver);
			valueSetPage.Selectresultrow.click();
			CommonUtils.hold(3);
			valueSetPage.OKbutton.click();
			CommonUtils.hold(3);
			valueSetPage.CvsSaveTransaction.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(ManageValuesnavigate, "Click", "",driver);
		} catch (Exception sIVSCE) {
			sIVSCE.printStackTrace();
		}
	}// End of selectIndVSCode()

	/*
	 * userModuleName(String UserModuleId) will select "User Module Name id" of
	 * the valuesetcode.
	 */
	void userModuleName(String UserModuleId,WebDriver driver) {
		try {
			valueSetPage.UserModLovid.click();
//			CommonUtils.explicitWait(valueSetPage.UserModSearchPopup, "Click", "",driver);
//			valueSetPage.UserModSearchPopup.click();
//			CommonUtils.explicitWait(valueSetPage.SearchableUserModuleId, "Visible", "",driver);
//			valueSetPage.SearchableUserModuleId.sendKeys(UserModuleId);
//			valueSetPage.InitiateUserModSearch.click();
//			CommonUtils.explicitWait(valueSetPage.UserModResulttable, "Visible", "",driver);
//			valueSetPage.UserModresultrowSelect.click();
//			CommonUtils.hold(3);
//			valueSetPage.UserModOKbutton.click();

		} catch (Exception uMNE) {
			uMNE.printStackTrace();
		}
	}// End of userModuleName()

	/*
	 * manageValues() method will create values for Independant and Dependant
	 * Valueset codes
	 */
	void manageValues(String vsCodeType, String noOfValues, String valueType, String valueSubType,WebDriver driver) {
		try {
			int valuesCount = Integer.parseInt(noOfValues);
			CommonUtils.waitforElementtoClick(8,CreateValueEle,driver);
//			CommonUtils.explicitWait(CreateValueEle, "Visible", "",driver);
			System.out.println("Values count : " + valuesCount);
			for (int loop = 1; loop <= valuesCount; loop++) {
				CreateValueEle.click();
				switch (valueDataType.valueOf(valueType)) {
					case Character:
//					CommonUtils.explicitWait(CharValueEle, "Visible", "",driver);
						CommonUtils.waitforElementtoVisible(6,CharValueEle,driver);
						CharValueEle.click();
						CommonUtils.hold(2);
						CharValueEle.sendKeys(valueSetPage.taskflowParamaters.get("Value" + loop));
						valueSetPage.vsCodeDataType = "Character";
						if (valueSubType.equalsIgnoreCase("TranslatedText"))
							CharTranslationTextEle
									.sendKeys(valueSetPage.taskflowParamaters.get("TranslatedValue" + loop));
						break;
					case Number:
//					CommonUtils.explicitWait(NumberValueEle, "Visible", "",driver);
						CommonUtils.waitforElementtoVisible(10,NumberValueEle,driver);
						NumberValueEle.click();
						CommonUtils.hold(2);
						NumberValueEle.sendKeys(valueSetPage.taskflowParamaters.get("Value" + loop));
						valueSetPage.vsCodeDataType = "Number";
						break;
					case Date:
//					CommonUtils.explicitWait(DateValueEle, "Visible", "",driver);
						CommonUtils.waitforElementtoVisible(10,DateValueEle,driver);
						DateValueEle.click();
						CommonUtils.hold(2);
						DateValueEle.sendKeys(valueSetPage.taskflowParamaters.get("Value" + loop));
//					ManageValuestf.DateValueEle1.sendKeys(ValueSetTaskflow.taskflowParamaters.get("Value" + loop));
						valueSetPage.vsCodeDataType = "Date";
						break;
					case DateTime:
//					CommonUtils.explicitWait(DateTimeValueEle, "Visible", "",driver);
						CommonUtils.waitforElementtoVisible(10,DateTimeValueEle,driver);
						DateTimeValueEle.click();
						CommonUtils.hold(2);
						DateTimeValueEle.sendKeys(valueSetPage.taskflowParamaters.get("Value" + loop));
						valueSetPage.vsCodeDataType = "DateTime";
						break;
					default:
						break;
				}
				if (valueSetPage.taskflowParamaters.containsKey("Desc" + loop))
					getDescEle(valueSetPage.vsCodeDataType,driver).sendKeys(valueSetPage.taskflowParamaters.get("Desc" + loop));
				if (vsCodeType.equalsIgnoreCase("Dependent"))
					selectIndependantValue(valueSetPage.taskflowParamaters.get("IndValue" + loop),driver);
				if (valueSetPage.taskflowParamaters.containsKey("EFlag" + loop)
						&& valueSetPage.taskflowParamaters.get("EFlag" + loop).equalsIgnoreCase("N"))
					getEnabledEle(valueSetPage.vsCodeDataType,driver).click();
				CommonUtils.hold(2);
				if (valueSetPage.taskflowParamaters.containsKey("SDate" + loop))
					getStartDateEle(valueSetPage.vsCodeDataType,driver)
							.sendKeys(valueSetPage.taskflowParamaters.get("SDate" + loop));
				if (valueSetPage.taskflowParamaters.containsKey("EDate" + loop))
					getEndDateEle(valueSetPage.vsCodeDataType,driver)
							.sendKeys(valueSetPage.taskflowParamaters.get("EDate" + loop));
				if (valueSetPage.taskflowParamaters.containsKey("SOrder" + loop))
					getSortOrderEle(valueSetPage.vsCodeDataType,driver)
							.sendKeys(valueSetPage.taskflowParamaters.get("SOrder" + loop));

				SaveTransEle.click();
				checkTransactionComplete(SaveTransEle);
				CommonUtils.hold(5);
				CommonUtils.waitForInvisibilityOfElement(navPage.username,driver,10);
//				CommonUtils.explicitWait(getCreatedValue(valueSetPage.taskflowParamaters.get("Value" + loop),driver),	"Visible", "",driver);
				CommonUtils.waitforElementtoVisible(5,getCreatedValue(valueSetPage.taskflowParamaters.get("Value" + loop),driver),driver);
			}

			SaveAndCloseTransEle.click();
			CommonUtils.waitForInvisibilityOfElement(SaveAndCloseTransEle,driver,10);
		} catch (Exception mVE) {
			mVE.printStackTrace();
		}

	}// End of manageValues()


	public void checkTransactionComplete(WebElement element){
		boolean status=element.isEnabled();

		while(!status){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> In checkTransactionComplete() <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				CommonUtils.hold(5);
				status=element.isEnabled();

			}catch(Exception exp){
				CommonUtils.hold(5);
				status=element.isEnabled();

			}
		}
	}

	/*
	 * selectIndependantValue(String IndependantValue) will select independant
	 * value from the list of existing values for dependant valueset code. i/p :
	 * IndependantValue - Independant value to be assign to dependant value set
	 * code.
	 */
	void selectIndependantValue(String IndependantValue,WebDriver driver) {
		try {
			System.out.println("Independent value : "+IndependantValue);
			getIndVSLovEle(valueSetPage.vsCodeDataType,driver).clear();
			getIndVSLovEle(valueSetPage.vsCodeDataType,driver).sendKeys(IndependantValue);
//			CommonUtils.waitforElementtoClick(4,CreateLovSearchEle,driver);
////			CommonUtils.explicitWait(CreateLovSearchEle, "Click", "",driver);
//			CreateLovSearchEle.click();
////			CommonUtils.hold(2);
//			CommonUtils.waitForInvisibilityOfElement(navPage.username,driver,4);
//			CommonUtils.waitforElementtoClick(4,CreateIndSetValueEle,driver);
////			CommonUtils.explicitWait(CreateIndSetValueEle, "Visible", "",driver);
//			CreateIndSetValueEle.sendKeys(IndependantValue);
//			CreateInitiateIndValueSearch.click();
////			CommonUtils.hold(2);
//			CommonUtils.waitForInvisibilityOfElement(navPage.username,driver,2);
//			CommonUtils.waitforElementtoClick(4,CreatSearchResultWait,driver);
////			CommonUtils.explicitWait(CreatSearchResultWait, "Visible", "",driver);
//			CreatSearchResult.click();
//			CommonUtils.hold(2);
//			CreateIndValSearchOK.click();
//			CommonUtils.waitForInvisibilityOfElement(CreateIndValSearchOK,driver,4);
////			CommonUtils.hold(2);
//			CommonUtils.explicitWait(getSelectedIndValue(IndependantValue,driver), "Visible", "",driver);
		} catch (Exception ivE) {
			ivE.printStackTrace();
		}
	}// End of selectIndependantValue()

	/*
	 * searchIndependantValue(String IndependantValue) will search ValueSetcode
	 * values based on IndependantValueSet values
	 */
	void searchIndependantValue(String IndependantValue,WebDriver driver) {
		try {
			SearchLovIcon.click();
			CommonUtils.explicitWait(LovSearchEle, "Click", "",driver);
			LovSearchEle.click();
			CommonUtils.explicitWait(IndSetValueEle, "Visible", "",driver);
			IndSetValueEle.sendKeys(IndependantValue);
			InitiateIndValueSearch.click();
			CommonUtils.explicitWait(SearchResultWait, "Visible", "",driver);
			SearchResult.click();
			CommonUtils.hold(2);
			IndValSearchOK.click();
		} catch (Exception sivE) {
			sivE.printStackTrace();
		}
	}// End of searchIndependantValue()

	/*
	 * updateValueSet(String valueSetCode, String ..updatablefieds) method
	 * updates valueset fiels of the corresponding valueset code valueSetCode ->
	 * Valueset code to be update updatablefields -> List of valuse set fields
	 * to be update
	 */
	public void updateValueSet(WebDriver driver,String valueSetCode, String...updatablefields) {
		try {
			System.out.println("Length of valueSetParam argument -> " + updatablefields.length);
			valueSetPage.typeOfOperation = "Update";
			valueSetPage.taskflowParamaters = new LinkedHashMap<String, String>();
			for (int i = 0; i < updatablefields.length; i += 2) {
				valueSetPage.taskflowParamaters.put(updatablefields[i], updatablefields[i + 1]);
				System.out.println("Key -> " + updatablefields[i] + " && " + "Value -> " + updatablefields[i + 1]);
			}
			navigateToManageValueSettf(driver);
			if (searchValueSet(valueSetCode,driver)) {
				String vType = getVSValidtaionType(valueSetCode,driver);
				String valueType = getVSValuedataType(valueSetCode,driver);
				getValueSet(valueSetCode,driver).click();
				CommonUtils.explicitWait(valueSetPage.InitiateObjUpdation, "Click", "",driver);
				valueSetPage.InitiateObjUpdation.click();
				CommonUtils.explicitWait(valueSetPage.CvsSaveTransaction, "Visible", "",driver);
				if (valueSetPage.taskflowParamaters.containsKey("ValueSetDescription"))
					valueSetPage.VSDescriptionEle
							.sendKeys(valueSetPage.taskflowParamaters.get("ValueSetDescription"));
				if (valueSetPage.taskflowParamaters.containsKey("ValueSetModule"))
					userModuleName(valueSetPage.taskflowParamaters.get("ValueSetModule"),driver);
				if (vType.equalsIgnoreCase("Table"))
					tableTypeDef(driver);
				else
					setValueType(valueType,driver);
				valueSetPage.CvsSaveTransaction.click();
				valueSetPage.CvsSaveTransaction.click();
			} else {
				System.out.println("Valueset code to be update doesn't exist. Kindly recheck valueset code to update");
				return;
			}

		} catch (Exception uVSE) {
			uVSE.printStackTrace();
		}
	}

	/*
	 * searchValueSet(String valuesetCode) method will check for the presence of
	 * valueset based on valueset code i/p param : valuesetCode -> Valueset code
	 * to be searched for. o/p : true -> if the valueset found false -> of the
	 * valueset doesn't found
	 */
	public boolean searchValueSet(String valuesetCode,WebDriver driver) {
		boolean verifiedFlag = false;
		try {
			valueSetPage.vspValueSetCode.click();
			valueSetPage.vspValueSetCode.clear();
			valueSetPage.vspValueSetCode.sendKeys(valuesetCode);
			valueSetPage.InitiateObjSearch.click();
			dffUtils.waitForElementToAppear(driver,valueSetPage.SearchResultTable);
//			CommonUtils.waitforElementtoVisible(10,valueSetPage.SearchResultTable,driver);
			verifiedFlag = getValueSet(valuesetCode,driver).isDisplayed();
		} catch (Exception vscE) {
			System.out.println("Exception While Fetching VS Code : "+vscE.getMessage());
			driver.navigate().refresh();
			vscE.printStackTrace();
			verifiedFlag=false;
		}finally{
			valueSetPage.resetBtn.click();
			CommonUtils.hold(8);
		}
		return verifiedFlag;
	}

	/*
	 * getValueSet(String valuesetCode) method will return valueset webelement
	 * of the valuesetcode that is being searched
	 */
	WebElement getValueSet(String valuesetCode,WebDriver driver) {
		WebElement resultantValueSet;
		resultantValueSet = driver
				.findElement(By
						.xpath("//div[contains(@id , 'AP1:AT1:_ATp:ATt1::db')]/descendant::table[@_rowcount > 0]/descendant::td[span[text() = '"
								+ valuesetCode + "']]"));
		return resultantValueSet;
	}

	/*
	 * deleteValueSet(String valueSetCode) method will delete valueset.
	 */

	public void deleteValueSet(String valueSetCode,WebDriver driver) {
		try {
			navigateToManageValueSettf(driver);
			if (searchValueSet(valueSetCode,driver)) {
				getValueSet(valueSetCode,driver).click();
				CommonUtils.explicitWait(valueSetPage.InitiateObjdelete, "Click", "",driver);
				valueSetPage.InitiateObjdelete.click();
				Assert.assertFalse(searchValueSet(valueSetCode,driver));
			} else {
				System.out.println("Valueset code doesn't exist for deletion. Kindly recheck valueset code to delete");
				return;
			}
		} catch (Exception dVSE) {
			dVSE.printStackTrace();
		}

	}

	String getVSValidtaionType(String vsCode,WebDriver driver) {
		String vsValidationType;
		vsValidationType = driver
				.findElement(By
						.xpath("//div[contains(@id , 'AP1:AT1:_ATp:ATt1::db')]/descendant::table[@_rowcount > 0]/descendant::td[span[text() = '"
								+ vsCode + "']]/ancestor::tr[1]//descendant::span[contains(@id , 'ot5')]"))
				.getText();
		return vsValidationType;
	}

	String getVSValuedataType(String vsCode,WebDriver driver) {
		String vsValuedataType;
		vsValuedataType = driver
				.findElement(By
						.xpath("//div[contains(@id , 'AP1:AT1:_ATp:ATt1::db')]/descendant::table[@_rowcount > 0]/descendant::td[span[text() = '"
								+ vsCode + "']]/ancestor::tr[1]//descendant::span[contains(@id , 'ot6')]"))
				.getText();
		return vsValuedataType;
	}

}// End of ManageValueSets class
