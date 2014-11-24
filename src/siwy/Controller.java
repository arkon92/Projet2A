package siwy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Controller implements Runnable {

	private Socket socket;
	private Scanner scanner;
	private DataInputStream din;
	private DataOutputStream dout;
	private boolean connected;

	Controller(String address, int num) throws UnknownHostException,
			IOException {
		this.socket = new Socket(InetAddress.getByName(address), num);
		this.din = new DataInputStream(this.socket.getInputStream());
		this.dout = new DataOutputStream(this.socket.getOutputStream());
		this.scanner = new Scanner(System.in);
		this.connected = true;
	}

	public void action(int code) {
		switch (code) {
		case 1:
			System.out.println("Cas 1");
			try {
				dout.writeInt(1);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Cas 2");
			try {
				dout.writeInt(2);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Cas 3");
			try {
				dout.writeInt(3);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("Deconection du controller");
			try {
				dout.writeInt(4);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.connected = false;
			break;
		case 5:
			System.out
					.println("Attention ceci entrainera une coupure totale du système");
			try {
				dout.writeInt(5);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.connected = false;
			break;
		default:
			System.out.println("Default");
			try {
				dout.writeInt(0);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void wait ( int a ) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int code = 0;
		while (this.connected && code != 5) {
			code = this.scanner.nextInt();
			this.action(code);
			this.wait(50);
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
