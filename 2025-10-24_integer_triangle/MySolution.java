class Solution {
    Integer currentMax[][];
    int max_depth;
    int[][] triangle;
    
    public int solution(int[][] triangle) {
        this.triangle = triangle;
        max_depth = triangle.length - 1;
        currentMax = new Integer[triangle.length][triangle.length];
        
        int result = sumBelow(0,0);
        return result;
    }
    
    private int sumBelow(int level, int index){
        if(level == max_depth){
            return triangle[level][index];
        }
        
        if(currentMax[level][index] != null){
            return currentMax[level][index];
        }
        
        int left = sumBelow(level + 1, index);
        int right = sumBelow(level + 1, index + 1);
        
        if(left < right){
            currentMax[level][index] = triangle[level][index] + right;
        }else{
            currentMax[level][index] = triangle[level][index] + left;
        }
        
        return currentMax[level][index];

    }
}