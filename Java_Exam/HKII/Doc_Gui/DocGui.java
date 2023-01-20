package Doc_Gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DocGui {
	private Socket socket;
	private DataInputStream in ;
	private DataOutputStream out ;
	public DocGui(Socket socket) throws IOException{
		this.socket = socket;
		
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
	}
	public void DocDuLieu() {
		Thread thdoc = new Thread() {
			@Override
			public void run() {
				try {
					String doc = in.readUTF();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		thdoc.start();
	}
	public void GuiTin(String noidung) throws IOException{
		out.writeUTF(noidung);
		out.flush();
		
	}
	public String LayDuLieu() throws IOException{
		String noidung = in.readUTF();
		return noidung;
	}
	public void ngatketnoi() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
