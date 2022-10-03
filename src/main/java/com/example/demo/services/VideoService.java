package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.VideoEntity;
import com.example.demo.vo.VideoVO;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    VideoVO getVideo(Long id) throws IOException;

    VideoVO getRandomVideo() throws IOException;

    List<String> getAllVideoNames();

	void saveVideo(MultipartFile file) throws IllegalStateException, IOException;

	List<VideoVO> getAllVideo();
}
