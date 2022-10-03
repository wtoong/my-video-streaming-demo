package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "video")
@Data
@NoArgsConstructor
public class VideoEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(length = 4000)
    private String name;
    @Column(unique = true, length = 4000)
	private String path;
}
