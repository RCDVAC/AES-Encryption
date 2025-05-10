package model;

public class Word {

    private final byte[] bytes;

    public Word(byte[] bytes) {
        if (bytes.length > 4) {
            throw new IllegalArgumentException("Word only only has 4 bytes");
        }
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte getByte(int index) {
        if (index > 4) {
            throw new IllegalArgumentException("Word only only has 4 bytes");
        }
        return bytes[index];
    }

    public void setByte(int index, byte b) {
        if (index > 4) {
            throw new IllegalArgumentException("Word only only has 4 bytes");
        }
        this.bytes[index] = b;
    }
}
