package com.bl.test;

import java.util.function.BiFunction;

public class TestingClass {
	
	private static String SALUTE = "SALUTE you both " ;
	
	public static void main(String[] args) {
//		TestingClass test = new TestingClass() ;
//		
//		MathInterface add = (int a , int b) -> a + b ;
//		MathInterface sub = (a , b) -> a - b ;
//		MathInterface mult = (a , b) -> { return a * b ; } ;
//		
//		System.err.println("ADD 5 + 10 ==== " + test.operate(5 , 10 , add)) ;
//		System.err.println("SUB 5 - 10 ==== " + test.operate(5 , 10 , sub)) ;
//		System.err.println("MULT 5 * 10 ==== " + test.operate(5 , 10 , mult)) ;
//		
//		GreetingInterface greeting1 = msg -> System.err.println("HELLO " + msg) ;
//		GreetingInterface greeting2 = (message) -> System.err.println("HI " + message) ;
//		
//		greeting1.say("MENNA") ;
//		greeting2.say("NOUR") ;
//		
//		greeting1 = msg -> System.err.println((SALUTE + msg)) ;
//		greeting1.say("MENNA and NOUR ") ;
		
		Sayable s = TestingClass::saySomething ;
		s.say();
		
//		BiFunction<Integer , Integer , Integer> adder = TestingClass::add ;
//		System.err.println(adder.apply(10 , 20)) ;
	}

	private int operate(int a, int b, MathInterface mathOperation) {
		return mathOperation.operation(a, b);
	}
	
	private static int saySomething() {
		System.err.println("I LOVE MENNA") ;
		return 0 ;
	}
	
	private static int add(int a , int b) {
		return a + b ;
	}
}
