
# Fuzzy Relation Composition using Matrix Form
# Author: Pushpakraj Girhe

# --- Input Fuzzy Matrices ---

# Fuzzy Relation A (m x n)
A = [
    [0.3, 0.7, 0.5],
    [0.9, 0.2, 0.8]
]

# Fuzzy Relation B (n x p)
B = [
    [0.6, 0.4],
    [0.8, 0.7],
    [0.5, 0.9]
]

# --- Display Input Matrices ---
print("Matrix A:")
for row in A:
    print(row)

print("\nMatrix B:")
for row in B:
    print(row)

# Get dimensions
m = len(A)
n = len(A[0])
p = len(B[0])

# --- Max-Min Composition ---
C_min = [[0 for _ in range(p)] for _ in range(m)]

for i in range(m):
    for j in range(p):
        mins = [min(A[i][k], B[k][j]) for k in range(n)]
        C_min[i][j] = max(mins)

# --- Max-Product Composition ---
C_prod = [[0 for _ in range(p)] for _ in range(m)]

for i in range(m):
    for j in range(p):
        prods = [A[i][k] * B[k][j] for k in range(n)]
        C_prod[i][j] = max(prods)

# --- Display Results ---
print("\nMax–Min Composition (A∘B):")
for row in C_min:
    print(row)

print("\nMax–Product Composition (A∘B):")
for row in C_prod:
    print(row)