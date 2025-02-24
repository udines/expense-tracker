package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.model.payload.CategoryPayload
import com.udinesfata.expenz.data.model.query.CategoryQuery
import com.udinesfata.expenz.data.model.remote.CategoryResponse
import com.udinesfata.expenz.domain.entity.Category
import com.udinesfata.expenz.domain.entity.params.CategoryParams
import com.udinesfata.expenz.domain.entity.request.CategoryRequest

fun CategoryResponse.toEntity(): Category {
    return Category(
        id = this.id,
        name = this.name,
        type = this.type,
    )
}

fun CategoryResponse.toDb(): CategoryDb {
    return CategoryDb(
        id = this.id,
        name = this.name,
        type = this.type,
        isSynced = true,
    )
}

fun Category.toDb(): CategoryDb {
    return CategoryDb(
        id = this.id,
        name = this.name,
        type = this.type,
        isSynced = false,
    )
}

fun Category.toPayload(): CategoryPayload {
    return CategoryPayload(
        id = this.id,
        name = this.name,
        type = this.type,
    )
}

fun CategoryDb.toEntity(): Category {
    return Category(
        id = this.id,
        name = this.name,
        type = this.type,
    )
}

fun CategoryParams.toQuery(): CategoryQuery {
    return CategoryQuery(
        name = this.name
    )
}

fun CategoryRequest.toEntity(): Category {
    return Category(
        id = 0,
        name = this.name,
        type = this.type,
    )
}