package it.takethesecoins.hyperion.grabbers;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * Grab screen using {@link Robot}
 * @author Luca
 *
 */
public class RobotGrabber extends AbsGrabber{

		@Override
		protected BufferedImage getScreenshot() {
			BufferedImage screenShot = null;
			try {
				screenShot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			 return screenShot;
		}
	}