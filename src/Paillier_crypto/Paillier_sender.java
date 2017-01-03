/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paillier_crypto;

import java.math.BigInteger;
import java.util.Random;
import Utils.*;

/**
 *
 * @author Bertrand
 */
public class Paillier_sender {
    
    private Paillier_key pk;
    private String name;

    public Paillier_sender() {
        this.name = "Alice";
        this.pk = Keygen();
    }
    
    public Paillier_sender(String name) {
        this.name = name;
        this.pk = Keygen();
    }
    
    public Paillier_key getKey(){
        return this.pk;
    }
    
    /* ENCRYPTIONS */
    public BigInteger encryptMessageWithExternalKey(Paillier_key pk, String m){
        BigInteger message = new BigInteger(m);
        BigInteger r = RandomBigInteger.nextRandomBigInteger(pk.getN());
        
        BigInteger part1 = pk.getN().add(new BigInteger("1")).modPow(message, pk.getN().multiply(pk.getN()));
        BigInteger part2 = part1.multiply(r.modPow(pk.getN(), pk.getN().multiply(pk.getN())));
        
        return part2.mod(pk.getN().multiply(pk.getN()));
    }
    
    public BigInteger encryptMessageWithOwnKey(String m){
        return this.encryptMessageWithExternalKey(pk, m);
    }
    
    public BigInteger encryptStringMessageWithExternalKey(Paillier_key pk, String m){
        return this.encryptMessageWithExternalKey(pk, AsciiConversion.StringToInt(m));
    }
    
    public BigInteger encryptStringMessageWithOwnKey(String m){
        return this.encryptStringMessageWithExternalKey(pk, m);
    }
    
    /* DECRYPTIONS */
    public String decryptMessageWithExternalKey(Paillier_key pk, BigInteger cm){
        BigInteger r = cm.modPow((pk.getN().modInverse(pk.getPhi_N())), pk.getN());
        BigInteger m1 = (cm.multiply(r.modPow(pk.getN().negate(), pk.getN().multiply(pk.getN())))).mod(pk.getN().multiply(pk.getN())).subtract(new BigInteger("1"));
        BigInteger m = m1.divide(pk.getN());
        return m+"";
    }

    public String decryptMessageWithOwnKey(BigInteger cm){
        return this.decryptMessageWithExternalKey(pk, cm);
    }
    
    public String decryptStringMessageWithExternalKey(Paillier_key pk, BigInteger cm){
        return AsciiConversion.IntToString(this.decryptMessageWithExternalKey(pk, cm));
    }
    
    public String decryptStringMessageWithOwnKey(BigInteger cm){
        return this.decryptStringMessageWithExternalKey(pk, cm);
    }
    
    /* KEY GENERATION */
    public Paillier_key Keygen() {
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
        
        return new Paillier_key(n, phi_n);
    }
    
    public static boolean primalite_fermat(BigInteger m) {
        BigInteger nn = new BigInteger("2");
        return nn.modPow(m.subtract(new BigInteger("1")), m).equals(new BigInteger("1"));
    }
    
    public String testSum(String int1, String int2){
        BigInteger enc1 = this.encryptMessageWithExternalKey(this.getKey(), int1);
        BigInteger enc2 = this.encryptMessageWithExternalKey(this.getKey(), int2);
        
        BigInteger finalEnc = (enc1.multiply(enc2)).mod(this.getKey().getN().multiply(this.getKey().getN()));
        return this.decryptMessageWithExternalKey(this.getKey(), finalEnc)+"";
    }
    
    /*
    
    X1 = encryptMessageWithExternalKey(x1)
    X2 = encryptMessageWithExternalKey(x2)
    
    on doit déduire X3 sans avoir x1 et x2 tel que decryptMessageWithExternalKey(X3) = x1*x2
    
    on peut demander à bob d'encrypter x1+valeur secrète
    
    bob nous renverra donc (X1+rand1) * (X2+rand2)
    
    Si on lui demande d'encrypter A1 = enc(num), on peut avoir X1A1 = x+num
    
    
    
    */
}
