package by.karpovich.filmService.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadDownloadUtil {

    private static final String UPLOAD_PATH = "D://image//poster";

    public static void saveFile(String fileName,
                                MultipartFile file) {

        Path uploadPath = Paths.get(UPLOAD_PATH);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generationFileName(MultipartFile file) {
        String uuidFile = UUID.randomUUID().toString();
        return uuidFile + "-" + file.getOriginalFilename();
    }

    public static byte[] getImageAsResponseEntity(String fileName) {
        String dirPath = "D://image//poster" + "//";
        InputStream in = null;
        byte[] media = new byte[0];
        try {
            in = new FileInputStream(dirPath + fileName);
            media = IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return media;
    }

//    public static String downloadFile(String fileName) {
//        String path = UPLOAD_PATH + "//" + fileName;
//
//        ReadableByteChannel rbc = null;
//        FileOutputStream fos = null;
//
//        try {
//            URL url = new URL(path);
//
//            rbc = Channels.newChannel(url.openStream());
//            fos = new FileOutputStream(fileName);
//            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (rbc != null) {
//                    rbc.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return fileName;
//    }
}
