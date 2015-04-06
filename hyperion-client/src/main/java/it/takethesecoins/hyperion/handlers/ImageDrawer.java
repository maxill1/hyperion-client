package it.takethesecoins.hyperion.handlers;

import hyperion.Hyperion;
import it.takethesecoins.hyperion.HyperionClientConfig;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageDrawer {
	

	private static final int RGB_BYTES = 3;
	
    Hyperion hyperion;
    
    public ImageDrawer(){
    	try {
    		hyperion = new Hyperion(HyperionClientConfig.getProperty("address"), HyperionClientConfig.getPropertyAsInt("port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
	
	public void draw(InputStream file) {
		
			BufferedImage image;
			try {
				image = ImageIO.read(file);
				draw(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
		
	public void draw(BufferedImage image) {
		
		try {
			
			int h = image.getData().getBounds().height;
			int w = image.getData().getBounds().width;
		
			ColorParser parser = new ColorParser(image);
			
			int widthLeds = HyperionClientConfig.getPropertyAsInt("leds.width");
			int heightlLeds = HyperionClientConfig.getPropertyAsInt("leds.height");
			
		    int priority = HyperionClientConfig.getPropertyAsInt("priority");
			
			
			int[][][] coordinates = new int[widthLeds][heightlLeds][2];
			
			for (int riga = 0; riga < heightlLeds; riga++) {
				for (int colonna = 0; colonna < widthLeds; colonna++) {
				
					int x = w/widthLeds*colonna;
				
					int y = h/heightlLeds*riga;

					coordinates[colonna][riga] = new int[]{x,y};
				}
			}
			
			int imageSize = RGB_BYTES * widthLeds * heightlLeds ;
			
			byte[] bytes = new byte[imageSize];
			
			int counter = 0;
			
			for (int riga = 0; riga < heightlLeds; riga++) {

				for (int colonna = 0; colonna < widthLeds; colonna++) {
				
					byte[] rgb = {0,0,0};
					
					int x = coordinates[colonna][riga][0];
					int y = coordinates[colonna][riga][1];
					
					rgb = parser.getColorAsByte(x, y);
					
					bytes[counter++] = rgb[0];
					bytes[counter++] = rgb[1];
					bytes[counter++] = rgb[2];
				}
				
			}
			
			byte[] imageData = bytes;

			hyperion.setImage(imageData, widthLeds, heightlLeds, priority);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Usa {@link Robot#getPixelColor(int, int)} (lento)
	 */
	@Deprecated
	public void draw() {
		try {
	        Robot robot = new Robot();		
			int h = 1080;
			int w = 1920;
			
			int widthLeds = HyperionClientConfig.getPropertyAsInt("leds.width");
			int heightlLeds = HyperionClientConfig.getPropertyAsInt("leds.heigth");
			
			int[][][] coordinates = new int[widthLeds][heightlLeds][2];
			
			for (int riga = 0; riga < heightlLeds; riga++) {
				for (int colonna = 0; colonna < widthLeds; colonna++) {
				
					int x = w/widthLeds*colonna;
				
					int y = h/heightlLeds*riga;

					coordinates[colonna][riga] = new int[]{x,y};
				}
			}
			
			int imageSize = RGB_BYTES * widthLeds * heightlLeds ;
			
			byte[] bytes = new byte[imageSize];
			
			int counter = 0;
			
			for (int riga = 0; riga < heightlLeds; riga++) {

				for (int colonna = 0; colonna < widthLeds; colonna++) {
				
					byte[] rgb = {0,0,0};
					
					int x = coordinates[colonna][riga][0];
					int y = coordinates[colonna][riga][1];

					Color color = robot.getPixelColor(x, y);
					rgb = new ColorParser().getColorAsByte(color.getRGB());
					
					bytes[counter++] = rgb[0];
					bytes[counter++] = rgb[1];
					bytes[counter++] = rgb[2];
				}
				
			}
			
			byte[] imageData = bytes;

            hyperion.setImage(imageData, widthLeds, heightlLeds, 50);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	
	}

	public void clear() {
		try {
			hyperion.clearall();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
