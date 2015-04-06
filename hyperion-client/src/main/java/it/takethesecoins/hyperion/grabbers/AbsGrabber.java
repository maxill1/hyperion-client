package it.takethesecoins.hyperion.grabbers;

import it.takethesecoins.hyperion.HyperionClientConfig;
import it.takethesecoins.hyperion.handlers.ImageDrawer;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Generic grabber.
 * @author Luca
 *
 */
public abstract class AbsGrabber {

	private boolean gogogo = true;
	private ImageDrawer drawer = new ImageDrawer();

	/**
	 * Start a grabber
	 * @throws Exception
	 */
	public void start() throws Exception {

		if(hasLock()){
			System.out.println("Another grabber is running... there can be only one.");
			return;
		}

		System.out.println("Starting grabber: " +this.getClass().getSimpleName());

		initLock();

		gogogo = true;
		
		drawer.clear();
		
		int delay = HyperionClientConfig.getPropertyAsInt("frequency");
		while(gogogo){
			grab();
			Thread.sleep(delay);
		}
	}

	private void initLock() {
		try {
			File lock = new File(HyperionClientConfig.getLockFile());
			if(!lock.exists()){
				lock.createNewFile();
				lock.deleteOnExit();
			}
		} catch (Exception e) {
			System.out.println("Cannot obtain lock...aborting.");
			//no lock, no party
			stop();
		}
	}

	private void removeLock() {

		try {
			File lock = new File(HyperionClientConfig.getLockFile());
			if(!lock.exists()){
				return;
			}else{
				System.out.println("Lock removed");
				lock.delete();
			}
			
		} catch (Exception e) {
			System.out.println("Cannot remove lock...aborting.");
		}
	
	}
	
	private boolean hasLock() {
		try {
			String file = HyperionClientConfig.getLockFile();
			if(!new File(file).exists()){
				return false;
			}
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Stop current grabber
	 * @throws Exception
	 */
	public void stop() {
		gogogo = false;

		removeLock();
	}

	/**
	 * obtains a screenshot, than call the drawer
	 * @throws Exception
	 */
	public void grab() throws Exception{
		BufferedImage screenShot = getScreenshot();
		drawer.draw(screenShot);
	}

	/**
	 * obtains a screenshot. Grabber implementation
	 * @return
	 */
	protected abstract BufferedImage getScreenshot();

}