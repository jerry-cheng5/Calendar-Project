/*  Jerry Cheng
    Java at a Glance
    Calendar Assignment
*/

//importing calendar and scanner library
import java.util.Calendar;
import java.util.GregorianCalendar;//online sources say gregorian is more accurate, so I used gregorian
import java.util.Scanner;//to get user input

public class CalendarFinal {
    public static void main(String[] args) {//main method
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter a year.");//prompting user for input
        String temp = userInput.next();//storing user input
        int year = Calendar.getInstance().get(Calendar.YEAR);// year from system
            try {
                year = Integer.parseInt(temp);
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect number: " + temp);
                System.out.println("Default Year: " + year);
            }//exception handling
        printCal(year);//calling the printCal function with the user input as year
    }

    public static boolean checkLeapYear(int year){// checking if there is a leap year, to determine if I need to add a day to febuary or not
        boolean leapYear = false;//set as false because majority of time it is false
        if(year % 400 == 0) {leapYear = true;}// if divisible by 400: 1200, 1600, 2000, etc it is a leap year
        else if(year % 100 == 0) {leapYear = false;}// if divisible by 100, and not 400 it is not a leap year, according to the gregorian calendar
        else if(year % 4 == 0) {leapYear = true;}// divisible by 4 and not 100 = leap year
        else {leapYear = false;}// not divisible by 400 or 4, not a leap year
        return leapYear;
    }

    public static int dayOfWeek(int year, int month) {//finding the day of the wek for the first date of each month
        Calendar cal = new GregorianCalendar(year, month, 1); //creating a new gregorian calendar, with inputed year and month, but constant date (1)
        int firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK) - 1; // using the calendar library, get the day of the week minus 1,
                                                                // because we want it to be 0-6 and not 1-7
        return firstDayOfMonth;
    }

    public static int[][] createQuarter(int year, int quarter) {//creating a quarter of the calendar (jan-mar, apr-jun, etc)
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};// days in each month
        int[][] quarterArr = new int[6][21];// 2d array, first array(rows) is 6 because that is the max lines a month can occupy
                                            // second array(columns) is 21, because in each quarter there are 3 months each wtih 7 days in a week 3*7=21
        if(checkLeapYear(year) == true) { //check for leap year and add to febuary if needed
            daysInMonth[1]+=1;
        }
        for(int i=0; i<3; i++) {//for each month, so do it 3 times, as there are 3 months in a quarter
            int firstDay = dayOfWeek(year, (quarter*3 + i)); //get day of week for first date in the month, quarter*3 + i is the month
            for(int j=0; j<daysInMonth[quarter*3 + i]; j++) {//assigning all days in a month into the 2d array
                quarterArr[(int)((firstDay+j)/7)][((firstDay + j)%7) + 7*i] = j+1; // for rows, adding j(day counter) to the first day, then divided by 7, 
                                                                                 //and rounded down to find the week that it is in
                                                                                 // For columns, j(day counter) is added to the first day, then using modulo 7, 
                                                                                 //the remainder is found. Adding 7*i(month counter), makes the first week for all months in one row,
                                                                                 // second week in the second row, etc
            }
        }
        return quarterArr;
    }

    public static void printCal(int year) {//printing the calendar
        //Set variables
        String[] monthTitle = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] dayOfWeekTitle = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        System.out.println("");//spacing
        for(int i=0; i<(monthTitle.length/3); i++) {// a for loop for each quarter
            for(int j=0; j<3; j++) {//a for loop for each of the month titles in the quarter
                System.out.printf("%-30s", monthTitle[j+i*3] + " " + year); // j+i*3: the month counter plus the quarter counter*3
            }
            System.out.println("\n"); //Spacing and new line for week titles

            for(int j=0; j<3; j++) {//for every month in a quarter
                for(int k=0; k<dayOfWeekTitle.length; k++) {//day of week titles
                    System.out.printf("%-4s", dayOfWeekTitle[k]);
                }
                System.out.print("  "); //spacing between months (horizontal)
            }
            System.out.println("");//new line for the dates

            int[][] tempQuarter = createQuarter(year, i); //creating a quarter array for the dates, using i as the quarter, as it increases every itteration
            for(int j=0; j<6; j++) {//rows (weeks)
                for(int k=0; k<21; k++) {//columns (days in week)
                    if(tempQuarter[j][k] != 0) {// check for no number/0
                        System.out.printf("%-4d", tempQuarter[j][k]);
                    }
                    else {// If there is no number in the called spot in the array, then just print 4 spaces to maintain the formating
                        System.out.print("    ");
                    }
                    
                    if(k == 6 || k == 13) {//spacing between each month horizontally, because index 6 is the 7th item, and index 13 is the 14th item
                        System.out.print("  ");
                    }
                }
                System.out.println(""); //new line for each new week
            }
            System.out.println(""); //new line for each quarter
        }
    }
}