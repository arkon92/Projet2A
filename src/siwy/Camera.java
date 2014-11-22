package siwy;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Camera implements Runnable {
	private VideoCapture webCam;
	private boolean isOpen;
	private Mat webcamImage;

	public Camera(int a) {
		this.isOpen = true;
		this.webcamImage = new Mat();
		this.webCam = new VideoCapture(a);
		this.wait(1000);
	}

	public boolean isOpen() {
		return this.isOpen;
	}

	public void stopThread() {
		this.isOpen = false;
	}

	public void closeCamera() {
		this.webCam.release();
	}

	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Mat getMatImage() {
		return this.webcamImage;
	}

	public boolean getStatusCam() {
		return this.isOpen;
	}

	public void setMatImage() {
		webCam.read(webcamImage);
	}

	public void run() {
		this.wait(1000);
		while (isOpen) {
			if (webCam.isOpened()) {
				this.setMatImage();
			}
		}
		this.wait(1000);
		System.out.println("La caméra va fermer");
		this.wait(2000);
		System.out.println("Caméra fermée");
		this.closeCamera();
	}
}
