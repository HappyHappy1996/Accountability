package ua.nure.coursework.ivanov.data;

public class Person {
	
	private String name;
	private String appointment;
	
	public Person(String name, String appointment) {
		this.name = name;
		this.appointment = appointment;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [name=");
		builder.append(name);
		builder.append(", appointment=");
		builder.append(appointment);
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public String getAppointment() {
		return appointment;
	}

}
