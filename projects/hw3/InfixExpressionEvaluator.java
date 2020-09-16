package edu.pitt.cs.as3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
// NEW: Import Math package:
import java.lang.Math;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens:
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating):
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;
	// NEW: Stack to check balance of delimiters:
	StackInterface<Character> openDelimiterStack;
	
	// Result value to return:
	Double result;
	
    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream:
        tokenizer = new StreamTokenizer(new BufferedReader(
                new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators:
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression:
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks:
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
		openDelimiterStack = new ArrayStack<Character>();
		
		// Initialize the result:
		result = new Double(0);
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError {
		int lastToken = ' ';
		
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
			if(tokenizer.ttype == ')' || tokenizer.ttype == '>')
			{
				throw new ExpressionError("expression cannot start with close bracket");
			}
			else if(tokenizer.ttype == '+' || tokenizer.ttype == '-' || tokenizer.ttype == '*' || tokenizer.ttype == '/' || tokenizer.ttype == '^')
			{
				throw new ExpressionError("expression cannot start with operator");
			}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line:
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types:
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand:
                    handleOperand((double)tokenizer.nval);
					if(lastToken == StreamTokenizer.TT_NUMBER)
					{
						throw new ExpressionError("Operand cannot follow operand.");
					}
					else if(lastToken == ')' || lastToken == '>')
					{
						throw new ExpressionError("opeand cannot follow close bracket");
					}
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // is an operator: 
                    handleOperator((char)tokenizer.ttype);
					if (lastToken == '+' || lastToken == '-' || lastToken == '*' || lastToken == '/' || lastToken == '^')
					{
						throw new ExpressionError("operator cannot follow operator");
					}
					else if (lastToken == '(' || lastToken == '<')
					{
						throw new ExpressionError("operator cannot follow open bracket");
					}
                    break;
                case '(':
                case '<':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly:
                    handleOpenBracket((char)tokenizer.ttype);
					if(lastToken == StreamTokenizer.TT_NUMBER)
					{
						throw new ExpressionError("open bracket cannot follow operand");
					}
					else if(lastToken == ')' || lastToken == '>')
					{
						throw new ExpressionError("open bracket cannot follow close bracket");
					}
                    break;
                case ')':
                case '>':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly:
                    handleCloseBracket((char)tokenizer.ttype);
					if (lastToken == '+' || lastToken == '-' || lastToken == '*' || lastToken == '/' || lastToken == '^')
					{
						throw new ExpressionError("close bracket cannot follow operator");
					}
					else if (lastToken == '(' || lastToken == '<')
					{
						throw new ExpressionError("close bracket cannot follow open bracket");
					}
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error:
                    throw new ExpressionError("Unrecognized token: " +
                            tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error:
                    throw new ExpressionError("Unrecognized token: " +
                            String.valueOf((char)tokenizer.ttype));
            }
			
			lastToken = tokenizer.ttype;

            // Read the next token, again converting any potential IO exception:
            try {
                tokenizer.nextToken();
				
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
		/*if(tokenizer.ttype == '+' || tokenizer.ttype == '-' || tokenizer.ttype == '*' || tokenizer.ttype == '/' || tokenizer.ttype == '^')
		{
			throw new ExpressionError("expression cannot end with operator");
		}*/
		
		if(!openDelimiterStack.isEmpty())
		{
			throw new ExpressionError("delimiters in expression are not balanced");
		}

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        handleRemainingOperators();
		
		return result;
    }
	
	// NEW: isPairded checks whether there is a pair of open and close brackets: 
	private boolean isPaired(char openBracket, char closeBracket)
	{
		return(openBracket == '(' && closeBracket == ')' || openBracket == '<' && closeBracket == '>');
	}
	
	// NEW: getPrecedence gets the precedence of an operator and returns
	// the precedence value:
	private int getPrecedence(char operator) {
		switch(operator) 
		{
			case '+':
			case '-':
				return 1;
				
			case '*':
			case '/':
				return 2;
			
			case '^':
				return 3;
			// 
		}
		return -1;
	}

	// NEW: performOperation performs an operation on two operands and pushes
	// the result to the operandStack:
	private void performOperation()
	{
		Double operand2 = new Double(operandStack.pop());
		char   operand  = operatorStack.pop();
		if(operandStack.isEmpty())
		{
			throw new ExpressionError("expression cannot end with operator");
		}
		Double operand1 = new Double(operandStack.pop());
		
		switch(operand) {
			case '+':
				result = operand1 + operand2;
				break;
			case '-':
				result = operand1 - operand2;
				break;
			case '*':
				result = operand1 * operand2;
				break;
			case '/':
				// NEW: Catch divide-by-zero error:
				if(operand2 == 0)
				{
					throw new ExpressionError("cannot divide by zero");
				}
				result = operand1 / operand2;
			case '^':
				result = Math.pow(operand1, operand2);
				break;
		}
		operandStack.push(result);
	}
	
    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void handleOperand(double operand) {	// DONE
		// Push operand to operandStack:
		operandStack.push(operand);
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void handleOperator(char operator) {	// DONE		
		// If operatorStack is empty, then push operator onto operatorStack:
		if(operatorStack.isEmpty())
		{
			operatorStack.push(operator);
		}
		// If operatorStack is NOT empty and the operator's precedence is greater
		// than or equal to the precedence of the stack top of operatorStack, then 
		// push operator onto operatorStack:
		else if (!operatorStack.isEmpty() && getPrecedence(operator) >= getPrecedence(operatorStack.peek()))
		{
			operatorStack.push(operator);
		}
		// Else...performOperation() until operatorStack is empty or the operator precedence is greater
		// than or equal to the precedence of the top of the operatorStack:
		else
		{
			while(!operatorStack.isEmpty() && getPrecedence(operator) < getPrecedence(operatorStack.peek()))
			{
				performOperation();
			}
			operatorStack.push(operator);
		}
    }

    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    void handleOpenBracket(char openBracket) {	// DONE
		// Push openBracket onto the operatorStack.
		operatorStack.push(openBracket);
		
		//--------------------------------------------------------------------------
		// Push openBracket to openDelimiterStack:
		openDelimiterStack.push(openBracket);
		//--------------------------------------------------------------------------
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
    void handleCloseBracket(char closeBracket) {	// DONE
		// -------------------------------------------------------------------------
		if(openDelimiterStack.isEmpty())
		{
			throw new ExpressionError("delimiters in expression are not balanced");
		}
		else if (!isPaired(openDelimiterStack.pop(), closeBracket))
		{
			throw new ExpressionError("delimiters in expression are not balanced");
		}
		//--------------------------------------------------------------------------
		switch(closeBracket)
		{
			case ')':
				while (!operatorStack.isEmpty() && operatorStack.peek() != '(')
				{
					performOperation();
				}
				operatorStack.pop();
				break;
				
			case '>':
				while (!operatorStack.isEmpty() && operatorStack.peek() != '<')
				{
					performOperation();
				}
				operatorStack.pop();
				break;
		}
    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void handleRemainingOperators() {	// DONE
		while(!operatorStack.isEmpty())
		{
			performOperation();
		}
    }
	
    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
			
        } catch (ExpressionError e) {
            System.out.println("ExpressionError: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        } else {
            System.out.println("Evaluator returned null");
        }
    }
}