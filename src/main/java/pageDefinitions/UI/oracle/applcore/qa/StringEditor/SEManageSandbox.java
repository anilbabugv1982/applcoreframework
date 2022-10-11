/*package oracle.applcore.qa.stringeditor;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.common.SetupAndMaintenance;
import oracle.applcore.qa.sandbox.ManageSandbox;
import oracle.applcore.qa.setup.DriverInstance;
import oracle.applcore.qa.stringeditor.SearchAndReplace.SearchModules;
import oracle.applcore.qa.tests.PageTemplateFusePlus;

public class SEManageSandbox extends ManageSandbox {
	public static enum SearchReplaceTypesLabel{
	USER_INTERFACE, GLOBAL_MENU, MUTI_PART, ENTERPRISE;
	public static String get(SearchReplaceTypes type) {
	switch(type) {
		case USER_INTERFACE:
			// "//*[@id='pluginCB::Label0']";
			return "//*[contains(text(),'User Interface Text')]";
		case GLOBAL_MENU:
			
			//return "//*[@id='pluginCBj_id_2::Label0']";
			return "//*[contains(text(),'Global Menu Label Text')]";
		case MUTI_PART:
			//return "//*[@id='pluginCBj_id_1::Label0']";
			return "//*[contains(text(),'Multipart Validation Messages')]";
		case ENTERPRISE:
			//return "//*[@id='pluginCBj_id_3::Label0']";
			return "//*[contains(text(),'Enterprise Scheduler Text')]";
	}
	return null;
	}}
	
    public static enum SearchReplaceTypes{
        USER_INTERFACE, GLOBAL_MENU, MUTI_PART, ENTERPRISE,ALL;
        public static String get(SearchReplaceTypes type) {
        switch(type) {
            case USER_INTERFACE:
                //return "//*[@id='pluginCB::content']";
            	return "//*[contains(text(),'User Interface Text')]//preceding-sibling::input[1]";
            case GLOBAL_MENU:

                //return "//*[@id='pluginCBj_id_2::content']";
            	return "//*[contains(text(),'Global Menu Label Text')]//preceding-sibling::input[1]";
            case MUTI_PART:
                //return "//*[@id='pluginCBj_id_1::content']";
            	return "//*[contains(text(),'Multipart Validation Messages')]//preceding-sibling::input[1]";
            case ENTERPRISE:
                //return "//*[@id='pluginCBj_id_3::content']";
                return "//*[contains(text(),'Enterprise Scheduler Text')]//preceding-sibling::input[1]";
        }
        return null;
        }}

	public SEManageSandbox() {
		// TODO Auto-generated constructor stub
	}
	 public static String sandbox_name=null;

	//**************************************************************************************************************************************************************************************************************************** 
		public static String sandboxcreate(String sbName) throws Exception {
			  CommonUtils.hold(5);
			  CommonUtils.waitForElement(DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")));
			  CommonUtils.hold(5);
			  DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")).click();                  //Click on User Link
			  CommonUtils.hold(10);
		      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi14")).click();                        //Click on Manage Sandbox
		     
		      CommonUtils.hold(10);
		      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:create::icon")).click();   //Add Sandbox
		      CommonUtils.hold(10);
		      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:it2::content")).sendKeys(sbName+CommonUtils.uniqueId());
		      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:it41::content")).sendKeys(sbName+CommonUtils.uniqueId());
		      sandbox_name=DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:it2::content")).getAttribute("value");
		      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:FAsc1")).click();
		      CommonUtils.hold(15);
		      DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:d23::ok")).click();//pt1:_UISmsr:0:d23::ok//pt1:_UISmsr:0:d23::ok
			  return sandbox_name;	
			 
			
		}
		
		
	//****************************************************************************************************************************************************************************************************************************	
		
		
		public static void ActivateSandbox(String sbName) {
			
			CommonUtils.hold(5);
			DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).click();
			//DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).sendKeys(sbName);
			//DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).clear();
			DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).sendKeys(sbName);
//     		DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).sendKeys(Keys.TAB);
			//DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISmsr:0:ot6']")).click();
			CommonUtils.hold(1);
			//DriverInstance.getDriverInstance(). .findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:cil1::icon"))
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISmsr:0:AT1:_ATp:cil1'] | //*[@id='pt1:_UISmsr:0:AT1:_ATp:cil1::icon']")).click();
			CommonUtils.hold(5);
			DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='pt1:_UISmsr:0:AT1:_ATp:_sbit1::content']")).sendKeys(Keys.BACK_SPACE);
			CommonUtils.hold(1);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISmsr:0:AT1:_ATp:cil1'] | //*[@id='pt1:_UISmsr:0:AT1:_ATp:cil1::icon']")).click();
		    CommonUtils.hold(10);
			DriverInstance.getDriverInstance().findElement(By.xpath("//span[text()[contains(.,'"+sbName+"')]]")).click();//pt1:_UISmsr:0:AT1:_ATp:ATt1:0:ot5   pt1:_UISmsr:0:AT1:_ATp:ATt1:52:ot5
			CommonUtils.hold(10);
			DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmsr:0:AT1:_ATp:cb1")).click();
			CommonUtils.hold(10);
			
			
			
			
		}
		
		
	//****************************************************************************************************************************************************************************************************************************
		
		public static void deletesandbox(String sandboxname) {
			
		}
		
		
	//****************************************************************************************************************************************************************************************************************************
		
		public static void publishsandbox() {
			CommonUtils.hold(10);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISsot2']")).click();
			CommonUtils.hold(5);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISmrh:0:cl1']")).click();
			CommonUtils.hold(15);
			//CommonUtils.ExplicitWait(SetupAndMaintenance.panelDrawer, "Click", "");
			//DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISsdcb']")).isDisplayed() == Boolean.FALSE) {
			//	CommonUtils.hold(5);
				//DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISsdcb']")).sendKeys(Keys.TAB);
			//}
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISsdcb']")).click();	
			CommonUtils.hold(15);

		}
		
	//****************************************************************************************************************************************************************************************************************************
		public static void exitSandbox() {
			//*[@id="pt1:_UISsot2"]
			CommonUtils.hold(10);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISsot2']")).click();
			//*[@id="pt1:_UISmrh:0:cl2"]
			CommonUtils.hold(5);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISmrh:0:cl2']")).click();
			//*[@id="pt1:_UISmrh:0:d1::yes"]
			CommonUtils.hold(5);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UISmrh:0:d1::yes']")).click();
			CommonUtils.hold(5);
		}
		
		///////////////////////////////////////////////
		public static void SearchAndReplaceFrameSwitch() {
			System.out.println("SearchAndReplaceFrameSwitch() Begin");
			//DriverInstance.getDriverInstance().findElement(By.xpath("//iframe[contains(@src, 'adf.dialog-request')]")).
			String FrameId = DriverInstance.getDriverInstance().findElement(By.xpath("//iframe[contains(@src, 'adf.dialog-request')]")).getAttribute("id");
			DriverInstance.getDriverInstance().switchTo().frame(FrameId);
			//DriverInstance.getDriverInstance().
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='sngSrch::content']")).click();
			//SingularSearch.click();
			
			System.out.println("SearchAndReplaceFrameSwitch() End");
		}//End of SearchAndReplaceFrameSwitch()
		
		
		public static void SearchAndReplaceStrings(String SingleSearch, String SingleReplace, String PlurSearch, String PlurReplace, SEManageSandbox.SearchReplaceTypes type) {
			System.out.println("SearchAndReplaceStirngs() Begin");
			
			//SearchAndReplaceFrameSwitch();
			//*[@id="sngSrch::content"]
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:USma:0:MAnt1:0:pt1:rg1:0:cb1'] | //*[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_user_interface_text:0:_FOTsr1:0:pt1:rg1:0:cb1'] ")).click();
			CommonUtils.hold(15);
			SearchAndReplaceFrameSwitch();
			//DriverInstance.getDriverInstance().switchTo();
			//WebElement we = DriverInstance.getDriverInstance().findElement(By.xpath("//textarea[contains(@name,'sngSrch')]"));
			//we.click();
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='sngSrch::content']")).click();
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='sngSrch::content']")).clear();
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='sngSrch::content']")).sendKeys(SingleSearch);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='sngRepl::content']")).sendKeys(SingleReplace);
			
			if(!(PlurSearch.isEmpty())) {
				DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pluSrch::content']")).sendKeys(PlurSearch);
				DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pluRepl::content']")).sendKeys(PlurReplace);
			}
			WebElement we1 = DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypesLabel.get(SEManageSandbox.SearchReplaceTypes.ENTERPRISE)));
			for(SEManageSandbox.SearchReplaceTypes t : SEManageSandbox.SearchReplaceTypes.values()) {
				if(t == SearchReplaceTypes.ALL) {
					continue;
				}
				if(t==type || type == SearchReplaceTypes.ALL) {
					WebElement we = DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypes.get(t)));
					//WebElement we = DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypesLabel.get(t)));
					if(we.isSelected()) {
						DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypesLabel.get(t))).click();
						DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pluSrch::content']")).click();
						CommonUtils.hold(1);
					}
					DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypesLabel.get(t))).click();
					DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pluSrch::content']")).click();
					CommonUtils.hold(5);
					
				}else {
					WebElement we = DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypes.get(t)));
					if(we.isSelected()) {
						DriverInstance.getDriverInstance().findElement(By.xpath(SEManageSandbox.SearchReplaceTypesLabel.get(t))).click();
						DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pluSrch::content']")).click();
						CommonUtils.hold(1);
					}
				}
			}
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='previewButton']")).click();
			CommonUtils.hold(100);
			if (type == SearchReplaceTypes.ALL) {
				CommonUtils.hold(50);
			}
			DriverInstance.getDriverInstance().switchTo().defaultContent();
			CommonUtils.hold(10);
			DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_user_interface_text:0:_FOTsr1:0:pt1:rg1:1:saveCloseBtn'] | //*[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_user_interface_text:0:_FOTsr1:0:pt1:rg1:1:saveCloseBtnLive']")).click();
			
			CommonUtils.hold(10);
			if(SEManageSandbox.SearchReplaceTypes.MUTI_PART == type || type == SearchReplaceTypes.ALL) {
				DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:USma:0:MAnt1:0:pt1:rg1:1:d1::yes'] | //*[@id='_FOpt1:_FOr1:0:_FOSritemNode_tools_user_interface_text:0:_FOTsr1:0:pt1:rg1:1:d1::yes']")).click();
				CommonUtils.hold(10);
			}
			
			System.out.println("SearchAndReplaceStirngs() End");
		}//End of SearchAndReplaceStrings()
		
            public static void clickUserMenuAndActivateSandbox(String sbName) {
		     CommonUtils.hold(8);
       		 SEManageSandbox.exitSandbox();
             CommonUtils.hold(10);
             DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")).click();                  //Click on User Link
       		 CommonUtils.hold(10);
       	     DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi14")).click(); 
       		 CommonUtils.hold(10);
       		 SEManageSandbox.ActivateSandbox(sbName);             //Activate Sandbox			
			}
         public static void clickUserMenuAndActivateSandboxNEW(String sbName) {
   		         CommonUtils.hold(8);
                 DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")).click();                  //Click on User Link
          		 CommonUtils.hold(10);
          	     DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi14")).click(); 
          		 CommonUtils.hold(10);
          		 SEManageSandbox.ActivateSandbox(sbName);             //Activate Sandbox			
   			}
               
             public static String getValueForUserInterfaceTextFlow1() {
    			goToSetPreferences();
           	    return DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:_FOTsr1:0:P_sp1:cl12")).getText();

            }
            public static String getValueForUserInterfaceTextFlow2() {
    			goToUserLink();
           	    return DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi99")).getText();

            }
            public static String getValueForGlobalMenuLabelTextFlow1() {
    			CommonUtils.hold(8);
    			PageTemplateFusePlus.navigatorButton.click();                  
           		CommonUtils.hold(10);
           	  return  DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:nv_itemNode_tools_reports_and_analytics']")).getText();

            }
            public static void goToHomePage() {
            	CommonUtils.hold(5);
        		DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:_UIShome::icon'] | //*[@id='_FOpt1:_UIShome'] ")  ).click();
            	CommonUtils.hold(5);
           }
            public static void goToUserLink() {
            	CommonUtils.hold(8);
           		DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmil1u::icon")).click();                  //Click on User Link
           		CommonUtils.hold(10);
           }
            public static void goToSetPreferences() {
            	goToUserLink();
            	DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi1")).click();            		
            	CommonUtils.hold(10);
           }
            public static String getValueForGlobalMenuLabelTextFlow2() {
            	CommonUtils.hold(8);
    			PageTemplateFusePlus.navigatorButton.click();                  
           		CommonUtils.hold(10);
           	  return  DriverInstance.getDriverInstance().findElement(By.xpath("//*[@id='pt1:nv_itemNode_tools_scheduled_processes_fuse_plus']")).getText();

            }
            public static String getValueForMultiPartTextFlow1() {
            CommonUtils.hold(8);
            goToUserLink();
           	CommonUtils.hold(5);
           	 DriverInstance.getDriverInstance().findElement(By.id("pt1:_UIScmi4")).click();
           	CommonUtils.navigateToAOLTaskFlows("Manage Messages");
           DriverInstance.getDriverInstance().findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId2:value00::content")).click();
         	CommonUtils.hold(10);
            DriverInstance.getDriverInstance().findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId2:value00::content")).sendKeys("AR_AAPI_INVALID_DESC_FLEX");
             CommonUtils.hold(5);
            DriverInstance.getDriverInstance().findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId2::search")).click();
             CommonUtils.hold(5);
             DriverInstance.getDriverInstance().findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT2:_ATp:ATt2:0:userModuleNameId")).click();
              CommonUtils.hold(5);
           	DriverInstance.getDriverInstance().findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT2:_ATp:edit")).click();
             CommonUtils.hold(5);
           String shortText = DriverInstance.getDriverInstance().findElement(By.id("pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:it10::content")).getText();
             return shortText = shortText.substring(0, SEManageSandbox.getIndex(shortText, " ", 3));
           		
            }
            public static String getValueForAll() {
            	CommonUtils.hold(8);
            	goToSetPreferences();                  
           		CommonUtils.hold(5);
           	  return  DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:_FOTsr1:0:P_sp1:SPpsl::c")).getText();          
           	  }
            
            public static int getIndex(String str, String searchString, int indexPos) {
            	int currentIndex = -1;
            	int currentPos = 0;
            	int finalIndex = 0;
            	while(currentPos < indexPos)
            	{	currentIndex = str.indexOf(searchString);
            		if(currentIndex < 0) {
            			break;
            		}
            		int beginIndex = currentIndex+searchString.length();
            		finalIndex += beginIndex; 
            		str = str.substring(beginIndex);
            		currentPos++;
            	}
            	if (currentPos == indexPos) {
            		return finalIndex - searchString.length();
            	}
            	return -1;
            }


}



*/