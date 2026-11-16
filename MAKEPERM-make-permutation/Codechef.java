import java.io.*;
import java.util.*;

class Codechef {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(br);
        StringBuilder out = new StringBuilder();
        st.nextToken();
        int t = (int) st.nval;
        while (t-- > 0) {
            st.nextToken();
            int n = (int) st.nval;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) { st.nextToken(); a[i] = (int) st.nval; }
            Arrays.sort(a);
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if (a[i] > i + 1) { ok = false; break; }   // i-th smallest must fit into value i+1 or more
            }
            out.append(ok ? "YES" : "NO").append('\n');
        }
        System.out.print(out);
    }
}
