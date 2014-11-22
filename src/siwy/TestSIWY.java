package siwy;

import java.io.IOException;

import org.opencv.core.Core;

public class TestSIWY {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Camera cam = new Camera();
		cam.initCamera(0);
		Thread t = new Thread(cam);
		t.start();

		/*
		 * Camera cam2 = new Camera(); cam2.initCamera(1); Thread t2 = new
		 * Thread(cam2); t2.start();
		 */

		ServerSIWY serv = new ServerSIWY(6500, 1);
		Thread s = new Thread(serv);
		s.start();

		String addServ = "127.0.0.1";
		//String addServ = "81.48.122.227";
		//String addServ = "192.168.1.22";
		ClientSIWY client = new ClientSIWY(6500, addServ, cam);
		Thread c = new Thread(client);
		c.start();

		/*
		 * ClientSIWY client2 = new ClientSIWY(6500, addServ, cam2); Thread c2 =
		 * new Thread(client2); c2.start();
		 */

		// cam.stopThread();
	}

}
