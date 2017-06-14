edge(1,2).
edge(2,3).
edge(3,5).
edge(1,4).
edge(2,6).
edge(6,4).
edge(5,6).

%  mypath(X,Y,A,L) means 

mypath(X,Y,A,L) :- edge(Z,Y), mypath(X,Z,[Y|A],L).

mypath(X,Y,A,L) :- edge(X,Y), L=[X,Y|A].

