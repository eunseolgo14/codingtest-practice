import java.util.*;
class Solution {
    public int[] solution(int n, int[] info) {
        
        //어피치, 라이언 점수 배열 마련.
        int[] apeach = info;
        int[] lion = new int[11];
        setScore(0, n, lion, apeach);
        
        String buff = "";
        int index = 0;
        for(int i: lion){
            buff += ", " + index + ": " + i;
            index++;
        }
        print("lion: " + buff);
        int[] answer = lion;
        
        return answer;
    }
    
    private void setScore(int round, int arrowCount, int[] lionArr, int[] apeachArr){
        
        if(round == 11){
            //최종 점수까지 세팅 완료 => 점수 계산.
            return;
        }
       
        int apeachScore = apeachArr[round];
        
        //이번 판 어피치에게 이기는 선택.
        if(apeachScore < arrowCount){
            lionArr[round] = (apeachScore + 1);
            int winArrowCount = arrowCount - (apeachScore + 1);
            setScore(round + 1, winArrowCount, lionArr, apeachArr);
            lionArr[round] = 0;
        }
        
        //이번 판 어피치에게 지는 선택.
        setScore(round + 1, arrowCount, lionArr, apeachArr);
    }
    
    private void print(String mess){
         System.out.print(mess + "\n");
    }
}