package com.bn.common.util;

import com.bn.common.dto.util.ToStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public final class ExpressionUtil {

    private ExpressionUtil() {
    }

    public static boolean isValidValue(String value, String regex) {
        try {
            return StringUtils.isNotEmpty(value) && value.matches(regex);
        }
        catch(Exception e) {
            log.error("Error during regex evaluation, value=" + ToStringUtil.wrapDoubleQuotes(value)
                + ", regEx=" + ToStringUtil.wrapDoubleQuotes(regex), e);
        }
        return false;
    }
}
