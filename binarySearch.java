public class BinarySearch {
    //there are (a.length+b.length)*log2(b.length)+a.length comparisons
    //for 1 mil array inputs, 2001 mil comparisons
    public static int[] sort(int[] a) {
        int min_j = 0;
        int i = 0;
        int j = 0;

        for (i = 0; i < a.length; i++) {
            min_j = i;   // assume a[i] is the minimum
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < a[min_j]) {
                    min_j = j;    // find smaller minimum, update min_j
                }
            }
            //swap a[i] and a[min_j]
            int help = a[i];
            a[i] = a[min_j];
            a[min_j] = help;

        }
        return a;
    }

    public static void main(String[] args) {
        int[] a = {5, 4, 3,2};
        int[] b = {2, 2, 22, 2, 5, 2, 256};
        System.out.println(listIntersection(a, b));
    }

    public static int listIntersection(int[] a, int[] b) {
        //second array input must be sorted for binary search
        b = sort(b);
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            //this makes a.length*(log2(b.length)+1) comparisons
            if (binarySearch(b, a[i])) {
                count++;
            }
        }

        return count;
    }

    //this function halves set a [] each time. Therefore, there are log2(a.length)+1 comparisons,
    //including the final a[left]==k comparison
    public static boolean binarySearch(int[] a, int k) {
        int left = 0;
        int right = a.length;
        int mid = 0;

        while (right > left + 1) {
            //goal is to grab number between left and right bounds, left+1 leaves one space between
            //we want midpoint to round to a higher cell since comparision is a[mid]>k
            mid = (int) Math.ceil((right + left) / 2.0);
            //if mid greater than k, any element bigger than mid is not k, make right endpoint as mid
            if (a[mid] > k) {
                right = mid;
            } else {
                //if mid less than k, any element smaller than mid is not k, make left endpoint mid
                //when a[mid]==k, mark left as mid
                left = mid;
            }
        }

        if (a[left] == k) {
            return true;
        }

        return false;

    }


}
