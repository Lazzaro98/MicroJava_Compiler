// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationPomList extends ConstDeclPomList {

    private ConstDeclPomList ConstDeclPomList;
    private String constName;
    private ConstType ConstType;

    public ConstDeclarationPomList (ConstDeclPomList ConstDeclPomList, String constName, ConstType ConstType) {
        this.ConstDeclPomList=ConstDeclPomList;
        if(ConstDeclPomList!=null) ConstDeclPomList.setParent(this);
        this.constName=constName;
        this.ConstType=ConstType;
        if(ConstType!=null) ConstType.setParent(this);
    }

    public ConstDeclPomList getConstDeclPomList() {
        return ConstDeclPomList;
    }

    public void setConstDeclPomList(ConstDeclPomList ConstDeclPomList) {
        this.ConstDeclPomList=ConstDeclPomList;
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclPomList!=null) ConstDeclPomList.accept(visitor);
        if(ConstType!=null) ConstType.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclPomList!=null) ConstDeclPomList.traverseTopDown(visitor);
        if(ConstType!=null) ConstType.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclPomList!=null) ConstDeclPomList.traverseBottomUp(visitor);
        if(ConstType!=null) ConstType.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationPomList(\n");

        if(ConstDeclPomList!=null)
            buffer.append(ConstDeclPomList.toString("  "+tab));
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

        buffer.append(tab);
        buffer.append(") [ConstDeclarationPomList]");
        return buffer.toString();
    }
}
