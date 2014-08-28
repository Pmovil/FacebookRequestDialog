/*
 * The MIT License
 *
 * Copyright 2014 Pmovil LTDA.
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
import android.os.Bundle;
import android.widget.Toast;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class NativeFacebookRequestDialogImpl {

    private WebDialog requestsDialog = null;

    public boolean isShown() {
        if (requestsDialog == null) {
            return false;
        }
        return requestsDialog.isShowing();
    }

    public void show(String message) {
        final Bundle params = new Bundle();
        params.putString("message", message);
        final Activity app = (Activity) FacebookRequestDialog.getContext();

        app.runOnUiThread(new Runnable() {
            public void run() {
                requestsDialog = (new WebDialog.RequestsDialogBuilder(app,
                        Session.getActiveSession(),
                        params))
                        .setOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(Bundle values,
                                    FacebookException error) {
                                if (error != null) {
                                    if (error instanceof FacebookOperationCanceledException) {
                                        Toast.makeText(app.getApplicationContext(),
                                                "Request cancelled",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(app.getApplicationContext(),
                                                "Network Error",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    final String requestId = values.getString("request");
                                    if (requestId != null) {
                                        Toast.makeText(app.getApplicationContext(),
                                                "Request sent",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(app.getApplicationContext(),
                                                "Request cancelled",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .build();
                requestsDialog.show();
            }
        });

    }

    public boolean isSupported() {
        return true;
    }

}
