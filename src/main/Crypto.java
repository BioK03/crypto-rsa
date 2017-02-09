/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import RSA_crypto.RSA_user;
import RSA_crypto.RSA_key;
import Paillier_crypto.*;
import Utils.AsciiConversion;
import Utils.RandomBigInteger;
import java.math.BigInteger;
import java.util.Random;

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
        
        RSA_user a = new RSA_user();
        
        System.out.print("Encryption and decryption for the message 23 : ");
        System.out.println(a.decryptMessageWithPrivateKey(a.encryptMessageWithPublicKey(23)));
        System.out.print("Encryption and decryption for the string message 'HELLO' : ");
        System.out.println(a.decryptStringMessageWithPrivateKey(a.encryptStringMessageWithPublicKey("HELLO")));
        
        RSA_key k = a.keygen();
        k.setE(new BigInteger("3"));
        System.out.print("Security exploit for a RSA bad utilization, trying to decrypt ther message 5465431 without using the private key : ");
        System.out.println(a.test_4_3(new BigInteger("5465431")));
        
        System.out.println("\n*** Paillier ***");
        
        Paillier_user ps = new Paillier_user();
        System.out.print("Encryption and decryption for the message 12 : ");
        System.out.println(ps.decryptMessageWithExternalKey(ps.getKey(), ps.encryptMessageWithExternalKey(ps.getKey(), "12")[0]));
        
        System.out.print("Decryption of the sum of encryptions of messages 1 and 2, result : ");
        System.out.println(ps.testSum("1", "2"));
        
        System.out.print("Encryption and decryption for the string message 'HELLO' : ");
        System.out.println(ps.decryptStringMessageWithOwnKey(ps.encryptStringMessageWithOwnKey("HELLO")[0]));
        
        System.out.println("\nEncryption test for advanced functions with r");
        BigInteger[] results = ps.encryptMessageWithOwnKey("1");
        System.out.println("Must print 1 : "+ps.decryptMessageWithOwnKey(results[0], results[1]));
        
        System.out.println("\n*** Question Problem ***");
        questionProblem();
    }
    
    
    
    public static void questionProblem(){
        /* 
        Game rules :
        Alice has created 10 questions with Bob.
        Bob has to choose one question to be answered by Alice
        
        Problem : 
        Alice wants Bob to only know the answer to the question he choosed
        Bob doesn't want Alice to know the question he choosed
        
        */
        
        String[] questions = {"Question A", "Question B", "Question C", "Question D", "Question E", "Question F", "Question G", "Question H", "Question I", "Question J"};
        String[] aliceAnswers = {"Answer A", "Answer B", "Answer C", "Answer D", "Answer E", "Answer F", "Answer G", "Answer H", "Answer I", "Answer J"};
        
        Paillier_user alice = new Paillier_user("Alice");
        Paillier_user bob = new Paillier_user("Bob");
        // Two users, but we uses only Bob's keys
        
        
        // Bob chooses a question Alice must answer (i), 11 equals number of question + padding between natural (arrays begin at 1) and logic (arrays begin at 0)
        int chosenQuestionInt = 10;
        BigInteger[] IandR = bob.customEncryption(bob.getKey(), 11-chosenQuestionInt);
        
        //System.out.println(bob.customDecryption(bob.getKey(), IandR[0]));
        
        // Bob send his encrypted chosen question (I) and r in plain text to Alice
        // Alice encrypts all numbers between -1 and -10 with the same r (Ik)
        BigInteger[] Ik = new BigInteger[10];
        for(int j=-10; j<0; j++){
            Ik[j+10] = bob.customEncryption(bob.getKey(), j)[0];
            //System.out.println(bob.customDecryption(bob.getKey(), Ik[j+10]));
        }
        
        //System.out.println(bob.customDecryption(bob.getKey(), bob.customEncryption(bob.getKey(), 0)[0]));
        
        // Alice calculates all Ik - I * random
        BigInteger[] Mk = new BigInteger[10];
        for(int j=0; j<10; j++){
            
            BigInteger tempSum = (Ik[j].multiply(IandR[0]));
            //System.out.println(bob.customDecryption(bob.getKey(), tempSum.mod(bob.getKey().getN().multiply(bob.getKey().getN()))));
            // Previous line equals 0 if i = the chosen question
            // This line multiplies all previous tempSum with random numbers. Every Mk is a random, except one : the one corresponding to the index of the chosen question
            Mk[j] = tempSum.modPow(RandomBigInteger.nextRandomBigInteger(bob.getKey().getN()), bob.getKey().getN().multiply(bob.getKey().getN()));
            //System.out.println(bob.customDecryption(bob.getKey(), Mk[j]));
        }
        
        // Alice encrypts her answers
        BigInteger[] Rs = new BigInteger[10];
        for (int j=0; j<10; j++){
            Rs[j] = alice.encryptStringMessageWithExternalKey(bob.getKey(), aliceAnswers[j], IandR[1])[0];
            //System.out.println(bob.decryptStringMessageWithOwnKey(Rs[j]));
        }
        
        // Alice has to computes her answers with Mk and sent them to Bob
        BigInteger[] Rk = new BigInteger[10];
        for(int j=0; j<10; j++){
            Rk[j] = Mk[j].multiply(Rs[j]);
        }
        
        // Bob has to decrypt every answer
        BigInteger[] decryptedAnswers = new BigInteger[10];
        for(int j=0; j<10; j++){
            System.out.println(AsciiConversion.IntToString(bob.decryptMessageWithOwnKey(Rk[j])));
        }
        
        
        
        
    }
    
     
    
}
