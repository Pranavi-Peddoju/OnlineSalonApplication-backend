package com.cg.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ErrorResponse {
	
	private Date date;
	private String message;
	private String error;
	private int status;
	private String path;

}
