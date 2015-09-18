import sys
import basket
import csv

def main():
    print "Hello!"
    pm = __import__('basket')

    raw_input("\nInit basket. Press Enter to continue...")
    #Init basket
    basket.init_basket()
    #Start Testing
    
    raw_input("\nShow main menu. Press Enter to continue...")
    #Show menu
    basket.main_menu()
    
    raw_input("\nShow unit cost. Press Enter to continue...")
    #Show Unit cost of fruits
    basket.print_unit_cost()
    
    raw_input("\nShow empty basket. Press Enter to continue...")
    #Show empty basket
    #Should show: Your basket is currently empty
    basket.print_basket()
    
    raw_input("\nAdd 1 apple. Press Enter to continue...")
    #Add one apple to the basket
    basket.add_fruit('Apple',1)
    #Show basket
    basket.print_basket()

    raw_input("\nTry removing 2 apples but not enough in the basket. Press Enter to continue...")
    #Try removing 2 apples of your basket
    basket.remove_fruit('Apple',2)
    #Show basket with 1 apple
    #Should not be empty as there was not enough apple
    basket.print_basket()
    
    raw_input("\nTry to remove 1 banana but not enough in the basket. Press Enter to continue...")
    #Try removing 1 banana of your basket
    basket.remove_fruit('Banana',1)
    #Show basketwith 1 apple
    #Should not be empty as there was no banana to be removed
    basket.print_basket()

    
    raw_input("\nRemove 1 apple. Press Enter to continue...")
    #Remove 1 apple
    #Should have empty basket
    basket.remove_fruit('Apple',1)
    #Show empty basket
    basket.print_basket()

    
    raw_input("\nAdd 3 banana list of fruits. Press Enter to continue...")
    #Add list of fruits with a str
    basket.add_basket('Banana,Banana,Banana')
    #Show basket with 3 bananas
    basket.print_basket()

    raw_input("\nReset basket. Press Enter to continue...")
    #Reset basket
    basket.init_basket()
    #Show basket with 3 bananas
    basket.print_basket()

    
    raw_input("\n Import csv file with correct input. Press Enter to continue...")
    #Add correct input
    your_list = []
    with open('Basket_Test.csv', 'rb') as f:
        reader = csv.reader(f)
        for row in f:
            your_list.append(row.strip())
    basket.add_to_basket(your_list)
    #Show basket
    basket.print_basket()

    raw_input("\n Reset and Import csv file with one wrong input. Press Enter to continue...")
    basket.init_basket()
    #Add correct input
    your_list = []
    with open('Basket_Test_Wrong_Input.csv', 'rb') as f:
        reader = csv.reader(f)
        for row in f:
            your_list.append(row.strip())
    basket.add_to_basket(your_list)
    #Show basket
    basket.print_basket()
    
if __name__ == "__main__":
    main()
    

