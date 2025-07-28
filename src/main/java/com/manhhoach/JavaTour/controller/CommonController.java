package com.manhhoach.JavaTour.controller;


import com.manhhoach.JavaTour.common.ApiResponse;
import com.manhhoach.JavaTour.constants.FileExtension;
import com.manhhoach.JavaTour.helpers.FileHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {
    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");
    private static final List<String> ALLOWED_EXTENSIONS = List.of(FileExtension.JPEG, FileExtension.PNG);

    @PostMapping("/upload")
    public ApiResponse<List<String>> uploadFile(@RequestParam("files") MultipartFile[] files) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        var res = Arrays.stream(files).filter(file -> !file.isEmpty()).map(file -> {
            try {

                if (!FileHelper.checkFileMagicNumber(file, ALLOWED_EXTENSIONS)) {
                    return null;
                }

                String originalName = file.getOriginalFilename();
                String timestamp = String.valueOf(System.currentTimeMillis());

                String extension = "";
                int dotIndex = originalName.lastIndexOf('.');
                if (dotIndex >= 0) {
                    extension = originalName.substring(dotIndex);
                    originalName = originalName.substring(0, dotIndex);
                }

                String newFileName = originalName + "_" + timestamp + extension;
                Path filePath = uploadDir.resolve(newFileName);

                file.transferTo(filePath.toFile()); // Lưu file
                return "uploads/" + newFileName;    // Trả lại tên file đã lưu
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }).filter(x -> x != null).toList();
        return ApiResponse.success(res);
    }
}
