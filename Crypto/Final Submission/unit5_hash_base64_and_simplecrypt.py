import base64
import hashlib
from getpass import getpass

try:
	from simplecrypt import encrypt as sc_encrypt, decrypt as sc_decrypt
	extra_available = True
except Exception:
	extra_available = False


def md5_sha(text: str):
	md5 = hashlib.md5(text.encode("utf-8")).hexdigest()
	sha = hashlib.sha256(text.encode("utf-8")).hexdigest()
	return md5, sha


def b64_encode_decode(text: str):
	encoded = base64.b64encode(text.encode("utf-8")).decode("ascii")
	decoded = base64.b64decode(encoded.encode("ascii")).decode("utf-8")
	return encoded, decoded


def simple_crypt_demo(text: str):
	if not extra_available:
		return None, None
	password = getpass("Enter password for simple-crypt: ")
	cipher = sc_encrypt(password, text)
	plain = sc_decrypt(password, cipher).decode("utf-8")
	return cipher.hex(), plain


def main():
	print("Unit V - Hashing, Base64, and simple-crypt demo")
	user_text = input("Enter text: ")
	md5, sha = md5_sha(user_text)
	print("MD5:", md5)
	print("SHA-256:", sha)
	enc, dec = b64_encode_decode(user_text)
	print("base64 encoded:", enc)
	print("base64 decoded:", dec)
	if extra_available:
		cipher_hex, plain = simple_crypt_demo(user_text)
		print("simple-crypt ciphertext(hex):", cipher_hex)
		print("simple-crypt decrypted:", plain)
	else:
		print("simple-crypt not installed. Install with: pip install simple-crypt")


if __name__ == "__main__":
	main()


