package com.example.multiplefile.application.Service;

import com.example.multiplefile.domain.File;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileServiceAPI {

    public void save (MultipartFile file) throws  Exception;

    public Resource load (String name) throws Exception;

    public void save (List<MultipartFile> file) throws Exception;

    public Stream<Path> loadAll() throws Exception;

    public void uploadToLocal(MultipartFile multipartFile);

    public File uploadToDB(MultipartFile multipartFile);


}
