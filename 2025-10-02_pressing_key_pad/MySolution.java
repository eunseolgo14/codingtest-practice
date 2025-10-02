import java.lang.Math;

class Solution {
    public int keyButton[][] = {
        {3,1},//0
        {0,0},//1
        {0,1},//2
        {0,2},//3
        {1,0},//4
        {1,1},//5
        {1,2},//6
        {2,0},//7
        {2,1},//8
        {2,2}//9
    };
    
    public int[] leftThumbPos = {3,0};
    public int[] rightThumbPos = {3,2};
    String hand ="";
    
    public String solution(int[] numbers, String hand) {
        String answer = "";
        this.hand = hand;
        for(int i = 0; i < numbers.length; i++){
            answer += pressKey(numbers[i]);
        }
        return answer;
    }
    
    private String pressKey(int num){
        if(num == 1 || num == 4 || num == 7){
            leftThumbPos = keyButton[num];
            return "L";
        }else if(num == 3 || num == 6 || num == 9){
            rightThumbPos = keyButton[num];
            return "R";
        }else{
            int[] targetPos = keyButton[num];
            int leftOff = (int)Math.abs(targetPos[0]-leftThumbPos[0]) + (int)Math.abs(targetPos[1]-leftThumbPos[1]);
            int rightOff = (int)Math.abs(targetPos[0]-rightThumbPos[0]) + (int)Math.abs(targetPos[1]-rightThumbPos[1]);
            
            if(leftOff < rightOff){
                leftThumbPos = keyButton[num];
                return "L";
            }else if(rightOff < leftOff){
                rightThumbPos = keyButton[num];
                return "R";
            }else{
                if(this.hand.equals("right")){
                    rightThumbPos = keyButton[num];
                    return "R";
                }else{
                    leftThumbPos = keyButton[num];
                    return "L";
                }
            }
        }
    }
}