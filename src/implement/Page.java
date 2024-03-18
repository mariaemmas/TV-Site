package implement;

import java.util.ArrayList;

/**
 * Class creates a page object
 * A page hase it's name, id but also a list of possible future pages
 */
public final class Page {
    private String pageName;
    private int pageId;
    private ArrayList<Integer> nextPages;

    public Page() { };
    public Page(final String pageName, final int pageId, final int[] nextPages) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.nextPages = new ArrayList<Integer>();
        for (int i = 0; i < nextPages.length; i++) {
            this.nextPages.add(nextPages[i]);
        }
    }
    public String getPageName() {
        return pageName;
    }

    public void setPageName(final String pageName) {
        this.pageName = pageName;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(final int pageId) {
        this.pageId = pageId;
    }

    public ArrayList<Integer> getNextPages() {
        return nextPages;
    }

    public void setNextPages(final ArrayList<Integer> nextPages) {
        this.nextPages = nextPages;
    }
}
