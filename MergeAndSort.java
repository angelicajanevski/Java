public class MergeAndSort {
    //there are (a.length+b.length)*log(a.length+b.length)+ a.length+b.length-1 comparisons
    //for arrays of 1 mil inputs each, 14.6 mil comparisons
    public static int listIntersection(int a[], int b[] ){
        int count=0;
        int [] c = new int[a.length+b.length];

        for (int i =0; i<a.length; i++){
            c[i]=a[i];
        }
        for(int j=0; j<b.length; j++){
            c[j+a.length]=b[j];
        }

        c=BinarySearch.sort(c);

        int ptr=0;
        while(ptr<(a.length+b.length)){
            if(c[ptr]==c[ptr+1]){
                count++;
                ptr+=2;
            }
        }

        return count;
    }
}
