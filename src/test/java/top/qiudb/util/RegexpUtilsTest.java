package top.qiudb.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 正则表达式工具类测试
 */
class RegexpUtilsTest {

    @Test
    void identityCardTest() {
        assertTrue(RegexpUtils.validateInfo("151162188905211415", RegexpUtils.IDENTITY_CARD_REGEXP));
        assertTrue(RegexpUtils.validateInfo("15116218890521141X", RegexpUtils.IDENTITY_CARD_REGEXP));
        assertFalse(RegexpUtils.validateInfo("1511621889052114", RegexpUtils.IDENTITY_CARD_REGEXP));
    }
}
