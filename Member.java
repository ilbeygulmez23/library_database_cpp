import java.time.LocalDate;
import java.util.ArrayList;

public class Member {
    //Declare variables
    private int id;
    private String type;
    private int timeLimit;
    private int borrowingLimit;
    private int timesBorrowed = 0;
    private String typeChar;
    private static final ArrayList<Member> members = new ArrayList<>();

    /* Constructor */

    public Member(String typeChar) {
        this.typeChar = typeChar;
        if(typeChar.equals("A")){
            setType("Academic");
            setTimeLimit();
            setBorrowingLimit();
        }
        else if (typeChar.equals("S")) {
            setType("Student");
            setTimeLimit();
            setBorrowingLimit();
        }
        else{
            System.out.println("Invalid member type!");
        }
    }

    /* Getter and setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeChar() {
        return typeChar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<Member> getMembers() {
        return members;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit() {
        if(typeChar.equals("A")){ //Set time limit for each member types
            this.timeLimit = 2;
        }
        else if (typeChar.equals("S")) {
            this.timeLimit = 1;
        }
    }

    public void setBorrowingLimit() {
        if(typeChar.equals("A")){ //Set borrowing limit for each member types
            this.borrowingLimit = 4;
        }
        else if (typeChar.equals("S")) {
            this.borrowingLimit = 2;
        }
    }

    public int getTimesBorrowed() {
        return timesBorrowed;
    }

    public void setTimesBorrowed(int timesBorrowed) {
        this.timesBorrowed += timesBorrowed;
    }
}
