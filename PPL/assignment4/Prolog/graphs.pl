different(X,X) :- !,fail.
different(_,_).

edge(a,f).
edge(a,b).
edge(b,c).
edge(c,e).
edge(c,g).
edge(f,d).
edge(d,e).
edge(g,a).
edge(d,b).

pathEasy(X,Y) :- edge(X,Y).

pathEasy(X,Y) :- edge(X,A), pathEasy(A,Y).


%  path(X,Y,L) means L is a list of vertices going from X to Y, inclusive
%  path(a,e,[a,b,c,e])

path(X,Y,[X,Y]) :- edge(X,Y).

path(X,Y,[X|T] ) :- edge(X,A), path(A,Y,T).
