import secrets

p  = 23  # prime
g = 5	 # primitive root

# Random private keys
a = secrets.randbelow(p - 2) + 2
b = secrets.randbelow(p - 2) + 2


# Compute public keys
A, B = pow(g, a, p), pow(g, b, p)

# Compute shared secret
shared_a, shared_b = pow(B, a, p), pow(A, b, p)

# Display results
print("Diffie–Hellman Key Exchange")
print(f"Public prime p = {p}, generator g = {g}")
print(f"Alice public = {A}")
print(f"Bob public = {B}")
print(f"Shared key (Alice) = {shared_a}")
print(f"Shared key (Bob) = {shared_b}")
print("Keys match:", shared_a == shared_b)
