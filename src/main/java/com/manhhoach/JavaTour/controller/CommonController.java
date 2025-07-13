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
                String fileName = file.getOriginalFilename();
                Path filePath = uploadDir.resolve(fileName);

                file.transferTo(filePath.toFile()); // Lưu file
                return "uploads/"+fileName; // Trả lại tên file đã lưu
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }).toList();
        return ApiResponse.success(res);
    }
}
