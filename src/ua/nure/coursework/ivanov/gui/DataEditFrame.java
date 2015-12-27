package ua.nure.coursework.ivanov.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLDataException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import ua.nure.coursework.ivanov.data.Person;
import ua.nure.coursework.ivanov.data.Trip;
import ua.nure.coursework.ivanov.data.Trip.TripState;
import ua.nure.coursework.ivanov.db.DBWorker;

public class DataEditFrame extends DataFrame {

	private JButton chooseButton = new JButton("Выбрать");
	private JButton removeButton = new JButton("Удалить");
	private JButton closeButton = new JButton("Закрыть");
	
	private Trip editableTrip;
	
	public void setEditableTrip(Trip trip) {
		editableTrip = trip;
	}
	
	public DataEditFrame() throws SQLException, ReflectiveOperationException {
		super();
		setTitle("Окно редактирования");
		getChangeButton().setText("Редактировать");
		add(chooseButton);
		chooseButton.setBounds(400, 60, 140, 25);
		
//		add(closeButton);
		closeButton.setBounds(110, 500, 140, 25);
		
		add(removeButton);
		removeButton.setBounds(210, 500, 140, 25);
		
		closeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (getTripTypeComboBox().getSelectedItem() == TripState.RETURNED) {
					try {
						DBWorker dbWorker = DBWorker.getInstance();
						dbWorker.closeTrip(editableTrip.getId());
						clearFields();
					} catch (SQLException | ReflectiveOperationException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Запрещенная операция!");
				}
			}
		});
		
		removeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (editableTrip == null) {
					JOptionPane.showMessageDialog(null, "Сначала выбрерите строку для редактирования!");
					return;
				}
				try {
					DBWorker dbWorker = DBWorker.getInstance();
					dbWorker.deleteTrip(editableTrip.getId());
					JOptionPane.showMessageDialog(null, "Удалено");
					clearFields();
				} catch (SQLException | ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}
		});
		
		chooseButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				
//				if (!SwingUtilities.isEventDispatchThread()){
//					OverviewFrame overviewFrame = OverviewFrame.getInstance();
//					overviewFrame.setVisible(true);
//					Trip trip = overviewFrame.getSelectedTrip();
//					System.out.println(trip);
//					overviewFrame.setVisible(false);
//				} else {
//				    SwingUtilities.invokeLater(new Runnable(){
//				        public void run(){
//				        	OverviewFrame overviewFrame = OverviewFrame.getInstance();
//							overviewFrame.setVisible(true);
//							Trip trip = overviewFrame.getSelectedTrip();
//							System.out.println(trip);
//							overviewFrame.setVisible(false);
//				        }
//				    });
//				}
				
				Trip trip = null;
				SwingWorker swingWorker = new SwingWorkerLoader(trip, DataEditFrame.this);
				swingWorker.execute();
				
			
			}
		});
		
		getChangeButton().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				
				if (editableTrip == null) {
					JOptionPane.showMessageDialog(null, "Сначала выбрерите строку для редактирования!");
					return;
				}
					
				int beforeIndexState = editableTrip.getState().ordinal();
				int afterIndexState = getTripTypeComboBox().getSelectedIndex();
					
				if (afterIndexState < beforeIndexState) {
					JOptionPane.showMessageDialog(null, "Запрещенная операция!");
					return;
				}
					
				try {
					DBWorker dbWorker = DBWorker.getInstance();
					int tripId = editableTrip.getId();
					switch (getTripTypeComboBox().getSelectedIndex()) {
					case 0:
						dbWorker.updateTrip(getPersonName().getText(), getPersonAppointment().getText(),
								getDestination().getText(), getDate().getText(), tripId);
						break;
						
					case 1:
						dbWorker.updateTrip(getPersonName().getText(), getPersonAppointment().getText(),
								getDestination().getText(), getDate().getText(),
								Integer.parseInt(getExpectedDuration().getText()), Double.parseDouble(getExpectedTicketPrice().getText()),
								Double.parseDouble(getExpectedDailyHabitation().getText()), tripId);
						break;
						
					case 2:
						Trip trip = new Trip(new Person(getPersonName().getText(), getPersonAppointment().getText()),
								getDestination().getText(), getDate().getText(),
								Integer.parseInt(getExpectedDuration().getText()), Double.parseDouble(getExpectedTicketPrice().getText()),
								Double.parseDouble(getExpectedDailyHabitation().getText()),
								Integer.parseInt(getActualDuration().getText()), Double.parseDouble(getActualTicketPrice().getText()),
								Double.parseDouble(getActualDailyHabitation().getText()));
						double balance = trip.getBalance();
						
						dbWorker.updateTrip(getPersonName().getText(), getPersonAppointment().getText(),
								getDestination().getText(), getDate().getText(),
								Integer.parseInt(getExpectedDuration().getText()), Double.parseDouble(getExpectedTicketPrice().getText()),
								Double.parseDouble(getExpectedDailyHabitation().getText()),
								Integer.parseInt(getActualDuration().getText()), Double.parseDouble(getActualTicketPrice().getText()),
								Double.parseDouble(getActualDailyHabitation().getText()), balance, tripId);
						break;
					}
					JOptionPane.showMessageDialog(null, "Обновление выполнено");
				} catch (SQLDataException e) {
					JOptionPane.showMessageDialog(null, "Некорректные данные!");
				}catch (SQLException | ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	private void clearFields() {
		getPersonName().setText("");
		getPersonAppointment().setText("");
		getDestination().setText("");
		getDate().setText("");
		getExpectedDuration().setText("");
		getExpectedTicketPrice().setText("");
		getExpectedDailyHabitation().setText("");
		getActualDuration().setText("");
		getActualTicketPrice().setText("");
		getActualDailyHabitation().setText("");
	}
}

class SwingWorkerLoader extends SwingWorker {

    private DataEditFrame frame;
    private OverviewFrame overviewFrame;

    private Trip trip;

    public SwingWorkerLoader(Trip trip, DataEditFrame frame) {
        this.trip = trip;
        this.frame = frame;
    }

    /**
     * Background part of loader. This method is called in background thread. It reads data from data source and
     * places it to UI  by calling {@link javax.swing.SwingWorker#publish(Object[])}
     *
     * @return background execution result - all data loaded
     * @throws Exception if any error occures
     */
    @Override
    protected Trip doInBackground() throws Exception {
        overviewFrame = OverviewFrame.getInstance();
        overviewFrame.setVisible(true);
		while (!overviewFrame.isChoosen()) {
			trip = overviewFrame.getSelectedTrip();
		}

        return null;
    }

   

    /**
     * This method is called in EDT after {@link #doInBackground()} is finished.
     */
    @Override
    protected void done() {
		trip = overviewFrame.getSelectedTrip();
		frame.setEditableTrip(trip);
		frame.getPersonName().setText(trip.getPerson().getName());
		frame.getPersonAppointment().setText(trip.getPerson().getAppointment());
		frame.getDestination().setText(trip.getDestination());
		frame.getDate().setText(trip.getDate());
		if (trip.getExpectedCost() != null) {
			frame.getExpectedDuration().setText(String.valueOf(trip.getExpectedCostDuration()));
			frame.getExpectedTicketPrice().setText(String.valueOf(trip.getExpectedCostTicketPrice()));
			frame.getExpectedDailyHabitation().setText(String.valueOf(trip.getExpectedCostDailyHabitation()));
			if (trip.getActualCost() != null) {
				frame.getActualDuration().setText(String.valueOf(trip.getActualCostDuration()));
				frame.getActualTicketPrice().setText(String.valueOf(trip.getActualCostTicketPrice()));
				frame.getActualDailyHabitation().setText(String.valueOf(trip.getActualCostDailyHabitation()));
				frame.getTripTypeComboBox().setSelectedItem(TripState.RETURNED);
			} else {
				frame.getTripTypeComboBox().setSelectedItem(TripState.DEPARTED);
			}
		}
		OverviewFrame.getInstance().setVisible(false);
    }
}