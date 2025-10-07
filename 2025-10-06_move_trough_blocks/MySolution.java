import java.util.*;

//0. 진행 단계 저장을 위한 클래스 제작.
class Step{
    int row;
    int col;
    int mode;
    int time;
    Step(int r, int c, int mode, int time){
        this.row = r;
        this.col = c;
        this.mode = mode;
        this.time = time;
    }
}

class Solution {
    public int solution(int[][] board) {
        
        //방문 기록 및 저장을 위한 변수 선언.
        int size = board.length;
        boolean[][][] visited = new boolean[size][size][2];
        Queue<Step> steps = new LinkedList<>();
        
        //초기 상태값 세팅.
        visited[0][0][0] = true;
        steps.add(new Step(0,0,0,0));
        
        //평행이동을 위한 상하좌우 이동값 저장.
        int[][] directions = {{1,0},{-1,0},{0,-1},{0,1}};
        
        while(!steps.isEmpty()){
            //큐에서 하나씩 아웃.
            Step thisStep = steps.poll();
            //이번 아웃 좌표가 도착 지점 도달 여부 확인.
            if(isArrived(thisStep.row, thisStep.col, thisStep.mode, size)){
                return thisStep.time;
            }
            
            //이번 좌표 기준 평행이동으로 도달 가능한 이웃 좌표 큐에 저장.
            for(int i = 0; i < directions.length; i++){
                //현위치 기준 상/하/좌/우 1보 이동 좌표값 산출.
                int nextRow = thisStep.row + directions[i][0];
                int nextCol = thisStep.col + directions[i][1];
                int nextMode = thisStep.mode;
                
                //이동 좌표값이 유효하고 미방문이면 방문 처리, 큐에 저장.
                if(isMovable(nextRow, nextCol, nextMode, board) && !visited[nextRow][nextCol][nextMode]){
                    visited[nextRow][nextCol][nextMode] = true;
                    steps.add(new Step(nextRow, nextCol, nextMode, thisStep.time+1));
                }
            }
            
            //현 위치 기준 모든 회전 가능 상태 산출.
            List<int[]> reaches = isRotatable(thisStep.row, thisStep.col, thisStep.mode, board);
            
            for(int i = 0; i < reaches.size(); i++){
                int[] reach = reaches.get(i);
                if(!visited[reach[0]][reach[1]][reach[2]]){
                    visited[reach[0]][reach[1]][reach[2]] = true;
                    steps.add(new Step(reach[0], reach[1], reach[2], thisStep.time+1));
                }
            }
            
        }
        
        int answer = 0;
        return answer;
    }
    
    //유틸함수 정의부.
    //1. 좌표가 보드 안 범위인지 확인.
    private boolean isInRange(int row, int col, int size){
        if((0 <= row && 0 <= col) && (row < size && col < size)){
            return true;
        }else{
            return false;
        }
    }
    
    //2. 좌표가 도착 지점인지 확인.
    private boolean isArrived(int row, int col, int mode, int size){
        //기준점 먼저 도착 도달 확인.
        if((row == size-1) && (col == size-1)){
            return true;
        }
        
        //기준점 아닌 다른 한쪽 도달 확인.
        if((mode == 0) && ((row == size-1) && (col+1 == size-1))){
            return true;
        }else if((mode == 1) && ((row+1 == size-1) && (col == size-1))){
            return true;
        }else{
            //모두 아닐 경우, 도착 전.
            return false;
        }
    }
   
    //3. 평행이동 가능 여부 확인.
    private boolean isMovable(int row, int col, int mode, int[][] board){
        int size = board.length;
        
        //타겟 지점이 범위 안인지 선확인.
        if(!isInRange(row, col, size)){
            return false;
        }
        
        //가로/세로 별 기준칸+비기준칸 모두 범위 안 + 접근 가능여부(1이 아닌 0인지) 확인.
        //이때 inRange체크 후 인덱스 접근 하지 않으면 OOR오류 발생 위험 있음.
        //순서 매우 중요~!
        
        //가로모드 => 내 col+1 범위 안 확인.
        if(mode == 0 && isInRange(row, col+1, size)){
            //안전하게 인덱스 접근.
            if(board[row][col] == 0 && board[row][col+1] == 0){
                return true;
            }
        }
        //세로모드 => 내 row+1 범위 안 확인.
        if(mode == 1 && isInRange(row+1, col, size)){
            //인덱스로 벽 여부 확인.
            if(board[row][col] == 0 && board[row+1][col] == 0){
                return true;
            }
        }
        
        return false;
    }
    
    //4. 회전 가능 여부 확인 후 회전 후 상태값 모두 리턴.
    private List<int[]> isRotatable(int row, int col, int mode, int[][] board){
       
        List<int[]> reaches = new ArrayList<>();
        int size = board.length;
        
        //현 좌표 [row, col].
        if(mode == 0){
            
            //선제 확인 안 된 기준점 옆칸[row, col+1] 범위 여부 확인.
            if(!isInRange(row, col+1, size)){
                return reaches;
            }
            
            //현 위치에서 위 두칸 비어있음 확인.
            if((0 <= row-1) && (board[row-1][col] == 0 && board[row-1][col+1] == 0)){
                //오른쪽 기준으로 위로 회전, 새로 형성된 위 좌표, 세로 모드 저장.
                reaches.add(new int[]{row-1, col, 1});
                //왼쪽 기준으로 위로 회전, 새로 형성된 위 좌표, 세로 모드 저장.
                reaches.add(new int[]{row-1, col+1, 1});
            }
            
            //현 위치에서 아래 두칸 비어있음 확인.
            if((row+1 < size) && (board[row+1][col] == 0 && board[row+1][col+1] == 0)){
                //왼쪽 기준 아래로 회전, 기존 왼쪽 좌표, 세로 모드 저장.
                reaches.add(new int[]{row, col, 1});
                //오른쪽 기준 아래로 회전, 기존 오른쪽 좌표, 세로모드 저장.
                reaches.add(new int[]{row, col+1, 1});
            }
        }
        //현재 모드 세로(1) 모드.
        else{
            
            //미확인 아래 좌표 범위 안 확인.
            if(!isInRange(row+1, col, size)){
                return reaches;
            }
            
            //현 위치에서 왼쪽(col-1) 두칸 비어있음을 확인.
            if((0 <= col-1) && (board[row][col-1] == 0 && board[row+1][col-1] == 0)){
                //위 기준으로 왼쪽으로 회전, 새로 형성된 왼쪽 좌표, 가로모드 저장.
                reaches.add(new int[]{row, col-1, 0});
                //아래 기준으로 왼쪽으로 회전, 새로 형성된 왼쪽 좌표, 가로모드 저장.
                reaches.add(new int[]{row+1, col-1, 0});
            }
            
            //현 위치에서 오른쪽(col+1) 두칸 비어있음을 확인.
            if((col+1 < size) && (board[row][col+1] == 0 && board[row+1][col+1] == 0)){
                //위 기준으로 오른쪽으로 회전, 기존 위 좌표, 가로모드 저장.
                reaches.add(new int[]{row, col, 0});
                //아래 기준으로 오른쪽으로 회전, 기존 아래 좌표, 가로모드 저장.
                reaches.add(new int[]{row+1, col, 0});
            }
            
            //현 위치에서 왼쪽(col-1) 두칸 비어있음을 확인.
            if((0 <= col-1) && (board[row][col-1] == 0 && board[row+1][col-1] == 0)){
                //위 기준으로 왼쪽으로 회전, 새로 형성된 왼쪽 좌표, 가로모드 저장.
                reaches.add(new int[]{row, col-1, 0});
                //아래 기준으로 왼쪽으로 회전, 새로 형성된 왼쪽 좌표, 가로모드 저장.
                reaches.add(new int[]{row+1, col-1, 0});
            }
                
        }
        
        return reaches;
    }
    

}