package com.example.multiplefile.infraestructure.Repository;

import com.example.multiplefile.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
