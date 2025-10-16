import java.util.*;

class Task{
    int requestedAt;
    int takeTime;
    int index;
    Task(int req, int take, int idx){
        this.requestedAt = req;
        this.takeTime = take;
        this.index = idx;
    }
}
class Solution {
    //19:55 - 19:59 문제 이해.
    //요청시간, 소요시간, 작업 번호 => 큐에 들어가 있음.
    //작업 안 함, 대기큐 안 빔 => 큐 poll, 우선순위 높은 순으로.
    //우선순위 순서 => 소요시간 적은순, 작업 요청시간 빠른순, 작업 번호 작은 순.
    //마치는 시점에 새 요청 in => 대기큐 저장.

    //19:59 - 20:01 로직 설계.
    //1.jobs => Task객체로 변환.
    //2. Priority Queue 제작.
    //3. task객체 전부 add.
    //4. 정렬, comparator정의(지문에서 정의한대로).
    //5. queue 비어있을때까지 poll하며 turnaround시간 누적.
    //6. 결과 리턴.
    
    public int solution(int[][] jobs) {
        
        //20:01 - 20:27 코드 작성.
        
        //0. 전체 작업 목록.
        List<Task> taskList = new ArrayList<>();
        
        //1. 마련된 목록에 jobs add.
        for(int i = 0; i < jobs.length; i++){
            Task newTask = new Task(jobs[i][0], jobs[i][1], i);
            taskList.add(newTask);
        }
        
        //2. 목록 입장 시간 순 정렬.
        taskList.sort((a,b) -> {return a.requestedAt - b.requestedAt;});
        
        //3. 프라이오리티 큐 생성 및 컴퍼레이터 생성. 
        PriorityQueue<Task> waitingQueue = new PriorityQueue<>((a, b) -> {
            if(a.takeTime == b.takeTime){
                if(a.requestedAt == b.requestedAt){
                    return a.index - b.index;
                }else{
                    return a.requestedAt - b.requestedAt;
                }
            }else{
                return a.takeTime - b.takeTime;
            }
        });
        
        //4. 큐 준비.        
        int totaltime = 0;
        int index = 0;
        int doneCount = 0;
        int turnAroundTime = 0;
        
        //5. 작업 시작.
        while(doneCount < jobs.length){
            
            while (index < taskList.size()) {
                Task cur = taskList.get(index);
                if (cur.requestedAt <= totaltime) {
                    waitingQueue.add(cur);
                    index++;              
                } else {
                    break;                
                }
            }
            
             if (waitingQueue.isEmpty()) {
                if (index < taskList.size()) {
                    totaltime = taskList.get(index).requestedAt; 
                }
                continue; 
            }
            
            Task thisTask = waitingQueue.poll();

            totaltime += thisTask.takeTime;
            turnAroundTime += (totaltime - thisTask.requestedAt);
            
            doneCount++;
        }
        int answer = turnAroundTime / jobs.length;
        return answer;
    }
}

