package rs.etf.sab.student;

import java.util.List;
import rs.etf.sab.operations.ShopOperations;
import rs.etf.sab.student.utils.*;


class ShopOperationsImpl implements ShopOperations {
    
    @Override
    public int createShop(String name, String cityName) {
        return DB.insert("Shop", new Column() {{
            put("Name", name);
            put("CityID", UtilityOperations.getCityByName(cityName));
        }});
    }
    
    @Override
    public int setCity(int shopId, String cityName) {
        return DB.update("Shop", new Column() {{
            put("CityID", UtilityOperations.getCityByName(cityName));
        }}, new Where("ID", "=", shopId));
    }
    
    @Override
    public int getCity(int shopId) {
        return (int) DB.select("Shop", new Where("Id", "=", shopId)).get("CityID");
    }
    
    @Override
    public int setDiscount(int shopId, int discountPercentage) {
        return DB.update("Shop", new Column() {{
            put("Discount", discountPercentage);
        }}, new Where("ID", "=", shopId));
    }
    
    @Override
    public int increaseArticleCount(int articleId, int increment) {
        boolean success = DB.update("Article", new Column() {{
            put("Count", "Count + " + increment);
        }}, new Where("ID", "=", articleId)) != -1;
        
        return success ? getArticleCount(articleId) : -1;
    }
    
    @Override
    public int getArticleCount(int articleId) {
        Result article = DB.select("Article", new Where("ID", "=", articleId));
        
        return article.isEmpty() ? 0 : (int) article.get("Count");
    }
    
    @Override
    public List<Integer> getArticles(int shopId) {
        Result articles = DB.select("Article", new Where("ShopID", "=", shopId));
        
        return (List<Integer>) articles.getAll("ID");
    }
    
    @Override
    public int getDiscount(int shopId) {
        Result shop = DB.select("Shop", new Where("ID", "=", shopId));
        
        return shop.isEmpty() ? -1 : (int) shop.get("Discount");
    }
    
}