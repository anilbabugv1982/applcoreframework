package UI.tests.Flex.EFF;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;


/*
 * 1) Default Circuit Board --> Segment - Box' display type is "Drop-down List", as part of testing, it will be changed to "List of Values" and reverted back at the end.
 * 2) A new Segment "Colors" of type - Character, Value set - COLOR_VS (pre-created), display type - "Drop-down list" is created, deployed and validated at runtime. It is deleted at the end and redeployed.
 * */


public class EFFPIMUpdateDelete extends GetDriverInstance {
	
	private WebDriver effPIMUpdDelTestDriver;
	private EFFUtils effUtils;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	String itemUsagePage = null;
	String itemPageClassName = null;
	String pimPrimaryItemUsagePage = null;
	String contextCode = null;
	String existingSegment = null;
	String newSegment = null;
	String existingDisplayType = null;
	String existingItemName = null;
	String itemUsage = null;
	String currentSegmentDisplayType = null;
	String modifiedSegmentDisplayType = null;
	String flexfield = null;
	String flexCode = null;
	String newDisplayType = null;
	String oldDisplayType = null;
	
	@BeforeClass
	public void startUP() throws Exception {
		
		try{
		       itemUsagePage="PD Page";
		        itemPageClassName="Circuit Board";
		        pimPrimaryItemUsagePage="PD Page";
		        existingSegment="Box";
		        newSegment="Colors";
		        existingItemName="Circuit Board";
		        itemUsage="Item";
		        flexCode="EGO_ITEM_EFF";
		        contextCode="PLM_Packaging Information";
		        newDisplayType="List of Values";
		        oldDisplayType="Drop-down List";
		        effPIMUpdDelTestDriver = getDriverInstanceObject();
				dep = new Deployment(effPIMUpdDelTestDriver);
				pimRunTime = new RuntimeValidation(effPIMUpdDelTestDriver);
				aLoginInstance = new ApplicationLogin(effPIMUpdDelTestDriver);
			    ntFInstance = new NavigationTaskFlows(effPIMUpdDelTestDriver);
			    nMEInstance = new NavigatorMenuElements(effPIMUpdDelTestDriver);
			    effUtils = new EFFUtils(effPIMUpdDelTestDriver);
			   // dbConnection = new DbResource(effPIMTest);
//				deleteSegment();
				 
		        aLoginInstance.login("pimqa", "Welcome1",effPIMUpdDelTestDriver);
		        CommonUtils.hold(5);
		}catch(Exception ex){
			System.out.println("EFF Error message = ===== : "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/*@Test (description="Check whether the preconfigured setup data displayed in existing Item or not")
	public void checkRunTimeSegmentValues() throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMUpdDelTestDriver);
		pimRunTime.openManageItems();
        Assert.assertTrue(pimRunTime.basicManageItemSearch(itemPageClassName, existingItemName), "validation of Basic Item search is successful");
        pimRunTime.openItem(existingItemName);
        Assert.assertTrue(pimRunTime.navigateToPage(itemUsage, pimPrimaryItemUsagePage), "validation of Basic Item search is successful");
        pimRunTime.checkSegmentDisplayed(existingSegment);
        currentSegmentDisplayType=pimRunTime.getAttributeDisplayType(existingSegment);
        System.out.println("Current Display type for Segment -"+existingSegment+" is - "+currentSegmentDisplayType);
	}*/
	
	@Test(/*dependsOnMethods= {"checkRunTimeSegmentValues"},*/description = "Modify Segment display type in existing Context and Create a new Segment")
	public void modifyExistingSegmentDisplay() throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMUpdDelTestDriver);
        ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields", effPIMUpdDelTestDriver);
        CommonUtils.hold(5);
        Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
        Assert.assertEquals(dep.searchContextCode(contextCode), true, "Context code search successful");
        dep.editSegment(existingSegment);
        dep.modifySegmentDisplayType(newDisplayType);
        dep.createSegment(newSegment, "Character", "COLOR_VS", "Single Row", oldDisplayType, false);
        dep.navigateToCategoriesPage();
        dep.saveAndCloseButtonUnderCategoryPages();
        Assert.assertEquals(dep.deployFlexFieldIncremental(flexCode, effPIMUpdDelTestDriver), true);
        aLoginInstance.logout(effPIMUpdDelTestDriver);
	}
	
	@Test(dependsOnMethods= {"modifyExistingSegmentDisplay"},description = "Validate modified Display type for Segment")
	public void validateModifiedSegmentDisplayRunTime() throws Exception {
		aLoginInstance.login("pimqa", "Welcome1",effPIMUpdDelTestDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMUpdDelTestDriver);
        pimRunTime.openManageItems();
        Assert.assertTrue(pimRunTime.basicManageItemSearch(itemPageClassName, existingItemName), "validation of Basic Item search is successful");
        pimRunTime.openItem(existingItemName);
        Assert.assertTrue(pimRunTime.navigateToPage(itemUsage, pimPrimaryItemUsagePage), "validation of Basic Item search is successful");
        pimRunTime.checkSegmentDisplayed(existingSegment);
        modifiedSegmentDisplayType=pimRunTime.getAttributeDisplayType(existingSegment);
        Assert.assertEquals(modifiedSegmentDisplayType, "LOV","TEST PASS - Modified Display type - List of Values for Segment - "+existingSegment);
	}
	
	@Test(dependsOnMethods= {"validateModifiedSegmentDisplayRunTime"},description = "Validate modified Display type for Segment")
	public void validateModifiedSegmentValueRunTime() throws Exception {
		Assert.assertEquals(pimRunTime.getAttributeValue(existingSegment), "Large","Segment value is intact after Display type is modified");
	}
	
	@Test(dependsOnMethods= {"validateModifiedSegmentValueRunTime"},description = "Validate modified value for Segment at Runtime")
	public void updateExistingSegmentValue() {
		pimRunTime.modifySegmentValueRunTimeForSeedData();
		Assert.assertEquals(pimRunTime.getAttributeValue(existingSegment), "Medium");
	}
	
	@Test(dependsOnMethods= {"updateExistingSegmentValue"},description = "Validate newly created Segment at Runtime")
	public void validateNewlyCreatedSegmentRuntimeUnderExistingContext() {
		Assert.assertTrue(pimRunTime.selectRunTimeDropDownAttributeValue(newSegment, "BLACK"));
		Assert.assertEquals(pimRunTime.getAttributeValue(newSegment),"BLACK");
		pimRunTime.itemSaveAndClose();
	}
	
	@Test(dependsOnMethods= {"validateNewlyCreatedSegmentRuntimeUnderExistingContext"},description = "Delete the newly created segment from setup, reset the existing Segment and deploy flex")
	public void deleteNewSegmentFromSetup() throws Exception {
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMUpdDelTestDriver);
        ntFInstance.navigateToAOLTaskFlows("Manage Extensible Flexfields", effPIMUpdDelTestDriver);
        CommonUtils.hold(5);
        Assert.assertEquals(dep.searchFlexFieldCode(flexCode), true, "Flex Code Result - "+flexCode);
        Assert.assertEquals(dep.searchContextCode(contextCode), true, "Context code search successful");
        dep.editSegment(existingSegment);
        dep.modifySegmentDisplayType(existingDisplayType);
        dep.deleteSegment(newSegment);
        Assert.assertFalse(dep.searchSegment(newSegment),"Segment - "+newSegment+" deletion is successful");
        dep.navigateToCategoriesPage();
        dep.saveAndCloseButtonUnderCategoryPages();
        Assert.assertEquals(dep.deployFlexFieldIncremental(flexCode, effPIMUpdDelTestDriver), true);
        aLoginInstance.logout(effPIMUpdDelTestDriver);
	}
	
	@Test(dependsOnMethods= {"deleteNewSegmentFromSetup"},description = "Validate whether deleted Segment reflected Runtime or not")
	public void validateDeleteSegmentRunTime() throws Exception {
		aLoginInstance.login("pimqa", "Welcome1",effPIMUpdDelTestDriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMUpdDelTestDriver);
        pimRunTime.openManageItems();
        Assert.assertTrue(pimRunTime.basicManageItemSearch(itemPageClassName, existingItemName), "validation of Basic Item search is successful");
        pimRunTime.openItem(existingItemName);
        Assert.assertTrue(pimRunTime.navigateToPage(itemUsage, pimPrimaryItemUsagePage), "validation of Basic Item search is successful");
        Assert.assertFalse(pimRunTime.checkSegmentDisplayed(newSegment),"Deleted Segment no more displayed");
	}
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMUpdDelTestDriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMUpdDelTestDriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMUpdDelTestDriver);
		}finally {
			try {
				effPIMUpdDelTestDriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
