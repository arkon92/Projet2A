package siwy;

import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

public class TMathBytes implements Runnable {

	private MatOfByte matByte;
	private byte[] tabByte;
	private CacheMatImage cache;
	private boolean ouvert;

	public TMathBytes(CacheMatImage c) {
		this.matByte = new MatOfByte();
		this.cache = c;
		this.ouvert = true;
		new Thread(this).start();
	}

	public void run() {
		while (this.ouvert) {
			Highgui.imencode(".jpg", this.cache.getMat(), this.matByte);
			this.tabByte = matByte.toArray();
		}
	}

}
