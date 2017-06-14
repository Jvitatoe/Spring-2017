% Miscellaneous Prolog Things

%  careful to not put in a space like  f (X,Y)

%  if don't have a left part, is just stuff to do
:- write('hello'), nl.

list([]).
list([_|_]).

% append( X,Y, Z ) means if you concatenate X and Y you get Z
append1([],L,L).
append1([X|L1],L2,[X|L3] ) :- append1( L1, L2, L3 ).

delete1(H,[H|T],T).
delete1(X,[H|T],[H|U]) :- delete1( X, T, U ).

different(X,X) :- !,fail.
different(_,_).
