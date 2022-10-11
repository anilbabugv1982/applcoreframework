package pageDefinitions.UI.oracle.applcore.qa.Eff;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import org.sqlite.SQLiteConfig.SynchronousMode;

import java.io.PrintStream;
import java.sql.SQLException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;

public class EFFFileValidations {
	private static String currentDeployFlexLogsPath;
	public static String latestRepo;
    public static String jobId;
    public static String faHost;
    public static String executionPlatform;
    public static String upgPath;
    public static String faIntegLabel;
    public static String p4FALabel;
    public static String atgPatch;
	private static String userName;
	private static String password;
	private static String noFileDirectory;
	private static String noAccess;
	private static String privateKeyFile;
	private static String knownHostsFile;
	private static String command;
	private static String DBHost;
	private static String dbPassword;
	private static String DBPWD;
	private static String dbSID;
	private static String SIName;
	private static String dbPort;
	private static String dbUserName;
	
	
	private File f1;
	private BufferedReader br;
	private FileReader fr;
	private int loopCount;
	private ArrayList<String> flexPIM;
	public ArrayList<String> result;
	public String FlexDeploymentStatus;
	public String timeTaken;
	public boolean bValidateFlexDepComplete;
	public boolean bValidateDeployPatchedFlexComplete;
	public boolean bWlstExecutionComplete;
	private ArrayList<String> filesList;
	public ArrayList<String> consoleOutput;
	private JSch jsch;
	private Session session;
	Properties config;
	public Channel channel;
	InputStream input;
	InputStreamReader inputReader;
	BufferedReader bufferedReader;
	ChannelSftp channelSftp;
	
	static {
		try {
			FileReader propFileRead = new FileReader(
					"./src/main/java/ConfigurationResources/UI/UIConfiguration.properties");
			Properties fileProperties = new Properties();
			
			if(propFileRead != null) {
				fileProperties.load(propFileRead);
				executionPlatform = fileProperties.getProperty("executionPlatform");
                if(executionPlatform.equalsIgnoreCase("local")) {
                	DBHost = fileProperties.getProperty("DBHost");
                	dbSID = fileProperties.getProperty("sid");
    				dbPort = fileProperties.getProperty("port");
    				dbUserName = fileProperties.getProperty("dbUsername");
                    latestRepo=fileProperties.getProperty("releaseFullVersion");
                    upgPath=fileProperties.getProperty("upgradePath");
                    jobId=fileProperties.getProperty("jobId");    
                    faHost=fileProperties.getProperty("faHost");
                    faIntegLabel=fileProperties.getProperty("faIntegLabel");
                    p4FALabel=fileProperties.getProperty("p4FALabel");
                    atgPatch=fileProperties.getProperty("atgPatch");
                    DBPWD=fileProperties.getProperty("DBPWD");
                }
                else {
                    latestRepo=System.getProperty("releaseFullVersion");
                    jobId=System.getProperty("jobId");    
                    faHost=System.getProperty("faHost");
                    upgPath=System.getProperty("upgradePath");
                    faIntegLabel=System.getProperty("faIntegLabel");
                    p4FALabel=System.getProperty("p4FALabel");
                    atgPatch=System.getProperty("atgPatch");
                    DBHost = System.getProperty("DBHost");
                    dbPort = System.getProperty("DBPort");
					SIName = System.getProperty("SIName");
					DBPWD = System.getProperty("DBPWD");
                    dbPassword=System.getProperty("DBPWD");
                }
                currentDeployFlexLogsPath="/podscratch/lcm/logs/PRIMARY/orchestration/"+latestRepo+"."+jobId+"/RUP/";
                userName="oracle";
                password="Welcome1";
                noFileDirectory = "No such file or directory";
                noAccess = "ls: cannot access";
                privateKeyFile = "id_dsa";
                knownHostsFile = "known_hosts";
                
                
			}
		}
		catch(Exception rFE) {
			rFE.printStackTrace();
		}
	}
	
	public void executeUpdateQuery() throws SQLException {
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'READY'");
		DbResource.sqlStatement.executeQuery("update FND_kF_FLEXFIELDS_B set deployment_status = 'READY'");
		/*DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'DEPLOYED'");
		DbResource.sqlStatement.executeQuery("update FND_kF_FLEXFIELDS_B set deployment_status = 'DEPLOYED'");
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'READY' where DESCRIPTIVE_FLEXFIELD_CODE = 'PER_LOCATION_INFORMATION_EFF'");
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'READY' where DESCRIPTIVE_FLEXFIELD_CODE = 'PER_ORGANIZATION_INFORMATION_EFF'");
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'READY' where DESCRIPTIVE_FLEXFIELD_CODE = 'PER_PERSONS_DFF'");
		DbResource.sqlStatement.executeQuery("update FND_kF_FLEXFIELDS_B set deployment_status = 'READY' where KEY_FLEXFIELD_CODE = 'COST'");*/
	}
	
	public void executeUpdateQueryForIndividualFlexStatusChange(String modifyStatus,String flexCode) throws SQLException {
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'DEPLOYED'");
		DbResource.sqlStatement.executeQuery("update FND_kF_FLEXFIELDS_B set deployment_status = 'DEPLOYED'");
		DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = '"+modifyStatus+"' where DESCRIPTIVE_FLEXFIELD_CODE = '"+flexCode+"'");
		/*DbResource.sqlStatement.executeQuery("update FND_dF_FLEXFIELDS_B set deployment_status = 'READY' where DESCRIPTIVE_FLEXFIELD_CODE = 'PER_PERSONS_DFF'");
		DbResource.sqlStatement.executeQuery("update FND_kF_FLEXFIELDS_B set deployment_status = 'READY' where KEY_FLEXFIELD_CODE = 'COST'");*/
	}
	
	public String updateStatusForAllFlex(String updateStatus) {
		String sqlUpdateQuery = "BEGIN\r\n"+ 
								"update FND_dF_FLEXFIELDS_B set deployment_status = '"+updateStatus+"';\r\n"+
								"update FND_kF_FLEXFIELDS_B set deployment_status = '"+updateStatus+"';\r\n"+
								"END;\r\n";
		return sqlUpdateQuery;
	}
	
	public String updateStatusForIndividualEFFDFFFlex(String flexCode,String updateStatus) {
		String sqlUpdateQuery= "update FND_dF_FLEXFIELDS_B set deployment_status = '"+updateStatus+"' where DESCRIPTIVE_FLEXFIELD_CODE = '"+flexCode+"'";
		return sqlUpdateQuery;
	}
	
	public String updateStatusForIndividualKFFFlex(String flexCode,String updateStatus) {
		String sqlUpdateQuery= "update FND_kF_FLEXFIELDS_B set deployment_status = '"+updateStatus+"' where KEY_FLEXFIELD_CODE = '"+flexCode+"'";
		return sqlUpdateQuery;
	}
	
	public EFFFileValidations() throws JSchException, SQLException, ClassNotFoundException {
		
		jsch = new JSch();
		session = jsch.getSession(userName, faHost, 22);
		config = new Properties();
		result = new ArrayList<String>();
		consoleOutput = new ArrayList<String>();
		bValidateFlexDepComplete = false;
		bValidateDeployPatchedFlexComplete=false;
		filesList = new ArrayList<String>();
		loopCount = 1;
		flexPIM = new ArrayList<String>();
		timeTaken = null;
		FlexDeploymentStatus = null;
		bWlstExecutionComplete = false;
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(password);
		session.connect();
		/*try {
			session.connect();
			channel = session.openChannel("exec");
		} catch (JSchException e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
		
	}
	
	public void connectToDB() throws  SQLException, ClassNotFoundException {
		//DbResource.createDbStatement();
		DbResource.getDBConnection();
	}
	
	public void connectToFAHost(String command) {
		String currentDeployFlexFileNamePath=null;
		command = "ls /u01/flexdep";
		System.out.println("Current command to be executed - "+command);
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(password);
		try {
			
			((ChannelExec)channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);
			InputStream input = channel.getInputStream();
			channel.connect();
			System.out.println("Channel Connected to machine " + faHost + " server with command: " + command );
		}
		catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeFlexDeployment() throws JSchException, IOException, InterruptedException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
 
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        channel.setOutputStream(outputStream);
        PrintStream stream = new PrintStream(channel.getOutputStream());
        channel.connect();
        if(atgPatch.contains("REL13")) {
            System.out.println("Current environment belongs to 11g - "+atgPatch);
            stream.println("cd /u01/APPLTOP/fusionapps/atgpf/common/bin");
        }
        if(atgPatch.contains("12c")) {
            System.out.println("Current environment belongs to 12c - "+atgPatch);
            stream.println("cd /u01/APPLTOP/fmw/atgpf/common/bin");
        }
        /*stream.println("cd /u01/APPLTOP/fusionapps/atgpf/common/bin");*/
        stream.flush();
        waitForPrompt(outputStream,"$");
        Thread.sleep(2000);
        stream.println("./wlst.sh");
        stream.flush();
        waitForPrompt(outputStream,"wls:/offline");
 
        Thread.sleep(4000);
        stream.println("connect('faadmin','Fusionapps1','"+faHost+":11401')");
        stream.flush();
        waitForPrompt(outputStream,"wls:/FADomain/serverConfig");
        Thread.sleep(6000);
        stream.println("deployPatchedFlex()");
 
        stream.flush();
        waitForPrompt(outputStream,"wls:/FADomain/serverConfig");
        Thread.sleep(5000);
        stream.println("disconnect()");
        stream.flush();
        waitForPrompt(outputStream,"wls:/offline");
 
        stream.println("exit()");
        stream.flush();
        waitForPrompt(outputStream,"$");
        channel.disconnect();
 
    }
	
	public boolean executeCommandDeployFlex(String flexCode,String flexType) throws JSchException, IOException, InterruptedException {
		boolean sExecutionResult = false;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		 
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        channel.setOutputStream(outputStream);
        PrintStream stream = new PrintStream(channel.getOutputStream());
        channel.connect();
        if(atgPatch.contains("REL13")) {
            System.out.println("Current environment belongs to 11g - "+atgPatch);
            stream.println("cd /u01/APPLTOP/fusionapps/atgpf/common/bin");
        }
        if(atgPatch.contains("12c")) {
            System.out.println("Current environment belongs to 12c - "+atgPatch);
            stream.println("cd /u01/APPLTOP/fmw/atgpf/common/bin");
        }
        stream.flush();
        waitForPrompt(outputStream,"$");
        Thread.sleep(2000);
        stream.println("./wlst.sh");
        stream.flush();
        waitForPrompt(outputStream,"wls:/offline");
 
        Thread.sleep(4000);
        stream.println("connect('faadmin','Fusionapps1','"+faHost+":11401')");
        stream.flush();
        waitForPrompt(outputStream,"wls:/FADomain/serverConfig");
        Thread.sleep(6000);
        stream.println("deployFlex('"+flexCode+"','"+flexType+"')");
 
        stream.flush();
        waitForPrompt(outputStream,"wls:/FADomain/serverConfig");
        Thread.sleep(5000);
        stream.println("disconnect()");
        stream.flush();
        waitForPrompt(outputStream,"wls:/offline");
 
        stream.println("exit()");
        stream.flush();
        waitForPrompt(outputStream,"$");
        channel.disconnect();
		return sExecutionResult;
	}
	public void executeStatusFlipScripts() {
		String flexDepFolder = "/u01/flexdep";
		ArrayList<String> commands = new ArrayList<String>();
		String line = null;
		commands.add("rm -rf deployed.sql");
		commands.add("rm -rf deployflex.py");
		commands.add("rm -rf deppatchflex.py");
		commands.add("rm -rf edit_ready.sql");
		commands.add("rm -rf edit.sql");
		commands.add("rm -rf error.sql");
		commands.add("rm -rf ready.sql");
		commands.add("rm -rf sandbox.sql");
		commands.add("rm -rf validateflexdep.sh");
		commands.add("rm -rf validatePatchFlex.sh");
		commands.add("rm -rf APPSORA.env");
		commands.add("rm -rf flex_dep.zip");
		commands.add("unzip flex_dep.zip");
		commands.add("ls -ltr");
		commands.add("chmod -Rf 775 *.sql");
		commands.add("chmod -Rf 775 *.sh");
		commands.add("chmod -Rf 775 *.py");
		commands.add("./validateflexdep.sh fusion/"+dbPassword);
		commands.add("./validatePatchFlex.sh fusion/"+dbPassword);
		try {
			for(String command:commands) {
				ChannelExec channel = (ChannelExec) session.openChannel("exec");
	            
	            channel.setErrStream(System.err);
	            channel.setCommand(command);
	            input = channel.getInputStream();
	            System.out.println("Channel Connected to machine " + faHost + " server with command: " + command );
	            channel.setInputStream(null);
				line = null;
	            channel.connect();
	            
	            try {
					Thread.sleep(2000);
					if(command.contains("./validate")) {
						Thread.sleep(60000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            inputReader = new InputStreamReader(input);
				bufferedReader = new BufferedReader(inputReader);
				while((line=bufferedReader.readLine())!=null) {
					System.out.println(line);
					consoleOutput.add(line);
					if(command.contains("./validateflexdep.sh") && line.contains("Key Flexfield COST") && line.contains("ERROR - DEPLOYED") && line.contains("PASS")) {
						bValidateFlexDepComplete=true;
					}
					if(command.contains("./validatePatchFlex.sh") && line.contains("Key Flexfield COST") && line.contains("EDITED_READY - DEPLOYED") && line.contains("PASS")) {
						bValidateDeployPatchedFlexComplete=true;
					}
				}
				
				if(command.contains("rm -rf flex_dep.zip")) {
					uploadFileToFAHost("./src/test/resources/flex_dep.zip", "/home/oracle/");
				}
				int exitStatus = channel.getExitStatus();
				if (channel.isClosed()) {
		                if (input.available() > 0) continue;
		        }
		        bufferedReader.close();
				inputReader.close();
				channel.disconnect();
			}    
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public String checkDeployFlexFileAvailability() {
		String currentDeployFlexFileNamePath=null;
		command = "ls "+currentDeployFlexLogsPath+"DeployingFlexfields*.log";
		System.out.println("Current command to be executed - "+command);
		/*config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(password);*/
		try {
			/*session.connect();*/
			channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);
			InputStream input = channel.getInputStream();
			channel.connect();
			System.out.println("Channel Connected to machine " + faHost + " server with command: " + command );
			inputReader = new InputStreamReader(input);
			bufferedReader = new BufferedReader(inputReader);
			String line = null;
			String deployFlexFilePath = null;
			while((line = bufferedReader.readLine()) != null){
				System.out.println(line);
				if(line.contains("DeployingFlexfields")) {
					System.out.println("Current Flex logs file name - "+line);
					break;
				}
			}
			
			if(!line.contains(noAccess) || !line.contains(noFileDirectory)) {
				currentDeployFlexFileNamePath=line;
				System.out.println("Current line paramater value - "+currentDeployFlexFileNamePath);
			}
			else {
				System.out.println("ERROR - currentDeployFlexFileNamePath is 'NULL' ");
			}
			channel.disconnect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentDeployFlexFileNamePath;
	}

	public void copyDeployFlexlogFile(String srcFilePath,String destFilePath) {
		try {
			// Get a reusable channel if the session is not auto closed.
			//jsch.addIdentity(privateKeyFile);
			jsch.setKnownHosts(knownHostsFile);
			System.out.println("Starting File download for - "+srcFilePath);
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.get(srcFilePath,destFilePath);
			channelSftp.exit();
		} catch (JSchException jschException) {
			jschException.printStackTrace();
		} 
		catch(SftpException sftpException) {
			sftpException.printStackTrace();
		}
	}
	
	public void uploadFileToFAHost(String srcFilePath,String destFilePath) {
		try {
			jsch.setKnownHosts(knownHostsFile);
			System.out.println("Starting File upload from - "+srcFilePath+" to remote machine's directory - "+destFilePath);
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.put(srcFilePath,destFilePath);
			//channelSftp.exit();
		} catch (JSchException jschException) {
			jschException.printStackTrace();
		} 
		catch(SftpException sftpException) {
			sftpException.printStackTrace();
		}
	}
	
	public String searchOverAllStatus(String logfile) throws IOException {
		f1 = new File("C:/EXPIMP/"+logfile);
		fr=new FileReader(f1);
		br=new BufferedReader(fr);
		String statusText = "Overall Status";
		String currentLine;
		String currentStatusText= null;
		while((currentLine=br.readLine())!=null) {
			System.out.println("Current line - "+currentLine);
			if(currentLine.contains("EGO_ITEM_EFF")) {
				flexPIM.add(currentLine);
			}
			if(currentLine.contains("Force")) {
				forceDeploymentText(currentLine);
			}
			if(currentLine.contains(statusText)) {
				currentStatusText = currentLine;
				break;
			}
			if(currentLine.contains("FlexfieldDeploymentReport status")) {
				String tempLine = currentLine;
				tempLine = tempLine.substring(tempLine.indexOf("<")+1,tempLine.indexOf(">"));
				System.out.println("After trimming, temp text - "+tempLine);
				timeTaken=tempLine.substring(tempLine.lastIndexOf("=")+1,tempLine.length());
				System.out.println("Total timetaken - "+timeTaken);
			}
		}
		System.out.println("Text after scanning log file - "+currentStatusText);
		String overallStatus = currentStatusText.substring(currentStatusText.indexOf(": ")+2);
		System.out.println("Overall Status of Deployment - "+overallStatus);
		br.close();
		fr.close();
		return overallStatus;
	}
	
	public void forceDeploymentText(String currentText){
		if(currentText.contains("Force")) {
			result.add(currentText.substring(currentText.lastIndexOf("<", currentText.indexOf("Force")), currentText.indexOf("Force")+8));
			currentText=currentText.substring(currentText.indexOf("Force")+8, currentText.length());
			System.out.println("After trimming Full String - "+currentText);
			System.out.println("Size of current Text - "+currentText.length());
			if(currentText.length()>=0 || currentText.contains("Force")) {
				System.out.println("Force Deployment mode found for - "+loopCount);
				forceDeploymentText(currentText);
				loopCount++;
			}
		}
		if(currentText.length()<=0 && result.size()==0) {
			System.out.println("Current Text doesn't contain 'Force' Deployment mode");
		}
	}
	
	public boolean checkPIMFlexExists() {
		if(flexPIM.size()>0) {
			System.out.println("flex - EGO_ITEM_EFF entry exists. Below are the details :");
			for(int pimCount=0;pimCount<flexPIM.size();pimCount++) {
				System.out.println(flexPIM.get(pimCount));
			}
			return true;
		}
		else {
			System.out.println("EGO_ITEM_EFF entry doesn't exists under DeployingFlex");
			return false;
		}
	}
	
	public void getForceDeploymentDetails() {
		System.out.println("Total Force deploy count - "+result.size());
		if(result.size()>0) {
			System.out.println("Overall count of Force Deployment -"+result.size());
			for(int resultCount=0;resultCount<result.size();resultCount++) {
				System.out.println(result.get(resultCount));
			}
		}
		else {
			System.out.println("No Flex with Force Deployment encountered");
		}
	}
	
	public void waitForPrompt(ByteArrayOutputStream outputStream, String str) throws InterruptedException {
        int retries = 50;
        String line = null;
        String statusText = "FlexfieldDeploymentReport status=";
        String currentline = "";
        String endText = "";
        while(!outputStream.toString().contains(str)) {
            Thread.sleep(15000);
            System.out.print(outputStream.toString());
            if(outputStream.toString().contains(statusText) && outputStream.toString().contains("SUCCESS")) {
            	System.out.println("\n\nInside String validation condition\n\n");
            	/*currentline = outputStream.toString();
            	System.out.println("Current line text - "+currentline);
            	if (currentline.contains("timeTaken")) {
            		endText="timeTaken";
            	}
            	else {
            		endText=">";
            	}*/
            	FlexDeploymentStatus = "SUCCESS";/*currentline.substring(currentline.lastIndexOf(statusText)+34,currentline.indexOf(endText)-2);*/
            	System.out.println("Overall status for deployment - "+FlexDeploymentStatus);
            }
            if(outputStream.toString().contains("Exiting WebLogic Scripting Tool")) {
                  System.out.println("wlst execution completed successfully");
                  bWlstExecutionComplete = true;
              }
        }
        outputStream.reset();
        return;
        /*for (int x = 1; x < retries; x++) {
           Thread.sleep(2000);
           System.out.print(outputStream.toString());
           if (outputStream.toString().contains(str)) {
               if(outputStream.toString().contains("Exiting WebLogic Scripting Tool")) {
                  System.out.println("wlst execution completed successfully");
                  bWlstExecutionComplete = true;
              }

              outputStream.reset();
              return;
           }
        }*/
     }
	
	public String getFlexfieldDeploymentReportStatus(File flexDeploymentFile) throws IOException {
		String currentStatus = "";
		FileReader fr = new FileReader(flexDeploymentFile);
		BufferedReader br=new BufferedReader(fr);
		String statusText = "FlexfieldDeploymentReport status";
		String currentLine;
		String currentStatusText= null;
		while((currentLine=br.readLine())!=null) {
			System.out.println("Current line - "+currentLine);
			if(currentLine.contains(statusText)) {
				FlexDeploymentStatus = currentLine;
				break;
			}
		}
		currentStatus=FlexDeploymentStatus.substring(FlexDeploymentStatus.lastIndexOf(statusText)+34,FlexDeploymentStatus.indexOf("timeTaken")-2);
		System.out.println("Current status displayed - "+currentStatus);
		br.close();
		fr.close();
		return currentStatus;
	}
	
	public String getTotalFlexDeploymentTime() {
		String timeTaken=null;
		timeTaken = FlexDeploymentStatus.substring(FlexDeploymentStatus.lastIndexOf("timeTaken")+11,FlexDeploymentStatus.indexOf(">")-1);
		System.out.println("Total timetaken for FlexDeployment - "+timeTaken);
		return timeTaken;
	}
	
	public String getATGPatchLabel() {
		String currentATGLabel = null;
		if(atgPatch.contains("REL13")) {
            /*System.out.println("Current environment belongs to 11g - "+atgPatch);
            stream.println("cd /u01/APPLTOP/fusionapps/atgpf/common/bin");*/
			command = "unzip -p /u01/APPLTOP/fusionapps/atgpf/atgpf/modules/oracle.applcore.model_11.1.1/Common-Model.jar META-INF/MANIFEST.MF";
        }
        if(atgPatch.contains("12c")) {
            /*System.out.println("Current environment belongs to 12c - "+atgPatch);
            stream.println("cd /u01/APPLTOP/fmw/atgpf/common/bin");*/
        	command = "unzip -p /u01/APPLTOP/fmw/atgpf/atgpf/modules/oracle.applcore.model/Common-Model.jar META-INF/MANIFEST.MF";
        }
		
		try {
			/*session.connect();*/
			channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);
			InputStream input = channel.getInputStream();
			channel.connect();
			System.out.println("Channel Connected to machine " + faHost + " server with command: " + command );
			inputReader = new InputStreamReader(input);
			bufferedReader = new BufferedReader(inputReader);
			String line = null;
			String deployFlexFilePath = null;
			while((line = bufferedReader.readLine()) != null){
				System.out.println(line);
				if(line.contains("Oracle-Version:")) {
					System.out.println("Current line - "+line);
					currentATGLabel = line.substring(line.lastIndexOf(": ")+2,line.length());
					break;
				}
			}
			System.out.println("Current ATG label - "+currentATGLabel);
			
			channel.disconnect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentATGLabel;
	}
	
	public ArrayList<String> getFlexfieldsProcessedSuccess(String flexType) {
		ArrayList<String> flexDetails = new ArrayList<String>();
		String currentFlexProcessed = null;
		String currentFlexSuccess = null;
		String tempString = null;
		String[] tempText;
		tempText=FlexDeploymentStatus.split(">");
		for (String str:tempText) {
			/*System.out.println("Current line - "+str);*/
			if(str.contains(flexType) && str.contains("processed") && str.contains("success")) {
				tempString=str;
				/*System.out.println("Current temp String - "+tempString);*/
				break;
			}
		}
		currentFlexProcessed=tempString.substring(tempString.lastIndexOf("processed")+11,tempString.indexOf("success")-2);
		System.out.println("Total Flexfields 'processed' for '"+flexType+"' : "+currentFlexProcessed);
		currentFlexSuccess=tempString.substring(tempString.lastIndexOf("success")+9,tempString.length()-2);
		System.out.println("Total Flexfields 'success' for '"+flexType+"' : "+currentFlexSuccess);
		flexDetails.add(currentFlexProcessed);
		flexDetails.add(currentFlexSuccess);
		return flexDetails;
	}
	
	public void closeAll() throws SftpException, JSchException, java.lang.NullPointerException, SQLException {
		/*if(channelSftp.isConnected()) {
			channelSftp.exit();
		}*/
		
		try {
			DbResource.dbConnectionClose();
			session.disconnect();
		}
		catch(NullPointerException nullex) {
			nullex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			DbResource.dbConnectionClose();
			session.disconnect();
			
		}

	}
}
