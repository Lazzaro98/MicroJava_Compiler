// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclaration extends ConstDecl {

    private Type Type;
    private String constName;
    private ConstType ConstType;
    private ConstDeclPomList ConstDeclPomList;

    public ConstDeclaration (Type Type, String constName, ConstType ConstType, ConstDeclPomList ConstDeclPomList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.constName=constName;
        this.ConstType=ConstType;
        if(ConstType!=null) ConstType.setParent(this);
        this.ConstDeclPomList=ConstDeclPomList;
        if(ConstDeclPomList!=null) ConstDeclPomList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public ConstType getConstType() {
        return ConstType;
    }

    public void setConstType(ConstType ConstType) {
        this.ConstType=ConstType;
    }

    public ConstDeclPomList getConstDeclPomList() {
        return ConstDeclPomList;
    }

    public void setConstDeclPomList(ConstDeclPomList ConstDeclPomList) {
        this.ConstDeclPomList=ConstDeclPomList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstType!=null) ConstType.accept(visitor);
        if(ConstDeclPomList!=null) ConstDeclPomList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstType!=null) ConstType.traverseTopDown(visitor);
        if(ConstDeclPomList!=null) ConstDeclPomList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstType!=null) ConstType.traverseBottomUp(visitor);
        if(ConstDeclPomList!=null) ConstDeclPomList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(ConstType!=null)
            buffer.append(ConstType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclPomList!=null)
            buffer.append(ConstDeclPomList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclaration]");
        return buffer.toString();
    }
}
