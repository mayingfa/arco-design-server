package top.qiudb.common.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import top.qiudb.common.domain.BaseEnum;


public enum SexEnum implements IEnum<Integer>, BaseEnum<Integer> {
    /**
     * 女 0
     */
    WOMEN(0, "女"),

    /**
     * 男 1
     */
    MAN(1, "男");

    private final Integer value;

    private final String desc;

    SexEnum(Integer value, String desc) {
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

    /**
     * 注意要重写此方法，不然会将值转换成 ‘MAN’，而不是 ‘男’
     */
    @Override
    public String toString() {
        return this.desc;
    }
}