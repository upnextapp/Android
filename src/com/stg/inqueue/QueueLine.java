package com.stg.inqueue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueLine {
	private String restaurantName;
	private Queue<String> queue;
	
	public QueueLine(String name) {
		restaurantName = name;
		queue = new LinkedList<String>();
	}
	
	public void add(String person) {
		queue.add(person);
	}
	
	public int size() {
		return queue.size();
	}
	
	public int getPosition(String name) {
		int position = 1;
		for (String n : queue)
			if (n != name)
				position++;
			else
				return position;
		return 0;
	}
	
	public String getName() {
		return this.restaurantName;
	}
}