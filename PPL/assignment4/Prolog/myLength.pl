% myLength( List, N ) means the length of List is N

myLength( [], 0 ).

myLength( [H|T], N ) :- myLength(T,N1), N is N1+1.
