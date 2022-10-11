package UtilClasses.REST;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;

import TestBase.UI.GetDriverInstance;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

public class RestCommonUtils {

    public static Response postRequest(String url, String paramaters, Map<String,String> headers, String uname, String password,String... cookie) {
        RequestSpecification request = RestAssured.given();
       
        /*
         * adding HEADER information to 'request' object instance
         */
        if(!(headers.isEmpty())) {
        	for(Map.Entry<String, String> headerContent : headers.entrySet()) {
            	request.header(headerContent.getKey(),headerContent.getValue());
            }
        }
        
        
        request.auth().preemptive().basic(uname, password);
     
        System.out.println("Url : " + url);
        System.out.println("parameters : " + paramaters);
        if (!(paramaters.contentEquals(""))) {
            JSONObject jsonObj = new JSONObject(paramaters);
            System.out.println(jsonObj.toString());

            request.body(jsonObj.toString());
        }
        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = request.post(url);
         } catch (Exception e) {
            e.printStackTrace();

        }

        // sending request with cookie in case of 302
        if(response.getStatusCode()==302)
        {
            RequestSpecification req=RestAssured.given();
            String key="";
            String val="";
            Map<String,String> cookies=response.cookies();
            for(Map.Entry<String,String> c: cookies.entrySet())
            {
                if(c.getKey().equals(cookie[0]))
                {
                    key=c.getKey();
                    val=c.getValue();
                    break;
                }
            }
            req.cookie(key,val);
            req.auth().preemptive().basic(uname, password);
            req.contentType("application/json");
            if (!(paramaters.contentEquals(""))) {
                JSONObject jsonObj = new JSONObject(paramaters);
                System.out.println(jsonObj.toString());
                req.body(jsonObj.toString());
            }
            response=req.post(url);

        }
        JsonPath jsonPathEvaluator = response.jsonPath();
        // Get Response Body as String

        String bodyStringValue = response.body().asString();


        // int statusCode = response.getStatusCode();
        // System.out.println("Retruned Status Code : " + statusCode);   //paramterize

        //	return Integer.toString(statusCode);
        return response;
    }
    
    public static Response postRequest(String url, String paramaters,String uname) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        //request.header("Content-Type", headerType);
       // request.header("x-resource-service-instance-guid", "fnd");
        request.auth().preemptive().basic(uname, "Welcome1");

        System.out.println("Url : " + url);
        System.out.println("parameters : " + paramaters);
        if (paramaters != "NULL") {
            JSONObject jsonObj = new JSONObject(paramaters);
            System.out.println(jsonObj.toString());

            request.body(jsonObj.toString());
        }
        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = request.post(url);
            // jsonparser(response);
        } catch (Exception e) {
            e.printStackTrace();

        }
       
        return response;
    }
    
    public static Response patchRequest(String url, String parameters,String uname, String password) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        //request.header("Content-Type", headerType);
       // request.header("x-resource-service-instance-guid", "fnd");
        request.auth().preemptive().basic(uname, password);

        System.out.println("Url : " + url);
        System.out.println("parameters : " + parameters);
        if (parameters != "NULL") {
            JSONObject jsonObj = new JSONObject(parameters);
            System.out.println(jsonObj.toString());

            request.body(jsonObj.toString());
        }
        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = request.patch(url);
            // jsonparser(response);
        } catch (Exception e) {
            e.printStackTrace();

        }
       
        return response;
    }


    //**************************************************************************************************************************************************************************
    public static Response getRequest(String url, Map<String,String> headers, String uname, String password) {

        RequestSpecification getRequest = RestAssured.given();
        
        /*
         * adding HEADER information to 'getRequest' object instance
         */
        if(!(headers.isEmpty())) {
        	for(Map.Entry<String, String> headerContent : headers.entrySet()) {
            	getRequest.header(headerContent.getKey(),headerContent.getValue());
            }
        }
             
        getRequest.auth().preemptive().basic(uname, password);
        
        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = getRequest.get(url);
         }
         catch (Exception e) {
            e.printStackTrace();
        }
          return response;
    }

    //**************************************************************************************************************************************************************************
    public static Response deleteRequest(String url, Map<String,String> headers, String uname, String password) {
        RequestSpecification deleteRequest = RestAssured.given();
        
        /*
         * adding HEADER information to 'deleteRequest' object instance
         */
        for(Map.Entry<String, String> headerContent : headers.entrySet()) {
        	deleteRequest.header(headerContent.getKey(),headerContent.getValue());
        }
        
        deleteRequest.auth().preemptive().basic(uname, password);
        
        restSessiontimeout();             //Timesync
        Response response = deleteRequest.delete(url);


        int statusCode = response.getStatusCode();
        System.out.println("Retruned Status Code : " + statusCode);

        return response;
    }

    //**************************************************************************************************************************************************************************
    /*
     * ReadJSONFile() method will read json file and return the node value of the node attribute specified as a parameter
     */
    public static String ReadJSONFile(String nodeValuie, String filePath) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePath));
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
        String value = (String) jsonObject.get(nodeValuie);
        return value;
    }
//**************************************************************************************************************************************************************************		

    public static Response putRequest(String url, String paramaters, Map<String,String> headers, String uname, String password) {
        RequestSpecification putRequest = RestAssured.given();

        /*
         * adding HEADER information to 'request' object instance
         */
        for(Map.Entry<String, String> headerContent : headers.entrySet()) {
        	putRequest.header(headerContent.getKey(),headerContent.getValue());
        }
        
        putRequest.auth().preemptive().basic(uname, password);

        if (paramaters != "NULL") {
            JSONObject jsonObj = new JSONObject(paramaters);

            putRequest.body(jsonObj.toString());
        }
        restSessiontimeout();             //Timesync
        Response response = putRequest.put(url);
			 
		/*	 int statusCode = response.getStatusCode();
			 System.out.println("Retruned Status Code : " + statusCode);
			 Assert.assertEquals(statusCode==200, true , "Request is successful");*/
        //return Integer.toString(statusCode);
        return response;
    }

  //**************************************************************************************************************************************************************************		

/*    public static Response putRequest(String url, String paramaters) {
        RequestSpecification request = RestAssured.given();

        request.auth().preemptive().basic("app_impl_consultant", "Welcome1");

        request.header("Content-Type", "application/json");
        request.header("x-resource-service-instance-guid", "fnd");

        if (paramaters != "NULL") {
            JSONObject jsonObj = new JSONObject(paramaters);

            request.body(jsonObj.toString());
        }
        restSessiontimeout();             //Timesync
        Response response = request.put(url);
			 
			 int statusCode = response.getStatusCode();
			 System.out.println("Retruned Status Code : " + statusCode);
			 Assert.assertEquals(statusCode==200, true , "Request is successful");
        //return Integer.toString(statusCode);
        return response;
    }*/
    //**************************************************************************************************************************************************************************
    public static void restSessiontimeout() {
        RestAssuredConfig config = RestAssured.config()

                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 1000));

    }

    //**************************************************************************************************************************************************************************
    /*public static String getRequestforstarter(String url) {

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.auth().preemptive().basic("app_impl_consultant", "Welcome1");
        httpRequest.header("Content-Type", "application/json");

        // Making GET request directly by RequestSpecification.get() method

        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = httpRequest.get(url);
            // jsonparser(response);
        }
        // Response response = httpRequest.get(url);
        catch (Exception e) {
            e.printStackTrace();

        }
        //String body = response.getBody().asString();
        JsonPath jsonPathEvaluator = response.jsonPath();
        // Get Response Body as String
        Assert.assertEquals(response.getStatusCode() == 200, true, "Elastic Server Request is successful");
        System.out.println("Actual StatusCode:==>" + response.statusCode());
        String bodyStringValue = response.body().asString();
        return bodyStringValue;

    }
*/
    //**************************************************************************************************************************************************************************
   /* public static String getRequestforNegTests(String url) {

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.auth().preemptive().basic("app_impl_consultant", "Welcome1");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.header("x-resource-service-instance-guid", "fnd");
        // Making GET request directly by RequestSpecification.get() method

        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = httpRequest.get(url);
            // jsonparser(response);
        }
        // Response response = httpRequest.get(url);
        catch (Exception e) {
            e.printStackTrace();

        }
        //String body = response.getBody().asString();
        JsonPath jsonPathEvaluator = response.jsonPath();
        // Get Response Body as String
        System.out.println("Asserting");
        System.out.println("Actual StatusCode:==>" + response.statusCode());

        Assert.assertEquals(response.getStatusCode() == 400, true, "Elastic Server Request is successful");

        String bodyStringValue = response.body().asString();
        return bodyStringValue;

    }
*/
    //**************************************************************************************************************************************************************************
    /*public static Map<String, String> responseParser(Response responsebody, String ParentField, String... ChildFields) throws Exception {
        Map<String, String> resultantObjects = new HashMap<String, String>();

        JSONParser parse = new JSONParser();
        String resp = responsebody.body().asString();
        System.out.println("------->" + resp);
        org.json.simple.JSONObject jobj = (org.json.simple.JSONObject) parse.parse(resp);
        System.out.println("Parentfield of the response -> "+jobj.get(ParentField));

//	System.out.println("Number Of Child Objects -> "+( jsonarr_1).size());
        //Get data for Results array
        if (ParentField.equals("NULL")) {
            JSONArray jsonarr_1 = (JSONArray) jobj.get("{");
            System.out.println("JsonArray Size -> "+jsonarr_1.size());
            for (int i = 0; i < jsonarr_1.size(); i++) {

                org.json.simple.JSONObject jsonobj_1 = (org.json.simple.JSONObject) jsonarr_1.get(i);

                for (int argslist = 0; argslist < ChildFields.length; argslist++) {
                	System.out.println("chld attr -> "+ChildFields[argslist] + i);
                    resultantObjects.put(ChildFields[argslist] + i, jsonobj_1.get(ChildFields[argslist]).toString());
                }
                	
            }
            System.out.println(jobj.get("{"));
        } else {
            JSONArray jsonarr_1 = (JSONArray) jobj.get(ParentField);
            for (int i = 0; i < jsonarr_1.size(); i++) {

                org.json.simple.JSONObject jsonobj_1 = (org.json.simple.JSONObject) jsonarr_1.get(i);
			
			System.out.println(jsonobj_1.get("userName"));
			System.out.println(jsonobj_1.get("description"));
			System.out.println(jsonobj_1.get("qualifiedBusinessObject"));
                for (int argslist = 0; argslist < ChildFields.length; argslist++) {
                	System.out.println("chld attr -> "+ChildFields[argslist] + i);
                	resultantObjects.put(ChildFields[argslist] + i, jsonobj_1.get(ChildFields[argslist]).toString());
                }
                    
			System.out.println(jsonobj_1.get(ChildFields[0]));
			System.out.println(jsonobj_1.get(ChildFields[1]));
			System.out.println(jsonobj_1.get(ChildFields[2]));

            }
        }

        return resultantObjects;
    }
*/
  //**************************************************************************************************************************************************************************
    /*
     * responseParser() will parse the resultant response and return the values of the corresponding attributes passed as a parameter
     * @return
     */
  /*  public static Map<String, String> responseParser(Response responsebody, String ParentField, String... ChildFields) throws Exception {
        Map<String, String> resultantObjects = new HashMap<String, String>();

        JSONParser parse = new JSONParser();
        String resp = responsebody.body().asString();
        System.out.println("------->" + resp);
        org.json.simple.JSONObject jobj = (org.json.simple.JSONObject) parse.parse(resp);
        System.out.println("Parentfield of the response -> "+jobj.values());

       //Get data for Results array
        if (ParentField.contentEquals("")) {
        	System.out.println("Parent doesn't exist for the response");
               
            for (int argslist = 0; argslist < ChildFields.length; argslist++) {
                	System.out.println("chld attr -> "+ChildFields[argslist]);
                    resultantObjects.put(ChildFields[argslist], jobj.get(ChildFields[argslist]).toString());
                }
            
        } else {
            JSONArray jsonarr_1 = (JSONArray) jobj.get(ParentField);
            for (int i = 0; i < jsonarr_1.size(); i++) {

                org.json.simple.JSONObject jsonobj_1 = (org.json.simple.JSONObject) jsonarr_1.get(i);
			
		        for (int argslist = 0; argslist < ChildFields.length; argslist++) {
                	System.out.println("chld attr -> "+ChildFields[argslist] + i);
                	resultantObjects.put(ChildFields[argslist] + i, jsonobj_1.get(ChildFields[argslist]).toString());
                }
                    
		    }
        }

        return resultantObjects;
    }*/
    
  //**************************************************************************************************************************************************************************
    /*responseParser() method will parse response and return the values of the attributes passed in parameters.
     * @return type : Map<String, Map<String,String>>
     * 
     * If ParentField is empty then resultant Map is as <"Value of first child parameter" , Map<ChildParameter, ValueOfChildParameter>>
     * If ParentField is non empty then resultant Map is as <"ParentFiled Name" , Map<ChildParameter, ValueOfChildParameter>>
     */
    public static Map<String, Map<String,String>> responseParser(Response responsebody, String ParentField, String... ChildFields) throws Exception {
        Map<String, Map<String,String>> resultantObjects = new HashMap<String, Map<String,String>>();

        JSONParser parse = new JSONParser();
        String resp = responsebody.body().asString();
        System.out.println("------->" + resp);
        org.json.simple.JSONObject jobj = (org.json.simple.JSONObject) parse.parse(resp);
        System.out.println("Parentfield of the response -> "+jobj.values());

       //Get data for Results array
        if (ParentField.contentEquals("")) {
       // 	System.out.println("Parent doesn't exist for the response");
        	Map<String,String> resultantMapValues = new HashMap<String,String>();
            for (int argslist = 0; argslist < ChildFields.length; argslist++) {
            	//   	System.out.println("chld attr -> "+ChildFields[argslist] +"<-> Value -> "+jobj.get(ChildFields[argslist]).toString());
                	resultantMapValues.put(ChildFields[argslist],jobj.get(ChildFields[argslist]).toString());
                	if(argslist == (ChildFields.length-1))
                		resultantObjects.put(jobj.get(ChildFields[0]).toString(), resultantMapValues);
                }
            
        } else {
            JSONArray jsonarr_1 = (JSONArray) jobj.get(ParentField);
            System.out.println("ArraySize : "+jsonarr_1.size());
            for (int i = 0; i < jsonarr_1.size(); i++) {
            //	System.out.println("i : "+i);
            	Map<String,String> resultantMapValues = new HashMap<String,String>();
                org.json.simple.JSONObject jsonobj_1 = (org.json.simple.JSONObject) jsonarr_1.get(i);
			
		        for (int argslist = 0; argslist < ChildFields.length; argslist++) {
		        	 
                   	resultantMapValues.put(ChildFields[argslist],jsonobj_1.get(ChildFields[argslist]).toString());
                }
		        resultantObjects.put(jsonobj_1.get(ChildFields[0]).toString(), resultantMapValues);
		    }
        }

        return resultantObjects;
    }
    

//**************************************************************************************************************************************************************************
    public static String parseResponsetemp(Response responsebody, String attr) throws Exception {
        String resultvalue = null;
        JsonFactory jsonFactory = new JsonFactory();

        JsonParser parser = jsonFactory.createParser(responsebody.body().asString());

        while (!parser.isClosed()) {
            JsonToken token = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(token)) {
                String fieldName = parser.getCurrentName();
               // System.out.println("FieldName  -> " + fieldName);
                token = parser.nextToken();
                if (attr.equals(fieldName) && ((JsonToken.VALUE_STRING.equals(token)) || JsonToken.VALUE_NUMBER_INT.equals(token) || JsonToken.VALUE_NUMBER_FLOAT.equals(token))) {
                 //   System.out.println("Attribute field Value -> " + parser.getValueAsString());
                    resultvalue = parser.getValueAsString();
                }
            }
        }
        return resultvalue;
    }

 //*********************************************************************************************************************************************************
    /*
     * responseStatus(Response responsebody) method will return status code of the response
     */
    public static int responseStatus(Response responsebody) {
    	return responsebody.getStatusCode();
    }
    
 //*********************************************************************************************************************************************************
    
    public static Map<String, Map<String,Object>> parseJson(String parentNode, String filePath) throws FileNotFoundException, IOException, ParseException {
        Map<String, Map<String,Object>> requestMapList = new HashMap<String, Map<String,Object>>();
              
        JSONParser parser = new JSONParser();

        Object parsingObject = parser.parse(new FileReader(filePath));
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parsingObject;
        JSONArray parentJsonArray = (JSONArray) jsonObject.get(parentNode);
        System.out.println("Json Array Size -> " + (parentJsonArray).size());

        
        /*
         *  loop through all the child arrays of the parent array passed as an argument to parseJson() method
         */
         
        for (int loop = 0; loop < parentJsonArray.size(); loop++) {
           	Map<String,Object> attributesMap = new HashMap<String, Object>();
            org.json.simple.JSONObject childArrayObject = (org.json.simple.JSONObject) parentJsonArray.get(loop);
            System.out.println("Child Object of index [" + loop + "] -> " + childArrayObject);
            System.out.println("Requesttype -> " + childArrayObject.get("Requesttype"));
            System.out.println("RequestURL -> " + childArrayObject.get("RequestURL"));
            System.out.println("KeyDescription -> " + childArrayObject.get("KeyDescription"));
            System.out.println("Payload -> " + childArrayObject.get("Payloads"));
                      
            attributesMap.put("Requesttype", childArrayObject.get("Requesttype"));
            attributesMap.put("RequestURL", childArrayObject.get("RequestURL"));
            attributesMap.put("Payloads", childArrayObject.get("Payloads"));
            if (childArrayObject.containsKey("Payloads")) {
               	JSONArray payloadChildArray = (JSONArray) childArrayObject.get("Payloads");
		 
		  /*
		   *  loop through all the "payloads" child arrays
		   */
		  
		 
            	if(payloadChildArray!=null)
            	{
            		System.out.println("Total Number Of Payloads -> "+payloadChildArray.size());
            		attributesMap.put("PayloadsCount", payloadChildArray.size());
            		for(int payloadChildLoop=0;payloadChildLoop < payloadChildArray.size();  payloadChildLoop++) {
            			attributesMap.put("Payload"+payloadChildLoop, payloadChildArray.get(payloadChildLoop));
            			System.out.println("Payload ["+payloadChildLoop+"] -> "+payloadChildArray.get(payloadChildLoop));
            		}
            	}else
            		attributesMap.put("PayloadsCount", "0");
            }
            requestMapList.put((String) childArrayObject.get("KeyDescription"), attributesMap);
        }
      
        return requestMapList;
    }//End Of parseJson()
    
 //*********************************************************************************************************************************************************

    /*
     * updatePayload() will update json attributes of the requested payload specified in "updatablePayload" attribute and return the updated object
     */
    public static String updatePayload(Object updatablePayload, String... updatableAttributes) {
        boolean isArgToUpdate = false;
        int tokennum = 0;
        String updatedPayload = "{";
        String[] payloadStringToken = updatablePayload.toString().substring(updatablePayload.toString().indexOf("{") + 1, updatablePayload.toString().indexOf("}")).split("\",");
     //   String[] payloadStringToken = updatablePayload.toString().substring(updatablePayload.toString().indexOf("{") + 1, updatablePayload.toString().lastIndexOf("}")).split(",");
     //   String[] payloadStringToken = updatablePayload.toString().substring(updatablePayload.toString().indexOf("{") + 1, updatablePayload.toString().lastIndexOf("}")).split("\",");
        System.out.print("Count of Tokens  -> " + payloadStringToken.length);
        for (String token : payloadStringToken) {
        	tokennum++;
            System.out.println(token);
            for (int argslist = 0; argslist < updatableAttributes.length; argslist++) {
                if (token.substring(0, token.indexOf(":")).contentEquals("\"" + updatableAttributes[argslist] + "\"")) {
                    argslist++;
                    updatedPayload = updatedPayload + token.replace(token.substring(token.indexOf(":") + 1, token.length()), "\"" + updatableAttributes[argslist] + "\"") + ",";
                    argslist++;
                    isArgToUpdate = true;
                    break;
                }
            }
            if (!(isArgToUpdate)) {
            	if(tokennum < payloadStringToken.length)
            		updatedPayload = updatedPayload + token + "\",";
            	else
            		updatedPayload = updatedPayload + token + ",";
            }
                
            isArgToUpdate = false;
        }
        updatedPayload = updatedPayload.substring(0, updatedPayload.length() - 1);
        updatedPayload = updatedPayload + "}";
        System.out.println("Final Updatable String -> " + updatedPayload);
        return updatedPayload;
    }//End Of updatePayload()

  //  *********************************************************************************************************************************************************    
    public static String updateSerivcePayload(Object updatablePayload, String... updatableAttributes) {
    	 boolean isArgToUpdate = false;
         int tokennum = 0;
         String updatedPayload = "{";
          String[] payloadStringToken = updatablePayload.toString().substring(updatablePayload.toString().indexOf("{") + 1, updatablePayload.toString().lastIndexOf("}")).split(",\"");
          System.out.print("Count of Tokens  -> " + payloadStringToken.length);
          for (String token : payloadStringToken) {
              System.out.println(token);
              if(tokennum > 0)
            	  token = "\""+token;
              tokennum++;
              for (int argslist = 0; argslist < updatableAttributes.length; argslist++) {
            	if (token.substring(0, token.indexOf(":")).contentEquals("\"" + updatableAttributes[argslist] + "\"")) {
                      argslist++;
                      updatedPayload = updatedPayload + token.replace(token.substring(token.indexOf(":") + 1, token.length()), "\"" + updatableAttributes[argslist] + "\"") + ",";
                      argslist++;
                      isArgToUpdate = true;
                      break;
                  }
            	
              }
              if (!(isArgToUpdate))
            	  updatedPayload = updatedPayload + token + ",";
              isArgToUpdate = false;
          }
          updatedPayload = updatedPayload.substring(0, updatedPayload.length() - 1);
          updatedPayload = updatedPayload + "}";
          System.out.println("Final Updatable String -> " + updatedPayload);
          return updatedPayload;
    }//End Of updatePayload()
    
    /*
     * getExecutableHost() will return host name to execute REST services
     */
    public static String getExecutableHost() {
    	String hostName = GetDriverInstance.EnvironmentName;
    	String[] host = hostName.split("fscmUI");
		System.out.println("Executable host -> "+host[0]);
	 return host[0];
 	}

   //VV Added
    //**************************************************************************************************************************************************************************
    
    public static Response postUSBRequest(String url, String paramaters,String ContentType,String SandboxID) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", ContentType);
        request.header("Metadata-Context","sandbox="+"\""+SandboxID+"\"");
        //request.header("Content-Type", headerType);
        request.header("x-resource-service-instance-guid", "fnd");
        request.auth().preemptive().basic("app_impl_consultant", "Welcome1");

        System.out.println("Url : " + url);
        System.out.println("parameters : " + paramaters);
        if (paramaters != "NULL") {
            JSONObject jsonObj = new JSONObject(paramaters);
            System.out.println(jsonObj.toString());

            request.body(jsonObj.toString());
        }
        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = request.post(url);
            // jsonparser(response);
        } catch (Exception e) {
            e.printStackTrace();

        }
        JsonPath jsonPathEvaluator = response.jsonPath();
        // Get Response Body as String

        String bodyStringValue = response.body().asString();


        // int statusCode = response.getStatusCode();
        // System.out.println("Retruned Status Code : " + statusCode);   //paramterize

        //	return Integer.toString(statusCode);
        return response;
    }
    
    //**************************************************************************************************************************************************************************

    public static Response getUSBRequest(String url) {

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.auth().preemptive().basic("app_impl_consultant", "Welcome1");
        httpRequest.header("Content-Type", "application/json");
        httpRequest.header("x-resource-service-instance-guid", "fnd");
        // Making GET request directly by RequestSpecification.get() method

        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = httpRequest.get(url);
            // jsonparser(response);
        }
        // Response response = httpRequest.get(url);
        catch (Exception e) {
            e.printStackTrace();

        }
        //String body = response.getBody().asString();
        JsonPath jsonPathEvaluator = response.jsonPath();
        // Get Response Body as String
        System.out.println("Asserting");
        System.out.println("Actual StatusCode:==>" + response.statusCode());

        

        return response;
    }
    
  //**************************************************************************************************************************************************************************
    public static Response deleteUSBRequest(String url) {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.auth().preemptive().basic("app_impl_consultant", "Welcome1");
        restSessiontimeout();             //Timesync
        Response response = httpRequest.delete(url);
        return response;
    }
    
  //**************************************************************************************************************************************************************************
    public static Map<String, List<Object>> parseUSBJson(String parentNode, String filePath) throws FileNotFoundException, IOException, ParseException {
        Map<String, List<Object>> requestMapList = new HashMap<String, List<Object>>();
        JSONParser parser = new JSONParser();

        Object parsingObject = parser.parse(new FileReader(filePath));
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parsingObject;
        JSONArray parentJsonArray = (JSONArray) jsonObject.get(parentNode);
        System.out.println("Json Array Size -> " + (parentJsonArray).size());

        /*
         * loop through all the child arrays of the parent array passed as an argument to parseJson() method
         */
        for (int loop = 0; loop < parentJsonArray.size(); loop++) {
            List<Object> attributesList = new ArrayList<Object>();
            org.json.simple.JSONObject childArrayObject = (org.json.simple.JSONObject) parentJsonArray.get(loop);
            /* VV commented
            System.out.println("Child Object of index [" + loop + "] -> " + childArrayObject);
            System.out.println("Requesttype -> " + childArrayObject.get("Requesttype"));
            System.out.println("RequestURL -> " + childArrayObject.get("RequestURL"));
            System.out.println("KeyDescription -> " + childArrayObject.get("KeyDescription"));
            */
            attributesList.add((String) childArrayObject.get("Requesttype"));
            attributesList.add((String) childArrayObject.get("RequestURL"));

            /*
            attributesList.add((String) childArrayObject.get("SQLQuery"));
            attributesList.add((String) childArrayObject.get("CheckColumnName"));
            attributesList.add((String) childArrayObject.get("ExpectedValue"));
            attributesList.add((String) childArrayObject.get("ErrorMessage"));
            attributesList.add((String) childArrayObject.get("SuccessMessage"));
            //attributesList.add((String) childArrayObject.get("RequestURL"));
*/
            if (childArrayObject.containsKey("Payloads")) {
                JSONArray payloadChildArray = (JSONArray) childArrayObject.get("Payloads");
                /*
                 * loop through all the "payloads" child arrays
                 */
                for (int payloadChildLoop = 0; payloadChildLoop < payloadChildArray.size(); payloadChildLoop++) {
                    attributesList.add(payloadChildArray.get(payloadChildLoop));
                    //System.out.println("Payload [" + payloadChildLoop + "] -> " + payloadChildArray.get(payloadChildLoop));
                }
            }
		 /*JSONArray payloadChildArray = (JSONArray) childArrayObject.get("Payloads");
		 
		  * loop through all the "payloads" child arrays
		  
		 
		 if(payloadChildArray!=null)
		 {
		 for(int payloadChildLoop=0;payloadChildLoop < payloadChildArray.size();  payloadChildLoop++) {
			 attributesList.add(payloadChildArray.get(payloadChildLoop));
			 System.out.println("Payload ["+payloadChildLoop+"] -> "+payloadChildArray.get(payloadChildLoop));
		 }
		 }
	*/
            requestMapList.put((String) childArrayObject.get("KeyDescription"), attributesList);
            // attributesList.clear();
        }
        return requestMapList;
    }//End Of parseUSBJson()

    /**
     *
     * @param data
     * @param payloadName
     * @param key
     * @return
     * @throws JsonProcessingException
     */
    public static String fetchAnyKeyDataFromPayload(String data,String payloadName,String key) throws JsonProcessingException {
        String value = "";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap=new HashMap<>();
        try {
            if(payloadName.isEmpty())
            {
                jsonMap=objectMapper.readValue(data, new TypeReference<Map<String,Object>>(){});
            }else
            {
                JSONObject jsonObject=new JSONObject(data);
                JSONObject childObj= (JSONObject) jsonObject.getJSONArray(payloadName).get(0);
                jsonMap=objectMapper.readValue(childObj.toString(), new TypeReference<Map<String,Object>>(){});
            }
            value=jsonMap.get(key).toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }

    public static Response redirectPostRequest(String url, String paramaters, Map<String,String> headers, String uname, String password) {
        RequestSpecification request = RestAssured.given();
        /*
         * adding HEADER information to 'request' object instance
         */
        if(!(headers.isEmpty())) {
            for(Map.Entry<String, String> headerContent : headers.entrySet()) {
                request.header(headerContent.getKey(),headerContent.getValue());
            }
        }
        request.auth().preemptive().basic(uname, password);
        System.out.println("Url : " + url);
        System.out.println("parameters : " + paramaters);
        if (!(paramaters.contentEquals(""))) {
            JSONObject jsonObj = new JSONObject(paramaters);
            System.out.println(jsonObj.toString());
            request.body(jsonObj.toString());
        }
        request.redirects().follow(false).expect().statusCode(302).when();
        restSessiontimeout();             //Timesync
        Response response = null;
        try {
            response = request.post(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String reqLocation = response.getHeader("Location");
        Response redirectresponse = null;
        Map<String, String> redirectheaders = new HashMap<String, String>();

        redirectheaders.put("Location", response.getHeader("Location"));
        redirectheaders.put("Content-Type", response.getHeader("Content-Type"));
        redirectheaders.put("Keep-Alive", response.getHeader("Keep-Alive"));
        redirectheaders.put("Connection", response.getHeader("Connection"));
        redirectresponse = RestAssured.given().cookie(response.getDetailedCookie("OAMAuthnHintCookie")).headers(redirectheaders).expect().statusCode(200).when().get();

        JsonPath jsonPathEvaluator = redirectresponse.jsonPath();
        // Get Response Body as String
        String bodyStringValue = redirectresponse.body().asString();
        // int statusCode = response.getStatusCode();
        // System.out.println("Retruned Status Code : " + statusCode);   //paramterize
        //	return Integer.toString(statusCode);
        return redirectresponse;
    }
}
	
