import java.util.*;

interface Observer {
    void update(String stockName, double price);
}

interface Stock {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double stockPrice;

    public void setStockData(String name, double price) {
        this.stockName = name;
        this.stockPrice = price;
        notifyObservers();
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(stockName, stockPrice);
        }
    }
}

class MobileApp implements Observer {
    public void update(String stockName, double price) {
        System.out.println("Mobile App: " + stockName + " price updated to ₹" + price);
    }
}

class WebApp implements Observer {
    public void update(String stockName, double price) {
        System.out.println("Web App: " + stockName + " price updated to ₹" + price);
    }
}

public class ObserverPatternExample {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();

        Observer mobile = new MobileApp();
        Observer web = new WebApp();

        stockMarket.registerObserver(mobile);
        stockMarket.registerObserver(web);

        stockMarket.setStockData("TCS", 3800.50);
        System.out.println("---");
        stockMarket.setStockData("Infosys", 1525.75);

        stockMarket.removeObserver(web);
        System.out.println("---");
        stockMarket.setStockData("Wipro", 415.20);
    }
}
