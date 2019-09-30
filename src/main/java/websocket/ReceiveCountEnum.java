package websocket;

public enum ReceiveCountEnum {
    INSTANCE;

    ReceiveCount count;
    ReceiveCountEnum() {
       this.count = new ReceiveCount();
    }

    public ReceiveCount getInstance() {
        return count;
    }

    public class ReceiveCount{
        volatile int count = 0;

        private ReceiveCount(){}


        public void add() {
            count++;
        }

        public void del() {
            count--;
        }

        public int getCount() {
            return count;
        }
    }
}
