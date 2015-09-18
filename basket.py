import sys

def init_basket():
    '''
    Initialize the basket composed of different fruits
    Empty the current basket with cost equals to zero
    Define the price of each fruit
    '''
    global prices #Global variable containing a dictionnary binding fruit to its price
    global fruit_min 
    global basket
    global basket_cost    
    fruit_min = {"B":"Banana","O":"Orange","A":"Apple","L":"Lemon","P":"Peach"}
    prices = {"Banana":1.5,"Orange":2.3,"Apple":2.8,"Lemon":4.1,"Peach":3.1}
    basket = {"Banana":0,"Orange":0,"Apple":0,"Lemon":0,"Peach":0}
    basket_cost = 0.0

def print_basket():
    '''
    Print the complete composition of the current basket
    Print the basket total cost
    only if the basket is not empty
    '''
    if float(basket_cost) != 0.0: #Test if the basket is empty, ie. its cost is equal to zero
        print "Your basket is composed of: \n"
        for k,v in basket.items(): #Going through the basket dictionary
            print k + ": " + str(v)
        print "For a total cost of " + str(basket_cost)
    else:
        print "Your basket is currently empty."

def main_menu():
    ''' Print the menu available for the user '''
    print "Menu"
    print "(1) Show your current basket and its total cost."
    print "(2) Import a basket."
    print "(3) Add some fruits."
    print "(4) Remove some fruits."
    print "(5) Reset your basket."
    print "(6) Show unit cost of the fruits."

def print_unit_cost():
    ''' Print the unit cost of each fruit available '''
    for k,v in prices.items(): #Going through the dictionary
        print k + ": " + str(v)

def add_fruit(fruit,number):
    '''
    Add a certain number of fruit in the basket

    @type fruit: string
    @param fruit: The name of the fruit to be added to the basket
    @type number: int
    @type number: The number of fruit to be added to the basket
    '''
    global basket_cost #Retrieve the global variable containing the basket cost
    basket[fruit] = basket[fruit] + int(number) #Add the number of fruit to the basket
    basket_cost =  basket_cost + prices[fruit]*float(number) #Convert the number to float for the multiply operation
    return True;

def remove_fruit(fruit,number):
    '''
    Remove a certain number of fruit of the basket

    @type fruit: string
    @param fruit: The name of the fruit to be removed from the basket
    @type number: int
    @type number: The number of fruit to be removed from the basket

    @rtype: boolean
    @return: True if removal has worked else False
    '''
    global basket_cost #Retrieve the global variable containing the basket cost
    if basket[fruit] >= int(number): #Test if there is enough fruit to remove
        basket[fruit] = basket[fruit] - int(number) #Remove of a number of fruit
        basket_cost = basket_cost - prices[fruit]*float(number) #Substract the cost of removed fruit to the basket
        return True #Return true to confirm the removal
    else:
        return False #Return false to invalidate the removal

def add_basket(test_basket):
    '''
    Add a list of fruit to the basket

    @type test_basket: string
    @param test_basket: The input of the user to be parsed into a list of fruits
    '''
    
    if test_basket: #If the input is not void
        list_input = map(str, test_basket.split(',')) #Try to create a list from the input
        if isinstance(list_input, list): #If the list is created
            add_to_basket(list_input)

def add_to_basket(list_input):
    list_fruit = ['Banana','Orange','Apple','Lemon','Peach'] #List of the available fruit to be added
    for n in list_input: #Going through the list
        if n in list_fruit: #If the element in the input list is a fruit
            add_fruit(n,1) #Add fruit one by one
        else:
            print n + " is not a fruit" 
    
def test_input(isAdd):
    '''
    Test the input of user to add or remove fruits to the basket

    @type isAdd: boolean
    @param isAdd: Add fruit if True else Remove fruit
    '''
    transaction_completed = False; #Boolean to test if the add/remove is complete
    while (True): #First step, choose the fruit
        input_Fruit = raw_input("Which fruit would you add? (B)anana, (O)range, (A)pple, (L)emon, (P)each? Or go back to (M)enu ") #Ask user for a letter
        if (input_Fruit == "B") or (input_Fruit == "O") or (input_Fruit == "A") or (input_Fruit == "L") or (input_Fruit == "P") : #If input is a fruit
            fruit = fruit_min[input_Fruit] #Retrieve the complete name for the fruit
            while (True): #Second step, choose the number of fruit
                input_Number = raw_input("How many " + fruit + " would you like to " + ("add" if isAdd else "remove") + " to your basket? ")
                if input_Number.isdigit(): #Test if input is a number
                    if int(input_Number) > 0 and int(input_Number) < 100: #Test if number is between 0 and 100
                        if isAdd: 
                            add_fruit(fruit,input_Number); #Add number of fruits
                            transaction_completed = True
                        else:
                            if remove_fruit(fruit,input_Number): #Test if removal has worked
                                transaction_completed = True
                            else:
                                print "Not enough fruits in your basket"
                        break; 
                    else:
                        print "Please a number between 0 and 100"
                else:
                    print "Please enter a valid number"
            if transaction_completed:
                break
        elif test_Add == "M": #Going back to main menu
            break                  
        else:
            print "Please enter the letter corresponding to the fruit"  

def basket_menu():
    '''
    Show menu
    '''
    while(True):
        main_menu()
        while (True): #While the input for the choice in menu is wrong
            testVar = raw_input("What to you want to do?\n")
            if testVar.isdigit():
                if int(testVar) > 0 and int(testVar) < 7:
                    break
            print "Please enter a number between 1 and 6 included";
        testVar = int(testVar)
        if testVar == 1:
            print_basket()
        elif testVar == 2:
            test_basket = raw_input("Import your basket. (e.g. 'Banana,Orange,Banana')\n")
            if test_basket:
                add_basket(test_basket)
        elif testVar == 3:
            test_input(True)
        elif testVar == 4:
            test_input(False)
        elif testVar == 5:
            init_basket()
        elif testVar == 6:
            print_unit_cost()
            print "Your basket has been reset"
 
def main():
    print "Hello!"
    init_basket()
    basket_menu()
    
if __name__ == "__main__":
    main()
    

