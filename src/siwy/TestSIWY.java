package siwy;

import java.io.IOException;

import org.opencv.core.Core;

import Client.ClientSIWY;

public class TestSIWY {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String addServ = "192.168.1.12";
		ServerSIWY serv = new ServerSIWY(6500, 1);
		Thread s = new Thread(serv);
		s.start();
		
		//ClientSIWY c = new ClientSIWY(addServ, 6500);
		
	}

}
