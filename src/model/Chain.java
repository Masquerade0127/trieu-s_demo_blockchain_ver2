package model;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Scanner;

public class Chain {

    public ArrayList<Block> arrL_blockchain = new ArrayList<Block>();
    public int i_difficulty;
    Scanner scanner = new Scanner(System.in);

    /*
     * check validation for block
     * parameters: non
     * return: boolean
     */
    public boolean isChainValid(){
        Block current, previous;
        for(int i = 1; i < arrL_blockchain.size(); i++){
            current = arrL_blockchain.get(i);
            previous = arrL_blockchain.get(i-1);
            String hashTarget = new String(new char[i_difficulty]).replace("\0", "0");
            //check current block's hash
            if(!current.hash.equals(current.calculateHash())){
                System.out.println("current hash is invalid");
                /*System.out.print(current.hash);
                System.out.print(current.calculateHash());*/
                return false;
            }
            //check previous block's hash
            if(!previous.hash.equals(previous.calculateHash())){
                System.out.println("previous hash is invalid");
                return false;
            }
            //check if hash is solved
            if(!current.hash.substring(0, i_difficulty).equals(hashTarget)){
                System.out.println("this block isn't create");
                return false;
            }
        }
        return true;
    }

    /*
     * set difficulty for block's hash
     * parameters: non
     * return: non
     */
    public int setDifficulty(){
        //enter difficulty, it should be 1 to 3, so long if greater than 3
        System.out.print("difficulty: ");
        i_difficulty = scanner.nextInt();
        return i_difficulty;
    }

    /*
     * add block to chain
     * parameters: data String type
     * return non
     */
    public void addBlock(String data){
        if (arrL_blockchain.size() == 0){
            //create genesis block
            arrL_blockchain.add(new Block(data, "0"));
            //set difficulty
            arrL_blockchain.get(arrL_blockchain.size()-1).mine(i_difficulty);
            //set index for genesis block
            //arrL_blockchain.get(arrL_blockchain.size()-1).setIndex(0);
        }
        else{
            //create new block
            arrL_blockchain.add(new Block(data,arrL_blockchain.get(arrL_blockchain.size()-1).hash));
            //set difficulty
            arrL_blockchain.get(arrL_blockchain.size()-1).mine(i_difficulty);
            //set index for block
            arrL_blockchain.get(arrL_blockchain.size()-1).setIndex(arrL_blockchain.size()-1);
        }
    }

    /*
     * print blockchain
     * parameter: non
     * return: non
     */
    public void printBlockChain(){
        String blockchain_json = new GsonBuilder().setPrettyPrinting().create().toJson(arrL_blockchain);
        System.out.println("blockchain: ");
        System.out.println(blockchain_json);
    }

    public static void main(String[] args){
        Chain nc = new Chain();
        //set difficulty
        nc.setDifficulty();

        //add two block to chain
        nc.addBlock("first block");
        nc.addBlock("second block");
        nc.addBlock("third block");

        //check block is valid and print blockchain
        System.out.println("validation: " + nc.isChainValid());
        nc.printBlockChain();
    }
}
