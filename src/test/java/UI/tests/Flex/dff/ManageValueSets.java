package UI.tests.Flex.dff;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import UtilClasses.UI.DbResource;

public class ManageValueSets extends GetDriverInstance{

	private String id="";
	private String dffContext_name="";
	private WebDriver vsDriver=null;
	private ApplicationLogin login=null;
	private NavigationTaskFlows navTF=null;
	private ManaageValueSetsUtil vsUtil=null;
	private DFFUtils dffSql=null;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		id = CommonUtils.uniqueId();
		vsDriver=getDriverInstanceObject();
		login= new ApplicationLogin(vsDriver);
		navTF= new NavigationTaskFlows(vsDriver);
		dffSql= new DFFUtils(vsDriver);
		deleteSegment(user);
		dffContext_name = "DFFCON" + id;
		login.login(user, passWord,vsDriver);
		CommonUtils.waitForInvisibilityOfElement(navTF.username,vsDriver,15);
		vsUtil= new ManaageValueSetsUtil(vsDriver);
	}

	public void deleteSegment(String user){
		try {
			DbResource.sqlStatement.executeUpdate(dffSql.deleteGlobalSegmentSQL("PER_PERSONS_DFF", "800",user));
			DbResource.sqlStatement.executeUpdate(dffSql.deleteContextCode("PER_PERSONS_DFF", "800",user));
			DbResource.sqlStatement.executeUpdate(dffSql.deleteGlobalSegmentSQL("GL_JE_LINES", "101",user));
			DbResource.sqlStatement.executeUpdate(dffSql.deleteContextCode("GL_JE_LINES", "101",user));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(description = "Create Format-Character type valueSet")
	public void testcase01() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "FCHAR_" + id, "ValueSetDescription",
				"Format Character type value set code", "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly",
				"ValueType", "Character", "ValueSubType", "Text", "MaxLength", "10", "UpperCase", "Yes");
	}

	@Test(description = "Create Format-Number type valueSet")
	public void testcase02() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "FNUM_" + id, "ValueSetDescription",
				"Format Number type value set code", "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly",
				"ValueType", "Number", "MinVal", "3", "MaxVal", "9");
	}

	@Test(description = "Create Format-Number type valueSet with Prec/Scale")
	public void testcase03() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "FNUMP_" + id, "ValueSetDescription",
				"Format date type value set code", "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly",
				"ValueType", "Number", "Precision", "4", "Scale", "2");

	}

	@Test(description = "Create Format-Date type valueSet")
	public void testcase04() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "FDATE_" + id, "ValueSetDescription", "QATestVSDesc2",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly", "ValueType", "Date");

	}

	@Test(description = "Create Independent-Character valueSet")
	public void testcase05() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "INDCHAR_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Character",
				"ValueSubType", "Text", "MaxLength", "10", "ManageValues", "2", "Value1", "FLEX", "Value2", "AOL",
				"Desc1", "Flex one", "Desc2", "AOL two");
	}

	@Test(description = "Create Dependent-Character valueSet", dependsOnMethods = "testcase05")
	public void testcase06() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "DEPCHAR" + id, "ValueSetDescription", "QATestVSDesc13",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Dependent", "ValueType", "Character", "ValueSubType",
				"Text", "MaxLength", "10", "IndValueSetCode", "INDCHAR_" + id, "ManageValues", "4", "Value1", "FLEX1",
				"IndValue1", "FLEX", "Value2", "FLEX2", "IndValue2", "FLEX", "Value3", "AOL1", "IndValue3", "AOL",
				"Value4", "AOL2", "IndValue4", "AOL", "Desc1", "First Flex", "Desc2", "Second Flex", "Desc3",
				"First AOL", "Desc4", "Second AOL");
	}

	@Test(description = "Create Sub-Character valueSet", dependsOnMethods = "testcase05")
	public void testcase07() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "SUBCHAR" + id, "ValueSetDescription", "QATestVSDesc13",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Subset", "IndValueSetCode", "INDCHAR_" + id,
				"ManageValues", "2", "Value1", "FLEX", "Value2", "AOL");
	}

	@Test(description = "Create Independent-Number valueSet")
	public void testcase08() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "INDNUM_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Number", "ManageValues",
				"2", "Value1", "10", "Value2", "20", "Desc1", "Ten", "Desc2", "Twenty");

	}
//
//	// @Test(description = "Create Independent-Number valueSet with Prec/Sacle")
//	// public void testcase09() {
//	// vsUtil.createValueSet("ValueSetCode", "DPKINDNUMP_" + id,
//	// "ValueSetDescription", "QATestVSDesc12",
//	// "ValueSetModule", "Oracle M%", "ValidationType", "Independent",
//	// "ValueType", "Number", "ManageValues",
//	// "2", "Value1", "10.10", "Value2", "20.02", "Precision", "4", "Scale",
//	// "2");
//	// }
//
	@Test(description = "Create Dependent-Number valueSet", dependsOnMethods = "testcase08")
	public void testcase10() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "DEPNUM" + id, "ValueSetDescription", "QATestVSDesc13",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Dependent", "ValueType", "Number", "IndValueSetCode",
				"INDNUM_" + id, "ManageValues", "4", "Value1", "10", "IndValue1", "10", "Value2", "100", "IndValue2",
				"10", "Value3", "20", "IndValue3", "20", "Value4", "200", "IndValue4", "20", "Desc1", "Ten", "Desc2",
				"Hundred", "Desc3", "Twenty", "Desc4", "Two Hundred");

	}

	@Test(description = "Create Table-Character valueSet")
	public void testcase11() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "TABLE_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
				"FND_TABLES", "ValueColumnName", "TABLE_NAME", "WHEREClause",
				"application_short_name = 'FND' and table_name like 'FND%TREE%'", "DescriptionColumnName",
				"DESCRIPTION");

	}

	@Test(description = "Create global segment of type Format-Char", dependsOnMethods = { "testcase01" })
	public void testcase12() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "DFFFC_" + id,
				"dffCode", "DFFFC_CODE" + id, "dffAPIName", "DFFFCAPI" + id, "dataType", "Character", "valueSetCode",
				"FCHAR_" + id, "promtName", "DFFFC_" + id, "displayType", "Text Box", "createContext", "No",
				"contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
				"contextAPIName", "DPKCAPI"

		};
		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "Create global segment of type Format-Number", dependsOnMethods = { "testcase02" })
	public void testcase13() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "DFFFN_" + id,
				"dffCode", "DFFFN_CODE" + id, "dffAPIName", "DFFFNAPI" + id, "dataType", "Number", "valueSetCode",
				"FNUM_" + id, "promtName", "DFFFN_" + id, "displayType", "Text Box", "createContext", "No",
				"contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
				"contextAPIName", "DPKCAPI"

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "Create global segment of type format-Date", dependsOnMethods = { "testcase04" })
	public void testcase14() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "DFFFD_" + id,
				"dffCode", "DFFFD_CODE" + id, "dffAPIName", "DFFFDAPI" + id, "dataType", "Date", "valueSetCode",
				"FDATE_" + id, "promtName", "DFFFD_" + id, "displayType", "Date/Time", "createContext", "Yes",
				"contextCodeNameSearch", "DPK_CCODE1", "contextDisplayName", "DPK_NAME1", "contextCodeName",
				"DPK_CCODE1", "contextAPIName", "DPKCAPI1"

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM : Create sensitive segment - Independent/Character", dependsOnMethods = { "testcase05" })
	public void testcase15() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFINCHAR_" + id,
				"dffCode", "DFFINCHAR_CODE" + id, "dffAPIName", "DFFINCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "INDCHAR_" + id, "promtName", "DFFINCHAR_" + id, "displayType", "Drop-down List",
				"createContext", "Yes", "contextCodeNameSearch", "DPK_CCODE1", "contextDisplayName", "DFFCON_NAME" + id,
				"contextCodeName", "DFFCON_CCODE" + id, "contextAPIName", "DFFCONCAPI" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM : Create sensitive segment - Dependent/Character", dependsOnMethods = { "testcase06" })
	public void testcase16() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFDEPCHAR_" + id,
				"dffCode", "DFFDEPCHAR_CODE" + id, "dffAPIName", "DFFDEPCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "DEPCHAR" + id, "promtName", "DFFDEPCHAR_" + id, "displayType", "List of Values",
				"createContext", "No", "contextCodeNameSearch", "DFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM : Create sensitive segment - SubSet/Character", dependsOnMethods = { "testcase07" })
	public void testcase17() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFSUB" + id,
				"dffCode", "DFFSUB_CODE" + id, "dffAPIName", "DFFSUBAPI" + id, "dataType", "Character", "valueSetCode",
				"SUBCHAR" + id, "promtName", "DFFSUB" + id, "displayType", "Pop-up List of Values", "createContext",
				"No", "contextCodeNameSearch", "DFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM : Create sensitive segment - Table/Character", dependsOnMethods = { "testcase11" })
	public void testcase18() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFTAB" + id,
				"dffCode", "DFFTAB_CODE" + id, "dffAPIName", "DFFTABAPI" + id, "dataType", "Character", "valueSetCode",
				"TABLE_" + id, "promtName", "DFFTAB" + id, "displayType", "List of Values", "createContext", "No",
				"contextCodeNameSearch", "DFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM : Create sensitive segment - Independent/Number", dependsOnMethods = { "testcase08" })
	public void testcase19() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFNUM_" + id,
				"dffCode", "DFFNUM__CODE" + id, "dffAPIName", "DFFNUMAPI" + id, "dataType", "Number", "valueSetCode",
				"INDNUM_" + id, "promtName", "DFFNUM_" + id, "displayType", "Drop-down List", "createContext", "No",
				"contextCodeNameSearch", "DFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM : Create sensitive segment - Dependent/Number", dependsOnMethods = { "testcase10" })
	public void testcase20() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFDEPNUM" + id,
				"dffCode", "DFFDEPNUMCODE" + id, "dffAPIName", "DFFDEPNUMAPI" + id, "dataType", "Number",
				"valueSetCode", "DEPNUM" + id, "promtName", "DFFDEPNUM" + id, "displayType", "List of Values",
				"createContext", "No", "contextCodeNameSearch", "DFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "TABLE : Create global segment of type Format-Char", dependsOnMethods = { "testcase01" })
	public void testcase21() {

		String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "global", "dffName", "GLDFFFC_" + id, "dffCode",
				"GLDFFFC_CODE" + id, "dffAPIName", "GLDFFFCAPI" + id, "dataType", "Character", "valueSetCode",
				"FCHAR_" + id, "promtName", "GLDFFFC_" + id, "displayType", "Text Box", "createContext", "No",
				"contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
				"contextAPIName", "DPKCAPI"

		};
		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "TABLE : Create sensitive segment - Independent/Character", dependsOnMethods = { "testcase05" })
	public void testcase22() {

		String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFINCHAR_" + id,
				"dffCode", "GLDFFINCHAR_CODE" + id, "dffAPIName", "GLDFFINCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "INDCHAR_" + id, "promtName", "GLDFFINCHAR_" + id, "displayType", "Drop-down List",
				"createContext", "Yes", "contextCodeNameSearch", "GLDPK_CCODE" + id, "contextDisplayName",
				"GLDFFCON_NAME" + id, "contextCodeName", "GLDFFCON_CCODE" + id, "contextAPIName", "GLDFFCONCAPI" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "TABLE : Create sensitive segment - Dependent/Character", dependsOnMethods = { "testcase06" })
	public void testcase23() {

		String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFDEPCHAR_" + id,
				"dffCode", "GLDFFDEPCHAR_CODE" + id, "dffAPIName", "GLDFFDEPCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "DEPCHAR" + id, "promtName", "GLDFFDEPCHAR_" + id, "displayType", "List of Values",
				"createContext", "No", "contextCodeNameSearch", "GLDFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "TABLE : Create sensitive segment - SubSet/Character", dependsOnMethods = { "testcase07" })
	public void testcase24() {

		String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFSUB" + id,
				"dffCode", "GLDFFSUB_CODE" + id, "dffAPIName", "GLDFFSUBAPI" + id, "dataType", "Character",
				"valueSetCode", "SUBCHAR" + id, "promtName", "GLDFFSUB" + id, "displayType", "Pop-up List of Values",
				"createContext", "No", "contextCodeNameSearch", "GLDFFCON_CCODE" + id
				};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "TABLE : Create sensitive segment - Table/Character", dependsOnMethods = { "testcase11" })
	public void testcase25() {

		String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFTAB" + id,
				"dffCode", "GLDFFTAB_CODE" + id, "dffAPIName", "GLDFFTABAPI" + id, "dataType", "Character",
				"valueSetCode", "TABLE_" + id, "promtName", "GLDFFTAB" + id, "displayType", "List of Values",
				"createContext", "No", "contextCodeNameSearch", "GLDFFCON_CCODE" + id
		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV : Create global segment - Independent/Character", dependsOnMethods = {	"testcase05" })
	public void testcase26() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "CLOVINCHAR_" + id,
				"dffCode", "CLOVINCHAR_CODE" + id, "dffAPIName", "CLOVINCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "INDCHAR_" + id, "promtName", "CLOVINCHAR_" + id, "displayType", "Inline Search" };

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV : Create global segment - Dependent/Character", dependsOnMethods = {
			"testcase06" })
	public void testcase27() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "CLOVDEPCHAR_" + id,
				"dffCode", "CLOVDEPCHAR_CODE" + id, "dffAPIName", "CLOVDEPCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "DEPCHAR" + id, "promtName", "CLOVDEPCHAR_" + id, "displayType", "Inline Search"

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV: Create sensitive segment - Independent/Character", dependsOnMethods = {"testcase05" })
	public void testcase28() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "SCLOVINCHAR_" + id,
				"dffCode", "SCLOVINCHAR_CODE" + id, "dffAPIName", "SCLOVINCHARAPI" + id, "dataType", "Character",
				"valueSetCode", "INDCHAR_" + id, "promtName", "SCLOVINCHAR_" + id, "displayType", "Inline Search",
				"createContext", "Yes", "contextCodeNameSearch", "DPK_CCODE1", "contextDisplayName",
				"SCLOVCON_NAME" + id, "contextCodeName", "SCLOVCON_CCODE" + id, "contextAPIName", "SCLOVCONCAPI" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV : Create sensitive segment - Dependent/Character", dependsOnMethods = {"testcase06" })
	public void testcase29() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName",
				"SCLOVDEPCHAR_" + id, "dffCode", "SCLOVDEPCHAR_CODE" + id, "dffAPIName", "SCLOVDEPCHARAPI" + id,
				"dataType", "Character", "valueSetCode", "DEPCHAR" + id, "promtName", "SCLOVDEPCHAR_" + id,
				"displayType", "Inline Search", "createContext", "No", "contextCodeNameSearch", "SCLOVCON_CCODE" + id };

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV : Create sensitive segment - Independent/Number", dependsOnMethods = {"testcase08" })
	public void testcase30() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "SCLOVNUM_" + id,
				"dffCode", "SCLOVNUM__CODE" + id, "dffAPIName", "SCLOVNUMAPI" + id, "dataType", "Number",
				"valueSetCode", "INDNUM_" + id, "promtName", "SCLOVNUM_" + id, "displayType", "Inline Search",
				"createContext", "No", "contextCodeNameSearch", "SCLOVCON_CCODE" + id };

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV: Create sensitive segment - Dependent/Number", dependsOnMethods = {"testcase10" })
	public void testcase31() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "SCLOVDEPNUM" + id,
				"dffCode", "SCLOVDEPNUMCODE" + id, "dffAPIName", "SCLOVDEPNUMAPI" + id, "dataType", "Number",
				"valueSetCode", "DEPNUM" + id, "promtName", "SCLOVDEPNUM" + id, "displayType", "Inline Search",
				"createContext", "No", "contextCodeNameSearch", "SCLOVCON_CCODE" + id };

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM Client-LOV: Create sensitive segment - Table/Character", dependsOnMethods = {	"testcase11" })
	public void testcase32() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "SCLOVTAB" + id,
				"dffCode", "SCLOVTAB_CODE" + id, "dffAPIName", "SCLOVTABAPI" + id, "dataType", "Character",
				"valueSetCode", "TABLE_" + id, "promtName", "SCLOVTAB" + id, "displayType", "Inline Search",
				"createContext", "No", "contextCodeNameSearch", "SCLOVCON_CCODE" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "Create Table/BIND1:DFF_CODE and APP_ID")
	public void testcase33() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "BIND1_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
				"FND_DF_CONTEXTS_VL", "ValueColumnName", "CONTEXT_CODE", "WHEREClause",
				"APPLICATION_ID=:{FLEXFIELD.APPLICATION_ID}  and DESCRIPTIVE_FLEXFIELD_CODE=:{FLEXFIELD.DESCRIPTIVE_FLEXFIELD_CODE}");

	}

	@Test(description = "Create Table/BIND2:CONTEXT_CODE")
	public void testcase34() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "BIND2_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
				"FND_DF_SEGMENTS_B", "ValueColumnName", "SEGMENT_CODE", "WHEREClause",
				"CONTEXT_CODE=:{FLEXFIELD.CONTEXT_CODE}");

	}

	@Test(description = "Create Table/BIND3:SEGMENT", dependsOnMethods = { "testcase16" })
	public void testcase35() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "BIND3_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
				"FND_DF_SEGMENTS_B", "ValueColumnName", "SEGMENT_CODE", "WHEREClause",
				"CONTEXT_CODE=:{SEGMENT.BIND1_CODE" + id + "}");

	}

	@Test(description = "Create Table/BIND4:VALUESET", dependsOnMethods = { "testcase33" })
	public void testcase36() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "BIND4_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
				"FND_DF_SEGMENTS_B", "ValueColumnName", "SEGMENT_CODE", "WHEREClause",
				"CONTEXT_CODE=:{VALUESET.BIND1_" + id + "}");

	}

	@Test(description = "Create Table/BIND5:VALUESET")
	public void testcase37() {
		vsUtil.createValueSet(vsDriver,"ValueSetCode", "BIND5_" + id, "ValueSetDescription", "QATestVSDesc12",
				"ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
				"FND_DF_SEGMENTS_B", "ValueColumnName", "CONTEXT_CODE", "WHEREClause",
				"SEGMENT_CODE=:{FLEXFIELD.SEGMENT_CODE}");

	}

	@Test(description = "FORM BIND1-LOV: Create sensitive segment - Table/Character", dependsOnMethods = {"testcase33" })
	public void testcase38() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "BIND1" + id,
				"dffCode", "BIND1_CODE" + id, "dffAPIName", "BIND1API" + id, "dataType", "Character", "valueSetCode",
				"BIND1_" + id, "promtName", "BIND1" + id, "displayType", "Inline Search", "createContext", "Yes", "contextCodeNameSearch", "DPK_CCODE1",
				"contextDisplayName", "BINDCON_NAME" + id,"contextCodeName", "BINDCON_CCODE" + id, "contextAPIName", "BINDCONCAPI" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM BIND2-LOV: Create sensitive segment - Table/Character", dependsOnMethods = {
			"testcase34" })
	public void testcase39() {

		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "BIND2" + id,
				"dffCode", "BIND2_CODE" + id, "dffAPIName", "BIND2API" + id, "dataType", "Character", "valueSetCode",
				"BIND2_" + id, "promtName", "BIND2" + id, "displayType", "Inline Search", "createContext", "No",
				"contextCodeNameSearch", "BINDCON_CCODE" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM BIND3-LOV: Create sensitive segment - Table/Character", dependsOnMethods = {"testcase35" })
	public void testcase40() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "BIND3" + id,
				"dffCode", "BIND3_CODE" + id, "dffAPIName", "BIND3API" + id, "dataType", "Character", "valueSetCode",
				"BIND3_" + id, "promtName", "BIND3" + id, "displayType", "Inline Search", "createContext", "No",
				"contextCodeNameSearch", "BINDCON_CCODE" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM BIND4-LOV: Create sensitive segment - Table/Character", dependsOnMethods = {"testcase36" })
	public void testcase41() {
		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "BIND4" + id,
				"dffCode", "BIND4_CODE" + id, "dffAPIName", "BIND4API" + id, "dataType", "Character", "valueSetCode",
				"BIND4_" + id, "promtName", "BIND4" + id, "displayType", "Inline Search", "createContext", "No",
				"contextCodeNameSearch", "BINDCON_CCODE" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@Test(description = "FORM BIND5-LOV: Create sensitive segment - Table/Character", dependsOnMethods = {"testcase37" })
	public void testcase42() {
				String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "BIND5" + id,
				"dffCode", "BIND5_CODE" + id, "dffAPIName", "BIND5API" + id, "dataType", "Character", "valueSetCode",
				"BIND5_" + id, "promtName", "BIND5" + id, "displayType", "Inline Search", "createContext", "No",
				"contextCodeNameSearch", "BINDCON_CCODE" + id

		};

		dffSql.navigateToDFFtf(vsDriver);
		dffSql.createSegment(vsDriver,arges);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		vsDriver.quit();
	}

}
