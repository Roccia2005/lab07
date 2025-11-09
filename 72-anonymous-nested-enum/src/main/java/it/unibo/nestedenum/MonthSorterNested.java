package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    public enum Month{
        JANUARY("January",31),
        FEBRUARY("February",28),
        MARCH("March",31),
        APRIL("April",30),
        MAY("May",31),
        JUNE("June",30),
        JULY("July",31),
        AUGUST("August",31),
        SEPTEMBER("September",30),
        OCTOBER("October",31),
        NOVEMBER("November",30),
        DECEMBER("December",31);

        private final int days;
        private final String month;

        Month(final String month, final int days) {
            this.month = month;
            this.days = days;
        }

        public String getMonth(){
            return this.month;
        }

        static Month fromString(final String month){
            Month monthfromString = null;
            String upperCaseMonth = month.toUpperCase();
            for (final Month i : Month.values()) {
                if (i.getMonth().toUpperCase().startsWith(upperCaseMonth)){
                    if (monthfromString != null){
                        throw new IllegalArgumentException("Exist some month that stats with " + upperCaseMonth);
                    }else{
                        monthfromString = i;
                    }
                }
            }
            if (monthfromString == null){
                throw new IllegalArgumentException("The month name does not exist");
            }
            return monthfromString;
        }
        
    }

    private final class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            final Month month1 = Month.fromString(o1);
            final Month month2 = Month.fromString(o2);

            return Integer.compare(month1.ordinal(), month2.ordinal());
        }

    }
    
    private class SortByDate implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            
            final Month month1 = Month.fromString(o1);
            final Month month2 = Month.fromString(o2);
        
            return Integer.compare(month1.days, month2.days);
        }

    }
}
