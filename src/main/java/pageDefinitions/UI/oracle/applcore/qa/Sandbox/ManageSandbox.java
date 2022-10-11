/*package oracle.applcore.qa.sandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.common.Log;
import oracle.applcore.qa.setup.DriverInstance;

public class ManageSandbox {
 public static String sandbox_name=null;

//**************************************************************************************************************************************************************************************************************************** 
	public static String sandboxcreate() throws Exception {
		  CommonUtils.hold(30);
		  CommonUtils.waitForElement(DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")));
		  CommonUtils.hold(30);
		  DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")).click();                  //Click on User Link
		  CommonUtils.hold(30);
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi14")).click();                        //Click on Manage Sandbox
	     
	      CommonUtils.hold(30);
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:create::icon")).click();   //Add Sandbox
	      CommonUtils.hold(20);
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:it2::content")).sendKeys("applcoreqa"+CommonUtils.uniqueId());
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:it41::content")).sendKeys("applcoreqa"+CommonUtils.uniqueId());
	      sandbox_name=DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:it2::content")).getAttribute("value");
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:FAsc1")).click();
	      CommonUtils.hold(20);
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:d23::ok")).click();
		  return "applcoreqa"+CommonUtils.uniqueId();	
		 
		
	}
	
	
//****************************************************************************************************************************************************************************************************************************	
	
	
	public static void ActivateSandbox() {
		
		CommonUtils.hold(20);
		DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).click();
		Log.info("Click on Search Sandbox Link");
		DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).sendKeys(sandbox_name);
		Log.info("Enter Sandbox Name");
		DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:cil1::icon")).click();
		Log.info("Click on Filter");
		CommonUtils.hold(20);
		DriverInstance.getDriverInstance().findElement(By.xpath("/html[1]/body[1]/div[2]/form[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[3]")).click();
		Log.info("Select the First Result");
		CommonUtils.hold(10);
		DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:cb1")).click();
		Log.info("Activate the First Sandbox");
		CommonUtils.hold(5);
		
		
		
		
	}
	
	
//****************************************************************************************************************************************************************************************************************************
	
	public static void deletesandbox(String sandboxname) {
		
	}
	
	
//****************************************************************************************************************************************************************************************************************************
	
	public static void publishsandbox() {
		  CommonUtils.hold(30);
		  CommonUtils.waitForElement(DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")));
		  CommonUtils.hold(30);
		  DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")).click();                  //Click on User Link
		  CommonUtils.hold(30);
	      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi14")).click();                        //Click on Manage Sandbox
	      CommonUtils.hold(20);
		DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).click();
		Log.info("Click on Search Sandbox Link");
		DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).sendKeys(sandbox_name);
		Log.info("Enter Sandbox Name");
		DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:cil1::icon")).click();
		Log.info("Click on Filter");
		CommonUtils.hold(20);
		DriverInstance.getDriverInstance().findElement(By.xpath("//span[text()='"+sandbox_name+"']")).click();
		Log.info("Select the First Result");
		CommonUtils.hold(10);
		DriverInstance.getDriverInstance().findElement(By.xpath("//button[@id='pt1:_UISmsr:0:AT1:_ATp:cb2']")).click();
		Log.info("Click on Publish Sandbox");
		CommonUtils.hold(10);
		DriverInstance.getDriverInstance().findElement(By.xpath("//button[text()='Yes']")).click();
		Log.info("Click on Yes to publish sandbox");
	}
	
//****************************************************************************************************************************************************************************************************************************
}
*/