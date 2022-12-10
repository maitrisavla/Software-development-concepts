public class Assignment2{
    public static void main(String[] args) {


        ManualCoin coin = new ManualCoin();
        ListHierarchy lh = new ListHierarchy(coin);

        //Adding key and data into ListHierarchy
        lh.printToString();

        lh.add("apple","");
        lh.add("grape","");
        lh.add("date","");
        lh.add("orange","");

        lh.printToString();

        lh.add("lettuce","");
        lh.add("pepper","");
        lh.add("salt","");
        lh.add("tea","");

        lh.printToString();

        System.out.println(lh.keypath("lettuce"));

        System.out.println("Everything Added");

    }
}

