package com.amaze.filemanager.services.cloud;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by vishal on 14/2/17.
 *
 * Service provides implementation of {@link AbstractAccountAuthenticator}
 * for the {@link android.accounts.AccountManager} to handle account creation and synchronization
 */

public class CloudAccountsService {

    public static Account GET_ACCOUNT(String accountName, String accountType) {
        return new Account(accountName, accountType);
    }

    public static class GOOGLE_DRIVE_SERVICE extends Service {

        private Authenticator mAuthenticator;

        @Override
        public void onCreate() {
            super.onCreate();
            mAuthenticator = new Authenticator(getApplicationContext());
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return mAuthenticator.getIBinder();
        }
    }

    public static class DROPBOX_SERVICE extends Service {

        private Authenticator mAuthenticator;

        @Override
        public void onCreate() {
            super.onCreate();
            mAuthenticator = new Authenticator(getApplicationContext());
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return mAuthenticator.getIBinder();
        }
    }

    public static class BOX_SERVICE extends Service {

        private Authenticator mAuthenticator;

        @Override
        public void onCreate() {
            super.onCreate();
            mAuthenticator = new Authenticator(getApplicationContext());
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return mAuthenticator.getIBinder();
        }
    }

    public static class ONEDRIVE_SERVICE extends Service {

        private Authenticator mAuthenticator;

        @Override
        public void onCreate() {
            super.onCreate();
            mAuthenticator = new Authenticator(getApplicationContext());
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return mAuthenticator.getIBinder();
        }
    }

    private static class Authenticator extends AbstractAccountAuthenticator {

        public Authenticator(Context context) {
            super(context);
        }

        @Override
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            return null;
        }

        @Override
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                                 String authTokenType, String[] requiredFeatures, Bundle options)
                throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
                                         Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
                                   String authTokenType, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public String getAuthTokenLabel(String authTokenType) {
            return null;
        }

        @Override
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account,
                                        String authTokenType, Bundle options) throws NetworkErrorException {
            return null;
        }

        @Override
        public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
                                  String[] features) throws NetworkErrorException {
            return null;
        }
    }
}
