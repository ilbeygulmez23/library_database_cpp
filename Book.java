import java.time.LocalDate;
import java.util.ArrayList;

public class Book {
    //Declare variables
    private String typeChar;
    private String type;
    private int id;
    private boolean isBorrowed = false;
    private boolean isReturned = false;
    private boolean isExtended = false;
    private long fee;
    private LocalDate deadline;
    private String borrowMessage;
    private String readInLibraryMessage;
    /* Declared lists */
    private static final ArrayList<Book> books = new ArrayList<>();
    private static final ArrayList<Book> borrowedBooks = new ArrayList<>();
    private static final ArrayList<Book> readInLibraryBooks = new ArrayList<>();

    /* Constructor */

    public Book(String typeChar) {
        this.typeChar = typeChar;
        if(typeChar.equals("P")){
            setType("Printed");
        }
        else if (typeChar.equals("H")) {
            setType("Handwritten");
        }
        else{
            System.out.println("Invalid book type!");
        }
    }

    /* Getter and setters */

    public String getTypeChar() {
        return typeChar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public boolean isExtended() {
        return isExtended;
    }

    public void setExtended(boolean extended) {
        isExtended = extended;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        /*
        * If the deadline has not yet passed, fee would be taken negative, this ensures the fee is 0 in that case.
        */
        if(fee > 0){this.fee = fee;}
        else{this.fee = 0;}

    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public static ArrayList<Book> getBooks() {
        return books;
    }

    public static ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public static ArrayList<Book> getReadInLibraryBooks() {
        return readInLibraryBooks;
    }

    public String getBorrowMessage() {
        return borrowMessage;
    }

    public void setBorrowMessage(String borrowMessage) {
        this.borrowMessage = borrowMessage;
    }

    public String getReadInLibraryMessage() {
        return readInLibraryMessage;
    }

    public void setReadInLibraryMessage(String readInLibraryMessage) {
        this.readInLibraryMessage = readInLibraryMessage;
    }

}
