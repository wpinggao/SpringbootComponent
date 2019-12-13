package com.wping.component.shield.utils;

import com.wping.component.shield.enums.FileCategoryEnum;
import com.wping.component.shield.enums.FileTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/5/31 15:17
 */
public class FileTypeUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileTypeUtils.class);

    public static final FileTypeEnum[] PICS = {FileTypeEnum.JPEG, FileTypeEnum.PNG, FileTypeEnum.GIF, FileTypeEnum.TIFF, FileTypeEnum.BMP, FileTypeEnum.DWG, FileTypeEnum.PSD};

    public static final FileTypeEnum[] DOCS = {FileTypeEnum.RTF, FileTypeEnum.XML, FileTypeEnum.HTML, FileTypeEnum.CSS, FileTypeEnum.JS, FileTypeEnum.EML, FileTypeEnum.DBX, FileTypeEnum.PST, FileTypeEnum.XLS, FileTypeEnum.DOC, FileTypeEnum.XLSX, FileTypeEnum.DOCX, FileTypeEnum.VSD,
            FileTypeEnum.MDB, FileTypeEnum.WPS, FileTypeEnum.WPD, FileTypeEnum.EPS, FileTypeEnum.PDF, FileTypeEnum.QDF, FileTypeEnum.PWL, FileTypeEnum.ZIP, FileTypeEnum.RAR, FileTypeEnum.JSP, FileTypeEnum.JAVA, FileTypeEnum.CLASS,
            FileTypeEnum.JAR, FileTypeEnum.MF, FileTypeEnum.EXE, FileTypeEnum.CHM, FileTypeEnum.PPTX, FileTypeEnum.PPT, FileTypeEnum.TXT};

    public static final FileTypeEnum[] VIDEOS = {FileTypeEnum.AVI, FileTypeEnum.RAM, FileTypeEnum.RM, FileTypeEnum.RMVB, FileTypeEnum.MPG, FileTypeEnum.MOV, FileTypeEnum.ASF, FileTypeEnum.MP4, FileTypeEnum.FLV, FileTypeEnum.MID};

    public static final FileTypeEnum[] TORRENT = {FileTypeEnum.TORRENT};

    public static final FileTypeEnum[] AUDIOS = {FileTypeEnum.WAV, FileTypeEnum.MP3};

    public static final FileTypeEnum[] OTHERS = {};

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取文件头
     *
     * @param is
     * @return
     */
    private static String getFileHead(InputStream is) {

        byte[] b = new byte[28];
        try {
            is.read(b, 0, 28);
        } catch (IOException e) {
            logger.error("读取文件头失败", e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭文件流失败", e);
                    return null;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 获取文件类型(返回一个)
     *
     * @param is
     * @return
     */
    public static @Nullable FileTypeEnum getFileType(InputStream is) {

        List<FileTypeEnum> fileTypeEnumList = getFileTypes(is);
        if (!CollectionUtils.isEmpty(fileTypeEnumList)) {
            return fileTypeEnumList.get(0);
        }

        return null;
    }

    /**
     * 获取文件类型(返回多个)
     *
     * @param is
     * @return
     */
    public static @Nullable List<FileTypeEnum> getFileTypes(InputStream is) {

        String fileHead = getFileHead(is);
        if (StringUtils.isEmpty(fileHead)) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        List<FileTypeEnum> fileTypeEnumList = new ArrayList<>();

        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileHead.startsWith(fileTypeEnum.getValue())) {
                fileTypeEnumList.add(fileTypeEnum);
            }
        }

        return fileTypeEnumList;
    }

    /**
     * 获取文件名后缀，获取不到时返回空(null) 例如: .png
     * @param is 文件流 注意传进来的文件流会被关闭
     * @return
     */
    @Deprecated
    public static @Nullable String obtainFileNameSuffix(InputStream is) {

        if (is != null) {
            FileTypeEnum fileTypeEnum = getFileType(is);
            if (fileTypeEnum != null) {
                return fileTypeEnum.getFileNameSuffix();
            }
        }
        return null;
    }

    /**
     * 获取文件名后缀，获取不到时返回空(null), 获取到多个时返回 defaultFileNameSuffix, 次方法用于魔数相同文件类型判断
     * 例如: xls, doc的魔数相同，defaultFileNameSuffix为 .xls, 则返回 .xls
     * @param is 文件流 注意传进来的文件流会被关闭
     * @param defaultFileNameSuffix 默认文件后缀名 例如 .xls
     * @return
     */
    public static @Nullable String obtainFileNameSuffix(InputStream is, String defaultFileNameSuffix) {

        if (is != null) {
            List<FileTypeEnum> fileTypeEnumList = getFileTypes(is);
            if (!CollectionUtils.isEmpty(fileTypeEnumList)) {
                for (FileTypeEnum fileTypeEnum : fileTypeEnumList) {
                    if (StringUtils.isNotEmpty(defaultFileNameSuffix) && fileTypeEnum.getFileNameSuffix().equalsIgnoreCase(defaultFileNameSuffix)) {
                        return fileTypeEnum.getFileNameSuffix();
                    }
                }
                return fileTypeEnumList.get(0).getFileNameSuffix();
            }
        }
        return null;
    }

    /**
     * 获取文件名类型，获取不到时返回空(null), 获取到多个时返回 defaultFileNameSuffix, 次方法用于魔数相同文件类型判断
     * 例如: xls, doc的魔数相同，defaultFileNameSuffix为 .xls, 则返回 XLS
     * @param is 文件流 注意传进来的文件流会被关闭
     * @param defaultFileNameSuffix 默认文件后缀名 例如 .xls
     * @return
     */
    public static @Nullable FileTypeEnum obtainFileTypeEnum(InputStream is, String defaultFileNameSuffix) {

        if (is != null) {
            List<FileTypeEnum> fileTypeEnumList = getFileTypes(is);
            if (!CollectionUtils.isEmpty(fileTypeEnumList)) {
                for (FileTypeEnum fileTypeEnum : fileTypeEnumList) {
                    if (StringUtils.isNotEmpty(defaultFileNameSuffix) && fileTypeEnum.getFileNameSuffix().equalsIgnoreCase(defaultFileNameSuffix)) {
                        return fileTypeEnum;
                    }
                }
                return fileTypeEnumList.get(0);
            }
        }
        return null;
    }


    public static Boolean isPic(FileTypeEnum fileTypeEnum) {

        if (fileTypeEnum == null) {
            return false;
        }

        for (FileTypeEnum picEnum : PICS) {
            if (picEnum.equals(fileTypeEnum)) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isFileTypeEnum(FileTypeEnum[] fileTypeEnums, FileTypeEnum fileTypeEnum) {

        if (fileTypeEnum == null || fileTypeEnums == null) {
            return false;
        }

        for (FileTypeEnum picEnum : fileTypeEnums) {
            if (picEnum.equals(fileTypeEnum)) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isFileCategoryEnum(FileCategoryEnum oriFileCategoryEnum, FileTypeEnum fileTypeEnum) {

        if (fileTypeEnum == null || oriFileCategoryEnum == null) {
            return false;
        }

        FileCategoryEnum fileCategoryEnum = getFileCategoryEnum(fileTypeEnum);
        if (fileCategoryEnum != null && fileCategoryEnum.equals(oriFileCategoryEnum)) {
            return true;
        }

        return false;
    }

    public static FileCategoryEnum getFileCategoryEnum(FileTypeEnum fileTypeEnum) {

        if (fileTypeEnum == null) {
            return null;
        }

        if (isFileTypeEnum(FileCategoryEnum.PICS.getFileTypeEnums(), fileTypeEnum)) {
            return FileCategoryEnum.PICS;
        }

        if (isFileTypeEnum(FileCategoryEnum.DOCS.getFileTypeEnums(), fileTypeEnum)) {
            return FileCategoryEnum.DOCS;
        }

        if (isFileTypeEnum(FileCategoryEnum.VIDEOS.getFileTypeEnums(), fileTypeEnum)) {
            return FileCategoryEnum.VIDEOS;
        }

        if (isFileTypeEnum(FileCategoryEnum.TORRENT.getFileTypeEnums(), fileTypeEnum)) {
            return FileCategoryEnum.TORRENT;
        }

        if (isFileTypeEnum(FileCategoryEnum.AUDIOS.getFileTypeEnums(), fileTypeEnum)) {
            return FileCategoryEnum.AUDIOS;
        }

        return FileCategoryEnum.OTHERS;

    }

    public static void main(String args[]) throws Exception {

        String path = "E:\\myProject\\bpm\\异常场景梳理_V02.xlsx";

        System.out.println(FileTypeUtils.obtainFileNameSuffix(new FileInputStream(new File(path))));
        System.out.println(FileTypeUtils.obtainFileNameSuffix(new FileInputStream(new File(path)), ".xlsx"));

    }


}
