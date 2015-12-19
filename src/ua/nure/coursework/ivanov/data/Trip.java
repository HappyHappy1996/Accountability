package ua.nure.coursework.ivanov.data;

public class Trip {
	
	private Person person;
	private String destination;
	private String date;
	private Cost expectedCost;
	private Cost actualCost;
	private double balance;
	private int id = -1;
	private TripState state;
	
	public Trip(Person person, String destination, String date) {
		this.person = person;
		this.destination = destination;
		this.date = date;
		expectedCost = null;
		actualCost = null;
		balance = 0;
		state = TripState.OPENED;
	}
	
	public Trip(Person person, String destination, String date, int id) {
		this.person = person;
		this.destination = destination;
		this.date = date;
		this.id = id;
		expectedCost = null;
		actualCost = null;
		balance = 0;
		state = TripState.OPENED;
	}
	
	public Trip(Person person, String destination, String date,
			int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation) {
		this.person = person;
		this.destination = destination;
		this.date = date;
		expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
		actualCost = null;
		balance = 0;
		state = TripState.DEPARTED;
	}
	
	public Trip(Person person, String destination, String date,
			int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation, int id) {
		this.person = person;
		this.destination = destination;
		this.date = date;
		this.id = id;
		expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
		actualCost = null;
		balance = 0;
		state = TripState.DEPARTED;
	}
	
	public Trip(Person person, String destination, String date,
			int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation,
			int actualDuration, double actualTicketPrice, double actualDailyHabitation) {
		this.person = person;
		this.destination = destination;
		this.date = date;
		expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
		actualCost = new Cost(actualDuration, actualTicketPrice, actualDailyHabitation);
		balance = expectedCost.getValue() - actualCost.getValue();
		state = TripState.RETURNED;
	}
	
	public Trip(Person person, String destination, String date,
			int expectedDuration, double expectedTicketPrice, double expectedDailyHabitation,
			int actualDuration, double actualTicketPrice, double actualDailyHabitation, int id) {
		this.person = person;
		this.destination = destination;
		this.date = date;
		this.id = id;
		expectedCost = new Cost(expectedDuration, expectedTicketPrice, expectedDailyHabitation);
		actualCost = new Cost(actualDuration, actualTicketPrice, actualDailyHabitation);
		balance = expectedCost.getValue() - actualCost.getValue();
		state = TripState.RETURNED;
	}
	
	public void setId(int id) {
		if (id > 0) {
			this.id = id;
		}
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(person);
		builder.append(", destination=");
		builder.append(destination);
		builder.append(", date=");
		builder.append(date);
		
		switch (state) {
			
		case DEPARTED:
			builder.append(", expected");
			builder.append(expectedCost);
			break;
			
		case RETURNED:
			builder.append(", expected");
			builder.append(expectedCost);
			builder.append(", actual");
			builder.append(actualCost);
			builder.append(", balance=");
			builder.append(balance);
			break;

		case CLOSED:
			builder.append(", balance=");
			builder.append(balance);
			break;
		}
		
		
		return builder.toString();
	}

	public class Cost {
		
		private static final double DAILY_ALLOWANCE = 40.0;
		
		private int duration;
		private double ticketPrice;
		private double dailyHabitation;
		private double value;
		
		private Cost(int duration, double ticketPrice, double dailyHabitation) {
			this.duration = duration;
			this.ticketPrice = ticketPrice;
			this.dailyHabitation = dailyHabitation;
			value = 2 * this.ticketPrice + this.duration * dailyHabitation + this.duration * DAILY_ALLOWANCE;
		}
		
		private double getValue() {
			return value;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Cost [duration=");
			builder.append(duration);
			builder.append(", ticketPrice=");
			builder.append(ticketPrice);
			builder.append(", dailyHabitation=");
			builder.append(dailyHabitation);
			builder.append("]");
			return builder.toString();
		}
		
	}

	public enum TripState {
		OPENED, DEPARTED, RETURNED, CLOSED;
	}

	public Person getPerson() {
		return person;
	}

	public String getDestination() {
		return destination;
	}

	public String getDate() {
		return date;
	}

	public Cost getExpectedCost() {
		return expectedCost;
	}

	public int getExpectedCostDuration() {
		return expectedCost.duration;
	}
	
	public double getExpectedCostTicketPrice() {
		return expectedCost.ticketPrice;
	}
	
	public double getExpectedCostDailyHabitation() {
		return expectedCost.dailyHabitation;
	}
	
	public Cost getActualCost() {
		return actualCost;
	}
	
	public int getActualCostDuration() {
		return actualCost.duration;
	}
	
	public double getActualCostTicketPrice() {
		return actualCost.ticketPrice;
	}
	
	public double getActualCostDailyHabitation() {
		return actualCost.dailyHabitation;
	}
	
	public double getBalance() {
		return balance;
	}

	public TripState getState() {
		return state;
	}

	public int getId() {
		return id;
	}

}