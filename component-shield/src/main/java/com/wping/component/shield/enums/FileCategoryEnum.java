package com.wping.component.shield.enums;

import com.wping.component.shield.utils.FileTypeUtils;

/**
 * describe: 文件大类别
 *
 * @author Wping.gao
 * @date 2019/5/31 15:39
 */
public enum FileCategoryEnum {

    PICS("图片", FileTypeUtils.PICS),
    DOCS("文档", FileTypeUtils.DOCS),
    VIDEOS("视频", FileTypeUtils.VIDEOS),
    TORRENT("种子", FileTypeUtils.TORRENT),
    AUDIOS("音乐", FileTypeUtils.AUDIOS),
    OTHERS("其他", FileTypeUtils.OTHERS),
    ;

    private String desc;
    private FileTypeEnum[] fileTypeEnums;

    FileCategoryEnum(String desc, FileTypeEnum[] fileTypeEnums) {
        this.desc = desc;
        this.fileTypeEnums = fileTypeEnums;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public FileTypeEnum[] getFileTypeEnums() {
        return fileTypeEnums;
    }

    public void setFileTypeEnums(FileTypeEnum[] fileTypeEnums) {
        this.fileTypeEnums = fileTypeEnums;
    }
}
