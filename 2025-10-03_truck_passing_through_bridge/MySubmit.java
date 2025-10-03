import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        //트럭 대기열 제작.
        Queue<Integer> trucksInLine = new LinkedList<Integer>();
        
        //트럭 대기열 초기화.
        for(int i = 0; i < truck_weights.length; i++){
            trucksInLine.add(Integer.valueOf(truck_weights[i]));
        }
        
        //예상 종료시간 도출.
        int answer = getEndTime(bridge_length, weight, trucksInLine);
        return answer;
        
      
    }
    
    private int getEndTime(int length, int maxWeight, Queue<Integer> line){
        
        //현재 다리위 트럭 하중의 총량 시작값.
        int currentWeight = 0;
        
        //마지막 이동 종료시간 시작값(1초부터 시작).
        int expectedEnd = 1;
        
        while(!line.isEmpty()){
                        
            //이번 트럭 추출 => element는 dequeue는 아닌가 peek같은건가 진짜 "빠지지는" 않은가봐.
            //무한루프 도는 것 같네.
            int currentTruck = line.poll();
            
            //다리 위 올라갈 수 있는지 확인.
            if(currentWeight + currentTruck <= maxWeight){
                
                //다리 위 첫타자면 기본 시간 확립.
                if(currentWeight == 0){
                    expectedEnd += length;
                }else{
                    //첫타 아니면 1초라는 딜레이만 추가.
                    expectedEnd += 1;
                }
                
                //다리 위 탑승 처리.
                currentWeight += currentTruck;
                
            }else{
                //다리 위 모든 차 빠질때까지 대기 후 진입.
                currentWeight = currentTruck;
                expectedEnd += length;
            }
        }
        
        return expectedEnd;
         
    }
}