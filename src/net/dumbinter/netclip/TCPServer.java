package net.dumbinter.netclip;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable{
	
	ServerSocket welcomeSocket = null;
	
	public TCPServer(Integer port){
        try {
        	welcomeSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				Socket connectionSocket = welcomeSocket.accept();
	            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	            byte[] data;
	            if(NetClipboard.isListenOnly()){
	            	data=new String("").getBytes("US-ASCII");
	            } else {
	            	data = Crypto.encrypt(NetClipboard.getData());
	            }
	            outToClient.write(data);
	            outToClient.close();
	            connectionSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
