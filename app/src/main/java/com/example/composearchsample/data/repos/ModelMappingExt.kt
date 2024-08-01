package com.example.composearchsample.data.repos

import com.example.composearchsample.data.local.user.LocalUser
import com.example.composearchsample.data.network.NetworkUser
import com.example.composearchsample.data.uimodels.UserUiModel

fun LocalUser.toNetwork() = NetworkUser(
    id = id,
    name = name,
    email = email
)

fun List<LocalUser>.toNetwork() = map(LocalUser::toNetwork)

fun UserUiModel.toLocal() = LocalUser(
    id = id,
    name = name,
    email = email
)

fun LocalUser.toUiUserModel() = UserUiModel(
    id = id,
    name = name,
    email = email
)

fun List<LocalUser>.toUiUserModels() = map(LocalUser::toUiUserModel)
