package rs.etf.sab.student;

import rs.etf.sab.operations.ArticleOperations;
import rs.etf.sab.student.utils.Column;
import rs.etf.sab.student.utils.DB;


class ArticleOperationsImpl implements ArticleOperations {
    
    @Override
    public int createArticle(int shopId, String articleName, int articlePrice) {
        return DB.insert("Article", new Column() {{
            put("ShopID", shopId);
            put("Name", articleName);
            put("Price", articlePrice);
        }});
    }
    
}