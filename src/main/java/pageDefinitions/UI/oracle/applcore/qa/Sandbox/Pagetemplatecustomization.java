/**
 * 
 *//*
package oracle.applcore.qa.sandbox;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.setup.DriverInstance;

*//**
 * @author VAGOPU
 *
 *//*
public class Pagetemplatecustomization {
	public static void viewlink(){
		WebElement viewlink=DriverInstance.getDriverInstance().findElement(By.linkText("View"));
		CommonUtils.waitForElement(viewlink);
		viewlink.click();
	}
	 
	public static void sourcelink(){
		WebElement sourcelink=DriverInstance.getDriverInstance().findElement(By.xpath("//tr[@id='pt1:tbCodeMenu']/td[2]"));
		CommonUtils.waitForElement(sourcelink);
		sourcelink.click();
	} 
	
	public static void imagelogo(){
		WebElement imagelogo=DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScil1u"));
		CommonUtils.waitForElement(imagelogo);
		imagelogo.click();
	}
	
	public static void Customizeediticon(){
		WebElement Customizeediticon=DriverInstance.getDriverInstance().findElement(By.xpath("//img[contains(@id,'pt1:pePanel:0:curPanl:0:sn_prop::icon')]"));
				// "//img[@id='pt1:pePanel:0:curPanl:0:sn_prop::icon']"));
				//img[contains(@id,'pt1:pePanel:0:curPanl:0:sn_prop::icon']")
		                                                             
		CommonUtils.waitForElement(Customizeediticon);
		if(Customizeediticon.isEnabled()==true){
			Customizeediticon.click();
			//Log.info("Editable");
			System.out.println("Editable");
		}else{
			//Log.info("Not able to Edit");
			System.out.println("Not able to Edit");
		}
		
	}	
	
	public static void styletab(){
		WebElement styletab=DriverInstance.getDriverInstance().findElement(By.xpath("//a[text()='Style']"));
				//xpath("//table/tbody/tr[2]/td[2]/div/div/div/div[2]/div/div/div[3]/div/a"));
		CommonUtils.waitForElement(styletab);
		styletab.click();
	}	
	
	public static void backgroundimage(){
		WebElement backgroundimage=DriverInstance.getDriverInstance().findElement(By.xpath("//label[text()='Background Image']/following::input[1]"));
				//id("pt1:pePanel:0:peDiReg:1:comppir2:0:j_id__ctru16pc11::content"));
		CommonUtils.waitForElement(backgroundimage);
		backgroundimage.click();
		backgroundimage.clear();
		Actions builder = new Actions(DriverInstance.getDriverInstance());
        Actions seriesOfActions = builder.moveToElement(backgroundimage).click().sendKeys(backgroundimage, "http://aseng-wiki.us.oracle.com/asengwiki/images/logo/default-space-logo-48.png");
        seriesOfActions.perform();
        //String WelImg = backgroundimage.getCssValue("background-image");
        //Assert.assertTrue(WelImg.contains("http://aseng-wiki.us.oracle.com/asengwiki/images/logo/default-space-logo-48.png"));
	}
		
	
	public static void iconedit(){
		WebElement iconedit=DriverInstance.getDriverInstance().findElement(By.id("pt1:pePanel:0:peDiReg:1:comppir0:0:j_id__ctru3pc8:22:cil1::icon"));
	                                                                           
		CommonUtils.waitForElement(iconedit);
		iconedit.click();
	}
	  
	
	public static void expressionbuilder(){
		WebElement expressionbuilder=DriverInstance.getDriverInstance().findElement(By.xpath("//tr[@id='pt1:pePanel:0:peDiReg:1:comppir0:0:j_id__ctru32pc8']/td[2]"));
		CommonUtils.waitForElement(expressionbuilder);
		expressionbuilder.click();
	}
	
	public static void expressioneditor(String UpdatableText){
		WebElement expressioneditor=DriverInstance.getDriverInstance().findElement(By.id("pt1:pePanel:0:elbuilder1:0:j_id__ctru15pc12::content"));
		CommonUtils.waitForElement(expressioneditor);
		expressioneditor.click();
		expressioneditor.clear();
		expressioneditor.sendKeys(UpdatableText);
	}
	
	public static void expressioneditortestbutton(){
		WebElement expressioneditortestbutton=DriverInstance.getDriverInstance().findElement(By.id("pt1:pePanel:0:test"));
		CommonUtils.waitForElement(expressioneditortestbutton);
		expressioneditortestbutton.click();
	}
	
	public static void expressioneditorokbutton(){
		WebElement expressioneditorokbutton=DriverInstance.getDriverInstance().findElement(By.id("pt1:pePanel:0:j_id__ctru19pc6::ok"));
		CommonUtils.waitForElement(expressioneditorokbutton);
		expressioneditorokbutton.click();
	}
	
	 
	
	  public static void pagecomposerstructure(){
		WebElement pagecomposerstructure=DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:dsv_src"));
		CommonUtils.waitForElement(pagecomposerstructure);
		pagecomposerstructure.click();
	}
	
	  
	    
		  public static void  pagecomposerpopupclose(){
		 WebElement pagecomposerpopupclose=DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(@id,'pt1:peClose')]"));
				 //id("pt1:peClose"));
		  CommonUtils.waitForElement(pagecomposerpopupclose);
		  pagecomposerpopupclose.click();
		//a[contains(@id,'pt1:peClose')]

	 }
	  public static void preferencespage(String str){
		  WebElement Preferencepage=DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'"+str+"')]"));
		  //CommonUtils.waitForElement(Preferencepage);
		  CommonUtils.hold(8);

		  	//Assert.assertEquals(true, Preferencepage.isDisplayed());

		  	Preferencepage.click(); 
	 }
	  
	  
	  public static void pagecomposerediticon(){
	WebElement pagecomposerediticon=DriverInstance.getDriverInstance().findElement(By.xpath("//button[text()='Edit']"));
	CommonUtils.waitForElement(pagecomposerediticon);
	pagecomposerediticon.click();
	  }
	  
	
	public static void customizedropdown(String str,int i){
		  WebElement customizedropdown=DriverInstance.getDriverInstance().findElement(By.xpath("//label[contains(text(),'"+str+"')]/following::img["+i+"]"));
		  CommonUtils.waitForElement(customizedropdown);

		  	Assert.assertEquals(true, customizedropdown.isDisplayed());

		  	customizedropdown.click(); 
		
	}
	
	public static void customizepageexpressionbuilder(String st){
		  WebElement customizepageexpressionbuilder=DriverInstance.getDriverInstance().findElement(By.xpath("//td[contains(text(),'"+st+"')]"));
		  CommonUtils.waitForElement(customizepageexpressionbuilder);
		  customizepageexpressionbuilder.click();
		
	}
	
	 
	  public static void pagecomposertextareaeditor(String UpdatableText){
		  WebElement pagecomposertextareaeditor=DriverInstance.getDriverInstance().findElement(By.xpath("//textarea[contains(@id,'content')]"));
		  CommonUtils.waitForElement(pagecomposertextareaeditor);
		  pagecomposertextareaeditor.click();
		  pagecomposertextareaeditor.clear();
		  pagecomposertextareaeditor.sendKeys(UpdatableText);
		
	  }
	
	
	 
	  public static void pagecomposertextareatestbutton(){
		  WebElement pagecomposertextareatestbutton=DriverInstance.getDriverInstance().findElement(By.xpath("//button[contains(text(),'Test')]"));
		  CommonUtils.waitForElement(pagecomposertextareatestbutton);
		  pagecomposertextareatestbutton.click();
	  }
	
	
	  public static void pagecomposertextareaokbutton(){
		  WebElement pagecomposertextareaokbutton=DriverInstance.getDriverInstance().findElement(By.xpath("//button[text() = 'Test']/following-sibling::button[text() = 'OK']"));
		  CommonUtils.waitForElement(pagecomposertextareaokbutton);
		  pagecomposertextareaokbutton.click(); 
	  }
	  
	  public static void pagecomposercustomerlayer(int i){
		  //WebElement   pagecomposercustomerlayer=DriverInstance.getDriverInstance().findElement(By.name("tipTable"));
		  //CommonUtils.waitForElement(pagecomposercustomerlayer);
		  //pagecomposercustomerlayer.click();
		  CommonUtils.hold(10);
		  List<WebElement> oRadioButton  = DriverInstance.getDriverInstance().findElements(By.name("tipTable"));
		  
		
			  oRadioButton.get(i).click();
	  }
	 
	  public static void pagecomposercustomerlayerokbutton(){
		  WebElement pagecomposercustomerlayerokbutton=DriverInstance.getDriverInstance().findElement(By.xpath("//button[contains(@id,'FOpt1:USgr3:0:r1:0:okABtn')]"));
		  CommonUtils.waitForElement(pagecomposercustomerlayerokbutton);
		  pagecomposercustomerlayerokbutton.click(); 
	  }
	  
	  public static void pagecomposerselect(){
			WebElement pagecomposerselect=DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:dsv_comp"));
			CommonUtils.waitForElement(pagecomposerselect);
			pagecomposerselect.click();
		}
	  
	  public static void salesglobaldetails(){
		  WebElement salesglobaldetails=DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:_FOr1:0:_FOSritemNode_sales_quotas:0:_FOTsr1:0:pt1:AP1:sdi3::disAcr"));
			CommonUtils.waitForElement(salesglobaldetails);
			salesglobaldetails.click();
	  }


	  public static void pagecomposereditcomponent(){
		  WebElement pagecomposereditcomponent=DriverInstance.getDriverInstance().findElement(By.xpath("//a[@id='_FOpt1:pePanel:0:cil1']/span"));
			CommonUtils.waitForElement(pagecomposereditcomponent);
			pagecomposereditcomponent.click();
	  }
	  
	  public static void salescomponenteditcontent(String UpdatableText){
		  WebElement salescomponenteditcontent=DriverInstance.getDriverInstance().findElement(By.xpath("//input[contains(@id,'FOpt1:pePanel:0:peDiReg:1:comppir0:0:text::content')]"));
		 CommonUtils.waitForElement(salescomponenteditcontent);
		  salescomponenteditcontent.click();
		  salescomponenteditcontent.clear();
		  salescomponenteditcontent.sendKeys(UpdatableText);
	  }
	  
	  public static WebElement customizePagesLink() {
		  WebElement CustomizePagesEle=DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(@id,'pt1:_UIScmi3')]"));
		  
		  return CustomizePagesEle;
	  }
}
*/