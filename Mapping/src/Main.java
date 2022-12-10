import java.util.ArrayList;                                         //import the ArrayList class
import java.util.*;                                                //import java package

public class Main {
    public static void main(String[] args) {
        myMap instance = new myMap();                             //instance of class myMap

        instance.put("A", "Arithmetic");                          //Putting Values in the map
        instance.put("B","Biology");
        instance.put("C", "Calculus");
        instance.put("D", "DBMS");

        Scanner x = new Scanner(System.in);
        System.out.print("KeyList: " + instance.keyList);        //Displays the Keys for user
        System.out.print("\nEnter the Key: ");
        String str = x.nextLine();
        System.out.println(instance.get(str));                  //Returns the value of the key given by user

    }
}

class myMap
{
    ArrayList<String> keyList;
    ArrayList<String> valueList;
    myMap()
    {
        keyList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    public boolean put(String key, String value)               //Defining method to add key and value to the array
    {
        int index = keyList.size();
        keyList.add(key);
        valueList.add(value);

        return true;
    }

    public String get(String key)  //Defining method to retrieve the value of key
    {
        for(int i = 0 ; i < keyList.size() ; i ++)              //Iterating through the KeyList
        {
            if(keyList.get(i).equals(key))                     //Comparing current index value with key
            {
                return valueList.get(i);
            }
        }
        return "THE KEY IS NOT VALID";                         //Prints the message if key is null or not present
    }
}


