package example.com.sunshine.HTTP;

public class FinalizableObject {
        int i;  // set in the constructor
        int j; // set by the setter, below
        static int k;  // set by direct access
        public FinalizableObject(int i) {
            this.i = i;
        }
        public void setJ(int j) {
            this.j = j;
        }
        public void finalize() {
            System.out.println(i + " " + j + " " + k);
        }
    }