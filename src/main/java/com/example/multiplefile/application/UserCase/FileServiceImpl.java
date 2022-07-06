package com.example.multiplefile.application.UserCase;

import com.example.multiplefile.application.Service.FileServiceAPI;
import com.example.multiplefile.domain.File;
import com.example.multiplefile.infraestructure.Repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileServiceAPI {

    private final Path rootFolder = Paths.get("uploads");
    private final FileRepository fileRepository;

    @Override
    public void save(MultipartFile file) throws Exception {

        Files.copy(file.getInputStream(), this.rootFolder.resolve(Objects.requireNonNull(file.getOriginalFilename())));

    }

    @Override
    public Resource load(String name) {
        return null;
    }

    @Override
    public void save(List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files){
            this.save(file);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }


    //------------------------Using thymeleaf

    @Override
    public void uploadToLocal(MultipartFile multipartFile){
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


}
