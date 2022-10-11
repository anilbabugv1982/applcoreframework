package pageDefinitions.UI.oracle.applcore.qa.GlobalSearch;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;


public class GSUtils extends gs {
	private GlobalPageTemplate gPTInstance;
	private gs gsInstance;
	 
	public GSUtils(WebDriver driver) {
		super(driver);
	
	}

	public  void searchactivegroups() {
		List<WebElement> checkBoxes=gsInstance.searchactivegrpscheckbox;
		
		
	   System.out.println(checkBoxes.size());
	   
	   
	   if(checkBoxes.isEmpty()) {
	        //Assert.fail();
		   System.out.println("Checkboxes are empty");
		   for(WebElement checkBox:checkBoxes)
	       {
	           checkBox.click(); 
	           
	       }
	    }
	

	}
	
	
	public  void autosuggestsize() {
		List<WebElement> listBox = gsInstance.autosuggestpopup;
        int listBoxSize = listBox.size();
        System.out.println("The size of the listbox is:"+listBoxSize);
        ArrayList<String> listBoxItems = new ArrayList<String>();
        for (WebElement option : listBox)
        {
              
              System.out.println(option.getText());
        }
	}
	
	
	public  void searchselect(String textsearch) {
		gsInstance.searchselect.click();
		
		
	}
	
	
	public  String favortiessearch() {
		gsInstance.favoritesclick.click();
		CommonUtils.hold(10);                                  //Hold for 10 Sec
		Log.info("Click on the Favorites in Home Page");
		fav_text=gsInstance.favoritesclick.getText();
		System.out.println(fav_text);
		return fav_text;
				
		
		
	}
}
