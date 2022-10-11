package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;

public class FlexUtils extends NavigationTaskFlows{

	private GlobalPageTemplate fUgPTempalteInstance;
	private DFFUtils dffUtilInstance = null;
	private Map<String, String> flexUpdatableFields = new LinkedHashMap<>();
		
	public FlexUtils(WebDriver flexUtilsDriver) {
		super(flexUtilsDriver);
		PageFactory.initElements(flexUtilsDriver, this);
		fUgPTempalteInstance = new GlobalPageTemplate(flexUtilsDriver);
		dffUtilInstance= new DFFUtils(flexUtilsDriver);
	}
	
	
	@FindBy(xpath = "//td[text() = 'All configurations on this page are done in the current sandbox.']")
	public  WebElement flexPageEditModeMsg;
	
	@FindBy(xpath = "//td[text() = 'Task Flexfield is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.']")
	public  WebElement flexPagePreviewModeMsg;
	
	@FindBy(xpath = "//div[contains(@id , '_ATp:ATm') and @class = 'af_menu af_menu_bar-item']")
	public  WebElement flexActionsMenubar;
	
	@FindBy(xpath = "//div[contains(@id , 'ATm::ScrollBox')]")
	public  WebElement flexActionsMenuScrollBox;
	
	@FindBy(xpath = "//tr[contains(@id , '_ATp:depDFFL2')]/td[@class = 'af_commandMenuItem_menu-item-text']")
	public  WebElement initiateDeployFlexIntoSandbox;
	
	@FindBy(xpath = "//button[contains(@id , 'ffAT:yesSB')]")
	public  WebElement proceedDeployFlexIntoSandbox;
	
	@FindBy(xpath = "//button[contains(@id , 'ffAT:noSB')]")
	public  WebElement abortDeployFlexIntoSandbox;
	
	@FindBy(xpath="//div[contains(@id,':pt1:AP1:ffAT:d1::_ttxt')]")
	public WebElement deploymentProcessDialog;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:ffAT:okdlgp')]")
	public WebElement deploymentProcessDialogOK;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:q1:value10::content')]")
	public WebElement dffSearchField;
	
	@FindBy(xpath = "//tr[contains(@id , '_ATp:validateDFFL1')]/td[@class = 'af_commandMenuItem_menu-item-text']")
	public  WebElement validateFlexField;

	/*
	 * navigateFlexPage(String flexName) navigate to Flexfield page of the flex passed as a parameter
	 * flexName -> Name of the flexfield page navigate to
	 */
	public void navigateFlexPage(String flexFieldtf, WebDriver driver) {
		try {
			CommonUtils.hold(5);
				System.out.println("navigating to <Setup and Maintenance> initiated");
			fUgPTempalteInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(fUgPTempalteInstance.administration_SetupAndMaintenance,"Visible","",driver);
				System.out.println("navigated to <Setup and Maintenance> initiated");
				System.out.println("navigating to "+flexFieldtf+" taskflow initiated");
			fUgPTempalteInstance.administration_SetupAndMaintenance.click();
			navigateToAOLTaskFlows(flexFieldtf,driver);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//button[text()='Search']")), "Click", "",driver);
			System.out.println(flexFieldtf+" taskflow loaded successfully");
			CommonUtils.hold(5);
		}catch(Exception nfpe) {
			System.out.println("Exception while navigating to Flex Page : "+nfpe.getMessage());
			nfpe.printStackTrace();
		}
	}//End Of navigateFlexPage()
	
	/*
	 * Method to deploy flexfield into sandbox session
	 * @return status of flex deployment into sandbox operation
	 */
	public String deployFlexIntoSandbox(String flexFieldCode, WebDriver driver) {
		String[] flexDeploymentStatusText = null;
		
		try {
			flexActionsMenubar.click();
			//CommonUtils.explicitWait(flexActionsMenuScrollBox, "Visible", "", driver);
			CommonUtils.explicitWait(initiateDeployFlexIntoSandbox, "Click", "", driver);
			CommonUtils.hold(2);
			initiateDeployFlexIntoSandbox.click();
			CommonUtils.explicitWait(proceedDeployFlexIntoSandbox, "Click", "", driver);
			proceedDeployFlexIntoSandbox.click();
			CommonUtils.explicitWait(deploymentProcessDialogOK, "Click", "", driver);
			while(!deploymentProcessDialogOK.isEnabled())
				CommonUtils.hold(3);
			
			flexDeploymentStatusText = deploymentProcessDialog.getText().split(":");
			System.out.println("Flex deployment status : " + Arrays.toString(flexDeploymentStatusText));
			deploymentProcessDialogOK.click();
			CommonUtils.explicitWait(dffSearchField, CommonUtils.ExplicitWaitCalls.Click.toString(), "", driver);
			
			System.out.println("Status of "+flexFieldCode+" delpoyment into sandbox operation is : "+flexDeploymentStatusText[1].trim());
						
		}catch(Exception dFISE) {
			System.out.println("Deployment of Flex :" + flexFieldCode + " failed. Please review the error message."+dFISE.getMessage());
			dFISE.printStackTrace();
		}
		
		return flexDeploymentStatusText[1].trim();
	}
	
	/*
	 * Method return deployment status from UI of the flexfield passed through parameter
	 * @return flexfield deployment status 
	 */
	public String getFlexDeploymentStatus(String flexFiledCode,WebDriver driver) {
		String deplymentStatus = null;
		
		deplymentStatus = driver.findElement(By.xpath("//span[text() = '"+flexFiledCode+"']/ancestor::tr[1]/descendant::img[contains(@id , '_ATp:ffTab')]")).getAttribute("title");
		
		return deplymentStatus;
	}
	
	/*
	 * Method return flexfield name of the flexfield code passed through parameter
	 * @return flexfield name 
	 */
	public String getFlexfieldName(String flexFiledCode,WebDriver driver) {
		String flexFieldName = null;
		
		flexFieldName = driver.findElement(By.xpath("//span[text() = '"+flexFiledCode+"']/ancestor::tr[1]/descendant::span[contains(@id , 'ot01')]")).getText();
		
		return flexFieldName;
	}
	
	/*
	 * Method to update fields of the flexfield which are passed through parameter
	 */
	public void updateSegment(WebDriver driver, String ...updatableFields) {
		try {
			for(int index=0; index<updatableFields.length; index+=2) {
				flexUpdatableFields.put(updatableFields[index], updatableFields[index+1]);
				System.out.println("Parameter : "+updatableFields[index]+"-> UpdatableValue : "+updatableFields[index+1]);
			}
			
			if(dffUtilInstance.searchDFFCode(flexUpdatableFields.get("flexFieldCode"), driver)) {
				CommonUtils.waitforElementtoClick(5,dffUtilInstance.edithBtn,driver);
				dffUtilInstance.edithBtn.click();
                CommonUtils.waitForInvisibilityOfElement(dffUtilInstance.searchBtn,driver,8);
                
                getFlexSegmentRow(flexUpdatableFields.get("flexFieldCode"),flexUpdatableFields.get("contextCode"),flexUpdatableFields.get("segmentCode"),driver).click();
                CommonUtils.hold(5);
                
                flexUpdatableFields.remove("segmentCode");
                flexUpdatableFields.remove("contextCode");
                flexUpdatableFields.remove("flexFieldCode");
                                
             switch (flexUpdatableFields.get("segmentType")) {
             	case "global":
             		flexUpdatableFields.remove("segmentType");
             		
             		dffUtilInstance.glbSegEdithBtn.click();
             		
             		CommonUtils.waitForInvisibilityOfElement(dffUtilInstance.glbSegEdithBtn,driver,10);
                    CommonUtils.waitforElementtoClick(10,dffUtilInstance.glbSegNameInput,driver);
             		
             		while(!(flexUpdatableFields.isEmpty())) {
             			if(flexUpdatableFields.containsKey("glbSegmentDesc")) {
             				dffUtilInstance.glbSegmentDesc.sendKeys(flexUpdatableFields.get("glbSegmentDesc"));
             				flexUpdatableFields.remove("glbSegmentDesc");
             			}
             			if(flexUpdatableFields.containsKey("glbSegmentEnable")) {
             				dffUtilInstance.glbSegmentEnabled.click();
             				flexUpdatableFields.remove("glbSegmentEnable");
             			}
             			if(flexUpdatableFields.containsKey("glbValidationRangeType")) {
             				CommonUtils.selectDropDownElement(dffUtilInstance.glbValueSetRangeType, flexUpdatableFields.get("glbValidationRangeType"));
             				CommonUtils.hold(2);
             				flexUpdatableFields.remove("glbValidationRangeType");
             			}
             			if(flexUpdatableFields.containsKey("glbValidationRequired")) {
             				dffUtilInstance.glbValueSetValidationReq.click();
             				flexUpdatableFields.remove("glbValidationRequired");
             			}
             			if(flexUpdatableFields.containsKey("glbDefaultType")) {
             				CommonUtils.selectDropDownElement(dffUtilInstance.glbDefaultTypeDD, flexUpdatableFields.get("glbDefaultType"));
             				CommonUtils.hold(3);
             				if(flexUpdatableFields.get("glbDefaultType").equalsIgnoreCase("Constant")) {
             					dffUtilInstance.glbDefaultValue.sendKeys(flexUpdatableFields.get("glbConstantDefaultValue"));
             					flexUpdatableFields.remove("glbConstantDefaultValue");
             				}else if(flexUpdatableFields.get("glbDefaultType").equalsIgnoreCase("SQL")) {
             					dffUtilInstance.glbSqlDefaultTypeValue.sendKeys(flexUpdatableFields.get("glbSqlDefaultValue"));
             					flexUpdatableFields.remove("glbSqlDefaultValue");
             				}
             			           				
             				flexUpdatableFields.remove("glbDefaultType");
             				
             			}
             			if(flexUpdatableFields.containsKey("glbDisplayType")) {
             				dffUtilInstance.glbDisplayTypeDD.click();
             	            CommonUtils.waitforElementtoClick(10,driver.findElement(By.xpath("//option[@title='" + flexUpdatableFields.get("glbDisplayType") + "']")),driver);
             	            driver.findElement(By.xpath("//option[@title='" + flexUpdatableFields.get("glbDisplayType") + "']")).click();
             				
             				CommonUtils.hold(2);
             				dffUtilInstance.glbDisplaySize.clear();
             				CommonUtils.hold(1);
             				dffUtilInstance.glbDisplaySize.sendKeys(flexUpdatableFields.get("glbDisplayTypeSize"));
             				
             				if(flexUpdatableFields.containsKey("glbDisplayTypeHeight")) {
             					dffUtilInstance.glbDisplayHeight.clear();
             					CommonUtils.hold(1);
             					dffUtilInstance.glbDisplayHeight.sendKeys(flexUpdatableFields.get("glbDisplayTypeHeight"));
             					flexUpdatableFields.remove("glbDisplayTypeHeight");
             				}
             				
             				flexUpdatableFields.remove("glbDisplayType");
             				flexUpdatableFields.remove("glbDisplayTypeSize");
             				
             			}
             			if(flexUpdatableFields.containsKey("glbDisplayTypeReadOnly")) {
             				dffUtilInstance.glbReadOnlyDisplay.click();
             				flexUpdatableFields.remove("glbDisplayTypeReadOnly");
             			}
             			if(flexUpdatableFields.containsKey("glbDisplayDefHelpText")) {
             				dffUtilInstance.glbDefHelpText.sendKeys(flexUpdatableFields.get("glbDisplayDefHelpText"));
             				flexUpdatableFields.remove("glbDisplayDefHelpText");
             			}
             			if(flexUpdatableFields.containsKey("glbDisplayInstructionHelpText")) {
             				dffUtilInstance.glbInstructionHelpText.sendKeys(flexUpdatableFields.get("glbDisplayInstructionHelpText"));
             				flexUpdatableFields.remove("glbDisplayInstructionHelpText");
             			}
             			if(flexUpdatableFields.containsKey("glbBIEnable")) {
             				dffUtilInstance.glbBIEnabled.click();
             				CommonUtils.hold(3);
             				dffUtilInstance.glbBILabel.click();
             				CommonUtils.waitforElementtoClick(10,driver.findElement(By.xpath("//option[@title='" + flexUpdatableFields.get("glbBILabel") + "']")),driver);
             	            driver.findElement(By.xpath("//option[@title='" + flexUpdatableFields.get("glbBILabel") + "']")).click();
             				flexUpdatableFields.remove("glbBIEnable");
             				flexUpdatableFields.remove("glbBILabel");
             			}
             			
            			CommonUtils.hold(2);
             		}
             		
             			
             		break;
            	case "sensitive":
             		break;
             	default:
             		break;
             }
                
			}
			
			flexUpdatableFields.clear();
			
			dffUtilInstance.glbEditSegSaveCloseBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dffUtilInstance.glbSegNameInput,driver,10);
            CommonUtils.waitforElementtoClick(10, dffUtilInstance.editSaveCloseBtn,driver);
            dffUtilInstance.editSaveCloseBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dffUtilInstance.editSaveCloseBtn,driver,5);
            CommonUtils.waitforElementtoClick(10,dffUtilInstance.dffSearchField,driver);
			
		}catch(Exception uSE) {
			System.out.println("Exception in updateSegment() "+uSE.getMessage());
			uSE.printStackTrace();
		}
		
	}
	
	public void updateFlexfield(WebDriver driver, String ...updatableFields) {
		
	}
	
	/*
	 * Method to return segment code SequenceNumber of the flexField
	 * @return Segment Sequence number
	 * i/P :
	 * flexCode -> Flexfield code
	 * contextCode -> For Global Segments, contexCode = 'Global Data Elements'
	 * 				  For Context Segments, contexCode = 'Context Code of Context Segment'
	 * segmentCode -> Name of the segment whose sequence number to get.
	 */
	public int getSegmentSeqNumber(String flexCode,String contextCode,String segmentCode) {
		int segmentSeqNumber = 0;
		String getSegmentSeqeunceNumber = "select * from FND_DF_SEGMENTS_B where descriptive_flexfield_code = '"+flexCode+"' and context_code = '"+contextCode+"' and segment_code = '"+segmentCode+"'";
		try {
			if (DbResource.sqlStatement != null) {
				System.out.println("Executable Query -> "+getSegmentSeqeunceNumber);
				DbResource.queryResult = DbResource.sqlStatement.executeQuery(getSegmentSeqeunceNumber);
				if (DbResource.queryResult.next()) {
					segmentSeqNumber = Integer.parseInt(DbResource.queryResult.getNString("SEQUENCE_NUMBER"));
				}
				System.out.println("Sequence number of "+segmentCode+" -> "+segmentSeqNumber);
			}else {
				System.out.println("SqlStatement not established - Check For DBConnection");
			}
			
			DbResource.queryResult = null;
			
		}catch(Exception gSSNE) {
			System.out.println("Exception in getSegmentSeqNumber() "+gSSNE.getMessage());
			gSSNE.printStackTrace();
		}
		return segmentSeqNumber;
	}
	
	public WebElement getFlexSegmentRow(String flexCode,String contextCode,String segmentCode,WebDriver driver) {
		WebElement segmentWebElement = null;
		
		if(contextCode.equalsIgnoreCase("Global Data Elements"))
			segmentWebElement = driver.findElement(By.xpath("//input[contains(@id , 'gsIT1::content') and @value = '"+getSegmentSeqNumber(flexCode,contextCode,segmentCode)+"']/ancestor::tr[1]/descendant::span[contains(@id,'gsOT2')]"));
		else
			segmentWebElement = driver.findElement(By.xpath("//input[contains(@id , 'cssIT1::content') and @value = '"+getSegmentSeqNumber(flexCode,contextCode,segmentCode)+"']/ancestor::tr[1]/descendant::span[contains(@id,'cssOT3')]"));
		 
		return segmentWebElement;
	}
	
		
	/*
	 * getNextAvailableTableColumn(String dataType) will get next available "Table Column" value of the data type passed as parameter
	 * @return Next Available Table Column
	 * @deprecated. Not using now, will check in future
	 */
	public String getNextAvailableTableColumn(String dataType,String flexCode) {
		String availableTableColumnValue = null;
		String usedTableColumnValues = "select * from FND_DF_SEGMENTS_B where descriptive_flexfield_code = '"+flexCode+"' and context_code = 'Global Data Elements';";
		int[] charAttr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] numberAttr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int[] dateAttr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		try {
			if (DbResource.sqlStatement != null) {
				System.out.println("Executable Query -> "+usedTableColumnValues);
				DbResource.queryResult = DbResource.sqlStatement.executeQuery(usedTableColumnValues);
				System.out.println("Result set retreived");
				while (DbResource.queryResult.next()) {
					String columnValue = DbResource.queryResult.getNString("COLUMN_NAME");
					if(columnValue.contains("ATTRIBUTE_NUMBER"))
						numberAttr[Integer.parseInt(columnValue.substring("ATTRIBUTE_NUMBER".length(), columnValue.length()))]=1;
					if(columnValue.contains("ATTRIBUTE_DATE"))
						numberAttr[Integer.parseInt(columnValue.substring("ATTRIBUTE_DATE".length(), columnValue.length()))]=1;
					if(columnValue.contains("ATTRIBUTE"))
						numberAttr[Integer.parseInt(columnValue.substring("ATTRIBUTE".length(), columnValue.length()))]=1;
				}
				System.out.println("Result set processed");
			}else {
				System.out.println("SqlStatement not established - Check For DBConnection");
			}
			
			DbResource.queryResult = null;
			if(dataType.contentEquals("Character")) {
				for(int index = 1;index < charAttr.length-1; index++) {
					if(charAttr[index]==0) {
						availableTableColumnValue = "ATTRIBUTE"+index;
						break;
					}
				}
			}
			if(dataType.contentEquals("Number")) {
				for(int index = 1;index < charAttr.length-1; index++) {
					if(numberAttr[index]==0) {
						availableTableColumnValue = "ATTRIBUTE_NUMBER"+index;
						break;
					}
				}
			}
			if(dataType.contentEquals("Date")) {
				for(int index = 1;index < charAttr.length-1; index++) {
					if(dateAttr[index]==0) {
						availableTableColumnValue = "ATTRIBUTE_DATE"+index;
						break;
					}
				}
			}
				
		 System.out.println("Available ColumnValue -> "+availableTableColumnValue);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return availableTableColumnValue;
	}
	
}
