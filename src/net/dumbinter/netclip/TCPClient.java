package net.dumbinter.netclip;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
	public static byte[] fetchClipboard(InetAddress adr, int port) {
		try {
			Socket clientSocket = new Socket(adr.getHostAddress(), port);
			
			InputStream is = clientSocket.getInputStream();

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}

			buffer.flush();
			byte[] content = Crypto.decrypt(buffer.toByteArray());
			System.out.println("TCP : " + new String(content));
			return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
