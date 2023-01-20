package CUOIKI.LAPTRINHMANG;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class DangNhap extends JFrame {
	private String tkadmin = "admin";
	private String mkadmin = "admin";
	private String tkkh1 = "khachhang1";
	private String mkkh1 = "khachhang1";
	private String tkkh2 = "khachhang2";
	private String mkkh2 = "khachhang2";
	private String tkkh3 = "khachhang3";
	private String mkkh3 = "khachhang3";
	private JPanel contentPane;
	private JTextField tftk;
	private JPasswordField pfmk;
	private JCheckBox showpass;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DangNhap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u0110\u0102NG NH\u00C2\u0323P");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 10, 455, 52);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("T\u00EAn \u0110\u0103ng Nh\u00E2\u0323p:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(58, 72, 116, 32);
		contentPane.add(lblNewLabel_1);
		
		tftk = new JTextField();
		tftk.setBounds(166, 72, 249, 24);
		contentPane.add(tftk);
		tftk.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("M\u00E2\u0323t Kh\u00E2\u0309u:");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(58, 114, 116, 32);
		contentPane.add(lblNewLabel_1_1);
		
		pfmk = new JPasswordField();
		pfmk.setBounds(166, 114, 249, 24);
		contentPane.add(pfmk);
		
		showpass = new JCheckBox("Hi\u00EA\u0323n Thi\u0323");
		showpass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource() == showpass) {
					if (showpass.isSelected()) {
						pfmk.setEchoChar((char) 0);
					} else {
						pfmk.setEchoChar('*');
					}

				}
			}
		});
		showpass.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		showpass.setBounds(166, 152, 93, 21);
		contentPane.add(showpass);
		
		JButton btndangnhap = new JButton("\u0110\u0102NG NH\u00C2\u0323P");
		btndangnhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					String userText="";
					String pwdText="";
					userText = tftk.getText();
					pwdText = pfmk.getText();
					int kq =tkadmin.compareTo(userText);
					int kq1 = mkadmin.compareTo(pwdText);
					
					if (kq ==0 && kq1 ==0) {
						JOptionPane.showMessageDialog(null, "Đăng Nhập Thành Công");
						try {
							Server server = new Server();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nhập sai Tên Đăng Nhập hoặc mật khẩu ");
					}
					
					int client1 =tkkh1.compareTo(userText);
					int mk_client1 = mkkh1.compareTo(pwdText);
					int client2 =tkkh2.compareTo(userText);
					int mk_client2 = mkkh2.compareTo(pwdText);
					int client3 =tkkh3.compareTo(userText);
					int mk_client3 = mkkh3.compareTo(pwdText);
					
					if (client1 ==0 && mk_client1 ==0 || client2 ==0 && mk_client2 ==0 || client3 ==0 && mk_client3 ==0 ) {
						JOptionPane.showMessageDialog(null, "Đăng Nhập Thành Công");
						Client server = new Client("VeTau");
					} else {
						JOptionPane.showMessageDialog(null, "Nhập sai Tên Đăng Nhập hoặc mật khẩu ");
					}
					
				
			}
		});
		btndangnhap.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btndangnhap.setBounds(74, 199, 141, 52);
		contentPane.add(btndangnhap);
		
		JButton btnthoat = new JButton("THOA\u0301T");
		btnthoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnthoat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnthoat.setBounds(253, 199, 141, 52);
		contentPane.add(btnthoat);
	}
}
