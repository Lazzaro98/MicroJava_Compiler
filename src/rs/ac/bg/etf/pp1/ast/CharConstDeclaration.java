// generated with ast extension for cup
// version 0.8
// 17/1/2022 8:52:44


package rs.ac.bg.etf.pp1.ast;

public class CharConstDeclaration extends ConstType {

    private Character chr;

    public CharConstDeclaration (Character chr) {
        this.chr=chr;
    }

    public Character getChr() {
        return chr;
    }

    public void setChr(Character chr) {
        this.chr=chr;
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
        buffer.append("CharConstDeclaration(\n");

        buffer.append(" "+tab+chr);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharConstDeclaration]");
        return buffer.toString();
    }
}
