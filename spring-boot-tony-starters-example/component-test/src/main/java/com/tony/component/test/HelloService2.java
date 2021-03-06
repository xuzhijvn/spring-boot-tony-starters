/*
 *       Copyright© (2020).
 */
package com.tony.component.test;

import com.tony.component.annotation.ThreadLocalCleanAfter;
import org.springframework.stereotype.Service;

/**
 * @author tony
 * @create 2021-07-29
 * @description:
 */
@Service
public class HelloService2 {

    @ThreadLocalCleanAfter
    public void handle(){
        System.out.println("定时处理");
    }
}
