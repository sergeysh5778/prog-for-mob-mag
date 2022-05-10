package ru.sfedu.sergeysh.common.data.login

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    private var _user: LoggedInUser? = null

    init {
        _user = null
    }

    fun login(username: String, password: String): LoggedInUser? {
        val user: LoggedInUser? = dataSource.login(username, password)

        if (user != null) {
            _user = user
        }

        return user
    }
}
