/**
 * 
 */
package com.sfeir.captcha.shared;

import java.io.Serializable;

/**
 * @author sfeir
 *
 */
@SuppressWarnings("serial")
public class CaptchaResult implements Serializable {
	
	private String publicKey;
	private String challenge;
	private String response;

	public CaptchaResult() {}

	public CaptchaResult(String publicKey, String challenge, String response) {
		this.publicKey = publicKey;
		this.challenge = challenge;
		this.response = response;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
