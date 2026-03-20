
//Leetcode 232

class Celula{
    int elemento;
    Celula prox;

    public Celula(int e){
        this.elemento = e;
        this.prox = null;
    }
}

class MyQueue {
    Celula prim;
    Celula ult;

    public MyQueue() {
        this.prim = null;
        this.ult = prim;
    }
    
    public void push(int x) {
        
    }
    
    public int pop() {
        
    }
    
    public int peek() {
        
    }
    
    public boolean empty() {
        
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String ent = sc.nextLine();
        String[] partes = ent.split("(?<=\\\\])\\\\s+(?=\\\\[)"); 
        String ops = partes[0];
        String nums = partes[1];


    }
}

// (?<=\\\\\])\\\\s+(?=\\\\\[)

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */