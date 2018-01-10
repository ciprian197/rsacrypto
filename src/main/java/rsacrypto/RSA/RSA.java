package rsacrypto.RSA;


import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;

/**
 * Created by dariusi on 1/9/18.
 */
public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int k,l;
    private int bitlength = 5;

    private Random r;
    public RSA() {
        r = new Random();
        k = 2;
        l = 3;
        do {
            p = BigInteger.probablePrime(bitlength, r);
            q = BigInteger.probablePrime(bitlength, r);
            N = p.multiply(q);
            bitlength++;
        }while (N.compareTo(BigInteger.valueOf((int)pow(27,k))) < 0 || N.compareTo(BigInteger.valueOf((int)pow(27,l))) > 0);

//        p = new BigInteger("31");
//        q = new BigInteger("53");
//
//        N = new BigInteger("1643");
//        e = new BigInteger("67");

        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0 ) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);

    }

    public static void main (String[] args) throws IOException, IOException {
        RSA rsa = new RSA();
        DataInputStream in=new DataInputStream(System.in);
        String teststring ;
        System.out.println("Enter the plain text:");
        teststring=in.readLine();
        String ciphertext = rsa.encrypt(teststring);
        String plaintext = rsa.decrypt(ciphertext);
        System.out.println("Ciphertext:" + ciphertext);
        System.out.println("Decypted:" + plaintext);
    }

    private int[] toNumbers(String message)
    {
        int[] msg = new int[message.length()];
        for (int i = 0; i < message.length(); ++i){
            if(message.charAt(i) == '_')
                msg[i] = 0;
            else
                msg[i] = (int)message.charAt(i) - 96;
        }
        return msg;
    }

    public int[] normalize(ArrayList<int[]> list){
        int[] res = new int[list.size()];
        for (int i = 0; i <list.size() ; i++) {
            int n = 0;
            for (int j = 0; j < list.get(i).length ; j++) {
                n += pow(27,j)*list.get(i)[list.get(i).length - j - 1];
            }
            res[i] = n;
        }
        return res;
    }

    public ArrayList<int[]> split(int[] n, int k){
        ArrayList<int[]> myList = new ArrayList<>();
        int[] seq  = new int[k];
        int len = n.length / k + (n.length % k == 0 ? 0 : 1);
        for(int i = 0; i < len * k; i++)
        {
            if(i >= n.length)
            {
                seq[i % k] = 0;
            }
            else
            {
                seq[i % k] = n[i];
            }

            if(i % k == k - 1)
            {
                myList.add(seq.clone());
            }
        }
        return myList;
    }

    public int[] splitBack(int nr, int l){
        int[] res = new int[l];
        for (int i = 0; i < l ; i++) {
            res[l - i - 1] = nr % 27;
            nr /= 27;
        }
        return res;
    }

    public String getCyphertext(int[] n, int l){
        String s = "";
        for (int i = 0; i < n.length; i++) {
            int[] res = splitBack(n[i],l);
            for (int j = 0; j < res.length; j++) {
                if(res[j] == 0)
                    s+= "_";
                else
                    s += (char)(res[j] + 96);
            }
        }
        return s.toUpperCase();
    }

    //Encrypt message
    public String encrypt(String message) {

        int[] numerical = normalize(split(toNumbers(message.toLowerCase()), k));
        for (int i = 0; i < numerical.length ; i++) {
            numerical[i] = new BigInteger(String.valueOf(numerical[i])).modPow(e,N).intValue();
        }
        return getCyphertext(numerical, l);
    }

    // Decrypt message
    public String decrypt(String message) {
        int[] numerical = normalize(split(toNumbers(message.toLowerCase()), l));
        for (int i = 0; i < numerical.length ; i++) {
            numerical[i] = new BigInteger(String.valueOf(numerical[i])).modPow(d,N).intValue();
        }
        return getCyphertext(numerical, k);
    }
}

