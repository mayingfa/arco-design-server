package top.qiudb.common.constant;

import top.qiudb.common.domain.BaseEnum;


public enum FileStorageEnum implements BaseEnum<String> {
    /**
     * 头像文件夹
     */
    AVATAR("avatar", "存储头像图片的文件夹"),

    /**
     * 图像文件夹
     */
    IMAGE("image", "存储图像的文件夹"),

    /**
     * 文档文件夹
     */
    DOCUMENT("document", "存储文档的文件夹");

    private final String value;

    private final String desc;

    FileStorageEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}