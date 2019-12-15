package Main;

public class WordPair {
    private String Native, Foreign;

    WordPair(String word1, String word2) {
        Native = word1;
        Foreign = word2;
    }

    public String Get(int language) {
        switch (language) {
            case 0:
                return Native;
            case 1:
                return Foreign;
            default:
                return "";
        }
    }

    public boolean equals(WordPair pair){
        return this.Native.equals(pair.Native) || this.Foreign.equals(pair.Foreign);
    }

    @Override
    public String toString() {
        return Native + " - " + Foreign;
    }
}
