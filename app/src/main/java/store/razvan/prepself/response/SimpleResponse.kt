package store.razvan.prepself.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SimpleResponse(
    @JsonProperty("success")
    var success: Boolean,
    @JsonProperty("errorMessage")
    var errorMessage: String?,
    @JsonProperty("body")
    var body: String?
)