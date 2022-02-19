package top.qiudb.common.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
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

    private final Integer value;

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

    @Override
    public String toString() {
        return this.desc;
    }
}