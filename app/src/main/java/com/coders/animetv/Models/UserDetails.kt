package com.coders.animetv.Models

class UserDetails {
    var profile_pic: String? = null
//profil resmini yükleme ve çekme

    constructor() {}

    constructor(profile_pic: String?) {
        this.profile_pic = profile_pic
    }

    override fun toString(): String {
        return "UserDetails(profile_pic=$profile_pic)"
    }


}