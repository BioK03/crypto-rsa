/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoTest;

import RSA_crypto.RSA_user;
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Bertrand
 */
public class cryptoRSA {
    
    protected RSA_user senderBob;
    protected RSA_user senderAlice;
    
    public cryptoRSA() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        senderBob = new RSA_user("Bob");
        senderAlice = new RSA_user("Alice");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void simpleEncryption(){
        int toBeEncrypted = 42;
        int enc2dec = Integer.parseInt(senderBob.decryptMessageWithPrivateKey(senderBob.encryptMessageWithPublicKey(toBeEncrypted)));
        assertEquals(enc2dec, toBeEncrypted);
    }
    
    @Test
    public void msgFromAliceToBob(){
        int toBeEncrypted = 424242;
        int enc2dec = Integer.parseInt(senderBob.decryptMessageWithPrivateKey(senderAlice.encryptMessageWithExternalKey(toBeEncrypted, senderBob.getKey())));
        assertEquals(toBeEncrypted, enc2dec);
    }
    
    @Test
    public void stringEncryption(){
        String toBeEncrypted = "BONJOUR";
        String enc2dec = senderBob.decryptStringMessageWithPrivateKey(senderBob.encryptStringMessageWithPublicKey(toBeEncrypted));
        assertEquals(enc2dec, toBeEncrypted);
    }
    
    @Test
    public void stringMsgFromBobToAlice(){
        String toBeEncrypted = "Bonjour";
        String enc2dec = senderAlice.decryptStringMessageWithPrivateKey(senderBob.encryptStringMessageWithExternalKey(toBeEncrypted, senderAlice.getKey()));
        assertEquals(toBeEncrypted, enc2dec);
    }
    
    @Test
    public void test4_3(){
        int toBeEncrypted = 424344;
        int testResult = Integer.parseInt(senderBob.test_4_3(new BigInteger(toBeEncrypted+""))+"");
        assertEquals(toBeEncrypted, testResult);
    }
}
