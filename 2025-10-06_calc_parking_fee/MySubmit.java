import java.util.*;

class Solution {

    int basicMin;
    int basicFee;
    int feeUnit;
    int feeMin;
    
    public int[] solution(int[] fees, String[] records) {
        basicMin = fees[0];
        basicFee = fees[1];
        feeUnit = fees[2];
        feeMin = fees[3];
        
        //IN일때 저장할 입차 기록 저장 맵(차번호, 입차시간 in 분).
        Map<Integer, Integer> inRecords = new HashMap<>();
        //OUT을때 저장할 총 누적 시간 맵(차번호, 입차시간 in 분).
        Map<Integer, Integer> totalRecords = new HashMap<>();
        
        //전체 기록을 돌며 저장.
        int[] processed;
        
        for(int i = 0; i < records.length; i++){
            
            //[0]:차번호, [1]:입차시간, [2]:입출차 유형.
            processed = processRecord(records[i]);
            
            //IN의 경우 => inRecords에
            if(processed[2] == 0){
                inRecords.put(processed[0], processed[1]);
            }else{
                //OUT의 경우 => TimeDiff산출 후 totalRecords에 put.
                int timeDiff = processed[1] - inRecords.get(processed[0]);
                
                int prevTime = totalRecords.getOrDefault(processed[0], 0);
                
                //누적 저장.
                totalRecords.put(processed[0], prevTime + timeDiff); 
                
                //누적 산출 완료한 입차 기록은 소멸.
                inRecords.remove(processed[0]);
            }
        }
        
        //짝이 맞지 않는, 즉 IN은 있으나 OUT이 명시적으로 안 남은 입차 기록 마감.
        for(int carNum: inRecords.keySet()){
           
            //금일 마감 시간.
            int outTIme = (23 * 60) + 59;
            
            int timeDiff = outTIme - inRecords.get(carNum);
            int prevTime = totalRecords.getOrDefault(carNum, 0);

            totalRecords.put(carNum, prevTime + timeDiff);
            //[틀린부분✏️]ConcurrentModificationException => for문으로inRecords를 순회하는 중에.
            //내부 구조 삭제시 에러 던짐 => 런타임 에러로 실패함.
            //inRecords.remove(carNum); -> 이걸 하면 에러.
        }
        //여기서 inRecords.clean();하면 오류 없이 한번에 정리.
        
        //totalRecords 차번호(키) 오름차순.
        //[틀린부분✏️] => 차 번호 키는 현재 int형임, get을 int키가 아닌 string키로 하고있음.
        List<String> keySet = new ArrayList(totalRecords.keySet());
        Collections.sort(keySet);
        
        //주차요금 산출 후 제출 배열에 저장.
        int[] answer = new int[keySet.size()];

        for(int i = 0; i < keySet.size(); i++){
            int totalDur = totalRecords.get(keySet.get(i));
            int fee = calcFee(totalDur);
            answer[i] = fee;
        }
        
        return answer;
    }
    
    //1.record를 받아 차번호, 입차시간, 유형을 리턴.
    private int[] processRecord(String record){
        
        int[] result = new int[3];
        String[] parts = record.split(" ");
        
        //차번호 세팅.
        result[0] = Integer.parseInt(parts[1]);
        
        //시간값은 :을 기준으로 한번더 문자열 가공.
        int hour = Integer.parseInt(parts[0].split(":")[0]);
        int min = Integer.parseInt(parts[0].split(":")[1]);
        
        //입차 시간 세팅.
        result[1] = (hour * 60) + min;
        
        //입출차 유형 세팅.
        result[2] = (parts[2].equals("IN")) ? 0 : 1;
        
        return result;
    }
    //2. 머문 시간(분) 입력 후 산출된 주차요금 리턴.
    private int calcFee(int durMin){

        //기본시간 이하 체류.
        if(durMin <= basicMin){
            return basicFee;
        }else{
            return basicFee + (int)(Math.ceil((durMin-basicMin)/(float)feeUnit)) * feeMin;
        }
    }
    
}