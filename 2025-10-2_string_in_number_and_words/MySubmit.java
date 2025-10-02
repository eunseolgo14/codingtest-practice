import java.util.HashMap;
import java.lang.Math;

class Solution {
    
    public HashMap<String, String> numbMap = new HashMap<>();
    
    public int solution(String s) {
        int answer = 0;
        setNumbMap(numbMap);
        answer = convertIntoNum(s);
        return answer;
    }
    
    private void setNumbMap(HashMap<String, String> map){
        map.put("zero", "0");
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        map.put("six", "6");
        map.put("seven", "7");
        map.put("eight", "8");
        map.put("nine", "9");
    }
    
    private int convertIntoNum(String text){
        int value = 0;
        
        for(String numt :numbMap.keySet()){
             if(text.contains(numt)){
                text = text.replaceAll(numt, numbMap.get(numt));
            }
        }
        
        value = Integer.parseInt(text);
        return value;
    }
}