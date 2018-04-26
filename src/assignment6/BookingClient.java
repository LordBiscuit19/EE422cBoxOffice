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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Thread;

public class BookingClient {
	
	Map<String, Integer> offices;
	Theater theater;	//the theater the booking client is buying tickets from
	boolean noMoreRoom = false;	//this is set to true if the theater is out of room
	boolean hasPrinted = false;	//this is set to true if the out of seats message has already printed so it isn't printed twice
	int clientIDs = 0;	//this is a number that represents how many clients have been served. it is incremented each time a ticket is printed
  /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
  public BookingClient(Map<String, Integer> office, Theater theater) {
	  offices = office;
	  this.theater = theater;
  }

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
	public List<Thread> simulate() {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (Map.Entry<String,Integer> entry : offices.entrySet()) {
			BoxOffice boxOffice = new BoxOffice(entry.getKey(), entry.getValue());
			Thread thread = new Thread(boxOffice);
			thread.start();
			threads.add(thread);
		}
		return threads;
	}
	
	
	/**
	 * The BoxOffice class simulates each individual box office
	 * Each BoxOffice makes ticket orders from the theater for all of its clients
	 */
	private class BoxOffice implements Runnable{
		String id;	//the id of the box office
		int clients;	//the number of clients the box office has
		
		
		/**
		 * prints the tickets for the clients of the box office 
		 */
		@Override
		public void run() {
			if (!noMoreRoom) {
				for (int i = 0; i < clients; i++) {
					
					synchronized (theater){ //synchronize on the theater so the threads don't interfere with each other
						Theater.Seat seat;
						seat = theater.bestAvailableSeat();
						if (seat == null) {
							noMoreRoom = true;	//if the theater is out of room notify the booking client so no more reservations are made
							if (!hasPrinted) {	//if the out of seats message has not been printed the print it
								hasPrinted = true;
								System.out.println("Sorry, we are sold out! ");
							}
							return;
						}
						else {
							clientIDs++;
							theater.printTicket(id, seat, clientIDs);
						}
					}
					
					try {
						Thread.sleep(0,1);
					} 
					catch (InterruptedException e) {}
					
				}
			}
			
		}
		
		/**
		 * constructor for the BoxOffice
		 * @param name	the box offices id number
		 * @param numClients	the number of clients the box office has
		 */
		public BoxOffice(String name, int numClients) {
			id = name;
			clients = numClients;
		}
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Theater theater = new Theater(500, 2, "Ouijajajaj");
		//BX1=3, BX3=3, BX2=4, BX5=3, BX4=3
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("BX1", 400);
		map.put("BX2", 601);
		//map.put("BX3", 200);
		//map.put("BX4", 200);
		//map.put("BX5", 400);
		BookingClient client = new BookingClient(map,theater);
		
		
		
		List<Thread> threads = client.simulate();
		  try {
		        for (Thread t : threads) {
		            t.join();
		        }
		    } catch (InterruptedException ie) {}
	}
	
	
}
