#include <assert.h>
#include "Matrix.h"

using namespace std;

void testAll() { 
	Matrix m(4, 4);
	assert(m.nrLines() == 4);
	assert(m.nrColumns() == 4);	
	m.modify(1, 1, 5);
	assert(m.element(1, 1) == 5);
	TElem old = m.modify(1, 1, 6);
	m.modify(2, 1, 4);
	m.modify(3, 1, 7);
	assert(m.numberOfNonZeroElems(1) == 3);
	
	try {
		m.numberOfNonZeroElems(-3);
		assert(false);
	}
	catch (exception&) {
		assert(true);
	}
	assert(m.element(1, 2) == NULL_TELEM);
	assert(old == 5);
}