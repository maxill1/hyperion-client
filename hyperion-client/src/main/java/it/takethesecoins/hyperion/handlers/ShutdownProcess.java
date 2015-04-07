package it.takethesecoins.hyperion.handlers;

import hyperion.Hyperion;
import it.takethesecoins.hyperion.grabbers.AbsGrabber;

/**
 * Send a clear command to {@link Hyperion}
 * @author Luca
 *
 */
public class ShutdownProcess extends Thread {

	private AbsGrabber grabber;

	public ShutdownProcess(AbsGrabber grabber) {
		this.grabber = grabber;
	}

	@Override
	public void run() {
		System.out.println("Hyperion-client shutting down...");
		if(grabber != null){
			 grabber.stop();
		}
		System.out.println("Hyperion cleared...");
	}

}
