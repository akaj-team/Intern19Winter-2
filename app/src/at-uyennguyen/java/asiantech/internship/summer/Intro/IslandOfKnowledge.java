package asiantech.internship.summer.Intro;

public class IslandOfKnowledge {
    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        if(((yourLeft+yourRight)==(friendsLeft+friendsRight))&&(yourLeft==friendsLeft||yourLeft==friendsRight)){
            return true;
        }
        return false;
    }
    static int arrayMaximalAdjacentDifference(int[] inputArray) {
        int a=0;
        int b=0;
        for(int i=0;i<inputArray.length-1;i++){
            if(inputArray[i]>inputArray[i+1]){
                b= inputArray[i]-inputArray[i+1];
            }
            else{
                b= inputArray[i+1]-inputArray[i];
            }
            if(b>a){
                a=b;
            }

        }
        return a;
    }
     static boolean isIPv4Address(String inputString) {
        String[]a = inputString.split("[.]");
        if( a.length != 4){
            return false;
        }
        for(int i=0;i<a.length;i++){
            if(!a[i].matches("[0-9]{1,3}")){
                return false;
            }
            if(a[i].isEmpty()){
                return false;
            }
            if((Integer.parseInt(a[i])<0)||(Integer.parseInt(a[i])>255)){
                return false;
            }
        }
        return true;
    }
    static int avoidObstacles(int[] inputArray) {

        boolean found = false;
        int i = 1;
        while(true){
            found = true;
            for(int j = 0; j < inputArray.length; j++){
                if(inputArray[j] % i == 0){
                    found = false;
                }
            }
            if(found){
                return i;
            }
            i++;
        }
    }

    static int[][] boxBlur(int[][] image) {
        int[][] result = new int[image.length-2][image[0].length-2];
        for (int i =1; i < image.length-1; i++){
            for (int j = 1; j < image[0].length-1; j++) {
                int sum=0;
                for(int ii=i-1;ii<=i+1;ii++) {
                    for(int jj=j-1;jj<=j+1;jj++) {
                        sum+=image[ii][jj];
                    }
                }
                result[i-1][j-1]=sum/9;
            }
        }
        return result;
    }

    int[][] minesweeper(boolean[][] matrix) {
        int[][] sol = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                for (int ii = Math.max(0,i - 1); ii <=
                        Math.min(i + 1,matrix.length-1); ii++){
                    for (int jj = Math.max(0,j - 1); jj <=
                            Math.min(j + 1,matrix[0].length-1); jj++) {
                        if (matrix[ii][jj] && (i!=ii || jj!=j)) {
                            sol[i][j]++;
                        }
                    }
                }
            }
        }
        return sol;
    }


}
