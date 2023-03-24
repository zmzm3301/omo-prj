package com.omo.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {

//    private final Storage storage;
    private final String bucketName = "omo-prj.appspot.com";
    
    
 
    public String uploadFiles(MultipartFile file, String nameFile)
        throws IOException, FirebaseAuthException {
        Bucket bucket = StorageClient.getInstance().bucket("omo-prj.appspot.com");
        InputStream content = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create(nameFile.toString(), content, file.getContentType());
        return blob.getMediaLink();
    }

//    public FirebaseStorageService() throws IOException {
//        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("src/main/resources/firebase_accessKey.json");
//
//        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
//        this.storage = storageOptions.getService();
//    }
//
//    public String uploadFile(MultipartFile file) throws IOException {
//        String fileName = UUID.randomUUID().toString();
//        BlobId blobId = BlobId.of(bucketName, fileName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
//        Path tempFile = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
//        file.transferTo(Files.createFile(tempFile).toFile());
//        storage.create(blobInfo, Files.readAllBytes(tempFile));
//        Files.delete(tempFile);
//        return storage.get(bucketName).get(fileName).getMediaLink();
//    }
    
}


