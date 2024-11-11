package pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    private String INN;
    private int Revenue;
    private List<String> TaxNames;
    private List<Integer> TaxSums;

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public int getRevenue() {
        return Revenue;
    }

    public void setRevenue(int Revenue) {
        this.Revenue = Revenue;
    }

    public List<String> getTaxNames() {
        return TaxNames;
    }

    public void setTaxNames(List<String> taxNames) {
        this.TaxNames = taxNames;
    }

    public void addTaxName(String TaxName) {
        if (TaxNames == null) this.TaxNames = new ArrayList<>();
        this.TaxNames.add(TaxName);
    }

    public List<Integer> getTaxSums() {
        return TaxSums;
    }

    public void setTaxSums(List<Integer> taxSums) {
        this.TaxSums = taxSums;
    }

    public void addTaxSum(int TaxSum) {
        if (TaxSums == null) this.TaxSums = new ArrayList<>();
        this.TaxSums.add(TaxSum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Revenue == item.Revenue &&
                Objects.equals(INN, item.INN) &&
                Objects.equals(TaxNames, item.TaxNames) &&
                Objects.equals(TaxSums, item.TaxSums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(INN, Revenue, TaxNames, TaxSums);
    }

    public void print() {
        System.out.println(
                "INN: " + this.INN
                + "\nRevenue: " + this.Revenue
                + "\nTaxNames: " + this.TaxNames
                + "\nTaxSums: " + this.TaxSums
        );
    }
}
