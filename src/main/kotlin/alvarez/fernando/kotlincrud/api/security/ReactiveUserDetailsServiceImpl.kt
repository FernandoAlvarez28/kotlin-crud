package alvarez.fernando.kotlincrud.api.security

import alvarez.fernando.kotlincrud.domain.users.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveUserDetailsServiceImpl(private val userRepository: UserRepository) :
    ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {
        return this.userRepository.getByUsername(username!!).flatMap { user -> Mono.just(User(user.username, user.password, emptyList())) }
    }

}