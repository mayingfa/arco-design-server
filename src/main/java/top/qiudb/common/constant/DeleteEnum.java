package top.qiudb.common.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import top.qiudb.common.domain.BaseEnum;

/**
 * 逻辑删除
 */
public enum DeleteEnum implements IEnum<Integer>, BaseEnum<Integer> {
    /**
     * 未删除状态 0
     */
    NOT_DELETE(0, "未删除"),

    /**
     * 已删除状态 1
     */
    DELETE(1, "已删除");

    private final Integer value;

    private final String desc;

    DeleteEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
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