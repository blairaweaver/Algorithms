import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int[] temp = new int[20];
        RandomArray(temp);

        System.out.println();
        int recMax = FindMaxRec(temp, 0, temp.length -1);
        System.out.println(temp[recMax]);

//        this method does modify the original array. Should modify to fix this
        int nonRecMax = FindMax(temp);
        System.out.println(nonRecMax);
    }

    public static int FindMax(int[] x) {
        int[] temp = x;
        int numInArray = x.length;
        int halfArray = (int) Math.ceil(x.length / 2);
        while(numInArray > 1) {
            for (int i = 0; i < numInArray; i++){
                if (temp[i] < temp[numInArray - i -1]) {
                    temp[i] = temp[numInArray -i -1];
                }
            }
            numInArray = halfArray;
            halfArray = (int) Math.ceil(numInArray / 2);
        }

        return temp[0];
    }

    public static int FindMaxRec(int[] x, int left, int right) {
        if(left < right) {
            int mid = (int) Math.floor((left + right) / 2);
            left = FindMaxRec(x, left, mid);
            right = FindMaxRec(x, mid + 1, right);
            if (x[left] < x[right]) {
                return right;
            }
            else {
                return left;
            }
        }
        else return left;
    }

    public static void RandomArray(int[] x) {
        Random rand = new Random();
        for (int i = 0; i < x.length; i++) {
            x[i] = rand.nextInt(100);
        }
    }
}
