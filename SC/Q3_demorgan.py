# Fuzzy De Morgan's Law Implementation

# Define fuzzy sets A and B
A = {'x1': 0.2, 'x2': 0.5, 'x3': 0.8}
B = {'x1': 0.6, 'x2': 0.3, 'x3': 0.9}

# Complement of a fuzzy set: 1 - value
A_comp = {x: 1 - A[x] for x in A}
B_comp = {x: 1 - B[x] for x in B}

# Fuzzy union (A ∪ B): max(A[x], B[x])
union = {x: max(A[x], B[x]) for x in A}

# Fuzzy intersection (A ∩ B): min(A[x], B[x])
intersection = {x: min(A[x], B[x]) for x in A}

# (A ∪ B)' = A' ∩ B'
lhs1 = {x: 1 - union[x] for x in A}   # Left-hand side
rhs1 = {x: min(A_comp[x], B_comp[x]) for x in A}  # Right-hand side

# (A ∩ B)' = A' ∪ B'
lhs2 = {x: 1 - intersection[x] for x in A}
rhs2 = {x: max(A_comp[x], B_comp[x]) for x in A}

# Display results
print("A:", A)
print("B:", B)
print("\nA complement:", A_comp)
print("B complement:", B_comp)

print("\n(A ∪ B)' =", lhs1)
print("A' ∩ B'  =", rhs1)

print("\n(A ∩ B)' =", lhs2)
print("A' ∪ B'  =", rhs2)