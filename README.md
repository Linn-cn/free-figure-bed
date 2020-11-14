# 使用GitHub + jsDelivr CDN全球加速的免费图床

## 创建一个Github仓库
仓库的权限必须是public的

## 在个人设置中生成 Access Token
![image](https://springboot.io/uploads/default/original/2X/b/ba40b6bb02331d2c741128bcf619d415f3a11f21.png)

**repo权限全部勾上**

![image](https://springboot.io/uploads/default/original/2X/3/339cd2f7d4cf27c56bf01b98a50e4490541102f5.png)

**这里一定要记录下生成的Token**

## 使用SpringBoot上传图片

### SpringBoot的yml文件中添加配置

```yaml
github:
  # 配置仓库所属的用户名（如果是自己创建的，就是自己的用户名）
  user: "Linn-cn"
  # 配置仓库名称
  repository: "image-bucket"
  accessToken: "你的Github访问Token"
  api: https://api.github.com/repos/${github.user}/${github.repository}/contents/
  url: https://raw.githubusercontent.com/${github.user}/${github.repository}/master/
  cdn:
    url: https://cdn.jsdelivr.net/gh/${github.user}/${github.repository}/
```

根据上面的配置进行文件的上传，具体逻辑请看`GithubUploader`类

```java
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
```

这里就是通过调用Github的API来上传图片并返回路径，然后将路径替换成cdn路径，如果上传失败了之后会放到数据库做一个记录。

> 数据库表文件在resource/sql/目录下，一张上传信息表，一张失败信息表

### 举例

我们这里通过 jsdelivr cdn 来访问，在使用GitHub 图床图片的地方将链接换为https://cdn.jsdelivr.net/gh/{user}/{repo}/图片路径

**效果如下：**
Github中图片的位置：https://raw.githubusercontent.com/Linn-cn/image-bucket/master/2020/11/12/cfec132b6e4c44ef812ea0912ed4c860.jpg

使用jsdelivr访问的URL：https://cdn.jsdelivr.net/gh/Linn-cn/image-bucket/2020/11/12/cfec132b6e4c44ef812ea0912ed4c860.jpg

> jsDelivr 还支持加载指定文件版本和自动压缩 JS，具体用法可以参考[官方教程](https://www.jsdelivr.com/features)。
>
> jsDeliver CDN 单个文件好像有一定的大小限制。GitHub 仓库大小建议是1GB，硬性限制是 100GB。

## 致谢
参考文献：

* [在SpringBoot中通过RestTemplate提交文件到Github 作者：KevinBlandy](https://springboot.io/t/topic/2077)
* [GitHub + jsDelivr CDN 全球加速的免费图床，它不香吗？作者：KevinBlandy](https://springboot.io/t/topic/1561)

**还有什么疑问欢迎通过Github主页的联系方式 => 联系我**

