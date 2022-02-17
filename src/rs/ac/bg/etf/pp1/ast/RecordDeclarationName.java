// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class RecordDeclarationName extends RecordDeclName {

    private String recordName;

    public RecordDeclarationName (String recordName) {
        this.recordName=recordName;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName=recordName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDeclarationName(\n");

        buffer.append(" "+tab+recordName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDeclarationName]");
        return buffer.toString();
    }
}
