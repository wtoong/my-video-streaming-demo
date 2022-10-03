package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.VideoEntity;
import com.example.demo.exceptions.VideoAlreadyExistsException;
import com.example.demo.exceptions.VideoNotFoundException;
import com.example.demo.repo.VideoRepo;
import com.example.demo.vo.VideoVO;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	private ModelMapper mapper;
    private VideoRepo repo;
	private String path;
	
	public VideoServiceImpl(VideoRepo repo, @Value("${file.path}") String path,
			@Value("${file.scan.paths}") String pathsForScan) throws UnsupportedEncodingException {
		this.repo = repo;
		this.path = path;
		init(pathsForScan);
	}

	private void init(String pathsForScan) throws UnsupportedEncodingException {
		String[] paths = pathsForScan.split(",");
		for (String path : paths) {
			loadFiles(path);
		}
	}

	private void loadFiles(String path) throws UnsupportedEncodingException {
		File dir = new File(path);
		File[] files = dir.listFiles();
		
		for (File file : files) {
			loadFile(file);
		}
	}

	private void loadFile(File file) throws UnsupportedEncodingException {
		String path = file.getAbsolutePath();
		String name = file.getName();
		
		VideoEntity videoEntity = new VideoEntity();
		videoEntity.setName(name);
		videoEntity.setPath(path);
		
		repo.save(videoEntity);
	}

    @Override
    public VideoVO getVideo(Long id) throws IOException {
        VideoEntity videoEntity = repo.findById(id).get();
        return toVO(videoEntity);
    }

	private VideoVO toVO(VideoEntity videoEntity) throws IOException {
		VideoVO videoVO = mapper.map(videoEntity, VideoVO.class);
        videoVO.setData(Files.readAllBytes(new File(videoVO.getPath()).toPath()));
        return videoVO;
	}

    @Override
    public List<String> getAllVideoNames() {
        return repo.getAllEntryNames();
    }

    @Override
    public void saveVideo(MultipartFile file) throws IllegalStateException, IOException {
    	String fname = file.getOriginalFilename();
        if(repo.existsByName(fname)){
            throw new VideoAlreadyExistsException();
        }
        
		String fullPath = Paths.get(path, fname).toString();
		
		VideoEntity pImage = new VideoEntity();
		pImage.setName(fname);
		pImage.setPath(fullPath);

		file.transferTo(new File(fullPath));
		repo.save(pImage);
    }

	@Override
	public VideoVO getRandomVideo() throws IOException {
		int idx = (int)(Math.random() * repo.count());
		Page<VideoEntity> videoPage = repo.findAll(PageRequest.of(idx, 1));
		if (videoPage.hasContent())
			return toVO(videoPage.getContent().get(0));
		throw new VideoNotFoundException(String.format("No Videos"));
	}

	@Override
	public List<VideoVO> getAllVideo() {
		return repo.findAll().stream().map((e) -> mapper.map(e, VideoVO.class)).toList();
	}
}
