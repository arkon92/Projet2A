package siwy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Printer implements Runnable {
	private Socket socket;
	private DataInputStream din;
	private JFrame fenetre;
	private boolean isRunning;

	Printer(Socket soc, JFrame jframe) throws IOException {
		this.socket = soc;
		this.din = new DataInputStream(this.socket.getInputStream());
		this.fenetre = jframe;
		this.isRunning = true;
	}

	public void close() {
		this.isRunning = false;
	}
	
	public void wait( int a ) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		int length = 0;
		JPanel jpanel;
		System.out.println(socket.isClosed());
		while (this.isRunning && !this.socket.isClosed() && length!=9999 ) {
			try {
				length = din.readInt();
				if (length == 0) {
					continue;
				}
				byte[] bytes = new byte[length];
				din.readFully(bytes, 0, length);
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(
						bytes));
				jpanel = new JPanel();
				JLabel imagej = new JLabel(new ImageIcon(image));
				jpanel.add(imagej);
				this.fenetre.add(jpanel);
				this.fenetre.setContentPane(jpanel);
				this.fenetre.revalidate();
				this.wait(10);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		this.fenetre.dispose();
	}
}
