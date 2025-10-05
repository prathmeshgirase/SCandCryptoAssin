from typing import Dict, List, Tuple


FuzzySet = Dict[str, float]


def lambda_cut(fs: FuzzySet, lam: float) -> List[str]:
	if not (0.0 <= lam <= 1.0):
		raise ValueError("lambda must be in [0,1]")
	return [x for x, mu in fs.items() if mu >= lam]


def strong_lambda_cut(fs: FuzzySet, lam: float) -> List[str]:
	return [x for x, mu in fs.items() if mu > lam]


def main():
	A = {"x1": 0.1, "x2": 0.6, "x3": 0.8, "x4": 0.3}
	lam = 0.5
	print("A:", A)
	print("λ-cut (>= 0.5):", lambda_cut(A, lam))
	print("strong λ-cut (> 0.5):", strong_lambda_cut(A, lam))


if __name__ == "__main__":
	main()


