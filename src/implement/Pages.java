package implement;

import java.util.ArrayList;

/**
 * Class creates an array of pages
 */
public final class Pages {
    private ArrayList<Page> allPages;

    /**
     * Method creates the array
     */
    public Pages() {
        this.allPages = new ArrayList<>();
        int homepageNeautentificatId = 1;
        int loginId = 2;
        int registerId = 3;
        int homepageAutentificatId = 4;
        int moviesId = 5;
        int seeDetailsId = 6;
        int upgradesId = 7;
        int logoutId = 8;
        this.allPages.add(new Page("Homepage neautentificat", homepageNeautentificatId,
                new int[]{loginId, registerId}));
        this.allPages.add(new Page("login", loginId, new int[]{homepageAutentificatId}));
        this.allPages.add(new Page("register", registerId, new int[]{homepageAutentificatId}));
        this.allPages.add(new Page("Homepage autentificat", homepageAutentificatId,
                new int[]{moviesId, upgradesId, logoutId}));
        this.allPages.add(new Page("movies", moviesId, new int[]{homepageAutentificatId,
                seeDetailsId, upgradesId, logoutId}));
        this.allPages.add(new Page("see details", seeDetailsId, new int[]{homepageAutentificatId,
                moviesId, upgradesId, logoutId}));
        this.allPages.add(new Page("upgrades", upgradesId, new int[]{homepageAutentificatId,
                moviesId, logoutId}));
        this.allPages.add(new Page("logout", logoutId, new int[]{homepageNeautentificatId}));
    }

    public ArrayList<Page> getAllPages() {
        return allPages;
    }

    public void setAllPages(final ArrayList<Page> allPages) {
        this.allPages = allPages;
    }

    /**
     * Method searches the array of pages and returns the page found
     * @param pageName the name tha page has to have
     * @return the found page
     */
    public Page getPageByName(final String pageName) {
        for (int i = 0; i < this.allPages.size(); i++) {
            if (allPages.get(i).getPageName().equals(pageName)) {
                return allPages.get(i);
            }
        }
        return null;
    }

}
