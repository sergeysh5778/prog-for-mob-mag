package ru.sfedu.sergeysh.lab3.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ru.sfedu.sergeysh.common.R
import ru.sfedu.sergeysh.common.ui.login.LoginFormState
import ru.sfedu.sergeysh.common.ui.login.LoginResult
import ru.sfedu.sergeysh.common.ui.login.LoginViewModel
import ru.sfedu.sergeysh.common.ui.login.LoginViewModelFactory
import ru.sfedu.sergeysh.lab3.databinding.ActivityLoginBinding
import ru.sfedu.sergeysh.lab3.ui.movie.MainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.loginToolbar)

        val username: TextInputEditText = binding.username
        val password: TextInputEditText = binding.password
        val login: MaterialButton = binding.login

        val loginViewModel: LoginViewModel by viewModels { LoginViewModelFactory(applicationContext) }

        loginViewModel.loginFormState.observe(this) {
            val loginState: LoginFormState = it ?: return@observe

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            username.error = if (loginState.usernameError != null) {
                getString(loginState.usernameError!!)
            } else {
                null
            }

            password.error = if (loginState.passwordError != null) {
                getString(loginState.passwordError!!)
            } else {
                null
            }
        }

        loginViewModel.loginResult.observe(this) {
            val loginResult: LoginResult = it ?: return@observe

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error!!)

                return@observe
            }

            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success!!)

                setResult(RESULT_OK)
                //Complete and destroy login activity once successful
                finish()
            }
        }

        username.afterTextChanged {
            loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> loginViewModel.login(
                        username.text.toString(), password.text.toString()
                    )
                }
                false
            }

            login.setOnClickListener {
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(displayName: String) {
        val welcome: String = getString(R.string.welcome)
        Toast.makeText(
            applicationContext, "$welcome$displayName", Toast.LENGTH_LONG
        ).show()

        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
