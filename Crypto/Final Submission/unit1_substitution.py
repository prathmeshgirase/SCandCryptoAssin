import string

ALPHABET = string.ascii_uppercase


# =============== BASIC CAESAR CIPHER ===============
def caesar_cipher(text, shift, decrypt=False):
    text = text.upper()
    res = []
    for ch in text:
        if ch in ALPHABET:
            s = -shift if decrypt else shift
            res.append(ALPHABET[(ALPHABET.index(ch) + s) % 26])
        else:
            res.append(ch)
    return ''.join(res)


# =============== MODIFIED CAESAR (LIST OF NUMERIC KEYS) ===============
def modified_caesar_keys(text, keys, decrypt=False):
    """Polyalphabetic numeric key list. Keys are integers, applied cyclically.
    Case is preserved; non-letters are copied and also advance the key index
    to match the provided snippet's behavior.
    """
    res = []
    for i, ch in enumerate(text):
        k = keys[i % len(keys)] % 26
        if 'A' <= ch <= 'Z':
            base = ord('A')
            shift = -k if decrypt else k
            res.append(chr((ord(ch) - base + shift) % 26 + base))
        elif 'a' <= ch <= 'z':
            base = ord('a')
            shift = -k if decrypt else k
            res.append(chr((ord(ch) - base + shift) % 26 + base))
        else:
            # keep as-is, still consume key position to mirror provided code
            res.append(ch)
    return ''.join(res)


# =============== PLAYFAIR CIPHER ===============
def _prepare_key(key):
    key = key.upper().replace('J', 'I')
    seen, ordered = set(), []
    for ch in key + ALPHABET.replace('J', ''):
        if ch.isalpha() and ch not in seen:
            seen.add(ch)
            ordered.append(ch)
    return ''.join(ordered)


def _matrix(key):
    p = _prepare_key(key)
    return [list(p[i:i + 5]) for i in range(0, 25, 5)]


def _find(m, ch):
    for r in range(5):
        for c in range(5):
            if m[r][c] == ch:
                return r, c
    raise ValueError("Character not in matrix")


def _pairs(text):
    text = text.upper().replace('J', 'I')
    letters = [ch for ch in text if ch in ALPHABET]
    pairs, i = [], 0
    while i < len(letters):
        a = letters[i]
        b = letters[i + 1] if i + 1 < len(letters) else 'X'
        if a == b:
            pairs.append((a, 'X'))
            i += 1
        else:
            pairs.append((a, b))
            i += 2
    return pairs


def playfair_cipher(text, key, decrypt=False):
    m = _matrix(key)
    res = []
    for a, b in _pairs(text):
        ra, ca = _find(m, a)
        rb, cb = _find(m, b)
        if ra == rb:
            shift = -1 if decrypt else 1
            res.append(m[ra][(ca + shift) % 5])
            res.append(m[rb][(cb + shift) % 5])
        elif ca == cb:
            shift = -1 if decrypt else 1
            res.append(m[(ra + shift) % 5][ca])
            res.append(m[(rb + shift) % 5][cb])
        else:
            res.append(m[ra][cb])
            res.append(m[rb][ca])
    return ''.join(res)


# =============== MAIN MENU ===============
def main():
    print("=== UNIT I: SUBSTITUTION CIPHERS ===")
    print("1) Caesar Cipher")
    print("2) Modified Caesar (Numeric keys list)")
    print("3) Playfair Cipher")

    choice = input("Choose (1/2/3): ").strip()
    text = input("Enter text: ")

    if choice == "1":
        shift = int(input("Enter shift (e.g., 3): "))
        enc = caesar_cipher(text, shift)
        dec = caesar_cipher(enc, shift, decrypt=True)
        print("\nEncrypted:", enc)
        print("Decrypted:", dec)

    elif choice == "2":
        raw = input("Enter keys (comma separated): ")
        keys = [int(x.strip()) for x in raw.split(',') if x.strip()]
        enc = modified_caesar_keys(text, keys)
        dec = modified_caesar_keys(enc, keys, decrypt=True)
        print("\nEncrypted:", enc)
        print("Decrypted:", dec)

    elif choice == "3":
        key = input("Enter Playfair key: ")
        enc = playfair_cipher(text, key)
        dec = playfair_cipher(enc, key, decrypt=True)
        print("\nEncrypted:", enc)
        print("Decrypted:", dec)

    else:
        print("Invalid choice!")


if __name__ == "__main__":
    main()
