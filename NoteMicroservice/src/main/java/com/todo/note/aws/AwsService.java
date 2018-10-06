/*package com.todo.note.aws;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

public interface AwsService {

		*//**
		 * purpose:To create bucket in S3
		 * @param bucketName
		 * @return
		 *//*
		String createBucket(String bucketName);

		*//**
		 * purpose:to create folder in s3
		 * @param bucketName
		 * @param folderName
		 * @return
		 *//*
		String createFolder(String bucketName, String folderName);

		*//**
		 * 
		 * @param bucketName
		 * @param folderName
		 * @param path
		 * @return
		 *//*
		String uploadProfilePic(String bucketName, String folderName, String path);

		*//**
		 * purpose:to delete bucket
		 * @param bucketName
		 * @return String
		 *//*
		String deleteBucket(String bucketName);


		*//**
		 * purpose:to get object
		 * @param fileName
		 * @param userId
		 * @param noteId
		 * @throws IOException
		 *//*
		void getObject(String fileName, String userId, String noteId) throws IOException;

		*//**
		 * purpose:to delete object
		 * @param userId
		 * @param noteId
		 * @param fileName
		 * @return String
		 *//*
		String deleteImage(String userId, String noteId, String fileName);

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
		String storeImages(String folderName, String noteId, MultipartFile file)
				throws AmazonServiceException, SdkClientException, IOException;

	
}
*/