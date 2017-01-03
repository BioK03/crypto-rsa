/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import RSA_crypto.RSA_sender;
import RSA_crypto.RSA_key;
import Paillier_crypto.*;
import java.math.BigInteger;

/**
 *
 * @author p1201629
 */
public class Crypto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\n*** RSA ***");
        
        RSA_sender a = new RSA_sender();
        
        System.out.print("Encryption and decryption for the message 23 : ");
        System.out.println(a.decryptMessageWithPrivateKey(a.encryptMessageWithPublicKey(23)));
        System.out.print("Encryption and decryption for the string message 'HELLO' : ");
        System.out.println(a.decryptStringMessageWithPrivateKey(a.encryptStringMessageWithPublicKey("HELLO")));
        
        RSA_key k = a.keygen();
        k.setE(new BigInteger("3"));
        System.out.print("Security exploit for a RSA bad utilization, trying to decrypt ther message 5465431 without using the private key : ");
        System.out.println(a.test_4_3(new BigInteger("5465431")));
        
        System.out.println("\n*** Paillier ***");
        
        Paillier_sender ps = new Paillier_sender();
        System.out.print("Encryption and decryption for the message 12 : ");
        System.out.println(ps.decryptMessageWithExternalKey(ps.getKey(), ps.encryptMessageWithExternalKey(ps.getKey(), "12")));
        
        System.out.print("Decryption of the sum of encryptions of messages 1 and 2, result : ");
        System.out.println(ps.testSum("1", "2"));
        
        System.out.print("Encryption and decryption for the string message 'HELLO' : ");
        System.out.println(ps.decryptStringMessageWithOwnKey(ps.encryptStringMessageWithOwnKey("HELLO")));
    }
    
     
    
}
