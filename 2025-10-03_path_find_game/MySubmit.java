import java.util.*;

//10-15분 손코딩
//방문할 곳의 2차원 좌표(nodeinfo)가 주어진다.
//좌표를 2진 트리의 노드 순서대로 위-아래, 왼-오 로 정렬.
//정렬된 녀석들에 자식을 붙인다 => 루트-> 왼-> 왼의 자녀...->오-> 오의 자녀...
//전위 순회, 후위 순회의 결과를 2차원 배열에 순서대로 담는다.

//1. nodeinfo가 있을때 이걸 어떻게 이진트리로 가공할까... 링크드리스트 개념으로, 내번호, 좌자녀, 우자녀 한 세트로 묶는 클래스.
//2. 이진트리에 순서는 어떻게 붙이는건가...? 들어온 배열 순서를 노드 순번으로 사용.
//3. 전위순회 후위순회 모두 노드를 타고 더이상 자녀가 없는 깊이까지 재귀적으로 들어가 저장학 빠져나오는 느낌으로.

//노드 클래스 정의.
class Node{
    int x;
    int y;
    int idx;
    Node left;
    Node right;
    Node(int x, int y, int idx){
        this.x = x;
        this.y = y;
        this.idx = idx;
    }
}

class Solution {
    
    List<Integer> preOrderList = new ArrayList<>();
    List<Integer> postOrderList = new ArrayList<>();

    public int[][] solution(int[][] nodeinfo) {
        
        List<Node> tree = formTree(nodeinfo);
        // for (Node n : tree) {
        //     System.out.println("idx=" + n.idx + ", x=" + n.x + ", y=" + n.y);
        // }
        
        //리스트에 전위, 후위 순회 순서 결과값 저장.
        doPreOrder(tree.get(0));
        doPostOrder(tree.get(0));
        
        //배열로 가공.
        int[] preArr = new int[tree.size()];
        int[] postArr = new int[tree.size()];
        
        for(int i = 0; i < tree.size(); i ++){
            preArr[i] = preOrderList.get(i);
            postArr[i] = postOrderList.get(i);
        }
        int[][] answer = {preArr, postArr};
        
        return answer;
    }
    
    //노드 => 트리로 정렬.
    private List<Node> formTree(int[][] nodes){
        
        //좌표 => 노드 리스트로 가공.
        List<Node> tree = new ArrayList<>();
        for(int i = 0; i < nodes.length; i++){
            tree.add(new Node(nodes[i][0], nodes[i][1], (i +1)));
        }
                       
        //노드 리스트 트리 형태로 정렬.
        tree.sort((a,b) -> {
            //같은 계층이면 왼쪽(x작은쪽)으로 오름차순.
            if(a.y == b.y){
                return a.x - b.x;
            }
            //nodes 값 중 y값 내림차순.
            return b.y - a.y;
        });
        
        //순서대로 자식 연결.
        Node root = tree.get(0);
        for(int i = 0; i < tree.size() - 1; i++){
            insertChildren(root, tree.get(i+1));
        }
        
        return tree;
    }
    
    private void insertChildren(Node parent, Node child){
        
        //왼편 자손 처리.
        if(parent.x > child.x){
            //왼편 자식 비어있는지 확인.
            if(parent.left == null){
                parent.left = child;
            }else{
                //이미 만석 => 한계층 아래로.
                insertChildren(parent.left, child);
            }
        }else{
            //오른편 자손 처리.
            if(parent.right == null){
                parent.right = child;
            }else{
                //이미 만석 => 한계층 아래로.
                insertChildren(parent.right, child);
            }
        }
    }
    
    //전위순회.
    private void doPreOrder(Node node){
       if(node == null){
           return;
       }else{
           //나 자신 먼저 읽음.
           preOrderList.add(node.idx);
           //왼쪽 읽게 보내기.
           doPreOrder(node.left);
           //오른쪽 읽게 보내기.
           doPreOrder(node.right);

       }
    }
    
    //후위순회.
    private void doPostOrder(Node node){
       if(node == null){
           return;
       }else{
           //왼쪽 읽게 보내기.
           doPostOrder(node.left);
           //오른쪽 읽게 보내기.
           doPostOrder(node.right);
           //나 자신 맨 마지막에 읽음.
           postOrderList.add(node.idx);
       }
    }
    
}

