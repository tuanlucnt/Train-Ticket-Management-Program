package CUOIKI.LAPTRINHMANG;

import java.io.DataInputStream;
import java.net.Socket;

public class SeverDoc extends Thread {
	private int khacrong = 0;
	private Socket socket;
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
	private String sms;
	private String ndYeuCau = "";
	private String ndTrangThai = "";
	private String ndCMND = "";
	private String ndmavetau = "";
	private String ndnoidi = "";
	private String ndnoiden = "";
	private String ndngaydi = "";
	public String getNdmavetau() {
		return ndmavetau;
	}

	public void setNdmavetau(String ndmavetau) {
		this.ndmavetau = ndmavetau;
	}

	public String getNdnoidi() {
		return ndnoidi;
	}

	public void setNdnoidi(String ndnoidi) {
		this.ndnoidi = ndnoidi;
	}

	public String getNdnoiden() {
		return ndnoiden;
	}

	public void setNdnoiden(String ndnoiden) {
		this.ndnoiden = ndnoiden;
	}

	public String getNdngaydi() {
		return ndngaydi;
	}

	public void setNdngaydi(String ndngaydi) {
		this.ndngaydi = ndngaydi;
	}

	public int getKhacrong() {
		return khacrong;
	}

	public void setKhacrong(int khacrong) {
		this.khacrong = khacrong;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getNdYeuCau() {
		return ndYeuCau;
	}

	public void setNdYeuCau(String ndYeuCau) {
		this.ndYeuCau = ndYeuCau;
	}

	public String getNdTrangThai() {
		return ndTrangThai;
	}

	public void setNdTrangThai(String ndTrangThai) {
		this.ndTrangThai = ndTrangThai;
	}

	public String getNdCMND() {
		return ndCMND;
	}

	public void setNdCMND(String ndCMND) {
		this.ndCMND = ndCMND;
	}

	public SeverDoc(Socket socket) {
		this.socket = socket;
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

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		DataInputStream in = null;
		try {
			while (true) {
				in = new DataInputStream(socket.getInputStream());
				sms = in.readUTF(); 
//				System.out.println(sms);
				if (sms != null) {
					if (sms.contains("TraCuu")) {
						khacrong = 1;
						TachChuoi();
					} else if (sms.contains("[Ðãòt Veì]")) {
						khacrong = 1;
						TachChuoiUpdate();
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				in.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	private void TachChuoiUpdate() {
		LamMoi1();
		int size = sms.length();
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (sms.charAt(i) == ',') {
				index = index + 1;
				continue;
			}
			if (index == 0) {
				ndYeuCau = ndYeuCau + sms.charAt(i);
			}
			if (index == 1) {
				hoten = hoten + sms.charAt(i);
			}
			if (index == 2) {
				cmnd = cmnd + sms.charAt(i);
			}
			if (index == 3) {
				sdt = sdt + sms.charAt(i);
			}
			if (index == 4) {
				diachi = diachi + sms.charAt(i);
			}
			if (index == 5) {
				mavetau = mavetau + sms.charAt(i);
			}
			if (index == 6) {
				noiden = noiden + sms.charAt(i);
			}
			if (index == 7) {
				noidi = noidi + sms.charAt(i);
			}
			if (index == 8) {
				ngaydi = ngaydi + sms.charAt(i);
			}
			if (index == 9) {
				toa = toa + sms.charAt(i);
			}
			if (index == 10) {
				loaicho = loaicho + sms.charAt(i);
			}
			if (index == 11) {
				loaive= loaive+ sms.charAt(i);
			}
			if (index == 12) {
				giatien = giatien + sms.charAt(i);
			}
			
		}
	}

	private void LamMoi1() {
		
	}

	private void TachChuoi() {
		ndmavetau = "";
		ndnoiden = "";
		ndnoidi = "";
		ndngaydi = "";
		ndTrangThai = "";
		ndYeuCau = "";
		int size = sms.length();
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (sms.charAt(i) == ',') {
				index = index + 1;
				continue;
			}
			if (index == -1) {
				ndYeuCau = ndYeuCau + sms.charAt(i);
			}
			if (index == 0) {
				ndmavetau = ndmavetau + sms.charAt(i);
			}
			if (index == 1) {
				ndnoidi = ndnoidi + sms.charAt(i);
			}
			if(index == 2) {
				ndnoiden = ndnoiden + sms.charAt(i);
			}
			if(index == 3) {
				ndngaydi = ndngaydi + sms.charAt(i);
			}
		}
//		System.out.println(ndmavetau + "\n" + ndnoidi + "\n" + ndnoiden + "\n" + ndngaydi + "\n----------------\n");
	}

}
