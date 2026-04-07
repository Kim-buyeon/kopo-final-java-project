package com.finaljavaproject.www.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

//파일에 json 데이터를 작성하는 클래스
public class JsonWriter {
    private final  ObjectMapper mapper = new ObjectMapper();
    //json 디렉토리 밑에 파일을 만들고 그 파일에 json 형식의 데이터를 작성하는 메서드
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
