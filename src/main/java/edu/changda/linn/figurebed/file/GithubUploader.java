package edu.changda.linn.figurebed.file;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import edu.changda.linn.figurebed.dao.ImageFailRepository;
import edu.changda.linn.figurebed.dao.ImageInfoRepository;
import edu.changda.linn.figurebed.entity.ImageFail;
import edu.changda.linn.figurebed.entity.ImageInfo;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


/**
 * 文件上传工具类
 *
 * @author Linn-cn
 * @create 2020/08/31
 */
@Component
public class GithubUploader {

    private static final Logger log = LoggerFactory.getLogger(GithubUploader.class);

    public static final String URI_SEPARATOR = "/";

    protected static final Set<String> ALLOW_FILE_SUFFIX = new HashSet<>(Arrays.asList(".jpg", ".png", ".jpeg", ".gif"));

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ImageFailRepository imageFailRepository;

    @Autowired
    private ImageInfoRepository imageInfoRepository;

    @Value("${github.accessToken}")
    private String accessToken;

    @Value("${github.api}")
    private String githubApi;

    @Value("${github.url}")
    private String githubUrl;

    @Value("${github.cdn.url}")
    private String cdnUrl;

    public String upload(MultipartFile multipartFile) throws IOException {
        String suffix = this.getSuffix(multipartFile).toLowerCase();
        if (!ALLOW_FILE_SUFFIX.contains(suffix)) {
            String errorMsg = "是不支持的文件后缀：" + suffix;
            throw new InvalidFileNameException(multipartFile.getOriginalFilename(), errorMsg);
        }

        // 重命名文件
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        // 目录按照日期打散
        String[] folders = this.getDateFolder();

        // 最终的文件路径
        String filePath = String.join(URI_SEPARATOR, folders) + URI_SEPARATOR + fileName;

        log.info("上传文件到Github：{}", filePath);

        JsonObject payload = new JsonObject();
        payload.add("message", new JsonPrimitive("file upload"));
        payload.add("content", new JsonPrimitive(Base64.getEncoder().encodeToString(multipartFile.getBytes())));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(githubApi + filePath, HttpMethod.PUT,
                new HttpEntity<>(payload.toString(), httpHeaders), String.class);
        // 上传失败
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String jsdelivrUrl = cdnUrl + filePath;
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImageName(multipartFile.getOriginalFilename());
            imageInfo.setJsdelivrUrl(jsdelivrUrl);
            imageInfo.setGithubUrl(githubUrl + filePath);
            ImageInfo result = imageInfoRepository.save(imageInfo);
            log.info("上传完毕: {}", result.toString());
            // TODO 序列化到磁盘备份
            return jsdelivrUrl;
        } else {
            ImageFail imageFail = new ImageFail();
            imageFail.setImageName(multipartFile.getOriginalFilename());
            imageFail.setErrorInfo(responseEntity.getBody());
            ImageFail result = imageFailRepository.save(imageFail);
            log.info("上传失败: {}", result.toString());
            return "error";
        }
    }

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return java.lang.String
     * @author Linn-cn
     * @date 2020/8/31
     */
    protected String getSuffix(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String suffix = fileName.substring(fileName.lastIndexOf('.'));
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法的文件名称：" + fileName);
    }

    /**
     * 按照年月日获取打散的打散目录
     * yyyy/mmd/dd
     *
     * @return java.lang.String[]
     * @author Linn-cn
     * @date 2020/8/31
     */
    protected String[] getDateFolder() {
        String[] retVal = new String[3];

        LocalDate localDate = LocalDate.now();
        retVal[0] = String.valueOf(localDate.getYear());

        int month = localDate.getMonthValue();
        retVal[1] = month < 10 ? "0" + month : String.valueOf(month);

        int day = localDate.getDayOfMonth();
        retVal[2] = day < 10 ? "0" + day : String.valueOf(day);

        return retVal;
    }
}
