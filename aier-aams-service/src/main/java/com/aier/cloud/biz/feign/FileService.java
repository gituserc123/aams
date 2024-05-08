package com.aier.cloud.biz.feign;

import com.aier.cloud.basic.api.response.domain.file.FileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@FeignClient(name="aier-service-file")
public interface FileService {

	 
		/**
		 * 
		 * @param file 文件对象
		 * @param bucketType 1:私有读私有写、2：公有读私有写
		 * @param isEncrypt 是否加密
		 * @param path 路径 + 文件名
		 * @param platform 来源平台
		 * @return
		 */
		@RequestMapping(value = "/api/file/fileInfo/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    FileInfo upload(@RequestPart("file") MultipartFile file,
	                    @RequestParam(name = "bucketType", required = false) String bucketType,
	                    @RequestParam("isEncrypt") String isEncrypt,
	                    @RequestParam("path") String path,
	                    @RequestParam("platform") String platform);

		/**
		 * 获取文件信息
		 * @param file 文件对象
		 * @param bucketType 1:私有读私有写、2：公有读私有写
		 * @param path 路径 + 文件名
		 * @param platform 来源平台
		 * @return
		 */
	    @RequestMapping("/api/file/fileInfo/getFileInfo")
	    public @ResponseBody
	    FileInfo getFileInfo(@RequestParam(name = "bucketType") String bucketType,
	                      @RequestParam("path") String path,
	                      @RequestParam("platform") String platform,
	                        @RequestParam(name="expiration",required = false) Date expiration) ;

	@RequestMapping(value = "/api/file/fileInfo/deleteFile", method = RequestMethod.POST)
	boolean deleteFile(@RequestParam(name = "bucketType", required = false) String bucketType,
					   @RequestParam("path") String path,
					   @RequestParam("platform") String platform);
}
