package com.wping.component.shield.annotation;

import com.wping.component.shield.enums.FileTypeEnum;

import java.lang.annotation.*;

/**
 * describe: 允许的文件类型验证
 *
 * @author Wping.gao
 * @date 2019/5/14 17:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface PermitFileType {

    /**
     * 允许的文件类别
     * @return
     */
    FileTypeEnum[] value();


}
