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
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploadUtil {

    private String uploadImageDirectory = System.getProperty("user.dir") + "/src/main/resources/static/upload/images/";
    private String uploadTrailerDirectory = System.getProperty("user.dir") + "/src/main/resources/static/upload/trailers/";

    // Method to upload image and return URL
    public String uploadImage(MultipartFile image) throws IOException {
        String originalFileName = image.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String newFileName = generateUniqueFileName() + "." + fileExtension; // Generate unique filename

        // Remove invalid characters from the filename
        newFileName = removeInvalidChars(newFileName);

        Path imagePath = Paths.get(uploadImageDirectory + newFileName);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!Files.exists(imagePath.getParent())) {
            Files.createDirectories(imagePath.getParent());
        }

        // Sao chép file
        try {
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Xử lý lỗi, ví dụ:
            e.printStackTrace();
            throw new RuntimeException("Failed to store file " + newFileName, e);
        }

        return "/upload/images/" + newFileName;
    }

    // Method to upload trailer and return URL
    public String uploadTrailer(MultipartFile trailer) throws IOException {
       // String fileName = generateUniqueFileName();
        String fileName = trailer.getOriginalFilename();
        Path trailerPath = Paths.get(uploadTrailerDirectory + fileName);

        if (!Files.exists(trailerPath.getParent())) {
            Files.createDirectories(trailerPath.getParent());
        }

        // Sao chép file
        Files.copy(trailer.getInputStream(), trailerPath, StandardCopyOption.REPLACE_EXISTING);

        return "/upload/trailers/" + fileName;
    }

    private String removeInvalidChars(String filename) {
        // Define a list of invalid characters (for example, ':' in this case)
        String invalidChars = ":";
        // Replace invalid characters with a valid alternative (for example, '-')
        for (char invalidChar : invalidChars.toCharArray()) {
            filename = filename.replace(invalidChar, '-');
        }
        return filename;
    }

    // Generate a unique file name to avoid file name conflicts
    private String generateUniqueFileName() {
        return UUID.randomUUID().toString();
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

