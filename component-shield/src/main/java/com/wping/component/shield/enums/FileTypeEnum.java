package com.wping.component.shield.enums;

import org.apache.commons.lang.StringUtils;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/5/31 15:12
 */
public enum FileTypeEnum {

    /**
     * JEPG.
     */
    JPEG("FFD8FF", ".jpeg"),

    /**
     * PNG.
     */
    PNG("89504E47", ".png"),

    /**
     * GIF.
     */
    GIF("47494638", ".gif"),

    /**
     * TIFF.
     */
    TIFF("49492A00", ".tif"),

    /**
     * Windows Bitmap.
     */
    BMP("424D", ".bmp"),

    /**
     * CAD.
     */
    DWG("41433130", ".dwg"),

    /**
     * Adobe Photoshop.
     */
    PSD("38425053", ".psd"),

    /**
     * Rich Text Format.
     */
    RTF("7B5C727466", ".rtf"),

    /**
     * XML.
     */
    XML("3C3F786D6C", ".xml"),

    /**
     * HTML.
     */
    HTML("68746D6C3E", ".html"),
    /**
     * CSS.
     */
    CSS("48544D4C207B0D0A0942", ".css"),
    /**
     * JS.
     */
    JS("696B2E71623D696B2E71", ".js"),
    /**
     * Email [thorough only].
     */
    EML("44656C69766572792D646174653A", ".eml"),

    /**
     * Outlook Express.
     */
    DBX("CFAD12FEC5FD746F", ".dbx"),

    /**
     * Outlook (pst).
     */
    PST("2142444E", ".pst"),

    /**
     * MS Word/Excel.
     */
    XLS("D0CF11E0", ".xls"),

    DOC("D0CF11E0", ".doc"),

    PPT("D0CF11E0", ".ppt"),

    XLSX("504B0304", ".xlsx"),

    DOCX("504B0304", ".docx"),

    PPTX("504B0304", ".pptx"),

    /**
     * Visio
     */
    VSD("d0cf11e0a1b11ae10000", ".vsd"),
    /**
     * MS Access.
     */
    MDB("5374616E64617264204A", ".mdb"),
    /**
     * WPS文字wps、表格et、演示dps都是一样的
     */
    WPS("d0cf11e0a1b11ae10000", ".wps"),
    /**
     * torrent
     */
    TORRENT("6431303A637265617465", ".torrent"),
    /**
     * WordPerfect.
     */
    WPD("FF575043", ".wpd"),

    /**
     * Postscript.
     */
    EPS("252150532D41646F6265", ".eps"),

    /**
     * Adobe Acrobat.
     */
    PDF("255044462D312E", ".pdf"),

    /**
     * Quicken.
     */
    QDF("AC9EBD8F", ".qdf"),

    /**
     * Windows Password.
     */
    PWL("E3828596", ".pwl"),

    /**
     * ZIP Archive.
     */
    ZIP("504B0304", ".zip"),

    /**
     * RAR Archive.
     */
    RAR("52617221", ".rar"),
    /**
     * JSP Archive.
     */
    JSP("3C2540207061676520", ".jsp"),
    /**
     * JAVA Archive.
     */
    JAVA("7061636B61676520", ".java"),
    /**
     * CLASS Archive.
     */
    CLASS("CAFEBABE0000002E00", ".class"),
    /**
     * JAR Archive.
     */
    JAR("504B03040A000000", ".jar"),
    /**
     * MF Archive.
     */
    MF("4D616E69666573742D56", ".mf"),
    /**
     *EXE Archive.
     */
    EXE("4D5A9000030000000400", ".exe"),
    /**
     *CHM Archive.
     */
    CHM("49545346030000006000", ".chm"),
    /*
     * INI("235468697320636F6E66"), SQL("494E5345525420494E54"), BAT(
     * "406563686F206f66660D"), GZ("1F8B0800000000000000"), PROPERTIES(
     * "6C6F67346A2E726F6F74"), MXP(
     * "04000000010000001300"),
     */
    /**
     * Wave.
     */
    WAV("57415645", ".wav"),

    /**
     * AVI.
     */
    AVI("41564920", ".avi"),

    /**
     * Real Audio.
     */
    RAM("2E7261FD", ".ram"),

    /**
     * Real Media.
     */
    RM("2E524D46", ".rm"),

    /** Real Media (rm) rmvb/rm相同  */
    RMVB("2E524D46000000120001", ".rmvb"),

    /**
     * MPEG (mpg).
     */
    MPG("000001BA", ".mpg"),

    /**
     * Quicktime.
     */
    MOV("6D6F6F76", ".mov"),

    /**
     * Windows Media.
     */
    ASF("3026B2758E66CF11", ".asf"),

    /**
     * MIDI.
     */
    MID("4D546864", ".mid"),
    /**
     * MP4.
     */
    MP4("00000020667479706973", ".mp4"),
    /**
     * MP3.
     */
    MP3("49443303000000002176", ".mp3"),

    TXT("TXT----TXT", ".txt"),

    /**
     * FLV.
     */
    FLV("464C5601050000000900", ".flv");

    private String value;
    private String fileNameSuffix;

    FileTypeEnum(String value, String fileNameSuffix) {
        this.value = value;
        this.fileNameSuffix = fileNameSuffix;
    }

    public static FileTypeEnum getFileTypeEnum(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.getValue().equalsIgnoreCase(value)) {
                return fileTypeEnum;
            }
        }

        return null;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public void setFileNameSuffix(String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }
}
