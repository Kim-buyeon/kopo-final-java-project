package com.finaljavaproject.www.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

   private final ObjectMapper mapper = new ObjectMapper();
    public <T> List<T> read(String fileName, Class<T> clazz){
	    String filePath = "./json/" + fileName;
	    File file = new File(filePath);
	    if(!file.exists())return new ArrayList<T>();
	    try {
		return mapper.readValue(file, 
				mapper.getTypeFactory().constructCollectionType(List.class, clazz));
	} catch (IOException e) {
		return new ArrayList<T>();
	}
    }
}
