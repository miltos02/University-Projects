import java.util.ArrayList;

public class StatementList {
    private final ArrayList<Statement> Statements = new ArrayList<>();

    public void newStatement(Statement st){
        Statements.add(st);
    }

    public void deleteStatement(int i){Statements.remove(i);}

    public Statement getStatement(int i){return Statements.get(i);}

    public int numOfStatements(){return Statements.size();}

    public String toString() {
        String typesPrinted ="\n";
        int i = 1;
        for (Statement st : Statements) {
            typesPrinted += "\t\t\t" + i + ". " + st.toString() + "\n";
            i++;
        }
        return typesPrinted;
    }


}
