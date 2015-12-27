package ua.nure.coursework.ivanov.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLDataException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ua.nure.coursework.ivanov.data.Person;
import ua.nure.coursework.ivanov.data.Trip;
import ua.nure.coursework.ivanov.db.DBWorker;


public class DataInputFrame extends DataFrame {

	public DataInputFrame() throws SQLException, ReflectiveOperationException {
		super();
		setTitle("Окно ввода");
		getChangeButton().setText("Внести");
		
		getChangeButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					DBWorker dbWorker = DBWorker.getInstance();
					switch (getTripTypeComboBox().getSelectedIndex()) {
					case 0:
						dbWorker.insertTrip(getPersonName().getText(), getPersonAppointment().getText(),
								getDestination().getText(), getDate().getText());
						break;
						
					case 1:
						dbWorker.insertTrip(getPersonName().getText(), getPersonAppointment().getText(),
								getDestination().getText(), getDate().getText(),
								Integer.parseInt(getExpectedDuration().getText()), Double.parseDouble(getExpectedTicketPrice().getText()),
								Double.parseDouble(getExpectedDailyHabitation().getText()));
						break;
						
					case 2:
						Trip trip = new Trip(new Person(getPersonName().getText(), getPersonAppointment().getText()),
								getDestination().getText(), getDate().getText(),
								Integer.parseInt(getExpectedDuration().getText()), Double.parseDouble(getExpectedTicketPrice().getText()),
								Double.parseDouble(getExpectedDailyHabitation().getText()),
								Integer.parseInt(getActualDuration().getText()), Double.parseDouble(getActualTicketPrice().getText()),
								Double.parseDouble(getActualDailyHabitation().getText()));
						double balance = trip.getBalance();
						
						dbWorker.insertTrip(getPersonName().getText(), getPersonAppointment().getText(),
								getDestination().getText(), getDate().getText(),
								Integer.parseInt(getExpectedDuration().getText()), Double.parseDouble(getExpectedTicketPrice().getText()),
								Double.parseDouble(getExpectedDailyHabitation().getText()),
								Integer.parseInt(getActualDuration().getText()), Double.parseDouble(getActualTicketPrice().getText()),
								Double.parseDouble(getActualDailyHabitation().getText()), balance);
						break;
					}
					JOptionPane.showMessageDialog(null, "Добавлено");
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Некорректные ожидаемые/актуальные данные!");
				} catch (SQLDataException e) {
					JOptionPane.showMessageDialog(null, "Некорректная дата!");
				} catch (SQLException | ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
