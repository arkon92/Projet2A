package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Conecteur {

	private Socket socket;

	public Conecteur(String adresse, int numPort) {
		try {
			this.socket = new Socket(InetAddress.getByName(adresse), numPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void fermerSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
