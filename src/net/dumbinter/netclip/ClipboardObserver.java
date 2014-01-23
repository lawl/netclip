package net.dumbinter.netclip;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardObserver implements Runnable{
	
	
	UDPServer broadcaster = null;
	
	public ClipboardObserver(UDPServer broadcaster) {
		this.broadcaster=broadcaster;
	}
	
	public void run() {
		System.out.println("Observer running");
		while(true){
			try {
				if(!NetClipboard.isListenOnly() && NetClipboard.getClipboard().isDataFlavorAvailable(DataFlavor.stringFlavor)){
					final String clipboardData = (String) NetClipboard.getClipboard().getData(DataFlavor.stringFlavor);
					if (NetClipboard.getLastData().hashCode() != clipboardData.hashCode()) {
						NetClipboard.setLastData(clipboardData);
						NetClipboard.setData(clipboardData.getBytes("UTF-8"));
						System.out.println("Local change detected: "+ clipboardData);
						broadcaster.broadcast();
					}
				}
			} catch (UnsupportedFlavorException | IOException e) {
				System.err.println("Couldn't read clipboard data!");
				e.printStackTrace();
			} 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// who cares?
			}
		}
	}
}