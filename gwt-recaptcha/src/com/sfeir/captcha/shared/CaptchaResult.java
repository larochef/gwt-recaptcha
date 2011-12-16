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
package com.sfeir.captcha.shared;

import java.io.Serializable;

/**
 * Transport class to validate the captcha from the client to the server
 * 
 * @author François LAROCHE
 */
@SuppressWarnings("serial")
public class CaptchaResult implements Serializable {
	
	/**
	 * The public key that was used by the client to display the captcha
	 */
	private String publicKey;
	/**
	 * The captcha challenge
	 */
	private String challenge;
	/**
	 * the response entered by the client
	 */
	private String response;

	/**
	 * Default constructor, required to get the result from the client to the server
	 */
	public CaptchaResult() {}

	/**
	 * Utility constructor, setting all the different fields of the bean
	 * 
	 * @param publicKey the public key used by the captcha 
	 * @param challenge the challenge of the captcha
	 * @param response the response entered by the client
	 */
	public CaptchaResult(String publicKey, String challenge, String response) {
		this.publicKey = publicKey;
		this.challenge = challenge;
		this.response = response;
	}

	/**
	 * Getter for the public key
	 * 
	 * @return the public key of this bean
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * Change the public key
	 * 
	 * @param publicKey the new public key to set to this object
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * getter for the captcha challenge
	 * 
	 * @return the challenge of this bean
	 */
	public String getChallenge() {
		return challenge;
	}

	/**
	 * Change the challenge
	 * 
	 * @param challenge the new challenge
	 */
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	/**
	 * getter for the response entered
	 * 
	 * @return the response entered by the user
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Change the response entered
	 * 
	 * @param response the new response
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((challenge == null) ? 0 : challenge.hashCode());
		result = prime * result
				+ ((publicKey == null) ? 0 : publicKey.hashCode());
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaptchaResult other = (CaptchaResult) obj;
		if (challenge == null) {
			if (other.challenge != null)
				return false;
		} else if (!challenge.equals(other.challenge))
			return false;
		if (publicKey == null) {
			if (other.publicKey != null)
				return false;
		} else if (!publicKey.equals(other.publicKey))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}
	
}