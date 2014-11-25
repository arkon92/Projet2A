package Client;

import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;


public class TMathBytes implements Runnable {

	private MatOfByte matByte;
	private CacheTabBytes cacheBytes;
	private byte[] tabByte;
	private CacheMatImage cacheMat;
	private boolean ouvert;

	public TMathBytes(CacheMatImage m, CacheTabBytes t) {
		this.matByte = new MatOfByte();
		this.cacheMat = m;
		this.cacheBytes = t;
		this.ouvert = true;
		new Thread(this).start();
	}

	public void close() {
		this.ouvert = false;
	}
	
	public void run() {
		while (this.ouvert) {
			Highgui.imencode(".jpg", this.cacheMat.getMat(), this.matByte);
			this.tabByte = matByte.toArray();
			this.cacheBytes.setTab( this.tabByte );
		}
	}

}
