/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import basketapp.Basket;
import basketapp.Fruit;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ConcurrentModificationException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author xavier.guerbet
 */
public class BasketJUnitTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    /**
     * Set up output stream for tests
     */
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Remove the output stream for tests
     */
    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
    /**
     * Test adding one orange to basket
     */
    @Test
    public void testAddOneOrangeBasket() {
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 1);
        Assert.assertEquals(1, (int)basket.hmap.get(Fruit.Orange));
    }
    
    /**
     * Test adding one orange two times
     */
    @Test
    public void testAddOneOrangeNonEmptyBasket() {
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 1);
        basket.addFruit(Fruit.Orange, 1);
        Assert.assertEquals(2, (int)basket.hmap.get(Fruit.Orange));
    }
    /**
     * Test to display total cost for empty and non empty basket
     */
    @Test
    public void testTotalCost() {
        Basket basket = new Basket();
        Assert.assertEquals(0.0, basket.totalCost);
        basket.addFruit(Fruit.Orange, 1);
        Assert.assertEquals(Fruit.Orange.getCost(), basket.totalCost);
    }
    
    /**
     * Test adding CSV basket of 330480 fruits within 1 sec
     */
    @Test (timeout = 1000)
    public void testAddCSVBasket() {
        Basket basket = new Basket();
        basket.readCSVToBasket(System.getProperty("user.dir")+"/test/sample/Basket_Test.csv");
        Assert.assertEquals(720252.0, basket.totalCost);
        Assert.assertEquals(106920, (int)basket.hmap.get(Fruit.Orange));
    }
    
    /**
     * Test getting value of a non existing fruit
     */
    @Test (expected = NullPointerException.class)
    public void testGetValueMissingFruit(){
        Basket basket = new Basket();
        Assert.assertEquals(1, (int)basket.hmap.get(Fruit.Orange));
    }
    
    /**
     * Test removing a fruit from a non empty basket
     */
    @Test
    public void testRemoveOneOrangeBasket () {
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 2);
        Assert.assertEquals(2, (int)basket.hmap.get(Fruit.Orange));
        basket.removeFruit(Fruit.Orange, 1);
        Assert.assertEquals(1, (int)basket.hmap.get(Fruit.Orange));
    }

    /**
     * Test removing a fruit from an empty basket
     */
    @Test (expected = NullPointerException.class)
    public void testRemoveEmptyBasket(){
        Basket basket = new Basket();
        basket.removeFruit(Fruit.Orange, 1);
    }
    /**
     *
     */
    @Test (expected = NullPointerException.class)
    public void testGetValueMissingFruitAfterRemoval(){
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 1);
        basket.removeFruit(Fruit.Orange, 1);
        Assert.assertEquals(1, (int)basket.hmap.get(Fruit.Orange));
    }
    
    /**
     *
     */
    @Test
    public void testStringAsFruit(){
        Assert.assertEquals(Fruit.Banana, Basket.stringAsFruit("Banana"));
    }
    
    /**
     *
     */
    @Test
    public void testWrongStringAsFruit(){        
        Assert.assertNull(Basket.stringAsFruit("Banana1"));
    }
    
    /**
     *
     */
    @Test
    public void testAddBasket(){
        Basket initBasket = new Basket();
        initBasket.addFruit(Fruit.Orange, 1);
        Basket basket = new Basket();
        basket.addBasket(initBasket);
        Assert.assertEquals(initBasket.hmap, basket.hmap);
    }
    
    /**
     *
     */
    @Test (expected = ConcurrentModificationException.class)
    public void testRemoveBasketException() {
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 2);
        basket.removeBasket(basket);
    }
    
    /**
     *
     */
    @Test
    public void testRemoveBasket() {
        Basket initBasket = new Basket();
        initBasket.addFruit(Fruit.Orange, 2);
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 1);
        initBasket.removeBasket(basket);
        Assert.assertEquals(1, (int)initBasket.hmap.get(Fruit.Orange));
    }
    
    @Test
    public void testDisplayNonEmptyBasketOut(){
        Basket basket = new Basket();
        basket.addFruit(Fruit.Orange, 1);
        basket.displayBasket();
        Assert.assertNotSame("The basket is currently empty", outContent.toString());
    }
    
}
