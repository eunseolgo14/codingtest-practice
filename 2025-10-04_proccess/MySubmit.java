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
        
        //문서 설정.
        for(int i = 0; i < priorities.length; i++){
            Document doc = new Document(priorities[i]);
            
            if(i == location){
                doc.isMarked = true;
            }
            docQueue.add(doc);
        }
        
        boolean hasBigger = false;
        while(!docQueue.isEmpty()){
            
            Document doc = docQueue.poll();
            
            foreach(Document d: docQueue){
                if(d.priority > doc.priority){
                    hasBigger = true;
                    break;
                }
            }
            
            if(hasBigger){
                docQueue.add(doc);
            }else{
                answer++;
                if(doc.isMarked){
                    break;
                }
            }
        }
        
        return answer;
    }
}

//priorities를 queue에 넣는다.
//isEmpty일까지 앞 수를 확인한다.
//하나씩 꺼내며 index를 ++한다.
//인덱스가 location이 된 시점에서 해당 수를 마킹한다.
//해당 수가 밖으로 나가면 안에 들어 있던 횟수 만큼을 확인.

//내가 마킹할 수를 다시 큐 맨 뒤에 넣으면. 
//내 수가 어디에 있는지를 확인할 수 있어야함 => 이 문서가 내 문서야 마킹 플래그 필요.
