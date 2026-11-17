# JUMPCOST · Jumping Cost

**Platform:** CodeChef (practice) · **Difficulty rating:** 1281 · **Tags:** greedy, math · **Solved:** 17 Nov 2026 · [Original problem](https://www.codechef.com/problems/JUMPCOST)

---

## 1. The problem in simple words

You have an array `A[1..N]`. You stand on index 1 with balance 0.
A jump goes from index `i` to any later index `j > i`. It **adds `A[j]`** to your balance but also **subtracts the jump length `j − i`**.
You can jump as many times as you like (or not at all) and stop whenever you want. What is the **highest balance** you can ever reach?

| Limit | Value |
|---|---|
| `T` | up to 100 |
| `N` | 2 to 100 |
| `A[i]` | −100 to 100 |

---

## 2. Intuition: the jump lengths telescope

Take any path `1 = i₀ < i₁ < … < iₖ`. The total of the jump lengths is

```
(i₁ − i₀) + (i₂ − i₁) + … + (iₖ − iₖ₋₁) = iₖ − 1
```

So the total penalty depends **only on where you stop**, not on how many jumps you make.
Once you fix the stopping index `e`, the penalty is fixed at `e − 1`, and you should land on **every index between 1 and `e` whose value is positive** (each one only adds to your balance). Note that `A[1]` is never added, because you start there.

```
best(e) = A[e] + (sum of positive A[j] for 1 < j < e) − (e − 1)
answer  = max(0, best(2), best(3), …, best(N))
```

The `0` is the "never jump" option.

---

## 3. Brute force (and why it's slow)

Try every path: every subset of indices after 1. That's `2^(N−1)` paths, about 10³⁰ for `N = 100`. Impossible.
(A DP `dp[j] = max over i < j of dp[i] + A[j] − (j − i)` would be `O(N²)` and pass here, but the telescoping trick gives `O(N)` and shows *why* the answer looks the way it does.)

---

## 4. Optimal approach

Scan `e` from 2 to `N`, keeping `positives` = sum of positive values strictly between 1 and `e`:

1. `best = max(best, positives + A[e] − (e − 1))`
2. Then, if `A[e] > 0`, add it to `positives` for the next `e`.

Start with `best = 0`.

---

## 5. Dry run

Sample 1: `A = [5, 5, −1, 5, −1, 1]` (1-indexed).

| `e` | `A[e]` | positives before `e` | best(e) = positives + A[e] − (e−1) | running max |
|---|---|---|---|---|
| 2 | 5 | 0 | 0 + 5 − 1 = 4 | 4 |
| 3 | −1 | 5 | 5 − 1 − 2 = 2 | 4 |
| 4 | 5 | 5 | 5 + 5 − 3 = **7** | **7** |
| 5 | −1 | 10 | 10 − 1 − 4 = 5 | 7 |
| 6 | 1 | 10 | 10 + 1 − 5 = 6 | 7 |

Answer **7**: jump 1 → 2 → 4 (balance 4, then 4 + 5 − 2 = 7).

Sample 3, all negative: every `best(e) < 0`, so the answer is **0** (don't jump).

---

## 6. Java code

```java
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
```

File: [`Codechef.java`](./Codechef.java).

---

## 7. Complexity

| | Value | Why |
|---|---|---|
| Time | `O(N)` per test | One left-to-right scan |
| Space | `O(N)` | The array (could be `O(1)` by processing while reading) |

---

## 8. Edge cases

- **All values negative:** the answer is 0, because you can choose not to jump.
- **`A[1]` is large:** it doesn't count. You start on index 1 and never "land" on it.
- **The best stop is in the middle:** the balance can drop later, so take the max over every `e`, not just `e = N`.

---

## 9. Common mistakes

- Adding `A[1]` to the balance.
- Only checking the balance at the last index instead of "at any point".
- Adding `A[e]` to `positives` *before* computing `best(e)`, which counts the stopping value twice.

---

## 10. Key takeaway

> When costs are differences of positions (`j − i`), add them up along a path. They usually telescope, and then only the endpoints matter.

---

## Submission

**Correct Answer** · 100% (4/4 tasks, max 0.05 s) · Java · 1st attempt
