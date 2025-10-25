import java.util.*;

class Solution {
    
    Set<String> gemSet;
        
    public int[] solution(String[] gems) {
        
        //고유 보석 목록 산출.
        gemSet = new HashSet<>();
        for(String name: gems){
            gemSet.add(name);
        }
        
        int left = 1;
        int right = gems.length;
        
        while (left <= right){
            
            if(isHaveAllGems(left, right, gems)){
                right -= 1;
            }else{
                left += 1;
            }
        }
        
        int[] answer = {left, right};
        return answer;
    }
    
    private boolean isHaveAllGems(int start, int end, String[] gems){
        
        int hitCount = 0;
        Iterator<String> content = gemSet.iterator();
        
        while(content.hasNext()){
            
            String thisGem = content.next();
            
            for(String gem: gems){
                if(gem.equals(thisGem)){
                    hitCount++;
                }
            }
        }
        
        return hitCount == gemSet.size();
    }
}