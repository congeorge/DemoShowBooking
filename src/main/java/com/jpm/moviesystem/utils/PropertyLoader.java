package com.jpm.moviesystem.utils;

import com.jpm.moviesystem.exceptions.BookingSystemException;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private PropertyLoader() {
    }
        static final String PROPERTIES_FILE = "application.properties";
        static final Properties prop= new Properties();
        static {
            try {
                prop.load(PropertyLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            } catch (IOException e) {
                throw new BookingSystemException(e.getMessage());
            }
        }



        public static String getProperty(String propname)  {
            return (String)prop.get(propname);
        }



    }

