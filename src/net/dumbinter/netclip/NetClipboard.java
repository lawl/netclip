package net.dumbinter.netclip;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class NetClipboard {
	private static byte[] data;
	private static Clipboard clipboard = null;
	private static String lastData = "";

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
	public static synchronized void update(){
		String newData = new String(data);
		StringSelection stringSelection = new StringSelection(newData);
		clipboard.setContents(stringSelection, null);
		lastData = newData;
	}
}
