package pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver.Unique;

public class PanelSectionEditPage {
	private WebDriver driver;
	
	public PanelSectionEditPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public static String UniqueID = CommonUtils.uniqueId();
	
	
	@FindBy(xpath ="//select[contains(@id,':AP2:AT4:_ATp:ATt4')]")
	public static WebElement profileValue2;
	
	@FindBy(xpath ="//input[contains(@id,':AP2:AT4:_ATp:ATt4')]")
	public static WebElement profileValue1;
	
	@FindBy(xpath ="//button[text()='Save']")
	public static WebElement save;
	
	//@FindBy(xpath = "//span[contains(text(),'Contact Info')]")
	@FindBy(xpath = "//span[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:lp1Upl:UPsp1:i2:3:tb1:TBot4')]")
	public static WebElement contactInfo;
	
	@FindBy(xpath="//span[contains(@id,'lp1Upl:UPsp1:i2:2:tb1:TBot4')]")
	public static WebElement indentificationInfo;
	
	@FindBy(xpath="//div[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:PCEcil1')]")
	public static WebElement addCitizenShip;
	
	@FindBy(xpath = "//span[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:lp1Upl:UPsp1:i2:0:tb1:TBot4')]")
	public WebElement personalDetails;
	
	@FindBy(xpath = "//a[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:commRgn:0:commPce:phonPce:phoneLv:0:phnePse:PSEcil6')]")
	public static WebElement editHomePhone;
	
	@FindBy(xpath="//div[contains(@id,'docsUpl:UPsp1:ciRnPce:PCEcil1')]//a//span[contains(text(),'Add')]")
	public static WebElement addCitizenship;
	
	@FindBy(xpath = "//tr[td[div[div[div[div[div[div[div[div[div[table[tbody[tr[td[div[div[div[div[*[text()='Home Phone']]]]]]]]]]]]]]]]]]]]//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:commRgn:0:commPce:phonPce:phoneLv') and @title='Edit']")
	public static WebElement editHomePhone1;
	
	
	@FindBy(xpath = "//div//a//span/span[contains(text(),'C')]")
	public static WebElement cancelHomePhone;
	
	@FindBy(xpath = "//input[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:commRgn:0:commPce:phonPce:phoneLv:0:phnePse:pNumInp::content')]")
	public static WebElement inputHomephoneNumer;
	
	@FindBy(xpath="//div//a//span/span[contains(text(),'m')]")
	public static WebElement submitButton;
	
	@FindBy(xpath="//img[contains(@id,'pDtlUpl:UPsp1:nmRnPce:PCEcil2::icon')]")
	public static WebElement collapseNameSection;
	
	@FindBy(xpath="//img[contains(@id,'pDtlUpl:UPsp1:nmRnPce:PCEcil2::icon')]")
	public static WebElement expandNameSection;
	
	@FindBy(xpath="//div[conatains(@id,'docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:PSEcb2')]")
	public static WebElement submitcitizenship;
	
	@FindBy(xpath="//span[contains(text(),'Identification Info')]")
	public static WebElement idf;
	
	@FindBy(xpath="//span[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:lp1Upl:UPsp1:i2:2:tb1:TBot4')]")
	public static WebElement IdentificationInfo;
	
	@FindBy(xpath="//span(contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:docsUpl:UPsp1:ciRnPce:PCEcil1'))")
	public static WebElement addcitizenship;
	
	@FindBy(xpath="//div//a//span/span[contains(text(),'C')]")
	public static WebElement homeCanelButton;
	
	@FindBy(xpath="//div//input[contains(@id,':addrPse:a1:AddStIt:0:adL1Inp::content')]")
	public static WebElement addressLine1;
	
	@FindBy(xpath="//span//input[contains(@id,':geo4Sis:geo4Srh::content')]")
	public static WebElement city;
	
	@FindBy(xpath="//input[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:addrsLv:0:addrPse:PSEcb2')]")
	public static WebElement submitHomeAddressButton;
	
	@FindBy(xpath="//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:PCEcil2::icon')]")
	public static WebElement collapseHomeAddressButton;
	
	@FindBy(xpath="//div//a//span//span[contains(text(),'m')]")
	public static WebElement AddressSubmit;
	
	@FindBy(xpath="//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:PCEcil2::icon')]")
	public static WebElement expandHomeAddressButton;
	
	@FindBy(xpath="//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:0:r1:0:pDtlUpl:UPsp1:nmRnPce:PCEcil2::icon')]")
	public static WebElement expandNameInfoButton;
	
	@FindBy(xpath="//input[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:addrsLv:0:addrPse:a1:AddStIt:8:fNumInp::content')]")
	public static WebElement floor;
	
	@FindBy(xpath="//div//a//span//span[contains(text(),'C')]")
	public static WebElement CancelName;
	
	
	@FindBy(xpath="//div//input[contains(@id,':NamePse:iPNGEd:0:ELNInp::content')]")
	public static WebElement lastName;
	
	@FindBy(xpath="//input[contains(@id,'pDtlUpl:UPsp1:nmRnPce:nameRgn:0:NamePce:namesLv:0:NamePse:iPNGEd:1:EFNInp::content')]")
	public static WebElement firstName;
	
	@FindBy(xpath="//input[contains(@name,'pDtlUpl:UPsp1:nmRnPce:nameRgn:0:NamePce:namesLv:0:NamePse:PSEcb2')]")
	public static WebElement suffixName;
	
	@FindBy(xpath="//div//a//span//span[contains(text(),'m')]")
	public static WebElement SubmitName;
	
	@FindBy(xpath="//input[contains(@id,'pDtlUpl:UPsp1:nmRnPce:nameRgn:0:NamePce:namesLv:0:NamePse:iPNGEd:3:EMNInp::content')]")
	public static WebElement middleName;
	
	@FindBy(xpath="//a[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:addrsLv:0:addrPse:adEsdDt::glyph')]")
	public static WebElement Calendericon;
	
	@FindBy(xpath="//a[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:addrsLv:0:addrPse:adEsdDt::pop::cd::nm')]")
	public static WebElement nextmonth;
	
	@FindBy(xpath="//div//a//img[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:0:r1:0:contUpl:UPsp1:commRgn:0:commPce:PCEcil2::icon']")
	public static WebElement collapseCommunication;
	
	@FindBy(xpath="//div//a//img[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:0:r1:0:contUpl:UPsp1:commRgn:0:commPce:PCEcil2::icon']")
	public static WebElement expandCommunication;
	
	@FindBy(xpath="//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:pARnPce:pAddRgn:1:pAddPce:paddrLv:0:pAddPse:PSEcil6::icon')]")
	public static WebElement editMailingAddress;
	
	@FindBy(xpath="//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:pARnPce:pAddRgn:1:pAddPce:paddrLv:0:pAddPse:hETySel:_0')]")
	public static WebElement radiobuttonAddress;
	
	@FindBy(xpath = "//input[contains(@id,'it3::content')]")
	public WebElement profileValue;
	
	@FindBy(xpath = "//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:commRgn:0:commPce:phonPce:phoneLv:2:phnePse:PSEcil6::icon')]")
	public WebElement EditEmail;
	
	@FindBy(xpath="//input[contains(@id,'ctznPce:ctznpLv:0:ctznPse:eCtySel::content')]")
	public WebElement selectcitizenship;
	
	@FindBy(xpath ="//input[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:eCstSel::content')]")
	public WebElement selectCitizenshipStatus;
	
	@FindBy(xpath="//div[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:PSEcb2')]")
	public WebElement submitcitizenshipstatus;
	
	@FindBy(xpath="//div[conatins(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:PSEcb3')]")
	public WebElement cancelCitizenship;
	
	@FindBy(xpath="(//div[div[label[text()='Citizenship Status']]]//*[@class='AFPanelFormLayoutContentCell']//input)[1]")
	public WebElement cancelCitizenshipStatus;
	
	@FindBy(xpath="//div//a//span//span[contains(text(),'m')]")
	public WebElement citizenshipSubmit;
	
	@FindBy(xpath="input[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:eDtFmDt::content'] or @id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:1:ctznPse:eDtFmDt::content'")
	public WebElement fromdate;
	
	@FindBy(xpath="input[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:eDtFmDt::content'] or @id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:1:ctznPse:eDtFmDt::content'")
	public WebElement todate;
	
	@FindBy(xpath="input[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:eDtFmDt::content'] or @id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:1:ctznPse:eDtFmDt::content'")
	public WebElement citizenshipStatus;
	
	public void setAdminProfileForResponsiveUI(String value, WebDriver driver) throws Exception {
		profileValue2.clear();
		profileValue2.sendKeys(value);
		save.click();
			CommonUtils.hold(5);
			
		}
	
	public String getIdOfAddressChange(WebDriver driver) throws Exception {
		String addChange = driver.findElement(By.xpath("//input[contains(@id,':addrPse:adEsdDt::content')]")).getAttribute("id");
		Log.info("ID has"+addChange);
		return addChange;
		}

	public String getIdOfCity(WebDriver driver) throws Exception {
		String cityChange = driver.findElement(By.xpath("//span//input[contains(@id,':geo4Sis:geo4Srh::content')]")).getAttribute("id");
		Log.info("ID has"+cityChange);
		return cityChange;
		}
	
	
	public String getIdOfNameDate(WebDriver driver) throws Exception {
		String nameChange = driver.findElement(By.xpath("//div//input[contains(@id,':NamePse:ECDDt::content')]")).getAttribute("id");
		Log.info("ID has"+nameChange);
		return nameChange;
		}
	
	
	public String getIdOfcitizenshipDate(WebDriver driver) throws Exception {
		String citidate = driver.findElement(By.xpath("//span//input[contains(@id,':ctznPse:eCtySel::content')]")).getAttribute("id");
		Log.info("ID has"+citidate);
		return citidate;
		}
	
	public String getIdOfcitizenshipStatus(WebDriver driver) throws Exception {
		String citistatus = driver.findElement(By.xpath("//span//input[contains(@id,':ctznPse:eCstSel::content')]")).getAttribute("id");
		Log.info("ID has"+citistatus);
		return citistatus;
		}
	
	public String getId(WebDriver driver) throws Exception {
		String panelsecid = driver.findElement(By.xpath("//input[contains(@id,':NamePse:ECDDt::content')]")).getAttribute("id");
		Log.info("ID has"+panelsecid);
		return panelsecid;
		}
	
	public String getIDCitizenshipDropdown(WebDriver driver) throws Exception{
		Log.info("Inside get element");
		String fromDate = driver.findElement(By.xpath("//input[@name='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:0:r1:0:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:eDtFmDt']")).getAttribute("id");
		return fromDate;
		
	}
	
	
	public void SelectCitizenship(WebDriver driver) throws Exception {
		Select citizenship = new Select(driver.findElement(By.id("_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:1:ctznPse:eCtySel::drop")));
		citizenship.selectByVisibleText("Indian");
		}
	
	public void SelectCitizenshipStatus(WebDriver driver) throws Exception {
	Select citizenshipstatus = new Select(driver.findElement(By.xpath("//input[contains(@id,'docsUpl:UPsp1:ciRnPce:cizpRgn:0:ctznPce:ctznpLv:0:ctznPse:eCstSel::content')]")));
	citizenshipstatus.deselectByVisibleText("Active");
	}
	
	
	
	public String setHomePhoneNumber(WebDriver driver) throws Exception {
		Log.info("Unique ID is"+UniqueID);
		String phnNum = CommonUtils.uniqueId();    //input string
		String lastFourDigits = "";     //substring containing last 4 characters
		 
		if (phnNum.length() > 4) 
		{
		    lastFourDigits = phnNum.substring(phnNum.length() - 4);
		} 
		else
		{
		    lastFourDigits = phnNum;
		}
		 
		System.out.println(lastFourDigits);
		String homePhnNumber = "434-"+lastFourDigits;
		return homePhnNumber;
			
		}
	
	
	public void EditOptions(String value, WebDriver driver) throws Exception {
		WebElement editmenu = driver.findElement(By.xpath("//tr[td[div[div[div[div[div[div[div[div[div[table[tbody[tr[td[div[div[div[div[*[text()='"+value+"']]]]]]]]]]]]]]]]]]]]//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:commRgn:0:commPce:phonPce:phoneLv:0:phnePse:PSEcil6::icon') and @title='Edit']"));
		Log.info("inside EditOptions");
		editmenu.click();
			
		}
	
	public void EditAddress(String value, WebDriver driver) throws Exception {
		WebElement editmenu = driver.findElement(By.xpath("//tr[td[div[div[div[div[div[div[div[div[div[table[tbody[tr[td[div[div[div[div[*[text()='"+value+"']]]]]]]]]]]]]]]]]]]]//img[contains(@id,'_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:contUpl:UPsp1:addrRgn:0:addrPce:addrsLv:0:addrPse:PSEcil6::icon') and @title='Edit']"));
		editmenu.click();
			
		}
}
