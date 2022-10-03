package com.example.demo.vo;

import lombok.Data;

@Data
public class VideoVO {
	private Long id;
	private String name;
	private String path;
	private byte[] data;
}
