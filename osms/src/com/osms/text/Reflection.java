package com.osms.text;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import com.osms.entity.Academy;
import com.osms.entity.Major;
import com.osms.entity.Users;
import com.osms.utils.Utils;

public class Reflection {

	@Test
	public void test() {
		Academy academy=new Academy(1,"电信学院","CS",1);
		Users user=new Users(0, "Student", 1, 1, "17855833079", 
				"2539676270@qq.com", Utils.toMD5("123456"), Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
				4, null);
		
		Major major=new Major(0, "cName", "eName", 0);
		getFieldValueMap(major);
//		Class<?> clazz=academy.getClass();
//		String[] colums={"academyId","cName","eName","status"};
//		for(String c:colums)
//		{
//			try {
//				Field field=clazz.getDeclaredField(c);
//				PropertyDescriptor pd=new PropertyDescriptor(field.getName(), clazz);
//				Method getMethod=pd.getReadMethod();
//				if(getMethod.invoke(academy)!=null)
//				{
//					System.out.println(field.getName()+" ,  "+getMethod.invoke(academy));
//				}
//			} catch (NoSuchFieldException | SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IntrospectionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	   public void getFieldValueMap(Object bean) {  
	        Class<?> cls = bean.getClass();  
	        Method[] methods = cls.getDeclaredMethods();  
	        Field[] fields = cls.getDeclaredFields();  
	        for (Field field : fields) {  
	            try {  
	                String fieldType = field.getType().getSimpleName();  
	                String fieldGetName = parGetName(field.getName());  
	                if (!checkGetMet(methods, fieldGetName)) {  
	                    continue;  
	                }  
	                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});  
	                Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});  
	                String result = null;  
	                if ("Date".equals(fieldType)) {  
	                    result = fmtDate((Date) fieldVal);  
	                } else {  
	                    if (null != fieldVal) {  
	                        result = String.valueOf(fieldVal);  
	                    }  
	                }  
//	              String fieldKeyName = parKeyName(field.getName());  
	              System.out.println(field.getName()+"  ,  "+result);
	            } catch (Exception e) {  
	                continue;  
	            }  
	        }  
	    }




   public static boolean checkGetMet(Method[] methods, String fieldGetMet) {  
	        for (Method met : methods) {  
	            if (fieldGetMet.equals(met.getName())) {  
	                return true;  
	            }  
	        }  
	        return false;  
	    }  



    public static String parGetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "get"  
                + fieldName.substring(startIndex, startIndex + 1)
                + fieldName.substring(startIndex + 1);  
    }




	 public static String fmtDate(Date date) {  
	        if (null == date) {  
	            return null;  
	        }  
	        try {  
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",  
	                    Locale.CHINA);  
	            return sdf.format(date);  
	        } catch (Exception e) {  
	            return null;  
	        }  
	    }  
	
	
}
