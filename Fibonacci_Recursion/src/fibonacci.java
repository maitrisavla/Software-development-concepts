public class fibonacci {

    static class MaximumRecursionDepth extends Exception {
        public static int glb =0;

        //Returns the level of recursion
        public static int getDepth(int limit, int level, int num) throws MaximumRecursionDepth {
            glb = glb + 1;
            level = glb;

            //Checks if Recursion Depth is less than 10
            if(level<=10) {
                System.out.println("current level:" + level);
            }

            //Checks if number is less than 1
            if (num <= 1) return num;
            //Checks if number is greater than 1
            if (level > 10 && num > 1) throw new MaximumRecursionDepth();
            return getDepth(limit, level, num - 1) + getDepth(limit, level, num - 2);

        }

        public MaximumRecursionDepth() {
            System.out.println("Maximum Recursion Depth Reached");
            getMessage();

        }
    }

    public static void main(String args[]) {
        try {
            MaximumRecursionDepth.getDepth(10, 0, 3);
        } catch (MaximumRecursionDepth e) {
            e.getMessage();
        }
    }
}
