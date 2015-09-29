package basketapp;

/**
 *
 * @author xavier.guerbet
 */
public enum Fruit {
    Banana("B", "Banana", 1.5),
    Orange("O", "Orange", 2.3),
    Apple("A", "Apple", 2.8),
    Lemon("L", "Lemon", 4.1),
    Peach("P", "Peach", 3.1);
    
    private final String abbr;
    private final String full;
    private final double fruitCost;

    Fruit(String _abbr, String _full, double _fruitCost){
        this.abbr = _abbr;
        this.full = _full;
        this.fruitCost = _fruitCost;
    }
    
    public String getAbbr(){
        return abbr;
    }
    
    public String getFull(){
        return full;
    }

    public double getCost(){
        return fruitCost;
    }
    
    /**
     * Display the cost of each fruit available in a basket
     */
    public static void displayUnitCost() {
        System.out.println("Unit cost of each fruit: ");
        for (Fruit fruit: Fruit.values()){
            System.out.println("(" + fruit.getAbbr() + ") " + fruit.name() + ": " + fruit.getCost());
        }
    }
}