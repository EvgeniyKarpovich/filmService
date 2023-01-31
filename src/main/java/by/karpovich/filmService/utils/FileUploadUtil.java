package by.karpovich.filmService.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_PATH = "D://image//poster";

    public static void saveFile(String fileName,
                                MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(UPLOAD_PATH);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uuid = UUID.randomUUID().toString();
        String resultName = uuid + "-" + fileName;

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(resultName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + resultName, ioe);
        }
    }

    public static String generationFileName(MultipartFile file) {
        String uuidFile = UUID.randomUUID().toString();
        return uuidFile + "." + file.getOriginalFilename();
    }
}
