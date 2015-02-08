In a famous scientific problem, researchers are interested in the following question:
if sites are independently set to be open with probability p (and therefore blocked
with probability 1 − p), what is the probability that the system percolates? When p
equals 0, the system does not percolate; when p equals 1, the system percolates.

When N is sufficiently large, there is a threshold value p* such that when p < p*
a random N-by-N grid almost never percolates, and when p > p*, a random N-by-N grid
almost always percolates. No mathematical solution for determining the percolation
threshold p* has yet been derived. This program to estimates p*.

# Usage
To run 100 percolation experiments on a 200x200 grid.
```
java PercolationStats 200 100
```

# Example Output

```shell
% java PercolationStats 200 100
mean                    = 0.593626 
stddev                  = 0.010351 
95% confidence interval = 0.5915973702203986, 0.5956551297796017
```

# Requirements
* algs4.jar
* stdlib.jar

Both are from algs4 and must be in the Java classpath.
