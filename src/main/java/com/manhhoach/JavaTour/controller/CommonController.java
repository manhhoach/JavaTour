package com.manhhoach.JavaTour.controller;


import com.manhhoach.JavaTour.common.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    private final Path uploadDir = Paths.get("uploads");

    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Tạo file path (tránh ghi đè)
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);

            // Lưu file
            file.transferTo(filePath.toFile());

            // Trả lại URL hoặc tên file
            return ApiResponse.success("/uploads/" + fileName); // bạn tự cấu hình route tĩnh
        } catch (IOException e) {
            return ApiResponse.error("File upload failed: " + e.getMessage());
        }
    }
}
