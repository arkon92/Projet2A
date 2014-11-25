package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Envoyeur implements Runnable {

	private CacheTabBytes cacheBytes;
	private boolean ouvert;
	private DataOutputStream dout;

	public Envoyeur(CacheTabBytes b, Socket s) {
		try {
			this.dout = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cacheBytes = b;
		this.ouvert = true;
		new Thread(this).start();
	}

	public void close() {
		this.ouvert = false;
	}

	public void run() {
		while (this.ouvert) {
			try {
				byte[] tab = this.cacheBytes.getTab();
				this.dout.writeInt(tab.length);
				this.dout.write(tab);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.dout.writeInt(9999); // Envoie d'un message pour prévenir de la
										// fermeture conection
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
