package com.example.thuctap.services.cloudinary;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface ICloudinaryServices {
    public Map upload(MultipartFile file);
    public void uploadMultipleFiles(MultipartFile[] files);
}
