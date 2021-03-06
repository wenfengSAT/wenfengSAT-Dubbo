package com.dubbo.api.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.CaseFormat;

/**
 * 
 * @Description： 功能描述
 * @author [ Wenfeng.Huang ] on [2019年5月31日上午11:42:41]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public class BeanUtil {
    public static Map<String, Object> object2Map(Object object) {
        Map<String, Object> data = new TreeMap<String, Object>(); // 对key进行排序
        try {
            BeanInfo info = Introspector.getBeanInfo(object.getClass(), Introspector.IGNORE_ALL_BEANINFO);
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : descriptors) {
                // 将 驼峰式写法转成下划线写法
                String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pd.getName());
                Object value = pd.getReadMethod().invoke(object);
                if ("class".equals(name) || value == null)
                    continue;
                data.put(name, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T map2Object(Class<T> clazz, Map<String, Object> data) {
        try {
            T object = clazz.newInstance();
            BeanInfo info = Introspector.getBeanInfo(object.getClass(), Introspector.IGNORE_ALL_BEANINFO);
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : descriptors) {
                String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pd.getName());

                Object value = data.get(name);
                if (value == null)
                    continue;
                pd.getWriteMethod().invoke(object, value);

            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
