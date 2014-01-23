package net.dumbinter.netclip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	static Properties prop = new Properties();
	static boolean initialized = false;

	public static void read() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("config.properties");
		prop.load(stream);
	}
	
	public static Properties get() {
		if(!initialized){
			try {
				read();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return prop;
	}
}
