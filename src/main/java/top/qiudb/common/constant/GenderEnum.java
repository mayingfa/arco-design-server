package top.qiudb.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import top.qiudb.common.domain.BaseEnum;


public enum GenderEnum implements IEnum<Integer>, BaseEnum<Integer> {
    /**
     * 女 0
     */
    WOMEN(0, "女"),

    /**
     * 男 1
     */
    MAN(1, "男");

    @EnumValue
    private final Integer value;

    @JsonValue
    private final String desc;

    GenderEnum(Integer value, String desc) {
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
}