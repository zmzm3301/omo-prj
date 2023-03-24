package com.omo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.auth.FirebaseAuthException;
import com.omo.service.FirebaseStorageService;



@RestController
public class FirebaseController {

	@Autowired
	private FirebaseStorageService firebaseService;
	
	@PostMapping("/files")
	public String uploadFile(@RequestParam("file") MultipartFile file, String nameFile)
	    throws IOException, FirebaseAuthException {
	    if (file.isEmpty()) {
	        return "is empty";
	    }
	    return firebaseService.uploadFiles(file, nameFile);
	}
}
