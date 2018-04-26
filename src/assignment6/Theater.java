/* MULTITHREADING <MyClass.java>
 * EE422C Project 6 submission by
 * Replace <...> with your actual data.
 * Donald Maze-England
 * dsm2588
 * 15465
 * Slip days used: <0>
 * Spring 2018
 */
package assignment6;

import java.util.ArrayList;
import java.util.List;

public class Theater {
	private int seatsPerRow;
	private int numRows;
	private String show;
	private ArrayList<Seat> seats = new ArrayList<Seat>();
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	
	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;
		private boolean reserved = false; //all seats start as not reserved

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		/**
		 * prints the seats row and seat number
		 */
		@Override
		public String toString() {
			int rowNumTemp = rowNum;
			String result = "";
			int remainder;
		    while (rowNumTemp > 0) {
		      rowNumTemp--; // 1 => a, not 0 => a
		      remainder = rowNumTemp % 26;
		      char digit = (char) (remainder + (int) 'A'); //Lettering starts at A
		      result = digit + result;
		      rowNumTemp = rowNumTemp - remainder;
		      rowNumTemp = rowNumTemp/26;
		    }

		    return result + Integer.toString(seatNum);		  
			
		}
	}

  /*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
		private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			seat.reserved = true; //whenever a tickets is created for a seat mark that seat as reserved
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		
		/**
		 * prints a text representation of a ticket purchased by a client to the console
		 */
		@Override
		public String toString() {
			//print the top row of the ticket
			String str = "";
			for (int i = 0; i < 31; i++) {
				str+="-";
			}
			//print the show
			str += "\n";
			String temp = "| Show: " + show;
			str += temp;
			for (int i = 0; i < (31 - temp.length())-1; i++) {
				str += " ";
			}
			//print the box office id
			str += "|\n";
			temp = "| Box Office ID: " + boxOfficeId;
			str += temp;
			for (int i = 0; i < (31 - temp.length())-1; i++) {
				str += " ";
			}
			//print the seat number
			str += "|\n";
			temp = "| Seat: " + seat.toString();
			str += temp;
			for (int i = 0; i < (31 - temp.length())-1; i++) {
				str += " ";
			}
			//print the client id
			str += "|\n";
			temp = "| Client: " + client;
			str += temp;
			for (int i = 0; i < (31 - temp.length())-1; i++) {
				str += " ";
			}
			//print the bottom of the ticket
			str += "|\n";
			for (int i = 0; i < 31; i++) {
				str+="-";
			}
			return str;
		}
	}

	
	/**
	 * Constructor for the theater
	 * @param numRows	the number of rows of seats the theater has
	 * @param seatsPerRow	the number of seats in each row the theater has
	 * @param show	the show the theater is currently playing
	 */
	public Theater(int numRows, int seatsPerRow, String show) {
		this.numRows = numRows;
		this.seatsPerRow = seatsPerRow;
		this.show = show;
		//make all the seats in the theater
		for (int i = 1; i <= numRows; i++) {
			for (int j = 1; j <= seatsPerRow; j++){
				seats.add(new Seat(i,j));
			}
		}	
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		for (Seat s : seats) {
			if (!s.reserved) {
				return s;
			}
		}
		//if no seats available return null
		return null;
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		if (seat == null) {
			return null;
		}
		else {
			Ticket ticket = new Ticket(show, boxOfficeId, seat, client);
			System.out.println(ticket.toString());
			tickets.add(ticket);
			return ticket;
		}
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		return tickets;
	}
}
