package ua.nure.coursework.ivanov.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ua.nure.coursework.ivanov.data.Trip.TripState;

public class DataFrame extends JFrame {

	private JLabel typeLabel = new JLabel("Select state of business trip");
	private JComboBox<TripState> tripType = new JComboBox<TripState>();
	
	private JLabel personNameLabel = new JLabel("Person name");
	private JTextField personName = new JTextField();
	private JLabel personAppointmentLabel = new JLabel("Person appointment");
	private JTextField personAppointment = new JTextField();
	private JLabel destinationLabel = new JLabel("Destination");
	private JTextField destination = new JTextField();
	private JLabel dateLabel = new JLabel("Date (e.g. 1970-01-01)");
	private JTextField date = new JTextField();
	
	private JLabel expectedDurationLabel = new JLabel("Expected duration");
	private JTextField expectedDuration = new JTextField();
	private JLabel expectedTicketPriceLabel = new JLabel("Expected ticket price");
	private JTextField expectedTicketPrice = new JTextField();
	private JLabel expectedDailyHabitationLabel = new JLabel("Expected daily habitation");
	private JTextField expectedDailyHabitation = new JTextField();
	
	private JLabel actualDurationLabel = new JLabel("Actual duration");
	private JTextField actualDuration = new JTextField();
	private JLabel actualTicketPriceLabel = new JLabel("Actual ticket price");
	private JTextField actualTicketPrice = new JTextField();
	private JLabel actualDailyHabitationLabel = new JLabel("Actual daily habitation");
	private JTextField actualDailyHabitation = new JTextField();

	private JButton changeButton = new JButton("OK");
	private JButton backButton = new JButton("Back");

	public DataFrame() throws SQLException, ReflectiveOperationException {
		
		setBounds(100, 100, 600, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		add(typeLabel);
		add(tripType);
		add(changeButton);
		add(backButton);

		add(personNameLabel);
		add(personName);
		add(personAppointmentLabel);
		add(personAppointment);
		add(destinationLabel);
		add(destination);
		add(date);
		add(dateLabel);
		
		expectedDurationLabel.setVisible(false);
		add(expectedDurationLabel);
		expectedDuration.setVisible(false);
		add(expectedDuration);		
		expectedTicketPriceLabel.setVisible(false);
		add(expectedTicketPriceLabel);
		expectedTicketPrice.setVisible(false);
		add(expectedTicketPrice);		
		add(expectedDailyHabitationLabel);
		expectedDailyHabitationLabel.setVisible(false);
		add(expectedDailyHabitation);
		expectedDailyHabitation.setVisible(false);
		
		actualDurationLabel.setVisible(false);
		add(actualDurationLabel);
		actualDuration.setVisible(false);
		add(actualDuration);		
		actualTicketPriceLabel.setVisible(false);
		add(actualTicketPriceLabel);
		actualTicketPrice.setVisible(false);
		add(actualTicketPrice);		
		add(actualDailyHabitationLabel);
		actualDailyHabitationLabel.setVisible(false);
		add(actualDailyHabitation);
		actualDailyHabitation.setVisible(false);
		
		typeLabel.setBounds(10, 20, 180, 25);
		tripType.setBounds(200, 20, 100, 25);
		changeButton.setBounds(10, 500, 80, 25);
		backButton.setBounds(400, 500, 80, 25);
		
		personNameLabel.setBounds				(10, 60, 150, 25);
		personName.setBounds					(160, 60, 150, 25);
		personAppointmentLabel.setBounds		(10, 100, 150, 25);
		personAppointment.setBounds				(160, 100, 150, 25);
		destinationLabel.setBounds				(10, 140, 150, 25);
		destination.setBounds					(160, 140, 150, 25);
		dateLabel.setBounds						(10, 180, 150, 25);
		date.setBounds							(160, 180, 150, 25);
		expectedDurationLabel.setBounds			(10, 220, 150, 25);
		expectedDuration.setBounds				(160, 220, 150, 25);
		expectedTicketPriceLabel.setBounds		(10, 260, 150, 25);
		expectedTicketPrice.setBounds			(160, 260, 150, 25);
		expectedDailyHabitationLabel.setBounds	(10, 300, 150, 25);
		expectedDailyHabitation.setBounds		(160, 300, 150, 25);
		actualDurationLabel.setBounds			(10, 340, 150, 25);
		actualDuration.setBounds				(160, 340, 150, 25);
		actualTicketPriceLabel.setBounds		(10, 380, 150, 25);
		actualTicketPrice.setBounds				(160, 380, 150, 25);
		actualDailyHabitationLabel.setBounds	(10, 420, 150, 25);
		actualDailyHabitation.setBounds			(160, 420, 150, 25);

		tripType.addItem(TripState.OPENED);
		tripType.addItem(TripState.DEPARTED);
		tripType.addItem(TripState.RETURNED);
		
		initializeListeners();

	}

	private void initializeListeners() {
		
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				StartFrame.getInstance().setVisible(true);
				DataFrame.this.dispose();
			}
		});
		
		tripType.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        switch (tripType.getSelectedIndex()) {
		        case 0:
					expectedDurationLabel.setVisible(false);
					expectedDuration.setVisible(false);
					expectedTicketPriceLabel.setVisible(false);
					expectedTicketPrice.setVisible(false);
					expectedDailyHabitationLabel.setVisible(false);
					expectedDailyHabitation.setVisible(false);
					actualDurationLabel.setVisible(false);
					actualDuration.setVisible(false);
					actualTicketPriceLabel.setVisible(false);
					actualTicketPrice.setVisible(false);
					actualDailyHabitationLabel.setVisible(false);
					actualDailyHabitation.setVisible(false);
					break;
					
				case 1:
					expectedDurationLabel.setVisible(true);
					expectedDuration.setVisible(true);
					expectedTicketPriceLabel.setVisible(true);
					expectedTicketPrice.setVisible(true);
					expectedDailyHabitationLabel.setVisible(true);
					expectedDailyHabitation.setVisible(true);
					actualDurationLabel.setVisible(false);
					actualDuration.setVisible(false);
					actualTicketPriceLabel.setVisible(false);
					actualTicketPrice.setVisible(false);
					actualDailyHabitationLabel.setVisible(false);
					actualDailyHabitation.setVisible(false);
					break;
					
				 case 2:
					expectedDurationLabel.setVisible(true);
					expectedDuration.setVisible(true);
					expectedTicketPriceLabel.setVisible(true);
					expectedTicketPrice.setVisible(true);
					expectedDailyHabitationLabel.setVisible(true);
					expectedDailyHabitation.setVisible(true);
					actualDurationLabel.setVisible(true);
					actualDuration.setVisible(true);
					actualTicketPriceLabel.setVisible(true);
					actualTicketPrice.setVisible(true);
					actualDailyHabitationLabel.setVisible(true);
					actualDailyHabitation.setVisible(true);
		        }
		    }
		});
	}

	public JTextField getPersonName() {
		return personName;
	}

	public JTextField getPersonAppointment() {
		return personAppointment;
	}

	public JTextField getDestination() {
		return destination;
	}

	public JTextField getDate() {
		return date;
	}
	
	public JTextField getExpectedDuration() {
		return expectedDuration;
	}

	public JTextField getExpectedTicketPrice() {
		return expectedTicketPrice;
	}

	public JTextField getExpectedDailyHabitation() {
		return expectedDailyHabitation;
	}

	public JTextField getActualDuration() {
		return actualDuration;
	}

	public JTextField getActualTicketPrice() {
		return actualTicketPrice;
	}

	public JTextField getActualDailyHabitation() {
		return actualDailyHabitation;
	}
	
	public JButton getChangeButton() {
		return changeButton;
	}

	public JComboBox getTripTypeComboBox() {
		return tripType;
	}
}
