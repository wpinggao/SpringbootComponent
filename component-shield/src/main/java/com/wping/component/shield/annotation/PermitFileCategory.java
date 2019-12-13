package com.wping.component.shield.annotation;

import com.wping.component.shield.enums.FileCategoryEnum;

import java.lang.annotation.*;

/**
 * describe: 允许的文件类别验证
 *
 * @author Wping.gao
 * @date 2019/5/14 17:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface PermitFileCategory {

    /**
     * 允许的文件类别
     * @return
     */
    FileCategoryEnum[] value();


}
