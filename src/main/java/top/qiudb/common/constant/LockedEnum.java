package top.qiudb.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import top.qiudb.common.domain.BaseEnum;

/**
 * 账号锁定
 */
public enum LockedEnum implements IEnum<Integer>, BaseEnum<Integer> {
    /**
     * 未锁定状态 0
     */
    NOT_LOCKED(0, "未锁定"),

    /**
     * 已锁定状态 1
     */
    LOCKED(1, "已锁定");

    @EnumValue
    private final Integer value;

    @JsonValue
    private final String desc;

    LockedEnum(Integer value, String desc) {
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