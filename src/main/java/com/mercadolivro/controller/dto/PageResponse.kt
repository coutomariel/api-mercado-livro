package com.mercadolivro.controller.dto

import org.springframework.data.domain.Page

data class PageResponse<T>(
    var items: List<T>,
    var totalPages: Int,
    var totalItems: Long,
    var currentPage: Int,
)


fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        this.content,
        this.totalPages,
        this.totalElements,
        this.number
    )
}