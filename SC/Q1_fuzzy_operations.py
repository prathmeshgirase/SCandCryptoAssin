# Fuzzy Set Operations
# 1 Define two fuzzy sets A and B as dictionaries (element: membership value)
A = {'x1': 0.2, 'x2': 0.5, 'x3': 0.7}
B = {'x1': 0.4, 'x2': 0.3, 'x3': 0.8}

print("Fuzzy Set A:", A)
print("Fuzzy Set B:", B)

# 1️⃣ Union: max(μA(x), μB(x))
union = {x: max(A[x], B[x]) for x in A}
print("\nUnion (A ∪ B):", union)

# 2️⃣ Intersection: min(μA(x), μB(x))
intersection = {x: min(A[x], B[x]) for x in A}
print("Intersection (A ∩ B):", intersection)

# 3️⃣ Complement: 1 - μA(x)
complement_A = {x: 1 - A[x] for x in A}
print("Complement of A (A'):", complement_A)

# 4️⃣ Algebraic Sum: μA(x) + μB(x) - μA(x)*μB(x)
algebraic_sum = {x: (A[x] + B[x]) - (A[x] * B[x]) for x in A}
print("Algebraic Sum:", algebraic_sum)

# 5️⃣ Algebraic Product: μA(x) * μB(x)
algebraic_product = {x: A[x] * B[x] for x in A}
print("Algebraic Product:", algebraic_product)

# 6️⃣ Cartesian Product: all pairs (x, y) with min(μA(x), μB(y))
cartesian_product = {(x, y): min(A[x], B[y]) for x in A for y in B}
print("\nCartesian Product (A × B):")
for pair, value in cartesian_product.items():
    print(f"{pair}: {value}")