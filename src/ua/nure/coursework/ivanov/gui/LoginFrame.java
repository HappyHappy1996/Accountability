package ua.nure.coursework.ivanov.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import ua.nure.coursework.ivanov.cryptographic.Cryptographer;

public class LoginFrame extends JFrame {

	private JLabel passwordLabel = new JLabel("Пароль");

	private JPasswordField password = new JPasswordField();

	private JButton logIn = new JButton("Войти");

	private static LoginFrame instance;

	public LoginFrame() {
		setTitle("Окно входа");
		setBounds(100, 100, 250, 250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		add(passwordLabel);
		add(password);
		add(logIn);

		passwordLabel.setBounds(100, 25, 100, 25);
		password.setBounds(50, 60, 150, 25);
		logIn.setBounds(75, 150, 100, 25);

		initializeListeners();

	}

	public static synchronized LoginFrame getInstance() {
		if (instance == null) {
			instance = new LoginFrame();
		}
		return instance;
	}

	private void initializeListeners() {
		logIn.addMouseListener(new MouseAdapter() {

			private final String correctPassHash = "4fc5b4aab5f5e16c89f51753a25c893a";
			private final String ALGORITHM = "MD5";

			public void mouseClicked(MouseEvent event) {

				try {
					if (Cryptographer.hash(
							String.valueOf(password.getPassword()), ALGORITHM)
							.equals(correctPassHash)) {
						password.setText("");
						StartFrame.getInstance().setVisible(true);
						LoginFrame.this.setVisible(false);
					} else {
						password.setText("");
						JOptionPane.showMessageDialog(null,
								"Password is wrong!");
					}
				} catch (UnsupportedEncodingException
						| NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
		});

	}

}