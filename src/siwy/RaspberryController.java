package siwy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RaspberryController implements Runnable {

	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	private boolean connected;
	private ClientSIWY client;
	private Camera camera;

	RaspberryController(String address, int num) throws UnknownHostException,
			IOException {
		this.socket = new Socket(InetAddress.getByName(address), num);
		this.din = new DataInputStream(this.socket.getInputStream());
		this.dout = new DataOutputStream(this.socket.getOutputStream());
		this.connected = true;
	}

	public void action(int code) throws UnknownHostException, IOException {
		switch (code) {
		case 1:
			//this.camera = new Camera(0);
			Thread t1,
			t2;
			t1 = new Thread(camera);
			System.out.println("Prosessing");
			t1.start();
			this.client = new ClientSIWY(6500, "127.0.0.1", camera);
			t2 = new Thread(this.client);
			t2.start();
			break;
		case 2:
			this.client.close();
			//this.camera.closeCamera();
			System.out.println("Camera is dead");
			break;
		case 5:
			this.connected = false;
			break;
		default:
			System.out.println("Numéro anormal");
			break;
		}
	}
	
	public void run() {
		int code;
		while (this.connected && !socket.isClosed() ) {
			try {
				code = din.readInt();
				System.out.println("Code recu: " + code);
				this.action(code);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
