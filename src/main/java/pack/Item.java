package pack;

import java.util.Vector;

public class Item {
    private String INN;
    private String OKPO;
    private String OKATO_Fact;
    private String OKTMO;
    private String Company_Type;
    private String OFSN_Type;
    private String ERMSP_Type;
    private String Msp_Type;
    private String OKVED_Osn;
    private String OKVED_ne_Osn;
    private String Fact_OKVED_Osn;
    private String Fact_OKVED_Neosn;
    private int SCHR;
    private int Revenue;
    private Vector<String> TaxNames;
    private Vector<Integer> TaxSums;

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getOKPO() {
        return OKPO;
    }

    public void setOKPO(String OKPO) {
        this.OKPO = OKPO;
    }

    public String getOKATO_Fact() {
        return OKATO_Fact;
    }

    public void setOKATO_Fact(String OKATO_Fact) {
        this.OKATO_Fact = OKATO_Fact;
    }

    public String getOKTMO() {
        return OKTMO;
    }

    public void setOKTMO(String OKTMO) {
        this.OKTMO = OKTMO;
    }

    public String getCompany_Type() {
        return Company_Type;
    }

    public void setCompany_Type(String Company_Type) {
        this.Company_Type = Company_Type;
    }

    public String getOFSN_Type() {
        return OFSN_Type;
    }

    public void setOFSN_Type(String OFSN_Type) {
        this.OFSN_Type = OFSN_Type;
    }

    public String getERMSP_Type() {
        return ERMSP_Type;
    }

    public void setERMSP_Type(String ERMSP_Type) {
        this.ERMSP_Type = ERMSP_Type;
    }

    public String getMsp_Type() {
        return Msp_Type;
    }

    public void setMsp_Type(String Msp_Type) {
        this.Msp_Type = Msp_Type;
    }

    public String getOKVED_Osn() {
        return OKVED_Osn;
    }

    public void setOKVED_Osn(String OKVED_Osn) {
        this.OKVED_Osn = OKVED_Osn;
    }

    public String getOKVED_ne_Osn() {
        return OKVED_ne_Osn;
    }

    public void setOKVED_ne_Osn(String OKVED_ne_Osn) {
        this.OKVED_ne_Osn = OKVED_ne_Osn;
    }

    public String getFact_OKVED_Osn() {
        return Fact_OKVED_Osn;
    }

    public void setFact_OKVED_Osn(String Fact_OKVED_Osn) {
        this.Fact_OKVED_Osn = Fact_OKVED_Osn;
    }

    public String getFact_OKVED_Neosn() {
        return Fact_OKVED_Neosn;
    }

    public void setFact_OKVED_Neosn(String Fact_OKVED_Neosn) {
        this.Fact_OKVED_Neosn = Fact_OKVED_Neosn;
    }

    public int getSCHR() {
        return SCHR;
    }

    public void setSCHR(int SCHR) {
        this.SCHR = SCHR;
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

    public void setTaxNames(Vector<String> TaxNames) {
        this.TaxNames = TaxNames;
    }

    public void addTaxName(String TaxName) {
        if (TaxNames == null) this.TaxNames = new Vector<>();
        this.TaxNames.add(TaxName);
    }

    public Vector<Integer> getTaxSums() {
        return TaxSums;
    }

    public void setTaxSums(Vector<Integer> TaxSums) {
        this.TaxSums = TaxSums;
    }

    public void addTaxSum(int TaxSum) {
        if (TaxSums == null) this.TaxSums = new Vector<>();
        this.TaxSums.add(TaxSum);
    }
}
