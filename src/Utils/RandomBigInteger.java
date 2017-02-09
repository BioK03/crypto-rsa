/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Bertrand
 */
public class RandomBigInteger {
    
    /* Taken from http://stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java */
    public static BigInteger nextRandomBigInteger(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }
    
    public static BigInteger nextRandomBigInteger() {
        Random rand = new Random();
        BigInteger result = new BigInteger(128, rand);
        return result;
    }
}
