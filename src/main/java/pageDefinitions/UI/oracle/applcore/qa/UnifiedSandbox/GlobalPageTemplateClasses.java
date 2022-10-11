/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * @author VAGOPU
 *
 */
public class GlobalPageTemplateClasses {
	
	public GlobalPageTemplateClasses(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
		@FindBy(xpath="//a[contains(@id , 'peClose')]")
	public  WebElement GlobalPageTemplateComposerExit;
	
	@FindBy(xpath="//div[contains(@id , 'peDialo::_ttxt')]")
	public WebElement GTCEditDiv;
	
	@FindBy(xpath="//tr[contains(@id , '22:j_id__ctru4pc')]/descendant::img[contains(@id , 'cil1::icon')]")
	public WebElement GTCSearchImageIcon;
	
	@FindBy(xpath="//tr[contains(@id , '__ctru32pc10')]")
	public WebElement GTCSearchImageIcon_eBuilder;
	
	@FindBy(xpath="//textarea[contains(@id , 'ctru15pc14::content')]")
	public WebElement GTCSearchImageIcon_textArea;
	
	@FindBy(xpath="//button[contains(@id , 'ctru19pc8::ok')]")
	public WebElement GTCSearchImageIcon_okBtn;
	
	@FindBy(xpath="//button[contains(@id , 'peApply')]")
	public WebElement GTCEditDiv_apply;
	
	@FindBy(xpath="//button[contains(@id , 'peDialo::ok')]")
	public WebElement GTCEditDiv_okBtn;
	
	@FindBy(xpath="//button[contains(@id , '_shEdD1::yes')]")
	public WebElement GTC_CustomEdit_Confirm;
	
	@FindBy(xpath="//span[contains(@id , 'pt1:_UISusbtwm')]")
	public WebElement GTC_PreviewmodeDialog;
	
	@FindBy(xpath="//button[contains(@id , 'pt1:_UISusbwdt::ok')]")
	public WebElement GTC_PreviewmodeDialog_Ok;
	
	
	
	public void viewlink(WebDriver driver){
		WebElement viewlink=driver.findElement(By.linkText("View"));
		CommonUtils.waitForElement(viewlink,driver);
		viewlink.click();
	}
	 
	public void sourcelink(WebDriver driver){
		WebElement sourcelink=driver.findElement(By.xpath("//tr[@id='pt1:tbCodeMenu']/td[2]"));
		CommonUtils.waitForElement(sourcelink,driver);
		sourcelink.click();
	} 
	
	public void imagelogo(WebDriver driver){
		WebElement imagelogo=driver.findElement(By.id("pt1:_UIScil1u"));
		CommonUtils.waitForElement(imagelogo,driver);
		imagelogo.click();
	}
	
	public void Customizeediticon(WebDriver driver){
		WebElement Customizeediticon=driver.findElement(By.xpath("//img[contains(@id,'pt1:pePanel:0:curPanl:0:sn_prop::icon')]"));
				// "//img[@id='pt1:pePanel:0:curPanl:0:sn_prop::icon']"));
				//img[contains(@id,'pt1:pePanel:0:curPanl:0:sn_prop::icon']")
		                                                             
		CommonUtils.waitForElement(Customizeediticon,driver);
		if(Customizeediticon.isEnabled()==true){
			Customizeediticon.click();
			//Log.info("Editable");
			System.out.println("Editable");
		}else{
			//Log.info("Not able to Edit");
			System.out.println("Not able to Edit");
		}
		
	}	
	
	public void styletab(WebDriver driver){
		WebElement styletab=driver.findElement(By.xpath("//div[contains(@id , 'comppi::tabh::cbc')]/descendant::a[text() = 'Style']"));
		CommonUtils.waitForElement(styletab,driver);
		styletab.click();
	}	
	
	public void backgroundimage(WebDriver driver){
		WebElement backgroundimage=driver.findElement(By.xpath("//label[text()='Background Image']/following::input[1]"));
				//id("pt1:pePanel:0:peDiReg:1:comppir2:0:j_id__ctru16pc11::content"));
		CommonUtils.waitForElement(backgroundimage,driver);
		backgroundimage.click();
		backgroundimage.clear();
		Actions builder = new Actions(driver);
        Actions seriesOfActions = builder.moveToElement(backgroundimage).click().sendKeys(backgroundimage, "http://aseng-wiki.us.oracle.com/asengwiki/images/logo/default-space-logo-48.png");
        seriesOfActions.perform();
        //String WelImg = backgroundimage.getCssValue("background-image");
        //Assert.assertTrue(WelImg.contains("http://aseng-wiki.us.oracle.com/asengwiki/images/logo/default-space-logo-48.png"));
	}
		
	
	public void iconedit(WebDriver driver){
		WebElement iconedit=driver.findElement(By.xpath("//img[contains(@id, '22:cil1::icon')]"));
	                                                                           
		CommonUtils.waitForElement(iconedit,driver);
		iconedit.click();
	}
	  
	
	public void expressionbuilder(WebDriver driver){
		WebElement expressionbuilder=driver.findElement(By.xpath("//td[contains(text() , 'Expression Builder...')]"));
		CommonUtils.waitForElement(expressionbuilder,driver);
		expressionbuilder.click();
	}
	
	public void expressioneditor(String UpdatableText,WebDriver driver){
		WebElement expressioneditor=driver.findElement(By.xpath("//textarea[contains(@id , 'ctru15pc14::content')]"));
		CommonUtils.waitForElement(expressioneditor,driver);
		expressioneditor.click();
		expressioneditor.clear();
		expressioneditor.sendKeys(UpdatableText);
	}
	
	public void expressioneditortestbutton(WebDriver driver){
		WebElement expressioneditortestbutton=driver.findElement(By.xpath("//button[contains(@id , 'ctru19pc8::ok')]/ancestor::tr[1]/descendant::button[contains(@id , 'test')]"));
		CommonUtils.waitForElement(expressioneditortestbutton,driver);
		expressioneditortestbutton.click();
	}
	
	public void expressioneditorokbutton(WebDriver driver){
		WebElement expressioneditorokbutton=driver.findElement(By.xpath("//button[contains(@id , 'ctru19pc8::ok')]"));
		CommonUtils.waitForElement(expressioneditorokbutton,driver);
		expressioneditorokbutton.click();
	}
	
	 
	
	  public void pagecomposerstructure(WebDriver driver){
		WebElement pagecomposerstructure=driver.findElement(By.id("_FOpt1:dsv_src"));
		CommonUtils.waitForElement(pagecomposerstructure,driver);
		pagecomposerstructure.click();
	}
	
	  
	    
		  public void  pagecomposerpopupclose(WebDriver driver){
		 WebElement pagecomposerpopupclose=driver.findElement(By.xpath("//a[contains(@id,'pt1:peClose')]"));
				 //id("pt1:peClose"));
		  CommonUtils.waitForElement(pagecomposerpopupclose,driver);
		  pagecomposerpopupclose.click();
		//a[contains(@id,'pt1:peClose')]

	 }
	  public void preferencespage(String str,WebDriver driver){
		  WebElement Preferencepage=driver.findElement(By.xpath("//a[contains(text(),'"+str+"')]"));
		  //CommonUtils.waitForElement(Preferencepage);
		  CommonUtils.hold(8);

		  	//Assert.assertEquals(true, Preferencepage.isDisplayed());

		  	Preferencepage.click(); 
	 }
	  
	  
	  public void pagecomposerediticon(WebDriver driver){
	WebElement pagecomposerediticon=driver.findElement(By.xpath("//button[text()='Edit']"));
	CommonUtils.waitForElement(pagecomposerediticon,driver);
	pagecomposerediticon.click();
	  }
	  
	
	public void customizedropdown(String str,int i,WebDriver driver){
		  WebElement customizedropdown=driver.findElement(By.xpath("//label[contains(text(),'"+str+"')]/following::img["+i+"]"));
		  CommonUtils.waitForElement(customizedropdown,driver);

		  	Assert.assertEquals(true, customizedropdown.isDisplayed());

		  	customizedropdown.click(); 
		
	}
	
	public void customizepageexpressionbuilder(String st,WebDriver driver){
		  WebElement customizepageexpressionbuilder=driver.findElement(By.xpath("//td[contains(text(),'"+st+"')]"));
		  CommonUtils.waitForElement(customizepageexpressionbuilder,driver);
		  customizepageexpressionbuilder.click();
		
	}
	
	 
	  public void pagecomposertextareaeditor(String UpdatableText,WebDriver driver){
		  WebElement pagecomposertextareaeditor=driver.findElement(By.xpath("//textarea[contains(@id,'content')]"));
		  CommonUtils.waitForElement(pagecomposertextareaeditor,driver);
		  pagecomposertextareaeditor.click();
		  pagecomposertextareaeditor.clear();
		  pagecomposertextareaeditor.sendKeys(UpdatableText);
		
	  }
	
	
	 
	  public void pagecomposertextareatestbutton(WebDriver driver){
		  WebElement pagecomposertextareatestbutton=driver.findElement(By.xpath("//button[contains(text(),'Test')]"));
		  CommonUtils.waitForElement(pagecomposertextareatestbutton,driver);
		  pagecomposertextareatestbutton.click();
	  }
	
	
	  public void pagecomposertextareaokbutton(WebDriver driver){
		  WebElement pagecomposertextareaokbutton=driver.findElement(By.xpath("//button[text() = 'Test']/following-sibling::button[text() = 'OK']"));
		  CommonUtils.waitForElement(pagecomposertextareaokbutton,driver);
		  pagecomposertextareaokbutton.click(); 
	  }
	  
	  public void pagecomposercustomerlayer(int i,WebDriver driver){
		  //WebElement   pagecomposercustomerlayer=driver.findElement(By.name("tipTable"));
		  //CommonUtils.waitForElement(pagecomposercustomerlayer);
		  //pagecomposercustomerlayer.click();
		  CommonUtils.hold(10);
		  List<WebElement> oRadioButton  = driver.findElements(By.name("tipTable"));
		  
		
			  oRadioButton.get(i).click();
	  }
	 
	  public void pagecomposercustomerlayerokbutton(WebDriver driver){
		  WebElement pagecomposercustomerlayerokbutton=driver.findElement(By.xpath("//button[contains(@id,'FOpt1:USgr3:0:r1:0:okABtn')]"));
		  CommonUtils.waitForElement(pagecomposercustomerlayerokbutton,driver);
		  pagecomposercustomerlayerokbutton.click(); 
	  }
	  
	  public void pagecomposerselect(WebDriver driver){
			WebElement pagecomposerselect=driver.findElement(By.id("_FOpt1:dsv_comp"));
			CommonUtils.waitForElement(pagecomposerselect,driver);
			pagecomposerselect.click();
		}
	  
	  public void salesglobaldetails(WebDriver driver){
		  WebElement salesglobaldetails=driver.findElement(By.id("_FOpt1:_FOr1:0:_FOSritemNode_sales_quotas:0:_FOTsr1:0:pt1:AP1:sdi3::disAcr"));
			CommonUtils.waitForElement(salesglobaldetails,driver);
			salesglobaldetails.click();
	  }


	  public void pagecomposereditcomponent(WebDriver driver){
		  WebElement pagecomposereditcomponent=driver.findElement(By.xpath("//a[@id='_FOpt1:pePanel:0:cil1']/span"));
			CommonUtils.waitForElement(pagecomposereditcomponent,driver);
			pagecomposereditcomponent.click();
	  }
	  
	  public void salescomponenteditcontent(String UpdatableText,WebDriver driver){
		  WebElement salescomponenteditcontent=driver.findElement(By.xpath("//input[contains(@id,'FOpt1:pePanel:0:peDiReg:1:comppir0:0:text::content')]"));
		  CommonUtils.waitForElement(salescomponenteditcontent,driver);
		  salescomponenteditcontent.click();
		  salescomponenteditcontent.clear();
		  salescomponenteditcontent.sendKeys(UpdatableText);
	  }
	  
	  public WebElement customizePagesLink(WebDriver driver) {
		  WebElement CustomizePagesEle=driver.findElement(By.xpath("//a[contains(@id,'pt1:_UIScmi3')]"));
		  
		  return CustomizePagesEle;
	  }
}
