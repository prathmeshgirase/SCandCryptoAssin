def complement(A):
    return [1 - a for a in A]

def union(A, B):
    return [max(a, b) for a, b in zip(A, B)]

def intersection(A, B):
    return [min(a, b) for a, b in zip(A, B)]

A = list(map(float, input("Enter fuzzy set A (0-1, space separated): ").split()))
B = list(map(float, input("Enter fuzzy set B (0-1, space separated): ").split()))

if len(A) != len(B):
    print("Sets must have the same length!")
else:
    not_A = complement(A)
    not_B = complement(B)

    left_union = complement(union(A, B))
    right_intersection = intersection(not_A, not_B)

    left_intersection = complement(intersection(A, B))
    right_union = union(not_A, not_B)

    print("\nDe Morgan's Law Check:")
    print("1. NOT (A U B) =", left_union)
    print("   NOT A ∩ NOT B =", right_intersection)
    print("   Law holds?" , left_union == right_intersection)

    print("2. NOT (A ∩ B) =", left_intersection)
    print("   NOT A U NOT B =", right_union)
    print("   Law holds?" , left_intersection == right_union)
