package ru.sfedu.sergeysh.lab1.data;

import android.content.Context;
import android.content.res.Resources;

import ru.sfedu.sergeysh.lab1.R;
import ru.sfedu.sergeysh.lab1.ui.login.LoggedInUser;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private String[] mUsernames;
    private String[] mPasswords;

    public LoginDataSource(Context context) {
        Resources res = context.getResources();
        mUsernames = res.getStringArray(R.array.usernames);
        mPasswords = res.getStringArray(R.array.passwords);
    }

    public LoggedInUser login(String username, String password) {

        for (int i = 0; i < mUsernames.length; i++) {
            if (mUsernames[i].equalsIgnoreCase(username) && mPasswords[i].equals(password)) {
                LoggedInUser user = new LoggedInUser(username);
                return user;
            }
        }
        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
