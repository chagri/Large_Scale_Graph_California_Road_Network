#include <iostream>
#include <string>
#include <stdlib.h>
#include "Graph.h"
#include "PQ.h"
#include <time.h>
#include <random>
using namespace std;
int main(int argc, char* argv[])
{
	string filename = "C:/Users/Shankar/Desktop/test/edge/1Mil.txt"; 
	int min,max;
	min =1;
	max = 34;
	clock_t t1,t2;
	std::mt19937 rng(time(NULL));
	std::uniform_int_distribution<int> gen(min, max); // uniform, unbiased
	int start = gen(rng);
	int finish = gen(rng);

	t1=clock();
	Graph g;
	g.createGraph(filename.c_str());
	g.computeShortestPath(finish);
	int returnValue = g.printPath(start);
	t2=clock();

	float diff ((float)t2-(float)t1);
    cout<<"Time taken = "<<diff<<endl;
	return 0;

}