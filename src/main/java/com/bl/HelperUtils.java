package com.bl;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;

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
	
	
	
    public static void main(String[] args) {
//    	String encodePassword = "123"  ;
//    	for(int i = 0 ; i < 19 ; i++) {
//    		encodePassword = encode(encodePassword) ;
//    	}
//		System.err.println(encodePassword) ; 
		
//		String decodePassword = "Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTVjAxV2JETlhhMUpUVmpBeFYySkVUbGhoTVVwVVZtcEJlRll5U2tWVWJHaG9UVlZ3VlZacVFtRlRNbEpJVm10a1dHSkdjSEJXYTFwaFpWWmFkR1ZHV214U2JHdzFWa2QwYzJGc1NuUmhSemxWVm0xb1JGWldXbXRXTVhCRlZXeFNUbUY2UlRCV01uUnZVakZXZEZOclpGaGlSMmhoV1ZSS2IxSkdXbGRYYlhSWFRWaENSbFpYZUZOVWJVWTJVbFJDVjAxdVVuWlZha1pYWkVaT2NscEdhR2xTTW1ob1YxWlNTMkl4U2tkWGJHUllZbFZhY1ZadGRHRk5SbFowWlVaT1ZXSlZXVEpWYkZKSFZqRmFSbUl6WkZkaGExcG9WakJhVDJOdFJraGhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJGWmhZMnhXY1ZGVVJsTk5XRUpIVmpKNFQxWlhTa2RqUmxwWFlsaFNNMVpxU2t0V1ZrcFpXa1prYUdFeGNGbFhhMVpoVkRKTmVGcElUbWhTTW5oVVZGY3hiMWRzV1hoYVJGSnBUV3RzTlZadE5VOVdiVXBIVjJ4U1dtSkdXbWhaTVZwaFpFZFNTRkpyTlZOaVJtOTNWMnhXWVZReFdsaFRiRnBxVWxkU1lWUlZXbUZOTVZweFUydDBWMVpyY0ZwWGExcDNZa2RGZWxGcmJGaFhTRUpJVmtSS1UxWXhXblZVYkdocFZqTm9WVlpHWTNoaU1XUkhWMjVTVGxaRlNsaFVWM2hIVGxaYVdFNVZPV2hpUlhBd1ZsZDRjMWR0U2tkWGJXaGFUVzVvV0ZsNlJsZGpiSEJIVlcxc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVVVW14d2VGVXlkR0ZpUmxwelYyeHdXR0V4Y0ROWmEyUkdaV3hHY21KR1pHbFhSVXBKVm10U1MxVXhXWGhYYmxaVllrZG9jRlpxVG05V1ZscEhXVE5vYVUxWFVraFdNalZUVkd4YVJsTnNhRlZXYlZFd1ZqQmFZVmRIVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp0ZEd0V2JrSkhWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0=" ;
//		for(int i = 0 ; i < 19 ; i++) {
//			decodePassword = decode(decodePassword) ;
//    	}
//		System.err.println(encrypt("123"));
//		System.err.println(decrypt("Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTVjAxV2JETlhhMUpUVmpBeFYySkVUbGhoTVVwVVZtcEJlRll5U2tWVWJHaG9UVlZ3VlZacVFtRlRNbEpJVm10a1dHSkdjSEJXYTFwaFpWWmFkR1ZHV214U2JHdzFWa2QwYzJGc1NuUmhSemxWVm0xb1JGWldXbXRXTVhCRlZXeFNUbUY2UlRCV01uUnZVakZXZEZOclpGaGlSMmhoV1ZSS2IxSkdXbGRYYlhSWFRWaENSbFpYZUZOVWJVWTJVbFJDVjAxdVVuWlZha1pYWkVaT2NscEdhR2xTTW1ob1YxWlNTMkl4U2tkWGJHUllZbFZhY1ZadGRHRk5SbFowWlVaT1ZXSlZXVEpWYkZKSFZqRmFSbUl6WkZkaGExcG9WakJhVDJOdFJraGhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJGWmhZMnhXY1ZGVVJsTk5XRUpIVmpKNFQxWlhTa2RqUmxwWFlsaFNNMVpxU2t0V1ZrcFpXa1prYUdFeGNGbFhhMVpoVkRKTmVGcElUbWhTTW5oVVZGY3hiMWRzV1hoYVJGSnBUV3RzTlZadE5VOVdiVXBIVjJ4U1dtSkdXbWhaTVZwaFpFZFNTRkpyTlZOaVJtOTNWMnhXWVZReFdsaFRiRnBxVWxkU1lWUlZXbUZOTVZweFUydDBWMVpyY0ZwWGExcDNZa2RGZWxGcmJGaFhTRUpJVmtSS1UxWXhXblZVYkdocFZqTm9WVlpHWTNoaU1XUkhWMjVTVGxaRlNsaFVWM2hIVGxaYVdFNVZPV2hpUlhBd1ZsZDRjMWR0U2tkWGJXaGFUVzVvV0ZsNlJsZGpiSEJIVlcxc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVVVW14d2VGVXlkR0ZpUmxwelYyeHdXR0V4Y0ROWmEyUkdaV3hHY21KR1pHbFhSVXBKVm10U1MxVXhXWGhYYmxaVllrZG9jRlpxVG05V1ZscEhXVE5vYVUxWFVraFdNalZUVkd4YVJsTnNhRlZXYlZFd1ZqQmFZVmRIVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp0ZEd0V2JrSkhWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="));
    	
    	
//    	Pattern p = Pattern.compile("[01]* aJAVA") ;
//    	Matcher m = p.matcher("a01JAVA") ;
//    	System.err.println(m.matches());
    	
//    	LocalDate ld = LocalDate.parse("2018-04-30" , DateTimeFormatter.ISO_LOCAL_DATE) ;
//    	ld.plusDays(2) ;
//    	ld.pl
    	
    }
}
