package alvarez.fernando.kotlincrud.api.security

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * Password encoder implementation that actually doesn't encoding anything, for simplicity and education purpuses ONLY.
 *
 * **Do not use this on a real application!**
 */
@Component
class PlainPasswordEncoderImpl: PasswordEncoder {

    override fun encode(rawPassword: CharSequence?): String {
        return rawPassword.toString()
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return this.encode(rawPassword) == encodedPassword
    }

}