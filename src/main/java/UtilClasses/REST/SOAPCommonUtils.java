
	
package UtilClasses.REST;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import TestBase.UI.GetDriverInstance;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.commons.io.IOUtils;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

public class SOAPCommonUtils {

    public static Response postSOAPRequest(String baseurl, String uname, String password, String KFF_to_be_deployed, String XMLFileLocation, String SOAPAction) throws IOException {
        Response response=null;
        System.out.println(uname + " " + password);
        System.out.println(baseurl);
        System.out.println(XMLFileLocation);
        System.out.println(SOAPAction);
    	try {
    		if(!KFF_to_be_deployed.isEmpty()) {
    			modifyFile(XMLFileLocation,"KFFNAME",KFF_to_be_deployed);
    		}
        
        FileInputStream SOAPWSDL = new FileInputStream(new File(XMLFileLocation));
        
        RestAssured.baseURI=baseurl;
        response=RestAssured.given()
        	   .auth().preemptive().basic(uname, password)
               .header("Content-Type", "text/xml")
               .and()
               .body(IOUtils.toString(SOAPWSDL,"UTF-8"))
        .when()
           .post(SOAPAction)
        .then()
               .statusCode(200)
               .and()
               .log().all()
               .extract().response();
        //System.out.println("Response is: " +  response.asString());
        SOAPWSDL.close();
    	}
    	catch(Exception e) {
    		System.out.println("SOAP Request Exception"+e.toString());
    	}
    	if(!KFF_to_be_deployed.isEmpty()) {
    		modifyFile(XMLFileLocation,KFF_to_be_deployed,"KFFNAME");
    	}
       return response;
    }
    
    public static void restSessiontimeout() {
        RestAssuredConfig config = RestAssured.config()

                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 1000));

    }

    public static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));             
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            //Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceAll(oldString, newString);
            //Rewriting the input text file with newContent
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                reader.close();
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

	public static Response postPatchedSOAPRequest(String baseurl, String uname, String password, String XMLFileLocation, String SOAPAction) {
		
		   Response response=null;
	        System.out.println(uname + " " + password);
	        System.out.println(baseurl);
	        System.out.println(XMLFileLocation);
	        System.out.println(SOAPAction);
	    	try {
	        FileInputStream SOAPWSDL = new FileInputStream(new File(XMLFileLocation));
	        
	        RestAssured.baseURI=baseurl;
	        response=RestAssured.given()
	        	   .auth().preemptive().basic(uname, password)
	               .header("Content-Type", "text/xml")
	               .and()
	               .body(IOUtils.toString(SOAPWSDL,"UTF-8"))
	        .when()
	           .post(SOAPAction)
	        .then()
	               .statusCode(200)
	               .and()
	               .log().all()
	               .extract().response();
	        SOAPWSDL.close();
	    	}
	    	catch(Exception e) {
	    		System.out.println("SOAP Request Exception"+e.toString());
	    	}
	       return response;
	}
	
	
   
}
	
