//Fill in your name and student number
//Name: Angelica Janevski
//Student Number: 260801180

// do NOT touch these import statements

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CountdownDays {

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    public static void main(String args[]) {
        //input the date from command line
        displayCountdown(args[0]);


    }

    public static String getSubstring(String s, int i, int j) {
        String x = "";
        if (i > j) {
            throw new IllegalArgumentException("Parameter i cannot be greater than j.");
        } else {
            for (int n = i; n <= j; n++) {
                //start at i, stop at j, build substring
                x += s.charAt(n);
            }

            return x;

        }
    }

    //get the day, month, year from input string
    public static int getDay(String s) {
        return Integer.parseInt(getSubstring(s, 0, 1));

    }

    public static int getMonth(String s) {
        return Integer.parseInt(getSubstring(s, 3, 4));

    }

    public static int getYear(String s) {
        return Integer.parseInt(getSubstring(s, 6, 9));

    }

    //check if the year is leap
    public static boolean isLeapYear(int n) {
        if ((n % 100 == 0 && n % 400 == 0) || (n % 4 == 0 && n % 100 != 0)) {
            //it's a century and divisible by 400 or it's not a century and divisible by 4
            return true;

        }
        return false;

    }

    public static int getDaysInAMonth(int m, int y) {
        int days = 0;
        //depending on month #, days count gets updated
        switch (m) {
            case 1:
                days = 31;
                break;
            case 2:
                if (isLeapYear(y)) {
                    days = 29;
                    break;
                } else {
                    days = 28;
                    break;
                }

            case 3:
                days = 31;
                break;
            case 4:
                days = 30;
                break;
            case 5:
                days = 31;
                break;
            case 6:
                days = 30;
                break;
            case 7:
                days = 31;
                break;
            case 8:
                days = 31;
                break;
            case 9:
                days = 30;
                break;
            case 10:
                days = 31;
                break;
            case 11:
                days = 30;
                break;
            case 12:
                days = 31;
                break;

        }

        return days;
    }

    //my custom method: get the number of days starting from january (of the current year) up until and excluding current month
    public static int getDaysUpToMonth(int m, int y) {
        int daysCount = 0;

        //if the month is january, we will just call getDay() to count the days
        if (m == 1) {
            return 0;
        } else {
            //we will stop the loop before it reaches the current month (i<m)
            // since getDay() will count days of current month
            for (int i = 1; i < m; i++) {
                //loop from 1 (Jan) to m-1 months and add days
                daysCount = daysCount + getDaysInAMonth(i, y);
            }
            return daysCount;
        }

    }


    public static int getDaysUpToYear(int y) {
        //how many leap years in date?
        int leapYears = 0;

        //we want to consider all the years except the current year (i<y)
        // since getDaysUpToMonth() will count days in current year
        for (int i = 1; i < y; i++) {
            if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0 && i % 100 == 0)) {
                leapYears++;
            }

        }
        int commonYears = y - leapYears;
        //now with # of common and leap years, multiply by according number of days in each
        return (commonYears * 365 + leapYears * 366);

    }

    public static boolean dueDateHasPassed(String current, String due) {

        if (getYear(current) > getYear(due)) {
            //if the current year is greater than the due date, due date has passed
            return true;
        } else if (getYear(current) == getYear(due) && getMonth(current) > getMonth(due)) {
            //if the year is the same, but the current month is larger, due date has passed
            return true;
        } else if (getYear(current) == getYear(due) && getMonth(current) == getMonth(due) && getDay(current) >= getDay(due)) {
            //the year is the same, the month is the same, but the current day is equal to or greater than the due date, the due date has passed
            return true;
        }

        return false;

    }

    public static int countDaysLeft(String current, String due) {
        if (dueDateHasPassed(current, due)) {
            return 0;
        }
        /*the due date has not passed.
        (1) call getdDay to count days in current month
        (2) call getDaysUpToMonth to count days of the current year up to but excluding the current month
        (3) call getDaysUpTpYear to count days in all the years that have passed, excluding current year*/
        int daysCurrent = getDay(current) + getDaysUpToMonth(getMonth(current), getYear(current)) + getDaysUpToYear(getYear(current));
        int daysDue = getDay(due) + getDaysUpToMonth(getMonth(due), getYear(due)) + getDaysUpToYear(getYear(due));
        //subtract todays current day count from the due date day count, this is how many days between deadline
        return daysDue - daysCurrent;

    }

    public static void displayCountdown(String due) {
        if (!dueDateHasPassed(getCurrentDate(), due)) {
            //the due date has not passed, print days left by calling countDaysLeft
            System.out.println("Today is: " + getCurrentDate());
            System.out.println("Due date: " + due);
            System.out.println("You have " + countDaysLeft(getCurrentDate(), due) + " days left. There's still time!");
        } else {
            //due date passed sucker
            System.out.println("Today is: " + getCurrentDate());
            System.out.println("Due date: " + due);
            System.out.println("The due date has passed. :/");

        }
    }

}