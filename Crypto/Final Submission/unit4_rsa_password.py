import rsa

(public_key, private_key) = rsa.newkeys(512)  

username = input("Enter username: ")
password = input("Enter password: ")

encrypted_password = rsa.encrypt(password.encode(), public_key)

decrypted_password = rsa.decrypt(encrypted_password, private_key).decode()

print("\nUsername:", username)
print("Password:", password)
print("Encrypted password:", encrypted_password)
print("Decrypted password:", decrypted_password)
