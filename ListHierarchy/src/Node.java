class Node
{
    //Pointer of next item
    Node next;
    //Pointer of previous item
    Node prev;
    //Pointer of the above item
    Node up;
    //Pointer of the below item
    Node down;
    int level;
    String key;
    String data;
    Node(String key, String data)
    {
        this.key = key;
        this.data = data;
    }
}
