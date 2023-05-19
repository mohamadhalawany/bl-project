package com.bl;

import org.springframework.web.multipart.MultipartFile;

public class CustomValidation {

	public static int validatePictureExtension(String extension) {
		if(!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png") && !extension.equals("webp")) {
			return 0 ;
		}else {
			return 1 ;
		}
	}
	
	public static int validateItemLogoSize(MultipartFile logo) {
		int result = 0 ;
		String value = HelperUtils.readFromRootBundle("UPLOAD_PICTURE_SIZE") ;
		if(value != "File not found, try another file" || value != "Key not found, try another key") {
			long size = Long.parseLong(value) ;
			if(logo.getSize() > size) {
				result = 0 ;
			}else {
				result = 1 ;
			}
		}
		return result ;
	}
	
	
	// Default value of  pictureSize is 10 * 1024 * 1024 which equals the value in application.properties file with the given key
	public static boolean validatePictureSize(MultipartFile picture) {		
		long pictureSize = 10 * 1024 * 1024  ;
		if(picture.getSize() > pictureSize) {
			System.err.println(picture.getSize() + " ======== if picture.getSize()");
			return false ;
		}else {
			System.err.println(picture.getSize() + " ======== else picture.getSize()");
			return true ;
		}
		
	}
}
