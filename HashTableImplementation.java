package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

//Implement Hash table./Users/ashwinramananda/Desktop/course work/TopologicalSort/src/com/company/HashTableImplementation.java
//        Pick 20 random words.  Each word must be of different lengths, maximum length 8 and minimum length 3.
//        The words will be of letters a-zA-Z and the space character.
//        Insert them into a hash table.
//        You can use a library for only the hash function.
//        The collision resolution scheme should be open addressing - quadratic.
//        Initially the table size is 31.  The program should increase the table size and rehash at load factor of .5
//        So after you inserted about 15 or 16 words, your program automatically doubles the table size and re-inserts (automatically) the old words and then continue the insert of additional words.
//        You do not have to insert the words manually (one by one) but you can add the words in a file and let your program read from the file
//        At the end print the total number of collisions you get.
//        Submit your code and print screen of your execution


public class HashTableImplementation {

    float loadFactor = 0.5f;
    static int tableSize=31;
    static int size = 0;
    static int countOfCollisions =0;
    String [] hashTableqp=new String[tableSize];

    HashTableImplementation(){
        size = 0;
    }

    //hash function
    public int hashFunction(String key){

        int hashVal = 0;
        for (int i =0; i< key.length();i++){
            hashVal = 23 * hashVal + key.charAt(i);
        }

        if (hashVal < 0){
            hashVal += Integer.MAX_VALUE;
        }

    return hashVal;
    }

    public void insertQuadraticProbing(String key){

        int index = hashFunction(key) % hashTableqp.length;

        if(hashTableqp[index] == null){             // insert at the index no collision
            HashTableImplementation.size++;
            hashTableqp[index] = key;
        }

        else if (hashTableqp[index].equals(key)){   //the same key already exists so return
            return;
        }

        else {                                      //collision occurred so we are doing a quadratic probe
            int findNextIndex = 0;
            int count =1;
            findNextIndex = (index + count*count)% hashTableqp.length;

            while(findNextIndex != index){      //insert into the next index after quadratic probing
                if(hashTableqp[findNextIndex]==null){   // if the next index has nothing then insert value
                    hashTableqp[findNextIndex] = key;
                    HashTableImplementation.size++;
                    break;
                }
                else if(hashTableqp[index].equals(key)){
                    return;     // return if the same key is already present in the index
                }
                count++;
                HashTableImplementation.countOfCollisions++;        //count the number of collisions
                findNextIndex = (index + count * count) % hashTableqp.length;
            }
        }

        //checking the loadfactor and rehashing if the loadfactor is more than 0.5

        if (2*HashTableImplementation.size > hashTableqp.length){
            HashTableImplementation.size = 0;
            System.out.println("Number of collisions on the HashTable " +countOfCollisions);
            HashTableImplementation.countOfCollisions = 0;
            hashTableqp = rehashQuadraticProbing(hashTableqp);  // rehash the table as the load factor is greater than 1
        }
    }

    //function to rehash when the loadfactor is greater than 0.5

    public String[] rehashQuadraticProbing(String[] oldHashTable){
        String tempArr[] = Arrays.copyOf(oldHashTable, oldHashTable.length);
        oldHashTable = new String[findNextPrime(2*tempArr.length)];
        System.out.println("Increasing the table size to " +oldHashTable.length);
        for(String tempStr: tempArr){
            if(tempStr!=null){
                insertQuadraticProbing(tempStr);
            }
        }
        return oldHashTable;
    }

    public int findNextPrime(int i){
        if(isPrimeNumber(i)) return i;
        if(i<= 1) return 2;
        int primeNumber = i;
        boolean primeNumberFound = false;
        while(!primeNumberFound){
            primeNumber++;
            if(isPrimeNumber(primeNumber)) primeNumberFound = true;
        }
    return primeNumber;
    }


    public boolean isPrimeNumber(int i){
        if(i<=1) return false;
        if(i<=3) return true;
        if(i%2 == 0 || i%3 == 0) return false;
        for(int a=3;a<=Math.sqrt(i);a+=2) {
            if(i%a==0)
                return false;
        }
        return true;
    }


    public  static void main(String args[]){

        HashTableImplementation hashtable = new HashTableImplementation();
        try {
            //change the input file location to the location of the file in your system
            FileReader inputFile = new FileReader("/Users/ashwinramananda/Desktop/courseWork/TopologicalSort/src/com/company/input.txt");
            BufferedReader br = new BufferedReader(inputFile);
            String words = br.readLine();
            while (words!=null){
                hashtable.insertQuadraticProbing(words);
//                System.out.println(words);     uncomment this line to print the words
                words = br.readLine();
            }
        }
        catch(Exception e){
            System.out.println("Exception in the try block");
            e.printStackTrace();
        }
        System.out.println("No of collisions on the HashTable " +HashTableImplementation.countOfCollisions);
    }
}