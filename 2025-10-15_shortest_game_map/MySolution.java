import java.util.*;

class Block{
    int col;
    int row;
    int time;
    int value;
    
    Block(int col, int row, int time, int value){
        this.col = col;
        this.row = row;
        this.time = time;
        this.value = value;
    }
}

class Solution {
    
    int mapSizeN;
    int mapSizeM;
        
    public int solution(int[][] maps) {
        
        //맵 세로 길이.
        mapSizeN = maps.length;
        
        //맵 가로 길이.
        mapSizeM = maps[0].length;
        
        //큐 초기화, 방문 기록 초기화.
        Queue<Block> steps = new LinkedList<>();
        boolean visited[][] = new boolean[mapSizeN][mapSizeM];
        
        //상하좌우 이동 정의.
        int[][] directions = {{1,0}, {-1,0}, {0,-1}, {0,1}};
        
        //0,0부터 탐색 시작.
        steps.add(new Block(0, 0, 0, maps[0][0]));
        visited[0][0] = true;

        while(!steps.isEmpty()){

            Block curPos = steps.poll();
            
            if(isArrived(curPos)){
                return curPos.time + 1;
            }
            
            for(int[] next : directions){
                
                int nCol = curPos.col + next[0];
                int nRow = curPos.row + next[1];
                int nTime = curPos.time + 1;
                
                if(isInRange(nCol, nRow)){
                    if(!visited[nCol][nRow] && maps[nCol][nRow] == 1){
                        visited[nCol][nRow] = true;
                        Block nextStep = new Block(nCol, nRow, nTime, maps[nCol][nRow]);
                        steps.add(nextStep);
                    }
                    
                }
                
            }
            
        }
        
        //탐색 종료.
        return -1;
        
    }
    
    //보드 내 위치 여부 확인 유틸.
    private boolean isInRange(int col, int row){
        return (0 <= col && col < mapSizeN) && (0 <= row && row < mapSizeM);
    }
    
    //도착 여부 확인 유틸.
    private boolean isArrived(Block pos){
        return pos.col == mapSizeN -1 && pos.row == mapSizeM -1;
    }
    
}