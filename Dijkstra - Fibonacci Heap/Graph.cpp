#include "PQ.h"
#include "Graph.h"
#include <string>
using namespace std;

std::vector<Node*> vertices;
std::vector<Edge*> edges;

Graph::Graph()
{
}

Graph::~Graph()
{
}

void Graph::createGraph(string filename)
{
	double noOfVertices;
	fstream myfile(filename.c_str(), std::ios_base::in);
	double a,b,check=0;
	double c;
	int head,tail;
	double length=1.0;
	int count =1;

	while(myfile >> a)
	{
		if(count == 1)
		{
			noOfVertices= a;
			for(int j=0; j<noOfVertices-1 ; j++)
			{
				Node* v = new Node(j, 0);
				vertices.push_back(v);
			
			}
			cout << "Done with node creation...."<<endl; 

			vertices.push_back(new Node(noOfVertices-1, 0)); 
			vertices[noOfVertices-1]->state = LABELED;
			count++;
		}
		else
			break;
	}
	int catcher=0;
	cout << "Start read data"<<endl<<endl;
	while(myfile >> head >> tail  )
	{
	
		length = 1.0;
		Edge* edge = new Edge(vertices[head], vertices[tail], length);
		edge->head->addIncomingEdge(edge);
		edge->tail->addOutgoingEdge(edge);
		edges.push_back(edge);
		catcher++;
	}
	cout << "Nodes:" << vertices.size() << " Edges:" << edges.size() <<endl <<endl;
}


	void Graph::computeShortestPath(int finish)
	{
		PQ* heap = new PQ();

		heap->insertVertex(vertices[finish-1]);
	
		bool abort = false;
		long j = 0;
	// Scan
		do
		{
		// Delete minimum path
			Node* v = heap->deleteMin();
		
			v->state = SCANNED;
		
			for(int i = 0; i < v->incomingEdges.size(); i++)
			{
				Edge* currentEdge = v->incomingEdges[i];
				Node* headOfCurrentEdge = currentEdge->tail;

				if(headOfCurrentEdge->state != SCANNED)
					{
					if(headOfCurrentEdge->state == UNLABELED)
					{
						// Insert a vertex with infinite key
						headOfCurrentEdge->state = LABELED;
						headOfCurrentEdge->pred = v;
						headOfCurrentEdge->key = v->key + currentEdge->length;
						heap->insertVertex(headOfCurrentEdge);
					}
					else if(headOfCurrentEdge->key > v->key + currentEdge->length )
					{
						// decrease the key of a vertex with finite key
						headOfCurrentEdge->pred = v;
						heap->decreaseKey(v->key + currentEdge->length, headOfCurrentEdge);
					}
				}
			}
		}
		while(!heap->isEmpty());
	}

	// Print out path
	int Graph::printPath(int start)
	{
		Node* temp = vertices[start-1];
		if(!temp->pred)
		{
			cout << "No Path available" << endl;
			return 0;
		}

		int vertexCount = 0;
		cout << "Shortest Path Distance: " << vertices[0]->key << endl << endl;
		cout << "Path: " ;
		while(temp)
		{
			cout<<temp->data+1;
			temp = temp->pred;
			if(temp)
				cout<<"-->";

			vertexCount++;
		}
		cout<<endl <<endl;

		return 0;
	}