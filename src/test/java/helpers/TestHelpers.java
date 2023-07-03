package helpers;

import com.example.loanpayback.domain.PaybackPeriod;
import com.example.loanpayback.request.LoanPaybackRequest;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.loanpayback.domain.LoanType.MORTGAGE;
import static java.math.BigDecimal.valueOf;

public class TestHelpers {

    public static LoanPaybackRequest createLoanPaybackRequest() {
        LoanPaybackRequest request = new LoanPaybackRequest();
        request.setAmount(valueOf(20000));
        request.setPaybackTimeYears(5);
        request.setLoanType(MORTGAGE);
        return request;
    }

    public static PaybackPeriod createPaybackPeriod() {
        return new PaybackPeriod(
                1,
                valueOf(10),
                valueOf(20),
                valueOf(30),
                valueOf(40),
                valueOf(50));
    }

    public static List<PaybackPeriod> getCalculationResult() {
        List<PaybackPeriod> expectedList = new ArrayList<>();
        expectedList.add(new PaybackPeriod(1, valueOf(1698.43), valueOf(58.33), valueOf(1640.10).setScale(2, RoundingMode.HALF_UP), valueOf(18359.90).setScale(2, RoundingMode.HALF_UP), valueOf(58.33)));
        expectedList.add(new PaybackPeriod(2, valueOf(1698.43), valueOf(53.55), valueOf(1644.88), valueOf(16715.02), valueOf(111.88)));
        expectedList.add(new PaybackPeriod(3, valueOf(1698.43), valueOf(48.75), valueOf(1649.68), valueOf(15065.34), valueOf(160.63)));
        expectedList.add(new PaybackPeriod(4, valueOf(1698.43), valueOf(43.94), valueOf(1654.49), valueOf(13410.85), valueOf(204.57)));
        expectedList.add(new PaybackPeriod(5, valueOf(1698.43), valueOf(39.11), valueOf(1659.32), valueOf(11751.53), valueOf(243.68)));
        expectedList.add(new PaybackPeriod(6, valueOf(1698.43), valueOf(34.28), valueOf(1664.15), valueOf(10087.38), valueOf(277.96)));
        expectedList.add(new PaybackPeriod(7, valueOf(1698.43), valueOf(29.42), valueOf(1669.01), valueOf(8418.37), valueOf(307.38)));
        expectedList.add(new PaybackPeriod(8, valueOf(1698.43), valueOf(24.55), valueOf(1673.88), valueOf(6744.49), valueOf(331.93)));
        expectedList.add(new PaybackPeriod(9, valueOf(1698.43), valueOf(19.67), valueOf(1678.76), valueOf(5065.73), valueOf(351.60).setScale(2, RoundingMode.HALF_UP)));
        expectedList.add(new PaybackPeriod(10, valueOf(1698.43), valueOf(14.78), valueOf(1683.65), valueOf(3382.08), valueOf(366.38)));
        expectedList.add(new PaybackPeriod(11, valueOf(1698.43), valueOf(9.86), valueOf(1688.57), valueOf(1693.51), valueOf(376.24)));
        expectedList.add(new PaybackPeriod(12, valueOf(1698.43), valueOf(4.94), valueOf(1693.49), valueOf(0.02), valueOf(381.18)));

        return expectedList;
    }
}
