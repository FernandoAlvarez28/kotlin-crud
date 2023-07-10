package alvarez.fernando.kotlincrud.api.security

import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.temporal.ChronoUnit

@Component
class JwtServerAuthenticationSuccessHandlerImpl(private val jwtTokenService: JwtTokenService,
                                                @Value("\${app.security.token.access.expiration-in-minutes}") accessTokenExpirationInMinutes: Long,
                                                @Value("\${app.security.token.refresh.expiration-in-minutes}") refreshTokenExpirationInMinutes: Long,
                                                private val accessTokenExpiration: Duration = Duration.of(accessTokenExpirationInMinutes, ChronoUnit.MINUTES),
                                                private val refreshTokenExpiration: Duration = Duration.of(refreshTokenExpirationInMinutes, ChronoUnit.MINUTES)
) : ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(webFilterExchange: WebFilterExchange?, authentication: Authentication?): Mono<Void> = mono {
        val principal = authentication?.principal ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")

        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()
                val accessToken = jwtTokenService.accessToken(principal.username, accessTokenExpiration.toMillis().toInt(), roles)
                val refreshToken = jwtTokenService.refreshToken(principal.username, refreshTokenExpiration.toMillis().toInt(), roles)
                webFilterExchange?.exchange?.response?.headers?.set("Authorization", accessToken)
                webFilterExchange?.exchange?.response?.headers?.set("JWT-Refresh-Token", refreshToken)
            }
        }

        return@mono null
    }

}