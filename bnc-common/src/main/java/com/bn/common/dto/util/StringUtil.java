package com.bn.common.dto.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.StringTokenizer;

@Slf4j
public class StringUtil {

    private static final String DELIMITER = ", ";

    /**
     * prevent object use
     */
    private StringUtil() {
    }

    /**
     * Tests if a string is empty or not
     *
     * @param str string to test
     * @return true if the string is empty otherwise false
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * To check the given string is not empty.
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        if(StringUtils.isNotEmpty(string) && !"null".equalsIgnoreCase(string) && StringUtils.isNotBlank(string)) {
            return true;
        }
        return false;
    }

    public static <T> String toFormattedString(Collection<T> params, String separator) {
        if (params == null || params.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for (T param : params) {
            if (param != null) {
                sb.append(param).append(separator);
            }
        }

        if (sb.length() > 0)
            sb.deleteCharAt(sb.length()-separator.length());

        return sb.toString();
    }

    public static String[] parseIt(String value, String delimiter) {
        String[] result = null;

        if (value != null && value.length() > 0
                && delimiter != null && delimiter.length() > 0) {
            StringTokenizer st = new StringTokenizer(value, delimiter);
            result = new String[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                result[i] = st.nextToken();
                i++;
            }
        }

        return result;
    }

    private static void appendQuotedString(StringBuilder sb, String value) {
        // strings look awkward with {} delimiters
        //String strval = value.replaceAll("[\\\"]", "\\\\$1");  jkirby: not sure what the goal was here but this was causing an IndexOutOfBoundsException
        sb.append('"').append(value).append('"');
    }

    private static void appendSimpleValue(StringBuilder sb, Object value) {
        String strval = value.toString();
        if (strval.indexOf(' ') >= 0 || strval.indexOf(',') >= 0
                || strval.indexOf('=') >= 0) {
            sb.append('{').append(strval).append('}');
        }
        else
            sb.append(strval);
    }

    public static void appendField(StringBuilder sb, String name, String value) {
        if (value == null)
            return;
        if (sb.length() != 0)
            sb.append(DELIMITER);
        sb.append(name).append('=');
        appendQuotedString(sb, value);
    }

    public static void appendField(StringBuilder sb, String name, Object value) {
        if (value == null)
            return;
        if (sb.length() != 0)
            sb.append(DELIMITER);
        sb.append(name).append('=');
        appendSimpleValue(sb, value);
    }

    public static String removeSpaces(String x) {
        String y = x;

        if (x != null) {
            y = x.replaceAll(" ", "").trim();
        }

        return y;
    }

    public static String emptyToNull(String x) {
        return "".equals(x) ? null : x;
    }

    public static String nullToEmpty(String x) {
        return x == null ? "" : x;
    }

    /**
     * A method to return the boolean representation of a string value
     * @param  value the string parameter to be evaluated
     * @return true if input value is non-null and either "y" or "true" or "1" all case-insensitive
     */
    public static boolean getBoolean(String value) {
        return value!=null && ("y".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value));
    }

    /**
     * Trim the string to the given number of bytes.
     * Account for the fact that UTF8 characters will take 1 to 4 bytes.
     * @param s string
     * @param maxBytes result string will be this many bytes or less. ignored if not a positive number
     * @return new string that has byte length <= given length
     */
    public static String abbreviateBytes(String s, int maxBytes) {
        // do nothing if string is null or maxBytes is not positive or
        // even if it's all UTF8, it still will be short enough if it's less than a quarter the maxBytes
        if(s == null || maxBytes <= 0 || s.length() <= maxBytes / 4) {
            return s;
        }
        final String out = truncateWhenUTF8(s, maxBytes);
        log.debug(out);
        return out;
    }

    private static String truncateWhenUTF8(String s, int maxBytes) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            // ranges from http://en.wikipedia.org/wiki/UTF-8
            int skip = 0;
            final int more;
            if(s.charAt(i) <= 0x007f) {
                more = 1;
            }
            else if(s.charAt(i) <= 0x07FF) {
                more = 2;
            }
            else if(s.charAt(i) <= 0xd7ff) {
                more = 3;
            }
            else if(s.charAt(i) <= 0xDFFF) {
                // surrogate area, consume next char as well
                more = 4;
                skip = 1;
            }
            else {
                more = 3;
            }
            if(count + more > maxBytes) {
                return s.substring(0, i);
            }
            count += more;
            i += skip;
        }
        return s;
    }

    /**
     * Truncates a string to a given string. Leaves the string untouched if its length is less than maxLength.
     *
     * @param str a string to trancate.
     * @param maxLength the maximum length of the resulting string.
     * @return a truncated string
     */
    public static String truncate(final String str, final int maxLength) {

        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, Math.min(str.length(), maxLength));
    }

    /**
     * Get a string as an int. A null, empty or non-numeric string will return 0
     * @param s
     * @return The int value of a string. 0 if s is null, empty or cannot be converted to an integer
     *
     */
    public static int getIntValue(String s) {
        int returnVal = 0;
        if (StringUtil.isEmpty(s)) return returnVal;
        try{
            returnVal = Integer.parseInt(s);
        }catch(Exception e){
            returnVal = 0;
        }
        return returnVal;
    }

    /**
     * Concatenate the two strings with the separator in the middle if they are not blank
     * @param x first string
     * @param y second string
     * @param separator what to separate them with
     */
    public static String join(String x, String y, String separator) {
        final boolean xBlank = StringUtils.isBlank(x);
        final boolean yBlank = StringUtils.isBlank(y);
        if(xBlank && yBlank) {
            return null;
        }
        if(xBlank) {
            return y;
        }
        if(yBlank) {
            return x;
        }
        return x + separator + y;
    }

    /**
     * Join the two strings with only one forward slash "/" between them regardless whether each give string starts or ends with a forward slash
     * @param baseUrl something like "http://csqa.barnesandnoble.com/bncloud"
     * @param urlPath something like "earnBadges/customer/library"
     * @param connector "/" or "?"
     * @return for example "http://csqa.barnesandnoble.com/bncloud/earnBadges/customer/library"
     */
    public static String joinUrlParts(String baseUrl, String urlPath, String connector) {
        String url = baseUrl;
        if(!url.endsWith(connector)) {
            url += connector;
        }
        if(urlPath.startsWith(connector)) {
            url += urlPath.substring(1);
        }
        else {
            url += urlPath;
        }
        return url;
    }

    /**
     * Strips out all characters that are not valid in xml 1.0. Strips all chars that are not #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
     * @param str any String
     * @return input string minus any control characters, null if input str is null
     */
    public static String stripInvalidXMLCharacters(String str) {
        if (str == null) return null;

        StringBuffer s = new StringBuffer();
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if ((c == 0x9) || (c == 0xA) || (c == 0xD)
                    || ((c >= 0x20) && (c <= 0xD7FF))
                    || ((c >= 0xE000) && (c <= 0xFFFD))
                    || ((c >= 0x10000) && (c <= 0x10FFFF))) {

                s.append(c);
            }
        }
        return s.toString();
    }

    // TODO [gfeigenson@book.com 1/21/14] - Replace this with StringUtils.defaultIfBlank when we move to commons-lang 3.x

    /**
     * Provides a temporary implementation to default in the case a string is blank. We do not currently get this from
     * commons-lang because we're using a pretty old version... This will eventually be in the 3.x line when we upgrade.
     *
     * @param inputString The input string to check to see if it is considered blank by {@link StringUtils#isBlank(CharSequence)}}.
     * @param defaultString The string to return if the input string is determined to be blank.
     *
     * @return The input string, if not blank, else the default value.
     */
    public static String defaultIfBlank(final String inputString, final String defaultString) {
        return StringUtils.isBlank(inputString) ? defaultString : inputString;
    }

    /**
     * Efficiently checks that the given string is numeric by not relying on an Exception being thrown
     * in cases where the string is not numeric.
     *
     * This method detects both integer strings and floating point strings as being numeric.
     *
     * @param str
     *
     * @return true if the input string is numeric
     */
    public static boolean isStringNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        boolean isMinus = (str.charAt(0) == '-');

        if ((isMinus && str.length() < 2) || ((!isMinus) && !(Character.isDigit(str.charAt(0)) || '.' == str.charAt(0) || ',' == str.charAt(0)))) {
            return false;
        }

        boolean isDecimalSeparatorFound = false;

        for (char c : str.substring(1).toCharArray()) {
            if (!Character.isDigit(c) ) {
                if ((c == ',' || c == '.') && !isDecimalSeparatorFound ) {
                    isDecimalSeparatorFound = true;
                    continue;
                }

                return false;
            }
        }
        return true;
    }

    /**
     * To convert the given string to float.
     *
     * @param str
     * @return
     */
    public static float getFloatValue(String str) {
        float value = 0;
        if(isStringNumeric(str)) {
            value = Float.valueOf(str.trim()).floatValue();
        }
        return value;
    }
}
