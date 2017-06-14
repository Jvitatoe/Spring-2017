%  factorial(N,M) means "the factorial of N is M"

factorial( 0, 1 ).

factorial( N, M ) :- X is N-1, factorial(X,Y), M is N*Y.
