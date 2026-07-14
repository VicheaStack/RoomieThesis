package org.roomrental.group.RoomieHub.storage;

import com.cloudinary.Cloudinary;
import org.roomrental.group.RoomieHub.exception.AppException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Original method – kept for backward compatibility (auto‑generates ID)
    public String uploadImage(MultipartFile file) {
        return uploadImage(file, null);  // no custom ID → Cloudinary generates one
    }

    // New method – allows custom ID and returns the public_id
    public String uploadImage(MultipartFile file, String customPublicId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("folder", "roomiehub");
            if (customPublicId != null && !customPublicId.isBlank()) {
                params.put("public_id", customPublicId);
                params.put("overwrite", true);  // optional – replace if exists
            }

            Map result = cloudinary.uploader().upload(file.getBytes(), params);

            // Return the public_id (including the folder prefix)
            return result.get("public_id").toString();

        } catch (Exception e) {
            System.out.println("Cloudinary Error: " + e.getMessage());
            e.printStackTrace();
            throw AppException.of("Image upload failed: " + e.getMessage());
        }
    }
}