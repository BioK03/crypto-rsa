/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA_crypto;

import Utils.AsciiConversion;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author p1201629
 */
public class RSA_sender {

    private RSA_key key;/* RSA_key of the sender*/
    private String name;/* Name of the sender, only for puprose*/

    public RSA_sender() {
        this.name = "Alice";
        this.key = new RSA_key();
        this.setKey(this.keygen());
    }

    public RSA_sender(String name) {
        this.name = name;
        this.key = new RSA_key();
        this.setKey(this.keygen());
    }

    /* GETTERS & SETTERS */
    public RSA_key getKey(){
        return this.key;
    }
    
    public void setKey(RSA_key k) {
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
    private BigInteger encryptWithExternalKey(BigInteger x, BigInteger e, BigInteger n) {
        return x.modPow(e, n);
    }
    
    private BigInteger encryptWithOwnKey(BigInteger x) {
        return this.encryptWithExternalKey(x, getPublicKey().get("e"), getPublicKey().get("n"));
    }
    
    private BigInteger decryptWithExternalKey(BigInteger x, BigInteger d, BigInteger n) {
        return x.modPow(d, n);
    }
    
    private BigInteger decryptWithOwnKey(BigInteger x) {
        return this.decryptWithExternalKey(x, getPrivateKey().get("d"), getPrivateKey().get("n"));
    }

    /* KEY GENERATION */
    public RSA_key keygen() {
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
        
        return new RSA_key(n, e, d);
    }

    public static boolean primalite_fermat(BigInteger m) {
        BigInteger nn = new BigInteger("2");
        return nn.modPow(m.subtract(new BigInteger("1")), m).equals(new BigInteger("1"));
    }
    
    
    
    /* ENCRYPTIONS & DECRYPTIONS */
    public BigInteger encryptMessageWithPublicKey(int msg) {
        BigInteger x = new BigInteger(msg+"");
        return this.encryptWithOwnKey(x);
    }
    
    public BigInteger encryptStringMessageWithPublicKey(String msg) {
        BigInteger x = new BigInteger(AsciiConversion.StringToInt(msg));
        return this.encryptWithOwnKey(x);
    }
    
    public BigInteger encryptMessageWithExternalKey(int msg, RSA_key key){
        return this.encryptWithExternalKey(new BigInteger(msg+""), key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }
    
    public BigInteger encryptStringMessageWithExternalKey(String msg, RSA_key key){
        return this.encryptWithExternalKey(new BigInteger(AsciiConversion.StringToInt(msg)), key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }

    /* DECRYPTIONS */
    public String decryptMessageWithPrivateKey(BigInteger cmsg) {
        return ""+this.decryptWithOwnKey(cmsg);
    }
    
    public String decryptStringMessageWithPrivateKey(BigInteger cmsg) {
        return AsciiConversion.IntToString(""+this.decryptWithOwnKey(cmsg));
    }
    
    public String decryptMessageWithExternalKey(BigInteger msg, RSA_key key){
        return ""+this.decryptWithExternalKey(msg, key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }
    
    public String decryptStringMessageWithExternalKey(BigInteger msg, RSA_key key){
        return AsciiConversion.IntToString(""+this.decryptWithExternalKey(msg, key.getPublicKey().get("e"), key.getPublicKey().get("n")));
    }
    
    /* BAD UTILIZATION OF RSA */
    public BigInteger test_4_3(BigInteger msg){
        BigInteger n = this.getPublicKey().get("n");
        
        BigInteger top = ((msg.add(new BigInteger("1"))).pow(3)).add(new BigInteger("2").multiply(msg.pow(3))).subtract(new BigInteger("1"));
        BigInteger bottom = ((msg.add(new BigInteger("1"))).pow(3)).subtract(msg.pow(3)).add(new BigInteger("2"));
        
        return top.divide(bottom).mod(n);
    }
}
