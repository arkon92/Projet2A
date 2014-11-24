package siwy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerControl implements Runnable {

	private ServerSocket servSocket;
	private Socket[] socket;
	private DataInputStream[] din;
	private DataOutputStream[] dout;
	private boolean connected;
	private ServerSIWY server;

	ServerControl(int num) throws UnknownHostException, IOException {
		this.servSocket = new ServerSocket(num);
		this.connected = true;
		this.socket = new Socket[2];
		this.din = new DataInputStream[2];
		this.dout = new DataOutputStream[2];

	}

	public void closeConnecion() {
		this.connected = false;
	}

	public void wait( int a) throws InterruptedException {
		Thread.sleep(a);
	}
	
	public void action(int code) throws IOException, InterruptedException {
		switch (code) {
		case 1:
			this.server = new ServerSIWY(6500, 1);
			Thread t = new Thread(this.server);
			t.start();
			this.dout[0].writeInt(1);
			this.dout[0].flush();
			break;
		case 2:
			if (this.server.getState() == true) {
				this.server.closeServer();
				this.dout[0].writeInt(2);
				this.dout[0].flush();
				System.out.println("Server de traitement coupé");
			}
			break;
		default:
			System.out.println("COde incorect");
			break;
		}

	}

	public void run() {
		int code = 0;
		try { // Conection avec le controller Rasp
			this.socket[0] = servSocket.accept();
			this.din[0] = new DataInputStream(this.socket[0].getInputStream());
			this.dout[0] = new DataOutputStream(
					this.socket[0].getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("R conecté");
		while (connected) { // Conection avec le controlleur Apk
			try {
				this.socket[1] = servSocket.accept();
				System.out.println("COntroller connecté");
				this.din[1] = new DataInputStream(
						this.socket[1].getInputStream());
				this.dout[1] = new DataOutputStream(
						this.socket[1].getOutputStream());
				while (code != 4 && code != 5) {
					code = din[1].readInt();
					this.action(code);
				}
				this.socket[1].close();
				if (code == 5) {
					this.dout[0].writeInt(5);
					this.dout[0].flush();
					this.closeConnecion();
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			this.socket[0].close();
			this.servSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}