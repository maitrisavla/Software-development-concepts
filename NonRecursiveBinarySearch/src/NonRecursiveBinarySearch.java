class NonRecursiveBinarySearch{

    public static void binarySearch(int arr[], int first, int last, int key){

        int mid = (first + last)/2;
        //Assertion: Pre condition
        assert arr.length>0: "Array is not empty";
        while( first <= last ){
            //Assertion: Loop invariant
            assert arr.length>0: "Array is not empty";
            if ( arr[mid] < key ){

                first = mid + 1;
            }else if ( arr[mid] == key ){
                //Assertion: Loop invariant
                assert first <= mid: "Middle element is greater than first";
                assert mid <= last: "Middle element is smaller than first";
                System.out.println("Element is found at index: " + mid);
                break;
            }else{
                last = mid - 1;
            }
            mid = (first + last)/2;
        }
        //Assertion: Post condition
        assert arr.length>0: "Array is not empty";
        if ( first > last ){
            assert first !=0: "First can not be 0";
            System.out.println("Element is not found!");
        }

    }
    public static void main(String args[]){
        int arr[]={};
        int key = 20;
        int last=arr.length-1;
        binarySearch(arr,0,last,key);
    }
}  