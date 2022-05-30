package store.razvan.prepself.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TokenResponse (
    @JsonProperty("token")
    var tokenString : String,
    @JsonProperty("createdAt")
    var createdAt: LocalDateTime,
    @JsonProperty("expiresAt")
    var expiresAt : LocalDateTime
)