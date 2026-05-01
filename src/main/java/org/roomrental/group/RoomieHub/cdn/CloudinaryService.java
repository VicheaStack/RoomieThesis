package org.roomrental.group.RoomieHub.cdn;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {

        try {
            Map result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("folder", "roomiehub")
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {
            System.out.println("Cloudinary Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Image upload failed: " + e.getMessage());
        }
    }
}