package com.wping.component.shield.aspect;

import com.wping.component.shield.annotation.PermitFileType;
import com.wping.component.shield.enums.FileTypeEnum;
import com.wping.component.shield.exception.FileTypeException;
import com.wping.component.shield.utils.FileTypeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * describe: 文件类型切面
 *
 * @author Wping.gao
 * @date 2019/5/31 16:29
 */
@Aspect
public class PermitFileTypeAspect {

    private static final Logger logger = LoggerFactory.getLogger(PermitFileTypeAspect.class);

    @Before("@annotation(permitFileType)")
    public void before(JoinPoint point, PermitFileType permitFileType) {

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
            FileTypeEnum[] fileTypeEnums = permitFileType.value();
            for (MultipartFile multipartFile : multipartFiles) {
                List<FileTypeEnum> fileTypeEnumList = FileTypeUtils.getFileTypes(multipartFile.getInputStream());
                if (CollectionUtils.isEmpty(fileTypeEnumList)) {
                    logger.error("文件类型验证失败, 未获取到文件类型");
                    throw new FileTypeException("文件类型验证失败, 未获取到文件类型");
                }

                Boolean checkFlag = false;
                StringBuilder sb = new StringBuilder();
                for (FileTypeEnum fileTypeEnum : fileTypeEnumList) {
                    sb.append(fileTypeEnum + "/");
                    if (FileTypeUtils.isFileTypeEnum(fileTypeEnums, fileTypeEnum)) {
                        checkFlag = true;
                        break;
                    }
                }

                String fileTypeString = sb.substring(0, sb.length() - 1);
                if (!checkFlag) {
                    logger.error("文件类型验证失败, 不支持文件类型:{}", fileTypeString);
                    throw new FileTypeException("文件类型验证失败, 不支持文件类型:" + fileTypeString);
                }
            }

        } catch (FileTypeException fte) {
            throw fte;
        } catch (Exception e) {
            logger.error("文件类型验证未知异常", e);
            throw new FileTypeException("文件类型验证未知异常");
        }

    }

}
