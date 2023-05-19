package com.bl;

import java.util.ArrayList; 
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MyClass {
    
    public static void main(String[] args) {
//    	String input = "abd(jnb)asdf" ;
//    	String x = input.substring(input.indexOf('(') + 1 , input.indexOf(')')) ;
//    	String before = input.substring(0 , input.indexOf('(') + 1) ;
//    	String reverse = reverse("mohamad") ;
//    	String after = input.substring(input.indexOf(')') , input.length()) ;
//    	System.err.println(reverse) ;
//    	System.err.println(before + reverse + after) ;
		int[] array = {1 ,2 , 4 , 6 , 3} ;
		System.err.println(secondHighest(array)) ;
		System.err.println(heighestNumber(array));
    }

    private static String reverse(String input) {
	if(input == null) {
	    System.err.println( "It is null");
	}
	
	StringBuilder out = new StringBuilder() ;
	
	char[] chr = input.toCharArray() ;
	
	for(int i = chr.length - 1 ; i >= 0 ; i--) {
	    out.append(chr[i]) ;
	}
	return out.toString() ;
    }
    
    private static void streams() {
	List<Integer> list = new ArrayList<>() ;
	for(int i = 0 ; i < 100 ; i++) {
	    list.add(i) ;
	}
	
	Stream<Integer> seq = list.stream() ;
	Stream<Integer> highSeq = seq.filter(p -> p > 90) ;
	highSeq.forEach(p -> System.err.println("High SEQ is ======= " + p)) ;
	
	Stream<Integer> par = list.parallelStream() ;
	Stream<Integer> highPar = par.filter(x -> x > 90) ;
	highPar.forEach(x -> System.err.println("High PAR is <><><><><><> " + x) );
    }
    
    private static void swap() {
	int a = 10 ;
	int b = 20 ;
	
	a = a + b ;  // 10 + 20 = 30
	b = a - b ; // 30 - 20 = 10
	a = a - b ; // 30 - 10 = 20
	System.err.println("A is " + a + " ========== B is " + b);
    }
    
    
    private static void vowel (String in) {
	boolean b = in.toLowerCase().matches(".*[aeiou].*") ;
	System.err.println(b);
    }
    
    private static boolean isPrime(int n) {
	if (n == 0 || n == 1) {
	    return false;
	}
	if (n == 2) {
	    return true;
	}
	for (int i = 2; i <= n / 2; i++) {
	    System.out.println(i + " IIII");
	    System.out.println( n / 2 + " NNN");
	    if (n % i == 0) {
		
		
		return false;
	    }
	}
	return true;
    }
    
    private static void fibonacci(int count) {
	int a = 0 ;
	int b = 1 ;
	int c = 1 ;
	
	for(int i = 1 ; i <= count ; i++) {
	    System.err.print(c + " , ") ;	// 1) a = 0  || 2) a = 1
	    a = b ;  		// 1) a = 1  || 2) a = 1  || 3) a = 2 
	    b = c ;			// 1) b = 1  || 2) b = 2  || 3) b = 3
	    c = a + b ;		// 1) c = 2   || 2) c = 3  || 3) c = 5
	}
    }
    
    
    private static void odds(List<Integer> numbers) {
//	for(int i : numbers) {
//	    if(i % 2 != 0) {
//		System.err.println(i) ;
//	    }
//	}
	numbers.parallelStream().anyMatch(x -> x % 2 != 0) ;
    }
    
    
    private static void removeWhiteSpaces(String s) {
	StringBuilder sb = new StringBuilder() ;
	char[] c = s.toCharArray() ;
	for(char x : c) {
	    if(!Character.isWhitespace(x)) {
		sb.append(x) ;
	    }
	}
	System.err.println(sb.toString()) ;
    }
    
    private static void removeLeadingTrailing(String s) {
	System.err.println(s.strip()) ;	
    }
    
    private static long factorial(long n) {
	return n*  factorial(n - 1)  ;
    }
    
    
    private static void reverseLinkedList() {
	LinkedList<Integer> ll1 = new LinkedList<>() ;
	ll1.add(1) ;
	ll1.add(2) ;
	ll1.add(3) ;
	LinkedList<Integer> ll2 = new LinkedList<>() ;
	ll1.descendingIterator().forEachRemaining(ll2::add) ;
	System.err.println(ll2);
    }
    
    
    private static void printPyramid(String s , int times) {
	for(int i = 0 ; i < times ; i++) {
	    System.err.println(s) ;
	}
    }
    
    
    private static void printPattern(int rows) {
	for(int i = 1 ; i < rows ; i++) {
	    int whiteSpaces = rows - i ;
	    printPyramid(" " , whiteSpaces) ;
	    
	    printPyramid(i + " " , i) ;
	    
	    System.err.println("");
	}
    }
    
    
	private static int secondHighest(int[] array) {
		int highest = Integer.MIN_VALUE;
		int secondHighest = Integer.MIN_VALUE;
		for (int i : array) {
			if (i > highest) {
				secondHighest = highest;
				highest = i;
			} else if (i > secondHighest) {
				secondHighest = i;
			}
		}
		return secondHighest;
	}
	
	private static int heighestNumber(int[] array) {
		int heighest = Integer.MIN_VALUE ;
		for(int i : array) {
			if(i > heighest) {
				heighest = i ;
			}
		}
		return heighest ;
	}
}
