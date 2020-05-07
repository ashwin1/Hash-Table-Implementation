package com.company;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class HashTable {


    static int tableSize=53, sizelp=0,sizeqp=0,collisionlp=0,collisionqp=0;
    float loadFactor=0.5f;
    public HashTable() {
        sizelp=0;
        sizeqp=0;
    }
    String [] hashTablelp=new String[tableSize];
    String [] hashTableqp=new String[tableSize];
    private static Scanner scan;

    public int findNextPrime(int num) {
        if (isPrime(num)) return num;
        if (num <= 1)  return 2;
        int prime = num;
        boolean found = false;
        while (!found) {
            prime++;
            if (isPrime(prime)) {found = true;}
        }
        return prime;
    }

    public boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;
        for (int i = 5; i * i <= num; i = i + 6) {
            if (num % i == 0 || num % (i + 2) == 0) {return false;}
        }
        return true;
    }

    //Hash function te generate hashvalue of given string
    int hash (String key)
    {

        int hashVal = 0;
        for (int i = 0; i < key.length(); i++) {
            hashVal = 23 * hashVal + key.charAt(i);
        }
        if (hashVal < 0)
            hashVal += Integer.MAX_VALUE;
        return hashVal;
    }


    //Inserts an element if not present using linear probing
    //Also rehashes when load factor is greater than 0.5
    public void insertLinearProbing(String key) {

        int index=hash(key)%hashTablelp.length;
        if(hashTablelp[index]==null) {
            HashTable.sizelp++;
            hashTablelp[index]=key;
        }else if(hashTablelp[index].equals(key)) {
            return;
        }
        else {
            int nextIndex=(index+1)%hashTablelp.length;

            while(nextIndex!=index) {
                if(hashTablelp[nextIndex]==null) {
                    hashTablelp[nextIndex]=key;
                    HashTable.sizelp++;
                    break;
                }else if(hashTablelp[index].equals(key)){

                    return;
                }
                HashTable.collisionlp++;
                nextIndex++;
                nextIndex%=hashTablelp.length;
            }
        }
        if(2*HashTable.sizelp>hashTablelp.length) {
            HashTable.sizelp=0;
            System.out.println("No of collisions :"+HashTable.collisionlp);
            HashTable.collisionlp=0;
            hashTablelp=rehashlp(hashTablelp);
        }

    }
    //Rehashes when load factor is greater than 0.5
    public String[] rehashlp(String[] oldTable) {
        String[] tempArr=Arrays.copyOf(oldTable,oldTable.length);
        oldTable=new String[findNextPrime(2*tempArr.length)];
        System.out.println("Increasing table size to "+oldTable.length);
        for(String i:tempArr) {
            if(i!=null) {
                this.insertLinearProbing(i);

            }

        }

        return oldTable;
    }

    //Inserts an element if not present using quadratic probing
    public void insertQuadraticProbing(String key) {

        int index=hash(key)%hashTableqp.length;
        if(hashTableqp[index]==null) {
            HashTable.sizeqp++;
            hashTableqp[index]=key;
        }else if(hashTableqp[index].equals(key)) {
            return;
        }
        else {

            int c=1;
            int nextIndex=(index+c*c)%hashTableqp.length;
            while(nextIndex!=index) {
                if(hashTableqp[nextIndex]==null) {
                    hashTableqp[nextIndex]=key;
                    HashTable.sizeqp++;
                    break;
                }else if(hashTableqp[index].equals(key)){
                    return;
                }
                c++;
                HashTable.collisionqp++;
                nextIndex=(index+c*c)%hashTableqp.length;
            }
        }

        if(2*HashTable.sizeqp>hashTableqp.length) {
            HashTable.sizeqp=0;
            System.out.println("No of collisions :"+HashTable.collisionqp);
            HashTable.collisionqp=0;
            hashTableqp=rehashqp(hashTableqp);
        }

    }

    //Rehashes when load factor is greater than 0.5
    public String[] rehashqp(String[] oldTable) {
        String[] tempArr=Arrays.copyOf(oldTable,oldTable.length);
        oldTable=new String[this.findNextPrime(2*tempArr.length)];
        System.out.println("Increasing table size to "+oldTable.length);
        for(String i:tempArr) {
            if(i!=null)
                insertQuadraticProbing(i);
        }

        return oldTable;
    }

    //Used for searching the word in hashtable
    public int findLinearProbing(String key) {
        int index=hash(key)%hashTablelp.length;
        if(hashTablelp[index]==null) {
            return -1;
        }else if(hashTablelp[index].equals(key)) {
            return index;
        }
        else {
            int nextIndex=(index+1)%hashTablelp.length;
            while(hashTablelp[nextIndex]!=null) {
                if(hashTablelp[index].equals(key)){
                    return index;
                }
                nextIndex++;
                nextIndex%=hashTablelp.length;
            }
        }
        return -1;
    }

    //Used for searching the word in hashtable
    public int findQuadraticProbing(String key) {
        int index=hash(key)%hashTableqp.length;
        if(hashTableqp[index]==null) {
            return -1;
        }else if(hashTableqp[index].equals(key)) {
            return index;
        }
        else{
            int c=1;
            int nextIndex=(index+c*c)%hashTableqp.length;
            while(hashTableqp[nextIndex]!=null) {
                if(hashTableqp[index].equals(key)){
                    return index;
                }
                c++;
                nextIndex=(index+c*c)%hashTableqp.length;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] words= {"john","sam","castle","derek","ravi","raki","reddy","bala","tomy","krishna"};
        HashTable ht=new HashTable();
        System.out.println("Running Linear probing with a table size of 53");
        try {
            FileReader fr=new FileReader("/Users/ashwinramananda/Desktop/courseWork/TopologicalSort/src/com/company/input.txt");
            BufferedReader br = new BufferedReader(fr);
            String word=br.readLine();

            while(word!=null) {

                ht.insertLinearProbing(word);
                word=br.readLine();
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No of collisons:"+HashTable.collisionlp);
        System.out.println("Adding 10 more words");
        for(String s:words) {
            ht.insertLinearProbing(s);
        }
        System.out.println("No of collisons:"+HashTable.collisionlp);
        System.out.println("\n");
        System.out.println("Running Quadratic probing with a table size of 53");
        try {
            FileReader fr=new FileReader("/Users/ashwinramananda/Desktop/courseWork/TopologicalSort/src/com/company/input.txt");
            BufferedReader br = new BufferedReader(fr);
            String word=br.readLine();

            while(word!=null) {

                ht.insertQuadraticProbing(word);
                word=br.readLine();
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("No of collisons:"+HashTable.collisionqp);
        System.out.println("Adding 10 more words");
        for(String s:words) {
            ht.insertQuadraticProbing(s);
        }
        System.out.println("No of collisons:"+HashTable.collisionqp);

        scan = new Scanner(System.in);
        while(true) {
            System.out.println();
            System.out.println("Enter a word to search:");
            String word = scan.nextLine();
            try {
                while(true) {
                    System.out.println("Searching hashtable-linear probing");
                    int index;
                    if((index=ht.findLinearProbing(word))!=-1) {
                        System.out.println("Word found at "+ index+" :Word spelling is correct");
                    }else {
                        System.out.println("Word not found ");
                    }
                    System.out.println("");
                    System.out.println("Searching hashtable-Quadratic probing");
                    if((index=ht.findQuadraticProbing(word))!=-1) {
                        System.out.println("Word found at "+ index +" :Word spelling is correct");
                    }else {
                        System.out.println("Word not found ");
                    }
                    System.out.println("Enter a word to search:");
                    word=scan.nextLine();
                }
            }catch(Exception e) {
                System.out.println("Closing the spell checker");
                System.exit(0);
            }

        }
    }

}
