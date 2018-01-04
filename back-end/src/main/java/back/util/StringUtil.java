package back.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
  /*
  */

    /**
     * replace {{placeholders}} to their values
     *
     * @param template string with placeholders
     * @param params   placeholder key, placeholder value
     * @return replace result
     */
    public static String usePlaceholders(String template, String... params) {
        if (params != null && template != null) {
            for (int i = 0; i < params.length - 1; i += 2) {
                String key = params[i] == null ? "" : params[i].trim();
                String value = params[i + 1] == null ? "" : params[i + 1].trim();
                template = template.replaceAll("\\{\\{" + key + "\\}\\}", value);
            }
        }
        return template;
    }

    /**
     * Extracts part of a string passed
     *
     * @param text    - whole text
     * @param regexp  - regular expression. text matched inside group markers () will be returned
     * @param groupNo - regexp group index to return (if there's more then one)
     * @return found string or empty line otherwise
     */
    public static String extract(String text, String regexp, int groupNo) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find() && matcher.groupCount() >= groupNo) {
            return matcher.group(groupNo);
        }
        return "";
    }

    public static Properties createParams(String... keyThenValue) {
        Properties properties = new Properties();
        for (int i = 0; i < keyThenValue.length - 1; i += 2) {
            properties.setProperty(keyThenValue[i], keyThenValue[i + 1]);
        }
        return properties;
    }

    /**
     * Trim key then replace spaces in its name to '_'
     *
     * @param key
     * @return
     */
    public static String unspace(String key) {
        return key.trim().replace(' ', '_');
    }

    public static String joinInt(int[] eventIdList, String delim) {
        StringBuffer idList = new StringBuffer();
        for (int id : eventIdList) {
            if (idList.length() > 0)
                idList.append(delim);
            idList.append(id);
        }
        return idList.toString();
    }

    public static String joinStrings(String[] strList, String delim) {
        StringBuffer result = new StringBuffer();
        for (String value : strList) {
            if (result.length() > 0)
                result.append(delim);
            result.append(value);
        }
        return result.toString();
    }
}
