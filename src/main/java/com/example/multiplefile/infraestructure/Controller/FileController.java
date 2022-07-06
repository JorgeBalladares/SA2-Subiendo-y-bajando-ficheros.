package com.example.multiplefile.infraestructure.Controller;

import com.example.multiplefile.application.Service.FileServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping ("/files")
public class FileController {

    @Autowired
    private FileServiceAPI fileServiceAPI;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles (@RequestParam ("files") List<MultipartFile> files)  {
        try{
            fileServiceAPI.save(files);
            return ResponseEntity.status(HttpStatus.OK).body("successfully load");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("corrupted or repetead file");

        }
    }


    @PostMapping("/upload/local")
    public void uploadLocal (@RequestParam("files") MultipartFile multipartFile){
        fileServiceAPI.uploadToLocal(multipartFile);
    }


    @PostMapping ("/upload/db")
    public void uploadDb (@RequestParam("files") MultipartFile multipartFile) throws IOException {
        fileServiceAPI.uploadToDB(multipartFile);

    }

}
