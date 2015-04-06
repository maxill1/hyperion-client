package it.takethesecoins;

import it.takethesecoins.hyperion.handlers.ImageDrawer;

import java.io.InputStream;

public class ImageDrawerTest {

	public static void main(String[] args) {
		try {

			//original video: http://www.tweaking4all.com/home-theatre/xbmc/xbmc-boblight-openelec-ws2811-ws2812/
			String path = "/test-image.png";
			InputStream file = ImageDrawer.class.getResourceAsStream(path);
			new ImageDrawer().draw(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
