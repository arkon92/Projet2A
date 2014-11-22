package siwy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerControl implements Runnable {

	private ServerSocket servSocket;
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	private boolean connected;
	private ServerSIWY server;

	ServerControl( int num ,ServerSIWY serv) throws UnknownHostException, IOException {
		this.servSocket = new ServerSocket(num);
		this.connected = true;
		this.server = serv;
	}

	void closeConnecion() {
		this.connected = false;
	}
	
	public void run() {
		int code = 0;
		while ( connected ) {
			try {
				this.socket = servSocket.accept();
				this.din = new DataInputStream( this.socket.getInputStream());
				this.dout = new DataOutputStream( this.socket.getOutputStream());
				while( code != 4 && code != 5) {
					code = din.readInt();
					System.out.println("Recu: " + code);
				}
				this.socket.close();
				if( code == 5 ) {
					this.closeConnecion();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.servSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}