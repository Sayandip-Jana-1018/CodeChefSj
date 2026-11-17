import java.util.*;
import java.io.*;

class Codechef {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(br);
        st.nextToken();
        int t = (int) st.nval;
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            st.nextToken();
            int n = (int) st.nval;
            int[] a = new int[n + 1];
            for (int i = 1; i <= n; i++) { st.nextToken(); a[i] = (int) st.nval; }

            // Any path 1 = i0 < i1 < ... < ik pays (ik - 1) in total distance,
            // so only the LAST index matters for the penalty.
            // Ending at e: take A[e], plus every positive A[j] with 1 < j < e.
            long best = 0;          // we may never jump at all
            long positives = 0;     // sum of positive A[j] for 1 < j < e
            for (int e = 2; e <= n; e++) {
                best = Math.max(best, positives + a[e] - (e - 1));
                if (a[e] > 0) positives += a[e];
            }
            sb.append(best).append('\n');
        }
        System.out.print(sb);
    }
}
