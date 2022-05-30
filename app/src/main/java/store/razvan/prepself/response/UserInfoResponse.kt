package store.razvan.prepself.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoResponse(
    @JsonProperty("successful")
    var success: Boolean,
    @JsonProperty("errorMessage")
    var errorMessage: String?,
    @JsonProperty("firstName")
    var firstName: String?,
    @JsonProperty("lastName")
    var lastName: String?,
    @JsonProperty("email")
    var email: String?
)