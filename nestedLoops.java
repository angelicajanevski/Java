public class NestedLoops {
    //there are a.length x b.length comparisons
    //for 1 mil array inputs, 100000 mil comparisons
    public static int listIntersection(int[] a, int[] b){
        int count=0;
        for (int i=0; i<a.length;i++){
            for(int j=0; j<b.length; j++){
                if(a[i]==b[j]){
                    count++;
                }
            }
        }

        return count;
    }

}
