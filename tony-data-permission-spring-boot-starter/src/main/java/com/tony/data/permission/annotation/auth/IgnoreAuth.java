package com.tony.data.permission.annotation.auth;

import java.lang.annotation.*;

/**
 * 
 * @ClassName: Pass
 * @Description: 在Controller方法上加入该注解不会验证身份
 * @author tony
 * @date 2019年5月18日 下午11:52:07
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
