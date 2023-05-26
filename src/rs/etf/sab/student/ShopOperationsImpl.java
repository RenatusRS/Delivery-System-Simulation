package rs.etf.sab.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rs.etf.sab.operations.ShopOperations;
import rs.etf.sab.student.utils.SelectResult;
import rs.etf.sab.student.utils.DB;
import rs.etf.sab.student.utils.UtilityOperations;
import rs.etf.sab.student.utils.Where;


class ShopOperationsImpl implements ShopOperations {
    
    @Override
    public int createShop(String name, String cityName) {
        return DB.insert("Shop", new HashMap<>() {{
            put("Name", name);
            put("CityID", UtilityOperations.getCityByName(cityName));
        }});
    }
    
    @Override
    public int setCity(int shopId, String cityName) {
        return DB.update("Shop", new HashMap<>() {{
            put("CityID", UtilityOperations.getCityByName(cityName));
        }}, new Where("ID", "=", shopId));
    }
    
    @Override
    public int getCity(int shopId) {
        return (int) DB.select("Shop", new Where("Id", "=", shopId)).get("CityID");
    }
    
    @Override
    public int setDiscount(int shopId, int discountPercentage) {
        return DB.update("Shop", new HashMap<>() {{
            put("Discount", discountPercentage);
        }}, new Where("ID", "=", shopId));
    }
    
    @Override
    public int increaseArticleCount(int articleId, int increment) {
        boolean success = DB.update("Article", new HashMap<>() {{
            put("Count", "Count + " + increment);
        }}, new Where("ID", "=", articleId)) != -1;
        
        return success ? getArticleCount(articleId) : -1;
    }
    
    @Override
    public int getArticleCount(int articleId) {
        SelectResult article = DB.select("Article", new Where("ID", "=", articleId));
        
        return article.isEmpty() ? 0 : (int) article.get("Count");
    }
    
    @Override
    public List<Integer> getArticles(int shopId) {
        SelectResult articles = DB.select("Article", new Where("ShopID", "=", shopId));
        
        ArrayList<Integer> articleIds = new ArrayList<>();
        
        for (HashMap<String, Object> article : articles) {
            articleIds.add((int) article.get("ID"));
        }
        
        return articleIds;
    }
    
    @Override
    public int getDiscount(int shopId) {
        SelectResult shop = DB.select("Shop", new Where("ID", "=", shopId));
        
        return shop.isEmpty() ? -1 : (int) shop.get("Discount");
    }
    
}