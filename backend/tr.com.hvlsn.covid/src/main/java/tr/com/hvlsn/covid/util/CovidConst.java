package tr.com.hvlsn.covid.util;

/**
 * Created by ogyilmaz on 4/23/2021 3:58 PM
 */
public class CovidConst {
    private CovidConst() {
        throw new IllegalStateException("Utility class");
    }

    public final static String COVID_CASE_KEYWORD = "VAKA";
    public final static String COVID_DEATH_KEYWORD = "VEFAT";
    public final static String COVID_DISCHARGE_KEYWORD = "TABURCU";
}
