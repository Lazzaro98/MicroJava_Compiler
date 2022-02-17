// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class RecordDeclaration extends RecordDecl {

    private RecordDeclName RecordDeclName;
    private VarDeclList VarDeclList;

    public RecordDeclaration (RecordDeclName RecordDeclName, VarDeclList VarDeclList) {
        this.RecordDeclName=RecordDeclName;
        if(RecordDeclName!=null) RecordDeclName.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public RecordDeclName getRecordDeclName() {
        return RecordDeclName;
    }

    public void setRecordDeclName(RecordDeclName RecordDeclName) {
        this.RecordDeclName=RecordDeclName;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(RecordDeclName!=null) RecordDeclName.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordDeclName!=null) RecordDeclName.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordDeclName!=null) RecordDeclName.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDeclaration(\n");

        if(RecordDeclName!=null)
            buffer.append(RecordDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDeclaration]");
        return buffer.toString();
    }
}
