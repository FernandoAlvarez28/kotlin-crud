package alvarez.fernando.kotlincrud.api.security

import alvarez.fernando.kotlincrud.api.ApiEndpoints
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun configureSecurity(http: ServerHttpSecurity,
                          jwtAuthenticationFilter: AuthenticationWebFilter,
                          jwtAuthorizationManager: JwtAuthorizationManager,
                          jwtTokenService: JwtTokenService
    ): SecurityWebFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .logout { logout -> logout.disable() }
            .authorizeExchange { exchanges -> exchanges
                .pathMatchers(*ApiEndpoints.PUBLIC).permitAll()
                .pathMatchers("/api/**").access(jwtAuthorizationManager) //Matcher that adds an access rule manager for any endpoint under "/api"
            }
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) //Don't store sessions
            .build()
    }

    @Bean
    fun authenticationWebFilter(reactiveAuthenticationManager: ReactiveAuthenticationManager,
                                jwtConverter: ServerAuthenticationConverter,
                                serverAuthenticationSuccessHandler: ServerAuthenticationSuccessHandler,
                                jwtServerAuthenticationFailureHandler: ServerAuthenticationFailureHandler
    ): AuthenticationWebFilter {
        val authenticationWebFilter = AuthenticationWebFilter(reactiveAuthenticationManager)
        authenticationWebFilter.setRequiresAuthenticationMatcher { ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, ApiEndpoints.API_V1_LOGIN).matches(it) }
        authenticationWebFilter.setServerAuthenticationConverter(jwtConverter)
        authenticationWebFilter.setAuthenticationSuccessHandler(serverAuthenticationSuccessHandler)
        authenticationWebFilter.setAuthenticationFailureHandler(jwtServerAuthenticationFailureHandler)
        authenticationWebFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        return authenticationWebFilter
    }

    @Bean
    fun reactiveAuthenticationManager(reactiveUserDetailsService: ReactiveUserDetailsServiceImpl,
                                      passwordEncoder: PasswordEncoder
    ): ReactiveAuthenticationManager {
        val manager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
        manager.setPasswordEncoder(passwordEncoder)
        return manager
    }

}