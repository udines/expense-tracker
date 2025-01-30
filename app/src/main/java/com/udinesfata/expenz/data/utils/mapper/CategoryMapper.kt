package com.udinesfata.expenz.data.utils.mapper

import com.udinesfata.expenz.data.model.local.CategoryDb
import com.udinesfata.expenz.data.model.remote.CategoryResponse
import com.udinesfata.expenz.domain.entity.Category

class CategoryMapper {
    fun responseToEntity(response: CategoryResponse): Category {
        return Category(
            id = response.id,
            name = response.name,
            type = response.type,
        )
    }

    fun responseToDb(response: CategoryResponse): CategoryDb {
        return CategoryDb(
            id = response.id,
            name = response.name,
            type = response.type,
            isSynced = true,
        )
    }

    fun entityToDb(entity: Category): CategoryDb {
        return CategoryDb(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            isSynced = false,
        )
    }

    fun dbToEntity(db: CategoryDb): Category {
        return Category(
            id = db.id,
            name = db.name,
            type = db.type,
        )
    }
}