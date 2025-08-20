public class CaesarCipher {

    // Encrypt text
    public static String encrypt(String text, int shift) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        // Normalize the shift key to ensure it is within [0, 25]
        shift = (shift % 26 + 26) % 26;
        StringBuilder encrypted = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encrypted.append((char) ((c - base + shift) % 26 + base));
            } else {
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }

    // Decrypt text
    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }
}
