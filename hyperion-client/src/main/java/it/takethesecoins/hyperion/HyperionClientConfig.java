package it.takethesecoins.hyperion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HyperionClientConfig {

	private static Properties prop = new Properties();
	static{
		init(null);
	}
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public static void init(String fileConfigPath) {
		InputStream io = null;
		try {
			if(fileConfigPath!=null){
				io = new FileInputStream(fileConfigPath);
			}else{
				io = HyperionClientConfig.class.getResourceAsStream("/hyperion.config");
			}
			prop.load(io);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				io.close();
			} catch (IOException e) {}
		}		
	}
	
	public static int getPropertyAsInt(String key) {
		
		String value = getProperty(key);
		if(value==null){
			value ="";
		}
		return Integer.parseInt(value.trim());
	}
	
	public static String getLockFile() {
		String lock = System.getProperty("java.io.tmpdir")+File.separatorChar+"hyperion-client.lock";
		return lock;
	}
	
}
