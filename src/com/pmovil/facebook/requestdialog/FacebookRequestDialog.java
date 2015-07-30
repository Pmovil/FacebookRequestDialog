/*
 * The MIT License
 *
 * Copyright 2014-2015 Pmovil LTDA.
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

import com.codename1.social.FacebookConnect;
import com.codename1.system.NativeLookup;

/**
 * This is a demo class to help you get started building a library
 *
 * @author Your name here
 */
public class FacebookRequestDialog {
    private static Object contextValue;
    private static NativeFacebookRequestDialog peer;
    private final String text;
    
    private FacebookRequestDialog(String text) {
        this.text = text;
    }
    
    public static FacebookRequestDialog build(Object context, String text) throws RuntimeException {
        if (peer == null) {
            peer = (NativeFacebookRequestDialog)NativeLookup.create(NativeFacebookRequestDialog.class);
            if ( peer == null ) {
                throw new RuntimeException("FacebookRequestDialog is not implemented yet in this platform.");
            }
        }
        if ( !peer.isSupported() ){
            throw new RuntimeException("FacebookRequestDialog is not supported in this platform.");
        }
        if ( !FacebookConnect.getInstance().isFacebookSDKSupported() ){
            throw new RuntimeException("FacebookRequestDialog is not supported in this platform.");
        }
        contextValue = context;
        FacebookRequestDialog dialog = new FacebookRequestDialog(text);
        return dialog;
    }
    
    protected static Object getContext() {
        return contextValue;
    }
    
    public boolean isShown(){
        return peer.isShown();
    }
    
    public void show() {
        peer.show(text);
    }
}