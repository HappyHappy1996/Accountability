package ua.nure.coursework.ivanov.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartFrame extends JFrame {

	private JButton overviewButton = new JButton("Overview");
	private JButton inputValuesButton = new JButton("Input values");
	private JButton editValuesButton = new JButton("Edit values");
	
	private DataEditFrame dataEditFrame = null;
	private DataInputFrame dataInputFrame = null;
	
	private static StartFrame instance;

	private StartFrame() {
		setTitle("Start window");
		setBounds(100, 100, 250, 220);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		add(overviewButton);
		add(inputValuesButton);
		add(editValuesButton);

		overviewButton.setBounds(65, 30, 120, 25);
		inputValuesButton.setBounds(65, 80, 120, 25);
		editValuesButton.setBounds(65, 130, 120, 25);

		initializeListeners();

	}

	public static synchronized StartFrame getInstance() {
		if (instance == null) {
			instance = new StartFrame();
		}
		return instance;
	}

	private void initializeListeners() {
		overviewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				OverviewFrame.getInstance().setVisible(true);
			}
		});
		
		inputValuesButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				StartFrame.this.setVisible(false);
				try {
					dataInputFrame = new DataInputFrame();
				} catch (SQLException | ReflectiveOperationException e) {
					JOptionPane.showMessageDialog(null, "Can't connect to DB");
					e.printStackTrace();
					StartFrame.this.setVisible(true);
				}
			}
		});
		
		editValuesButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				StartFrame.this.setVisible(false);
				try {
					dataEditFrame = new DataEditFrame();
					StartFrame.getInstance().setVisible(false);;
				} catch (SQLException | ReflectiveOperationException e) {
					JOptionPane.showMessageDialog(null, "Can't connect to DB");
					e.printStackTrace();
					StartFrame.this.setVisible(true);
				}
			}
		});

	}
	
}