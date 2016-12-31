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
/* Used for abstraction layer only*/
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
    
    /* GETTERS & SETTERS */
    public Sender getSender(){
        return s;
    }
    
    /* By default, generating own key */
    private void initializaion() {
        s.setKey(s.Keygen());
    }
    
    /* ENCRYPTIONS */
    public BigInteger encryptMessageWithPublicKey(int msg) {
        BigInteger x = new BigInteger(msg+"");
        return s.EncryptWithOwnKey(x);
    }
    
    public BigInteger encryptStringMessageWithPublicKey(String msg) {
        BigInteger x = new BigInteger(StringToInt(msg));
        return s.EncryptWithOwnKey(x);
    }
    
    public BigInteger encryptMessageWithOnBoardKey(int msg, Key key){
        return s.EncryptWithExternalKey(new BigInteger(msg+""), key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }
    
    public BigInteger encryptStringMessageWithOnBoardKey(String msg, Key key){
        return s.EncryptWithExternalKey(new BigInteger(StringToInt(msg)), key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }

    /* DECRYPTIONS */
    public String decryptMessageWithPrivateKey(BigInteger cmsg) {
        return ""+s.DecryptWithOwnKey(cmsg);
    }
    
    public String decryptStringMessageWithPrivateKey(BigInteger cmsg) {
        return IntToString(""+s.DecryptWithOwnKey(cmsg));
    }
    
    public String decryptMessageWithOnBoardKey(BigInteger msg, Key key){
        return ""+s.DecryptWithExternalKey(msg, key.getPublicKey().get("e"), key.getPublicKey().get("n"));
    }
    
    public String decryptStringMessageWithOnBoardKey(BigInteger msg, Key key){
        return IntToString(""+s.DecryptWithExternalKey(msg, key.getPublicKey().get("e"), key.getPublicKey().get("n")));
    }

    
    
    /* FONCTIONNAL */
    public String StringToInt(String s){
        String msg = "";
        for(int i=0; i<s.length(); i++){
            String character = ((int) s.charAt(i))+"";
            while(character.length() < 3){
                character = "0"+character;
            }
            msg += character;
        }
        
        return msg;
    }
    
    public String IntToString(String s){
        String msg = "";
        while (s.length() % 3 != 0){
            s = "0"+s;
        }
        for(int i=0; i<s.length()/3; i++){
            int temp = Integer.parseInt(s.subSequence(i*3, (i+1)*3).toString());
            msg += Character.toString ((char) temp);
        }
        return msg;
    }
    
    /* BAD UTILIZATION OF RSA */
    public BigInteger test_4_3(BigInteger msg){
        BigInteger n = s.getPublicKey().get("n");
        
        BigInteger top = ((msg.add(new BigInteger("1"))).pow(3)).add(new BigInteger("2").multiply(msg.pow(3))).subtract(new BigInteger("1"));
        BigInteger bottom = ((msg.add(new BigInteger("1"))).pow(3)).subtract(msg.pow(3)).add(new BigInteger("2"));
        
        return top.divide(bottom).mod(n);
    }
}
