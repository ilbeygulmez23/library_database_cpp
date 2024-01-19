import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryCommands {
    public static int bookIdCount = 0;
    public static int memberIdCount = 0;
    public static void readInput(String file){
        File inputFile = new File(file);
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();//Current line
                String[] lineParts = line.trim().split("\t");
                ExecuteCommand(lineParts[0],lineParts); //Call the command method
            }
        }catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found: " + file);
        }
    }
    public static void ExecuteCommand(String command,String[] lineParts){
        switch(command){
            case "addBook":
                bookIdCount += 1;
                Book book = new Book(lineParts[1]);
                book.setId(bookIdCount);
                Book.getBooks().add(book);
                System.out.println("Created new book: " + book.getType() + " [id: "+ bookIdCount +"]");
                break;

            case "addMember":
                memberIdCount += 1;
                Member member = new Member(lineParts[1]);
                member.setId(memberIdCount);
                Member.getMembers().add(member);
                System.out.println("Created new member: " + member.getType() + " [id: "+ memberIdCount +"]");
                break;

            case "borrowBook":
                for(Book bBook : Book.getBooks()){
                    for(Member bMember : Member.getMembers()){
                        if(bBook.getId() == Integer.parseInt(lineParts[1]) && bMember.getId() == Integer.parseInt(lineParts[2])){
                            if(bMember.getTimesBorrowed() <= bMember.getTimeLimit()){
                            bBook.setBorrowed(true);
                            bMember.setTimesBorrowed(1);
                            bBook.setDeadline(LocalDate.parse(lineParts[3]).plusWeeks(bMember.getTimeLimit())); //time limit for the LibraryMember
                            String message = "The book " + "[" + bBook.getId() + "]" + " was borrowed by member " + "[" + bMember.getId() + "]" + " at " + lineParts[3];
                            bBook.setBorrowMessage(message);
                            Book.getBorrowedBooks().add(bBook);
                            System.out.println(message);
                            }
                            else{
                                System.out.println("You have exceeded the borrowing limit!");
                            }
                        }
                    }
                }
                break;

            case "returnBook":
                for(Book bBook : Book.getBooks()){
                    for(Member bMember : Member.getMembers()){
                        if(bBook.getId() == Integer.parseInt(lineParts[1]) && bMember.getId() == Integer.parseInt(lineParts[2])){
                            if(bBook.isBorrowed()){
                                bBook.setBorrowed(false);
                                if(bBook.getDeadline() != null){ //If deadline is null, that means the book is being read in library
                                    bBook.setFee(ChronoUnit.DAYS.between(bBook.getDeadline(),LocalDate.parse(lineParts[3])));
                                    Book.getBorrowedBooks().remove(bBook);
                                }
                                else{
                                    Book.getReadInLibraryBooks().remove(bBook);
                                }
                                System.out.println("The book " + "[" + bBook.getId() + "]" + " was returned by member " + "[" + bMember.getId() + "]" + " at " + lineParts[3] + " Fee: " + bBook.getFee());
                            }
                            else{
                                System.out.println("The book is not borrowed!");
                            }
                        }
                    }
                }
                break;

            case "extendBook":
                for(Book bBook : Book.getBooks()){
                    for(Member bMember : Member.getMembers()){
                        if(bBook.getId() == Integer.parseInt(lineParts[1]) && bMember.getId() == Integer.parseInt(lineParts[2])){
                            if(bBook.isBorrowed()){
                                if(bBook.isExtended()){
                                    System.out.println("You cannot extend the deadline!");
                                }
                                else{
                                    if(LocalDate.parse(lineParts[3]).isBefore(bBook.getDeadline())){
                                        /* If the deadline is not yet passed, member has doubled amount of time extended. */
                                        bBook.setDeadline(bBook.getDeadline().plusWeeks(bMember.getTimeLimit() * 2L));
                                    }
                                    else{
                                        bBook.setDeadline(bBook.getDeadline().plusWeeks(bMember.getTimeLimit()));
                                    }
                                    bBook.setExtended(true);
                                    System.out.println("The deadline of book " + "[" + bBook.getId() + "]" + " was extended by member " + "[" + bMember.getId() + "]" + " at " + lineParts[3]);
                                    System.out.println("New deadline of book " + "[" + bBook.getId() + "]" + " is " + bBook.getDeadline());

                                }
                            }
                            else{
                                System.out.println("The book is not borrowed!");
                            }
                        }
                    }
                }
                break;

            case "readInLibrary":
                for(Book bBook : Book.getBooks()){
                    for(Member bMember : Member.getMembers()){
                        if(bBook.getId() == Integer.parseInt(lineParts[1]) && bMember.getId() == Integer.parseInt(lineParts[2])){
                            if(bBook.isBorrowed()){
                                System.out.println("You can not read this book!");
                            }
                            else if (bBook.getTypeChar().equals("H") && bMember.getTypeChar().equals("S")) {
                                System.out.println("Students can not read handwritten books!");
                            }
                            else{
                                String message = "The book " + "[" + bBook.getId() + "]" + " was read in library by member " + "[" + bMember.getId() + "]" + " at " + lineParts[3];
                                System.out.println(message);
                                Book.getReadInLibraryBooks().add(bBook);
                                bBook.setReadInLibraryMessage(message);
                                bBook.setBorrowed(true);
                            }
                        }
                    }
                }
                break;

            case "getTheHistory":
                getTheHistory();
                break;

            default:
                System.out.println("Erroneous command!");
                break;
        }
    }
    public static void getTheHistory(){
        /* Variables and lists for the method */
        int numStudents = 0;
        int numAcademics = 0;
        int numPrinted = 0;
        int numHandwritten = 0;
        ArrayList<Book> printedBooks = new ArrayList<>();
        ArrayList<Book> handwrittenBooks = new ArrayList<>();
        ArrayList<Member> academics = new ArrayList<>();
        ArrayList<Member> students = new ArrayList<>();

        for(Member member : Member.getMembers()){
            if(member.getTypeChar().equals("A")){
                numAcademics += 1;
                academics.add(member);
            }
            else if (member.getTypeChar().equals("S")) {
                numStudents += 1;
                students.add(member);
            }
        }
        for(Book book : Book.getBooks()){
            if(book.getTypeChar().equals("P")){
                numPrinted += 1;
                printedBooks.add(book);
            }
            else if (book.getTypeChar().equals("H")) {
                numHandwritten += 1;
                handwrittenBooks.add(book);
            }
        }
        System.out.println("History of library:");
        System.out.println();
        System.out.println("Number of students:" + numStudents);
        for(Member student : students){
            System.out.println(student.getType() + " [id: " + student.getId() + "]");
        }
        System.out.println();
        System.out.println("Number of academics:" + numAcademics);
        for(Member academic : academics){
            System.out.println(academic.getType() + " [id: " + academic.getId() + "]");
        }
        System.out.println();
        System.out.println("Number of printed books:" + numPrinted);
        for(Book book : printedBooks){
            System.out.println(book.getType() + " [id: " + book.getId() + "]");
        }
        System.out.println();
        System.out.println("Number of handwritten books:" + numHandwritten);
        for(Book book : handwrittenBooks){
            System.out.println(book.getType() + " [id: " + book.getId() + "]");
        }
        System.out.println();
        System.out.println("Number of borrowed books:" + Book.getBorrowedBooks().size());
        for(Book book : Book.getBorrowedBooks()){
            System.out.println(book.getBorrowMessage());
        }
        System.out.println();
        System.out.println("Number of books read in library:" + Book.getReadInLibraryBooks().size());
        for(Book book : Book.getReadInLibraryBooks()){
                System.out.println(book.getReadInLibraryMessage());
        }
    }
}
