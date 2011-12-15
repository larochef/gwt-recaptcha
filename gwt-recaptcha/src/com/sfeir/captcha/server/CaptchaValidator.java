/**
 * 
 */
package com.sfeir.captcha.server;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import com.sfeir.captcha.shared.CaptchaResult;

/**
 * @author sfeir
 *
 */
public class CaptchaValidator {

	private String privateKey;
	private ReCaptchaImpl reCaptcha;
	
	public CaptchaValidator(String privateKey) {
		this.privateKey = privateKey;
		this.reCaptcha = new ReCaptchaImpl();
		this.reCaptcha.setPrivateKey(this.privateKey);
	}

	public boolean validateCaptcha(CaptchaResult result, String remoteAddr){
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, result.getChallenge(), result.getResponse());
		return reCaptchaResponse.isValid();
	}
}
