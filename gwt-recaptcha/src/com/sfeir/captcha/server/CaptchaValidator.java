/**
 * Copyright (c) 2011 François LAROCHE

 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sfeir.captcha.server;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import com.sfeir.captcha.shared.CaptchaResult;

/**
 * Class used to validate the captcha server side.<br />
 * You are not obliged to use it, you can use the ReCaptchaImpl class provided in recaptcha4j.
 * 
 * 
 * @author François LAROCHE
 */
public class CaptchaValidator {

	/**
	 * Private key for recaptcha
	 */
	private String privateKey;

	/**
	 * Object used to validate the captcha, making the remote call
	 */
	private ReCaptchaImpl reCaptcha;
	
	/**
	 * Constructs a new <code>CaptchaValidator</code> using the given private key
	 * 
	 * @param privateKey the private key, as defined in recaptcha
	 */
	public CaptchaValidator(String privateKey) {
		this.privateKey = privateKey;
		this.reCaptcha = new ReCaptchaImpl();
		this.reCaptcha.setPrivateKey(this.privateKey);
	}

	/**
	 * Validates a captcha result obtained through a {@link com.sfeir.captcha.client.ui.Captcha} 
	 * 
	 * @param result the result of the captcha, obtained by the client
	 * @param remoteAddr the IP address of the client
	 * @return whether or not the text entered by the client is valid
	 */
	public boolean validateCaptcha(CaptchaResult result, String remoteAddr){
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, result.getChallenge(), result.getResponse());
		return reCaptchaResponse.isValid();
	}
}
