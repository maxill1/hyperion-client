package it.takethesecoins.hyperion;

import it.takethesecoins.hyperion.grabbers.AbsGrabber;
import it.takethesecoins.hyperion.handlers.ShutdownProcess;

/**
 * Hyperion client main.
 * @author Luca
 *
 */
public class HyperionClient {

	public static void main(String[] args) {
		try {

			String grabImpl = HyperionClientConfig.getProperty("grabber");
			boolean forceStop = false;
			if(args!=null){
				//TODO cleaunp
				for (int i = 0; i < args.length; i++) {
					String param = args[i];
					if(param.toLowerCase().equals("-f")){
						forceStop = true;
					}
					if(param.toLowerCase().equals("-c")){
						try {
							int valueIndex = i+1;
							String value = args[valueIndex];
							if(value.startsWith("-")){
								System.out.println("-c provided with an invalid sintax: '"+value+"'");
								System.exit(0);
							}
							HyperionClientConfig.init(value);
						} catch (Exception e) {
							System.out.println("Cannot load config file.");
							System.exit(0);
						}
					}
					
					if(param.toLowerCase().equals("-g")){
						int valueIndex = i+1;
						String value = args[valueIndex];
						if(value.startsWith("-")){
							System.out.println("-g provided with an invalid sintax: '"+value+"'");
							System.exit(0);
						}
						grabImpl = value;
					}
				}
			}

			AbsGrabber 	grabber = AbsGrabber.getImpl(grabImpl);
			
			Runtime.getRuntime().addShutdownHook(new ShutdownProcess(grabber));
			
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
