package com.bl.test;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {
	
	// filtering data 
	// fetching price  
	// collecting as list  
	private static List<Product> productList(){
		List<Product> list = new ArrayList<Product>() ;
		list.add(new Product(1 , "Toyota Corolla" , 300000)) ;
		list.add(new Product(1 , "VW Passat" , 350000)) ;
		list.add(new Product(1 , "Mercedes C280" , 4000000)) ;
		list.add(new Product(1 , "BNW X6" , 4500000)) ;
		
		return list ;
	}
	private static void filterFetchCollect() {
		List<Product> list = productList() ;
		list.add(new Product(1 , "Toyota Corolla" , 300000)) ;
		list.add(new Product(1 , "VW Passat" , 350000)) ;
		list.add(new Product(1 , "Mercedes C280" , 4000000)) ;
		list.add(new Product(1 , "BNW X6" , 4500000)) ;
		
		List<Long> priceList = list.stream().filter(x -> x.getPrice() < 400000) // filtering price to fetch only < 400000 
				.map(p -> p.getPrice())				// after filter, fetching price  
				.collect(Collectors.toList()) ;		// then collect the prices to a list
		
		System.err.println(priceList) ;
	}
	
	
	private static void iterate() {
		Stream.iterate(1 , element -> element + 1)
		.filter(ele -> ele % 5 == 0).limit(5) 
		.forEach(System.out::println);		
	}
	
	private static void filterIterateCollection() {
		List<Product> list = productList() ;
		list.stream().filter(x -> x.getPrice() > 400000)
		.forEach(x -> System.err.println(x.getName())) ;
	}
	
	
	private static void reduce() {
		List<Product> list = productList() ;
		long total = list.stream().map(x -> x.getPrice()).reduce(0l , Long :: sum) ;
		long sum = list.stream().map(x -> x.getPrice()).
				reduce(0l , (su , price) -> su +  price) ;
		long toalPrice = list.stream().collect(Collectors.summingLong(x -> x.getPrice())) ;
		Product max = list.stream().max((x1 , x2) -> x1.getPrice() < x2.getPrice() ? 1 : -1).get() ;  	// min is the same or change the boolean expression to less "<"	
		long count = list.stream().count() ;					// you can add filter to add any condition like filter(x -> x.getPrice > 400000)
		System.err.println(count) ;
		
	}
	
	
	public static void main(String[] args) {
		reduce() ;
	}

}
