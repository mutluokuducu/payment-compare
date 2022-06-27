package com.paymentology.services.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageServiceImpl implements  StorageService{

  private final Path rootLocation;

  @Autowired
  public FileSystemStorageServiceImpl(StorageProperties properties) {
    this.rootLocation = Paths.get(properties.getLocation());
  }
  @Override
  public void store(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new FileNotFoundException("Failed to store empty file.");
      }
      Path destinationFile = this.rootLocation.resolve(
              Paths.get(file.getOriginalFilename()))
          .normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new FileNotFoundException(
            "Cannot store file outside current directory.");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
      }
    }
    catch (IOException e) {
      return;
    }
  }

  @Override
  public void deleteFile(String fileName) {
    File file= new File(fileName);
    file.delete();
  }


  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }
}
