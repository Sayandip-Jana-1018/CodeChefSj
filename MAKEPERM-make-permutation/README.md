# MAKEPERM · Make Permutation

**Platform:** CodeChef (practice) · **Difficulty rating:** 1200 · **Tags:** greedy, sorting · **Solved:** 16 Nov 2026 · [Original problem](https://www.codechef.com/problems/MAKEPERM)

---

## 1. The problem in simple words

You get an array `A` of size `N`, where every value is between 1 and `N`.

You may **add any non-negative number** to each element (adding 0 is allowed). You can never decrease anything.

Can you end up with a **permutation of 1..N**, meaning every number from 1 to N appears exactly once? Print `YES` or `NO`.

| Limit | Value |
|---|---|
| Test cases `T` | up to 100 |
| `N` | 1 to 100 |
| `A[i]` | 1 to N |
| Time limit | 1 second |

---

## 2. Intuition

Every final value must be **≥ its starting value**. So the real question is:

> Can we give each element its own slot from 1..N, where each element's slot is at least as big as the element?

Small elements can fit almost anywhere. Big elements are picky: they can only go into big slots. So **hand out slots in sorted order**:

- The smallest element gets slot 1, the next smallest gets slot 2, and so on.
- If the i-th smallest element is **bigger than `i`**, there's no hope, so the answer is `NO`.

Why that's enough to prove `NO`: suppose the i-th smallest value is `v > i`. Then the elements from position i to N are all ≥ `v`, which is `N − i + 1` elements. They need `N − i + 1` different slots, all ≥ `v`. But only `N − v + 1` such slots exist, and that's fewer. By pigeonhole, two of them would have to share a slot.

> ✅ Answer is `YES` **if and only if** `sorted A[i] ≤ i` for every position `i` (1-indexed).

---

## 3. Brute force (and why it fails)

Try every way to assign slots 1..N to the elements (backtracking) and check `slot ≥ A[i]`. That's up to `N!` assignments, and 100! is astronomically large.

It's fine for tiny `N`, though. I used it with `N ≤ 7` to cross-check the greedy on 100 random tests.

---

## 4. Optimal approach: sort and check

1. Sort `A` ascending.
2. For `i = 0 .. N−1` (0-indexed), check `A[i] ≤ i + 1`.
3. If every check passes, print `YES`. Otherwise print `NO`.

---

## 5. Dry run

**Test 1:** `A = [4, 1, 3, 2, 1]` → sorted `[1, 1, 2, 3, 4]`

| Slot `i` | 1 | 2 | 3 | 4 | 5 |
|---|---|---|---|---|---|
| Sorted value | 1 | 1 | 2 | 3 | 4 |
| value ≤ slot? | ✅ | ✅ | ✅ | ✅ | ✅ |

Answer **YES**. One valid final array is 1, 2, 3, 4, 5 in sorted order.

**Test 2:** `A = [2, 4, 3, 4, 2]` → sorted `[2, 2, 3, 4, 4]`

Slot 1 needs a value ≤ 1, but the smallest is 2 ❌. Answer **NO**: nothing can ever become 1.

**Test 4:** `A = [1, 1, 1, 1, 6, 6]` → sorted `[1, 1, 1, 1, 6, 6]`

Slot 5 gets value 6, and 6 ≤ 5 fails ❌. Answer **NO**: the two 6s would both need slot 6.

---

## 6. Java code

```java
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
```

The same code is in [`Codechef.java`](./Codechef.java).

---

## 7. Complexity

| | Cost | Why |
|---|---|---|
| **Time** | **O(N log N)** per test | Sorting. Counting sort would make it O(N), since values are ≤ N. |
| **Space** | **O(N)** | The array |

---

## 8. Edge cases

- `N = 1`, `A = [1]`: `YES`.
- All values equal to 1: `YES`, since they can become 1, 2, 3, …
- Two copies of `N`: `NO`, since only one slot holds the value N.

## 9. Common mistakes

- Only checking that every `A[i] ≤ N`. The constraints already guarantee that, and it ignores duplicates fighting over the same slot.
- Checking the array **without sorting** it first.
- Off-by-one: the i-th position (0-indexed) is slot `i + 1`.

## 10. Key takeaway

> When values can only move **one way** (only up, or only down), sort them and match greedily to targets in order. Pigeonhole tells you when it's impossible.

---

## Submission

**Correct Answer** · 4/4 subtasks · max 0.05 s · Java · 1 attempt
