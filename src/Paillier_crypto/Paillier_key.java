/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paillier_crypto;

import java.math.BigInteger;
import java.util.HashMap;

/**
 *
 * @author p1201629
 */
public class Paillier_key {
    private BigInteger n;
    private BigInteger phi_n;
    
    public Paillier_key(){
    }
    
    public Paillier_key(BigInteger n, BigInteger phi_n){
        this.n = n;
        this.phi_n = phi_n;
    }
    
    public void setN(BigInteger n){
        this.n = n;
    }
    
    public void setPhi_N(BigInteger phi_n){
        this.phi_n = phi_n;
    }
    
    
    /* Get the public key*/
    public BigInteger getN(){
        return n;
    }
    
    /* Get the private key*/
    public BigInteger getPhi_N(){
        return phi_n;
    }
}
