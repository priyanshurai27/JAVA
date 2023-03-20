package com.amazonaws.orcaleutility;

public class EncryptionDecryption {

	public static void main(String args[]){
		KeyBasedFileProcessor kbP = new KeyBasedFileProcessor();
		InputStream decryptedStream = null;
		//read encryptedFile in inputstream
		InputStream stream = <encryptedFile>;
		//read private key File in inputstream
		InputStream privateKey = < "private.key" file >;
		byte[] decryptedData = kbP.decryptFile(stream, privateKey, "", "");
		decryptedStream = new ByteArrayInputStream(decryptedData);
			
	}
}
