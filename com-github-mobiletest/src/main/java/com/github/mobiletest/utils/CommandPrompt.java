package com.github.mobiletest.utils;
/**
 * Command Prompt - this class contains method to run windows and mac commands  
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.github.mobiletest.test.TestContext;

public class CommandPrompt {

	Process process;
	ProcessBuilder builder;

	private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };
    private static final String[] OS_LINUX_RUNTIME = { "/bin/bash", "-l", "-c" };

    public CommandPrompt() {
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
	public static Process executeCommand(String command) throws InterruptedException, IOException
	{
		Process tempProcess;
		ProcessBuilder tempBuilder;
		
		String os = System.getProperty("os.name");
		System.out.println("INFO: Run Command on [" + os + "]: " + command);

		String[] allCommand;
		// build cmd proccess according to os
		if(os.contains("Windows")) // if windows
		{
            allCommand = concat(WIN_RUNTIME, new String[]{command});
        } else 
        {
            allCommand = concat(OS_LINUX_RUNTIME, new String[]{command});
        }
		tempBuilder = new ProcessBuilder(allCommand);
		tempBuilder.redirectErrorStream(true);
		Thread.sleep(1000);
		tempProcess = tempBuilder.start();
		
		return tempProcess;
	}
	
	/**
	 * This method run command on windows and mac
	 * @param command to run  
	 */
	public String runCommand(String command) throws InterruptedException, IOException
	{
		process = executeCommand(command);

		// get std output
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line="";
		String allLine="";
		while((line=reader.readLine()) != null){
			
			if(line.isEmpty()) continue;
			
			allLine=allLine+""+line+"\n";
			if(line.contains("Appium REST http interface listener started"))
			{
				new Thread(new StreamDrainer(reader)).start();
				break;
			}
		}

		return allLine;
	}

	public void destory()
	{
		process.destroy();
	}

	class StreamDrainer implements Runnable {   
		private BufferedReader reader;   
		public StreamDrainer(BufferedReader ins) 
		{   
			this.reader = ins;   
		}   
		public void run(){   
			String line = null;
			try {
				while ((line = reader.readLine()) != null) 
				{   
					if(!"ON".equalsIgnoreCase(TestContext.getInstance().getVariable("CMD_Mode")))
					{
						System.out.println(line);
					}
				}  

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
	
		Process process = CommandPrompt.executeCommand("adb -s FOTGLMH621709540 shell logcat -v time Telephony:V *:S");
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line="";
		while((line=reader.readLine()) != null){
			
			if(line.isEmpty()) continue;
			
			System.out.println(line);
		}
		
	}
}
