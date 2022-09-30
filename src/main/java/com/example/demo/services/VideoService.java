package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Video;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    Video getVideo(String name);

    void saveVideo(MultipartFile file, String name) throws IOException;

    List<String> getAllVideoNames();
}
