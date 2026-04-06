package com.finaljavaproject.www.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//클래스 자동으로 변환 해주는 건 일딘 후에
public class JsonReader {
    public List<String> read(String url){
        List<String> json = new ArrayList<>();
        String filePath = "./json" + url;
        Path path = Paths.get(filePath);
        try(BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))){
            String line;
            while((line = br.readLine())!= null){
                json.add(line);
            }
            System.out.println("성공적으로 파일을 읽어왔습니다.");

        } catch (IOException e) {
            System.out.println("에러 발생" + e.getMessage());
            e.printStackTrace();
        }
        return json;
    }
}
