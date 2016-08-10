package com.cyc.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 *	全局配置文件读取器
 *  GLOBAL_STRING_GONFIG 储存了了resourse目录下global.properties中的配置文件
 */
@Component
public class ConfigManage   {

	
	public static Map<String, String > GLOBAL_STRING_CONFIG=new HashMap<String, String>();
	private static String CONFIPATH="global.properties";
		private Logger log ; 
	private ConfigManage()
	{
		log=Logger.getLogger(ConfigManage.class.getName());
		try {
			initConfig(CONFIPATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void initConfig(String path) throws IOException {
		// TODO Auto-generated method stub
 		GLOBAL_STRING_CONFIG=new HashMap<>();
        // 方法二：通过类加载目录getClassLoader()加载属性文件  
        InputStream in = ConfigManage.class.getClassLoader()  
                .getResourceAsStream(path);  
         
         Properties prop = new Properties();  
        try {  
            prop.load(in); 
            Set<Object> keys= prop.keySet();
            Iterator<Object> iterator= keys.iterator();
            while(iterator.hasNext())
            {
            	Object key=iterator.next();
            	log.log(Level.INFO, "读取配置文件"+key+">>>>"+prop.getProperty((String) key));
            	GLOBAL_STRING_CONFIG.put((String) key, prop.getProperty((String) key));
            }
           
           
        } catch (IOException e) {  
            System.out.println("读取global配置文件出错");  
            e.printStackTrace();  
        }  
        finally{
        	
        	if(in!=null)
        	{
        		in.close();
        	}
        }
         
	}

	
	
	
}
