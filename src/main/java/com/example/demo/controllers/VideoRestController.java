package com.example.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.services.VideoService;
import com.example.demo.vo.VideoVO;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("video")
@AllArgsConstructor
public class VideoRestController {

	private VideoService videoService;

	// Each parameter annotated with @RequestParam corresponds to a form field where
	// the String argument is the name of the field
	@PostMapping()
	public ResponseEntity<String> saveVideo(@RequestParam("file") MultipartFile file) throws IOException {
		videoService.saveVideo(file);
		return ResponseEntity.ok("Video saved successfully.");

	}

	// {name} is a path variable in the url. It is extracted as the String parameter
	// annotated with @PathVariable
	@GetMapping("{id}")
	public ResponseEntity<Resource> getVideoByName(@PathVariable("id") Long id) throws IOException {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new ByteArrayResource(videoService.getVideo(id).getData()));

	}

	@GetMapping("random")
	public Long getRandomVideo() throws IOException {
		VideoVO video = videoService.getRandomVideo();
		return video.getId();
	}

	@GetMapping("all")
	public ResponseEntity<List<String>> getAllVideoNames() {
		return ResponseEntity.ok(videoService.getAllVideoNames());

	}

}
