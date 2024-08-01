package com.example.composearchsample.data.local

/**
 * Created by Kethu on 26/06/2024.
 */
object DbConstants {
    const val USER_DATABASE_NAME = "users.db"
    const val USER_TABLE_NAME = "user"
    const val INVENTORY_DATABASE_NAME = "inventory.db"
    const val INVENTORY_TABLE_NAME = "inventory"
    const val INVENTORIES_QUERY_BY_ID = "SELECT * from $INVENTORY_TABLE_NAME WHERE id = :id"
    const val INVENTORIES_QUERY_BY_NAME_IN_ASC = "SELECT * from $INVENTORY_TABLE_NAME ORDER BY name ASC"

}