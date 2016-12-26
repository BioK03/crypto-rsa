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
public class SenderAbs {

    private Sender s;

    public SenderAbs() {
        s = new Sender();
        initializaion();

    }

    public SenderAbs(String name) {
        s = new Sender(name);
        initializaion();
    }
    
    public Sender getSender(){
        return s;
    }

    private void initializaion() {
        s.setKey(s.Keygen());
    }

    public BigInteger encryptMessageWithPublicKey(int msg) {
        BigInteger x = new BigInteger(msg+"");
        return s.Encrypt2(x);
    }
    
    public BigInteger encryptStringMessageWithPublicKey(String msg) {
        BigInteger x = new BigInteger(StringToInt(msg));
        return s.Encrypt2(x);
    }

    public String decryptMessageWithPrivateKey(BigInteger cmsg) {
        return ""+s.Decrypt2(cmsg);
    }
    
    public String decryptStringMessageWithPrivateKey(BigInteger cmsg) {
        
        return IntToString(""+s.Decrypt2(cmsg));
    }
    
    public BigInteger encryptMessageWithOnBoardKey(BigInteger msg, Key key){
        return s.Encrypt(msg, key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }
    
    public String StringToInt(String s){
        String msg = "";
        for(int i=0; i<s.length(); i++){
            msg += (int) s.charAt(i);
        }
        
        return msg;
    }
    
    public String IntToString(String s){
        String msg = "";
        for(int i=0; i<s.length()/2; i++){
            int temp = Integer.parseInt(s.subSequence(i*2, (i+1)*2).toString());
            msg += Character.toString ((char) temp);
        }
        return msg;
    }
    
    public BigInteger test_4_3(BigInteger msg){
        BigInteger n = s.getPublicKey().get("n");
        
        BigInteger top = ((msg.add(new BigInteger("1"))).pow(3)).add(new BigInteger("2").multiply(msg.pow(3))).subtract(new BigInteger("1"));
        BigInteger bottom = ((msg.add(new BigInteger("1"))).pow(3)).subtract(msg.pow(3)).add(new BigInteger("2"));
        
        return top.divide(bottom).mod(n);
    }
}
