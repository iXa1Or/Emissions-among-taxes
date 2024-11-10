package pack;

import java.util.Vector;

public class Item {
    private String INN;
    private int Revenue;
    private Vector<String> TaxNames;
    private Vector<Integer> TaxSums;

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

    public Vector<String> getTaxNames() {
        return TaxNames;
    }

    public void addTaxName(String TaxName) {
        if (TaxNames == null) this.TaxNames = new Vector<>();
        this.TaxNames.add(TaxName);
    }

    public Vector<Integer> getTaxSums() {
        return TaxSums;
    }

    public void addTaxSum(int TaxSum) {
        if (TaxSums == null) this.TaxSums = new Vector<>();
        this.TaxSums.add(TaxSum);
    }
}
