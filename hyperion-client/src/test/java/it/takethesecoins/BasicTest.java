package it.takethesecoins;

import hyperion.Hyperion;
import it.takethesecoins.hyperion.HyperionClientConfig;

import java.awt.Color;

public class BasicTest {
	
	 public static void main(String[] args) {
	        try {
	            Hyperion hyperion = new Hyperion(HyperionClientConfig.getProperty("address"), HyperionClientConfig.getPropertyAsInt("port"));
			
	            hyperion.setColor(Color.GREEN, 50);
	            Thread.sleep(3000);
	            hyperion.clear(50);

	            byte[] imageData = {
	                    (byte)0xFF, (byte)0x00, (byte)0x00,
	                    (byte)0xFF, (byte)0xFF, (byte)0x00,
	                    (byte)0x00, (byte)0xFF, (byte)0x00,
	                    (byte)0x00, (byte)0xFF, (byte)0xFF,
	                    (byte)0x00, (byte)0x00, (byte)0xFF};
	            
	            hyperion.setImage(imageData, 5, 1, 50, 5000);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
