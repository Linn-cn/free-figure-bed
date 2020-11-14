# 一个GitHub + jsDelivr CDN全球加速的免费图床

## 创建一个Github仓库
仓库的权限必须是public的

### 在个人设置中生成 Access Token
![image](https://springboot.io/uploads/default/original/2X/b/ba40b6bb02331d2c741128bcf619d415f3a11f21.png)

**repo权限全部勾上**

![image](https://springboot.io/uploads/default/original/2X/3/339cd2f7d4cf27c56bf01b98a50e4490541102f5.png)

**记录下生成的Token**

通过 jsdelivr cdn 来访问，在使用GitHub 图床图片的地方将链接换为

> https://cdn.jsdelivr.net/gh/{user}/{repo}/图片路径

例如：
Github中图片的位置：https://api.github.com/repos/Linn-cn/image-bucket/contents/2020/11/12/7cba79d45016418790709c9c9f3ee2f3.jpg

https://api.github.com/repos/Linn-cn/image-bucket/contents/2020/11/12/7cba79d45016418790709c9c9f3ee2f3.jpg

使用jsdelivr访问的URL：https://cdn.jsdelivr.net/gh/Linn-cn/image-bucket/2020/11/12/7cba79d45016418790709c9c9f3ee2f3.jpg

## 最后
jsDelivr 还支持加载指定文件版本和自动压缩 JS，具体用法可以参考[官方教程](https://www.jsdelivr.com/features)。

jsDeliver CDN 单个文件好像有一定的大小限制。GitHub 仓库大小建议是1GB，硬性限制是 100GB。可劲儿造吧。

