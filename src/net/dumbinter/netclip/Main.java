package net.dumbinter.netclip;

import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Integer aesKeysize = new Integer(Config.get().getProperty("netclip.aeskeysize"));
		
		
		try {
			Crypto.init(aesKeysize);
		} catch (UnsupportedEncodingException e) {
			System.exit(-1);
		}
		
		Integer port = new Integer(Config.get().getProperty("netclip.port"));
		String broadcast = Config.get().getProperty("netclip.broadcast");
		
		
		
		System.out.println("Broadcasting changes to: " + broadcast);
		System.out.println("Listening on port: " + String.valueOf(port));
		System.out.println("Using AES key size of (bit): " + String.valueOf(aesKeysize));
		
		NetClipboard.setClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
		
		new Thread(new ClipboardObserver(new UDPServer(broadcast,port))).start();
		new Thread(new UDPReceiver(port)).start();
		new Thread(new TCPServer(port)).start();
	}

}