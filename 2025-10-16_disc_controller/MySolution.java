import java.util.*;
class Solution {
    public int solution(int[][] jobs) {
        
        //1. 도착시간 오름차순 정렬.
        Arrays.sort(jobs, (a, b) -> { return a[0] - b[0];});
        
        //2. 소요 시간 오름차순 우선순위 큐 마련.
        PriorityQueue<int[]> waitingQueue = new PriorityQueue<>((a, b) -> {
            return a[1] - b[1];
        });
        
        int index = 0;
        int currTime = 0;
        int turnAround = 0;
        
        int size = jobs.length;
        int doneCount = 0;
        
        while(doneCount < size){
            
            while(index < size && jobs[index][0] <= currTime){
                waitingQueue.add(jobs[index]);
                index++;
            }
            
            if(waitingQueue.isEmpty()){
                currTime = jobs[index][0];
                continue;
            }
            int[] job = waitingQueue.poll();
            currTime += job[1];
            turnAround += (currTime - job[0]);
            doneCount++;
        }
        
        int answer = turnAround / size;
        return answer;
    }
}