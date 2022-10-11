/**
 * 
 */
package UtilClasses.UI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author klalam
 *
 */
public class NavigatorMenuElements {
	
	public NavigatorMenuElements(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(@id , 'UISnvr:0:nvcl1')]")
	public WebElement showMoreOrLessLink;
	
	@FindBy(xpath = "//*[contains(@id , 'nvcil1::icon')]")
	public WebElement nMenuClose;

	@FindBy(xpath="//a[contains(@id,'_setup_and_maintenance')]")
	public WebElement SetupAndMaintenance;
	
	@FindBy(xpath = "//span[contains(text(),'Personal Information')]")
	public WebElement PersonalInformation;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ATK_HOMEPAGE_FUSE_SOCIAL')]/span")
	public WebElement Social;
	
	//List of child menu items under "Sales" Parent menu
	@FindBy(xpath = "//a[contains(@id,'nv_MOO_OPPTYMGMTOPPORTUNITIES_CRM_CARD')]/span")
	public WebElement Opportunities;
	
	@FindBy(xpath = "//a[@id = 'nv_ZCM_CUSTOMERCTRINFRA360_CUSTOMERS_CRM_CARD']")
	public WebElement Accounts;
	
	@FindBy(xpath = "//a[@id = 'nv_ATK_HOMEPAGE_FUSE_REPORTS']")
	public WebElement Analytics;
	
	@FindBy(xpath = "//a[@id = 'nv_MOT_SALESTERRMGMTTERRITORIES_CRM_CARD']")
	public WebElement Territories;
	
	@FindBy(xpath = "//a[@id = 'nv_itemNode_sales_quotas']")
	public WebElement Quotas;
	
	@FindBy(xpath = "//a[@id = 'nv_itemNode_sales_competitors']")
	public WebElement Competitors;
	
	
	//List of child menu items under "Tools" Parent menu 
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_Tools_Preferences')]")
	public WebElement SetPreferences;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ATK_HOMEPAGE_FUSE_ANNOUNCEMENTS')]")
	public WebElement Annoucements;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_rest_alerts_composer')]")
	public WebElement AlertComposer;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_DeepLink_ManageDeepLinksforHCM')]")
	public WebElement DeepLink;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ATK_FUSE_DEVELOPER_ANNEX')]")
	public WebElement DeveloperConnect;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ATK_HOMEPAGE_FUSE_WORKLIST')]")
	public WebElement WorkList;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_spaces_spaces')]")
	public WebElement Spaces;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_ContactSearch')]")
	public WebElement ConnectSearch;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_reports_and_analytics')]")
	public WebElement ReportAndAnalytics;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_scheduled_processes_fuse_plus')]")
	public WebElement SechduledProcess;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_ImportManagement')]")
	public WebElement ImportManagement;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_file_import_and_export')]")
	public WebElement FileExportAndImport;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_audit_reports')]")
	public WebElement AuditReports;
	
	@FindBy(xpath = "//a[contains(@id , 'pt1:j_id108')]")
	public WebElement DownloadDesktopIntegration;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_SmartText')]")
	public WebElement SmartText;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_Tools_TransactionConsole_TransactionAdministratorConsol')]")
	public WebElement TransactionConsole;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ASE_FUSE_SECURITY_CONSOLE')]")
	public WebElement SecurityConsole;
		
	//List of child menu items under "Configuration" Parent menu 
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_sandboxes')]")
	public WebElement Sandboxes;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_migration_fnd1')]")
	public WebElement Migration;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ATK_HOMEPAGE_FUSE_SETTINGS')]")
	public WebElement Appearacne;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_ATK_HOMEPAGE_FUSE_STRUCTURE')]")
	public WebElement Structure;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_user_interface_text')]")
	public WebElement UserInterfaceText;
	
	@FindBy(xpath = "//a[@id = 'nv_itemNode_Tools_ConfigureBusinessObjectsforHCM1']")
	public WebElement BuisinessObjects;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_application_composer')]")
	public WebElement ApplicationComposer;
	
	@FindBy(xpath = "//a[contains(@id , 'nv_itemNode_tools_new_pages')]")
	public WebElement PageIntegrationWizard;
	
	@FindBy(xpath="//a[contains(@id,'nv_itemNode_procurement_negotiations')]/span")
	public WebElement navigatorProcurementNegotiations;
	
	@FindBy(xpath = "//a[contains(@id,'nv_itemNode_procurement_PurchaseOrders')]/span")
	public WebElement navigatorPurchaseOrder;

}
