package com.manhhoach.JavaTour.controller;


import com.manhhoach.JavaTour.common.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");


    @PostMapping("/upload")
    public ApiResponse<List<String>> uploadFile(@RequestParam("files") MultipartFile[] files) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        var res = Arrays.stream(files).map(file -> {
            try {
                String originalName = file.getOriginalFilename();
                String timestamp = String.valueOf(System.currentTimeMillis());

                // Tách phần đuôi file (.jpg, .png, v.v.)
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
        }).toList();
        return ApiResponse.success(res);
    }
}
