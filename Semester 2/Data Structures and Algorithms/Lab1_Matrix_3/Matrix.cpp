#include "Matrix.h"
#include <exception>
using namespace std;


Matrix::Matrix(int nrLines, int nrCols) {
	   
	//WC = nrLines* nrCols   BC =1    TC=nrLines* nrCols
	//Implementation
	//O(n^2)
	if (nrLines <= 0 or nrCols <= 0)
	{
		throw exception();
	}
	this->nrL = nrLines;
	this->nrC = nrCols;
	this->mat = new TElem * [nrLines];
	for (int i = 0; i < nrLines; i++)
	{
		this->mat[i] = new TElem[nrCols];
		for (int j = 0; j < nrCols; j++)
		{
			this->mat[i][j] = NULL_TELEM;
		}
	}
}

//Matrix* Matrix::construct(TElem nrLine, TElem nrColumn, TElem** mat)
//{
//	if (mat == NULL)
//	{
//		return NULL_TELEM;
//	}
//	this->nrL = nrLine;
//	this->nrC = nrColumn;
//
//} 



Matrix::~Matrix()
{
	delete this->mat;
}

int Matrix::nrLines() const {
	//Total Complexity=1
	//Implementation
	return this->nrL;
}


int Matrix::nrColumns() const {
	//Total Complexity=1
	// O(1)
	//Implementation
	return this->nrC;
}


TElem Matrix::element(int i, int j) const {
	//Implementation
	//Total Complexity=1
	//O(1)
	if (i > this->nrL or j > this->nrC)
		throw exception();
	else if (i < 0 or j < 0)
		throw exception();
	return this->mat[i][j];

}

int Matrix::numberOfNonZeroElems(int col) const
{
	//O(n)
	//WC = n   BC = 1
	if (col <= 0)
		throw exception();
	int count = 0;
	for (int i = 0; i < this->nrL; i++)
	{
		if (this->mat[i][col] != NULL_TELEM)
			count++;
	}
	return count;
}

TElem Matrix::modify(int i, int j, TElem e) {
	//Implementation
	//Total Complexity = 1
	//O(1)
	if (i > this->nrL or j > this->nrC)
		throw exception();
	TElem old;
	old = this->mat[i][j];
	this->mat[i][j] = static_cast<TElem>(e);
	return old;
}


