    import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Scanner scin = new Scanner(System.in);
      System.out.print("Enter your username: ");
      String uname = scin.nextLine();
      Scanner scan = new Scanner(System.in);
      System.out.print("Enter your password: ");
      String pwd = scan.nextLine();
      if (uname.equals("brown") && pwd.equals("db123")) {
         int option = 0;

         while(true) {
            while(true) {
               label154:
               while(option != 12) {
                  System.out.println("\nDisplaying menu: \n");
                  System.out.println("(1) Retrieve ISBN, title, genre name, publication date, name of the publisher, edition number and description of each book.\n");
                  System.out.println("(2) Retrieve for a book first, middle and last names of its authors.\n");
                  System.out.println("(3) Retrieve ISBN, title and barcode of every book copy.\n");
                  System.out.println("(4) Retrieve card number, first, middle and last name of every member.\n");
                  System.out.println("(5) Retrieve info (ISBN, title, barcode, date borrowed and number of renewals) of every loan that was not finalized for a chosen member\n");
                  System.out.println("(6) Register in the system the return of a book a chosen member borrowed.\n");
                  System.out.println("(7) Borrow a book copy to a chosen member.\n");
                  System.out.println("(8) Renew a loan of a book copy to a chosen member.\n");
                  System.out.println("(9) Retrieve how much money a chosen member owes to the library.\n");
                  System.out.println("(10) Retrieve for a member ISBN, title, barcode, date borrowed, date returned and fee for every book he owes money for to the library\n");
                  System.out.println("(11) Register in the system, for a member, a payment for a loan of a book copy that was overdue.\n");
                  System.out.println("(12) quit\n");
                  System.out.println("\nPlease enter the number of the action you would like to perform: ");
                  Scanner s = new Scanner(System.in);

                  while(!s.hasNextInt()) {
                     s.next();
                     System.out.print("Please enter an integer: ");
                  }

                  option = s.nextInt();
                  if (option > 0 && option < 13) {
                     PrintStream var10000;
                     int card;
                     String var10001;
                     int updated;
                     int update;
                     int affectedRows;
                     Connection connection;
                     Statement statement;
                     ResultSet resultSet;
                     switch(option) {
                     case 1:
                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           Statement statement = connection.createStatement();
                           ResultSet resultSet = statement.executeQuery("select ISBN, title, name, date_published, edition, description from book natural join genre");

                           while(true) {
                              if (!resultSet.next()) {
                                 continue label154;
                              }

                              var10000 = System.out;
                              var10001 = resultSet.getString("ISBN");
                              var10000.println(var10001 + "   " + resultSet.getString("title") + "   " + resultSet.getString("name") + "   " + resultSet.getString("date_published") + "   " + resultSet.getString("edition") + "   " + resultSet.getString("description"));
                           }
                        } catch (SQLException var41) {
                           System.out.println("The information requested could not be acquired");
                           break;
                        }
                     case 2:
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Please input the name of the book you would like to search: ");
                        String name = sc.nextLine();

                        try {
                           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String query = "select first_name, middle_name, last_name from book natural join book_author natural join author where book.title = ?";
                           PreparedStatement pstmt = connection.prepareStatement(query);
                           pstmt.setString(1, name);
                           ResultSet resultSet = pstmt.executeQuery();

                           while(true) {
                              if (!resultSet.next()) {
                                 continue label154;
                              }

                              var10000 = System.out;
                              var10001 = resultSet.getString("first_name");
                              var10000.println(var10001 + "  " + resultSet.getString("middle_name") + "  " + resultSet.getString("last_name"));
                           }
                        } catch (SQLException var40) {
                           System.out.println("The book requested could not be found in the database, sorry!");
                           break;
                        }
                     case 3:
                        try {
                           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           statement = connection.createStatement();
                           resultSet = statement.executeQuery("select ISBN, title, barcode from copy natural join book");

                           while(true) {
                              if (!resultSet.next()) {
                                 continue label154;
                              }

                              var10000 = System.out;
                              var10001 = resultSet.getString("ISBN");
                              var10000.println(var10001 + "   " + resultSet.getString("title") + "   " + resultSet.getString("barcode"));
                           }
                        } catch (SQLException var39) {
                           System.out.println("The information requested could not be acquired");
                           break;
                        }
                     case 4:
                        try {
                           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           statement = connection.createStatement();
                           resultSet = statement.executeQuery("select card_no, first_name, middle_name, last_name from member");

                           while(true) {
                              if (!resultSet.next()) {
                                 continue label154;
                              }

                              var10000 = System.out;
                              var10001 = resultSet.getString("card_no");
                              var10000.println(var10001 + "   " + resultSet.getString("first_name") + "   " + resultSet.getString("middle_name") + "   " + resultSet.getString("last_name"));
                           }
                        } catch (SQLException var38) {
                           System.out.println("The information requested could not be acquired");
                           break;
                        }
                     case 5:
                        System.out.println("Please enter the card number: ");
                        Scanner p = new Scanner(System.in);

                        while(!p.hasNextInt()) {
                           p.next();
                           System.out.print("Please enter an integer: ");
                        }

                        int cnum = p.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String query1 = "select ISBN, title, barcode, date_borrowed, renewals_no from borrow natural join copy natural join book where date_returned is null and card_no = ?";
                           PreparedStatement pstmt = connection.prepareStatement(query1);
                           pstmt.setInt(1, cnum);
                           ResultSet resultSet = pstmt.executeQuery();

                           while(resultSet.next()) {
                              var10000 = System.out;
                              var10001 = resultSet.getString("ISBN");
                              var10000.println(var10001 + "   " + resultSet.getString("title") + "   " + resultSet.getString("barcode") + "   " + resultSet.getString("date_borrowed") + "   " + resultSet.getString("renewals_no"));
                           }
                        } catch (SQLException var37) {
                           System.out.println("The information requested could not be acquired");
                        }
                        break;
                     case 6:
                        System.out.println("Please enter the card number: ");
                        Scanner x = new Scanner(System.in);

                        while(!x.hasNextInt()) {
                           x.next();
                           System.out.print("Please enter an integer: ");
                        }

                        int card_no = x.nextInt();
                        System.out.println("Please enter the barcode: ");
                        Scanner y = new Scanner(System.in);

                        while(!y.hasNextInt()) {
                           y.next();
                           System.out.print("Please enter an integer: ");
                        }

                        int barcode = y.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String sql = "UPDATE borrow set date_returned = now() where card_no = ? AND barcode = ? and date_returned is null";
                           PreparedStatement pstmt = connection.prepareStatement(sql);
                           pstmt.setInt(1, card_no);
                           pstmt.setInt(2, barcode);
                           updated = pstmt.executeUpdate();
                           if (updated == 0) {
                              System.out.println("Could not be inserted into the database! Please review information entered.");
                           } else {
                              System.out.println("Entry successful!");
                           }
                        } catch (SQLException var35) {
                           System.out.println("Could not be inserted into the database! Please review information entered.");
                        }
                        break;
                     case 7:
                        System.out.println("Please enter the card number: ");
                        Scanner a = new Scanner(System.in);

                        while(!a.hasNextInt()) {
                           a.next();
                           System.out.print("Please enter an integer: ");
                        }

                        card = a.nextInt();
                        System.out.println("Please enter the barcode: ");
                        Scanner b = new Scanner(System.in);

                        while(!b.hasNextInt()) {
                           b.next();
                           System.out.print("Please enter an integer: ");
                        }

                        updated = b.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String sql = "INSERT into BORROW(card_no, barcode, date_borrowed, date_returned, renewals_no, paid)values(?,?,now(),null,0,null)";
                           PreparedStatement pstmt = connection.prepareStatement(sql);
                           pstmt.setInt(1, card);
                           pstmt.setInt(2, updated);
                           update = pstmt.executeUpdate();
                           if (update == 0) {
                              System.out.println("Could not be inserted into the database! Please review information entered.");
                           } else {
                              System.out.println("Entry successful!");
                           }
                        } catch (SQLException var34) {
                           System.out.println("Could not be inserted into the database! Please review information entered.");
                        }
                        break;
                     case 8:
                        System.out.println("Please enter the card number: ");
                        Scanner w = new Scanner(System.in);

                        while(!w.hasNextInt()) {
                           w.next();
                           System.out.print("Please enter an integer: ");
                        }

                        card = w.nextInt();
                        System.out.println("Please enter the barcode: ");
                        Scanner bc = new Scanner(System.in);

                        while(!bc.hasNextInt()) {
                           bc.next();
                           System.out.print("Please enter an integer: ");
                        }

                        updated = bc.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String sql = "UPDATE borrow set renewals_no = (renewals_no + 1) where card_no = ? AND barcode = ? and date_returned is null";
                           PreparedStatement pstmt = connection.prepareStatement(sql);
                           pstmt.setInt(1, card);
                           pstmt.setInt(2, updated);
                           affectedRows = pstmt.executeUpdate();
                           if (affectedRows == 0) {
                              System.out.println("Could not be inserted into the database! Please review information entered.");
                           } else {
                              System.out.println("Update Successful!.");
                           }
                        } catch (SQLException var33) {
                           System.out.println("Could not be inserted into the database! Please review information entered.");
                        }
                        break;
                     case 9:
                        System.out.print("Please enter the member's card number: ");
                        Scanner m = new Scanner(System.in);

                        while(!m.hasNextInt()) {
                           m.next();
                           System.out.print("Please enter an integer: ");
                        }

                        update = m.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String query = "\nselect sum(money_owed) as total_sum from ((select sum((DATEDIFF(now(), date_borrowed) * 0.25)) as money_owed from member natural join borrow where adddate(date_borrowed, 14) < now() and renewals_no = 0 and date_returned is null and card_no = ?) UNION (select sum((DATEDIFF(now(), adddate(date_borrowed, 28)) * 0.25)) as money_owed from member natural join borrow where adddate(date_borrowed, 28) < now() and renewals_no = 1 and date_returned is null and card_no = ?) UNION (select sum((DATEDIFF(now(), adddate(date_borrowed, 42)) * 0.25)) as money_owed from member natural join borrow where adddate(date_borrowed, 42) < now() and renewals_no = 0 and date_returned is null and card_no = ?) UNION (select (DATEDIFF(date_returned, adddate(date_borrowed, 14)) * 0.25) as money_owed from member natural join borrow where adddate(date_borrowed, 14) < date_returned and renewals_no = 0 and card_no = ?) UNION (select (DATEDIFF(date_returned, adddate(date_borrowed, 28)) * 0.25) as money_owed from member natural join borrow where adddate(date_borrowed, 28) < date_returned and renewals_no = 1 and card_no = ?) UNION (select (DATEDIFF(date_returned, adddate(date_borrowed, 42)) * 0.25) as money_owed from member natural join borrow where adddate(date_borrowed, 42) < date_returned and renewals_no = 2 and card_no = ?)) t1";
                           PreparedStatement pstmt = connection.prepareStatement(query);
                           pstmt.setInt(1, update);
                           pstmt.setInt(2, update);
                           pstmt.setInt(3, update);
                           pstmt.setInt(4, update);
                           pstmt.setInt(5, update);
                           pstmt.setInt(6, update);
                           ResultSet resultSet = pstmt.executeQuery();
                           resultSet.next();
                           String sum = resultSet.getString("total_sum");
                           System.out.print("$" + sum);
                        } catch (SQLException var32) {
                           System.out.println("The data requested could not be found in the database, sorry!");
                        }
                        break;
                     case 10:
                        System.out.print("Please enter the member's card number: ");
                        Scanner v = new Scanner(System.in);

                        while(!v.hasNextInt()) {
                           v.next();
                           System.out.print("Please enter an integer: ");
                        }

                        affectedRows = v.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String query = "(select ISBN, title, barcode, date_borrowed, date_returned, (DATEDIFF(date_returned, adddate(date_borrowed, 14)) * 0.25) as money_owed from borrow natural join copy natural join book where adddate(date_borrowed, 14) < date_returned and renewals_no = 0 and card_no = ?) UNION (select ISBN, title, barcode, date_borrowed, date_returned, (DATEDIFF(date_returned, adddate(date_borrowed, 28)) * 0.25) as money_owed from borrow natural join copy natural join book where adddate(date_borrowed, 28) < date_returned and renewals_no = 1 and card_no = ?) UNION (select ISBN, title, barcode, date_borrowed, date_returned, (DATEDIFF(date_returned, adddate(date_borrowed, 42)) * 0.25) as money_owed from borrow natural join copy natural join book where adddate(date_borrowed, 42) < date_returned and renewals_no = 2 and card_no = ?) UNION (select ISBN, title, barcode, date_borrowed, date_returned, (DATEDIFF(now(), date_borrowed) * 0.25) as money_owed from borrow natural join copy natural join book where adddate(date_borrowed, 14) < now() and renewals_no = 0 and date_returned is null and card_no = ?) UNION (select ISBN, title, barcode, date_borrowed, date_returned, (DATEDIFF(now(), adddate(date_borrowed, 28)) * 0.25) as money_owed from borrow natural join copy natural join book where adddate(date_borrowed, 28) < now() and renewals_no = 1 and date_returned is null and card_no = ?) UNION (select ISBN, title, barcode, date_borrowed, date_returned, (DATEDIFF(now(), adddate(date_borrowed, 42)) * 0.25) as money_owed from borrow natural join copy natural join book where adddate(date_borrowed, 42) < now() and renewals_no = 0 and date_returned is null and card_no = ?)";
                           PreparedStatement pstmt = connection.prepareStatement(query);
                           pstmt.setInt(1, affectedRows);
                           pstmt.setInt(2, affectedRows);
                           pstmt.setInt(3, affectedRows);
                           pstmt.setInt(4, affectedRows);
                           pstmt.setInt(5, affectedRows);
                           pstmt.setInt(6, affectedRows);
                           ResultSet resultSet = pstmt.executeQuery();

                           while(true) {
                              if (!resultSet.next()) {
                                 continue label154;
                              }

                              var10000 = System.out;
                              var10001 = resultSet.getString("ISBN");
                              var10000.println(var10001 + "  " + resultSet.getString("title") + "  " + resultSet.getString("barcode") + "  " + resultSet.getString("date_borrowed") + "  " + resultSet.getString("date_returned") + "  " + resultSet.getString("money_owed"));
                           }
                        } catch (SQLException var36) {
                           System.out.println("The data requested could not be found in the database, sorry!");
                           break;
                        }
                     case 11:
                        System.out.println("Please enter the card number: ");
                        Scanner f = new Scanner(System.in);

                        while(!f.hasNextInt()) {
                           f.next();
                           System.out.print("Please enter an integer: ");
                        }

                        card = f.nextInt();
                        System.out.println("Please enter the barcode: ");
                        Scanner g = new Scanner(System.in);

                        while(!g.hasNextInt()) {
                           g.next();
                           System.out.print("Please enter an integer: ");
                        }

                        updated = g.nextInt();

                        try {
                           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "brown", "db123");
                           String sql = "UPDATE borrow set paid = 1 where card_no = ? and barcode = ? and date_returned is not null";
                           PreparedStatement pstmt = connection.prepareStatement(sql);
                           pstmt.setInt(1, card);
                           pstmt.setInt(2, updated);
                           int affectedRows = pstmt.executeUpdate();
                           if (affectedRows == 0) {
                              System.out.println("Could not be inserted into the database! Please review information entered.");
                           } else {
                              System.out.println("Update Successful!.");
                           }
                        } catch (SQLException var31) {
                           System.out.println("Could not be inserted into the database! Please review information entered.");
                        }
                        break;
                     case 12:
                        System.out.println("Exiting . . .");
                     }
                  } else {
                     System.out.println("Invalid Input, try again!");
                  }
               }

               return;
            }
         }
      } else {
         System.out.println("Invalid login credentials, program is now terminating!");
      }
   }
}