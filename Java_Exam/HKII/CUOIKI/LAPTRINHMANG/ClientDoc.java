package CUOIKI.LAPTRINHMANG;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.Vector;

public class ClientDoc extends Thread {
	public ClientDoc(Socket socket) {
		this.socket = socket;
	}

	private Socket socket;
	private String noidung = "";
	private Vector vTraCuu = new Vector();
	private String hoten;
	private String cmnd;
	private String sdt;
	private String diachi;
	private String mavetau;
	private String noiden;
	private String noidi;
	private String ngaydi;
	private String toa;
	private String loaicho;
	private String loaive;
	private String giatien;
	private String tenchuyentau ="";

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getMavetau() {
		return mavetau;
	}

	public void setMavetau(String mavetau) {
		this.mavetau = mavetau;
	}
	
//	public Vector getvTraCuu() {
//		return vTraCuu;
//	}
//
//	public void setvTraCuu(Vector vTraCuu) {
//		this.vTraCuu = vTraCuu;
//	}

	public String getTenchuyentau() {
		return tenchuyentau;
	}

	public void setTenchuyentau(String tenchuyentau) {
		this.tenchuyentau = tenchuyentau;
	}

	public String getNoiden() {
		return noiden;
	}

	public void setNoiden(String noiden) {
		this.noiden = noiden;
	}

	public String getNoidi() {
		return noidi;
	}

	public void setNoidi(String noidi) {
		this.noidi = noidi;
	}

	public String getNgaydi() {
		return ngaydi;
	}

	public void setNgaydi(String ngaydi) {
		this.ngaydi = ngaydi;
	}

	public String getToa() {
		return toa;
	}

	public void setToa(String toa) {
		this.toa = toa;
	}
	

	public String getLoaicho() {
		return loaicho;
	}

	public void setLoaicho(String loaicho) {
		this.loaicho = loaicho;
	}

	public String getLoaive() {
		return loaive;
	}

	public void setLoaive(String loaive) {
		this.loaive = loaive;
	}

	public String getGiatien() {
		return giatien;
	}

	public void setGiatien(String giatien) {
		this.giatien = giatien;
	}
	

	@Override
	public void run() {
//		DataInputStream in = null;
		try {
			while (true) {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				System.out.println("doc du lieu.....");
				String sms = in.readUTF();
				System.out.println("da nhan " + sms);
				noidung = sms;
//				if (noidung != "") {
//					if (sms.equalsIgnoreCase("[exit]")) {
//						in.close();
//					} else {
//						LamMoi();
						System.out.println("Vao Ham");	
						TachChuoi(sms);
//					}
//				}
//				Thread.sleep(2000);
//				noidung = "";
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for(int i = 0; i< vTraCuu.size();i++) {
//			Vector row = (Vector) vTraCuu.get(i);
//			String test = (String) row.elementAt(0);
//			System.out.println(test);
//		}
	}

	private void TachChuoi(String sms) {
		int size = sms.length();
		int index = 0;

		Vector row = new Vector();
		// add dýõòc 1 voÌng khi gãòp \n thiÌ noì bãìt ðâÌu return laòi rôÌi add laòi 
		for (int i = 0; i < size; i++) {
			if (sms.charAt(i) == ',') {
				index = index + 1;
				continue;
			}
			
			if (index == 0) {
				mavetau = mavetau + sms.charAt(i);
			}
			if (index == 1) {
				tenchuyentau = tenchuyentau + sms.charAt(i);
				
			}
			if (index == 2) {
				noidi = noidi + sms.charAt(i);
				
			}
			if (index == 3) {
				noiden = noiden + sms.charAt(i);
				
			}
			if (index == 4) {
				ngaydi = ngaydi + sms.charAt(i);
				
			}
			if (index == 5) {
				toa = toa + sms.charAt(i);
				
			}
			if (index == 6) {
				loaicho = loaicho + sms.charAt(i);
				
			}
			if (index == 7) {
				loaive= loaive+ sms.charAt(i);
				
			}
			if (index == 8) {
				giatien = giatien + sms.charAt(i);
				
			}
			if(index == 9) {
				cmnd = cmnd + sms.charAt(i);
			}

//			if(sms.charAt(i) == '-') {
//				index = 0;
//				TachChuoi(sms);
//				continue;
//				
//			}
			
			
		}
		row.add(mavetau);
		row.add(tenchuyentau);
		row.add(noidi);
		row.add(noiden);
		row.add(ngaydi);
		row.add(toa);
		row.add(loaicho);
		row.add(loaive);
		row.add(giatien);
		vTraCuu.add(row);
			
//			System.out.println("Row" + row.toString());
			System.out.println("Vtracuu: "+vTraCuu.toString());
		
	}


	private void LamMoi() {
		hoten = "";
		cmnd = "";
		sdt = "";
		diachi = "";
		mavetau = "";
		noiden = "";
		noidi = "";
		ngaydi = "";
		toa = "";
		loaicho = "";
		loaive = "";
		giatien = "";
		tenchuyentau = "";
		

	}

}
