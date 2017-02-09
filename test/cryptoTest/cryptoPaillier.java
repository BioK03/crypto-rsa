/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import Paillier_crypto.*;

/**
 *
 * @author Bertrand
 */
public class cryptoPaillier {
    
    protected Paillier_user senderBob;
    protected Paillier_user senderAlice;
    
    public cryptoPaillier() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        senderBob = new Paillier_user("Bob");
        senderAlice = new Paillier_user("Alice");
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
        int enc2dec = Integer.parseInt(senderBob.decryptMessageWithOwnKey(senderBob.encryptMessageWithOwnKey(toBeEncrypted+"")[0]));
        assertEquals(enc2dec, toBeEncrypted);
    }
    
    @Test
    public void msgFromAliceToBob(){
        int toBeEncrypted = 424242;
        int enc2dec = Integer.parseInt(senderBob.decryptMessageWithOwnKey(senderAlice.encryptMessageWithExternalKey(senderBob.getKey(), toBeEncrypted+"")[0]));
        assertEquals(toBeEncrypted, enc2dec);
    }
    
    @Test
    public void stringEncryption(){
        String toBeEncrypted = "BONJOUR";
        String enc2dec = senderBob.decryptStringMessageWithOwnKey(senderBob.encryptStringMessageWithOwnKey(toBeEncrypted)[0]);
        assertEquals(enc2dec, toBeEncrypted);
    }
    
    @Test
    public void stringMsgFromBobToAlice(){
        String toBeEncrypted = "Bonjour";
        String enc2dec = senderAlice.decryptStringMessageWithOwnKey(senderBob.encryptStringMessageWithExternalKey(senderAlice.getKey(), toBeEncrypted)[0]);
        assertEquals(toBeEncrypted, enc2dec);
    }
    
    @Test
    public void testSumEncEqEncSum(){
        assertEquals(senderAlice.testSum("3", "41"), "44");
    }
}
