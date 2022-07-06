package com.example.multiplefile.Configuration.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    //metodo creado que se invoca cuando se lance una excepcion del tipo peso maximo de archivo
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException (MaxUploadSizeExceededException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File size not allowed, OGGG");
    }

}
