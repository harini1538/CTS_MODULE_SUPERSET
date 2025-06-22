public class FinancialForecast {

    // Recursive method to calculate future financial value
    public static double forecastValue(double currentValue, double growthRate, int years) {
        // Base case: stop when years reach zero
        if (years == 0) {
            return currentValue;
        }
        // Recursive step: calculate next year's value
        return forecastValue(currentValue * (1 + growthRate), growthRate, years - 1);
    }

    public static void main(String[] args) {
        // Initial financial data
        double initialValue = 10000.0;   // Starting value (e.g., investment amount)
        double growthRate = 0.08;        // Annual growth rate (8%)
        int years = 5;                   // Forecast for 5 years

        // Calculate future value using recursion
        double futureValue = forecastValue(initialValue, growthRate, years);

        // Output result
        System.out.printf("Predicted value after %d years: %.2f\n", years, futureValue);
    }
}
