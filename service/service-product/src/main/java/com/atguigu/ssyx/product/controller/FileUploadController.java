package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

@ApiOperation("文件上传接口")
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {

    @ApiOperation("图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", required = true, dataType = "MultipartFile", paramType = "form")
    })
    @PostMapping("fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        String minioEndpoint = "http://localhost:9000"; // MinIO 地址
        String accessKey = "minioadmin";
        String secretKey = "minioadmin123";
        String bucketName = "mashuai";

        if (file.isEmpty()) {
            throw new RuntimeException("文件为空");
        }

        // 1️⃣ 生成日期目录
        LocalDate today = LocalDate.now();
        String datePath = today.getYear() + "/" +
                String.format("%02d", today.getMonthValue()) + "/" +
                String.format("%02d", today.getDayOfMonth());

        // 2️⃣ 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename != null && originalFilename.contains(".") ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        // 3️⃣ 最终对象路径
        String objectPath = datePath + "/" + filename;

        try {
            // 4️⃣ 初始化 MinIO 客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 5️⃣ 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream")
                            .build()
            );

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException("上传失败: " + e.getMessage(), e);
        }

        // 6️⃣ 返回完整 URL
        String fileUrl = minioEndpoint + "/" + bucketName + "/" + objectPath;
        return Result.ok(fileUrl);
    }
}