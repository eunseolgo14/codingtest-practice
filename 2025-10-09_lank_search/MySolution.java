import java.util.*;

class Solution {
    Map<String, List<Integer>> comboMap;
    
    public int[] solution(String[] info, String[] query) {
        int[] answer = {};
        
        //1. 모든 조합 생성
        //2. 각 조합(key)에 점수를 넣기 
        comboMap = new HashMap<>();
        
        for(String data: info){
            String[] parts = data.split(" ");
            makeAllCombinations(parts);
        }
        
        //3. 빠른 검색을 위한 점수 리스트 내림차순 정렬.
        setScoresDesc();
        
        //4. 베열 준비 및 검색.
        int[] result = new int[query.length];
        int index = 0;
        for(String data: query){
            String[] parts = data.split(" ");
            //java[0] and[1] backend[2] and[3] junior[4] and[5] pizza[6] 100[7].
            String key = parts[0] + " " + parts[2] + " " + parts[4] + " " + parts[6];
            int score = Integer.parseInt(parts[7]);
            result[index] = getMatchCount(key, score);
            index++;
        }
        
        answer = result;
        //printMap();
        return answer;

    }
    
    //모든 조합 map 생성 유틸.
    private void makeAllCombinations(String[] parts){

        //"java[0] backend[1] junior[2] pizza[3] 150[4]"
        String[] langArr = {parts[0], "-"}; 
        String[] expertiesArr = {parts[1], "-"}; 
        String[] levelArr = {parts[2], "-"}; 
        String[] foodArr = {parts[3], "-"}; 
        
        String combi = "";
        List<Integer> list;
        
        for(int i = 0; i < langArr.length; i++){
            for(int j = 0; j < expertiesArr.length; j++){
                for(int k = 0; k < levelArr.length; k++){
                    for(int l = 0; l < foodArr.length; l++){
                        combi = langArr[i] + " " + expertiesArr[j] + " " + levelArr[k] + " " + foodArr[l];
                        if(comboMap.containsKey(combi)){
                            comboMap.get(combi).add(Integer.parseInt(parts[4]));
                        }else{
                            list = new ArrayList<>();
                            list.add(Integer.parseInt(parts[4]));
                            comboMap.put(combi, list);
                        }
                    }
                }
            }
        }
    }
    
    private void printMap(){
        for(String key: comboMap.keySet()){
            
            String scores = "";
            
            for(int score: comboMap.get(key)){
                scores += score + ",";
            }
            
            System.out.print(key +": [" + scores +"]\n");
        }
    }
    
    private void setScoresDesc(){
        for(String key: comboMap.keySet()){
            Collections.sort(comboMap.get(key));
        }
    }
    
    private int getMatchCount(String key, int score) {

        List<Integer> list = comboMap.get(key);
        if(list == null){
            return 0;
        }else{
            int start = 0;
            int end = list.size();
            
            //시작위치 값이 종료 위치값보다 작을때까지.
            while(start < end){
                //임의 중앙 지점 산출.
                int mid = (start + end) / 2;
                
                //중앙 지점이 타겟보다 작음 => start - mid지점 버림, 구간 재산출.
                if(list.get(mid) < score){
                    start = (mid + 1);
                }else{
                    //중앙지점이 타겟보다 큼 => start - mid까지를 다음 구간으로 지정.
                    end = mid;
                }
            }
            
            return list.size() - start; 
        }
        
        
    }
 
}