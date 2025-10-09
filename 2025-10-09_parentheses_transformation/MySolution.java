import java.util.*;

class Solution {
    public String solution(String p) {
        
        String result = "";
        String answer = process(p);
        return answer;
    }
    
    private String process(String text){
        
        String result = "";
        
        if(text.equals("")){
            return "";
        }
        
        //balced부 추출해내기.
        String[] parts = findBalanced(text);
        
        String first = parts[0];
        String later = parts[1];

        if(isCorrect(first)){
            result += first;
            result += process(later);
            
        }else{
            result += ("(" + process(later) + ")");
            first = first.substring(1, first.length()-1);
            result += reverse(first);

        }
        return result;
    }
    
    private String[] findBalanced(String text){
        String[] parts = new String[]{"", ""};
        
        String firstBalance = "";
        int openCount = 0;
        int closeCount = 0;
        
        for(int i = 0; i < text.length(); i++){
            
            firstBalance += text.charAt(i);
            
            if(text.charAt(i) == '('){
                openCount++;
            }else{
                closeCount++;
            }
            
            if(openCount == closeCount){
                break;
            }
        }
        
        parts[0] = firstBalance;
        parts[1] = text.substring(firstBalance.length());

        return parts;
    }
    
    private boolean isCorrect(String text){
        
        //balanced가 correct가 맞는지 획인용 유틸 함수.
        if(!text.startsWith("(")){
            return false;
        }
        //예: (()))((
        int result = 0;
        
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '('){
                result++;
            }
            else{
                result--;
            }
            
            if(result < 0){
                //() 규칙이 깨짐, 여는 괄호 없이 닫는 괄호가 남음 => 미완.
                return false;
            }
        }
        return true;
    }
    
    private String reverse(String text){
        String reversed = "";
        
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '('){
                reversed += ')';
            }else{
                reversed += '(';
            }
        }
        return reversed;
    }
}

