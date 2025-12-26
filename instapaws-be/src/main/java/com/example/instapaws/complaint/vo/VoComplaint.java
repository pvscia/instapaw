package com.example.instapaws.complaint.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
public class VoComplaint {
	private Long id;
	private String complaint, username;
}
