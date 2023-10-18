package rs.etf.sab.student;

import rs.etf.sab.student.implementations.ArticleOperationsImpl;
import rs.etf.sab.student.utils.Logger;


class ArticleOperations extends ArticleOperationsImpl {
    
    @Override
    public int createArticle(int shopId, String articleName, int articlePrice) {
        Logger.functionStart("ArticleOperations createArticle(shopId: " + shopId + ", articleName: " + articleName + ", articlePrice: " + articlePrice + ")");
        
        int result = super.createArticle(shopId, articleName, articlePrice);
        
        Logger.functionEnd(result);
        
        return result;
    }
    
}