package Demo_Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import com.mysql.jdbc.PreparedStatement;
import com.toedter.calendar.JDateChooser;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

public class Client_Main extends JFrame {
	private Socket socket;
	private Vector vData = new Vector();
	private Vector vTitle = new Vector();
	private Vector vListSocket = new Vector();
	private Vector vTitlelistSocket = new Vector();
	private int danhdauketnoi = 0;
	private JPanel contentPane;
	private JTable table;

	private DefaultTableModel dtmtau;
	private DefaultTableModel modelsocket;
	private JTextField tfhoten;
	private JTextField tfcmnd;
	private JTextField tfsdt;
	private JTextField tfdiachi;
	private JTextField tfmavetau;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTable table_1;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField tfpost;
	private JLabel lbltrangthai;
	private JTextField tfmavetau_tracuu;
	private JComboBox cbbgadi_tracuu ,cbbgaden_tracuu;
	private JDateChooser tfdate_tracuu;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * Create the frame.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_Main frame = new Client_Main();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//  KĂª̀�t nĂ´̀�i Ä‘Ăª̀�n server
	private void KetNoiDenServer() {
		Thread th1 = new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("Dang Vao");
					socket = new Socket("localhost", Integer.parseInt(tfpost.getText().toString()));
					System.out.println("Da vao");
					XuLyCacCauLenh();
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		};
		th1.start();
	}
// xu ly cac tin tu server
	protected void XuLyCacCauLenh() {
		Thread thXuLySuKien = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						ArrayList listTraVe = new ArrayList();
						DocGuiChung.DocGui docgui = new DocGuiChung.DocGui(socket);
						String truyVanTraVe = docgui.LayDuLieu();
						String[] chr = truyVanTraVe.split(",");
//						txaThongBao.setText(txaThongBao.getText() + truyVanTraVe + "\n");
						if (chr[0].equals("[TraKetQuaTim]")) {
							Vector addDaTaRowTim = new Vector<>();
							for (int i = 1; i < chr.length; i++) {
								addDaTaRowTim.add(chr[i]);
							}
							xuLyAddDuLieuTimVaoBang(addDaTaRowTim);
						}
//						} else if (chr[0].equals("[TinNhanSerVer]")) {
//							String smsTinNhan = "";
//							for (int i = 1; i < chr.length; i++) {
//								if (chr[i] == chr[chr.length - 1]) {
//									smsTinNhan += chr[i];
//								} else {
//									smsTinNhan += "," + chr[i];
//								}
//							}
//						} else if (chr[0].equals("[HuyKetNoiClient]")) {
//							for (int i = 1; i < chr.length; i++) {
//							}
//							socket.close();
//						} else if (chr[0].equals("[TraKetQuaDangKI]")) {
//							for (int i = 1; i < chr.length; i++) {
//							}
//						} else if (chr[0].equals("[TraKetQuaTimThongTinNguoiDung]")) {
//
//							ThongTinNguoiDung ttnd = new ThongTinNguoiDung();
//							ttnd.inThongTinlen(chr[1], chr[2], chr[3], chr[4], chr[5], chr[6], chr[7], chr[8]);
//						} else if (chr[0].equals("[TraVeKetQuaSuaThongTinNguoiDung]")) {
//							String smsTraVe = "";
//							for (int i = 1; i < chr.length; i++) {
//								if (chr[i] == chr[chr.length - 1]) {
//									smsTraVe += chr[i];
//								} else {
//									smsTraVe += chr[i] + ",";
//								}
//							}
//
//							JOptionPane.showMessageDialog(null, smsTraVe);
//						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		};
		thXuLySuKien.start();
		
}

	protected void xuLyAddDuLieuTimVaoBang(Vector addDaTaRowTim) {
		dtmtau.fireTableDataChanged();
		
	}

	protected void XuLyTraCuu() {
		String smsCauNhanBietTim = "[TraCuu]";
		String smsCauLenhTim = "select * from vetau where `Mã Vé Tàu` LIKE N'%" + tfmavetau_tracuu.getText().toString()
				+ "%' AND `Ga Đi` like N'%" +cbbgadi_tracuu.getSelectedItem().toString()  
				+ "%' AND  `Ga Đến` like N'%" +cbbgaden_tracuu.getSelectedItem().toString()
				+ "%' AND `Ngày Đi` like N'%" + df.format(tfdate_tracuu.getDate().toString()) + "%'";
		Thread thTim = new Thread() {
			@Override
			public void run() {
				try {
					String smsTongLai = smsCauNhanBietTim + "," + smsCauLenhTim;
					xuLyGuiTinDi(smsTongLai);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Đã phát sinh lỗi, không thể tìm");
				}
			}
		};
		thTim.start();
	}

	protected void xuLyGuiTinDi(String tinGuiDi) {
		Thread thXuLyGuiTinDi = new Thread() {
			@Override
			public void run() {
				try {
					DocGuiChung.DocGui docGui = new DocGuiChung.DocGui(socket);
					docGui.GuiTin(tinGuiDi);
					System.out.println("đã gửi qua");
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Không thể thực hiện");
				}
			}
		};
		thXuLyGuiTinDi.start();

		
	}

	public Client_Main() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1251, 687);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 1239, 638);
		contentPane.add(tabbedPane);

		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Ve Tau", null, desktopPane_1, null);
		desktopPane_1.setLayout(null);

		JLabel label = new JLabel("New label");
		label.setBounds(0, 0, 45, -2);
		desktopPane_1.add(label);

		JLabel lblNewLabel = new JLabel(
				"H\u00CA\u0323 TH\u00D4\u0301NG BA\u0301N VE\u0301 TA\u0300U TOA\u0300N QU\u00D4\u0301C");
		lblNewLabel.setBounds(382, 0, 772, 72);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		desktopPane_1.add(lblNewLabel);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 115, 405, 197);
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"TRA C\u01AF\u0301U ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		desktopPane_1.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 15, 389, 174);
		panel_3.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Ma\u0303 Ve\u0301 Ta\u0300u:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 10, 110, 19);
		panel_2.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("N\u01A1i \u0110\u00EA\u0301n");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 71, 110, 19);
		panel_2.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Nga\u0300y \u0110i:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 100, 110, 19);
		panel_2.add(lblNewLabel_1_2);

		JButton btntracuu = new JButton("Tra C\u01B0\u0301u");
		btntracuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				XuLyTraCuu();
			}
		});
		btntracuu.setFont(new Font("Tahoma", Font.BOLD, 14));
		btntracuu.setBounds(102, 131, 110, 33);
		panel_2.add(btntracuu);

		JLabel lblNewLabel_1_1_1 = new JLabel("N\u01A1i \u0110i:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 42, 110, 19);
		panel_2.add(lblNewLabel_1_1_1);
		
		cbbgadi_tracuu = new JComboBox();
		cbbgadi_tracuu.setBounds(102, 41, 231, 18);
		panel_2.add(cbbgadi_tracuu);
		
		cbbgaden_tracuu = new JComboBox();
		cbbgaden_tracuu.setBounds(102, 70, 231, 18);
		panel_2.add(cbbgaden_tracuu);
		
		 tfdate_tracuu = new JDateChooser((Date) null, "YYYY-MM-dd");
		tfdate_tracuu.setBounds(102, 100, 231, 19);
		panel_2.add(tfdate_tracuu);
		
		tfmavetau_tracuu = new JTextField();
		tfmavetau_tracuu.setColumns(10);
		tfmavetau_tracuu.setBounds(102, 10, 231, 19);
		panel_2.add(tfmavetau_tracuu);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Ba\u0309ng Ve\u0301 Ta\u0300u", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(6, 322, 645, 285);
		desktopPane_1.add(panel_4);
		panel_4.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 625, 253);
		panel_4.add(scrollPane);

		table = new JTable();
		dtmtau = new DefaultTableModel();
		dtmtau.addColumn("MĂ£ TĂ u");
		dtmtau.addColumn("TĂªn Chuyáº¿n TĂ u");
		dtmtau.addColumn("Ga Ä�i");
		dtmtau.addColumn("Ga Ä�áº¿n");
		dtmtau.addColumn("NgĂ y Ä�i");
		dtmtau.addColumn("GiĂ¡ VĂ©");

//		table.setModel(dtmtau);
		scrollPane.setViewportView(table);

		JButton btnNewButton_1 = new JButton("\u0110\u0102\u0323T VE\u0301");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(736, 498, 156, 53);
		desktopPane_1.add(btnNewButton_1);

		JLabel lblNewLabel_1_3 = new JLabel("Ho\u0323 T\u00EAn:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(670, 84, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3);

		tfhoten = new JTextField();
		tfhoten.setColumns(10);
		tfhoten.setBounds(790, 82, 402, 19);
		desktopPane_1.add(tfhoten);

		JLabel lblNewLabel_1_3_1 = new JLabel("CMND:");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_1.setBounds(670, 115, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_1);

		tfcmnd = new JTextField();
		tfcmnd.setColumns(10);
		tfcmnd.setBounds(790, 113, 402, 19);
		desktopPane_1.add(tfcmnd);

		JLabel lblNewLabel_1_3_2 = new JLabel("S\u00F4\u0301 \u0110i\u00EA\u0323n Thoa\u0323i:");
		lblNewLabel_1_3_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_2.setBounds(670, 146, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_2);

		tfsdt = new JTextField();
		tfsdt.setColumns(10);
		tfsdt.setBounds(790, 144, 402, 19);
		desktopPane_1.add(tfsdt);

		JLabel lblNewLabel_1_3_3 = new JLabel("\u0110i\u0323a Chi\u0309:");
		lblNewLabel_1_3_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_3.setBounds(670, 182, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_3);

		tfdiachi = new JTextField();
		tfdiachi.setColumns(10);
		tfdiachi.setBounds(790, 180, 402, 19);
		desktopPane_1.add(tfdiachi);

		JLabel lblNewLabel_1_3_4 = new JLabel("Ma\u0303 Ve\u0301 Ta\u0300u:");
		lblNewLabel_1_3_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_4.setBounds(670, 214, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_4);

		tfmavetau = new JTextField();
		tfmavetau.setColumns(10);
		tfmavetau.setBounds(790, 212, 402, 19);
		desktopPane_1.add(tfmavetau);

		JLabel lblNewLabel_1_3_5 = new JLabel("N\u01A1i \u0110\u00EA\u0301n:");
		lblNewLabel_1_3_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_5.setBounds(670, 279, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_5);

		JLabel lblNewLabel_1_3_6 = new JLabel("N\u01A1i \u0110i:");
		lblNewLabel_1_3_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_6.setBounds(670, 243, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_6);

		JLabel lblNewLabel_1_3_6_1 = new JLabel("Nga\u0300y \u0110i:");
		lblNewLabel_1_3_6_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_6_1.setBounds(670, 314, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_6_1);

		JDateChooser datechooser = new JDateChooser(null, "YYYY-MM-dd");
		datechooser.setBounds(790, 312, 402, 19);
		desktopPane_1.add(datechooser);

		JLabel lblNewLabel_1_3_6_1_1 = new JLabel("Toa:");
		lblNewLabel_1_3_6_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_6_1_1.setBounds(670, 345, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_6_1_1);

		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(790, 343, 402, 19);
		desktopPane_1.add(textField_10);

		JLabel lblNewLabel_1_3_6_1_2 = new JLabel("Loa\u0323i Ch\u00F4\u0303:");
		lblNewLabel_1_3_6_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_6_1_2.setBounds(670, 376, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_6_1_2);

		JLabel lblNewLabel_1_3_6_1_3 = new JLabel("Loa\u0323i Ve\u0301:");
		lblNewLabel_1_3_6_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_6_1_3.setBounds(670, 407, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_6_1_3);

		JLabel lblNewLabel_1_3_6_1_4 = new JLabel("Gia\u0301 Ve\u0301:");
		lblNewLabel_1_3_6_1_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_3_6_1_4.setBounds(670, 441, 110, 19);
		desktopPane_1.add(lblNewLabel_1_3_6_1_4);

		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(790, 439, 402, 19);
		desktopPane_1.add(textField_13);

		JButton btnNewButton_1_1 = new JButton("XO\u0301A VE\u0301");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_1.setBounds(994, 498, 156, 53);
		desktopPane_1.add(btnNewButton_1_1);

		JLabel lblNewLabel_2 = new JLabel("CIENT - POST:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 0, 86, 28);
		desktopPane_1.add(lblNewLabel_2);

		tfpost = new JTextField();
		tfpost.setText("9876");
		tfpost.setBounds(106, 0, 187, 28);
		desktopPane_1.add(tfpost);
		tfpost.setColumns(10);

		JComboBox cbbgaden = new JComboBox();
		cbbgaden.setModel(new DefaultComboBoxModel(
				new String[] { "", "TP HÀ€ NĂ”̀£I", "TP VINH", "TP HUĂ�̀�", "TP Ä�À€ NÄ‚̀ƒNG ", "TP HCM" }));
		cbbgaden.setBounds(790, 276, 402, 22);
		desktopPane_1.add(cbbgaden);

		JComboBox cbbgadi = new JComboBox();
		cbbgadi.setModel(new DefaultComboBoxModel(
				new String[] { "", "TP HÀ€ NĂ”̀£I", "TP VINH", "TP HUĂ�̀�", "TP Ä�À€ NÄ‚̀ƒNG ", "TP HCM" }));
		cbbgadi.setBounds(790, 243, 402, 22);
		desktopPane_1.add(cbbgadi);

		JComboBox cbbloaicho = new JComboBox();
		cbbloaicho.setModel(new DefaultComboBoxModel(new String[] { "", "ThÆ°Æ¡̀€ng", "Ä�iĂª̀€u Hò€a" }));
		cbbloaicho.setBounds(790, 377, 402, 22);
		desktopPane_1.add(cbbloaicho);

		JComboBox cbbloaive = new JComboBox();
		cbbloaive.setModel(new DefaultComboBoxModel(new String[] { "", "ThÆ°Æ¡̀€ng", "KhÆ°̀� HĂ´̀€i" }));
		cbbloaive.setBounds(790, 408, 402, 22);
		desktopPane_1.add(cbbloaive);

		JPanel panel_3_2 = new JPanel();
		panel_3_2.setLayout(null);
		panel_3_2.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"TRA\u0323NG THA\u0301I", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3_2.setBounds(10, 32, 405, 85);
		desktopPane_1.add(panel_3_2);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 14, 386, 61);
		panel_3_2.add(panel);

		lbltrangthai = new JLabel("Tra\u0323ng Tha\u0301i:");
		lbltrangthai.setHorizontalAlignment(SwingConstants.LEFT);
		lbltrangthai.setBounds(119, 7, 257, 44);
		panel.add(lbltrangthai);
//		KĂª̀�t NĂ´̀�i
		JButton btnknoi = new JButton("K\u00EA\u0301t N\u00F4\u0301i");
		btnknoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (danhdauketnoi == 0) {
						KetNoiDenServer();
						if (socket != null) {
							lbltrangthai.setText("Trạng Thái: Bật");
							danhdauketnoi = 1;

						} else {
							lbltrangthai.setText("Trạng Thái:Đợi Server Kết Nối...");
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		btnknoi.setBounds(0, 1, 88, 32);
		panel.add(btnknoi);

		JButton btnngatknoi = new JButton("Ng\u0103\u0301t K\u00EA\u0301t N\u00F4\u0301i");
		btnngatknoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (socket == null) {
						lbltrangthai.setText("Trạng Thái: Đã Tắt");
						danhdauketnoi = 0;
					} else {
						socket.close();
						socket = null;
						lbltrangthai.setText("Trạng Thái; Đã Tắt");
						danhdauketnoi = 0;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		btnngatknoi.setBounds(0, 29, 88, 32);
		panel.add(btnngatknoi);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		tabbedPane.addTab("Thong Tin Ca Nhan", null, desktopPane, null);
		desktopPane.setLayout(null);

		JLabel lblKimTr = new JLabel("KI\u00CA\u0309M TRA TH\u00D4NG TIN CA\u0301 NH\u00C2N");
		lblKimTr.setBounds(10, 10, 1150, 72);
		lblKimTr.setHorizontalAlignment(SwingConstants.CENTER);
		lblKimTr.setFont(new Font("Tahoma", Font.BOLD, 24));
		desktopPane.add(lblKimTr);

		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBounds(0, 88, 360, 170);
		panel_3_1.setLayout(null);
		panel_3_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"TRA C\u01AF\u0301U ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		desktopPane.add(panel_3_1);

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBounds(6, 15, 349, 148);
		panel_3_1.add(panel_2_1);

		JLabel lblNewLabel_1_4 = new JLabel("Ho\u0323 T\u00EAn:");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_4.setBounds(10, 10, 110, 19);
		panel_2_1.add(lblNewLabel_1_4);

		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(107, 12, 231, 19);
		panel_2_1.add(textField_14);

		JButton btnNewButton_2 = new JButton("Tra C\u01B0\u0301u");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setBounds(107, 106, 110, 33);
		panel_2_1.add(btnNewButton_2);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("CMND:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1_1.setBounds(10, 42, 110, 19);
		panel_2_1.add(lblNewLabel_1_1_1_1);

		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(107, 44, 231, 19);
		panel_2_1.add(textField_15);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("S\u00F4\u0301 \u0110i\u00EA\u0323n Thoa\u0323i:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1_1_1.setBounds(10, 75, 110, 19);
		panel_2_1.add(lblNewLabel_1_1_1_1_1);

		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBounds(107, 77, 231, 19);
		panel_2_1.add(textField_16);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 296, 1224, 315);
		desktopPane.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		JLabel lblNewLabel_2_5 = new JLabel("Ho\u0323 T\u00EAn:");
		lblNewLabel_2_5.setBounds(370, 92, 91, 23);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_5);

		textField_17 = new JTextField();
		textField_17.setBounds(465, 94, 302, 21);
		textField_17.setColumns(10);
		desktopPane.add(textField_17);

		JLabel lblNewLabel_2_1_1 = new JLabel("CMND:");
		lblNewLabel_2_1_1.setBounds(370, 125, 91, 23);
		lblNewLabel_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_1_1);

		textField_18 = new JTextField();
		textField_18.setBounds(465, 127, 302, 21);
		textField_18.setColumns(10);
		desktopPane.add(textField_18);

		JLabel lblNewLabel_2_5_1 = new JLabel("Toa:");
		lblNewLabel_2_5_1.setBounds(836, 92, 91, 23);
		lblNewLabel_2_5_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_5_1);

		textField_19 = new JTextField();
		textField_19.setBounds(950, 92, 258, 23);
		textField_19.setColumns(10);
		desktopPane.add(textField_19);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("S\u00F4\u0301 Gh\u00EA\u0301:");
		lblNewLabel_2_1_1_1.setBounds(836, 125, 91, 23);
		lblNewLabel_2_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_1_1_1);

		textField_20 = new JTextField();
		textField_20.setBounds(950, 125, 258, 23);
		textField_20.setColumns(10);
		desktopPane.add(textField_20);

		JLabel lblNewLabel_2_5_1_1 = new JLabel("Toa:");
		lblNewLabel_2_5_1_1.setBounds(836, 158, 91, 23);
		lblNewLabel_2_5_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_5_1_1);

		textField_21 = new JTextField();
		textField_21.setBounds(465, 160, 302, 21);
		textField_21.setColumns(10);
		desktopPane.add(textField_21);

		JLabel lblNewLabel_2_5_2 = new JLabel("SĂ´̀� Ä�iĂª̀£n Thoà£i:");
		lblNewLabel_2_5_2.setBounds(370, 158, 91, 23);
		lblNewLabel_2_5_2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_5_2);

		textField_22 = new JTextField();
		textField_22.setBounds(950, 158, 258, 23);
		textField_22.setColumns(10);
		desktopPane.add(textField_22);

		JLabel lblNewLabel_2_5_1_2 = new JLabel("Toa:");
		lblNewLabel_2_5_1_2.setBounds(836, 191, 91, 23);
		lblNewLabel_2_5_1_2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_5_1_2);

		textField_23 = new JTextField();
		textField_23.setBounds(465, 193, 302, 21);
		textField_23.setColumns(10);
		desktopPane.add(textField_23);

		JLabel lblNewLabel_2_5_3 = new JLabel("Ä�ì£a Chì‰:");
		lblNewLabel_2_5_3.setBounds(370, 191, 91, 23);
		lblNewLabel_2_5_3.setFont(new Font("Times New Roman", Font.BOLD, 12));
		desktopPane.add(lblNewLabel_2_5_3);

		textField_24 = new JTextField();
		textField_24.setBounds(950, 191, 258, 23);
		textField_24.setColumns(10);
		desktopPane.add(textField_24);

		JButton button = new JButton("New button");
		button.setBounds(376, 224, 134, 38);
		desktopPane.add(button);

		JButton button_1 = new JButton("New button");
		button_1.setBounds(547, 224, 134, 38);
		desktopPane.add(button_1);

		JButton button_2 = new JButton("New button");
		button_2.setBounds(716, 224, 134, 38);
		desktopPane.add(button_2);

		JButton button_3 = new JButton("New button");
		button_3.setBounds(886, 224, 134, 38);
		desktopPane.add(button_3);

		JButton button_4 = new JButton("New button");
		button_4.setBounds(1049, 224, 134, 38);
		desktopPane.add(button_4);
	}
}
