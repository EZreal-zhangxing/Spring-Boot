package com.zx.WebSocket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class test {
	public static void main(String[] args) {
		System.out.println(Math.max(1, 2));
		List<String> list=new ArrayList<String>();
//		list.get(index)
		System.out.println("here");
		LinkedList linklist=new LinkedList();
		linklist.add("1");
		linklist.add("2");
		linklist.add("3");
		Iterator it=linklist.iterator();
		while(it.hasNext()){
			System.out.println((String)it.next());
		}
	}
}
