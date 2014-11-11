package siwy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

public class ClientSIWY implements Runnable {
	private Socket socket;
	private String addressServ;
	private Camera camera;
	private MatOfByte bytemat;

	ClientSIWY(int num, String address, Camera camera)
			throws UnknownHostException, IOException {
		this.addressServ = address;
		this.socket = new Socket(InetAddress.getByName(addressServ), num);
		System.out.println("Client: "+socket);
		this.camera = camera;
		this.bytemat = new MatOfByte();
	}
	//InetAddress.getByName(addressServ) InetAddress.getLocalHost()
	public void wait(int a) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendImage() {
		this.wait(50);
		if (!camera.getMatImage().empty()) {
			Highgui.imencode(".jpg", camera.getMatImage(), bytemat);
			byte[] bytes = bytemat.toArray();
			try {
				DataOutputStream dout = new DataOutputStream(
						socket.getOutputStream());
				dout.writeInt(bytes.length);
				System.out.println("Send");
				dout.write(bytes);
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		while (camera.isOpen()) {
			// Pour avoir une image fluide
			this.sendImage();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
