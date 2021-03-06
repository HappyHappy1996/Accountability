package ua.nure.coursework.ivanov.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.nure.coursework.ivanov.data.Person;
import ua.nure.coursework.ivanov.data.Trip;

public class DBWorker {

	private static DBWorker instance;

	private static final String URL = "jdbc:derby:tripdb;create=true";
	private static final String USER = "root";
	private static final String PASSWORD = "rootdb";
	private static final String TABLE_NAME = "trips";

	private Connection connection = null;
	private Statement statement = null;

	private DBWorker() throws SQLException, ReflectiveOperationException {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			shutdown();
			System.err.println("Couldn't get connection");
		} catch (ReflectiveOperationException e) {
			System.err.println("Couldn't register driver");
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static synchronized DBWorker getInstance() throws SQLException,
			ReflectiveOperationException {
		if (instance == null) {
			instance = new DBWorker();
		}
		return instance;
	}

	private void createTable() throws SQLException {
		statement = connection.createStatement();
        statement.execute("create table " + TABLE_NAME + "(id int not null generated by default as identity, " +
        		"name varchar(40), appointment varchar(20), destination varchar(20), date timestamp, " +
        		"expectedDuration int, expectedTicketPrice double, expectedDailyHabitation double, " +
        		"actualDuration int, actualTicketPrice double, actualDailyHabitation double, balance double)");
        System.out.println("db is created, all is ok");
        statement.close();
    }
	
	private void dropTable() throws SQLException {
		statement = connection.createStatement();
        statement.execute("drop table " + TABLE_NAME);
        System.out.println("db is dropped, all is ok");
        statement.close();
    }
        
	public Trip[] selectAllTrips() throws SQLException {
		try {
			int tripsCount = selectTripsCount();
			if (tripsCount == 0) {
				return new Trip[0];
			}
			
			Trip[] tripsArray = createAndFillTripsArray(tripsCount);
			return tripsArray;
		} catch (SQLException e) {
			throw e;
		}
	}
    
    private int selectTripsCount() throws SQLException {
    	int size = 0;
    	try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select count(*) from " + TABLE_NAME);
			if (resultSet.next()) {
				size = resultSet.getInt(1);
				statement.close();
			} else {
				resultSet.close();
				statement.close();
				System.out.println("size in else in selectTripsCount method section is " + size);
				return size;
			}
		} catch (SQLException e) {
			throw e;
		}
    	return size;
    }
    
    private Trip[] createAndFillTripsArray(int arraySize) throws SQLException {
    	Trip[] tripsArray = new Trip[arraySize];
    	statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from " + TABLE_NAME);
		int iteration = 0;
		while (resultSet.next()) {
			tripsArray[iteration] = new Trip(new Person(resultSet.getString("name"), resultSet.getString("appointment")), 
					resultSet.getString("destination"), resultSet.getDate("date").toString(), resultSet.getInt("id"));
			if (resultSet.getInt("expectedDuration") != 0) {
				tripsArray[iteration].setExpectedCost(resultSet.getInt("expectedDuration"),
						resultSet.getDouble("expectedTicketPrice"), resultSet.getDouble("expectedDailyHabitation"));
				if (resultSet.getInt("actualDuration") != 0) {
					tripsArray[iteration].setActualCost(resultSet.getInt("actualDuration"),
							resultSet.getDouble("actualTicketPrice"), resultSet.getDouble("actualDailyHabitation"));
				}
			}
			iteration++;
		}
		
		resultSet.close();
		statement.close();
    	return tripsArray;
    }

	public void updateTrip(String name, String appointment, String destination, String date, int id) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("update " + TABLE_NAME + " set name='" + name + "', appointment='" + appointment +
    			"', destination='" + destination + "', date={ts '" + date + " 00:00:00'}" + " where id = " + id);
    	statement.close();
    }
    
    public void updateTrip(String name, String appointment, String destination, String date,
    		int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation, int id) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("update " + TABLE_NAME + " set name='" + name + "', appointment='" + appointment +
    			"', destination='" + destination + "' , date={ts '" + date + " 00:00:00'}, expectedDuration=" +
    			expectedDuration + ", expectedTicketPrice=" + expectedTicketPrice +
    			", expectedDailyHabitation=" + expectedDailyHabitation + " where id = " + id);
    	statement.close();
    }
    
    public void updateTrip(String name, String appointment, String destination, String date,
    		int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation,
    		int actualDuration, double actualTicketPrice, double actualDailyHabitation,
    		double balance, int id) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("update " + TABLE_NAME + " set name='" + name + "', appointment='" + appointment +
    			"', destination='" + destination + "' , date={ts '" + date + " 00:00:00'}, expectedDuration=" +
    			expectedDuration + ", expectedTicketPrice=" + expectedTicketPrice + ", expectedDailyHabitation=" + expectedDailyHabitation + 
    			", actualDuration=" + actualDuration + ", actualTicketPrice=" + actualTicketPrice + 
    			", actualDailyHabitation=" + actualDailyHabitation + ", balance=" + balance + " where id = " + id);
    	statement.close();
    }
    
    public void closeTrip(int id) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("update " + TABLE_NAME + " set expectedDuration=NULL, expectedTicketPrice=NULL,"
    			+ " expectedDailyHabitation=NULL, actualDuration=NULL, actualTicketPrice=NULL,"
    			+ " actualDailyHabitation=NULL, balance=NULL where id = " + id);
    	statement.close();
    }
    
    public void insertTrip(String name, String appointment, String destination, String date) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("insert into " + TABLE_NAME + " (name, appointment, destination, date) values ('" +
    			name + "', '" + appointment + "', '" + destination + "', {ts '" + date + " 00:00:00'})");
    	statement.close();
    }
    
    public void insertTrip(String name, String appointment, String destination, String date,
    		int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("insert into " + TABLE_NAME +
    			" (name, appointment, destination, date, expectedDuration, expectedTicketPrice, expectedDailyHabitation) values ('"
    			+ name + "', '" + appointment + "', '"  + destination + "', {ts '" + date + " 00:00:00'}, "
    			+ expectedDuration + ", " + expectedTicketPrice + ", " + expectedDailyHabitation + ")");
    	statement.close();
    }
    
    public void insertTrip(String name, String appointment, String destination, String date,
    		int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation,
    		int actualDuration, double actualTicketPrice, double actualDailyHabitation,
    		double balance) throws SQLException {
    	statement = connection.createStatement();
    	statement.execute("insert into " + TABLE_NAME +
    			" (name, appointment, destination, date, expectedDuration, expectedTicketPrice, expectedDailyHabitation, "
    			+ "actualDuration, actualTicketPrice, actualDailyHabitation, balance) values ('"
    			+ name + "', '" + appointment + "', '" + destination + "',  {ts '" + date + " 00:00:00'}, "
    			+ expectedDuration + ", " + expectedTicketPrice + ", " + expectedDailyHabitation + ", "
    			+ actualDuration + ", " + actualTicketPrice + ", " + actualDailyHabitation + ", "
    			+ balance + ")");
    	statement.close();
    }
    
    public void deleteTrip(int id) throws SQLException {
    	statement = connection.createStatement();
    	statement.executeUpdate("delete from " + TABLE_NAME + " where id = " + id);
    	statement.close();
    }
   
	private void shutdown() throws SQLException {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				DriverManager.getConnection(URL + ";shutdown=true", USER,
						PASSWORD);
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Couldn't shutdown connection");
			throw e;
		}

	}

	private static void restore(String[] args) throws Exception {
		
		DBWorker dbWorker = DBWorker.getInstance();
		
//		System.out.println("1");
//		dbWorker.createTable();
		
//		System.out.println("2");
//		dbWorker.insertTrip("Bonnie", "Singer", "NY", "1971-02-03");
//		dbWorker.insertTrip("Adele", "Singer", "LA", "1975-04-09");
//		dbWorker.insertTrip("Abrams", "General", "Kosovo", "1965-01-21");
//		dbWorker.insertTrip("Rommel", "General", "Berlin", "1944-07-17", 1, 2, 3);
//		dbWorker.insertTrip("Guderian", "Inspector", "Oslo", "1943-02-14", 4, 5, 6);
//		dbWorker.insertTrip("Mark", "Economist", "Luxemburg", "2014-05-06", 7, 8, 9, 10, 11, 12, 10.2);
//		dbWorker.insertTrip("Yosef", "Developer", "Poznan", "2015-01-27", 4, 5, 6, 7, 8, 9, 19.2);
		
		System.out.println("3");

		dbWorker.selectAllTrips();
		
//		dbWorker.updateTrip("T-4", "doiche tank", "Moscow", "1942-01-06", 1);
//		dbWorker.updateTrip("Guderian", "inspector", "Rein", "1944-05-06", 11, 22, 33, 101);
//		dbWorker.updateTrip("David", "Manager", "Oslo", "2010-11-12", 60, 61, 62, 63, 64, 64, 201);
		
//		dbWorker.dropTable();
		
		System.out.println("All is ok");
	}
	
}