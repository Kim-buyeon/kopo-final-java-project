package com.finaljavaproject.www.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonWriter {
    private final  ObjectMapper mapper = new ObjectMapper();
    public void write(String fileName, Object data) {
	    String filePath = "./json/" + fileName;
	    try {
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
		System.out.println("파일 저장 완료" + fileName);
	} catch (IOException e) {
		System.err.println("저장 오류 : " + e. getMessage());
	}

    }
}
