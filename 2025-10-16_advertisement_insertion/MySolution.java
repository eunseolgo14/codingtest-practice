import java.util.*;

class Solution {
   
    public String solution(String play_time, String adv_time, String[] logs) {
        
        //전체 영상 길이와 광고 길이 비교.
        int totalVideo = getSecFromString(play_time);
        int totalAd = getSecFromString(adv_time);
        
        if(totalVideo == totalAd){
            return "00:00:00";
        }
        
        long[] totalView = new long[totalVideo + 1];
        
        //1. 들어가고 나온 기록만 저장.
        for(String log: logs){
            int[] sec = getStartAndEnd(log);
            totalView[sec[0]]  += 1;
            totalView[sec[1]]  -= 1;
        }
        
        //2. 구간 시청자 왼쪽 -> 오른쪽으로 밀어서 넣기.
        for(int i = 1; i< totalView.length; i++){
            totalView[i] += totalView[i-1];
        }
        
        //3. 구간 시청자에 의한 총 누적 시청 초 수 밀어서 넣기.
        for(int i = 1; i< totalView.length; i++){
            totalView[i] += totalView[i-1];
        }
        
        //4. 광고 시작 지점을 0에 두고 1초씩 뒤로 밀어가며 
        //시작시점 별 순수 구간 누적 시청 시간이 가장 크게 나오는 구간 찾기.
        long maxView = totalView[totalAd-1];
        int startTime = 0;
        for(int i = totalAd; i< totalVideo; i++){
            long curView = totalView[i] - totalView[i - totalAd];
            if(maxView < curView){
                maxView = curView;
                startTime = i - totalAd + 1;
            }
        }
                
        String answer = getStringFromSec(startTime);
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
    
     private String getStringFromSec(int number){

        int time = number / 3600;
        int min =  (number % 3600) / 60;
        int sec = ((number % 3600) % 60);
        
        String result = String.format("%02d:%02d:%02d", time, min, sec);
        return result;
    }
}