package com.example.eventproject.cache

import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.util.StringUtils
import java.lang.reflect.Method

class KeyGenerator: KeyGenerator {

    override fun generate(target: Any, method: Method, vararg params: Any?): Any {
        return "${target.javaClass.simpleName}_${method.name}_${StringUtils.arrayToDelimitedString(params, "_")}"
    }

}