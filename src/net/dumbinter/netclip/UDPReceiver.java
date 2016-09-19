package net.dumbinter.netclip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPReceiver implements Runnable {
	DatagramSocket socket;
	int port;
	
	public UDPReceiver(int port) {
		this.port=port;
		try {
			socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("Listening for input");
		while(true) { 
			byte[] recvBuf = new byte[14];
			DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
			try {
				socket.receive(packet);
				final InetAddress addr = packet.getAddress();
				System.out.println("Received from: " + addr.getHostAddress() + " [" + new String(packet.getData()) + "]");
				// Only process the message if we didn't send it
				if (NetworkInterface.getByInetAddress(addr) == null) {
					if ("NETCLIP_NOTIFY".equals(new String(packet.getData()))) {
						System.out.println("Fetching Clipboard Data via TCP");
						NetClipboard.setData(TCPClient.fetchClipboard(packet.getAddress(),port));
						NetClipboard.update();
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
