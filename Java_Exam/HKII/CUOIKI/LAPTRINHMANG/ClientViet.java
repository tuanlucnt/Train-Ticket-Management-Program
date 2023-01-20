package CUOIKI.LAPTRINHMANG;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientViet extends Thread{
	Socket socket;
	String noidung;
	public ClientViet(Socket socket, String noidung) {
		this.socket = socket;
		this.noidung = noidung;
	}
	@Override
	public void run() {
		DataOutputStream out = null;
		try {
			while(true) {
				out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(noidung);
				out.flush();
				
			}
		} catch (Exception e) {
			try {
				out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	
	}

}
