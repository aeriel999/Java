package org.example.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void init();
    String SaveImage(MultipartFile file, FileSaveFormat format);

    void deleteImage(String fileName) throws IOException;
    String SaveImageURL(String imageUrl, FileSaveFormat format);
}
