package rs.etf.sab.student.implementations;

import rs.etf.sab.operations.ArticleOperations;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.Entry;


public abstract class ArticleOperationsImpl implements ArticleOperations {
    
    @Override
    public int createArticle(int shopId, String articleName, int articlePrice) {
        return DB.insert("Article", new Entry() {{
            put("ShopID", shopId);
            put("Name", articleName);
            put("Price", articlePrice);
        }});
    }
    
}