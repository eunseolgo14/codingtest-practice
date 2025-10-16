import java.util.*;

class Watching{
    
    int start;
    int end;
    int index;
    List<Watching> overlapped = new ArrayList<>();;
    
    Watching(int start, int end, int index){
        this.start = start;
        this.end = end;
        this.index = index;
    }
    
    public void addOverlapped(Watching nextWatch){
        if(this.start < nextWatch.start && nextWatch.start < this.end){
            
            if(this.overlapped == null){
                this.overlapped = new ArrayList<>();
            }
            this.overlapped.add(nextWatch);
        }
    }
}

class Solution {
    
    
    //13:07 - 13:19 로직 설계
    //광고 삽입 될 동영상 전체 구간 2시간 3분 55초
    //7200 + 180 + 88 = 7435초
    
    //광고 재생시간 adv_time
    
    //누적 재생 시간 구하는 공식 => 각 logs로 기준으로 나와 재생 시간이 겹지는 다른 logs를 저장함.
    //즉 다른 노드의 시작시간이 내 시작 이후, 내 종료 이전인 녀석들을 저장.
    
    //겹침 정보를 리스트로 add후 add의 length가 가장 긴 구간의 길이 산출.
    //광고 삽입 가능 길이면 그 겹침 시작 시간이 가장 빠른 구간이 광고 삽입 시작 구간.
    
    //실제 시청 구간 => 시작, 종료, 인덱스
    
    
    public String solution(String play_time, String adv_time, String[] logs) {
        
        //전체 영상 길이와 광고 길이 비교.
        int totalVideo = getSecFromString(play_time);
        int totalAd = getSecFromString(adv_time);
        
        if(totalVideo == totalAd){
            return "00:00:00";
        }
        
        List<Watching> watchList = new ArrayList<>();
        
        //시청 구간 watch데이터로 가공.
       for(int i = 0; i < logs.length; i++){
           int[] timeData = getStartAndEnd(logs[i]);
           Watching data = new Watching(timeData[0],timeData[1],i);
           watchList.add(data);
       }
        //시작 순으로 정렬.
        watchList.sort((a,b) -> {
            return a.start - b.start;
        });
        
        //겹침 정보 삽입.
        for(int i = 0; i < watchList.size(); i++){
            for(int j = 0; j < watchList.size() -1; j++){
                watchList.get(i).addOverlapped(watchList.get(j));
            }
        }
        
        //한번 더 정렬
        watchList.sort((a,b) -> {
            if(a.overlapped.size() == b.overlapped.size()){
                return a.start - b.start;
            }
            return b.overlapped.size() - a.overlapped.size();
        });
        
        System.out.print("time: " + watchList.get(0).start);
        
        String answer = "";
        return answer;
    }
    
    //클래스 및 유틸 메서드 제작.
    private int[] getStartAndEnd(String log){
   
        String[] parts = log.split("-");
        
        int start = getSecFromString(parts[0]);
        int end = getSecFromString(parts[1]);
        
        int[] result = {start, end};
        
        return result;
    }
    
     private int getSecFromString(String log){

        String[] parts = log.split(":");

        int time = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);
        int sec = Integer.parseInt(parts[2]);
        
        int result = (time * 3600) + (min * 60) + sec;
        return result;
    }
    
}