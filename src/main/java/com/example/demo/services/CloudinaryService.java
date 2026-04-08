package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
    
    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        Map<?,?> result = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder", folder,"resource_type","image"
            )
        );
        return (String) result.get("secure_url");
    }

    public List<String> uploadMultipleFiles(List<MultipartFile> files, String folder) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(uploadFile(file, folder));
        }
        return urls;
    }

    public String uploadFromUrl(String imageUrl, String folder) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(
            imageUrl,
            ObjectUtils.asMap(
                "folder", folder,
                "resource_type", "image"
            )
        );
        return (String) result.get("secure_url");
    }

}
