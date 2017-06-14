%  myreverse(X,A,R) means R is X prepended to A in reverse order
%     (so myreverse(X,[],R) means R is X reversed)

myreverse([],A,A).

myreverse([H|T],A,R) :- myreverse(T,[H|A],R).
