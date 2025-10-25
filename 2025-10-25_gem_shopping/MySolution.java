import java.util.*;

class Solution {
    
    public int[] solution(String[] gems) {
        
        //중복없는 고유 보석 목록.
        Set<String> gemSet = new HashSet<>();
        
        //Set초기화.
        for(String gem : gems){
            gemSet.add(gem);
        }
        
        //구간내 보석 계산용.
        Map<String, Integer> rangeMap = new HashMap<>();
        
        //구간 내 이동용 좌우 변수.
        int rMove = 0; 
        int lMove = 0;
        
        //최종 제출용 오염없는 유효 값.
        int finalR = 0;
        int finalL = 0;
        
        //맵에 초기값 넣기.
        rangeMap.put(gems[0], 1);
        
        //갱신 기준이 되는 최소 거리.
        int minLength = Integer.MAX_VALUE;
        
        while(lMove <= rMove && rMove < gems.length){
            
            //범위 안 보석 채워짐.
            if(gemSet.size() == rangeMap.size()){
                
                //갱신 여부 확인.
                if(rMove - lMove < minLength){
                    
                    finalR = rMove;
                    finalL = lMove;
                    
                    minLength = rMove - lMove;
                }
                
                //왼쪽 깎아내기.
                String leftGem = gems[lMove];
                int leftCount = rangeMap.get(leftGem);
                
                //하나 줄였을 시 0, 즉 제거되어야 함.
                if(leftCount <= 1){
                    rangeMap.remove(leftGem);
                }else{
                    //2이상, 수만 1차감.
                    rangeMap.put(leftGem, leftCount - 1);
                }
                
                lMove++;
                
            }else{
                
                //계속 오른쪽 확장.
                rMove++;
                
                //유효 범위 안이면 맵에 추가.
                if(rMove < gems.length){
                    
                    String gem = gems[rMove];
                    int gemCount = rangeMap.getOrDefault(gem, 0) + 1;
                    
                    rangeMap.put(gem, gemCount);
                }
            }
        }
        
        int[] answer = {(finalL + 1), (finalR + 1)};
        return answer;
    }
}