package com.bl;

public class DomainValues {
	
	public static final class OrderStatus{
		public static final Integer ADD_TO_CART = 1 ;
		public static final Integer ORDERED = 2 ;
		public static final Integer ACCEPTED = 3 ;
		public static final Integer IN_THE_WAY = 4 ;
		public static final Integer DELIVERED = 5 ;
		public static final Integer REJECTED_ADMIN = 6 ;
		public static final Integer REFUESED_BY_CUSTOMER = 7 ;
		public static final Integer CANCELLED = 8 ;
		public static final Integer ADD_ITEM = 9 ;
		public static final Integer DELETE_ITEM = 10 ;
		public static final Integer ORDER_EXPIRE = 11 ;
	}
	
	
	public static final class BlockReason{
		public static final Integer CRIMINAL = 1 ;
		public static final Integer MORAL = 2 ;
		public static final Integer LACK_COMMITMENT = 3 ;
	}
	
	
	public static final class SessionKeys{
		public static final String USER = "user" ;
		public static final String CUSTOMER = "customer" ;
	}
	
	
	public static final class Paths{
		public static final String UPLOAD_ITEM_LOGO_PATH = "E:/UPLOADS/ITEMS_LOGO" ;
		public static final String UPLOAD_BRANDS_LOGO_PATH = "E:/UPLOADS/BRANDS_LOGO" ;
		public static final String UPLOAD_MAIN_PAGE_PATH = "E:/UPLOADS/MAIN_PAGE" ;
		public static final String UPLOAD_COMPANY_LOGO_PATH = "E:/UPLOADS/LOGO" ;
		public static final String BUNDLE_PATH = "E:\\Work\\bl\\src\\main\\java" ;
	}
}
