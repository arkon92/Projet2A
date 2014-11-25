package Client;

import org.opencv.core.Mat;

public class CacheMatImage {

	public CacheMatImage() {
		this.cache = false;
	}

	synchronized void setMat(Mat mat) {
		if (this.cache) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.matImage = mat;
		this.cache = true;
		notify();
	}

	synchronized Mat getMat() {
		if ( !this.cache ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.cache = false;
		notify();
		return this.matImage;
	}

	private Mat matImage;
	private boolean cache;

}
