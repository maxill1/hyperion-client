package it.takethesecoins.hyperion.handlers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Ottiene informazioni sui colori
 * @author Luca
 *
 */
public class ColorParser {

	private BufferedImage image;

	public ColorParser(BufferedImage image){
		this.image = image;
	}
	@Deprecated
	public ColorParser() {
	}

	public Color getColor(int x, int y){
		int rgb=  image.getRGB(x,y);
		return new Color(rgb);
	}

	public byte[] getColorAsByte(int x, int y){
		int rgb=  image.getRGB(x,y);
		return getColorAsByte(rgb);
	}


	public byte[] getColorAsByte(int rgb) {

		byte[] argb = ByteBuffer.allocate(4).putInt(rgb).array();

		byte[] bytes = new byte[]{argb[1], argb[2], argb[3]};
		return bytes;
	}

	public String getHEX(Color color) {
		String hex = Integer.toHexString(color.getRGB() & 0xffffff);
		if (hex.length() < 6) {
			hex = "0" + hex;
		}
		hex = "#" + hex;
		return hex;
	}


}
