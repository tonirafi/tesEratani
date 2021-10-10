package com.template.project.exception

/**
 * 定义
 */
class ApiException(throwable: Throwable?, val code: Int) : RuntimeException(throwable) {
    @JvmField
    var msg: String? = null

}