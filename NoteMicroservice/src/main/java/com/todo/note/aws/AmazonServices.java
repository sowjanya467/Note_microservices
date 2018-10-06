/*package com.todo.note.aws;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonServices implements AwsService {
	private static final String SUFFIX = "/";
	static final String bucketName = "bridgelabz-todo-storage";

	static AWSCredentials credentials = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY_ID"),
			System.getenv("AWS_SECRET_ACCESS_KEY"));
	// static AmazonS3 s3client = new AmazonS3Client(credentials);

	static AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();


	*//**
	 * purpose:To create bucket in S3
	 * @param bucketName
	 * @return
	 *//*
	@Override
	public String createBucket(String bucketName) {

		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName,
				com.amazonaws.services.s3.model.Region.AP_Mumbai);
		Bucket resp = amazonS3Client.createBucket(createBucketRequest);
		return resp.getName();

	}

	*//**
	 * purpose:to create folder in s3
	 * @param bucketName
	 * @param folderName
	 * @return
	 *//*
	@Override
	public String createFolder(String bucketName, String folderName) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		folderName = folderName + SUFFIX + "profileFolder" + SUFFIX;
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, emptyContent, metadata);
		// send request to S3 to create folder
		amazonS3Client.putObject(putObjectRequest);
		return folderName;
	}

	
	@Override
	public String uploadProfilePic(String bucketName, String folderName, String path) {
		String fileName = folderName + SUFFIX + path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		// folderName + SUFFIX + "3dimage.jpg";
		amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, new File(path))
				.withCannedAcl(CannedAccessControlList.PublicRead));
		return "file uploaded!!";/*

	}

	*//**
	 * purpose:to store images in s3
	 * @param folderName
	 * @param noteId
	 * @param file
	 * @return
	 * @throws AmazonServiceException
	 * @throws SdkClientException
	 * @throws IOException
	 *//*
	@Override
	public String storeImages(String folderName, String noteId, MultipartFile file)
			throws AmazonServiceException, SdkClientException, IOException {
		String s = file.getOriginalFilename();
		String fpath = folderName + SUFFIX + noteId + SUFFIX + s.substring(s.lastIndexOf("/") + 1, s.lastIndexOf("."));
		System.out.println(fpath);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		File convfiles = multipartToFile(file);
		amazonS3Client.putObject(bucketName, fpath, convfiles);

		
		 * amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, new
		 * File(path)) .withCannedAcl(CannedAccessControlList.PublicRead));
		 
		URL i1 = amazonS3Client.getUrl(bucketName, fpath);
		String imagePath = i1.toString();
		return imagePath;

	}

	*//**
	 * purpose:to store images in s3
	 * @param folderName
	 * @param noteId
	 * @param file
	 * @return
	 * @throws AmazonServiceException
	 * @throws SdkClientException
	 * @throws IOException
	 *//*
	@Override
	public String deleteImage(String userId, String noteId, String fileName) {
		String fpath = userId + SUFFIX + noteId + SUFFIX + fileName;

		amazonS3Client.deleteObject(bucketName, fpath);
		return "deleted successfully";

	}

	*//**
	 * purpose:to delete bucket
	 * @param bucketName
	 * @return String
	 *//*
	@Override
	public String deleteBucket(String bucketName) {
		for (Bucket bucket : amazonS3Client.listBuckets()) {

			System.out.println(" - " + bucket.getName());
			if (bucket.getName().equals(bucketName)) {
				amazonS3Client.deleteBucket(bucketName);
			} else {
				return "bucket with this name does not exist";
			}
		}

		return "bucket deleted";

	}


	*//**
	 * purpose:to get object
	 * @param fileName
	 * @param userId
	 * @param noteId
	 * @throws IOException
	 *//*
	@Override
	public void getObject(String fileName, String userId, String noteId) throws IOException {
		String key = userId + SUFFIX + noteId + SUFFIX + fileName;
		com.amazonaws.services.s3.model.S3Object fullObject = amazonS3Client.getObject(bucketName, key);
		fullObject.getObjectContent();
		displayTextInputStream(fullObject.getObjectContent());
	}

	public static File multipartToFile(MultipartFile file) throws IllegalStateException, IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private static void displayTextInputStream(InputStream input) throws IOException {
		// Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		System.out.println();
	}

}
*/