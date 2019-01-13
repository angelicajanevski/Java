public class ParallelPointers {
    //there are at most a.length*log2(a.length)+b.length*log2(b.length)+2(a.length+b.length)
    //for arrays of 1 mil inputs, 2004 mill comparisons
    public static int listIntersection(int[]a, int[] b){
        int count=0;
        a=BinarySearch.sort(a);
        b=BinarySearch.sort(b);

        int ptrA=0;
        int ptrB=0;

        while(ptrA<a.length && ptrB<b.length){
            if(a[ptrA]==b[ptrB]){
                count++;
                ptrA++;
                ptrB++;
            }

            else if(a[ptrA]<b[ptrB]){
                ptrA++;
            }

            else{
                ptrB++;
            }
        }

        return count;
    }
}
