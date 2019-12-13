# 防攻击组件(shield)

特性
1. xss防护
2. 上传文件类型验证

如何使用

1. 添加配置

wping:

  shield:
  
    xss-filter-url-patterns: /auth/*
    
2. 上传文件类型验证使用, 请求方面入参必须有 MultipartFile 或者 MultipartFile[], 否则注解无效
通过判断文件头（魔数）来判断文件类型
通过注解 @PermitFileCategory 可以指定文件类别
    
    PICS("图片", FileTypeUtils.PICS),
    DOCS("文档", FileTypeUtils.DOCS),
    VIDEOS("视频", FileTypeUtils.VIDEOS),
    TORRENT("种子", FileTypeUtils.TORRENT),
    AUDIOS("音乐", FileTypeUtils.AUDIOS),
    OTHERS("其他", FileTypeUtils.OTHERS),
    
或者通过注解 @PermitFileType 指定文件类型 如FileTypeEnum.PNG 等

     



    
