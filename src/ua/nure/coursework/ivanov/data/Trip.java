package ua.nure.coursework.ivanov.data;

public class Trip {
	
	private Person person;
	private String destination;
	private Cost expectedCost;
	private Cost actualCost;
	private double balance;
	private TripState state;
	
	public Trip(Person person, String destination) {
		this.person = person;
		this.destination = destination;
		expectedCost = null;
		actualCost = null;
		balance = 0;
		state = TripState.OPENED;
	}
	
	public Trip(Person person, String destination, 
			int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation) {
		this.person = person;
		this.destination = destination;
		expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
		actualCost = null;
		balance = 0;
		state = TripState.DEPARTED;
	}
	
	public Trip(Person person, String destination, 
			int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation,
			int actualDuration, double actualTicketPrice, double actualDailyHabitation) {
		this.person = person;
		this.destination = destination;
		expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
		actualCost = new Cost(actualDuration, actualTicketPrice, actualDailyHabitation);
		balance = expectedCost.getValue() - actualCost.getValue();
		state = TripState.RETURNED;
	}
		
	public void setExpectedCost(int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation) throws IllegalStateException {
		if (state == TripState.OPENED) {
			expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
			state = TripState.DEPARTED;
		} else {
			throw new IllegalStateException("The trip has wrong state!");
		}
	}
	
	public void setActualCost(int actualDuration, double actualTicketPrice, double actualDailyHabitation) throws IllegalStateException {
		if (state == TripState.DEPARTED) {
			actualCost = new Cost(actualDuration, actualTicketPrice, actualDailyHabitation);
			balance = expectedCost.getValue() - actualCost.getValue();
			state = TripState.RETURNED;
		} else {
			throw new IllegalStateException("The trip has wrong state!");
		}
		
	}
	
	public void closeTrip() throws IllegalStateException {
		if (state == TripState.RETURNED || state == TripState.OPENED) {
			state = TripState.CLOSED;
			balance = 0;
			expectedCost = null;
			actualCost = null;
		} else {
			throw new IllegalStateException("The trip has wrong state!");
		}
	}

	private class Cost {
		
		private static final double DAILY_ALLOWANCE = 40.0;
		
		private int duration;
		private double ticketPrice;
		private double value;
		
		private Cost(int duration, double ticketPrice, double dailyHabitation) {
			this.duration = duration;
			this.ticketPrice = ticketPrice;
			value = 2 * this.ticketPrice + this.ticketPrice * dailyHabitation + this.duration * DAILY_ALLOWANCE;
		}
		
		private double getValue() {
			return value;
		}
	}

	public enum TripState {
		OPENED, DEPARTED, RETURNED, CLOSED;
	}
	
}