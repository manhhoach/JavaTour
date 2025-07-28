package com.manhhoach.JavaTour.helpers;

import com.manhhoach.JavaTour.constants.FileExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileHelper {
    private static final Map<String, byte[]> MAGIC_NUMBERS = new HashMap<>();

    static {
        MAGIC_NUMBERS.put(FileExtension.PNG, new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47});
        MAGIC_NUMBERS.put(FileExtension.JPEG, new byte[]{(byte) 0xFF, (byte) 0xD8});
        MAGIC_NUMBERS.put(FileExtension.PDF, new byte[]{(byte) 0x25, 0x50, 0x44, 0x46});
        MAGIC_NUMBERS.put(FileExtension.MP3, new byte[]{(byte) 0xFF, (byte) 0xFB});
    }

    public static boolean checkFileMagicNumber(MultipartFile file, List<String> allowExtension) {
        try (var fis = file.getInputStream()) {
            byte[] header = new byte[4];
            fis.read(header);
            var allowMagicNumbers = MAGIC_NUMBERS.entrySet()
                    .stream().filter(x -> allowExtension.contains(x.getKey()))
                    .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
            for (Map.Entry<String, byte[]> entry : allowMagicNumbers.entrySet()) {
                boolean isMatched = true;
                byte[] magicNumber = entry.getValue();
                for (int i = 0; i < magicNumber.length; i++) {
                    if (i >= header.length || header[i] != magicNumber[i]) {
                        isMatched = false;
                        break;
                    }
                }
                if (isMatched) {
                    return true;
                }
            }
            return false;

        } catch (IOException ex) {
            return false;
        }
    }
}
