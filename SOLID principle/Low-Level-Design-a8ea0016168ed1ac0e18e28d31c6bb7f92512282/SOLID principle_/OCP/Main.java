// This is example of Open-Close Principle which O from Solid principle
interface TaxCalculator{
    double amountAfterTax(double amount);
}

class IndianTax implements TaxCalculator{
    public double amountAfterTax(double amount){
        return (amount + (0.18 * amount));
    }
} 
class UsTax implements TaxCalculator{
    public double amountAfterTax(double amount){
        return (amount + (0.10 * amount));
    }
} 

class InvoiceService{
    public void calculate(){
        TaxCalculator calculator =  new UsTax();
        calculator.amountAfterTax(100);
    }
}
public class Main {

    
}