package com.example.postpractice
import com.google.gson.annotations.SerializedName
class Users {
  var data: List<UserDetails>? = null

        class UserDetails {

            @SerializedName("name")
            var name: String? = null

            @SerializedName("location")
            var location: String? = null
            @SerializedName("pk")
            var pk: Int? = null

            constructor(name: String?, location: String?) {
                this.name = name
                this.location = location

            }
        }
}