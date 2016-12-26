/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

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
        
        SenderAbs a = new SenderAbs();
        
        System.out.println(a.decryptMessageWithPrivateKey(a.encryptMessageWithPublicKey(23)));
        System.out.println(a.decryptStringMessageWithPrivateKey(a.encryptStringMessageWithPublicKey("BONJOUR")));
        
        Key k = a.getSender().Keygen();
        k.setE(new BigInteger("3"));
        
        System.out.println(a.test_4_3(new BigInteger("5465431")));
        
        
    }
    
     
    
}
