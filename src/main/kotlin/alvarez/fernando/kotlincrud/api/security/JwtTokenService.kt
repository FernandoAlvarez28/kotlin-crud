package alvarez.fernando.kotlincrud.api.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtTokenService(@Value("\${app.security.secret}") private val secret: String,
                      @Value("\${app.security.refresh}") private val refresh: String) {

    fun accessToken(username: String, expirationInMillis: Int, roles: Array<String>): String {
        return generate(username, expirationInMillis, roles, this.secret)
    }

    fun decodeAccessToken(accessToken: String): DecodedJWT {
        return decode(this.secret, accessToken)
    }

    fun refreshToken(username: String, expirationInMillis: Int, roles: Array<String>): String {
        return generate(username, expirationInMillis, roles, this.refresh)
    }

    fun decodeRefreshToken(refreshToken: String): DecodedJWT {
        return decode(this.refresh, refreshToken)
    }

    fun getRoles(decodedJWT: DecodedJWT) = decodedJWT.getClaim("role").asList(String::class.java)
        .map { SimpleGrantedAuthority(it) }

    private fun generate(username: String, expirationInMillis: Int, roles: Array<String>, signature: String): String {
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationInMillis))
            .withArrayClaim("role", roles)
            .sign(Algorithm.HMAC512(signature.toByteArray()))
    }

    private fun decode(signature: String, token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC512(signature.toByteArray()))
            .build()
            .verify(token.replace("Bearer ", ""))
    }

}