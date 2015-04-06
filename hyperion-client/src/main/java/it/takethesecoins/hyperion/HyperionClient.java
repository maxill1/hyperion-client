package it.takethesecoins.hyperion;

import it.takethesecoins.hyperion.grabbers.JNAGrabber;

/**
 * Hyperion client main.
 * @author Luca
 *
 */
public class HyperionClient {

	public static void main(String[] args) {
		try {
			
			boolean forceStop = false;
			if(args!=null){
				
				for (int i = 0; i < args.length; i++) {
					String param = args[i];
					if(param.toLowerCase().equals("-f")){
						forceStop = true;
					}
					if(param.toLowerCase().startsWith("-c")){
						String file = param.substring(2);
						HyperionClientConfig.init(file);
					}
					//TODO user config
					//TODO alt grabber
				}
			}

			JNAGrabber grabber = new JNAGrabber();
			if(forceStop){
				grabber.stop();
			}
			grabber.start();	
		} catch (Exception e) {
			System.out.println("Cannot start hyperion-client: " + e.getMessage() );
			e.printStackTrace();
		}
	}

}
