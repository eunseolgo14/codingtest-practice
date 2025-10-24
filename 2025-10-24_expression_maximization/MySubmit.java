import java.util.*;
class Solution {
    public long solution(String expression) {
        String[][] combos = {{"+", "-", "*"}, {"+", "*", "-"}, {"-", "+", "*"}, {"-", "*", "+"}, {"*", "+", "-"}, {"*", "-", "+"}};
        Long[] values = new Long[combos.length];
        
        StringTokenizer tokens = new StringTokenizer(expression, "+-*", true);
        List<String> splitedText = new ArrayList<>();
        
        while(tokens.hasMoreTokens()){
            splitedText.add(tokens.nextToken());
        }
        //"100-200*300-500+20"
        for(int i = 0; i < combos.length; i++){
            
            List<String> thisList = new ArrayList<>(splitedText);
            
            for(String operator: combos[i]){
                for(int j = 0; j < thisList.size(); j++){
                    if(splitedText.get(j).equals(operator)){
                        Long result = calc(thisList.get(j - 1), 
                                           thisList.get(j + 1), thisList.get(j));
                        thisList.remove(j - 1);      //"-200*300-500+20", "-200*-500+20"
                        thisList.remove(j - 1);      //"200*300-500+20", "-200*500+20"
                        thisList.set(j - 1, ""+result); //"-100*300-500+20", "-200*-200+20"
                        j -= 2;
                    }
                }
            }
            values[i] = Math.abs(Long.parseLong(thisList.get(0)));

        }
        Arrays.sort(values, Collections.reverseOrder());
        long answer = values[0];
        return answer;
    }
    
    private Long calc(String right, String left, String operator){
        switch(operator){
            case "+":
                return Long.parseLong(right) + Long.parseLong(left);
            case "-":
                return Long.parseLong(right) - Long.parseLong(left);
            case "*":
                return Long.parseLong(right) * Long.parseLong(left);
            default:
                return Long.valueOf(0);
        }
    }
}