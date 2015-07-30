/*
 * The MIT License
 *
 * Copyright 2015 Pmovil LTDA.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pmovil.facebook.requestdialog;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.codename1.impl.android.CodenameOneActivity;
import com.codename1.impl.android.IntentResultListener;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;

public class NativeFacebookRequestDialogImpl {

    private static final String TAG = "FACEBOOK_REQUEST_DIALOG";
    private static CodenameOneActivity app;
    GameRequestDialog requestsDialog;
    CallbackManager callbackManager;
    boolean first = true;

    public boolean isShown() {
        return true; // not supported anymore
    }

    public void show(final String message) {
        app = (CodenameOneActivity) FacebookRequestDialog.getContext();

        app.runOnUiThread(new Runnable() {
            public void run() {
                if (first) {
                    first = false;
                    FacebookSdk.sdkInitialize(app.getApplicationContext());
                    callbackManager = CallbackManager.Factory.create();
                    app.setIntentResultListener(new IntentResultListener() {
                        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                            callbackManager.onActivityResult(requestCode, resultCode, data);  
                        }
                    });
                }
                requestsDialog = new GameRequestDialog(app);
                requestsDialog.registerCallback(callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
                        Log.i(TAG, "Request sent - " + result.getRequestId());
                    }

                    public void onCancel() {
                        Log.i(TAG, "Request cancelled");
                    }

                    public void onError(FacebookException error) {
                        Log.w(TAG, "Network Error");
                    }
                });
                GameRequestContent content = new GameRequestContent.Builder()
                .setMessage(message)
                .build();
                requestsDialog.show(content);
            }
        });

    }

    public boolean isSupported() {
        return true;
    }

}
