package com.coders.animetv.Models

class Users {
    var user_email: String? = null
    var user_password: String? = null
    var user_nickname: String? = null
    var user_id: String? = null
    var user_register_time: String? = null
    //Compozisyon class  yani classın alt classı
    var user_detail: UserDetails? = null

    constructor() {}
    constructor(
        user_email: String?,
        user_password: String?,
        user_nickname: String?,
        user_id: String?,
        user_register_time: String?,
        user_detail: UserDetails?
    ) {
        this.user_email = user_email
        this.user_password = user_password
        this.user_nickname = user_nickname
        this.user_id = user_id
        this.user_register_time = user_register_time
        this.user_detail = user_detail
    }

    override fun toString(): String {
        return "Users(user_email=$user_email, user_password=$user_password, user_nickname=$user_nickname, user_id=$user_id, user_register_time=$user_register_time, user_detail=$user_detail)"
    }


}