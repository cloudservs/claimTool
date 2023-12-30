package com.cloudservs.claimtool.handler;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.cloudservs.claimtool.domain.claim.UploadData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Component
public class AWSHelper {
    Logger logger = LoggerFactory.getLogger(AWSHelper.class);
    private static final String SUFFIX = "/";

    private static AWSCredentials credentials;

    private static AmazonS3 s3client;

    @Value("${amazon.s3.claimtool-bucket}")
    private String CLAIMTOOL_CASE_BUCKET;

    @Value("${amazon.s3.access_key}")
    private String accessKey;
    @Value("${amazon.s3.sec_key}")
    private String secKey;
    @PostConstruct
    public void init(){
        credentials = new BasicAWSCredentials(accessKey,secKey);
        s3client = new AmazonS3Client(credentials);
    }

    public AmazonS3 getS3client(){
        return s3client;
    }
    public S3Object getS3Object(String bucket, String key) {
        return s3client.getObject(bucket, key);
    }
    public S3Object getS3ObjectFromBucket(String bucket,String key) {
        return s3client.getObject(bucket, key);
    }
    public byte[] getObjectBytes(S3ObjectInputStream objectContent) {
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(objectContent);
        } catch (Exception e) {
            /*logger.error("Error getting File: " + e, e);*/
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(objectContent,null);
        }
        return bytes;
    }



    public PutObjectResult insertFolderInBucket(String bucketName, String folderName){
        return createFolder(bucketName, folderName, s3client);
    }

    public PutObjectResult insertFileInS3(String fileName, File file){
        try {
            System.out.println("Inserting the file into s3 path : " + fileName);
            return s3client.putObject(new PutObjectRequest(CLAIMTOOL_CASE_BUCKET, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch ( Exception e){
            logger.info("Error while inserting file {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public PutObjectResult saveFileToS3(String bucketName, UploadData ud){
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.addUserMetadata("FILE_NAME", ud.getFileName());
            InputStream stream = new ByteArrayInputStream(ud.getFileData());
            String keyName = ud.getInnerBucketPath();
            logger.info("Inserting the file into s3 path : " + ud.getInnerBucketPath());
            PutObjectRequest putObjectRequest = null;
            if(ud.isPublicFile()) {
                putObjectRequest = new PutObjectRequest(bucketName, keyName, stream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
            } else {
                putObjectRequest = new PutObjectRequest(bucketName, keyName, stream, objectMetadata);
            }
            PutObjectResult putObjectResult = s3client.putObject(putObjectRequest);
            if(ud.isPublicFile())
                ud.setPublicUrl(s3client.getUrl(bucketName, keyName).toExternalForm());

            return putObjectResult;// s3client.putObject(putObjectRequest);
        }catch ( Exception e){
            logger.info("Error while inserting file {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public String getPresignedURL(String keyName) {
        java.util.Date expiration = new java.util.Date();
        long milliSeconds = expiration.getTime();
        milliSeconds += 1000 * 60 * 7; // Add 7 day.
        expiration.setTime(milliSeconds);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(CLAIMTOOL_CASE_BUCKET, keyName);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);
        return s3client.generatePresignedUrl(generatePresignedUrlRequest).toExternalForm();
    }




    public PutObjectResult createFolder(String bucketName, String folderName, AmazonS3 client) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            String folder = (folderName + SUFFIX).replace( "//","/" );
            /*   if(folder.substring( folder.length()-1 ).equals( "/" )) folder = folder.substring( 0,folder.length()-2);*/
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    folder, emptyContent, metadata);
            return client.putObject(putObjectRequest);

        }catch (Exception e){
            logger.info("Error while creating folder :{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public boolean  deleteFolder(String bucketName, String folderName) {
        try {
            s3client.deleteObject(bucketName, folderName);
            return true;
        }catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
        return false;
    }

}