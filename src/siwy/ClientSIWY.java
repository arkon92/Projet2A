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
	private boolean isRunning;

	ClientSIWY(int num, String address, Camera camera)
			throws UnknownHostException, IOException {
		this.addressServ = address;
		this.socket = new Socket(InetAddress.getByName(addressServ), num);
		System.out.println("Client: " + socket);
		this.camera = camera;
		this.bytemat = new MatOfByte();
		this.isRunning = true;
	}

	// InetAddress.getByName(addressServ) InetAddress.getLocalHost()
	public void wait(int a) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		this.isRunning = false;
	}

	public void sendImage() {
		this.wait(50);
		/*
		 * if (!camera.getMatImage().empty() && !this.socket.isClosed()) {
		 * Highgui.imencode(".jpg", camera.getMatImage(), bytemat); byte[] bytes
		 * = bytemat.toArray(); try { DataOutputStream dout = new
		 * DataOutputStream( socket.getOutputStream());
		 * dout.writeInt(bytes.length); dout.write(bytes); dout.flush(); } catch
		 * (IOException e) { e.printStackTrace(); } }
		 */
	}

	public void run() {
		int a;
		/*
		 * while (this.isRunning) { if (this.camera.isOpen()) { if
		 * (this.isRunning && !this.socket.isClosed()) { this.wait(10);
		 * this.sendImage(); } } } try { this.socket.close();
		 * this.camera.closeCamera(); } catch (IOException e) {
		 * e.printStackTrace(); } }
		 */
	}
}
