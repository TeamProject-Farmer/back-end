package com.farmer.backend.api.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String reviewImgUpload(MultipartFile reviewImg) throws IOException{
        File uploadReviewImg = convert(reviewImg)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_CONVERT));

        String fileName = "reviewImg" + "/" + uploadReviewImg.getName();
        String uploadImageUrl = putS3(uploadReviewImg, fileName);

        removeNewFile(uploadReviewImg);

        return uploadImageUrl;

    }

    private String putS3(File uploadReviewImg, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadReviewImg)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * 로컬 생성 파일 삭제
     */
    private void removeNewFile(File reviewImg) {
        if(reviewImg.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 않았습니다.");
        }
    }

    private Optional<File> convert(MultipartFile reviewImg) throws  IOException {
        File convertFile = new File(reviewImg.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(reviewImg.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }


}
