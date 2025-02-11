package org.raven.api.codegen.util;


import org.raven.commons.util.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liangyi
 * @date 2018/8/28
 */
public abstract class GenerationUtils {

    /**
     * @param value
     * @param set
     * @return
     */
    public static boolean match(String value, Set<String> set) {
        for (String item : set) {
            if (Pattern.matches(item, value)) return true;
        }

        return false;
    }

    /**
     * @param value
     * @param hashMap
     * @return
     */
    public static String replaceFixedName(String value, Map<String, String> hashMap) {

        if (hashMap == null || hashMap.isEmpty())
            return value;
        if (StringUtils.isEmpty(value))
            return value;

        for (Map.Entry<String, String> item : hashMap.entrySet()) {

            String res = value.replace(item.getKey(), item.getValue());
            if (!res.equals(value))
                return res;
        }

        return value;
    }

    public static String replaceName(String value, Map<String, String> nameMap) {

        if (value == null || value.isEmpty() || nameMap == null || nameMap.isEmpty()) {
            return value;
        }

        for (Map.Entry<String, String> entry : nameMap.entrySet()) {

            String pattern = entry.getKey();
            String replacement = entry.getValue();

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(value);

            if (matcher.matches()) {
                return matcher.replaceAll(replacement);
            }
        }

        return value;
    }

    public static String extractClassName(String typeName) {
        int index = typeName.lastIndexOf(".");
        if (index > -1) {

            return typeName.substring(index + 1);

        }

        return typeName;
    }


    public static String extractPackageName(String typeName) {
        int index = typeName.lastIndexOf(".");
        if (index > -1) {

            return typeName.substring(0, index);
        } else {
            return "";
        }

    }

    public static String pascalCase(String value) {

        char[] _temp = value.toCharArray();
        _temp[0] = Character.toUpperCase(_temp[0]);
        return new String(_temp);

    }

    public static String camelCase(String value) {

        try {
            char[] _temp = value.toCharArray();
            _temp[0] = Character.toLowerCase(_temp[0]);
            return new String(_temp);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;

        }

    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
