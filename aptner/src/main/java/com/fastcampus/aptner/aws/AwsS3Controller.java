package com.fastcampus.aptner.aws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "AWS", description = "AWS S3 관련 서비스")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    @PostMapping(value ="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "파일 업로드 API",
            description = "파일을 업로드 하고, URL 을 반환",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "파일 업로드 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "업로드 중 문제 발생"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "파일이 비어있음")
                    }
    )
    public ResponseEntity<?> uploadFile(
            @RequestParam("category") String category,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFileV1(category, multipartFile);
    }

}
