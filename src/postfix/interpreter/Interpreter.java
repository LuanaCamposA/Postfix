/* *******************************************************************
 * Copyright (c) 2021 Universidade Federal de Pernambuco (UFPE).
 * 
 * This file is part of the Compilers course at UFPE.
 * 
 * During the 1970s and 1980s, Hewlett-Packard used RPN in all 
 * of their desktop and hand-held calculators, and continued to 
 * use it in some models into the 2020s. In computer science, 
 * reverse Polish notation is used in stack-oriented programming languages 
 * such as Forth, STOIC, PostScript, RPL and Joy.
 *  
 * Contributors: 
 *     Henrique Rebelo      initial design and implementation 
 *     http://www.cin.ufpe.br/~hemr/
 * ******************************************************************/

package postfix.interpreter;

import postfix.ast.Expr;
import java.util.HashMap;
/**
 * @author Henrique Rebelo
 */
public class Interpreter implements Expr.Visitor<Integer> {

	//Adding a hashMap to verify the ID value
	public static HashMap hashing() {
		HashMap<String,Integer> hashTable = new HashMap<>();
		hashTable.put("x",Integer.parseInt("10"));
		hashTable.put("y", Integer.parseInt("20"));
		hashTable.put("z", Integer.parseInt("30"));
		return hashTable;
	}

	public int interp(Expr expression) {
		int value = evaluate(expression);
		return value;
	}

	@Override
	public Integer visitNumberExpr(Expr.Number expr) {
		HashMap newHash = hashing();
		String variable = expr.value;
		if(newHash.containsKey(expr.value)){
			Integer result = (Integer) newHash.get(variable);
			return result;
		}else{
			return Integer.parseInt(expr.value);
		}
	}

	@Override
	public Integer visitBinopExpr(Expr.Binop expr) {
		int left = evaluate(expr.left);
		int right = evaluate(expr.right); 
		int result = 0;

		switch (expr.operator.type) {
		case PLUS:
			result = left + right;
			break;
		case MINUS:
			result = left - right;
			break;
		case SLASH:
			result = left / right;
			break;
		case STAR:
			result = left * right;
			break;
		default:
			break;
		}

		return result;
	}

	private int evaluate(Expr expr) {
		return expr.accept(this);
	}
}
