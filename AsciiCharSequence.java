import java.util.*;

/**
 * Compact strings with AsciiCharSequence
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-15
 */

/**
 * Interface  Compact strings with AsciiCharSequence
 *
 * Strings in Java implement java.lang.CharSequence interface. Since Java internally uses UTF-16,
 * 2 bytes are required to store each char. At the same time, ASCII encoding allows storing character
 * codes in one byte and includes all Latin letters, digits, and standard special characters. Compared
 * to the standard String class, ASCII-character sequences require half the memory.
 *
 * Write a class named AsciiCharSequence for storing ASCII-character sequences, that should:
 *
 * implement the interface java.lang.CharSequence;
 * have a constructor that takes a byte array;
 * have methods length, charAt, subSequence, and toString.
 * You can find the declaration of methods and their behavior in the description of java.lang.CharSequence
 * (JavaDoc or sources).
 *
 * Carefully check signatures of abstract methods of java.lang.CharSequence interface, especially subSequence
 * method. It accepts 2 integers: start index (inclusive) and end index (!exclusive). The method returns
 * an object of a class that implements java.lang.CharSequence interface. For example, it can be an instance
 * of AsciiCharSequence class.
 *
 * Note: the testing system will always pass correct input parameters to overridden methods.
 *
 * P.S. The feature is supported since Java 9 in standard strings. For details, see this article on compact
 * strings in Java 9.
 */

class AsciiCharSequence implements CharSequence {
    byte[] charSeq;

    public AsciiCharSequence(byte[] charSeq) {
        this.charSeq = charSeq.clone();
    }

    // implementation
    public int length() {
        return this.charSeq.length;
    }

    public char charAt(int index) {
        return (char) this.charSeq[index];
    }

    public CharSequence subSequence(int start, int end) {

        byte[] subSeq = new byte[end - start];

        for (int i = start; i < end; i++) {
            subSeq[i - start] = this.charSeq[i];
        }

        return new AsciiCharSequence(subSeq);
    }

    public String toString() {
        return new String(this.charSeq);
    }
}