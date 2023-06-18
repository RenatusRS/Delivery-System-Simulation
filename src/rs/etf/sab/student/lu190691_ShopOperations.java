package rs.etf.sab.student;

import rs.etf.sab.student.implementations.ShopOperationsImpl;
import rs.etf.sab.student.utils.Logger;

import java.util.List;


class lu190691_ShopOperations extends ShopOperationsImpl {
    
    @Override
    public int createShop(String name, String cityName) {
        Logger.functionStart("ShopOperations createShop(name: " + name + ", cityName: " + cityName + ")");
        
        int result = super.createShop(name, cityName);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int setCity(int shopId, String cityName) {
        Logger.functionStart("ShopOperations setCity(shopId: " + shopId + ", cityName: " + cityName + ")");
        
        int result = super.setCity(shopId, cityName);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getCity(int shopId) {
        Logger.functionStart("ShopOperations getCity(shopId: " + shopId + ")");
        
        int result = super.getCity(shopId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int setDiscount(int shopId, int discountPercentage) {
        Logger.functionStart("ShopOperations setDiscount(shopId: " + shopId + ", discountPercentage: " + discountPercentage + ")");
        
        int result = super.setDiscount(shopId, discountPercentage);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int increaseArticleCount(int articleId, int increment) {
        Logger.functionStart("ShopOperations increaseArticleCount(articleId: " + articleId + ", increment: " + increment + ")");
        
        int result = super.increaseArticleCount(articleId, increment);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getArticleCount(int articleId) {
        Logger.functionStart("ShopOperations getArticleCount(articleId: " + articleId + ")");
        
        int result = super.getArticleCount(articleId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public List<Integer> getArticles(int shopId) {
        Logger.functionStart("ShopOperations getArticles(shopId: " + shopId + ")");
        
        List<Integer> result = super.getArticles(shopId);
    
        Logger.functionEnd(result);
        
        return result;
    }
    
    @Override
    public int getDiscount(int shopId) {
        Logger.functionStart("ShopOperations getDiscount(shopId: " + shopId + ")");
        
        int result = super.getDiscount(shopId);
    
        Logger.functionEnd(result);
        
        return result;
    }
}