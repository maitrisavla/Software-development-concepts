class ListHierarchy
{
    //Node for inserting
    static Node root;

    //Node for searching
    static Node searchRoot;

    //Coin type
    static Coin currentCoin;
    ListHierarchy(Coin flip)
    {
        currentCoin = flip;
    }


    //To get the sequence of the keys we get while finding for the given key
    static String keypath(String key)
    {
        StringBuilder sb = new StringBuilder();
        Node current = searchRoot;

        while(current != null)
        {
            sb.append(current.key + "\t");

            Node next = current.next;
            Node prev = current.prev;

            if(current.key.equals(key))
            {
                //Returns if searchRoot is equal to key
                return sb.toString();
            }
            else if(next != null && next.key.equals(key))
            {
                current = next;
            }
            else if(prev != null && prev.key.equals(key))
            {
                current = prev;
            }
            else if( (next != null && key.compareTo(current.key) > 0 && key.compareTo(next.key) < 0)
                    || (prev != null && key.compareTo(current.key) < 0 && key.compareTo(prev.key) > 0))
            {
                if(next != null && key.compareTo(current.key) > 0 && key.compareTo(next.key) < 0)
                {
                    sb.append(current.next.key + "\t");
                }
                else if(prev != null && key.compareTo(current.key) < 0 && key.compareTo(prev.key) > 0)
                {
                    sb.append(current.prev.key + "\t");
                }

                current = current.down;
            }
            else if(next != null && key.compareTo(current.key) > 0 && key.compareTo(next.key) > 0)
            {
                current = current.next;
            }
            else if(prev != null && key.compareTo(current.key) < 0 && key.compareTo(prev.key) < 0)
            {
                current = current.prev;
            }
            else
            {
                current = current.down;
            }
        }
        return sb.toString();
    }

    //To get the data associated with the key
    static String find(String key)
    {
        Node current = searchRoot;
        while(current != null)
        {
            Node next = current.next;
            Node prev = current.prev;

            if(current.key.equals(key))
            {
                return current.data;
            }
            else if(next != null && next.key.equals(key))
            {
                current = next;
            }
            else if(prev != null && prev.key.equals(key))
            {
                current = prev;
            }
            else if( (next != null && key.compareTo(current.key) > 0 && key.compareTo(next.key) < 0)
                    || (prev != null && key.compareTo(current.key) < 0 && key.compareTo(prev.key) > 0))
            {
                current = current.down;
            }
            else if(next != null && key.compareTo(current.key) > 0 && key.compareTo(next.key) > 0)
            {
                current = current.next;
            }
            else if(prev != null && key.compareTo(current.key) < 0 && key.compareTo(prev.key) < 0)
            {
                current = current.prev;
            }
            else
            {
                current = current.down;
            }
        }
        return null;
    }

    //To get the string that displays the full structure of the object
    static void printToString()
    {
        Node level = searchRoot;
        while(level != null)
        {
            Node currentNode = level;
            while(currentNode.prev != null)
            {
                currentNode = currentNode.prev;
            }

            while(currentNode != null)
            {
                System.out.print(currentNode.key + "\t");
                currentNode = currentNode.next;
            }

            System.out.println();
            level = level.down;
        }
        System.out.println();

    }

    static void updateSearchNodeIfNecessary(Node newNode)
    {
        boolean anyUpperNodeFound = false;

        Node current = newNode;
        while(!anyUpperNodeFound && current.next != null)
        {
            current = current.next;
            if(current.up != null) anyUpperNodeFound = true;
        }

        current = newNode;
        while(!anyUpperNodeFound && current.prev != null)
        {
            current = current.prev;
            if(current.up != null) anyUpperNodeFound = true;
        }

        if(!anyUpperNodeFound && current.prev == null)
        {
            searchRoot = current;
        }
    }

    static void addToNextLevel(Node newNode)
    {
        //Search node in the upper level which would be previous to our Node
        Node prevNode = searchPreviousUpperLevel(newNode);

        //Search node in the upper level which would be next to our Node
        Node nextNode = searchNextUpperLevel(newNode);

        //Create the node in the upper level and assign previous and next nodes
        newNode.up = new Node(newNode.key, newNode.data);
        newNode.up.level = newNode.level + 1;
        newNode.up.down = newNode;
        newNode = newNode.up;
        newNode.prev = prevNode;
        if(prevNode != null){
            prevNode.next = newNode;
        }
        newNode.next = nextNode;
        if(nextNode != null){
            nextNode.prev = newNode;
        }

        //Check if we need to put the node into the next upper level
        if(newNode.next != null || newNode.prev != null)
        {
            updateSearchNodeIfNecessary(newNode);

            System.out.println(newNode.key + " is on level " + newNode.level + ". Do you wish to add it on level " + (newNode.level+1) );
            if(currentCoin.flip())
            {
                addToNextLevel(newNode);
            }
        }
        else
        {
            //Updating the searchRoot as we just added a new Node
            searchRoot = newNode;
        }
    }

    static Node searchNextUpperLevel(Node newNode)
    {
        Node nextNode = newNode.next;
        while(nextNode != null && nextNode.up == null)
        {
            nextNode = nextNode.next;
        }
        if(nextNode != null)
        {
            nextNode = nextNode.up;
        }
        return nextNode;
    }

    static Node searchPreviousUpperLevel(Node newNode)
    {
        Node prevNode = newNode.prev;
        while(prevNode != null && prevNode.up == null)
        {
            prevNode = prevNode.prev;
        }
        if(prevNode != null)
        {
            prevNode = prevNode.up;
        }
        return prevNode;
    }

    //To add the key in the data structure
    int add( String key, String data )
    {
        Node newNode = new Node(key, data);
        if(root == null)
        {
            root = newNode;
            searchRoot = root;
        }
        else
        {
            Node current = root;

            //Checking if the newNode is the first node and root needs to be replaced
            if(key.compareTo(current.key) < 1)
            {
                newNode.next = current;
                current.prev = newNode;
                root = newNode;
            }
            else{
                //Traverse till we find the right place to insert the key
                while(current.next != null && key.compareTo(current.key) > 0)
                {
                    current = current.next;
                }

                //Check if the newNode is the last Node
                if(current.next == null && key.compareTo(current.key) > 0)
                {
                    current.next = newNode;
                    newNode.prev = current;
                }
                //The Right place to insert the node
                else
                {
                    Node prevNode = current.prev;
                    prevNode.next = newNode;
                    newNode.prev = prevNode;
                    newNode.next = current;
                    current.prev = newNode;
                }
            }

            System.out.println(newNode.key + " is on level " + newNode.level + ". Do you wish to add it on level " + (newNode.level+1) );
            if(currentCoin.flip())
            {
                addToNextLevel(newNode);
            }

        }
        return 0;
    }

}
