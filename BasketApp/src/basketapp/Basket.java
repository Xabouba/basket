package basketapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author xavier.guerbet 
 */
public class Basket {
    public HashMap<Fruit, Integer> hmap;
    public double totalCost;
        
    public Basket (){
        this.hmap = new HashMap<>();
        this.totalCost = 0.0;
    }
    
    /**
     * Display the content of the basket
     */
    public void displayBasket (){
        if (this.totalCost == 0.0) {
            System.out.println("The basket is currently empty");
        } else {
            System.out.println("The basket is made of: ");      
            this.hmap.entrySet().stream().forEach((mentry) -> {
                System.out.println(mentry.getKey() + ": " + mentry.getValue());
            });
            System.out.println("The total cost of this basket is " + totalCost);
        }
    }
    
    /**
     * Add the number of fruit to the basket
     * 
     * @param fruit     the Fruit to add to the basket
     * @param number    the number of fruits to add (integer)
     */
    public void addFruit (Fruit fruit, int number){
        //Check if there is at least one occurence of fruit in the hashmap of the basket
        if (this.hmap.containsKey(fruit)){
            this.hmap.put(fruit, this.hmap.get(fruit) + number);
        } else {
            this.hmap.put(fruit, number);
        }
        //Update the total cost of the basket
        totalCost += fruit.getCost()*number;
        System.out.println("Added " + number + " " + fruit.name() + " to the basket");
    }
    
    /**
     * Remove the number of fruit from the basket if possible
     * 
     * @param fruit     the Fruit to remove to the basket
     * @param number    the number of fruits to remove from the basket
     */
    public void removeFruit (Fruit fruit, int number){
        //Check if there is not enough fruit
        if (this.hmap.get(fruit) < number){
            System.out.println("There is not enough " + fruit.name() + " to remove");
        } else {
            //Remove the netry from the hashmap if there is no fruit anymore in the basket
            if (this.hmap.get(fruit) == number){
                this.hmap.remove(fruit);
                System.out.println("There is no " + fruit.name() + " anymore in your basket");
            } else {
                this.hmap.put(fruit, this.hmap.get(fruit) - number);
                System.out.println("Removed " + number + " " + fruit.name() + " from the basket");
            }
            //Update the total cost of the basket
            totalCost -= fruit.getCost()*number;
        }
    }
    
    /**
     * Add a basket to the current basket
     * 
     * @param basket the content of a basket to add to the current basket
     */
    public void addBasket (Basket basket){
        basket.hmap.entrySet().stream().forEach((mentry) -> {
            addFruit((Fruit)mentry.getKey(), (int)mentry.getValue());
        });
    }
    
    /**
     * Remove a basket from the current basket
     * 
     * @param basket the content of current basket to remove
     */
    public void removeBasket (Basket basket){
        try {
            basket.hmap.entrySet().stream().forEach((mentry) -> {
            removeFruit((Fruit)mentry.getKey(), (int)mentry.getValue());
            });
        } catch (NullPointerException e){
        }
    }
    
    /**
     * Return the Fruit from a string
     * If not found, return null
     * 
     * @param str   a name of fruit
     * @return      the Fruit with the name str
     */
    public static Fruit stringAsFruit(String str){
        for (Fruit fruit: Fruit.values()){
            if (fruit.name().equalsIgnoreCase(str))
                return fruit;
        }
        return null;
    }
    
    /**
     * Find, read and create basket from a CSV file
     * 
     * @param file the path to a CSV file to read from
     */
    public void readCSVToBasket (String file){
	BufferedReader br = null;
	String line;
        String cvsSplitBy;

        cvsSplitBy = ",";
        HashMap<Fruit,Integer> currentHmap = new HashMap<>();
	try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] fruitStr = line.split(cvsSplitBy);
                Fruit fruit;
                if(fruitStr.length > 0){
                    fruit = stringAsFruit(fruitStr[0]);
                    if(fruit != null){
                        if (currentHmap.containsKey(fruit)){
                            currentHmap.put(fruit, currentHmap.get(fruit) + 1);
                        } else {
                            currentHmap.put(fruit, 1);
                        }
                    }                    
                }
            }
            Basket basket = new Basket();
            basket.hmap = currentHmap;
            this.addBasket(basket);
	} catch (FileNotFoundException e) {
	} catch (IOException e) {
	} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
	}
    }
}
