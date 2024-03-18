package implement.actions.recommendation;

/**
 * Class stores a genres the user and uses the Builder design pattern
 */
public final class Genres {
    private String name = new String();
    private int numLikes;

    public final static class Builder {
        private String name = new String();     // mandatory
        private int numLikes;                   // optional

        /**
         * Constructor for Builder Class
         * @param name is the mandatory parameter
         */
        public Builder(final String name) {
            this.name = name;
        }

        /**
         * Method sets optional parameters
         * @param likes is the optional parameter
         * @return
         */
        public Builder numLikes(final int likes) {
            this.numLikes = likes;
            return this;
        }

        /**
         * Method creates an instance of the Genres class
         * @return the new instance
         */
        public Genres build() {
            return new Genres(this);
        }
    }
    private Genres(final Builder builder) {
        this.name = builder.name;
        this.numLikes = builder.numLikes;
    }

    public String getName() {
        return name;
    }

    public int getNumLikes() {
        return numLikes;
    }
}
