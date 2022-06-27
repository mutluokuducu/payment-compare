package com.paymentology.services.store;

import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void store(MultipartFile file);

  void deleteFile(String fileName);

  Path load(String filename);
}
