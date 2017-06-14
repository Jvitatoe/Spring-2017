%  incall(X,Y) means Y is the list of all the numbers in X incremented

incall( [], [] ).

% redundant:  incall( [H], [Z] ) :- Z is H+1.

incall( [H|T], [A|B] ) :- A is H+1, incall( T, B ).

%  swapPairs( X, Y ) means Y is X with all its pairs swapped

swapPairs( [], [] ).

swapPairs( [ [A,B] | R ], [ [B,A] | S ] ) :- swapPairs( R, S ).

%  minlist( X, A ) means A is the min in list X

minList( [A], A ).

minList( [H|T], H ) :- minList( T, X ), H <= X.

need another rule!
