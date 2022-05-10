package ru.sfedu.sergeysh.lab3.data.login

import android.content.Context
import ru.sfedu.sergeysh.lab3.R

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(context: Context) {

    private val usernames: Array<String>
    private val passwords: Array<String>

    init {
        context.resources.apply {
            usernames = getStringArray(R.array.usernames)
            passwords = getStringArray(R.array.passwords)
        }
    }

    fun login(username: String, password: String): LoggedInUser? {
        usernames.indices.forEach { i: Int ->
            if (usernames[i].equals(username, true) && passwords[i] == password) {
                return LoggedInUser(username)
            }
        }

        return null
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
