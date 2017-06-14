father(homer,bart).
father(homer,lisa).
father(homer,maggie).
mother(marge,bart).
mother(marge,lisa).
mother(marge,maggie).
father(abe, homer ).

age(bart,10).
age(lisa,8).
age(maggie,2).
age(homer,42).
age(marge,40).
age(abe,107).

sibling(X,Y) :- father(F,X), father(F,Y), X \= Y.

youngersibling(X,Y) :- sibling(X,Y), age(X,A1), age(Y,A2), A1 < A2.

