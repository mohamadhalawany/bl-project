package com.bl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

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
    
    
    
//    public static int validateMail(String mail) throws Exception{
//    	int i = 0 ;
//    	String beforeAt = mail.substring(0 , mail.indexOf("@")) ;
//		String toDot = mail.substring(0 , mail.lastIndexOf(".")) ;
//		mail.substring(toDot.length() , mail.length() - 1) ;
//		if(beforeAt.equals("")) {
//			i = 2 ;
//		}
//		return i ;
//    }
//    
    
    
    
    public static String getValueFromBundle(String key , int language) {
        String res = " ";
        String languagePrefix = "ar" ;
        if(language == 2) {
        	languagePrefix = "en" ;
        }
        
        try {
            FileReader reader = new FileReader(DomainValues.Paths.BUNDLE_PATH + "\\messages_" + languagePrefix + ".properties") ;
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
	
	
	
	public static void upload(String name, MultipartFile file) {
		if (!file.isEmpty()) {
		    try {
		    	byte[] fileBytes = file.getBytes();

		    	// THE SERVER'S PATH
		    	String path = DomainValues.Paths.UPLOAD_ITEM_LOGO_PATH ;

		    	// CREATE FOLDER TO SAVE UPLOADED FILES.
				File dir = new File(path);
				if (!dir.exists()) {
				    dir.mkdirs();
				}

				// CREATE FILE ON SERVER
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serverFile));
				bos.write(fileBytes);
				bos.close();
		    }catch (Exception e) {
		    	e.printStackTrace();
		    }
		}else{
		    System.out.println("FAILD TO UPLOAD THE FILE");
		}
	}
	
	
	// path = 1 upload Brand Logo
	// path = 2 upload Main Page images
	public static void uploadImages(int path , String name, MultipartFile file) {
		if (!file.isEmpty()) {
		    try {
		    	byte[] fileBytes = file.getBytes();

		    	// THE SERVER'S PATH
		    	String uploadPath = null ;
		    	
		    	if(path == 1) {
		    		uploadPath = DomainValues.Paths.UPLOAD_BRANDS_LOGO_PATH ;
		    	}else if(path == 2) {
		    		uploadPath = DomainValues.Paths.UPLOAD_MAIN_PAGE_PATH ;
		    	}else if(path == 3) {
		    		uploadPath = DomainValues.Paths.UPLOAD_COMPANY_LOGO_PATH ;
		    	}

		    	// CREATE FOLDER TO SAVE UPLOADED FILES.
				File dir = new File(uploadPath);
				if (!dir.exists()) {
				    dir.mkdirs();
				}

				// CREATE FILE ON SERVER
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serverFile));
				bos.write(fileBytes);
				bos.close();
		    }catch (Exception e) {
		    	e.printStackTrace();
		    }
		}else{
		    System.out.println("FAILD TO UPLOAD THE FILE");
		}
	}
	
	public static int dateBeforeDate(String from , String to) {		
		int before = 1 ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
		try {
			Date dateFrom = sdf.parse(from) ;
			Date dateTo = sdf.parse(to) ;
			Long diff = dateFrom.getTime() - dateTo.getTime() ;
			if(diff <= 0) {
				before = 0 ;				// before(tomorrow) TO, by another words FROM is less than TO
			}else {
				before = 1 ;			    // after(yesterday) TO by another words FROM is greater than TO
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		EntityManager em = EntityManagerHelper.open() ;
//		String sql = "SELECT DATEDIFF('" + from +  "', '" + to + "') AS DateDiff from DUAL" ;
//		Query query = em.createNativeQuery(sql) ;
//		Object objs = query.getSingleResult() ;
//		if(objs != null) {
//			Long diff = Long.parseLong(objs.toString()) ;
//			if(diff != null && diff > 0) {
//				before = 0 ;
//			}
//		}
		return before ;
	}
	
	public static Long dateDifferencesInDays(Date date) {
		Long days = null ;
		Long diff = null ;
		diff = new Date().getTime() - date.getTime() ;
		days = TimeUnit.MILLISECONDS.toDays(diff) ;
		return days ;
	}
	
	public static Date convertStringToDate(String from) {
		Date date = new Date() ;
		int year = Integer.parseInt(from.substring(0 , 4)) ;
		int month = Integer.parseInt(from.substring(5 , 7)) ;
		int day = Integer.parseInt(from.substring(8 , from.length())) ;
		date.setYear(year - 1900) ;
		date.setMonth(month - 1) ;
		date.setDate(day) ;
		return date ;
	}
//	
	
	static void allZones() {
		Set<String> allZoneIds = ZoneId.getAvailableZoneIds();
		Consumer<String> con = new Consumer<String>() {
			public void accept(String t) {
				System.err.println(t);
			};
		} ;
		allZoneIds.forEach(con);
	}
	
	
	public static Locale systemDefaultLanguage() {
		return Locale.getDefault() ;
	}
	
	public static String currentZone() {
		return ZoneId.systemDefault().getId() ;
	}
	
	public static String defaultCountry() {
		return Locale.getDefault().getDisplayCountry() ;
	}
	
	public static boolean isAfter(String date) {
		return LocalDate.parse(date).isAfter(LocalDate.now()) ; 
	}
	
	public static void systemEnvironmentsKeysValues() {
		Map<String , String> map = System.getenv() ;
		BiConsumer<String , String> con = new BiConsumer<String , String>() {
			@Override
			public void accept(String key , String value) {
				System.err.println(key + " KEY , " + value + " VALUE");
			}
		};
		map.forEach(con);
	}
	
	public static boolean validatEmail(String emailAddress) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		boolean valid = Pattern.compile(regexPattern).matcher(emailAddress).matches();
		return valid ;
	}
	
	
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a") ;
		return sdf.format(date) ;
	}
	
	
	/*Returns 0 if the key not found.
		Returns -1 if the file not found.
		Else it returns the value of the given key.
	*/
	public static String readFromRootBundle(String key) {
		String value = null ;
		try {
			InputStream is = HelperUtils.class.getResourceAsStream("/applications.properties") ;			
			if(is != null) {
				Properties prop = new Properties() ;
				prop.load(is) ;
				if(prop.get(key) == null) {
					value = "0" ;
					System.err.println(value) ;
				}else {
					value = (String) prop.get(key) ;
					System.err.println(value) ;					
				}
			}else {
				value = "-1" ;
				System.err.println(value) ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		return value ;
	}
	
	public static void main(String args[]) throws Exception{
		String value = readFromRootBundle("UPLOAD_PICTURE_SIZE") ;
		if(value == "File not found, try another file") {
			System.err.println("done");
		}
	}

}
