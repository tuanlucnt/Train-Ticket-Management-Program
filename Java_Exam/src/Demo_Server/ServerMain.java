package Demo_Server;

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
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Label;
import java.awt.Color;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.toedter.calendar.JDateChooser;

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

public class ServerMain extends JFrame {
	private Connection conn;
	private PreparedStatement prst;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelvetau;
	private DefaultTableModel modelsocket;

	private String sqlSelectAll = "Select * from vetau";

	private int danhdauketnoi = 0;
	private JDateChooser jdate = new JDateChooser();
	private Vector vTitle = new Vector();
	private Vector vData = new Vector();
	private Vector vTitlelistSocket = new Vector();
	private Vector vListSocket = new Vector();

	private ServerSocket sever;

	private JTextField tfmave;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_7;
	private JTextField tfpost;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTable tablevetau;
	private JTable tablesocket;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;

	/**
	 * Create the frame.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerMain frame = new ServerMain();
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

	public ServerMain() throws IOException {
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

	private void xulyngat() {

	}

	private void ClientKetNoiServer(int post, int i) {
		try {
			if (i == 1) {
				sever.close();
			} else {
				sever = new ServerSocket(post);
				System.out.println("�a� K��t N��i");
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
						xuLyCacCauLenh(socket,row);
						
						Thread th1 = new Thread() {
							@Override
							public void run() {
								while (true) {
									System.out.println("");
									try {
										Thread.sleep(1000);
									} catch (Exception e) {
										// TODO: handle exception
									}
								}

							}
						};
						capnhatbangrocket(socket);
						th1.start();
						
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void xuLyCacCauLenh(Socket socket,Vector row) {
		Thread thxulycaulenh = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						DocGuiChung.DocGui docdulieu = new DocGuiChung.DocGui(socket);
						String caulenh = docdulieu.LayDuLieu();
						String[] chuoi = caulenh.split(",");
						System.out.println("Chuoi" + chuoi);
						if(chuoi[0].equals("[TraCuu]")) {
							row.set(1, "Tra Cứu");
							row.set(2, "Đang Yêu Cầu Tra Cứu");
							row.set(3, "...");
							vListSocket.add(row);
							for(int i = 1; i <chuoi.length; i++) {
								XuLyTraCuu(chuoi[i], socket);
							}
						}
						else if(chuoi[0].equals("[DatVe]")) {
							
							row.set(1, "Đặt Vé...");
							row.set(2, "Đang Yêu Cầu Đặt Vé");
							row.set(3, "Lúa Về");
							vListSocket.add(row);
							for(int i =1 ; i< chuoi.length;i++) {
								XuLyDatVe(chuoi[i], socket);
							}
						}
						else if(chuoi[0].equals("[XoaVe]")) {
							
							row.set(1, "Xóa Vé");
							row.set(2, "Đang Yêu Cầu Đặt Vé");
							row.set(3, "Xóa Vé......");
							vListSocket.add(row);
							for(int i =1 ; i< chuoi.length;i++) {
								XuLyXoaVe(chuoi[i], socket);
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					
					}
				}
			
			}
			
		};
		thxulycaulenh.start();
	}
	protected void XuLyXoaVe(String string, Socket socket) {
		// TODO Auto-generated method stub
		
	}
	
	protected void XuLyDatVe(String string, Socket socket) {
		// TODO Auto-generated method stub
		
	}

	protected void XuLyTraCuu(String cauLenhTraCuu, Socket socket) {
		Thread thTIm = new Thread() {
			@Override
			public void run() {
				try {
					Vector<Object> vec1 = new Vector<Object>();
					Statement stm = (Statement) conn.createStatement();
					ResultSet rss = stm.executeQuery(cauLenhTraCuu);
//						dtmSach.setRowCount(0);
					while (rss.next()) {
						String ChuaTrave = "[TraKetQuaTim]" + "," + rss.getString(1) + "," + rss.getString(2) + ","
								+ rss.getString(3) + "," + rss.getString(4) + "," + rss.getString(5) + ","
								+ rss.getString(6) + rss.getString(7) + rss.getString(8) + rss.getString(9) 
								+ rss.getString(10) ;
						vec1.add(ChuaTrave);

					}
					for (int i = 0; i < vec1.size(); i++) {
						DocGuiChung.DocGui docgui = new DocGuiChung.DocGui(socket);
						docgui.GuiTin(vec1.get(i).toString());
						System.out.println("đã gửi xong");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thTIm.start();
		
		
	}

	private void capnhatbangrocket(Socket socket) {
		int size = vListSocket.size();
		for (int i = 0; i < size; i++) {
			Vector row = (Vector) vListSocket.get(i);
			Socket socketList = (Socket) row.elementAt(0);
			if (socket == socketList) {
				row.set(2, "[Đặt Vé]");
			}
			vListSocket.set(i, row);
			modelsocket.fireTableDataChanged();
		}
	}

	private void NhanThongTinTuUser() {
		try {
			vTitlelistSocket.add("Tên Server Kết Nối");
			vTitlelistSocket.add("Yêu Cầu");
			vTitlelistSocket.add("Tra�ng Tha�i");
			vTitlelistSocket.add("CMND");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void NapDuLieutuDataBaselenBang() {
		vData.clear();
		vTitle.clear();
		try {
			prst = (PreparedStatement) conn.prepareStatement(sqlSelectAll);
			ResultSet rss = prst.executeQuery();
			ResultSetMetaData rsmd = rss.getMetaData();
			int col = rsmd.getColumnCount();
			for (int i = 1; i <= col; i++) {
				vTitle.add(rsmd.getColumnName(i).toString());
			}
			while (rss.next()) {
				Vector row = new Vector();
				for (int i = 1; i <= col; i++) {
					row.add(rss.getString(i));
				}
				vData.add(row);
			}
			modelvetau.fireTableDataChanged();

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
		setBounds(100, 100, 1283, 677);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1269, 640);
		contentPane.add(tabbedPane);

		modelsocket = new DefaultTableModel(vListSocket, vTitlelistSocket);

		modelvetau = new DefaultTableModel(vData, vTitle);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		tabbedPane.addTab("Qua�n Ly� Ve� Ta�u", null, desktopPane, null);

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
		panel_1.setBounds(547, 79, 707, 206);
		desktopPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 15, 691, 185);
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

		JButton btnketnoi = new JButton("K\u00CA\u0301T N\u00D4\u0301I");
		btnketnoi.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnketnoi.setBounds(547, 295, 121, 32);
		desktopPane.add(btnketnoi);

		JButton btnxemyeucau = new JButton("XEM Y\u00CAU C\u00C2\u0300U");
		btnxemyeucau.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnxemyeucau.setBounds(690, 295, 121, 32);
		desktopPane.add(btnxemyeucau);

		JButton btnNewButton_3 = new JButton("\u0110\u00D4\u0300NG Y\u0301 ");
		btnNewButton_3.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnNewButton_3.setBounds(823, 295, 121, 32);
		desktopPane.add(btnNewButton_3);

		JButton btnngatkn = new JButton("NG\u0102\u0301T K\u00CA\u0301T N\u00D4\u0301I");
		btnngatkn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int SelectlistRocket = tablesocket.getSelectedRow();
				Vector row = (Vector) vListSocket.get(SelectlistRocket);
				Socket socket = (Socket) row.elementAt(0);
//				ServerGui svg = new ServerGui(socket, "[Exit]");
//				svg.start();
				vListSocket.remove(SelectlistRocket);
				JOptionPane.showInternalMessageDialog(null,
						"�a� ng��t k��t n��i v��i client " + vListSocket.get(0).toString(), "Th�ng Ba�o",
						JOptionPane.DEFAULT_OPTION);
			}
		});
		btnngatkn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnngatkn.setBounds(1106, 295, 137, 32);
		desktopPane.add(btnngatkn);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setForeground(Color.BLACK);
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"BA\u0309NG VE\u0301 TA\u0300U", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(547, 331, 707, 282);
		desktopPane.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 10, 697, 272);
		panel_2.add(scrollPane_1);
		tablevetau = new JTable(modelvetau);
		// khi click vao mot hang thi hien thong tin hang do ra
		tablevetau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector row = (Vector) vData.get(tablevetau.getSelectedRow());

			}
		});

		scrollPane_1.setViewportView(tablevetau);

		JButton btnThm = new JButton("TH\u00CAM");
		btnThm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnThm.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnThm.setBounds(34, 571, 121, 32);
		desktopPane.add(btnThm);

		JButton btnNewButton_1_1 = new JButton("C\u00C2\u0323P NH\u00C2\u0323T");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnNewButton_1_1.setBounds(173, 571, 121, 32);
		desktopPane.add(btnNewButton_1_1);

		JButton btntimkiem = new JButton("TI\u0300M KI\u00CA\u0301M");
		btntimkiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btntimkiem.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btntimkiem.setBounds(304, 571, 121, 32);
		desktopPane.add(btntimkiem);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"TRA\u0323NG THA\u0301I", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 31, 405, 85);
		desktopPane.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 14, 386, 61);
		panel_3.add(panel);
		panel.setLayout(null);

		final JLabel lbltrangthai = new JLabel("Tra\u0323ng Tha\u0301i:");
		lbltrangthai.setHorizontalAlignment(SwingConstants.LEFT);
		lbltrangthai.setBounds(119, 7, 257, 44);
		panel.add(lbltrangthai);

		JButton btnknoi = new JButton("K\u00EA\u0301t N\u00F4\u0301i");
		btnknoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread th1 = new Thread() {
					public void run() {
						try {
							lbltrangthai.setText("Trạng Thái: Bật");
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
							lbltrangthai.setText("Trạng Thái: Tắt");
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

		JLabel lblhoten = new JLabel("Họ Tên:");
		lblhoten.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblhoten.setBounds(34, 147, 110, 19);
		desktopPane.add(lblhoten);

		JLabel lblcmnd = new JLabel("CMND:");
		lblcmnd.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblcmnd.setBounds(34, 178, 110, 19);
		desktopPane.add(lblcmnd);

		JLabel lblsdt = new JLabel("Số Điện Thoại:");
		lblsdt.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblsdt.setBounds(34, 209, 110, 19);
		desktopPane.add(lblsdt);

		JLabel lbldiachi = new JLabel("Địa Chỉ:");
		lbldiachi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbldiachi.setBounds(34, 245, 110, 19);
		desktopPane.add(lbldiachi);

		JLabel lblmavetau = new JLabel("Mã Vé Tàu:");
		lblmavetau.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblmavetau.setBounds(34, 277, 110, 19);
		desktopPane.add(lblmavetau);

		JLabel lblnoidi = new JLabel("Nơi Đi:");
		lblnoidi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblnoidi.setBounds(33, 306, 110, 19);
		desktopPane.add(lblnoidi);

		JLabel lblnoiden = new JLabel("Nơi Đến:");
		lblnoiden.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblnoiden.setBounds(32, 338, 121, 19);
		desktopPane.add(lblnoiden);

		JLabel lblngaydi = new JLabel("Ngày Đi:");
		lblngaydi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblngaydi.setBounds(34, 377, 110, 19);
		desktopPane.add(lblngaydi);

		JLabel lbltoa = new JLabel("Toa:");
		lbltoa.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbltoa.setBounds(34, 408, 110, 19);
		desktopPane.add(lbltoa);

		JLabel lblloaicho = new JLabel("Loại Chỗ:");
		lblloaicho.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblloaicho.setBounds(34, 439, 110, 19);
		desktopPane.add(lblloaicho);

		JLabel lblloaive = new JLabel("Loại Vé:");
		lblloaive.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblloaive.setBounds(34, 470, 110, 19);
		desktopPane.add(lblloaive);

		JLabel lblgiave = new JLabel("Giá Vé:");
		lblgiave.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblgiave.setBounds(34, 504, 110, 19);
		desktopPane.add(lblgiave);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(154, 502, 271, 19);
		desktopPane.add(textField);

		JComboBox cbbloaive = new JComboBox();
		cbbloaive.setBounds(154, 471, 271, 22);
		desktopPane.add(cbbloaive);

		JComboBox cbbloaicho = new JComboBox();
		cbbloaicho.setBounds(154, 440, 271, 22);
		desktopPane.add(cbbloaicho);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(154, 406, 271, 19);
		desktopPane.add(textField_1);

		JDateChooser tfdate = new JDateChooser((Date) null, "YYYY-MM-dd");
		tfdate.setBounds(154, 375, 271, 19);
		desktopPane.add(tfdate);

		JComboBox cbbgaden = new JComboBox();
		cbbgaden.setBounds(154, 338, 271, 22);
		desktopPane.add(cbbgaden);

		JComboBox cbbgadi = new JComboBox();
		cbbgadi.setBounds(154, 306, 271, 22);
		desktopPane.add(cbbgadi);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(154, 275, 271, 19);
		desktopPane.add(textField_2);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(154, 243, 271, 19);
		desktopPane.add(textField_8);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(154, 207, 271, 19);
		desktopPane.add(textField_9);

		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(154, 176, 271, 19);
		desktopPane.add(textField_10);

		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(154, 145, 271, 19);
		desktopPane.add(textField_11);
		
		JButton btnNewButton_3_1 = new JButton("GỬI THÔNG TIN");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnNewButton_3_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnNewButton_3_1.setBounds(954, 295, 142, 32);
		desktopPane.add(btnNewButton_3_1);

		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Th�ng tin Ta�u", null, desktopPane_1, null);
		desktopPane_1.setLayout(null);

		JLabel lblServer = new JLabel("SERVER");
		lblServer.setBounds(0, 0, 91, 32);
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer.setFont(new Font("Times New Roman", Font.BOLD, 22));
		desktopPane_1.add(lblServer);

		JLabel lblNewLabel_3 = new JLabel("TH\u00D4NG TIN TA\u0300U");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel_3.setBounds(42, 10, 1006, 54);
		desktopPane_1.add(lblNewLabel_3);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 277, 1148, 326);
		desktopPane_1.add(scrollPane_2);

		table = new JTable();
		scrollPane_2.setViewportView(table);

		JLabel lblNewLabel_2_5 = new JLabel("Ma\u0303 Ve\u0301 Ta\u0300u:");
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_5.setBounds(41, 62, 91, 23);
		desktopPane_1.add(lblNewLabel_2_5);

		tfmave = new JTextField();
		tfmave.setColumns(10);
		tfmave.setBounds(136, 64, 302, 21);
		desktopPane_1.add(tfmave);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(136, 97, 302, 21);
		desktopPane_1.add(textField_4);

		JLabel lblNewLabel_2_1_1 = new JLabel("T\u00EAn Chuy\u00EA\u0301n Ta\u0300u:");
		lblNewLabel_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_1_1.setBounds(41, 95, 91, 23);
		desktopPane_1.add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_2_1 = new JLabel("Ga \u0110i:");
		lblNewLabel_2_2_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_2_1.setBounds(41, 128, 91, 23);
		desktopPane_1.add(lblNewLabel_2_2_1);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "", "TP HA\u0300 N\u00D4\u0323I", "TP VINH",
				"TP HU\u00CA\u0301", "TP \u0110A\u0300 N\u0102\u0303NG ", "TP HCM" }));
		comboBox_1.setBounds(136, 161, 302, 23);
		desktopPane_1.add(comboBox_1);

		JLabel lblNewLabel_2_4_1 = new JLabel("Ga \u0110\u00EA\u0301n:");
		lblNewLabel_2_4_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_4_1.setBounds(41, 161, 91, 23);
		desktopPane_1.add(lblNewLabel_2_4_1);

		JLabel lblNewLabel_2_3_1 = new JLabel("Nga\u0300y Kh\u01A1\u0309i Ha\u0300nh");
		lblNewLabel_2_3_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_3_1.setBounds(41, 194, 91, 23);
		desktopPane_1.add(lblNewLabel_2_3_1);

		JDateChooser datechooser = new JDateChooser(null, "YYYY-MM-dd");
		datechooser.setBounds(136, 196, 302, 21);
		desktopPane_1.add(datechooser);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "", "TP HA\u0300 N\u00D4\u0323I", "TP VINH",
				"TP HU\u00CA\u0301", "TP \u0110A\u0300 N\u0102\u0303NG ", "TP HCM" }));
		comboBox_2.setBounds(136, 129, 302, 22);
		desktopPane_1.add(comboBox_2);

		JLabel lblNewLabel_2_5_1 = new JLabel("Toa:");
		lblNewLabel_2_5_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_5_1.setBounds(507, 62, 91, 23);
		desktopPane_1.add(lblNewLabel_2_5_1);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("S\u00F4\u0301 Gh\u00EA\u0301:");
		lblNewLabel_2_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_1_1_1.setBounds(507, 95, 91, 23);
		desktopPane_1.add(lblNewLabel_2_1_1_1);

		JLabel lblNewLabel_2_2_1_1 = new JLabel("Loa\u0323i Ch\u00F4\u0303:");
		lblNewLabel_2_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_2_1_1.setBounds(507, 128, 91, 23);
		desktopPane_1.add(lblNewLabel_2_2_1_1);

		JLabel lblNewLabel_2_4_1_1 = new JLabel("Loa\u0323i Ve\u0301:");
		lblNewLabel_2_4_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_4_1_1.setBounds(507, 161, 91, 23);
		desktopPane_1.add(lblNewLabel_2_4_1_1);

		JLabel lblNewLabel_2_3_1_1 = new JLabel("Gia\u0301 Ve\u0301:");
		lblNewLabel_2_3_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_2_3_1_1.setBounds(507, 194, 91, 23);
		desktopPane_1.add(lblNewLabel_2_3_1_1);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(621, 62, 302, 21);
		desktopPane_1.add(textField_3);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(621, 95, 302, 21);
		desktopPane_1.add(textField_5);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(621, 194, 302, 21);
		desktopPane_1.add(textField_7);

		JComboBox comboBox_2_1 = new JComboBox();
		comboBox_2_1.setModel(new DefaultComboBoxModel(
				new String[] { "", "Th\u01B0\u01A1\u0300ng", "\u0110i\u00EA\u0300u Ho\u0300a" }));
		comboBox_2_1.setBounds(621, 129, 302, 22);
		desktopPane_1.add(comboBox_2_1);

		JComboBox comboBox_1_1 = new JComboBox();
		comboBox_1_1.setModel(new DefaultComboBoxModel(
				new String[] { "", "Th\u01B0\u01A1\u0300ng", "Kh\u01B0\u0301 H\u00F4\u0300i" }));
		comboBox_1_1.setBounds(621, 161, 302, 23);
		desktopPane_1.add(comboBox_1_1);

		JLabel lblNewLabel_4 = new JLabel("BA\u0309NG TH\u00D4NG TIN VE\u0301 TA\u0300U:");
		lblNewLabel_4.setBounds(11, 253, 135, 23);
		desktopPane_1.add(lblNewLabel_4);

		JButton btnNewButton = new JButton("TH\u00CAM ");
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(958, 63, 135, 22);
		desktopPane_1.add(btnNewButton);

		JButton btnSa = new JButton("S\u01AF\u0309A");
		btnSa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSa.setBounds(958, 97, 135, 22);
		desktopPane_1.add(btnSa);

		JButton btnXoa = new JButton("XO\u0301A");
		btnXoa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnXoa.setBounds(958, 129, 135, 22);
		desktopPane_1.add(btnXoa);

		JButton btnTimKim = new JButton("TI\u0300M KI\u00CA\u0301M");
		btnTimKim.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKim.setBounds(958, 162, 135, 22);
		desktopPane_1.add(btnTimKim);

		JButton btnThoat = new JButton("THOA\u0301T");
		btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnThoat.setBounds(958, 195, 135, 22);
		desktopPane_1.add(btnThoat);

		JDesktopPane desktopPane_2 = new JDesktopPane();
		desktopPane_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Th��ng k�", null, desktopPane_2, null);
		desktopPane_2.setLayout(null);

		JLabel lblServer_1 = new JLabel("SERVER");
		lblServer_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer_1.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblServer_1.setBounds(0, 0, 102, 32);
		desktopPane_2.add(lblServer_1);

		JLabel lblNewLabel_5 = new JLabel("TH\u00D4\u0301NG K\u00CA");
		lblNewLabel_5.setBackground(Color.WHITE);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblNewLabel_5.setBounds(192, 13, 783, 65);
		desktopPane_2.add(lblNewLabel_5);
	}
}
