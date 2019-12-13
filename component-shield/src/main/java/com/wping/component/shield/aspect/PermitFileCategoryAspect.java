package com.wping.component.shield.aspect;

import com.wping.component.shield.annotation.PermitFileCategory;
import com.wping.component.shield.enums.FileCategoryEnum;
import com.wping.component.shield.enums.FileTypeEnum;
import com.wping.component.shield.exception.FileTypeException;
import com.wping.component.shield.utils.FileTypeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * describe: 文件类别切面
 *
 * @author Wping.gao
 * @date 2019/5/31 16:29
 */
@Aspect
public class PermitFileCategoryAspect {

    private static final Logger logger = LoggerFactory.getLogger(PermitFileCategoryAspect.class);

    @Before("@annotation(permitFileCategory)")
    public void before(JoinPoint point, PermitFileCategory permitFileCategory) {

        MultipartFile[] multipartFiles = null;
        Object[] objects = point.getArgs();
        for (Object object : objects) {
            if (object instanceof MultipartFile) {
                multipartFiles = new MultipartFile[1];
                multipartFiles[0] = (MultipartFile) object;
                break;
            }

            if (object instanceof MultipartFile[]) {
                multipartFiles = (MultipartFile[]) object;
                break;
            }
        }

        if (multipartFiles == null) {
            logger.warn("获取MultipartFile失败, 错误使用");
            return;
        }

        try {
            FileCategoryEnum[] fileCategoryEnums = permitFileCategory.value();
            for (MultipartFile multipartFile : multipartFiles) {
                FileTypeEnum fileTypeEnum = FileTypeUtils.getFileType(multipartFile.getInputStream());
                if (fileTypeEnum == null && ".TXT".equalsIgnoreCase(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")))) {
                    fileTypeEnum = FileTypeEnum.TXT;
                }

                boolean flag = false;
                for (FileCategoryEnum fileCategoryEnum : fileCategoryEnums) {
                    if (FileTypeUtils.isFileCategoryEnum(fileCategoryEnum, fileTypeEnum)) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    logger.error("文件类别验证失败, 不支持文件类型:{}", fileTypeEnum);
                    throw new FileTypeException("文件类别验证失败, 不支持文件类型:" + fileTypeEnum);
                }
            }

        } catch (FileTypeException fte) {
            throw fte;
        } catch (Exception e) {
            logger.error("文件类别验证未知异常", e);
            throw new FileTypeException("文件类别验证未知异常");
        }

    }

}
