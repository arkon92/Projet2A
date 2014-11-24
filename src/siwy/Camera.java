package siwy;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Camera implements Runnable {
	private VideoCapture webCam;
	private CacheMatImage cache;
	private boolean ouvert;

	public Camera(int a, CacheMatImage c) {
		this.webCam = new VideoCapture(a);
		this.cache = c;
		this.ouvert = true;
		new Thread(this).start();
	}

	public void run() {
		while (this.ouvert) {
			Mat m = new Mat();
			if (this.webCam.isOpened()) {
				this.webCam.read(m);
				this.cache.setMat(m);
			}
		}
	}
}
