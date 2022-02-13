package top.qiudb.common.domain;

import java.io.Serializable;
import java.util.Objects;

public interface BaseEnum<T extends Serializable> {

    /**
     * 获取枚举类的值
     *
     * @return T
     */
    T getValue();

    /**
     * 获取枚举类的说明
     *
     * @return String
     */
    String getDesc();

    /**
     * 比较参数是否与枚举类的value相同
     *
     * @param value 枚举类值
     * @return boolean
     */
    default boolean equalsValue(T value) {
        return Objects.equals(getValue(), value);
    }

    /**
     * 比较枚举类是否相同
     *
     * @param baseEnum 被比较的枚举类
     * @return boolean
     */
    default boolean equals(BaseEnum<T> baseEnum) {
        return Objects.equals(getValue(), baseEnum.getValue())
                && Objects.equals(getDesc(), baseEnum.getDesc());
    }

}
