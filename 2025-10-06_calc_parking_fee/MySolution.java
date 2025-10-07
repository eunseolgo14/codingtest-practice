import java.util.*;

class Solution {
    int allowance;
    int basicFee;
    int timeUnit;
    int feeUnit;
    
    public int[] solution(int[] fees, String[] records) {
        allowance = fees[0];
        basicFee = fees[1];
        timeUnit = fees[2];
        feeUnit = fees[3];
        
        Map<Integer, Integer> inRecords = new HashMap<>();
        Map<Integer, Integer> totalRecords = new HashMap<>();
        
        for(int i = 0; i < records.length; i++){
            
            int[] processed = processRecord(records[i]);
            
            if(processed[2] == 0){
                inRecords.put(processed[0], processed[1]);
            }else{
                int time = processed[1] - inRecords.get(processed[0]);
                int prev = totalRecords.getOrDefault(processed[0], 0);
                totalRecords.put(processed[0], prev+time);
                inRecords.remove(processed[0]);
            }
            
        }
        
        for(int key :inRecords.keySet()){
            int outTime = (23 * 60) + 59;
            int time = outTime - inRecords.get(key);
            int prev = totalRecords.getOrDefault(key, 0);
            totalRecords.put(key, prev+time);
        }
        inRecords.clear();
        
        List<Integer> keys = new ArrayList(totalRecords.keySet());
        Collections.sort(keys);
        
        int[] answer = new int[keys.size()];
        
        for(int i = 0; i < keys.size(); i++){
            answer[i] = calcFee(totalRecords.get(keys.get(i)));
        }
        
        return answer;
    }
    
    private int[] processRecord(String record){
        int[] result = new int[3];
        String[] parts = record.split(" ");
        
        result[0] = Integer.parseInt(parts[1]);
        result[2] = (parts[2].equals("IN")) ? 0 : 1;
        
        String[] timeparts = parts[0].split(":");
        int hour = Integer.parseInt(timeparts[0]);
        int min = Integer.parseInt(timeparts[1]);
        result[1] = (hour * 60) + min;
        
        return result;
    }
    
    private int calcFee(int time){
        if(time <= allowance){
            return basicFee;
        }
        return basicFee + (int)(Math.ceil((time - allowance) / (float)timeUnit)) * feeUnit;
    }
}