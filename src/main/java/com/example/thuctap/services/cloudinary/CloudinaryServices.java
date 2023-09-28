package com.example.thuctap.services.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServices implements ICloudinaryServices{

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) {

        try {
            Map data = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        }
        catch (IOException exception){
            throw new RuntimeException("Image uploading fail");
        }
    }

    @Override
    public void uploadMultipleFiles(MultipartFile[] files) {

        for (MultipartFile file: files) {
            try {
                cloudinary.uploader().upload(file.getBytes(), Map.of());
            }
            catch (IOException exception){
                throw new RuntimeException("Image uploading fail");
            }
        }
    }


}
