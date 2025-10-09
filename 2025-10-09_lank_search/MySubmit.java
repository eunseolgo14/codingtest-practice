import java.util.*;
import java.util.stream.*;
class Info{
    String lang;
    String experties;
    String level;
    String food;
    int score;
    
    Info(String lang, String experties, String level, String food, int score){
        this.lang = lang;
        this.experties = experties;
        this.level = level;
        this.food = food;
        this.score = score;
    }
    
}
class Solution {
    List<Info> infoList;
    List<Info> queryList;
    
    public int[] solution(String[] info, String[] query){
        
        //info를 가공하여 리스트에 정리
        infoList = processInfos(info);
        
        //query를 가공하여 리스트에 정리
        queryList = processQueries(query);
        
        int[] result = new int[queryList.size()];
        
        for(int i = 0; i < queryList.size(); i++){
            result[i] = filterQueryToCount(queryList.get(i));
        }

        int[] answer = result;
        return answer;
    }
    
    private List<Info> processInfos(String[] info){
        
        List<Info> list = new ArrayList<>();
        
        for(int i = 0; i < info.length; i++){
            
            //"java backend junior pizza 150"
            String[] infos = info[i].split(" ");

            Info data = new Info(infos[0], infos[1], infos[2], 
                                 infos[3], Integer.parseInt(infos[4]));
            list.add(data);
        }
        
        return list;
    }
    private List<Info> processQueries(String[] query){
        
        List<Info> list = new ArrayList<>();
        
        for(int i = 0; i < query.length; i++){
            
            //"java0 and1 backend2 and3 junior4 and5 pizza6 1007"
            String[] infos = query[i].split(" ");

            Info data = new Info(infos[0], infos[2], infos[4], 
                                 infos[6], 0);
            
            try {
               data.score = Integer.parseInt(infos[7]);
            } catch(NumberFormatException ex) {
               data.score = 0;
            }
            
            list.add(data);
        }
        
        return list;
    }
    
    private int filterQueryToCount(Info query){
        //info: 1 이상 50,000 이하, query: => 1 이상 100,000 이하
        //필터링을 위한 for문 사용시 성능 저하 심각 스트림 filter등을 이용할 것
        
        // List<Integer> evenNumbers = numbers.stream()
        //                            .filter(n -> n % 2 == 0)
        //                            .collect(Collectors.toList());
        
        int queryCount = infoList.stream()
            .filter(info -> checkCondition(info, query))
            .collect(Collectors.toList()).size();
        return queryCount;
    }
    
    private boolean checkCondition(Info info, Info query){

        if(checkVal(info.lang, query.lang)
           && checkVal(info.experties, query.experties)
           && checkVal(info.level, query.level) 
           && checkVal(info.food, query.food)
           && checkScore(info.score, query.score)){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    private boolean checkVal(String infoVal, String queryVal){
        if(queryVal.equals("-")){
            return true;
        }else{
            return infoVal.equals(queryVal);
        }
    }
   
    private boolean checkScore(int infoScore, int queryScore){
        return queryScore <= infoScore;
    }
}