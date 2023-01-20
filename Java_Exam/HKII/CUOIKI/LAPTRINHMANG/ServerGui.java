package CUOIKI.LAPTRINHMANG;

import java.io.DataOutputStream;
import java.net.Socket;

public class ServerGui extends Thread{
	private Socket socket;
	private String noidung;
	public ServerGui(Socket socket, String noidung) {
		this.socket = socket;
		this.noidung = noidung;
	}
	@Override
	public void run() {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(noidung);
			out.flush();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
}
