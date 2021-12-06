package com.tony.common.utils;

import com.tony.common.constant.Constants;
import org.springframework.stereotype.Component;


/**
 * 字典工具类
 *
 * @author tony
 */
@Component
public class DictUtils {
    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 设置字典缓存
     *
     * @param key       参数键
     * @param dictDatas 字典数据列表
     */
//    public static void setDictCache(String key, List<SysDictData> dictDatas) {
//        CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
//    }

    /**
     * 获取字典缓存
     *
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
//    public static List<SysDictData> getDictCache(String key) {
//        Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
//        if (StringUtils.isNotNull(cacheObj)) {
//            List<SysDictData> DictDatas = StringUtils.cast(cacheObj);
//            return DictDatas;
//        }
//        return null;
//    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue) {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel) {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator) {
//        StringBuilder propertyString = new StringBuilder();
//        List<SysDictData> datas = getDictCache(dictType);
//
//        if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas)) {
//            for (SysDictData dict : datas) {
//                for (String value : dictValue.split(separator)) {
//                    if (value.equals(dict.getDictValue())) {
//                        propertyString.append(dict.getDictLabel() + separator);
//                        break;
//                    }
//                }
//            }
//        } else {
//            for (SysDictData dict : datas) {
//                if (dictValue.equals(dict.getDictValue())) {
//                    return dict.getDictLabel();
//                }
//            }
//        }
//        return StringUtils.stripEnd(propertyString.toString(), separator);
        return null;
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator) {
//        StringBuilder propertyString = new StringBuilder();
//        List<SysDictData> datas = getDictCache(dictType);
//
//        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas)) {
//            for (SysDictData dict : datas) {
//                for (String label : dictLabel.split(separator)) {
//                    if (label.equals(dict.getDictLabel())) {
//                        propertyString.append(dict.getDictValue() + separator);
//                        break;
//                    }
//                }
//            }
//        } else {
//            for (SysDictData dict : datas) {
//                if (dictLabel.equals(dict.getDictLabel())) {
//                    return dict.getDictValue();
//                }
//            }
//        }
//        return StringUtils.stripEnd(propertyString.toString(), separator);
        return null;
    }

    /**
     * 删除指定字典缓存
     *
     * @param key 字典键
     */
    public static void removeDictCache(String key) {
//        CacheUtils.remove(getCacheName(), getCacheKey(key));
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache() {
//        CacheUtils.removeAll(getCacheName());
    }

    /**
     * 获取cache name
     *
     * @return 缓存名
     */
    public static String getCacheName() {
        return Constants.SYS_DICT_CACHE;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
