package ru.sfedu.sergeysh.lab1.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.sfedu.sergeysh.lab1.data.LoginRepository;
import ru.sfedu.sergeysh.lab1.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private MutableLiveData<LoggedInUser> loggedInUser = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    LiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        LoggedInUser user = loginRepository.login(username, password);
        this.loggedInUser.setValue(user);
        this.isLoggedIn.setValue(user != null);
    }

    public void loginDataChanged(String username, String password) {
        Integer usernameError = isUsernameValid(username) ? null : R.string.invalid_username;
        Integer passwordError = isPasswordValid(password) ? null : R.string.invalid_password;
        if (usernameError != null || passwordError != null) {
            loginFormState.setValue(new LoginFormState(usernameError, passwordError));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUsernameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
