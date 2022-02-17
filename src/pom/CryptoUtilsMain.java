package pom;

import java.io.File;

/**
* A tester for the CryptoUtils class.
* @author www.codejava.net
*
*/
public class CryptoUtilsMain {
   public static void main(String[] args) {
       String key = "Laki je najbolji";
       File inputFile = new File("test.txt");
       File encryptedFile = new File("t");
       File decryptedFile = new File("td");
        
       try {
           //CryptoUtils.encrypt(key, inputFile, encryptedFile);
           CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
       } catch (CryptoException ex) {
           System.out.println(ex.getMessage());
           ex.printStackTrace();
       }
   }
}