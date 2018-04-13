// Insert header here
package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Thread;

public class BookingClient {
	
	Map<String, Integer> offices;
	Theater theater;
	boolean noMoreRoom = false;
	int clientIDs = 0;
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
	
	
	private class BoxOffice implements Runnable{
		String id;
		int clients;
		
		@Override
		public void run() {
			if (!noMoreRoom) {
				for (int i = 0; i < clients; i++) {
					Theater.Seat seat = theater.bestAvailableSeat();
					if (seat == null) {
						noMoreRoom = true;
						System.out.println("Sorry, we are sold out! ");
						return;
					}
					clientIDs++;
					theater.printTicket(id, seat, clientIDs);
				}
			}
			
		}
		
		public BoxOffice(String name, int numClients) {
			id = name;
			clients = numClients;
		}
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Theater theater = new Theater(60, 1, "Ouija");
		//BX1=3, BX3=3, BX2=4, BX5=3, BX4=3
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("BX1", 15);
		map.put("BX2", 15);
		map.put("BX3", 12);
		map.put("BX4", 14);
		map.put("BX5", 16);
		BookingClient client = new BookingClient(map,theater);
		
		
		
		List<Thread> threads = client.simulate();
		  try {
		        for (Thread t : threads) {
		            t.join();
		        }
		    } catch (InterruptedException ie) {}
	}
	
	
}