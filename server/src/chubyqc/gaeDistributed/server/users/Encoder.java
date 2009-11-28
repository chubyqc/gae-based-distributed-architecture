package chubyqc.gaeDistributed.server.users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.appengine.repackaged.com.google.common.util.Base64;

import chubyqc.gaeDistributed.server.Logger;

public class Encoder {
	private static final Encoder _instance = new Encoder("SHA");
	public static Encoder getInstance() {
		return _instance;
	}
	
	private static final String ENCODING = "UTF-8";
	
	private MessageDigest _md;
	
	private Encoder(String algorithm) {
		try {
			_md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			Logger.getInstance().fatal(e);
		}
	}
	
	public synchronized String encrypt(String toEncrypt) {
		try {
			_md.update(toEncrypt.getBytes(ENCODING));
			return new String(Base64.encode(_md.digest()));
		} catch (UnsupportedEncodingException e) {
			Logger.getInstance().fatal(e);
		}
		return new String();
	}
}
