
%  if don't have a left part, is just stuff to do
:- write('hello'), nl.

%  ourMember( X, Y ) means X is a member of the list Y

ourMember( A, [A|_] ).

ourMember( A, [H|T] ) :- ourMember( A, T ).
