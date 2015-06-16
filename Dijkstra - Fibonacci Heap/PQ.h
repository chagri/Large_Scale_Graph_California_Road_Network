
#ifndef _PQ_H_
#define _PQ_H_

#include <stdio.h>
#include <vector> 


enum State
{
	LABELED, UNLABELED, SCANNED
};

class Node;

class Edge
{
private:
public:
	Node* tail;
	Node* head;
	double length;
	double delta;

	Edge(Node* tail, Node* head, double length);
};


class Node
{
private:
public:
	Node * parent;
	Node * leftSibling, * rightSibling;
	Node * children; 
	Node * pred;

	Node(int data, double key);
	Node();

	int data;
	double key;
	int rank;

	std::vector<Edge*> incomingEdges;
	std::vector<Edge*> outgoingEdges;

	State state;

	bool addChild(Node * node);
	bool addSibling(Node * node);
	bool remove();
	
	Node* leftMostSibling();
	Node* rightMostSibling();

	void addIncomingEdge(Edge * edge);
	void addOutgoingEdge(Edge * edge);

};

class PQ
{
private:
	Node* rootListByRank[100];

	bool link(Node* root);	
	Node * minRoot;

public:

	PQ();
	PQ(Node *root);

	~PQ();

	bool isEmpty();
	bool insertVertex(Node * node);	
	void decreaseKey(double delta, Node* vertex);

	Node* findMin();
	Node* deleteMin();
};

#endif
