def lambda_cut(fuzzy_set, lam):
    return [1 if x >= lam else 0 for x in fuzzy_set]

fuzzy_set = list(map(float, input("Enter fuzzy set elements (0-1, space separated): ").split()))
lam = float(input("Enter lambda value (0-1): "))

cut_set = lambda_cut(fuzzy_set, lam)

print("\nOriginal fuzzy set:", fuzzy_set)
print(f"λ-cut (λ = {lam}):", cut_set)
