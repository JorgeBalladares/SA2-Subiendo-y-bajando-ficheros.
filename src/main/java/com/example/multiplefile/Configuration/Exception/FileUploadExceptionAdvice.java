package com.example.multiplefile.Configuration.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    //metodo creado que se invoca cuando se lance una excepcion del tipo peso maximo de archivo
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException (MaxUploadSizeExceededException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File size not allowed, OGGG");
    }

    @ExceptionHandler(MultipartException.class)
    public String handleMultiparFile (MultipartException ex, RedirectAttributes attributes){
        attributes.addFlashAttribute("message", ex.getCause().getMessage());
        return "redirect:/status";
    }

}
