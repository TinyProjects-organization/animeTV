package com.coders.animetv.Models

class Users {
    var user_email: String? = null
    var user_password: String? = null
    var user_nickname: String? = null
    var user_id: String? = null

    constructor() {}
    constructor(
        user_email: String?,
        user_password: String?,
        user_nickname: String?,
        user_id: String?
    ) {
        this.user_email = user_email
        this.user_password = user_password
        this.user_nickname = user_nickname
        this.user_id = user_id
    }
}