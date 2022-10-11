//package oracle.applcore.qa.tests;
//
//import org.testng.SkipException;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//import oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;
//import oracle.applcore.qa.common.CommonUtils;
//import oracle.applcore.qa.dff.DFFUtils;
//import oracle.applcore.qa.dff.ManaageValueSetsUtil;
//
//public class ManageValueSets {
//
//	String id;
//	String dffContext_name;
//
//	@Parameters({ "user", "pwd" })
//	@BeforeClass
//	public void setUp(String user, String passWord) throws Exception {
//	    deleteSegment();
//		id = CommonUtils.uniqueId();
//		dffContext_name = "DFFCON" + id;
//		CommonUtils.login(user, passWord);
//		CommonUtils.hold(5);
//	}
//	
//		public void deleteSegment() throws Exception{
//		try{
//			System.out.println("Deleting segments");
//			CommonUtils.GetDBConnection();
//			CommonUtils.CreateStatement();
//			CommonUtils.SqlStatement.executeUpdate(DFFUtils.deleteGlobalSegmentSQL("PER_PERSONS_DFF", "800"));
//			CommonUtils.SqlStatement.executeUpdate(DFFUtils.deleteContextCode("PER_PERSONS_DFF", "800"));
//			CommonUtils.SqlStatement.executeUpdate(DFFUtils.deleteGlobalSegmentSQL("GL_JE_LINES", "101"));
//			CommonUtils.SqlStatement.executeUpdate(DFFUtils.deleteContextCode("GL_JE_LINES", "101"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			CommonUtils.closeDBConnection();
//			
//		}
//	}
//
//	@Test(description = "Create Format-Character type valueSet")
//	public void testcase01() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "FCHAR_" + id, "ValueSetDescription",
//				"Format Character type value set code", "ValueSetModule", "Oracle M%", "ValidationType", "FormatOnly",
//				"ValueType", "Character", "ValueSubType", "Text", "MaxLength", "10", "UpperCase", "Yes");
//	}
//
//	@Test(description = "Create Format-Number type valueSet")
//	public void testcase02() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "FNUM_" + id, "ValueSetDescription",
//				"Format Number type value set code", "ValueSetModule", "Oracle M%", "ValidationType", "FormatOnly",
//				"ValueType", "Number", "MinVal", "3", "MaxVal", "9");
//	}
//
//	@Test(description = "Create Format-Number type valueSet with Prec/Scale")
//	public void testcase03() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "FNUMP_" + id, "ValueSetDescription",
//				"Format date type value set code", "ValueSetModule", "Oracle M%", "ValidationType", "FormatOnly",
//				"ValueType", "Number", "Precision", "4", "Scale", "2");
//
//	}
//
//	@Test(description = "Create Format-Date type valueSet")
//	public void testcase04() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "FDATE_" + id, "ValueSetDescription", "QATestVSDesc2",
//				"ValueSetModule", "Oracle M%", "ValidationType", "FormatOnly", "ValueType", "Date");
//
//	}
//
//	@Test(description = "Create Independent-Character valueSet")
//	public void testcase05() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "INDCHAR_" + id, "ValueSetDescription", "QATestVSDesc12",
//				"ValueSetModule", "Oracle M%", "ValidationType", "Independent", "ValueType", "Character",
//				"ValueSubType", "Text", "MaxLength", "10", "ManageValues", "2", "Value1", "FLEX", "Value2", "AOL");
//	}
//
//	@Test(description = "Create Dependent-Character valueSet", dependsOnMethods = "testcase05")
//	public void testcase06() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "DEPCHAR" + id, "ValueSetDescription", "QATestVSDesc13",
//				"ValueSetModule", "Oracle M%", "ValidationType", "Dependent", "ValueType", "Character", "ValueSubType",
//				"Text", "MaxLength", "10", "IndValueSetCode", "INDCHAR_" + id, "ManageValues", "4", "Value1", "FLEX1",
//				"IndValue1", "FLEX", "Value2", "FLEX2", "IndValue2", "FLEX", "Value3", "AOL1", "IndValue3", "AOL",
//				"Value4", "AOL2", "IndValue4", "AOL");
//	}
//
//	@Test(description = "Create Sub-Character valueSet", dependsOnMethods = "testcase05")
//	public void testcase07() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "SUBCHAR" + id, "ValueSetDescription", "QATestVSDesc13",
//				"ValueSetModule", "Oracle M%", "ValidationType", "Subset", "IndValueSetCode", "INDCHAR_" + id,
//				"ManageValues", "2", "Value1", "FLEX", "Value2", "AOL");
//	}
//
//	@Test(description = "Create Independent-Number valueSet")
//	public void testcase08() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "INDNUM_" + id, "ValueSetDescription", "QATestVSDesc12",
//				"ValueSetModule", "Oracle M%", "ValidationType", "Independent", "ValueType", "Number", "ManageValues",
//				"2", "Value1", "10", "Value2", "20");
//
//	}
//
//	// @Test(description = "Create Independent-Number valueSet with Prec/Sacle")
//	// public void testcase09() {
//	// ManaageValueSetsUtil.createValueSet("ValueSetCode", "DPKINDNUMP_" + id,
//	// "ValueSetDescription", "QATestVSDesc12",
//	// "ValueSetModule", "Oracle M%", "ValidationType", "Independent",
//	// "ValueType", "Number", "ManageValues",
//	// "2", "Value1", "10.10", "Value2", "20.02", "Precision", "4", "Scale",
//	// "2");
//	// }
//
//	@Test(description = "Create Dependent-Number valueSet", dependsOnMethods = "testcase08")
//	public void testcase10() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "DEPNUM" + id, "ValueSetDescription", "QATestVSDesc13",
//				"ValueSetModule", "Oracle M%", "ValidationType", "Dependent", "ValueType", "Number", "IndValueSetCode",
//				"INDNUM_" + id, "ManageValues", "4", "Value1", "10", "IndValue1", "10", "Value2", "100", "IndValue2",
//				"10", "Value3", "20", "IndValue3", "20", "Value4", "200", "IndValue4", "20");
//
//	}
//
//	@Test(description = "Create Table-Character valueSet")
//	public void testcase11() {
//		ManaageValueSetsUtil.createValueSet("ValueSetCode", "TABLE_" + id, "ValueSetDescription", "QATestVSDesc12",
//				"ValueSetModule", "Oracle M%", "ValidationType", "Table", "ValueType", "Character", "FromClause",
//				"FND_TABLES", "ValueColumnName", "TABLE_NAME", "WHEREClause",
//				"application_short_name = 'FND' and table_name like 'FND%TREE%'");
//
//	}
//
//	@Test(description = "Create global segment of type Format-Char", dependsOnMethods = { "testcase01" })
//	public void testcase12() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "DFFFC_" + id,
//				"dffCode", "DFFFC_CODE" + id, "dffAPIName", "DFFFCAPI" + id, "dataType", "Character", "valueSetCode",
//				"FCHAR_" + id, "promtName", "DFFFC_" + id, "displayType", "Text Box", "createContext", "No",
//				"contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
//				"contextAPIName", "DPKCAPI"
//
//		};
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "Create global segment of type Format-Number", dependsOnMethods = { "testcase02" })
//	public void testcase13() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "DFFFN_" + id,
//				"dffCode", "DFFFN_CODE" + id, "dffAPIName", "DFFFNAPI" + id, "dataType", "Number", "valueSetCode",
//				"FNUM_" + id, "promtName", "DFFFN_" + id, "displayType", "Text Box", "createContext", "No",
//				"contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
//				"contextAPIName", "DPKCAPI"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "Create global segment of type format-Date", dependsOnMethods = { "testcase04" })
//	public void testcase14() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "global", "dffName", "DFFFD_" + id,
//				"dffCode", "DFFFD_CODE" + id, "dffAPIName", "DFFFDAPI" + id, "dataType", "Date", "valueSetCode",
//				"FDATE_" + id, "promtName", "DFFFD_" + id, "displayType", "Date/Time", "createContext", "Yes",
//				"contextCodeNameSearch", "DPK_CCODE1", "contextDisplayName", "DPK_NAME1", "contextCodeName",
//				"DPK_CCODE1", "contextAPIName", "DPKCAPI1"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "FORM : Create sensitive segment - Independent/Character", dependsOnMethods = { "testcase05" })
//	public void testcase15() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFINCHAR_" + id,
//				"dffCode", "DFFINCHAR_CODE" + id, "dffAPIName", "DFFINCHARAPI" + id, "dataType", "Character",
//				"valueSetCode", "INDCHAR_" + id, "promtName", "DFFINCHAR_" + id, "displayType", "Drop-down List",
//				"createContext", "Yes", "contextCodeNameSearch", "DPK_CCODE1", "contextDisplayName", "DFFCON_NAME" + id,
//				"contextCodeName", "DFFCON_CCODE" + id, "contextAPIName", "DFFCONCAPI" + id
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "FORM : Create sensitive segment - Dependent/Character", dependsOnMethods = { "testcase06" })
//	public void testcase16() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFDEPCHAR_" + id,
//				"dffCode", "DFFDEPCHAR_CODE" + id, "dffAPIName", "DFFDEPCHARAPI" + id, "dataType", "Character",
//				"valueSetCode", "DEPCHAR" + id, "promtName", "DFFDEPCHAR_" + id, "displayType", "List of Values",
//				"createContext", "No", "contextCodeNameSearch", "DFFCON_CCODE" + id, "contextDisplayName", "DPK_NAME11",
//				"contextCodeName", "DPK_CCODE11", "contextAPIName", "DPKCAPI11"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "FORM : Create sensitive segment - SubSet/Character", dependsOnMethods = { "testcase07" })
//	public void testcase17() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFSUB" + id,
//				"dffCode", "DFFSUB_CODE" + id, "dffAPIName", "DFFSUBAPI" + id, "dataType", "Character", "valueSetCode",
//				"SUBCHAR" + id, "promtName", "DFFSUB" + id, "displayType", "Pop-up List of Values", "createContext",
//				"No", "contextCodeNameSearch", "DFFCON_CCODE" + id, "contextDisplayName", "DPK_NAME111",
//				"contextCodeName", "DPK_CCODE111", "contextAPIName", "DPKCAPI111"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "FORM : Create sensitive segment - Table/Character", dependsOnMethods = { "testcase11" })
//	public void testcase18() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFTAB" + id,
//				"dffCode", "DFFTAB_CODE" + id, "dffAPIName", "DFFTABAPI" + id, "dataType", "Character", "valueSetCode",
//				"TABLE_" + id, "promtName", "DFFTAB" + id, "displayType", "List of Values", "createContext", "No",
//				"contextCodeNameSearch", "DFFCON_CCODE" + id, "contextDisplayName", "DPK_NAME111", "contextCodeName",
//				"DPK_CCODE111", "contextAPIName", "DPKCAPI111"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "FORM : Create sensitive segment - Independent/Number", dependsOnMethods = { "testcase08" })
//	public void testcase19() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFNUM_" + id,
//				"dffCode", "DFFNUM__CODE" + id, "dffAPIName", "DFFNUMAPI" + id, "dataType", "Number", "valueSetCode",
//				"INDNUM_" + id, "promtName", "DFFNUM_" + id, "displayType", "Drop-down List", "createContext", "No",
//				"contextCodeNameSearch", "DFFCON_CCODE" + id, "contextDisplayName", "DPK_NAME1", "contextCodeName",
//				"DPK_CCODE1", "contextAPIName", "DPKCAPI1"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//
//	@Test(description = "FORM : Create sensitive segment - Dependent/Number", dependsOnMethods = { "testcase10" })
//	public void testcase20() {
//
//		String arges[] = { "dffFlexCode", "PER_PERSONS_DFF", "segmentType", "sensitive", "dffName", "DFFDEPNUM" + id,
//				"dffCode", "DFFDEPNUMCODE" + id, "dffAPIName", "DFFDEPNUMAPI" + id, "dataType", "Number",
//				"valueSetCode", "DEPNUM" + id, "promtName", "DFFDEPNUM" + id, "displayType", "List of Values",
//				"createContext", "No", "contextCodeNameSearch", "DFFCON_CCODE" + id, "contextDisplayName", "DPK_NAME1",
//				"contextCodeName", "DPK_CCODE1", "contextAPIName", "DPKCAPI1"
//
//		};
//
//		DFFUtils.navigateToDFFtf();
//		DFFUtils.createSegment(arges);
//	}
//	
//	@Test(description = "TABLE : Create global segment of type Format-Char", dependsOnMethods = { "testcase01" })
//    public void testcase21() {
// 
//        String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "global", "dffName", "GLDFFFC_" + id,
//                "dffCode", "GLDFFFC_CODE" + id, "dffAPIName", "GLDFFFCAPI" + id, "dataType", "Character", "valueSetCode",
//                "FCHAR_" + id, "promtName", "GLDFFFC_" + id, "displayType", "Text Box", "createContext", "No",
//                "contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
//                "contextAPIName", "DPKCAPI"
// 
//        };
//        DFFUtils.navigateToDFFtf();
//        DFFUtils.createSegment(arges);
//    }
// 
//    @Test(description = "TABLE : Create sensitive segment - Independent/Character", dependsOnMethods = { "testcase05" })
//    public void testcase22() {
// 
//        String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFINCHAR_" + id,
//                "dffCode", "GLDFFINCHAR_CODE" + id, "dffAPIName", "GLDFFINCHARAPI" + id, "dataType", "Character",
//                "valueSetCode", "INDCHAR_" + id, "promtName", "GLDFFINCHAR_" + id, "displayType", "Drop-down List",
//                "createContext", "Yes", "contextCodeNameSearch", "GLDPK_CCODE"+id, "contextDisplayName", "GLDFFCON_NAME" + id,
//                "contextCodeName", "GLDFFCON_CCODE" + id, "contextAPIName", "GLDFFCONCAPI" + id
// 
//        };
// 
//        DFFUtils.navigateToDFFtf();
//        DFFUtils.createSegment(arges);
//    }
// 
//    @Test(description = "TABLE : Create sensitive segment - Dependent/Character", dependsOnMethods = { "testcase06" })
//    public void testcase23() {
// 
//        String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFDEPCHAR_" + id,
//                "dffCode", "GLDFFDEPCHAR_CODE" + id, "dffAPIName", "GLDFFDEPCHARAPI" + id, "dataType", "Character",
//                "valueSetCode", "DEPCHAR" + id, "promtName", "GLDFFDEPCHAR_" + id, "displayType", "List of Values",
//                "createContext", "No", "contextCodeNameSearch", "GLDFFCON_CCODE" + id, "contextDisplayName", "GLDPK_NAME11",
//                "contextCodeName", "GLDPK_CCODE11", "contextAPIName", "GLDPKCAPI11"
// 
//        };
// 
//        DFFUtils.navigateToDFFtf();
//        DFFUtils.createSegment(arges);
//    }
// 
//    @Test(description = "TABLE : Create sensitive segment - SubSet/Character", dependsOnMethods = { "testcase07" })
//    public void testcase24() {
// 
//        String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFSUB" + id,
//                "dffCode", "GLDFFSUB_CODE" + id, "dffAPIName", "GLDFFSUBAPI" + id, "dataType", "Character", "valueSetCode",
//                "SUBCHAR" + id, "promtName", "GLDFFSUB" + id, "displayType", "Pop-up List of Values", "createContext",
//                "No", "contextCodeNameSearch", "GLDFFCON_CCODE" + id, "contextDisplayName", "GLDPK_NAME111",
//                "contextCodeName", "GLDPK_CCODE111", "contextAPIName", "GLDPKCAPI111"
// 
//        };
// 
//        DFFUtils.navigateToDFFtf();
//        DFFUtils.createSegment(arges);
//    }
// 
//    @Test(description = "TABLE : Create sensitive segment - Table/Character", dependsOnMethods = { "testcase11" })
//    public void testcase25() {
// 
//        String arges[] = { "dffFlexCode", "GL_JE_LINES", "segmentType", "sensitive", "dffName", "GLDFFTAB" + id,
//                "dffCode", "GLDFFTAB_CODE" + id, "dffAPIName", "GLDFFTABAPI" + id, "dataType", "Character", "valueSetCode",
//                "TABLE_" + id, "promtName", "GLDFFTAB" + id, "displayType", "List of Values", "createContext", "No",
//                "contextCodeNameSearch", "GLDFFCON_CCODE" + id, "contextDisplayName", "DPK_NAME111", "contextCodeName",
//                "DPK_CCODE111", "contextAPIName", "DPKCAPI111"
// 
//        };
// 
//        DFFUtils.navigateToDFFtf();
//        DFFUtils.createSegment(arges);
//        System.out.println("");
//    }
//	
//	
//	
////	@Test(description="Deploy FlexField : PER_PERSONS_DFF")
////	public void testcase26(){
////		DFFUtils.navigateToDFFtf();
////		DFFUtils.searchDFFCode("PER_PERSONS_DFF");
////		DFFUtils.deployFlex();
////	}
//
//
//	@AfterClass(alwaysRun = true)
//	public void tearDown() throws Exception {
//		CommonUtils.logout();
//	}
//
//	// @Test(description = "Create Dependent-Number valueSet")
//	// public void testcase011() {
//	// ManaageValueSetsUtil.createValueSet("ValueSetCode", "DPKDEPNUM1",
//	// "ValueSetDescription", "QATestVSDesc13",
//	// "ValueSetModule", "Oracle M%", "ValidationType", "Dependent",
//	// "ValueType", "Number","IndValueSetCode", "ABCD", "ManageValues", "4",
//	// "Value1", "10.00",
//	// "IndValue1", "10.10","Value2", "10.11","IndValue2", "10.10","Value3",
//	// "20.00", "IndValue3", "20.02","Value4", "20.22", "IndValue4", "20.02");
//	// }
//
//	// @Test(description = "Create Independent-Date valueSet")
//	// public void testcase08() {
//	// ManaageValueSets.createValueSet("ValueSetCode", "DPKINDDATE_"+id,
//	// "ValueSetDescription", "QATestVSDesc12",
//	// "ValueSetModule", "Oracle M%", "ValidationType", "Independent",
//	// "ValueType", "Date", "ManageValues", "2", "Value1", "1/1/18", "Value2",
//	// "1/1/25");
//	// }
//
//	// @Test(description = "Create Dependent-Date valueSet")
//	// public void testcase09() {
//	// ManaageValueSets.createValueSet("ValueSetCode", "DPKDEPDATE",
//	// "ValueSetDescription", "QATestVSDesc13",
//	// "ValueSetModule", "Oracle M%", "ValidationType", "Dependent",
//	// "ValueType", "Date","IndValueSetCode", "DPKINDDATE_1902010207",
//	// "ManageValues", "4", "Value1", "1/1/19",
//	// "IndValue1", "1/1/18","Value2", "1/1/20","IndValue2", "1/1/18","Value3",
//	// "1/1/19", "IndValue3", "1/1/25","Value4", "1/1/20", "IndValue4",
//	// "1/1/25");
//	// }
//
//		
//
//}
