// generated with ast extension for cup
// version 0.8
// 26/0/2022 20:53:10


package rs.ac.bg.etf.pp1.ast;

public class DeclarationListProgram extends DeclListProgram {

    private DeclListProgram DeclListProgram;
    private Decl Decl;

    public DeclarationListProgram (DeclListProgram DeclListProgram, Decl Decl) {
        this.DeclListProgram=DeclListProgram;
        if(DeclListProgram!=null) DeclListProgram.setParent(this);
        this.Decl=Decl;
        if(Decl!=null) Decl.setParent(this);
    }

    public DeclListProgram getDeclListProgram() {
        return DeclListProgram;
    }

    public void setDeclListProgram(DeclListProgram DeclListProgram) {
        this.DeclListProgram=DeclListProgram;
    }

    public Decl getDecl() {
        return Decl;
    }

    public void setDecl(Decl Decl) {
        this.Decl=Decl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclListProgram!=null) DeclListProgram.accept(visitor);
        if(Decl!=null) Decl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclListProgram!=null) DeclListProgram.traverseTopDown(visitor);
        if(Decl!=null) Decl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclListProgram!=null) DeclListProgram.traverseBottomUp(visitor);
        if(Decl!=null) Decl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationListProgram(\n");

        if(DeclListProgram!=null)
            buffer.append(DeclListProgram.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Decl!=null)
            buffer.append(Decl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationListProgram]");
        return buffer.toString();
    }
}
