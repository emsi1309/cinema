package emsi.cinema.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUploadUtil {

    @Value("${upload.image-directory}")
    private String uploadImageDirectory;

    @Value("${upload.trailer-directory}")
    private String uploadTrailerDirectory;

    // Method to upload image and return URL
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = generateUniqueFileName(image.getOriginalFilename());
        Path imagePath = Paths.get(uploadImageDirectory + fileName);
        Files.copy(image.getInputStream(), imagePath);
        return "/upload/images/" + fileName;
    }

    // Method to upload trailer and return URL
    public String uploadTrailer(MultipartFile trailer) throws IOException {
        String fileName = generateUniqueFileName(trailer.getOriginalFilename());
        Path trailerPath = Paths.get(uploadTrailerDirectory + fileName);
        Files.copy(trailer.getInputStream(), trailerPath);
        return "/upload/trailers/" + fileName;
    }

    // Generate a unique file name to avoid file name conflicts
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(originalFileName);
    }

    // Method to load image or trailer as Resource
    public Resource loadFileAsResource(String fileName, String directory) throws MalformedURLException {
        Path filePath = Paths.get(directory).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found: " + fileName);
        }
    }
}

