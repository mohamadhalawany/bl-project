package com.bl;

public class ProblemSolving {

	private static String reverse(String input) {
		if (input == null || input.length() < 1 || input.length() > 2000) {
			System.err.println("It is null");
		}
		input = input.toLowerCase() ;
		StringBuilder out = new StringBuilder();

		char[] chr = input.toCharArray();

		for (int i = chr.length - 1; i >= 0; i--) {
			out.append(chr[i]);
		}
		return out.toString();
	}
	
	private static String reverseFirst() {
		String input = "abd(jnb)asdf" ; 
		String toReverse = input.substring(input.indexOf('(') + 1 , input.indexOf(')')) ;
    	String before = input.substring(0 , input.indexOf('(') + 1) ;
    	String reverseString = reverse(toReverse) ;
    	String after = input.substring(input.indexOf(')') , input.length()) ;
    	String output = before + reverseString + after ;
    	return output ;
	}
	
	private static String reverseSecond() {
		String input2 = "dd(df)a(ghhh)" ;
    	String before = input2.substring(0 , input2.indexOf('(') + 1) ;
    	String toReverse1 = input2.substring(input2.indexOf('(') + 1 , input2.indexOf(')')) ;
    	toReverse1 = reverse(toReverse1) ;
    	
    	String toReverse2 = input2.substring(input2.lastIndexOf("(") + 1 , input2.length() - 1) ;
    	toReverse2 = reverse(toReverse2) ;
    	
    	String middle = input2.substring(input2.indexOf(')') , input2.lastIndexOf('(') + 1) ;
    	
    	String output = before + toReverse1 + middle + toReverse2 + input2.substring(input2.length() - 1);
    	return output ;
	}
	
	public static void main(String[] args) {
    	System.err.println(reverseFirst()) ;
    	System.err.println(reverseSecond()) ;
	}
}
