import java.util.*;

class Document{
    public int priority;
    public boolean isMarked;
    Document(int priority){
        this.priority = priority;
    }
}

class Solution {
    public int solution(int[] priorities, int location) {
        int answer = 0;
        Queue<Document> docQueue = new LinkedList<>();
        PriorityQueue<Integer> descQueue = new PriorityQueue<>(Collections.reverseOrder());

        for(int i = 0; i < priorities.length; i++){
            Document doc = new Document(priorities[i]);
            
            if(i == location){
                doc.isMarked = true;
            }
            
            docQueue.add(doc);
            descQueue.add(priorities[i]);
        }
        
        while(!docQueue.isEmpty()){
            Document thisDoc = docQueue.poll();
            
            if(descQueue.peek() > thisDoc.priority){
                docQueue.add(thisDoc);
            }else{
                answer++;
                descQueue.poll();
                if(thisDoc.isMarked){
                    break;
                }
            }
        }
        return answer;
    }
}