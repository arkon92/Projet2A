package siwy;

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
		System.out.println("Done!");
		this.cache = true;
		notify();
	}

	synchronized Mat getMat() {
		System.out.println("Incomming");
		if ( !this.cache ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.cache = false;
		notify();
		System.out.println("Done2!");
		return this.matImage;
	}

	private Mat matImage;
	private boolean cache;

}
