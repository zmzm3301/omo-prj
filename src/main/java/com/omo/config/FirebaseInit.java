package com.omo.config;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseInit {
	  public static void init() {
	    try {
	      FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase_accessKey.json");

	      FirebaseOptions options = FirebaseOptions.builder()
	        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	        .setStorageBucket("omo-prj.appspot.com")
	        .build();

	      FirebaseApp.initializeApp(options);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	}

