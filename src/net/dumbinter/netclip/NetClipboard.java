package net.dumbinter.netclip;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.UnsupportedEncodingException;

public class NetClipboard {
	private static byte[] data;
	private static Clipboard clipboard = null;
	private static String lastData = "";
	private static boolean listenOnly = false;

	public static synchronized boolean isListenOnly() {
		return listenOnly;
	}

	public static synchronized void setListenOnly(boolean listenOnly) {
		NetClipboard.listenOnly = listenOnly;
	}

	public static synchronized String getLastData() {
		return lastData;
	}

	public static synchronized void setLastData(String lastData) {
		NetClipboard.lastData = lastData;
	}

	public static synchronized Clipboard getClipboard() {
		return clipboard;
	}

	public static synchronized void setClipboard(Clipboard clipboard) {
		NetClipboard.clipboard = clipboard;
	}

	public static synchronized byte[] getData() {
		return data;
	}

	public static synchronized void setData(byte[] data) {
		NetClipboard.data = data;
	}
	public static synchronized void update() {
		try {
			String newData = new String(data, "UTF-8");
			StringSelection stringSelection = new StringSelection(newData);
			clipboard.setContents(stringSelection, null);
			lastData = newData;
		} catch (UnsupportedEncodingException e) {
			System.err.println("Couldn't update clipboard data!");
			e.printStackTrace();
		} 
	}
}
