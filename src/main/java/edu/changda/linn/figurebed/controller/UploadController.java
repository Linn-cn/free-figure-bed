package edu.changda.linn.figurebed.controller;

import edu.changda.linn.figurebed.file.GithubUploader;
import edu.changda.linn.figurebed.util.Result;
import edu.changda.linn.figurebed.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片操作
 *
 * @author Linn-cn
 * @create 2020/08/31
 */
@Api(tags = "图片操作")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private GithubUploader githubUploader;

    @ApiOperation("图片上传")
    @PostMapping("/image")
    public Result<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        String filePath;
        try {
            filePath = githubUploader.upload(multipartFile);
        } catch (Exception e) {
            if (e instanceof InvalidFileNameException){
                String message = "文件名：" + ((InvalidFileNameException) e).getName() + "," + e.getMessage();
                return ResultUtils.ofMsg(ResultUtils.CodeStatusEnum.SERVER_ERROR, message);
            }
            return ResultUtils.ofMsg(ResultUtils.CodeStatusEnum.SERVER_ERROR, e.getMessage());
        }
        return ResultUtils.ofOk(filePath);
    }
}
