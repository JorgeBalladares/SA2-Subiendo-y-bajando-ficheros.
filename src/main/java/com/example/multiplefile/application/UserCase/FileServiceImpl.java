package com.example.multiplefile.application.UserCase;

import com.example.multiplefile.application.Service.FileServiceAPI;
import com.example.multiplefile.domain.File;
import com.example.multiplefile.infraestructure.Repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileServiceAPI {

    private final Path rootFolder = Paths.get("uploads");
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;


    //------------Save file in local path
    @Override
    public void save(MultipartFile file) throws Exception {
        //---A type of method which we can add files in a local path
        Files.copy(file.getInputStream(), this.rootFolder.resolve(Objects.requireNonNull(file.getOriginalFilename())));

    }
    @Override
    public void save(List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files){
            this.save(file);
        }
    }


    //------------Save file in local path
    @Override
    public void uploadToLocal(MultipartFile multipartFile){
        //---Another method to add a file in local path
        try {
            byte[] data = multipartFile.getBytes();
            String uploadFolderPath = "C:\\Users\\jorge.balladares\\Desktop\\multiplefile\\uploads";
            Path path = Paths.get(uploadFolderPath, multipartFile.getOriginalFilename());
            Files.write(path, data );
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void uploadToLocal(List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files){
            this.save(file);
        }
    }


    //---------------Upload a file in a Database
    @Override
    public File uploadToDB(MultipartFile file1) {
        File file = new File();
        try {
            file.setFileData(file1.getBytes());
            file.setFileType(file1.getContentType());
            file.setFileName(file1.getOriginalFilename());
            file.setUploadDate(new Date());
            return fileRepository.save(file);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    //---Download files from Database
    public File downloadFile (String fileId) {
        Optional<File> file = fileRepository.findById(fileId);
        return modelMapper.map(file, File.class);

    }








}
