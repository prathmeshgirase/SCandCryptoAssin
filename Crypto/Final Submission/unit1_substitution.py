import string


ALPHABET = string.ascii_uppercase


def caesar_encrypt(plaintext: str, shift: int) -> str:
	plaintext = plaintext.upper()
	cipher = []
	for ch in plaintext:
		if ch in ALPHABET:
			idx = (ALPHABET.index(ch) + shift) % 26
			cipher.append(ALPHABET[idx])
		else:
			cipher.append(ch)
	return "".join(cipher)


def caesar_decrypt(ciphertext: str, shift: int) -> str:
	return caesar_encrypt(ciphertext, -shift)


def modified_caesar_encrypt(plaintext: str, key: str) -> str:
	"""
	Modified Caesar: polyalphabetic shift using a textual key.
	Each letter is shifted by the position of the corresponding key letter (A=0..Z=25).
	Non-letters are passed through unchanged.
	"""
	plaintext = plaintext.upper()
	key = key.upper()
	if not key or not any(k in ALPHABET for k in key):
		raise ValueError("Key must contain at least one alphabet character")
	result = []
	ki = 0
	for ch in plaintext:
		if ch in ALPHABET:
			k = ALPHABET.index(key[ki % len(key)])
			idx = (ALPHABET.index(ch) + k) % 26
			result.append(ALPHABET[idx])
			ki += 1
		else:
			result.append(ch)
	return "".join(result)


def modified_caesar_decrypt(ciphertext: str, key: str) -> str:
	ciphertext = ciphertext.upper()
	key = key.upper()
	result = []
	ki = 0
	for ch in ciphertext:
		if ch in ALPHABET:
			k = ALPHABET.index(key[ki % len(key)])
			idx = (ALPHABET.index(ch) - k) % 26
			result.append(ALPHABET[idx])
			ki += 1
		else:
			result.append(ch)
	return "".join(result)


def _playfair_prepare_key(key: str) -> str:
	key = key.upper().replace("J", "I")
	seen = set()
	ordered = []
	for ch in key + ALPHABET.replace("J", ""):
		if ch in ALPHABET and ch != "J" and ch not in seen:
			seen.add(ch)
			ordered.append(ch)
	return "".join(ordered)


def _playfair_matrix(key: str):
	prepared = _playfair_prepare_key(key)
	return [list(prepared[i:i + 5]) for i in range(0, 25, 5)]


def _playfair_find(matrix, ch):
	for r in range(5):
		for c in range(5):
			if matrix[r][c] == ch:
				return r, c
	raise ValueError("Character not in matrix")


def _playfair_pairs(text: str):
	text = text.upper().replace("J", "I")
	letters = [ch for ch in text if ch in ALPHABET]
	pairs = []
	i = 0
	while i < len(letters):
		a = letters[i]
		b = letters[i + 1] if i + 1 < len(letters) else "X"
		if a == b:
			pairs.append((a, "X"))
			i += 1
		else:
			pairs.append((a, b))
			i += 2
	return pairs


def playfair_encrypt(plaintext: str, key: str) -> str:
	m = _playfair_matrix(key)
	result = []
	for a, b in _playfair_pairs(plaintext):
		ra, ca = _playfair_find(m, a)
		rb, cb = _playfair_find(m, b)
		if ra == rb:
			result.append(m[ra][(ca + 1) % 5])
			result.append(m[rb][(cb + 1) % 5])
		elif ca == cb:
			result.append(m[(ra + 1) % 5][ca])
			result.append(m[(rb + 1) % 5][cb])
		else:
			result.append(m[ra][cb])
			result.append(m[rb][ca])
	return "".join(result)


def playfair_decrypt(ciphertext: str, key: str) -> str:
	m = _playfair_matrix(key)
	result = []
	ciphertext = ciphertext.upper()
	for i in range(0, len([c for c in ciphertext if c in ALPHABET]), 2):
		pair = [c for c in ciphertext if c in ALPHABET][i:i + 2]
		if len(pair) < 2:
			break
		a, b = pair
		ra, ca = _playfair_find(m, a)
		rb, cb = _playfair_find(m, b)
		if ra == rb:
			result.append(m[ra][(ca - 1) % 5])
			result.append(m[rb][(cb - 1) % 5])
		elif ca == cb:
			result.append(m[(ra - 1) % 5][ca])
			result.append(m[(rb - 1) % 5][cb])
		else:
			result.append(m[ra][cb])
			result.append(m[rb][ca])
	return "".join(result)


def _prompt_int(prompt: str) -> int:
	while True:
		try:
			return int(input(prompt).strip())
		except ValueError:
			print("Enter a valid integer.")


def main():
	print("Unit I - Substitution Ciphers")
	print("1) Caesar Cipher  2) Modified Caesar (Vigenère)  3) Playfair")
	choice = input("Choose (1/2/3): ").strip()
	text = input("Enter text: ")
	if choice == "1":
		shift = _prompt_int("Enter shift: ")
		enc = caesar_encrypt(text, shift)
		dec = caesar_decrypt(enc, shift)
		print("Encrypted:", enc)
		print("Decrypted:", dec)
	elif choice == "2":
		key = input("Enter key text: ")
		enc = modified_caesar_encrypt(text, key)
		dec = modified_caesar_decrypt(enc, key)
		print("Encrypted:", enc)
		print("Decrypted:", dec)
	elif choice == "3":
		key = input("Enter Playfair key: ")
		enc = playfair_encrypt(text, key)
		dec = playfair_decrypt(enc, key)
		print("Encrypted:", enc)
		print("Decrypted:", dec)
	else:
		print("Invalid choice")


if __name__ == "__main__":
	main()


