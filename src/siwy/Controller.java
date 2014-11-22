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

	public void run() {
		int code;
		String test;
		while (this.connected) {
			code = this.scanner.nextInt();
			switch (code) {
			case 1:
				System.out.println("Cas 1");
				try {
					dout.writeInt(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("Cas 2");
				try {
					dout.writeInt(2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("Cas 3");
				try {
					dout.writeInt(3);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				System.out.println("Cas 4");
				try {
					dout.writeInt(4);
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.connected = false;
				break;
			case 5:
				System.out
						.println("Attention ceci entrainera une coupure totale du système");
				test = scanner.nextLine();
				//System.out.println("Etes vous sur o/n?");
				//if (test == "o") {
					try {
						dout.writeInt(5);
					} catch (IOException e) {
						e.printStackTrace();
					}
				//}
				this.connected = false;
				break;
			default:
				System.out.println("Default");
				try {
					dout.writeInt(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
