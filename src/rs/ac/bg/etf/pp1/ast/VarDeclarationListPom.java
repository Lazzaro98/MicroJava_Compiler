// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationListPom extends VarDeclListPom {

    private VarDeclListPom VarDeclListPom;
    private String varName;

    public VarDeclarationListPom (VarDeclListPom VarDeclListPom, String varName) {
        this.VarDeclListPom=VarDeclListPom;
        if(VarDeclListPom!=null) VarDeclListPom.setParent(this);
        this.varName=varName;
    }

    public VarDeclListPom getVarDeclListPom() {
        return VarDeclListPom;
    }

    public void setVarDeclListPom(VarDeclListPom VarDeclListPom) {
        this.VarDeclListPom=VarDeclListPom;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclListPom!=null) VarDeclListPom.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListPom!=null) VarDeclListPom.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListPom!=null) VarDeclListPom.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationListPom(\n");

        if(VarDeclListPom!=null)
            buffer.append(VarDeclListPom.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationListPom]");
        return buffer.toString();
    }
}
