/**
 * Copyright (c) 2011 François LAROCHE
 *
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

package com.sfeir.captcha.client.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.sfeir.captcha.shared.CaptchaResult;

/**
 * Simple Widget displaying a captcha.<br />
 * This widget will create a div element and inject the captcha within.
 * 
 * @author François LAROCHE
 */
public class Captcha extends Composite {

	/**
	 * List of properties that aren't string literals in the configuration. <br />
	 * If you need more properties left unescaped, add it to the list.
	 */
	private static final List<String> UNESCAPED_PROPERTIES;
	
	/**
	 * default configuration for ReCaptcha, uses the the 'white'
	 */
	private static final Map<String, String> DEFAULT_CONFIGURATION;
	
	static {
		Map<String, String> defaultConf = new HashMap<String, String>();
		defaultConf.put("theme", "white");
		defaultConf.put("callback", "Recaptcha.focus_response_field");
		DEFAULT_CONFIGURATION = Collections.unmodifiableMap(defaultConf);

		List<String> properties = new ArrayList<String>();
		properties.add("tabindex");
		properties.add("callback");
		properties.add("custom_translations");
		UNESCAPED_PROPERTIES = Collections.unmodifiableList(properties);
	}

	/**
	 * id used to identify in a unic way the div in which the captcha is injected.<br />
	 * Event if it is very unlikely, it is possible to have several captchas on the same page
	 */
	private final String divId;
	
	/**
	 * the public key for ReCaptcha
	 */
	private final String key;
	
	/**
	 * Configuration of the captcha, see the official site for more information
	 */
	private final Map<String, String> configuration;

	/**
	 * Constructor of the widget, using the default configuration for the captcha
	 * 
	 * @param key the public key associated with the deployment
	 */
	public Captcha (String key) {
		this(key, cloneDefaultConfiguration());
	}

	/**
	 * Constructor of the widget
	 * 
	 * @param key the public key associated with the deployment
	 * @param configuration the captcha configuration
	 */
	public Captcha(String key, Map<String, String> configuration) {
		this.divId = "captcha-" + System.currentTimeMillis();
		this.key = key;
		this.configuration = configuration;
		// force the callback method.
		this.configuration.put("callback", DEFAULT_CONFIGURATION.get("callback"));

		HTML content = new HTML("<div id='" + divId + "'></div>");
		initWidget(content);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		injectCaptcha(this.key, this.divId, transformMapToJsAssociativeArray(this.configuration));
	}
	
	/**
	 * Validates and reloads Captcha. <br />
	 * If you need to change your captcha, you call this method.
	 * 
	 * @return the result of the captcha, containong all information for its validation
	 */
	public CaptchaResult validateCaptcha() {
		CaptchaResult result = new CaptchaResult(this.key, getCaptchaChallenge(), getCaptchaResponse());
		injectCaptcha(this.key, this.divId, transformMapToJsAssociativeArray(this.configuration));
		return result;
	}

	/**
	 * Inject captcha into its div
	 * 
	 * @param key the public key
	 * @param divId the id of the div in which to inject the captcha
	 */
	private static native void injectCaptcha(String key, String divId, String configuration) /*-{
		var myDiv = $doc.getElementById(divId);
		Recaptcha = $wnd.Recaptcha;
		eval('var conf = ' + configuration);
		Recaptcha.create(key, myDiv, conf);
	}-*/;

	/**
	 * returns the challenge of the captcha. <br >
	 * A challenge corresponds to the text that needs to be guessed by the client. <br />
	 * The returned String is an encrypted version of the challenge, it is impossible to have a bot guess it automatically
	 * 
	 * @return the challenge of the captcha
	 */
	private static native String getCaptchaChallenge() /*-{ 
		Recaptcha = $wnd.Recaptcha;
		return Recaptcha.get_challenge();
	}-*/;

	/**
	 * returns the response of the captcha.<br />
	 * A response is the text entered by the client. It isn't encrypted
	 * 
	 * @return the response to the captcha entered by the client
	 */
	private static native String getCaptchaResponse() /*-{ 
		Recaptcha = $wnd.Recaptcha;
		return Recaptcha.get_response();
	}-*/;

	/**
	 * Transforms a java Map to a String that will be evaluated to an associative array in Js
	 * This is ugly, but so far I don't know a better way.
	 * 
	 * @param map the map to transform
	 * @return the map as a String
	 */
	private static String transformMapToJsAssociativeArray(Map<String, String> map) {
		String all = "{";
		String separator;
		for(String key : map.keySet()) {
			if(UNESCAPED_PROPERTIES.contains(key)) {
				separator = "";
			}
			else {
				separator = "'";
			}
			if(!"{".equals(all)) {
				all += ", ";
			}
			all += key + ": " + separator + map.get(key) + separator;
		}
		all += "}";
		return all;
	}

	/**
	 * Clone the default configuration to ensure it will not change over time
	 * 
	 * @return a new clone of the default configuration
	 */
	private static Map<String, String> cloneDefaultConfiguration() {
		Map<String, String> configuration = new HashMap<String, String>();
		for(String key : DEFAULT_CONFIGURATION.keySet()) {
			configuration.put(key, DEFAULT_CONFIGURATION.get(key));
		}
		return configuration;
	}
}
