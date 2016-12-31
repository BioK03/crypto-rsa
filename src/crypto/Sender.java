/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author p1201629
 */
public class Sender {

    private Key key;/* Key of the sender*/
    private String name;/* Name of the sender, only for puprose*/

    public Sender() {
        this.name = "Alice";
        this.key = new Key();
    }

    public Sender(String name) {
        this.name = name;
        this.key = new Key();
    }

    /* GETTERS & SETTERS */
    public Key getKey(){
        return this.key;
    }
    
    public void setKey(Key k) {
        this.key = k;
    }
    
    public String getName() {
        return this.name;
    }
    
    /* GET KEYS */
    public HashMap<String, BigInteger> getPrivateKey() {
        return key.getPrivateKey();
    }
    
    public HashMap<String, BigInteger> getPublicKey() {
        return key.getPublicKey();
    }
    
    /* ENC & DEC */
    public BigInteger EncryptWithExternalKey(BigInteger x, BigInteger e, BigInteger n) {
        return x.modPow(e, n);
    }
    
    public BigInteger DecryptWithExternalKey(BigInteger x, BigInteger d, BigInteger n) {
        return x.modPow(d, n);
    }
    
    public BigInteger EncryptWithOwnKey(BigInteger x) {
        return this.EncryptWithExternalKey(x, getPublicKey().get("e"), getPublicKey().get("n"));
    }

    
    public BigInteger DecryptWithOwnKey(BigInteger x) {
        return this.DecryptWithExternalKey(x, getPrivateKey().get("d"), getPrivateKey().get("n"));
    }

    /* KEY GENERATION */
    public Key Keygen() {
        BigInteger p = new BigInteger(512, 2, new Random());
        while (!primalite_fermat(p)) {
            p = p.nextProbablePrime();
        }
        BigInteger q = new BigInteger(512, 2, new Random());
        while (!primalite_fermat(q)) {
            q = q.nextProbablePrime();
        }
        BigInteger n = p.multiply(q);
        BigInteger phi_n = p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));

        BigInteger e = new BigInteger(16, 2, new Random());
        while (!primalite_fermat(e)) {
            e = e.nextProbablePrime();
        }
        while (!phi_n.gcd(e).equals(new BigInteger("1"))) {
            e = e.nextProbablePrime();
        }
        BigInteger d = e.modInverse(phi_n);
        
        return new Key(n, e, d);
    }

    public static boolean primalite_fermat(BigInteger m) {
        BigInteger nn = new BigInteger("2");
        return nn.modPow(m.subtract(new BigInteger("1")), m).equals(new BigInteger("1"));
    }
}
