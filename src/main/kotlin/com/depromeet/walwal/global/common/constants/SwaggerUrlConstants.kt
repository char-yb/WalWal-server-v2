package com.depromeet.walwal.global.common.constants

enum class SwaggerUrlConstants(val value: String) {
    SWAGGER_RESOURCES_URL("/swagger-resources/**"),
    SWAGGER_UI_URL("/swagger-ui/**"),
    SWAGGER_API_DOCS_URL("/v3/api-docs/**"),
    ;

    companion object {
        fun getSwaggerUrls(): Array<String> = entries.map { it.value }.toTypedArray()
    }
}
