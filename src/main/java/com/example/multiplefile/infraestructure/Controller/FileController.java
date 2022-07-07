package com.example.multiplefile.infraestructure.Controller;

import com.example.multiplefile.application.Service.FileServiceAPI;
import com.example.multiplefile.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping ("/files")
public class FileController {

    @Autowired
    private FileServiceAPI fileServiceAPI;


    @PostMapping("/uploading")
    public ResponseEntity<String> uploadFiles (@RequestParam ("files") List<MultipartFile> files)  {
        try{
            fileServiceAPI.save(files);
            return ResponseEntity.status(HttpStatus.OK).body("successfully load");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("corrupted or repetead file");

        }
    }


    @PostMapping("/upload/local")
    public ResponseEntity<String> uploadLocal (@RequestParam("files")  List<MultipartFile> files) {
        try{
            fileServiceAPI.uploadToLocal(files);
            return ResponseEntity.status(HttpStatus.OK).body("successfully load");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("corrupted or repetead file");
        }
    }


    @PostMapping ("/upload/db")
    public ResponseEntity<String> uploadDb (@RequestParam("files") MultipartFile multipartFile) {
        if(!multipartFile.isEmpty()){
            File file = fileServiceAPI.uploadToDB(multipartFile);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Successfully uploads");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Fail to upload file, it's empty or missed");
        }
    }

    @GetMapping ("/get/file/{fileId}")
    public ResponseEntity<Object> getFile (@PathVariable ("fileId") String fileId) throws NoSuchFileException {
        File file =  fileServiceAPI.downloadFile(fileId);
        if (file!=null){
            return ResponseEntity.status(HttpStatus.OK).body(file);
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("File not found");
        }
    }

    @GetMapping ("/download/file/{fileId}")
    public ResponseEntity<Object> downloadFile (@PathVariable ("fileId") String fileId)  {
        File file =  fileServiceAPI.downloadFile(fileId);
        if (file!=null){
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+file.getFileName())
                    .body(new ByteArrayResource(file.getFileData()));
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("File not found");
        }
    }


}
