/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoTest;

import crypto.SenderAbs;
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Bertrand
 */
public class cryptoRSA {
    
    protected SenderAbs senderBob;
    protected SenderAbs senderAlice;
    
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
        senderBob = new SenderAbs("Bob");
        senderAlice = new SenderAbs("Alice");
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
        assertTrue(enc2dec == toBeEncrypted);
    }
    
    @Test
    public void msgFromAliceToBob(){
        int toBeEncrypted = 424242;
        int enc2dec = Integer.parseInt(senderBob.decryptMessageWithPrivateKey(senderAlice.encryptMessageWithOnBoardKey(toBeEncrypted, senderBob.getSender().getKey())));
        assertTrue(toBeEncrypted == enc2dec);
    }
    
    @Test
    public void stringEncryption(){
        String toBeEncrypted = "BONJOUR";
        String enc2dec = senderBob.decryptStringMessageWithPrivateKey(senderBob.encryptStringMessageWithPublicKey(toBeEncrypted));
        assertTrue(enc2dec.equals(toBeEncrypted));
    }
    
    @Test
    public void stringMsgFromBobToAlice(){
        String toBeEncrypted = "Bonjour";
        String enc2dec = senderAlice.decryptStringMessageWithPrivateKey(senderBob.encryptStringMessageWithOnBoardKey(toBeEncrypted, senderAlice.getSender().getKey()));
        assertTrue(toBeEncrypted.equals(enc2dec));
    }
    
    @Test
    public void test43(){
        int toBeEncrypted = 424344;
        int testResult = Integer.parseInt(senderBob.test_4_3(new BigInteger(toBeEncrypted+""))+"");
        assertTrue(toBeEncrypted == testResult);
    }
}
