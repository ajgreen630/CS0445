* InfixExpressionEvaluator edited; all other files were code provided by lab prompt.
EXTRA CREDIT:
	- Program can detect and report divide-by-zero (line: 228).

NEW METHODS (labeled underneath comments starting with NEW):
	- isPaired: Helper method that returns true if an open and close bracket are
		    a pair, or false otherwise.
	- getPrecedence: Helper method that gets the precedence of an operator and
			 returns the precedence as a value.
	- performOperation: Helper method that performs an operation on two operands.

Additional add-ins:
	- StackInterface<Character> openDelimiterStack: keeps track of open bracket
	  characters.
	- result: stores the result of performOperation and is the return value
	  for InfixExpressionEvaluator.
	- lastToken: data member that keeps track of the tokenizer data type.