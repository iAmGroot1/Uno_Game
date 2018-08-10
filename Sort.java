import java.util.Arrays;

class Sort extends AI{

    
    /**
     * this method takes an int array and returns the index of the largest int
     */
    public static int indexOfMaxInRange(int[]nums,int low, int high){

        int max = nums[low];
        int maxIndex = low;

        for(int i = low+1; i <= high; i++){

            if(nums[i] >= max){

                max = nums[i];
                maxIndex = i;

            }

        }

        return maxIndex;

    }

    
   
    /**
     * this method swaps two elements of an array at their indexes
     */
    public static int[] swapElement(int[] nums, int i1, int i2){

        int num = nums[i1];
        nums[i1] = nums[i2];
        nums[i2] = num;
        return nums;

    }

    
    
    /**
     * this method sorts an integer array in descending order
     */
    public static int[] sortArray(int[] scrambled){

        for(int i = 0; i < scrambled.length-1; i++){

            int highest = indexOfMaxInRange(scrambled,i,scrambled.length-1);
            scrambled = swapElement(scrambled,highest,i);

        }

        return scrambled;

    }

    
}

