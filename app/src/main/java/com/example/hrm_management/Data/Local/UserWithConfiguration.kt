package com.example.hrm_management.Data.Local

import androidx.room.Embedded
import androidx.room.Relation


class UserWithConfigurations {
    @Embedded
    var user: User? = null

    @Relation(parentColumn = "UserID", entityColumn = "UserID")
    var configurations: List<ConfigurationList>? = null
}