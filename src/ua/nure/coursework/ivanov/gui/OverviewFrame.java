package ua.nure.coursework.ivanov.gui;

import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ua.nure.coursework.ivanov.data.Trip;
import ua.nure.coursework.ivanov.db.DBWorker;

public class OverviewFrame extends JFrame {

	private static OverviewFrame instance;
	
	private JList<Trip> tripList;
	private DefaultListModel<Trip> listModel = new DefaultListModel<Trip>();
	private Trip selectedTrip;
	
	public Trip getSelectedTrip() {
		return selectedTrip;
	}
	
	public boolean isChoosen() {
		return selectedTrip != null;
	}
	
	public static synchronized OverviewFrame getInstance() {
		if (instance == null) {
			instance = new OverviewFrame();
		} else {
			instance.updateData();
		}
		return instance;
	}

	private OverviewFrame() {

		setTitle("Îבחמנ");
		setSize(1000, 600);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setVisible(true);
	    setLocationRelativeTo(null);
		
	    
	    
	    updateData();
	     
        tripList = new JList<Trip>(listModel);
        add(tripList);
        add(new JScrollPane(tripList));
                
        tripList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
            	if (!evt.getValueIsAdjusting()) {
            		selectedTrip = tripList.getSelectedValue();
            	}}
            });
        
        
	}
	
	private void updateData() {
		listModel.clear();
		Trip[] trips = null;
		try {
			trips = DBWorker.getInstance().selectAllTrips();
		} catch (SQLException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
	    
	    for(int i = 0; i < trips.length; i++) {
	    	listModel.addElement(trips[i]);
	    }
	}
	
}
