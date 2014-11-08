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

	Printer(Socket soc, JFrame jframe) throws IOException {
		this.socket = soc;
		this.din = new DataInputStream(this.socket.getInputStream());
		this.fenetre = jframe;
	}

	public void run() {
		int length;
		JPanel jpanel;
		while ( !socket.isClosed()) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
