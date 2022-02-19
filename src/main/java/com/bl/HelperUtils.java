package com.bl;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;

import com.bl.dto.ItemsDTO;

public class HelperUtils {

	private static ModelMapper modelMapper = new ModelMapper();

    public static <T> T convertEntityToDto(Object entity, Class<T> dtoClass) {
	if (entity != null)
	    return modelMapper.map(entity, dtoClass);
	else
	    return null;
    }

    public static <T> T convertDtoToEntity(Object dto, Class<T> entityClass) {
	modelMapper.getConfiguration().setAmbiguityIgnored(true);
	if (dto != null)
	    return modelMapper.map(dto, entityClass);
	else
	    return null;
    }
    
    
    
    public static int validateMail(String mail) throws Exception{
    	int i = 0 ;
    	String beforeAt = mail.substring(0 , mail.indexOf("@")) ;
		String toDot = mail.substring(0 , mail.lastIndexOf(".")) ;
		mail.substring(toDot.length() , mail.length() - 1) ;
		if(beforeAt.equals("")) {
			i = 2 ;
		}
		return i ;
    }
    
    
    
    
    public static String getValueFromBundle(String key , int language) {
        String res = " ";
        String languagePrefix = "ar" ;
        if(language == 2) {
        	languagePrefix = "en" ;
        }
        
        try {
            FileReader reader = new FileReader("D:\\Private\\Work\\bl\\bl\\src\\main\\java\\messages_" + languagePrefix + ".properties") ;
            Properties p = new Properties();
            p.load(reader);
            res = p.getProperty(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

	private static String decode(String text) {		
		byte[] actualByte = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8)) ;	
		String result = new String(actualByte);
		return result ;
	}
	
	
	private static String encode(String text) {
		String result = new String(Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8))) ;
		return result ;
	}
    
	
	
	public static String encrypt(String text) {		
    	for(int i = 0 ; i < 14 ; i++) {
    		text = encode(text) ;
    	}
    	return text ;
	}
	
	
	public static String decrypt(String text) {		
    	for(int i = 0 ; i < 14 ; i++) {
    		text = decode(text) ;
    	}
    	return text ;
	} 
	
	
	
	public static void main(String args[])
	{
	
		
//	Arrays.stream(ar).forEach(x -> System.err.println(x + " ")) ;
	
//	Arrays.parallelSort(ar);
//	Arrays.stream(ar).forEach(x -> System.err.print(x + " ")) ;
//	for(String str : ar)
//	System.out.print(str  + "");
//	System.out.println(Arrays.binarySearch(ar , "b"));
	
	
	
	}

}
