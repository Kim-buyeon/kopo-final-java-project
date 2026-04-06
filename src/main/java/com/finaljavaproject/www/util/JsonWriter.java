package com.finaljavaproject.www.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonWriter {
    public ObjectMapper mapper;
    public void write(String url, List<Object> objects) throws JsonProcessingException {
        mapper = new ObjectMapper();
        String objectToJson = mapper.writeValueAsString(objects);
        String sPath = "./json" + url;
        Path path = Paths.get(sPath);
        try(FileWriter writer = new FileWriter(String.valueOf(path))) {
            writer.write(objectToJson);
            System.out.println("파일이 작성되었습니다.");
        } catch (IOException e) {
            System.out.println("오류가 발생했습니다." + e.getMessage() );
            e.printStackTrace();
        }

    }
}
