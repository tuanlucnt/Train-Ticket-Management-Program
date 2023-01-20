package CUOIKI.LAPTRINHMANG;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Label;
import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.toedter.calendar.JDateChooser;

import Doc_Gui.DocGui;

import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

public class Server extends JFrame {
	private Connection conn;
	private PreparedStatement prst;
	private JPanel contentPane;
	private JTable table_ThongTin;
	private DefaultTableModel modelvetau;
	private DefaultTableModel modelsocket;
	private DefaultTableModel modelthongtin;
	private int danhdauketnoi = 0;
	private JDateChooser jdate = new JDateChooser();
	private Vector vTitle = new Vector();
	private Vector vData = new Vector();
	private Vector vTitlelistSocket = new Vector();
	private Vector vListSocket = new Vector();
	private Vector vDatVe = new Vector();
	private Vector vTraCuu = new Vector();

	private ServerSocket sever;

	private JTextField mave_thongtin;
	private JTextField tenchuyentau_thongtin;
	private JTextField textField_6;
	private JTextField toa_thongtin;
	private JTextField giave_thongtin;
	private JTextField tfpost;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTable tablevetau;
	private JTable tablesocket;

	private String tracuumavetau = "";
	private String tracuunoiden = "";
	private String tracuunoidi = "";
	private String tracuungaydi = "";
	private String MaVeTauClientCanTraCuu = "";
	private String hoten = "";
	private String cmnd = "";
	private String sdt = "";
	private String diachi = "";
	private String mavetau = "";
	private String noidi = "";
	private String noiden = "";
	private String ngaydi = "";
	private String toa = "";
	private String loaive = "";
	private String loaicho = "";
	private String giave = "";
	private String thongtingui = "";
	private String sqlSelectAll = "Select * from demo";
//	private String sqltracuu = "select * from vetau where `Mã Vé Tàu` LIKE N'%" + tracuumavetau
//			+ "%' AND `Ga Đi` like N'%" + tracuunoidi + "%' AND  `Ga Đến` like N'%" + tracuunoiden
//			+ "%' AND `Ngày Đi` like N'%" + tracuungaydi + "%'";

	/**
	 * Create the frame.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
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

	public Server() throws IOException {
		int i = 1;
		KetNoiDataBase();
		NapDuLieutuDataBaselenBang();
		NhanThongTinTuUser();
		GiaoDien();
//		showWindow();
		Thread th1 = new Thread() {
			@Override
			public void run() {
				while (true) {
					if (i == 10) {
						break;
					} else {
						modelsocket.fireTableDataChanged();
					}
					try {
						Thread.sleep(3000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		};
		th1.start();
	}

	private void ClientKetNoiServer(int post, int i) {
		try {
			if (i == 1) {
				sever.close();
			} else {
				sever = new ServerSocket(post);
				System.out.println("Đã Kết Nối");
				while (true) {
					if (i == 1) {
						break;
					} else {
						Socket socket = sever.accept();
						Vector row = new Vector();

						row.add(0, socket);
						row.add(1, "Đang Đợi...");
						row.add(2, "Đang Đợi...");
						row.add(3, "Đang Đợi...");
						Thread th1 = new Thread() {
							@Override
							public void run() {
								while (true) {
									try {
										DocGui docgui = new DocGui(socket);
										String noidungnhan = docgui.LayDuLieu();
										String[] chr = noidungnhan.split(",");
										if (chr[0].equals("TraCuu")) {
											row.set(1, "Tra Cứu");
											row.set(2, "Đang Yêu Cầu Tra Cứu");
											row.set(3, "Đang Chờ ....");
//											vListSocket.add(row);
											for (int i = 1; i < chr.length; i++) {
												XuLyTraCuu(chr[i], socket);
//												System.out.println(chr[i]);
											}
											capnhatbangrocket(socket);
										} else if (chr[0].equals("DatVe")) {
											row.set(1, "Đặt Vé");
											row.set(2, "Đang Yêu Cầu Đặt Vé");
											row.set(3, "Lúa Về");
//											vListSocket.add(row);
											String cauLenhDatVe = "";
											for (int i = 1; i < chr.length; i++) {
												if (chr[i] == chr[chr.length - 1]) {
													cauLenhDatVe += chr[i];
												} else {
													cauLenhDatVe += chr[i] + ",";
												}
											}
											XuLyDatVe(cauLenhDatVe, socket);
											capnhatbangrocket(socket);
										} else if (chr[0].equals("XoaVe")) {
											row.set(1, "Xóa Vé");
											row.set(2, "Đang Yêu Cầu Đặt Vé");
											row.set(3, "Đang Đợi Xử Lý...");
//											vListSocket.add(row);
											String cauLenhXoaVe = "";
											for (int i = 1; i < chr.length; i++) {
												if (chr[i] == chr[chr.length - 1]) {
													cauLenhXoaVe += chr[i];
												} else {
													cauLenhXoaVe += chr[i] + ",";
												}
											}
											XuLyXoaVe(cauLenhXoaVe, socket);
											capnhatbangrocket(socket);
										} else if (chr[0].equals("ChinhSuaThongTin")) {
											row.add(1, "Chỉnh Sửa Thông Tin");
											row.add(2, "Đang Yêu Cầu Cập Nhập Thông Tin");
											row.add(3, "Đang Đợi Xử Lý....");
											String caulenhChinhSua = "";
											for (int i = 1; i < chr.length; i++) {
												if (chr[i] == chr[chr.length - 1]) {
													caulenhChinhSua = caulenhChinhSua + chr[i];
												} else {
													caulenhChinhSua = caulenhChinhSua + chr[i] + ",";
												}
												XuLyCapNhat(caulenhChinhSua, socket);
												capnhatbangrocket(socket);
											}
										}

									} catch (Exception e) {
										// TODO: handle exception
									}
								}
							}
						};
						th1.start();
						vListSocket.add(row);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void capnhatbangrocket(Socket socket) {
		int size = vListSocket.size();
		for (int i = 0; i < size; i++) {
			Vector row = (Vector) vListSocket.get(i);
			Socket socketList = (Socket) row.elementAt(0);
			if (socket == socketList) {
				row.set(2, "[Đợi Phản Hồi Từ Server...]");
			}
			vListSocket.set(i, row);
			modelsocket.fireTableDataChanged();
		}
	}

	protected void XuLyCapNhat(String caulenhChinhSua, Socket socket) {
		Thread thcapnhat = new Thread() {
			@Override
			public void run() {
				try {
					PreparedStatement prst = (PreparedStatement) conn.prepareStatement(caulenhChinhSua);
					System.out.println("Chinh Sửa : " + caulenhChinhSua);
					int x = prst.executeUpdate();
					if (x > 0) {
						String smsguidi = "[ChinhSuaThongTin],Chỉnh Sửa Thành Công!!";
						DocGui docgui = new DocGui(socket);
						docgui.GuiTin(smsguidi);
					}
				} catch (Exception e) {

				}
				JOptionPane.showMessageDialog(null, "Chỉnh Sửa Hoàn Tất");
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				NapDuLieutuDataBaselenBang();
			}
		};
		thcapnhat.start();
	}

	protected void XuLyXoaVe(String cauLenhXoaVe, Socket socket) {
		Thread thXoa = new Thread() {
			@Override
			public void run() {
				try {

					PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(cauLenhXoaVe);
					int x = pstm.executeUpdate();
					if (x > 0) {
						String sms = "[DaXoaThongTin],Xóa Vé Tàu Thành Công";
						DocGui docgui = new DocGui(socket);
						docgui.GuiTin(sms);
					}

				} catch (Exception e) {
					e.printStackTrace();

				}
				JOptionPane.showMessageDialog(null, "Đã Xóa");
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				NapDuLieutuDataBaselenBang();
			}

		};
		thXoa.start();

	}

	protected void XuLyDatVe(String caulenhdatve, Socket socket) {
		Thread thDatVe = new Thread() {
			@Override
			public void run() {
				try {
					String sms = caulenhdatve;
					System.out.println("Câu Lệnh Đạt Vé" + caulenhdatve);
					PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sms);
					int x = pstm.executeUpdate();
					if (x > 0) {
						String smsTraVeThongBao = "[ThongBaoDatVe],Bạn Đã Đặt Vé Thành Công";
						DocGui docgui = new DocGui(socket);
						docgui.GuiTin(smsTraVeThongBao);

					} else {
						JOptionPane.showInternalMessageDialog(null, "Lỗi Đặt Vé");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Đã Ghi Dữ Liệu...");
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				NapDuLieutuDataBaselenBang();
			}
		};
		thDatVe.start();

	}

	protected void XuLyTraCuu(String caulenhtracuu, Socket socket) {
		Thread thtracuu = new Thread() {
			@Override
			public void run() {
				try {

					Vector row = new Vector();
					PreparedStatement prst = (PreparedStatement) conn.prepareStatement(caulenhtracuu);
//					Statement stm = (Statement) conn.createStatement();
					ResultSet rss = prst.executeQuery();
					ResultSetMetaData rssmd = rss.getMetaData();
					while (rss.next()) {
						String noidungtracuu = "[ThongTinTraCuu]" + "," + rss.getString(1) + "," + rss.getString(2)
								+ "," + rss.getString(3) + "," + rss.getString(4) + "," + rss.getString(5) + ","
								+ rss.getString(6) + "," + rss.getString(7) + "," + rss.getString(8) + ","
								+ rss.getString(9) + "," + rss.getString(10);
						row.add(noidungtracuu);
					}
					System.out.println(row.toString());

					JOptionPane.showMessageDialog(null, "Đã Gửi Thông tin Tra Cứu...");
					// duyet tung hang roi gui
					Thread.sleep(1000);
					for (int i = 0; i < row.size(); i++) {
						DocGui docgui = new DocGui(socket);
						docgui.GuiTin(row.get(i).toString());
						System.out.println("Da Gui");
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Lỗi");
				}
			}
		};
		thtracuu.start();

	}

	private void XoaALL() {
		hoten = "";
		cmnd = "";
		sdt = "";
		diachi = "";
		mavetau = "";
		noidi = "";
		noiden = "";
		ngaydi = "";
		toa = "";
		loaive = "";
		loaicho = "";
		giave = "";

	}

// Tên tiêu đề của bảng socket
	private void NhanThongTinTuUser() {
		try {
			vTitlelistSocket.add("Địa Chỉ Client");
			vTitlelistSocket.add("Yêu Cầu");
			vTitlelistSocket.add("Trạng Thái");
			vTitlelistSocket.add("Ghi Chú");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

// nap du lieu lên bảng 
	private void NapDuLieutuDataBaselenBang() {
		try {
			Statement stm = (Statement) conn.createStatement();
			ResultSet rss = stm.executeQuery(sqlSelectAll);
			modelvetau.setRowCount(0);
			while (rss.next()) {
				Vector row = new Vector();
				row.add(rss.getString(1));
				row.add(rss.getString(2));
				row.add(rss.getString(3));
				row.add(rss.getString(4));
				row.add(rss.getString(5));
				row.add(rss.getString(6));
				row.add(rss.getString(7));
				row.add(rss.getString(8));
				row.add(rss.getString(9));
				row.add(rss.getString(10));
				row.add(rss.getString(11));
				row.add(rss.getString(12));

				modelvetau.addRow(row);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void KetNoiDataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlyvetau", "root", "");
		} catch (Exception e) {

		}
	}

	private void GiaoDien() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1289, 677);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1285, 640);
		contentPane.add(tabbedPane);

		modelsocket = new DefaultTableModel(vListSocket, vTitlelistSocket);

//		modelvetau = new DefaultTableModel(vData, vTitle);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		tabbedPane.addTab("Quản Lý Vé Tàu", null, desktopPane, null);

		JLabel lblNewLabel = new JLabel("SERVER - POST:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(0, 0, 202, 32);
		desktopPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("ADMIN - QUA\u0309N LY\u0301 VE\u0301 TA\u0300U");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1.setBounds(388, 0, 352, 55);
		desktopPane.add(lblNewLabel_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thi\u00EA\u0301t Bi\u0323 K\u00EA\u0301t N\u00F4\u0301i", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_1.setBounds(425, 81, 683, 216);
		desktopPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 20, 669, 186);
		panel_1.add(scrollPane);
		tablesocket = new JTable(modelsocket);
		tablesocket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectrowSocket = tablesocket.getSelectedRow();
				Vector row = (Vector) vListSocket.get(selectrowSocket);
				System.out.println(row.elementAt(0));
			}
		});

		scrollPane.setViewportView(tablesocket);
// gửi thông tin
		JButton btnketnoi = new JButton("G\u01AF\u0309I TH\u00D4NG TIN");
		btnketnoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread th1 = new Thread() {
					@Override
					public void run() {
						int selectvSocket = tablesocket.getSelectedRow();
						Vector row = (Vector) vListSocket.get(selectvSocket);
						Socket socket = (Socket) row.elementAt(0); // lấy địa chỉ socket

					}
				};
				th1.start();
			}
		});
		btnketnoi.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnketnoi.setBounds(1133, 123, 137, 32);
		desktopPane.add(btnketnoi);

		JButton btnngatkn = new JButton("XO\u0301A CLIENT");
		btnngatkn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int SelectlistRocket = tablesocket.getSelectedRow();
				Vector row = (Vector) vListSocket.get(SelectlistRocket);
				Socket socket = (Socket) row.elementAt(0);
				String sms = "[EXIT]";
				try {
					DocGui docgui = new DocGui(socket);
					docgui.GuiTin(sms);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				vListSocket.remove(SelectlistRocket);
				JOptionPane.showInternalMessageDialog(null,
						"Đã ngắt kết nối với client " + vListSocket.get(0).toString(), "Thông Báo",
						JOptionPane.DEFAULT_OPTION);
			}
		});
		btnngatkn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnngatkn.setBounds(1133, 165, 137, 32);
		desktopPane.add(btnngatkn);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setForeground(Color.BLACK);
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"BA\u0309NG VE\u0301 TA\u0300U", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 295, 1260, 318);
		desktopPane.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 15, 1240, 293);
		panel_2.add(scrollPane_1);
		modelvetau = new DefaultTableModel();
		modelvetau.addColumn("Họ Tên");
		modelvetau.addColumn("CMND");
		modelvetau.addColumn("Số Điện Thoại");
		modelvetau.addColumn("Địa Chỉ");
		modelvetau.addColumn("Mã Vé Tàu");
		modelvetau.addColumn("Nơi Đi");
		modelvetau.addColumn("Nơi Đến");
		modelvetau.addColumn("Ngày Đi");
		modelvetau.addColumn("Toa");
		modelvetau.addColumn("Loại Chỗ");
		modelvetau.addColumn("Loại Vé");
		modelvetau.addColumn("Giá Vé");
		tablevetau = new JTable(modelvetau);
		// khi click vao mot hang thi hien thong tin hang do ra
		tablevetau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector row = (Vector) vData.get(tablevetau.getSelectedRow());

			}
		});

		scrollPane_1.setViewportView(tablevetau);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"TRA\u0323NG THA\u0301I", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 42, 400, 85);
		desktopPane.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 14, 383, 61);
		panel_3.add(panel);
		panel.setLayout(null);

		final JLabel lbltrangthai = new JLabel("Tra\u0323ng Tha\u0301i:");
		lbltrangthai.setHorizontalAlignment(SwingConstants.LEFT);
		lbltrangthai.setBounds(119, 7, 249, 44);
		panel.add(lbltrangthai);

		JButton btnknoi = new JButton("K\u00EA\u0301t N\u00F4\u0301i");
		btnknoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread th1 = new Thread() {
					public void run() {
						try {
							NapDuLieutuDataBaselenBang();
							lbltrangthai.setText("Trạng thái: Bật");
							ClientKetNoiServer(Integer.parseInt(tfpost.getText().toString()), 0);
						} catch (Exception e) {
							// TODO: handle exception
						}

					};
				};
				th1.start();
			}
		});
		btnknoi.setBounds(0, 1, 88, 32);
		panel.add(btnknoi);

		JButton btnNewButton_1_2 = new JButton("Ng\u0103\u0301t K\u00EA\u0301t N\u00F4\u0301i");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread th1 = new Thread() {
					@Override
					public void run() {
						try {
							JOptionPane.showMessageDialog(null, "Đã Ngắt Kết Nối");
							lbltrangthai.setText("Trạng Thái: Tắt");
							sever.close();
							ClientKetNoiServer(Integer.parseInt(tfpost.getText().toString()), 1);
							danhdauketnoi = 0;
						} catch (Exception e) {
							// TODO: handle exception
						}

					}
				};
				th1.start();
			}
		});
		btnNewButton_1_2.setBounds(0, 29, 88, 32);
		panel.add(btnNewButton_1_2);

		tfpost = new JTextField();
		tfpost.setText("9876");
		tfpost.setBounds(194, 10, 96, 19);
		desktopPane.add(tfpost);
		tfpost.setColumns(10);
		
		JButton btnthoat = new JButton("THOÁT");
		btnthoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnthoat.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnthoat.setBounds(1133, 211, 137, 32);
		desktopPane.add(btnthoat);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("D:\\CODE\\JAVA Very EZ\\Java_Exam\\image\\320px-Icon_train.svg.png"));
		lblNewLabel_2.setBounds(34, 165, 352, 109);
		desktopPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("D:\\CODE\\JAVA Very EZ\\Java_Exam\\image\\Ticket-icon.png"));
		lblNewLabel_5.setBounds(299, 133, 48, 32);
		desktopPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("");
		lblNewLabel_5_1.setBounds(338, 165, 48, 32);
		desktopPane.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("");
		lblNewLabel_5_2.setIcon(new ImageIcon("D:\\CODE\\JAVA Very EZ\\Java_Exam\\image\\Ticket-icon.png"));
		lblNewLabel_5_2.setBounds(216, 133, 48, 32);
		desktopPane.add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_5_3 = new JLabel("");
		lblNewLabel_5_3.setIcon(new ImageIcon("D:\\CODE\\JAVA Very EZ\\Java_Exam\\image\\Ticket-icon.png"));
		lblNewLabel_5_3.setBounds(134, 133, 48, 32);
		desktopPane.add(lblNewLabel_5_3);
		
		JLabel lblNewLabel_5_4 = new JLabel("");
		lblNewLabel_5_4.setIcon(new ImageIcon("D:\\CODE\\JAVA Very EZ\\Java_Exam\\image\\Ticket-icon.png"));
		lblNewLabel_5_4.setBounds(65, 133, 48, 32);
		desktopPane.add(lblNewLabel_5_4);
		

		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Thông tin Tàu", null, desktopPane_1, null);
		desktopPane_1.setLayout(null);

		JLabel lblServer = new JLabel("SERVER");
		lblServer.setBounds(10, 10, 91, 32);
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer.setFont(new Font("Times New Roman", Font.BOLD, 22));
		desktopPane_1.add(lblServer);

		JLabel lblNewLabel_3 = new JLabel("TH\u00D4NG TIN TA\u0300U");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel_3.setBounds(42, 10, 1006, 54);
		desktopPane_1.add(lblNewLabel_3);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 277, 1260, 326);
		desktopPane_1.add(scrollPane_2);
		modelthongtin = new DefaultTableModel();
		modelthongtin.addColumn("Mã Vé Tàu");
		modelthongtin.addColumn("Tên Chuyến Tàu");
		modelthongtin.addColumn("Nơi Đi");
		modelthongtin.addColumn("Nơi Đến");
		modelthongtin.addColumn("Ngày Đi");
		modelthongtin.addColumn("Toa");
		modelthongtin.addColumn("Loại Chỗ");
		modelthongtin.addColumn("Loại Vé");
		modelthongtin.addColumn("Giá Vé");
//		dtmtau.addColumn("CMND");
		table_ThongTin = new JTable(modelthongtin);
		scrollPane_2.setViewportView(table_ThongTin);

		JLabel lblNewLabel_2_5 = new JLabel("Ma\u0303 Ve\u0301 Ta\u0300u:");
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_5.setBounds(41, 62, 91, 23);
		desktopPane_1.add(lblNewLabel_2_5);

		mave_thongtin = new JTextField();
		mave_thongtin.setColumns(10);
		mave_thongtin.setBounds(136, 64, 363, 21);
		desktopPane_1.add(mave_thongtin);

		tenchuyentau_thongtin = new JTextField();
		tenchuyentau_thongtin.setColumns(10);
		tenchuyentau_thongtin.setBounds(136, 97, 363, 21);
		desktopPane_1.add(tenchuyentau_thongtin);

		JLabel lblNewLabel_2_1_1 = new JLabel("T\u00EAn Chuy\u00EA\u0301n Ta\u0300u:");
		lblNewLabel_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_1_1.setBounds(41, 95, 91, 23);
		desktopPane_1.add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_2_1 = new JLabel("Ga \u0110i:");
		lblNewLabel_2_2_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_2_1.setBounds(41, 128, 91, 23);
		desktopPane_1.add(lblNewLabel_2_2_1);

		JComboBox cbbgaden_thongtin = new JComboBox();
		cbbgaden_thongtin.setModel(
				new DefaultComboBoxModel(new String[] { "", "Hà Nội", "Vinh", "Huế", "Đà Nẵng", "Hồ Chí Minh" }));
		cbbgaden_thongtin.setBounds(136, 161, 363, 23);
		desktopPane_1.add(cbbgaden_thongtin);

		JLabel lblNewLabel_2_4_1 = new JLabel("Ga \u0110\u00EA\u0301n:");
		lblNewLabel_2_4_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_4_1.setBounds(41, 161, 91, 23);
		desktopPane_1.add(lblNewLabel_2_4_1);

		JLabel lblNewLabel_2_3_1 = new JLabel("Nga\u0300y Kh\u01A1\u0309i Ha\u0300nh");
		lblNewLabel_2_3_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_3_1.setBounds(41, 194, 91, 23);
		desktopPane_1.add(lblNewLabel_2_3_1);

		JDateChooser tfdate_thongtin = new JDateChooser(null, "YYYY-MM-dd");
		tfdate_thongtin.setBounds(136, 196, 363, 21);
		desktopPane_1.add(tfdate_thongtin);

		JComboBox cbbgadi_thongtin = new JComboBox();
		cbbgadi_thongtin.setModel(
				new DefaultComboBoxModel(new String[] { "", "Hà Nội", "Vinh", "Huế", "Đà Nẵng", "Hồ Chí Minh" }));
		cbbgadi_thongtin.setBounds(136, 129, 363, 22);
		desktopPane_1.add(cbbgadi_thongtin);

		JLabel lblNewLabel_2_5_1 = new JLabel("Toa:");
		lblNewLabel_2_5_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_5_1.setBounds(575, 62, 104, 23);
		desktopPane_1.add(lblNewLabel_2_5_1);

		JLabel lblNewLabel_2_2_1_1 = new JLabel("Loa\u0323i Ch\u00F4\u0303:");
		lblNewLabel_2_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_2_1_1.setBounds(575, 96, 104, 23);
		desktopPane_1.add(lblNewLabel_2_2_1_1);

		JLabel lblNewLabel_2_4_1_1 = new JLabel("Loa\u0323i Ve\u0301:");
		lblNewLabel_2_4_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_4_1_1.setBounds(575, 130, 104, 23);
		desktopPane_1.add(lblNewLabel_2_4_1_1);

		JLabel lblNewLabel_2_3_1_1 = new JLabel("Gia\u0301 Ve\u0301:");
		lblNewLabel_2_3_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_3_1_1.setBounds(575, 161, 104, 23);
		desktopPane_1.add(lblNewLabel_2_3_1_1);

		toa_thongtin = new JTextField();
		toa_thongtin.setColumns(10);
		toa_thongtin.setBounds(689, 62, 323, 21);
		desktopPane_1.add(toa_thongtin);

		giave_thongtin = new JTextField();
		giave_thongtin.setColumns(10);
		giave_thongtin.setBounds(689, 162, 323, 21);
		desktopPane_1.add(giave_thongtin);

		JComboBox cbbloaicho_thongtin = new JComboBox();
		cbbloaicho_thongtin.setModel(new DefaultComboBoxModel(
				new String[] { "", "Th\u01B0\u01A1\u0300ng", "\u0110i\u00EA\u0300u Ho\u0300a" }));
		cbbloaicho_thongtin.setBounds(689, 96, 323, 22);
		desktopPane_1.add(cbbloaicho_thongtin);

		JComboBox cbbloaive_thongtin = new JComboBox();
		cbbloaive_thongtin.setModel(new DefaultComboBoxModel(
				new String[] { "", "Th\u01B0\u01A1\u0300ng", "Kh\u01B0\u0301 H\u00F4\u0300i" }));
		cbbloaive_thongtin.setBounds(689, 128, 323, 23);
		desktopPane_1.add(cbbloaive_thongtin);

		JLabel lblNewLabel_4 = new JLabel("BA\u0309NG TH\u00D4NG TIN VE\u0301 TA\u0300U:");
		lblNewLabel_4.setBounds(11, 253, 135, 23);
		desktopPane_1.add(lblNewLabel_4);

		JButton btnNewButton = new JButton("TH\u00CAM ");
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(1079, 63, 171, 22);
		desktopPane_1.add(btnNewButton);

		JButton btnSa = new JButton("S\u01AF\u0309A");
		btnSa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSa.setBounds(1079, 97, 171, 22);
		desktopPane_1.add(btnSa);

		JButton btnXoa = new JButton("XO\u0301A");
		btnXoa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnXoa.setBounds(1079, 129, 171, 22);
		desktopPane_1.add(btnXoa);

		JButton btnTimKim = new JButton("TI\u0300M KI\u00CA\u0301M");
		btnTimKim.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKim.setBounds(1079, 162, 171, 22);
		desktopPane_1.add(btnTimKim);

		JButton btnThoat = new JButton("THOA\u0301T");
		btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnThoat.setBounds(1079, 195, 171, 22);
		desktopPane_1.add(btnThoat);
	}
}
