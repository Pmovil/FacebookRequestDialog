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

#import "com_pmovil_facebook_requestdialog_NativeFacebookRequestDialogImpl.h"
#import "FBSDKGameRequestDialog.h"
#import "FBSDKGameRequestContent.h"

@implementation com_pmovil_facebook_requestdialog_NativeFacebookRequestDialogImpl

// TODO
-(BOOL)isShown{
    return YES;
}

-(void)show:(NSString*)param{

    dispatch_sync(dispatch_get_main_queue(), ^{
        FBSDKGameRequestContent *gameRequestContent = [[FBSDKGameRequestContent alloc] init];
        gameRequestContent.message = param;
        //gameRequestContent.title = @"OPTIONAL TITLE";
        [FBSDKGameRequestDialog showWithContent:gameRequestContent delegate:self];
    });

}

-(BOOL)isSupported{
    return YES;
}


-(void) gameRequestDialog : (FBSDKGameRequestDialog *) gameRequestDialog
didCompleteWithResults : (NSDictionary *) results {
    NSLog(@"Request sent.");
}

-(void) gameRequestDialogDidCancel : (FBSDKGameRequestDialog *) gameRequestDialog {
    NSLog(@"Request cancelled.");
}

-(void) gameRequestDialog : (FBSDKGameRequestDialog *) gameRequestDialog
didFailWithError : (NSError *) error {
    NSLog(@"Error: %@", error);
}

@end
