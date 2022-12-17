package com.example.demo.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.VideoService;
import com.example.demo.vo.VideoVO;

@Controller
public class VideoController {
	@Autowired
    private VideoService videoService;
    
	@GetMapping("/list")
	public String index(Model model) {
		List<VideoVO> allVideos = videoService.getAllVideo();
		model.addAttribute("allVideos", allVideos);
		return "list";
	}
	
	@GetMapping("/player")
	public String playVideo(@RequestParam(name="id") Long id, Model model) throws UnsupportedEncodingException {
		String url = String.format("/video/%d", id);
		model.addAttribute("src", url);
		return "playing";
	}

	@GetMapping("/random")
	public String playRandomVideo(Model model) throws IOException {
		Long id = videoService.getRandomVideo().getId();
		String url = String.format("/video/%d", id);
		model.addAttribute("src", url);
		return "random";
	}
}
