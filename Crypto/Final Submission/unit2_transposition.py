# ======================= Que1.py =======================
# Rail Fence Cipher

def rail_fence_encrypt(text, rails):
    fence = [[] for _ in range(rails)]
    rail, var = 0, 1
    for char in text:
        fence[rail].append(char)
        rail += var
        if rail == 0 or rail == rails - 1:
            var = -var
    return ''.join(''.join(row) for row in fence)

def rail_fence_decrypt(cipher, rails):
    pattern = list(range(rails)) + list(range(rails - 2, 0, -1))
    rail_len = [0] * rails
    for i, p in enumerate(pattern * (len(cipher) // len(pattern) + 1)):
        if i >= len(cipher): 
            break
        rail_len[p] += 1

    rails_txt = []
    idx = 0
    for r in rail_len:
        rails_txt.append(list(cipher[idx:idx + r]))
        idx += r

    result = ""
    for p in pattern * (len(cipher) // len(pattern) + 1):
        if not rails_txt[p]:
            break
        result += rails_txt[p].pop(0)
    return result

# Example
text = "HELLO"
cipher = rail_fence_encrypt(text, 3)
print("=== Rail Fence Cipher ===")
print("Encrypted:", cipher)
print("Decrypted:", rail_fence_decrypt(cipher, 3))
print()


# ======================= Que2.py =======================
# Columnar Transposition Cipher

def columnar_encrypt(text, key):
    text = text.replace(" ", "")
    columns = [''] * len(key)
    for i, char in enumerate(text):
        columns[i % len(key)] += char
    order = sorted(range(len(key)), key=lambda k: key[k])
    return ''.join(columns[i] for i in order)

def columnar_decrypt(cipher, key):
    key_len = len(key)
    order = sorted(range(key_len), key=lambda k: key[k])
    n = len(cipher)

    # Determine column lengths
    col_lens = [n // key_len] * key_len
    for i in range(n % key_len):
        col_lens[i] += 1

    # Reconstruct columns based on key order
    cols = [''] * key_len
    idx = 0
    for i in order:
        cols[i] = cipher[idx:idx + col_lens[i]]
        idx += col_lens[i]

    # Read row-wise to reconstruct plaintext
    result = ''
    for i in range(max(col_lens)):
        for j in range(key_len):
            if i < len(cols[j]):
                result += cols[j][i]
    return result

# Example
text = "HELLO WORLD"
key = "KEY"
cipher = columnar_encrypt(text, key)
print("=== Columnar Cipher ===")
print("Encrypted:", cipher)
print("Decrypted:", columnar_decrypt(cipher, key))
