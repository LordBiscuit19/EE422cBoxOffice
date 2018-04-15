// insert header here
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
		private boolean reserved = false;

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

		@Override
		public String toString() {
			int rowNumTemp = rowNum;
			int remainder;
			String rowNumStr = "";
			//This while loop should convert the rowNum to an alphabetical representation
			if (rowNumTemp == 26) {
				rowNumTemp = 26;
			}

			while (rowNumTemp >= 27) {
				remainder = rowNumTemp % 26;
				rowNumStr += (char) (remainder + (int) 'A');
				rowNumTemp = rowNumTemp/26;
			}
			while (rowNumTemp > 0) {
				remainder = rowNumTemp % 26;
				rowNumStr += (char) (remainder + (int) 'A'- 1);
				rowNumTemp = rowNumTemp/26;
			}			
			StringBuilder sb = new StringBuilder(rowNumStr);
			return sb.reverse() + Integer.toString(seatNum);
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
			seat.reserved = true;
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

		@Override
		public String toString() {
			String str = "";
			for (int i = 0; i < 30; i++) {
				str+="-";
			}
			str += "\n";
			String temp = "| Show: " + show;
			str += temp;
			for (int i = 0; i < (30 - temp.length())-1; i++) {
				str += " ";
			}
			str += "|\n";
			temp = "| Box Office ID: " + boxOfficeId;
			str += temp;
			for (int i = 0; i < (30 - temp.length())-1; i++) {
				str += " ";
			}
			str += "|\n";
			temp = "| Seat: " + seat.toString();
			str += temp;
			for (int i = 0; i < (30 - temp.length())-1; i++) {
				str += " ";
			}
			str += "|\n";
			temp = "| Client: " + client;
			str += temp;
			for (int i = 0; i < (30 - temp.length())-1; i++) {
				str += " ";
			}
			str += "|\n";
			for (int i = 0; i < 30; i++) {
				str+="-";
			}
			return str;
		}
	}

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
		//if no seats avaliable return null
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
		Ticket ticket = new Ticket(show, boxOfficeId, seat, client);
		System.out.println(ticket.toString());
		tickets.add(ticket);
		return ticket;
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
