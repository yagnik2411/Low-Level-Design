// This is example of Dependecy Invertion Principle which I from Solid principle
 interface  Recommendation {
    public void recommend();
}

class TradingRecommendation implements Recommendation{
    public void recommend(){

    }
}
class GenreRecommendation implements Recommendation{
    public void recommend(){
        
    }
}
class RecentRecommendation implements Recommendation{
    public void recommend(){
        
    }
}

class RecommendationAlgorithm {
    private Recommendation recommendation;

    RecommendationAlgorithm(Recommendation recommendation){
        this.recommendation=recommendation;
    }

    public  void recommend(){
        recommendation.recommend();
    }
}
public class Main {
    public static void main(String[] args) {
        RecommendationAlgorithm recommendationAlgorithm = new RecommendationAlgorithm(new TradingRecommendation());
        recommendationAlgorithm.recommend();
    }
}
