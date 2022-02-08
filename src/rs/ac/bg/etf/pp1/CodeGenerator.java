package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.AddopMinus;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.ColonHelper;
import rs.ac.bg.etf.pp1.ast.CondTermList;
import rs.ac.bg.etf.pp1.ast.ConditionFactor;
import rs.ac.bg.etf.pp1.ast.ConditionFactorList;
import rs.ac.bg.etf.pp1.ast.ConditionFactorSingle;
import rs.ac.bg.etf.pp1.ast.DesignatorDot;
import rs.ac.bg.etf.pp1.ast.DesignatorSingle;
import rs.ac.bg.etf.pp1.ast.DesignatorSquare;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementActParams;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementActParamsEmpty;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementEq;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementInc;
import rs.ac.bg.etf.pp1.ast.Do;
import rs.ac.bg.etf.pp1.ast.DoCondition1;
import rs.ac.bg.etf.pp1.ast.DoConditionStart1;
import rs.ac.bg.etf.pp1.ast.ElseStart1;
import rs.ac.bg.etf.pp1.ast.EqualsOp;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorDesignator;
import rs.ac.bg.etf.pp1.ast.FactorNewSquare;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.FuncCallNP;
import rs.ac.bg.etf.pp1.ast.FuncCallP;
import rs.ac.bg.etf.pp1.ast.GreaterEqualsOperator;
import rs.ac.bg.etf.pp1.ast.GreaterOp;
import rs.ac.bg.etf.pp1.ast.IFCondition1;
import rs.ac.bg.etf.pp1.ast.LeftAnd1;
import rs.ac.bg.etf.pp1.ast.LeftOr1;
import rs.ac.bg.etf.pp1.ast.LessEqualsOperator;
import rs.ac.bg.etf.pp1.ast.LessOperator;
import rs.ac.bg.etf.pp1.ast.MatchedStmt;
import rs.ac.bg.etf.pp1.ast.MethodDeclaration;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MethodTypeName_;
import rs.ac.bg.etf.pp1.ast.MethodVoidName;
import rs.ac.bg.etf.pp1.ast.Mulop;
import rs.ac.bg.etf.pp1.ast.MulopDiv;
import rs.ac.bg.etf.pp1.ast.MulopMod;
import rs.ac.bg.etf.pp1.ast.MulopMul;
import rs.ac.bg.etf.pp1.ast.NoEqualsOp;
import rs.ac.bg.etf.pp1.ast.ProgramName;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.SingleStatementPrint;
import rs.ac.bg.etf.pp1.ast.SingleStatementPrintNumconst;
import rs.ac.bg.etf.pp1.ast.SingleStatementRead;
import rs.ac.bg.etf.pp1.ast.StatementBreak;
import rs.ac.bg.etf.pp1.ast.StatementContinue;
import rs.ac.bg.etf.pp1.ast.StatementDoWhile;
import rs.ac.bg.etf.pp1.ast.StatementReturn;
import rs.ac.bg.etf.pp1.ast.StatementReturnNull;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermExprMin;
import rs.ac.bg.etf.pp1.ast.TermMulFactor;
import rs.ac.bg.etf.pp1.ast.UnmatchedIf;
import rs.ac.bg.etf.pp1.ast.UnmatchedIfElse;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	private String currentMethodDeclaration = null;;

	private LinkedList<Integer> usl = new LinkedList<Integer>();

	enum op {
		EQUALS, NOT_EQUALS, GREATER, GREATER_EQUALS, LESS, LESS_EQUALS
	};

	op saved_op;

	Obj programObj;

	public static Struct bool = Tab.find("bool").getType();

	public int getMainPc() {
		return this.mainPc;
	}

	public void visit(MethodTypeName_ arg) {
		if ("main".equalsIgnoreCase(arg.getMethName())) {
			mainPc = Code.pc;
		}
		Obj obj = arg.obj;
		// pri ulasku u metodu postavljamo adr polje a to je polje trenutnog pc-a
		currentMethodDeclaration = arg.getMethName();
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());

		// Collect argument and local variable
		/*
		 * SyntaxNode methodNode = arg.getParent();
		 * 
		 * VarCounter varCnt = new VarCounter(); methodNode.traverseTopDown(varCnt);
		 * 
		 * FormParamCounter paramCnt = new FormParamCounter();
		 * methodNode.traverseTopDown(paramCnt);
		 * 
		 * // Generate entry Code.put(Code.enter); Code.put(paramCnt.getCount());
		 * Code.put(paramCnt.getCount() + varCnt.getCount());
		 */
	}

	public void visit(MethodVoidName arg) {
		if ("main".equalsIgnoreCase(arg.getMethName())) {
			mainPc = Code.pc;
		}

		Obj obj = arg.obj;
		// pri ulasku u metodu postavljamo adr polje a to je polje trenutnog pc-a
		currentMethodDeclaration = arg.getMethName();
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());

//		arg.obj.setAdr(Code.pc);
		// pri ulasku u metodu postavljamo adr polje a to je polje trenutnog pc-a

		// Collect argument and local variable
//		SyntaxNode methodNode = arg.getParent();
//
//		VarCounter varCnt = new VarCounter();
//		methodNode.traverseTopDown(varCnt);
//
//		FormParamCounter paramCnt = new FormParamCounter();
//		methodNode.traverseTopDown(paramCnt);
//
//		// Generate entry
//		Code.put(Code.enter);
//		Code.put(paramCnt.getCount());
//		Code.put(paramCnt.getCount() + varCnt.getCount());
	}

	public void visit(MethodDeclaration arg) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(StatementReturn arg) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(StatementReturnNull arg) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// print
	public void visit(SingleStatementPrint arg) {
		// mi podrazumevamo da kad se expr obradio tj posetio da je on stavio svoju
		// vrednost na exprstack
		// ta vrednost moze biti ili bajt ili int
		if (arg.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			// zato sto nam treba da ucitamo velicinu a to cemo uraditi sa const
			// opsti oblik instrukcije const je CONST W ako zelimo konstantu vecu od 4 sto
			// je ovde slucaj
			// zbog toga nam treba 1B za rec const i 4B za rec
			Code.put(Code.print);
		} else {
			// ovde je sirina 1 pa nam je dovoljno da kazemo const_1 pa stoga saljemo samo
			// 1B
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	public void visit(SingleStatementPrintNumconst arg) {
		// mi podrazumevamo da kad se expr obradio tj posetio da je on stavio svoju
		// vrednost na exprstack
		// ta vrednost moze biti ili bajt ili int
		if (arg.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			// zato sto nam treba da ucitamo velicinu a to cemo uraditi sa const
			// opsti oblik instrukcije const je CONST W ako zelimo konstantu vecu od 4 sto
			// je ovde slucaj
			// zbog toga nam treba 1B za rec const i 4B za rec
			Code.put(Code.print);
		} else {
			// ovde je sirina 1 pa nam je dovoljno da kazemo const_1 pa stoga saljemo samo
			// 1B
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	// Factor

	public void visit(FactorNumConst arg) {
		Obj con = Tab.insert(Obj.Con, "$", arg.struct);
		con.setLevel(0); // globalna
		con.setAdr(arg.getN1());

		Code.load(con);
	}

	public void visit(FactorCharConst arg) { // not sure what this is about
		Obj con = Tab.insert(Obj.Con, "$", arg.struct);
		con.setLevel(0); // globalna
		con.setAdr(arg.getC1());

		Code.load(con);
	}

	public void visit(FactorBoolConst arg) {
		Obj con = Tab.insert(Obj.Con, "$", arg.struct);
		con.setLevel(0); // globalna
		con.setAdr(arg.getB1());

		Code.load(con);
	}

	public void visit(FactorDesignator arg) {
		// TODO
		// Var var, kod duce

		Struct struct = arg.getDesignator().obj.getType();
		Obj obj = findSymbol(currentMethodDeclaration, arg.getDesignator().obj.getName());
		if (struct.getKind() != Struct.Array && obj.getType().getElemType() != null)
			obj = new Obj(Obj.Elem, "$", obj.getType().getElemType());
		Code.load(obj);

	}

	public void visit(TermMulFactor arg) {
		if (arg.getMulop().getClass() == MulopDiv.class)
			Code.put(Code.div);
		else if (arg.getMulop().getClass() == MulopMul.class)
			Code.put(Code.mul);
		else if (arg.getMulop().getClass() == MulopMod.class)
			Code.put(Code.rem);
	}

	public void visit(AddExpr arg) {
		if (arg.getAddop().getClass() == AddopPlus.class)
			Code.put(Code.add);
		else if (arg.getAddop().getClass() == AddopMinus.class)
			Code.put(Code.sub);
	}

	public void visit(FactorNewSquare arg) {
		if (arg.getType().struct.equals(Tab.charType))
			Code.put(0);
		else
			Code.put(1);
		Code.put(1);
	}

	// program
	public void visit(ProgramName arg) {
		programObj = Tab.find(arg.getProgramName());
	}

	public void visit(DesignatorStatementInc arg) {
		Obj designator = arg.getDesignator().obj;

		Code.loadConst(1);

		Code.put(Code.add);
		Code.store(designator);
	}

	public void visit(rs.ac.bg.etf.pp1.ast.DesignatorStatementDec arg) {
		Obj designator = arg.getDesignator().obj;

		Code.loadConst(1);

		Code.put(Code.sub);
		Code.store(designator);
	}

	public void visit(FuncCallNP arg) {
		Obj functionObj = arg.getDesignator().obj;

		int offset = functionObj.getAdr() - Code.pc; // dobijamo pc relative adresu
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(DesignatorStatementEq arg) {
//		Obj o = arg.getDesignator().obj;
//		Obj obj = findSymbol(currentMethodDeclaration, arg.getDesignator().obj.getName());
//		
//		if(o.getType().getKind() != Struct.Array && obj.getType().getElemType() != null) {
//			
//		}
		Code.store(arg.getDesignator().obj);
	}

	public void visit(DesignatorSingle arg) {
		SyntaxNode parent = arg.getParent();

		if (arg.obj.getKind() == Obj.Elem) {
			Obj obj = Tab.find(arg.obj.getName());

			Code.load(obj);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);

			if (parent.getClass() == DesignatorStatementInc.class
					|| parent.getClass() == rs.ac.bg.etf.pp1.ast.DesignatorStatementDec.class) {
				Code.put(Code.dup2);
			}
		}

		if (DesignatorStatementEq.class != parent.getClass() && FuncCallP.class != parent.getClass()
				&& FuncCallNP.class != parent.getClass()) {
			Code.load(arg.obj);
		}
	}

	public void visit(DesignatorStatementActParamsEmpty arg) {
		String methodName = arg.getDesignator().obj.getName();
		Obj obj = findSymbol(currentMethodDeclaration, methodName);

		if (obj.getType().getKind() != Struct.None)
			Code.put(Code.pop);
	}

	// DesignatorStatementActParams
	public void visit(DesignatorStatementActParams arg) {
		String methodName = arg.getDesignator().obj.getName();
		Obj obj = findSymbol(currentMethodDeclaration, methodName);

		if (obj.getType().getKind() != Struct.None)
			Code.put(Code.pop);

//		Obj functionObj = arg.getDesignator().obj;
//		int offset = functionObj.getAdr() - Code.pc; // dobijamo pc relative adresu
//		Code.put(Code.call);
//
//		Code.put2(offset);
//		if (arg.getDesignator().obj.getType() != Tab.noType) {
//			Code.put(Code.pop);
//		}
	}

	// Designatoru

	// Designator (DesignatorSquare) Designator LSQUARE Expr:e RSQUARE
	public void visit(DesignatorSquare arg) {
		Code.load(arg.obj);
	}

	public void visit(DesignatorDot arg) {
		Code.load(arg.obj);

	}

	public void visit(TermExprMin arg) {
		Code.put(Code.neg);
	}

	/*
	 * public void visit(ColonHelper arg) { usl.add(Code.pc + 1); Code.putJump(0);
	 * 
	 * Code.fixup((int)usl.removeFirst()); }
	 */

	public void visit(ConditionFactor arg) {
//		if (saved_op == null) {
//			Code.loadConst(0);
//			usl.add(Code.pc + 1);
//			Code.putFalseJump(4, 0);
//		} else {
//			usl.add(Code.pc + 1);
//			if (saved_op == op.EQUALS)
//				Code.putFalseJump(0, 0);
//			if (saved_op == op.NOT_EQUALS)
//				Code.putFalseJump(1, 0);
//			if (saved_op == op.GREATER)
//				Code.putFalseJump(4, 0);
//			if (saved_op == op.GREATER_EQUALS)
//				Code.putFalseJump(5, 0);
//			if (saved_op == op.LESS)
//				Code.putFalseJump(2, 0);
//			if (saved_op == op.LESS_EQUALS)
//				Code.putFalseJump(3, 0);
//		}
//		saved_op = null;
		Relop relop = arg.getRelop();
		if (relop.getClass() == EqualsOp.class)
			Code.put(Code.jcc + Code.inverse[Code.eq]);
		if (relop.getClass() == NoEqualsOp.class)
			Code.put(Code.jcc + Code.inverse[Code.ne]);
		if (relop.getClass() == GreaterOp.class)
			Code.put(Code.jcc + Code.inverse[Code.gt]);
		if (relop.getClass() == GreaterEqualsOperator.class)
			Code.put(Code.jcc + Code.inverse[Code.ge]);
		if (relop.getClass() == LessOperator.class)
			Code.put(Code.jcc + Code.inverse[Code.lt]);
		if (relop.getClass() == LessOperator.class)
			Code.put(Code.jcc + Code.inverse[Code.le]);

		Code.put2(7);
		Code.put(Code.const_1);
		Code.put(Code.jmp);
		Code.put2(4);
		Code.put(Code.const_n);

	}

	public void visit(ConditionFactorSingle arg) {
		if (saved_op == null) {
			Code.loadConst(0);
			usl.add(Code.pc + 1);
			Code.putFalseJump(4, 0);
		} else {
			usl.add(Code.pc + 1);
			if (saved_op == op.EQUALS)
				Code.putFalseJump(0, 0);
			if (saved_op == op.NOT_EQUALS)
				Code.putFalseJump(1, 0);
			if (saved_op == op.GREATER)
				Code.putFalseJump(4, 0);
			if (saved_op == op.GREATER_EQUALS)
				Code.putFalseJump(5, 0);
			if (saved_op == op.LESS)
				Code.putFalseJump(2, 0);
			if (saved_op == op.LESS_EQUALS)
				Code.putFalseJump(3, 0);
		}
		saved_op = null;
	}

	public void visit(EqualsOp arg) {
		saved_op = op.EQUALS;
	}

	public void visit(NoEqualsOp arg) {
		saved_op = op.NOT_EQUALS;
	}

	public void visit(GreaterOp arg) {
		saved_op = op.GREATER;
	}

	public void visit(GreaterEqualsOperator arg) {
		saved_op = op.GREATER_EQUALS;
	}

	public void visit(LessOperator arg) {
		saved_op = op.LESS;
	}

	public void visit(LessEqualsOperator arg) {
		saved_op = op.LESS_EQUALS;
	}

	public void visit(SingleStatementRead arg) {
		if (arg.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else if (arg.getDesignator().obj.getType() == Tab.intType || arg.getDesignator().obj.getType() == bool) {
			Code.put(Code.read);
		}

		Code.store(arg.getDesignator().obj);
	}

	private Stack<Integer> leftAndToFixUp = new Stack<>();

	public void visit(LeftAnd1 leftAnd) {
		Code.put(Code.dup);
		Code.put(Code.const_1);
		leftAndToFixUp.push(Code.pc + 1);
		Code.putFalseJump(Code.eq, Code.pc);
		Code.put(Code.pop);
	}

	public void visit(ConditionFactorList arg) {
		Code.fixup(leftAndToFixUp.pop());
	}

	private Stack<Integer> leftOrToFixUp = new Stack<>();

	public void visit(LeftOr1 leftOr) {
		Code.put(Code.dup);
		Code.put(Code.const_n);
		leftOrToFixUp.push(Code.pc + 1);
		Code.putFalseJump(Code.eq, Code.pc);
		Code.put(Code.pop);
	}

	public void visit(CondTermList arg) {
		Code.fixup(leftOrToFixUp.pop());
	}

	// if
	Stack<Integer> ifStackFixPatch = new Stack<>();

	public void visit(IFCondition1 ifCondition) {
		Code.put(Code.const_1);
		ifStackFixPatch.push(Code.pc + 1);
		Code.putFalseJump(Code.eq, Code.pc);
	}

	public void visit(ElseStart1 elseStart) {
		int jumpToFix = ifStackFixPatch.pop();
		ifStackFixPatch.push(Code.pc + 1);
		Code.putJump(Code.pc);
		Code.fixup(jumpToFix);
	}

	public void visit(UnmatchedIf unmatched) {
		int jumpToFix = ifStackFixPatch.pop();
		Code.fixup(jumpToFix);
	}

	public void visit(UnmatchedIfElse unmatchedIfElse) {
		int jumpToFix = ifStackFixPatch.pop();
		Code.fixup(jumpToFix);
	}

	public void visit(MatchedStmt matchedStatement) {
		int jumpToFix;
		if(!ifStackFixPatch.empty()) {jumpToFix = ifStackFixPatch.pop();Code.fixup(jumpToFix);}
	}

	// do while

	Stack<Integer> doWhileAddressStart = new Stack<>();
	Stack<Integer> doWhileConditionStart = new Stack<>();

	Stack<Integer> forAdressStart = new Stack<>();

	Stack<List<Integer>> breakPatchAddress = new Stack<>();
	Stack<List<Integer>> continuePatchAddress = new Stack<>();

	public void visit(Do startDoStmt) {
		doWhileAddressStart.push(Code.pc);
		breakPatchAddress.push(new ArrayList<>());
		continuePatchAddress.push(new ArrayList<>());
	}

	public void visit(DoCondition1 doCondition) {
		Code.put(Code.const_1);
		Code.put(Code.jcc + Code.eq);
		Code.put2(doWhileAddressStart.pop() - Code.pc + 1);

	}

	public void visit(DoConditionStart1 doConditionStart) {
		for (int address : continuePatchAddress.pop()) {
			Code.fixup(address);
		}
	}

	// StatementDoWhile
	public void visit(StatementBreak breakStmt) {
		breakPatchAddress.peek().add(Code.pc + 1);
		Code.putJump(Code.pc);
	}

	public void visit(StatementContinue continueStmt) {
		continuePatchAddress.peek().add(Code.pc + 1);
		Code.putJump(0);
	}

	public void visit(StatementDoWhile doStmt) {
		for (int address : breakPatchAddress.pop()) {
			Code.fixup(address);
		}
	}

	private Obj findSymbol(String methodName, String varName) {
		Collection<Obj> symbols = null;
		if (methodName != null) {
			symbols = programObj.getLocalSymbols().stream().filter((Obj obj) -> {
				return obj.getName().equals(methodName);
			}).findFirst().orElse(new Obj(Obj.Meth, "$", Tab.noType)).getLocalSymbols();
			if (symbols != null) {
				Obj help = symbols.stream().filter((Obj obj) -> {
					return obj.getName().equals(varName);
				}).findFirst().orElse(null);
				if (help != null)
					return help;
			}
		}
		symbols = programObj.getLocalSymbols();
		Obj symbol = symbols.stream().filter((Obj obj) -> {
			return obj.getName().equals(varName);
		}).findFirst().orElse(null);
		if (symbol == null) {
			symbol = Tab.find(varName);
		}
		return symbol;
	}
}
