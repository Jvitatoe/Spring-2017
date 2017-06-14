edge(1,2).
edge(2,3).
edge(3,5).
edge(1,4).
edge(2,6).
edge(6,4).
edge(5,6).

% path(X,Y,L) means:  there is a sequence of 1 or more edges from X to Y
% and L is a list of the vertices

path(X,Y,L) :- edge(X,Y), L=[X,Y].
path(X,Y,L) :- edge(X,A), path(A,Y,L2), L=[X|L2].
