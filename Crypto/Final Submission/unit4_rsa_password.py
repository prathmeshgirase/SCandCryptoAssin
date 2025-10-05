from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP


def generate_keys(bits: int = 2048):
	key = RSA.generate(bits)
	private_key = key
	public_key = key.publickey()
	return public_key, private_key


def encrypt_password(password: str, public_key: RSA.RsaKey) -> bytes:
	cipher = PKCS1_OAEP.new(public_key)
	return cipher.encrypt(password.encode("utf-8"))


def decrypt_password(token: bytes, private_key: RSA.RsaKey) -> str:
	cipher = PKCS1_OAEP.new(private_key)
	return cipher.decrypt(token).decode("utf-8")


def main():
	print("Unit IV - RSA Password Encryption (OAEP)")
	username = input("Username: ")
	password = input("Password: ")
	pub, priv = generate_keys()
	encrypted = encrypt_password(password, pub)
	decrypted = decrypt_password(encrypted, priv)
	print("User -", username)
	print("Password -", password)
	print("Encrypted password -", encrypted.hex())
	print("Decrypted password -", decrypted)


if __name__ == "__main__":
	main()


