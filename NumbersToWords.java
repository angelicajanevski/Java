public class NumbersToWords {

    public static void main(String[] args) {
        System.out.println(numberToWords(932894));
    }

    public static String numberToWords(int number) {
        int originalNumber = number;
        int originalReverse = reverse(number);

        if (number < 0) {
            return "Invalid Value";
        } else {
            int lastDigit = 0;
            String stat = "";
            int renumber = reverse(number);


            while (renumber > 0) {
                lastDigit = renumber % 10;
                switch (lastDigit) {
                    case 0:
                        stat += "Zero ";
                        break;
                    case 1:
                        stat += "One ";
                        break;
                    case 2:
                        stat += "Two ";
                        break;
                    case 3:
                        stat += "Three ";
                        break;
                    case 4:
                        stat += "Four ";
                        break;
                    case 5:
                        stat += "Five ";
                        break;
                    case 6:
                        stat += "Six ";
                        break;
                    case 7:
                        stat += "Seven ";
                        break;
                    case 8:
                        stat += "Eight ";
                        break;
                    case 9:
                        stat += "Nine ";
                        break;

                }

                renumber /= 10;


            }

            if (getDigitCount(originalReverse) != getDigitCount(originalNumber)) {
                int diff = getDigitCount(originalNumber) - getDigitCount(originalReverse);

                for (int i = 0; i < diff; i++) {
                    stat += "Zero ";

                }
            }

            return stat;
        }


    }

    public static int reverse(int par) {
        int reverse = 0;
        int lastD = 0;

        while (par != 0) {
            lastD = par % 10;
            reverse *= 10;
            reverse += lastD;
            par /= 10;

        }
        return reverse;
    }

    public static int getDigitCount(int num) {
        if (num < 0) {
            return -1;
        } else {
            int digitCount = 0;
            int parr = num;
            while (num > 0) {
                parr = num % 10;
                digitCount++;
                num /= 10;
            }
            return digitCount;
        }
    }
}

