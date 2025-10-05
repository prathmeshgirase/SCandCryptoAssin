from typing import Dict


FuzzySet = Dict[str, float]


def complement(a: FuzzySet) -> FuzzySet:
	return {x: 1.0 - a[x] for x in a}


def union(a: FuzzySet, b: FuzzySet) -> FuzzySet:
	u = set(a) | set(b)
	a = {x: a.get(x, 0.0) for x in u}
	b = {x: b.get(x, 0.0) for x in u}
	return {x: max(a[x], b[x]) for x in u}


def intersection(a: FuzzySet, b: FuzzySet) -> FuzzySet:
	u = set(a) | set(b)
	a = {x: a.get(x, 0.0) for x in u}
	b = {x: b.get(x, 0.0) for x in u}
	return {x: min(a[x], b[x]) for x in u}


def approx_equal(a: FuzzySet, b: FuzzySet, eps: float = 1e-9) -> bool:
	keys = set(a) | set(b)
	return all(abs(a.get(k, 0.0) - b.get(k, 0.0)) < eps for k in keys)


def pretty(fs: FuzzySet) -> str:
	return "{" + ", ".join(f"{k}:{v:.2f}" for k, v in sorted(fs.items())) + "}"


def main():
	A = {"x1": 0.2, "x2": 0.7, "x3": 0.4}
	B = {"x2": 0.5, "x3": 0.8, "x4": 0.1}
	print("A =", pretty(A))
	print("B =", pretty(B))
	left1 = complement(union(A, B))
	right1 = intersection(complement(A), complement(B))
	left2 = complement(intersection(A, B))
	right2 = union(complement(A), complement(B))
	print("De Morgan 1: (A ∪ B)' == A' ∩ B' ->", approx_equal(left1, right1))
	print("De Morgan 2: (A ∩ B)' == A' ∪ B' ->", approx_equal(left2, right2))


if __name__ == "__main__":
	main()


