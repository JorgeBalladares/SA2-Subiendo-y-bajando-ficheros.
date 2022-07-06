package com.example.multiplefile.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Files")
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String fileId;

    @Column(name = "name")
    private String fileName;

    @Column(name = "type")
    private String fileType;

    @Column(name = "uploadDate")
    private Date uploadDate;

    @Lob
    private byte[] fileData;




}
