import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        
        Queue<Integer> bridge = new LinkedList<>();
        
        int timeCount = 0;
        int index = 0;
        int currWeight = 0;
        
        //올려보낼 남은 트럭 있거나 다리 위 지나고 있는 차가 있다면 반복.
        //아니라면 == 남은 트럭도 없으며 다리위도 비워져있다 == 이동 완료.
        while(index < truck_weights.length || currWeight > 0){
            
            timeCount++;
            
            //넣기전 반드시 먼저 탈출 처리.
            if(bridge.size() == bridge_length){
                currWeight -= bridge.poll();
            }
            
            //들어갈 수 있는지 확인(weight와 동일한 선까지 가능).
            //트럭 인덱스 한번 더 체크.
            if(index < truck_weights.length &&(currWeight + truck_weights[index]) <= weight){
                bridge.add(truck_weights[index]);
                currWeight += truck_weights[index];
                index++;
            }else{
                bridge.add(0);
            }
        }
        
        int answer = timeCount;
        return answer;
    }
}