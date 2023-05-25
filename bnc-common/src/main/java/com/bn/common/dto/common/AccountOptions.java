package com.bn.common.dto.common;

/**
 * Bit set to store account preference options
 * User: ataylor
 * Date: 4/3/12
 */
public enum AccountOptions {

    NONE,                       //  0       not set
    USE_INTERNAL_LICENSE_KEY;   //  2       use internally generated key for issuing DRM Licenses

    /**
     * Converts bit flag to its int value
     */
    public int getValue() {
        return 1 << ordinal();
    }

    /**
     * Returns the integer value for an EnumSet
     * @param enumSet strongly typed representation of a bit mask
     * @return integer value
     */
    public static Integer encode(Iterable<AccountOptions> enumSet) {
        if(enumSet == null) {
            return null;
        }
        int ret = 0;
        for(AccountOptions value : enumSet) {
            ret |= 1 << value.ordinal();
        }
        return ret;
    }
}
