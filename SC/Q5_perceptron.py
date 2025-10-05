from typing import List, Tuple


def perceptron_train(X: List[List[float]], y: List[int], lr: float = 1.0, epochs: int = 50) -> Tuple[List[float], float]:
    """Binary perceptron for labels {-1, +1}. Returns (weights, bias)."""
    if not X:
        return [], 0.0
    n_features = len(X[0])
    w = [0.0] * n_features
    b = 0.0
    for _ in range(epochs):
        errors = 0
        for xi, yi in zip(X, y):
            activation = sum(wj * xj for wj, xj in zip(w, xi)) + b
            if (activation >= 0) != (yi == 1):
                errors += 1
                update = lr * yi
                # vectorized-style update loop kept minimal
                for j in range(n_features):
                    w[j] += update * xi[j]
                b += update
        if errors == 0:
            break
    return w, b


def perceptron_predict(X: List[List[float]], w: List[float], b: float) -> List[int]:
	return [1 if (sum(wj * xj for wj, xj in zip(w, xi)) + b) >= 0 else -1 for xi in X]


def main():
	# AND gate example
	X = [[0, 0], [0, 1], [1, 0], [1, 1]]
	y = [-1, -1, -1, 1]
	w, b = perceptron_train(X, y, lr=0.5, epochs=20)
	print("Weights:", w, "Bias:", b)
	print("Predictions:", perceptron_predict(X, w, b))


if __name__ == "__main__":
	main()


