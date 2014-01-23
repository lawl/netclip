package net.dumbinter.netclip;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {
	
	private InetAddress host;
    private DatagramSocket socket;
    int port;
	
	public UDPServer(String broadcast,int port) {
        try {
        	this.port=port;
			socket = new DatagramSocket (null);
			socket.setBroadcast(true);
			host = InetAddress.getByName(broadcast);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void broadcast() throws UnsupportedEncodingException {
		DatagramPacket packet;
		byte[] buf = "NETCLIP_NOTIFY".getBytes("US-ASCII");
		packet=new DatagramPacket (buf, buf.length, host, port);
        try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
