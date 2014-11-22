package siwy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import org.opencv.core.Core;

public class ServerSIWY implements Runnable {

	private ServerSocket socketserver;
	private Socket[] socket;
	private int nbrClient;
	private JFrame[] fenetre;
	private int clientMax;
	private Printer[] printer;
	private Thread[] thread;

	ServerSIWY(int num, int max) throws IOException {
		this.socketserver = new ServerSocket(num,5);
		this.nbrClient = 0;
		this.socket = new Socket[5];
		this.clientMax = max;
		this.fenetre = new JFrame[max];
		for (int i = 0; i < max; i++) {
			this.fenetre[i] = new JFrame("Reception" + i);
			this.fenetre[i].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.fenetre[i].setSize(800, 600);
			this.fenetre[i].setVisible(true);
		}
		this.printer = new Printer[this.clientMax];
		this.thread = new Thread[this.clientMax];
	}

	public int getNbrClient() {
		return this.nbrClient;
	}

	public void run() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// On attend de recevoir les clients
		while (this.nbrClient < this.clientMax) {
			try {
				this.socket[this.nbrClient] = socketserver.accept();
				System.out.println(socket[this.nbrClient]);
				System.out.println(this.socketserver);
				printer[this.nbrClient] = new Printer(socket[this.nbrClient],
						fenetre[this.nbrClient]);
				thread[this.nbrClient] = new Thread(printer[this.nbrClient]);
				this.nbrClient++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Trucs a faire
		for (int i = 0; i < this.nbrClient; i++) {
			thread[i].start();
		}
		boolean testOpen = true;
		System.out.println("Client connecté");
		while (testOpen) {
			for (int i = 0; i < this.nbrClient; i++) {
				testOpen = testOpen & fenetre[i].isShowing();
			}
		}
		// Fermeture sockets
		try {
			for (int i = 0; i < this.nbrClient; i++) {
				socket[i].close();
			}
			socketserver.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
